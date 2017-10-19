package com.alqsoft.rpc.mobile.impl;

import java.util.Map;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.alqsoft.rpc.mobile.RpcZhiFuService;
import com.alqsoft.service.payservice.PayService;


@Controller
public class RpcZhiFuServiceImpl implements RpcZhiFuService{

	@Autowired
	private PayService payService;
	
	/**
	 * 统一支付请求（微信和支付宝）
	 */
	@Override
	public Result sendCodeUnifiedOrder(String orderids, Integer type, String ip) {
		// TODO Auto-generated method stub
		return payService.sendCodeUnifiedOrder(orderids, type, ip);
	}

	/**
	 * 
	 */
	@Override
	public String AlipayNotify(Map<String, String> params) {
		return payService.AlipayNotify(params);
	}

}
