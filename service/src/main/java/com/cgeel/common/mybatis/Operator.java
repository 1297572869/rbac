package com.cgeel.common.mybatis;

/**
 * Created by ZXW on 2015/1/30.
 */
public enum Operator {

    EQ(" = "),
    NOT_EQ(" != "),
    LIKE(" LIKE "),
    IN(" IN "),
    NOT_IN(" NOT IN "),
    LT(" < "),
    GT(" > "),
    LT_EQ(" <= "),
    GT_EQ(" >= ");

    Operator(String op){
        this.op = op;
    }
    private String op;

    public String getOp() {
        return op;
    }
}
