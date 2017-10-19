package com.alqsoft.entity.productattachment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alqsoft.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 商品主图附件表
 * @author sunhuijie
 *
 * @date 2017年3月1日
 * @used
 */
@Entity
@Table(name = "alq_product_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class ProductAttachment extends IdEntity{
	
	@NotBlank(message = "附件名称不能为空")
	@Length(min = 2, max = 50, message = "附件名称必须在2-50之间，请重新输入")
	private String name;//附件名称
	
	@NotBlank(message = "附件地址不能为空")
	@Length(min = 2, max = 200, message = "附件名称必须在2-200之间，请重新输入")
	private String address;//附件地址
	
	private Product   product;//商品
	
	private Integer sortNum;//显示顺序的字段
	
	 
	public String getName() {
		return name;
	}
	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
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
