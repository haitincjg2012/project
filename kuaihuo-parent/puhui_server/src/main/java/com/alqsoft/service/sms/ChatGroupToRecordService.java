package com.alqsoft.service.sms;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.sms.ChatGroupToRecord;

/**
 * 
 * @Title: ChatGroupToRecordService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ChatGroupToRecordService extends BaseService<ChatGroupToRecord>{
	public EasyuiResult<List<ChatGroupToRecord>> getChatGroupToRecordPage(Map<String, Object> map,Integer page,Integer rows);
	
	public ChatGroupToRecord getChatGroupToRecordByUniqueKeyAndAccountId(String uniqueKey,Long accountId);
}
