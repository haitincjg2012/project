package com.alqsoft.vo;

/**
 * 行业分类VO
 * @author Xuejizheng
 * @date 2017-03-02 14:36
 * @see
 * @since 1.8
 */
public class IndustryTypeVO {

    private Long id ;//主键
    private String name;//行业分类名称
    private Long  parentId;//父级id
    private String address;//图片地址

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
