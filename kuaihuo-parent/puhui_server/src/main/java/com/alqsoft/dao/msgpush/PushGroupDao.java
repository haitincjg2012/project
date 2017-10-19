package com.alqsoft.dao.msgpush;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.msgpush.PushGroup;

/**
 * 
 * @Title: PushGroupDao.java
 * @Description: 消息群推送dao
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupDao extends BaseDao<PushGroup>{
	
	@Query("from PushGroup c where c.pushGroupName=:pushGroupName")
	public PushGroup getPushGroupByPushGroupName(@Param("pushGroupName") String pushGroupName);
}
