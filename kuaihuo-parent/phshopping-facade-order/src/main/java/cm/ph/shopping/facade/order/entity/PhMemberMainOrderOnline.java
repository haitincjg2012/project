package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员线上订单主表
 * @作者： 张霞
 * @创建时间： 22:57 2017/5/23
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_main_order_online")
public class PhMemberMainOrderOnline implements Serializable {
    private static final long serialVersionUID = -2282197982321131649L;
    /**
     * 主键id
     */
    @Id
    private Long id;
    /**
     * 订单号
     */
    @Column(name = "orderNo")
    private String orderNo;
    /** 商品总价格
     *
     */
    @Column(name = "productMoney")
    private Double productMoney;
    /** 订单总价格(加上物流费用)
     *
     */
    @Column(name = "orderMoney")
    private Double orderMoney;
    /**
     * 订单物流总费用
     */
    @Column(name = "logisticsMoney")
    private Double logisticsMoney;
    /** 会员id(创建人)
     *
     */
    @Column(name = "memberId")
    private Long memberId;
    /** 支付时间
     *
     */
    @Column(name = "payTime")
    private Date payTime;
    /** 创建时间
     *
     */
    @Column(name = "createTime")
    private Date createTime;
    /** md5记录
     *
     */
    @Column(name = "md5")
    private String md5;

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

    public Double getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(Double productMoney) {
        this.productMoney = productMoney;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getLogisticsMoney() {
        return logisticsMoney;
    }

    public void setLogisticsMoney(Double logisticsMoney) {
        this.logisticsMoney = logisticsMoney;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
