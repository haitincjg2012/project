package cm.ph.shopping.facade.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 子订单分页列表详情VO
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
public class PurchaseSubOrderPageVO implements Serializable {

    private static final long serialVersionUID = -7311291159898882364L;

    /** 表流水 */
    private Long id;

    /** 订单号 */
    private String orderNo;

    /** 发货商id */
    private Long senderId;

    /** 商品总金额 */
    private BigDecimal money;

    /** 物流费用 */
    private BigDecimal freight;

    /** 订单总金额(物流费用+商品总金额) */
    private BigDecimal totalCost;

    /** 状态(0待付款，1待发货，2待收货，3交易完成，4交易取消) */
    private Byte status;

    /** 退款状态 0退款中 1退款完成 2退款取消*/
    private Byte refundStatus;

    /** 下单时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /** 进货人id */
    private Long purchaserId;

    /** 支付方式： 0-余额支付 1-第三方支付 */
    private Byte payment;

    /**供应商id*/
    private Long supplerId;

    /** 进货类型 0=商户进货；1=代理商进货*/
    private Byte purchaseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }

    public Long getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(Long supplerId) {
        this.supplerId = supplerId;
    }

    public Byte getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Byte purchaseType) {
        this.purchaseType = purchaseType;
    }
}
