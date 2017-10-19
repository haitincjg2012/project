package cm.ph.shopping.facade.order.service;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTOS;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;

/**
 * @dete 2017-08-25
 * @author 李治桦
 * 交易订单service
 */
public interface TradOrderService {

	/**
	 * 添加交易订单
	 * @param orderUnline
	 * @return
	 */
	Result addTradOrder(PhMemberOrderUnlineDTOS orderUnline,byte payType);
	/**
	 * 获取交易列表
	 * @param orderUnline
	 * @return
	 */
	Result getMerchantTradOrder(PhMemberOrderUnlineDTO orderUnline,int page, int pageSize);
	
	/**
	 * 交易订单状态修改
	 * @param id
	 * @param createrId
	 * @param payType
	 * @return 
	 */
	Result updateOrder(Long id, Long createrId, byte payType,Long orderMoney);
	/**
	 * 交易失败更新
	 * @param payType
	 * @param id
	 * @param createrId
	 * @param orderMoney
	 * @return
	 */
	Result updateOrderForFail(byte payType, Long id, Long createrId, Long orderMoney);
	/**
	 * 发起验证
	 * @param memberPhone
	 * @param merchantId
	 * @return
	 */
	Result testAddTradOrder(String memberPhone, Long merchantId);
	/**
	 * 商户APP
	 * 验证绑定人是否为平台用户
	 * @param memberPhone
	 */
	Result testMemberPro(String memberPhone);
	
	
}
