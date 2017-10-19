package com.alqsoft.dao.feedback;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.feedback.FeedBack;
import com.alqsoft.entity.feedback.FeedBackType;

/**
 * 
 * @Title: FeedBackTypeDao.java
 * @Description: 意见反馈类型Dao
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface FeedBackTypeDao extends BaseDao<FeedBackType> {
	@Query("from FeedBackType fbt where fbt.typeName=:typeName")
	public FeedBackType getFeedBackTypeByTypeName(@Param("typeName") String typeName);
	
	@Query("from FeedBack f,FeedBackType ft where f.feedBackTypeId=ft.id and ft.id=:id")
	public List<FeedBack> findFeedBackListByFeedBackTypeId(@Param("id") Long id );
}
