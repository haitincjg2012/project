package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单列表返回页面交互数据
 * @作者： 张霞
 * @创建时间： 16:53 2017/5/31
 * @Copyright @2017 by zhangxia
 */
public class PhMemberSubOrderOnlinePageVO implements Serializable {
    private static final long serialVersionUID = -1201649290604922038L;
    private Long id;//主键
    /**
     * 子订单编号
     */
    private String orderNo;
    /**
     *  终端来源:0=平台；1=APP商城
     */
    private Integer terminalUnit;
    /**
     * 创建时间/下单时间
     */
    private String createTime;
    /**
     * 收货人(会员名称)
     */
    private String shippingName;
    /**
     * 收货人手机号(会员手机号)
     */
    private String shippingTelphone;
    /**
     * 收货地址(会员完整的收货地址)
     */
    private String shippingAddress;
    /**
     * 商户门店名称
     */
    private String merchantName;
    /**
     * 提货点联系人
     */
    private String merchantPersonName;
    /**
     * 提货点电话+座机号码
     */
    private String merchantTelphone;
    /**
     * 商户收货地址(当会员选择到商户的店铺处自提时，商户的地址)
     */
    private String takeGoodsAddress;
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
     * 付款方式：0=微信支付；1=积分支付；2=支付宝支付；3=银行卡支付
     */
    private Integer payType;
    /**
     * 订单状态：0=待付款；1=待发货；2=待收货；3=交易完成；4=交易取消
     */
    private Integer status;
    /**
     * 物流公司id
     */
    private Long logisticsId;
    /**
     * 物流(物流公司的名称)
     */
    private String logisticsName;
    /**
     *物流公司的拼音简写名称
     */
    private String logisticsNameEng;
    /**
     * 物流编号
     */
    private String logisticsNo;
    /**
     * 收货方式：0=自提方式；1=送货到家。
     */
    private Integer shippingType;

    /**
     * 供应商企业名称
     */
    private String supplierName;
    /**
     * 订单中商品的数量
     */
    private List<PhMemberSubOrderOnlineProductVO> phMemberSubOrderOnlineProductVOS = new ArrayList<>();

    /**
     * 申请退款状态
     */
    private Integer appliStatus;

    /**
     * 申请退款表id
     */
    private Long refundId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTerminalUnit() {
        return terminalUnit;
    }

    public void setTerminalUnit(Integer terminalUnit) {
        this.terminalUnit = terminalUnit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShippingType() {
        return shippingType;
    }

    public void setShippingType(Integer shippingType) {
        this.shippingType = shippingType;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getShippingTelphone() {
        return shippingTelphone;
    }

    public void setShippingTelphone(String shippingTelphone) {
        this.shippingTelphone = shippingTelphone;
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

    public String getMerchantPersonName() {
        return merchantPersonName;
    }

    public void setMerchantPersonName(String merchantPersonName) {
        this.merchantPersonName = merchantPersonName;
    }

    public String getMerchantTelphone() {
        return merchantTelphone;
    }

    public void setMerchantTelphone(String merchantTelphone) {
        this.merchantTelphone = merchantTelphone;
    }

    public String getTakeGoodsAddress() {
        return takeGoodsAddress;
    }

    public void setTakeGoodsAddress(String takeGoodsAddress) {
        this.takeGoodsAddress = takeGoodsAddress;
    }

    public List<PhMemberSubOrderOnlineProductVO> getPhMemberSubOrderOnlineProductVOS() {
        return phMemberSubOrderOnlineProductVOS;
    }

    public void setPhMemberSubOrderOnlineProductVOS(List<PhMemberSubOrderOnlineProductVO> phMemberSubOrderOnlineProductVOS) {
        this.phMemberSubOrderOnlineProductVOS = phMemberSubOrderOnlineProductVOS;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getLogisticsNameEng() {
        return logisticsNameEng;
    }

    public void setLogisticsNameEng(String logisticsNameEng) {
        this.logisticsNameEng = logisticsNameEng;
    }

    public Integer getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Integer appliStatus) {
        this.appliStatus = appliStatus;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }
}
