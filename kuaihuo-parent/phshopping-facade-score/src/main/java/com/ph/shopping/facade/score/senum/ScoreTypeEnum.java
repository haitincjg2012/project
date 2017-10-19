/**  
 * @Title:  ScoreTypeEnum.java   
 * @Package com.ph.shopping.facade.score.senum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午1:59:04   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.score.senum;

/**   
 * @ClassName:  ScoreTypeEnum   
 * @Description:积分查询类型   
 * @author: 李杰
 * @date:   2017年7月26日 下午1:59:04     
 * @Copyright: 2017
 */
public enum ScoreTypeEnum {
	/**
	 * 现金余额
	 */
	CASH_BALANCE((byte)1,"现金余额"),
	/**
	 * 积分余额
	 */
	SCORE_BALANCE((byte)2,"积分余额"),
	/**
	 * 可提现余额
	 */
	DRWN_BALANCE((byte)3,"可提现余额"),
	/**
	 * 推广会员奖励
	 */
	MEMBER_REWARD((byte)4,"推广会员奖励"),
	/**
	 * 推广商户奖励
	 */
	MERCHANT_REWARD((byte)5,"推广商户奖励");
	
	ScoreTypeEnum(Byte code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private Byte code;
	
	private String desc;

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static ScoreTypeEnum getScoreTypeEnum(Byte code){
		for(ScoreTypeEnum type : values()){
			if(type.getCode().equals(code)){
				return type;
			}
		}
		return null;
	}
}
