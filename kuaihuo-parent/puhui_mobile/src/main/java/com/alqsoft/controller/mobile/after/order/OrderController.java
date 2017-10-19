package com.alqsoft.controller.mobile.after.order;

import com.alqsoft.anno.Explosionproof;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.alqframework.json.JsonUtil;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.vo.OrderListVO;

@RequestMapping("mobile/after/order")
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

    private org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 设置退货留言
	 * @param member 用户
	 * @param msg 留言信息
	 * @param oid  订单ID
	 * @return
	 */
	 @Explosionproof
	 @RequestMapping(value = "setRefund",method = RequestMethod.POST)
	 @ResponseBody
	 public Result  setRefundMsg(@MemberAnno Member member,
								 @RequestParam("msg")String msg,
								 @RequestParam("oid")String oid,
								 @RequestParam("type")String type){

	 	return orderService.setRefundMsg(member.getId(),oid,msg,type);
	 }
	
	/**
	 * 批发商待付款订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findorderbywaitpay",method = RequestMethod.POST)
	public Result findOrderByWaitPay(@MemberAnno Member m, Member member, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.findOrderByWaitPay(m, page, rows);
	}
	
	/**
	 * 批发商已付定金订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findorderbysubscription",method = RequestMethod.POST)
	public Result findOrderBySubscription(@MemberAnno Member m, Member member, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.findOrderBySubscription(m, page, rows);
	}
	
	/**
	 * 批发商待发货订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findorderbywaitforsend",method = RequestMethod.POST)
	public Result findOrderByWaitForSend(@MemberAnno Member m, Member member, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.findOrderByWaitForSend(m, page, rows);
	}
	
	/**
	 * 批发商待发货订单详情
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getorderbyidandwait",method = RequestMethod.POST)
	public Result getOrderByIdAndWait(@MemberAnno Member m, Member member, Order order){
		return this.orderService.getOrderByIdAndWait(m, order);
	}
	
	/**
	 * 批发商已发货订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findorderbysend",method = RequestMethod.POST)
	public Result findOrderBySend(@MemberAnno Member m, Member member, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.findOrderBySend(m, page, rows);
	}
	
	/**
	 * 批发商订单退款
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 * 
	 * 2017-07-18 废弃 
	 */
	@ResponseBody
	@Explosionproof
	@RequestMapping(value = "findorderbysubscriptiontoback",method = RequestMethod.POST)
	public Result findOrderBySubscriptionToBack(@MemberAnno Member m, Member member, @RequestParam(value="id", required=true)Long id, String refundMsg){
		//return ResultUtils.returnError("该功能已关闭");
		return this.orderService.findOrderBySubscriptionToBack(m, id, refundMsg);
	}

	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/25 21:32
	 * @desc:买家取消订单（商户）  取消主订单下所有的子订单
	 **/
	@ResponseBody
	@RequestMapping(value = "cancelOrderForMerchant",method = RequestMethod.POST)
	public Result cancelOrderForMerchant(@MemberAnno Member m, Member member, @RequestParam(value="orderNo", required=true)String orderNo){
        log.info("买家取消订单传入参数：用户id："+ member.getId()+"取消的订单号:"+orderNo);
        Result result = null;
        try{
			if(m.getId() == null){
				return ResultUtils.returnError("用户登录异常");
			}
			result = this.orderService.cancelOrderForMerchant(orderNo);
        }catch (Exception e){
            log.info("取消订单异常："+e.getMessage());
            result = ResultUtils.returnError("取消订单失败！");
        }
		return result;
	}

	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/26 21:12
	 * @desc:卖家取消订单（供应商）
	 **/
	@ResponseBody
	@RequestMapping(value = "cancelOrderForSuppler",method = RequestMethod.POST)
	public Result cancelOrderForSuppler(@MemberAnno Member m, Member member, @RequestParam(value="orderNo", required=true)String orderNo , Integer status){
		log.info("卖家取消订单传入参数：用户id："+ member.getId()+"取消的订单号:"+orderNo);
		Result result = null;
		try{
			if(m.getId() == null){
				return ResultUtils.returnError("用户登录异常！");
			}
			if (status == null){
				return ResultUtils.returnError("订单状态传入有误！");
			}
			result = this.orderService.cancelOrderForSuppler(orderNo,status);
		}catch (Exception e){
			log.info("取消订单异常："+e.getMessage());
			result = ResultUtils.returnError("取消订单失败！");
		}
		return result;
	}
	
	/**
	 * 批发商已付定金订单议价
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 * 2017-07-18 废弃 
	 */
	@ResponseBody
	@Explosionproof
	@RequestMapping(value = "findorderbysubscriptiontopaymoney",method = RequestMethod.POST)
	public Result findOrderBySubscriptionToPayMoney(@MemberAnno Member m, Member member, Order order, Double nPrice){
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.findOrderBySubscriptionToPayMoney(m, order, nPrice);
	}

	/**
	 * 订单详情
	 * @return
	 */
	@RequestMapping(value = "detail",method = RequestMethod.POST)
	public @ResponseBody Result detail (@MemberAnno Member member,
										@RequestParam("oid")Long oid,
										@RequestParam(value = "type",required = false,defaultValue = "0")String type){

		Result result = orderService.detail(member,oid,type);
		return result;
	}

	/**
	 * 批发商个人中心--收入明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="findorderbyhunterid",method=RequestMethod.POST)
	@ResponseBody
	public Result findorderbyhunterid(@RequestParam(value="hunterId")Long hunterId,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="5")Integer rows,
			@MemberAnno Member member){
		
		Result result =this.orderService.findOrderByHunterId(hunterId,page,rows,member);
		
		return result;		
		
	}

	/**
	 * 批发商个人中心--收入订单明细
	 * @param hunterId
	 * @param orderNum
	 * @return
	 */
	@RequestMapping(value="findorderbyordernum",method=RequestMethod.POST)
	@ResponseBody
	public Result findorderbyordernum(@RequestParam(value="hunterId")Long hunterId,
			@RequestParam(value="orderNum")String orderNum,
			@MemberAnno Member member){
		
		Result result =this.orderService.findOrderByorderNum(hunterId,orderNum,member);
		
		return result;		
		
	}
	
	/**
	 * 用户去结算接口
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 */
	@Explosionproof
	@ResponseBody
	@RequestMapping(value = "topay",method = RequestMethod.POST)
	public Result toPay(@MemberAnno Member m, Member member, String sIds, String nums){
		return this.orderService.toPay(m, sIds, nums);
	}
	
	/**
	 * 用户去下单接口
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 */
	@Explosionproof
	@ResponseBody
	@RequestMapping(value = "tocreateorder",method = RequestMethod.POST)
	public Result toCreateOrder(@MemberAnno Member m, Member member,@RequestParam(name="list")String list, Long aId){
		OrderListVO orderList;
		try {
			orderList = JsonUtil.jsonToObject(list, OrderListVO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("参数异常");
		}
		return this.orderService.toCreateOrder(m, orderList, aId);
	}
	/**
	 * 用户待支付订单结算
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 * 2017-07-18 废弃 
	 */
	@Explosionproof
	@ResponseBody
	@RequestMapping(value = "topayorderbywait",method = RequestMethod.POST)
	public Result toPayOrderByWait(@MemberAnno Member m, Member member, Order order, String ip){
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.toPayOrderByWait(m, order, ip);
	}

	/**
	 * 确认收货
	 * @param member 会员
	 * @param orderNo 订单ID
	 * @return
	 * 2017-07-18 废弃 
	 */
	@Explosionproof
	@RequestMapping(value = "confirmReceive",method = RequestMethod.POST)
	public @ResponseBody Result confirmReceive(@MemberAnno Member member,@RequestParam("oid")String orderNo){
		return ResultUtils.returnError("该功能已关闭");
		//return orderService.confirmReceive(member.getId(),orderNo);
	}
	/**
	 * 确认发货
	 * @param member 会员
	 * @param orderNo 订单ID
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "confirmSend",method = RequestMethod.POST)
	public @ResponseBody Result confirmSend(@MemberAnno Member member,@RequestParam("oid")String orderNo){

		return orderService.confirmSend(member,orderNo);
	}
	/**
	 * 用户协商价格结算
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 * 2017-07-18 废弃 
	 */
	@ResponseBody
	@Explosionproof
	@RequestMapping(value = "topayorderbynegotiate",method = RequestMethod.POST)
	public Result toPayOrderByNegotiate(@MemberAnno Member m, Member member, Order order, String ip){
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.toPayOrderByNegotiate(m, order, ip);
	}
	
	/**
	 * 用户订单退款
	 * @param m
	 * @param member
	 * @param order
	 * @return
	 * 2017-07-18 废弃 
	 */
	@ResponseBody
	@Explosionproof
	@RequestMapping(value = "topayback",method = RequestMethod.POST)
	public Result toPayBack(@MemberAnno Member m, Member member, Order order){
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.toPayBack(m, order);
	}
	
	/**
	 * 用户订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getorderlist",method = RequestMethod.POST)
	public Result getOrderList(@MemberAnno Member m, Member member, String orderStatus, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.getOrderList(m, orderStatus, page, rows);
	}
	
	/**
	 * 批发商订单列表
	 * @param m
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "gethunterorderlist",method = RequestMethod.POST)
	public Result getHunterOrderList(@MemberAnno Member m, Member member, String orderStatus, @RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		return this.orderService.getHunterOrderList(m, orderStatus, page, rows);
	}

	/**
	 * 查看订单退货信息
	 * @param m
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "viewRefundMsg",method = RequestMethod.POST)
	public @ResponseBody Result getRefundMsg(@MemberAnno Member m,@RequestParam("oid")Long oid){

		return orderService.viewRefundMsg(m.getId(),oid);
	}
	
	/**
	 * V2
	 * 用户订单列表
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMemberOrderList",method = RequestMethod.POST)
	public Result getMemberOrderList(@MemberAnno Member m,
									 @RequestParam(value="orderStatus",defaultValue="1")String orderStatus,
									 @RequestParam(value="page",defaultValue="1")Integer page,
							   @RequestParam(value="rows",defaultValue="10")Integer rows){

		return orderService.getMemberOrderList(m, orderStatus, page, rows);
	}

	/**
	 * V2 批发商订单列表
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getHunterOrderList",method = RequestMethod.POST)
	public Result getPuhuiHunterOrderList(@MemberAnno Member m,
										  @RequestParam(value="orderStatus",defaultValue="1")String orderStatus,
										  @RequestParam(value="page",defaultValue="1")Integer page,
									 @RequestParam(value="rows",defaultValue="10")Integer rows){

		return orderService.getPuHuiHunterOrderList(m, orderStatus, page, rows);
	}
	
	/**
	 * 用户输入尾款价格
	 * @param member
	 * @param orderId
	 * @return
	 * 2017-07-18 废弃 
	 */
	@Explosionproof
	@RequestMapping(value = "addnegotiatepricefororder",method = RequestMethod.POST)
	@ResponseBody
	public Result addnegotiatepricefororder(@MemberAnno Member member, @RequestParam(value="orderId", required=true)Long orderId, String negotiatePrice){
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.addNegotiatePriceForOrder(member, orderId, negotiatePrice);
	}
	/**
	 * 商家对尾款价格进行操作
	 * @param member
	 * @param orderId
	 * @param type
	 * @return
	 * 2017-07-18 废弃 
	 */
	@Explosionproof
	@RequestMapping(value = "choosenegotiatepricefororder",method = RequestMethod.POST)
	@ResponseBody
	public Result choosenegotiatepricefororder(@MemberAnno Member member, @RequestParam(value="orderId", required=true)Long orderId,String type){//type 1同意  2驳回
		return ResultUtils.returnError("该功能已关闭");
		//return this.orderService.chooseNegotiatePriceForOrder(member, orderId,type);
	}

	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/27 10:02
	 * @desc:买家（商户查询订单列表）  附带分页信息
	 **/
	@RequestMapping(value = "queryOnlineOrders",method = RequestMethod.POST)
	@ResponseBody
	public Result queryOnlineOrders(Integer status,@RequestParam(value="page",defaultValue="1")Integer page,
									@RequestParam(value="rows",defaultValue="10")Integer rows,@MemberAnno Member member,Integer userType){
		Result result;
		//校验参数
		log.info("查询线上订单传入参数：memberId : "+member.getId() +",status: "+status+",page: "+page+",rows: "+rows+" userType:"+userType);
		try {
			if (userType == null){
				throw new Exception("用户身份标识传入空值！");
			}

			if (member.getId() == null) {
				throw new Exception("用户id传入空值！");
			}
			if(status == null){
				throw new Exception("订单状态值传入空值！");
			}

			Map<String, Object> params = new HashMap<>();
			if (userType == 2){
				if (member.getHunter() == null){
					throw new Exception("当前用户还不是供应商！");
				}else {
					params.put("memberId",member.getHunter().getId());
				}
			}else {
				params.put("memberId",member.getId());
			}
			params.put("status",status);
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			params.put("userType",userType);
			result = orderService.queryOnllieOrders(params);
		}catch (Exception e){
			log.info("查询异常："+e.getMessage());
			return ResultUtils.returnError("查询订单失败！");
		}
		return result;
	}
	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/27 14:38
	 * @desc: 查询订单详情
	 **/
	@RequestMapping(value = "queryOrderDeail",method = RequestMethod.POST)
	@ResponseBody
	public Result queryOrderDeail(@RequestParam("orderNo")String orderNo,@RequestParam("status")Integer status,@MemberAnno Member member){
		log.info("查询线上订单详情传入参数：orderNo: " + orderNo  +" status: "+status);
		Result result;
		try{
			if (orderNo == null)
				throw new Exception("订单号传入空值!");
			if (status ==null)
				throw new Exception("订单状态传入空值！");
			result = orderService.queryOnlineOrderDetails(orderNo,status);
		}catch (Exception e){
			log.info("定单详情查询异常："+e.getMessage());
			result = ResultUtils.returnError("查询订单详情失败！");
		}
		return result;
	}
	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/27 17:27
	 * @desc:供应商接单
	 **/
    @RequestMapping(value = "agreeOrderBySupplier",method = RequestMethod.POST)
    @ResponseBody
    public Result agreeOrderBySupplier(@RequestParam("orderNo")String orderNo,@MemberAnno Member member){
        log.info("供应商接单传入参数：orderNo: " + orderNo);
        Result result;
        try{
            if (orderNo == null || orderNo.equals(""))
                throw new Exception("订单号传入空值!");
            result = orderService.agreeOrderBySupplier(orderNo);
        }catch (Exception e){
            log.info("接单异常："+e.getMessage());
            result = ResultUtils.returnError("接单失败！");
        }
        return result;
    }
}
