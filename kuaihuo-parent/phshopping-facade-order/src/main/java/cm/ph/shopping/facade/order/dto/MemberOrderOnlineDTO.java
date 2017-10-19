package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：创建线上子订单
 * @作者： 张霞
 * @创建时间： 18:01 2017/5/31
 * @Copyright @2017 by zhangxia
 */
public class MemberOrderOnlineDTO implements Serializable {

    private static final long serialVersionUID = 4217176085699759216L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 订单总金额(包含：商品+物流)
     */
    private Double orderMoney;
    /**
     * 商品总金额
     */
    private Double productMoney;
    /**
     * 物流总费用
     */
    private Double logisticsMoney;
    /**
     * 终端来源
     */
    private Byte terminalUnit;
    /**
     * 支付方式
     */
    private Byte payType;
    /**
     * 发货人id(供应商id)
     */
    private Long supplierId;
    /**
     * 发送地址id
     */
    private Long sendAddressId;
    /**
     *发货地址(即供应商提供的仓库地址)
     */
    private String sendAddressName;

    /**
     * 收货地址省id
     */
    private Long shippingProvinceId;
    /**
     * 收货地址市id
     */
    private Long shippingCityId;
    /**
     * 收货地址区县id
     */
    private Long shippingCountyId;
    /**
     * 收货地址社区id
     */
    private Long shippingTownId;
    /**
     * 会员收货详细地址
     */
    private String shippingAddressDetail;
    /**
     * 会员收货完整地址
     */
    private String shippingAddress;
    /**
     * 收货方式
     */
    private Byte shippingType;
    /**
     * 提货的商户id(选择收货地址信息时，自提方式时地址对应的商户)
     */
    private Long merchantId;
	/**
	 * 提货的商户店名称
	 */
	private String merchantName;
    /**
     * 订单状态
     */
    private Byte status;
    /**
     * 创建人id
     */
    private Long createrId;
    /**
     * 区域id（分润用）
     */
    private Long positionId;
    /**
     * 会员收货人信息ID
     */
    private Long shippingAddressId;
    /**
     * 自提地址详情
     */
    private String takeGoodsAddress;
	/**
	 * 提货点联系人
	 */
	private String takeGoodsName;
	/**
	 * 提货点联系电话
	 */
	private String takeGoodsTelphone;
	/**
	 * 会员收货人名称
	 */
	private String shippingName;
	/**
	 * 会员收货人电话
	 */
	private String shippingTelphone;
	/**
	 * 收货自提省ID
	 */
	private Long takeProvinceId;
	/**
	 * 收货自提省名称
	 */
	private String takeProvinceName;
	/**
	 * 收货自提市/区ID
	 */
	private Long takeCityId;
	/**
	 * 收货自提市/区名称
	 */
	private String takeCityName;
	/**
	 * 收货自提区/县ID
	 */
	private Long takeCountyId;
	/**
	 * 收货自提区/县名称
	 */
	private String takeCountyName;
	/**
	 * 收货自提社区ID
	 */
	private Long takeTownId;
	/**
	 * 收货自提社区名称
	 */
	private String takeTownName;
    /**
     * 订单商品信息
     */
    private List<MemberProductDTO> productDTOS = new ArrayList<>();
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Double getProductMoney() {
		return productMoney;
	}
	public void setProductMoney(Double productMoney) {
		this.productMoney = productMoney;
	}
	public Double getLogisticsMoney() {
		return logisticsMoney;
	}
	public void setLogisticsMoney(Double logisticsMoney) {
		this.logisticsMoney = logisticsMoney;
	}
	public Byte getTerminalUnit() {
		return terminalUnit;
	}
	public void setTerminalUnit(Byte terminalUnit) {
		this.terminalUnit = terminalUnit;
	}
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getSendAddressId() {
		return sendAddressId;
	}
	public void setSendAddressId(Long sendAddressId) {
		this.sendAddressId = sendAddressId;
	}
	public String getSendAddressName() {
		return sendAddressName;
	}
	public void setSendAddressName(String sendAddressName) {
		this.sendAddressName = sendAddressName;
	}
	public Long getShippingProvinceId() {
		return shippingProvinceId;
	}
	public void setShippingProvinceId(Long shippingProvinceId) {
		this.shippingProvinceId = shippingProvinceId;
	}
	public Long getShippingCityId() {
		return shippingCityId;
	}
	public void setShippingCityId(Long shippingCityId) {
		this.shippingCityId = shippingCityId;
	}
	public Long getShippingCountyId() {
		return shippingCountyId;
	}
	public void setShippingCountyId(Long shippingCountyId) {
		this.shippingCountyId = shippingCountyId;
	}
	public Long getShippingTownId() {
		return shippingTownId;
	}
	public void setShippingTownId(Long shippingTownId) {
		this.shippingTownId = shippingTownId;
	}
	public String getShippingAddressDetail() {
		return shippingAddressDetail;
	}
	public void setShippingAddressDetail(String shippingAddressDetail) {
		this.shippingAddressDetail = shippingAddressDetail;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Byte getShippingType() {
		return shippingType;
	}
	public void setShippingType(Byte shippingType) {
		this.shippingType = shippingType;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public Long getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public String getTakeGoodsAddress() {
		return takeGoodsAddress;
	}
	public void setTakeGoodsAddress(String takeGoodsAddress) {
		this.takeGoodsAddress = takeGoodsAddress;
	}
	public List<MemberProductDTO> getProductDTOS() {
		return productDTOS;
	}
	public void setProductDTOS(List<MemberProductDTO> productDTOS) {
		this.productDTOS = productDTOS;
	}
	public Long getTakeProvinceId() {
		return takeProvinceId;
	}
	public void setTakeProvinceId(Long takeProvinceId) {
		this.takeProvinceId = takeProvinceId;
	}
	public Long getTakeCityId() {
		return takeCityId;
	}
	public void setTakeCityId(Long takeCityId) {
		this.takeCityId = takeCityId;
	}
	public Long getTakeCountyId() {
		return takeCountyId;
	}
	public void setTakeCountyId(Long takeCountyId) {
		this.takeCountyId = takeCountyId;
	}
	public Long getTakeTownId() {
		return takeTownId;
	}
	public void setTakeTownId(Long takeTownId) {
		this.takeTownId = takeTownId;
	}

	public String getTakeProvinceName() {
		return takeProvinceName;
	}

	public void setTakeProvinceName(String takeProvinceName) {
		this.takeProvinceName = takeProvinceName;
	}

	public String getTakeCityName() {
		return takeCityName;
	}

	public void setTakeCityName(String takeCityName) {
		this.takeCityName = takeCityName;
	}

	public String getTakeCountyName() {
		return takeCountyName;
	}

	public void setTakeCountyName(String takeCountyName) {
		this.takeCountyName = takeCountyName;
	}

	public String getTakeTownName() {
		return takeTownName;
	}

	public void setTakeTownName(String takeTownName) {
		this.takeTownName = takeTownName;
	}

	public String getShippingTelphone() {
		return shippingTelphone;
	}

	public void setShippingTelphone(String shippingTelphone) {
		this.shippingTelphone = shippingTelphone;
	}

	public String getTakeGoodsName() {
		return takeGoodsName;
	}

	public void setTakeGoodsName(String takeGoodsName) {
		this.takeGoodsName = takeGoodsName;
	}

	public String getTakeGoodsTelphone() {
		return takeGoodsTelphone;
	}

	public void setTakeGoodsTelphone(String takeGoodsTelphone) {
		this.takeGoodsTelphone = takeGoodsTelphone;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
