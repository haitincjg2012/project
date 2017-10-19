/**  
 * @Title:  SynHeadhuntingVO.java   
 * @Package com.ph.member.api.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月11日 下午3:40:36   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo.syn;

import java.io.Serializable;

/**   
 * @ClassName:  SynHeadhuntingVO   
 * @Description:批发商查询返回信息
 * @author: 李杰
 * @date:   2017年7月11日 下午3:40:36     
 * @Copyright: 2017
 */
public class SynHeadhuntingVO implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8074195953312038578L;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 编码描述
	 */
	private String msg;
	/**
	 * 详细内容
	 */
	private SynHeadhuntingContent content;
	
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
	public SynHeadhuntingContent getContent() {
		return content;
	}
	public void setContent(SynHeadhuntingContent content) {
		this.content = content;
	}
}
