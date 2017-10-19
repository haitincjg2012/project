
package com.alqsoft.entity.hunter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.area.Area;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;

/**
 * Date: 2017年2月27日 下午1:48:11 <br/>
 * 
 * @author zhangcan
 * @version 批发商
 * @since JDK 1.8
 * @see
 */

public class Hunter extends IdEntity implements Serializable {

	/**
	 * private String phone;//手机号 2017年3月6日15:32:34 陈金鼎 注释原因 与member表phone作用重复
	 */
	private static final long serialVersionUID = 1L;

	private String name;// 真实姓名

	private String card;// 身份证号

	private Attachment logoAttachment;// 上传头像

	private String nickname;// 昵称

	private Area provinceArea;// 所属省

	private Area cityArea;// 所属市

	private String longitude;// 经度

	private String latitude;// 维度

	private String station;// 驻地区域 经纬度定位

	private Long phTownId;// 乡镇 街道办事处编号

	private Long phProvinceId;// 省编号

	private Long phCityId;// 市编号

	private Long phCountyId;// 区县编号

	private String provinceName;// 省

	private String cityName;// 市

	private String countyName;// 县

	private String townName;// 乡镇

	private String detail;// 详细地址

	private Integer positionLevel;// 当前用户级别 0省代理 1市代理 2县代理 3区代理

	private String service;// 批发商服务

	private String serviceDigest;// 批发商服务摘要 用于列表头部展示

	private IndustryType industryType;// 行业分类

	private IndustryAssociation industryAssociation;// 行业协会

	private Integer isValid;// 是否经过安全验证0 null否 1已经验证过

	private Integer star;// 星际 1 星 2 2星 。。。。。5 5星 批发商星级

	private Integer level;// 等级 0 null 大众 1高级 2专家 3顶级 批发商等级

	private Long incomeAllMoney;// 收入总金额

	private Long haveDepositMoney;// 已提现金额

	private Long leftDepositMoney;// 剩下可提现总金额5

	private Long badCommentNum;// 差评总数 对批发商的差评数量

	private Long CommentNum;// 中评总数 对批发商的中评

	private Long goodCommentNum;// 好评总数 对批发商的好评

	private Long num;// 已完成订单量或者也叫多少人找他

	private Integer isSubject;// 是否属于专题分类

	private String phone;

	private Long badCommentNumOrder; // 该批发商订单的好评数

	private Long commentNumOrder; // 该批发商订单的中评数

	private Long goodCommentNumOrder; // 该批发商订单的好评数

	private String major;// 专业信息

	private Long orderMoney; // 该批发商的订单总额

	private String sourcingService;// 货源服务

	private Attachment backgroundImage;// 店铺背景图片

	private Integer state;// 测试标记 null、非0展示 | 0不展示

	private String agreeStartTime; //接单开始时间
	private String agreeEndTime; //接单关闭时间

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAgreeStartTime() {
		return agreeStartTime;
	}

	public void setAgreeStartTime(String agreeStartTime) {
		this.agreeStartTime = agreeStartTime;
	}

	public String getAgreeEndTime() {
		return agreeEndTime;
	}

	public void setAgreeEndTime(String agreeEndTime) {
		this.agreeEndTime = agreeEndTime;
	}

	public String getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public String getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public Long getBeiHuoTime() {
		return beiHuoTime;
	}

	public void setBeiHuoTime(Long beiHuoTime) {
		this.beiHuoTime = beiHuoTime;
	}

	public Long getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(Long startMoney) {
		this.startMoney = startMoney;
	}

	public Date getCloseStartTime() {
		return closeStartTime;
	}

	public void setCloseStartTime(Date closeStartTime) {
		this.closeStartTime = closeStartTime;
	}

	public Date getCloseEndTime() {
		return closeEndTime;
	}

