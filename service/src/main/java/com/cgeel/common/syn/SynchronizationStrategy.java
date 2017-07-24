package com.cgeel.common.syn;

/**
 * Created by zxw on 2015/8/11.
 */
public abstract class SynchronizationStrategy {

    public abstract boolean beforeExecute(String syn);

    public abstract void afterExecute(String syn);

    public <T, E extends Exception> T execute(SynchronousBlock<T, E> block, String syn) throws E {
        if(beforeExecute(syn)) {
            T result = null;
            try {
                result = block.run();
            } finally {
                afterExecute(syn);
            }
            return result;
        }
        return null;
    }

}
