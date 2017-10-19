package com.ph.shopping.facade.member.dto.message;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;
/**
 * 
 * @ClassName:  MessagePushRecordDTO   
 * @Description:推送记录传输DTO   
 * @author: 李杰
 * @date:   2017年5月31日 下午2:20:13     
 * @Copyright: 2017
 */
public class MessagePushRecordDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -5939870155793358555L;

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 消息ID
	 */
	@NotNull(message = "[消息模板ID]不能为空")
	private Long templateId;
	/**
	 * 用户ID
	 */
	@NotNull(message = "[用户ID]不能为空")
	private Long userId;
	/**
	 * 用户类型
	 */
	@NotNull(message = "[用户类型]不能为空")
	private Byte userType;
	/**
	 * 设备ID
	 */
	@NotBlank(message = "[设备ID]不能为空")
	private String equipmentId;
	/**
	 * 发送状态
	 */
	private Byte status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createrId;
	/**
	 * 消息推送内容
	 */
	private String messageContent;
	/**
	 * 是否已读
	 */
	private Byte isRead;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getUserType() {
		return userType;
	}
	public void setUserType(Byte userType) {
		this.userType = userType;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Byte getIsRead() {
		return isRead;
	}
	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}
}
