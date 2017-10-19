
package com.alqsoft.entity.member;

import javax.persistence.Column;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hunter.Hunter;

/**
 * Date:     2017年2月27日 下午1:43:27 <br/>
 * @author   zhangcan
 * @version  会员表
 * @since    JDK 1.8
 * @see 	 
 */

public class Member extends IdEntity{
	
	private String phone;//手机号
	
	private String password;//密码
	
	private Hunter	hunter;//所关联批发商
	
	private String uuid;//uuid
	
   private Attachment logoAttachment;//上传头像 
	
	private String nickname;//昵称
	
	private String imId;//im
	
	private String imPassword;//
	
	private Long productNum;//收藏商品数量
	
	private Long hunterNum;//收藏批发商数量
	
	private String name;//姓名
	
	private String card;//身份证号
	
	private String token;//注册用户，调用重庆接口返回的数据
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(length=500)
	private String imSign;//im签名
	
	public Long getProductNum() {
		return productNum;
	}

	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}

	public Long getHunterNum() {
		return hunterNum;
	}

	public void setHunterNum(Long hunterNum) {
		this.hunterNum = hunterNum;
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

	public String getImSign() {
		return imSign;
	}

	public void setImSign(String imSign) {
		this.imSign = imSign;
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

	public String getImId() {
		return imId;
	}

	public void setImId(String imId) {
		this.imId = imId;
	}

	public String getImPassword() {
		return imPassword;
	}

	public void setImPassword(String imPassword) {
		this.imPassword = imPassword;
	}

	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}

