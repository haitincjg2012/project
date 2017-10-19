package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应链订单商品sku VO
 *
 * @author 郑朋
 * @create 2017/5/22
 **/
public class PurchaseProductSkuVO implements Serializable{


    private static final long serialVersionUID = -535491133599648805L;
    /** sku数量 */
    private Integer skuNum;

    /** 单价(商品表的进货价) */
    private BigDecimal purchasePrice;

    /**  零售价 */
    private BigDecimal retailPrice;

    /** 结算价*/
    private BigDecimal settlementPrice;

    /** sku物流费*/
    private BigDecimal skuFreight;

    /** sku名称*/
    private String skuName;

    /** 商品规格id */
    private Long skuId;

    /** 规格值组合id */
    private String specificationValIds;

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
}
