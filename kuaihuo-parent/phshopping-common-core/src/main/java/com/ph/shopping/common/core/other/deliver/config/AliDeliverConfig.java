package com.ph.shopping.common.core.other.deliver.config;

/**
 * 快递100参数类
 *
 * @author 郑朋
 * @create 2017/6/21
 **/
public class AliDeliverConfig {

    /**
     *  AppKey
     */
    public static final String APP_KEY = "24492775";

    /**
     * AppSecret
     */
    public static final String APP_SECRET = "940ac0b35ea6b866aa0e5f192f45c14e";

    /**
     * AppCode
     */
    public static final String APP_CODE = "f5987c33513e45c792d3a70d0719e5a6";

    /**
     * ali物流查询地址
     */
    public static final String DELIVER_URL = "http://ali-deliver.showapi.com/showapi_expInfo";


    /**
     * 如不知道快递公司名,可以使用"auto"代替
     */
    public static final String AUTO = "auto";

    /**
     * 物流缓存时间
     */
    public static final Long TIMEOUT = 2L;


}
