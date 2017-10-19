package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 修改订单物流费用DTO
 *
 * @author 郑朋
 * @create 2017/5/17
 **/
public class UpdatePurOrderFreDTO extends BaseValidate implements Serializable{

    private static final long serialVersionUID = 614003754229713097L;

    /**
     * 修改人id
     */
    @NotNull(message = "[修改人id]不可为空")
    private Long updaterId;

    /**
     * 订单id
     */
    @NotNull(message = "[订单id]不可为空")
    private Long orderId;

    /**
     * 物流费用
     */
    @NotNull(message = "[物流费用]不可为空")
    private BigDecimal freight;

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

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }
}
