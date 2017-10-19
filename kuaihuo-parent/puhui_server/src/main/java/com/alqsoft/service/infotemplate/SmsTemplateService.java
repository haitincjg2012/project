package com.alqsoft.service.infotemplate;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.infotemplate.SmsTemplate;

/**
 * 
 * @Title: EmailTemplateService.java
 * @Description: 短信模板服务接口
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:46:05
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface SmsTemplateService extends BaseService<SmsTemplate>{
	/**
	 * 分页查询短信模板
	 * @Title: getSmsTemplatePage
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiResult<List<SmsTemplate>>
	 * @throws
	 */
	public EasyuiResult<List<SmsTemplate>> getSmsTemplatePage(Map<String, Object> map, Integer page, Integer rows);
	
	/**
	 * 根据短信模板英文名获取短信模板对象
	 * @Title: getSmsTemplateByEnglishName
	 * @Description: TODO
	 * @param: @param smsEnglishName
	 * @param: @return
	 * @return: SmsTemplate
	 * @throws
	 */
	public SmsTemplate getSmsTemplateByEnglishName(String smsEnglishName);
}
