package com.alqsoft.entity.msgpush;

import java.util.Date;

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
 * @Description: 消息推送日志记录表 （单推）
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月13日 下午4:14:18 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Entity
@Table(name = "alq_push_content_log", indexes = {
		@Index(columnList = "pushReceiveName", name = "index_push_receive_name"),
		@Index(columnList = "year", name = "index_year"),
		@Index(columnList = "month", name = "index_month"),
		@Index(columnList = "day", name = "index_day"),
		@Index(columnList = "pushState", name = "index_push_state"),
		@Index(columnList = "state", name = "index_state"),@Index(columnList = "pushSendTime", name = "index_push_sendtime"),
		@Index(columnList = "uniqueKey", name = "index_unique_key") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class PushContentLog extends IdEntity {

	private Long pushReceiveId;// 接收者ID,或者群id
	@Length(max = 100)
	private String pushReceiveName;// 接收者名称,或用户名称

	private String content;// 内容

	private Integer state;// 0 单推，1、群推

	private Integer year;// 年

	private Integer month;// 月

	private Integer day;// 日

	private Integer pushState;// 0:待推送、1:已推送、2:已接收
	
	private Integer pushType;//消息推送类型：1 普通 2 图片 3：语音 4、视频

	@JsonSerialize(using=JacksonConvertorDate.class)
	private Date pushSendTime;// 推送时间
	
	@Length(max = 100)
	private String uniqueKey;// 唯一标识

	private Integer pushTimes;// 推送次数
	
	private Long stepWatch;//相差毫秒数

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

	public Integer getPushState() {
		return pushState;
	}

	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Date getPushSendTime() {
		return pushSendTime;
	}

	public void setPushSendTime(Date pushSendTime) {
		this.pushSendTime = pushSendTime;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Integer getPushTimes() {
		return pushTimes;
	}

	public void setPushTimes(Integer pushTimes) {
		this.pushTimes = pushTimes;
	}

	public Long getStepWatch() {
		return stepWatch;
	}

	public void setStepWatch(Long stepWatch) {
		this.stepWatch = stepWatch;
	}
}
