package com.ph.shopping.common.util.other.bankcard.request;

import java.io.Serializable;
/**
 * 
 * @ClassName:  CheckBankCardData   
 * @Description:校验银行卡信息请求数据  
 * @author: 李杰
 * @date:   2017年4月27日 下午3:38:10     
 * @Copyright: 2017
 */
public class CheckBankCardData implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4452840050434556834L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 身份证号
	 */
	private String cardNum;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 校验地址
	 */
	private String checkUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCheckUrl() {
		return checkUrl;
	}
	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}
	
}
