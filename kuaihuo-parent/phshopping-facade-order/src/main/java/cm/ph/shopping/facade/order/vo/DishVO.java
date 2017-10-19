package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商户商品VO
 * 
 * @author gaoge
 *
 */
public class DishVO implements Serializable {
	private static final long serialVersionUID = 4159749956384436455L;
	private Long id;
	private String dishName;//名称
	private Long imgId;//大图Id
	private Long money;//单价
	private Long dCount;//库存
	private Long dishTypeId;//分类id
	private String imgAddress;//大图地址
	private int type;//0 菜品 1 餐位
	private Long saleNum;//销量
	private Long isDelete;//0 上架  1下架
	private String specification;//商品规格介绍
	private String typeName;
	private Long merchantId;
	private String address;
	
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
