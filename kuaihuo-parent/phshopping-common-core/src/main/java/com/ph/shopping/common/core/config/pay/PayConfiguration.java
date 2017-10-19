package com.ph.shopping.common.core.config.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @项目：phshopping-api-pay
 * @描述：初始化支付配置环境
 * @作者： 张霞
 * @创建时间： 14:37 2017/3/25
 * @Copyright @2017 by zhangxia
 */
@Configuration
@PropertySource(value = "classpath:pay.properties")
public class PayConfiguration {
    @Value("${eco_pay}")
    private String ecoPay;

    @Value("${ali_pay}")
    private String aliPay;

    @Value("${cash_notify_url}")
    private String cashNOTIFYURL;

    @Value("${jsb_union_url}")
    private String jsbUnionUrl;

    /**
     * 北京通用支付 by wang
     */
    @Value("${bj_com_pay}")
    private String bjcompay;

    /**
     * pay工程提现
     */
    @Value("${pay.score.cash}")
    private String cash;
    
    public String getEcoPay() {
        return ecoPay;
    }

    public void setEcoPay(String ecoPay) {
        this.ecoPay = ecoPay;
    }

    public String getAliPay() {
        return aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }

    public String getCashNOTIFYURL() {
        return cashNOTIFYURL;
    }

    public void setCashNOTIFYURL(String cashNOTIFYURL) {
        this.cashNOTIFYURL = cashNOTIFYURL;
    }

    public String getJsbUnionUrl() {
        return jsbUnionUrl;
    }

    public void setJsbUnionUrl(String jsbUnionUrl) {
        this.jsbUnionUrl = jsbUnionUrl;
    }

	public String getBjcompay() {
		return bjcompay;
	}

	public void setBjcompay(String bjcompay) {
		this.bjcompay = bjcompay;
	}

    public String getCash() {
        return cash;
    }
}
