
package com.alqsoft.entity.huntercomment;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;

/**
 * Date:     2017年2月27日 下午4:41:57 <br/>
 * @author   zhangcan
 * @version  对批发商的评论
 * @since    JDK 1.8
 * @see 	 
 */

public class HunterComment extends IdEntity{
	
	private String content;//评论的内容
	
	private Hunter hunter;//批发商
	
	private Member member;//会员
	
	private Integer star;//评星数量 0-2 差  3 4中评 5好评
	
	private Integer isOne;//是否评论批发商 批发商详情页面只能评价一次
	
	private HunterComment parent;//父级
	
	private Long replyNum;//回复数量
	
	private Long fabulousNum;//赞的数量
	
	
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


	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	
	public Member getMember() {
		return member;
	}

	public void setMerber(Member merber) {
		this.member = member;
	}

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
	
	
}

