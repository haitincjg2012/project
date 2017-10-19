package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员申请线上订单退款页面内容
 * @作者： 张霞
 * @创建时间： 11:30 2017/6/5
 * @Copyright @2017 by zhangxia
 */
public class AddMemberOrderOnlineRefundDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = -4031237413025054432L;
    /**
     * 线上订单(子订单)id
     */
    @NotNull(message = "申请线上退款子订单号不能为空")
    private Long orderId;
    /**
     * 创建人id
     */
    @NotNull(message = "申请线上退款创建人不能为空")

    private Long createrId;
    /**
     * 申请原因
     */
    @NotNull(message = "申请线上退款申请原因不能为空")

    private String appliReason;
    /**
     * 创建时间
     */
    @NotNull(message = "申请线上退款创建时间不能为空")
    private Date createTime;
    /**
     * 申请退款状态
     */
    @NotNull(message = "申请线上退款状态不能为空")
    private Byte status;
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public String getAppliReason() {
        return appliReason;
    }

    public void setAppliReason(String appliReason) {
        this.appliReason = appliReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
