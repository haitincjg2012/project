package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-parent
 * @描述：提现DTO
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang
 */
public class DefrayDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -4162246679915868284L;

    @NotNull
    private String id;//提现记录主键Id
    @NotNull
    private String bathNo;//批次号
    @NotNull
    private String bankNo;//银行卡号
    @NotNull
    private String accountName;//绑定银行卡的户名
    @NotNull
    private String amount;//提现金额
    @NotNull
    private String bankName;//银行名称
    @NotNull
    private String orderNo;//商户系统的订单号
    private String accountType;//账号类型:00银行卡，01存折。默认00
    private String merchantAccountType;//账号类别：0-私人;1-公司。默认0

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBathNo() {
        return bathNo;
    }

    public void setBathNo(String bathNo) {
        this.bathNo = bathNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMerchantAccountType() {
        return merchantAccountType;
    }

    public void setMerchantAccountType(String merchantAccountType) {
        this.merchantAccountType = merchantAccountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    private DefrayDTO(){}

    public DefrayDTO(String id, String bathNo, String bankNo, String accountName, String amount, String bankName, String orderNo) {
        this.id = id;
        this.bathNo = bathNo;
        this.bankNo = bankNo;
        this.accountName = accountName;
        this.amount = amount;
        this.bankName = bankName;
        this.orderNo = orderNo;
        this.accountType = "00";
        this.merchantAccountType = "0";
    }
}
