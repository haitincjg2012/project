package com.ph.shopping.facade.pay.config;

/**
 * @Project: phshopping-parent
 * @Desc: 微信支付参数
 * Created by wangxueyang on 2017/9/5 9:20.
 */
public class WeChatConfig {
    //微信统一下单URL
    public static final String WeChat_ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String APPID = "wxc94befe7059e7123";//服务号的应用号
    public final static String MCH_ID = "1488438432";
    public final static String APP_SECRECT = "762b26a1cdf264d1e1d6137afa2bbc0d";//服务号的应用密码
    //微信请求pay  加签路径
    public final static String PAY_REQUEST_URL ="http://123.206.92.142:20028/SHY-pay_interface/pay/wxpay/AppSign";

    //微信验签请求地址
    public final static String PAY_CHECK_URL="http://123.206.92.142:20028/SHY-pay_interface/pay/wxpay/AppVerify";
    //微信异步通知地址
    //public final static String NOTIFY_URL = "http://www.yst315.com/weChat/payback";
    public final static String NOTIFY_URL = "http://123.207.173.18/weChat/payback";

}
