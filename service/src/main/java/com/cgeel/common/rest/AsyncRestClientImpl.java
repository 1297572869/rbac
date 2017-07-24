package com.cgeel.common.rest;

import com.cgeel.common.Paginator;
import com.cgeel.common.rest.converter.BeanConverter;
import com.cgeel.common.rest.exception.RestException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by zxw on 2015/7/7.
 */
public class AsyncRestClientImpl implements AsyncRestClient {

    private RestClient restClient;
    ExecutorService executor = Executors.newFixedThreadPool(100);

    public AsyncRestClientImpl(){
    }

    public AsyncRestClientImpl(RestClient restClient){
        this.restClient = restClient;
    }

    @Override
    public <T> Future<T> get(final String url, final Class<T> tClass, final Object ... urlVars){
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.get(url, tClass, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> get(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.get(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> get(final String url, final Class<T> tClass, final Map<String, Object> params, final Map<String, Object> urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.get(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T, V> Future<V> get(final String url, final Class<T> tClass, final Map<String, Object> params, final BeanConverter converter, final Object... urlVars) {
        return execute(new Executor<V>() {
            @Override
            public V doExecute() throws RestException {
                return restClient.get(url, tClass, params, converter, urlVars);
            }
        });
    }

    @Override
    public <T, V> Future<V> get(final String url, final Class<T> tClass, final Map<String, Object> params, final BeanConverter converter, final Map<String, Object> urlVars) {
        return execute(new Executor<V>() {
            @Override
            public V doExecute() throws RestException {
                return restClient.get(url, tClass, params, converter, urlVars);
            }
        });
    }

    @Override
    public <T> Future<List<T>> list(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<List<T>>() {
            @Override
            public List<T> doExecute() throws RestException {
                return restClient.list(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<Paginator> page(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<Paginator>() {
            @Override
            public Paginator doExecute() throws RestException {
                return restClient.page(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> put(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.put(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T, V> Future<V> put(final String url, final Class<T> tClass, final Map<String, Object> params, final BeanConverter converter, final Object... urlVars) {
        return execute(new Executor<V>() {
            @Override
            public V doExecute() throws RestException {
                return restClient.put(url, tClass, params, converter, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> put(final String url, final Class<T> tClass, final Map<String, Object> params, final Map<String, Object> urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.put(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> post(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.post(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T, V> Future<V> post(final String url, final Class<T> tClass, final Map<String, Object> params, final BeanConverter converter, final Object... urlVars) {
        return execute(new Executor<V>() {
            @Override
            public V doExecute() throws RestException {
                return restClient.post(url, tClass, params, converter, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> post(final String url, final Class<T> tClass, final Map<String, Object> params, final Map<String, Object> urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.post(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> delete(final String url, final Class<T> tClass, final Map<String, Object> params, final Object... urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.delete(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public <T> Future<T> delete(final String url, final Class<T> tClass, final Map<String, Object> params, final Map<String, Object> urlVars) {
        return execute(new Executor<T>() {
            @Override
            public T doExecute() throws RestException {
                return restClient.delete(url, tClass, params, urlVars);
            }
        });
    }

    @Override
    public void delete(final String url, final Map<String, Object> params, final Object... urlVars) {
        execute(new Executor<Void>() {
            @Override
            public Void doExecute() throws RestException {
                restClient.delete(url, params, urlVars);
                return null;
            }
        });
    }

    private <T> Future<T> execute(final Executor<T> exec){
        FutureTask<T> future =
                new FutureTask<T>(new Callable<T>() {
                    public T call() throws RestException {
                        return exec.doExecute();
                    }});
        executor.execute(future);
        return future;
    }

    interface Executor<T>{
        T doExecute() throws RestException;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }
}
