package com.alqsoft.entity.alreadycashorder;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 已处理过的提现记录表
 * 
 * @Description: TODO
 * @author Shen.joe
 * @e-mail sudiluo_java@163.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 上午9:50:07
 * @used
 */
@Entity
@Table(name = "already_cash_order")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class AlreadyCashOrder extends IdEntity {
	
	private static final long serialVersionUID = 1L;

	private String merSeqId;// 流水号
	
	private Integer auditStatus;// 0待审核 1审核通过 2审核不通过 null自动提现
	
	private Integer status;// 1已申请 2打款成功 3打款失败
	
	private Date auditTime;// 审核时间
	
	private Integer money;// 金额，以分为单位；

	public String getMerSeqId() {
		return merSeqId;
	}

	public void setMerSeqId(String merSeqId) {
		this.merSeqId = merSeqId;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
}
