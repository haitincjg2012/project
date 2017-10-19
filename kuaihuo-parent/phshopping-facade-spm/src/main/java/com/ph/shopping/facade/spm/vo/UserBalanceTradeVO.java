package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 王强
 * @ClassName: UserBalanceTradeVO
 * @Description: 余额流水VO
 * @date 2017年6月2日 上午11:14:17
 */
public class UserBalanceTradeVO implements Serializable {

    private static final long serialVersionUID = 8026256578607290760L;

    private String orderNo;// 订单号
    private String source;// 来源

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;// 时间
    private Long score1;//数据库金额
    private String score;// 金额
    private Long handingCharge1;//数据库金额
    private String handingCharge;// 手续费
    private String remark;// 备注
    private Long totalPrice;//订单总额

    private Double totalMoney;

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getScore1() {
        return score1;
    }

    public void setScore1(Long score1) {
        this.score1 = score1;
    }

    public Long getHandingCharge1() {
        return handingCharge1;
    }

    public void setHandingCharge1(Long handingCharge1) {
        this.handingCharge1 = handingCharge1;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHandingCharge() {
        return handingCharge;
    }

    public void setHandingCharge(String handingCharge) {
        this.handingCharge = handingCharge;
    }
}
