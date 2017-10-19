package com.alqsoft.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.order.OrderDao;
import com.alqsoft.dto.OrderDTO;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.rpc.mobile.RpcOrderService;
import com.alqsoft.rpc.mobile.RpcWxPayOrderService;
import com.alqsoft.service.comment.OrderCommentService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.memberaddress.MemberAddressService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.producttrace.ProductTraceService;
import com.alqsoft.service.wxpay.WxPayService;
import com.alqsoft.utils.*;
import com.alqsoft.vo.OrderListVO;
import com.alqsoft.vo.OrderVO;
import com.alqsoft.vo.ProductTraceVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.alqframework.date.DateUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService  {

	private static final String FORMAT_DATETIME="yyyyMMdd HH:mm";

	private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private MemberAddressService memberAddressService;
	@Autowired
	private RpcOrderService rpcOrderService;
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private RpcWxPayOrderService rpcWxPayOrderService;
	@Autowired
	private WxPayService wxPayOrderService;
	@Autowired
	private ProductTraceService productTraceService;

	@Autowired
	private OrderCommentService orderCommentService;

	@Autowired
	private HunterService hunterService;

	@Autowired
	private MemberService memberService;

	@Override
	public Result findOrderByWaitPay(Member member, Integer page, Integer rows) {
		if(member.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", member.getId());
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String, Object>> orderList = this.orderDao.findOrderByWaitPay(params);
		int count = this.orderDao.findOrderByWaitPayCount(member.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		data.put("count", count);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	/**
	 * 设置退货留言
	 * @param uid 用户ID
	 * @param oid 订单ID
	 * @param  msg  留言内容
	 * @param  type 0:买家退款 1：批发商退款
	 * @return
	 */
	@Override
	public Result setRefundMsg(Long uid, String oid,String msg,String type) {
		log.info(" setRefundMsg uid: {} ,orderNo:{} , msg : {} ,type:{}",uid,oid,msg,type);
		if (Objects.isNull(uid) || Objects.isNull(oid) || StringUtils.isBlank(msg)|| StringUtils.isBlank(type)) {
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		if (!"0".equals(type) && !"1".equals(type)){
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		if(msg==null||"".equals(msg.replaceAll("\\s*", ""))){
			return ResultUtils.returnError("请填写退款留言信息");
		}
		if(msg.length()>50){
			return ResultUtils.returnError("退款留言信息不能超过50字符");
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("mid",uid);
		map.put("oid",oid);
		map.put("msg",msg);
		map.put("type",type);
		try {
			OrderVO orderVO = orderDao.getOrder(map);
			if ( orderVO == null){
				return ResultUtils.returnError("订单不存在");
			}
			return rpcOrderService.setRefundMsg(map);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
		}
	}


	/**
	 * 判断会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 */
	@Override
	public int getMemberHaveOrderForHunterCommentCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return orderDao.getMemberHaveOrderForHunterCommentCount(params);
	}

	@Override
	public Result findOrderBySubscription(Member member, Integer page, Integer rows) {
		if(member.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", member.getId());
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String, Object>> orderList = this.orderDao.findOrderBySubscription(params);
		int count = this.orderDao.findOrderBySubscriptionCount(member.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		data.put("count", count);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	@Override
	public Result findOrderBySubscriptionToBack(Member m, Long id, String refundMsg) {
		if(m.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mId", m.getId());
		params.put("orderId", id);
		Order o = this.orderDao.getOrderByOrderIdAndMid(params);
		System.out.println(o.getStatus());
		if(o.getStatus().intValue() != 1 && o.getStatus().intValue() != 5 && o.getStatus().intValue() != 2){
			return ResultUtils.returnError("订单状态异常");
		}
		List<WxPayOrder> list = this.wxPayOrderService.getPayBackOrderByOrderId(o.getId());
		if(list.size() > 0){
			return ResultUtils.returnError("退款处理中");
		}
		o.setHunterRefundMsg(refundMsg);
		try {
			log.info("执行退款业务操作");
			return this.rpcOrderService.findOrderBySubscriptionToBack(o);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("退款异常");
		}
	}

	@Override
	public Result findOrderBySubscriptionToPayMoney(Member m, Order order, Double negotiatePrice) {
		if(m.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		if(negotiatePrice <= 0){
			return ResultUtils.returnError("协商价格不能为负");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mId", m.getId());
		params.put("orderId", order.getId());
		Order o = this.orderDao.getOrderByOrderIdAndMid(params);
		if(o == null){
			return ResultUtils.returnError("订单信息异常");
		}
		if(!(o.getNegotiatePrice() == null || o.getNegotiatePrice() == 0)){
			return ResultUtils.returnError("订单已协商价格");
		}
		if(o.getNegotiatePrice() == null || o.getNegotiatePrice() == 0){
			Long negotiate = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(negotiatePrice*100, 0))))+"");
			o.setNegotiatePrice(negotiate);
			try {
				Result result = this.rpcOrderService.findOrderBySubscriptionToPayMoney(o);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtils.returnError("订单议价异常");
			}
		}else{
			return ResultUtils.returnError("订单已协商价格");
		}
	}

	@Override
	public Result findOrderByWaitForSend(Member member, Integer page, Integer rows) {
		if(member.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", member.getId());
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String, Object>> orderList = this.orderDao.findOrderByWaitForSend(params);
		for (Map<String, Object> map : orderList) {
			String a = (String)map.get("receive_address");
			String address = "";
			if(a.contains("北京")||a.contains("天津")||a.contains("重庆")||a.contains("上海")){
				if(a.contains("区")){
					address = a.split("区")[0] + "区";
				}
				if(a.contains("县")){
					address = a.split("县")[0] + "县";
				}
			}else {
				address = a.split("市")[0] + "市";
			}
			map.put("areaAdd", address);
		}

		int count = this.orderDao.findOrderByWaitForSendCount(member.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		data.put("count", count);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	@Override
	public Result findOrderBySend(Member member, Integer page, Integer rows) {
		if(member.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", member.getId());
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String, Object>> orderList = this.orderDao.findOrderBySend(params);
		int count = this.orderDao.findOrderBySendCount(member.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		data.put("count", count);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	@Override
	public Result getOrderByIdAndWait(Member member, Order order) {
		if(member.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		Map<String, Object> map = this.orderDao.getOrderByIdAndWait(order.getId());
		if(map.size() == 0){
			return ResultUtils.returnError("订单信息异常");
		}
		if(Integer.valueOf(map.get("status").toString()).intValue() != 2){
			return ResultUtils.returnError("订单状态异常");
		}
		return ResultUtils.returnSuccess("查询成功", map);
	}

	@Override
	public Result toPay(Member m, String sIds, String nums) {
		if(sIds == null || sIds.equals("")){
			return ResultUtils.returnError("商品信息异常");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String, Object> memberAddress = this.memberAddressService.getDefAddByMid(m.getId());
		if(memberAddress == null){
			data.put("defAdd", 0);//没有默认收货地址
		}else {
			data.put("defAdd", 1);//有默认收货地址
		}
		data.put("address", memberAddress);
		if(nums == null || nums.equals(null)){//商品页直接去结算
			Map<String, Object> map = this.productSpecificationService.getHIdByPsId(Long.valueOf(sIds));
			List<Map<String, Object>> proList = new ArrayList<Map<String, Object>>();
			Map<String, Object> params = this.productSpecificationService.getProductSpecificationById(Long.valueOf(sIds));

			if(params == null){
				return ResultUtils.returnError("商品不存在");
			}
			String status = params.get("status").toString();
			String name = params.get("name").toString();
			Integer num = Integer.valueOf(params.get("num").toString());
			Integer startNum=Integer.valueOf(params.get("start_num").toString());
			if(m.getHunter() != null){
				if(Long.valueOf(params.get("hId").toString()).longValue() == m.getHunter().getId().longValue()){
					return ResultUtils.returnError("不能购买自己的商品");
				}
			}
			if(status.equals(null) || status=="" || Integer.valueOf(status).intValue()==0){
				return ResultUtils.returnError("商品"+name+"已下架");
			}
			//当库存数量小于起批数量，显示库存不足
			if(num.intValue() < startNum.intValue()){
				return ResultUtils.returnError("商品"+name+"库存不足");
			}



			if(map.get("level") == null || Integer.valueOf(map.get("level").toString()).intValue() == 0){
				map.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}else if(Integer.valueOf(map.get("level").toString()).intValue() == 1){
				map.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}else if(Integer.valueOf(map.get("level").toString()).intValue() == 2){
				map.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}else if(Integer.valueOf(map.get("level").toString()).intValue() == 3){
				map.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
			params.put("count", 1);
			proList.add(params);
			map.put("proList", proList);
			//获取当期时间，判断预计送达时间（22:00之前：+1天。22:00之后：+2天）
			Date now = new Date();
			Date qwdate = null;
			int h=DateUtil.getNowHour();
			if(h>=22){
				qwdate =DateUtil.getDayAfter2(now);
			} else{
				qwdate =DateUtil.getDayAfter(now);
			}
			map.put("predictServiceDate", DateUtil.getDateTimeFormatPuhui(qwdate));
			List<Map<java.lang.String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(map);
			datas.put("list", list);
		}else{//购物车去结算
			String[] sIdArray = sIds.split(",");
			String[] numArray = nums.split(",");
			if(sIdArray.length != numArray.length){
				return ResultUtils.returnError("参数异常");
			}
			int i = sIdArray.length;
			List<Long> hIds = new ArrayList<Long>();
			List<Map<String, Object>> hunters = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < i; j++) {
				Map<String, Object> map = this.productSpecificationService.getHIdByPsId(Long.valueOf(sIdArray[j]));
				Long id = Long.valueOf(map.get("id").toString());
				if(!hIds.contains(id)){
					hIds.add(id);
					hunters.add(map);
				}
			}
			for (Map<String, Object> map : hunters) {
				//获取当期时间，判断预计送达时间（22:00之前：+1天。22:00之后：+2天）
				Date now = new Date();
				Date qwdate = null;
				int h=DateUtil.getNowHour();
				if(h>=22){
					qwdate =DateUtil.getDayAfter2(now);
				} else{
					qwdate =DateUtil.getDayAfter(now);
				}
				map.put("predictServiceDate", DateUtil.getDateTimeFormatPuhui(qwdate));
				Long id = Long.valueOf(map.get("id").toString());
				List<Map<String, Object>> proList = new ArrayList<Map<String, Object>>();
				for (int j = 0; j < i; j++) {
					Map<String, Object> params = this.productSpecificationService.getProductSpecificationById(Long.valueOf(sIdArray[j]));
					if(params == null){
						return ResultUtils.returnError("商品不存在");
					}
					Long hId = Long.valueOf(params.get("hId").toString());
					if(hId.longValue() == id.longValue()){
						proList.add(params);
					}
					String status = params.get("status").toString();
					String name = params.get("name").toString();

					Integer num = Integer.valueOf(params.get("num").toString());
					Integer startNum=Integer.valueOf(params.get("start_num").toString());
					if(m.getHunter() != null){
						if(Long.valueOf(params.get("hId").toString()).longValue() == m.getHunter().getId().longValue()){
							return ResultUtils.returnError("不能购买自己的商品");
						}
					}

					if(status.equals(null) || status=="" || Integer.valueOf(status).intValue()==0){
						return ResultUtils.returnError("商品"+(name.length()>=10?name.substring(0, 10):name)+"已下架");
					}
					if(num.intValue() < Integer.valueOf(numArray[j]).intValue()){
						return ResultUtils.returnError("商品"+name+"库存不足");
					}
					//判断购买数量不能少于起批数量
					if(Integer.parseInt(numArray[j])<startNum.intValue()){
						return ResultUtils.returnError("购买数量不能少于起批数量");
					}
					params.put("count", numArray[j]);
				}
				if(map.get("level") == null || Integer.valueOf(map.get("level").toString()).intValue() == 0){
					map.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
				}else if(Integer.valueOf(map.get("level").toString()).intValue() == 1){
					map.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
				}else if(Integer.valueOf(map.get("level").toString()).intValue() == 2){
					map.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
				}else if(Integer.valueOf(map.get("level").toString()).intValue() == 3){
					map.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
				}
				map.put("proList", proList);
			}
			datas.put("list", hunters);
		}
		data.put("datas", datas);
		return ResultUtils.returnSuccess("结算成功", data);
	}

	/**
	 * 查看订单详情
	 * @param member
	 * @param oid
	 * @return
	 */
	@Override
	public Result detail(Member member, Long oid,String type) {
		Long uid = member.getId();
		log.info(" detail uid: {} ,orderId:{}",uid,oid);
		if (Objects.isNull(uid) || Objects.isNull(oid)) {
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		if (StringUtils.isBlank(type) ){
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		if (!"0".equals(type) && !"1".equals(type)){
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("mid",uid);
		map.put("oid",oid);
		map.put("type",type);
		if ("1".equals(type)){
			Hunter hunter=member.getHunter();
			if (hunter==null){
				return ResultUtils.returnError("非批发商用户");
			}
			Long hid = hunter.getId();
			map.put("hid",hid);
		}
		JSONObject result = new JSONObject();
		try {
			OrderVO orderVO = orderDao.getOrder(map);
			if (orderVO == null) {
				return ResultUtils.returnError("订单不存在");
			}
			//会员登录
			if ("0".equals(type)){
				Long mid=orderVO.getMemberId();
				if (!uid.equals(mid)){
					return ResultUtils.returnError("用户信息不匹配");
				}
			}
			Hunter hunter =member.getHunter();
			if ("1".equals(type) && hunter==null){
				return ResultUtils.returnError("批发商信息异常");
			}

			Long productSpecificationId=orderVO.getProductSpecificationId();
			if (Objects.isNull(productSpecificationId)){
				return ResultUtils.returnError("订单商品规格不存在");
			}
			Long productId =  orderVO.getProductId();
			if (Objects.isNull(productId)){
				return ResultUtils.returnError("订单对应商品不存在");
			}
			//尾款金额
			result.put("negoPrice",BigDecimal.valueOf(orderVO.getProductSalePrice()*orderVO.getNum()).
					subtract(BigDecimal.valueOf(orderVO.getSubscriptionMoney())).divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
			result.put("negotiatePriceStatus",orderVO.getNegotiatePriceStatus());
			//收货人
			result.put("receiveName",orderVO.getReceiveName());
			//电话
			result.put("receivePhone",orderVO.getReceivePhone());
			//收货地址
			result.put("receiveAddress",orderVO.getReceiveAddress());
			//用户留言
			result.put("message",orderVO.getMessage());
			//订单号
			result.put("orderNo",orderVO.getOrderNo());
			//子订单编号
			result.put("orderSubNo",orderVO.getOrderSubNo());
			//支付类型  0预付定金 1全款
			result.put("payType",orderVO.getPayType());
			result.put("hopeServiceDate",DateUtils.dateFormat(orderVO.getHopeServiceDate(),FORMAT_DATETIME));
			result.put("predictServiceDate",DateUtils.dateFormat(orderVO.getPredictServiceDate(),FORMAT_DATETIME));
			//协商价格
			Long  negotiatePrice=orderVO.getNegotiatePrice();

			if (Objects.isNull(negotiatePrice)){
				result.put("negotiatePrice","0.00");
			}else{
				result.put("negotiatePrice",BigDecimalUtil.div(negotiatePrice));
			}

			//服务费用
			Long serviceMoney = orderVO.getServiceMoney();
			if (Objects.isNull(serviceMoney)){
				result.put("serviceMoney","0.00");
			}else{
				result.put("serviceMoney",BigDecimalUtil.div(serviceMoney));
			}

			//实际付款
			Long actualMoney=orderVO.getActualMoney();
			if (Objects.isNull(actualMoney)){
				result.put("actualMoney","0.00");
			}  else {
				result.put("actualMoney",BigDecimalUtil.div(actualMoney));
			}

			//定金价格
			Long   subscriptionMoney= orderVO.getSubscriptionMoney();
			if (Objects.isNull(subscriptionMoney)){
				result.put("subscriptionMoney","0.00");
			}  else {
				result.put("subscriptionMoney", BigDecimalUtil.div(subscriptionMoney));
			}

			Integer status = orderVO.getStatus();
			if ( status<=3 ){
				result.put("actPrice",BigDecimalUtil.div(subscriptionMoney));
			} else {
				//实际付款金额
				Long act = Objects.isNull(orderVO.getNegotiatePrice())?0L:orderVO.getNegotiatePrice();
				result.put("actPrice",BigDecimalUtil.div(act+subscriptionMoney));
			}
			//订单状态
			result.put("status",orderVO.getStatus());
			//订单创建时间
			result.put("createTime",DateUtils.dateFormat(orderVO.getCreatedTime(),FORMAT_DATETIME));
			//商品购买数量
			result.put("num",orderVO.getNum());
			int state = orderVO.getStatus();
			//ProductSpecificationVO productSpecificationVO=productSpecificationDao.getProductSpecificationVO(productSpecificationId);
			//商品规格
			result.put("content",orderVO.getProductSpecificationName());
			//List<Map> productDetailResult=productDetailDao.getProductDetailText(productId);
			//log.info("productDetailResult : {} ", JSON.toJSONString(productDetailResult));
			//Map<String,Object> detailResult =productDetailResult.get(0);
			result.put("productName",orderVO.getProductName());
			Long price = orderVO.getProductSalePrice();
			if (Objects.isNull(price)){
				result.put("price","0.00");
			} else {
				result.put("price",BigDecimalUtil.div(price));
			}

			result.put("address",orderVO.getImgAddress());
			result.put("hunterId",orderVO.getHunterId());
			result.put("productId",orderVO.getProductId());

			result.put("productStatus",orderVO.getProductStatus());
			//付款后，添加商品跟踪信息
			if ( state > 1  ){
				//根据子订单编号查询跟踪信息
				List<ProductTraceVO> productTraceVOs = productTraceService.getTrace(orderVO.getOrderSubNo());
				JSONArray ary = new JSONArray();
				for (ProductTraceVO productTraceVO:productTraceVOs){
					JSONObject trace = new JSONObject();
					trace.put("createdTime",DateUtils.dateFormat(productTraceVO.getCreatedTime(),FORMAT_DATETIME));
					trace.put("content",productTraceVO.getContent());

					List<Map<String,Object>> address=productTraceService.getAttachmentList(productTraceVO.getId());
					JSONArray addressAry = new JSONArray();
					for (Map<String,Object> resultMap :address){
						addressAry.add(resultMap.get("address"));
					}
					trace.put("address",addressAry);
					ary.add(trace);
				}
				result.put("trace",ary);

			}

			switch (state){
				//已付定金
				case 1:


					//代发货
				case 2:
					result.put("subscriptionTime",DateUtils.dateFormat(orderVO.getSubscriptionTime(),FORMAT_DATETIME));
					//付款时间
					result.put("payTime",DateUtils.dateFormat(orderVO.getPayTime(),FORMAT_DATETIME));
					break;
				//待收货
				case 3:
					result.put("subscriptionTime",DateUtils.dateFormat(orderVO.getSubscriptionTime(),FORMAT_DATETIME));
					//付款时间
					result.put("payTime",DateUtils.dateFormat(orderVO.getPayTime(),FORMAT_DATETIME));
					//发货时间
					result.put("sendTime",DateUtils.dateFormat(orderVO.getSendTime(),FORMAT_DATETIME));
					break;
				//已评价
				case 4:

					//5申请退款
				case 5:

					//已退款
				case 6:

					//已收货
				case 7:
					Result resultOrder = orderCommentService.getOrderCommentByOrderAndMember(oid);
					if (resultOrder.getCode()==1){
						String content = (String) resultOrder.getContent();
						JSONObject robj = new JSONObject();
						if (!Objects.isNull(content)){
							robj.put("content",content);
						}else{
							robj.put("content","");
						}
						result.put("comment",robj);
					}
					result.put("subscriptionTime",DateUtils.dateFormat(orderVO.getSubscriptionTime(),FORMAT_DATETIME));
					//付款时间
					result.put("payTime",DateUtils.dateFormat(orderVO.getPayTime(),FORMAT_DATETIME));
					//发货时间
					result.put("sendTime",DateUtils.dateFormat(orderVO.getSendTime(),FORMAT_DATETIME));
					//收货时间
					result.put("receiveTime",DateUtils.dateFormat(orderVO.getReceiveTime(),FORMAT_DATETIME));
					break;
				default:

					break;
			}
			return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),result);
		} catch (Exception e){
			log.error(e.getMessage(),e);
			return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
		}

	}

	/**
	 * 批发商个人中心--收入明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Result findOrderByHunterId(Long hunterId, Integer page, Integer rows, Member member) {

		HashMap<String, Object> param = new HashMap<String,Object>();

		Hunter hunter=member.getHunter();

		if(null==hunter){

			return ResultUtils.returnError("角色异常");
		}

		if(hunterId==null||"".equals(hunterId)){

			return ResultUtils.returnError("用户id不能为空");
		}

		Long hunterI1=member.getHunter().getId();//登录的批发商id

		if(hunterId.longValue() != hunterI1.longValue()){

			return ResultUtils.returnError("参数异常");
		}

		param.put("hunterId", hunterId);
		param.put("startIndex", (page-1)*rows);
		param.put("endIndex",rows);

		List<Map<String,Object>> orderHunterList = orderDao.findOrderByHunterId(param);

		if(orderHunterList.size()>0){
			for (Map<String, Object> orderHunter : orderHunterList) {
				String createdTime=DateUtils.dateFormat((Date)orderHunter.get("createdTime"), "yyyy-MM-dd HH:mm:ss");
				orderHunter.put("createdTime", createdTime);
			}

			return ResultUtils.returnSuccess("请求成功", orderHunterList);
		}else{
			return ResultUtils.returnError("没有数据");
		}
	}

	/**
	 * 批发商个人中心--收入订单明细
	 * @param hunterId
	 * @param orderNum
	 * @return
	 */
	@Override
	public Result findOrderByorderNum(Long hunterId, String orderNum, Member member) {

		HashMap<String, Object> param = new HashMap<String,Object>();

		Hunter hunter=member.getHunter();

		if(null==hunter){

			return ResultUtils.returnError("角色异常");
		}

		if(hunterId==null||"".equals(hunterId)){

			return ResultUtils.returnError("用户id不能为空");
		}

		if("".equals(orderNum)){

			return ResultUtils.returnError("订单号不能为空");
		}

		Long hunterI1=member.getHunter().getId();//登录的批发商id

		if(hunterId.longValue() != hunterI1.longValue()){

			return ResultUtils.returnError("参数异常");
		}

		param.put("hunterId", hunterId);
		param.put("orderNum",orderNum);

		List<Map<String,Object>> orderHunterList = orderDao.findOrderByorderNum(param);

		if(orderHunterList.size()>0){

			Map<String,Object> orderHunter=orderHunterList.get(0);

			String createdTime=DateUtils.dateFormat((Date)orderHunter.get("createdTime"), "yyyy-MM-dd HH:mm:ss");
			orderHunter.put("createdTime", createdTime);

			orderHunter.put("status", "直营收入/推荐收入");

			return ResultUtils.returnSuccess("请求成功", orderHunter);

		}else{
			return ResultUtils.returnError("没有数据");
		}
	}

	@Override
	public Result toCreateOrder(Member m, OrderListVO list, Long aId) {
		if(aId == null){
			return ResultUtils.returnError("请选择收货地址");
		}
		if(list == null){
			return ResultUtils.returnError("请选择商品");
		}
		try {
			Result result = this.rpcOrderService.toCreateOrder(m, list, aId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("下单异常");
		}
	}

	@Override
	@Transactional
	public Result toPayOrderByWait(Member m, Order order, String ip) {
		Order o = this.orderDao.getOrderById(order.getId());
		if(o == null){
			return ResultUtils.returnError("订单不存在");
		}
		if(o.getMember().getId().longValue() != m.getId().longValue()){
			return ResultUtils.returnError("订单信息异常");
		}
		if(o.getStatus().intValue() != 0){
			return ResultUtils.returnError("订单状态异常");
		}
		WxPayOrder wxPayOrder = new WxPayOrder();
		//生成订单号
		String wxOrderNum=UtilDate.getOrderNum()+UtilDate.getThree();
		wxPayOrder.setWxOrderNum(wxOrderNum);
		wxPayOrder.setOrderInfo(order);
		wxPayOrder.setStatus(0);
		if(o.getPayType().intValue() == 0){
			wxPayOrder.setType(1);
		}else{
			wxPayOrder.setType(0);
		}

		wxPayOrder.setMoney(o.getTotalPrice());
		try {
			return this.rpcWxPayOrderService.saveRecordByPay(wxPayOrder, ip);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("支付记录生成异常");
		}

	}

	@Override
	public Result toPayOrderByNegotiate(Member m, Order order, String ip) {
		Order o = this.orderDao.getOrderById(order.getId());
		if(o == null){
			return ResultUtils.returnError("订单不存在");
		}
		if(o.getMember().getId().longValue() != m.getId().longValue()){
			return ResultUtils.returnError("订单信息异常");
		}
		if(o.getStatus().intValue() != 1){
			return ResultUtils.returnError("订单状态异常");
		}
		Long negotiatePrice = o.getNegotiatePrice();
		Long servicePrice = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(negotiatePrice*0.13, 0))))+"");
		Long money = negotiatePrice.longValue() + servicePrice.longValue();
		WxPayOrder wxPayOrder = new WxPayOrder();
		//生成订单号
		String wxOrderNum=UtilDate.getOrderNum()+UtilDate.getThree();
		wxPayOrder.setWxOrderNum(wxOrderNum);
		wxPayOrder.setOrderInfo(order);
		wxPayOrder.setStatus(0);
		wxPayOrder.setType(2);
		wxPayOrder.setMoney(negotiatePrice);
		try {
			return this.rpcWxPayOrderService.saveRecordByPay(wxPayOrder ,ip);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("支付记录生成异常");
		}
	}

	@Override
	public Result toPayBack(Member m, Order order) {
		Order o = this.orderDao.getOrderById(order.getId());
		if(o == null){
			return ResultUtils.returnError("订单信息异常");
		}
		if(o.getMember().getId().longValue() != m.getId().longValue()){
			return ResultUtils.returnError("登录信息异常");
		}
		o.setRefundType(0);
		o.setRefundMsg(order.getRefundMsg());
		try {
			return this.rpcOrderService.memberToPayBack(o);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("退款异常");
		}
	}
	/**
	 * 确认收货
	 * @param id
	 * @param oid   订单ID
	 * @return
	 */
	@Override
	@Transactional
	public Result confirmReceive(Long id, String oid) {
		if (Objects.isNull(id) || StringUtils.isBlank(oid)) {
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("mid", id);
		map.put("oid", oid);
		try {
			OrderVO orderVO = orderDao.getOrder(map);
			if ( orderVO == null){
				return ResultUtils.returnError("订单不存在");
			}
			int state = orderVO.getStatus();
			if (state!=3){
				return ResultUtils.returnError("订单非待收货状态,无法确认收货.");
			}
			Result result=rpcOrderService.confirmReceive(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
		}
	}

	@Override
	public Result getOrderList(Member m, String status, Integer page, Integer rows) {
		/*if(orderStatus == null || orderStatus.equals("")){
			return ResultUtils.returnError("订单状态为空");
		}*/
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		if(status==null || status.equals("")){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", m.getId());
			params.put("status", status);
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			List<Map<String, Object>> list =  this.orderDao.getOrderListByStatus(params);
			orderList.addAll(list);
		}else{
			String[] split = status.split(",");
			for (String orderStatus : split) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", m.getId());
				params.put("status", orderStatus);
				params.put("startIndex", (page-1)*rows);
				params.put("endIndex",rows);
				List<Map<String, Object>> list =  this.orderDao.getOrderListByStatus(params);
				orderList.addAll(list);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	@Override
	public Result getHunterOrderList(Member m, String status, Integer page, Integer rows) {
		if(m.getHunter() == null){
			return ResultUtils.returnError("用户登录信息异常");
		}
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		if(status==null || status.equals("")){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", m.getId());
			params.put("status", status);
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			List<Map<String, Object>> list =  this.orderDao.getHunterOrderListByStatus(params);
			for (Map<String, Object> map : list) {
				String a = (String)map.get("receive_address");
				String address = "";
				if(a.contains("北京")||a.contains("天津")||a.contains("重庆")||a.contains("上海")){
					address = a.split("区")[0];
				}else {
					address = a.split("市")[0];
				}
				map.put("areaAdd", address);
			}
			orderList.addAll(list);
		}else{
			String[] split = status.split(",");
			for (String orderStatus : split) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", m.getId());
				params.put("status", orderStatus);
				params.put("startIndex", (page-1)*rows);
				params.put("endIndex",rows);
				List<Map<String, Object>> list =  this.orderDao.getHunterOrderListByStatus(params);
				for (Map<String, Object> map : list) {
					String a = (String)map.get("receive_address");
					String address = "";
					if(a.contains("北京")||a.contains("天津")||a.contains("重庆")||a.contains("上海")){
						address = a.split("区")[0];
					}else {
						address = a.split("市")[0];
					}
					map.put("areaAdd", address);
				}
				orderList.addAll(list);
			}
		}
		List<Map<String, Object>> count = this.orderDao.getHunterOrderListByStatusCount(m.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderList", orderList);
		data.put("count", count);
		return ResultUtils.returnSuccess("查询成功", data);
	}

	/**
	 * 确认发货
	 * @param pmember
	 * @param oid
	 * @return
	 */
	@Override
	public Result confirmSend(Member pmember, String oid) {
		Long id = pmember.getId();
		if (Objects.isNull(id) || StringUtils.isBlank(oid)) {
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		Hunter phunter = pmember.getHunter();
		if (phunter==null){
			return ResultUtils.returnError("非批发商不能发货.");
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("mid", id);
		map.put("oid", oid);
		try {
			OrderVO orderVO = orderDao.getOrder(map);
			if ( orderVO == null){
				return ResultUtils.returnError("订单不存在");
			}
			int state = Objects.isNull(orderVO.getStatus())?0:orderVO.getStatus();
			if (state!=2){////0代付款 1已付定金 (2待发货,无此状态) 3待收货 4已评价 5申请退款   6已退款 7已收货
				return ResultUtils.returnError("订单非待发货状态,无法确认发货.");
			}
			Result result=rpcOrderService.confirmSend(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
		}
	}

	/**
	 * 查看退款信息
	 * @param id
	 * @param oid
	 * @return
	 */
	@Override
	public Result viewRefundMsg(Long id, Long oid) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("oid", oid);
		try {
			OrderVO orderVO = orderDao.getOrder(map);
			if (orderVO==null){
				return ResultUtils.returnError("订单不存在.");
			}
			int state = orderVO.getStatus();
			Integer type= Objects.isNull(orderVO.getRefundType())?0:orderVO.getRefundType();
			String msg=orderVO.getRefundMsg();
			if (type==1){
				msg = orderVO.getHunterRefundMsg();
			}
			JSONObject json = new JSONObject();
			json.put("applyRefundTime",DateUtils.dateFormat(orderVO.getApplyRefundTime(),DateUtils.DATE_PATTERN_07));
			json.put("msg",msg);
			json.put("state",state);
			json.put("refundType",orderVO.getRefundType());
			switch (state){
				case 5:
					break;
				case 6:
					json.put("refundTime",DateUtils.dateFormat(orderVO.getRefundTime(),DateUtils.DATE_PATTERN_07));
					break;
				default:
					break;
			}
			return ResultUtils.returnSuccess("成功",json);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError("接口调用失败.");
		}
	}

	/**
	 * 用户输入尾款价格
	 * @param member
	 * @return
	 */
	@Override
	public Result addNegotiatePriceForOrder(Member member, Long orderId, String negotiatePrice) {

		Long memberId = member.getId();
		if (Objects.isNull(memberId)) {
			return ResultUtils.returnError("角色异常");
		}
		if (Objects.isNull(orderId)) {
			return ResultUtils.returnError("订单id不能为空");
		}
		if (Objects.isNull(negotiatePrice)) {
			return ResultUtils.returnError("尾款价格不能为空");
		}
		String validateStatue=ValidateMoneyUtils.validateMoney(negotiatePrice,"");
		if(!"SUCCESS".equals(validateStatue)){
			return ResultUtils.returnError(validateStatue);
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mId", memberId);
		params.put("orderId", orderId);
		Order order = this.orderDao.getOrderByOrderId(params);
		if(order == null){
			return ResultUtils.returnError("订单信息异常");
		}
		String orderNo=order.getOrderNo();
		if(orderNo == null || "".equals(orderNo)){
			return ResultUtils.returnError("订单号异常");
		}
		if(null ==order.getHunter().getId() || "".equals(order.getHunter().getId())){
			return ResultUtils.returnError("订单id异常");
		}
		Long hunterId=order.getHunter().getId();

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		param.put("hunterId", hunterId);
		List<Map<String, Object>> orderList = this.orderDao.getOrderListByOrderNo(param);//根据订单号查询订单列表
		if(orderList.size()<1){
			return ResultUtils.returnError("订单查询异常");
		}

		String ids="";
		for (Map<String, Object> map : orderList) {
			ids+=map.get("id")+",";
		}

		Double aa=Double.valueOf(negotiatePrice);
		Long negotiate = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(aa*100, 0))))+"");//单位分
		order.setNegotiatePrice(negotiate);
		try {
			Result result=rpcOrderService.addNegotiatePriceForOrder(order,ids);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("尾款价格异常");
		}

	}

	/**
	 * 商家对尾款价格进行操作
	 * @param member
	 * @param orderId
	 * @param type
	 * @return
	 */
	@Override
	public Result chooseNegotiatePriceForOrder(Member member, Long orderId, String type) {
		Long memberId = member.getId();
		if (Objects.isNull(memberId)) {
			return ResultUtils.returnError("角色异常");
		}
		if (Objects.isNull(orderId)) {
			return ResultUtils.returnError("订单id不能为空");
		}
		if (Objects.isNull(type)) {
			return ResultUtils.returnError("类型不能为空");
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mId", memberId);
		params.put("orderId", orderId);
		Order order = this.orderDao.getOrderByOrderIdAndMid(params);
		if(order == null){
			return ResultUtils.returnError("订单信息异常");
		}

		try {
			Result result=rpcOrderService.chooseNegotiatePriceForOrder(order,type);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("操作异常");
		}
	}


	/**
	 * 普惠 用户中心获取订单列表
	 * @param m
	 * @param statuses
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Result getMemberOrderList(Member m, String statuses, Integer page, Integer rows) {
		log.info("status:{},member:{}",statuses,m);
		if (StringUtils.isBlank(statuses)){
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		List<Map<String,Object>> result = Lists.newArrayList();
		String[] statuss = statuses.split(",");
		for (String status:statuss){
			Map<String,Object> memberParam = Maps.newHashMap();
			memberParam.put("mid",m.getId());
			memberParam.put("status",status);
			memberParam.put("page",(page-1)*rows);
			memberParam.put("size",rows);
			List<Map<String,Object>> orderNos = orderDao.getOrderNoByMember(memberParam);
			orderNos.forEach(e-> {
				String orderNo = e.get("orderNo").toString();
				Long hid = Long.valueOf(e.get("hid").toString());
				Map<String, Object> hunterInfo = hunterService.getHunterLogoImage(hid);
				if (!Objects.isNull(hunterInfo)) {
					Map<String, Object> hunter = Maps.newHashMap();
					Map<String, Object> param = Maps.newHashMap();
					param.put("orderNo", orderNo);
					param.put("hid", hid);
					param.put("mid", m.getId());
					param.put("status", status);
					List<Map<String, Object>> resultOrders = orderDao.getOrderByMemberAndOrderNo(param);
					BigDecimal totalPrice=BigDecimal.ZERO;
					BigDecimal negoPrice=BigDecimal.ZERO;
					BigDecimal subscripePrice=BigDecimal.ZERO;
					Integer orderStatus=null;
					for (Map<String, Object> resultOrder : resultOrders) {
						BigDecimal price = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("price").toString()));
						BigDecimal num = BigDecimal.valueOf(Long.parseLong(resultOrder.get("num").toString()));
						BigDecimal negotationPrice = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("negotiate_price").toString()));
						BigDecimal subscriptionMoney = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("subscription_money").toString()));
						totalPrice=totalPrice.add(price.multiply(num));
						negoPrice=negoPrice.add(negotationPrice);
						subscripePrice=subscripePrice.add(subscriptionMoney);
						orderStatus = (Integer)resultOrder.get("status");
					}
					hunterInfo.put("totalPrice",totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					hunterInfo.put("negoPrice",negoPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					hunterInfo.put("subscriptionPrice",subscripePrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					hunterInfo.put("status",orderStatus);
					hunterInfo.put("content", resultOrders);
					hunter.putAll(hunterInfo);
					result.add(hunter);
				}
			});
		}

		return ResultUtils.returnSuccess("查询成功", result);
	}

	/**
	 * 普惠 批发商列表
	 * @param m
	 * @param statuses 订单状态
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Result getPuHuiHunterOrderList(Member m, String statuses, Integer page, Integer rows) {

		log.info("status:{},member:{}",statuses,m);
		if (StringUtils.isBlank(statuses)){
			return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		if (!Objects.isNull(m)){
			Hunter hid = m.getHunter();
			if (Objects.isNull(hid)){
				return ResultUtils.returnError("非批发中心用户");
			}
		}
		String[] statuss = statuses.split(",");
		Map<String,Object> lastResult = Maps.newHashMap();
		List<Map<String,Object>> result = Lists.newArrayList();
		for(String status:statuss){
			Long hid = m.getHunter().getId();
			Map<String,Object> memberParam = Maps.newHashMap();
			memberParam.put("hid",hid);
			memberParam.put("status",status);
			memberParam.put("page",(page-1)*rows);
			memberParam.put("size",rows);
			List<Map<String,Object>> members = orderDao.getAllMemberByMap(memberParam);
			members.forEach(e->{
				Long memberId = Long.valueOf(e.get("mid").toString());
				String orderNo =e.get("orderNo").toString();
				Map<String,Object> memberInfo = memberService.getMemberLogoImage(memberId);
				if (!Objects.isNull(memberInfo)){
					Map<String,Object> member = Maps.newHashMap();
					Map<String,Object> map = Maps.newHashMap();
					map.put("mid",memberId);
					map.put("hid",hid);
					map.put("status",status);
					map.put("orderNo",orderNo);
					List<Map<String,Object>> resultOrders = orderDao.getOrderByMemberAndOrderNo(map);
					BigDecimal totalPrice=BigDecimal.ZERO;
					BigDecimal negoPrice=BigDecimal.ZERO;
					BigDecimal subscripePrice=BigDecimal.ZERO;
					Integer orderStatus=null;
					String receiveAddress=null;
					String receiveName=null;
					String receivePhone=null;
					String areaAdd=null;
					for (Map<String, Object> resultOrder : resultOrders) {
						BigDecimal price = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("price").toString()));
						BigDecimal num = BigDecimal.valueOf(Long.parseLong(resultOrder.get("num").toString()));
						BigDecimal negotationPrice = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("negotiate_price").toString()));
						BigDecimal subscriptionMoney = BigDecimal.valueOf(Double.parseDouble(resultOrder.get("subscription_money").toString()));
						totalPrice=totalPrice.add(price.multiply(num));
						negoPrice=negoPrice.add(negotationPrice);
						subscripePrice=subscripePrice.add(subscriptionMoney);
						String a = (String)resultOrder.get("receive_address");
						String b = (String)resultOrder.get("receive_phone");
						String c = (String)resultOrder.get("receive_name");
						Integer d = (Integer)resultOrder.get("status");
						String address;
						if(a.contains("北京")||a.contains("天津")||a.contains("重庆")||a.contains("上海")){
							address = a.split("区")[0];
						}else {
							address = a.split("市")[0];
						}
						orderStatus=d;
						receiveAddress=a;
						areaAdd=address;
						receivePhone=b;
						receiveName=c;

						resultOrder.put("areaAdd", address);
					}
					memberInfo.put("totalPrice",totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					memberInfo.put("negoPrice",negoPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					memberInfo.put("subscriptionPrice",subscripePrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					memberInfo.put("status",orderStatus);
					memberInfo.put("areaAdd",areaAdd);
					memberInfo.put("receive_phone",receivePhone);
					memberInfo.put("receive_address",receiveAddress);
					memberInfo.put("receive_name",receiveName);
					memberInfo.put("content",resultOrders);
					member.putAll(memberInfo);
					result.add(member);
				}
			});
			lastResult.put("count",orderDao.getHunterOrderListByStatusCount(m.getId()));
			lastResult.put("orderList",result);
		}
		return ResultUtils.returnSuccess("查询成功", lastResult);
	}

	@Override
	public Result createOrder(Member member, String json) {
		if (Objects.isNull(member) || StringUtils.isBlank(json)){
			return ResultUtils.returnError("参数缺少");
		}
		try {
			List<OrderDTO> orderDTOS =JSONArray.parseArray(json,OrderDTO.class);
			return rpcOrderService.createOrder(member,orderDTOS);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError("下单异常");
		}
	}

	@Override
	public Result recurOrder(String json, Member member) {
		if (Objects.isNull(member) || StringUtils.isBlank(json)) {
			return ResultUtils.returnError("参数缺少");
		}
		try {
			List<OrderDTO> orderDTOS = JSONArray.parseArray(json, OrderDTO.class);
			return rpcOrderService.recurOrder(orderDTOS, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.returnError("下单异常");
		}
	}
	/**
	 * 用户取消订单（商户供应商接单前）by  wangxueyang
	 * 所有要取消的订单必须在订单状态为待结单状态
	 * @param orderNo
	 * @return
	 */
	@Override
	public Result cancelOrderForMerchant (String orderNo){
		Result result = null;
		try {
			//查询当前订状态
			List<Integer> status = orderDao.getOrderStatusByOrdeNo(orderNo);
			boolean flag = false;
			if (status.size() > 0) {
				for (int i = 0; i < status.size(); i++) {
					if (status.get(i) != 1) {
						flag = true;
						break;
					}
				}
			} else {
				log.info("当前订单的子订单状态获取异常！");
				flag = true;
			}
			if (flag) {
				return ResultUtils.returnError("当前订单状态存在异常，取消订单失败！");
			}
			log.info("*******************  订单校验通过，开始操作取消订单  *****************");
			result = this.rpcOrderService.cancelOrderByOrderNo(orderNo);
		} catch (Exception e) {
			log.info("取消订单异常：" + e.getMessage());
			result = ResultUtils.returnError("系统异常，订单取消失败！");
		}
		return result;
	}

	@Override
	public Result cancelOrderForSuppler (String orderNo, Integer status){
		Result result = null;
		try {
			//查询当前订状态
			List<Integer> s = orderDao.getOrderStatusByOrdeNo(orderNo);
			boolean flag = false;
			if (s.size() > 0) {
				for (int i = 0; i < s.size(); i++) {
					if (s.get(i) != status) {
						flag = true;
						break;
					}
				}
			} else {
				log.info("当前订单的子订单状态获取异常！");
				flag = true;
			}
			if (flag) {
				return ResultUtils.returnError("当前订单状态存在异常，取消订单失败！");
			}
			log.info("*******************  订单校验通过，开始操作取消订单  *****************");
			result = this.rpcOrderService.cancelOrderByOrderNo(orderNo);
		} catch (Exception e) {
			log.info("取消订单异常：" + e.getMessage());
			result = ResultUtils.returnError("系统异常，订单取消失败！");
		}
		return result;
	}

	/**
	 * 买家（商户）查询订单列表
	 * @return
	 */
	@Override
	public Result queryOnllieOrders (Map < String, Object > map){
		Result result = null;
		try {
			//查询主订单
			List<Map<String, Object>> pOrders = orderDao.queryParentOrders(map);
			//分别查询每个主订单下包含的总金额
			if (pOrders.size() > 0) {
				for (int i = 0; i < pOrders.size(); i++) {
					Long totalMoney = orderDao.countOrderMoney(pOrders.get(i).get("orderNo").toString());
					//处理金额
					pOrders.get(i).put("totalMoney", DoubleUtils.formatRound(totalMoney / 100.00, 2));
				}
			}

			result = ResultUtils.returnSuccess("查询订单列表成功！", pOrders);
		} catch (Exception e) {
			log.info("查询线上订单异常：" + e.getMessage());
			result = ResultUtils.returnError("查询线上订单失败！");
		}
		return result;
	}

	/**
	 * 查询订单详情
	 * @param orderNo
	 * @param status
	 * @return
	 */
	@Override
	public Result queryOnlineOrderDetails(String orderNo, Integer status) {
		try{
		log.info("**************      开始查询订单详情     *********************");
		//根据主订单号查询对应的 收货人 联系电话 收货地址  订单号 订单生成时间  定金支付时间  接单时间  发货时间	尾款（收货）支付时间 卖家名称  卖家头像 根据主订单 计算出相应的  订单总金额 定金金额 定金
		Map<String, Object> pOrder = orderDao.queryOrderDetailByOrderNo(orderNo);
		//根据主订单号查询对应的子订单 商品名称 商品图片 商品数量  商品规格（单价）
			List<Map<String, Object>> products = orderDao.queryOrderForProducts(orderNo);
			pOrder.put("products",products);
			//根据订单状态剔除多余字段
			//0代付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货 8 已取消
			switch (status){
				case 0:
					pOrder.remove("subscriptionTime");
					pOrder.remove("sendTime");
					pOrder.remove("agreeTime");
					pOrder.remove("receiveTime");
				case 1:
				case 2:
					pOrder.remove("sendTime");
					pOrder.remove("agreeTime");
					pOrder.remove("receiveTime");
					break;
				case 3:
					pOrder.remove("receiveTime");
					break;
				default:
					break;
			}
			return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),pOrder);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
		}
	}

    /**
     * 供应商接单
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public Result agreeOrderBySupplier(String orderNo) {
        Result result = null;
        try{
            //根据主订单号 查询所有的子订单状态 待接单  已付定金 1
            List<Integer> s = orderDao.getOrderStatusByOrdeNo(orderNo);
            boolean flag = false;
            if (s.size()>0){
                for (int i = 0 ; i<s.size() ; i++){
                    if (s.get(i) == 1){
                        continue;
                    }
                    flag = true;
                }
            }else {
                throw new Exception("获取子订单下状态异常");
            }
            if (flag){
                //订单状态校验不通过
                result = ResultUtils.returnError("订单状态存在异常，接单失败！");
            }else{
                log.info("+++++++++++++++   订单校验通过  开始更新订单状态   ++++++++++++++++++++");
                Integer num = this.rpcOrderService.agreeOrderBySupplyer(orderNo);
                if (num>0){
                    result = ResultUtils.returnSuccess("接单成功！");
                }else{
                    result = ResultUtils.returnError("接单失败");
                }
            }
        }catch (Exception e){
            log.info("取消订单异常："+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result = ResultUtils.returnError("接单失败!");
        }
        return result;
    }

	/**
	 * 结算中心订单列表
	 * @param member
	 * @param time
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Result getAccountOrderList(Member member, String time,int page,int size) {
		Map<String,Object> query = Maps.newHashMap();
		query.put("status","0,3");
		if (StringUtils.isBlank(time)){
			query.put("time",null);
		}else {
			query.put("time",time);
		}

		query.put("id",member.getId());
		query.put("startIndex",(page-1)*size);
		query.put("endIndex",size);
		List<String> orderNos = orderDao.getDistinctOrderNos(query);
		List<Map<String,Object>> finalResult = Lists.newArrayList();
		orderNos.forEach($_->{
			query.put("orderNo",$_);
			List<Map<String,Object>> orders = orderDao.getOrderListByCondition(query);
			int count = orders.size();
			if (count>0){
				//总价
				BigDecimal totalPrice = orders.stream().map(p -> BigDecimal.valueOf(Double.valueOf(p.get("total_price").toString()))).reduce((sum,price)->sum.add(price)).get();
				//定金价
				BigDecimal subPrice = orders.stream().map(p -> BigDecimal.valueOf(Double.valueOf(p.get("subscription_money").toString()))).reduce((sum,price)->sum.add(price)).get();
				//店鋪暱稱
				String hunterName = orders.stream().map(n -> n.get("nickname").toString() ).findFirst().get();
				//店鋪圖片
				String imageUrl = orders.stream().map(i -> i.get("logoImg").toString()).findFirst().get();
				String status = orders.stream().map(i -> i.get("status").toString()).findFirst().get();
				String created_time = orders.stream().map(i -> i.get("created_time").toString()).findFirst().get();
				//希望送達時間
				String hope_service_date = orders.stream().map(i -> i.get("hope_service_date").toString()).findFirst().get();
				String payType = orders.stream().map(i -> i.get("pay_type").toString()).findFirst().get();
				//訂單編號
				String orderNo = orders.stream().map(i -> i.get("order_no").toString()).findFirst().get();
				//商品列表
				List<Map<String,Object>> products = new ArrayList<>();
				orders.stream().map( m -> {
					Map<String,Object> o = new HashMap<>();
					o.put("id",m.get("id"));
					o.put("order_no",m.get("order_no"));
					o.put("product_name",m.get("product_name"));
					o.put("product_specification_name",m.get("product_specification_name"));
					o.put("product_type_name",m.get("product_type_name"));
					o.put("address",m.get("address"));
					o.put("price",m.get("price"));
					o.put("num",m.get("num"));
					o.put("hid",m.get("hid"));
					o.put("spid",m.get("spid"));
					o.put("stid",m.get("stid"));
					o.put("pid",m.get("pid"));
					o.put("start_time",m.get("start_time"));
					o.put("end_time",m.get("end_time"));
					return o;
				}).forEach(products::add);

				Map<String,Object> result = new HashMap<>();
				result.put("orderNo",orderNo);
				//總價
				result.put("totalPrice",totalPrice.toString());
				//定金價格
				result.put("subscriptionMoney",subPrice.toString());
				//尾款
				result.put("finalPayment",totalPrice.subtract(subPrice).toString());
				//商品數量
				result.put("count",count);
				result.put("hunterName",hunterName);
				result.put("imageUrl",imageUrl);
				//訂單狀態
				result.put("status",status);
				result.put("created_time",created_time);
				result.put("hope_service_date",hope_service_date);
				result.put("payType",payType);
				//商品列表
				result.put("products",products);
				finalResult.add(result);
			}
		});
		return ResultUtils.returnSuccess("成功",finalResult);
	}

	/**
	 * 修改期望时间
	 * @param time
	 * @param orderNO
	 * @param member
	 * @return
	 */
	@Override
	public Result updateOrder(String time, String orderNO, Member member) {
		if (StringUtils.isBlank(time) ||
				Objects.isNull(orderNO) ||
				Objects.isNull(member)){
			return ResultUtils.returnError("参数错误");
		}
		return rpcOrderService.updateOrder(time,orderNO,member);
	}

	/**
	 * 删除订单
	 * @param id
	 * @param member
	 * @return
	 */
	@Override
	public Result deleteOrder(Long id, Member member) {
		return rpcOrderService.deleteBySubOrderNo(id,member);
	}

	/**
	 * 修改订单数量
	 * @param id
	 * @param num
	 * @param member
	 * @return
	 */
	@Override
	public Result udpateOrderNum(Long id, Long num, Member member) {
		return rpcOrderService.updateOrderNum(id,num,member);
	}

	/**
	 * 返回购物车数据列表
	 * @param orderNo
	 * @param member
	 * @return
	 */
	@Override
	public Result backShopCart(String orderNo, Member member) {
		if (StringUtils.isBlank(orderNo) && Objects.isNull(member)){
			return ResultUtils.returnError("参数错误");
		}
		try {
			List<Map<String,Object>> orderList =  orderDao.getOrderListByOrderNoForShopCart(orderNo,member.getId());
			return ResultUtils.returnSuccess("成功",orderList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError("内部服务异常");
		}
	}

	/**
	 * 返回修改
	 * @param json
	 * @param member
	 * @return
	 */
	@Override
	public Result recurOrder(String type,String json, Member member) {
		if (Objects.isNull(member) || StringUtils.isBlank(json)|| StringUtils.isBlank(type)){
			return ResultUtils.returnError("参数缺少");
		}
		try {
			List<OrderDTO> orderDTOS =JSONArray.parseArray(json,OrderDTO.class);
			return rpcOrderService.recurOrder(type,orderDTOS,member);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResultUtils.returnError("下单异常");
		}
	}
}
