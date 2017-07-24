package com.cgeel.common.rest.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zxw on 2015/7/6.
 */
public class SimpleBeanConverter implements BeanConverter {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper om = new ObjectMapper();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T converter(String body, Class<?> type) {
        try {
            if(type == String.class){
                return (T)body;
            }else if (type == Boolean.TYPE || type == Boolean.class) {        // boolean
                return (T)Boolean.valueOf(body);
            } else if (type == Character.TYPE || type == Character.class) { // char
                return (T)Character.valueOf(body.toCharArray()[0]);
            } else if (type == Byte.TYPE || type == Byte.class) {    // byte
                return (T)Byte.valueOf(body);
            } else if (type == Short.TYPE || type == Short.class) {   // short
                return (T)Short.valueOf(body);
            } else if (type == Integer.TYPE || type == Integer.class) { // int
                return (T)Integer.valueOf(body);
            } else if (type == Long.TYPE || type == Long.class) {    // long
                return (T)Long.valueOf(body);
            } else if (type == Float.TYPE || type == Float.class) {   // float
                return (T)Float.valueOf(body);
            } else if (type == Double.TYPE || type == Double.class) {  // double
                return (T)Double.valueOf(body);
            } else if (type == Void.TYPE || type == Void.class) {    // void
                return null;
            }
            return (T)om.readValue(body, type);
        } catch (IOException e) {
            logger.error("json解析失败", e);
        }
        return null;
    }
}
