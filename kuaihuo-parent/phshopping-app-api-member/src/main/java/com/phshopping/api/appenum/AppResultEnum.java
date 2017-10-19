/**  
 * @Title:  ErrorEnum.java   
 * @Package com.phshopping.api.appenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月10日 上午11:22:18   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.appenum;

import com.ph.shopping.common.util.core.RespCode;

/**   
 * @ClassName:  ErrorEnum   
 * @Description:app 错误枚举值   
 * @author: 李杰
 * @date:   2017年5月10日 上午11:22:18     
 * @Copyright: 2017
 */
public enum AppResultEnum implements RespCode{

	SERVER_ERROR("500", "服务器繁忙，请稍后再试！"),
	USERNOTEXIST("AP1003","当前用户已其它设备上登录"),
	NOHANDLER("404","访问的资源不存在！"),
	PASSWORD_FORMAT_FAIL("AP1005","密码格式错误【以字母开头，只能包含英文字母、数字和符号，长度6-16位】"),
	PASSWORD_FORMAT_FAIL2("AP1007","密码格式错误【只能包含英文字母、数字和符号，长度6-16位】"),
	QUERY_TYPE_ERROR("AP1006","查询类型错误");
	;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 错误描述
	 */
	private String msg;
	
	private AppResultEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

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
