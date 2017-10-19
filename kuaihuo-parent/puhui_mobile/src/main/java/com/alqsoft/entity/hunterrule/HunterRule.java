
package com.alqsoft.entity.hunterrule;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;

/**
 * Date:     2017年2月27日 下午7:02:59 <br/>
 * @author   zhangcan
 * @version  批发商法则
 * @since    JDK 1.8
 * @see 	 
 */

public class HunterRule extends IdEntity{
	
	private Hunter hunter;//批发商
	
	private String content;//文本内容
	
	private Integer  isDeleted;//0 null  不删除  1删除

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

