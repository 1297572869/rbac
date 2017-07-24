package com.cgeel.common.syn;

/**
 * Created by zxw on 2015/8/11.
 */
public interface SynchronousBlock<T,E extends Exception> {

    public T run() throws E;

}
