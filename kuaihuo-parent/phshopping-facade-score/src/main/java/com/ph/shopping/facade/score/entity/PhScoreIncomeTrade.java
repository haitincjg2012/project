package com.ph.shopping.facade.score.entity;
/**
 * 
* @ClassName: PhScoreIncomeTrade
* @Description: 记入收入流水表实体
* @author 王强
* @date 2017年4月24日 下午4:12:37
 */
public class PhScoreIncomeTrade extends PhScoreTotalTrade {
	/**类型(1收入，2支出)*/
//	private int type;
	
    /** 套字号 */
    private Long setId;

	public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }
}