package com.ph.shopping.facade.product.entity;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * MallIndex 商城首页管理
 * @version: 2.1
 * @author: 李超
 * @date: 2017-06-13 16:11:57
 */
@Table(name="ph_mall_index")
public class MallIndex implements Serializable {

    private static final long serialVersionUID = 8774795602698574422L;

    /** id */
    @Column(name="id")
    private Long id;

    /** 商品id */
    @Column(name="productId")
    private Long productId;

    /** 商品类别id */
    @Column(name="classifyId")
    private Long classifyId;

    /** 商品在首页中的图片 */
    @Column(name="indexImageUrl")
    private String indexImageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getIndexImageUrl() {
        return indexImageUrl;
    }

    public void setIndexImageUrl(String indexImageUrl) {
        this.indexImageUrl = indexImageUrl;
    }
}
