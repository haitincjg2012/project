package com.alqsoft.service.wxpay;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.wxpayorder.WxPayOrder;

public interface WxPayService {

	public List<WxPayOrder> findWxPayOrderByWxOrderNum(Map<String, Object> params);

	public void updateWxPayOrderType(WxPayOrder wxPayOrder);

	public List<WxPayOrder> getPayOrderByOrderId(Long id);

	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip);

	public List<WxPayOrder> getPayBackOrderByOrderId(Long id);

}
