package com.alqsoft.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alqsoft.anno.Permission;
import com.alqsoft.utils.SystemRole;

/**
 * 
 * @Title: UserInterceptor.java
 * @Description: 用户登录拦截器 
 * @author 张灿
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月10日 下午3:41:23
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class RoleInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception err)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		boolean flag=false;
		// 获取session值---到时 开发的时候启用
		String r = request.getSession().getAttribute(SystemRole.SESSIONROLE.getName())==null?"":request.getSession().getAttribute(SystemRole.SESSIONROLE.getName()).toString();
		 //处理Permission Annotation，实现方法级权限控制  
        HandlerMethod method = (HandlerMethod)obj;  
        Permission permission = method.getMethodAnnotation(Permission.class);  
		
        if(null!=permission){
	        SystemRole rolePermission=permission.value();
			
			if(rolePermission.getName().equals(r)){//用户当前session角色  与  请求的controller的方法Permission注解角色是否一致
				return true;
			}else{
				flag=false;
			}
        }
    	return flag;
	}
}
