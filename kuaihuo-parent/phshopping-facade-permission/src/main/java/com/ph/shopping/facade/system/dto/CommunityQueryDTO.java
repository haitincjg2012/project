/**  
 * @Title:  CommunityInfoDTO.java   
 * @Package com.ph.shopping.facade.system.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午1:55:42   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.dto;

import com.ph.shopping.common.core.base.BaseValidate;

/**   
 * @ClassName:  CommunityInfoDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月15日 下午1:55:42     
 * @Copyright: 2017
 */
public class CommunityQueryDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 6339462233217702258L;
	/**
	 * 省ID
	 */
	private Long provinceId;
	/**
	 * 市ID
	 */
	private Long cityId;
	/**
	 * 区ID
	 */
	private Long countyId;
	/**
	 * 社区名称
	 */
	private String townName;
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
}
