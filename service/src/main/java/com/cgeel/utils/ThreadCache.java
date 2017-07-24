package com.cgeel.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxw on 2015/8/14.
 */
public class ThreadCache {

    private static ThreadLocal<Map<String, Object>>  tl = new ThreadLocal<>();

    public static void set(String key, Object obj){
        Map<String, Object> map = tl.get();
        if(map == null){
            map = new HashMap<>();
            map.put(key, obj);
            tl.set(map);
        }else{
            map.put(key, obj);
        }
    }

    public static Object get(String key){
        Map<String, Object> map = tl.get();
        if(map != null){
            return map.get(key);
        }
        return null;
    }

    public static void clear(){
        tl.remove();
    }

}
