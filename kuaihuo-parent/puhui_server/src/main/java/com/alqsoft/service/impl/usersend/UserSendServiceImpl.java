package com.alqsoft.service.impl.usersend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.service.usersend.UserSendService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 下午3:55:54
 *获取手机验证码
 */
@Transactional(readOnly=true)
@Service
public class UserSendServiceImpl implements UserSendService {
	@Autowired
	private InitParamPc initParams;

	@Override
	public Result sendMessageCode(String phone,String codeType) {
		// TODO Auto-generated method stub
		// 判断手机是否符合判断格式
		phone = phone.substring(0,11);
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		Result result=new Result();
		if(""==phone || null==phone){
			return ResultUtils.returnError("手机号不能为空");
		}
		else if(!m.matches()){
			return ResultUtils.returnError("不符合手机格式");
		}
		
		try {
			String msg_url = this.initParams.getProperties().getProperty("send_msg_url");//发送短信路径
			String url = msg_url+"?phone="+phone+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultUtils.returnError("发送失败");
		}
		return result;
	}
	/***
	 * 验证短信
	 */

	/**
	 * 验证验证码
	 * @param phone
	 * @param code
	 * @param codeType
	 * @return
	 */
	public Result checkMsg(String phone, String code, String codeType) {
		Result result = new Result();
		phone = phone.substring(0,11);
		try {
			String msg_url = this.initParams.getProperties().getProperty("check_msg_url");//发送短信路径
			String url = msg_url+"?phone="+phone+"&code="+code+"&codeType="+codeType;
			String sendGet = HttpClientUtils.getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			result.setCode(Integer.parseInt(json.get("code").toString()));
			result.setMsg(json.get("msg").toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultUtils.returnError("调用验证验证码接口异常");
		}
	}
}
