/**  
 * @Title:  MemberShareDTO.java   
 * @Package com.ph.shopping.facade.member.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月25日 上午11:11:00   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * @ClassName: MemberShareDTO
 * @Description:会员分享传输DTO
 * @author: 李杰
 * @date: 2017年7月25日 上午11:11:00
 * @Copyright: 2017
 */
public class MemberAddShareDTO extends BaseValidate {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5303747949921004226L;

	@NotBlank(message = "分享人手机号不能为空")
	private String telPhone;
	/**
	 * 分享人ID
	 */
	@NotNull(message = "分享人ID不能为空")
	private Long userId;
	/**
	 * 被分享人手机号
	 */
	@NotBlank(message = "被分享人手机号不能为空")
	private String toTelPhone;

	@NotNull(message = "分享人类型不能为空")
	private Byte type;

	private String createIp;
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToTelPhone() {
		return toTelPhone;
	}

	public void setToTelPhone(String toTelPhone) {
		this.toTelPhone = toTelPhone;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

}
