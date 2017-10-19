package com.alqsoft.entity.emailpush;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * @Title: EmailPushLog.java
 * @Description: 邮件推送日志
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午9:39:30 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */

public class EmailPushLog extends IdEntity {

	private String emailTitle;//邮件标题
	
	private Long emailSenderId;// 发送者ID 帐号id

	private String emailSenderName;// 发送者名称

	private Long emailReceiveId;// 接收者ID 帐号id

	private String emailReceiveName;// 接收者名称

	private String describer;// 描述

	private Integer year;// 年

	private Integer month;// 月

	private Integer day;// 日

	private String content;// 内容

	private Integer state;// 0 未发送 1发送失败

	private Long stepwatch;// 时间差(单位：ms)
	
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

	public String getDescriber() {
		return describer;
	}

	public void setDescriber(String describer) {
		this.describer = describer;
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

	public Long getStepwatch() {
		return stepwatch;
	}

	public void setStepwatch(Long stepwatch) {
		this.stepwatch = stepwatch;
	}
	

	public String getEmailEnglishName() {
		return emailEnglishName;
	}

	public void setEmailEnglishName(String emailEnglishName) {
		this.emailEnglishName = emailEnglishName;
	}

}
