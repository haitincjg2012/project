package com.alqsoft.dao.cashreceivestation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface CashReceiveStationDao {
	/**
	 * 批发商个人中心--提现明细
	 * @return
	 */
	public List<Map<String, Object>> findCashReceiveStationById(HashMap<String, Object> param);
	/**
	 * 批发商个人中心--提现订单明细
	 */
	public List<Map<String, Object>> findCashReceiveStationByMerSeqId(HashMap<String, Object> param);
	/***
	 * 批发商个人中心--收入明细
	 * @param id
	 * @return
	 */
	public List<Map> findMoneyIncome(Long id);

}
