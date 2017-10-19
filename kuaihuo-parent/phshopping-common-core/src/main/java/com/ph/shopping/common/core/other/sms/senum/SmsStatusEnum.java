package com.ph.shopping.common.core.other.sms.senum;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 
 * @ClassName:  SmsStatusEnum   
 * @Description:短信发送返回状态枚举  
 * @author: lijie
 * @date:   2017年5月11日 下午3:41:43     
 * @Copyright: 2017
 */
public enum SmsStatusEnum implements RespCode{

	//调用远程短信发送服务返回的
	SUCCESS("0","发送成功"),
	PARAM_INCOMPLETE("1","请求参数不全"),
	FORMAL_ERROR("2","请求参数格式错误"),
	SINGLE_OVERTIME("33","请求过于频繁，请1小时后重试！"),
	HOUR_OVERTIME("22","请求过于频繁，请1小时后重试！"),
	SKY_OVERTIME("17","当天短信发送已达上限！"),
	//自定义的
	VERIFYCODE_FAIL("3","验证码发送失败"),
	PASSWORD_FAIL("4","密码发送失败"),
	SMS_ERROR("101","短信验证码错误"),
	SMS_LOSE("102","短信验证码失效"),
	OVERTIME("44","发送验证码过于频繁，请稍后重试！"),
	SMS_MODELERROR("103","短信发送内容不能为空");
	/**
	 * 状态码
	 */
    private String code;
    /**
	 * 描述
	 */
    private String msg;

	private SmsStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
