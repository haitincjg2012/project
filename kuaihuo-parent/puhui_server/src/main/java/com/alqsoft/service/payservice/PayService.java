package com.alqsoft.service.payservice;

import java.util.Map;

import org.alqframework.result.Result;

public interface PayService {
	/**
	 * 支付统一请求
	 * @param orderids
	 * @param type
	 * @param ip
	 * @return
	 */
	public Result sendCodeUnifiedOrder(String orderids, Integer type, String ip);
	
	/**
	 * 支付宝回调
	 * @param params
	 */
	public String AlipayNotify(Map<String, String> params);

}
