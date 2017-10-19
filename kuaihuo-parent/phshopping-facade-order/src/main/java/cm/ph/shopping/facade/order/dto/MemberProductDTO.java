package cm.ph.shopping.facade.order.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单中的商品DTO
 * @作者： 张霞
 * @创建时间： 9:20 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public class MemberProductDTO implements Serializable {
    private static final long serialVersionUID = 42171765699759216L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 商品快照id
     */
    private Long productSnapId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品条形编码
     */
    @NotBlank(message="[商品条形编码]不可为空")
    private String barCode;
    /**
     * 商品总数量
     */
    private Integer productNum;
    /**
     * 商品总物流费用(sku物流费*商品数量)
     */
    private Double totalFreight;
    /**
     * 商品总金额(商品数量*商品单价)
     */
    private Double totalRetailPrice;
    /**
     * 商品总结算价(商品数量*商品结算价)
     */
    private Double totalSettlementPrice;
    /**
     * 商品单价(即零售价)
     */
    private Double retailPrice;
    /**
     * 支付总费用(商品总金额+商品总物流费用)
     */
    private Double totalMoney;

    /**
     * 商品图片地址
     */
    private String productImageUrl;

    /**
     * 商品对应的sku集合
     */
    List<MemberProductSkuDTO> productSkuDTOS = new ArrayList<>(  );


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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<MemberProductSkuDTO> getProductSkuDTOS() {
        return productSkuDTOS;
    }

    public void setProductSkuDTOS(List<MemberProductSkuDTO> productSkuDTOS) {
        this.productSkuDTOS = productSkuDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
    
}
