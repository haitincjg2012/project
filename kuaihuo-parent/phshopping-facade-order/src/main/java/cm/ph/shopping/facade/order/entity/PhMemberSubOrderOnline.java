package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员线上子订单表
 * @作者： 张霞
 * @创建时间： 13:36 2017/5/24
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_sub_order_online")
public class PhMemberSubOrderOnline implements Serializable{
    private static final long serialVersionUID = -2682197982321131649L;

    /** 主键id
     *
     */
    @Id
    private Long id;
    /** 订单号
     *
     */
    @Column(name = "orderNo")
    private String orderNo;
    /** 终端来源:0=平台；1=APP商城
     *
     */
    @Column(name = "terminalUnit")
    private Byte terminalUnit;
    /** 订单总价格(加上物流费用)
     *
     */
    @Column(name = "orderMoney")
    private Double orderMoney;
    /** 商品总价格
     *
     */
    @Column(name = "productMoney")
    private Double productMoney;
    /** 子订单物流总费用
     *
     */
    @Column(name = "logisticsMoney")
    private Double logisticsMoney;
    /**
     * 商品结算价(子订单中所有规格商品结算总价之和)
     */
    @Column(name = "settleMoney")
    private Double settleMoney;
    /** 付款方式：0=微信支付；1=积分支付；2=支付宝支付；3=银行卡支付
     *
     */
    @Column(name = "payType")
    private Byte payType;
    /** 订单状态：0=待付款；1=待发货；2=待收货；3=交易完成；4=交易取消
     *
     */
    @Column(name = "status")
    private Byte status;
    /** 创建时间(下单时间)
     *
     */
    @Column(name = "createTime")
    private Date createTime;
    /** 创建人(同称：会员id)
     *
     */
    @Column(name = "createrId")
    private Long createrId;
    /** 更新时间
     *
     */
    @Column(name = "updateTime")
    private Date updateTime;
    /** 更新人id
     *
     */
    @Column(name = "updaterId")
    private Long updaterId;
    /** 更新人类型(角色：线上订单只有会员、定时器和供应商操作):0=会员；1=供应商；2=定时器。
     *
     */
    @Column(name = "updaterRoleType")
    private Byte updaterRoleType;
    /** 物流公司id
     *
     */
    @Column(name = "logisticsId")
    private Long logisticsId;
    /** 物流(物流公司的名称)
     *
     */
    @Column(name = "logisticsName")
    private String logisticsName;
    /** 物流编号
     *
     */
    @Column(name = "logisticsNo")
    private String logisticsNo;
    /** 发货人id(供应商id)
     *
     */
    @Column(name = "supplierId")
    private Long supplierId;
    /** 发货地址id
     *
     */
    @Column(name = "sendAddressId")
    private Long sendAddressId;
    /** 发货地址(即供应商提供的仓库地址)
     *
     */
    @Column(name = "sendAddressName")
    private String sendAddressName;
    /** 发货时间
     *
     */
    @Column(name = "sendTime")
    private Date sendTime;
    /** 完成时间(收货时间、订单取消完成时间)
     *
     */
    @Column(name = "doneTime")
    private Date doneTime;
    /**
     * 收货地址省id
     */
    @Column(name = "shippingProvinceId")
    private Long shippingProvinceId;
    /**
     * 收货地址市id
     */
    @Column(name = "shippingCityId")
    private Long shippingCityId;
    /**
     * 收货地址区县id
     */
    @Column(name = "shippingCountyId")
    private Long shippingCountyId;
    /**
     * 收货地址社区id
     */
    @Column(name = "shippingTownId")
    private Long shippingTownId;
    /** 会员收货详细地址
     *
     */
    @Column(name = "shippingAddressDetail")
    private String shippingAddressDetail;
    /** 会员收货完整地址(会员自己管理添加的地址=送货到家方式；会员自选省市区后所在地的商户自己管理的仓库地址且是设置为“自提”的地址=自提方式。此地址还包含详细地址)
     *
     *
     */
    @Column(name = "shippingAddress")
    private String shippingAddress;
    /** 收货方式：0=自提方式；1=送货到家。
     *
     */
    @Column(name = "shippingType")
    private Byte shippingType;

