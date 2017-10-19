package com.phshopping.api.controller.personal.order;

import cm.ph.shopping.facade.order.dto.QueryOrderDTO;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author: 王雪洋
 * @project: phshopping-app-api-member
 * @ClassName: OrderQueryController
 * @Date: 2017年8月21日下午8:33:57
 * @Desc: 查询订单相关信息
 */
@RestController
@RequestMapping("api/personal/queryOrder")
public class OrderQueryController {
	
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	/**
	 * 订单服务接口对象
	 */
    @Reference(version = "1.0.0")
    private IOnlineOrderService iOnlineOrderService;
	
	/**
	 * 查询当前会员用户所有订单(列表)
	 * @param result
	 * @param queryOrderDTO 用户id  订单状态  当前页码 页大小
	 * @param userType : member merchant
	 * @return
	 */
	@RequestMapping(value = "/queryOrders/member", method = RequestMethod.GET)
	public Result queryOrders(Result result , QueryOrderDTO queryOrderDTO){
		logger.info("查询线下订单传入参数: 用户ID" + queryOrderDTO.getUserId() + "订单状态码: " + queryOrderDTO.getStatus() +"分页参数：页码 - "+queryOrderDTO.getPageNum()+",页大小："+ queryOrderDTO.getPageSize() );
		try{
			if(queryOrderDTO.getUserId() == null || queryOrderDTO.getUserId().equals("")){
				throw new Exception("用户ID为空！");
			}
			if(queryOrderDTO.getStatus() == null || queryOrderDTO.getStatus().equals("")){
				throw new Exception("订单状态为空！");
			}
			if (queryOrderDTO.getPageNum() == null || queryOrderDTO.getPageSize() == null){
				throw new Exception("分页传入空值！");
			}
			if(queryOrderDTO.getPageNum() < 1 ){
				throw new Exception("页码数值传入不能小于1！");
			}
			queryOrderDTO.setUserType("member");
			result = iOnlineOrderService.onlineOrderQuery(queryOrderDTO);
		}catch (Exception e) {
			logger.info(e.getMessage());
			result = ResultUtil.setResult(false, "显示失败！");
		}
		return result;
	}
	/**
	 * 
	 * @author:王雪洋
	 * @desc:查询订单详情
	 * @return:Result
	 * @createtime:2017年8月23日 上午11:36:57
	 */
	@RequestMapping("/queryOrder")
	@ResponseBody
	public Result queryOrderDital(Result result , String orderNo){
		logger.info("查询订单详情传入参数:订单ID" + orderNo);
		try {
			if( orderNo == null || orderNo.equals("") ){
				throw new Exception("订单号传入空值！");
			}
			result = iOnlineOrderService.queryOrderDital(orderNo);
		} catch (Exception e) {
			logger.info(e.getMessage());
			result = ResultUtil.setResult(false, "显示失败！");
		}
		return result;
	}
	/**
	 * @author: wangxueyang
	 * @return: result
	 * @time: 2017-9-12 10:35:29
	 * @Desc: 会员取消订单
	 */
	@RequestMapping(value="/merchant/cancelOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result cancelOrder(Result result ,Long orderId,Long memberId){
		logger.info("商户取消订单传入订单id:"+orderId+"，商户id："+memberId);
		try {
			if (orderId == null ){
				throw new Exception("订单号传入空值！");
			}
			if (memberId == null || memberId.equals("")){
				throw new Exception("会员号传入空值！");
			}
			result = iOnlineOrderService.cancelOrder(orderId,memberId);
		}catch (Exception e){
			logger.info(e.getMessage());
			result = ResultUtil.setResult(false, "取消订单失败！");
		}
		return result;
	}
}
