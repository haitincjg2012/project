package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionCountyVo
* @Description:区县VO
* @author 王强
* @date 2017年4月25日 下午5:00:02
 */
public class PositionCountyVO implements Serializable {
	
	private static final long serialVersionUID = -7838159727921037206L;
	private long id;//主键id
	
	private long countyId;//区县id
	private String countyName;//区县名称
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCountyId() {
		return countyId;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	
}