    /**
     * 收货人名称((会员添加的收货地址的联系人名称)
     */
    @Column(name = "shippingName")
    private String shippingName;
    /**
     * 收货人电话(会员添加的收货地址的联系人电话)
     */
    @Column(name = "shippingTelphone")
    private String shippingTelphone;
    /** 商户id(选择收货地址信息时，自提方式时地址对应的商户)
     *
     */
    @Column(name = "merchantId")
    private Long merchantId;
    /**
     * 提货点商户店名称
     */
    @Column(name = "merchantName")
    private String merchantName;
    /**
     * 提货点地址(当会员选择到商户的店铺处自提时，商户设置的提货点地址（注意：不是商户门店地址）)
     */
    @Column(name = "takeGoodsAddress")
    private String takeGoodsAddress;

    /**
     * 提货点联系人
     */
    @Column(name = "takeGoodsName")
    private String takeGoodsName;
    /**
     * 提货点联系电话
     */
    @Column(name = "takeGoodsTelphone")
    private String takeGoodsTelphone;
    /** 是否已返积分;0=未返；1=已返
     * 默认为0
     */
    @Column(name = "isBackScore")
    private Byte isBackScore;
    /** 是否结算:0=未结算；1=结算
     * 默认为0
     */
    @Column(name = "isSettle")
    private Byte isSettle;
    /** 分润时间
     *
     */
    @Column(name = "profitTime")
    private Date profitTime;
    /** 区域id（分润用）
     *
     */
    @Column(name = "positionId")
    private Long positionId;
    /** 主订单表id
     *
     */
    @Column(name = "mainOrderId")
    private Long mainOrderId;
    /** 取消时间
     *
     */
    @Column(name = "cancelTime")
    private Date cancelTime;
    /** 取消订单原因
     *
     */
    @Column(name = "cancelReason")
    private String cancelReason;
    /** 支付时间
     *
     */
    @Column(name = "payTime")
    private Date payTime;
    /**
     * 是否分润(0=未分润；1=已分润)
     */
    @Column(name = "isProfit")
    private Byte isProfit;
    /**
     * 结算时间
     */
    @Column(name = "settleTime")
    private Date settleTime;

    /**
     * 收货自提省ID
     */
    @Column(name = "takeProvinceId")
    private Long takeProvinceId;
    /**
     * 收货自提省名称
     */
    @Column(name = "takeProvinceName")
    private String  takeProvinceName;
    /**
     * 收货自提市/区ID
     */
    @Column(name = "takeCityId")
    private Long takeCityId;
    /**
     * 收货自提市/区名称
     */
    @Column(name = "takeCityName")
    private String  takeCityName;
    /**
     * 收货自提区/县ID
     */
    @Column(name = "takeCountyId")
    private Long takeCountyId;
    /**
     * 收货自提区/县名称
     */
    @Column(name = "takeCountyName")
    private String  takeCountyName;
    /**
     * 收货自提社区ID
     */
    @Column(name = "takeTownId")
    private Long takeTownId;
    /**
     * 收货自提社区名称
     */
    @Column(name = "takeTownName")
    private String  takeTownName;

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

    public Byte getTerminalUnit() {
        return terminalUnit;
    }

