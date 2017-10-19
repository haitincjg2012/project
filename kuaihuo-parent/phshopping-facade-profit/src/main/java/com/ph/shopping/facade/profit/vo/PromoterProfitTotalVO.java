package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: PromoterProfitTotalVO
* @Description: TODO(推广师分润总表)
* @author Mr.Dong
* @date 2017年6月6日 上午11:00:56
 */
public class PromoterProfitTotalVO implements Serializable{

	private static final long serialVersionUID = 7374820309406823220L;

	private String telPhone;//推广师账号
	
	private String memberName;//推广师名字
	
	private Long profited;//已经分润
	
	private Double profited1;//已经分润

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getProfited() {
		return profited;
	}

	public void setProfited(Long profited) {
		this.profited = profited;
	}

	public Double getProfited1() {
		return profited1;
	}

	public void setProfited1(Double profited1) {
		this.profited1 = profited1;
	}
	
	
}
