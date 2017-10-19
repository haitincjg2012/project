package cm.ph.shopping.facade.order.dto;

import com.ph.shopping.common.core.base.BaseEntityForToken;

import java.io.Serializable;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-facade-order
 * @ClassName: CommentDTO
 * @Date: 2017年8月24日上午9:29:51
 * @Desc: 评论传参实体
 */

public class CommentDTO extends BaseEntityForToken implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 评论ID
	 */
	private Long commentid;
	/**
	 * 订单编号
	 */
	private Long orderid;
	/**
	 * 会员ID
	 */
	private Long memberid;
	/**
	 * 商户ID
	 */
	private Long merchantid;
	/**
	 * 星星数
	 */
	private Integer start;
	/**
	 * 评论内容
	 */
	private Long parentid;
	private String content;
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(Long merchantid) {
		this.merchantid = merchantid;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public Long getCommentid() {
		return commentid;
	}
	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}
}
