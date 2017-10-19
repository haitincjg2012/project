
package com.alqsoft.entity.productdetail;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午4:22:42 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_product_detail", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ProductDetail extends IdEntity{
	
	private String content;//商品详情文本内容
	
	private Attachment imageAttachment;//商品详情图片内容
	
	private Product product;//所属商品
	
	private Integer type;//类型  0是文本  1是图片  2是分隔符
	
	private Integer sortNum;//显示顺序的字段
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "image_attachment_id")
	public Attachment getImageAttachment() {
		return imageAttachment;
	}

	public void setImageAttachment(Attachment imageAttachment) {
		this.imageAttachment = imageAttachment;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
}

