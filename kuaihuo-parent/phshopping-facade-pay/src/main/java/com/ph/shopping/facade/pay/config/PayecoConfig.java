package com.ph.shopping.facade.pay.config;

/**
 * @项目：phshopping-parent
 * @描述：易联支付配置
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang
 */
public class PayecoConfig {
    /**
     * 易联支付参数
     * */
    public static final String PROC_CODE = "0200";
    public static final String PROCESS_CODE = "190011";//UPOP+网银 接入方式
    public static final String VERSION = "2.0.0";//版本号
    public static final String MERCHANT_NO = "502020002803";//商户号
    public static final String MERCHANT_PWD = "9F1EEA196DBC47E4";//商户密钥
//    public static final String SYNC_ADDRESS = "https://pay.phds315.com/api/pay/payecoReturnSync";//同步回调地址
//    public static final String ASYNC_ADDRESS = "https://pay.phds315.com/api/pay/payecoReturnAsync";//异步回调地址
    public static final String PAYECO_URL = "https://ebank.payeco.com/services/DnaPayB2C";//易联支付地址
    public static final String CUR_CODE = "CNY";//CNY-人民币;HKD-港币；USD-美元
    public static final String REFERENCE = "Reference";
    public static final String MERCHANT_NAME="快火";//商户名称

    /**测试环境参数*/
//    public static final String MERCHANT_PWD = "123456";//测试账号
//    public static final String MERCHANT_NO = "302020000114";//测试账号
//    public static final String PAYECO_URL = "https://test.payeco.com:9443/DnaOnlineTest/servlet/DnaPayB2C";
    public static final String SYNC_ADDRESS = "http://pay.phds315.com/payeco/callback/sync";//用于接受反馈信息的地址
    public static final String ASYNC_ADDRESS = "http://pay.phds315.com/payeco/callback/async";//用于接受反馈信息的地址

}
