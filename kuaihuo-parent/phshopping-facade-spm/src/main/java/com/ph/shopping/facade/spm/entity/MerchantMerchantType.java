package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 商户和行业类别挂靠实体
 * @作者： 熊克文
 * @创建时间： 2017/7/12
 * @Copyright by xkw
 */
@Table(name = "ph_merchant_merchant_type")
public class MerchantMerchantType implements Serializable {

    @Column(name = "id")
    private Long id; //主键id

    @Column(name = "firstMerchantTypeId")
    private Long firstMerchantTypeId;  //一级行业类别id

    @Column(name = "secondMerchantTypeId")
    private Long secondMerchantTypeId;  //二级行业类别id

    @Column(name = "merchantId")
    private Long merchantId;  //商户id

    @Column(name = "createrId")
    private Long createrId;  //创建人id

    @Column(name = "createTime")
    private Date createTime;  //创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstMerchantTypeId() {
        return firstMerchantTypeId;
    }

    public void setFirstMerchantTypeId(Long firstMerchantTypeId) {
        this.firstMerchantTypeId = firstMerchantTypeId;
    }

    public Long getSecondMerchantTypeId() {
        return secondMerchantTypeId;
    }

    public void setSecondMerchantTypeId(Long secondMerchantTypeId) {
        this.secondMerchantTypeId = secondMerchantTypeId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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
