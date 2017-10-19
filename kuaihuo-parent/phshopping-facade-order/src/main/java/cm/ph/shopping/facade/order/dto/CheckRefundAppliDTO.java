package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 审核退款申请DTO
 *
 * @author 郑朋
 * @create 2017/5/17
 **/
public class CheckRefundAppliDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -4030647413025054432L;

    /**
     * 子订单id
     */
    @NotNull(message = "[子订单id]不可为空")
    private Long subOrderId;

    /**
     * 驳回人
     */
    @NotNull(message = "[驳回人id]不可为空")
    private Long updaterId;

    /**
     * 驳回原因
     */
    private String rejectedReason;
    /**
     * 驳回人联系电话
     */
    private String telPhone;

    /**
     * 申请状态 {@link cm.ph.shopping.facade.order.constant.RefundStatusEnum}
     */
    @NotNull(message = "[申请状态]不可为空")
    private Byte appliStatus;

    /**
     * 申请id
     */
    private Long id;

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
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

    public Byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(Byte appliStatus) {
        this.appliStatus = appliStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
