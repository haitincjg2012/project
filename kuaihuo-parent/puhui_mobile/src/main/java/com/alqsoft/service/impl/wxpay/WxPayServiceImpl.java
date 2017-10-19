package com.alqsoft.service.impl.wxpay;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.wxpayorder.WxPayOrderDao;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.rpc.mobile.RpcWxPayOrderService;
import com.alqsoft.service.wxpay.WxPayService;

@Service
@Transactional(readOnly=true)
public class WxPayServiceImpl implements WxPayService {

	@Autowired
	private WxPayOrderDao wxPayOrderDao;
	@Autowired
	private RpcWxPayOrderService rpcWxPayOrderService;
	
	@Override
	public List<WxPayOrder> findWxPayOrderByWxOrderNum(Map<String, Object> params) {
		return this.wxPayOrderDao.findWxPayOrderByWxOrderNum(params);
	}

	@Override
	public void updateWxPayOrderType(WxPayOrder wxPayOrder) {
		this.rpcWxPayOrderService.updateWxPayOrderType(wxPayOrder);
	}

	@Override
	public List<WxPayOrder> getPayOrderByOrderId(Long id) {
		return this.wxPayOrderDao.getPayOrderByOrderId(id);
	}

	@Override
	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip) {
		return this.rpcWxPayOrderService.sendCodeUnifiedOrder(wxOrderNum, money, ip);
	}

	@Override
	public List<WxPayOrder> getPayBackOrderByOrderId(Long id) {
		return this.wxPayOrderDao.getPayBackOrderByOrderId(id);
	}
	
}
