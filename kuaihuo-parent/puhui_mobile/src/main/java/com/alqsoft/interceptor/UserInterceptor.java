package com.alqsoft.interceptor;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alqframework.encode.EncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alqsoft.utils.SystemRole;
import com.alqsoft.utils.WebUtils;

/**
 * 
 * @Title: UserInterceptor.java
 * @Description: 用户登录拦截器 
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月10日 下午3:41:23
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class UserInterceptor implements HandlerInterceptor {

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
		//System.out.println("========1preHandle 进入拦截器了========");
		boolean flag=false;
		// 获取session值---到时 开发的时候启用
		String r = request.getSession().getAttribute(SystemRole.SESSIONROLE.getName())==null?"":request.getSession().getAttribute(SystemRole.SESSIONROLE.getName()).toString();
		if(StringUtils.isBlank(r)){
			if (request.getHeader("x-requested-with") != null&& request
	                 .getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8");  
				PrintWriter out = response.getWriter();
				out.write("{\"code\":\"3\",\"msg\":\"未登录或登录过期\"}");
				out.close();
			}else{
				response.sendRedirect(request.getContextPath().toString() + "/pc/view/login/toLogin");// 跳转到登录页面
			}
			return false;
		}
		if(SystemRole.USER.getName().equals(r)){
			if (WebUtils.getUser() != null) {
				return true;
			}else{
				flag=true;
			}
			
		}else {
			 flag=true;
			 
		}
		
		if(flag){

			String projectName = request.getContextPath().toString();// 获取项目名称
			String requestUri = request.getRequestURI().toString();// 请求行中的资源名部分
			
			if (request.getHeader("x-requested-with") != null&& request
	                 .getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8");  
				PrintWriter out = response.getWriter();
				out.write("{\"code\":\"3\",\"msg\":\"未登录或登录过期\"}");
				out.close();
				return false;
			}
			Enumeration enu=request.getParameterNames();  
			StringBuffer sb=new StringBuffer();
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();  
				//System.out.println(paraName+": "+request.getParameter(paraName));  
				for (String string : request.getParameterValues(paraName)) {
					sb.append(paraName+"="+EncodeUtils.urlEncode(string)+"&");
				}
			} 
			if(StringUtils.isNoneBlank(sb.toString())){
				requestUri = requestUri + "?"+sb.toString();
			}
			String pathStr = StringUtils.substringAfterLast(requestUri, "/");
			//System.out.println("pathStr--"+pathStr);
			//System.out.println("requestUri===:" + requestUri);
			
			//#######到时这边要区分是weixin或web的页面
			if(requestUri.contains("/pc/")){//WEB
				requestUri= "/pc/"+StringUtils.substringAfter(requestUri, "pc/");
				if (StringUtils.isBlank(pathStr)) {// 判断是否是跳转到首页
					request.getSession().setAttribute("backUrl", requestUri);
					response.sendRedirect(projectName + "/pc/view/login/toLogin");// 跳转到首页
				}else{
					request.getSession().setAttribute("backUrl", requestUri);
					response.sendRedirect(projectName + "/pc/view/login/toLogin");// 跳转到登录页面
				}
			}else if(requestUri.contains("/weixin/")){//weixin
				requestUri= "/weixin/"+StringUtils.substringAfter(requestUri, "weixin/");
				if (StringUtils.isBlank(pathStr)) {// 判断是否是跳转到首页
					request.getSession().setAttribute("backUrl", requestUri);
					response.sendRedirect(projectName + "/weixin/view/main/index");// 跳转到首页
				}else if("index.html".equals(pathStr)) {
					request.getRequestDispatcher("/weixin/view/main/index").forward(request, response);    
					//response.sendRedirect(projectName + "/view/toindex");// 跳转到登录页面
				}
				else {
					request.getSession().setAttribute("backUrl", requestUri);
					response.sendRedirect(projectName + "/weixin/view/member/toLogin");// 跳转到登录页面
				}
			}
			return false;
		}
		return true;
	}

}
