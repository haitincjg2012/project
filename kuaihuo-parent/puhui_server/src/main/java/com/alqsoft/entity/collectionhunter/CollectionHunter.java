package com.alqsoft.entity.collectionhunter;

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
import com.alqsoft.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月10日
 *
 *	收藏批发商记录
 * @used
 */
@Entity
@Table(name = "alq_collection_hunter", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class CollectionHunter  extends IdEntity{

	private Member member;//谁收藏的
	
	private Hunter hunter;//收藏的那个批发商
	
	private Integer type;//0收藏   1取消收藏
	
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	 

	public Integer getType() {
		return type;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
