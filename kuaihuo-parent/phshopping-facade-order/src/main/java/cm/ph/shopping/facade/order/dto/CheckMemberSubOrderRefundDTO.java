package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：后台审核线上订单退款申请DTO
 * @作者： 张霞
 * @创建时间： 10:23 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public class CheckMemberSubOrderRefundDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = -4031237413025054432L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 子订单id
     */
    @NotNull(message = "[子订单id]不可为空")
    private Long subOrderId;

    /**
     * 驳回人
     */
    @NotNull(message = "[审核人id]不可为空")
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
     * 审核结果
     */
    private Byte checkResultStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Byte getCheckResultStatus() {
        return checkResultStatus;
    }

    public void setCheckResultStatus(Byte checkResultStatus) {
        this.checkResultStatus = checkResultStatus;
    }
}
