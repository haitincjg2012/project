package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 子订单修改变更历史实体
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Table(name = "ph_sub_order_mdy_his")
public class SubOrderMdyHis implements Serializable{

    private static final long serialVersionUID = 1864650378880081527L;

    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 子订单id
     */
    @Column(name = "subOrderId")
    private Long subOrderId;
    /**
     * 订单状态
     */
    @Column(name = "status")
    private Byte status;
    /**
     * 处理状态
     */
    @Column(name = "dealStatus")
    private Byte dealStatus;
    /**
     * 操作人
     */
    @Column(name = "createId")
    private Long createId;
    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;
    /**
     * 变更描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 操作类型  1，改物流费用 2，修改订单状态 3，修改分润
     */
    @Column(name = "operateType")
    private Byte operateType;
    /**
     * 改价金额
     */
    @Column(name = "money")
    private BigDecimal money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Byte dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
