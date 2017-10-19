package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MerchantDishDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4819097579667778832L;
	/**
	 * 菜品分类id
	 */
	private Long typeId;
	/**
	 * 包间预定时常
	 */
	private String typeName;
	/**
	 * type 0菜品 1 餐位
	 */
	private int type;
	
	/**
	 * 商户ID
	 */
	private Long merchantId;
	/**
	 * 预计到达时间
	 */
	private String hopeServiceDate;
	private Long memberId;
	
	
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getHopeServiceDate() {
		return hopeServiceDate;
	}

	public void setHopeServiceDate(String hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}

	private List<DishDto> list;
	
	
	public List<DishDto> getList() {
		return list;
	}

	public void setList(List<DishDto> list) {
		this.list = list;
	}


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}


