package cm.ph.shopping.facade.order.dto;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * @项目：phshopping-facade-order
 * @描述：创建线下订单（后台）
 * @作者： 张霞
 * @创建时间： 14:22 2017/5/26
 * @Copyright @2017 by zhangxia
 */
public class AddMemberOrderUnlineDTO extends BaseValidate{
    /**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -3385182392083477336L;
	/**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    private Double orderMoney;
    /**
     * 支付方式：0 =现金 , 1=积分, 2=支付宝支付, 3=微信支付
     */
    @NotNull(message = "订单支付方式不能为空")
    private Byte payType;
    /**
     * 会员手机号
     */
    private String memberTelphone;
    /**
     * 会员卡内码
     */
    private String memberCardNo;
    /**
     * 商户支付密码：在现金支付方式中填写
     */
    private String payPassWord;
    /**
     * 短信验证码内容：在会员积分支付时填写
     */
    private String verificationCode;
    /**
     * 验证重复提交
     */
    private String token;
    /**
     * 后台商户id（注意是ph_permission_common_user登录信息表中的id）
     */
    @NotNull(message = "[商户id]不能为空")
    private Long merchantId;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 创建人id
     */
    @NotNull(message = "[创建人id]不能为空")
    private Long createrId;

    /**
     * 条形码标识(会员扫码支付时的条形码)
     */
    private String barcodeMark;
	/**
	 * 分享人电话
	 */
	private String proTelPhone;

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Byte getPayType() {
		return payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public String getMemberTelphone() {
		return memberTelphone;
	}

	public void setMemberTelphone(String memberTelphone) {
		this.memberTelphone = memberTelphone;
	}

	public String getMemberCardNo() {
		return memberCardNo;
	}

	public void setMemberCardNo(String memberCardNo) {
		this.memberCardNo = memberCardNo;
	}

	public String getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(String payPassWord) {
		this.payPassWord = payPassWord;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public String getBarcodeMark() {
		return barcodeMark;
	}

	public void setBarcodeMark(String barcodeMark) {
		this.barcodeMark = barcodeMark;
	}

	public String getProTelPhone() {
		return proTelPhone;
	}

	public void setProTelPhone(String proTelPhone) {
		this.proTelPhone = proTelPhone;
	}
}
