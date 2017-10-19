package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 供应链子订单详情VO
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public class PurchaseSubOrderDetailVO implements Serializable {

    private static final long serialVersionUID = 7978814738172254836L;

    /** 表流水 */
    private Long id;

    /** 进货人id */
    private Long purchaserId;

    /** 联系人 */
    private String contacts;

    /** 联系电话 */
    private String telPhone;

    /** 详细地址 */
    private String address;

    /** 发货商id */
    private Long senderId;

    /** 发货详细地址 */
    private String sendAddress;

    /** 发货联系人 */
    private String sendContacts;

    /** 发货人联系电话 */
    private String sendTelPhone;

    /** 订单号 */
    private String orderNo;

    /** 下单时间 */
    private Date createTime;

    /** 支付时间*/
    private Date payTime;

    /** 发货时间*/
    private Date sendTime;

    /** 收货时间*/
    private Date deliveryTime;

    /** 完成时间*/
    private Date doneTime;

    /** 订单取消时间*/
    private Date cancelTime;

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

    /** 供应商id(商品的供应商) */
    private Long supplerId;

    /** 进货类型 0=商户进货；1=代理商进货*/
    private Byte purchaseType;

    /** 商品信息 */
    List<PurchaseProductVO> list;

    /** 物流默认费用费用 */
    private BigDecimal referenceFreight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendContacts() {
        return sendContacts;
    }

    public void setSendContacts(String sendContacts) {
        this.sendContacts = sendContacts;
    }

    public String getSendTelPhone() {
        return sendTelPhone;
    }

    public void setSendTelPhone(String sendTelPhone) {
        this.sendTelPhone = sendTelPhone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
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

    public Long getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(Long supplerId) {
        this.supplerId = supplerId;
    }

    public List<PurchaseProductVO> getList() {
        return list;
    }

    public void setList(List<PurchaseProductVO> list) {
        this.list = list;
    }

    public Byte getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Byte purchaseType) {
        this.purchaseType = purchaseType;
    }

    public BigDecimal getReferenceFreight() {
        return referenceFreight;
    }

    public void setReferenceFreight(BigDecimal referenceFreight) {
        this.referenceFreight = referenceFreight;
    }
}
