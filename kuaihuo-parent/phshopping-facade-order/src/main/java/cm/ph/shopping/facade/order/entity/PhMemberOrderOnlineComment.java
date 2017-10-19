
package cm.ph.shopping.facade.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Date:     2017年8月21日 下午6:44:50 <br/>
 * @author   chen
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Table(name = "ph_member_order_online_comment")
public class PhMemberOrderOnlineComment implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8072346889549010090L;

	@Id
	private Long id;
	@Column(name = "created_time")
	private Date created_time;
	@Column(name = "update_time")
	private Date update_time;
	@Column(name = "orderId")
	private Long orderId;//订单
	@Column(name = "memberId")
	private Long memberId;//会员
	@Column(name = "content")
	private String content;//评论的内容
	@Column(name = "parentId")
	private Long parentId;//父级
	@Column(name = "replyNum")
	private Long replyNum;//回复数量
	@Column(name = "fabulousNum")
	private Long fabulousNum;//赞的数量
	@Column(name = "startNum")
	private Integer startNum;//星星数量
	@Column(name = "isOne")
	private Integer isOne;//是否回复该评论   0或null未回复，1为已回复  只能给父级评论回复一次
	@Column(name = "merchantId")
	private Long merchantId;//商户
	@Column(name = "isDelete")
	private Integer isDelete;//0或null未删，1删除
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}

