package com.alqsoft.vo;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:47
 */
public class CollectionProductVO {

    private Long id;
    private Long memberId;
    private Long productId;
    private Integer type;//0收藏   1取消收藏

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
