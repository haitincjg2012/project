package com.ph.shopping.common.util.verifycode;

import java.lang.reflect.Field;
import java.util.List;

public class VerifyUtil {

	/**
	 * 
	 * @Title: verifyEntityField
	 * @Description: 用于校验实体的每个字段都不为空
	 * @author WQiang
	 * @date 2017年4月5日 下午2:47:47
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyEntityField(Object bean) throws Exception {
		for (Field f : bean.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.get(bean) == null) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: isEmpty
	 * @Description: 校验对象不为空
	 * @author WQiang
	 * @date 2017年4月5日 下午2:48:13
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		if (value == null) {
			return false;
		}
		if ("".equals(value.toString()) || "null".equals(value.toString())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: isEmpty
	 * @Description: 校验对象不为空
	 * @author WQiang
	 * @date 2017年4月5日 下午2:48:13
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ("".equals(value.toString()) || "null".equals(value.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: listIsNotNull
	 * @Description: 判断List不为空
	 * @author 王强
	 * @date 2017年5月17日 上午11:27:33
	 * @param list
	 * @return
	 */
	public static boolean listIsNotNull(List<?> list) {
		if (list == null || list.size() == 0) {
			return false;
		}

		if (list.get(0) == null) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @Title: listIsNotNull
	 * @Description: 判断List不为空
	 * @author 王强
	 * @date 2017年5月17日 上午11:27:33
	 * @param list
	 * @return
	 */
	public static boolean listIsNull(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
}
