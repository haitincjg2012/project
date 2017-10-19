
package com.alqsoft.entity.bankcard;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;

/**
 * Date:     2017年2月27日 下午2:22:29 <br/>
 * @author   zhangcan
 * @version  绑定银行卡
 * @since    JDK 1.8
 * @see 	 
 */

public class BankCard extends IdEntity{
	
	private String bankNo;//银行卡号
	
	private String bank;//开户行
	
	private String name;//姓名
	
	private String card;//身份证号
	
	private IndustryAssociation industryAssociationId;//行业协会
	
	private Hunter hunter;//批发商
	
	private Integer status;//0 绑卡记录 1正在绑定中的银行卡
	
	private String bankAddr; //开户行缩略
	
	
	public String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	 
	public IndustryAssociation getIndustryAssociationId() {
		return industryAssociationId;
	}

	public void setIndustryAssociationId(IndustryAssociation industryAssociationId) {
		this.industryAssociationId = industryAssociationId;
	}
	 
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
}

