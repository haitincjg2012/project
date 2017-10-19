package com.alqsoft.dao.msgpush;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.msgpush.PushGroupToRecord;

/**
 * 
 * @Title: PushGroupToRecordDao.java
 * @Description: dao
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushGroupToRecordDao extends BaseDao<PushGroupToRecord>{
	
	@Query("from PushGroupToRecord cl where cl.uniqueKey=:uniqueKey and cl.accountId=:accountId")
	public PushGroupToRecord getPushGroupToRecordByUniqueKeyAndAccountId(@Param("uniqueKey") String uniqueKey,@Param("accountId") Long accountId);
}
