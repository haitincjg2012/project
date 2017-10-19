package com.alqsoft.service.impl.bankcard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.bankcard.BankCardDao;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.service.bankcard.BankCardService;

/**
 * Date:     2017年3月14日  16:45:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */


@Service
@Transactional(readOnly=true)
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardDao bankCardDao;
	
	/**
	 * 根据批发商ID获取银行卡信息
	 */
	@Override
	public List<Map<String, Object>> getBankCardByHunterId(Long hunterId) {
		// TODO Auto-generated method stub
		return bankCardDao.getBankCardByHunterId(hunterId);
	}
	/**
	 * 根据批发商ID获取银行卡信息
	 */
	@Override
	public BankCard getBankCardById(Long id) {
		// TODO Auto-generated method stub
		return bankCardDao.getBankCardById(id);
	}
	/**
	 * 查询当月修改次数
	 */
	@Override
	public Integer getBankCardCount(Long hunterId) {
		// TODO Auto-generated method stub
		return bankCardDao.getBankCardCount(hunterId);
	}
	
	@Override
	public List<Map<String, Object>> getHunterBankCardMsg(Long hunterId) {
		// TODO Auto-generated method stub
		return bankCardDao.getHunterBankCardMsg(hunterId);
	}

	
}
