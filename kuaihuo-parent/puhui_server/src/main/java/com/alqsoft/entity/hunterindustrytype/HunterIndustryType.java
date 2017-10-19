
package com.alqsoft.entity.hunterindustrytype;

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
import com.alqsoft.entity.industrytype.IndustryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 批发商与行业分类（标签）关联记录表
 * @author sunhuijie
 *
 * @date 2017年5月22日
 * @used
 */
@Entity
@Table(name = "alq_hunter_industry_type", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class HunterIndustryType extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IndustryType industryType;//二级行业分类
	
	private IndustryType oneLevelIndustryType;//一级行业分类  方便查询
	
	private Hunter hunter;//关联批发商
	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_type_id")
	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "one_level_industry_type_id")
	public IndustryType getOneLevelIndustryType() {
		return oneLevelIndustryType;
	}

	public void setOneLevelIndustryType(IndustryType oneLevelIndustryType) {
		this.oneLevelIndustryType = oneLevelIndustryType;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	 
}

