package com.alqsoft.dao.feedback;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.feedback.FeedBack;

/**
 * 
 * @Title: FeedBackDao.java
 * @Description: 意见反馈Dao
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface FeedBackDao extends BaseDao<FeedBack>{
	//查询意见反馈类型下的意见反馈信息数量
	@Query("SELECT COUNT(1) FROM FeedBack where feedBackTypeId=:id")
	public Long getFeedBackNumByTypeId(@Param("id") Long id );
}
