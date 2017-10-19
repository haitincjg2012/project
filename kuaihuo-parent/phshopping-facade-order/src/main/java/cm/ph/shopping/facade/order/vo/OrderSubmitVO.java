/**  
 * @Title:  OrderSubmitVO.java   
 * @Package cm.ph.shopping.facade.order.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月22日 下午2:01:47   
 * @version V1.0 
 * @Copyright: 2017
 */
package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: OrderSubmitVO
 * @Description:订单提交VO
 * @author: 李杰
 * @date: 2017年6月22日 下午2:01:47
 * @Copyright: 2017
 */
public class OrderSubmitVO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5669196365937407462L;
	/**
	 * 供应商和商品的信息
	 */
	private List<OrderGoodsSuppVO> ogs;
	/**
	 * 商品总费用
	 */
	private BigDecimal productTotalPrice;
	/**
	 * 总运费
	 */
	private BigDecimal totalFreight;
	/**
	 * 支付金额
	 */
	private BigDecimal payMoney;
	/**
	 * 返还积分
	 */
	private BigDecimal backIntegral;
	
	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public BigDecimal getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public BigDecimal getBackIntegral() {
		return backIntegral;
	}
	public void setBackIntegral(BigDecimal backIntegral) {
		this.backIntegral = backIntegral;
	}
	public List<OrderGoodsSuppVO> getOgs() {
		return ogs;
	}
	public void setOgs(List<OrderGoodsSuppVO> ogs) {
		this.ogs = ogs;
	}
	
}
