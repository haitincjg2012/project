/**  
 * @Title:  MemberInfo.java   
 * @Package com.phshopping.api.entity   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月12日 下午3:54:51   
 * @version V1.0 
 * @Copyright: 2017
 */  
package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**   
 * @ClassName:  MemberInfo   
 * @Description:会员数据   
 * @author: 李杰
 * @date:   2017年5月12日 下午3:54:51     
 * @Copyright: 2017
 */
public class MemberInfo implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 52698288476128920L;

	/**
	 * 手机号
	 */
	private String telPhone;
	/**
	 * 姓名
	 */
	private String memberName;
	/**
	 * 性别
	 */
	private Byte sex;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 是否是推广师
	 */
	private Byte isMarketing;
	/**
	 * 状态
	 */
	private Byte status;
	/**
	 * 头像图片 url
	 */
	private String headImage;
	/**
	 * 认证
	 */
	private Byte certification;
	/**
	 * 会员等级
	 */
	private Byte level;
	/**
	 * id
	 */
	private Long id;
	/**
	 * app token
	 */
	private String token;
	/**
	 * 设备ID
	 */
	private String equipmentId;
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public Byte getIsMarketing() {
		return isMarketing;
	}
	public void setIsMarketing(Byte isMarketing) {
		this.isMarketing = isMarketing;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public Byte getCertification() {
		return certification;
	}
	public void setCertification(Byte certification) {
		this.certification = certification;
	}
	public Byte getLevel() {
		return level;
	}
	public void setLevel(Byte level) {
		this.level = level;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	
}
