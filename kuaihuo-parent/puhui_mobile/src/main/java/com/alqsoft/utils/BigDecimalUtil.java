package com.alqsoft.utils;

import java.math.BigDecimal;

/**
 * @author xwolf
 * @date 2017-04-21 19:40
 * @since 1.8
 */
public class BigDecimalUtil {

    public static BigDecimal div(long a,long b,int scale){
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b)).setScale(scale);
    }

    public static BigDecimal div(long a,long b){
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b)).setScale(2);
    }

    public static String div(Long a){
        return  div(a,100,2).toString();
    }

}
