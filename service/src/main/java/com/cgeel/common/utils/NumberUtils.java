package com.cgeel.common.utils;

import java.math.BigDecimal;

/**
 * Created by zxw on 2015/8/24.
 */
public class NumberUtils {

    public static double halfUp(double num, Integer scale){
        BigDecimal n = BigDecimal.valueOf(num);
        return n.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
