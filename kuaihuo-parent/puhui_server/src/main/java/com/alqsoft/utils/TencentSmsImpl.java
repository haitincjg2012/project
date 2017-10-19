package com.alqsoft.utils;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月19日
 *
 */
@Component
public class TencentSmsImpl implements AdditionalAuthenticationChecksValidater{
	
	private static Logger logger = LoggerFactory.getLogger(TencentSmsImpl.class);
	private final static String send_msg_url="http://123.207.175.228:9999/four/mobile/view/check/sendMsg";
	private final static String check_msg_url="http://123.207.175.228:9999/four/mobile/view/check/checkMsg";
	
	/**
	 * 发送验证码  alq20170319
	 * @param phone
	 * @return
	 */
	public Result sendMsg(String phone, String codeType) {
		Result result = new Result();
		
		if(StringUtils.isEmptyOrWhitespaceOnly(phone)){
			return ResultUtils.returnError("手机号不能为空");
		}
		
		try {
			String msg_url = send_msg_url;//发送短信路径
			String url = msg_url+"?phone="+phone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用发送验证码接口异常"+e.getMessage());
			return ResultUtils.returnError("发送失败");
		}
		
		
	}
	
	/**
	 * 验证验证码 alq20170319
	 * @param phone
	 * @param code
	 * @param codeType
	 * @return
	 */
	@Override
	public Result checkInfo(String phone, String code) {
		Result result = new Result();
		try {
			String msg_url =check_msg_url;//发送短信路径
			String url = msg_url+"?phone="+phone+"&code="+code+"&codeType=alq20170319";
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("调用验证验证码接口异常"+e.getMessage());
			return ResultUtils.returnError("调用验证验证码接口异常");
		}
	}
	
}
