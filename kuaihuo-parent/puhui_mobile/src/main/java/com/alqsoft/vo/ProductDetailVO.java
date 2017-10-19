package com.alqsoft.vo;

/**
 * 商品详情
 * @author Administrator
 *
 */
public class ProductDetailVO {
	private Integer id;//商品详情id
	
	private Integer type;//类型，0是文本  1是图片  2是分隔符
	
	private String content;//内容与type相对应
	
	private Long pictureId;//图片id
	
	private  String imgaddress;//图片路径
	
	private Integer sortNum;//显示顺序的字段
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	
	public String getImgaddress() {
		return imgaddress;
	}

	public void setImgaddress(String imgaddress) {
		this.imgaddress = imgaddress;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
	
	
}
