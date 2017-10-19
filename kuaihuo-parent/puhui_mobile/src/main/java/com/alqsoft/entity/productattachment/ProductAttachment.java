package com.alqsoft.entity.productattachment;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.product.Product;

/**
 * 商品主图附件表
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */
public class ProductAttachment extends IdEntity{
	
	
	private String name;//附件名称

	private String address;//附件地址
	
	private Product product;//商品
	
	private Integer sortNum;//显示顺序的字段
	
	 
	public String getName() {
		return name;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
