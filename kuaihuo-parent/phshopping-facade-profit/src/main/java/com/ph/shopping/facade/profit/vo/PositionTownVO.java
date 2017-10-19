package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionTownVo
* @Description: 乡镇VO
* @author 王强
* @date 2017年4月25日 下午5:01:30
 */
public class PositionTownVO implements Serializable {

	private static final long serialVersionUID = 410309962280838223L;
	private long id;//主键id
	private long townId;//乡镇id
	private String townName;//乡镇名称

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
