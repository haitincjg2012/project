package com.alqsoft.service.msgpush;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.msgpush.PushGroupContentLog;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushGroupContentLogService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupContentLogService extends BaseService<PushGroupContentLog>{
	public EasyuiResult<List<PushGroupContentLog>> getPushGroupContentLogPage(Map<String, Object> map,Integer page,Integer rows);
	
	public PushGroupContentLog getPushGroupContentLogByUniqueKey(String uniqueKey);
	/**
	 * 客户端接口使用的分页方法
	 * @Title: getPushGroupContentLogPageByMobile
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiWebResult<List<PushGroupContentLog>>
	 * @throws
	 */
	public EasyuiWebResult<List<PushGroupContentLog>> getPushGroupContentLogPageByMobile(Map<String, Object> map,Integer page,Integer rows);
}
