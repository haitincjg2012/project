
package com.alqsoft.entity.producttype;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;

/**
 * Date:     2017年2月27日 下午3:35:42 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

public class ProductType extends IdEntity{
	
	private String content;//商品分类
	
	private ProductType parent;//所属一级分类id
	
	private Long sumProduct;//商品数量
	
	private Hunter hunter;//批发商
	
	private Integer sortNum;//获取排序数字
	
	
	
	
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

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
	
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}
}

