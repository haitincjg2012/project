
package com.alqsoft.entity.hunterservice;

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
 * Date:     2017年2月27日 下午6:59:34 <br/>
 * @author   zhangcan
 * @version  批发商服务
 * @since    JDK 1.8
 * @see 	（ 图片  分割线  文字  ） 货源服务   批发商法则
 * @used
 */
@Entity
@Table(name = "alq_hunter_service", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class HunterService extends IdEntity{
	
	private Hunter hunter;//批发商
	
	private String title;//批发商服务
	
	private String detail;//服务详情

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}

