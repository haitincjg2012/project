
package com.alqsoft.entity.adattachment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alqsoft.entity.hunterrule.HunterRule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月27日 下午7:14:41 <br/>
 * @author   zhangcan
 * @version  首页广告图
 * @since    JDK 1.8
 * @see
 * @used
 */
@Entity
@Table(name = "alq_ad_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class AdAttachment extends IdEntity{
	
	@NotBlank(message = "附件名称不能为空")
	@Length(min = 2, max = 50, message = "附件名称必须在2到50之间，请重新输入")
	private String name;//附件名称
	
	@NotBlank(message = "附件地址不能为空")
	@Length(min = 2, max = 200, message = "附件名称必须在2到200之间，请重新输入")
	private String address;//附件地址
	
	private String detailContent;//详情内容
	
	private String header;//图片头
	
	private Integer type;//区分商户和批发商的轮播图；1开心花  2掌柜  3批发商
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}

