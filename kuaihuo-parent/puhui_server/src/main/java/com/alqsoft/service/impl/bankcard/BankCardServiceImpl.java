package com.alqsoft.service.impl.bankcard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.bankcard.BankCardDao;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.service.bankcard.BankCardService;

@Service
@Transactional(readOnly=true)
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardDao bankCardDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BankCard get(Long arg0) {
		// TODO Auto-generated method stub
		return bankCardDao.findOne(arg0);
	}

	@Override
	@Transactional
	public BankCard saveAndModify(BankCard arg0) {
		// TODO Auto-generated method stub
		return bankCardDao.save(arg0);
	}
	/**
	 * 根据批发商id查询银行信息
	 **/
	@Override
	public BankCard getBankCardByHunterId(Long hunterId) {
		// TODO Auto-generated method stub
		return bankCardDao.getBankCardByHunterId(hunterId);
	}
	/**
	 * 根据批发商id查询银行卡集合
	 **/
	@Override
	public List<BankCard> findBankCardByHunterId(Long hunterId) {
		// TODO Auto-generated method stub
		return bankCardDao.findBankCardByHunterId(hunterId);
	}
	/**
	 * 根据批发商id修改银行卡状态
	 **/
	@Override
	public int updateBankCardByHunterId(String[] bids) {
		
		return bankCardDao.updateBankCardByHunterId(bids);
	}

	/**
	 * 根据行业协会id查询银行卡集合
	 **/
	@Override
	public List<BankCard> findBankCardByindustryAssociationId(Long industryAssociationId) {
		// TODO Auto-generated method stub
		return bankCardDao.findBankCardByindustryAssociationId(industryAssociationId);
	}

}
