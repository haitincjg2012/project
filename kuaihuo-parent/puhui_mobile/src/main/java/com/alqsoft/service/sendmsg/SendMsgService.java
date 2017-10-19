package com.alqsoft.service.sendmsg;

import org.alqframework.result.Result;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午6:27:10
 * 
 */
public interface SendMsgService {
	
	public Result sendMessageCodeForBank(String phone,String type);
	
	public Result checkMessageCodeForBank(String phone,String imageCode,String codeType);
	
	/**
	 * 修改密码通过验证码,通过手机号获取验证码     或者  通过验证码登录
	 * 此方法有判断手机号是否为空
	 * @version 1.22
	 * @create-date 2017/6/6
	 * @param phone 手机号
	 * @param codeType 验证码格式
	 * @return
	 */
	public Result sendMessageCodeUpdatePassword(String phone,String codeType);

}
