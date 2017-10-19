package com.alqsoft.dao.bankcard;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.bankcard.BankCard;

public interface BankCardDao extends BaseDao<BankCard>{

	/**
	 * 根据批发商id查询银行信息
	 **/
	@Query("from BankCard as bc where bc.hunter.id=:hunterId")
	public BankCard getBankCardByHunterId(@Param("hunterId")Long hunterId);

	/**
	 * 根据批发商id查询银行卡集合
	 **/
	@Query("from BankCard as bc where bc.hunter.id=:hunterId")
	public List<BankCard> findBankCardByHunterId(@Param("hunterId")Long hunterId);
	
	/**
	 * 根据银行卡id修改银行卡状态
	 **/
	@Query(value="UPDATE alq_bank_card SET status=0 WHERE id in (:bids)",nativeQuery=true)
	@Modifying
	public int updateBankCardByHunterId(@Param("bids")String[] bids);
	/**
	 * 根据行业协会id查询银行卡集合
	 **/
	@Query("from BankCard as bc where bc.industryAssociationId.id=:industryAssociationId")
	public List<BankCard> findBankCardByindustryAssociationId(@Param("industryAssociationId")Long industryAssociationId);
	
}
