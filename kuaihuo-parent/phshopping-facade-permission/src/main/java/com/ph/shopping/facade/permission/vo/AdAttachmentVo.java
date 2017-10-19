package com.ph.shopping.facade.permission.vo;

import java.io.Serializable;

/**
 * Created by wudi on 2017/9/1.
 */
public class AdAttachmentVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5645594700043186105L;

    /**
     * 轮播图创建的时间
     */
    private String time;



    /**
     * 轮播图id
     */
    private Long id;

    /**
     * 轮播图名字
     */
    private String name;
    /**
     * 轮播图地址
     */
    private String address;
    /**
     * 轮播图header
     */
    private String header;
    /**
     * 轮播图类型
     */
    private String type;

    /**
     * 置顶的标识
     */
    private Integer isTop;

    /**
     * 手机号
     */
    private String phone;

    private String detail_content;

    public String getDetail_content(){
        return this.detail_content;
    }

    public void setDetail_content(String detail_content){
        this.detail_content=detail_content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**

     * 验证码
     */
    private String code;

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
