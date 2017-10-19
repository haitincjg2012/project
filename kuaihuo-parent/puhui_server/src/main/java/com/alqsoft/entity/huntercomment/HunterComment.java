
package com.alqsoft.entity.huntercomment;

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
 * Date:     2017年2月27日 下午4:41:57 <br/>
 * @author   zhangcan
 * @version  对批发商的评论
 * @since    JDK 1.8
 * @see 	 
 */
@Entity
@Table(name = "alq_hunter_comment", indexes = {})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class HunterComment extends IdEntity{
	
	private String content;//评论的内容
	
	private Hunter hunter;//批发商
	
	private Member member;//会员
	
	private Integer star;//评星数量 0-2 差  3 4中评 5好评
	
	private Integer isOne;//是否评论批发商 批发商详情页面只能评价一次   0或null未回复，1为已回复  只能给批发商回复一次
	
	private HunterComment parent;//父级
	
	private Long replyNum;//回复数量
	
	private Long fabulousNum;//赞的数量
	

	public Long getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Long replyNum) {
		this.replyNum = replyNum;
	}

	public Long getFabulousNum() {
		return fabulousNum;
	}

	public void setFabulousNum(Long fabulousNum) {
		this.fabulousNum = fabulousNum;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public HunterComment getParent() {
		return parent;
	}

	public void setParent(HunterComment parent) {
		this.parent = parent;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getIsOne() {
		return isOne;
	}

	public void setIsOne(Integer isOne) {
		this.isOne = isOne;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "hunter_id")
	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMerber(Member merber) {
		this.member = member;
	}
}

