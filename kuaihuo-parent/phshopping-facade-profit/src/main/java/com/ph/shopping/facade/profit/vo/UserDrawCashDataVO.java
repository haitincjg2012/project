package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserDrawCashDataVO
 * @Description: 用户提现数据
 * @author 王强
 * @date 2017年6月27日 下午2:52:10
 */
public class UserDrawCashDataVO implements Serializable {
	
	private static final long serialVersionUID = -937443804681422424L;
	private Long score;// 提现实际得到的金额
	private Long handingCharge;// 手续费
	private String bankName;// 银行名称
	private String bankNo;// 银行卡号
	private String receiver;// 账户名
	private String orderNo;// 订单号

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(Long handingCharge) {
		this.handingCharge = handingCharge;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
