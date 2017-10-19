package com.alqsoft.service.impl.payservice;

import com.alqsoft.dao.wxpayorder.WxPayOrderDao;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.rpc.pay.RpcPayService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.payservice.PayService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.wxpayorder.WxPayOrderService;
import com.alqsoft.utils.CodeConstExt;
import com.alqsoft.utils.UtilDate;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly=true)
public class PayServiceImpl implements PayService{
	@Autowired
	private OrderService orderService;
	@Autowired
	private WxPayOrderService wxPayOrderService;
	@Autowired
	private InitParamPc initParam;
	@Autowired
	private RpcPayService rpcPayService;
	@Autowired
	private WxPayOrderDao wxPayOrderDao;
	@Autowired
	private ProductSpecificationService productSpecificationService;

	private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
	@Override
	@Transactional
	public Result sendCodeUnifiedOrder(String orderids, Integer type, String ip) {
		logger.info("=========支付的参数orderNos:"+orderids+"&type:"+type+"&ip:"+ip);
		try {
			String thirdPayOrderNum=UtilDate.getOrderNum()+UtilDate.getThree();
			if(type==0){
				thirdPayOrderNum="PSWX_"+thirdPayOrderNum;//微信
			}else{
				thirdPayOrderNum="PSAL_"+thirdPayOrderNum;//支付宝
			}
			String orderid[] = orderids.split(",");

			Long summoney=0L;//一共需要支付的钱
			for(int i =0;i<orderid.length;i++){
				Long money=0L;//每个订单生成交易表里的钱
				List<Order> list = this.orderService.getOrderListByOrderNum(orderid[i]);
				/*Order order = orderService.get(Long.valueOf(orderid[i]));*/
				if(list.get(0).getStatus()!=0 && list.get(0).getStatus()!=3){
					logger.error("订单支付状态异常,orderNo:"+list.get(0).getId());
					return ResultUtils.returnError("订单状态异常");
				}
				for(Order order : list){
					if(order.getStatus() == 0) {
						if (wxPayOrderDao.checkOrderPayBySub(order.getOrderNo(), 1).size() > 1) {
							return ResultUtils.returnError("订单"+order.getOrderNo()+"已支付");
						}
						money += order.getSubscriptionMoney();
					}
					if(order.getStatus() == 3) {
						if (wxPayOrderDao.checkOrderPayBySub(order.getOrderNo(), 2).size() > 1) {
							return ResultUtils.returnError("订单"+order.getOrderNo()+"已支付");
						}
						money += order.getNegotiatePrice();
					}
					ProductSpecification productSpecification = this.productSpecificationService.get(order.getProductSpecification().getId());//商品规格
					if(productSpecification.getProduct().getStatus().intValue() == 0){
						return ResultUtils.returnError("商品:"+(order.getProductName().length()>=10?order.getProductName().substring(0, 10):order.getProductName())+"已下架");
					}
				}
				//============================生成第三方交易记录===================================
				WxPayOrder wxPayOrder = new WxPayOrder();
				wxPayOrder.setWxOrderNum(thirdPayOrderNum);
				if(list.get(0).getStatus() == 0){ // 0 支付定金
					wxPayOrder.setType(1);
				}
				if(list.get(0).getStatus() == 3){ // 3 支付尾款
					wxPayOrder.setType(2);
				}
				summoney += money.longValue();
				wxPayOrder.setOrderNum(orderid[i]);
				wxPayOrder.setMoney(money);
				wxPayOrder.setStatus(0);//申请中
				wxPayOrder.setPayType(type);
				wxPayOrder.setOrderInfo(null);
				wxPayOrderService.saveAndModify(wxPayOrder);
			}

			if(type==0){//微信统一下单
				return wxPayOrderService.sendCodeUnifiedOrder(thirdPayOrderNum, summoney, ip);
			}else{
				String notify_url=initParam.getProperties().getProperty("zhb_back_url");//回调地址

				Map<String,String> params = new HashMap<String,String>();
				params.put("out_trade_no", thirdPayOrderNum);
				params.put("subject", "商品支付");
				params.put("body", "商品支付");
				params.put("total_fee", DoubleUtils.div(summoney, 100, 2)+"");
				params.put("notify_url",notify_url);
				params.put("service", "mobile.securitypay.pay");
				params.put("_input_charset", "UTF-8");
				params.put("return_url", "http://m.alipay.com");
				params.put("payment_type", "1");
				params.put("seller_id", "kuaihuo315@yst315.com");
				params.put("it_b_pay", "1m");

				Map data = this.rpcPayService.AlPaySign(params, "PAY_SYSTEM_FROM_PHPF");
				params.put("sign", data.get("rsaSign").toString());
				return ResultUtils.returnSuccess("成功",data.get("rsaSign").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("===========支付异常："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("支付异常");
		}
	}
	/**
	 * 支付宝回调处理
	 */
	@Override
	@Transactional
	public String AlipayNotify(Map<String, String> params) {
		logger.info("===================server端回调处理的参数map："+params);
		if(params==null){
			return "failure";
		}
		Result result = rpcPayService.verifyAlPaySingNotify(params, CodeConstExt.PAY_SYSTEM_FROM_PHPF);//调用pay工程验签
		logger.info("支付宝调用pay结果 result:{}",result);
		if (result.getCode()==0) {//与pay工程通信成功

			logger.info("支付宝回调调用pay工程验签方法,与pay工程通信成功");

			if(result.getMsg().equals("SUCCESS")){//验签成功
				String tradestatus =  params.get("trade_status");
				if(tradestatus==null){
					return "failure";
				}
				String out_trade_no= params.get("out_trade_no");//商户订单号
				String transaction_id=params.get("trade_no");//流水号
				String price=params.get("price");
				Long notifyprice =new BigDecimal(price).multiply(new BigDecimal(100)).longValue();
				List<WxPayOrder> list = wxPayOrderDao.getListByWxOrderNum(params.get("out_trade_no"));
				if(list.size() == 0){
					logger.error("订单号：" + out_trade_no + "不存在");
					return "success";
				}
				Long sumpaymoney=0L;//应该支付的价钱
				for(WxPayOrder w :list){
					if(w.getStatus()!=0){
						logger.info("订单号：" +out_trade_no+ "重复回调，该订单已处理完成");
						return "success";
					}
					sumpaymoney+=w.getMoney();
				}
				logger.info("============订单应该支付的金额为："+sumpaymoney+"==订单回调的金额为："+notifyprice+"==（单位分）");
				WxPayOrder wxPayOrder = new WxPayOrder();
				if(notifyprice.longValue()!=sumpaymoney.longValue()){
					logger.error("订单号：" + out_trade_no + "支付金额异常，应支付金额和回调金额不一致");
					for(WxPayOrder w :list){
						w.setDescription("支付金额异常，应支付金额和回调金额不一致");
						w.setStatus(2);
						w.setWxSerialNumber(transaction_id);
						wxPayOrderService.saveAndModify(w);
					}
					return "success";
				}

				if("TRADE_SUCCESS".equals(tradestatus.toString())||"TRADE_FINISHED".equals(tradestatus.toString())){//交易结果支付成功
					wxPayOrder.setWxOrderNum(out_trade_no);
					wxPayOrder.setWxSerialNumber(transaction_id);
					wxPayOrder.setDescription("交易成功");
					wxPayOrder.setStatus(1);
					this.wxPayOrderService.updateWxPayOrderType(wxPayOrder);
					logger.info("订单号：" + out_trade_no + "支付成功");

				}else if("WAIT_BUYER_PAY".equals(tradestatus.toString())){
					return "failure";

				}else{//交易结果支付失败
					wxPayOrder.setWxOrderNum(out_trade_no);
					wxPayOrder.setWxSerialNumber(transaction_id);
					wxPayOrder.setStatus(2);
					wxPayOrder.setDescription("交易失败");
					this.wxPayOrderService.updateWxPayOrderType(wxPayOrder);
				}
				return "success";
			}else{//验签失败
				logger.info("支付宝回调调用pay工程验签方法,验签失败.返回结果msg:"+result.getMsg());
				return "failure";
			}

		}else{//与pay工程通信失败
			logger.info("调用pay工程验签方法,与pay工程通信失败。返回结果code:"+result.getCode()+",msg:"+result.getMsg());
		}
		return "failure";
	}
}
