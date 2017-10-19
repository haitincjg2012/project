/**  
 * @Title:  SmsDataService.java   
 * @Package com.ph.shopping.common.core.other.sms.handle   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月15日 下午11:20:36   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.handle;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.CacheConfigEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.dto.FindPasswordDTO;
import com.ph.shopping.common.core.other.sms.YunPianSmsSendService;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.senum.SmsStatusEnum;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;

/**
 * 
 * @ClassName: SmsDataService
 * @Description: 短信发送服务  
 * @author liuy
 * @date 2017年6月1日 上午11:44:04
 */
@Service
@SuppressWarnings("unchecked")
public class SmsDataService implements ISmsDataService{

	@Autowired
	private @SuppressWarnings("rawtypes") ICacheService redisService;
	
	// yunpian 短信发送服务
	@Autowired
	private YunPianSmsSendService yunPianSmsSendService;
	@Autowired
	private SmsUtil smsUtil;

	@Override
	public Result sendSmsHaveCode(SmsSendData sendData) {
		Result result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
		FindPasswordDTO findPasswordDTO = new FindPasswordDTO();
		// 验证短信发送参数
		/*if (!sendDataVerify(sendData)) {
			return result;
		}*/
		// 封装数据
		final SmsCodeType type = sendData.getType();
		final String mobile = sendData.getMobile();
		final String mobileType = (String) sendData.getModelData();
		//final SmsSourceEnum smsSourceEnum = sendData.getSourceEnum();
		//final String smsCode = sendData.getSmsCode();
		
		if(null==type ||null==mobile || "".equals(mobile)){
			result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
			return result;
		}
		
		boolean exists = SmsCodeType.isExists(type);
		if(!exists){
			result = ResultUtil.getResult(SmsStatusEnum.FORMAL_ERROR);
			return result;
		}
		
		findPasswordDTO.setPhone(mobile);
		findPasswordDTO.setCodeType("Fr170002");
		String Ytime = 1+"";
		findPasswordDTO.setYTime(Ytime);
		//客服电话：400–8586–315
		findPasswordDTO.setKfPhone("400–8586–315");
		//完善短信模板
		if("1".equals(mobileType)){
			findPasswordDTO.setOperation("注册");
		}
		if("2".equals(mobileType)){
			findPasswordDTO.setOperation("找回密码");
		}
		
		// 发送短信
		//result = yunPianSmsSendService.sendSmsHaveCode(mobile, smsCode, smsSourceEnum, type);
		Result  sendRegisterOrFindPasswordMsg = smsUtil.sendRegisterOrFindPasswordMsg(findPasswordDTO);
		// 设置缓存
		//if (result.isSuccess()) {
		if("1".equals(sendRegisterOrFindPasswordMsg.getCode())){
			// 判断是否需要缓存，发送验证码需要缓存。这里借用“找回密码”验证码枚举是否缓存为0的值来判断
			/*if (type.getIsCache().equals(SmsCodeType.BACK_USER_PWD_VC.getIsCache())) {
				redisService.set(getSmsKey(mobile, type.getCodeType(), sendData.getSourceEnum()), smsCode,
						CacheConfigEnum.SMS_MAX_TIME.getDuration(), CacheConfigEnum.SMS_MAX_TIME.getUnit());
			}*/
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
			
		}else{
			result = ResultUtil.setResult(false,sendRegisterOrFindPasswordMsg.getMessage());
			return result;
		}
		return result;
		
	}

	@Override
	public Result sendSmsNoCode(SmsSendData sendData) {
		Result result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
		// 验证短信发送参数
		if (!sendDataVerifyNoCode(sendData)) {
			return result;
		}
		// 封装数据
		final SmsCodeType type = sendData.getType();
		final SmsSourceEnum smsSourceEnum = sendData.getSourceEnum();
		final String mobile = sendData.getMobile();
		// 发送短信
		result = yunPianSmsSendService.sendSmsNoCode(mobile, smsSourceEnum, type);
		return result;
	}
	
	@Override
	public Result sendSmsByCustomModelData(SmsSendData sendData) {
		Result result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
		if (!sendDataVerifyCustomModel(sendData)) {
			return result;
		}
		// 发送短信
		return yunPianSmsSendService.sendSmsByModelData(sendData.getMobile(), sendData.getModelData(),
				sendData.getType());
	}
	
	@Override
	public Result checkSmsCodeByMobile(SmsSendData sendData) {
		Result result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
		/*if (!sendDataVerify(sendData)) {
			return result;
		}*/
		final SmsCodeType type = sendData.getType();
		final String mobile = sendData.getMobile();
		final String smsCode = sendData.getSmsCode();
		//Object obj = redisService.get(getSmsKey(mobile, type.getCodeType(), sendData.getSourceEnum()));
		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setPhone(mobile);
		checkDTO.setCodeType("Fr170002");
		checkDTO.setCode(smsCode);
		
		Result  check = smsUtil.check(checkDTO);
		
		if("1".equals(check.getCode())){
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}else{
			result.setCode("10000");
			ResultUtil.setResult(result, false, check.getMessage());
		}
		
		/*if (obj == null) {
			ResultUtil.setResult(result, SmsStatusEnum.SMS_LOSE);
		} else if (obj.toString().equals(smsCode)) {
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		} else {
			ResultUtil.setResult(result, SmsStatusEnum.SMS_ERROR);
		}*/
		return result;
	}
	/**
	 * 
	 * @Title: getSmsKey   
	 * @Description: 根据手机号类型得到 sms key   
	 * @param: @param mobile
	 * @param: @param type
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private static String getSmsKey(String mobile, String type, SmsSourceEnum sourceEnum) {
		StringBuilder bud = new StringBuilder(sourceEnum.name())
				.append("_").append(mobile).append("_").append(type);
		return bud.toString();
	}
	/**
	 * 
	 * @Title: sendDataVerify
	 * @Description:非空验证   
	 * @param: @param sendData
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private static boolean sendDataVerify(SmsSendData sendData) {
		String[] fields = { "mobile", "type", "smsCode" };
		return ParamVerifyUtil.entityIsNotNullByField(sendData, fields);
	}
	
	/**
	 * 
	 * @Title: sendDataVerifyNoCode
	 * @Description: 非空验证（无验证码的校验）
	 * @author liuy
	 * @date  2017年6月23日 下午2:48:17
	 * @param sendData
	 * @return
	 */
	private static boolean sendDataVerifyNoCode(SmsSendData sendData) {
		String[] fields = { "mobile", "type", "sourceEnum" };
		return ParamVerifyUtil.entityIsNotNullByField(sendData, fields);
	}
	/**
	 * 
	 * @Title: sendDataVerifyCustomModel   
	 * @Description: 自定义模板数据填充参数校验  
	 * @param: @param sendData
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private static boolean sendDataVerifyCustomModel(SmsSendData sendData){
		String[] fields = { "mobile", "type", "sourceEnum" ,"modelData" };
		return ParamVerifyUtil.entityIsNotNullByField(sendData, fields);
	}

	@Override
	public Result checkSmsCodeByMobile2(SmsSendData sendData) {
		Result result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
		final SmsCodeType type = sendData.getType();
		final String mobile = sendData.getMobile();
		final String smsCode = sendData.getSmsCode();
		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setPhone(mobile);
		checkDTO.setCodeType("Fr170001");
		checkDTO.setCode(smsCode);
		Result check = smsUtil.check(checkDTO);
		if("1".equals(check.getCode())){
			return ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}else{
			return ResultUtil.setResult(false, check.getMessage());
		}
	}
}
