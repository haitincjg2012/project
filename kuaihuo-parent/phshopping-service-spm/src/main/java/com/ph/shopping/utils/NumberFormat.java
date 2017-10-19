package com.ph.shopping.utils;

import org.apache.commons.lang3.StringUtils;

import com.ph.shopping.common.util.date.DoubleUtils;

/**
 * @author xwolf
 * @date 2017-05-31 15:30
 * @since 1.8
 */
public class NumberFormat {

    public static String getFormatNumber(String num){
        if (StringUtils.isBlank(num)){
            return "0";
        }
        if("null".equals(num)){
            return "0";
        }
        long parseNum = Long.parseLong(num);
        if ( parseNum <= 9999 ){
            return num;
        }
        if ( parseNum > 9999 && parseNum <=100000 ){
           double a = DoubleUtils.div(parseNum,10000,1);
           return a+"万";
        }
        if ( parseNum > 100000){
            long a = parseNum / 10000;
            return a+"万";
        }
        return "0";
    }

}
