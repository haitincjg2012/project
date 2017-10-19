package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 主订单返回信息VO
 *
 * @author 郑朋
 * @create 2017/6/2
 **/
public class PurchaseMainOrderVO implements Serializable {
    private static final long serialVersionUID = -6348331344048491048L;

    private Long id;

    /** 订单号 */
    private String orderNo;

    /** 进货人id */
    private Long purchaserId;

    /** 总订单总金额(物流费用+商品总金额) */
    private BigDecimal totalCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
