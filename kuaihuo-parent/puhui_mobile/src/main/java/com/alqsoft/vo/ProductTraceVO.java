package com.alqsoft.vo;

import java.util.Date;

/**订单跟踪vo
 * @author Xuejizheng
 * @date 2017-03-08 13:57
 */
public class ProductTraceVO {

    private Long  id;
    private String name;//附件名称
    private String address;//附件地址
    private String orderNo;//订单号
    private String content;//订单跟踪内容
    private Long productTraceId;
    private Date createdTime; //创建时间

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProductTraceId() {
        return productTraceId;
    }

    public void setProductTraceId(Long productTraceId) {
        this.productTraceId = productTraceId;
    }
}
