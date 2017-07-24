package com.cgeel.common.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxw on 2015/8/19.
 */
public abstract class TransactionRegisterTools {

    private List<TransactionInstance> list = new ArrayList<>();

    public void addTransactionInstance(TransactionInstance ti){
        list.add(ti);
    }

    public List<TransactionInstance> getTransactionInstanceList(){
        return list;
    }

    public abstract void register();

}
