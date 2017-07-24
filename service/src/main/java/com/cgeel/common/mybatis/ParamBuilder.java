package com.cgeel.common.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZXW on 2015/1/19.
 */
public class ParamBuilder {

    private Map<String, Object> map = new HashMap<>();

    private ParamBuilder(){}

    public static ParamBuilder builder(){
        return new ParamBuilder();
    }

    public ParamBuilder put(String name, Object value){
        map.put(name, value);
        return this;
    }

    public Map<String, Object> build(){
        return map;
    }

}
