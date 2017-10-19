package com.alqsoft.service.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alqframework.result.Result;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 上午11:50:20
 * 登录总体的过程
 */
public interface LoginService {
	
	public Result loginPhoneAndPassword(HttpServletRequest request,HttpSession session);
	
	public Result loginPhoneAndMessage(HttpServletRequest request,HttpSession session,String codeType);
	
	public Result sendMessageCode(String phone,String codeType);

	/***
	 * 检测是否存在用户
	 * @param phone
	 * @param password 
	 */
	public Result checkMember(String phone, String password);
	/***
	 * 用户登录
	 * @param phone
	 * @param password 
	 * @param request
	 * @param session
	 */
	public Result userLogin(HttpServletRequest request, HttpSession session);
	
	
	
	
	/**快火批发APP接口登录接口查询商户，供应商信息
	用户手机号和密码登录
	 * @param request
	 * @author 王振宁
	 * @param session
	 * @param type
	 * @return
	 */
	public Result alqloginPhoneAndPassword(HttpServletRequest request, HttpSession session, Integer type);
	
	

}
