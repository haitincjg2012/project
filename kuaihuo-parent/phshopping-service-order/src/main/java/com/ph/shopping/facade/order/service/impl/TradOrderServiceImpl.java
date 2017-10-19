package com.ph.shopping.facade.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.lorne.tx.annotation.TxTransaction;
import com.ph.shopping.common.core.dto.PayDTO;
import com.ph.shopping.common.core.dto.PrePayDTO;
import com.ph.shopping.common.core.dto.PushDTO;
import com.ph.shopping.common.core.util.PushUtil;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.IMemberOrderOnlineMapper;
import com.ph.shopping.facade.mapper.TradOrderMapper;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.profit.service.IUnlineOrderProfitService;
import com.ph.shopping.facade.spm.entity.Merchant;

import cm.ph.shopping.facade.order.constant.OrderOnlineRoleTypeEnum;
import cm.ph.shopping.facade.order.constant.OrderOnlineStatusEnum;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTOS;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline;
import cm.ph.shopping.facade.order.service.TradOrderService;
import cm.ph.shopping.facade.order.vo.MemberInfo;
import cm.ph.shopping.facade.order.vo.MerchantVO;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @项目：phshopping-service-order
 *
 * @描述：商户接口实现
 *
 * @作者：Mr.Dong
 *
 * @创建时间：2017年3月10日
 *
 * @Copyright @2017 by Mr.Dong
 */
