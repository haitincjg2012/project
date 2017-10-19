package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
 * phshopping-facade-spm
 *
 * @description：用户余额记录流水VO
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public class UserBalanceRecordVO implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 3004785774756179303L;
	
	private Long id;//编号
    private String name;//商户/代理商名称
    private Long userId;//商户id
    private String transCode;//来源
    private Double money;//金额
    private String orderNo;//订单号
    private String remark;//备注
    private String creatTime;//创建时间
    /**
     * 开始时间查询条件
     */
    private String startTime;

    /**
     * 结束时间查询条件
     */
    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
