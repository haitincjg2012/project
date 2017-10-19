package com.alqsoft.vo;

/**
 * 商品分类VO
 * @author Xuejizheng
 * @date 2017-03-02 15:56
 * @see
 * @since 1.8
 */
public class ProcudtTypeVO {

    private Long id; //分类ID
    private String name; //分类名称
    private Long  parentId;//父级id
    private String adPicAddress;//首页图片轮播封面图片地址
    private String oldnameheader;//老图片header名称

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

	public String getAdPicAddress() {
		return adPicAddress;
	}

	public void setAdPicAddress(String adPicAddress) {
		this.adPicAddress = adPicAddress;
	}

	public String getOldnameheader() {
		return oldnameheader;
	}

	public void setOldnameheader(String oldnameheader) {
		this.oldnameheader = oldnameheader;
	}
    
}
