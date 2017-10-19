package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: MemberScoreVO
* @Description: TODO(会员积分VO) 用于分润返回推广师积分
* @author Mr.Dong
* @date 2017年5月24日 下午2:24:56
 */
public class MemberScoreVO implements Serializable{
	
	private static final long serialVersionUID = -2192971189187171224L;

	private Long id;//主键(用于插入积分主表返回id入分积分流水表)

	private Long memberId;//会员id
	
	private Long score;//积分(金额)
	
	private String  orderNo;//订单号码
	
	private String transCode;//交易码

	private Byte isMarketing;//推荐人是否是推广师 1 是推广师； 2会员

	private Byte isProfit;//是否分润 0=未分润；1=已分润；2=会员分润
	
	private Byte isReward;//判断是否写会员推广商户奖励金流水 1是要写  null是不写

	private Long totelScore;//余额

	public Long getTotelScore() {
		return totelScore;
	}

	public void setTotelScore(Long totelScore) {
		this.totelScore = totelScore;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getIsMarketing() {
		return isMarketing;
	}

	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}

	public Byte getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(Byte isProfit) {
		this.isProfit = isProfit;
	}

	public Byte getIsReward() {
		return isReward;
	}

	public void setIsReward(Byte isReward) {
		this.isReward = isReward;
	}
	
	
}
