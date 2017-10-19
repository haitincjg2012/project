package com.alqsoft.service.wxpayorder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.wxpayorder.WxPayOrder;

public interface WxPayOrderService extends BaseService<WxPayOrder> {

	public void updateWxPayOrderType(WxPayOrder wxPayOrder);

	public Result saveRecordByPay(WxPayOrder wxPayOrder, String ip);

	public List<WxPayOrder> findRecordByOrderId(Long id);

	public List<WxPayOrder> findRecordByOrderIdAndStatusSuc(Long id);

	public Long getSumPriceBySerialNum(String wxSerialNumber);

	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip);

	public Result sendRefund(Order o/*, HttpServletRequest request*/);
	public Result sendRefundForCancel(String orderNo);

}
