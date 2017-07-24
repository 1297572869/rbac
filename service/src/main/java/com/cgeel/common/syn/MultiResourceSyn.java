package com.cgeel.common.syn;

import java.util.List;

/**
 * Created by zxw on 2015/9/23.
 */
public abstract class MultiResourceSyn {

    public abstract boolean beforeExecute(List<String> syn);

    public abstract void afterExecute(List<String> syn);

    public <T, E extends Exception> T execute(SynchronousBlock<T, E> block, List<String> syn) throws E {
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
