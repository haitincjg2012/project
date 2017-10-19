package com.alqsoft.utils.aipg.config;

/**
 * 
 * @Title: AllinpayConfig.java
 * @Description: web 通联   基础配置类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月19日 上午10:16:54
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class AllinpayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	//正式请求地址
	//public static String serverHost="https://service.allinpay.com/gateway/index.do";
	//测试请求地址
	public static String serverHost="http://ceshi.allinpay.com/gateway/index.do";
	//测试商户号
	public static String merchantId="100020091218001";
	//正式商户号
//	public static String merchantId="109135921502005";
	//#测试环境
	//支付成功跳转的页面
	public static String  pickupUrl="http://117.29.170.130/pc/view/order/aipgToWaitReceive";
	//回调地址
	public static String  receiveUrl="http://117.29.170.130:8070/main/view/alipay/aippg_receive_url";
	//正式环境
//	public static String  pickupUrl="http://www.tigofood.com/pc/view/order/aipgToWaitReceive";
//	public static String  receiveUrl="http://www.tigofood.com:8070/main/view/alipay/aippg_receive_url";

	public static int	inputCharset=1;//字符集   可为空  默认填1，固定选择值：1、2、3；1代表UTF-8；2代表GBK；3代表GB2312
	public static int	language=1;//网关页面显示语言种类  可空  固定值：1；1代表简体中文、2代表繁体中文、3代表英文
	public static String version="v1.0";//网关接收支付请求接口版本  ---不可空  
	public static int	signType=1;//签名类型  ---不可空  默认填1，固定选择值：0、1；0表示客户用MD5验签，1表示客户用证书验签
	public static int	payType=0;//支付方式  ---不可空  0代表组合支付方式
	public static int	orderCurrency=0;//订单金额币种类型  可空 默认填0-人民币	0和156代表人民币、840代表美元、344代表港币等
	public static int orderExpireDatetime=1440;////订单过期时间  单位：分   可空
	public static String md5Key="1234567890";//签名字符串  ---不可空

	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\logs/web_tonglian_log";

}
