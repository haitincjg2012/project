package com.alqsoft.dao.smspush;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.smspush.SmsPush;

/**
 * 邮件推送  未发送
 * @Title: SmsPushDao.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午4:12:22
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface SmsPushDao extends BaseDao<SmsPush> {
	@Query(value="select * from alq_sms_push limit 0 ,:num",nativeQuery=true)
	public List<SmsPush> findSmsPushByNum(@Param("num") Integer num);
}
