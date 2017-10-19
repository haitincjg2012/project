package com.alqsoft.dao.sms;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.sms.ChatGroupToRecord;

/**
 * 
 * @Title: ChatGroupToRecordDao.java
 * @Description: dao
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ChatGroupToRecordDao extends BaseDao<ChatGroupToRecord>{
	
	@Query("from ChatGroupToRecord cl where cl.uniqueKey=:uniqueKey and cl.accountId=:accountId")
	public ChatGroupToRecord getChatGroupToRecordByUniqueKeyAndAccountId(@Param("uniqueKey") String uniqueKey,@Param("accountId") Long accountId);
	
}
