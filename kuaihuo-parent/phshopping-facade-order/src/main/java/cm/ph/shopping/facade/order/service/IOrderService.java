package cm.ph.shopping.facade.order.service;

import cm.ph.shopping.facade.order.entity.PhOrderAddress;
import cm.ph.shopping.facade.order.exception.OrderException;
import cm.ph.shopping.facade.order.request.PhOrderOnlineDTO;
import cm.ph.shopping.facade.order.request.UserDTO;
import cm.ph.shopping.facade.order.vo.QueryOnLineOrderDetailVO;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
/**
 * 
* @ClassName: IOrderService
* @Description: 会员订单接口
* @author 王强
* @date 2017年4月25日 下午5:30:59
 */
public interface IOrderService {
	
	/**
	 * 
	* @Title: insertPhOrderOnline
	* @Description: 新增会员线上订单
	* @author 王强
	* @date  2017年4月24日 下午4:24:31
	* @param request
	* @return
	* @throws OrderException
	 */
	public Result insertPhOrderOnline(PhOrderOnlineDTO request) throws OrderException;
	
	
	/**
	 * 
	* @Title: updatePhOrderOnline
	* @Description: 更新会员线上订单状态
	* @author 王强
	* @date  2017年4月24日 下午4:24:05
	* @param orderNo
	* @param status
	* @return
	* @throws OrderException
	 */
	public int updatePhOrderOnline(String orderNo, int status) throws OrderException;
	
	/**
	 * 
	* @Title: findOnlineOrders
	* @Description: 订单列表查询
	* @author 王强
	* @date  2017年3月17日 上午10:00:25
	* @return
	 */
	public Result findOnlineOrders(UserDTO agentRequest, PageBean pageBean) throws OrderException;
	

	/**
	 * 
	* @Title: insertOrderAddress
	* @Description: 新增收货地址
	* @author 王强
	* @date  2017年3月17日 上午11:02:32
	* @param orderAddress
	* @return
	* @throws OrderException
	 */
	public Result insertOrderAddress(PhOrderAddress orderAddress) throws OrderException;
	
	/**
	 * 
	* @Title: deleteOrderAddress
	* @Description: 删除收货地址
	* @author 王强
	* @date  2017年3月17日 上午11:26:41
	* @param id
	* @return
	* @throws OrderException
	 */
	public Result deleteOrderAddress(long id) throws OrderException;
	
	/**
	 * 
	* @Title: updateOrderAddress
	* @Description: 修改收货地址
	* @author 王强
	* @date  2017年3月17日 上午11:32:33
	* @param id
	* @return
	* @throws OrderException
	 */
//	public Result updateOrderAddress(PhOrderAddress orderAddress) throws OrderException;
	
	/**
	 * 
	* @Title: updateOrderAddrDef
	* @Description: 设置默认地址
	* @author 王强
	* @date  2017年3月17日 上午11:54:56
	* @param memberId
	* @param orderAddrId
	* @param
	* @return
	* @throws OrderException
	 */
	public Result updateOrderAddrDef(long memberId, long orderAddrId) throws OrderException;
	
	/**
	 *
	 * @Title: queryOrderAddress
	 * @Description: 查询收货地址
	 * @author 王强
	 * @date  2017年3月17日 上午11:39:42
	 * @param memberId
	 * @return
	 */
	public Result queryOrderAddress(long memberId);

	/**
	 *
	 * @Title: queryAddressDetail
	 * @Description: 查询收货地址详情
	 * @author zhengpeng
	 * @date  2017年3月17日 上午11:39:42
	 * @param addressId
	 * @return
	 */
	public Result queryAddressDetail(long addressId);
	
	/**
	 * 
	* @Title: queryWebOnLineOrders
	* @Description: 查询会员订单列表
	* @author 王强
	* @date  2017年3月28日 下午6:30:14
	* @param memberId
	* @param status
	* @param pageNum
	* @param pageSize
	* @return
	 */
	public Result queryWebOnLineOrders(long memberId,int status, int pageNum, int pageSize);
	
	/**
	 * 
	* @Title: getWebOnLineOrderDetail
	* @Description: 查询会员订单详情
	* @author 王强
	* @date  2017年3月28日 下午6:30:55
	* @param orderId
	* @return
	 */
	public Result getWebOnLineOrderDetail(long orderId, int deleiveryType) throws OrderException;
	
	/**
	 * 
	* @Title: querySupplyerName
	* @Description: 获取供应商名称
	* @author 王强
	* @date  2017年3月29日 下午2:48:35
	* @param productId
	* @return
	 */
	public Result querySupplyerName(long productId);
	
	/**
	 * 
	* @Title: getOrderDetail
	* @Description: 获取订单详情
	* @author 王强
	* @date  2017年3月31日 上午10:19:22
	* @param id
	* @return
	 */
	public QueryOnLineOrderDetailVO getOrderDetail(long id);
	
	/**
	 * 
	* @Title: getUserAddress
	* @Description: 获取用户地址
	* @author 王强
	* @date  2017年3月31日 下午4:36:55
	* @param userId
	* @return
	 */
	public Result getUserAddress(long userId);
	/**
	 * 
	* @Title: updateOrderToSend
	* @Description: 发货更新状态及物流信息
	* @author 王强
	* @date  2017年3月31日 下午5:40:41
	* @param status
	* @param logisticName
	* @param logisticNo
	* @param orderId
	* @return
	 */
	public Result updateOrderToSend(int status, String logisticName, String logisticNo, long orderId) throws OrderException;
	
	/**
	 * 
	* @Title: validateOrderIsPay
	* @Description: 校验订单是否超过规定的时间内支付
	* @author 王强
	* @date  2017年4月27日 下午4:27:26
	* @param orderNo
	* @return
	 */
	public boolean validateOrderIsPay(String orderNo);
}
