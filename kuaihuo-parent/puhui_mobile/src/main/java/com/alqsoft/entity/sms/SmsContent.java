package com.alqsoft.entity.sms;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

/**
 * 
 * @Title: SmsContent.java
 * @Description: 聊天消息体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月12日 上午11:20:58
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class SmsContent {

	private String messageKey;// smsContent
	private Long smsSenderId;// 发送者ID 帐号id
	@Length(max = 100)
	private String smsSenderName;// 发送者名称

	private Long smsReceiveId;// 接收者ID,或者群id
	@Length(max = 100)
	private String smsReceiveName;// 接收者名称、群名称

	private String content;// 内容

	private Integer state;// 0 单发，1、群发，3推送
	@Length(max = 100)
	private String uniqueKey;//唯一标识
	
	private Date smsSendTime;//发送时间
	
	private Integer times=0;//发送次数
	
	private Integer msgType;//消息类型1 普通 2 图片 3：语音 4、视频
	
	private Long chatGroupId;//群发送id (响应给客户端时用到)
	
	private String chatGroupName;//群名称(响应给客户端时用到)
	
	private String headImgPath;//发送者头像地址
	
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Long getSmsSenderId() {
		return smsSenderId;
	}

	public void setSmsSenderId(Long smsSenderId) {
		this.smsSenderId = smsSenderId;
	}

	public String getSmsSenderName() {
		return smsSenderName;
	}

	public void setSmsSenderName(String smsSenderName) {
		this.smsSenderName = smsSenderName;
	}

	public Long getSmsReceiveId() {
		return smsReceiveId;
	}

	public void setSmsReceiveId(Long smsReceiveId) {
		this.smsReceiveId = smsReceiveId;
	}

	public String getSmsReceiveName() {
		return smsReceiveName;
	}

	public void setSmsReceiveName(String smsReceiveName) {
		this.smsReceiveName = smsReceiveName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Date getSmsSendTime() {
		return smsSendTime;
	}

	public void setSmsSendTime(Date smsSendTime) {
		this.smsSendTime = smsSendTime;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Long getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(Long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}
	public String getHeadImgPath() {
		return headImgPath;
	}

	public void setHeadImgPath(String headImgPath) {
		this.headImgPath = headImgPath;
	}

	public String getChatGroupName() {
		return chatGroupName;
	}

	public void setChatGroupName(String chatGroupName) {
		this.chatGroupName = chatGroupName;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
		
	}
	
}
