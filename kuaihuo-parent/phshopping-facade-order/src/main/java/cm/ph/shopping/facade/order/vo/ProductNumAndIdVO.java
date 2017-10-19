package cm.ph.shopping.facade.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 王强
 * @ClassName: ProductNumAndIdVO
 * @Description: 商品交互数据
 * @date 2017年4月24日 下午6:07:57
 */
public class ProductNumAndIdVO implements Serializable {

	private static final long serialVersionUID = 3028304368358500331L;

    private int num;//商品数量

    private long productId;//商品ID
    
    private Date deliveryDate;//下单时间

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
}
