
package com.ph.shopping.facade.spm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午1:43:27 <br/>
 * @author   zhangcan
 * @version  会员表
 * @since    JDK 1.8
 * @see 	 
 */
@Table(name = "alq_member")
public class MerchantUuid implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3296550028902740197L;

	@Id
	private Long id;
	@Column(name = "created_time")
	private Date created_time;
	@Column(name = "update_time")
	private Date update_time;
	@Column(name = "phone")
	private String phone;//手机号
	@Column(name = "password")
	private String password;//密码
	@Column(name = "uuid")
	private String uuid;//uuid
	@Column(name = "logo_attachment_id")
	private Long logoAttachmentId;//上传头像
	@Column(name = "nickname")
	private String nickname;//昵称
	@Column(name = "product_num")
	private Long productNum;//收藏商品数量
	@Column(name = "hunter_num")
	private Long hunterNum;//收藏批发商数量
	@Column(name = "im_id")
	private String imId;//注册id
	@Column(name = "im_password")
	private String imPassword;//注册密码
	@Column(name = "name")
	private String name;//姓名
	@Column(name = "card")
	private String card;//身份证号
	@Column(name = "token")
	private String token;//注册用户，调用重庆接口返回的数据
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	
	private String imSign;//im签名
	 @Column(columnDefinition = "varchar(500) ")
	public String getImSign() {
		return imSign;
	}

	public void setImSign(String imSign) {
		this.imSign = imSign;
	}

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
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

