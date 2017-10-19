package com.alqsoft.utils;

import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import com.alqsoft.entity.user.User;

/**
 * 
 * 
 * @author 张灿
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-1 下午3:10:41
 * 
 */
public class WebUtils {
	/**
	 * 获取会话用户
	 * 
	 * @return
	 */
	public static User getUser() {
		return (User) SpringMVCUtils.getRequest().getSession().getAttribute("user");
	}

}
