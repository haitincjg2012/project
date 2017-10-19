package com.alqsoft.mybatis.service.industryassociation;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.industryassociation.IndustryAssociation;

public interface MyIndustryAssociationService {

	/**
	 * 行业协会后台——查询身份信息
	 * @return
	 */
	public List<Map<String, Object>> findIndustryAssociationById(Map<String, Object> params);
	/**
	 * 行业协会后台——查询身份信息条数
	 */
	public int findIndustryAssociationCountById(Map<String, Object> params);
	
	public IndustryAssociation getIndustryAssociationById(Map<String, Object> params);
	/**
	 * 行业协会后台--检测绑定银行卡
	 * @return
	 */
	public Result checkBankCard(IndustryAssociation industryAssociation);
	/**
	 * 行业协会后台--修改银行卡--根据会议协会ID查询银行卡信息
	 * @return
	 */
	public Map<String,Object> getBankCardIndustryAssociationId(Long industryAssociationId);
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
	/**
	 * 行业协会后台--根据手机号查询行业协会
	 */
	public IndustryAssociation getAssociationByPhone(Map<String, Object> param);
	
	/**
	 * 添加行业协会验证，名称是否重复存在
	 * @param param
	 * @return
	 */
	public int findAssociationLikeName(Map<String, Object> param);
	

}
