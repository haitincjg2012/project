package com.alqsoft.service.impl.wxpayorder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.wxpayorder.WxPayOrderDao;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.mybatis.dao.IOrderDao;
import com.alqsoft.rpc.pay.RpcPayService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.wxpayorder.WxPayOrderService;
import com.alqsoft.utils.CodeConstExt;
import com.alqsoft.utils.UtilDate;
import com.alqsoft.utils.weixin.RefundRequest;
import com.alqsoft.utils.weixin.WXRandCharsUtils;
import com.alqsoft.utils.weixin.common.Configure;
import com.alqsoft.utils.weixin.nativecode.HttpUtil;
import com.alqsoft.utils.weixin.nativecode.PayCommonUtil;
import com.alqsoft.utils.weixin.nativecode.XMLUtil;
import com.github.sd4324530.fastweixin.util.JSONUtil;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional(readOnly=true)
public class WxPayOrderServiceImpl implements WxPayOrderService {

	private static Logger logger = LoggerFactory.getLogger(WxPayOrderServiceImpl.class);
	@Autowired
	private WxPayOrderDao wxPayOrderDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private InitParamPc initParam;
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private RpcPayService rpcPayService;
	
	@Override
	public boolean delete(Long arg0) {
		return false;
	}

	@Autowired
	private IOrderDao iOrderDao;

	@Override
	public WxPayOrder get(Long arg0) {
		return wxPayOrderDao.findOne(arg0);
	}

	@Transactional
	@Override
	public WxPayOrder saveAndModify(WxPayOrder arg0) {
		return wxPayOrderDao.save(arg0);
	}

	@Override
	@Transactional
	public void updateWxPayOrderType(WxPayOrder wx) {
		logger.info("=========================进入updateWxPayOrderType方法===交易记录"+wx.getWxOrderNum()+"===============");
		List<WxPayOrder> list = this.wxPayOrderDao.getListByWxOrderNum(wx.getWxOrderNum());
		if(list.size() == 0){
			logger.info(wx.getWxOrderNum()+"记录不存在");
			return;
		}
		List<WxPayOrder> wxPayOrders = wxPayOrderDao.getAllByWxOrderNum(wx.getWxOrderNum());
		List<Long> ids = new ArrayList<Long>();
		List<String> orderNos = new ArrayList<String>();
		for (WxPayOrder  wxp: list) {
			if(wxp.getStatus() == 0){
				Long id = wxp.getId();
				String orderNo = wxp.getOrderNum();
				logger.info("==============wx.getWxOrderNum()："+id);
				ids.add(id);
				orderNos.add(orderNo);
			}
		}
		logger.info("==========================================wx.getStatus()"+wx.getStatus());
		if(wx.getStatus() == 1){
			if(ids.size()>0)
				this.wxPayOrderDao.updateWxPayOrderTypeByS(wx.getWxSerialNumber(), wx.getDescription(), ids);// 支付记录状态维护
			if(orderNos.size()>0){
				this.orderService.updateOrderTypeByPay(orderNos);//支付订单状态维护
				if (!Objects.isNull(wxPayOrders) && wxPayOrders.size()>0){
					WxPayOrder e = wxPayOrders.get(0);
					//尾款
					if (!Objects.isNull(e) && Objects.equals(e.getType(),2)){
						orderService.updateHunterInfo(orderNos);
					}
				}
			}
			logger.info(wx.getWxOrderNum()+"交易记录维护成功");
		}
		if(wx.getStatus() == 2){
			if(ids.size()>0){
				this.wxPayOrderDao.updateWxPayOrderTypeByF(wx.getWxSerialNumber(), wx.getDescription(), ids);//微信支付记录状态维护
				logger.info(wx.getWxOrderNum()+"交易记录维护成功");
			}
		}
	}

