package com.alqsoft.service.impl.sendmsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.sendmsg.SendMsgDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.init.InitParams;
import com.alqsoft.rpc.mobile.RpcUserRegisterService;
import com.alqsoft.rpc.mobile.RpcUserSendService;
import com.alqsoft.service.register.UserRegisterService;
import com.alqsoft.service.sendmsg.SendMsgService;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.utils.JedisUtils;
import com.alqsoft.utils.StatusCodeEnums;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午6:29:29
 * 
 */
@Service
public class SendMsgServiceImpl implements SendMsgService {

	private static Logger log = LoggerFactory.getLogger(SendMsgServiceImpl.class);

	@Autowired
	private RpcUserSendService rpcUserSendService;

	@Autowired
	private SendMsgDao sendMsgDao;
	@Autowired
	private InitParamPc initParam;
	@Autowired
	private InitParams initParams;
	@Autowired
	private RpcUserRegisterService rpcUserRegisterService;
	@Autowired
	private UserRegisterService userRegisterService;
	

	/***
	 * 获取手机验证码绑定银行
	 */
	public Result sendMessageCodeForBank(String phone, String codeType) {

		Result result = null;
		// 判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);

		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		}
		if (!m.matches()) {
			return ResultUtils.returnError("手机号不能符合要求");
		}
		// 判断手机号是否是密保手机号
		Long sendMessageCode = sendMsgDao.sendMessageCode(phone);
		if (sendMessageCode <= 0) {
			return ResultUtils.returnError("请输入密保手机号");
		}

		try {
			result = rpcUserSendService.sendMessageCode(phone, codeType);

		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}

		return result;
	}

	/***
	 * 检验手机验证码绑定银行卡
	 */
	public Result checkMessageCodeForBank(String phone, String image, String codeType) {
		// TODO Auto-generated method stub

		// 缓存工具类
		JedisUtils jedisUtils = JedisUtils.getRu(initParam);

		Result result = new Result();

		if (org.apache.commons.lang3.StringUtils.isBlank(image)) {
			return ResultUtils.returnError("验证不能为空");
		} else if (org.apache.commons.lang3.StringUtils.isBlank(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		}

		Result checkMsg = rpcUserSendService.checkMsg(phone, image, codeType);
		if (!(checkMsg.getCode() == 1)) {
			return ResultUtils.returnError(checkMsg.getMsg());
		}
		// 删除缓存
		jedisUtils.del("check" + phone);
		// 缓存中添加数据
		jedisUtils.set("check" + phone, phone);
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());

		return result;
	}

	@Override
	public Result sendMessageCodeUpdatePassword(String phone, String codeType) {

		Result result = new Result();
		//判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		if(StringUtils.isEmptyOrWhitespaceOnly(phone)){
			return ResultUtils.returnError("手机号不能为空");
		}
	    if(!m.matches()){
	    	return ResultUtils.returnError("手机号不能符合要求");
	    }
    	// 判断手机是否存在数据库中
 		Long number = sendMsgDao.selectMemberByPhone(phone);
 	
 		if (number <= 0) {
 			return ResultUtils.returnError("没有该用户请注册");
 		}
	    try {
	    	result = rpcUserSendService.sendMessageCode(phone,codeType);
	    	
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
	
		return result;
	}
}
