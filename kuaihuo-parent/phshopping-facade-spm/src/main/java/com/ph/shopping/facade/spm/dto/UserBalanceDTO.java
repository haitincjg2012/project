package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：余额记录主表DTO
 *
 * @author：liuy
 *
 * @createTime：2017年3月30日
 *
 * @Copyright @2017 by liuy
 */
public class UserBalanceDTO implements Serializable {
    private static final long serialVersionUID = -3819037031911040223L;

    /**
     * 当前页码
     */
    private Integer pageIndex;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * id
     */
    private Long id;

    /**
     * 商户余额
     */
    private BigDecimal balance;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账户id(商户 供应商 代理商)
     */
    private Long manageId;

    /**
     * 类型    人员类型   商户1  代理2
     */
    private Integer type;

    /**
     * 银行卡号
     */
    private String cardNum;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 开户人姓名
     */
    private String ownName;

    /**
     * 所属银行id
     */
    private Integer bankId;

    /**
     * 银行所绑定的手机号
     */
    private String bindPhone;

    /**
     * 充值金额
     */
    private BigDecimal chargeMoney;

    /**
     * 提现金额
     */
    private BigDecimal drawcashMoney;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 支付金额
     */
    private String amount;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 当天剩余提现额
     */
    private BigDecimal drawcashMoneyToday;

    /**
     * 所有待审核提现总额
     */
    private BigDecimal drawcashStatusPendingTotal;

    /**
     * 可申请提现的余额
     */
    private BigDecimal canApplyDrawcashMoney;

    /**
     * 用户名称/代理商或者商户名称（用于余额记录页面展示名称）
     */
    private String userName;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getManageId() {
        return manageId;
    }

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public BigDecimal getDrawcashMoney() {
        return drawcashMoney;
    }

    public void setDrawcashMoney(BigDecimal drawcashMoney) {
        this.drawcashMoney = drawcashMoney;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getDrawcashMoneyToday() {
        return drawcashMoneyToday;
    }

    public void setDrawcashMoneyToday(BigDecimal drawcashMoneyToday) {
        this.drawcashMoneyToday = drawcashMoneyToday;
    }

    public BigDecimal getDrawcashStatusPendingTotal() {
        return drawcashStatusPendingTotal;
    }

    public void setDrawcashStatusPendingTotal(BigDecimal drawcashStatusPendingTotal) {
        this.drawcashStatusPendingTotal = drawcashStatusPendingTotal;
    }

    public BigDecimal getCanApplyDrawcashMoney() {
        return canApplyDrawcashMoney;
    }

    public void setCanApplyDrawcashMoney(BigDecimal canApplyDrawcashMoney) {
        this.canApplyDrawcashMoney = canApplyDrawcashMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
