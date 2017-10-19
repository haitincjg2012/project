package com.alqsoft.mybatis.service.impl.bankcard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.mybatis.dao.bankcard.MyBankCardDao;
import com.alqsoft.mybatis.service.bankcard.MyBankCardService;

@Service
@Transactional(readOnly=true)
public class MyBankCardServiceImpl implements MyBankCardService {
	
	@Autowired
	private MyBankCardDao myBankCardDao;
	/**
	 * 行业协会后台--修改银行卡--根据会议协会ID查询银行卡信息
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getBankCardByIndustryAssociationId(Long industryAssociationId) {
		
		return myBankCardDao.getBankCardByIndustryAssociationId(industryAssociationId);
	}
	/**
	 * 行业协会后台--修改银行卡--修改次数
	 * @return
	 */
	@Override
	public int getBankCardCountByIndustryAssociationId(Long industryAssociationId) {
		
		return myBankCardDao.getBankCardCountByIndustryAssociationId(industryAssociationId);
	}
	/**
	 * 行业协会后台--提现页面--查询银行信息
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getIndustryAssociationBankCardMsg(Long industryAssociationId) {
		
		return myBankCardDao.getIndustryAssociationBankCardMsg(industryAssociationId);
	}

}
