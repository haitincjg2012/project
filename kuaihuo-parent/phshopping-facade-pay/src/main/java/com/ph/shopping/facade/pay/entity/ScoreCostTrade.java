package com.ph.shopping.facade.pay.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-pay
 * @描述：会员积分支出了流水记录表 实体
 * @作者： 张霞
 * @创建时间： 17:07 2017/4/11
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_score_cost_trade")
public class ScoreCostTrade extends BaseEntity {
    private static final long serialVersionUID = 273433423349452846L;
    /**
     * 会员id
     */
    @Column(name = "memberId")
    @NotBlank(message="[会员id]不可为空")
    private Long memberId;
    /**
     * 交易码
     */
    @Column(name = "transCode")
    private Integer transCode;
    /**
     * 积分
     */
    @Column(name = "score")
    private Long score;
    /**
     * 套接字号,总流水记录中的id
     */
    @Column(name = "setId")
    private Long setId;
    /**
     * 订单号
     */
    @Column(name = "orderNo")
    private String orderNo;
    /**
     * 手续费
     */
    @Column(name = "fee")
    private Long fee;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getTransCode() {
        return transCode;
    }

    public void setTransCode(Integer transCode) {
        this.transCode = transCode;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
}
