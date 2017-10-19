/**  
 * @Title:  CommunityInfoVO.java   
 * @Package com.ph.shopping.facade.system.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月9日 下午1:58:30   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.vo;

import java.io.Serializable;

/**   
 * @ClassName:  CommunityInfoVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年5月9日 下午1:58:30     
 * @Copyright: 2017
 */
public class CommunityInfoVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 9208298860850813896L;
	/**
	 * 社区ID
	 */
	private Long id;
	/**
	 * 省名称
	 */
	private String provinceName;
	/**
	 * 省名称
	 */
	private String cityName;
	/**
	 * 区名称
	 */
	private String countyName;
	/**
	 * 社区名称
	 */
	private String townName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
}
