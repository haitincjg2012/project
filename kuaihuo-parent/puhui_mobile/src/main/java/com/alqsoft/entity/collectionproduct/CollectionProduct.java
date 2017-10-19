package com.alqsoft.entity.collectionproduct;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月10日
 *	
 *  收藏商品数记录
 */

public class CollectionProduct  extends IdEntity{

	private Member member;//谁收藏的
	
	private Product product;//收藏的那个商品
	
	private Integer type;//0收藏   1取消收藏
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

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
