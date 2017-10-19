/**  
 * @Title:  IntegralDrawVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午4:37:13   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  IntegralDrawVO   
 * @Description:提现返回数据   
 * @author: 李杰
 * @date:   2017年6月19日 下午4:37:13     
 * @Copyright: 2017
 */
public class IntegralDrawVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 3298025459816576893L;

	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	
	/**
	 * 身份证号码
	 */
	private String idCardNo;
	/**
	 * 可用积分
	 */
	private String enabledScore;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getEnabledScore() {
		return enabledScore;
	}
	public void setEnabledScore(String enabledScore) {
		this.enabledScore = enabledScore;
	}
}
