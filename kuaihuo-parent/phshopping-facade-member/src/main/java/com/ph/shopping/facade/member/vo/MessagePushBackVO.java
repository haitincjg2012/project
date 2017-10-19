/**  
 * @Title:  MessagePushBackVO.java   
 * @Package com.ph.shopping.facade.member.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月20日 下午3:44:22   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  MessagePushBackVO   
 * @Description:添加推送数据返回信息   
 * @author: 李杰
 * @date:   2017年6月20日 下午3:44:22     
 * @Copyright: 2017
 */
public class MessagePushBackVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 5067474815673700467L;
	/**
	 * 消息推送记录ID
	 */
	private List<Long> pushDataIds;
	/**
	 * 用户ID 和消息记录ID
	 */
	private Map<String, String> userIdAndDataId;
	
	public List<Long> getPushDataIds() {
		return pushDataIds;
	}
	public void setPushDataIds(List<Long> pushDataIds) {
		this.pushDataIds = pushDataIds;
	}
	public Map<String, String> getUserIdAndDataId() {
		return userIdAndDataId;
	}
	public void setUserIdAndDataId(Map<String, String> userIdAndDataId) {
		this.userIdAndDataId = userIdAndDataId;
	}
	
}
