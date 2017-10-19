package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.rpc.mobile.RpcUserSendService;
import com.alqsoft.service.usersend.UserSendService;

/**
 * 登录短信获取验证码
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 下午3:49:55
 * 
 */
@Transactional
@Service
public class RpcUserSendServiceImpl implements RpcUserSendService{
     @Autowired
     private UserSendService userSendService;
	@Override
	public Result sendMessageCode(String phone,String codeType) {
		// TODO Auto-generated method stub
		return userSendService.sendMessageCode(phone,codeType);
	}
	@Override
	public Result checkMsg(String phone, String image, String codeType) {
		// TODO Auto-generated method stub
		return userSendService.checkMsg(phone,image,codeType);
	}
	

}
