package com.alqsoft.entity.hunterstoreattachment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alqsoft.entity.hunter.Hunter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 批发商店铺信息
 * @author sunhuijie
 *
 * @date 2017年4月18日
 *
 */
public class HunterStoreAttachment extends IdEntity{
	


	private String name;//附件名称
	
	private String address;//附件地址
	
	private String content;//附件详情
	
	private Hunter  hunter ;//批发商法则
	
	private Integer isDelete;//0未删除，1删除
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}

	public Hunter getHunter() {
		return hunter;
	}
	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
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
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
