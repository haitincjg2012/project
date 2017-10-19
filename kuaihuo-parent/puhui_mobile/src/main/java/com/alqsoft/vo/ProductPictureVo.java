package com.alqsoft.vo;

/**
 * 商品轮播图
 * @author Administrator
 *
 */
public class ProductPictureVo {

	private Long  id;//轮播图片id
	
	private Integer sortNum;//轮播图排序的位置

	private String address;//图片位置

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getAdress() {
		return address;
	}

	public void setAdress(String adress) {
		this.address = adress;
	}
}
