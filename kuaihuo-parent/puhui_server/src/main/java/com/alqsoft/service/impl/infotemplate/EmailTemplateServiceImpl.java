package com.alqsoft.service.impl.infotemplate;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.infotemplate.EmailTemplateDao;
import com.alqsoft.entity.infotemplate.EmailTemplate;
import com.alqsoft.service.infotemplate.EmailTemplateService;

/**
 * 
 * @Title: EmailTemplateServiceImpl.java
 * @Description: 邮箱模板服务实现类
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 下午2:48:05 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Service
@Transactional(readOnly = true)
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			emailTemplateDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public EmailTemplate get(Long arg0) {
		return emailTemplateDao.findOne(arg0);
	}

	@Override
	@Transactional
	public EmailTemplate saveAndModify(EmailTemplate arg0) {
		return emailTemplateDao.save(arg0);
	}

	@Override
	public EasyuiResult<List<EmailTemplate>> getEmailTemplatePage(Map<String, Object> map,
			Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<EmailTemplate> specification = DynamicSpecifications.bySearchFilter(
				filter.values(), EmailTemplate.class);
		Page<EmailTemplate> userPage = emailTemplateDao.findAll(specification, new PageRequest(
				page - 1, rows, new Sort(Direction.DESC, new String[] { "updateTime" })));
		return EasyUtils.returnPage(EmailTemplate.class, userPage);
	}

	@Override
	public EmailTemplate getEmailTemplateByEnglishName(String emailEnglishName) {
		return emailTemplateDao.getEmailTemplateByEnglishName(emailEnglishName);
	}
}
