package com.alqsoft.controller.sms;

import org.alqframework.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.utils.TencentSms;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月19日
 *
 */
@RequestMapping("sms")
@Controller
public class TencentSmsController {
	
	@RequestMapping(value="sendMsg",method=RequestMethod.POST)
	@ResponseBody
	public Result sendMsg(String phone){
		TencentSms sms=new TencentSms();
		return sms.sendMsg(phone, "alq20170319");
	}
	
	/*@RequestMapping(value="checkMsg",method=RequestMethod.POST)
	@ResponseBody*/
	public Result checkMsg(String phone,String code){
		TencentSms sms=new TencentSms();
		return sms.checkMsg(phone,code, "alq20170319");
	}
	   
}