	@Override
	@Transactional
	public Result saveRecordByPay(WxPayOrder wxPayOrder, String ip) {
		try {
			WxPayOrder saveAndModify = this.saveAndModify(wxPayOrder);
			Order order = this.orderService.get(wxPayOrder.getOrderInfo().getId());
			Order orderShare = this.orderService.orderShare(order, order.getMember());
			logger.info(orderShare.getId()+"订单议价后分润维护 ");
			return this.sendCodeUnifiedOrder(saveAndModify.getWxOrderNum(), saveAndModify.getMoney(), ip);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("保存微信支付记录失败");
		}
	}

	@Override
	public List<WxPayOrder> findRecordByOrderId(Long id) {
		return this.wxPayOrderDao.findRecordByOrderId(id);
	}

	@Override
	public List<WxPayOrder> findRecordByOrderIdAndStatusSuc(Long id) {
		return this.wxPayOrderDao.findRecordByOrderIdAndStatusSuc(id);
	}

	@Override
	public Long getSumPriceBySerialNum(String wxSerialNumber) {
		return this.wxPayOrderDao.getSumPriceBySerialNum(wxSerialNumber);
	}

	@Override
	public Result sendCodeUnifiedOrder(String wxOrderNum, Long money, String ip) {
		System.out.println("进入到微信请求统一下单");
		logger.info("---订单号：："+wxOrderNum+"--开始请求统一下单");
		if(wxOrderNum==null||"".equals(wxOrderNum)){
			return ResultUtils.returnError("订单号不能为空");
		}
		if(ip==null||"".equals(ip)){
			return ResultUtils.returnError("ip地址为空");
		}
		if(money==null|| money <= 0){
			return ResultUtils.returnError("价格异常");
		}
		List<WxPayOrder> wxPayOrderList = this.wxPayOrderDao.findWxPayOrderByWxOrderNumAndStatus(wxOrderNum);
		if(wxPayOrderList.size() == 0){
			return ResultUtils.returnError("不存在需要支付的订单");
		}
		Long totalPrice = 0L;
	/*	弃用 for (WxPayOrder wxPayOrder : wxPayOrderList) {
			Order order = this.orderService.get(wxPayOrder.getOrderInfo().getId());
			ProductSpecification productSpecification = this.productSpecificationService.get(order.getProductSpecification().getId());
			Long num = productSpecification.getNum();//库存
			Integer count = order.getNum();//购买数量
			if(productSpecification.getProduct().getStatus().intValue() == 0){
				return ResultUtils.returnError("商品:"+(order.getProductName().length()>=10?order.getProductName().substring(0, 10):order.getProductName())+"已下架");
			}
			if(count.intValue() > Integer.parseInt(num.toString())){
				return ResultUtils.returnError("商品:"+order.getProductSpecificationName()+"库存不足");
			}
			totalPrice += wxPayOrder.getMoney().longValue();
		}
		if(totalPrice.longValue() != money.longValue()){
			return ResultUtils.returnError("支付金额与实际存在订单金额不符");
		}*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("out_trade_no", wxOrderNum);
		//以下是需要发送的请求参数
		String open_appid=initParam.getProperties().getProperty("WECHAT_OPEN_APPID");//微信开放平台appid
		String appid=open_appid;
		String mch_id=initParam.getProperties().getProperty("WECHAT_MCH_ID");	//商户号
		String notify_url=initParam.getProperties().getProperty("wx_back_url");//回调地址
		String key=initParam.getProperties().getProperty("WECHAT_KEY");//商户key
		String unified_order_url=initParam.getProperties().getProperty("WECHAT_UNIFIED_ORDER_URl");//统一下单地址
		String nonce_str=WXRandCharsUtils.getRandomString(16);//随机字符串
		String body="下单支付";							//商品描述
		String out_trade_no=wxOrderNum;					//商户订单号
		//String total_fee=money.toString();				//总金额单位为分
		String total_fee=money.toString();	
		String spbill_create_ip=ip;						//终端ip
		String trade_type="APP";					//交易类型APP和NATIVE
		String time_start=PayCommonUtil.getCurrTime();	//交易起始时间
		//String product_id=orderId;	//这里的商品id也用订单id trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义

		//参数：开始生成签名
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", body);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("spbill_create_ip", spbill_create_ip);  
		parameters.put("notify_url", notify_url);  
		parameters.put("trade_type", trade_type);  
		parameters.put("time_start", time_start);
		String sign=PayCommonUtil.createSign("UTF-8", parameters, key);//生成签名
		parameters.put("sign", sign);  
		String requestXML = PayCommonUtil.getRequestXml(parameters);  //要发送请求的xml数据
		System.out.println(requestXML);
		String resXml = HttpUtil.postData(unified_order_url, requestXML); //统一下单请求
		if (resXml == null) {
			return ResultUtils.returnError("服务器请求超时，请重试");
		}
			 try {
				Map map=XMLUtil.doXMLParse(resXml);
				String prepay_id=(String) map.get("prepay_id");
				String noncestr=(String)map.get("nonce_str");
				//String returnSign=(String)map.get("sign");
				if(prepay_id!=null&&!"".equals(prepay_id)){
					Map<String ,String> param=new HashMap<String ,String>();//给手机端传递的参数信息
			    	param.put("prepayid", prepay_id);//pay id
			    	param.put("noncestr", noncestr);//随机字符串
			    	param.put("partid", mch_id);//商户id
					param.put("sign", sign);//签名
			    	return ResultUtils.returnSuccess("统一下单返回预支付交易链接成功", param);
				}else{
					  logger.error("APP统一下单请求预支付交易链接获取失败"+ map.get("return_msg") +"--"+ map.get("return_msg") +"--"+ map.get("err_code_des"));
					  return ResultUtils.returnError("APP统一下单请求预支付交易链接获取失败"+ map.get("return_msg") +"--"+ map.get("err_code_des"));
				}
				
			 } catch (Exception e) {
				logger.error("APP统一下单返回预支付交易信息解析失败"+e.getMessage());
				e.printStackTrace();
				return ResultUtils.returnError("统一下单返回预支付交易链接解析失败"+e.getMessage());
			 }
	}

	/**
	 * 退款，微信和支付宝
	 */
	@Override
	@Transactional
	public Result sendRefund(Order o) {
		List<WxPayOrder> list = this.wxPayOrderDao.findRecordByOrderId(o.getId());
		List<WxPayOrder> l = this.wxPayOrderDao.getListByOrderIdAndStatus(o.getId());
		if(l.size() != 0){
			logger.info(o.getId()+"订单已申请退款");
			return ResultUtils.returnError("订单已申请退款"); 
		}
		List<Map<String, Object>> refundMsg = new ArrayList<>();
		String wxOrderNum = "";
		if(list.size() == 0){// 无支付记录
			logger.info(o.getId()+"订单无支付记录");
			return ResultUtils.returnError("订单支付状态异常");
		}else if(list.size() == 1){// 全款支付 或 支付定金
			Map<String, Object> map = new HashMap<String, Object>();//退款信息
			if(list.get(0).getType() == 0 || list.get(0).getType() == 1){
				WxPayOrder wxPayOrder = new WxPayOrder();
				//=================================全款支付===============================================
				if(list.get(0).getType() == 0){
					List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 3);
					map.put("type", 3);
					wxPayOrder.setType(3);
					if(record.size() == 0){
						if(list.get(0).getPayType()==null||list.get(0).getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}
						
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();// 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号。
					}
				}
				//==================================定金支付===============================================
				if(list.get(0).getType() == 1){
					List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 4);
					map.put("type", 4);
					wxPayOrder.setType(4);
					if(record.size() == 0){
						if(list.get(0).getPayType()==null||list.get(0).getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
				}
				map.put("wxOrderNum", wxOrderNum);
				map.put("wxSerialNumber", list.get(0).getWxSerialNumber());
				map.put("total_fee", this.wxPayOrderDao.getSumPriceBySerialNum(list.get(0).getWxSerialNumber()));
				map.put("refund_fee", list.get(0).getMoney());
				map.put("paytype", list.get(0).getPayType()==null?0:list.get(0).getPayType());//退款的类型，是支付宝还是微信  0就从微信退，1从支付宝退				
				wxPayOrder.setOrderInfo(o);
				wxPayOrder.setWxOrderNum(wxOrderNum);
				wxPayOrder.setStatus(0);
				wxPayOrder.setMoney(list.get(0).getMoney());
				wxPayOrder.setPayType(list.get(0).getPayType()==null?0:list.get(0).getPayType());
				this.saveAndModify(wxPayOrder);
				logger.info("订单:"+o.getId()+"退款记录生成");
				
			}else{
				logger.info(o.getId()+"订单支付记录错误");
				return ResultUtils.returnError("订单支付记录错误");
			}
			refundMsg.add(map);
		}else if(list.size() == 2){
			//=================================支付定金  支付协商价格=================================================
			if(list.get(0).getType() == list.get(1).getType()){
				logger.info(o.getId()+"订单支付记录异常");
				return ResultUtils.returnError("订单支付记录异常");
			}
			for (WxPayOrder wxPayOrder : list) {
				Map<String, Object> map = new HashMap<String, Object>();//退款信息
				if(wxPayOrder.getType() == 0){// 全款支付
					logger.info(o.getId()+"订单支付记录异常");
					return ResultUtils.returnError("订单支付记录异常");
				}
				if(wxPayOrder.getType() == 1){// 定金支付
					List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 4);
					if(record.size() == 0){
						if(wxPayOrder.getPayType()==null||wxPayOrder.getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
					map.put("type", 4);
					map.put("wxOrderNum", wxOrderNum);
					WxPayOrder wx = new WxPayOrder();
					wx.setOrderInfo(o);
					wx.setWxOrderNum(wxOrderNum);
					wx.setType(4);
					wx.setStatus(0);
					wx.setMoney(wxPayOrder.getMoney());
					wx.setPayType(wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());
					this.saveAndModify(wx);
					logger.info("订单:"+o.getId()+"定金退款记录生成");
				}
				if(wxPayOrder.getType() == 2){// 协商价格支付
					List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 5);
					if(record.size() == 0){
						if(wxPayOrder.getPayType()==null||wxPayOrder.getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
					map.put("type", 5);
					map.put("wxOrderNum", wxOrderNum);
					WxPayOrder wx = new WxPayOrder();
					wx.setOrderInfo(o);
					wx.setWxOrderNum(wxOrderNum);
					wx.setType(5);
					wx.setStatus(0);
					wx.setMoney(wxPayOrder.getMoney());
					wx.setPayType(wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());//null和0都是微信，1是支付宝
					this.saveAndModify(wx);
					logger.info("订单:"+o.getId()+"协商价格退款记录生成");
				}
				map.put("wxSerialNumber", wxPayOrder.getWxSerialNumber());
				map.put("total_fee", this.wxPayOrderDao.getSumPriceBySerialNum(wxPayOrder.getWxSerialNumber()));
				map.put("refund_fee", wxPayOrder.getMoney());
				map.put("paytype", wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());
				refundMsg.add(map);
			}
		}else{
			logger.info(o.getId()+"订单支付记录异常");
			return ResultUtils.returnError("订单支付状态异常"); 
		}
		return this.sendRefundToWx(refundMsg, o);
	}
	/**
	 * @author:  wangxueyang[wxueyanghj@163.com]
	 * @create:  2017/9/26 15:44
	 * @desc: 取消订单退款
	 **/
	@Override
	public Result sendRefundForCancel(String orderNo) {
		//List<WxPayOrder> list = this.wxPayOrderDao.findRecordByOrderId(orderNo);
		//List<WxPayOrder> l = this.wxPayOrderDao.getListByOrderIdAndStatus(o.getId());
        List<WxPayOrder> list = this.wxPayOrderDao.findRecordByOrderNo(orderNo);
        //当前主订单号下 查询type = 3 4 5 状态为0的记录  status  = 0 的记录
        List<WxPayOrder> l = iOrderDao.getListByOrderNoAndStatus(orderNo,"3,4,5");

        if(l.size() != 0){
			logger.info(orderNo+"订单已申请退款");
			return ResultUtils.returnError("订单已申请退款");
		}
		List<Map<String, Object>> refundMsg = new ArrayList<>();
		String wxOrderNum = "";
		if(list.size() == 0){// 无支付记录
			logger.info(orderNo+"订单无支付记录");
			return ResultUtils.returnError("订单支付状态异常");
		}else if(list.size() == 1){// 全款支付 或 支付定金
			Map<String, Object> map = new HashMap<String, Object>();//退款信息
			if(list.get(0).getType() == 0 || list.get(0).getType() == 1){
				WxPayOrder wxPayOrder = new WxPayOrder();
				//=================================全款支付===============================================
				if(list.get(0).getType() == 0){
					//List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 3);
                    List<WxPayOrder> record = this.iOrderDao.getListByOrderNoAndStatus(orderNo,"3");
					map.put("type", 3);
					wxPayOrder.setType(3);
					if(record.size() == 0){
						if(list.get(0).getPayType()==null||list.get(0).getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}

					}else{
						wxOrderNum = record.get(0).getWxOrderNum();// 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号。
					}
				}
				//==================================定金支付===============================================
				if(list.get(0).getType() == 1){
                    List<WxPayOrder> record = this.iOrderDao.getListByOrderNoAndStatus(orderNo,"4");
					map.put("type", 4);
					wxPayOrder.setType(4);
					if(record.size() == 0){
						if(list.get(0).getPayType()==null||list.get(0).getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
				}
				map.put("wxOrderNum", wxOrderNum);
				map.put("wxSerialNumber", list.get(0).getWxSerialNumber());
				map.put("total_fee", this.wxPayOrderDao.getSumPriceBySerialNum(list.get(0).getWxSerialNumber()));
				map.put("refund_fee", list.get(0).getMoney());
				map.put("paytype", list.get(0).getPayType()==null?0:list.get(0).getPayType());//退款的类型，是支付宝还是微信  0就从微信退，1从支付宝退
				//wxPayOrder.setOrderInfo(o);
				wxPayOrder.setWxOrderNum(wxOrderNum);
				wxPayOrder.setStatus(0);
				wxPayOrder.setMoney(list.get(0).getMoney());
				wxPayOrder.setPayType(list.get(0).getPayType()==null?0:list.get(0).getPayType());
				this.saveAndModify(wxPayOrder);
				logger.info("订单:"+orderNo+"退款记录生成");

			}else{
				logger.info(orderNo+"订单支付记录错误");
				return ResultUtils.returnError("订单支付记录错误");
			}
			refundMsg.add(map);
		}else if(list.size() == 2){
			//=================================支付定金  支付协商价格=================================================
			if(list.get(0).getType() == list.get(1).getType()){
				logger.info(orderNo+"订单支付记录异常");
				return ResultUtils.returnError("订单支付记录异常");
			}
			for (WxPayOrder wxPayOrder : list) {
				Map<String, Object> map = new HashMap<String, Object>();//退款信息
				if(wxPayOrder.getType() == 0){// 全款支付
					logger.info(orderNo+"订单支付记录异常");
					return ResultUtils.returnError("订单支付记录异常");
				}
				if(wxPayOrder.getType() == 1){// 定金支付
					//List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 4);
                    List<WxPayOrder> record = this.iOrderDao.getListByOrderNoAndStatus(orderNo,"4");
					if(record.size() == 0){
						if(wxPayOrder.getPayType()==null||wxPayOrder.getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
					map.put("type", 4);
					map.put("wxOrderNum", wxOrderNum);
					WxPayOrder wx = new WxPayOrder();
					//wx.setOrderInfo(o);
					wx.setWxOrderNum(wxOrderNum);
					wx.setType(4);
					wx.setStatus(0);
					wx.setMoney(wxPayOrder.getMoney());
					wx.setPayType(wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());
					this.saveAndModify(wx);
					logger.info("订单:"+orderNo+"定金退款记录生成");
				}
				if(wxPayOrder.getType() == 2){// 协商价格支付
//					List<WxPayOrder> record = this.wxPayOrderDao.findRecordByOrderIdAndStatus(o.getId(), 5);
                    List<WxPayOrder> record = this.iOrderDao.getListByOrderNoAndStatus(orderNo,"5");
					if(record.size() == 0){
						if(wxPayOrder.getPayType()==null||wxPayOrder.getPayType()==0){
							wxOrderNum="PSWX_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号微信
						}else{
							wxOrderNum="PSAL_"+UtilDate.getOrderNum()+UtilDate.getThree();// 商户退货订单流水号支付宝
						}
					}else{
						wxOrderNum = record.get(0).getWxOrderNum();
					}
					map.put("type", 5);
					map.put("wxOrderNum", wxOrderNum);
					WxPayOrder wx = new WxPayOrder();
					//wx.setOrderInfo(o);
					wx.setWxOrderNum(wxOrderNum);
					wx.setType(5);
					wx.setStatus(0);
					wx.setMoney(wxPayOrder.getMoney());
					wx.setPayType(wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());//null和0都是微信，1是支付宝
					this.saveAndModify(wx);
					logger.info("订单:"+orderNo+"协商价格退款记录生成");
				}
				map.put("wxSerialNumber", wxPayOrder.getWxSerialNumber());
				map.put("total_fee", this.wxPayOrderDao.getSumPriceBySerialNum(wxPayOrder.getWxSerialNumber()));
				map.put("refund_fee", wxPayOrder.getMoney());
				map.put("paytype", wxPayOrder.getPayType()==null?0:wxPayOrder.getPayType());
				refundMsg.add(map);
			}
		}else{
			logger.info(orderNo+"订单支付记录异常");
			return ResultUtils.returnError("订单支付状态异常");
		}
		//return this.sendRefundToWx(refundMsg, o);
        Order o = new Order();
		o.setOrderNo(orderNo);
        return this.sendRefundToWx(refundMsg, o);
	}

	//向微信支付宝第三方发送退货请求
	@Transactional
	private Result sendRefundToWx(List<Map<String, Object>> refundMsg, Order o) {
		logger.info("=======进入第三方退款方法，refundMsg参数为："+refundMsg.toString()+"=======");
		int refundsuccess=0;//订金和协商款有其中一笔退款成功，则该订单标记为退款成功，如果有其中失败一笔退款失败则线下处理
		for (Map<String, Object> map : refundMsg) {
			String wxOrderNum = map.get("wxOrderNum").toString();//商户退款单号
			String wxSerialNumber = map.get("wxSerialNumber").toString();//支付流水号
			String totalFee = map.get("total_fee").toString();//订单总价
			String refundFee = map.get("refund_fee").toString();//退款价格
			//=======================向支付宝第三方发送请求退款=====================================
			if(map.get("paytype")==null||Integer.valueOf(map.get("paytype").toString())==0){//这里判断null是为了之前的旧数据
	
				System.out.println("进入到微信请求退货");
				String path = initParam.getProperties().getProperty("apiclient_cert_url");
				logger.info("---订单号：："+map.get("wxOrderNum")+"--开始微信请求退款接口");


				if(wxOrderNum==null||"".equals(wxOrderNum)){
					return ResultUtils.returnError("订单号不能为空");
				}
				if(wxSerialNumber==null||"".equals(wxSerialNumber)){
					return ResultUtils.returnError("支付流水号不能为空");
				}
				if(totalFee==null|| Long.valueOf(totalFee) <= 0){
					return ResultUtils.returnError("订单价格异常");
				}
				if(refundFee==null|| Long.valueOf(refundFee) < 0){
					return ResultUtils.returnError("退款价格异常");
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("out_refund_no", wxOrderNum);//退款商户单号
				//以下是需要发送的请求参数
				String open_appid=initParam.getProperties().getProperty("WECHAT_OPEN_APPID");//微信开放平台appid
				String appid=open_appid;
				String mch_id=initParam.getProperties().getProperty("WECHAT_MCH_ID");	//商户号
				String key=initParam.getProperties().getProperty("WECHAT_KEY");//商户key
				String nonce_str=WXRandCharsUtils.getRandomString(16);//随机字符串
				String out_refund_no=wxOrderNum;					//商户退款订单号
				String transaction_id=wxSerialNumber;					//支付微信流水号
				String total_fee=totalFee;				//总金额单位为分
				String refund_fee=refundFee;			//退款金额 单位分
				//String op_user_id=mch_id;	//操作员帐号, 默认为商户号
				//参数：开始生成签名
				SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
				parameters.put("appid", appid);
				parameters.put("mch_id", mch_id);
				parameters.put("nonce_str", nonce_str);
				parameters.put("transaction_id", transaction_id);
				parameters.put("out_refund_no", out_refund_no);
				parameters.put("total_fee", total_fee);
				parameters.put("refund_fee", refund_fee);
				parameters.put("op_user_id", mch_id);
				String sign=PayCommonUtil.createSign("UTF-8", parameters, key);//生成签名
				parameters.put("sign", sign);
				String requestXML = PayCommonUtil.getRequestXml(parameters);  //要发送请求的xml数据
				System.out.println(requestXML);
				RefundRequest refundRequest = new RefundRequest();

				try {
					String result = refundRequest.httpsRequest(Configure.REFUND_API, requestXML, path);
					Map data=XMLUtil.doXMLParse(result);
					String return_code=(String) data.get("return_code");//
					String return_msg=(String) data.get("return_msg");//失败返回信息
					String refund_id=(String)data.get("refund_id");//微信退款单号
					WxPayOrder wxPayOrder = this.wxPayOrderDao.findRecordByWxOrderNumAndStatus(wxOrderNum);
					if(refund_id!=null&&!"".equals(refund_id)){
						wxPayOrder.setStatus(1);
						wxPayOrder.setDescription("退款成功");
						wxPayOrder.setWxSerialNumber(refund_id);
						this.saveAndModify(wxPayOrder);//维护微信支付信息
						refundsuccess++;
					}else{
						  logger.error("退款请求退款链接获取失败"+ data.get("return_code") +"--"+ data.get("return_msg") +"--"+ data.get("err_code_des"));
						  wxPayOrder.setStatus(2);
						  wxPayOrder.setDescription(data.get("err_code_des").toString());
						  wxPayOrder.setWxSerialNumber(refund_id);
						  this.saveAndModify(wxPayOrder);//维护微信支付信息
						 // return ResultUtils.returnError("微信退款请求退款链接获取失败"+(String) data.get("return_msg")+"--"+(String)data.get("err_code_des"));
					}
				} catch (Exception e) {
					logger.error("微信请求退款链接返回信息解析失败"+e.getMessage());
					e.printStackTrace();
					//return ResultUtils.returnError("微信请求退款链接返回信息解析失败"+e.getMessage());
				}
			}
		
		 try {
			//===============================向支付宝第三方发送请求退款=======================================
			if(Integer.valueOf(map.get("paytype").toString())==1){
				String refundUrl = "http://118.89.229.182/pay/alipay/alipayTradeRefund";
				logger.info("==========订单号：："+map.get("wxOrderNum")+"====开始支付宝请求退款接口");

				RestTemplate restTemplate = new RestTemplate();
				MultiValueMap<String, String> map1 = new LinkedMultiValueMap<>();
				Map<String,Object> sendparams = new HashMap<String,Object>();
				sendparams.put("trade_no", wxSerialNumber);//支付宝交易流水号
				sendparams.put("refund_amount", DoubleUtils.div(Long.valueOf(refundFee), 100, 2));//退款金额
				sendparams.put("out_request_no", wxOrderNum);//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
				String bizContent=JSONUtil.toJson(sendparams);
				map1.add("bizContent", bizContent);
				map1.add("fromSystem", CodeConstExt.PAY_SYSTEM_FROM_PHPF);
				String aa = refundUrl+"?bizContent="+bizContent+"&fromSystem="+CodeConstExt.PAY_SYSTEM_FROM_PHPF;
				logger.info("========================"+aa);
				String sendGet = restTemplate.postForObject(refundUrl, map1, String.class);
				logger.info("退款回复======="+sendGet);
				JSONObject result = JSON.parseObject(sendGet);
				logger.info("退款信息content:{}",result.toJSONString());
				//Result result = rpcPayService.alipayTradeRefund(bizContent, CodeConstExt.PAY_SYSTEM_FROM_PHPF);
				//////////////////
				logger.info("支付宝退款调用pay结果 result:{}",result);
				if (Integer.valueOf(result.get("code").toString())==0) {//与pay工程通信成功
			       	 logger.info("支付宝回调调用pay工程验签方法,与pay工程通信成功");
			       	WxPayOrder wxPayOrder = this.wxPayOrderDao.findRecordByWxOrderNumAndStatus(wxOrderNum);
			       	if(result.get("msg").equals("SUCCESS")){//退款成功
			       		Map<String, Object> mapType = (Map<String, Object>)JSON.parseObject(result.get("content").toString(), Map.class);
			       		Map<String,Object> data = (Map<String, Object>) mapType.get("alipay_trade_refund_response");
				       	wxPayOrder.setStatus(1);
						wxPayOrder.setDescription("退款成功");
						wxPayOrder.setWxSerialNumber(data.get("trade_no")==null?"":data.get("trade_no").toString());
						this.saveAndModify(wxPayOrder);
							refundsuccess++;
			       	}else{//退款失败
			       		wxPayOrder.setStatus(2);
					    wxPayOrder.setDescription(result.get("msg").toString());
					  //wxPayOrder.setWxSerialNumber(refund_id);
					  this.saveAndModify(wxPayOrder);//维护微信支付信息
			       		logger.info("支付宝退款失败,返回结果msg:"+result.get("msg").toString());
			       	}
		       	
		       }else{//与pay工程通信失败
		       	 logger.info("调用pay工程支付宝退货接口失败。返回结果code:"+result.get("code").toString()+",msg:"+result.get("msg").toString());
		       	 //return ResultUtils.returnError("退款失败:"+result.getMsg());
		       }
			}
		 } catch (Exception e) {
			 	e.printStackTrace();
				logger.error("向支付宝第三方发送请求退款发生异常"+e.getMessage());
			}
	 
		if(Integer.valueOf(map.get("paytype").toString())!=1&&!(map.get("paytype")==null||Integer.valueOf(map.get("paytype").toString())==0)){
			logger.info("退款pay参数异常："+map.get("paytype"));
			return ResultUtils.returnError("退款类型异常,paytype:"+map.get("paytype"));
		}
	}
		
		if(refundsuccess>0){
            //更新本地订单状态  批量操作
            //根据主订单号  检索出所有的订单
            String orderNo = o.getOrderNo();
            List<String> oList = iOrderDao.getOrderListByOrderNo(orderNo);
            for (int i = 0 ; i< oList.size() ; i++){
                Order order = new Order();
                order.setId(Long.parseLong(oList.get(i)));
                this.orderService.updateOrderTypeByBack(order);
            }
            return ResultUtils.returnSuccess("取消订单成功！");
		}else{
			return ResultUtils.returnError("退款失败");
		}
	}
}
