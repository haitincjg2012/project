package com.ph.shopping.facade.product.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * ProductSpecificationValVO 规格值VO
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-17 14:25:04
 */
public class ProductSpecificationValVO implements Serializable{

    private static final long serialVersionUID = 545433759410709860L;

    /** 主键 */
    @Id
    private Long id;

    /** 规格名称id */
    private Long specificationId;

    /** 规格值 */
    private String specificationValue;

    /** 创建人 */
    private Long createrId;

    /** 创建时间 */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationValue() {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue) {
        this.specificationValue = specificationValue;
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
}
