
package com.alqsoft.entity.product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.producttype.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午4:07:13 <br/>
 * @author   zhangcan
 * @version  商品表
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_product", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Product extends IdEntity{
	
	private Attachment imageAttachment;//展示图片
	
	private String imageurl;//默认展示的图片
	
	private String name;//商品名称
	
	private ProductType productType;//商品类型
	
	private Hunter hunter;//批发商
	
	private Integer isSubscription;//是否支持订金 0否 1支持  --------------默认全部都是1  都支持定金
	
	private Long subscriptionMoney;//订金  默认为输入的
	
	private Integer status;//状态 0 null 下架 1出售中
	
	private Integer hotRecommend;//是否热门推荐
	
	private HotRecommend hoRecommendId;//热门推荐信息
	
	private Long  niceCommentNum;//好评
	
	private Long commonCommentNum;//中评
	
	private Long badCommentNum;//差评
	
	private Long saleNum;//销量
	
	private Integer startNum;//起批数量
	
	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hot_recommend_id")
	public HotRecommend getHoRecommendId() {
		return hoRecommendId;
	}

	public void setHoRecommendId(HotRecommend hoRecommendId) {
		this.hoRecommendId = hoRecommendId;
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

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "image_attachment_id")
	public Attachment getImageAttachment() {
		return imageAttachment;
	}

	public void setImageAttachment(Attachment imageAttachment) {
		this.imageAttachment = imageAttachment;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_type_id")
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
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

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}
	
}

