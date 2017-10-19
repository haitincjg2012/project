package com.alqsoft.aspect;

import java.lang.reflect.Field;
import java.util.List;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.reflection.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.alqsoft.annotations.AlqReplace;

/**
 * 
 * 
 * @author 张灿
 * @e-mail 1023059764@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-6-11 下午9:29:19
 * 
 */
@Aspect
@Component
public class AdviceTest {
	@Before("com.alqsoft.aspect.AspectTest.inMybatis()")
	public void beforeMybatis() {
		System.out.println("执行了");
	}

	@AfterReturning(value = "com.alqsoft.aspect.AspectTest.inAnnotations() && @annotation(replace)", returning = "result")
	public void afterMybatis(AlqReplace replace, Object result) {
		// 查询自己的属性
		Class<?> c = result.getClass();
		System.out.println(c.getName() + "进来了-："
				+ (result instanceof EasyuiResult<?>));
		Field[] fs = c.getDeclaredFields();
		for (Field field : fs) {
			AlqReplace ar = field.getAnnotation(AlqReplace.class);
			if (ar == null) {
				continue;
			}
			String s = (String) ReflectionUtils.getFieldValue(result,
					field.getName());
			ReflectionUtils.setFieldValue(result, field.getName(),
					StringUtils.abbreviate(s, ar.length()));
		}
		// 处理list集合
		if (result instanceof List) {
			for (Object o : (List) result) {
				Field[] fs1 = o.getClass().getDeclaredFields();
				for (Field field : fs1) {
					AlqReplace ar = field.getAnnotation(AlqReplace.class);
					if (ar == null) {
						continue;
					}
					String s = (String) ReflectionUtils.getFieldValue(o,
							field.getName());
					ReflectionUtils.setFieldValue(o, field.getName(),
							StringUtils.abbreviate(s, ar.length()));
				}
			}
			return;
		}
		// 处理easyui返回的对象EasyuiResult
		if (result instanceof EasyuiResult<?>) {
			EasyuiResult<?> rs = (EasyuiResult<?>) result;
			;
			for (Object o : (List) rs.getT()) {
				Field[] fs1 = o.getClass().getDeclaredFields();
				for (Field field : fs1) {
					AlqReplace ar = field.getAnnotation(AlqReplace.class);
					if (ar == null) {
						continue;
					}
					String s = (String) ReflectionUtils.getFieldValue(o,
							field.getName());
					ReflectionUtils.setFieldValue(o, field.getName(),
							StringUtils.abbreviate(s, ar.length()));
				}
			}
			return;
		}
	}
}
