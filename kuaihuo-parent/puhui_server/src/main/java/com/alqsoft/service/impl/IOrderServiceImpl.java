package com.alqsoft.service.impl;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.mybatis.dao.IOrderDao;
import com.alqsoft.service.IOrderService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
@Service
@Transactional(readOnly=true)
public class IOrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IndustryAssociationService industryAssociationService;

	@Override
	public EasyuiResult getOrderFenByManager(Integer page, Integer rows, Map<String, Object> map) {
		EasyuiResult easyuiResult = new EasyuiResult<>();
		map.put("startIndex", (page-1)*rows);
		map.put("endIndex",rows);
		List<Map<String, Object>> data = this.orderDao.getOrderFenByManager(map);
		for (Map<String, Object> datas : data) {
			Long price = Long.valueOf(datas.get("price").toString());
			Long total_price = Long.valueOf(datas.get("total_price").toString());
			Long puhui_fen = Long.valueOf(datas.get("puhui_fen").toString());
			Long direct_hunter_fen = Long.valueOf(datas.get("direct_hunter_fen").toString());
			/*Long recommend_hunter_fen = Long.valueOf((datas.get("recommend_hunter_fen")==null?"0":datas.get("recommend_hunter_fen")).toString());
			Long industry_fen = Long.valueOf((datas.get("industry_fen")==null?"0":datas.get("industry_fen")).toString());*/
			Integer num = Integer.valueOf(datas.get("num").toString());
			Long product_price = price*num;
			Integer pay_type = Integer.valueOf(datas.get("pay_type").toString());
			String payType = "";
			if(pay_type.intValue() == 0){
				payType = "预付订金";
			}
			if(pay_type.intValue() == 1){
				payType = "全额付款";
			}
			Long terrace_fen = total_price-direct_hunter_fen-puhui_fen;
			datas.put("terrace_fen", terrace_fen);
			datas.put("product_price", product_price);
			datas.put("payType", payType);
			datas.put("puhui", "普惠平台");
			datas.put("lietou", "批发商平台");
		}
		easyuiResult.setT(data);
		easyuiResult.setTotal(Long.valueOf(data.size()));
		return easyuiResult;
	}

	@Override
	public EasyuiResult getOrderFenByIndustryAssociation(Integer page, Integer rows, Map<String, Object> map) {
		EasyuiResult easyuiResult = new EasyuiResult<>();
		map.put("startIndex", (page-1)*rows);
		map.put("endIndex",rows);
		List<Map<String, Object>> data = this.orderDao.getOrderFenByIndustryAssociation(map);
		easyuiResult.setT(data);
		easyuiResult.setTotal(Long.valueOf(data.size()));
		return easyuiResult;
	}
	@Override
	public IndustryAssociation getIndustryAssociationByPhone(String userName) {
		return this.industryAssociationService.getIndustryAssociationByPhone(userName);
	}
	
}
