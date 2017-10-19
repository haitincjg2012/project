package com.alqsoft.service.impl.order;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alqsoft.dto.OrderDTO;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.hunter.HunterDao;
import com.alqsoft.dao.order.OrderDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.entity.membermoney.MemberMoney;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.shopcart.ShopCart;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.mybatis.dao.IAgentDao;
import com.alqsoft.mybatis.dao.IOrderDao;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.memberaddress.MemberAddressService;
import com.alqsoft.service.membermoney.MemberMoneyService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.ordercomment.OrderCommentService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.shopcart.ShopCartService;
import com.alqsoft.service.wxpayorder.WxPayOrderService;
import com.alqsoft.utils.DateUtil;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.utils.UtilDate;
import com.alqsoft.vo.CreateOrderVO;
import com.alqsoft.vo.OrderListVO;
import com.alqsoft.vo.ProVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly=true)
public class OrderServiceImpl implements OrderService{
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderDao  orderDao;
	@Autowired
	private MemberAddressService memberAddressService;
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private WxPayOrderService wxPayOrderService;
	@Autowired
	private HunterService hunterService;
	@Autowired
	private ShopCartService shopCartService;
	@Autowired
	private MemberMoneyService memberMoneyService;
	@Autowired
	private OrderCommentService orderCommentService;
	@Autowired
	private InitParamPc initParams;
	@Autowired
	private ProductService productService;
	@Autowired
	private IOrderDao iOrderDao;
	@Autowired
	private IAgentDao iAgentDao;
	@Autowired
	private OrderService orderService;

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order get(Long arg0) {
		// TODO Auto-generated method stub
		return orderDao.findOne(arg0);
	}

	@Override
	@Transactional
	public Order saveAndModify(Order arg0) {
		// TODO Auto-generated method stub
		return orderDao.save(arg0);
	}

	/**
	 * 查询会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 */
	@Override
	public int getMemberHaveOrderForHunterCommentCount(Long hunterid, Long memberid) {
		// TODO Auto-generated method stub
		return orderDao.getMemberHaveOrderForHunterCommentCount(hunterid, memberid);
	}

	@Override
	@Transactional
	public Result findOrderBySubscriptionToBack(Order order) {
		Order o = this.getRowLock(order.getId());
		logger.info(o.getStatus()+"开始退款");
		if(o.getStatus().intValue() != 5 && o.getStatus().intValue() != 1 && o.getStatus().intValue() != 2){
			return ResultUtils.returnError("订单状态异常");
		}
		//获取订单支付 微信流水号
		List<WxPayOrder> wxList = this.wxPayOrderService.findRecordByOrderId(o.getId());
		if(wxList.size() == 0){
			logger.info("订单:"+o.getId()+"的支付信息异常");
			return ResultUtils.returnError("订单支付信息异常");
		}
		if(!(order.getHunterRefundMsg() == null || order.getHunterRefundMsg().equals(""))){
			o.setRefundType(1);
			o.setHunterRefundMsg(order.getHunterRefundMsg());
		}
		List<WxPayOrder> wxList2 = this.wxPayOrderService.findRecordByOrderIdAndStatusSuc(o.getId());//查询是否有退款成功的
		if(wxList2.size() != 0){
			logger.info("订单:"+o.getId()+"已退款");
			return ResultUtils.returnError("已退款");
		}
		return this.wxPayOrderService.sendRefund(o);
	}

