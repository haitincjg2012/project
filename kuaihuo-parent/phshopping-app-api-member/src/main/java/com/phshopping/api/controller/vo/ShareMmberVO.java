/**  
 * @Title:  MmberVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 上午10:41:19   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;

/**   
 * @ClassName:  MmberVO   
 * @Description:页面会员对象  
 * @author: 李杰
 * @date:   2017年7月27日 上午10:41:19     
 * @Copyright: 2017
 */
public class ShareMmberVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -6168413078523734066L;

	private String telPhone;
	
	private String showTelPhone;
	
	private Long memberId;
	
	private Byte type;

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getShowTelPhone() {
		return showTelPhone;
	}

	public void setShowTelPhone(String showTelPhone) {
		this.showTelPhone = showTelPhone;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
}
