package com.alqsoft.service.infotemplate;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.infotemplate.EmailTemplate;

/**
 * 
 * @Title: EmailTemplateService.java
 * @Description: 邮箱模板服务接口
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:46:05
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface EmailTemplateService extends BaseService<EmailTemplate> {
	/**
	 * 分页查询邮箱模板
	 * @Title: getEmailTemplatePage
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiResult<List<EmailTemplate>>
	 * @throws
	 */
	public EasyuiResult<List<EmailTemplate>> getEmailTemplatePage(Map<String, Object> map, Integer page, Integer rows);
	
	/**
	 * 根据邮箱模板英文名获取邮箱模板对象
	 * @Title: getEmailTemplateByEnglishName
	 * @Description: TODO
	 * @param: @param emailEnglishName
	 * @param: @return
	 * @return: EmailTemplate
	 * @throws
	 */
	public EmailTemplate getEmailTemplateByEnglishName(String emailEnglishName);
}
