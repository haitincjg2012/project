package com.alqsoft.service.bankcard;

import java.util.List;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.bankcard.BankCard;

public interface BankCardService extends BaseService<BankCard>{

	
	/**
	 * 根据批发商id查询银行信息
	 **/
	public BankCard getBankCardByHunterId(Long hunterId);
	/**
	 * 根据批发商id查询银行卡集合
	 **/
	public List<BankCard> findBankCardByHunterId(Long hunterId);
	/**
	 * 根据批发商id修改银行卡状态
	 **/
	public int updateBankCardByHunterId(String[] bids);
	/**
	 * 根据行业协会id查询银行卡集合
	 **/
	public List<BankCard> findBankCardByindustryAssociationId(Long industryAssociationId);
	

}
