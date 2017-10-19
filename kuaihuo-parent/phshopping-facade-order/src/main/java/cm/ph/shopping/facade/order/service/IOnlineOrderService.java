package cm.ph.shopping.facade.order.service;


import cm.ph.shopping.facade.order.entity.ShopCart;


import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.entity.ShopCart;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单接口
 * @作者： 张霞
 * @创建时间： 14:14 2017/5/27
 * @Copyright @2017 by zhangxia
 */
public interface IOnlineOrderService {

    /**
     * @author: 张霞
     * @description：添加线上订单
     * @createDate: 16:07 2017/5/31
     * @param addMemberOrderOnlineDTO
     * @return:
     * @version: 2.1
     */
    Result addOnlineOrder(AddMemberOrderOnlineDTO addMemberOrderOnlineDTO);

    /**
     * @author: 张霞
     * @description：获取线上订单列表(子订单)/具体订单的物流信息
     * @createDate: 16:51 2017/5/31
     * @param queryMemberOrderOnlineDTO
     * @param pageBean
     * @return:
     * @version: 2.1
     */
    Result getOnlineOrderVoList(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO , PageBean pageBean);

    /**
     * @author: 张霞
     * @description：通过id获取订单页面信息
     * @createDate: 11:47 2017/6/17
     * @param queryMemberOrderOnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result getOnlineOrderVoById(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO);

    /**
     * @author: 张霞
     * @description：更新订单（子订单）状态
     * @createDate: 10:18 2017/6/1
     * @param updateOnlineOrderStatusDTO
     * @return: 返回常数
     * @version: 2.1
     */
//    Result updateOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO);

    /**
     * @author: 张霞
     * @description：取消线上订单
     * @createDate: 10:20 2017/6/1
     * @param updateOnlineOrderStatusDTO
     * @return:
     * @version: 2.1
     */
    Result cancleOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO);

    /**
     * @author: 张霞
     * @description：线上订单申请退款（当订单处于代发货状态才能申请退款）
     * @createDate: 10:11 2017/6/5
     * @param addMemberOrderOnlineRefundDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result addRefundApplyOnlineOrder(AddMemberOrderOnlineRefundDTO addMemberOrderOnlineRefundDTO);

    /**
     * @author: 张霞
     * @description：通过id获取线上订单详情
     * @createDate: 10:41 2017/6/2
     * @param queryMemberOrderOnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result getOnlineOrderDetailVO(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO);
    /**
     * @author: 张霞
     * @description：线上订单发货
     * @createDate: 15:03 2017/6/2
     * @param updateOnlineOrderStatusDTO
     * @param sendOnlineOrderDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result sendOnlineOrder(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO,SendOnlineOrderDTO sendOnlineOrderDTO);
    /**
     * @author: 张霞
     * @description：后台审核线上订单申请退款
     * @createDate: 14:10 2017/6/5
     * @param refundDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result checkOnlineOrderRefund(CheckMemberSubOrderRefundDTO refundDTO);

    /**
     * @author: 张霞
     * @description：通过id获取申请退款记录
     * @createDate: 15:39 2017/6/5
     * @param queryMemberOrderOnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result getOnlineOrderRefundById(QueryMemberSubOrderRefundDTO queryMemberOrderOnlineDTO);

    /**
     * @author: 张霞
     * @description：获取线上订单申请退款列表
     * @createDate: 18:42 2017/6/19
     * @param qdto
     * @param pageBean
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result getOnlineOrderRefundVoList(QueryMemberSubOrderRefundDTO qdto , PageBean pageBean);

    /**
     * @author: 张霞
     * @description：通过线上订单id进行确认收货
     * @createDate: 18:03 2017/6/26
     * @param udto
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    Result checkOrderShipping(UpdateOnlineOrderStatusDTO udto);

	/**
	 * @author chen
	 * @description: 定时任务 关闭15分钟未支付订单
	 * @return com.ph.shopping.common.util.result.Result
	 */
	Result onlineOrderClose();
	/**
	 * @author lzh
	 * @description: 预定菜品业务
	 * @param addDto
	 * @param dishs
	 * @param dCount
	 * @param subscriptionMoney 
	 * @param shopCartId 
	 * @return
	 */
	Result addOnLineOrder(AddMemberOrderOnlineDTO2 addDto, String dishs, String dCount, String subscriptionMoney, Long shopCartId);
	/**
	 * <pre>findOrderNoStatus(作用：商户接单时间验证)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月25日 下午5:48:36    
	 * @param addDto
	 * @return</pre>
	 */
	Result findOrderNoStatus(AddMemberOrderOnlineDTO2 addDto);
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 查询线上订单(列表)
	 * @return:Result
	 * @createtime:2017年8月22日 下午12:58:13
	 */
	Result onlineOrderQuery(QueryOrderDTO queryOrderDTO);
	
	/**
	 * 
	 * @author:王雪洋
	 * @desc: 查询订单详情
	 * @return:Result
	 * @createtime:2017年8月23日 上午11:27:13
	 */
	Result queryOrderDital(String orderNo);
	/**
	 * <pre>queryOrderDate(作用：查询订单日期)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月28日 下午9:34:52    
	 * @param orderNo
	 * @return</pre>
	 */
	AddMemberOrderOnlineDTO2 queryOrderDate(String orderNo);
	/**
	 * 获取购物车信息
	 * @param shopCart
	 * @return
	 */
	Result toAddOnLineOrder(ShopCart shopCart);
	/**
	 * 清空购物车接口(用户重新选择时间清空餐位)
	 * @param shopCart
	 * @return
	 */
	Result cleanShopCart(ShopCart shopCart);
	/**
	 * 商户取消接单
	 * @authot: wangxueyang
	 */
	Result cancelOrder(Long orderId,Long merchantId);
	/** 再来一单接口
	 * @param merchantId
	 * @return
	 * @authot lzh
	 */
	Result onceMoreOnlineOrder(Long orderId);


	/*===================================================快火二期============================================================*/

	/**
	 * 预定订单（餐位）
	 * @param dto
	 * @param dishId
	 * @return
	 */
	Result addOnLineOrderTwo(AddMemberOrderOnlineDTO2 dto,Long dishId);

	/**
	 * 预订订单（菜品）
	 * @param dishs
	 * @param orderId
	 * @param memberId
	 * @param merchantId
	 * @return
	 */
	Result addOnLineDishOrder(String dishs,Long orderId,Long memberId,Long merchantId);


	Result cancelOrderByMerchant(Long orderId, Long userId);
	/** 再来一单接口
	 * @param orderId
	 * @return
	 * @authot lzh
	 */
	Result onceMoreOnlineOrderTwo(Long orderId);
}
