package com.cgeel.common.retry;

/**
 * Created by ZXW on 2014/11/12.
 */
public abstract class Retry<T, V> {

    public abstract V execute(T obj) throws Exception;

    /**
     * 错误处理
     * @param ex
     * @param obj
     * @param retryCount
     * @return 返回 -1进入下一次循环，直到抛异常，1直接返回null
     * @throws Exception
     */
    public int errorHandle(Exception ex, T obj, int retryCount) throws Exception{
        ex.printStackTrace();
        return -1;
    }

}
