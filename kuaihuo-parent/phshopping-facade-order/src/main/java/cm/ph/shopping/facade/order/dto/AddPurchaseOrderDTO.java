package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 新增订单DTO
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public class AddPurchaseOrderDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = 3217176085179759216L;

    /**
     * 订单
     */
    @NotNull(message = "订单参数不能为空")
    List<PurchaseOrderDTO> purchaseOrderDTO;

    public AddPurchaseOrderDTO() {
    }

    public AddPurchaseOrderDTO(List<PurchaseOrderDTO> purchaseOrderDTO) {
        this.purchaseOrderDTO = purchaseOrderDTO;
    }

    public List<PurchaseOrderDTO> getPurchaseOrderDTO() {
        return purchaseOrderDTO;
    }

    public void setPurchaseOrderDTO(List<PurchaseOrderDTO> purchaseOrderDTO) {
        this.purchaseOrderDTO = purchaseOrderDTO;
    }
}
