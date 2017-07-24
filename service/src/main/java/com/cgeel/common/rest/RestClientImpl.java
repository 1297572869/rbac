package com.cgeel.common.rest;

import com.cgeel.common.Paginator;
import com.cgeel.common.rest.converter.BeanConverter;
import com.cgeel.common.rest.exception.RestException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zxw on 2015/7/5.
 */
public class RestClientImpl implements RestClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private CloseableHttpClient httpClient;

    public RestClientImpl(final int executionCount, final int connectionMaxTotal, final int connectionDefaultMaxPerRoute){
        HttpClientBuilder builder = HttpClientBuilder.create();

        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount1, HttpContext context) {
                if (executionCount1 >= executionCount) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        //连接池最大生成连接数
        manager.setMaxTotal(connectionMaxTotal);
        // 默认设置route最大连接数 如访问一个域名最多
        manager.setDefaultMaxPerRoute(connectionDefaultMaxPerRoute);
        builder.setConnectionManager(manager);
        builder.setRetryHandler(httpRequestRetryHandler);

        httpClient = builder.build();
    }

    public RestClientImpl(final int executionCount, final int connectionMaxTotal, final int connectionDefaultMaxPerRoute, SSLConnectionSocketFactory sslsf){
        HttpClientBuilder builder = HttpClientBuilder.create();

        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= executionCount) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        //连接池最大生成连接数200
        manager.setMaxTotal(connectionMaxTotal);
        // 默认设置route最大连接数为20
        manager.setDefaultMaxPerRoute(connectionDefaultMaxPerRoute);
        builder.setConnectionManager(manager);
        builder.setRetryHandler(httpRequestRetryHandler);
        builder.setSSLSocketFactory(sslsf);

        httpClient = builder.build();
    }

    @Override
    public <T> T get(String url, Class<T> tClass, Object ... urlVars) throws RestException {
        return get(url, tClass, null, urlVars);
    }

    @Override
    public <T> T get(String url, Class<T> tClass, Map<String, Object> params, Object ... urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass);
    }

    @Override
    public <T> T get(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass);
    }

    @Override
    public <T, V> V get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object ... urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass, converter);
    }

    @Override
    public <T, V> V get(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Map<String, Object> urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass, converter);
    }

    @Override
    public <T> List<T> list(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass, BeanConverter.list);
    }

    @Override
    public <T> Paginator page(String url, Class<T> tClass, Map<String, Object> params, Object... urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        return get(toURI(url, urlVars), tClass, BeanConverter.page);
    }

    private <T, V> T get(URI uri, Class<V> tClass, BeanConverter converter) throws RestException {
        Result result = httpGet(uri);
        return resolve(tClass, result, converter);
    }

    private <T> T get(URI uri, Class<T> tClass) throws RestException {
        Result result = httpGet(uri);
        return resolve(tClass, result);
    }

    @Override
    public <T> T put(String url, Class<T> tClass, Map<String, Object> params, Object ... urlVars) throws RestException {
        Result result = httpPut(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result);
    }

    @Override
    public <T, V> V put(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars) throws RestException {
        Result result = httpPut(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result, converter);
    }

    @Override
    public <T> T put(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException {
        Result result = httpPut(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result);
    }

    @Override
    public <T> T post(String url, Class<T> tClass, Map<String, Object> params, Object ... urlVars) throws RestException {
        Result result = httpPost(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result);
    }

    @Override
    public <T, V> V post(String url, Class<T> tClass, Map<String, Object> params, BeanConverter converter, Object... urlVars) throws RestException {
        Result result = httpPost(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result, converter);
    }

    @Override
    public <T> T post(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException {
        Result result = httpPost(toURI(url, urlVars), nameValuePair(params));
        return resolve(tClass, result);
    }

    @Override
    public <T> T delete(String url, Class<T> tClass, Map<String, Object> params, Object ... urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        Result result = httpDelete(toURI(url, urlVars));
        return resolve(tClass, result);
    }

    @Override
    public <T> T delete(String url, Class<T> tClass, Map<String, Object> params, Map<String, Object> urlVars) throws RestException {
        if(params != null && params.size() > 0) {
            url = mergeParam(url, params);
        }
        Result result = httpDelete(toURI(url, urlVars));
        return resolve(tClass, result);
    }

    @Override
    public void delete(String url, Map<String, Object> params, Object ... urlVars) throws RestException {
        delete(url, void.class, params, urlVars);
    }

    @Override
    public void close() {
        if(this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.manager.close();
        }
    }

    private <T> T resolve(Class<T> tClass, Result result) throws RestException {
        return resolve(tClass, result, BeanConverter.simple);
    }

    private <T, V> T resolve(Class<V> tClass, Result result, BeanConverter beanConverter) throws RestException {
        if(result == null){
            throw RestException.Error(0, "服务器无响应");
        }
        if(result.getCode() != 200){
            if(result.getCode() == 500) {
                try {
                    Map<?, ?> map = BeanConverter.simple.converter(new String(result.getContent(), Charset.forName("UTF-8")), Map.class);
                    throw RestException.Error(result.getCode(), map.get("reason").toString());
                }catch (RuntimeException e){
                    e.printStackTrace();
                    logger.error(new String(result.getContent(), Charset.forName("UTF-8")));
                    throw RestException.Error(result.getCode(), "无法解析的错误");
                }
            }else if(result.getCode() == 404){
                throw RestException.Error(result.getCode(), "无效的URL");
            }else{
                logger.error(new String(result.getContent(), Charset.forName("UTF-8")));
                throw RestException.Error(result.getCode(), "无法解析的错误");
            }
        }
        if (tClass.equals(Void.class) || tClass.equals(void.class)){
            return null;
        }
        if(result.getLength() == 0){
            return null;
        }

        return beanConverter.converter(new String(result.getContent(), Charset.forName("UTF-8")), tClass);
    }

    /**
     * 合并参数
     * @param url
     * @param params
     * @return
     */
    private String mergeParam(String url, Map<String, Object> params) {
        //String variable = URLEncodedUtils
        //        .format(nameValuePair(params), "UTF-8");
        StringBuilder variableBuilder = new StringBuilder();
        for(String key : params.keySet()){
            if(params.get(key) != null){
                variableBuilder.append(key).append("=").append(params.get(key));
            }
            variableBuilder.append("&");
        }
        String variable = "";
        if(variableBuilder.length() > 0 ){
            variable = variableBuilder.substring(0, variableBuilder.length()-1);
        }

        if (!variable.trim().equals("")) {
            if (url.contains("?")) {
                url += "&" + variable;
            } else {
                url += "?" + variable;
            }
        }

        return url;
    }

    /**
     * map转参数
     *
     * @param params
     * @return
     */
    private List<NameValuePair> nameValuePair(Map<String, Object> params) {
        List<NameValuePair> parameters = new ArrayList<>();
        if(params == null){
            return parameters;
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(entry.getValue() == null){
                continue;
            }
            if(entry.getValue() instanceof Collection){
                Collection coll = (Collection)entry.getValue();
                for(Object obj : coll){
                    if(obj != null) {
                        parameters.add(new BasicNameValuePair(entry.getKey(), obj.toString()));
                    }
                }
            }else if(entry.getValue().getClass().isArray()){
                Object[] objs = (Object[]) entry.getValue();
                for(Object obj : objs){
                    if(obj != null) {
                        parameters.add(new BasicNameValuePair(entry.getKey(), obj.toString()));
                    }
                }
            }else {
                String value = entry.getValue().toString();
                if (value != null) {
                    parameters.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }

        return parameters;
    }

    private URI toURI(String url, Object ... urlVars){
        URI uri;
        if(urlVars != null && urlVars.length > 0) {
            uri = new UriTemplate(url).expand(urlVars);
        }else{
            uri = new UriTemplate(url).expand();
        }
        return uri;
    }

    private URI toURI(String url, Map<String, Object> urlVars){
        URI uri;
        if(urlVars != null && urlVars.size() > 0) {
            uri = new UriTemplate(url).expand(urlVars);
        }else{
            uri = new UriTemplate(url).expand();
        }
        return uri;
    }

    public Result httpGet(URI uri){
        HttpGet httpGet = new HttpGet(uri);
        return execute(httpGet);
    }

    public Result httpPut(URI uri, List<NameValuePair> nameValuePair){
        HttpPut httpPut = new HttpPut(uri);
        httpPut.setEntity(new UrlEncodedFormEntity(nameValuePair, Charset.forName("UTF-8")));
        return execute(httpPut);
    }

    public Result httpPost(URI uri, List<NameValuePair> nameValuePair){
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, Charset.forName("UTF-8")));
        return execute(httpPost);
    }

    public Result httpDelete(URI uri){
        HttpDelete httpDelete = new HttpDelete(uri);
        return execute(httpDelete);
    }

    public Result execute(HttpRequestBase request){
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(request);
            entity = response.getEntity();
            int code = response.getStatusLine().getStatusCode();
            if(code != 200){
                logger.warn("URL: " + request.getURI().toString());
                logger.warn("http code：" + code);
            }
            //System.out.println(ContentType.getOrDefault(entity));
            byte[] bytes = EntityUtils.toByteArray(entity);
            long length = entity.getContentLength();
            return new Result(length, bytes, code);
        } catch (IOException e) {
            logger.error("httpclient调用异常", e);
        }finally {
            try {
                if(entity != null){
                    EntityUtils.consume(entity);
                }
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
