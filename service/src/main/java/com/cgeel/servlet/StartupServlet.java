package com.cgeel.servlet;

import com.cgeel.common.SpringContext;
import com.cgeel.common.mybatis.EntityScan;
import com.cgeel.common.utils.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 昕炜
 * Date: 13-4-17
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class StartupServlet extends DispatcherServlet {

	public static Map<String,Object> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    protected WebApplicationContext initWebApplicationContext() {
        WebApplicationContext ac = super.initWebApplicationContext();

        SpringContext.setApplicationContext(ac);

        //扫描Entity类
        EntityScan entityScan = ac.getBean(EntityScan.class);
        if(entityScan != null){
            entityScan.scan();
            //项目定制，强行修改mybatis配置
            entityScan.mybatisCustomizedInit();
        }
        Configuration.init("classpath*:config/web*.properties");

        return ac;
    }

}
