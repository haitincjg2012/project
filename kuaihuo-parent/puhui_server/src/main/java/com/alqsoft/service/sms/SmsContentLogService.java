package com.alqsoft.service.sms;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.sms.SmsContentLog;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: SmsContentLogService.java
 * @Description: 消息聊天日志 service
 * @author 
 * @e-mail 
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface SmsContentLogService extends BaseService<SmsContentLog>{
	public EasyuiResult<List<SmsContentLog>> getSmsContentLogPage(Map<String, Object> map,Integer page,Integer rows);
	
	public SmsContentLog getSmsContentLogByUniqueKey(String uniqueKey);
	/***
	 * 接口专用的分页查询方法
	 * @Title: getSmsContentLogPageByMobile
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiWebResult<List<SmsContentLog>>
	 * @throws
	 */
	public EasyuiWebResult<List<SmsContentLog>> getSmsContentLogPageByMobile(Map<String, Object> map,Integer page,Integer rows);
}
