/**  
 * @Title:  AddHeadhuntingDTO.java   
 * @Package com.ph.member.api.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月7日 下午4:01:14   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.ph.shopping.common.core.base.BaseValidate;

/**   
 * @ClassName:  AddHeadhuntingDTO   
 * @Description:添加批发商传输数据
 * @author: 李杰
 * @date:   2017年7月7日 下午4:01:14     
 * @Copyright: 2017
 */
public class AddHeadhuntingDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -7234498050876666866L;
	/**
	 * 昵称
	 */
	@NotBlank(message="[昵称]不能为空")
	private String nickName;
	/**
	 * 账号
	 */
	@NotBlank(message="[账号]不能为空")
	private String accountNumber;
	/**
	 * 服务
	 */
	@NotBlank(message="[服务]不能为空")
	private String service;
	/**
	 * 专业
	 */
	@NotBlank(message="[专业]不能为空")
	private String specialty;
	/**
	 * 省ID
	 */
	@NotNull(message="[省ID]不能为空")
	private Long provinceId;
	/**
	 * 省名称
	 */
	@NotBlank(message="[省名称]不能为空")
	private String provinceName;
	/**
	 * 市ID
	 */
	@NotNull(message="[市ID]不能为空")
	private Long cityId;
	/**
	 * 市名称
	 */
	@NotBlank(message="[市名称]不能为空")
	private String cityName;
	/**
	 * 区ID
	 */
	@NotNull(message="[区ID]不能为空")
	private Long areaId;
	/**
	 * 区名称
	 */
	@NotBlank(message="[区名称]不能为空")
	private String areaName;
	/**
	 * 社区ID
	 */
	private Long communityId;
	/**
	 * 社区名称
	 */
	private String communityName;
	/**
	 * 经度
	 */
	@NotBlank(message="[经度]不能为空")
	private String longitude;
	/**
	 * 纬度
	 */
	@NotBlank(message="[纬度]不能为空")
	private String latitude;
	/**
	 * 详细地址
	 */
	@NotBlank(message="[详细地址]不能为空")
	private String detatilAddress;
	/**
	 * 头像地址
	 */
	@NotBlank(message="[头像地址]不能为空")
	private String headImgUrl;
	/**
	 * 批发商相关标签
	 */
	@NotEmpty(message="[标签]不能为空")
	private List<HeadHuntingTagDTO> tags;
	/**
	 * 工会ID
	 */
	private String laborUnionId;
	/**
	 * 工会名称
	 */
	private String laborUnionName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
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
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getDetatilAddress() {
		return detatilAddress;
	}
	public void setDetatilAddress(String detatilAddress) {
		this.detatilAddress = detatilAddress;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public List<HeadHuntingTagDTO> getTags() {
		return tags;
	}
	public void setTags(List<HeadHuntingTagDTO> tags) {
		this.tags = tags;
	}
	public String getLaborUnionId() {
		return laborUnionId;
	}
	public void setLaborUnionId(String laborUnionId) {
		this.laborUnionId = laborUnionId;
	}
	public String getLaborUnionName() {
		return laborUnionName;
	}
	public void setLaborUnionName(String laborUnionName) {
		this.laborUnionName = laborUnionName;
	}
	
}
