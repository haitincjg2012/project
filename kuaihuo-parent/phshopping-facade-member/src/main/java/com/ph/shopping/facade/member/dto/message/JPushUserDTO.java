package com.ph.shopping.facade.member.dto.message;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
/**
 * 
 * @ClassName:  JPushUserDTO   
 * @Description:消息推送用户相关信息   
 * @author: 李杰
 * @date:   2017年5月27日 上午11:48:38     
 * @Copyright: 2017
 */
public class JPushUserDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8662743838945263536L;
	/**
	 * 用户ID
	 */
	@NotNull(message = "用户ID不能为空")
	private Long userId;
	/**
	 * 设备id
	 */
	@NotBlank(message = "设备id不能为空")
	private String equipmentId;
	/**
	 * 用户类型
	 */
	@NotNull(message = "用户类型不能为空")
	private Byte userType;

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
