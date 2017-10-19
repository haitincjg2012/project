package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单详情返回页面数据
 * @作者： 张霞
 * @创建时间： 11:12 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class PhMemberSubOrderOnlineDetailVO implements Serializable {
    private static final long serialVersionUID = -1201656270604922038L;

    /**
     * 子订单编号
     */
    private String orderNo;
    /**
     * 会员账号、会员联系电话（收货人电话）
     */
    private String memberTelPhone;
    /**
     * 会员名称、会员联系人(收货人名称)
     */
    private String memberName;
    /**
     * 会员收货完整地址
     */
    private String shippingAddress;
    /**
     * 店铺的名称
     */
    private String merchantName;
    /**
     * 商户企业名称
     */
    private String companyName;
    /**
     * 商户人名字（提货点联系人）
     */
    private String merchantPersonName;
    /**
     * 提货点联系电话
     */
    private String merchantTelPhone;
    /**
     * 供应商企业名称
     */
    private String supplierName;
    /**
     * 供应商联系电话
     */
    private String supplierTelPhone;
    /**
     * 供应商人名字
     */
    private String supplierPersonName;
    /**
     * 发货地址(即供应商提供的仓库地址)
     */
    private String sendAddressName;
    /**
     * 创建时间/下单时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 完成时间(收货时间)
     */
    private Date doneTime;
    /**
     * 取消时间（订单取消完成时间）
     */
    private Date cancelTime;
    /**
     * 商品总金额
     */
    private Double productMoney;
    /**
     * 物流总费用
     */
    private Double logisticsMoney;
    /**
     * 订单总金额
     */
    private Double orderMoney;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 收货方式：0=自提方式；1=送货到家。
     */
    private Integer shippingType;
    /**
     * 商户收货地址(当会员选择到商户的店铺处自提时，商户的地址)
     */
    private String takeGoodsAddress;
    /**
     * 订单中商品的数量
     */
    private List<PhMemberSubOrderOnlineProductVO> phMemberSubOrderOnlineProductVOS = new ArrayList<>();

    public String getMemberTelPhone() {
        return memberTelPhone;
    }

    public void setMemberTelPhone(String memberTelPhone) {
        this.memberTelPhone = memberTelPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMerchantTelPhone() {
        return merchantTelPhone;
    }

    public void setMerchantTelPhone(String merchantTelPhone) {
        this.merchantTelPhone = merchantTelPhone;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierTelPhone() {
        return supplierTelPhone;
    }

    public void setSupplierTelPhone(String supplierTelPhone) {
        this.supplierTelPhone = supplierTelPhone;
    }

    public String getSendAddressName() {
        return sendAddressName;
    }

    public void setSendAddressName(String sendAddressName) {
        this.sendAddressName = sendAddressName;
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

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Double getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(Double productMoney) {
        this.productMoney = productMoney;
    }

    public Double getLogisticsMoney() {
        return logisticsMoney;
    }

    public void setLogisticsMoney(Double logisticsMoney) {
        this.logisticsMoney = logisticsMoney;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public List<PhMemberSubOrderOnlineProductVO> getPhMemberSubOrderOnlineProductVOS() {
        return phMemberSubOrderOnlineProductVOS;
    }

    public void setPhMemberSubOrderOnlineProductVOS(List<PhMemberSubOrderOnlineProductVO> phMemberSubOrderOnlineProductVOS) {
        this.phMemberSubOrderOnlineProductVOS = phMemberSubOrderOnlineProductVOS;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getMerchantPersonName() {
        return merchantPersonName;
    }

    public void setMerchantPersonName(String merchantPersonName) {
        this.merchantPersonName = merchantPersonName;
    }

    public String getSupplierPersonName() {
        return supplierPersonName;
    }

    public void setSupplierPersonName(String supplierPersonName) {
        this.supplierPersonName = supplierPersonName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getShippingType() {
        return shippingType;
    }

    public void setShippingType(Integer shippingType) {
        this.shippingType = shippingType;
    }

    public String getTakeGoodsAddress() {
        return takeGoodsAddress;
    }

    public void setTakeGoodsAddress(String takeGoodsAddress) {
        this.takeGoodsAddress = takeGoodsAddress;
    }
}
