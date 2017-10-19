package com.ph.shopping.facade.member.dto;

import java.io.Serializable;

/**
 * 积分提现DTO
 *
 * @author 郑朋
 * @create 2017/6/14
 **/
public class MemberCashDTO implements Serializable {

    private static final long serialVersionUID = -8952111556727841280L;

    /**
     * ID
     */
    private Long id;
    /**
     * 提现积分
     */
    private Long cashScore;
    /**
     * 支付密码
     */
    private String payPwd;
    /**
     * 手机验证码
     */
    private String msgCode;

    /**
     * 提现ip
     */
    private String requestIP;

    /**
     *提现类型
     */
    private int cashType;

    public int getCashType() {
        return cashType;
    }

    public void setCashType(int cashType) {
        this.cashType = cashType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCashScore() {
        return cashScore;
    }

    public void setCashScore(Long cashScore) {
        this.cashScore = cashScore;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getRequestIP() {
        return requestIP;
    }

    public void setRequestIP(String requestIP) {
        this.requestIP = requestIP;
    }
}
