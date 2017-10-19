
package com.alqsoft.entity.productdetail;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.product.Product;

/**
 * Date:     2017年2月27日 下午4:22:42 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

public class ProductDetail extends IdEntity{
	
	private String content;//商品详情文本内容
	
	private Attachment imageAttachment;//商品详情图片内容
	
	private Product product;//所属商品
	
	private Integer type;//类型  0是文本  1是图片  2是分隔符
	
	private Integer sortNum;//详情的显示顺序

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Attachment getImageAttachment() {
		return imageAttachment;
	}

	public void setImageAttachment(Attachment imageAttachment) {
		this.imageAttachment = imageAttachment;
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

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
	
}

