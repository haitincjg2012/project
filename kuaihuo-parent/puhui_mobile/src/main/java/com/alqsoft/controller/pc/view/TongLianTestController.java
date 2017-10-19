package com.alqsoft.controller.pc.view;

import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.date.DateUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allinpay.ets.client.PaymentResult;
import com.allinpay.ets.client.StringUtil;
import com.alqsoft.utils.aipg.SecurityUtil;
import com.alqsoft.utils.aipg.TongLianUtils;
import com.alqsoft.utils.aipg.config.AllinpayConfig;

/**
 * 
 * @Title: TongLianTestController.java
 * @Description: 模拟通联支付控制器
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月18日 下午8:32:11
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@Controller
@RequestMapping("pc/view/tonglian")
public class TongLianTestController {
	private static Logger logger = LoggerFactory.getLogger(TongLianTestController.class);
	
	/**
	 * 
	* @Title: tonglianPay 
	* @Description: 模拟请求参数 并跳转到支付页面
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("paySub")
	public String tonglianPay(HttpServletRequest request){
		
		int inputCharset=AllinpayConfig.inputCharset;//字符集   可为空  默认填1，固定选择值：1、2、3；1代表UTF-8；2代表GBK；3代表GB2312
		String pickupUrl=AllinpayConfig.pickupUrl;//支付成功跳转地址  可空（建议不要空）
		String receiveUrl=AllinpayConfig.receiveUrl;//后台回调地址  ----可空（建议不要空）
		String version=AllinpayConfig.version;//网关接收支付请求接口版本  ---不可空  
		int language=AllinpayConfig.language;//网关页面显示语言种类  可空  固定值：1；1代表简体中文、2代表繁体中文、3代表英文
		int signType=AllinpayConfig.signType;//签名类型  ---不可空  默认填1，固定选择值：0、1；0表示客户用MD5验签，1表示客户用证书验签
		String merchantId=AllinpayConfig.merchantId;//商户id  ---不可空  
		int orderCurrency=AllinpayConfig.orderCurrency;//订单金额币种类型  可空 默认填0-人民币	0和156代表人民币、840代表美元、344代表港币等
		Integer orderExpireDatetime=AllinpayConfig.orderExpireDatetime;//订单过期时间  单位：分   可空
		String orderDatetime=DateUtils.dateFormat(new Date(), "yyyyMMddHHmmss");//商户订单提交时间  ---不可空   日期格式：yyyyMMddHHmmss
		String orderNo="20140823210129890817769";//商户订单号   ---不可空
		int payType=AllinpayConfig.payType;//支付方式  ---不可空  0代表组合支付方式
		Long orderAmount=100L;//商户订单金额   单位：分  ---不可空
		String key=AllinpayConfig.md5Key;//签名字符串  ---不可空
		
		//######  
		String payerName="czb";//付款人姓名 可空
		String productName="都教授T靴同款";//商品名称  可空 
		int productNum=1;//商品数量  可空
		String productDesc="新款的，很不错哦";//商品描述  可空
		String ext1="test1";//扩展字段1
		String ext2="test2";//扩展字段2
		Long productPrice=100L;//商品价格 可空
		
		
		StringBuffer signSrc=new StringBuffer();//签名串原串
		signSrc.append("inputCharset="+inputCharset);
		signSrc.append("&pickupUrl="+pickupUrl);
		signSrc.append("&receiveUrl="+receiveUrl);
		signSrc.append("&version="+version);
		signSrc.append("&language="+language);
		signSrc.append("&signType="+signType);
		signSrc.append("&merchantId="+merchantId);
		
		signSrc.append("&orderNo="+orderNo);
		signSrc.append("&orderAmount="+orderAmount);
		signSrc.append("&orderCurrency="+orderCurrency);
		signSrc.append("&orderDatetime="+orderDatetime);
		signSrc.append("&orderExpireDatetime="+orderExpireDatetime);
//		signSrc.append("&productName="+productName);
//		signSrc.append("&productPrice="+productPrice);
//		signSrc.append("&productNum="+productNum);
//		signSrc.append("&productDesc="+productDesc);
		signSrc.append("&ext1="+ext1);
//		signSrc.append("&ext2="+ext2);
		
		signSrc.append("&payType="+payType);
		
		//获取签名字符串
		String signMsg=TongLianUtils.getSignMsg(signSrc.toString(), key);
		
		request.setAttribute("inputCharset", inputCharset);
		request.setAttribute("pickupUrl", pickupUrl);
		request.setAttribute("receiveUrl", receiveUrl);
		request.setAttribute("version", version);
		request.setAttribute("language", language);
		request.setAttribute("signType", signType);
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("payerName", payerName);
		request.setAttribute("orderNo", orderNo);
		request.setAttribute("orderAmount", orderAmount);
		request.setAttribute("orderCurrency", orderCurrency);
		request.setAttribute("orderDatetime", orderDatetime);
		request.setAttribute("orderExpireDatetime", orderExpireDatetime);
		request.setAttribute("productName", productName);
		request.setAttribute("productPrice", productPrice);
		request.setAttribute("productNum", productNum);
		request.setAttribute("productDesc", productDesc);
		request.setAttribute("ext1", ext1);
//		request.setAttribute("ext2", ext2);
		request.setAttribute("payType", payType);
		request.setAttribute("strSignMsg", signMsg);
		
		request.setAttribute("strSrcMsg", signSrc.toString());
		
		
		return "pc/tonglian";
	}
	
	
	/**
	 * 
	* @Title: merchantOrderQuery 
	* @Description: 单笔订单查询接口 测试
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("merchantQuery")
	public String merchantOrderQuery(HttpServletRequest request){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		
		String serverUrl=AllinpayConfig.serverHost;
		String key=AllinpayConfig.md5Key;
		String merchantId=AllinpayConfig.merchantId;
		String version="v1.5";
		int signType=AllinpayConfig.signType;
		String orderNo="20140825174618153723523";
		String orderDatetime="20140825174618";
		String queryDatetime=format.format(new Date());
		
		StringBuffer bufSignSrc=new StringBuffer();
		StringUtil.appendSignPara(bufSignSrc, "merchantId",merchantId);
		StringUtil.appendSignPara(bufSignSrc, "version", version);
		StringUtil.appendSignPara(bufSignSrc, "signType", String.valueOf(signType));
		StringUtil.appendSignPara(bufSignSrc, "orderNo", orderNo);
		StringUtil.appendSignPara(bufSignSrc, "orderDatetime",orderDatetime);
		StringUtil.appendSignPara(bufSignSrc, "queryDatetime",queryDatetime);
		StringUtil.appendLastSignPara(bufSignSrc, "key", key);
		String signSrc=bufSignSrc.toString();
		
		String sign= SecurityUtil.MD5Encode(bufSignSrc.toString());
		
		request.setAttribute("merchantId", merchantId);
		request.setAttribute("version", version);
		request.setAttribute("signType", signType);
		request.setAttribute("orderNo", orderNo);
		request.setAttribute("orderDatetime", orderDatetime);
		request.setAttribute("queryDatetime", queryDatetime);
		request.setAttribute("serverUrl", serverUrl);
		request.setAttribute("signSrc", signSrc);
		request.setAttribute("sign", sign);
		
		return "pc/merchantOrderQuery";
		
	}
	
	
	
	/**
	 * 
	* @Title: getPayResult 
	* @Description: 模拟 回调控制
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("aipgResult")
	@ResponseBody
	public String getPayResult(HttpServletRequest request){
		
		String serverUrl=AllinpayConfig.serverHost;
		String key=AllinpayConfig.md5Key;
		String merchantId=request.getParameter("merchantId");	
		String version=request.getParameter("version");
		String signType=request.getParameter("signType");
		String orderNo=request.getParameter("orderNo");
		String orderDatetime=request.getParameter("orderDatetime");
		String queryDatetime=request.getParameter("queryDatetime");
		String signMsg=request.getParameter("signMsg");
		
		logger.info("aipgResult===========:"+signMsg);
	
		// 提交查询请求
		boolean isSuccess = false;
		String resultMsg = "";
		Map<String, String> result = new HashMap<String, String>();
		try{
			String listenUrl=serverUrl;
			HttpClient httpclient=new HttpClient();
			PostMethod postmethod=new PostMethod(listenUrl);
			NameValuePair[] date = { new NameValuePair("merchantId",merchantId),
					new NameValuePair("version",version),
					new NameValuePair("signType",signType),
					new NameValuePair("orderNo",orderNo),
					new NameValuePair("orderDatetime",orderDatetime),
					new NameValuePair("queryDatetime",queryDatetime),
					new NameValuePair("signMsg",signMsg)};
			postmethod.setRequestBody(date);
			int responseCode=httpclient.executeMethod(postmethod);
			logger.info("responseCode="+responseCode);		
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				String strResponse = postmethod.getResponseBodyAsString();
				
				// 解析查询返回结果
				strResponse = URLDecoder.decode(strResponse);
			//	Map<String, String> result = new HashMap<String, String>();
				String[] parameters = strResponse.split("&");
				for (int i = 0; i < parameters.length; i++) {
					String msg = parameters[i];
					int index = msg.indexOf('=');
					if (index > 0) {
						String name = msg.substring(0, index);
						String value = msg.substring(index + 1);
						result.put(name, value);
					}
				}
		
				// 查询结果会以Server方式通知商户(同支付返回)；
				// 若无法取得Server通知结果，可以通过解析查询返回结果，更新订单状态(参考如下).
				if (null != result.get("ERRORCODE")) {
					// 未查询到订单
					logger.info("ERRORCODE=" + result.get("ERRORCODE"));
					logger.info("ERRORMSG=" + result.get("ERRORMSG"));
					resultMsg = result.get("ERRORMSG");
		
				} else {
					// 查询到订单
					String payResult = result.get("payResult");
					if (payResult.equals("1")) {
						logger.info("订单付款成功！");
		
						// 支付成功，验证签名
						PaymentResult paymentResult = new PaymentResult();
						paymentResult.setMerchantId(result.get("merchantId"));
						paymentResult.setVersion(result.get("version"));
						paymentResult.setLanguage(result.get("language"));
						paymentResult.setSignType(result.get("signType"));
						paymentResult.setPayType(result.get("payType"));
						paymentResult.setIssuerId(result.get("issuerId"));
						paymentResult.setPaymentOrderId(result
								.get("paymentOrderId"));
						paymentResult.setOrderNo(result.get("orderNo"));
						paymentResult.setOrderDatetime(result
								.get("orderDatetime"));
						paymentResult.setOrderAmount(result.get("orderAmount"));
						paymentResult.setPayAmount(result.get("payAmount"));
						paymentResult.setPayDatetime(result.get("payDatetime"));
						paymentResult.setExt1(result.get("ext1"));
						paymentResult.setExt2(result.get("ext2"));
						paymentResult.setPayResult(result.get("payResult"));
						paymentResult.setErrorCode(result.get("errorCode"));
						paymentResult.setReturnDatetime(result
								.get("returnDatetime"));
						paymentResult.setKey(key);
						paymentResult.setSignMsg(result.get("signMsg"));
						paymentResult.setCertPath("TLCert-test.cer");
						
						boolean verifyResult = paymentResult.verify();
		
						if (verifyResult) {
							logger.info("验签成功！商户更新订单状态。");
							resultMsg = "订单支付成功，验签成功！商户更新订单状态。";
							isSuccess = true;
						} else {
							logger.info("验签失败！");
							resultMsg = "订单支付成功，验签失败！";
						}
		
					} else {
							logger.info("订单尚未付款！");
							resultMsg = "订单尚未付款！";
					}
				}
		
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return resultMsg;
	}

}
