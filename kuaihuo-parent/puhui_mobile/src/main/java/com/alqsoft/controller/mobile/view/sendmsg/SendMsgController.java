package com.alqsoft.controller.mobile.view.sendmsg;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.rpc.mobile.RpcUserSendService;
import com.alqsoft.service.sendmsg.SendMsgService;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午6:09:06
 * 
 */
@Controller
@RequestMapping("mobile/view/sendMsg")
public class SendMsgController {
	
	@Autowired
	private SendMsgService sendMsgService;
	@Autowired
	private RpcUserSendService  rpcUserSendService;
	
	/***
	 *  输入手机号获取验证码
	 *  phone 手机号
	 *  获取手机验证码，绑定银行卡
	 *  验证
	 *   codeType验证手机号的规格
	 * @return
	 */
	@RequestMapping(value="codeforbank",method=RequestMethod.POST)
	@ResponseBody
	public Result sendMessageCodeForBank(@RequestParam(value="phone") String phone){
		String codeType="KHPF20170909";
		Result result=sendMsgService.sendMessageCodeForBank(phone.substring(0,11),codeType);
		return result;
	}
	/***
	 *  输入手机号验证手机号
	 *  phone 手机号
	 *  检验手机验证码，绑定银行卡
	 *  codeType验证手机号的规格
	 *  验证
	 * @return
	 */
	@RequestMapping(value="checkforbank",method=RequestMethod.POST)
	@ResponseBody
    public Result checkMessageCodeForBank(@RequestParam(value="phone") String phone,@RequestParam(value="imageCode") String imageCode){
    	String codeType="KHPF20170909";
		Result result=sendMsgService.checkMessageCodeForBank(phone.substring(0,11),imageCode,codeType);
		return result;
    }
	/**
	 * 修改密码通过验证码,通过手机号获取验证码     或者  通过验证码登录
	 * 此方法有判断手机号是否为空
	 * @version 2.0
	 * @create-date 2017/5/10
	 * @param phone
	 *            手机号
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "sendmessagecodeupdatepassword", method = RequestMethod.POST)
	@ResponseBody
	public Result sendMessageCodeUpdatePassword(@RequestParam(value = "phone") String phone) {

		String codeType = "KHPF20170909";
		Result result = sendMsgService.sendMessageCodeUpdatePassword(phone.substring(0,11), codeType);

		return result;
	}
	/**
	 * 通过手机好获取验证码
	 * @param phone
	 * @return
	 */
	@Explosionproof//防爆
	@RequestMapping(value = "sendmessagecode", method = RequestMethod.POST)
	@ResponseBody
	public Result sendMessageCode(@RequestParam(value = "phone") String phone) {

		String codeType = "KHPF20170909";
		Result result = rpcUserSendService.sendMessageCode(phone.substring(0,11), codeType);

		return result;
	}
}
