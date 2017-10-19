package com.alqsoft.entity.infotemplate;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Title: SmsTemplate.java
 * @Description: 短信模版管理
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午4:28:49 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */

public class SmsTemplate extends IdEntity {

	@NotBlank(message = "短信变量标签不能为空")
	@Length(min = 3, max = 50, message = "短信变量标签必须在3到50之间，请重新输入")
	private String smsVariableTags;// 短信变量标签
	@NotBlank(message = "短信模版内容不能为空")
	private String smsContent;// 短信模版内容
	@NotBlank(message = "短信模版标题不能为空")
	@Length(min = 3, max = 100, message = "短信模版标题必须在3到100之间，请重新输入")
	private String smsTitle;// 短信模版标题
	
	@NotBlank(message = "短信模版英文名不能为空")
	@Length(min = 3, max = 60, message = "短信模版英文名必须在3到60之间，请重新输入")
	private String smsEnglishName;// 短信模版英文名（唯一）
	
	@NotBlank(message = "短信发送类型名称不能为空")
	@Length(min = 3, max = 200, message = "短信发送类型名称必须在3到200之间，请重新输入")
	private String smsInfoTypeName;//短信发送类型名称 

	public String getSmsVariableTags() {
		return smsVariableTags;
	}

	public void setSmsVariableTags(String smsVariableTags) {
		this.smsVariableTags = smsVariableTags;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsTitle() {
		return smsTitle;
	}

	public void setSmsTitle(String smsTitle) {
		this.smsTitle = smsTitle;
	}

	
	public String getSmsEnglishName() {
		return smsEnglishName;
	}

	public void setSmsEnglishName(String smsEnglishName) {
		this.smsEnglishName = smsEnglishName;
	}

	public String getSmsInfoTypeName() {
		return smsInfoTypeName;
	}

	public void setSmsInfoTypeName(String smsInfoTypeName) {
		this.smsInfoTypeName = smsInfoTypeName;
	}
	
}
