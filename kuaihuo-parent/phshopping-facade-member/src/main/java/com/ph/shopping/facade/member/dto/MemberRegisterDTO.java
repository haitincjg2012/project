package com.ph.shopping.facade.member.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  RegisterMemberDto   
 * @Description:注册传输数据   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:18:57     
 * @Copyright: 2017
 */
public class MemberRegisterDTO extends BaseValidate{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -418791511192196316L;

	/**
	 * 会员手机号
	 */
	@NotBlank(message="[会员手机号]不能为空")
	private String telPhone;
	/**
	 * 会员密码
	 */
	@NotBlank(message="[会员密码]不能为空")
	private String memberPwd;
	/**
	 *会员名称
	 */
	private String memberName;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	/**
	 * 会员昵称
	 */
	private String nikeName;

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
}
