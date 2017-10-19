package com.alqsoft.dao.bankcard;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.bankcard.BankCard;

@MyBatisRepository
public interface BankCardDao {

	/**
	 * 根据批发商ID获取银行卡信息
	 */
	public List<Map<String, Object>> getBankCardByHunterId(Long hunterId);
	/**
	 * 根据批发商ID获取银行卡信息
	 */
	public BankCard getBankCardById(@Param("id")Long id);
	/**
	 * 查询当月修改次数
	 */
	public Integer getBankCardCount(@Param("hunterId")Long hunterId);
	
	public List<Map<String, Object>> getHunterBankCardMsg(Long hunterId);

}
