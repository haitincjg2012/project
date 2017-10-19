package com.ph.shopping.facade.pay.config;

/**
 * @项目：phshopping-parent
 * @描述：支付宝参数
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang */
public class AlipayConfig {

    // 商户appid
    public static final String APP_ID = "2017051807279480";
    // 私钥 pkcs8格式的
    public static final String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCW82nt++1yt9soX/qubqlDX8ywW2Q56E0fQs8BsCnAPvE/DaX50e8g+j2IWPCagQOxVOaRWmiBEKefTqks9xO2cxRSNDtEU3EsfDZL5tnWZRy8zv41KPgTKxzz/TwKKYuK2HQXnaJtU0knPnCHLB7ZSCKPpbI6dpsdK6N9jJsYQSZ/dccWtXNLWspQP46NQ16csCsiYBYk6GgK+jVadPEnSB8vWgFyO8yuoD2v2aS3DsdWNSSbrGhPrGKN0+x3cevo78iFDnoHwrSR8phKWlM6OsTMghZLvA62SQmncYwruNQbrtEKw8dyq9uNIW7hILy5+TZhAc2SDUj/zCukTO3ZAgMBAAECggEABA7zUCSv05/B0qoR8VKkVl1jAXLTlQL739VJ/3+e/74xekKBrd/JEBfqQ5owjdbeJIxlzo6htKaf+xaAUecqroMVjsg8t6+OoWWNiCjivnLlfOYLy54YXVuRYbXzQUqwp8D+Dgx863eLxfRVziyPf8QCud34lr3NmdEsihmXqeU753pFY3HX5HUJxJPrdRu8aCvNgyfSb8nPpnKDCNYt5vczQv/e4hksFWcO3bQKdQ+33Wzjsms6/Bcj5sDQdGld3iX7/FDxSmvYKG0ZW2+maqKXOaSNWc7ne32ZmRGJSPImY2a4tl319HBEJ5KuvRnhkrA9k0NvFmAS1LmB5WzOAQKBgQDYLLJINdfNGsi8m0RJ9x10x6keK+bsBQWZtwaF/C0L8VUILaniFEdtifGjorDk81M4b8JwuudqbwzwI+dbTrkh/M3TP4hSWWW46GU+QaCFB28t8Z4s7wa7TtGxmtcGzP6RcFDAUwU5aRW4OFTm7RG+hOfMiGDIkfhWBptTfY9gIQKBgQCywpxupbMHx9OxEW5VAAk3biHUc4TY0a8qHQJKDQhxGnmYSQGAbMQb76F8l6IwDveSzVlCXLZCQxxlpM7Aosp5NkBzDG+MBfP/+1ASLu+Vfn1xoIQqsKYHNxKlPv/htD1ogoZwc1xK2w/N7KZrgg/ETKHK+LyS1J3tViB7Ujm2uQKBgQCZbU+ztJNPfT0Pr9uN65e6dKLxSROCXCYXbTFyEwMdwNErFs3GKcJwWkFodGW8eX6NRbTpMZ5hiiPSpIjl4z++gaDL2AG3AMATts10nDuDbz1XjG9JUPSkKFLoDQ+kQgZDz0Vg2wuQLac+sqx/oa7AW6/xGLIoaIGq/NFtNmZ2QQKBgH7eskmxXnPqNetY1LaNFP17M5Vl+2Yqw6ge+I333ALx6FTVk7RC/ZhaJYGY2OpE5R8SkQQRauVyXgghxP9hSlRm1a0RqWV2oCsQgU62cyHlqUBozQTDZMKAKsKPCOp2kpLG+IV8yobyHLBpAeARagFG5hl5GdUYA5H7a4g63m05AoGBANOi50jY+GsFCQNdsXtyqhbe7Mg8SETcu4DCzttBsKY4PD6gf0VdfscxaUiwcuZZ9u4tnVnqMy0STis8yw7nnldzZjj6qXEOMEkldDZnydv8JLWUQUoYVIUpJZa+hJ3GtI3PFqKDfnnI9rOHVfBnb+0PjGrhiCSt07PV0krfZpTF";
    // 线上服务器异步通知页面路径
    //public static final String ASYNC_NOTIFY_URL = "https://pay.phds315.com/alipay/callback/asyncCallback";
    //public static final String ASYNC_NOTIFY_URL = "http://api-pay.kuaihuo315.com/alipay/callback/asyncCallback";
    //public static final String ASYNC_NOTIFY_URL = "http://www.yst315.com/alipay/callback/asyncCallback";
    public static final String ASYNC_NOTIFY_URL = "http://123.207.173.18:80/alipay/callback/asyncCallback";
    // 外网测试服务器异步通知页面路径
    //public static final String ASYNC_NOTIFY_URL = "http://123.206.8.92:8080/alipay/callback/asyncCallback";
    // 页面跳转同步通知页面路径 
//    public static final String SYNC_NOTIFY_url = "http://pay.phds315.com/alipay/sync/notifyPage";
    public static final String SYNC_NOTIFY_url = "http://123.207.173.18:8080/alipay/sync/notifyPage";
    // 请求网关地址 
    public static final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
   //public static final String GATEWAY_URL = "http://123.207.173.18:8080/gateway.do";
    // 编码
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApL3cbd5x3uNZ/iqmh8NY+gW46aIr+CZA99rE+1bJ1EgWaxF2vAzcJ3xyDas9Eh/xm7VA3neSCcLGZZ3+8xq1BHXQt9qyPo7s/8wUHnfkOfY4PhdXfdzo47/vf64qwT5cFTGkabDtDi0DQrdDuilA1gCMUcz/ojHVfSOSmpfyyGER1md2V2iYUeMpBwf7nS77BG8gHU1jjdAk2bL8psqBNX1K6+JgrIjW2ygbUHL8GYXyO6D82NNUTA68aWWhqTQzNnI1zqdoSaoWCeR7f5pJBXuFhM5pJc7cc/xGY8t1QecxxOKau+XOtYyTkWK7Hym5LquqqUm6P2lOlNJ++/mCmQIDAQAB";
    // RSA2
    public static final String SIGNTYPE = "RSA2";
    //设置订单支付超时时间
    public static final String TIMEOUT_EXPRESS="60m";
    
    //支付订单生成的ip
    public static final String OrderFromIP = "192.168.1.226";
    //pay工程加签请求路径
    public static final String PaySignUrl = "http://118.89.229.182:80/pay/alipay/AppSign";

    //pay工程请求验签
    public static final String PaySignCheckUrl="http://118.89.229.182:80/pay/alipay/AppVerify";
}
