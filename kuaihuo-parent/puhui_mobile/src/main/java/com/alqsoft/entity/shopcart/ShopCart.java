
package com.alqsoft.entity.shopcart;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.productspecification.ProductSpecification;

/**
 * Date:     2017年2月27日 下午6:52:00 <br/>
 * @author   zhangcan
 * @version  购物车
 * @since    JDK 1.8
 * @see 	 
 */

public class ShopCart extends IdEntity{
	
	private Member member;//会员
	
	private ProductSpecification productSpecification;//商品规格
	
	private Long buyNum;//购买数量

	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

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

