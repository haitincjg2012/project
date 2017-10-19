package com.alqsoft.service.msgpush;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.msgpush.PushGroup;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushGroupService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupService extends BaseService<PushGroup>{
	public EasyuiResult<List<PushGroup>> getPushGroupPage(Map<String, Object> map,Integer page,Integer rows);
	
	public PushGroup getPushGroupByPushGroupName(String pushGroupName);
	/**
	 * 客户端接口使用的分页方法
	 * @Title: getPushGroupPageByMobile
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiWebResult<List<PushGroup>>
	 * @throws
	 */
	public EasyuiWebResult<List<PushGroup>> getPushGroupPageByMobile(Map<String, Object> map,Integer page,Integer rows);
}
