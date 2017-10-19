package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-facade-order
 * @ClassName: OrderOnlineCommentVO
 * @Date: 2017年8月26日上午11:13:40
 * @Desc: 订单下所有评论实体
 */
public class OrderOnlineCommentVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会员头像
	 */
	private String headImage;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 商户id
	 */
	private Long merchantId;

	/**
	 * 商户头像 [门店图片]
	 */
	private String merchantHeadImage;
	/**
	 * 会员昵称
	 */
	private String nikeName;
	/**
	 * 会员名字
	 */
	private String memberName;
	/**
	 * 商户名字
	 */
	private String merchantName;
	/**
	 * 星星数
	 */
	private Integer startNum;
	/**
	 * 评论时间
	 */
	private String created_time;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 展示用户标识  商户 1  会员 2
	 */
	private Integer userType;
	List<CommentVO> cList;

	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getMerchantHeadImage() {
		return merchantHeadImage;
	}
	public void setMerchantHeadImage(String merchantHeadImage) {
		this.merchantHeadImage = merchantHeadImage;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<CommentVO> getcList() {
		return cList;
	}

	public void setcList(List<CommentVO> cList) {
		this.cList = cList;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
}
