package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: PositionProvinceVo
* @Description: 省VO
* @author 王强
* @date 2017年4月25日 下午5:00:54
 */
public class PositionProvinceVO implements Serializable {
	
	private static final long serialVersionUID = -7838159727921037206L;
	private long id;//主键id
	private long provinceId;//省id
	private String provinceName;//省名称
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
