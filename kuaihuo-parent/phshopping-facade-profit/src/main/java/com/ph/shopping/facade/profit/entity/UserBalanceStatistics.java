package com.ph.shopping.facade.profit.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：供应链余额统计
 * @作者： 张霞
 * @创建时间： 16:00 2017/7/5
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_user_balance_statistics")
public class UserBalanceStatistics implements Serializable {
    private static final long serialVersionUID = 7906540527933513448L;
    private Long id;// 表流水、
    @Column(name = "userId")
    private Long userId;//用户id
    @Column(name = "userType")
    private Byte userType;//用户角色类型(供链类型)
    @Column(name = "telPhone")
    private String telPhone;//供链账号

    @Column(name = "oncharge")
    private Long oncharge;//在线充值
    @Column(name = "drawcash")
    private Long drawcash;//余额提现
    @Column(name = "unlineServiceCost")
    private Long unlineServiceCost;//线下订单-服务费用
    @Column(name = "unlineSettle")
    private Long unlineSettle;//线下订单-订单结算
    @Column(name = "unlineManageProfit")
    private Long unlineManageProfit;//线下订单-管理分润
    @Column(name = "onlineSettle")
    private Long onlineSettle;//线上订单-订单结算
    @Column(name = "onlineManageProfit")
    private Long onlineManageProfit;//线上订单-管理分润
    @Column(name = "onlineChainProfit")
    private Long onlineChainProfit;//线上订单-供链分润
    @Column(name = "supplyChainSettle")
    private Long supplyChainSettle;//供链订单-订单结算
    @Column(name = "supplyChainBalancePay")
    private Long supplyChainBalancePay;//供链订单-余额支付
    @Column(name = "hunterManageProfit")
    private Long hunterManageProfit;//批发商订单-管理分润
    @Column(name = "hunterChainProfit")
    private Long hunterChainProfit;//批发商订单-供链分润
    @Column(name = "supplyChainRefund")
    private Long supplyChainRefund;//供链订单-订单退款
    @Column(name = "balanced")
    private Long balanced;// 平衡差
    @Column(name = "status")
    private Byte status;//状态
    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;
    /**
     * 创建人
     */
    @Column(name = "createrId")
    private Long createrId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public Long getOncharge() {
        return oncharge;
    }

    public void setOncharge(Long oncharge) {
        this.oncharge = oncharge;
    }

    public Long getDrawcash() {
        return drawcash;
    }

    public void setDrawcash(Long drawcash) {
        this.drawcash = drawcash;
    }

    public Long getUnlineServiceCost() {
        return unlineServiceCost;
    }

    public void setUnlineServiceCost(Long unlineServiceCost) {
        this.unlineServiceCost = unlineServiceCost;
    }

    public Long getUnlineSettle() {
        return unlineSettle;
    }

    public void setUnlineSettle(Long unlineSettle) {
        this.unlineSettle = unlineSettle;
    }

    public Long getUnlineManageProfit() {
        return unlineManageProfit;
    }

    public void setUnlineManageProfit(Long unlineManageProfit) {
        this.unlineManageProfit = unlineManageProfit;
    }

    public Long getOnlineSettle() {
        return onlineSettle;
    }

    public void setOnlineSettle(Long onlineSettle) {
        this.onlineSettle = onlineSettle;
    }

    public Long getOnlineManageProfit() {
        return onlineManageProfit;
    }

    public void setOnlineManageProfit(Long onlineManageProfit) {
        this.onlineManageProfit = onlineManageProfit;
    }

    public Long getOnlineChainProfit() {
        return onlineChainProfit;
    }

    public void setOnlineChainProfit(Long onlineChainProfit) {
        this.onlineChainProfit = onlineChainProfit;
    }

    public Long getSupplyChainSettle() {
        return supplyChainSettle;
    }

    public void setSupplyChainSettle(Long supplyChainSettle) {
        this.supplyChainSettle = supplyChainSettle;
    }

    public Long getSupplyChainBalancePay() {
        return supplyChainBalancePay;
    }

    public void setSupplyChainBalancePay(Long supplyChainBalancePay) {
        this.supplyChainBalancePay = supplyChainBalancePay;
    }

    public Long getHunterManageProfit() {
        return hunterManageProfit;
    }

    public void setHunterManageProfit(Long hunterManageProfit) {
        this.hunterManageProfit = hunterManageProfit;
    }

    public Long getHunterChainProfit() {
        return hunterChainProfit;
    }

    public void setHunterChainProfit(Long hunterChainProfit) {
        this.hunterChainProfit = hunterChainProfit;
    }

    public Long getSupplyChainRefund() {
        return supplyChainRefund;
    }

    public void setSupplyChainRefund(Long supplyChainRefund) {
        this.supplyChainRefund = supplyChainRefund;
    }

    public Long getBalanced() {
        return balanced;
    }

    public void setBalanced(Long balanced) {
        this.balanced = balanced;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }
}
