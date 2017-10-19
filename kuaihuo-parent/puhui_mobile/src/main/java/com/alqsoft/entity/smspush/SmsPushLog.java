package com.alqsoft.entity.smspush;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * @Title: SmsPushLog.java
 * @Description: 短信推送日志
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午9:39:30 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */


public class SmsPushLog extends IdEntity {

	private String smsTitle;//短信标题
	
	private Long smsSenderId;// 发送者ID 帐号id

	private String smsSenderName;// 发送者名称

	private Long smsReceiveId;// 接收者ID 帐号id

	private String smsReceiveName;// 接收者名称

	private String describer;// 描述

	private Integer year;// 年

	private Integer month;// 月

	private Integer day;// 日

	private String content;// 内容

	private Integer state;// 0 未发送 1发送失败

	private Long stepwatch;// 时间差(单位：ms)
	
	private String smsEnglishName;// 短信模版英文名（唯一）

	public String getSmsTitle() {
		return smsTitle;
	}

	public void setSmsTitle(String smsTitle) {
		this.smsTitle = smsTitle;
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

	
	public String getSmsEnglishName() {
		return smsEnglishName;
	}

	public void setSmsEnglishName(String smsEnglishName) {
		this.smsEnglishName = smsEnglishName;
	}
	
}
