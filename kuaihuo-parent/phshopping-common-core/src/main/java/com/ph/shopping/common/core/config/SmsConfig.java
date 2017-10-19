package com.ph.shopping.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xwolf
 * @since 1.8
 **/
@PropertySource(value ="classpath:sms.properties")
@Configuration
public class SmsConfig{

    //代理商/业务员注册
    @Value("${sms.register.agent}")
    private String agentRegister;

    //快火用户注册/密码找回提醒
    @Value("${sms.register.password}")
    private String password;

    //快火代理提醒
    @Value("${sms.register.alert}")
    private String agentAlert;


    //现金/积分支付
    @Value("${sms.order.cash}")
    private String cash;

    //快火App预定订单通知
    @Value("${sms.order.prepay}")
    private String prepay;

    //快火App订单支付通知
    @Value("${sms.order.pay}")
    private String pay;


    //商户/会员推广奖金通知
    @Value("${sms.share.spread}")
    private String spread;

    //用户支付通知掌柜
    @Value("${sms.order.member.pay}")
    private String memberPay;

    //提交订单通知掌柜
    @Value("${sms.order.member.submit}")
    private String submitOrder;

    //公共发送验证码
    @Value("${sms.common.send}")
    private String common;

    // 取消订单
    @Value("${sms.order.cancel}")
    private String cancelOrder;

    //修改密码
    @Value("${sms.member.updatePassword}")
    private String updatePassword;

    //重置密码
    @Value("${sms.member.resetPassword}")
    private String resetPassword;

    //验证验证码
    @Value("${sms.check}")
    private String check;

    public  String  getAgentRegister() {
        return agentRegister;
    }

    public String getPassword() {
        return password;
    }

    public String getAgentAlert() {
        return agentAlert;
    }

    public String getCash() {
        return cash;
    }

    public String getPrepay() {
        return prepay;
    }

    public String getPay() {
        return pay;
    }

    public String getSpread() {
        return spread;
    }

    public String getMemberPay() {
        return memberPay;
    }

    public String getSubmitOrder() {
        return submitOrder;
    }

    public String getCommon() {
        return common;
    }

    public String getCheck() {
        return check;
    }

    public String getCancelOrder() {
        return cancelOrder;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public String getResetPassword() {
        return resetPassword;
    }
}
