package com.ph.shopping.facade.member.entity;

import java.io.Serializable;
/**
 * 
 * @ClassName:  MessagePushrRecord   
 * @Description:消息发送记录   
 * @author: 李杰
 * @date:   2017年5月27日 下午1:42:21     
 * @Copyright: 2017
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="ph_message_push_record")
public class MessagePushRecord implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 9070535235925780220L;
	/**
	 * 主键ID
	 */
	@Id
	private Long id;
	/**
	 * 消息ID
	 */
	@Column(name = "templateId")
	private Long templateId;
	/**
	 * 用户ID
	 */
	@Column(name = "userId")
	private Long userId;
	/**
	 * 用户类型
	 */
	@Column(name = "userType")
	private Byte userType;
	/**
	 * 设备ID
	 */
	@Column(name = "equipmentId")
	private String equipmentId;
	/**
	 * 发送状态
	 */
	@Column(name = "status")
	private Byte status;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private Date createTime;
	/**
	 * 创建人
	 */
	@Column(name = "createrId")
	private Long createrId;
	/**
	 * 消息推送内容
	 */
	@Column(name = "messageContent")
	private String messageContent;
	/**
	 * 是否已读
	 */
	@Column(name = "isRead")
	private Byte isRead;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Byte getIsRead() {
		return isRead;
	}

	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}
	
}
