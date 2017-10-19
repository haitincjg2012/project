package com.alqsoft.mybatis.dao.bankcard;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface MyBankCardDao {
	/**
	 * 行业协会后台--修改银行卡--根据会议协会ID查询银行卡信息
	 * @return
	 */
	public List<Map<String, Object>> getBankCardByIndustryAssociationId(Long industryAssociationId);
	/**
	 * 行业协会后台--修改银行卡--修改次数
	 * @return
	 */
	public int getBankCardCountByIndustryAssociationId(Long industryAssociationId);
	/**
	 * 行业协会后台--提现页面--查询银行信息
	 * @return
	 */
	public List<Map<String, Object>> getIndustryAssociationBankCardMsg(Long industryAssociationId);

}
