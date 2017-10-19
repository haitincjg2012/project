package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单详情页面vo
 * @作者： 张霞
 * @创建时间： 11:12 2017/6/12
 * @Copyright @2017 by zhangxia
 */
public class PhMemberOrderUnlineDetailVO implements Serializable {
    private static final long serialVersionUID = -1201640270604922038L;
    /** 订单金额 */
    private Double orderMoney;
    /** 服务费用 订单金额的 */
    private Double serviceCost;
    /** 状态(0=待付款；1=付款中；2=交易完成；3=交易取消（商户取消）)(原值：1支付完成，2退货) */
    private Integer status;
    /** 支付方式 支付方式0 =现金支付 , 1=积分支付, 2=支付宝支付, 3=微信支付(1 现金  2积分 3扫码支付)*/
    private Integer payType;
    /** 订单编号 */
    private String orderNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 取消时间
     */
    private Date cancelTime;
    /**
     * 完成时间
     */
    private Date doneTime;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户联系电话
     */
    private String merchantPhone;
    /**
     * 会员手机号
     */
    private String memberPhone;
    /**
     * 会员IC卡号(会员卡外码)
     */
    private String outerCode;

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode;
    }
}
