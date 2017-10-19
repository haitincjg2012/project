package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单退款查询DTO(子订单)
 * @作者： 张霞
 * @创建时间： 10:38 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public class QueryMemberSubOrderRefundDTO implements Serializable {
    private static final long serialVersionUID = 6950132567015596983L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 开始时间——申请退款创建时间的范围查询
     */
    private String startTime;
    /**
     * 结束时间——申请退款创建时间的范围查询
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
     * 申请状态 0=退款审核中；1=拒绝退款；2=退款中；3=退款完成
     */
    private Byte appliStatus;
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
     * 线上子订单id
     */
    private Long subOrderId;

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


    public Byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Byte appliStatus) {
        this.appliStatus = appliStatus;
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

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }
}
