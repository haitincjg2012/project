/**  
 * @Title:  ShareVO.java   
 * @Package com.phshopping.api.controller.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 下午3:21:37   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.vo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @ClassName:  ShareVO   
 * @Description:分享记录  
 * @author: 李杰
 * @date:   2017年7月27日 下午3:21:37     
 * @Copyright: 2017
 */
public class ShareRecordVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 7456736011235725757L;

	private String telPhone;
	
	private String userName;
	
	private Date createTime;

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
