package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "ph_member_ic_card_info")
public class IcCardInfo extends BaseEntity{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -4766289036477745587L;

	/**
	 * 内码
	 */
	@Column(name = "innerCode")
	private String innerCode;
	/**
	 * 外码
	 */
	@Column(name = "outerCode")
	private String outerCode;
	/**
	 * 是否删除
	 */
	@Column(name = "isDelete")
	private Byte isDelete;
	
	public String getInnerCode() {
		return innerCode;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
}
