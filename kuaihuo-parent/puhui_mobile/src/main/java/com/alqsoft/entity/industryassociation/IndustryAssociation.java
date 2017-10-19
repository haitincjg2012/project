
package com.alqsoft.entity.industryassociation;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * Date:     2017年2月27日 下午2:08:39 <br/>
 * @author   zhangcan
 * @version  行业协会
 * @since    JDK 1.8
 * @see 	 
 */

public class IndustryAssociation extends IdEntity{
	
	private String phone;//手机号
	
	private String password;//初始化密码
	
	private String payname;//收款人姓名
	
	private String card;//身份证
	
	private Long incomeAllMoney;//收入总金额
	
	private Long haveDepositMoney;//已提现金额
	
	private Long leftDepositMoney;//剩下可提现总金额
	
	public Long getIncomeAllMoney() {
		return incomeAllMoney;
	}

	public void setIncomeAllMoney(Long incomeAllMoney) {
		this.incomeAllMoney = incomeAllMoney;
	}

	public Long getHaveDepositMoney() {
		return haveDepositMoney;
	}

	public void setHaveDepositMoney(Long haveDepositMoney) {
		this.haveDepositMoney = haveDepositMoney;
	}

	public Long getLeftDepositMoney() {
		return leftDepositMoney;
	}

	public void setLeftDepositMoney(Long leftDepositMoney) {
		this.leftDepositMoney = leftDepositMoney;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPayname() {
		return payname;
	}

	public void setPayname(String payname) {
		this.payname = payname;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
}

