/**  
 * @Title:  AppUnbindBankCardDTO.java   
 * @Package com.phshopping.api.controller.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月19日 上午11:26:50   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.dto;

import java.io.Serializable;

/**   
 * @ClassName:  AppUnbindBankCardDTO   
 * @Description:解绑银行卡   
 * @author: 李杰
 * @date:   2017年6月19日 上午11:26:50     
 * @Copyright: 2017
 */
public class AppUnbindBankCardDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4405375426954812802L;
	/**
	 * 请求标识
	 */
	private String token;
	/**
	 * 绑定ID（会员银行卡绑定关系表主键）
	 */
	private Long bindCardId;

	public Long getBindCardId() {
		return bindCardId;
	}

	public void setBindCardId(Long bindCardId) {
		this.bindCardId = bindCardId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
