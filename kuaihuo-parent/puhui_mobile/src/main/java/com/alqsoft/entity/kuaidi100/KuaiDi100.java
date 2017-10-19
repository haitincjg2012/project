package com.alqsoft.entity.kuaidi100;

import java.util.List;

/** 
 * 
 * @Title: KuaiDi100.java
 * @Description: 快递100 接口返回实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午8:40:22
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */

public class KuaiDi100<T> {
	
	
	private String message;//消息体
	private String nu;//物流单号
	private int status;//查询结果状态：0：物流单暂无结果，	1：查询成功，2：接口出现异常
	/* state 快递单当前的状态 
		0：在途，即货物处于运输过程中；
		1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
		2：疑难，货物寄送过程出了问题；
		3：签收，收件人已签收；
		4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
		5：派件，即快递正在进行同城派件；
		6：退回，货物正处于退回发件人的途中
		*/
	private int state;
	private String com;//物流公司编号
	private String condition;
	private int ischeck;
	
	private List<KuaiDiData> kuaiDiDataList;//返回的结果集


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getNu() {
		return nu;
	}


	public void setNu(String nu) {
		this.nu = nu;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public String getCom() {
		return com;
	}


	public void setCom(String com) {
		this.com = com;
	}

	public List<KuaiDiData> getKuaiDiDataList() {
		return kuaiDiDataList;
	}


	public void setKuaiDiDataList(List<KuaiDiData> kuaiDiDataList) {
		this.kuaiDiDataList = kuaiDiDataList;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public int getIscheck() {
		return ischeck;
	}


	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}
	
}
