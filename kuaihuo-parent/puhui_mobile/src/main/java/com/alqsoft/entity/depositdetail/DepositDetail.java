
package com.alqsoft.entity.depositdetail;

import java.util.Date;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;

/**
 * Date:     2017年2月27日 下午3:05:54 <br/>
 * @author   zhangcan
 * @version  提现记录
 * @since    JDK 1.8
 * @see 	 
 */

public class DepositDetail extends IdEntity{
	
	private String depositNo;//提现流水号
	
	private Long money;//提现金额
	
	private Long fee;//提现手续费
	
	private String bankCard;//银行卡号
	
	private String bank;//开户行
	
	private Integer status;//提现状态 0已申请 1打款成功 2打款失败
	
	private Date applyDate;//申请提现时间
	
	private Date doneDate;//已完成时间
	
	private Hunter hunter;//批发商
	
	private IndustryAssociation industryAssociation;//行业协会

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	
	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}
	
}

