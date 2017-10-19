package com.ph.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：用户登录拦截器
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月24日
 *
 * @Copyright @2017 by Mr.chang
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
//    @Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//
//        //判断登录用户的session
//		//获取当前的Subject
//		Subject currentUser = SecurityUtils.getSubject();
//		Object obj = currentUser.getSession().getAttribute(CommonConstants.LOGIN_BACK_USER_SESSION);
//		if (null == obj) {
//				request.getSession().invalidate();
////				request.getRequestDispatcher("/login").forward(request, response);
//				response.sendRedirect("/login");
//				return false;
//			}else{
//				return true;
//			}
//	}

}
