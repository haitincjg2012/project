package com.alqsoft.entity.msgpush;

import java.util.Date;

import org.alqframework.json.jackson.convertor.JacksonConvertorDate;
import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Title: ChatGroupToRecord.java
 * @Description: 群推送到记录实体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午5:20:45
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */

public class PushGroupToRecord extends IdEntity{
	
	private Long accountId;//用户id
	@Length(max = 100)
	private String uniqueKey;// 唯一标识
	private int pushState;// 0:待推送、1:已推送、2:已接收 
	private Integer pushTimes;// 发送次数 
	@JsonSerialize(using=JacksonConvertorDate.class)
	private Date pushSendTime;// 发送时间
	private Long stepWatch;//相差毫秒数
	
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	public int getPushState() {
		return pushState;
	}
	public void setPushState(int pushState) {
		this.pushState = pushState;
	}
	public Integer getPushTimes() {
		return pushTimes;
	}
	public void setPushTimes(Integer pushTimes) {
		this.pushTimes = pushTimes;
	}
	public Date getPushSendTime() {
		return pushSendTime;
	}
	public void setPushSendTime(Date pushSendTime) {
		this.pushSendTime = pushSendTime;
	}
	public Long getStepWatch() {
		return stepWatch;
	}
	public void setStepWatch(Long stepWatch) {
		this.stepWatch = stepWatch;
	}
}
