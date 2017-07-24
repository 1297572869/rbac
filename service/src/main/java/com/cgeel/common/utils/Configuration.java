package com.cgeel.common.utils;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private static Properties props;

    public static void init(String filePath){
        props = new Properties();
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(filePath);
            Assert.isTrue(resources.length > 0);
            Resource resource = resources[0];
            props.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static String getProperty(String key){
        if(props == null){
            throw new BeanInitializationException("Configuration没有初始化");
        }
		return props.getProperty(key);
	}

    public static String getProperty(String key, String defaultValue){
        String value = getProperty(key);
        if(value == null){
            return defaultValue;
        }
        return value;
    }
	
}
