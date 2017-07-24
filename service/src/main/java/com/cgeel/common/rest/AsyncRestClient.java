package com.cgeel.common.rest;

import com.cgeel.common.Paginator;
import com.cgeel.common.rest.converter.BeanConverter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by zxw on 2015/7/6.
 */
public interface AsyncRestClient {

    /**
     * GET请求，得到单个对象
     * @param url url地址   /user/{id}
     * @param tClass 返回类型
     * @param urlVars url地址占位符替换 id具体的值
     */
    <T> Future<T> get(String url, Class<T> tClass, Object... urlVars);

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> get(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> get(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars);

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     * @param converter 类型转换
     */
    <T, V> Future<V> get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars);

    /**
     * GET请求，得到单个对象
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     * @param converter 类型转换
     */
    <T, V> Future<V> get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Map<String, Object> urlVars);

    /**
     * GET请求，返回list
     * @param url url地址
     * @param tClass 返回类型
     * @param params 参数 ?后面的参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<List<T>> list(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    <T> Future<Paginator> page(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> put(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param converter 转换类
     * @param urlVars url地址占位符替换
     */
    <T, V> Future<V> put(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars);

    /**
     * PUT请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数 在body内提交和POST类似
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> put(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars);

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> post(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param converter 转换器
     * @param urlVars url地址占位符替换
     */
    <T, V> Future<V> post(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars);

    /**
     * POST请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> post(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars);

    /**
     * DELETE请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> delete(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars);

    /**
     * DELETE请求
     * @param url url地址
     * @param tClass 返回类型 无返回传入 void.class
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    <T> Future<T> delete(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars);

    /**
     * DELETE请求
     * @param url url地址
     * @param params 参数
     * @param urlVars url地址占位符替换
     */
    void delete(String url, Map<String, Object> params, Object... urlVars);

}
