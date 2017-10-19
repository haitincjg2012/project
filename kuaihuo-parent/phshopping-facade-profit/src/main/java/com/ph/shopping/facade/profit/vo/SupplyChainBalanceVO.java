package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: SupplyChainBalanceVO
 * @Description: 供链余额VO
 * @author 王强
 * @date 2017年6月9日 下午12:33:54
 */
public class SupplyChainBalanceVO implements Serializable {
	private static final long serialVersionUID = -1220089417310455152L;

	private String telPhone;//供链账号
	private String enterpriseType;//供链类型

	private Byte userType;//后台用户类型
	private Long balanceId;//余额表id
	private Long userId;//用户id
	private String score;//账户余额
	private Long score1;
	private String oncharge;//在线充值
	private Long oncharge1;
	private String drawcash;//余额提现
	private Long drawcash1;
	private String serviceCost;//线下订单-服务费用
	private Long serviceCost1;
	private String unlineSettle;//线下订单-订单结算
	private Long unlineSettle1;
	private String unlineManageProfit;//线下订单-管理分润
	private Long unlineManageProfit1;
	private String onlineSettle;//线上订单-订单结算
	private Long onlineSettle1;
	private String onlineManageProfit;//线上订单-管理分润
	private Long onlineManageProfit1;
	private String onlineChainProfit;//线上订单-供链分润
	private Long onlineChainProfit1;
	private String supplyChainSettle;//供链订单-订单结算
	private Long supplyChainSettle1;
	private String supplyChainBalancePay;//供链订单-余额支付
	private Long supplyChainBalancePay1;
	private String hunterManageProfit;//批发商订单-管理分润
	private Long hunterManageProfit1;
	private String hunterChainProfit;//批发商订单-供链分润
	private Long hunterChainProfit1;
	private String supplyChainRefund;//供链订单-订单退款
	private Long supplyChainRefund1;
	private String balanceDifference;// 平衡差
	
	private Integer status;//状态

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Long balanceId) {
		this.balanceId = balanceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Long getScore1() {
		return score1;
	}

	public void setScore1(Long score1) {
		this.score1 = score1;
	}

	public String getOncharge() {
		return oncharge;
	}

	public void setOncharge(String oncharge) {
		this.oncharge = oncharge;
	}

	public Long getOncharge1() {
		return oncharge1;
	}

	public void setOncharge1(Long oncharge1) {
		this.oncharge1 = oncharge1;
	}

	public String getDrawcash() {
		return drawcash;
	}

	public void setDrawcash(String drawcash) {
		this.drawcash = drawcash;
	}

	public Long getDrawcash1() {
		return drawcash1;
	}

	public void setDrawcash1(Long drawcash1) {
		this.drawcash1 = drawcash1;
	}

	public String getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(String serviceCost) {
		this.serviceCost = serviceCost;
	}

	public Long getServiceCost1() {
		return serviceCost1;
	}

	public void setServiceCost1(Long serviceCost1) {
		this.serviceCost1 = serviceCost1;
	}

	public String getUnlineSettle() {
		return unlineSettle;
	}

	public void setUnlineSettle(String unlineSettle) {
		this.unlineSettle = unlineSettle;
	}

	public Long getUnlineSettle1() {
		return unlineSettle1;
	}

	public void setUnlineSettle1(Long unlineSettle1) {
		this.unlineSettle1 = unlineSettle1;
	}

	public String getUnlineManageProfit() {
		return unlineManageProfit;
	}

	public void setUnlineManageProfit(String unlineManageProfit) {
		this.unlineManageProfit = unlineManageProfit;
	}

	public Long getUnlineManageProfit1() {
		return unlineManageProfit1;
	}

	public void setUnlineManageProfit1(Long unlineManageProfit1) {
		this.unlineManageProfit1 = unlineManageProfit1;
	}

	public String getOnlineSettle() {
		return onlineSettle;
	}

	public void setOnlineSettle(String onlineSettle) {
		this.onlineSettle = onlineSettle;
	}

	public Long getOnlineSettle1() {
		return onlineSettle1;
	}

	public void setOnlineSettle1(Long onlineSettle1) {
		this.onlineSettle1 = onlineSettle1;
	}

	public String getOnlineManageProfit() {
		return onlineManageProfit;
	}

	public void setOnlineManageProfit(String onlineManageProfit) {
		this.onlineManageProfit = onlineManageProfit;
	}

	public Long getOnlineManageProfit1() {
		return onlineManageProfit1;
	}

	public void setOnlineManageProfit1(Long onlineManageProfit1) {
		this.onlineManageProfit1 = onlineManageProfit1;
	}

	public String getOnlineChainProfit() {
		return onlineChainProfit;
	}

	public void setOnlineChainProfit(String onlineChainProfit) {
		this.onlineChainProfit = onlineChainProfit;
	}

	public Long getOnlineChainProfit1() {
		return onlineChainProfit1;
	}

	public void setOnlineChainProfit1(Long onlineChainProfit1) {
		this.onlineChainProfit1 = onlineChainProfit1;
	}

	public String getSupplyChainSettle() {
		return supplyChainSettle;
	}

	public void setSupplyChainSettle(String supplyChainSettle) {
		this.supplyChainSettle = supplyChainSettle;
	}

	public Long getSupplyChainSettle1() {
		return supplyChainSettle1;
	}

	public void setSupplyChainSettle1(Long supplyChainSettle1) {
		this.supplyChainSettle1 = supplyChainSettle1;
	}

	public String getSupplyChainBalancePay() {
		return supplyChainBalancePay;
	}

	public void setSupplyChainBalancePay(String supplyChainBalancePay) {
		this.supplyChainBalancePay = supplyChainBalancePay;
	}

	public Long getSupplyChainBalancePay1() {
		return supplyChainBalancePay1;
	}

	public void setSupplyChainBalancePay1(Long supplyChainBalancePay1) {
		this.supplyChainBalancePay1 = supplyChainBalancePay1;
	}

	public String getHunterManageProfit() {
		return hunterManageProfit;
	}

	public void setHunterManageProfit(String hunterManageProfit) {
		this.hunterManageProfit = hunterManageProfit;
	}

	public Long getHunterManageProfit1() {
		return hunterManageProfit1;
	}

	public void setHunterManageProfit1(Long hunterManageProfit1) {
		this.hunterManageProfit1 = hunterManageProfit1;
	}

	public String getHunterChainProfit() {
		return hunterChainProfit;
	}

	public void setHunterChainProfit(String hunterChainProfit) {
		this.hunterChainProfit = hunterChainProfit;
	}

	public Long getHunterChainProfit1() {
		return hunterChainProfit1;
	}

	public void setHunterChainProfit1(Long hunterChainProfit1) {
		this.hunterChainProfit1 = hunterChainProfit1;
	}

	public String getSupplyChainRefund() {
		return supplyChainRefund;
	}

	public void setSupplyChainRefund(String supplyChainRefund) {
		this.supplyChainRefund = supplyChainRefund;
	}

	public Long getSupplyChainRefund1() {
		return supplyChainRefund1;
	}

	public void setSupplyChainRefund1(Long supplyChainRefund1) {
		this.supplyChainRefund1 = supplyChainRefund1;
	}

	public String getBalanceDifference() {
		return balanceDifference;
	}

	public void setBalanceDifference(String balanceDifference) {
		this.balanceDifference = balanceDifference;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}
}
