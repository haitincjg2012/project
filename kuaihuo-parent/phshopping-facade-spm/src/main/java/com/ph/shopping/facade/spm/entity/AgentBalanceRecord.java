/**
 *
 */
package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-agentBalanceRecord
 * @描述：代理商余额记录实体
 * @作者： Mr.Shu
 * @创建时间：2017年3月10日
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_balance_record")
public class AgentBalanceRecord extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6534534098639096330L;

	@Column(name = "source")
    private String source;//来源

    @Column(name = "balance")
    private Double balance;//金额

    @Column(name = "fee")
    private Double fee;//手续费

    @Column(name = "status")
    private String status;//状态 '1成功，2操作中，3失败';

    @Column(name = "remark")
    private String remark;//备注

    @Column(name = "manageId")
    private String manageId;//使用者id 针对商户 供应商 代理商

    @Column(name = "type")
    private String type;//人员类型 供应商1  商户2  代理3

    @Column(name = "orderNum")
    private String orderNum;//订单号

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

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
