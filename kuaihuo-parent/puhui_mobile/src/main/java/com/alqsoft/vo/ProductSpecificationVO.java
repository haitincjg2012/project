package com.alqsoft.vo;

/**
 * 商品规格VO
 * @author Xuejizheng
 * @date 2017-03-03 10:27
 */
public class ProductSpecificationVO {

    private Long id;//规格ID
    private String content;//型号
    private String price;//价格
    private Long num;//库存

    private Long productId;//商品ID
    private Long productTypeId;//商品类型ID

    private Long hunterId;

    private Integer productStatus;//商品状态

    private Long limitNum;//限购数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Long getLimitNum() {return limitNum;}

    public void setLimitNum(Long limitNum) {this.limitNum = limitNum;}
}