	@Override
	@Transactional
	public Result findOrderBySubscriptionToPayMoney(Order order) {
		Order o = this.get(order.getId());
		if(o.getStatus().intValue() != 1){
			return ResultUtils.returnError("订单状态异常");
		}
		o.setNegotiatePrice(order.getNegotiatePrice());
		Long price = o.getSubscriptionMoney().longValue() + o.getNegotiatePrice().longValue();//定金价+协商价
		o.setActualMoney(price.longValue());//总价
		o.setTotalPrice(price.longValue());
		try {
			Order orderdb = this.saveAndModify(o);
			this.orderService.orderShare(orderdb, orderdb.getMember());//议价进行分润计算
			return ResultUtils.returnSuccess("订单议价成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("订单议价失败");
		}
	}

	@Override
	public Order getRowLock(Long id) {
		// TODO Auto-generated method stub
		return orderDao.getRowLock(id);
	}

	@Override
	@Transactional
	public Result toCreateOrder(Member m, OrderListVO list, Long aId) {
		logger.info("orderList:{}",list);
		MemberAddress memberAddress = this.memberAddressService.getmemAddById(aId);
		if(memberAddress == null){
			return ResultUtils.returnError("收货地址不存在");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		//生成订单号
		//String wxOrderNum=UtilDate.getOrderNum()+UtilDate.getThree();
		Long money = 0L;
		//params.put("wxOrderNum", wxOrderNum);
		String pro = memberAddress.getProArea().getName();
		String city = memberAddress.getCityArea().getName();
		if(pro.equals("北京")||pro.equals("上海")||pro.equals("天津")||pro.equals("重庆")){
			pro+="市";
			city+="区";
		}else{
			pro+="省";
			city+="市";
		}
		String receiveAddress = pro+city+memberAddress.getDetailAddress();
		try {
			//String wxBackUrl = (String) initParams.getProperties().get("wx_back_url");// 获取微信支付回调地址
			//params.put("wxBackUrl", wxBackUrl);
			StringBuffer orderids=new StringBuffer();//订单id，逗号拼接
			for (CreateOrderVO createOrderVO : list.getList()) {
				Long id = createOrderVO.getHunter().getId();
				Hunter hunter = this.hunterService.get(id);
				if(hunter == null){
					logger.info(id+"批发商不存在");
					return ResultUtils.returnError("批发商不存在");
				}
				if(m.getHunter() != null){
					if(m.getHunter().getId().longValue() == id.longValue()){
						logger.info(id+"不能购买自己的商品");
						return ResultUtils.returnError("不能购买自己的商品");
					}
				}
				
				
				//生成订单号
				String orderNum="KHPFDD"+UtilDate.getOrderNum()+UtilDate.getThree();
				for (ProVO proVO : createOrderVO.getPsList()) {
					List<ShopCart> shopCarts = this.shopCartService.findByMidAndPsId(m.getId(), proVO.getPsId());
					if(shopCarts.size() != 0){
						Result result = this.shopCartService.deleteByMemberAndProductSpecification(m.getId(), proVO.getPsId());
						if(result.getCode() != 1){
							logger.info(proVO.getPsId()+"购物车删除失败");
							return ResultUtils.returnError("下单失败");
						}
					}
					Hunter h = this.hunterService.get(createOrderVO.getHunter().getId());
					Order order = new Order();
					order.setFenRunStatus(0);
					order.setReceiveName(memberAddress.getUserName());
					order.setReceivePhone(memberAddress.getMobile());
					order.setReceiveAddress(receiveAddress);
					order.setMember(m);
					order.setHunter(h);
					Date predictServiceDate=null;
					//判断预计送达时间不为空
					if(("").equals(createOrderVO.getPredictServiceDate())||createOrderVO.getPredictServiceDate()==null){
						predictServiceDate=null;
					}else{
						predictServiceDate=DateUtil.getDateTimeFormatPuhui(createOrderVO.getPredictServiceDate());
						
					}
					//保存预计送达时间
					order.setPredictServiceDate(predictServiceDate);
					
					//期望送达时间
					Date hopeServiceDate1=null;
					//判断期望送达时间不为空
					if(createOrderVO.getHopeServiceDate()==null || ("").equals(createOrderVO.getHopeServiceDate())){
						hopeServiceDate1=null;
					}else{
						hopeServiceDate1=DateUtil.getDateFormat(createOrderVO.getHopeServiceDate());
						if(DateUtil.compare_date(new Date(),hopeServiceDate1)){
							logger.info("期望送达时间不能小于当前时间");
							return ResultUtils.returnError("期望送达时间不能小于当前时间");
						}
					}
					//保存期望送达时间
					order.setHopeServiceDate(hopeServiceDate1);
					
					
					//尾款状态
					order.setNegotiatePriceStatus(0);
					if(createOrderVO.getMessage()!=null&&!"".equals(createOrderVO.getMessage().replaceAll("\\s*", ""))){
						if(createOrderVO.getMessage().length()>50){
							return ResultUtils.returnError("留言不能超过50字符");
						}
					}
					order.setMessage(createOrderVO.getMessage());
					order.setOrderNo(orderNum);
					//微信支付订单表 
					/*WxPayOrder wxPayOrder = new WxPayOrder();
					wxPayOrder.setWxOrderNum(wxOrderNum);
					wxPayOrder.setStatus(0);*/
					ProductSpecification ps = this.productSpecificationService.getLockRow(proVO.getPsId());
					if(ps == null){
						logger.info(proVO.getPsId()+"商品不存在");
						return ResultUtils.returnError("商品不存在");
					}
					if(ps.getProduct().getStatus().intValue() == 0){
						logger.info(ps.getId()+"已下架");
						return ResultUtils.returnError("改商品已下架");
					}
					/*if(proVO.getPayType() == 0 && ps.getProduct().getIsSubscription().intValue() == 0){
						logger.info(ps.getId()+"不支持预付定金");
						return ResultUtils.returnError(ps.getContent()+"不支持预付定金");
					}*/
					if(proVO.getCount() > ps.getNum().intValue()){
						logger.info(ps.getId()+"库存不足");
						return ResultUtils.returnError("改商品库存不足");
					}
					if(ps.getProduct().getStartNum()>proVO.getCount()){
						logger.info(ps.getId()+"起批数量不能大于购买数量");
						return ResultUtils.returnError("起批数量不能大于购买数量");
					}
					Long price = 0L;
					order.setProductName(ps.getProduct().getName());//商品名称
					order.setProductSalePrice(ps.getSalePrice());//商品销售价
					order.setProductSpecificationName(ps.getContent());//商品分类型号
					order.setProductTypeName(ps.getProductType().getContent());//商品分类名称
					
					order.setProduct(ps.getProduct());
					order.setIndustryAssociation(h.getIndustryAssociation());
					order.setProductType(ps.getProductType());
					order.setProductSpecification(ps);
					order.setPayType(proVO.getPayType());
					order.setOrderSubNo(orderNum+"_"+ps.getId());
					order.setNum(proVO.getCount());
					order.setStatus(0);//0待付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货
					if(proVO.getPayType() == 0){//支付类型 0预付定金 1全款
						//wxPayOrder.setType(1);
						Long subscriptionMoney = (ps.getSubscriptionMoney())*proVO.getCount();//定金价
						Long totalPrice=ps.getSalePrice().longValue()*proVO.getCount();   //总价
						order.setSubscriptionMoney(subscriptionMoney);
						/*Long serviceMoney = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(subscriptionMoney*0.13, 0))))+"");
						order.setServiceMoney(serviceMoney);*/
						order.setActualMoney(subscriptionMoney.longValue());
						order.setTotalPrice(totalPrice); 						//维护总价
						order.setNegotiatePrice(totalPrice.longValue()-subscriptionMoney.longValue());  //维护尾款
						price = subscriptionMoney.longValue();
					}/*else if(proVO.getPayType() == 1){//支付类型 0预付定金 1全款
						//wxPayOrder.setType(0);
						Long salePrice = ps.getSalePrice()*proVO.getCount();//商品价
						*//*Long serviceMoney = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(salePrice*0.13, 0))))+"");//服务费
						order.setServiceMoney(serviceMoney);*//*
						order.setActualMoney(salePrice.longValue());//总价
						order.setTotalPrice(salePrice.longValue());
						price = salePrice.longValue();
					}*/else{
						logger.info(ps.getId()+"支付类型异常");
						return ResultUtils.returnError("支付类型异常");
					}
					//money += price;
					//维护分润信息
					Order saveAndModify = this.orderShare(order, m);
					/*Long totalPrice = order.getTotalPrice();
					Long directHunterFen = 0L;
					Long recommendHunterFen = 0L;
					Long industryFen = 0L;
					Long puhuiFen = 0L;
					directHunterFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(totalPrice*0.72, 0))))+"");
					puhuiFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(totalPrice*0.28, 0))))+"");
					order.setIndustryFen(industryFen);
					order.setDirectHunterFen(directHunterFen);
					order.setRecommendHunterFen(recommendHunterFen);
					order.setPuhuiFen(puhuiFen);
					Order saveAndModify = this.saveAndModify(order);已弃用*/
					logger.info(saveAndModify.getId()+"订单生成成功 ");
					orderids.append(saveAndModify.getId()+",");
					/*wxPayOrder.setMoney(price);
					wxPayOrder.setOrderInfo(saveAndModify);
					WxPayOrder saveAndModify2 = this.wxPayOrderService.saveAndModify(wxPayOrder);
					logger.info(saveAndModify2.getId()+"微信支付记录生成成功 ");*/
				}
			}
			//params.put("money", money);
			String orderidss=orderids.toString();
			orderidss = orderidss.substring(0, orderidss.lastIndexOf(','));
			params.put("orderids", orderidss);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下单异常=====错误信息："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("下单失败");
		}
		return ResultUtils.returnSuccess("下单成功", params);
	}

	@Override
	@Transactional
	public Order orderShare(Order order, Member member) throws Exception{
		Long price = order.getTotalPrice();
		//List<Order> mOrders = this.orderDao.getOrderByMId(member.getId());
		Long directHunterFen = 0L;
		Long agentFen = 0L;
		Long puhuiFen = 0L;
		Long kuhuoFen = 0L;
		Long manageFen = 0L;
		Long serviceFen = 0L;
		Long promoterFen =0L;
		directHunterFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(price*0.92, 0))))+""); //批发商结算金额
		serviceFen = price.longValue() - directHunterFen.longValue(); //服务费分润
		Hunter hunter = this.hunterService.get(order.getHunter().getId());
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = null;
		if(hunter.getPositionLevel().intValue() == 1){
			Long phCityId = hunter.getPhCityId();
			params.put("type", 1);
			params.put("id", phCityId);
			map = this.iAgentDao.getAgentByStatusAndId(params);

		}else if(hunter.getPositionLevel().intValue() == 2){
			Long phCountyId = hunter.getPhCountyId();
			params.put("type", 2);
			params.put("id", phCountyId);
			map = this.iAgentDao.getAgentByStatusAndId(params);
		}else if(hunter.getPositionLevel().intValue() == 3){
			Long phTownId = hunter.getPhTownId();
			params.put("type", 3);
			params.put("id", phTownId);
			map = this.iAgentDao.getAgentByStatusAndId(params);
		}else{
			logger.info("订单"+order.getOrderNo()+"卖家代理无代理");
		}
		if(map != null && map.size()>0) {
			order.setAgentId(Long.valueOf(map.get("id").toString()));
			agentFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(price*0.003, 0))))+""); //代理分润
		}

		puhuiFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(price*0.05, 0))))+""); //普惠基金分润
		kuhuoFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(price*0.015, 0))))+""); // 快火管理费
		/**推荐人  暂无  不分润*/
		//promoterFen = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(price*0.002, 0))))+""); //推荐人结算金额
		manageFen = serviceFen.longValue() - agentFen.longValue() - puhuiFen.longValue() - kuhuoFen.longValue() - promoterFen.longValue();

		order.setDirectHunterFen(directHunterFen);
		order.setPuhuiFen(puhuiFen);
		order.setAgentFen(agentFen);
		order.setKuhuoFen(kuhuoFen);
		order.setManageFen(manageFen);
		order.setServiceFen(serviceFen);
		order.setPromoterFen(promoterFen);
		return this.saveAndModify(order);
	}

	@Override
	@Transactional
	public void updateOrderTypeByPay(List<String> orderIds) {
		logger.info("*******updateOrderTypeByPay:修改订单状态,orderIds:{}****************",orderIds);
		List<Long> payAll = new ArrayList<Long>();//全支付的订单id
		List<Long> payPart = new ArrayList<Long>();//付定金的订单id
		try {
		List<Order> list = this.orderDao.getOrderByOrderNums(orderIds);
		for (Order order : list) {
			logger.info("********待修改订单信息***order={}",order);
			if(order.getPayType() == 0){
				if(order.getStatus().intValue() != 0 && order.getStatus().intValue() != 3){
					logger.info("订单:"+order.getId()+"状态异常,修改支付状态失败");
					continue;
				}
				if(order.getStatus().intValue() == 0){//支付定金
					Long id = order.getProductSpecification().getId();//商品规格id
					Integer num = order.getNum();//订单购买商品数量
					ProductSpecification ps = this.productSpecificationService.getLockRow(id);
					this.productSpecificationService.updateNum(ps, num);
					payPart.add(order.getId());
				}

				if(order.getStatus().intValue() == 3){//尾款支付
					if(order.getStatus().intValue() != 3){
						logger.info("订单:"+order.getId()+"状态异常,修改支付状态失败");
						continue;
					}
					payAll.add(order.getId());
					/*Long id = order.getProductSpecification().getId();//商品规格id
					Integer num = order.getNum();//订单购买商品数量
					ProductSpecification ps = this.productSpecificationService.getLockRow(id);
					this.productSpecificationService.updateNum(ps, num);*/
				}
			}
		}
		    logger.info("************************修改订单状态:payPart={},payAll={}",payPart,payAll);
		    //全款支付
			if(payAll.size()>0){
				logger.info("************************尾款支付,修改订单状态:payPart={},payAll={}",payPart,payAll);
				//this.orderDao.updateOrderTypeByPayAll(payAll, new Date());
				for (Long id : payAll) {
					this.orderFen(this.get(id));//代理分润
				}
		    }
		    //预付定金
			if(payPart.size()>0){
				logger.info("************************定金支付,修改订单状态:payPart={},payAll={}",payPart,payAll);
				this.orderDao.updateOrderTypeByPayPart(payPart, new Date());
			}
			logger.info("预付定金订单号:"+payPart.toString()+"==付款订单号:"+payAll.toString()+"==确认支付");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.info("预付定金订单号:"+payPart.toString()+"==付款订单号:"+payAll.toString()+"==支付状态修改失败");
		}
	}


	/**
	 * 15天自动确认收货
	 */
	@Override
	@Transactional
	public void autoConfirmReceive() {
		logger.info("******************自动确认收货Task执行******************");
		List<Order> orderList= orderDao.getWaitReceiveOrderList();
        int size = orderList.size();
        if (size>0){
            orderDao.confirmReceive();
        }
		orderList.forEach(e->{
			Hunter  resultHunter = e.getHunter();
			if (!Objects.isNull(resultHunter)){
				Long orderMoney = Objects.isNull(resultHunter.getOrderMoney())?0L:resultHunter.getOrderMoney();
				Long totalPrice=Objects.isNull(e.getTotalPrice())?0L:e.getTotalPrice();
				Long money = orderMoney+totalPrice;
				Map<String,Object> resultMap = hunterService.checkHunterLevel(money,resultHunter.getGoodCommentNumOrder());
				if (MapUtils.isNotEmpty(resultMap)){
					//找他人数
					Long num = resultHunter.getNum();
					resultHunter.setNum(Objects.isNull(num)?1L:num+1);
					//批发商星级和级别维护
					Integer star= resultMap.get("star")==null ?0:(Integer) resultMap.get("star");
					Integer level= resultMap.get("level")==null ?0:(Integer) resultMap.get("level");
					resultHunter.setStar(star);
					resultHunter.setLevel(level);
					resultHunter.setOrderMoney(money);
					hunterService.saveAndModify(resultHunter);
				}
			}
		});
	}

	@Override
	@Transactional
	public void orderFenRun() {
		logger.info("******************七天自动分润Task执行******************");
		String time = UtilDate.getDayByBeforeWeek();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sf.parse(time);
			List<Order> orderByStatus = this.orderDao.getOrderByStatus();//获取待分润订单
			for (Order o : orderByStatus) {
				Order order = this.getRowLock(o.getId());
				this.orderFen(order);//代理分润
				order.setFenRunStatus(1);
				this.saveAndModify(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("异常"+e.getMessage());
		}
	}

	private void orderFen(Order order) {
		//批发商结算
		this.directHunterFen(order);
		//代理结算
		this.agenterFen(order);
		//维护订单分润状态
		order.setStatus(7);
		order.setPayTime(new Date());
		order.setFenRunStatus(1);
		this.saveAndModify(order);
		logger.info("订单"+order.getOrderNo()+"分润完成");
	}

	/**
	 * 代理分润
	 * @param order
	 */
	@Transactional
	private void agenterFen(Order order) {
		try{
			Hunter hunter = this.hunterService.get(order.getHunter().getId());
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> map = null;
			if(hunter.getPositionLevel().intValue() == 1){
				Long phCityId = hunter.getPhCityId();
				params.put("type", 1);
				params.put("id", phCityId);
				params.put("userType", 3);
				map = this.iAgentDao.getAgentByStatusAndId(params);
			}else if(hunter.getPositionLevel().intValue() == 2){
				Long phCountyId = hunter.getPhCountyId();
				params.put("type", 2);
				params.put("id", phCountyId);
				params.put("userType", 4);
				map = this.iAgentDao.getAgentByStatusAndId(params);
			}else if(hunter.getPositionLevel().intValue() == 3){
				Long phTownId = hunter.getPhTownId();
				params.put("type", 3);
				params.put("id", phTownId);
				params.put("userType", 5);
				map = this.iAgentDao.getAgentByStatusAndId(params);
			}else{
				logger.info("订单"+order.getOrderNo()+"卖家代理异常");
				return;
			}
			if(map != null){
				Long agentFen = order.getAgentFen();
				Object userId = map.get("userId");
				Object userType = params.get("userType");
				String orderNo = order.getOrderNo();
				Map<String, Object> param = new HashMap<String, Object>();
				//维护代理分润记录
				param.put("agentFen", agentFen);
				param.put("userId", userId);
				param.put("userType", userType);
				param.put("orderNo", orderNo);
				this.iAgentDao.insertUserBalanceTrade(param);
				this.iAgentDao.updateUserBalance(param);
			}
			logger.info("订单"+order.getOrderNo()+"代理分润完成");
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("订单"+order.getOrderNo()+"代理分润异常"+e.getMessage());
		}
	}

	/**
	 * 推荐批发商分润
	 * @param
	 */
	@Transactional
	private void recommendHunterFen(Order order, Long id) {
		try {
			Long recommendHunterFen = order.getRecommendHunterFen();
			Hunter hunter = this.hunterService.getRowLock(id);
			Long money = hunter.getIncomeAllMoney()==null?0L:hunter.getIncomeAllMoney();
			order.setRecommendHunterAfterMoney(money.longValue()+recommendHunterFen.longValue());//维护订单推荐批发商分润后金额
			
			/*MemberMoney memberMoney = new MemberMoney();//维护推荐批发商分润记录
			memberMoney.setMoney(recommendHunterFen);
			memberMoney.setBeforeMoney(money);
			memberMoney.setAfterMoney(money.longValue()+recommendHunterFen.longValue());
			memberMoney.setHunter(hunter);
			memberMoney.setOrderNo(order.getOrderNo());
			memberMoney.setOrderSubNo(order.getOrderSubNo());
			memberMoney.setStatus(1);
			memberMoney.setType(7);*/

			hunter.setIncomeAllMoney(money.longValue()+recommendHunterFen.longValue());//维护推荐批发商金额

			//this.saveAndModify(order);
			this.hunterService.saveAndModify(hunter);
			//this.memberMoneyService.saveAndModify(memberMoney);
			logger.info("订单:"+order.getId()+"推荐批发商分润完成");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("订单:"+order.getId()+"推荐批发商分润失败");
		}
	}

	/**
	 * 直营批发商分润
	 * @param
	 */
	@Transactional
	private void directHunterFen(Order order) {
		try {
			Long directHunterFen = order.getDirectHunterFen();
			Hunter hunter = this.hunterService.getRowLock(order.getHunter().getId());
			Long money = hunter.getIncomeAllMoney()==null?0L:hunter.getIncomeAllMoney();
			Long leftDepositMoney = hunter.getLeftDepositMoney()==null?0L:hunter.getLeftDepositMoney();
			order.setDirectHunterAfterMoney(money.longValue()+directHunterFen.longValue());//维护订单直营批发商分润后金额
			//order.setFenRunStatus(1);


			MemberMoney memberMoney = new MemberMoney();//维护直营批发商分润记录
			memberMoney.setMoney(directHunterFen);
			memberMoney.setBeforeMoney(money);
			memberMoney.setAfterMoney(money.longValue()+directHunterFen.longValue());
			memberMoney.setHunter(hunter);
			memberMoney.setOrderNo(order.getOrderNo());
			memberMoney.setOrderSubNo(order.getOrderSubNo());
			memberMoney.setStatus(1);
			memberMoney.setType(5);

			hunter.setIncomeAllMoney(money.longValue()+directHunterFen.longValue());//维护直营批发商金额
			hunter.setLeftDepositMoney(leftDepositMoney.longValue()+directHunterFen.longValue());//维护直营批发商可提现金额

			//this.saveAndModify(order);
			this.hunterService.saveAndModify(hunter);
			this.memberMoneyService.saveAndModify(memberMoney);
			logger.info("订单:"+order.getId()+"直营批发商分润完成");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.info("订单:"+order.getId()+"直营批发商分润失败");
		}
	}

	/**
	 * 获取订单信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyuiResult getOrderList(Map<String, Object> map, int page, int rows)  {

		map.put("page",(page-1)*rows);
		map.put("rows",rows);
		String orderNo = (String)map.get("orderNo");
		if (StringUtils.isNotBlank(orderNo)){
			map.put("orderNo","%"+orderNo+"%");
		}
		String order =(String)map.get("order");
		if (StringUtils.isBlank(order)){
			map.put("order","desc");
		}
		List<Map<String,Object>> orderVos=iOrderDao.getOrderList(map);
		Long total = iOrderDao.getOrderListTotal(map);
		EasyuiResult result = new EasyuiResult<>();
		result.setT(orderVos);
		result.setTotal(total);
		return result;
	}

	/**
	 * 删除（批量删除）
	 * @param ids
	 * @return
	 */
	@Override
	public Result deleteBatch(String ids) {
		try {
			if ( StringUtils.isNotBlank(ids)){
				String[] idsAry = ids.split(",");
				for (String id:idsAry){
					orderDao.delete(Long.parseLong(id));
				}
				return ResultUtils.returnSuccess("删除成功");
			}
			return ResultUtils.returnError("请求参数异常.");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("删除失败");
		}

	}

	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> detail(Long id) {

		Map<String,Object> map = iOrderDao.getOrderDetailById(id);
		Integer type =(Integer)map.get("pay_type");
		String changeVal = "0".equals(String.valueOf(type))?"预付定金":"全款";
		map.put("pay_type",changeVal);
		return map;
	}

	@Override
	public EasyuiResult getAccountList(Map<String, Object> map, int page, int row) {
        int rows = row/5;
		map.put("page",(page-1)*rows);
		map.put("rows",rows);
		String orderNo = (String)map.get("orderNo");
		if (StringUtils.isNotBlank(orderNo)){
			map.put("orderNo","%"+orderNo+"%");
		}
		String order =(String)map.get("order");
		if (StringUtils.isBlank(order)){
			map.put("order","desc");
		}
		List<Map<String,Object>> orderVOS= iOrderDao.getAccountList(map);
		List<Map<String,Object>> buildOrder= Lists.newArrayList();
		orderVOS.forEach(e->{
		    //拆訂單 一拆五
            for (int i=1;i<6;i++){
            	Map<String,Object> result = getFenRunInfo(e,String.valueOf(i));
            	buildOrder.add(result);
			}
        });
		Long total = iOrderDao.getAccountListTotal(map);
		EasyuiResult result = new EasyuiResult<>();
		result.setT(buildOrder);
		result.setTotal(total*5);
		return result;
	}

	private static Map<String,Object> getFenRunInfo(Map<String,Object> map,String type){
		Map<String,Object> result = Maps.newHashMap();
		result.putAll(map);
		switch (type){
			//直營獵頭分潤
			case "1":
				result.put("name",map.get("phone"));
				result.put("role","直营批发商");
				result.put("money",map.get("money"));
				result.put("after_money",map.get("after_money"));
				break;
			//推薦獵頭分潤
			case "2":
				result.put("name",map.get("rphone"));
				result.put("role","推荐批发商");
				result.put("money","0.00");
				result.put("after_money","0.00");
				break;
			//行業協會分潤
			case "3":
				result.put("name",map.get("iphone"));
				result.put("role","行业公会");
				result.put("money","0.00");
				result.put("after_money","0.00");
				break;
			//普惠平台分潤
			case "4":
				result.put("name","普惠平台");
				result.put("role","普惠平台");
				result.put("money",map.get("puhuifen"));
				result.put("after_money",map.get("puhuifen"));
				break;
			//獵頭平台分潤
			case "5":
				result.put("name","批发商平台");
				result.put("role","批发商平台");
				result.put("money","0.00");
				result.put("after_money","0.00");
				break;
			default:
					break;

		}
		return result;
	}
	@Override
	@Transactional
	public Result memberToPayBack(Order order) {
		Order o = this.get(order.getId());
		logger.info(o.getStatus()+"");
		if(o.getFenRunStatus().intValue() == 1){
			return ResultUtils.returnError("订单无法退款");
		}else if(o.getStatus().intValue() == 0){
			return ResultUtils.returnError("订单状态异常");
		}else if(o.getStatus().intValue() == 5){
			return ResultUtils.returnError("订单已申请退款");
		}else if(o.getStatus().intValue() == 6){
			return ResultUtils.returnError("订单已退款");
		}
		//获取订单支付 微信流水号
		List<WxPayOrder> wxList = this.wxPayOrderService.findRecordByOrderId(o.getId());
		if(wxList.size() != 1 && wxList.size() != 2){
			logger.info("订单:"+o.getId()+"的支付信息异常");
			return ResultUtils.returnError("订单信息异常");
		}
		
		if(order.getRefundType().intValue() != 0){
			logger.info("订单:"+o.getId()+"退款信息异常"+order.getRefundType());
			return ResultUtils.returnError("订单退款信息异常");
		}
		o.setStatus(5);
		o.setApplyRefundTime(new Date());
		o.setRefundMsg(order.getRefundMsg());
		o.setRefundType(order.getRefundType());
		try {
			this.saveAndModify(o);
			return ResultUtils.returnSuccess("申请退款成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("退款失败");
		}
	}

	/**
	 * 确认收货
	 * @return
	 */
	@Override
	@Transactional
	public Result confirmReceive(Map<String,Object> map) {
		try {
			Long orderId =Long.valueOf((String) map.get("oid")) ;
			Order order =this.get(orderId);
			if (Objects.isNull(order)){
				return ResultUtils.returnError("订单信息不存在");
			}
			Hunter resultHunter=order.getHunter();
			if (resultHunter==null){
                return ResultUtils.returnError("订单对应批发商信息不存在");
            }
			Long orderMoney = Objects.isNull(resultHunter.getOrderMoney())?0L:resultHunter.getOrderMoney();
			Long totalPrice=Objects.isNull(order.getTotalPrice())?0L:order.getTotalPrice();
			Long money = orderMoney+totalPrice;
			//设置已收货状态
			order.setStatus(7);
			order.setReceiveTime(new Date());
			this.saveAndModify(order);
			Map<String,Object> resultMap = hunterService.checkHunterLevel(money,resultHunter.getGoodCommentNumOrder());
			if (MapUtils.isEmpty(resultMap)){
                return ResultUtils.returnError("批发商信息获取失败");
            }
			//找他人数
			Long num = resultHunter.getNum();
			resultHunter.setNum(Objects.isNull(num)?1:num+1);

            //批发商星级和级别维护
			Integer star= resultMap.get("star")==null ?0:(Integer) resultMap.get("star");
			Integer level= resultMap.get("level")==null ?0:(Integer) resultMap.get("level");
			resultHunter.setStar(star);
			resultHunter.setLevel(level);
			resultHunter.setOrderMoney(money);
			hunterService.saveAndModify(resultHunter);
			return ResultUtils.returnSuccess("确认收货成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("接口调用失败");
		}

	}

	@Override
	@Transactional
	public Result updateOrderTypeByBack(Order o) {
		Order order = this.get(o.getId());
		int count = this.orderCommentService.getDirectOrderCommentCount(order.getId(), order.getMember().getId());
		try {
			if (Objects.equals(order.getNegotiatePriceStatus(),3)){
				this.hunterService.updateOrderMoneyByOrderId(order);
				if(count > 0){
					this.hunterService.updateCommentNumOrder(order);
					this.productService.updateCommentNumOrder(order);
				}
			}
			this.productSpecificationService.updateNumByBack(order);
			order.setStatus(6);
			order.setRefundTime(new Date());
			this.saveAndModify(order);
			//update 20170405  xuejz 此處退貨狀態修改為退款狀態
			logger.info("订单:"+order.getId()+"退款状态修改成功");
			return ResultUtils.returnSuccess("退款成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("订单:"+order.getId()+"退款状态修改失败");
			return ResultUtils.returnError("退款失败");
		}
		
	}

	@Override
	@Transactional
	public void orderPuhuiFenRun() {
		logger.info("******************普惠自动分润Task执行******haha********");
		
		List<Order> list = this.orderDao.getOrderByPuHuiFenRun();
		String url = "http://118.89.24.168:10088/api/score/save";
		for (Order order : list) {
			StringBuffer puHuiFenUrl = new StringBuffer();
			Integer status = order.getPuhuiRunStatus()==null?0:order.getPuhuiRunStatus();
			Long orderMoney = order.getTotalPrice()*100/2;
			String orderNo = order.getOrderNo();
			String token = order.getMember().getToken();//用户手机号
			//增加positionLevel参数
			puHuiFenUrl.append(url+"?orderMoney="+orderMoney+"&orderNo="+orderNo+"&token="+token+"&sourceType=1");
			try {
				logger.info("========================"+puHuiFenUrl);
				String sendGet = HttpClientUtils.geHttpsAll(puHuiFenUrl.toString(), null, null,"post");
				logger.info("");
				JSONObject content = JSON.parseObject(sendGet);
				logger.info("添加积分信息content:{}",content.toJSONString());
				if ("200".equals(content.getString("code"))){
					order.setPuhuiRunStatus(100);
					logger.info(order.getId()+":订单普惠分润接口调用成功");
				} else{
					order.setPuhuiRunStatus(status.intValue() + 1);
					logger.info(order.getId()+":订单普惠分润接口返回失败");
				}
				this.saveAndModify(order);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				order.setPuhuiRunStatus(status.intValue() + 1);
				this.saveAndModify(order);
				logger.info(order.getId()+":订单普惠分润接口调用异常");
			}
		}
	}

	/**
	 * 确认发货
	 * @param map
	 * @return
	 */
	@Override
	@Transactional
	public Result confirmSend(Map<String, Object> map) {
		try {
			Long orderId =Long.parseLong((String) map.get("oid")) ;
			Order order = orderDao.findOne(orderId);
			Long hunterId=order.getHunter().getId();
			if (Objects.isNull(order)){
				return ResultUtils.returnError("订单信息不存在.");
			}
			String orderNo=order.getOrderNo();//订单号
			if (Objects.isNull(orderNo)) {
				return ResultUtils.returnError("订单号异常为空");
			}
			if (Objects.isNull(hunterId)){
				return ResultUtils.returnError("订单信息有误.");
			}
			//根据订单号设置待收货状态
			int statusSum = orderDao.updateStatusForOrder(orderNo,hunterId,new Date());
			if (statusSum < 0) {
				return ResultUtils.returnError("确认发货失败");
			}
			
			//设置待收货状态
			/*order.setStatus(3);
			order.setSendTime(new Date());
			orderDao.save(order);*/
			return ResultUtils.returnSuccess("确认发货成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("接口调用失败");
		}
		
	}

	/**
	 * 设置退货留言信息
	 * @param map
	 * @return
	 */
	@Override
	@Transactional
	public Result setRefundMsg(Map<String, Object> map) {
		if (map.isEmpty()){
			return ResultUtils.returnError("参数缺失");
		}
		try {
			Long oid=Long.parseLong((String)map.get("oid"));
			Order order=orderDao.findOne(oid);
			if (order==null){
                return ResultUtils.returnError("订单信息不存在.");
            }
			String type=(String) map.get("type");
			String msg =(String)map.get("msg");
			order.setRefundType(StringUtils.isBlank(type)?0:Integer.parseInt(type));
			if("1".equals(type)){
				order.setHunterRefundMsg(msg);
			}else{
				order.setRefundMsg(msg);
			}
			order.setApplyRefundTime(new Date());
			orderDao.save(order);
			return ResultUtils.returnSuccess("退货留言成功.");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("接口调用失败.");
		}
	}

	/*@Override
	public void updatePsCountByPay(List<Long> orderIds) {
		List<Order> list = this.orderDao.getOrderByOrderNums(orderIds);
		for (Order order : list) {
			if(order.getPayType() == 0){
				if(order.getStatus().intValue() != 0 && order.getStatus().intValue() != 3){
					logger.info("订单:"+order.getId()+"状态异常,修改支付状态失败");
					continue;
				}
				if(order.getStatus().intValue() == 0){//支付定金
					ProductSpecification productSpecification = order.getProductSpecification();
					productSpecification.setNum(productSpecification.getNum()==null?0L:productSpecification.getNum() + order.getNum());
					this.productSpecificationService.saveAndModify(productSpecification);
				}
			}
			*//*if(order.getPayType() == 1){//全款支付
				if(order.getStatus().intValue() != 0){
					logger.info("订单:"+order.getId()+"状态异常,修改支付状态失败");
					continue;
				}
				ProductSpecification productSpecification = order.getProductSpecification();
				productSpecification.setNum(productSpecification.getNum()==null?0L:productSpecification.getNum() + order.getNum());
				this.productSpecificationService.saveAndModify(productSpecification);
			}*//*
		}
	}*/

	/**
	 * 用户输入尾款价格
	 */
	@Override
	@Transactional
	public Result addNegotiatePriceForOrder(Order order,String ids) {
		Result result=new Result();
		Order o = this.get(order.getId());
		if (null == o) {
			return ResultUtils.returnError("订单异常");
		}
		Double memberNegotiatePrice=Double.valueOf(order.getNegotiatePrice().toString());//用户输入尾款总价//单位分（已乘100）
		String orderNo=order.getOrderNo();//订单号
		//Long price = o.getSubscriptionMoney().longValue() +negotiatePrice;//定金价+尾款价
		if (Objects.isNull(orderNo)) {
			return ResultUtils.returnError("订单号不能为空");
		}
		if (Objects.isNull(ids)) {
			return ResultUtils.returnError("订单id集合不能为空");
		}
		String[] idList=ids.split(",");
		if(idList.length<1){
			return ResultUtils.returnError("订单id集合异常");
		}
		List<Long> orderIds=new ArrayList<Long>();
		for (String stringId : idList) {//根据订单id循环处理查出订单定金加总和，售价总和
			orderIds.add(Long.valueOf(stringId));
		}
		List<Order> list = this.orderDao.getOrderByOrderNum(orderIds);
		
		try {
			//维护尾款价格
			if(list.size()>1){//有多条
					//订单的定金价（总和）
					Double subscriptionMoneyAll=0.0;
					//订单的销售价（总和）
					Double productSalePriceAll=0.0;
					for (Order orderList : list) {
						subscriptionMoneyAll+=Double.valueOf(orderList.getSubscriptionMoney().toString());//子订单的定金价
						Double productSalePrice=Double.valueOf(orderList.getProductSalePrice().toString());//子订单的商品销售价格（单件）
						Integer num=orderList.getNum();//子订单的商品购买数量
						//子订单：期望尾款=该子订单的销售价格*数量-该子订单的定金价
						productSalePriceAll+=productSalePrice*num;
					}
					//订单的期望尾款总价：期望尾款=订单的销售价（总和）-订单的定金价（总和）
					Double negotiatePriceAll=productSalePriceAll-subscriptionMoneyAll;
					Long lastNegotiate=0L;
					for(int i=0;i<list.size()-1;i++){
						Order childOrder=list.get(i);
		
						if(childOrder.getStatus().intValue() != 3){//0代付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货
							return ResultUtils.returnError("订单状态异常");
						}
						if(childOrder.getNegotiatePriceStatus().intValue() == 1 || childOrder.getNegotiatePriceStatus().intValue() == 3){//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过
							return ResultUtils.returnError("订单状态异常");
						}
						//子订单的定金价
						Double childSubscriptionMoney=Double.valueOf(childOrder.getSubscriptionMoney().toString());//定金价(销售价格*0.3)//单位分
						//子订单的商品销售价格（单件）
						Double childProductSalePrice=Double.valueOf(childOrder.getProductSalePrice().toString());//商品销售价格//单位分
						Integer childNum=childOrder.getNum();//商品购买数量
						//子订单：期望尾款=该子订单的销售价格*数量-该子订单的定金价
						Double childNegotiatePrice=childProductSalePrice*childNum-childSubscriptionMoney;
						//子订单尾款价70/420*300=50  期望尾款/尾款总价*用户输入尾款总价
						Long negotiate = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(childNegotiatePrice/negotiatePriceAll*memberNegotiatePrice, 0))))+"");
						lastNegotiate+=negotiate;
						childOrder.setNegotiatePrice(negotiate);//子订单尾款价格
						childOrder.setNegotiatePriceStatus(1);//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过 此时维护negotiatePrice字段  再次单机付尾款则直接让其支付
						this.saveAndModify(childOrder);//保存子订单尾款价格及状态
						result.setCode(1);
						result.setMsg("保存尾款成功");
					}
					//维护尾款价格最后一条（用户输入的减去之前平分的）
					Order lastOrder=list.get(list.size()-1);
					Long aa=order.getNegotiatePrice()-lastNegotiate;
					lastOrder.setNegotiatePrice(aa);//子订单尾款价格
					lastOrder.setNegotiatePriceStatus(1);//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过 此时维护negotiatePrice字段  再次单机付尾款则直接让其支付
					this.saveAndModify(lastOrder);//保存子订单尾款价格及状态
					result.setCode(1);
					result.setMsg("保存尾款成功");
					
			}else{//只有一条
					o.setNegotiatePrice(order.getNegotiatePrice());
					o.setNegotiatePriceStatus(1);
					this.saveAndModify(o);//保存子订单尾款价格及状态
					result.setCode(1);
					result.setMsg("保存尾款成功");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("保存用户尾款价格失败");
		}
		
		return result;
	}
	/**
	 * 商家对尾款价格进行操作
	 */
	@Override
	@Transactional
	public Result chooseNegotiatePriceForOrder(Order order, String type) {
		Result result=new Result();
		Order o = this.get(order.getId());
		
		if(o.getStatus().intValue() != 3){//0代付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货
			return ResultUtils.returnError("订单状态异常");
		}
		if(o.getNegotiatePriceStatus().intValue() != 1){//0发货之后未输入  1 已输入待确认   2被驳回需重输  3已通过
			return ResultUtils.returnError("订单状态异常");
		}
		String orderNo=order.getOrderNo();//订单号
		if (Objects.isNull(orderNo)) {
			return ResultUtils.returnError("订单号不能为空");
		}
		if (Objects.isNull(type)) {
			return ResultUtils.returnError("类型不能为空");
		}
		
		if("1".equals(type)){//type  1同意   2 驳回

			List<Long> idList= orderDao.getOrderIdsByOrderNo(orderNo);
			if(idList.size()==0){
				return ResultUtils.returnError("订单异常,id为空");
			}
			for (Long long1 : idList) {
				Order orders = this.get(long1);
				Long totalPrice=orders.getNegotiatePrice()+orders.getSubscriptionMoney();//商品的订单价格=定金价+尾款价格
				orders.setTotalPrice(totalPrice);
				orders.setNegotiatePriceStatus(3);
				try {

					Order saveAndModify = this.saveAndModify(orders);
					result.setCode(1);
					result.setMsg("操作成功");
					this.orderShare(saveAndModify, saveAndModify.getMember());//议价进行分润计算
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ResultUtils.returnError("操作失败");
				}
			}

		}else if("2".equals(type)){
			try {
				//根据订单号维护订单状态
				int disagreeNegotiatesum = orderDao.disagreeNegotiatePriceForOrder(orderNo);
				if (disagreeNegotiatesum < 0) {
					return ResultUtils.returnError("操作失败");
				}
				
				//this.orderService.orderShare(orderdb, orderdb.getMember());//议价进行分润计算
				result.setCode(1);
				result.setMsg("操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return ResultUtils.returnError("操作失败");
			}
		}else{
			
			return ResultUtils.returnError("类型有误");
		}
		
		return result;
	}

	/**
	 * 维护批发商信息
	 * @param
	 * @return
	 */
	@Override
	@Transactional
	public Result updateHunterInfo(List<String> orderNos) {
		try {
			logger.info("****************待更新订单信息****************order:{}",orderNos);
			List<Order> list = this.orderDao.getOrderByOrderNums(orderNos);
			Map<Long,String> hunterMap = Maps.newHashMap();
			for (Order order : list) {
				if (Objects.isNull(order)) {
					return ResultUtils.returnError("订单信息不存在");
				}
				Hunter resultHunter = order.getHunter();
				if (resultHunter == null) {
					return ResultUtils.returnError("订单对应批发商信息不存在");
				}
				Long hid = resultHunter.getId();
				Long orderMoney = Objects.isNull(resultHunter.getOrderMoney()) ? 0L : resultHunter.getOrderMoney();
				Long totalPrice = Objects.isNull(order.getTotalPrice()) ? 0L : order.getTotalPrice();
				Long money = orderMoney + totalPrice;
				Map<String, Object> resultMap = hunterService.checkHunterLevel(money, resultHunter.getGoodCommentNumOrder());
				if (MapUtils.isEmpty(resultMap)) {
					return ResultUtils.returnError("批发商信息获取失败");
				}
				//批发商星级和级别维护
				Integer star = resultMap.get("star") == null ? 0 : (Integer) resultMap.get("star");
				Integer level = resultMap.get("level") == null ? 0 : (Integer) resultMap.get("level");
				if (hunterMap.keySet().contains(hid)){
					String value = hunterMap.get(hid);
					String[] vals = value.split(",");
					if (!Objects.isNull(vals)){
						Long hunterNum= Long.parseLong(vals[0])+1;
						Integer hunterStar= Integer.parseInt(vals[1])+star;
						Integer hunterLevl= Integer.parseInt(vals[2])+level;
						Long hunterMoney= Long.parseLong(vals[3])+money;
						String hunterInfo = new StringBuilder(String.valueOf(hunterNum)).append(",").
								append(String.valueOf(hunterStar)).append(",").append(String.valueOf(hunterLevl))
								.append(",").append(String.valueOf(hunterMoney)).toString();
						hunterMap.put(hid,hunterInfo);
					}
				}else{
					String hunterInfo = new StringBuilder("1").append(",").
							append(star).append(",").append(level).append(",").append(money).toString();
					hunterMap.put(hid,hunterInfo);
				}
			}
			logger.info("*******待更新批发商信息*****hunterMap:{}*******",hunterMap);
			for (Map.Entry<Long, String> entry : hunterMap.entrySet()) {
				Long key = entry.getKey();
				String vals = entry.getValue();
				Hunter hunter = hunterService.get(key);
				Long num = Objects.isNull(hunter.getNum()) ? 0L : hunter.getNum();
				Long  hunterNum = 0L;
				Integer  hunterStar = 0;
				Integer  hunterLevl = 0;
				Long  hunterMoney = 0L;
				String[] values = vals.split(",");
				if (!Objects.isNull(values)){
					 hunterNum= Long.parseLong(values[0]);
					 hunterStar= Integer.parseInt(values[1]);
					 hunterLevl= Integer.parseInt(values[2]);
					 hunterMoney= Long.parseLong(values[3]);
				}
				hunter.setNum(num+hunterNum);
				hunter.setStar(hunterStar);
				hunter.setLevel(hunterLevl);
				hunter.setOrderMoney(hunterMoney);
				hunterService.saveAndModify(hunter);
			}
			return ResultUtils.returnSuccess("维护成功.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("接口调用失败");
		}
	}

	/**
	 * 创建订单
	 * @param member
	 * @return
	 */
	@Override
	@Transactional
	public Result createOrder(Member member, List<OrderDTO> orderDTOS) {
		logger.info("orderList:{}",JSON.toJSONString(orderDTOS));
		if (Objects.isNull(member) || Objects.isNull(orderDTOS)){
			return ResultUtils.returnError("参数异常");
		}
		if (orderDTOS.isEmpty()){
			return ResultUtils.returnError("参数异常");
		}
		MemberAddress memberAddress =memberAddressService.getMemberAddressByMemberAndIsDefaultAndIsDelete(member);
		if(memberAddress == null){
			return ResultUtils.returnError("默认收货地址不存在,请设置.");
		}
		Map<String, Object> params = new HashMap<>();
		String pro = memberAddress.getProArea().getName();
		String city = memberAddress.getCityArea().getName();
		if(pro.equals("北京")||pro.equals("上海")||pro.equals("天津")||pro.equals("重庆")){
			pro+="市";
			city+="区";
		}else{
			pro+="省";
			city+="市";
		}
		String receiveAddress = pro+city+memberAddress.getDetailAddress();
		try {
			StringBuffer orderids=new StringBuffer();//订单id，逗号拼接
			for (OrderDTO orderDTO : orderDTOS) {
				Hunter hunter = this.hunterService.get(orderDTO.getHid());
				if(hunter == null){
					logger.info("id={},批发商不存在",orderDTO.getHid());
					return ResultUtils.returnError("批发商不存在");
				}
				if(member.getHunter() != null){
					if(Objects.equals(member.getHunter().getId(),orderDTO.getHid())){
						return ResultUtils.returnError("不能购买自己的商品");
					}
				}
				//生成订单号
				String orderNum="KHPFDD"+UtilDate.getOrderNum()+UtilDate.getThree();
				//List<ShopCart> shopCarts = this.shopCartService.findByMidAndPsId(member.getId(), orderDTO.getSpid());
				//if(shopCarts.size() != 0){
				// Result result = this.shopCartService.deleteByMemberAndProductSpecification(member.getId(), orderDTO.getSpid());
				//  if(result.getCode() != 1){
				///      logger.info("spid={}购物车删除失败",orderDTO.getSpid());
				//      return ResultUtils.returnError("下单失败");
				//   }
				Order order = new Order();
				order.setFenRunStatus(0);
				order.setReceiveName(memberAddress.getUserName());
				order.setReceivePhone(memberAddress.getMobile());
				order.setReceiveAddress(receiveAddress);
				order.setMember(member);
				order.setHunter(hunter);
				Date predictServiceDate=null;
				//判断预计送达时间不为空
					/*if(("").equals(createOrderVO.getPredictServiceDate())||createOrderVO.getPredictServiceDate()==null){
						predictServiceDate=null;
					}else{
						predictServiceDate=DateUtil.getDateTimeFormatPuhui(createOrderVO.getPredictServiceDate());

					}*/
				//保存预计送达时间
				order.setPredictServiceDate(predictServiceDate);

				//期望送达时间
				Date hopeServiceDate1;
				//判断期望送达时间不为空
				if(StringUtils.isBlank(orderDTO.getHopeServiceDate())){
					if (StringUtils.isBlank(hunter.getSendStartTime()) ||
							StringUtils.isBlank(hunter.getSendEndTime())){
						return ResultUtils.returnError("批发商未设置配送时间");
					}
					Date currentDate = new Date();
					String dateStr = DateUtil.getDateFormat(currentDate,"YYYY-MM-dd");
					Long time = Objects.isNull(hunter.getBeiHuoTime())?0L:hunter.getBeiHuoTime();
					Date newHopeServiceDate = DateUtils.addHours(currentDate,time.intValue());
					Date startSendTime = DateUtil.getDateTimeFormat(dateStr+" "+hunter.getSendStartTime());
					Date endSendTime = DateUtil.getDateTimeFormat(dateStr+" "+hunter.getSendEndTime());
					if (newHopeServiceDate.after(startSendTime) && newHopeServiceDate.before(endSendTime)){
						hopeServiceDate1=newHopeServiceDate;
					}else {
						hopeServiceDate1 = startSendTime;
					}
				}else{
					hopeServiceDate1=DateUtil.getDateFormat(orderDTO.getHopeServiceDate());
					if(DateUtil.compare_date(new Date(),hopeServiceDate1)){
						logger.info("期望送达时间不能小于当前时间");
						return ResultUtils.returnError("期望送达时间不能小于当前时间");
					}
				}
				//保存期望送达时间
				order.setHopeServiceDate(hopeServiceDate1);
				//尾款状态
				order.setNegotiatePriceStatus(0);
					/*if(createOrderVO.getMessage()!=null&&!"".equals(createOrderVO.getMessage().replaceAll("\\s*", ""))){
						if(createOrderVO.getMessage().length()>50){
							return ResultUtils.returnError("留言不能超过50字符");
						}
					}
					order.setMessage(createOrderVO.getMessage());*/
				order.setOrderNo(orderNum);
				ProductSpecification ps = this.productSpecificationService.getLockRow(orderDTO.getSpid());
				if(ps == null){
					return ResultUtils.returnError("商品不存在");
				}
				if(ps.getProduct().getStatus().intValue() == 0){
					logger.info(ps.getId()+"已下架");
					return ResultUtils.returnError("该商品已下架");
				}
					/*if(proVO.getPayType() == 0 && ps.getProduct().getIsSubscription().intValue() == 0){
						logger.info(ps.getId()+"不支持预付定金");
						return ResultUtils.returnError(ps.getContent()+"不支持预付定金");
					}*/
				/*if(orderDTO.getNum().longValue() > ps.getNum().intValue()){
					logger.info(ps.getId()+"库存不足");
					return ResultUtils.returnError("库存不足");
				}*/
/*
				if(ps.getProduct().getStartNum().longValue() > orderDTO.getNum().longValue()){
					logger.info(ps.getId()+"起批数量不能大于购买数量");
					return ResultUtils.returnError("起批数量不能大于购买数量");
				}
*/
				order.setProductName(ps.getProduct().getName());//商品名称
				order.setProductSalePrice(ps.getSalePrice());//商品销售价
				order.setProductSpecificationName(ps.getContent());//商品分类型号
				order.setProductTypeName(ps.getProductType().getContent());//商品分类名称
				order.setProduct(ps.getProduct());
				order.setIndustryAssociation(hunter.getIndustryAssociation());
				order.setProductType(ps.getProductType());
				order.setProductSpecification(ps);
				order.setPayType(0);//预付定金
				order.setOrderSubNo(orderNum+"_"+ps.getId());
				order.setNum(orderDTO.getNum().intValue());
				order.setStatus(0);//0待付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货
				//if(proVO.getPayType() == 0){//支付类型 0预付定金 1全款
				Long subscriptionMoney = (ps.getSubscriptionMoney())* orderDTO.getNum();//定金价
				Long totalPrice=ps.getSalePrice().longValue() * orderDTO.getNum();   //总价
				order.setSubscriptionMoney(subscriptionMoney);
				order.setActualMoney(subscriptionMoney.longValue());
				order.setTotalPrice(totalPrice); 						//维护总价
				order.setNegotiatePrice(totalPrice.longValue()-subscriptionMoney.longValue());  //维护尾款
				Long salePrice = ps.getSalePrice()* orderDTO.getNum();//商品价
				//*Long serviceMoney = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(salePrice*0.13, 0))))+"");//服务费
				//order.setServiceMoney(serviceMoney);//*
				order.setActualMoney(salePrice.longValue());//总价
				//维护分润信息
				Order saveAndModify = this.orderShare(order, member);
				logger.info(saveAndModify.getId()+"订单生成成功 ");
				orderids.append(saveAndModify.getId()+",");
				//	}
			}
			String orderidss=orderids.toString();
			orderidss = orderidss.substring(0, orderidss.lastIndexOf(','));
			params.put("orderids", orderidss);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下单异常=====错误信息："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("下单失败");
		}
		return ResultUtils.returnSuccess("下单成功", params);
	}

	/**
	 * 修改订单
	 * @param time
	 * @param orderNo
	 * @return
	 */
	@Override
	@Transactional
	public Result updateOrder(String time, String  orderNo, Member member) {
		try {
			List<Order> orders = orderDao.getOrdersByOrderNoAndMember(orderNo,member);
			if (Objects.isNull(orders)){
				return ResultUtils.returnError("订单不存在");
			}
			orders.forEach(e ->{
				e.setHopeServiceDate(DateUtil.getDateFormat(time));
				orderDao.save(e);
			});
			return ResultUtils.returnSuccess("成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultUtils.returnSuccess("修改失败");
		}
	}

	/**
	 * 子订单号删除
	 * @param id  订单id
	 * @param member
	 * @return
	 */
	@Override
	@Transactional
	public Result deleteBySubOrderNo(Long id, Member member) {
		try {
			Order order = orderDao.findOne(id);
			if (Objects.isNull(order)){
				return ResultUtils.returnError("订单不存在");
			}
			orderDao.delete(order);
			return ResultUtils.returnSuccess("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultUtils.returnSuccess("删除失败");
		}
	}

	/**
	 * 修改数量
	 * @param member
	 * @return
	 */
	@Override
	@Transactional
	public Result updateOrderNum(Long id, Long num, Member member) {
		try {
			if (Objects.isNull(id) ||
					Objects.isNull(num) ||
					num.longValue()<=0){
				return ResultUtils.returnError("参数错误");
			}
			Order order = orderDao.findOne(id);
			if (Objects.isNull(order)){
				return ResultUtils.returnError("订单不存在");
			}
			order.setNum(num.intValue());
			orderDao.save(order);
			return ResultUtils.returnSuccess("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultUtils.returnSuccess("修改失败");
		}
	}

	/**
	 * 返回修改
	 * @param orderDTOS
	 * @param member
	 * 买家在卖家接单前取消订单  退款
	 * @return
	 */
	@Override
	@Transactional
	public Result recurOrder(String type,List<OrderDTO> orderDTOS, Member member) {
		logger.info("orderList:{}",orderDTOS);
		if (Objects.isNull(member) || Objects.isNull(orderDTOS) ||
				StringUtils.isBlank(type)){
			return ResultUtils.returnError("参数异常");
		}
		if (orderDTOS.isEmpty()){
			return ResultUtils.returnError("参数异常");
		}
		Long oid = orderDTOS.get(0).getId();
		if (Objects.isNull(oid) && "1".equals(type)){
			return ResultUtils.returnError("参数缺少");
		}
		MemberAddress memberAddress =memberAddressService.getDefaultMemberAddressByMember(member);
		if(memberAddress == null){
			return ResultUtils.returnError("收货地址不存在");
		}
		Map<String, Object> params = new HashMap<>();
		String pro = memberAddress.getProArea().getName();
		String city = memberAddress.getCityArea().getName();
		if(pro.equals("北京")||pro.equals("上海")||pro.equals("天津")||pro.equals("重庆")){
			pro+="市";
			city+="区";
		}else{
			pro+="省";
			city+="市";
		}
		String receiveAddress = pro+city+memberAddress.getDetailAddress();
		try {
			Order oldOrder;
			Date createTime;
			Date hopeServiceDate;
			Hunter hunter;
			String orderNum;
			if ("1".equals(type)){
				oldOrder = orderDao.findOne(oid);
				if (Objects.isNull(oldOrder)){
					return ResultUtils.returnError("订单不存在");
				}
				hunter = oldOrder.getHunter();
				createTime= oldOrder.getCreatedTime();
				hopeServiceDate = oldOrder.getHopeServiceDate();
				//生成订单号
				orderNum= oldOrder.getOrderNo();
				int deleteResult = orderDao.deleteByOrderNoAndMember(orderNum,member);
				logger.info("根据订单号:{},删除的总记录数.",orderNum,deleteResult);
			} else {
				createTime = new Date();
				Long hid = orderDTOS.get(0).getHid();
				hunter = this.hunterService.get(hid);
				orderNum = "KHPFDD"+UtilDate.getOrderNum()+UtilDate.getThree();
				if(hunter == null){
					return ResultUtils.returnError("批发商不存在");
				}
				if(member.getHunter() != null){
					if(Objects.equals(member.getHunter().getId(),hid)){
						return ResultUtils.returnError("不能购买自己的商品");
					}
				}
				if (StringUtils.isBlank(hunter.getSendStartTime()) ||
						StringUtils.isBlank(hunter.getSendEndTime())){
					return ResultUtils.returnError("批发商未设置配送时间");
				}
				Date currentDate = new Date();
				String dateStr = DateUtil.getDateFormat(currentDate,"YYYY-MM-dd");
				Long time = Objects.isNull(hunter.getBeiHuoTime())?0L:hunter.getBeiHuoTime();
				Date newHopeServiceDate = DateUtils.addHours(currentDate,time.intValue());
				Date startSendTime = DateUtil.getDateTimeFormat(dateStr+" "+hunter.getSendStartTime());
				Date endSendTime = DateUtil.getDateTimeFormat(dateStr+" "+hunter.getSendEndTime());
				if (newHopeServiceDate.after(startSendTime) && newHopeServiceDate.before(endSendTime)){
					hopeServiceDate=newHopeServiceDate;
				}else {
					hopeServiceDate = startSendTime;
				}
			}
			StringBuffer orderids=new StringBuffer();//订单id，逗号拼接
			for (OrderDTO orderDTO : orderDTOS) {

				Order order = new Order();
				order.setCreatedTime(createTime);
				order.setFenRunStatus(0);
				order.setReceiveName(memberAddress.getUserName());
				order.setReceivePhone(memberAddress.getMobile());
				order.setReceiveAddress(receiveAddress);
				order.setMember(member);
				order.setHunter(hunter);
				Date predictServiceDate=null;
				//判断预计送达时间不为空
					/*if(("").equals(createOrderVO.getPredictServiceDate())||createOrderVO.getPredictServiceDate()==null){
						predictServiceDate=null;
					}else{
						predictServiceDate=DateUtil.getDateTimeFormatPuhui(createOrderVO.getPredictServiceDate());

					}*/
				//保存预计送达时间
				order.setPredictServiceDate(predictServiceDate);
				//保存期望送达时间
				order.setHopeServiceDate(hopeServiceDate);
				//尾款状态
				order.setNegotiatePriceStatus(0);
					/*if(createOrderVO.getMessage()!=null&&!"".equals(createOrderVO.getMessage().replaceAll("\\s*", ""))){
						if(createOrderVO.getMessage().length()>50){
							return ResultUtils.returnError("留言不能超过50字符");
						}
					}
					order.setMessage(createOrderVO.getMessage());*/
				order.setOrderNo(orderNum);
				ProductSpecification ps = this.productSpecificationService.getLockRow(orderDTO.getSpid());
				if(ps == null){
					return ResultUtils.returnError("商品不存在");
				}
				if(ps.getProduct().getStatus().intValue() == 0){
					logger.info(ps.getId()+"已下架");
					return ResultUtils.returnError("该商品已下架");
				}
					/*if(proVO.getPayType() == 0 && ps.getProduct().getIsSubscription().intValue() == 0){
						logger.info(ps.getId()+"不支持预付定金");
						return ResultUtils.returnError(ps.getContent()+"不支持预付定金");
					}*/
				/*if(orderDTO.getNum().longValue() > ps.getNum().intValue()){
					logger.info(ps.getId()+"库存不足");
					return ResultUtils.returnError("该商品库存不足");
				}*/
				/*if(ps.getProduct().getStartNum()> orderDTO.getNum().longValue()){
					logger.info(ps.getId()+"起批数量不能大于购买数量");
					return ResultUtils.returnError("起批数量不能大于购买数量");
				}*/
				order.setProductName(ps.getProduct().getName());//商品名称
				order.setProductSalePrice(ps.getSalePrice());//商品销售价
				order.setProductSpecificationName(ps.getContent());//商品分类型号
				order.setProductTypeName(ps.getProductType().getContent());//商品分类名称
				order.setProduct(ps.getProduct());
				order.setIndustryAssociation(hunter.getIndustryAssociation());
				order.setProductType(ps.getProductType());
				order.setProductSpecification(ps);
				order.setPayType(0);//预付定金
				order.setOrderSubNo(orderNum+"_"+ps.getId());
				order.setNum(orderDTO.getNum().intValue());
				order.setStatus(0);//0待付款 1已付定金 2待发货 3待收货 4已评价 5申请退款   6已退款 7已收货
				//if(proVO.getPayType() == 0){//支付类型 0预付定金 1全款
				Long subscriptionMoney = (ps.getSubscriptionMoney())* orderDTO.getNum();//定金价
				Long totalPrice=ps.getSalePrice().longValue() * orderDTO.getNum();   //总价
				order.setSubscriptionMoney(subscriptionMoney);
				order.setActualMoney(subscriptionMoney.longValue());
				order.setTotalPrice(totalPrice); 						//维护总价
				order.setNegotiatePrice(totalPrice.longValue()-subscriptionMoney.longValue());  //维护尾款
				Long salePrice = ps.getSalePrice()* orderDTO.getNum();//商品价
				//*Long serviceMoney = Long.valueOf(Math.round(Double.valueOf((DoubleUtils.formatRound(salePrice*0.13, 0))))+"");//服务费
				//order.setServiceMoney(serviceMoney);//*
				order.setActualMoney(salePrice.longValue());//总价
				//维护分润信息
				Order saveAndModify = this.orderShare(order, member);
				logger.info(saveAndModify.getId()+"订单生成成功 ");
				orderids.append(saveAndModify.getId()+",");

			}
			//}
			String orderidss=orderids.toString();
			orderidss = orderidss.substring(0, orderidss.lastIndexOf(','));
			params.put("orderids", orderidss);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下单异常=====错误信息："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("下单失败");
		}
		return ResultUtils.returnSuccess("下单成功", params);
	}

	@Override
	@Transactional
	public Result cancelOrderByOrderNo(String orderNo) {
		logger.info("取消订单传入主订单号：orderNo "+orderNo);
		Result result = null;
		try {
			logger.info("**************   开始执行退款操作  **************");
			//int num = iOrderDao.updateOrderStatusByOrderNo(orderNo,8);
            result = wxPayOrderService.sendRefundForCancel(orderNo);
        }catch (Exception e){
			logger.info("更新订单异常："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result = ResultUtils.returnError("取消订单失败！");
		}
		return result;
	}

	/**
	 * 供应商接单
	 * @param orderNo
	 * @return
	 */
	@Override
	@Transactional
	public Integer agreeOrderBySupplyer(String orderNo) {
		int status = 0;
		try{
			status = iOrderDao.agreeOrderBySupplyer(orderNo);
			if (status == 0){
				throw new Exception("供应商接单，更新数据异常！");
			}
			logger.info("更新订单成功！！");
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
		}
		return status;
	}

	@Override
	public List<Order> getOrderListByOrderNum(String orderNo){
		return this.orderDao.getOrderListByOrderNum(orderNo);
	}
}
