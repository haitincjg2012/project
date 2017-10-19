package cm.ph.shopping.facade.order.service;

import cm.ph.shopping.facade.order.vo.AlqOrderVO;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;

/**
 * 批发订单接口
 * @author lzh
 *
 */
public interface AlqOrderService {

	/**
	 * 批发订单列表
	 */
	Result getAlqList(PageBean pageBean,AlqOrderVO alqOrderVO);
	/**
	 * 批发代理订单列表
	 */
	Result getAlqPfList(PageBean pageBean,AlqOrderVO alqOrderVO);

	/**
	 * 批发订单详情
	 */
	Result getAlqOrderDetailVO(Long id);
}
