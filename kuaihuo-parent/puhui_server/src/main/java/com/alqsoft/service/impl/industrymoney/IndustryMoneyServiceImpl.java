package com.alqsoft.service.impl.industrymoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.industrymoney.IndustryMoneyDao;
import com.alqsoft.entity.industrymoney.IndustryMoney;
import com.alqsoft.service.industrymoney.IndustryMoneyService;

@Service
@Transactional(readOnly=true)
public class IndustryMoneyServiceImpl implements IndustryMoneyService {

	@Autowired
	private IndustryMoneyDao industryMoneyDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IndustryMoney get(Long arg0) {
		return this.industryMoneyDao.findOne(arg0);
	}

	@Override
	@Transactional
	public IndustryMoney saveAndModify(IndustryMoney arg0) {
		return this.industryMoneyDao.save(arg0);
	}

	@Override
	public IndustryMoney getRowLock(String orderNo) {
		// TODO Auto-generated method stub
		return industryMoneyDao.getRowLock(orderNo);
	}

}
