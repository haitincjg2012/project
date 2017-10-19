package com.alqsoft.entity.feedback;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 
 * @Title: FeedBackType.java
 * @Description: 意见反馈类型实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午6:11:15
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@Entity
@Table(name="alq_feedbacktype", indexes = {@Index(columnList = "typeName", name = "index_type_name")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class FeedBackType extends IdEntity{
	
	@NotBlank(message = "反馈类型名称不能为空")
	@Length(min = 3, max = 80, message = "反馈类型名称必须在3到100之间，请重新输入")
	private String typeName;//反馈类型名称

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
