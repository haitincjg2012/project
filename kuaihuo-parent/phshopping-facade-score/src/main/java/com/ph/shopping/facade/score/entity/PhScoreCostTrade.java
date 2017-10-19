package com.ph.shopping.facade.score.entity;
/**
 * 
* @ClassName: PhScoreCostTrade
* @Description: 记录支出流水表实体
* @author 王强
* @date 2017年4月24日 下午4:12:53
 */
public class PhScoreCostTrade extends PhScoreTotalTrade {

    /** 套字号 */
    private Long setId;


    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }
}