/**  
 * @Title:  IndustryInfoDTO.java   
 * @Package com.ph.shopping.facade.system.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午3:38:15   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.shopping.facade.system.dto;

import java.io.Serializable;

/**
 * @ClassName: IndustryInfoDTO
 * @Description:行业相关查询信息
 * @author: 李杰
 * @date: 2017年5月8日 下午3:38:15
 * @Copyright: 2017
 */
public class IndustryInfoDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -5233798424897905993L;

	/**
	 * 行业id
	 */
	private Long id;
	/**
	 * 行业名称
	 */
	private String industryName;
	/**
	 * 序号
	 */
	private Integer industryNum;
	/**
	 * 图标地址
	 */
	private String industryImgUrl;
	/**
	 * 上级行业ID
	 */
	private Long superIndustryId;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 状态
	 */
	private Byte status;
	/**
	 * 行业描述
	 */
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
