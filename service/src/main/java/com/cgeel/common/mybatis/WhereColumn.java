package com.cgeel.common.mybatis;

/**
 * Created by ZXW on 2015/1/16.
 */
public class WhereColumn {
    private String name;
    private String operator;
    private String prefix;
    private Object pararm;

    
    public Object getPararm() {
		return pararm;
	}

	public void setPararm(Object pararm) {
		this.pararm = pararm;
	}

	public WhereColumn(){}

    public WhereColumn(String name, String operator){
        this.name = name;
        this.operator = operator;
    }

    public WhereColumn(String name, String operator, String prefix){
        this.name = name;
        this.operator = operator;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
