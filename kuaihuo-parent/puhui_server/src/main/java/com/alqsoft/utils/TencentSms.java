package com.alqsoft.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class TencentSms {
	
	private static Logger logger = LoggerFactory.getLogger(TencentSms.class);
	private final static String send_msg_url="http://123.207.175.228:9999/four/mobile/view/check/sendMsg";
	private final static String check_msg_url="http://123.207.175.228:9999/four/mobile/view/check/checkMsg";
	/**
	 * 发送验证码  alq20170319
	 * @param phone
	 * @return
	 */
	public Result sendMsg(String phone, String codeType) {
		Result result = new Result();
		// 判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断手机号是否存在
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("手机号不符合要求");
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
	public Result checkMsg(String phone, String code, String codeType) {
		Result result = new Result();
		try {
			String msg_url =check_msg_url;//发送短信路径
			String url = msg_url+"?phone="+phone+"&code="+code+"&codeType="+codeType;
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
