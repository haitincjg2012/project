package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 奖励类型DTO
 * @author xwolf
 * @since 1.8
 **/
public class RewardUnlineOrderDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -6136680031117361674L;

    @NotNull(message = "订单ID不能为空")
    private Long id; //订单ID

    @NotNull(message = "返还类型不能为空")
    private String type; // 0：天天返 1:刮刮乐

    @NotNull(message = "用户ID不能为空")
    private Long memberId;

    private BigDecimal money;//红包金额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
