package com.alqsoft.vo;

import org.alqframework.orm.hibernate.IdEntity;

/**
 * 商品分类VO
 * @author Xuejizheng
 * @date 2017-03-02 15:56
 * @see
 * @since 1.8
 */
public class ProcudtTypeVO  {

    private Long id; //分类ID
    private String name; //分类名称
    private Long  parentId;//父级id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


}
