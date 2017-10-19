package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单申请退款列表
 * @作者： 张霞
 * @创建时间： 10:35 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public class PhMemberSubOrderRefundPageVO implements Serializable {
    private static final long serialVersionUID = -1205649290604922038L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 子订单id
     */
    private Long subOrderId;
    /**
     * 子订单编号
     */
    private String orderNo;
    /**
     * 终端来源
     */
    private Integer terminalUnit;
    /**
     * 创建时间/下单时间
     */
    private String createTime;
    /**
     * 收货人
     */
    private String shippingName;
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
     * 申请状态 0=退款审核中；1=拒绝退款；2=退款中；3=退款完成
     */
    private Integer appliStatus;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Integer appliStatus) {
        this.appliStatus = appliStatus;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }
}
