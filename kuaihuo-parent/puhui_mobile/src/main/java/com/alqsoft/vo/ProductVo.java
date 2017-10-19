package com.alqsoft.vo;

import org.alqframework.orm.hibernate.IdEntity;

import java.util.List;

/**
 * 接收手机端商品参数
 * @author Administrator
 *
 */
public class ProductVo extends IdEntity {

	private Long id;//商品id

	private Long hunterId;//批发商id

	private String name;//商品名称或描述

	private Long productTypeId;//商品分类id 以弃用

	private String pictureids;//图片id 多张图片用逗号拼接

	private Integer isSubscription;//是否支持订金 0否 1支持

	private String subscriptionMoney;//订金

	private String address;//默认图片

	private  List<ProductSpecificationVO> specifications;//规格

	private List<ProductDetailVO> productDetails;//商品详情

	private List<ProductPictureVo> productImags;//商品轮播图片

	private Integer startNum;//起批数量

	private Long  niceCommentNum;//好评

	private Long commonCommentNum;//中评

	private Long badCommentNum;//差评

	private Long saleNum;//销量

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

	public Long getHunterId() {
		return hunterId;
	}

	public void setHunterId(Long hunterId) {
		this.hunterId = hunterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getPictureids() {
		return pictureids;
	}

	public void setPictureids(String pictureids) {
		this.pictureids = pictureids;
	}

	public Integer getIsSubscription() {
		return isSubscription;
	}

	public void setIsSubscription(Integer isSubscription) {
		this.isSubscription = isSubscription;
	}


	public String getSubscriptionMoney() {
		return subscriptionMoney;
	}

	public void setSubscriptionMoney(String subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}

	public List<ProductSpecificationVO> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<ProductSpecificationVO> specifications) {
		this.specifications = specifications;
	}

	public List<ProductDetailVO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetailVO> productDetails) {
		this.productDetails = productDetails;
	}

	public List<ProductPictureVo> getProductImags() {
		return productImags;
	}

	public void setProductImags(List<ProductPictureVo> productImags) {
		this.productImags = productImags;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Long getNiceCommentNum() {
		return niceCommentNum;
	}

	public void setNiceCommentNum(Long niceCommentNum) {
		this.niceCommentNum = niceCommentNum;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Long getCommonCommentNum() {
		return commonCommentNum;
	}

	public void setCommonCommentNum(Long commonCommentNum) {
		this.commonCommentNum = commonCommentNum;
	}

	public Long getBadCommentNum() {
		return badCommentNum;
	}

	public void setBadCommentNum(Long badCommentNum) {
		this.badCommentNum = badCommentNum;
	}
}
