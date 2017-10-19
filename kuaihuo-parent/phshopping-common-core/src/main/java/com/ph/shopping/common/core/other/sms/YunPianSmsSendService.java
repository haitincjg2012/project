/**  
 * @Title:  YunPianSmsSendService.java   
 * @Package com.ph.shopping.common.core.other   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 下午1:04:10   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.config.properties.SmsProperties;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.sms.model.ModelData;
import com.ph.shopping.common.core.other.sms.model.ModelGenerate;
import com.ph.shopping.common.core.other.sms.senum.SmsStatusEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.other.smssend.YunPianSmsUtil;
import com.ph.shopping.common.util.other.smssend.request.YunPianSmsRequest;
import com.ph.shopping.common.util.other.smssend.response.YunPianResponse;
import com.ph.shopping.common.util.result.Result;

/**   
 * @ClassName:  YunPianSmsSendService   
 * @Description:yunpian 短信发送服务   
 * @author: 李杰
 * @date:   2017年5月11日 下午1:04:10     
 * @Copyright: 2017
 */
@Service
public class YunPianSmsSendService {

	private static final Logger log = LoggerFactory.getLogger(YunPianSmsSendService.class);
	
	@Autowired
	private SmsProperties smsProperties;
	
	/**
	 * 
	 * @Title: getSmsStatusEnum   
	 * @Description: 得到返回状态枚举  
	 * @param: @param code
	 * @param: @return      
	 * @return: SmsStatusEnum      
	 * @throws
	 */
	public SmsStatusEnum getSmsStatusEnum(Integer code){
		 for(SmsStatusEnum senum : SmsStatusEnum.values()){
			 if(senum.getCode().equals(code.toString())){
				 return senum;
			 }
		 }
		 return null;
	}
	
	/**
	 * @Title: sendSms
	 * @Description: 发送短信(有验证码/密码)
	 * @author liuy
	 * @date  2017年6月1日 上午11:27:35
	 * @param mobile 手机号
	 * @param code 验证码/密码
	 * @param smsSourceEnum 来源（会员、代理商、商户、供应商）
	 * @param smsCodeTypeEnum 操作类型
	 * @return Result
	 */
	public Result sendSmsHaveCode(String mobile, String code, SmsSourceEnum smsSourceEnum,
			SmsCodeType smsCodeTypeEnum) {
		log.info("短信验证码 发送参数 ： mobile " + mobile + ", code : " + code + ", type : " + smsCodeTypeEnum);
		ModelData data = new ModelData();
		data.setCustomData(code);
		data.setItem(smsCodeTypeEnum.getDesc());
		data.setMobile(mobile);
		data.setSmsCodeType(smsCodeTypeEnum);
		data.setSmsSource(smsSourceEnum.getMsg());
		return sendSms(mobile, ModelGenerate.getSmsModel(data));
	}
	
	/**
	 * @Title: sendSms
	 * @Description: 发送短信(无验证码)
	 * @author liuy
	 * @date  2017年6月1日 上午11:27:35
	 * @param code 验证码/密码
	 * @param smsSourceEnum 来源（会员、代理商、商户、供应商）
	 * @param smsCodeTypeEnum 操作类型
	 * @return Result
	 */
	public Result sendSmsNoCode(String mobile, SmsSourceEnum smsSourceEnum, SmsCodeType smsCodeTypeEnum) {
		log.info("短信验证码 发送参数 ： mobile " + mobile + ", type : " + smsCodeTypeEnum);
		// 1.根据短信类型枚举，获取短信模板
		ModelData data = new ModelData();
		data.setSmsCodeType(smsCodeTypeEnum);
		data.setSmsSource(smsSourceEnum.getMsg());
		return sendSms(mobile, ModelGenerate.getSmsModel(data));
	}
	/**
	 * 
	 * @Title: sendSmsByModelData   
	 * @Description: 发送短信，根据自定义消息来发送   
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	public Result sendSmsByModelData(String mobile, Object resource, SmsCodeType smsType) {
		log.info("短信验证码 发送参数 ： mobile " + mobile + ", SmsCodeType : " + smsType + " resource = {}",
				JSON.toJSONString(resource));
		ModelData data = new ModelData();
		data.setSmsCodeType(smsType);
		data.setCustomData(resource);
		return sendSms(mobile, ModelGenerate.getSmsModel(data));
	}
	/**
	 * 
	 * @Title: sendSms   
	 * @Description: 短信发送开始   
	 * @param: @param mobile
	 * @param: @param model
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	private Result sendSms(String mobile, String model) {
		log.info("短信发送开始>> 手机号 mobile = " + mobile + " 模板数据 model = " + model);
		if (StringUtils.isBlank(model)) {
			return ResultUtil.getResult(SmsStatusEnum.SMS_MODELERROR);
		}
		Result result = ResultUtil.getResult(SmsStatusEnum.VERIFYCODE_FAIL);
		// 封装短信发送数据
		YunPianSmsRequest rquest = new YunPianSmsRequest();
		rquest.setApikey(smsProperties.getYpApikey());
		rquest.setMobile(mobile);
		rquest.setText(model);
		// 发送短信
		YunPianResponse response = YunPianSmsUtil.sendSms(rquest, smsProperties.getYpUrl());
		// 根据响应数据封装Reslt
		log.info("短信发送返回数据 YunPianResponse = {}", JSON.toJSONString(response));
		if (response != null) {
			boolean flag = false;
			SmsStatusEnum senum = getSmsStatusEnum(response.getCode());
			if (senum != null) {
				if (flag = (SmsStatusEnum.SUCCESS.getCode().equals(senum.getCode()))) {
					result.setData(response);
				}
				ResultUtil.setResult(result, flag, senum.getMsg());
			}
		}
		return result;
	}
}
