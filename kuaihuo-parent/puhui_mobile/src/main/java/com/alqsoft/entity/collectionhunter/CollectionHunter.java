package com.alqsoft.entity.collectionhunter;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月10日
 *
 *	收藏批发商记录
 */

public class CollectionHunter  extends IdEntity{

	private Member member;//谁收藏的
	
	private Hunter hunter;//收藏的那个批发商
	
	private Integer type;//0收藏   1取消收藏
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	 

	public Integer getType() {
		return type;
	}

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
