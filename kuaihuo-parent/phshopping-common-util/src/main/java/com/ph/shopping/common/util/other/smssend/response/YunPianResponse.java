/**  
 * @Title:  YunPianResponse.java   
 * @Package com.ph.shopping.common.util.other.smssend.response   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 上午10:11:22   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.other.smssend.response;

import java.io.Serializable;

/**   
 * @ClassName:  YunPianResponse   
 * @Description:yunpian短信发送响应数据   
 * @author: 李杰
 * @date:   2017年5月11日 上午10:11:22     
 * @Copyright: 2017
 */
public class YunPianResponse implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2040307989712199933L;

	/**
	 * 0 代表发送成功，其他code代表出错，详细见"返回值说明"页面
	 */
	private Integer code;
	/**
	 * 例如""发送成功""，或者相应错误信息
	 */
	private String msg;
	/**
	 * 发送成功短信的计费条数(计费条数：70个字一条，超出70个字时按每67字一条计费)
	 */
	private Integer count;
	/**
	 * 扣费金额，单位：元，类型：双精度浮点型/double
	 */
	private Double fee;
	/**
	 * 短信id，64位整型， 对应Java和C#的Long，不可用int解析
	 */
	private Long sid;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	
}
