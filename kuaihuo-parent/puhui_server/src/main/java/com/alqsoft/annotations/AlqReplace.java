package com.alqsoft.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * 
 * @author 张灿
 * @e-mail 1023059764@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-6-12 下午1:37:27
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AlqReplace {
	String value() default "*";
	int length() default 6;
}
