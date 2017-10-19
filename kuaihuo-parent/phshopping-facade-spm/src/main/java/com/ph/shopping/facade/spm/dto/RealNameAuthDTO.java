package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: RealNameAuthDTO
 * @Description: 实名认证DTO
 * @author 王强
 * @date 2017年7月13日 上午9:12:30
 */
public class RealNameAuthDTO implements Serializable {

	private static final long serialVersionUID = -1936723411570801469L;

	private String realName; // 真实姓名

	private String idCardNo;// 身份证号

	private String telPhone; // 手机号

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

}
