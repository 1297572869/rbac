package com.cgeel.common.rest.converter;

/**
 * Created by zxw on 2015/7/4.
 */
public interface BeanConverter {

    public static final BeanConverter simple = new SimpleBeanConverter();
    public static final BeanConverter list = new ListConverter();
    public static final BeanConverter page = new PageConverter();

    public <T> T converter(String orgin, Class<?> tClass);

}
