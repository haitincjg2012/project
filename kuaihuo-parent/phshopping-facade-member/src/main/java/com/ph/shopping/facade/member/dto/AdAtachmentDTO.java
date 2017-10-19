package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import java.io.Serializable;

/**
 * Created by wudi on 2017/9/1.
 */
public class AdAtachmentDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = -4747734633757244024L;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 轮播图类型
     */
    private Integer type;
    /**
     * 图片name
     */
    private String name;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
