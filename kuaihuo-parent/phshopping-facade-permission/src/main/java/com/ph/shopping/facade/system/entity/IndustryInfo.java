/**  
 * @Title:  IndustryInfo.java   
 * @Package com.ph.shopping.facade.system.entity   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午3:27:59   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**   
 * @ClassName:  IndustryInfo   
 * @Description:行业实体   
 * @author: 李杰
 * @date:   2017年5月8日 下午3:27:59     
 * @Copyright: 2017
 */
@Table(name = "ph_industry_info")
public class IndustryInfo extends BaseEntity{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2410862806404712606L;

	/**
	 * 行业名称
	 */
	@Column(name = "industryName")
	private String industryName;
	/**
	 * 序号
	 */
	@Column(name = "industryNum")
	private Integer industryNum;
	/**
	 * 图标地址
	 */
	@Column(name = "industryImgUrl")
	private String industryImgUrl;
	/**
	 * 上级行业ID
	 */
	@Column(name = "superIndustryId")
	private Long superIndustryId;
	/**
	 * 级别
	 */
	@Column(name = "level")
	private Integer level;
	/**
	 * 状态
	 */
	@Column(name = "status")
	private Byte status;
	/**
	 * 行业描述
	 */
	@Column(name = "description")
	private String description;
	
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public Integer getIndustryNum() {
		return industryNum;
	}
	public void setIndustryNum(Integer industryNum) {
		this.industryNum = industryNum;
	}
	public String getIndustryImgUrl() {
		return industryImgUrl;
	}
	public void setIndustryImgUrl(String industryImgUrl) {
		this.industryImgUrl = industryImgUrl;
	}
	public Long getSuperIndustryId() {
		return superIndustryId;
	}
	public void setSuperIndustryId(Long superIndustryId) {
		this.superIndustryId = superIndustryId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
