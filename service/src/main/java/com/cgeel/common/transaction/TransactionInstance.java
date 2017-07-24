package com.cgeel.common.transaction;

import java.util.List;

/**
 * Created by zxw on 2015/8/19.
 */
public class TransactionInstance {

    private String id;
    private String name;
    private List<TransactionBlock> transactionBlockList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TransactionBlock> getTransactionBlockList() {
        return transactionBlockList;
    }

    public void setTransactionBlockList(List<TransactionBlock> transactionBlockList) {
        this.transactionBlockList = transactionBlockList;
    }
}
