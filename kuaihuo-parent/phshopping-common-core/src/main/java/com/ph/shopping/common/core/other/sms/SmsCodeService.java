package com.ph.shopping.common.core.other.sms;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.other.smssend.SmsSendUtil;
import com.ph.shopping.common.util.other.smssend.request.SmsCodeData;
import com.ph.shopping.common.util.other.smssend.request.SmsCustomModel;
import com.ph.shopping.common.util.result.Result;
/**
 * 
 * @ClassName:  SmsCodeService   
 * @Description:短信发送服务   (针对腾讯云的短息发送，已停用)
 * @author: 李杰
 * @date:   2017年4月27日 下午3:40:06     
 * @Copyright: 2017
 */
@Deprecated
public class SmsCodeService{

   private static final Logger log = LoggerFactory.getLogger(SmsCodeService.class);
	
   /**
	 * 自定义发送信息 url
	 */
	@Value("${sms.send.msg.url}")
	private String smsCustomUrl;
	/**
	 * 校验短信验证码
	 */
	@Value("${sms.code.check}")
	private String smsCodeCheckUrl;
	/**
	 * 发送验证码（不带自定义消息发送）
	 */
	@Value("${sms.send.url}")
	private String smsNoMsgUrl;
	
	/**
	 * 腾讯云接口sdkappid
	 */
	@Value("${sms.send.sdkappid}")
	private String sdkappid;
	/**
	 * 腾讯云接口appkey
	 */
	@Value("${sms.send.appkey}")
	private String appkey;
	/**
	 * 密码发送模板id
	 */
	@Value("${pwd.model.code}")
	private String modelCode;
	/**
	 * 短信发送模板id
	 */
	@Value("${sms.model.code}")
	private String smsModelCode;
	/**
	 * 区号
	 */
	@Value("${sms.nationcode}")
	private String nationcode;
	/**
	 * 名称
	 */
	@Value("${sms.sign}")
	private String sign;
	/**
	 * 类型
	 */
	@Value("${sms.send.txtype}")
	private String type;
	
	/**
	 * 
	* @Title: sendSmsCodeByNoMsg  
	* @Description: 发送短信验证码  
	* @param @param phone 手机号
	* @param @param type 格式PH2017020701 
	* (备注：短信验证码发送之前走的是北京接口， 现在改成统一走 腾讯云接口，暂时不用)
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	@Deprecated
	public boolean sendSmsByNoMsg(String phone,String type){
		boolean flag = false;
		try {
			SmsCodeData data = new SmsCodeData();
			data.setPhone(phone);
			data.setCodeType(type);
			data.setSendUrl(smsNoMsgUrl);
			String[] fields = { "sendUrl", "phone", "codeType" };
			if (!ParamVerifyUtil.entityIsNotNullByField(data, fields)) {
				log.warn("send smscodebynomsg parameter is not complete");
			} else {
				flag = SmsSendUtil.sendSmsCodeByNoMsg(data);
			}
		} catch (Exception e) {
			log.error("发送短信错误",e);
		}
		return flag;
	}
	/**
	 * 
	* @Title: verifySmsCode  
	* @Description:校验短信 
	* @param @param phone 手机号
	* @param @param type 类型 （备注：类型与发送时传的类型数据要一致）
	* (备注：短信验证码发送之前走的是北京接口， 现在改成统一走 腾讯云接口，暂时不用)
	* @param @param code 
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	@Deprecated
	public boolean verifySmsCode(String phone, String type, String code) {
		boolean flag = false;
		try {
			SmsCodeData data = new SmsCodeData();
			data.setCodeType(type);
			data.setPhone(phone);
			data.setSmsCode(code);
			data.setSmsCodeCheckUrl(smsCodeCheckUrl);
			String[] fields = { "smsCodeCheckUrl", "phone", "codeType", "smsCode" };
			if (!ParamVerifyUtil.entityIsNotNullByField(data, fields)) {
				log.warn("verify sms code parameter is not complete");
			} else {
				flag = SmsSendUtil.verifySmsCode(data);
			}
		} catch (Exception e) {
			log.error("校验短信错误",e);
		}
		return flag;
	}
	
	/**
	 * 
	* @Title: sendSmsCodeByMsg  
	* @Description: 发送密码  
	* @param @param phone 手机号
	* @param @param message 密码
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public Result sendSmsCodeByPassword(String phone, String password) {
		Result result = new Result();
		if (StringUtils.isBlank(password)) {
			result.setCode(RespCode.Code.FAIL.getCode());
			result.setMessage("发送的密码不能为空");
			return result;
		}
		Object[] message = {password};
		return sendSmsCodeByMsg(phone, message, Integer.parseInt(modelCode));
	}
	/**
	 * 
	 * @Title: sendSmsCodeBySms   
	 * @Description: 发送短信验证码  
	 * @param: @param phone
	 * @param: @param message
	 * @param: @return      
	 * @return: Result      
	 * @throws
	 */
	public Result sendSmsCodeBySmsCode(String phone, String smsCode){
		Result result = new Result();
		if (StringUtils.isBlank(smsCode)) {
			result.setCode(RespCode.Code.FAIL.getCode());
			result.setMessage("发送的短信验证码不能为空");
			return result;
		}
		Object[] message = {smsCode};
		return sendSmsCodeByMsg(phone, message, Integer.parseInt(smsModelCode));
	}
	/**
	 * 
	 * @Title: sendSmsCodeByMsg   
	 * @Description: 发送自定义短信信息   
	 * @param: @param phone
	 * @param: @param message
	 * @param: @param tplId
	 * @param: @return      
	 * @return: Result      
	 * @throws
	 */
	public Result sendSmsCodeByMsg(String phone, Object[] message, int tplId) {
		Result result = new Result();
		result.setCode(RespCode.Code.FAIL.getCode());
		result.setMessage(RespCode.Code.FAIL.getMsg());
		SmsCustomModel modelData = new SmsCustomModel();
		try {
			modelData.setAppKey(appkey);
			modelData.setNationcode(nationcode);
			modelData.setPhone(phone);
			modelData.setSdkappid(sdkappid);
			modelData.setSign(sign);
			modelData.setTplId(tplId);
			modelData.setUrl(smsCustomUrl);
			modelData.setType(type);
			List<Object> list = Arrays.asList(message);
			modelData.setParams(list);
			String[] fields = { "url", "phone", "tplId" };
			if (!ParamVerifyUtil.entityIsNotNullByField(modelData, fields)) {
				log.warn("verify sms code parameter is not complete");
				return result;
			}
			result = SmsSendUtil.sendSmsCodeByMsg(modelData);
		} catch (Exception e) {
			log.error("发送自定义短信错误", e);
		}
		return result;
	}
}
