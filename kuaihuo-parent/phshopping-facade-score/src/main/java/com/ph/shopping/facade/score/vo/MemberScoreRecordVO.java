/**  
 * @Title:  MemberScoreRecordVO.java   
 * @Package com.ph.shopping.facade.score.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午12:46:00   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.score.vo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @ClassName:  MemberScoreRecordVO   
 * @Description:会员积分记录  
 * @author: 李杰
 * @date:   2017年7月26日 下午12:46:00     
 * @Copyright: 2017
 */
public class MemberScoreRecordVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2619205642539605127L;
	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 积分
	 */
	private String score;
	/**
	 * 手续费
	 */
	private String fee;
	/**
	 * 订单号
	 */
	private String orderNo;
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
