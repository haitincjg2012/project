/**  
 * @Title:  IndustryInfoVO.java   
 * @Package com.ph.shopping.facade.system.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午3:44:09   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**   
 * @ClassName:  IndustryInfoVO   
 * @Description:行业返回数据  
 * @author: 李杰
 * @date:   2017年5月8日 下午3:44:09     
 * @Copyright: 2017
 */
public class IndustryInfoVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 2019790093135958068L;

	/**
	 * 行业ID
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
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 修改时间
	 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
	/**
	 * 商户数量
	 */
	private Integer merchantNum;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getMerchantNum() {
		return merchantNum;
	}
	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}
	
}
