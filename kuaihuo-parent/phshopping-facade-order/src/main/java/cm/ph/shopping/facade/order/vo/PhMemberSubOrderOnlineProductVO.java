package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单详情中的商品列表页面交互数据
 * @作者： 张霞
 * @创建时间： 11:21 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class PhMemberSubOrderOnlineProductVO implements Serializable {
    private static final long serialVersionUID = -1205649290604922038L;

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品组合名称
     */
    private String skuName;
    /**
     * 商品数量
     */
    private Integer productNum;
    /**
     * 商品零售价
     */
    private Double retailPrice;
    /**
     * 物流费用
     */
    private Double skuFreight;
    /**
     * 商品总金额（总的商品金额+总的物流费金额）
     */
    private Double totalMoney;
    /**
     * 商品图片地址
     */
    private String productImageUrl;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public Double getSkuFreight() {
        return skuFreight;
    }

    public void setSkuFreight(Double skuFreight) {
        this.skuFreight = skuFreight;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
