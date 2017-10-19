package com.alqsoft.mybatis.service.impl.cashreceivestation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.mybatis.dao.cashreceivestation.MyCashReceiveStationDao;
import com.alqsoft.mybatis.service.cashreceivestation.MyCashReceiveStationService;

@Service
@Transactional(readOnly=true)
public class MyCashReceiveStationServiceImpl implements MyCashReceiveStationService {

	
	@Autowired
	private MyCashReceiveStationDao myCashReceiveStationDao;
	

	/**
	 * 管理员后台——提现明细列表
	 */
	@Override
	public List<Map<String, Object>> getCashReceiveStationList(Map<String, Object> params) {
		
		return myCashReceiveStationDao.getCashReceiveStationList(params);
	}

	/**
	 * 管理员后台——提现明细列表-总条数
	 */
	@Override
	public int getCashReceiveStationCount(Map<String, Object> params) {
		
		return myCashReceiveStationDao.getCashReceiveStationCount(params);
	}

	/**
	 * 行业协会后台——提现明细列表
	 */
	@Override
	public List<Map<String, Object>> getIndustryAssociationCashReceiveStationList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return myCashReceiveStationDao.getIndustryAssociationCashReceiveStationList(params);
	}
	/**
	 * 行业协会后台——提现明细列表-总条数
	 */
	@Override
	public int getIndustryAssociationCashReceiveStationCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return myCashReceiveStationDao.getIndustryAssociationCashReceiveStationCount(params);
	}
}
