package com.alqsoft.mybatis.dao;

import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface IAgentDao {

	public Map<String, Object> getAgentByStatusAndId(Map<String, Object> params);

	public void insertUserBalanceTrade(Map<String, Object> param);

	public void updateUserBalance(Map<String, Object> param);

}
