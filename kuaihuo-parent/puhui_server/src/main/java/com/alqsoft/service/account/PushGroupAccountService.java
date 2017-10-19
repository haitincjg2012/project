package com.alqsoft.service.account;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.account.PushGroupAccount;

/**
 * 
 * @Title: PushGroupAccountService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupAccountService extends BaseService<PushGroupAccount>{
	
	public EasyuiResult<List<PushGroupAccount>> getPushGroupAccountPage(Map<String, Object> map,Integer page,Integer rows);
	
	public List<PushGroupAccount> findPushGroupAccountByAccountId(Long accountId);

	public List<PushGroupAccount> findPushGroupAccountByPushGroupId(Long pushGroupId);

	public PushGroupAccount getPushGroupAccountByPushGroupIdAndAccountId(Long pushGroupId,Long accountId);

	public Integer deletePushGroupAccountByAccountId(Long accountId);

	public Integer deletePushGroupAccountByPushGroupId(Long pushGroupId);
}
