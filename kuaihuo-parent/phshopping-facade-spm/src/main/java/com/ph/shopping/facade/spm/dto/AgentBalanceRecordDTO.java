package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;
/**
 * 
 * phshopping-facade-spm
 *
 * @description：代理商余额记录DTO
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public class AgentBalanceRecordDTO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6131913819528587382L;
	private Long id;//编号
    private String agentName;//代理名称
    private String source;//来源
    private Double balance;//金额
    private Double fee;//手续费
    private Double amount;//
    private String status;//状态 '1成功，2操作中，3失败';
    private String remark;//备注
    private String createTime;//创建时间
    private String orderNum;//订单号
    private String startTime;//开始时间
    private String endTime;//结束时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
