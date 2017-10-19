/**  
 * @Title:  MemberScoreVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午3:59:12   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  MemberScoreVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月19日 下午3:59:12     
 * @Copyright: 2017
 */
public class MemberScoreVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 3203483619936952384L;
	/**
	 * 可用积分
	 */
	private String enablescore;
	/**
	 * 待用积分
	 */
	private String standbyscore;
	/**
	 * 当天已体现积分
	 */
	private String dayDrawScore;
	/**
	 * 是否绑定了银行卡
	 */
	private Boolean isBindBank;
	/**
	 * 是否存在支付密码
	 */
	private Boolean isExistPayPwd;

	/**
	 * 推广会员可提现积分
	 */
	private String shareMemberScore;

	/**
	 * 推广商户可提现积分
	 */
	private String shareMerchantScore;

	/**
	 * 推广商户累计积分
	 */
	private String totleShareMemberScore;

	/**
	 * 推广会员累计积分
	 */
	private String totleShareMerchantScore;

	/**
	 * 当天提现积分
	 */
	private String dayCashScore;

	/*
	是否店面经理
	 */
	private String isStoreManager;

	private String storeManagerScore;//店面积分

	/**
	 * 天天返
	 */
	private String tiantian;


	/**
	 * 刮刮乐
	 */
	private String guagua;

	public String getTiantian() {
		return tiantian;
	}

	public void setTiantian(String tiantian) {
		this.tiantian = tiantian;
	}

	public String getGuagua() {
		return guagua;
	}

	public void setGuagua(String guagua) {
		this.guagua = guagua;
	}

	public Boolean getBindBank() {
		return isBindBank;
	}

	public void setBindBank(Boolean bindBank) {
		isBindBank = bindBank;
	}

	public Boolean getExistPayPwd() {
		return isExistPayPwd;
	}

	public void setExistPayPwd(Boolean existPayPwd) {
		isExistPayPwd = existPayPwd;
	}

	public String getShareMemberScore() {
		return shareMemberScore;
	}

	public void setShareMemberScore(String shareMemberScore) {
		this.shareMemberScore = shareMemberScore;
	}

	public String getShareMerchantScore() {
		return shareMerchantScore;
	}

	public void setShareMerchantScore(String shareMerchantScore) {
		this.shareMerchantScore = shareMerchantScore;
	}

	public String getTotleShareMemberScore() {
		return totleShareMemberScore;
	}

	public void setTotleShareMemberScore(String totleShareMemberScore) {
		this.totleShareMemberScore = totleShareMemberScore;
	}

	public String getTotleShareMerchantScore() {
		return totleShareMerchantScore;
	}

	public void setTotleShareMerchantScore(String totleShareMerchantScore) {
		this.totleShareMerchantScore = totleShareMerchantScore;
	}

	public String getDayCashScore() {
		return dayCashScore;
	}

	public void setDayCashScore(String dayCashScore) {
		this.dayCashScore = dayCashScore;
	}

	public Boolean getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(Boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public Boolean getIsExistPayPwd() {
		return isExistPayPwd;
	}

	public void setIsExistPayPwd(Boolean isExistPayPwd) {
		this.isExistPayPwd = isExistPayPwd;
	}

	public String getEnablescore() {
		return enablescore;
	}

	public void setEnablescore(String enablescore) {
		this.enablescore = enablescore;
	}

	public String getStandbyscore() {
		return standbyscore;
	}

	public void setStandbyscore(String standbyscore) {
		this.standbyscore = standbyscore;
	}

	public String getDayDrawScore() {
		return dayDrawScore;
	}

	public void setDayDrawScore(String dayDrawScore) {
		this.dayDrawScore = dayDrawScore;
	}

	public String getIsStoreManager() {
		return isStoreManager;
	}

	public void setIsStoreManager(String isStoreManager) {
		this.isStoreManager = isStoreManager;
	}

	public String getStoreManagerScore() {
		return storeManagerScore;
	}

	public void setStoreManagerScore(String storeManagerScore) {
		this.storeManagerScore = storeManagerScore;
	}
}
