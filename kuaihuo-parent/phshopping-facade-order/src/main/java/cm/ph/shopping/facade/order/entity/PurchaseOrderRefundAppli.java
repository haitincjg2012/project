package cm.ph.shopping.facade.order.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 退款申请实体
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Table(name = "ph_purchase_order_refund_appli")
public class PurchaseOrderRefundAppli extends BaseEntity{

    private static final long serialVersionUID = 6680205944878144885L;

    /**
     * 子订单id
     */
    @Column(name = "subOrderId")
    private Long subOrderId;
    /**
     * 申请原因
     */
    @Column(name = "appliReason")
    private String appliReason;

    /**
     * 申请状态
     */
    @Column(name = "appliStatus")
    private Byte appliStatus;

    /**
     * 联系电话
     */
    @Column(name = "telPhone")
    private String telPhone;

    /**
     * 拒绝原因
     */
    @Column(name = "rejectedReason")
    private String rejectedReason;

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public String getAppliReason() {
        return appliReason;
    }

    public void setAppliReason(String appliReason) {
        this.appliReason = appliReason;
    }

    public Byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Byte appliStatus) {
        this.appliStatus = appliStatus;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
