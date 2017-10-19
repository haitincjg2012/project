package com.alqsoft.service.impl.sendmsg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alqsoft.dao.sendmsg.SendMsgByPhoneDao;
import com.alqsoft.service.sendmsg.SendMsgByPhoneService;
import com.alqsoft.utils.TencentSms;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午2:07:02
 * 
 */
@Service
public class SendMsgByPhoneServiceImpl implements SendMsgByPhoneService {

	@Autowired
	private SendMsgByPhoneDao sendMsgByPhoneDao;

	@Override
	public Result SendMsgByPhone(String phone) {
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		}
		// 判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		int sendMsgByPhone;
		try {
			sendMsgByPhone = sendMsgByPhoneDao.sendMsgByPhone(phone);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultUtils.returnError("接口调用失败");
		}
		
		// 判断手机号是否存在
		if (!m.matches()) {
			return ResultUtils.returnError("手机号不符合要求");
		} else if (sendMsgByPhone <= 0) {
			return ResultUtils.returnError("该用户不存在");
		}
		//发送验证码,验证码的格式：alq20170319
		TencentSms sms = new TencentSms();
		Result sendMsg = sms.sendMsg(phone, "alq20170319");
		return sendMsg;
	}

}
