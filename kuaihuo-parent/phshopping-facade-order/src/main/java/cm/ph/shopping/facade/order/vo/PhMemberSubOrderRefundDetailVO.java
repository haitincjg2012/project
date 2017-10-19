package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：子订单申请退款的详情(审核过后才能查看审核详情)
 * @作者： 张霞
 * @创建时间： 15:17 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public class PhMemberSubOrderRefundDetailVO implements Serializable {
    /**
     * 退款说明
     */
    private String appliReason;
    /**
     * 退款状态
     */
    private byte appliStatus;
    /**
     * 审核不通过的原因
     */
    private String rejectedReason;
    /**
     *审核不通过的联系人
     */
    private String telPhone;

    public String getAppliReason() {
        return appliReason;
    }

    public void setAppliReason(String appliReason) {
        this.appliReason = appliReason;
    }

    public byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(byte appliStatus) {
        this.appliStatus = appliStatus;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}
