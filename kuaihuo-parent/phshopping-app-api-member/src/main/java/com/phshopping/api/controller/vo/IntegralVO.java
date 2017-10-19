/**  
 * @Title:  IntegraVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午4:21:22   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  IntegraVO   
 * @Description:积分相关数据   
 * @author: 李杰
 * @date:   2017年6月19日 下午4:21:22     
 * @Copyright: 2017
 */
public class IntegralVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2250191179619786471L;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
     * 来源
     */
    private String source;
    /**
     * 积分 正数表示收入 负数表示支出
     */
    private String score;
    /**
     * 手续费
     */
    private String fee;
    
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
}
