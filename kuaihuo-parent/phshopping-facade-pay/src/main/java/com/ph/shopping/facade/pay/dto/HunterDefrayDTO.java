package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 批发商提现DTO
 *
 * @author 郑朋
 * @create 2017/7/11
 **/
public class HunterDefrayDTO extends BaseValidate implements Serializable{


    private static final long serialVersionUID = 6299071130626903353L;

    /**
     * ID
     */
    @NotNull
    private Long id;
    /**
     * 提现积分
     */
    @NotNull
    private Long cashScore;
    /**
     * 支付密码
     */
    @NotBlank
    private String payPwd;
    /**
     * 手机验证码
     */
    private String msgCode;

    /**
     * 提现ip
     */
    @NotBlank
    private String requestIP;

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
