package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：子订单各种内容状态值的更新，包含：订单状态，订单是否分润，订单是否结算
 * @作者： 张霞
 * @创建时间： 15:32 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class UpdateOnlineOrderStatusDTO extends BaseValidate implements Serializable {
    private static final long serialVersionUID = -5859801857632779872L;

    /**
     * 需要更新后的状态值
     */
    @NotNull(message = "修改后的内容不能为空")
    private byte currentOrderStatus;
    /**
     * 修改人id
     */
    @NotNull(message = "修改人id不能为空")
    private Long updaterId;

    /**
     * 子订单id
     */
    @NotNull(message = "订单id不能为空")
    private Long orderId;
    /**
     * 更新人类型(角色：线上订单只有会员、定时器和供应商操作):0=会员；1=供应商；2=定时器。
     */
    @NotNull(message = "更新人类型不能为空")
    private byte roleType;
    /**
     * 更新订单内容类型
     */
    @NotNull(message = "更新订单内容类型")
    private byte updateContentType;
    /**
     * 需要更新的具体内容
     */
    private Object contentDto;


    public byte getCurrentOrderStatus() {
        return currentOrderStatus;
    }

    public void setCurrentOrderStatus(byte currentOrderStatus) {
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

    public byte getRoleType() {
        return roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }

    public byte getUpdateContentType() {
        return updateContentType;
    }

    public void setUpdateContentType(byte updateContentType) {
        this.updateContentType = updateContentType;
    }

    public Object getContentDto() {
        return contentDto;
    }

    public void setContentDto(Object contentDto) {
        this.contentDto = contentDto;
    }
}
