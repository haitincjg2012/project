/**  
 * @Title:  SynAddHeadhuntingVO.java   
 * @Package com.ph.member.api.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月11日 下午3:28:47   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo.syn;

import java.io.Serializable;

/**   
 * @ClassName:  SynAddHeadhuntingVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月11日 下午3:28:47     
 * @Copyright: 2017
 */
public class SynAddHeadhuntingVO implements Serializable{

	public static final String SUCCESS = "1";
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -4599747311834391677L;
	/**
	 * 响应编码1、成功 0、失败
	 */
	private String code;
	/**
	 * 描述
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
	
	public boolean isSuccess(){
		return SUCCESS.equals(this.getCode());
	}
}
