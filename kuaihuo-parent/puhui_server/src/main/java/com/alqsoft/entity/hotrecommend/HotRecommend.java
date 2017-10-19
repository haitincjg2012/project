
package com.alqsoft.entity.hotrecommend;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Date:     2017年2月28日 下午6:47:35 <br/>
 * @author   zhangcan
 * @version  热门推荐
 * @since    JDK 1.8
 * @see 	 
 */
@Entity
@Table(name = "alq_hotrecommend", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class HotRecommend extends IdEntity{
	
	private String name;//热门分类名称
	
	private Integer isDel;//是否删除
	
	

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

