package com.ph.shopping.common.util.other.smssend.request;
/**
 * 
 * @ClassName:  SmsCodeData   
 * @Description:一般短信发送请求数据   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:39:45     
 * @Copyright: 2017
 */
public class SmsCodeData {

	/**
	 * 短信发送地址
	 */
	private String sendUrl;
	/**
	 * 校验 短信地址
	 */
	private String smsCodeCheckUrl;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 编码类型
	 */
	private String codeType;
	/**
	 * 需要校验的短信码
	 */
	private String smsCode;
	
	
	public String getSmsCodeCheckUrl() {
		return smsCodeCheckUrl;
	}
	public void setSmsCodeCheckUrl(String smsCodeCheckUrl) {
		this.smsCodeCheckUrl = smsCodeCheckUrl;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
}
