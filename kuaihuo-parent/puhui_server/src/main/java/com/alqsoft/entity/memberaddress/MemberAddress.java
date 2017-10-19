
package com.alqsoft.entity.memberaddress;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.alqsoft.entity.area.Area;
import com.alqsoft.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2016年11月3日 下午10:11:49 <br/>
 * @author   zhangcan
 * @version  会员收获地址
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_member_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class MemberAddress extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Area proArea;//所属省
	
	private Area cityArea;//所属市
	
	private String detailAddress;//详细地址
	
	private String code;//邮政编码
	
	private String mobile;//手机号
	
	private String userName;//收货人名字
	
	private Member member;//会员
	
	private int isDefault;//是否默认地址
	
	private int isDelete;//是否删除 0未删除  1删除了
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_area_id")
	@ForeignKey(name="")
	public Area getProArea() {
		return proArea;
	}

	public void setProArea(Area proArea) {
		this.proArea = proArea;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_area_id")
	@ForeignKey(name="")
	public Area getCityArea() {
		return cityArea;
	}

	public void setCityArea(Area cityArea) {
		this.cityArea = cityArea;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@ForeignKey(name="")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}

