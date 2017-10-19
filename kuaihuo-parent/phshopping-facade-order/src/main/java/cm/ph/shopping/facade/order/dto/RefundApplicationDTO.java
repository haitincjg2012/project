package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 退款申请DTO
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public class RefundApplicationDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = 7873820783916577554L;

    /**
     * 子订单id
     */
    @NotNull(message = "子订单id不能为空")
    private Long subOrderId;

    /**
     * 申请人
     */
    @NotNull(message = "申请人id不能为空")
    private Long createrId;

    /**
     * 申请原因
     */
    @NotNull(message = "申请原因不能为空")
    @Length(max=255,message="[申请原因]最大长度为255个字符")
    private String appliReason;

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
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
}
