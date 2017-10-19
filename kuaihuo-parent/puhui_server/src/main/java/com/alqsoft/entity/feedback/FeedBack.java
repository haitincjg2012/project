package com.alqsoft.entity.feedback;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.alqframework.orm.hibernate.EntityGetUtils;
import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.alqsoft.service.feedback.FeedBackTypeService;
import com.alqsoft.utils.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysql.fabric.xmlrpc.base.Member;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 
 * @Title: FeedBack.java
 * @Description: 意见反馈实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午6:11:29
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@Entity
@Table(name="alq_feedback", indexes = {
		@Index(columnList = "feedbackTitle", name = "index_feedback_title"),
		@Index(columnList = "isLook", name = "index_is_look"),@Index(columnList = "feedBackTypeId", name = "index_feedback_type_id")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class FeedBack extends IdEntity{
	
	private Long memberId;//反馈人id
	
	/**
	 * 忽略该字段（避免加入到缓存中）
	 */
	@XStreamOmitField
	private Member member;// 反馈人
	private Long feedBackTypeId;//反馈类型id
	/**
	 * 忽略该字段（避免加入到缓存中）
	 */
	@XStreamOmitField
	private FeedBackType feedBackType;// 反馈类型
	@Length(max = 100)
	private String feedbackTitle;//反馈标题
	private String feedbackContent;//反馈内容
	private Integer isLook=Constant.yesOrNo_No;//(0)未查看（1）已查看
	
	public String getFeedbackTitle() {
		return feedbackTitle;
	}
	public void setFeedbackTitle(String feedbackTitle) {
		this.feedbackTitle = feedbackTitle;
	}
	@Lob
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public Integer getIsLook() {
		return isLook;
	}
	public void setIsLook(Integer isLook) {
		this.isLook = isLook;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getFeedBackTypeId() {
		return feedBackTypeId;
	}
	public void setFeedBackTypeId(Long feedBackTypeId) {
		this.feedBackTypeId = feedBackTypeId;
	}
	@Transient
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Transient
	public FeedBackType getFeedBackType() {
		return (FeedBackType) EntityGetUtils.get(FeedBackTypeService.class, feedBackTypeId, feedBackType);
	}
	public void setFeedBackType(FeedBackType feedBackType) {
		this.feedBackTypeId = feedBackType.getId();
		this.feedBackType = feedBackType;
	}
	
	
}
