package com.ph.shopping.facade.score.entity;
/**
 * 
* @ClassName: PhScoreTotalTrade
* @Description: 流水表实体
* @author 王强
* @date 2017年4月24日 下午4:12:00
 */
public class PhScoreTotalTrade {
	private long id;//主键ID

	private Long memberId;//会员ID

	private Integer transCode;//交易码
	
	private String orderNo;//订单号

	private long score;//积分
	
	private long handingCharge;//手续费


	private long totelScore; // 总积分

	private long shareMemberScore;

	private long shareMerchantScore;

	private long storeManagerScore;


	public long getTotelScore() {
		return totelScore;
	}

	public void setTotelScore(long totelScore) {
		this.totelScore = totelScore;
	}

	public long getShareMemberScore() {
		return shareMemberScore;
	}

	public void setShareMemberScore(long shareMemberScore) {
		this.shareMemberScore = shareMemberScore;
	}

	public long getShareMerchantScore() {
		return shareMerchantScore;
	}

	public void setShareMerchantScore(long shareMerchantScore) {
		this.shareMerchantScore = shareMerchantScore;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(long handingCharge) {
		this.handingCharge = handingCharge;
	}

	public long getStoreManagerScore() {
		return storeManagerScore;
	}

	public void setStoreManagerScore(long storeManagerScore) {
		this.storeManagerScore = storeManagerScore;
	}
}