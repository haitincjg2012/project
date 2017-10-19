package cm.ph.shopping.facade.order.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应链子订单实体
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Table(name = "ph_purchase_sub_order")
public class PurchaseSubOrder extends BaseEntity {

    private static final long serialVersionUID = -6306367486981876335L;

    /** 订单号 */
    @Column(name="orderNo")
    private String orderNo;

    /** 主订单id */
    @Column(name="mainOrderId")
    private Long mainOrderId;

    /** 供应商id(商品的供应商) */
    @Column(name="supplerId")
    private Long supplerId;

    /** 联系人 */
    @Column(name="contacts")
    private String contacts;

    /** 联系电话 */
    @Column(name="telPhone")
    private String telPhone;

    /** 详细地址 */
    @Column(name="address")
    private String address;

    /** 商品总金额 */
    @Column(name="money")
    private BigDecimal money;

    /** 物流费用 */
    @Column(name="freight")
    private BigDecimal freight;

    /** 物流默认费用费用 */
    @Column(name="referenceFreight")
    private BigDecimal referenceFreight;

    /** 订单总金额(物流费用+商品总金额) */
    @Column(name="totalCost")
    private BigDecimal totalCost;

    /** 状态(0待付款，1待发货，2待收货，3交易完成，4交易取消) */
    @Column(name="status")
    private Byte status;

    /** 退款状态 0退款中 1退款完成 2退款取消*/
    @Column(name="refundStatus")
    private Byte refundStatus;

    /** 进货人id */
    @Column(name="purchaserId")
    private Long purchaserId;

    /** 发货人id */
    @Column(name="senderId")
    private Long senderId;

    /** 物流公司 */
    @Column(name="logisticsCompany")
    private String logisticsCompany;

    /** 物流编号 */
    @Column(name="logisticsNo")
    private String logisticsNo;

    /** 发货地址(ph_manager_order_address表id) */
    @Column(name="sendAddressId")
    private Long sendAddressId;

    /** 收货地址(ph_manager_order_address表id) */
    @Column(name="shippingAddressId")
    private Long shippingAddressId;


    /** 0:未结算 1:已结算 */
    @Column(name="isSettle")
    private Byte isSettle;


    /** 结算价 */
    @Column(name="settleMoney")
    private BigDecimal settleMoney;

    /** 取消订单者id(代理商id或者供应商id) */
    @Column(name="cancelUserId")
    private Long cancelUserId;

    /** 物流公司id */
    @Column(name="logisticsId")
    private Long logisticsId;


    @Column(name="md5")
    private String md5;

    /** 发货详细地址 */
    @Column(name="sendAddress")
    private String sendAddress;

    /** 发货联系人 */
    @Column(name="sendContacts")
    private String sendContacts;

    /** 发货人联系电话 */
    @Column(name="sendTelPhone")
    private String sendTelPhone;

    /** 进货类型 0=商户进货；1=代理商进货*/
    @Column(name="purchaseType")
    private Byte purchaseType;

    /** 支付时间*/
    @Column(name="payTime")
    private Date payTime;

    /** 发货时间*/
    @Column(name="sendTime")
    private Date sendTime;

    /** 完成时间*/
    @Column(name="doneTime")
    private Date doneTime;

    /** 订单取消时间*/
    @Column(name="cancelTime")
    private Date cancelTime;

    /** 支付方式： 0-余额支付 1-第三方支付 */
    @Column(name="payment")
    private Byte payment;

    /** 是否分润(0=未分润；1=已分润) */
    @Column(name="isProfit")
    private Byte isProfit;

    /** 结算时间 */
    @Column(name = "settleTime")
    private Date settleTime;

    /**  分润时间 */
    @Column(name = "profitTime")
    private Date profitTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(Long mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public Long getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(Long supplerId) {
        this.supplerId = supplerId;
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

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Long getSendAddressId() {
        return sendAddressId;
    }

    public void setSendAddressId(Long sendAddressId) {
        this.sendAddressId = sendAddressId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Byte getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Byte isSettle) {
        this.isSettle = isSettle;
    }

    public Long getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Long cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
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

    public Byte getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Byte purchaseType) {
        this.purchaseType = purchaseType;
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

    public Byte getPayment() {
        return payment;
    }

    public void setPayment(Byte payment) {
        this.payment = payment;
    }

    public Byte getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(Byte isProfit) {
        this.isProfit = isProfit;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Date getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(Date profitTime) {
        this.profitTime = profitTime;
    }

    public BigDecimal getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(BigDecimal settleMoney) {
        this.settleMoney = settleMoney;
    }

    public BigDecimal getReferenceFreight() {
        return referenceFreight;
    }

    public void setReferenceFreight(BigDecimal referenceFreight) {
        this.referenceFreight = referenceFreight;
    }
}
