
package com.alqsoft.entity.hunterservice;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;

/**
 * Date:     2017年2月27日 下午6:59:34 <br/>
 * @author   zhangcan
 * @version  批发商服务
 * @since    JDK 1.8
 * @see 	 
 */

public class HunterService extends IdEntity{
	
	private Hunter hunter;//批发商
	
	private String title;//批发商服务
	
	private String detail;//服务详情

	
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}

