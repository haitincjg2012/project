package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: OnLineSettleVO
 * @Description: 积分明细VO
 * @author 王强
 * @date 2017年6月6日 上午9:47:19
 */
public class ScoreDetailVO implements Serializable {
	private static final long serialVersionUID = -7153811050956508837L;

	private String telPhone;// 会员账号
	private String memberType;// 会员类型
	private String memberName;// 会员名称
	private String source;// 来源
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 操作时间
	private Long score1;
	private String score;// 积分
	private Long handingCharge1;
	private String handingCharge;// 手续费
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public Long getHandingCharge1() {
		return handingCharge1;
	}
	public void setHandingCharge1(Long handingCharge1) {
		this.handingCharge1 = handingCharge1;
	}
	public String getHandingCharge() {
		return handingCharge;
	}
	public void setHandingCharge(String handingCharge) {
		this.handingCharge = handingCharge;
	}
	public Long getScore1() {
		return score1;
	}
	public void setScore1(Long score1) {
		this.score1 = score1;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
