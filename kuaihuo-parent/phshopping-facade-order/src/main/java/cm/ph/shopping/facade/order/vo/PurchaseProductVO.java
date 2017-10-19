package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 供应链新增订单商品VO
 *
 * @author 郑朋
 * @create 2017/5/22
 **/
public class PurchaseProductVO implements Serializable {


    private static final long serialVersionUID = -5643463988352345286L;

    /** 子订单id */
    private Long subOrderId;

    /** 商品快照id */
    private Long productSnapId;

    /** 商品id */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 商品编码 */
    private String productCode;

    /** 商品总金额 */
    private BigDecimal totalMoney;

    /** 商品总数量 */
    private Integer productNum;

    /** 商品物流费用 */
    private BigDecimal totalFreight;

    /** 商品对应的sku集合*/
    List<PurchaseProductSkuVO> purchaseProductSkuList;

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

    public List<PurchaseProductSkuVO> getPurchaseProductSkuList() {
        return purchaseProductSkuList;
    }

    public void setPurchaseProductSkuList(List<PurchaseProductSkuVO> purchaseProductSkuList) {
        this.purchaseProductSkuList = purchaseProductSkuList;
    }
}
