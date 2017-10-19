package com.alqsoft.service.register;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午9:20:24
 * Copyright 
 */
public interface UserRegisterService {
	
	/***
	 * 会员注册
	 * @return
	 */
	public Result userMemberRegister(HttpServletRequest request,HttpSession session);

	public Map chatRegist(String phone);
	
	/***
	 * 批发商注册
	 * @return
	 */
	/*public Result userHunterRegister(HttpServletRequest request,HttpSession session);*/
	/**
	 * 普惠调用接口，检验手机号是否已经在批发商注册
	 * @param phone
	 * @return
	 */
	public Result checkPhoneToPH(String phone);
	/***
	 *关联普惠注册
	 * @param request
	 * @param session
	 * @return
	 */
	public Result userMemberNewRegister(HttpServletRequest request,HttpSession session);
	
	/***
	 * 用户注册先使用手机号进行注册，在调用接口进行判断身份
	 * @param request
	 * @return
	 */
	
	public Result UserMemeberNewRegisterByPhone(HttpServletRequest request);
	/***
	 * 验证完身份正确后保存数据
	 * @param request
	 * @param session
	 * @return
	 */
	public Result SaveMemeberByCheckCard(HttpServletRequest request,HttpSession session);
	/***
	 * 只使用手机号注册成为会员
	 * @param request
	 * @return
	 */
	public Result UserMemberRegisterByPhone(HttpServletRequest request,HttpSession session);
	/**
	 * 通过手机号和密码保存数据到Member表中
	 * @param password 密码
	 * @param phone  手机号
	 * @return
	 */
	public Result  addMember(String password,String phone);

}
