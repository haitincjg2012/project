package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 商户和行业类别挂靠实体
 * @作者： 熊克文
 * @创建时间： 2017/7/12
 * @Copyright by xkw
 */
public class MerchantMerchantTypeDTO implements Serializable {

    private Long firstMerchantTypeId;  //一级行业类别id

    private Long secondMerchantTypeId;  //二级行业类别id

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

}
