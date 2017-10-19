package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;

public class DishDTO implements Serializable{
	private static final long serialVersionUID = -1181142641611806340L;
	private Long id;
	private String dishName;//名称
	private Long dishSpecificationId;//规格id
	private Long merchantId;//商户id
	private Long imgId;//大图Id
	private Double money;//单价
	private Long dCount;//库存
	private Long goodComment;//好评
	private Long comment;//中评 
	private Long badComment;//差评
	private Long dishTypeId;//分类id
	private String imgAddress;//大图地址
	private int type;//0 菜品 1 餐位
	private Long saleNum;//销量
	private Long isDelete;//0 上架  1下架
	private Long subscriptionMoney;//餐位订金
	private Long hopeTime;//进餐时间
	private String description;//简介
	private String typeName;
	private Integer pageNum;
	private Integer pageSize;
	private Integer beginIndex;
	private Integer orderNum;
	private Long memberId;//会员
	private String orderNo;//订单号 
	private Integer isChoose;//0待付款 1已付定金 2商户已接单 3尾款已支付 4已评价 5申请退款   6已退款 7已关闭
	private String address;
	private String imgArr; 
	private Long count;	//包间人数
	private String moneyUnit;//菜品金额单位

	private Integer buyNum;//购买数量
	private String token;

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
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	public Long getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Long isDelete) {
		this.isDelete = isDelete;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getIsChoose() {
		return isChoose;
	}
	public void setIsChoose(Integer isChoose) {
		this.isChoose = isChoose;
	}
	public String getImgArr() {
		return imgArr;
	}
	public void setImgArr(String imgArr) {
		this.imgArr = imgArr;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
