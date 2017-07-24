package com.cgeel.common.rest.exception;

import com.cgeel.common.validate.ValidateException;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZXW
 * Date: 14-4-2
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        response.setCharacterEncoding("UTF-8");
        HandlerMethod hm = (HandlerMethod) o;
        if(e != null){
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(
                        response.getOutputStream(), JsonEncoding.UTF8);
                response.setStatus(500);
                if(e instanceof RestException){
                    RestException re = (RestException) e;
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", re.getCode());
                    map.put("reason", re.getReason());
                    mapper.writeValue(jsonGenerator, map);
                }else if(e instanceof TypeMismatchException){
                    TypeMismatchException te = (TypeMismatchException)e;
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 30002);
                    map.put("reason", te.getMessage());
                    mapper.writeValue(jsonGenerator, map);
                }else if(e instanceof MissingServletRequestParameterException){
                    MissingServletRequestParameterException pe = (MissingServletRequestParameterException)e;
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 30001);
                    map.put("reason", "参数 " + pe.getParameterName() + " 不能为空，类型为" + pe.getParameterType());
                    mapper.writeValue(jsonGenerator, map);
                }else if(e instanceof BindException){
                    BindException be = (BindException)e;
                    Map<String, Object> map = new HashMap<>();
                    List<String> list = new ArrayList<>();
                    for(FieldError fe : be.getFieldErrors()){
                        list.add(fe.getDefaultMessage());
                    }
                    map.put("code", 30002);
                    map.put("reason", list.toString());
                    mapper.writeValue(jsonGenerator, map);
                }else if(e instanceof ValidateException){
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 30002);
                    map.put("reason", e.getMessage());
                    mapper.writeValue(jsonGenerator, map);
                }else if(e instanceof HttpRequestMethodNotSupportedException){
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 400);
                    map.put("reason", e.getMessage());
                    mapper.writeValue(jsonGenerator, map);
                }else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", -1);
                    map.put("reason", "未知错误");
                    mapper.writeValue(jsonGenerator, map);
                    logger.error("未知错误", e);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new ModelAndView();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
