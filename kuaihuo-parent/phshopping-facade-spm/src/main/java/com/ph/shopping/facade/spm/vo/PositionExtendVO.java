package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 代理商供应商商户区域扩展VO
 * @作者： 熊克文
 * @创建时间： 2017/6/20
 * @Copyright by xkw
 */
public class PositionExtendVO implements Serializable {

    /**
     * 省id
     */
    private Long provinceId;

    /**
     * 市id
     */
    private Long cityId;

    /**
     * 区id
     */
    private Long countyId;

    /**
     * 社区id
     */
    private Long townId;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }
}
