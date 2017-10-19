package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.wxpayorder.WxPayOrder;

public interface RpcWxPayOrderService {

	public void updateWxPayOrderType(WxPayOrder wxPayOrder);
	
	public Result saveRecordByPay(WxPayOrder wxPayOrder, String ip);
	
	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip);

}
