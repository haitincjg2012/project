package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: AreaVo
* @Description:区域VO
* @author 王强
* @date 2017年4月25日 下午4:58:33
 */
public class AreaVO implements Serializable {
	private static final long serialVersionUID = 8233083467492272833L;

	/**区域id*/
	private Long areaId;
	
    /** 名称 */
    private String areaName;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}