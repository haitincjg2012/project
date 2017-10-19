package com.ph.shopping.common.util.other.bankcard.response;
/**
 * 
 * @ClassName:  CheckBankResponse   
 * @Description:校验银行卡信息返回数据   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:38:23     
 * @Copyright: 2017
 */
public class CheckBankResponse {

	/**
	 * 状态码
	 */
	private String code;
	/**
	 * 状态描述
	 */
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
