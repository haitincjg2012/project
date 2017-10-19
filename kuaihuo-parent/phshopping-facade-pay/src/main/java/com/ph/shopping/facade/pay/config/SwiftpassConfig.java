package com.ph.shopping.facade.pay.config;

/**
 * @项目：phshopping-facade-pay
 * @描述：威富通扫码支付配置信息
 * @作者： 张霞
 * @创建时间： 14:39 2017/7/20
 * @Copyright @2017 by zhangxia
 */
public class SwiftpassConfig {

    //测试环境配置开始
    /**
     * 威富通交易密钥--测试 // 9d101c97133837e13dde2d32a5054abb
     */
    public static String key = "4cb56702b443248d86a6b4e91cb9fdea" ;

    /**
     * 威富通商户号--测试 // 7551000001
     */
    public static String mch_id = "199590179564";

    /**
     * 通知url--测试 http://123.206.8.92:8080
     */
    //public static String notify_url = "https://pay.phds315.com/swiftpasspay/callback/saync";
    public static String notify_url = "http://123.207.173.18:8080/swiftpasspay/callback/saync";
    //测试环境配置结束
    /**
     * 威富通请求url
     */
    public static String req_url = "https://pay.swiftpass.cn/pay/gateway";
    /**
     * 接口类型
     */
    public static String service = "pay.weixin.native";


}
