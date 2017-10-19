package com.alqsoft.entity.collectionproduct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月10日
 *	
 *  收藏商品数记录
 *  @used
 */
@Entity
@Table(name = "alq_collection_product", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class CollectionProduct  extends IdEntity{

	private Member member;//谁收藏的
	
	private Product product;//收藏的那个商品
	
	private Integer type;//0收藏   1取消收藏
	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
