package com.ph.shopping.facade.member.dto.message;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.common.core.customenum.PushTypeEnum;

public class JPushSendDTO extends BaseValidate {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 3327017785174663150L;
	/**
	 * 消息模板ID
	 */
	@NotNull(message = "[消息模板ID]不能为空")
	private Long templateId;
	/**
	 * 消息内容
	 */
	@NotBlank(message = "[消息内容]不能为空")
	private String messageContent;
	/**
	 * 发送类型 0、所有 1、Android 2、ios
	 */
	@NotNull(message = "[发送类型]不能为空")
	private Byte sendType;
	/**
	 * 用户基础信息
	 */
	@NotEmpty(message = "[用户信息]不能为空")
	private List<JPushUserDTO> users;
	/**
	 * 创建人ID
	 */
	@NotNull(message = "[创建人ID]不能为空")
	private Long createrId;
	 /**
     * 推送方式
     */
	@NotNull(message = "[推送方式]不能为空")
    private PushTypeEnum pushType;
    
	public Byte getSendType() {
		return sendType;
	}
	public void setSendType(Byte sendType) {
		this.sendType = sendType;
	}
	public List<JPushUserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<JPushUserDTO> users) {
		this.users = users;
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
	public PushTypeEnum getPushType() {
		return pushType;
	}
	public void setPushType(PushTypeEnum pushType) {
		this.pushType = pushType;
	}
	
}
