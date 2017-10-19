package com.ph.shopping.facade.product.dto;

import java.io.Serializable;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品图片DTO
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午4:22:16
 *
 * @Copyright by 杨颜光
 */
public class ProductImageDTO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1202418313955387347L;

	/**  */
    private Long id;

    /** 图片路径 */
    private String url;

    /** 商品id */
    private Long productId;

    /** 排序 */
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}