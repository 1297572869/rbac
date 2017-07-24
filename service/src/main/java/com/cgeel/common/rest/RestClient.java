package com.cgeel.common.rest;

import com.cgeel.common.Paginator;
import com.cgeel.common.rest.converter.BeanConverter;
import com.cgeel.common.rest.exception.RestException;

import java.util.List;
import java.util.Map;

/**
 * Created by zxw on 2015/7/6.
 */
public interface RestClient {

    /**
     * GET请求，得到单个对象
     * @param url url地址   /user/{id}
     * @param tClass 返回类型
     * @param urlVars url地址占位符替换 id具体的值
     */
    <T> T get(String url, Class<T> tClass, Object... urlVars) throws RestException;

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> T get(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> T get(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException;

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     * @param converter 类型转换
     */
    <T, V> V get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars) throws RestException;

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     * @param converter 类型转换
     */
    <T, V> V get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Map<String, Object> urlVars) throws RestException;

    /**
     * GET请求，返回list
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> List<T> list(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * GET请求，返回page
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> Paginator page(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param urlVars url地址占位符替换
     */
    <T> T put(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param converter 转换类
     * @param urlVars url地址占位符替换
     */
    <T, V> V put(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars) throws RestException;

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param urlVars url地址占位符替换
     */
    <T> T put(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException;

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> T post(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param converter 转换器
     * @param urlVars url地址占位符替换
     */
    <T, V> V post(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars) throws RestException;

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> T post(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException;

    /**
     * DELETE请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> T delete(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException;

    /**
     * DELETE请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> T delete(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException;

    /**
     * DELETE请求
     * @param url url地址
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    void delete(String url, Map<String, Object> params, Object... urlVars) throws RestException;

    void close();

}