    public void setTerminalUnit(Byte terminalUnit) {
        this.terminalUnit = terminalUnit;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
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

    public Double getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(Double settleMoney) {
        this.settleMoney = settleMoney;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Byte getUpdaterRoleType() {
        return updaterRoleType;
    }

    public void setUpdaterRoleType(Byte updaterRoleType) {
        this.updaterRoleType = updaterRoleType;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSendAddressId() {
        return sendAddressId;
    }

    public void setSendAddressId(Long sendAddressId) {
        this.sendAddressId = sendAddressId;
    }

    public String getSendAddressName() {
        return sendAddressName;
    }

    public void setSendAddressName(String sendAddressName) {
        this.sendAddressName = sendAddressName;
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

    public Long getShippingProvinceId() {
        return shippingProvinceId;
    }

    public void setShippingProvinceId(Long shippingProvinceId) {
        this.shippingProvinceId = shippingProvinceId;
    }

    public Long getShippingCityId() {
        return shippingCityId;
    }

    public void setShippingCityId(Long shippingCityId) {
        this.shippingCityId = shippingCityId;
    }

    public Long getShippingCountyId() {
        return shippingCountyId;
    }

    public void setShippingCountyId(Long shippingCountyId) {
        this.shippingCountyId = shippingCountyId;
    }

    public Long getShippingTownId() {
        return shippingTownId;
    }

    public void setShippingTownId(Long shippingTownId) {
        this.shippingTownId = shippingTownId;
    }

    public String getShippingAddressDetail() {
        return shippingAddressDetail;
    }

    public void setShippingAddressDetail(String shippingAddressDetail) {
        this.shippingAddressDetail = shippingAddressDetail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Byte getShippingType() {
        return shippingType;
    }

    public void setShippingType(Byte shippingType) {
        this.shippingType = shippingType;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getTakeGoodsAddress() {
        return takeGoodsAddress;
    }

    public void setTakeGoodsAddress(String takeGoodsAddress) {
        this.takeGoodsAddress = takeGoodsAddress;
    }

    public Byte getIsBackScore() {
        return isBackScore;
    }

    public void setIsBackScore(Byte isBackScore) {
        this.isBackScore = isBackScore;
    }

    public Byte getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Byte isSettle) {
        this.isSettle = isSettle;
    }

    public Date getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(Date profitTime) {
        this.profitTime = profitTime;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(Long mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Long getTakeProvinceId() {
        return takeProvinceId;
    }

    public void setTakeProvinceId(Long takeProvinceId) {
        this.takeProvinceId = takeProvinceId;
    }

    public String getTakeProvinceName() {
        return takeProvinceName;
    }

    public void setTakeProvinceName(String takeProvinceName) {
        this.takeProvinceName = takeProvinceName;
    }

    public Long getTakeCityId() {
        return takeCityId;
    }

    public void setTakeCityId(Long takeCityId) {
        this.takeCityId = takeCityId;
    }

    public String getTakeCityName() {
        return takeCityName;
    }

    public void setTakeCityName(String takeCityName) {
        this.takeCityName = takeCityName;
    }

    public Long getTakeCountyId() {
        return takeCountyId;
    }

    public void setTakeCountyId(Long takeCountyId) {
        this.takeCountyId = takeCountyId;
    }

    public String getTakeCountyName() {
        return takeCountyName;
    }

    public void setTakeCountyName(String takeCountyName) {
        this.takeCountyName = takeCountyName;
    }

    public Long getTakeTownId() {
        return takeTownId;
    }

    public void setTakeTownId(Long takeTownId) {
        this.takeTownId = takeTownId;
    }

    public String getTakeTownName() {
        return takeTownName;
    }

    public void setTakeTownName(String takeTownName) {
        this.takeTownName = takeTownName;
    }

    public String getShippingTelphone() {
        return shippingTelphone;
    }

    public void setShippingTelphone(String shippingTelphone) {
        this.shippingTelphone = shippingTelphone;
    }

    public String getTakeGoodsName() {
        return takeGoodsName;
    }

    public void setTakeGoodsName(String takeGoodsName) {
        this.takeGoodsName = takeGoodsName;
    }

    public String getTakeGoodsTelphone() {
        return takeGoodsTelphone;
    }

    public void setTakeGoodsTelphone(String takeGoodsTelphone) {
        this.takeGoodsTelphone = takeGoodsTelphone;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
