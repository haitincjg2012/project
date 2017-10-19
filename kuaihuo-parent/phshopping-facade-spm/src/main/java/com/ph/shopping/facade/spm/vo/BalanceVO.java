package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: BalanceVO
* @Description: 用户余额和支付密码VO
* @author 王强
* @date 2017年5月18日 上午9:41:31
 */
public class BalanceVO implements Serializable {

	private static final long serialVersionUID = -1264217633412438191L;

	private Long score1;//数据库值金额
	
	private Double score;//处理后的金额
	
	private String payPwd;//支付密码

	public Long getScore1() {
		return score1;
	}

	public void setScore1(Long score1) {
		this.score1 = score1;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
}
