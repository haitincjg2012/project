package com.alqsoft.controller.mobile.view.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.service.register.UserRegisterService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午9:07:02 会员注册
 */
@RestController
@RequestMapping("mobile/view/register")
public class UserRegisterController {

	@Autowired
	private UserRegisterService userRegisterService;

	/***
	 * 会员注册
	 * 
	 * @param request
	 * @param session
	 * @param request
	 * @return result
	 */
	@Explosionproof
	@RequestMapping(value = "member", method = RequestMethod.POST)
	public Result UserMemberRegister(HttpServletRequest request, HttpSession session) {

		Result result = userRegisterService.userMemberRegister(request, session);

		return result;
	}

	/***
	 * 普惠调用接口，检验手机号是否已经在批发商注册
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "checkphone", method = RequestMethod.POST)
	public Result checkPhoneToPH(@RequestParam(value = "phone") String phone) {
		Result result = userRegisterService.checkPhoneToPH(phone);
		return result;
	}

	/***
	 * 链接普惠的接口注册
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "newregister", method = RequestMethod.POST)
	public Result UserMemeberNewRegister(HttpServletRequest request, HttpSession session) {

		Result result = userRegisterService.userMemberNewRegister(request, session);

		return result;
	}

	/***
	 * 用户先用手机号注册,在调用另一个接口验证身份(手机号存在缓存中)
	 * 
	 * @param request
	 * @return
	 */
	@Explosionproof // 防爆注解
	@RequestMapping(value = "registerbyphone", method = RequestMethod.POST)
	public Result UserMemeberNewRegisterByPhone(HttpServletRequest request) {

		Result result = userRegisterService.UserMemeberNewRegisterByPhone(request);

		return result;
	}

	@Explosionproof // 防爆注解
	@RequestMapping(value = "savemember", method = RequestMethod.POST)
	public Result SaveMemeberByCheckCard(HttpServletRequest request, HttpSession session) {
		Result result = userRegisterService.SaveMemeberByCheckCard(request, session);

		return result;
	}
	/***
	 * 只通过手机号进行注册会员
	 * @return
	 */
	@Explosionproof // 防爆注解
	@RequestMapping(value = "usermemberregisterbyphone", method = RequestMethod.POST)
	public Result UserMemberRegisterByPhone(HttpServletRequest request,HttpSession session){
		Result result = userRegisterService.UserMemberRegisterByPhone(request,session);

		return result;
	}
}
