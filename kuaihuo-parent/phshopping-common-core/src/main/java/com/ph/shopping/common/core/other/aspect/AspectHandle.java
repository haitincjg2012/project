package com.ph.shopping.common.core.other.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import com.ph.shopping.common.util.container.ClassUtil;
/**
 * 
 * @ClassName:  AspectUtil   
 * @Description:解析切面方法参数   
 * @author: 李杰
 * @date:   2017年5月30日 下午4:47:30     
 * @Copyright: 2017
 */
public class AspectHandle {
	
	private static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	/**
	 * 参数类型
	 */
	private static final String[] TYPES = { 
		"java.lang.Integer", 
		"java.lang.Double", 
		"java.lang.Float",
		"java.lang.Long", 
		"java.lang.Short", 
		"java.lang.Byte", 
		"java.lang.Boolean", 
		"java.lang.Char",
		"java.lang.String", 
		"int", 
		"double", 
		"long", 
		"short", 
		"byte", 
		"boolean", 
		"char", 
		"float" 
	};
	/**
	 * 加载时执行排序
	 */
	static {
		Arrays.sort(TYPES);
	}
	 /**
	  * 
	  * @Title: getValueByName   
	  * @Description: 根据名字得到具体的参数值   
	  * @param: @param point
	  * @param: @param name
	  * @param: @return      
	  * @return: Object
	  * @author：李杰      
	  * @throws
	  */
	public static Object getValueByName(JoinPoint point, String name) {
		Object result = null;
		if (StringUtils.isBlank(name)) {
			return result;
		}
		Object[] objects = point.getArgs();
		if (ArrayUtils.isNotEmpty(objects)) {
			int num = valueNum(point, name);
			if (num >= 0) {
				result = objects[num];
			} else {
				int len = objects.length;
				for (int i = 0; i < len; i++) {
					result = getValue(objects[i], name);
					if (null != result) {
						break;
					}
				}
			}
		}
		return result;
	}
	/**
	 * 
	 * @Title: valueNum   
	 * @Description: 得到基本数据类型参数的下标   
	 * @param: @param point
	 * @param: @param name
	 * @param: @return      
	 * @return: int
	 * @author：李杰      
	 * @throws
	 */
	private static int valueNum(JoinPoint point, String name) {
		int num = -1;
		if (StringUtils.isNotBlank(name)) {
			MethodSignature methodSignature = (MethodSignature) point.getSignature();
			if (null != methodSignature) {
				Method method = methodSignature.getMethod();
				Class<?>[] parameterTypes = method.getParameterTypes();
				int len = parameterTypes == null ? 0 : parameterTypes.length;
				for (int i = 0; i < len; i++) {
					if (isExistsType(parameterTypes[i].getName())) {
						if (isExistsParamName(method, name)) {
							num = i;
							break;
						}
					}
				}
			}
		}
		return num;
	}
	/**
	 * 
	 * @Title: getValue   
	 * @Description: 根据字段得到参数值   
	 * @param: @param obj
	 * @param: @param name
	 * @param: @return      
	 * @return: Object
	 * @author：李杰      
	 * @throws
	 */
	private static Object getValue(Object obj, String name) {

		return ClassUtil.getPropertiesValue(name, obj);
	}
	/**
	 * 
	 * @Title: isExistsType   
	 * @Description: 判断参数是否属于基本数据类型   
	 * @param: @param typeName
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private static boolean isExistsType(String typeName) {
		
		return Arrays.binarySearch(TYPES, typeName) >= 0;
	}
	/**
	 * 
	 * @Title: getParameterNames   
	 * @Description: 得到所有的方法参数签名   
	 * @param: @param method
	 * @param: @return      
	 * @return: String[]
	 * @author：李杰      
	 * @throws
	 */
	public static String[] getParameterNames(Method method) {
		return parameterNameDiscoverer.getParameterNames(method);
	}
	/**
	 * 
	 * @Title: isExistsParamName   
	 * @Description: 判断字段名称是否该方法  
	 * @param: @param method
	 * @param: @param name
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	public static boolean isExistsParamName(Method method, String name) {
		if (null != name) {
			String[] params = getParameterNames(method);
			int len = params == null ? 0 : params.length;
			for (int i = 0; i < len; i++) {
				if (name.equals(params[i])) {
					return true;
				}
			}
		}
		return false;
	}
}
