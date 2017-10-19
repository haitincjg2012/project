package com.alqsoft.alipay;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alqsoft.utils.alipay.config.AlipayConfig;
import com.alqsoft.utils.alipay.util.AlipaySubmit;

/**
 * @Title: AliPayTest.java
 * @Description:支付宝测试类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月11日 下午8:47:41
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public class AliPayTest {

	
	/**
	 * 
	* @Title: aliPayWebTest 
	* @Description: web 支付宝调用测试
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Test
	public void aliPayWebTest(){
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type",AlipayConfig.payment_type);//支付类型
		sParaTemp.put("notify_url", AlipayConfig.notify_url);//服务器异步通知页面路径
		sParaTemp.put("return_url", AlipayConfig.return_url);	//页面跳转同步通知页面路径
		sParaTemp.put("seller_id", AlipayConfig.seller_id);//卖家支付宝账号对应的支付宝唯一用户号
		sParaTemp.put("seller_email", AlipayConfig.seller_email);	//卖家支付宝帐户
		sParaTemp.put("out_trade_no", "20150209105459247654296");//商户订单号
		sParaTemp.put("subject", "太古粮家商品测试");//商品名称/标题
		sParaTemp.put("total_fee", String.valueOf(1));//付款金额  单位：元  （最小单位：0.01）
		sParaTemp.put("body", "测试数据");//订单描述  (可空)
		sParaTemp.put("show_url", AlipayConfig.show_url); //商品展示网址(可空)
		sParaTemp.put("anti_phishing_key", "");//防钓鱼时间戳 ，建议不要为空 (可空)  AlipaySubmit.query_timestamp()
		sParaTemp.put("exter_invoke_ip", "");//客户端的IP地址  (可空)   request.getRemoteAddr()
		
		//建立请求,返回form表单数据，并在jsp 直接js显示：document.write(subUrl);
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		System.out.println("form 表单及参数---------："+sHtmlText);
	}
}
