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
 * @Title: SmsContentLog.java
 * @Description: 群发聊天消息体发送日志记录表 （群聊）
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月13日 下午4:14:18 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Entity
@Table(name = "alq_chat_group_content_log", indexes = {
		@Index(columnList = "smsSenderName", name = "index_sms_sender_name"),
		@Index(columnList = "smsReceiveName", name = "index_sms_receive_name"),
		@Index(columnList = "year", name = "index_year"),
		@Index(columnList = "month", name = "index_month"),
		@Index(columnList = "day", name = "index_day"),
		@Index(columnList = "state", name = "index_state"),@Index(columnList = "smsSendTime", name = "index_sms_sendtime"),
		@Index(columnList = "uniqueKey", name = "index_unique_key") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ChatGroupContentLog extends IdEntity {

	private Long smsSenderId;// 发送者ID 帐号id
	@Length(max = 100)
	private String smsSenderName;// 发送者名称

	private Long smsReceiveId;// 接收者ID,或者群id

	private String smsReceiveName;// 接收者名称,或用户名称

	private String content;// 内容

	private Integer state;// 0 单发，1、群发，3推送

	private Integer year;// 年

	private Integer month;// 月

	private Integer day;// 日
	
	private Integer msgType;//消息类型 1 普通 2 图片 3：语音 4、视频

	@JsonSerialize(using=JacksonConvertorDate.class)
	private Date smsSendTime;// 发送时间

	private String uniqueKey;// 唯一标识

	private Long stepWatch;//相差毫秒数
	
	private Integer times;// 发送次数 

	public Long getStepWatch() {
		return stepWatch;
	}

	public void setStepWatch(Long stepWatch) {
		this.stepWatch = stepWatch;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Date getSmsSendTime() {
		return smsSendTime;
	}

	public void setSmsSendTime(Date smsSendTime) {
		this.smsSendTime = smsSendTime;
	}

	@Column(unique = true)
	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
}
