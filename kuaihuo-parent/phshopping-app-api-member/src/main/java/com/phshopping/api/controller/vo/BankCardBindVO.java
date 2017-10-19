/**  
 * @Title:  BankCardBindVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 下午4:57:39   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  BankCardBindVO   
 * @Description:银行卡绑定数据   
 * @author: 李杰
 * @date:   2017年6月19日 下午4:57:39     
 * @Copyright: 2017
 */
public class BankCardBindVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 5822418491925177375L;

	/**
	 * 绑定ID（会员银行卡绑定关系表主键）
	 */
	private Long bindCardId;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 银行所绑定的手机号
	 */
	private String bindPhone;
	/**
	 * 开户银行
	 */
	private String bankName;
	/**
	 * 开户人姓名
	 */
	private String ownerName;
	/**
	 * 身份证号	
	 */
	private String idCardNo;
	
	public Long getBindCardId() {
		return bindCardId;
	}
	public void setBindCardId(Long bindCardId) {
		this.bindCardId = bindCardId;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBindPhone() {
		return bindPhone;
	}
	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
}
