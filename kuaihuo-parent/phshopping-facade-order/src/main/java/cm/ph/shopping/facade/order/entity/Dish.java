package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ph_dish")
public class Dish  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159749956384436455L;
	@Id
	private Long id;
	@Column(name = "dishName")
	private String dishName;//名称
	@Column(name = "dishSpecificationId")
	private Long dishSpecificationId;//规格id
	@Column(name = "merchantId")
	private Long merchantId;//商户id
	@Column(name = "imgId")
	private Long imgId;//大图Id
	@Column(name = "money")
	private Long money;//单价
	@Column(name = "dCount")
	private Long dCount;//库存
	@Column(name = "goodComment")
	private Long goodComment;//好评
	@Column(name = "comment")
	private Long comment;//中评 
	@Column(name = "badComment")
	private Long badComment;//差评
	@Column(name = "dishTypeId")
	private Long dishTypeId;//分类id
	@Column(name = "imgAddress")
	private String imgAddress;//大图地址
	@Column(name = "type")
	private int type;//0 菜品 1 餐位
	@Column(name = "saleNum")
	private Long saleNum;//销量
	@Column(name = "isDelete")
	private Long isDelete;//0 上架  1下架
	@Column(name = "subscriptionMoney")
	private Long subscriptionMoney;//餐位订金
	@Column(name = "hopeTime")
	private Long hopeTime;//进餐时间
	@Column(name = "description")
	private String description;//简介
	@Column(name = "count")
	private Long count;//包间人数
	@Column(name = "moneyUnit")
	private String moneyUnit;//包间人数

	public String getMoneyUnit() {
		return moneyUnit;
	}

	public void setMoneyUnit(String moneyUnit) {
		this.moneyUnit = moneyUnit;
	}

	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
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
	public Long getDishSpecificationId() {
		return dishSpecificationId;
	}
	public void setDishSpecificationId(Long dishSpecificationId) {
		this.dishSpecificationId = dishSpecificationId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getImgId() {
		return imgId;
	}
	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public Long getdCount() {
		return dCount;
	}
	public void setdCount(Long dCount) {
		this.dCount = dCount;
	}
	public Long getGoodComment() {
		return goodComment;
	}
	public void setGoodComment(Long goodComment) {
		this.goodComment = goodComment;
	}
	public Long getComment() {
		return comment;
	}
	public void setComment(Long comment) {
		this.comment = comment;
	}
	public Long getBadComment() {
		return badComment;
	}
	public void setBadComment(Long badComment) {
		this.badComment = badComment;
	}
	public Long getDishTypeId() {
		return dishTypeId;
	}
	public void setDishTypeId(Long dishTypeId) {
		this.dishTypeId = dishTypeId;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	
	public Long getSubscriptionMoney() {
		return subscriptionMoney;
	}
	public void setSubscriptionMoney(Long subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}
	public Long getHopeTime() {
		return hopeTime;
	}
	public void setHopeTime(Long hopeTime) {
		this.hopeTime = hopeTime;
	}
	public Long getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
