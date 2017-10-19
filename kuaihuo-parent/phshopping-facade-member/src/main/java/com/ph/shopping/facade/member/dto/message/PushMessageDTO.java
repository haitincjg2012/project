/**  
 * @Title:  PushMessageVO.java   
 * @Package com.ph.shopping.facade.member.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月22日 上午10:57:55   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.dto.message;

import java.io.Serializable;
import java.util.List;

import com.ph.shopping.common.core.other.message.jpush.JPushData;

/**   
 * @ClassName:  PushMessageVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月22日 上午10:57:55     
 * @Copyright: 2017
 */
public class PushMessageDTO implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -2828865165549493722L;
	/**
	 * 推送数据
	 */
	private JPushData pushData;
	/**
	 * 推送记录数据
	 */
	private List<MessagePushRecordDTO> list;
	
	public JPushData getPushData() {
		return pushData;
	}
	public void setPushData(JPushData pushData) {
		this.pushData = pushData;
	}
	public List<MessagePushRecordDTO> getList() {
		return list;
	}
	public void setList(List<MessagePushRecordDTO> list) {
		this.list = list;
	}
}
