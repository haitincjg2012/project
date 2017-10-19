package com.alqsoft.vo;

import java.util.List;

/**
 * Created by ywj on 2017/10/10.
 */
public class ProductShowVO {
    private Long id;//商品id

    private String name;//商品名称或描述

    private Integer startNum;//起批数量

    private Long  niceCommentNum;//好评

    private Long CommentNumAll;

    private Long saleNum;//销量

    private List<String> imgaddress;//商品详情图片路径

    private Long contentId;//规格ID

    private Long productTypeId;//商品分类ID

    private String content;//型号

    private String price;//价格

    private Long buyNum;//购物车数量

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Long buyNum) {
        this.buyNum = buyNum;
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

    public Long getCommentNumAll() {
        return CommentNumAll;
    }

    public void setCommentNumAll(Long commentNumAll) {
        CommentNumAll = commentNumAll;
    }

    public Long getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Long saleNum) {
        this.saleNum = saleNum;
    }


    public List<String> getImgaddress() {
        return imgaddress;
    }

    public void setImgaddress(List<String> imgaddress) {
        this.imgaddress = imgaddress;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
