package com.alqsoft.utils.weixin.wxpay.config;


/**
 * 
 * @Title: WeiXinPayConfig.java
 * @Description: 微信支付配置类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年2月4日 下午3:33:25
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class WeiXinPayConfig {
	/**
	 * 服务号相关信息
	 */
	 public final static String APPID = "wx63828c5b836a20c0";//服务号的应用号
	 public final static String APP_SECRECT = "762b26a1cdf264d1e1d6137afa2bbc0d";//服务号的应用密码
	 public final static String TOKEN = "zhouxunweixin";//服务号的配置token
	 public final static String MCH_ID = "1228022802";//商户号
	 public final static String API_KEY = "tigofood2015020409570ZORASUNTIGO";//API密钥(商户密钥)
	 public final static String SIGN_TYPE = "MD5";//签名加密方式
//	 public final static String PARRNER_KEY="303047";//商户平台登录密码
//	 public final static String CERT_PATH = "D:/apiclient_cert.p12";//微信支付证书存放路径地址
	 
	 //----订单微信支付定义 开始
	 // 微信支付统一接口的回调控制器（正式要改成：地址指向主工程---回调）
	 public final static String ORDER_NOTIFY_URL = "http://www.tigofood.com:8070/main/view/wxorderpay/wxpay-notify";
	//微信支付成功支付后跳转的地址（支付成功页面）
	 public final static String ORDER_SUCCESS_URL = "http://www.tigofood.com/weixin/after/wxpay/paySuccess";
	 //oauth2授权时回调控制器
	 public final static String ORDER_REDIRECT_URI = "http://www.tigofood.com/weixin/weixin/after/wxpay/turnPay?showwxpaytitle=1";//showwxpaytitle=1 显示微信安全标题
	 //----订单微信支付定义 结束
	 
	 
	 //微信支付充值 统一接口的回调控制器（正式要改成：地址指向主工程---回调）
	 public final static String RECHARGE_NOTIFY_URL = "http://www.tigofood.com:8070/main/view/wxpay/recharge-success";
	//微信支付充值 成功支付后跳转的地址（支付成功页面）
	 public final static String RECHARGE_SUCCESS_URL = "http://www.tigofood.com/weixin/after/recharge/pay-success";
	 //oauth2授权时回调控制器
	 public final static String RECHARGE_REDIRECT_URI = "http://www.tigofood.com/weixin/after/recharge/turnRecharge?showwxpaytitle=1";//showwxpaytitle=1 显示微信安全标题
	 
	 public final static String RECHARGE_PAY_URI = "weixin/after/recharge/recharge";
	/**
	 * 微信基础接口地址
	 */
	 //获取token接口(GET)
	 public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	 //oauth2授权接口(GET) --获取openId
	 public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APP_SECRECT+"&grant_type=authorization_code&code=";
	 //刷新access_token接口（GET）
	/* public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 菜单创建接口（POST）
	 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET）
	 public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";*/
	/**
	 * 微信支付接口地址
	 */
	//微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder?showwxpaytitle=1";
	//微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
}
