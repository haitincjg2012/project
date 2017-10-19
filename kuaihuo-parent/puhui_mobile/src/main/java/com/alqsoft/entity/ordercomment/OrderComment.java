
package com.alqsoft.entity.ordercomment;

import org.alqframework.orm.hibernate.IdEntity;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.product.Product;

/**
 * Date:     2017年2月27日 下午6:44:50 <br/>
 * @author   zhangcan
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

public class OrderComment extends IdEntity{
	
	private Order order;//订单
	
	private Product product;//商品
	
	private Member member;//会员
	
	private String content;//评论的内容
	
	private OrderComment parent;//父级
	
	private Long replyNum;//回复数量
	
	private Long fabulousNum;//赞的数量
	
	private Integer startNum;//星星数量
	
	private Integer isOne;//是否回复该评论   0或null未回复，1为已回复  只能给父级评论回复一次
	
	private Hunter hunter;//批发商
	
	private Integer isDelete;//0或null未删，1删除

	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Member getMember() {
		return member;
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
	
	public OrderComment getParent() {
		return parent;
	}

	public void setParent(OrderComment parent) {
		this.parent = parent;
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

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getIsOne() {
		return isOne;
	}

	public void setIsOne(Integer isOne) {
		this.isOne = isOne;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}
	
	
}

