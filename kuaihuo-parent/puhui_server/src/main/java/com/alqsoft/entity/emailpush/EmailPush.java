package com.alqsoft.entity.emailpush;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Title: EmailPush.java
 * @Description: 邮件推送
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午9:39:20 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Entity
@Table(name = "alq_email_push", indexes = {
		@Index(columnList = "emailSenderName", name = "index_email_sender_name"),
		@Index(columnList = "emailReceiveName", name = "index_email_receive_name"),
		@Index(columnList = "year", name = "index_year"),@Index(columnList = "month", name = "index_month"),
		@Index(columnList = "day", name = "index_day"),@Index(columnList = "state", name = "index_state")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class EmailPush extends IdEntity {
	
	private String emailTitle;//邮件标题

	private Long emailSenderId;// 发送者ID 帐号id

	private String emailSenderName;// 发送者名称

	private Long emailReceiveId;// 接收者ID 帐号id

	private String emailReceiveName;// 接收者名称

	private Integer year;// 年

	private Integer month;// 月

	private Integer day;// 日

	private String content;// 内容

	private Integer state;// 0 未发送 1发送失败

	private String describer;// 描述
	
	private String emailEnglishName;// 邮箱模版英文名（唯一）
	
	
	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public Long getEmailSenderId() {
		return emailSenderId;
	}

	public void setEmailSenderId(Long emailSenderId) {
		this.emailSenderId = emailSenderId;
	}

	public String getEmailSenderName() {
		return emailSenderName;
	}

	public void setEmailSenderName(String emailSenderName) {
		this.emailSenderName = emailSenderName;
	}

	public Long getEmailReceiveId() {
		return emailReceiveId;
	}

	public void setEmailReceiveId(Long emailReceiveId) {
		this.emailReceiveId = emailReceiveId;
	}

	public String getEmailReceiveName() {
		return emailReceiveName;
	}

	public void setEmailReceiveName(String emailReceiveName) {
		this.emailReceiveName = emailReceiveName;
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

	public String getDescriber() {
		return describer;
	}

	public void setDescriber(String describer) {
		this.describer = describer;
	}

	@Column(unique = true)
	public String getEmailEnglishName() {
		return emailEnglishName;
	}

	public void setEmailEnglishName(String emailEnglishName) {
		this.emailEnglishName = emailEnglishName;
	}
	
}
