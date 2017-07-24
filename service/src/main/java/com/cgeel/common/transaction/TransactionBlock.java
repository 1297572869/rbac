package com.cgeel.common.transaction;

/**
 * Created by zxw on 2015/8/19.
 */
public class TransactionBlock {
    private String name;
    private String rollbackName;
    private String rollbackMethod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollbackName() {
        return rollbackName;
    }

    public void setRollbackName(String rollbackName) {
        this.rollbackName = rollbackName;
    }

    public String getRollbackMethod() {
        return rollbackMethod;
    }

    public void setRollbackMethod(String rollbackMethod) {
        this.rollbackMethod = rollbackMethod;
    }
}
