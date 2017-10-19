/**
 * @Title:  SmsController.java
 * @Package com.phshopping.api.controller.sms
 * @Description:    TODO(用一句话描述该文件做什么)
 * @author: 李杰
 * @date:   2017年5月10日 上午10:02:26
 * @version V1.0
 * @Copyright: 2017
 */
package com.phshopping.api.controller.sms;

import com.ph.shopping.common.core.dto.FindPasswordDTO;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: SmsController
 * @Description:短信发送
 * @author: 李杰
 * @date: 2017年5月10日 上午10:02:26
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/sms")
public class SmsController {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	// 短信接口
	@Autowired
	private ISmsDataService smsDataService;

	@Autowired
	private SmsUtil smsUtil;

	/**
	 * 发送短信验证码
	 *
	 * @param: request
	 * @param: @param
	 *             phone
	 * @param: @param
	 *             model
	 * @return Result
	 * @author 李杰
	 * @createTime 2017年4月27日
	 */
	@RequestMapping(value = "/sendSmsCode/{phone}/{codeType}/{type}", method = RequestMethod.GET)
	public Result sendSmsCode(HttpServletRequest request, @PathVariable String phone, @PathVariable String codeType,
			@PathVariable String type) {
		logger.info("发送短信验证码 phone: " + phone + " codeType= " + codeType);
		FindPasswordDTO fDto = new FindPasswordDTO();
		fDto.setCodeType(codeType);
		fDto.setPhone(phone);
		fDto.setKfPhone("400–8586–315");
		fDto.setYTime("1");
		switch (type) {
		case "1":
			fDto.setOperation("注册");
			break;
		case "2":
			fDto.setOperation("找回密码");
			break;
		case "3":
			fDto.setOperation("分享建立推荐关系");
			break;
		case "4":
			fDto.setOperation("提现");
			break;
		case "5":
			fDto.setOperation("修改登录密码");
			break;
		case "6":
			fDto.setOperation("设置支付密码");
			break;
		case "7":
			fDto.setOperation("修改支付密码");
			break;
		}
		 Result sendRegisterOrFindPasswordMsg =  smsUtil.sendRegisterOrFindPasswordMsg(fDto);
		if ("1".equals(sendRegisterOrFindPasswordMsg.getCode())) {
			return  ResultUtil.getResult(RespCode.Code.SUCCESS);
		}else {
			return ResultUtil.setResult(false,sendRegisterOrFindPasswordMsg.getMessage());
		}

	}
	//分享的用户发送验证码
	@RequestMapping(value = "/sendsharecode/{phone}", method = RequestMethod.GET)
	public Result sendShareCode(HttpServletRequest request, @PathVariable  String phone) {
		logger.info("发送短信验证码 phone: " + phone + " codeType= " + "Fr170001");
		FindPasswordDTO fDto = new FindPasswordDTO();
		fDto.setCodeType("Fr170001");
		fDto.setPhone(phone);
		fDto.setKfPhone("400–8586–315");
		fDto.setYTime("1");
		fDto.setOperation("分享建立推荐关系");
		Result sendRegisterOrFindPasswordMsg =  smsUtil.sendRegisterOrFindPasswordMsg(fDto);
		if ("1".equals(sendRegisterOrFindPasswordMsg.getCode())) {
			return  ResultUtil.getResult(RespCode.Code.SUCCESS);
		}else {
			return ResultUtil.setResult(false,sendRegisterOrFindPasswordMsg.getMessage());
		}
	}
}
