package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntityForToken;

import cm.ph.shopping.facade.order.entity.Dish;

/**
 * Date: 2017年2月27日 下午6:52:00 <br/>
 * 
 * @author LZH
 * @version 购物车
 */
public class ShopCartDTO extends BaseEntityForToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1321597620742554113L;
	private Long id;
	private Long dishId;// 商品
	private Long memberId;// 会员
	private Long merchantId;// 商户
	private Long userId;//商户表userId
	private Long type;// 类型 0、菜品 1、包间
	private Date createdTime;//
	private Date updateTime;//
	private Date hopesDate;// 预计到达时间
	private String imgAddress;
	private String merchantName;
	private List<String> merchantImg; // 商户门店照片
	private Long buyNum;//购买数量
	private Long peopleNum;	//总用餐人数
	private Long orderId; //订单ID
	private String dishName;//菜品名称
	private Long money;//菜品单价

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Long peopleNum) {
		this.peopleNum = peopleNum;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	

	public List<String> getMerchantImg() {
		return merchantImg;
	}

	public void setMerchantImg(List<String> merchantImg) {
		this.merchantImg = merchantImg;
	}

	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}



	private List<DishDTO> dishs;

	public List<DishDTO> getDishs() {
		return dishs;
	}

	public void setDishs(List<DishDTO> dishs) {
		this.dishs = dishs;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public String getImgAddress() {
		return imgAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getHopesDate() {
		return hopesDate;
	}

	public void setHopesDate(Date hopesDate) {
		this.hopesDate = hopesDate;
	}

}
