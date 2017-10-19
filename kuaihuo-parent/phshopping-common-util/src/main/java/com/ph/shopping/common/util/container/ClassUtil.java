package com.ph.shopping.common.util.container;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @ClassName: ClassOperateUtil  
* @Description: class 操作类  
* @author lijie  
* @date 2017年3月13日  
*
 */
public final class ClassUtil {

	private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

	/**
	 * 根据clas 获取字段名
	 * 不获取父类
	 * @param clas
	 * @return
	 */
	public static String[] getFiledStrByClass(Class<?> clas) {
		if (clas != null) {
			Field[] fields = clas.getDeclaredFields();
			return getFiledStrByFileds(fields);
		}
		return null;
	}
	/**
	 * 
	* @Title: getFiledStrByClassBySuper  
	* @Description: 获取所有的字段包括父类 
	* @param @param clas
	* @param @return    参数  
	* @return String[]    返回类型  
	* @throws
	 */
	public static String[] getFiledStrByClassBySuper(Class<?> clas) {
		String[] fields = null;
		if (clas != null) {
			List<Field> fieldList = new ArrayList<Field>();
			while (clas != null) {
				fieldList.addAll(Arrays.asList(clas.getDeclaredFields()));
				clas = clas.getSuperclass();
			}
			if (!fieldList.isEmpty()) {
				Field[] fs = new Field[fieldList.size()];
				fields = getFiledStrByFileds(fieldList.toArray(fs));
			}
		}
		return fields;
	}
	/**
	 * 
	 * @Title: getFiledStrByFileds   
	 * @Description: 根据字段 得到字段名   
	 * @param: @param fields
	 * @param: @return      
	 * @return: String[]      
	 * @throws
	 */
	public static String[] getFiledStrByFileds(Field[] fields) {
		int len;
		if (fields != null && (len = fields.length) > 0) {
			String[] strs = new String[len];
			for (int i = 0; i < len; i++) {
				strs[i] = fields[i].getName();
			}
			return strs;
		}
		return null;
	}

	/**
	 * 
	 * @Title: setPropertiesValue   
	 * @Description: 根据class 赋值  
	 * @param: @param fieldName
	 * @param: @param obj
	 * @param: @param objValue      
	 * @return: void      
	 * @throws
	 */
	public static void setPropertiesValue(String fieldName, Object obj, Object objValue) {
		try {
			if (obj != null) {
				Class<?> objClas = obj.getClass();
				if (isWriteMethod(objClas, fieldName, objValue)) {
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, objClas);
					Method write = pd.getWriteMethod();
					write.invoke(obj, objValue);
				}
			}
		} catch (IntrospectionException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @Title: isReadMethod   
	 * @Description: 根据返回类型判断是否是get 方法   
	 * @param: @param clas
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isReadMethod(Class<?> clas, String name) {
		boolean flag = false;
		if (StringUtils.isNotBlank(name) && clas != null) {
			Method[] ms = clas.getMethods();
			for (Method m : ms) {
				if (!isReadType(m)) {
					continue;
				}
				if (flag = nameEquel(name, m.getName())) {
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 
	 * @Title: nameEquel   
	 * @Description: 判断资源是否包含   
	 * @param: @param name
	 * @param: @param resource
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private static boolean nameEquel(String name, String resource) {

		return strToLowerCase(resource).contains(strToLowerCase(name));
	}
	/**
	 * 
	 * @Title: isWriteMethod   
	 * @Description:
	 * @param: @param clas
	 * @param: @param name
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private static boolean isWriteMethod(Class<?> clas, String name, Object value) {
		boolean flag = false;
		if (StringUtils.isNotBlank(name) && clas != null && value != null) {
			Method[] ms = clas.getMethods();
			for (Method m : ms) {
				if (!isWriteType(m, value.getClass())) {
					continue;
				}
				if (flag = nameEquel(name, m.getName())) {
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 
	 * @Title: strToLowerCase   
	 * @Description: 转小写   
	 * @param: @param name
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	private static String strToLowerCase(String name){
		
		return name.toLowerCase();
	}
	/**
	 * 
	 * @Title: isReadType   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param m
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private static boolean isReadType(Method m) {

		Type type = m.getGenericReturnType();

		return type != null && type != void.class;
	}
	/**
	 * 
	 * @Title: isWrite   
	 * @Description: 判断设置的参数类型是否相等   
	 * @param: @param m
	 * @param: @param type
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private static boolean isWriteType(Method m, Type type) {
		boolean flag = false;
		Class<?>[] clas = m.getParameterTypes();
		if (clas != null) {
			for (Class<?> cl : clas) {
				if (flag = (type == cl)) {
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 
	* @Title: toLowerCase  
	* @Description:首字母转小写
	* @param @param str
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public static String toLowerCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	/**
	 * 
	* @Title: isFieldsExistsMethod  
	* @Description: 判断是否存在该方法  
	* @param @param mth
	* @param @param fieldsName
	* @param @param suffix
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public static boolean isFieldsExistsMethod(Method[] mth, String fieldsName, String suffix) {
		boolean flag = false;
		for (Method m : mth) {
			String name = m.getName();
			if (methodIsSuffix(name, suffix)) {
				name = name.substring(suffix.length());
				if ((flag = name.equalsIgnoreCase(fieldsName))) {
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 
	* @Title: methodIsSuffix  
	* @Description: 判断方法前缀  
	* @param @param methodName
	* @param @param suffix
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	private static boolean methodIsSuffix(String methodName, String suffix) {

		return methodName.startsWith(suffix);
	}
	/**
	 * 根据字段名返回值
	 * 
	 * @param fieldName
	 * @param clazz
	 * @param obj
	 * @return
	 */
	public static Object getPropertiesValue(String fieldName, Object obj) {
		try {
			if (obj != null) {
				Class<?> objClas = obj.getClass();
				if (isReadMethod(objClas, fieldName)) {
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, objClas);
					Method read = pd.getReadMethod();
					return read.invoke(obj);
				}
			}
		} catch (IntrospectionException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
}
