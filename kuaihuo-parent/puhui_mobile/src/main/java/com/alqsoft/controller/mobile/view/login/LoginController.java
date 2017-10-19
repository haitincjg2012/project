package com.alqsoft.controller.mobile.view.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.service.login.LoginService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 上午11:39:56 用户登录
 */
@RequestMapping("mobile/view/login")
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	/***
	 * 用户手机号和密码登录
	 * 
	 * @param request
	 * @param session
	 * @return result
	 */

	@RequestMapping(value = "password", method = RequestMethod.POST)
	public Result loginPhoneAndPassword(HttpServletRequest request, HttpSession session) {

		Result result = loginService.loginPhoneAndPassword(request, session);

		return result;

	}

	/***
	 * 用户使用手机号和短信验证码登陆路
	 * 
	 * @param request
	 * @param session
	 * @return result
	 */
	@RequestMapping(value = "message", method = RequestMethod.POST)
	public Result loginPhoneAndMessage(HttpServletRequest request, HttpSession session) {

		String codeType = "KHPF20170909";
		Result result = loginService.loginPhoneAndMessage(request, session, codeType);

		return result;
	}

	/***
	 * 输入手机号获取验证码 phone 手机号 codeType自定义的接口
	 * 
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "code", method = RequestMethod.POST)
	public Result sendMessageCode(@RequestParam(value = "phone") String phone) {
		String codeType = "KHPF20170909";
		Result result = loginService.sendMessageCode(phone.substring(0,11), codeType);
		return result;
	}

	/***
	 * 检测是否存在用户
	 * 
	 * @param phone
	 * @param password
	 */
	@RequestMapping(value = "checkMember", method = RequestMethod.POST)
	public Result checkMember(@RequestParam("phone") String phone, @RequestParam("password") String password) {

		Result result = loginService.checkMember(phone, password);

		return result;

	}

	/***
	 * 用户登录
	 * 
	 * @param
	 * @param
	 */
	@RequestMapping(value = "userlogin", method = RequestMethod.POST)
	public Result userlogin(HttpServletRequest request, HttpSession session) {

		Result result = loginService.userLogin(request, session);

		return result;

	}
	
	/**快火批发APP接口登录接口查询商户，供应商信息
	用户手机号和密码登录
	 * @param request
	 * @author 王振宁
	 * @param session
	 * @param type
	 * @return
	 */
	 
	@RequestMapping(value = "alq-member-login", method = RequestMethod.POST)
	public Result alqloginPhoneAndPassword(
			HttpServletRequest request,
			HttpSession session,
			Integer type){
		Result result = loginService.alqloginPhoneAndPassword(request, session,type);
		return result;
	}

}
