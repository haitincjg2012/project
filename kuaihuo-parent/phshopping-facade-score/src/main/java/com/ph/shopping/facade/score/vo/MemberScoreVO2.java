package com.ph.shopping.facade.score.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: MemberScoreVo2
 * @Description: 数据库查询返回数据
 * @author 王强
 * @date 2017年3月24日 下午3:31:44
 */
public class MemberScoreVO2 implements Serializable {

	private static final long serialVersionUID = -265537890322293018L;

	private long enablescore;// 可用积分
	private long standbyscore; //待用积分
	private long drawcashScore;//已提现积分
	private long bindStatus; //绑定银行卡状态
	private long memberRewardScore;// 推荐会员奖励积分
	private long merchantRewardScore;// 推荐商户奖励积分
	private long memberProfitScore;// 会员分润积分
	private String payPwd;//支付密码
	private long shareMemberScore;// 推广会员可提现积分
	private long shareMerchantScore;// 推广商户可提现积分
	private long totleShareMemberScore; // 推广会员累计积分
	private long totleShareMerchantScore; // 推广商户累计积分
	private long storeManagerScore;//店面经理
	private long totalStoreManagerScore;//店面经理累计积分

	private long dayCashScore; // 当天提现
	private long score; //提现金额
	private Date createTime;
	private String strTime;
	private long totelScore; // 剩余积分

	public long getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(long bindStatus) {
		this.bindStatus = bindStatus;
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

	public long getTotleShareMemberScore() {
		return totleShareMemberScore;
	}

	public void setTotleShareMemberScore(long totleShareMemberScore) {
		this.totleShareMemberScore = totleShareMemberScore;
	}

	public long getTotleShareMerchantScore() {
		return totleShareMerchantScore;
	}

	public void setTotleShareMerchantScore(long totleShareMerchantScore) {
		this.totleShareMerchantScore = totleShareMerchantScore;
	}

	public long getDayCashScore() {
		return dayCashScore;
	}

	public void setDayCashScore(long dayCashScore) {
		this.dayCashScore = dayCashScore;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public long getTotelScore() {
		return totelScore;
	}

	public void setTotelScore(long totelScore) {
		this.totelScore = totelScore;
	}

	public long getEnablescore() {
		return enablescore;
	}

	public void setEnablescore(long enablescore) {
		this.enablescore = enablescore;
	}

	public long getStandbyscore() {
		return standbyscore;
	}

	public void setStandbyscore(long standbyscore) {
		this.standbyscore = standbyscore;
	}

	public long getDrawcashScore() {
		return drawcashScore;
	}

	public void setDrawcashScore(long drawcashScore) {
		this.drawcashScore = drawcashScore;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public long getMemberRewardScore() {
		return memberRewardScore;
	}

	public void setMemberRewardScore(long memberRewardScore) {
		this.memberRewardScore = memberRewardScore;
	}

	public long getMerchantRewardScore() {
		return merchantRewardScore;
	}

	public void setMerchantRewardScore(long merchantRewardScore) {
		this.merchantRewardScore = merchantRewardScore;
	}

	public long getMemberProfitScore() {
		return memberProfitScore;
	}

	public void setMemberProfitScore(long memberProfitScore) {
		this.memberProfitScore = memberProfitScore;
	}

	public long getStoreManagerScore() {
		return storeManagerScore;
	}

	public void setStoreManagerScore(long storeManagerScore) {
		this.storeManagerScore = storeManagerScore;
	}

	public long getTotalStoreManagerScore() {
		return totalStoreManagerScore;
	}

	public void setTotalStoreManagerScore(long totalStoreManagerScore) {
		this.totalStoreManagerScore = totalStoreManagerScore;
	}
}
