package com.alqsoft.rpc.mobile;

import java.util.Map;

import org.alqframework.result.Result;

public interface RpcZhiFuService {
	
	/**
	 * 统一支付请求
	 * @param orderids
	 * @param type
	 * @param ip
	 * @return
	 */
	public Result sendCodeUnifiedOrder(String orderids, Integer type, String ip);
	
	/**
	 * 支付宝回调
	 * @param params
	 * @return
	 */
	public String AlipayNotify(Map<String,String> params);
}
