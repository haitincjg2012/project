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

import com.alqsoft.dao.infotemplate.SmsTemplateDao;
import com.alqsoft.entity.infotemplate.SmsTemplate;
import com.alqsoft.service.infotemplate.SmsTemplateService;

/**
 * 
 * @Title: EmailTemplateServiceImpl.java
 * @Description: 短信模板服务实现类
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
public class SmsTemplateServiceImpl implements SmsTemplateService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);

	@Autowired
	private SmsTemplateDao smsTemplateDao;

	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			smsTemplateDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public SmsTemplate get(Long arg0) {
		return smsTemplateDao.findOne(arg0);
	}

	@Override
	@Transactional
	public SmsTemplate saveAndModify(SmsTemplate arg0) {
		return smsTemplateDao.save(arg0);
	}

	@Override
	public EasyuiResult<List<SmsTemplate>> getSmsTemplatePage(Map<String, Object> map,
			Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<SmsTemplate> specification = DynamicSpecifications.bySearchFilter(
				filter.values(), SmsTemplate.class);
		Page<SmsTemplate> userPage = smsTemplateDao.findAll(specification, new PageRequest(
				page - 1, rows, new Sort(Direction.DESC, new String[] { "updateTime" })));
		return EasyUtils.returnPage(SmsTemplate.class, userPage);
	}

	@Override
	public SmsTemplate getSmsTemplateByEnglishName(String smsEnglishName) {
		return smsTemplateDao.getSmsTemplateByEnglishName(smsEnglishName);
	}

}
