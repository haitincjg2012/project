package com.alqsoft.entity.infotemplate;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Title: EmailTemplate.java
 * @Description: 邮件模版管理
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月16日 下午4:27:55 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */

public class EmailTemplate extends IdEntity {

	@NotBlank(message = "邮件变量标签不能为空")
	@Length(min = 3, max = 100, message = "邮件变量标签必须在3到100之间，请重新输入")
	private String emailVariableTags;// 邮件变量标签
	@NotBlank(message = "邮件模版内容不能为空")
	private String emailContent;// 邮件模版内容
	@NotBlank(message = "邮件模版标题不能为空")
	@Length(min = 3, max = 100, message = "邮件模版标题必须在3到100之间，请重新输入")
	private String emailTitle;// 邮件模版标题

	@NotBlank(message = "邮箱模版英文名不能为空")
	@Length(min = 3, max = 60, message = "邮箱模版英文名必须在3到60之间，请重新输入")
	private String emailEnglishName;// 邮箱模版英文名（唯一）
	
	@NotBlank(message = "邮件发送类型名称不能为空")
	@Length(min = 3, max = 200, message = "邮件发送类型名称必须在3到200之间，请重新输入")
	private String emailInfoTypeName;//邮件发送类型名称 
	
	public String getEmailVariableTags() {
		return emailVariableTags;
	}

	public void setEmailVariableTags(String emailVariableTags) {
		this.emailVariableTags = emailVariableTags;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	
	public String getEmailEnglishName() {
		return emailEnglishName;
	}

	public void setEmailEnglishName(String emailEnglishName) {
		this.emailEnglishName = emailEnglishName;
	}

	public String getEmailInfoTypeName() {
		return emailInfoTypeName;
	}

	public void setEmailInfoTypeName(String emailInfoTypeName) {
		this.emailInfoTypeName = emailInfoTypeName;
	}
	
}
