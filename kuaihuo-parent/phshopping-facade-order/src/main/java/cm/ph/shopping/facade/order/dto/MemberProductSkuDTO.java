package cm.ph.shopping.facade.order.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单中每种商品的规格
 * @作者： 张霞
 * @创建时间： 9:34 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class MemberProductSkuDTO implements Serializable {
    private static final long serialVersionUID = 42171765699759290L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 商品规格数量
     */
    private int skuNum;
    /**
     * 商品单价(商品的零售价)
     */
    private Double retailPrice;
    /**
     * 商品结算价(商品的结算价)
     */
    private Double settlementPrice;
    /**
     * sku物流费用
     */
    private Double skuFreight;
    /** 商品规格id */
    @NotBlank(message="[商品规格id]不可为空")
    private Long skuId;
    /**
     * 商品组合名称
     */
    private String skuName;
    /**
     * 规格值组合id
     */
    private String specificationValIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
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

    public Double getSkuFreight() {
        return skuFreight;
    }

    public void setSkuFreight(Double skuFreight) {
        this.skuFreight = skuFreight;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpecificationValIds() {
        return specificationValIds;
    }

    public void setSpecificationValIds(String specificationValIds) {
        this.specificationValIds = specificationValIds;
    }
}
