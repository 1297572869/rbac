package com.cgeel.common.rest.converter;

import com.cgeel.common.utils.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxw on 2015/7/6.
 */
public class ListConverter implements BeanConverter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @SuppressWarnings("unchecked")
    public <T> T converter(String orgin, final Class<?> tClass) {
        return (T) StringUtils.toObject(orgin, new TypeReference<List<?>>() {
            @Override
            public Type getType() {
                return CollectionType.construct(List.class, SimpleType.construct(tClass));
            }
        });
    }

}
