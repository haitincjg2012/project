package com.ph.shopping.facade.pay.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应链支付流水记录表
 *
 * @author 郑朋
 * @create 2017/5/18
 **/
@Table(name = "ph_purchase_charge_record")
public class PurchaseChargeRecord implements Serializable {

    private static final long serialVersionUID = 4916690893539847070L;

    @Id
    private Long id;
    /**
     * 用户id
     */
    @Column(name = "userId")
    private Long userId;
    /**
     * 支付转换后的积分，支付金额*10000后的数值
     */
    @Column(name = "score")
    private Long score;
    /**
     * 交易订单号
     */
    @Column(name = "orderNo")
    private String orderNo;
    /**
     * 支付方式 0 银行
     */
    @Column(name = "chargeType")
    private byte chargeType;
    /**
     * 支付状态 ：1=支付中；2=支付成功；3=支付失败
     */
    @Column(name = "chargeStatus")
    private byte chargeStatus;
    /**
     * md5报文值
     */
    @Column(name = "md5Str")
    private String md5Str;

    /**
     * 返回报文值
     */
    @Column(name = "responseCode")
    private String responseCode;

    /**
     * 创建人
     */
    @Column(name="createrId")
    private  Long createrId;

    /**
     * 创建时间
     */
    @Column(name="createTime")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public byte getChargeType() {
        return chargeType;
    }

    public void setChargeType(byte chargeType) {
        this.chargeType = chargeType;
    }

    public byte getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(byte chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getMd5Str() {
        return md5Str;
    }

    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