	public void setCloseEndTime(Date closeEndTime) {
		this.closeEndTime = closeEndTime;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getDistrictType() {
		return districtType;
	}

	public void setDistrictType(Long districtType) {
		this.districtType = districtType;
	}

	public String getDistricts() {
		return districts;
	}

	public void setDistricts(String districts) {
		this.districts = districts;
	}

	private String sendStartTime; //发货开始时间
	private String sendEndTime; //发货关闭时间

	private Long beiHuoTime; // 备货时间
	private Long startMoney; // 起配金额

	private Date closeStartTime;//打烊开始时间
	private Date closeEndTime;// 打烊结束时间


	private Long districtId; //区域id
	private Long districtType; //  1省级  2市级  3县级  4区域
	private String districts; //区域id集合

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Integer getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	public Attachment getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Attachment backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Attachment getLogoAttachment() {
		return logoAttachment;
	}

	public void setLogoAttachment(Attachment logoAttachment) {
		this.logoAttachment = logoAttachment;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Area getProvinceArea() {
		return provinceArea;
	}

	public void setProvinceArea(Area provinceArea) {
		this.provinceArea = provinceArea;
	}

	public Area getCityArea() {
		return cityArea;
	}

	public void setCityArea(Area cityArea) {
		this.cityArea = cityArea;
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

	public Long getPhTownId() {
		return phTownId;
	}

	public void setPhTownId(Long phTownId) {
		this.phTownId = phTownId;
	}

	public Long getPhProvinceId() {
		return phProvinceId;
	}

	public void setPhProvinceId(Long phProvinceId) {
		this.phProvinceId = phProvinceId;
	}

	public Long getPhCityId() {
		return phCityId;
	}

	public void setPhCityId(Long phCityId) {
		this.phCityId = phCityId;
	}

	public Long getPhCountyId() {
		return phCountyId;
	}

	public void setPhCountyId(Long phCountyId) {
		this.phCountyId = phCountyId;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServiceDigest() {
		return serviceDigest;
	}

	public void setServiceDigest(String serviceDigest) {
		this.serviceDigest = serviceDigest;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}

	public IndustryAssociation getIndustryAssociation() {
		return industryAssociation;
	}

	public void setIndustryAssociation(IndustryAssociation industryAssociation) {
		this.industryAssociation = industryAssociation;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getIncomeAllMoney() {
		return incomeAllMoney;
	}

	public void setIncomeAllMoney(Long incomeAllMoney) {
		this.incomeAllMoney = incomeAllMoney;
	}

	public Long getHaveDepositMoney() {
		return haveDepositMoney;
	}

	public void setHaveDepositMoney(Long haveDepositMoney) {
		this.haveDepositMoney = haveDepositMoney;
	}

	public Long getLeftDepositMoney() {
		return leftDepositMoney;
	}

	public void setLeftDepositMoney(Long leftDepositMoney) {
		this.leftDepositMoney = leftDepositMoney;
	}

	public Long getBadCommentNum() {
		return badCommentNum;
	}

	public void setBadCommentNum(Long badCommentNum) {
		this.badCommentNum = badCommentNum;
	}

	public Long getCommentNum() {
		return CommentNum;
	}

	public void setCommentNum(Long commentNum) {
		CommentNum = commentNum;
	}

	public Long getGoodCommentNum() {
		return goodCommentNum;
	}

	public void setGoodCommentNum(Long goodCommentNum) {
		this.goodCommentNum = goodCommentNum;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Integer getIsSubject() {
		return isSubject;
	}

	public void setIsSubject(Integer isSubject) {
		this.isSubject = isSubject;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getBadCommentNumOrder() {
		return badCommentNumOrder;
	}

	public void setBadCommentNumOrder(Long badCommentNumOrder) {
		this.badCommentNumOrder = badCommentNumOrder;
	}

	public Long getCommentNumOrder() {
		return commentNumOrder;
	}

	public void setCommentNumOrder(Long commentNumOrder) {
		this.commentNumOrder = commentNumOrder;
	}

	public Long getGoodCommentNumOrder() {
		return goodCommentNumOrder;
	}

	public void setGoodCommentNumOrder(Long goodCommentNumOrder) {
		this.goodCommentNumOrder = goodCommentNumOrder;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getSourcingService() {
		return sourcingService;
	}

	public void setSourcingService(String sourcingService) {
		this.sourcingService = sourcingService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
