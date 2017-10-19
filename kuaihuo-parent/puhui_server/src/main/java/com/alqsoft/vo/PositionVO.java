package com.alqsoft.vo;

import java.io.Serializable;

/**
 * Created by ywj on 2017/9/29.
 */
public class PositionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long countyId;//县编号
    private String countyName;//县名称

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
