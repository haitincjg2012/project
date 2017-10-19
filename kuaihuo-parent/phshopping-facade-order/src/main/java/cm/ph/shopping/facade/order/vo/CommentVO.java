package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 *  评论实体
 * Created by wang on 2017/8/30.
 */
public class CommentVO implements Serializable{

    /**
     * 评论id
     */
    private Long id;
    /**
     * 会员昵称
     */
    private String nikeName;
    /**
     * 会员名字
     */
    private String memberName;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 商户名字
     */
    private String merchantName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 展示用户标识  商户 1  会员 2
     */
    private Integer userType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }
}
