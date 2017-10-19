/**  
 * @Title:  SmsController.java   
 * @Package com.phshopping.api.controller.sms   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月10日 上午10:02:26   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.sms.api.controller;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CommonDTO;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
* @ClassName: SmsController
* @Description: 短信发送Controller
* @author liuy
* @date 2017年6月2日 上午11:15:14
 */
@Controller
@RequestMapping("web/sms")
public class SmsController {

	//日志对象
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	//短信接口
	@Autowired
	private ISmsDataService smsDataService;

	@Autowired
	private SmsUtil smsUtil;
	
	/**
	 * 
	 * @Title: sendSmsHaveCode
	 * @Description: 发送短信(有验证码/密码)
	 * @author liuy
	 * @date  2017年6月2日 上午11:28:43
	 * @param telPhone 手机号
	 * @param smsCodeTypeCode 短信发送类型Code（操作）
	 * @param sourceCode 短信来源Code
	 * @return01、
	 */
	@RequestMapping(value = "/sendSmsHaveCode")
	@ResponseBody
	public Result sendSmsHaveCode( String telPhone,String smsCodeTypeCode,String sourceCode) {
		logger.info("发送短信 phone: " + telPhone);
		/*SmsCodeType smsCodeType = SmsCodeType.getSmsCodeTypeByCode(smsCodeTypeCode);
		SmsSourceEnum smsSourceEnum = SmsSourceEnum.getSmsSourceEnumByCode(sourceCode);
		if(smsCodeType == null || smsSourceEnum == null){
			return new Result(false,"300", "短信发送参数不全");
		}*/
		if(telPhone == null){
			return new Result(false,"300", "短信发送参数不全");
		}
		/*SmsSendData sendData = new SmsSendData();
		sendData.setMobile(telPhone);
		sendData.setType(smsCodeType);
		sendData.setSourceEnum(smsSourceEnum);
		sendData.setSmsCode(SmsCodeUtil.getSmsCode());
		result = smsDataService.sendSmsHaveCode(sendData);*/
		CommonDTO commonDTO = new CommonDTO();
		commonDTO.setPhone(telPhone);
		commonDTO.setCodeType("Fr170002");

		Result send = smsUtil.sendCommonMsg(commonDTO);
		if ("1".equals(send.getCode())){
             return ResultUtil.setResult(true,"短信发送成功");
		} else {
			return ResultUtil.setResult(false,send.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: sendSmsNoCode
	 * @Description: 发送短信 (无验证码/密码)
	 * @author liuy
	 * @date  2017年6月2日 上午11:27:32
	 * @param telPhone 手机号
	 * @param smsCodeTypeCode 短信发送类型Code（操作）
	 * @param sourceCode 短信来源Code
	 * @return
	 */
	@RequestMapping(value = "/sendSmsNoCode", method = RequestMethod.GET)
	@ResponseBody
	public Result sendSmsNoCode( String telPhone,String smsCodeTypeCode,String sourceCode) {
		logger.info("发送短信 phone: " + telPhone);
		Result result =new Result(false,"300", "发送短信失败"); 
		SmsCodeType smsCodeType = SmsCodeType.getSmsCodeTypeByCode(smsCodeTypeCode);
		SmsSourceEnum smsSourceEnum = SmsSourceEnum.getSmsSourceEnumByCode(sourceCode);
		if(smsCodeType == null || smsSourceEnum == null){
			return new Result(false,"300", "短信发送参数不全");
		}
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(telPhone);
		sendData.setType(smsCodeType);
		sendData.setSourceEnum(smsSourceEnum);
		result = smsDataService.sendSmsNoCode(sendData);
		logger.info("发送短信返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}
	
}
