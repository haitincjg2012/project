package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 供应链新增订单DTO
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public class PurchaseOrderDTO implements Serializable {
    private static final long serialVersionUID = -4608459013660001857L;

    /** 供应商id(商品的供应商) */
    @NotBlank(message="[供应商id]不可为空")
    private Long supplerId;

    /** 联系人 */
    @NotBlank(message="[联系人]不可为空")
    private String contacts;

    /** 联系电话 */
    @NotBlank(message="[联系电话]不可为空")
    private String telPhone;

    /** 详细地址 */
    @NotBlank(message="[联系人]不可为空")
    private String address;

    /** 商品总金额 */
    @NotBlank(message="[商品总金额]不可为空")
    private BigDecimal money;

    /** 物流费用 */
    private BigDecimal freight;

    /** 订单总金额(物流费用+商品总金额) */
    @NotBlank(message="[订单总金额]不可为空")
    private BigDecimal totalCost;

    /** 状态(0待付款，1待发货，2待收货，3交易完成，4交易取消) */
    private Byte status;

    /** 进货人id */
    @NotBlank(message="[进货人id]不可为空")
    private Long purchaserId;

    /** 发货人id */
    @NotBlank(message="[发货人id]不可为空")
    private Long senderId;

    /** 收货地址(ph_manager_order_address表id) */
    private Long shippingAddressId;

    /** 进货类型 0=商户进货；1=代理商进货*/
    @NotBlank(message="[进货类型]不可为空")
    private Byte purchaseType;

    /** 商品信息 */
    private List<PurchaseProductDTO> purchaseProductList;

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

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Byte getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Byte purchaseType) {
        this.purchaseType = purchaseType;
    }

    public List<PurchaseProductDTO> getPurchaseProductList() {
        return purchaseProductList;
    }

    public void setPurchaseProductList(List<PurchaseProductDTO> purchaseProductList) {
        this.purchaseProductList = purchaseProductList;
    }
}
