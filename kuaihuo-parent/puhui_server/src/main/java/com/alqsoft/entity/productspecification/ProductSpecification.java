
package com.alqsoft.entity.productspecification;

import javax.persistence.*;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.producttype.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午3:42:12 <br/>
 * @author   zhangcan
 * @version  商品规格
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_product_specification", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class ProductSpecification extends IdEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;//型号 
	
	private Long price;//价格 成本价  -------------此字段废弃  用户输入的价格就是销售价
	
	private Long salePrice;//销售价
	
	private Long subscriptionMoney;//订金  默认为输入的销售价的20%
	
	private Long num;//库存

	private Long limitNum;//限购数量
	
	private ProductType productType;//商品类型
	
	private Product product;//商品

	private Long saleNum;//销量
	
	private Integer isDelete;//0或null未删除，1删除


	public Long getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
	}

	public Long getSubscriptionMoney() {
		return subscriptionMoney;
	}

	public void setSubscriptionMoney(Long subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_type_id")
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}

