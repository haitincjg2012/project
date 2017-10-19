package cm.ph.shopping.facade.order.exception;

import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;

/**
 * 
 * 
* @ClassName: OrderException
* @Description: 会员订单异常类
* @author 王强
* @date 2017年4月24日 下午5:07:14
 */
public class OrderException extends BizException {

	private static final long serialVersionUID = 4790611300720211303L;
	
	public OrderException(RespCode exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public OrderException() {
	}

	public OrderException(String message) {
		super(message);
	}
}
