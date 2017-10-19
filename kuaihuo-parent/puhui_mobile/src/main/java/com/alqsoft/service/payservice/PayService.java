package com.alqsoft.service.payservice;

import org.alqframework.result.Result;

/**
 * 
 * 支付
 * @author Administrator
 *
 */
public interface PayService {
	
	/**
	 * 订单支付请求
	 * @param orderids
	 * @param type		0：微信，1：支付宝
	 * @param ip
	 * @return
	 */
	public Result sendCodeUnifiedOrder(String orderids,Integer type,String ip);

}
