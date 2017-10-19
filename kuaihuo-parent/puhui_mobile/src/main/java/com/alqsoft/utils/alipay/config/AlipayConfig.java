package com.alqsoft.utils.alipay.config;

/* *
 *类名：支付宝  配置工具类 AlipayConfig    web端
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2015-03-11
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	
	// 合作身份者ID（PID），以2088开头由16位纯数字组成的字符串
	public static String partner = "2088711774842454";
	// 商户的私钥
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKP9A1pFdPs87hguy82WkNa1dkl1KVQoB3gPOf4mVafSO0aEFfKgJxUjDVeijqRKsatvMWIEaNtRXB9C+poyyFXGowXFO7xmkC+9xUxbkPACiSKdqStHacOlEpT/FD6AsrKpnPN51jaeVHFliyuoPPHFpx0HNGeXEx/hQTmV8HAzAgMBAAECgYAPYtzeqmeobvYT9MgQeREL5Ci+Ca0gBUV2IU+apfQhc9s16Yy5oAcqt4g38buadJuo7xzeCnXgJgkTsdAbiao8I50SUZ8TcG3VoeESVliGFbHm0OVouM/1wmF0YUV1DwAu0QnIIppjdmdR7SUYRglo9NWvrSJtCGeWKlT3pCPoKQJBAM3RLdoh8V44LOfKe5TwnDKYyUCeAOu26kpicFU7YwQ822KcQxWT11IW9lPmpjkk9wLF2rUw62lGvOD768GdIs8CQQDL+PB+ltr/oZLbB5Z4/KanA6/igkryjCX8bCq2LVuyMryKNviRHht8lbp/eBTlIsNBKsLjzrAPdsQXlF2lN0VdAkAlHtESQoTx5VfUBmT7m9nQFlZV3Sl1WewvtklkVe2p2gAHbP98aMmw+Is83qCkNdKHIYAspcIPnr56JXWD2f9pAkEAqF7bloNMT4u+P9MpoMFdh6wBegZ3e3O4v39CWpNih9xdyfHKiW/C3Lpz8ljbVrG1X4u+yHGK35j4EUbFbmYLeQJAP+vEQeBxn3xr7OTcCu//ofAw6T0afVI/2EntlJ8j++rYKcaN76hGX/HqcJWZQx/jGV6sPNZOYCDrZ3duAivlYA==";
	
	//支付宝提供给商户的服务接入网关URL(新)
	public static String ALIPAY_GATEWAY_NEW="https://mapi.alipay.com/gateway.do?";
	
	//支付宝消息验证地址
    public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
    
	//web 及时到账 服务名称
	public static String service="create_direct_pay_by_user";
	//支付类型：1：商品购买
	public static String  payment_type="1";
	
	//卖家支付宝一用户号	以2088开头的纯16位数字。
	public static String seller_id="2088711774842454";
	//卖家支付宝账号，格式为邮箱或手机号
	public static String seller_email="info@tigofood.com";
	//###测试环境
	//服务器异步通知页面路径
	public static String notify_url="http://117.29.170.130:8070/main/view/pc/unionpayoralipay/web_alipay_notify";
	//页面跳转同步通知页面路径
	public static String return_url="http://117.29.170.130/pc/view/order/aipgToWaitReceive";
	//####正式环境
	//public static String notify_url="http://www.tigofood.com:8070/main/view/pc/unionpayoralipay/web_alipay_notify";
	//public static String return_url="http://www.tigofood.com/pc/view/order/aipgToWaitReceive";
	
	//商品展示网址
	public static String show_url="http://www.tigofood.com";

	
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\logs/web_alipay_log";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "UTF-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
