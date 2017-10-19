package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.Date;

public class MessageVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4124240479154625948L;
	/**
	 * 消息记录ID
	 */
	private Long recordId; 
	/**
	 * 消息ID
	 */
	private Long templateId;
	/**
	 * 消息标题
	 */
	private String messageTitle;
	/**
	 * 消息提示
	 */
	private String messageAlert;
	/**
	 * 消息内容模板
	 */
	private String messageTemplate;
	/**
	 * 消息内容
	 */
	private String messageContent;
	/**
	 * 消息类型
	 */
	private Byte messageType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createrId;
	/**
	 * 是否已读
	 */
	private Byte isRead;
	/**
	 * 信任标语
	 */
	private String trustSlogan;
	/**
	 *品牌标语
	 */
	private String brandSlogan;
	/**
	 * 用户手机号
	 */
	private Long userId;

	/**
	 * 消息类型
	 * @return
	 */
	private Long type;
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Byte getIsRead() {
		return isRead;
	}

	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getMessageAlert() {
		return messageAlert;
	}

	public void setMessageAlert(String messageAlert) {
		this.messageAlert = messageAlert;
	}

	public Byte getMessageType() {
		return messageType;
	}

	public void setMessageType(Byte messageType) {
		this.messageType = messageType;
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

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getTrustSlogan() {
		return trustSlogan;
	}

	public void setTrustSlogan(String trustSlogan) {
		this.trustSlogan = trustSlogan;
	}

	public String getBrandSlogan() {
		return brandSlogan;
	}

	public void setBrandSlogan(String brandSlogan) {
		this.brandSlogan = brandSlogan;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
}
