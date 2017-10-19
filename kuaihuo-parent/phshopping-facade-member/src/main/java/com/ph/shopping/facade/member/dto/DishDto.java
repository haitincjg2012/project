package com.ph.shopping.facade.member.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DishDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2669014050123259387L;

	private Long id;
	private String dishName;	//菜品名称
	private String imgAddress; //图片地址
	private String money;  //价格
	private Long merchantId;
	private int status;  //1未预定  2已预定 3购物车里有
	private int dishTypeId;	
	private int type; 		//0菜  1 餐位
	private Date hopeServiceDate;
	private Date predictServiceDate;//离店时间
	private String description; //简介
	private Long saleNum;//销量
	private Double subscriptionMoney;//最低消费
	private Long count; //包间人数
	private Long hopeTime;
	private Long dCount;//购买数量
	private String moneyUnit;
	private String hopeServiceDates; //期望到达时间String类型
	private List<String> dishImg;;//图片地址
	public String getMoneyUnit() {
		return moneyUnit;
	}

	public void setMoneyUnit(String moneyUnit) {
		this.moneyUnit = moneyUnit;
	}

	public String getHopeServiceDates() {
		return hopeServiceDates;
	}

	public void setHopeServiceDates(String hopeServiceDates) {
		this.hopeServiceDates = hopeServiceDates;
	}

	public Long getHopeTime() {
		return hopeTime;
	}
	public void setHopeTime(Long hopeTime) {
		this.hopeTime = hopeTime;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Double getSubscriptionMoney() {
		return subscriptionMoney;
	}
	public void setSubscriptionMoney(Double subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}


	public Long getdCount() {
		return dCount;
	}

	public void setdCount(Long dCount) {
		this.dCount = dCount;
	}

	public List<String> getDishImg() {
		return dishImg;
	}
	public void setDishImg(List<String> dishImg) {
		this.dishImg = dishImg;
	}
	public Long getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getHopeServiceDate() {
		return hopeServiceDate;
	}
	public void setHopeServiceDate(Date hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}
	public Date getPredictServiceDate() {
		return predictServiceDate;
	}
	public void setPredictServiceDate(Date predictServiceDate) {
		this.predictServiceDate = predictServiceDate;
	}
	public int getDishTypeId() {
		return dishTypeId;
	}
	public void setDishTypeId(int dishTypeId) {
		this.dishTypeId = dishTypeId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
}
