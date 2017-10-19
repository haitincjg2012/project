package com.alqsoft.service;

import java.util.Map;

import org.alqframework.easyui.EasyuiResult;

import com.alqsoft.entity.industryassociation.IndustryAssociation;

public interface IOrderService {

	public EasyuiResult getOrderFenByManager(Integer page, Integer rows, Map<String, Object> map);

	public EasyuiResult getOrderFenByIndustryAssociation(Integer page, Integer rows, Map<String, Object> map);

	public IndustryAssociation getIndustryAssociationByPhone(String userName);

}
