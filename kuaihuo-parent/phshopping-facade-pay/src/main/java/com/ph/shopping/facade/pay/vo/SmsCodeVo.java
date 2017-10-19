package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;
import java.util.Date;

public class SmsCodeVo implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 1697409400596687484L;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 短信code
	 */
	private String smsCode;
	/**
	 * 发送状态
	 */
	private Byte sendStatus;
	/**
	 * 发送类型
	 */
	private String sendType;
	/**
	 * 是否有效
	 */
	private Byte isAvailable;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	public Byte getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Byte isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public Byte getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Byte sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
