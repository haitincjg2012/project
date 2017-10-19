/**  
 * @Title:  MemberSingleScoreVO.java   
 * @Package com.ph.shopping.facade.score.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午1:46:13   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.score.vo;

import java.io.Serializable;
import java.util.List;

/**   
 * @ClassName:  MemberSingleScoreVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月26日 下午1:46:13     
 * @Copyright: 2017
 */
public class MemberSingleScoreVO implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -680406906013565199L;
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
	 * 当前积分
	 */
	private String score;
	/**
	 * 今日到账积分
	 */
	private String todayScore;
	/**
	 * 累计到账积分
	 */
	private String accumulativeScore;
	/**
	 * 可提现金额
	 */
	private String drawnScore;
	/**
	 * 累计提现积分
	 */
	private String accumulativeDraw;
	/**
	 * 积分记录
	 */
	private List<MemberScoreRecordVO> scoreRecord;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTodayScore() {
		return todayScore;
	}
	public void setTodayScore(String todayScore) {
		this.todayScore = todayScore;
	}
	public String getAccumulativeScore() {
		return accumulativeScore;
	}
	public void setAccumulativeScore(String accumulativeScore) {
		this.accumulativeScore = accumulativeScore;
	}
	public String getDrawnScore() {
		return drawnScore;
	}
	public void setDrawnScore(String drawnScore) {
		this.drawnScore = drawnScore;
	}
	public String getAccumulativeDraw() {
		return accumulativeDraw;
	}
	public void setAccumulativeDraw(String accumulativeDraw) {
		this.accumulativeDraw = accumulativeDraw;
	}
	public List<MemberScoreRecordVO> getScoreRecord() {
		return scoreRecord;
	}
	public void setScoreRecord(List<MemberScoreRecordVO> scoreRecord) {
		this.scoreRecord = scoreRecord;
	}
	public String getDayDrawScore() {
		return dayDrawScore;
	}
	public void setDayDrawScore(String dayDrawScore) {
		this.dayDrawScore = dayDrawScore;
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
	
}
