package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: UserDrawCashDataVO
 * @Description: 用户提现数据
 * @author 王强
 * @date 2017年6月27日 下午2:52:10
 */
public class MemberDrawCashDataVO implements Serializable {

	private static final long serialVersionUID = -937443804681422424L;
	private Long score;// 提现实际得到的金额
	private Long handingCharge;// 手续费
	private String bankCardNo;// 银行卡号
	private String memberName;// 账户名
	private String orderNo;// 订单号
	private Long memberId;//会员id

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

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
