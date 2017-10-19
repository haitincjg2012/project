package com.alqsoft.mybatis.service.cashreceivestation;

import java.util.List;
import java.util.Map;

public interface MyCashReceiveStationService {

	/**
	 * 管理员后台——提现明细列表
	 */
	public List<Map<String, Object>> getCashReceiveStationList(Map<String, Object> params);
	/**
	 * 管理员后台——提现明细列表-总条数
	 */
	public int getCashReceiveStationCount(Map<String, Object> params);
	/**
	 * 行业协会后台——提现明细列表
	 */
	public List<Map<String, Object>> getIndustryAssociationCashReceiveStationList(Map<String, Object> params);
	/**
	 * 行业协会后台——提现明细列表-总条数
	 */
	public int getIndustryAssociationCashReceiveStationCount(Map<String, Object> params);
}
