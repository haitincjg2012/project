package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应链主订单实体
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Table(name = "ph_purchase_main_order")
public class PurchaseMainOrder implements Serializable {

    private static final long serialVersionUID = -2282197982321131647L;

    /** 创建时间 */
    @Column(name="createTime")
    private Date createTime;

    @Id
    private Long id;

    /** 订单号 */
    @Column(name="orderNo")
    private String orderNo;

    /** 进货人id */
    @Column(name="purchaserId")
    private Long purchaserId;

    /** 总订单商品总金额 */
    @Column(name="money")
    private BigDecimal money;

    /** 总订单总金额(物流费用+商品总金额) */
    @Column(name="totalCost")
    private BigDecimal totalCost;

    /** 支付时间*/
    @Column(name="payTime")
    private Date payTime;

    @Column(name="md5")
    private String md5;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
