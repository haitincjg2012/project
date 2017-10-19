package com.alqsoft.vo;

import java.math.BigDecimal;

/**
 * 购物车VO
 * @author Xuejizheng
 * @date 2017-02-28 18:07
 * @see
 * @since 1.8
 */
public class ShopCartVO{
   protected Long id;
   private Long buyNum;//购买数量
   /**
    * 用户相关
    */
  // private Long memberId;
   /**
    * 商品相关
    */
   private String name;
   private Long productId;
   private Long productSpecificationId;

   private String productSpecification;

   private BigDecimal price;//价格
   //private Long subscriptionMoney;//订金
   private Long num;//库存

   private Integer productStatus;

   //private Integer isSubscription;
   /**
    * 附件
    */
   private String address;//图片

   /**
    * 批发商相关
    */
   private Long hunterId;
   private String nickname;//昵称
   
   private Integer startNum;//起批量
   
   public Integer getStartNum() {
	return startNum;
}

public void setStartNum(Integer startNum) {
	this.startNum = startNum;
}

public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getBuyNum() {
      return buyNum;
   }

   public void setBuyNum(Long buyNum) {
      this.buyNum = buyNum;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getProductId() {
      return productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public Long getProductSpecificationId() {
      return productSpecificationId;
   }

   public void setProductSpecificationId(Long productSpecificationId) {
      this.productSpecificationId = productSpecificationId;
   }

   public String getProductSpecification() {
      return productSpecification;
   }

   public void setProductSpecification(String productSpecification) {
      this.productSpecification = productSpecification;
   }

   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public Long getNum() {
      return num;
   }

   public void setNum(Long num) {
      this.num = num;
   }

   public Integer getProductStatus() {
      return productStatus;
   }

   public void setProductStatus(Integer productStatus) {
      this.productStatus = productStatus;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Long getHunterId() {
      return hunterId;
   }

   public void setHunterId(Long hunterId) {
      this.hunterId = hunterId;
   }

   public String getNickname() {
      return nickname;
   }

   public void setNickname(String nickname) {
      this.nickname = nickname;
   }
}
