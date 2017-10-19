package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import java.io.Serializable;

/**
 * 行业的分类DTO
 * Created by wudi on 2017/9/1.
 */
public class IndustrysDTO extends BaseValidate implements Serializable {

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
     * 行业name
     */
    private  String name;

    /**
     * 二级行业分类的名
     */
    private String secondName;

    /**
     * 一级行业的id
     */
    private Long type;

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
