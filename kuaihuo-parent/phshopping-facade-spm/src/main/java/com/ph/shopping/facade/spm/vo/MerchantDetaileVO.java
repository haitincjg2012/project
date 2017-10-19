package com.ph.shopping.facade.spm.vo;


import java.io.Serializable;
import java.util.List;

import com.ph.shopping.facade.spm.entity.MerchantImage;

/**
 * @项目：phshopping-facade-merchant
 *
 * @描述：商户详细VO
 *
 * @作者：Mr.Dong
 *
 * @创建时间：2017年3月10日
 *
 * @Copyright @2017 by Mr.Dong
 */
public class MerchantDetaileVO implements Serializable {
    private static final long serialVersionUID = 5830892117459568239L;
	private Long id;//主键id
	private String companyName;//公司名字
	private String agentName;//上级代理名字
	private String merchantName;//商店名字
	private String personName;//老板的名字
	private String merchantTel;//商户老板电话
	
	private String provinceName;//省
	private String cityName;//市
	private String countyName;//区
	private String townName;//所属社区
		
	private String provinceId;//省
	private String cityId;//市
	private String countyId;//区
	private String townId;//社区id
	
	private String positionId;//区域id
	
	private int  status;//状态
	private String address;//详细地址
	private String promoterName;//推广师名字
	private String promoterTel;//推广师电话
	private String businessNumber;//营业执照编号
	private List<MerchantImage> businesslicenseImages;//营业执照图片
	private List<MerchantImage> idCardImages;//身份证图片
	private List<MerchantImage> shopImages;//门店图片
	private String idCard;//身份证
    private Double businessProfitRatio; //商戶分潤比率
    private Long promoterId;//推广师id
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getMerchantTel() {
		return merchantTel;
	}
	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getPromoterTel() {
		return promoterTel;
	}
	public void setPromoterTel(String promoterTel) {
		this.promoterTel = promoterTel;
	}
	public List<MerchantImage> getBusinesslicenseImages() {
		return businesslicenseImages;
	}
	public void setBusinesslicenseImages(List<MerchantImage> businesslicenseImages) {
		this.businesslicenseImages = businesslicenseImages;
	}
	public List<MerchantImage> getIdCardImages() {
		return idCardImages;
	}
	public void setIdCardImages(List<MerchantImage> idCardImages) {
		this.idCardImages = idCardImages;
	}
	public List<MerchantImage> getShopImages() {
		return shopImages;
	}
	public void setShopImages(List<MerchantImage> shopImages) {
		this.shopImages = shopImages;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Double getBusinessProfitRatio() {
		return businessProfitRatio;
	}
	public void setBusinessProfitRatio(Double businessProfitRatio) {
		this.businessProfitRatio = businessProfitRatio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
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
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public Long getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}
	
}
