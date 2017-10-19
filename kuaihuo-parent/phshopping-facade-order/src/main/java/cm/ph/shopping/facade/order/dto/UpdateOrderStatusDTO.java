package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 确认收货DTO
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public class UpdateOrderStatusDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -5859871857632779872L;

    /**
     * 当前订单的状态
     */
    @NotNull(message = "订单当前状态不能为空")
    private Byte currentOrderStatus;

    /**
     * 修改人id
     */
    @NotNull(message = "修改人id不能为空")
    private Long updaterId;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    public Byte getCurrentOrderStatus() {
        return currentOrderStatus;
    }

    public void setCurrentOrderStatus(Byte currentOrderStatus) {
        this.currentOrderStatus = currentOrderStatus;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
