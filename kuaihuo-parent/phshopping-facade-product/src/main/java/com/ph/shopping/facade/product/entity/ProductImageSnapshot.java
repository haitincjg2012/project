package com.ph.shopping.facade.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ProductImage 商品图片实体-快照
 *
 * @version: 2.1更新
 * @author: 李超
 * @date: 2017-05-12 10:45:37
 */
@Table(name="ph_product_image_snapshot")
public class ProductImageSnapshot implements Serializable{

	private static final long serialVersionUID = 6896809721850362138L;

	/**  */
	@Id
    private Long id;
	
    /** 商品图片表原表id */
    @Column(name="productImageId")
	private Long productImageId;

    /** 图片路径 */
	@Column(name="url")
    private String url;

    /** 商品id */
	@Column(name="productId")
    private Long productId;

    /** 排序 */
	@Column(name="sort")
    private Integer sort;
	
	/**商品快照表主键id*/
	@Column(name="productSnapshotId")
	private Long  productSnapshotId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getProductImageId() {return productImageId;}

    public void setProductImageId(Long productImageId) {this.productImageId = productImageId;}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductSnapshotId() {
		return productSnapshotId;
	}

	public void setProductSnapshotId(Long productSnapshotId) {
		this.productSnapshotId = productSnapshotId;
	}
    
	
    
}