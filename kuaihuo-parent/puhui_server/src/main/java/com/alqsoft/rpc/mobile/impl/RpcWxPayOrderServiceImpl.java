package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.rpc.mobile.RpcWxPayOrderService;
import com.alqsoft.service.wxpayorder.WxPayOrderService;

@Service
@Transactional(readOnly=true)
public class RpcWxPayOrderServiceImpl implements RpcWxPayOrderService{

	@Autowired
	private WxPayOrderService wxPayOrderService;
	
	@Override
	@Transactional
	public void updateWxPayOrderType(WxPayOrder wxPayOrder) {
		this.wxPayOrderService.updateWxPayOrderType(wxPayOrder);
	}

	@Override
	@Transactional
	public Result saveRecordByPay(WxPayOrder wxPayOrder, String ip) {
		return this.wxPayOrderService.saveRecordByPay(wxPayOrder, ip);
	}
	
	@Override
	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip) {
		return this.wxPayOrderService.sendCodeUnifiedOrder(wxOrderNum, money, ip);
	}
}
