package com.alqsoft.poi;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import javax.validation.constraints.NotNull;
import org.junit.Test;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-8 下午5:25:07
 * 
 */
public class Valitest {
	@Test
	public void asdfgf(){
		
		Field[] fields=User.class.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations=field.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation.annotationType()==NotNull.class);
			}
			System.out.println(field.getAnnotation(NotNull.class));
		}
	}
	
}
