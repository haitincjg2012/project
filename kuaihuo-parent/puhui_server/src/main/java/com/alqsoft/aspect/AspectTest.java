package com.alqsoft.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author 张灿
 * @e-mail 1023059764@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-6-11 下午8:47:30
 * 
 */
@Aspect
@Component
public class AspectTest {
	
	@Pointcut("within(com.alqsoft.mybatis..*)")
	public void inMybatis(){}
	@Pointcut("@annotation(com.alqsoft.annotations.AlqReplace)")
	public void inAnnotations(){}
}
