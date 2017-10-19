package com.ph.shopping.facade.product.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ProductSpecificationVO 规格和值VO
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-17 11:44:32
 */
public class ProductSpecificationVO implements Serializable {

    private static final long serialVersionUID = 9077005285972421622L;

    /** 主键 */
    private Long id;

    /** 商品id */
    private Long productId;

    /** 规格名称 */
    private String specificationName;

    /** 创建人 */
    private Long createrId;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 规格值(非sku) */
    private List<ProductSpecificationValVO> productSpecificationValVOList;

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

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ProductSpecificationValVO> getProductSpecificationValVOList() {
        return productSpecificationValVOList;
    }

    public void setProductSpecificationValVOList(List<ProductSpecificationValVO> productSpecificationValVOList) {
        this.productSpecificationValVOList = productSpecificationValVOList;
    }
}
