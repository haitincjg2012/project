package com.alqsoft.utils.easyuiweb;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 
 * @Title: EasyWebUtils.java
 * @Description: easyui工具   用于接口调用
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月18日 下午2:32:01
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class EasyWebUtils {

	/**
	 * 正常返回easyui数据
	 * 
	 * @param t
	 * @param page
	 * @return
	 */
	public static <T> EasyuiWebResult<List<T>> returnPage(Class<T> clazz, Page<T> page) {
		EasyuiWebResult<List<T>> easyuiResult = new EasyuiWebResult<List<T>>();
		easyuiResult.setTotal(page.getTotalElements());
		easyuiResult.setT(page.getContent());
		return easyuiResult;
	}

	/**
	 * 正常返回easyui数据带上footer
	 * 
	 * @param t
	 * @param page
	 * @return
	 */
	public static <T> EasyuiWebResult<List<T>> returnPageByfooter(Class<T> clazz, Page<T> page, Object object) {
		EasyuiWebResult<List<T>> easyuiResult = new EasyuiWebResult<List<T>>();
		easyuiResult.setTotal(page.getTotalElements());
		easyuiResult.setT(page.getContent());
		easyuiResult.setFooter(object);
		return easyuiResult;
	}
}
