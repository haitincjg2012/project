
package com.alqsoft.entity.product;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.producttype.ProductType;

/**
 * Date:     2017年2月27日 下午4:07:13 <br/>
 * @author   zhangcan
 * @version  商品表
 * @since    JDK 1.8
 * @see 	 
 */

public class Product extends IdEntity{

	private String name;//商品名称

	private Attachment imageAttachment;//展示图片
	
	private String imageurl;//默认展示的图片
	
	private ProductType productType;//商品类型
	
	private Hunter hunter;//批发商
	
	private Integer isSubscription;//是否支持订金 0否 1支持
	
	private Long subscriptionMoney;//订金
	
	private Integer status;//状态 0 null 下架 1出售中
	
	private Integer hotRecommend;//是否热门推荐
	
	private Long  niceCommentNum;//好评
	
	private Long commonCommentNum;//中评
	
	private Long badCommentNum;//差评
	
	private Long saleNum;//销量
	
	private Integer startNum;//起批数量

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHotRecommend() {
		return hotRecommend;
	}

	public void setHotRecommend(Integer hotRecommend) {
		this.hotRecommend = hotRecommend;
	}

	public Attachment getImageAttachment() {
		return imageAttachment;
	}

	public void setImageAttachment(Attachment imageAttachment) {
		this.imageAttachment = imageAttachment;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public Integer getIsSubscription() {
		return isSubscription;
	}

	public void setIsSubscription(Integer isSubscription) {
		this.isSubscription = isSubscription;
	}

	public Long getSubscriptionMoney() {
		return subscriptionMoney;
	}

	public void setSubscriptionMoney(Long subscriptionMoney) {
		this.subscriptionMoney = subscriptionMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Long getNiceCommentNum() {
		return niceCommentNum;
	}

	public void setNiceCommentNum(Long niceCommentNum) {
		this.niceCommentNum = niceCommentNum;
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

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	
	
}

