package com.alqsoft.service.sms;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.sms.ChatGroupContentLog;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: ChatGroupContentLogService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ChatGroupContentLogService extends BaseService<ChatGroupContentLog>{
	public EasyuiResult<List<ChatGroupContentLog>> getChatGroupContentLogPage(Map<String, Object> map,Integer page,Integer rows);
	
	/**
	 * 根据唯一标识获取群聊日志记录
	 * @Title: getChatGroupContentLogByUniqueKey
	 * @Description: TODO
	 * @param: @param uniqueKey
	 * @param: @return
	 * @return: ChatGroupContentLog
	 * @throws
	 */
	public ChatGroupContentLog getChatGroupContentLogByUniqueKey(String uniqueKey);
	
	/**
	 * 接口使用的列表分页方法
	 * @Title: getChatGroupContentLogPageByMobile
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiResult<List<ChatGroupContentLog>>
	 * @throws
	 */
	public EasyuiWebResult<List<ChatGroupContentLog>> getChatGroupContentLogPageByMobile(Map<String, Object> map,Integer page,Integer rows);
}
