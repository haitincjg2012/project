package com.ph.order.api.controller.qo;

import java.io.Serializable;

/**
 * 查询qo
 *
 * @author 郑朋
 * @create 2017/5/25
 **/
public class PurchaseOrderQO implements Serializable {
    private static final long serialVersionUID = -2055548688536921808L;

    /**
     * 下单时间-起始值
     */
    private String createTimeStr;
    /**
     * 下单时间-结束值
     */
    private String createTimeEnd;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 进货人id
     */
    private Long purchaserId;
    /**
     * 发货人id
     */
    private Long senderId;
    /**
     * 供应商id
     */
    private Long supplerId;

    /**
     * 订单状态 0待付款，1待发货，2待收货，3交易完成，4交易取消
     */
    private Byte status;

    /**
     * 退款状态 0退款中 1退款完成 2退款取消
     */
    private Byte refundStatus;

    /**
     * 支付方式 0-余额支付 1-银行卡支付
     */
    private Byte payment;

    /**
     * 查询类型：1-只查询退款状态不为空的订单
     */
    private Byte queryType;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(Long supplerId) {
        this.supplerId = supplerId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Byte refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }

    public Byte getQueryType() {
        return queryType;
    }

    public void setQueryType(Byte queryType) {
        this.queryType = queryType;
    }
}