@Component
@Service(version="1.0.0")
public class TradOrderServiceImpl implements TradOrderService {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TradOrderMapper tradOrderMapper;
	@Reference(version = "1.0.0")
	private IUnlineOrderProfitService iUnlineOrderProfitService;
	@Autowired
	private PushUtil pushUtil;
	@Autowired
	private SmsUtil smsUtil;
	/**
	 * 添加交易订单
	 */
	@Transactional
	@TxTransaction
	@Override
	public Result addTradOrder(PhMemberOrderUnlineDTOS orderUnline,byte payType) {
		Result result=ResultUtil.getResult(RespCode.Code.FAIL);
 		logger.info( "线下订单查询接口入参phMemberOrderUnlineDTO：{}",JSON.toJSONString(orderUnline) );
		try {
 		//订单号
 		String orderNo=null;
 		//=======================线上扫码订单=======================//
 		if(orderUnline.getMemberId()!=null){
 			//判断扫码支付金额不为空
 			if(orderUnline.getOrderMoney()==null){
 				return ResultUtil.getResult(RespCode.Code.PLEASE_MERCHANTSETMONEY);
 			}
 			orderUnline.setMemberId(orderUnline.getMemberId()); //设置会员ID
 			//通过会员id查询电话
 			MemberInfo memberInfo= tradOrderMapper.getMemberInfoByMemberId(orderUnline.getMemberId());
 			//订单号生成
 			orderNo="KHSMDD"+UtilDate.getOrderNum()+UtilDate.getThree();
 			if(memberInfo==null){
				return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
			}
			orderUnline.setMemberPhone(memberInfo.getTelPhone());	//会员id
			//订单金额处理
			double ordr=orderUnline.getOrderMoney()*10000;
			//获取商户信息
			MerchantVO merchantInfo=tradOrderMapper.getMerchantInfo(orderUnline.getMerchantId());
			orderUnline.setMerchantName(merchantInfo.getMerchantName());//商家名字
			orderUnline.setMerchantPhone(merchantInfo.getTelPhone()); //商家电话
			orderUnline.setOrderNo(orderNo);  	//订单号
			orderUnline.setPayType(payType);		//支付类型
			orderUnline.setOrderMoney(ordr);
			//创建交易订单
			tradOrderMapper.createTradOrder(orderUnline);
			//获取订单号和ID
	    	return ResultUtil.getResult(RespCode.Code.SUCCESS,orderUnline);
 		}
 		//=====================线上交易订单=========================================//
		if(orderUnline.getMemberPhone()!=null&&!"".equals(orderUnline.getMemberPhone())){
			//通过会员电话查询会员信息
			MemberInfo memberInfo= tradOrderMapper.getMemberInfo(orderUnline.getMemberPhone());
			//订单号生成
			orderNo="KHZFDD"+UtilDate.getOrderNum()+UtilDate.getThree();
			if(memberInfo==null){
				return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
			}
			orderUnline.setMemberId(memberInfo.getId());	//会员id
			//订单金额处理
			double ordr=orderUnline.getOrderMoney()*10000;
			//获取商户信息
			MerchantVO merchantInfo=tradOrderMapper.getMerchantInfo(orderUnline.getMerchantId());
			orderUnline.setMerchantName(merchantInfo.getMerchantName());//商家名字
			orderUnline.setMerchantPhone(merchantInfo.getTelPhone()); //商家电话
			orderUnline.setOrderNo(orderNo);  	//订单号
			orderUnline.setPayType(payType);		//支付类型
			orderUnline.setOrderMoney(ordr);
			//创建交易订单
			tradOrderMapper.createTradOrder(orderUnline);
			/**
			 * 创建完交易订单，给用户推送一下
			 */
			logger.info("=================交易订单完成，开始给会员推送====================");
			 PushDTO push=new PushDTO();
	         push.setCodeType("fr0001");
	         push.setContent("【快火】"+merchantInfo.getMerchantName()+"请您支付"+orderUnline.getOrderMoney()/10000+"元，点击查看详情");
	         push.setTitle("通知");
	         String userId=orderUnline.getMemberPhone()+"_0";
	         push.setUserId(userId);
			 pushUtil.push(push);
			//推送完成入库
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("content",push.getContent());
			map.put("userId",orderUnline.getMemberPhone());
			map.put("messageType",1);
			tradOrderMapper.insertPayPushMessage(map);
			logger.info("=================预订订单推送完成====================");
			logger.info("=================预订订单完成，开始发送短信====================");
			   PayDTO pay=new PayDTO();
	            pay.setKfPhone("4000-8586-315");
	            pay.setOrderNum(orderNo);
	            pay.setAddress(merchantInfo.getMerchantName());
	            pay.setMermentNum(merchantInfo.getTelPhone());
	            pay.setPhone(orderUnline.getMemberPhone());
	            String payMoney=ordr/10000+"";
	            pay.setMoney(payMoney);
	            smsUtil.sendPayMsg(pay);
	        
	        logger.info("=================预订订单短信完成================================");
	        //获取订单号和ID
	    	return ResultUtil.getResult(RespCode.Code.SUCCESS,orderUnline);
		}
        
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//失败回滚
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
		return result;
	
		
	}
	/**
	 * 商户获取交易列表
	 */
	@Override
	public Result getMerchantTradOrder(PhMemberOrderUnlineDTO merchant,int page, int pageSize) {
		logger.info("查询交易订单参数：PhMemberOrderUnline = {} ", JSON.toJSONString(merchant));
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		if(page==0){
			page=1;
		}
		int index=(page-1)*pageSize;
		//获取订单列表
		RowBounds rowBounds=new RowBounds(index,pageSize);
		List<PhMemberOrderUnlineDTO> tradOrder=tradOrderMapper.getMerchantTradOrder(merchant,rowBounds);
		//处理订单金额
		for(int i=0;i<tradOrder.size();i++){
			tradOrder.get(i).setOrderMoney(Double.parseDouble(DoubleUtils.formatRound(tradOrder.get(i).getOrderMoney()/10000.00, 2)));
			if(tradOrder.get(i).getPayType()!=null){
				//转换支付类型
				if(tradOrder.get(i).getPayType()==8){
					tradOrder.get(i).setPayTypeString("快捷支付");
				}else if(tradOrder.get(i).getPayType()==9){
					tradOrder.get(i).setPayTypeString("远程支付");
				}else if(tradOrder.get(i).getPayType()==0){
					tradOrder.get(i).setPayTypeString("现金支付");
				}
			}
			
		}
		if(tradOrder==null||tradOrder.isEmpty()){
			return ResultUtil.getResult(RespCode.Code.NO_ORDER);
		}
		result=ResultUtil.getResult(RespCode.Code.SUCCESS,tradOrder);
		
		logger.info("查看订单返回数据 result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 修改订单状态
	 */
	@Override
	@Transactional
	public Result updateOrder(Long id, Long createrId, byte payType,Long orderMoney) {
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		try {
			 //获取订单号
			 PhMemberOrderUnline unline= tradOrderMapper.getOrderNo(id);
			  //分润
			  iUnlineOrderProfitService.OrderProfit(unline.getOrderNo());
			  //***********更新订单状态**************//
			  PhMemberOrderUnline memberOrderUnline = new PhMemberOrderUnline();
			  Date date=new Date();
			  memberOrderUnline.setId(id);
			  memberOrderUnline.setPayTime(date);
			  memberOrderUnline.setStatus((byte)2);//已付款
			  memberOrderUnline.setUpdateTime(date);
			  memberOrderUnline.setUpdaterId(createrId);
			  if(payType==2){
				  memberOrderUnline.setDealCode("ALIPAY");	//维护到第三方字段
			  }
		      tradOrderMapper.updateOrder(memberOrderUnline);
		      //********************************修改商户余额****************//
		  	// 修改商户余额
		      Double merchantMoney=orderMoney*0.85;//订单金额的85%
		      HashMap<String,Object> map=new HashMap<String,Object>();
		      map.put("merchantMoney", merchantMoney);
		      map.put("merchantId", unline.getMerchantId());
		      map.put("orderNo", unline.getOrderNo());
		      tradOrderMapper.updateMerchantMoney(map);
			// 增加商户余额详情
		      tradOrderMapper.insertMerchantMoney(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
			result = ResultUtil.getResult(RespCode.Code.FAIL);
		}
		 
		return result;
			
		
	}
	/**
	 * 回掉失败更新订单状态
	 */
	@Override
	@Transactional
	public Result updateOrderForFail(byte payType, Long id, Long createrId, Long orderMoney){
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		try {
			  //***********更新订单状态**************//
			  PhMemberOrderUnline memberOrderUnline = new PhMemberOrderUnline();
			  Date date=new Date();
			  memberOrderUnline.setId(id);
			  memberOrderUnline.setPayTime(date);
			  memberOrderUnline.setUpdateTime(date);
			  memberOrderUnline.setUpdaterId(createrId);
			  if(payType==2){
				  memberOrderUnline.setDealCode("ALIPAY");	//维护到第三方字段
			  }
		      tradOrderMapper.updateOrderForFail(memberOrderUnline);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
			result = ResultUtil.getResult(RespCode.Code.FAIL);
		}
		 
		return result;
			
		
	}
	/**
	 * 验证会员是否存在  返回商户账户余额
	 */
	@Override
	public Result testAddTradOrder(String memberPhone, Long merchantId) {
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		//验证会员是否存在
		Member member=tradOrderMapper.getMemberByPhone(memberPhone);
		if(member!=null){
			//返回账户余额
			logger.info("返回账户余额商户ID ", JSON.toJSONString(merchantId));
			Long score=tradOrderMapper.getMerchantScoreById(merchantId);
			logger.info("score ", JSON.toJSONString(score));
			Map<String, Object> map=new HashMap<String, Object>();
			//查询该会员是否有线下订单
			List<Long> isProfit =tradOrderMapper.getOrderUnline(member.getId());

			/*返回绑定人是否存在，以及绑定人电话*/
			if(isProfit.size()==0){   //如果没有的话  不返回分享人电话
				map.put("memberId", member.getId());
				map.put("score", score);
				map.put("proTelPhone",null);
				result=ResultUtil.getResult(RespCode.Code.SUCCESS,map);
			}else{
			//if(score!=null){
				map.put("memberId", member.getId());
				map.put("score", score);
				map.put("proTelPhone",member.getTelPhone());
				//map.put("score", DoubleUtils.formatRound(score,2));
				result=ResultUtil.getResult(RespCode.Code.SUCCESS,map);
			//}
			}
		}else{
			result=ResultUtil.getResult(RespCode.Code.NO_MERMBER);
		}
		return result;
	}

	/**
	 * 验证绑定人是否为平台用户
	 */
	@Override
	public Result testMemberPro(String memberPhone) {
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		//验证绑定人是否存在
		Member member=tradOrderMapper.getMemberByPhone(memberPhone);
		if(member==null){
			result=ResultUtil.getResult(RespCode.Code.IS_NOTMEMBER);
		}
		return result;
	}
}
