
package com.alqsoft.entity.productspecification;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.producttype.ProductType;

/**
 * Date:     2017年2月27日 下午3:42:12 <br/>
 * @author   zhangcan
 * @version  商品规格
 * @since    JDK 1.8
 * @see 	 
 */

public class ProductSpecification extends IdEntity{
	
	private String content;//型号 
	
	private Long price;//价格 
	
	private Long num;//库存
	
	private Long salePrice;//销售价
	
	private ProductType productType;//商品类型
	
	private Product product;//商品
	
	private Long saleNum;//销量数量
	
	private Integer isDelete;//0或null未删除，1删除

	private Long limitNum;//限购数量

	public Long getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
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

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
}

