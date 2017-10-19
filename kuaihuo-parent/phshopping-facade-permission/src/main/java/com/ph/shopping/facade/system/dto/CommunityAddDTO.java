/**  
 * @Title:  CommunityAddDTO.java   
 * @Package com.ph.shopping.facade.system.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午2:39:27   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**   
 * @ClassName:  CommunityAddDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月15日 下午2:39:27     
 * @Copyright: 2017
 */
public class CommunityAddDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 209256691191836087L;

	@NotNull(message = "省ID不能为空")
	private Long provinceId;
	/**
	 * 省名称
	 */
	@NotNull(message = "省名称不能为空")
	private String provinceName;
	/**
	 * 市ID
	 */
	@NotNull(message = "市ID不能为空")
	private Long cityId;
	/**
	 * 市名称
	 */
	@NotNull(message = "市名称不能为空")
	private String cityName;
	/**
	 * 区ID
	 */
	@NotNull(message = "区ID不能为空")
	private Long countyId;
	/**
	 * 区名称
	 */
	@NotNull(message = "区名称不能为空")
	private String countyName;
	/**
	 * 社区名称
	 */
	@NotNull(message = "社区名称不能为空")
	private String townName;
	/**
	 * 创建IP
	 */
	
	private String createdIp;
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getCreatedIp() {
		return createdIp;
	}
	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
}
