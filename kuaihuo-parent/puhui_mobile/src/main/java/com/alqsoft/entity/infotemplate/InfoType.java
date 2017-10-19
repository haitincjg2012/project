package com.alqsoft.entity.infotemplate;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Title: InfoType.java
 * @Description: 发送信息类型
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午4:19:05
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */

public class InfoType extends IdEntity{

	
	@NotBlank(message = "发送类型名称不能为空")
	@Length(min = 3, max = 60, message = "发送类型名称必须在3到80之间，请重新输入")
	private String infoTypeName;//发送类型名称
	@NotBlank(message = "发送类型英文名称不能为空")
	@Length(min = 3, max = 100, message = "发送类型英文名称必须在3到100之间，请重新输入")
	private String infoTypeEnglishName;//发送类型英文名称
	
	public String getInfoTypeName() {
		return infoTypeName;
	}

	public void setInfoTypeName(String infoTypeName) {
		this.infoTypeName = infoTypeName;
	}
	

	public String getInfoTypeEnglishName() {
		return infoTypeEnglishName;
	}

	public void setInfoTypeEnglishName(String infoTypeEnglishName) {
		this.infoTypeEnglishName = infoTypeEnglishName;
	}
	
	
}
