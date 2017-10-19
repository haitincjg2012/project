package com.alqsoft.alipay;

import java.util.Map;

import org.alqframework.security.RSAUtils;
import org.junit.Test;

/**
 * 
 * @Title: AlipayTest.java
 * @Description: 支付宝测试类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月11日 下午3:51:40 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 *
 */
public class AlipayTest {

	@Test
	public void getPrivateKey() throws Exception {

		// 获取公私钥，方便支付宝使用
		Map<String, Object> maps = RSAUtils.genKeyPair();
		String pri = RSAUtils.getPrivateKey(maps);
		String pub = RSAUtils.getPublicKey(maps);
		System.out.println("RSA公钥：" + pub);
		System.out.println("RSA私钥：" + pri);

	}

	/**
	 * 
	 * @Title: getInfoMsg
	 * @Description: 获取 返回给客户端 的字符串参数
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@Test
	public void getInfoMsg() {
//		// 把请求参数打包成数组
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", AlipayConfig.service);
//		sParaTemp.put("partner", AlipayConfig.partner);
//		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("payment_type", AlipayConfig.payment_type);// 支付类型
//		sParaTemp.put("notify_url", AlipayConfig.notify_url);// 服务器异步通知页面路径
//		sParaTemp.put("seller_id", AlipayConfig.seller_id);// 卖家支付宝账号对应的支付宝唯一用户号
//		sParaTemp.put("out_trade_no", "20150209105459247654296");// 商户订单号
//		sParaTemp.put("subject", AlipayConfig.subject);// 商品名称/标题
//		sParaTemp.put("total_fee", String.valueOf(1));// 付款金额 单位：元 （最小单位：0.01）
//		sParaTemp.put("body", "app测试数据");// 订单描述 (可空)
//		
//		
//		//获取返回给客户端的字符串参数
//		String info=AlipayRequestUtils.buildRequestResultParas(sParaTemp);
//		
//		System.out.println("返回给客户端的参数结果====："+info);
//		
		
	}

}
