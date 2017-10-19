package com.alqsoft.entity.kuaidi100;

/** 
 * 
 * @Title: KuaiDiData.java
 * @Description:快递100显示的内容体
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午8:40:33
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */

public class KuaiDiData {
	
	private String time;//每条数据的时间
	private String context;//每条数据对应的内容
	
	private String ftime;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	
}
