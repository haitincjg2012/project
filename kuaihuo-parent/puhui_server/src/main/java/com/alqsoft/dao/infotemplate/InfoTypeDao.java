package com.alqsoft.dao.infotemplate;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.infotemplate.InfoType;

/**
 * 发送类型
 * @Title: InfoTypeDao.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午5:39:43
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface InfoTypeDao extends BaseDao<InfoType>{
	@Query("FROM InfoType i WHERE i.infoTypeEnglishName=:typeEnglishName")
	public InfoType getInfoTypeByTypeEnglishName(@Param("typeEnglishName") String typeEnglishName);
}
