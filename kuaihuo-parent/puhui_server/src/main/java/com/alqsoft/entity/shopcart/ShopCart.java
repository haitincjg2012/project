
package com.alqsoft.entity.shopcart;

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
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午6:52:00 <br/>
 * @author   zhangcan
 * @version  购物车
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_shop_cart", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ShopCart extends IdEntity{
	
	private Member member;//会员
	
	private ProductSpecification productSpecification;//商品规格
	
	private Long buyNum;//购买数量

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_specification_id")
	public ProductSpecification getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(ProductSpecification productSpecification) {
		this.productSpecification = productSpecification;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}
	
}

