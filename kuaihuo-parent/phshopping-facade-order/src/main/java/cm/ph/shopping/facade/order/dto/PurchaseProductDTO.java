package cm.ph.shopping.facade.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 供应链新增订单商品dto
 *
 * @author 郑朋
 * @create 2017/5/22
 **/
public class PurchaseProductDTO implements Serializable {

    private static final long serialVersionUID = 6905978202479072525L;

    /** 商品快照id */
    @NotBlank(message="[商品快照id ]不可为空")
    private Long productSnapId;

    /** 商品id */
    @NotBlank(message="[商品id]不可为空")
    private Long productId;

    /** 商品名称 */
    @NotBlank(message="[商品名称]不可为空")
    private String productName;

    /** 商品编码 */
    @NotBlank(message="[商品编码]不可为空")
    private String productCode;

    /** 商品总金额 */
    @NotBlank(message="[商品总金额]不可为空")
    private BigDecimal totalMoney;

    /** 商品总数量 */
    @NotBlank(message="[商品总数量]不可为空")
    private Integer productNum;

    /** 商品物流费用 */
    private BigDecimal totalFreight;

    /** 商品对应的sku集合*/
    List<PurchaseProductSkuDTO> purchaseProductSkuList;

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

	public List<PurchaseProductSkuDTO> getPurchaseProductSkuList() {
		return purchaseProductSkuList;
	}

	public void setPurchaseProductSkuList(List<PurchaseProductSkuDTO> purchaseProductSkuList) {
		this.purchaseProductSkuList = purchaseProductSkuList;
	}

}
