package com.alqsoft.service.impl.membermoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.membermoney.MemberMoneyDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.membermoney.MemberMoney;
import com.alqsoft.service.membermoney.MemberMoneyService;

@Service
@Transactional(readOnly=true)
public class MemberMoneyServiceImpl implements MemberMoneyService {

	@Autowired
	private MemberMoneyDao memberMoneyDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberMoney get(Long arg0) {
		// TODO Auto-generated method stub
		return memberMoneyDao.findOne(arg0);
	}

	@Override
	@Transactional
	public MemberMoney saveAndModify(MemberMoney arg0) {
		MemberMoney memberMoney=null;
		
		try {
			memberMoney=memberMoneyDao.save(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
		}
		
		return memberMoney;
	}

	@Override
	@Transactional
	public MemberMoney getRowLock(String orderNo) {
		// TODO Auto-generated method stub
		return memberMoneyDao.getRowLock(orderNo);
	}

}
