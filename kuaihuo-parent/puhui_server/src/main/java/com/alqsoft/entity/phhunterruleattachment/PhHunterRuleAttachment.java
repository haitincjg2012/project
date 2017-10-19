package com.alqsoft.entity.phhunterruleattachment;

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
import com.alqsoft.entity.producttrace.ProductTrace;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 系统附件
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-19 上午12:27:45
 * @used
 */
@Entity
@Table(name = "ph_hunter_rule_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class PhHunterRuleAttachment extends IdEntity{
	
	@NotBlank(message = "附件名称不能为空")
	@Length(min = 2, max = 50, message = "附件名称必须在2到50之间，请重新输入")
	private String name;//附件名称
	
	@NotBlank(message = "附件地址不能为空")
	@Length(min = 2, max = 200, message = "附件名称必须在2到200之间，请重新输入")
	private String address;//附件地址
	
	private int type;//类型
	
	private Long hunterId;//批发商Id
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getHunterId() {
		return hunterId;
	}
	public void setHunterId(Long hunterId) {
		this.hunterId = hunterId;
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
	
	
}
