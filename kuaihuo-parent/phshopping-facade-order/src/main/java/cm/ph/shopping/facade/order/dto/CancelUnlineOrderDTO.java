package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单取消订单dto
 * @作者： 张霞
 * @创建时间： 12:57 2017/6/9
 * @Copyright @2017 by zhangxia
 */
public class CancelUnlineOrderDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = 3097176085179759216L;
    /**
     * 线下订单id
     */
    @NotNull(message = "[订单id参数]不能为空")
    private Long id;
    /**
     * 取消订单申请说明
     */
    private String cancelReason;
    /**
     * 更新人id
     */
    private Long updaterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }
}
