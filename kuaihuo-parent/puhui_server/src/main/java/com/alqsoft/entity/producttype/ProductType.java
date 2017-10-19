
package com.alqsoft.entity.producttype;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.hunter.Hunter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午3:35:42 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_product_type", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ProductType extends IdEntity{
	
	private String content;//商品分类
	
	private ProductType parent;//所属一级分类id
	
	private Long sumProduct;//商品数量
	
	private Hunter hunter;//批发商
	
	private Integer sortNum;//排序序号
	
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public Long getSumProduct() {
		return sumProduct;
	}

	public void setSumProduct(Long sumProduct) {
		this.sumProduct = sumProduct;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}
}

