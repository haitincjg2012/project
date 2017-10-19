package com.phshopping.api.config;

import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.constant.CommonConstants;
import com.ph.shopping.common.util.result.Result;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

        String url = request.getRequestURI();
        //判断登录用户的session
		//获取当前的Subject
		Object obj = request.getSession().getAttribute(CommonConstants.LOGIN_BACK_USER_SESSION);
		if (null != obj && !url.contains("api/merchant/merchantAppLogin")) {
				request.getSession().invalidate();
//				request.getRequestDispatcher("/login").forward(request, response);
                Result result=new Result(false,"401","未登录");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                try ( PrintWriter out =response.getWriter()){
                    out.append(JSONObject.toJSONString(result));
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;

	}

}
