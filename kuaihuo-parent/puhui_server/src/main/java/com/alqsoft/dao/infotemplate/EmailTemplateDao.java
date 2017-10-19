package com.alqsoft.dao.infotemplate;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.infotemplate.EmailTemplate;

/**
 * 
 * @Title: EmailTemplateDao.java
 * @Description: 邮箱模板数据接口
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:42:32
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface EmailTemplateDao extends BaseDao<EmailTemplate> {
	@Query("FROM EmailTemplate email WHERE email.emailEnglishName=:emailEnglishName")
	public EmailTemplate getEmailTemplateByEnglishName(@Param("emailEnglishName") String emailEnglishName);
}
