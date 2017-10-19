/**  
 * @Title:  OrderGoodsSuppVO.java   
 * @Package cm.ph.shopping.facade.order.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月22日 下午3:27:58   
 * @version V1.0 
 * @Copyright: 2017
 */  
package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.List;

/**   
 * @ClassName:  OrderGoodsSuppVO   
 * @Description:供应商和商品信息   
 * @author: 李杰
 * @date:   2017年6月22日 下午3:27:58     
 * @Copyright: 2017
 */
public class OrderGoodsSuppVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4859242995349302696L;

	/**
	 * 商品信息
	 */
	private List<OrderGoodsInfoVO> goodsList;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 供应商ID
	 */
	private Long supplierId;
	
	public List<OrderGoodsInfoVO> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<OrderGoodsInfoVO> goodsList) {
		this.goodsList = goodsList;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}
