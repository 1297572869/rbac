package com.cgeel.utils;

import java.util.List;

/**
 * Created by zxw on 2015/7/31.
 */
public class RequestUtils {

    private static ThreadLocal<List<String>> tl = new ThreadLocal<List<String>>();

    public static List<String> getParameterNames(){
        return tl.get();
    }

    public static void setParameterNames(List<String> list){
        tl.set(list);
    }

    public static void clear() {
        tl.remove();
    }

}
