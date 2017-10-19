package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单查询DTO(子订单)
 * @作者： 张霞
 * @创建时间： 9:06 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class QueryMemberOrderOnlineDTO implements Serializable {
    private static final long serialVersionUID = 6950132567015596083L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 下单时间--开始时间
     */
    private String startTime;
    /**
     * 下单时间--结束时间
     */
    private String endTime;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 终端来源
     */
    private Byte terminalUnit;
    /**
     * 订单状态
     */
    private Byte status;

    /**
     * 收货地址省id——用于代理商获取线上订单
     */
    private Long shippingProvinceId;
    /**
     * 收货地址市id——用于代理商获取线上订单
     */
    private Long shippingCityId;
    /**
     * 收货地址区县id——用于代理商获取线上订单
     */
    private Long shippingCountyId;
    /**
     * 收货地址社区id——用于代理商获取线上订单
     */
    private Long shippingTownId;

    /**
     * 商户id（线上订单自提方式收货时，送达的商户地址的商户，引用的用户登录表中的id（即：userId））
     */
    private Long merchantId;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 用于表示是否查询退款订单
     */
    private boolean isRefund;
    /**
     * 是否是商城查询，用于查询更多信息
     */
    private boolean isShopping;

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isShopping() {
        return isShopping;
    }

    public void setShopping(boolean shopping) {
        isShopping = shopping;
    }
}
