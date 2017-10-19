/**  
 * @Title:  CommunityInfo.java   
 * @Package com.ph.shopping.facade.system.entity   
 * @Description:    社区实体   
 * @author: 李杰    
 * @date:   2017年5月9日 上午11:43:30   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.shopping.facade.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: CommunityInfo
 * @Description:社区实体
 * @author: 李杰
 * @date: 2017年5月9日 上午11:43:30
 * @Copyright: 2017
 */
@Table(name = "ph_position")
public class CommunityInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1838012498381627067L;

	@Id
	private Long id;
	/**
	 * 省ID
	 */
	@Column(name = "provinceId")
	private Long provinceId;
	/**
	 * 省名称
	 */
	@Column(name = "provinceName")
	private String provinceName;
	/**
	 * 市ID
	 */
	@Column(name = "cityId")
	private Long cityId;
	/**
	 * 市名称
	 */
	@Column(name = "cityName")
	private String cityName;
	/**
	 * 区ID
	 */
	@Column(name = "countyId")
	private Long countyId;
	/**
	 * 区名称
	 */
	@Column(name = "countyName")
	private String countyName;
	/**
	 * 创建人
	 */
	@Column(name = "createUser")
	private Long createUser;
	/**
	 * 创建ID
	 */
	@Column(name = "createdIp")
	private String createdIp;
	/**
	 * 创建时间
	 */
	@Column(name = "createdTime")
	private Date createdTime;
	/**
	 * 修改时间
	 */
	@Column(name = "updateTime")
	private Date updateTime;
	/**
	 * 镇id
	 */
	@Column(name = "townId")
	private Long townId;
	/**
	 * 镇名称
	 */
	@Column(name = "townName")
	private String townName;

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

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedIp() {
		return createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

}
