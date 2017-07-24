package com.cgeel.common.transaction;

/**
 * Created by zxw on 2015/8/19.
 */
public interface DistributedTransactionManager {

    public void init();

    public String startTransaction(String transactionId);

    public void commit(String name, String mark, Object data);

    public void commitAndFinish(String name, String mark, Object data);

    public void rollback(String mark);

    public void finish();

}
