package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;

/**
 * @author 王强
 * @ClassName: OrderProVO
 * @Description: 查询商品交互数据
 * @date 2017年4月24日 下午6:01:57
 */
public class OrderProVO implements Serializable {

	private static final long serialVersionUID = 546230430320913506L;
    private int productCount;//商品数量
    private long retailPrice1;//零售价
    private double retailPrice;
    private long freight1;//物流费用
    private double freight;
    private int numberOfPackages;//包邮数量

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public long getRetailPrice1() {
		return retailPrice1;
	}

	public void setRetailPrice1(long retailPrice1) {
		this.retailPrice1 = retailPrice1;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public long getFreight1() {
		return freight1;
	}

	public void setFreight1(long freight1) {
		this.freight1 = freight1;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public int getNumberOfPackages() {
		return numberOfPackages;
	}

	public void setNumberOfPackages(int numberOfPackages) {
		this.numberOfPackages = numberOfPackages;
	}
}
