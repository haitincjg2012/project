/**  
 * @Title:  OrderGoodsInfoVO.java   
 * @Package cm.ph.shopping.facade.order.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月22日 下午2:03:48   
 * @version V1.0 
 * @Copyright: 2017
 */  
package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**   
 * @ClassName:  OrderGoodsInfoVO   
 * @Description:订单商品信息   
 * @author: 李杰
 * @date:   2017年6月22日 下午2:03:48     
 * @Copyright: 2017
 */
public class OrderGoodsInfoVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 5579629603502507894L;

	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 商品skuID
	 */
	private Long productSkuId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品SKU名字
	 */
	private String skuName;
	/**
	 * 商品零售价
	 */
	private BigDecimal retailPrice;
	/**
	 * 商品数量
	 */
	private Integer productNum;
	/**
	 * 商品图片
	 */
	private String productImage;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 商品总额:商品零售价*商品数量+运费
	 */
	private BigDecimal productRental;
	
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
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public BigDecimal getProductRental() {
		return productRental;
	}
	public void setProductRental(BigDecimal productRental) {
		this.productRental = productRental;
	}
	public Long getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}
	
}
