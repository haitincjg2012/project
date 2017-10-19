package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  RegisterMemberDto   
 * @Description:新增传输数据   
 * @author: liuy
 * @date:   2017年4月25日 上午11:18:57     
 * @Copyright: 2017
 */
public class MemberAddDTO extends BaseValidate{

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
	 * 创建人Id
	 */
	@NotNull(message="[创建人]不能为空")
	private Long createrId;
	
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
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	
}
