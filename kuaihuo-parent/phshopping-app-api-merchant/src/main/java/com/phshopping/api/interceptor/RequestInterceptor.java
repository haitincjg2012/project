package com.phshopping.api.interceptor;


import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.phshopping.api.constant.MechantAppResultEnum;


public class RequestInterceptor extends HandlerInterceptorAdapter {

    //缓存
	@Autowired
	@SuppressWarnings("rawtypes")
	private ICacheService redisService;

	/**
	 * 拦截器
	* Title: preHandle
	* Description:
	* @author Mr.Dong
	* @date 2017年6月21日 上午10:56:49
	* @param request
	* @param response
	* @param handler
	* @return
	* @throws Exception
	* @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String url = request.getRequestURI();
//		response.setCharacterEncoding("UTF-8");
//		Result result=null;
//		if(url.contains("api/merchant/merchantAppLogin")) return true;
//		String token = request.getHeader("token");
//		//判断是否登录
//		if(StringUtils.isEmpty(token)){
//			PrintWriter out=response.getWriter();
//			result=ResultUtil.getResult(MechantAppResultEnum.NO_LOGIN);
//			out.write(JSONObject.toJSONString(result));
//			out.flush();
//			out.close();
//			return false;
//		}
//		if(redisService == null){//重容器中取
//			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//			redisService =  (ICacheService)factory.getBean("redisCacheService");
//		}
//		//判断redis是否存在登陆token
//		if (!redisService.exists(getMerchantAppKey(token,RoleEnum.MERCHANT))) {
//			PrintWriter out=response.getWriter();
//			result=ResultUtil.getResult(MechantAppResultEnum.NO_LOGIN);
//			out.write(JSONObject.toJSONString(result));
//			out.flush();
//			out.close();
//			return false;
//		}
//
//		return true;
//	}

	
	/**
     * 获取redis的key值
    * @Title: getMerchantAppKey
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author Mr.Dong
    * @date  2017年6月21日 上午10:43:55
    * @param mobile
    * @param roleEnum
    * @return
     */
	@SuppressWarnings("unused")
	private static String getMerchantAppKey(String token,RoleEnum roleEnum) {
		StringBuilder bud = new StringBuilder(roleEnum.name())
				.append("_").append(token);
		return bud.toString();
	}
}
