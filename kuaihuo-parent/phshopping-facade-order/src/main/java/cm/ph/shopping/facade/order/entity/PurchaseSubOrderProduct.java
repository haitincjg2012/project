package cm.ph.shopping.facade.order.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 子订单对应的商品实体
 *
 * @author 郑朋
 * @create 2017/5/15
 **/

@Table(name = "ph_purchase_sub_order_products")
public class PurchaseSubOrderProduct implements Serializable {

    private static final long serialVersionUID = 8953833977154609595L;

    @Id
    private Long id;

    /** sku数量 */
    @Column(name = "skuNum")
    private Integer skuNum;

    /** 单价(商品表的进货价) */
    @Column(name = "purchasePrice")
    private BigDecimal purchasePrice;

    /**  零售价 */
    @Column(name = "retailPrice")
    private BigDecimal retailPrice;

    /** 结算价*/
    @Column(name = "settlementPrice")
    private BigDecimal settlementPrice;

    /** sku物流费*/
    @Column(name = "skuFreight")
    private BigDecimal skuFreight;

    /** sku名称*/
    @Column(name = "skuName")
    private String skuName;

    /** 商品规格id */
    @Column(name = "skuId")
    private Long skuId;

    /** 规格值组合id */
    @Column(name = "specificationValIds")
    private String specificationValIds;

    /** 子订单id */
    @Column(name = "subOrderId")
    private Long subOrderId;

    /** 商品快照id */
    @Column(name = "productSnapId")
    private Long productSnapId;

    /** 商品id */
    @Column(name = "productId")
    private Long productId;

    /** 商品名称 */
    @Column(name = "productName")
    private String productName;

    /** 商品编码 */
    @Column(name = "productCode")
    private String productCode;

    /** 商品总金额 */
    @Column(name = "totalMoney")
    private BigDecimal totalMoney;

    /** 商品总数量 */
    @Column(name = "productNum")
    private Integer productNum;

    /** 商品物流费用 */
    @Column(name = "totalFreight")
    private BigDecimal totalFreight;

    /** 包邮数量 */
    @Column(name = "numberOfPackages")
    private Integer numberOfPackages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public BigDecimal getSkuFreight() {
        return skuFreight;
    }

    public void setSkuFreight(BigDecimal skuFreight) {
        this.skuFreight = skuFreight;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSpecificationValIds() {
        return specificationValIds;
    }

    public void setSpecificationValIds(String specificationValIds) {
        this.specificationValIds = specificationValIds;
    }

    public Long getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getTotalFreight() {
        return totalFreight;
    }

    public void setTotalFreight(BigDecimal totalFreight) {
        this.totalFreight = totalFreight;
    }

    public Integer getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(Integer numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }
}
