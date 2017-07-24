package com.cgeel.common.transaction;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxw on 2015/8/19.
 */
public class TransactionInstanceTools {

    private String transactionId;
    private String name;
    private List<TransactionBlock> linked = new ArrayList<>();

    private TransactionInstanceTools(){}

    public static TransactionInstanceTools build(){
        return new TransactionInstanceTools();
    }

    public TransactionInstanceTools setTransactionId(String transactionId){
        this.transactionId = transactionId;
        return this;
    }

    public TransactionInstanceTools setName(String name){
        this.name = name;
        return this;
    }

    public TransactionInstanceTools add(String name, String rollback, String method){
        TransactionBlock b = new TransactionBlock();
        b.setName(name);
        b.setRollbackName(rollback);
        b.setRollbackMethod(method);
        linked.add(b);
        return this;
    }

    public TransactionInstance builder(){
        if(StringUtils.isBlank(name)){
            throw new RuntimeException("name不能为空");
        }
        if(StringUtils.isBlank(transactionId)){
            throw new RuntimeException("transactionId不能为空");
        }
        if(linked == null || linked.isEmpty()){
            throw new RuntimeException("linked不能为空");
        }
        for(int i=0; i<linked.size(); i++){
            TransactionBlock b = linked.get(i);
            if(StringUtils.isBlank(b.getName())){
                throw new RuntimeException("linked中name不能为空");
            }
            /*if(i != linked.size()-1){
                if(StringUtils.isBlank(b.getRollbackName())){
                    throw new RuntimeException("linked中非最后一个元素rollback不能为空");
                }
                if(StringUtils.isBlank(b.getRollbackMethod())){
                    throw new RuntimeException("linked中rollbackMethod不能为空");
                }
            }*/
        }
        TransactionInstance transactionInstance = new TransactionInstance();
        transactionInstance.setName(name);
        transactionInstance.setId(transactionId);
        transactionInstance.setTransactionBlockList(linked);
        return transactionInstance;
    }

}
