package com.ph.shopping.facade.pay.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应链退款流水记录
 *
 * @author 郑朋
 * @create 2017/6/7
 **/
@Table(name = "ph_purchase_refund_trade")
public class PurchaseRefundTrade implements Serializable {

    private static final long serialVersionUID = 6168544857324346450L;

    @Id
    private Long id;

    /**
     * 子订单退款申请id
     */
    @Column(name = "refundAppliId")
    private Long refundAppliId;

    /**
     * 退款金额
     */
    @Column(name = "money")
    private BigDecimal money;


    /**
     * 支付方式： 0-余额支付 1-第三方支付
     */
    @Column(name = "payment")
    private Byte payment;

    /**
     * 退款时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 支付流水号
     */
    @Column(name = "payNo")
    private String payNo;

    /**
     * 操作人
     */
    @Column(name = "createrId")
    private Long createrId;


    /**
     * 支付状态
     */
    @Column(name = "payStatus")
    private String  payStatus;

    /**
     * 收款人
     */
    @Column(name = "payeeName")
    private String  payeeName;

    /**
     * 收款人银行卡号
     */
    @Column(name = "payeeBankNo")
    private String  payeeBankNo;

    /**
     * 收款人id
     */
    @Column(name = "payeeId")
    private Long  payeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefundAppliId() {
        return refundAppliId;
    }

    public void setRefundAppliId(Long refundAppliId) {
        this.refundAppliId = refundAppliId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeBankNo() {
        return payeeBankNo;
    }

    public void setPayeeBankNo(String payeeBankNo) {
        this.payeeBankNo = payeeBankNo;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }
}
