package com.alqsoft.entity.sms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.alqframework.json.jackson.convertor.JacksonConvertorDate;
import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Title: ChatGroupToRecord.java
 * @Description: 群发消息发送到记录实体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月16日 下午5:20:45
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Entity
@Table(name = "alq_chatgroup_to_record", indexes = {
		@Index(columnList = "uniqueKey", name = "index_unique_key"),
		@Index(columnList = "smsState", name = "index_sms_state"),
		@Index(columnList = "smsSendTime", name = "index_sms_send_time"),
		@Index(columnList = "accountId", name = "index_account_id") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ChatGroupToRecord extends IdEntity{
	
	private Long accountId;//用户id
	@Length(max = 100)
	private String uniqueKey;// 唯一标识
	private int smsState;// 0:待发送、1:已发送、2:已接收 
	private Integer times;// 发送次数 
	@JsonSerialize(using=JacksonConvertorDate.class)
	private Date smsSendTime;// 发送时间
	private Long stepWatch;//相差毫秒数
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	@Column(unique=true)
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	public int getSmsState() {
		return smsState;
	}
	public void setSmsState(int smsState) {
		this.smsState = smsState;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Date getSmsSendTime() {
		return smsSendTime;
	}
	public void setSmsSendTime(Date smsSendTime) {
		this.smsSendTime = smsSendTime;
	}
	public Long getStepWatch() {
		return stepWatch;
	}
	public void setStepWatch(Long stepWatch) {
		this.stepWatch = stepWatch;
	}
	
}
