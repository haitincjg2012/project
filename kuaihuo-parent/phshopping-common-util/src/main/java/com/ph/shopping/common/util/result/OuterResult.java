package com.ph.shopping.common.util.result;

import java.io.Serializable;

/**
 * 
 * @ClassName: OuterResult
 * @Description: 外部调用返回结果系
 * @author tony
 * @date 2017年3月25日
 *
 */
public class OuterResult implements Serializable {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 返回状态码 1 为成功 0为失败
	 */
	private String code;
	/**
	 * 结果描述
	 */
	private String msg;
	/**
	 * 返回结果数据
	 */
	private Object content;
	
	/**
	 * token值
	 */
	private String token;
	
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
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
