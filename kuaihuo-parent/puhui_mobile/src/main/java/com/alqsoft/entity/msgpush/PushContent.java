package com.alqsoft.entity.msgpush;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @Title: pushContent.java
 * @Description: 推送消息体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月12日 上午11:20:58
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class PushContent {

	private String messageKey;// pushContent

	private Long pushReceiveId;// 接收者ID,或者群id

	private String pushReceiveName;// 接收者名称、群名称

	private String content;// 内容

	private Integer state;// 0 单推，1、群推
	
	private String uniqueKey;//唯一标识
	
	private Date pushSendTime;//发送时间
	
	private Integer pushTimes=0;//发送次数
	
	private Integer pushType;//消息类型1 普通 2 图片 3：语音 4、视频
	
	private Long pushGroupId;//群推送id (响应给客户端时用到)
	
	private String headImgPath;//发送者头像地址
	
	
	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}


	public Long getPushReceiveId() {
		return pushReceiveId;
	}


	public void setPushReceiveId(Long pushReceiveId) {
		this.pushReceiveId = pushReceiveId;
	}


	public String getPushReceiveName() {
		return pushReceiveName;
	}


	public void setPushReceiveName(String pushReceiveName) {
		this.pushReceiveName = pushReceiveName;
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


	public Date getPushSendTime() {
		return pushSendTime;
	}


	public void setPushSendTime(Date pushSendTime) {
		this.pushSendTime = pushSendTime;
	}


	public Integer getPushTimes() {
		return pushTimes;
	}


	public void setPushTimes(Integer pushTimes) {
		this.pushTimes = pushTimes;
	}


	public Integer getPushType() {
		return pushType;
	}


	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Long getPushGroupId() {
		return pushGroupId;
	}

	public void setPushGroupId(Long pushGroupId) {
		this.pushGroupId = pushGroupId;
	}

	public String getHeadImgPath() {
		return headImgPath;
	}


	public void setHeadImgPath(String headImgPath) {
		this.headImgPath = headImgPath;
	}


	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
		
	}
	
}
