package com.cgeel.interceptor;

import com.cgeel.common.web.ExcludeInterceptor;
import com.cgeel.model.Admin;
import com.cgeel.utils.UserHolder;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用来拦截用户是否登录的
 * 
 * @author ZXW
 * @date 2012-11-28
 * 
 */
public class ConsoleInterceptor implements HandlerInterceptor, InitializingBean {

	private final Logger logger = Logger
			.getLogger(ConsoleInterceptor.class);
	

	@Autowired
	private ApplicationContext applicationContext;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private int flag = 0;

	private Set<String> urls = new LinkedHashSet<String>();
	
	@Autowired
	@Qualifier(value = "mediaDomain")
	private String mediaDomain;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setAttribute("mediaDomain", mediaDomain);
		
		Admin user = findUserFromCookie(request);
        if (user == null) {
            String ticket = request.getParameter("ticket");
            user = findUserFromCache(ticket, request, response);
        }

        UserHolder.setAdmin(user);
        request.setAttribute("adminUserSession", user);

		String path = urlPathHelper.getLookupPathForRequest(request);
		if (urls.contains(path)) {
			return true;
		}

		for (String registeredPattern : urls) {
			if (pathMatcher.match(registeredPattern, path)) {
				return true;
			}
		}

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.do");
            return false;
        }


		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        UserHolder.clear();
	}

    private Admin findUserFromCookie(HttpServletRequest request)
            throws IOException {
    	Admin user = null;
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(UserHolder.ADMIN_USER_SESSION_KEY)) {
                        String key = cookie.getValue();
                        user = findUserFromCache(key, request, null);
                    }
                }
            }
        }
        return user;
    }

    private Admin findUserFromCache(String key, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        if (key != null && !"".equals(key.trim())) {
            if (response != null) {
                Cookie cookie = new Cookie(UserHolder.ADMIN_USER_SESSION_KEY, key);
                cookie.setPath("/");
                /*if (StringUtils.isNotBlank(domain)) {
                    cookie.setDomain(domain);
                }*/
                response.addCookie(cookie);
            }
            MemcachedClient mt = applicationContext.getBean(MemcachedClient.class);
            try {
            	Admin userStr = (Admin) mt.get(key);
                if(userStr != null){
                    mt.set(key, 3600, userStr);
                }
                return userStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return null;
    }

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> map = applicationContext
				.getBeansWithAnnotation(Controller.class);
		for (Object obj : map.values()) {
			RequestMapping mapping = AnnotationUtils.findAnnotation(
					obj.getClass(), RequestMapping.class);
			final ExcludeInterceptor ein = AnnotationUtils.findAnnotation(
					obj.getClass(), ExcludeInterceptor.class);
			final String[] m;
			if (mapping != null) {
				m = mapping.value();
			} else {
				m = new String[] { "" };
			}
			ReflectionUtils.doWithMethods(obj.getClass(),
					new ReflectionUtils.MethodCallback() {
						public void doWith(Method method) {
							RequestMapping mapping = AnnotationUtils
									.findAnnotation(method,
											RequestMapping.class);
							ExcludeInterceptor ei = AnnotationUtils
									.findAnnotation(method,
											ExcludeInterceptor.class);
							if (mapping != null && (ein != null || ei != null)) {
								if (flag != 2) {
									flag = 1;
								}
								for (String mm : m) {
									addUrls(mapping, mm);
								}
							}
						}
					}, ReflectionUtils.USER_DECLARED_METHODS);
			if (flag == 2 && mapping != null && ein != null) {
				addUrls(mapping, null);
			}
			flag = 0;
		}
	}

	private void addUrls(RequestMapping mapping, String pre) {
		String[] mappedPatterns = mapping.value();
		if (mappedPatterns != null && mappedPatterns.length > 0) {
			for (String mappedPattern : mappedPatterns) {
				if (!mappedPattern.startsWith("/")) {
					mappedPattern = "/" + mappedPattern;
				}
				if (pre != null) {
					mappedPattern = pre + mappedPattern;
				}
				urls.add(mappedPattern);
				if (mappedPattern.indexOf('.') == -1
						&& !mappedPattern.endsWith("/")) {
					urls.add(mappedPattern + ".*");
					urls.add(mappedPattern + "/");
				}
			}
		} else {
			flag = 2;
			urls.add(null);
		}
	}

}
