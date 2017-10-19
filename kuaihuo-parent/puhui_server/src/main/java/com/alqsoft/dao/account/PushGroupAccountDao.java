package com.alqsoft.dao.account;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.account.PushGroupAccount;

/**
 * 
 * @Title: PushGroupAccountDao.java
 * @Description: 推送组和用户dao
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupAccountDao extends BaseDao<PushGroupAccount>{
	
	@Query("from PushGroupAccount cg where cg.accountId=:accountId")
	public List<PushGroupAccount> findPushGroupAccountByAccountId(@Param("accountId") Long accountId);

	@Query("from PushGroupAccount cg where cg.pushGroupId=:pushGroupId")
	public List<PushGroupAccount> findPushGroupAccountByPushGroupId(@Param("pushGroupId") Long pushGroupId);

	@Query("from PushGroupAccount cg where cg.pushGroupId=:pushGroupId and cg.accountId=:accountId")
	public PushGroupAccount getPushGroupAccountByPushGroupIdAndAccountId(@Param("pushGroupId") Long pushGroupId,
			@Param("accountId") Long accountId);

	@Modifying
	@Query("DELETE FROM PushGroupAccount cg WHERE cg.accountId=:accountId")
	public Integer deletePushGroupAccountByAccountId(@Param("accountId") Long accountId);

	@Modifying
	@Query("DELETE FROM PushGroupAccount cg WHERE cg.pushGroupId=:pushGroupId")
	public Integer deletePushGroupAccountByPushGroupId(@Param("pushGroupId") Long pushGroupId);
	
}
