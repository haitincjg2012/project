package com.alqsoft.service.impl.account;

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

import com.alqsoft.dao.account.PushGroupAccountDao;
import com.alqsoft.entity.account.PushGroupAccount;
import com.alqsoft.service.account.PushGroupAccountService;

/**
 * 
 * @Title: PushGroupAccountServiceImpl.java
 * @Description: 描述
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:48:05 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Service
@Transactional(readOnly=true)
public class PushGroupAccountServiceImpl implements PushGroupAccountService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(PushGroupAccountServiceImpl.class);
	@Autowired
	private PushGroupAccountDao accountDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			accountDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public PushGroupAccount get(Long arg0) {
		
		return accountDao.findOne(arg0);
	}
	@Override
	@Transactional
	public PushGroupAccount saveAndModify(PushGroupAccount arg0) {
		return accountDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<PushGroupAccount>> getPushGroupAccountPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroupAccount> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroupAccount.class);
		Page<PushGroupAccount> Page = accountDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(PushGroupAccount.class, Page);
	}

	@Override
	public List<PushGroupAccount> findPushGroupAccountByAccountId(Long accountId) {
		return accountDao.findPushGroupAccountByAccountId(accountId);
	}

	@Override
	public List<PushGroupAccount> findPushGroupAccountByPushGroupId(Long pushGroupId) {
		return accountDao.findPushGroupAccountByPushGroupId(pushGroupId);
	}

	@Override
	public PushGroupAccount getPushGroupAccountByPushGroupIdAndAccountId(Long pushGroupId,
			Long accountId) {
		return accountDao.getPushGroupAccountByPushGroupIdAndAccountId(pushGroupId, accountId);
	}

	@Override
	public Integer deletePushGroupAccountByAccountId(Long accountId) {
		return accountDao.deletePushGroupAccountByAccountId(accountId);
	}

	@Override
	public Integer deletePushGroupAccountByPushGroupId(Long pushGroupId) {
		return accountDao.deletePushGroupAccountByPushGroupId(pushGroupId);
	}
}
