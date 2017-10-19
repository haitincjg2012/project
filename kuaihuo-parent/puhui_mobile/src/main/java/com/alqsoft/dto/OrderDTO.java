package com.alqsoft.dto;

import java.io.Serializable;

/**
 * @author xwolf
 * @since 1.8
 **/
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 6702019480074490296L;

    private Long id;//订单id
    private Long spid;//商品规格
    private Long stid;//商品分类
    private Long num;//商品数量
    private Long hid;//商家ID
    private String hopeServiceDate;//期望送达时间

    public Long getSpid() {
        return spid;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public String getHopeServiceDate() {
        return hopeServiceDate;
    }

    public void setHopeServiceDate(String hopeServiceDate) {
        this.hopeServiceDate = hopeServiceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
