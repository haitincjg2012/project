package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-order
 * @描述：会员线上子订单表中订单的商品信息
 * @作者： 张霞
 * @创建时间： 15:00 2017/5/24
 * @Copyright @2017 by zhangxia
 */
@Table(name = "ph_member_sub_order_online_products")
public class PhMemberSubOrderOnlineProducts implements Serializable{

    private static final long serialVersionUID = -2282197982321131649L;
    /** 主键id
     *
     */
    @Id
    private Long id;
    /** 商品快照id
     *
     */
    @Column(name = "productSnapId")
    private Long productSnapId;
    /** 商品id
     *
     */
    @Column(name = "productId")
    private Long productId;
    /**
     * 商品名称
     */
    @Column(name = "productName")
    private String productName;
    /** 商品数量
     *
     */
    @Column(name = "productNum")
    private Integer productNum;
    /** 商品单价(商品的零售价)
     *
     */
    @Column(name = "retailPrice")
    private Double retailPrice;
    /** 商品结算价(商品的结算价)
     *
     */
    @Column(name = "settlementPrice")
    private Double settlementPrice;
    /** 商品物流费用(sku物流费*商品数量)
     *
     */
    @Column(name = "totalFreight")
    private Double totalFreight;
    /** 商品总金额(商品数量*商品单价)
     *
    */
    @Column(name = "totalRetailPrice")
    private Double totalRetailPrice;
    /** 商品总结算价(商品数量*商品结算价)
     *
     */
    @Column(name = "totalSettlementPrice")
    private Double totalSettlementPrice;
    /** 规格值组合id
     *
     */
    @Column(name = "specificationValIds")
    private String specificationValIds;
    /** 商品组合名称
     *
     */
    @Column(name = "skuName")
    private String skuName;
    /** 子订单id
     *
     */
    @Column(name = "subOrderId")
    private Long subOrderId;
    /** 创建时间
     *
     */
    @Column(name = "createTime")
    private Date createTime;
    /** 创建人id
     *
     */
    @Column(name = "createrId")
    private Long createrId;
    /** sku物流费(每种规格商品的单个物流费)
     *
     */
    @Column(name = "skuFreight")
    private Double skuFreight;
    /**
     * 商品总价(商品总金额+商品总物流金额)
     */
    @Column(name = "totalCost")
    private Double totalCost;

    /**
     * 商品图片地址
     */
    @Column(name = "productImageUrl")
    private String productImageUrl;

    /**
     * 商品条形编码
     */
    @Column(name = "barCode")
    private String barCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductSnapId() {
        return productSnapId;
    }

    public void setProductSnapId(Long productSnapId) {
        this.productSnapId = productSnapId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Double settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public Double getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }

    public Double getTotalRetailPrice() {
        return totalRetailPrice;
    }

    public void setTotalRetailPrice(Double totalRetailPrice) {
        this.totalRetailPrice = totalRetailPrice;
    }

    public Double getTotalSettlementPrice() {
        return totalSettlementPrice;
    }

    public void setTotalSettlementPrice(Double totalSettlementPrice) {
        this.totalSettlementPrice = totalSettlementPrice;
    }

    public String getSpecificationValIds() {
        return specificationValIds;
    }

    public void setSpecificationValIds(String specificationValIds) {
        this.specificationValIds = specificationValIds;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Double getSkuFreight() {
        return skuFreight;
    }

    public void setSkuFreight(Double skuFreight) {
        this.skuFreight = skuFreight;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
