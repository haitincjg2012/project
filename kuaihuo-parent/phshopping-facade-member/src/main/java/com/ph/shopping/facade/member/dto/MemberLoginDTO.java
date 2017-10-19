package com.ph.shopping.facade.member.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  MemberLoginDto   
 * @Description:会员登录传输数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:17:00     
 * @Copyright: 2017
 */
public class MemberLoginDTO extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 8985071640659889429L;

	/**
	 * 手机号
	 */
	@NotBlank(message="[会员手机号]不能为空")
	private String telPhone;
	/**
	 * 密码
	 */
	@NotBlank(message="[密码]不能为空")
	private String memberPwd;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	
}
