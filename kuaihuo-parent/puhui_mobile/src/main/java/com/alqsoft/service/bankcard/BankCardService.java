package com.alqsoft.service.bankcard;

import java.util.List;
import java.util.Map;

import com.alqsoft.entity.bankcard.BankCard;

public interface BankCardService {

	/**
	 * 根据批发商ID获取银行卡信息
	 */
	public List<Map<String, Object>> getBankCardByHunterId(Long hunterId);
	/**
	 * 根据批发商ID获取银行卡信息
	 */
	public BankCard getBankCardById(Long id);
	
	/**
	 * 查询当月修改次数
	 */
	public Integer getBankCardCount(Long hunterId);
	
	public List<Map<String, Object>> getHunterBankCardMsg(Long hunterId);

}
