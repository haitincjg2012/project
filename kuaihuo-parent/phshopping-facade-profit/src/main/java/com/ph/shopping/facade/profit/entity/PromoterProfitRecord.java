package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 推广师分润记录   ph_profit_promoter_record
* @ClassName: PromoterProfitRecord
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月22日 下午5:49:09
 */
public class PromoterProfitRecord implements Serializable {
	
	
	private static final long serialVersionUID = -1325282372223397752L;

	private Long id;

    /** 订单编号 */
    private String orderNo;

    /** 订单下单时间 */
    private Date deliveryDate;

    /** 订单金额 */
    private Long orderMoney;

    /** 分润金额 */
    private Long proProfit;

    /** 分润时间 */
    private Date profitDate;

    /** 角色编号 */
    private Byte enterpriseType;

    /** 后台用户名称 */
    private String enterpriseName;

    /** 分润师id */
    private Long promoterId;

    /** 用户id */
    private Long userId;

    /** 创建时间 */
    private Date createDate;


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
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Long orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Long getProProfit() {
        return proProfit;
    }

    public void setProProfit(Long proProfit) {
        this.proProfit = proProfit;
    }

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
    }


    public Byte getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Byte enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public Long getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Long promoterId) {
        this.promoterId = promoterId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}