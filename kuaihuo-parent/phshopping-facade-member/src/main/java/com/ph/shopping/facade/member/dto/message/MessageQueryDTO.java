package com.ph.shopping.facade.member.dto.message;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  MessageQueryDTO   
 * @Description:消息查询Dto   
 * @author: 李杰
 * @date:   2017年5月27日 下午1:47:27     
 * @Copyright: 2017
 */
public class MessageQueryDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 6409815150952062086L;
	/**
	 * 消息模板ID
	 */
	private Long templateId;
	/**
	 * 用户类型
	 */
	@NotNull(message = "[用户类型]不能为空")
	private Byte userType;
	/**
	 * 用户id
	 */
	@NotNull(message = "[用户ID]不能为空")
	private Long userId;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	/**
	 * 当前页码
	 */
	private Integer pageNum;
	/**
	 * 每页条数
	 */
	private Integer pageSize;
	/**
	 * 用户手机号
	 */
	private Long telPhone;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(Long telPhone) {
		this.telPhone = telPhone;
	}
	
}
