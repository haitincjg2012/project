package com.ph.queue.request;


/**
 * @项目：
 * @描述：
 * @作者： Mr.zheng
 * @创建时间：
 * @Copyright @2017 by Mr.zheng
 */
public class OrderTaskRequest<T> {

    //订单类型 1平台进货
    private Integer type;

    //订单参数
    private T data;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
