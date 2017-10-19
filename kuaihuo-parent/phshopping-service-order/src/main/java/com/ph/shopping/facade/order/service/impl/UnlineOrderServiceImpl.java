package com.ph.shopping.facade.order.service.impl;

import cm.ph.shopping.facade.order.constant.*;
import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.exception.OrderException;
import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
import cm.ph.shopping.facade.order.service.IUnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineDetailVO;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lorne.tx.annotation.TxTransaction;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.*;
import com.ph.shopping.common.core.dto.PuhuiScoreDTO;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.PuhuiUtil;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.string.StringHelper;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.IOrderUnlineMapper;
import com.ph.shopping.facade.mapper.TradOrderMapper;
import com.ph.shopping.facade.member.dto.MemberAddDTO;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.dto.MemberUpdateDTO;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.JPushUserDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.message.PushMessageEnum;
import com.ph.shopping.facade.member.service.IMemberCardService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;
import com.ph.shopping.facade.member.vo.MessageVO;
import com.ph.shopping.facade.pay.dto.AlipayRefundDTO;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.service.IBalanceService;
import com.ph.shopping.facade.pay.service.ICashService;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;
import com.ph.shopping.facade.profit.service.IUnlineOrderProfitService;
import com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.vo.BalanceVO;
import com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


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
public class UnlineOrderServiceImpl implements IUnlineOrderService {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IOrderUnlineMapper iOrderUnlineMapper;

	@Autowired
	private TradOrderMapper tradOrderMapper;

	@Reference(version="1.0.0")
	IUserBalanceService iUserBalance;

	@Reference(version="1.0.0")
	IMerchantService iMerchantService;

	@Reference(version="1.0.0")
	IScoreService iScoreService;

	@Reference(version = "1.0.0")
	IMemberService iMemberService;

	@Reference(version = "1.0.0")
	IMemberCardService iMemberCardService;

	@Reference(version = "1.0.0")
	ICashService iCashService;

	@Reference(version = "1.0.0")
	IMessageService iMessageService;

	@Reference(version = "1.0.0")
	IManageBankCardService manageBankCardService;

	@Reference(version = "1.0.0")
	IBalanceService balanceService;

	@Reference(version = "1.0.0")
	IUnLineSettleService iUnLineSettleService;
	@Reference(version = "1.0.0")
	ICashService cashService;
	@Reference(version = "1.0.0")
	IUnlineOrderProfitService iUnlineOrderProfitService;
	@Autowired
	private ISmsDataService smsDataService;

	@Autowired
	private PuhuiUtil puhuiUtil;
	
	/**
	 *线下订单List
	 */
	@Override
	public Result getUnLineOrderVoList(PhMemberOrderUnlineDTO phMemberOrderUnlineDTO, PageBean pagebean)  {
		logger.info( "线下订单查询接口入参phMemberOrderUnlineDTO：{}",JSON.toJSONString( phMemberOrderUnlineDTO ) );
		if (pagebean != null){
			pagebean.setPageSize(pagebean.getPageSize() == 0 ? PageConstant.pageSize : pagebean.getPageSize());
			pagebean.setPageNum(pagebean.getPageNum() == 0 ? PageConstant.pageNum : pagebean.getPageNum());
			PageHelper.startPage(pagebean.getPageNum(), pagebean.getPageSize());
		}
		if (!(StringUtils.isEmpty( phMemberOrderUnlineDTO.getStarTime() )&& StringUtils.isEmpty( phMemberOrderUnlineDTO.getEndTime() ))){
			if (phMemberOrderUnlineDTO.getStarTime().equals( phMemberOrderUnlineDTO.getEndTime() )){
				phMemberOrderUnlineDTO.setStarTime( phMemberOrderUnlineDTO.getStarTime()+" 00:00:00" );
				phMemberOrderUnlineDTO.setEndTime( phMemberOrderUnlineDTO.getEndTime()+" 23:59:59" );
			}
		}
		List<PhMemberOrderUnlineVO> unLineOrderVoList = iOrderUnlineMapper.getUnlineOrderVoList(phMemberOrderUnlineDTO);
		PageInfo<PhMemberOrderUnlineVO>pageInfo=new PageInfo<PhMemberOrderUnlineVO>(unLineOrderVoList);
		for(PhMemberOrderUnlineVO m : pageInfo.getList()){
			m.setOrderMoney(m.getOrderMoney() == null ? 0 : m.getOrderMoney());
			m.setServiceCost(m.getServiceCost() == null ? 0 : m.getServiceCost());
		}
    	return ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
	}
	/**
	 * @author: 张霞
	 * @description：
	 * @createDate: 9:22 2017/6/8
	 * @param addMemberOrderUnlineDTO
	 * @param payFlage
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	@Override
	@TxTransaction
	@Transactional
	public Result addUnlineOrder(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO,boolean payFlage) throws Exception {
		Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
		//try{
			logger.info( "添加线下订单入参参数addMemberOrderUnlineDTO：{}",JSON.toJSONString( addMemberOrderUnlineDTO ) );
			result = encapsulationUnlineOrder(addMemberOrderUnlineDTO);
			if (RespCode.Code.SUCCESS.getCode().equals( result.getCode() )){
				PhMemberOrderUnline phMemberOrderUnline = (PhMemberOrderUnline) result.getData();
				phMemberOrderUnline.setCreateTime(new Date());//创建时间
				phMemberOrderUnline.setBarcodeMark( addMemberOrderUnlineDTO.getBarcodeMark() );//条形码标识(会员扫码支付时的条形码)
				if (payFlage){
					phMemberOrderUnline.setPayType( addMemberOrderUnlineDTO.getPayType() );
					if(phMemberOrderUnline.getPayType() == PayTypeEnum.PAY_TYPE_CASH.getPayType()){
						//现金支付余额校验-star
						logger.info(PayTypeEnum.PAY_TYPE_CASH.getDesc());
						phMemberOrderUnline.setStatus( OrderUnlineStatusEnum.STATUS_DONE_ORDER.getStatus() );//订单状态完成
						
						String pwd = MD5.getMD5Str(addMemberOrderUnlineDTO.getPayPassWord()); //密码
						Long merchantId=phMemberOrderUnline.getMerchantId();	//商户id
						Long serviceCost=phMemberOrderUnline.getServiceCost();
						String orderNo=phMemberOrderUnline.getOrderNo();		//订单号
						Result results=iUserBalance.getAndUpdateBalance(pwd,merchantId,serviceCost,orderNo); //获取账户余额，更新余额
						
						//判断该商户的账户余额是否存在
						BalanceVO balanceVO = new BalanceVO();
////						Result userBalance2 = iUserBalance.getUserBalance(phMemberOrderUnline.getMerchantId());
						balanceVO = (BalanceVO) results.getData();
						if(!RespCode.Code.SUCCESS.getCode().equals(results.getCode())){//无此账户
							return results;
						}
						//判断余额是否足够
						if(balanceVO.getScore1() + 10000000l < phMemberOrderUnline.getServiceCost()){//余额不足
							return  ResultUtil.getResult(OrderResultEnum.NOENOUGH_MONEY);
						}
						//判断商户密码
						if(balanceVO.getPayPwd()==null){
							logger.error("获取商户支付密码异常");
							return  ResultUtil.getResult(OrderResultEnum.PAY_PWD_EMPTY);
						}
						if(!balanceVO.getPayPwd().equals( MD5.getMD5Str(addMemberOrderUnlineDTO.getPayPassWord()))){
							logger.error("商户密码不对");
							return  ResultUtil.getResult(OrderResultEnum.PAY_PASSWORD_ERROR);
						}
						//现金余额支付     快火10-14 需求   维护用户分享人关系
						//先查询分享人是否存在
						Map<String,Object> map=new HashMap<String,Object>();
						//返回该会员对应的分享人的电话
						Member member= tradOrderMapper.getMemberByPhone(addMemberOrderUnlineDTO.getMemberTelphone());
						//查询该会员是否有线下订单
						List<Long> isProfit =tradOrderMapper.getOrderUnline(member.getId());
						//查询分享人的信息
						Member pro=iOrderUnlineMapper.getMemberProInfo(addMemberOrderUnlineDTO.getProTelPhone());
//						if(member!=null&&member.getTelPhone()!=addMemberOrderUnlineDTO.getProTelPhone()){//库中的分享人电话与传过来的电话不相同
						if(member!=null&&isProfit.size()==0&&member.getTelPhone()==null){
							//维护分享人关系以及信息  修改分享人ID
							int p=iOrderUnlineMapper.updateMemberPro(pro.getId(),member.getId());
							//维护中间表  ph_member_share_record
							map.put("telPhone",pro.getTelPhone());
							map.put("userId",pro.getId());
							map.put("toTelPhone",addMemberOrderUnlineDTO.getMemberTelphone());
							int p1=iOrderUnlineMapper.insertMemberRecord(map);
						}else if(member!=null&&member.getTelPhone()!=null&&isProfit.size()==0){
							//维护分享人关系以及信息  修改
							int p2=iOrderUnlineMapper.updateMemberPro(pro.getId(),member.getId());
							//维护中间表  ph_member_share_record
							map.put("telPhone",pro.getTelPhone());
							map.put("userId",pro.getId());
							map.put("toTelPhone",addMemberOrderUnlineDTO.getMemberTelphone());
							int p3=iOrderUnlineMapper.updateMemberRecord(map);
						}
						//现金支付余额校验-end
						//现金支付--更新账户余额信息
//						int updateUserBalanceRecord =iUserBalance.userBalanceTrade( phMemberOrderUnline.getMerchantId(),
//								TransCodeEnum.MERCHANT_UNLINE_ORDER,
//								phMemberOrderUnline.getServiceCost(),
//								phMemberOrderUnline.getOrderNo(),
//								0L, (byte) RoleEnum.MERCHANT.getCode() );
//						if(results.get !=1){
//							result.setMessage( "操作失败" );
//							logger.info("修改商户余额和添加记录流水失败");
//							throw new Exception("修改商户余额和添加记录流水失败");
//						}
					}else if (phMemberOrderUnline.getPayType()==PayTypeEnum.PAY_TYPE_SCORE.getPayType()){ //积分支付
						//验证积分是否足够--star
						logger.info(PayTypeEnum.PAY_TYPE_SCORE.getDesc());
						phMemberOrderUnline.setStatus( OrderUnlineStatusEnum.STATUS_DONE_ORDER.getStatus() );

						//判断会员的积分是否充足
						Long memberId=phMemberOrderUnline.getMemberId();	//会员id
						Long orderMoney=phMemberOrderUnline.getOrderMoney().longValue(); //订单金额
						String orderNo=phMemberOrderUnline.getOrderNo();		//订单号

						Result memberScore = iScoreService.getAndupdateMemberScore(memberId,orderMoney,orderNo); //获取并更新会员积分

//						MemberScoreVO2 memberScore = iScoreService.getMemberScore(phMemberOrderUnline.getMemberId());
						if(memberScore.getData() == null){
							logger.error("没有查询到用户的积分");
							return  ResultUtil.getResult(OrderResultEnum.NO_MEMBERSCORCE);
						}
						if(((MemberScoreVO2) memberScore.getData()).getEnablescore() < phMemberOrderUnline.getOrderMoney()){//积分不足
							return  ResultUtil.getResult(OrderResultEnum.NOENOUGH_MEMBER_SCORE);
						}
						//验证积分是否足够--end

						//这里跟新用户的可用积分减少  待会积分增加(改为待用积分不增加了 积分模块统一处理反积分)
//						long updateMemberScore = iScoreService.memberScoreTrade( phMemberOrderUnline.getMemberId(),
//								TransCodeEnum.MEMBER_UNLINE_ORDER,phMemberOrderUnline.getOrderMoney().longValue(),phMemberOrderUnline.getOrderNo(),0);
//						if (updateMemberScore<0){
//							result.setMessage( "操作失败" );
//							logger.info("更新会员积分和记入总流水失败");
//							throw new Exception("更新会员积分和记入总流水失败");
//						}
					}
					phMemberOrderUnline.setDoneTime( new Date(  ) );//完成时间
					phMemberOrderUnline.setIsProfit((byte)0);//未分润  分润作业扫描状态为0则进行分润
					phMemberOrderUnline.setPayTime(new Date()); //支付时间
					phMemberOrderUnline.setProfitTime(new Date());//分润时间
					phMemberOrderUnline.setSettleTime( new Date(  ) );//结算时间
					phMemberOrderUnline.setType(0);//默认  天天返
					phMemberOrderUnline.setIsSettle( OrderIsSettleEnum.IS_SETTLE.getIsSettle());//是否结算（0=未结算；1=结算）//变更需求，立马结算
					phMemberOrderUnline.setIsBackScore( OrderIsBackScoreEnum.IS_BACK_SCORE_NOT.getIsBackScore());//是否已返积分（0=未返；1=已返）//变更需求，立马返还待用积分
					
					logger.info( "创建线下订单内容phMemberOrderUnline={}",JSON.toJSONString( phMemberOrderUnline ) );
					iOrderUnlineMapper.insertUseGeneratedKeys(phMemberOrderUnline);//创建订单
					//所有的执行都搞定 给用户来个消息推送（只针对已使用了app的用户进行推送）
					/*final Long memberId = phMemberOrderUnline.getMemberId();
					String equipmentId = getEquipmentId(memberId);
					logger.info("创建线下订单完成开始发送推送消息，会员ID memberId = "+memberId+" 推送设备ID equipmentId = " + equipmentId);
					if(org.apache.commons.lang3.StringUtils.isNotBlank(equipmentId)){
						pushMessage(phMemberOrderUnline.getMemberId(), phMemberOrderUnline.getId(), equipmentId, phMemberOrderUnline.getMerchantName());
					}
					//立即返还待用积分
					logger.info( "线下订单创建后，返还待用积分--star" );
					iScoreService.memberScoreTrade( memberId, TransCodeEnum.MEMBER_RETURN_STANDBYSCORE,
							phMemberOrderUnline.getOrderMoney().longValue(),phMemberOrderUnline.getOrderNo(),0
					);
					logger.info( "线下订单创建后，返还待用积分--end" );
					// TODO:立即返可用积分（返给会员推荐人）
					Member member = getMemberInfoById(memberId);
					if(null == member){
						throw new RuntimeException("根据会员ID memberId = "+memberId+"没有获取到会员信息");
					}
					// 得到推荐人ID
					final Long shareMemberId = member.getPromoterId();
					if (null != shareMemberId) {
						logger.info("线下订单创建后，返可用积分--start");
						BigDecimal shareMoney = MoneyTransUtil
								.formatByMultiply(phMemberOrderUnline.getOrderMoney().longValue(), 0.002);
						// 给推荐人返可用积分
						iScoreService.memberScoreTrade(shareMemberId, TransCodeEnum.MEMBER_RETURN_ENABLESCORE,
								shareMoney.longValue(), phMemberOrderUnline.getOrderNo(), 0);
						logger.info("线下订单创建后，返可用积分--end");
						// 设置奖励积分并记录奖励积分流水
						iScoreService.memberScoreTrade(shareMemberId, TransCodeEnum.RECOMMEND_MEMBER_UNLINE_ORDER_REWARD_SCORE,
								shareMoney.longValue(), phMemberOrderUnline.getOrderNo(), 0);
					}*/
					//订单进行结算
//					Result result1 = settleOrderNow( phMemberOrderUnline );
//					if (result1.isSuccess()){
//						logger.info( "订单号：{}，立即结算成功",phMemberOrderUnline.getOrderNo() );
//					}
					//发送短信--star
					//查询会员积分
				/*	MemberScoreVO2 msv = iScoreService.getMemberScore(memberId);
					//给会员发送短信
					SmsSendData ssd=new SmsSendData();
					ssd.setMobile(phMemberOrderUnline.getMemberPhone());
					ssd.setType(SmsCodeType.USER_CONSUME_REMIND);
					ssd.setSourceEnum(SmsSourceEnum.MEMBER);
					//构造短信模板
					ConsumeRemindModelData crm=new ConsumeRemindModelData();
					DateTime dt=new DateTime();
					crm.setDay(String.valueOf(dt.getDayOfMonth()));
					crm.setMonth(String.valueOf(dt.getMonthOfYear()));
					crm.setYear(String.valueOf(dt.getYear()));
					crm.setOrderNo(phMemberOrderUnline.getOrderNo());
					//计算订单金额
					BigDecimal bd=new BigDecimal(phMemberOrderUnline.getOrderMoney());
					String orderMoney=MoneyTransUtil.formatBigDecimalByDivisFiveStr(bd);
//					String orderMoney=bd.divide(new BigDecimal(10000)).setScale(5,BigDecimal.ROUND_HALF_UP).toString();
					crm.setMoney(orderMoney);
					crm.setSmsAcceptType(SmsSourceEnum.MEMBER.getMsg());
					BigDecimal stand=new BigDecimal(msv.getStandbyscore());
//					BigDecimal standScore=stand.divide(new BigDecimal(10000).setScale(5,BigDecimal.ROUND_HALF_UP));
					crm.setStandbyScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(stand));

					BigDecimal enable=new BigDecimal(msv.getEnablescore());
//					BigDecimal enableScore=enable.divide(new BigDecimal(10000).setScale(5,BigDecimal.ROUND_HALF_UP));
					crm.setUsableScore(MoneyTransUtil.formatBigDecimalByDivisFiveStr(enable));
					crm.setSubsidyScore(orderMoney);
					ssd.setModelData(crm);

					Result resultMSG=smsDataService.sendSmsByCustomModelData(ssd);
					logger.info("会员线下订单消费短信发送结果："+ JSON.toJSONString(resultMSG));*/
					//发送短信--end
				}else {
					phMemberOrderUnline.setStatus( OrderUnlineStatusEnum.STATUS_TODO_PAY.getStatus() );//待付款
					//用于第三方支付
					iOrderUnlineMapper.insertUseGeneratedKeys(phMemberOrderUnline);//创建订单
				}
				//进行分润   调用分润接口
				//iUnlineOrderProfitService.OrderProfit(phMemberOrderUnline.getOrderNo());
				Map<String,String> resultData = new HashMap<>(  );
				resultData.put( "id",phMemberOrderUnline.getId().toString() );
				resultData.put( "orderNo",phMemberOrderUnline.getOrderNo() );
				result.setData( resultData );
			}
		/*}catch (Exception e){
			logger.info( "添加线下订单异常，e={}",e );
			result.setMessage( OrderExceptionEnum.ADD_UNLINEORDER_EXCEPTION.getMsg() );
			throw new OrderException(OrderExceptionEnum.ADD_UNLINEORDER_EXCEPTION);
		}*/
		logger.info( "创建线下订单返回结果result：{}",JSON.toJSONString( result ) );
		return result ;
	}
	/**
	 * 
	 * @Title: getEquipmentId   
	 * @Description: 根据会员ID得到设备ID  
	 * @param: @param memberId
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private String getEquipmentId(Long memberId){
		Member member = getMemberInfoById(memberId);
		if(null != member){
			return member.getEquipmentId();
		}
		return null;
	}
	
	private Member getMemberInfoById(Long memberId) {
		Result result = iMemberService.getMemberInfoById(memberId);
		logger.info("根据会员ID 查询会员信息 result：{}", JSON.toJSONString(result));
		if (result.isSuccess()) {
			Object data = result.getData();
			if (data instanceof Member) {
				return (Member) data;
			}
		}
		return null;
	}
	/**
	 * @author: 张霞
	 * @description：新增需求，立即进行订单结算
	 * @createDate: 9:57 2017/8/1
	 * @param phMemberOrderUnline
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	private Result settleOrderNow(PhMemberOrderUnline phMemberOrderUnline) throws Exception{

		//立即结算--star
		logger.info( "线下订单创建后，立即结算--start" );
		UnLineSettleOrderVO unLineSettleOrderVO = new UnLineSettleOrderVO();
		if (PayTypeEnum.PAY_TYPE_CASH.getPayType()==phMemberOrderUnline.getPayType()){
			//余额支付时结算金额为0
			unLineSettleOrderVO.setSettleMoney( 0l );
		}else {
			//其他支付时结算金额为订单金额-服务费金额
			unLineSettleOrderVO.setSettleMoney( phMemberOrderUnline.getOrderMoney()-phMemberOrderUnline.getServiceCost() );
		}
		unLineSettleOrderVO.setId( phMemberOrderUnline.getId() );
		unLineSettleOrderVO.setMerchantId( phMemberOrderUnline.getMerchantId() );
		unLineSettleOrderVO.setOrderNo( phMemberOrderUnline.getOrderNo() );
		unLineSettleOrderVO.setPayType(phMemberOrderUnline.getPayType());
		Result result = iUnLineSettleService.doUnLineSettleNow( unLineSettleOrderVO );
		logger.info( "线下订单创建后，立即结算--end" );
		//立即结算--end
		return result;
	}
	/**
	 * 
	 * @Title: pushMessage   
	 * @Description: 线下订单消息推送 
	 * @param: @param memberId
	 * @param: @param orderId
	 * @param: @param equipmentId
	 * @param: @param mercantName      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private void pushMessage(Long memberId, Long orderId, String equipmentId, String mercantName) {
		JPushSendDTO dto = null;
		PhMemberOrderUnline orderUnline = iOrderUnlineMapper.selectByPrimaryKey(orderId);
		if (null == orderUnline) {
			logger.warn("根据订单ID = " + orderId + " 没有得到订单消息 ");
			return;
		}
		// 得到消息模板ID
		final Long templateId = MessageTemplateEnum.UNLINE_ORDER_PAY.getTemplateId();
		// 得到消息模板信息
		MessageVO messageInfo = getMessageInfo(templateId);
		if (null == messageInfo) {
			logger.warn("根据模板ID = " + templateId + " 没有得到消息模板消息 ");
			return;
		}
		Map<String, String> map = ContainerUtil.map();
		map.put("merchantName", mercantName);
		String money = MoneyTransUtil
				.formatObj(new BigDecimal(orderUnline.getOrderMoney()).divide(new BigDecimal(10000)));
		map.put("money", money);
		map.put("integral", money);
		map.put("brandSlogan", messageInfo.getBrandSlogan());
		map.put("trustSlogan", messageInfo.getTrustSlogan());
		final String messageContent = StringHelper.renderString(messageInfo.getMessageTemplate(), map);
		dto = new JPushSendDTO();
		dto.setCreaterId(memberId);
		dto.setMessageContent(messageContent);
		dto.setSendType(PushMessageEnum.ALL.getCode());
		dto.setTemplateId(templateId);
		List<JPushUserDTO> users = ContainerUtil.aList();
		JPushUserDTO jd = new JPushUserDTO();
		jd.setEquipmentId(equipmentId);
		jd.setUserId(memberId);
		jd.setUserType(PushMessageEnum.MEMBER.getCode());
		users.add(jd);
		dto.setUsers(users);
		dto.setPushType(PushTypeEnum.PEER_TO_PEER);
		logger.info("线下订单消息推送参数 JPushSendDTO = {}", JSON.toJSONString(dto));
		Result pushResult = iMessageService.pushMessage(dto);
		logger.info("线下订单消息推送返回数据 pushResult = {}", JSON.toJSONString(pushResult));
	}
	
	private MessageVO getMessageInfo(Long templateId) {
		Result result = iMessageService.getMessageSendInfoByTmplateId(templateId);
		logger.info("查询消息模板返回数据：Result = {} ", JSON.toJSONString(result));
		if (result.isSuccess()) {
			Object data = result.getData();
			if (data instanceof MessageVO) {
				return (MessageVO) data;
			}
		}
		return null;
	}
	/**
	 * @author: 张霞
	 * @description：更新线下订单状态
	 * @createDate: 9:49 2017/6/5
	 * @param phMemberOrderUnline
	 * @return: int
	 * @version: 2.1
	 */
	@Override
	public int updateUnlineOrder(PhMemberOrderUnline phMemberOrderUnline){

		return iOrderUnlineMapper.updateUnlineOrder(phMemberOrderUnline);
	}

	/**
	 * @author: 张霞
	 * @description：通过id获取线下订单
	 * @createDate: 9:50 2017/6/5
	 * @param id
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	@Override
	public Result getPhMemberOrderUnlineById(Long id) {
		return getUnlineOrderById(id);
	}

	/**
	 * @author: 张霞
	 * @description：取消线下订单
	 * @createDate: 9:52 2017/6/5
	 * @param cancelUnlineOrderDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	@Override
	@Transactional
	public Result cancelUnlineOrder(CancelUnlineOrderDTO cancelUnlineOrderDTO) {
		Result result = ResultUtil.getResult( RespCode.Code.SUCCESS );
		try {
			result = getUnlineOrderById( cancelUnlineOrderDTO.getId() );
			if (RespCode.Code.SUCCESS.getCode().equals( result.getCode() )) {
				//获取要取消的订单数据
				PhMemberOrderUnline phMemberOrderUnline = (PhMemberOrderUnline) result.getData();
				if (OrderUnlineStatusEnum.STATUS_DOING_PAY.getStatus() == phMemberOrderUnline.getStatus()
						|| OrderIsSettleEnum.IS_SETTLE.getIsSettle() == phMemberOrderUnline.getIsSettle()
						|| OrderIsProfitEnum.IS_PROFIT.getIsProfit() == phMemberOrderUnline.getIsProfit()
						|| OrderIsBackScoreEnum.IS_BACK_SCORE.getIsBackScore() == phMemberOrderUnline.getIsBackScore()) {
					//状态为付款中不能取消订单
					result = ResultUtil.getResult( OrderResultEnum.CANCEL_ORDER_UNABLE );
					return result;
				} else {
					//处理订单--start
					phMemberOrderUnline.setUpdaterId( cancelUnlineOrderDTO.getUpdaterId() );//申请取消人id
					phMemberOrderUnline.setCancelTime( new Date() );//取消时间
					phMemberOrderUnline.setCancelReason( cancelUnlineOrderDTO.getCancelReason() );//取消原因
					phMemberOrderUnline.setStatus( OrderUnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus() );//更新线下订单状态
					phMemberOrderUnline.setUpdateTime( new Date() );//更新时间
					int update = iOrderUnlineMapper.updateByPrimaryKeySelective( phMemberOrderUnline );
					if (update != 1) {
						logger.info( "取消订单时，更新线下订单状态失败" );
						result = ResultUtil.getResult( OrderResultEnum.CANCEL_ORDER_FAIL );
						return result;
					}
					//处理订单--end
					//退还金额--star
					byte payType = phMemberOrderUnline.getPayType();
					if (PayTypeEnum.PAY_TYPE_CASH.getPayType() == payType) {
						//账户现金余额支付--退还到商户账户中
						try {
							iUserBalance.userBalanceTrade( phMemberOrderUnline.getMerchantId(),
									TransCodeEnum.MERCHANT_UNLINE_ORDER_CANCEL,
									phMemberOrderUnline.getServiceCost(),
									phMemberOrderUnline.getOrderNo(),
									0L, (byte) RoleEnum.MERCHANT.getCode() );
						} catch (Exception e) {
							logger.info( "修改商户余额和添加记录流水失败" );
							throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
						}

					} else if (PayTypeEnum.PAY_TYPE_SCORE.getPayType() == payType) {
						//积分支付--退还到会员积分中
						try {
							iScoreService.memberScoreTrade( phMemberOrderUnline.getMemberId(),
									TransCodeEnum.MEMBER_UNLINE_ORDER_CANCEL, phMemberOrderUnline.getOrderMoney().longValue(), phMemberOrderUnline.getOrderNo() ,0);
						} catch (Exception e) {
							logger.info( "更新会员积分和记入总流水失败" );
							throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
						}
					}else if(PayTypeEnum.PAY_TYPE_ALIPAY.getPayType()==payType){
						//支付宝支付--退还到支付宝账号中
						AlipayRefundDTO ard = new AlipayRefundDTO();
						ard.setAmount( String.valueOf( MoneyTransUtil.transDivisDouble( phMemberOrderUnline.getOrderMoney() ) ) );//订单金额
						ard.setReason( phMemberOrderUnline.getCancelReason() );//申请原因
						ard.setOut_trade_no( phMemberOrderUnline.getOrderNo() );//交易订单号
						result = iCashService.alipayRefund( ard );//退还支付宝结果
						if (!result.isSuccess()){
							logger.info( "退还到支付宝账户失败" );
							throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
						}
					}else if(PayTypeEnum.PAY_TYPE_WEIXINPAY.getPayType()==payType){
						//微信支付-- 退还到微信账号中
							logger.info( "退还到微信账号中 有待完善" );
							throw new OrderException( OrderExceptionEnum.UPDATE_ORDER_EXCEPTION );
					}else if (PayTypeEnum.PAY_TYPE_WEIXINPAY.getPayType()==payType){
						//易联支付--需要用银联代付进行退款
						//查询银行卡信息
						ManageBankCardInfo manageBankCardInfo = new ManageBankCardInfo();
						manageBankCardInfo.setUserId(phMemberOrderUnline.getMerchantId());
						Result bankCard = manageBankCardService.getBindCardInfo(manageBankCardInfo);
						logger.info("查询银行卡信息返回值，bankCard={}", JSON.toJSONString(bankCard));
						if (null !=bankCard && bankCard.isSuccess()) {
							ManageBankCardInfoVO vo = (ManageBankCardInfoVO) bankCard.getData();
							DefrayDTO defrayDTO = new DefrayDTO(phMemberOrderUnline.getId().toString(),UniqueUtils.getBathNo(), vo.getCardNo(), vo.getOwnerName(),
									MoneyTransUtil.transDivis(new BigDecimal( phMemberOrderUnline.getOrderMoney().longValue() )).toString(), vo.getBankName(),
									phMemberOrderUnline.getOrderNo());
							logger.info("调用银行卡归还金额接口,defrayDTO={}", JSON.toJSONString(defrayDTO));
							result = cashService.defray(defrayDTO);
							logger.info("调用银行卡归还金额接口,result={}", JSON.toJSONString(result));
						} else {
							throw new RuntimeException("查询银行卡信息失败");
						}
					}
				}
			}else {
				result = ResultUtil.getResult( OrderResultEnum.NO_MEMBER_UNLINEORDER_DATA );
			}
		} catch (Exception e) {
			logger.info( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION.getMsg() );
			throw new OrderException( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION );
		}
		return result;
	}

	/**
	 * @author: 张霞
	 * @description：线下订单获取订单详情
	 * @createDate: 11:00 2017/6/12
	 * @param queryMemberOrderUnlineDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	@Override
	public Result getUnlineOrderDetail(QueryMemberOrderUnlineDTO queryMemberOrderUnlineDTO) {
		Result result;
		PhMemberOrderUnline phMemberOrderUnline = iOrderUnlineMapper.selectByPrimaryKey( queryMemberOrderUnlineDTO.getId() );
		if (phMemberOrderUnline==null){
			result = ResultUtil.getResult( OrderResultEnum.SELECT_NO_DATA );
		}else {
			PhMemberOrderUnlineDetailVO phMemberOrderUnlineDetailVO = new PhMemberOrderUnlineDetailVO();
			BeanUtils.copyProperties( phMemberOrderUnline,phMemberOrderUnlineDetailVO );
			phMemberOrderUnlineDetailVO.setOrderMoney( phMemberOrderUnline.getOrderMoney()==null?0L:phMemberOrderUnline.getOrderMoney().doubleValue() );
			phMemberOrderUnlineDetailVO.setServiceCost( phMemberOrderUnline.getServiceCost()==null?0L:phMemberOrderUnline.getServiceCost().doubleValue() );
			phMemberOrderUnlineDetailVO.setStatus( Integer.valueOf( phMemberOrderUnline.getStatus() ) );
			if(phMemberOrderUnline.getPayType() != null ){
				phMemberOrderUnlineDetailVO.setPayType( Integer.valueOf( phMemberOrderUnline.getPayType() ) );
			}
			logger.info( "详情页面vo={}",JSON.toJSON( phMemberOrderUnlineDetailVO ) );
			result = ResultUtil.getResult( RespCode.Code.SUCCESS,phMemberOrderUnlineDetailVO );
		}
		return result;
	}

	/**
	 * @author: 张霞
	 * @description：封装线下订单公共基本数据
	 * @createDate: 11:59 2017/6/8
	 * @param addMemberOrderUnlineDTO
	 * @return: cm.ph.shopping.facade.order.entity.PhMemberOrderUnline
	 * @version: 2.1
	 */
	private Result encapsulationUnlineOrder(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO){
		PhMemberOrderUnline phMemberOrderUnline  = new PhMemberOrderUnline();
		//线下订单公共补充内容_star
//		phMemberOrderUnline.setOrderNo( OrderUtil.createOrderCode( OrderBizCode.MEMBER_UNLINEORDER));//生成订单号
		phMemberOrderUnline.setOrderNo("KHXXDD"+UtilDate.getOrderNum()+UtilDate.getThree());	//快火线下订单订单号
		phMemberOrderUnline.setMerchantId(addMemberOrderUnlineDTO.getMerchantId());//当前商户的id_正式环境
		phMemberOrderUnline.setCreaterId(addMemberOrderUnlineDTO.getCreaterId());//当前登录的id_正式环境
		//获取商户分润比率
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setUserId(addMemberOrderUnlineDTO.getMerchantId());//登录表中的id
		MerchantVO merchantVO = iMerchantService.getMerchantListById(merchantDTO);
		if (merchantVO==null){
			return ResultUtil.getResult( OrderResultEnum.NO_MERCHANT_DATA);
		}
		phMemberOrderUnline.setMerchantName( merchantVO.getMerchantName() );//商户名称
		phMemberOrderUnline.setMerchantPhone( merchantVO.getTelPhone() );//商户电话
		if(merchantVO.getBusinessProfitRatio() == null){
			return ResultUtil.getResult( OrderResultEnum.MERCHANT_PROFITRATIO_ERROE);
		}
		Double businessProfitRatio = merchantVO.getBusinessProfitRatio().doubleValue();//得到分润比率
		phMemberOrderUnline.setServiceCost(MoneyTransUtil.transMultiDouble(addMemberOrderUnlineDTO.getOrderMoney()*businessProfitRatio));//服务费
		phMemberOrderUnline.setOrderMoney(MoneyTransUtil.transMultiDouble(addMemberOrderUnlineDTO.getOrderMoney()));
		phMemberOrderUnline.setIsSettle( OrderIsSettleEnum.IS_SETTLE_NOT.getIsSettle());//是否结算（0=未结算；1=结算）
		phMemberOrderUnline.setIsBackScore( OrderIsBackScoreEnum.IS_BACK_SCORE_NOT.getIsBackScore());//是否已返积分（0=未返；1=已返）
		phMemberOrderUnline.setIsProfit( OrderIsProfitEnum.IS_PROFIT_NOT.getIsProfit() );//是否已分润（0=未分润；1=已分润）
//线下订单公共补充内容_end

		if (addMemberOrderUnlineDTO.getMemberId()==null){
			//通过手机号判断会员信息--star
			if (addMemberOrderUnlineDTO.getMemberTelphone()==null||"".equals( addMemberOrderUnlineDTO.getMemberTelphone() )){
				return ResultUtil.getResult( OrderResultEnum.ORDER_OPERAION_INVALID );
			}else {
				Result result = iMemberService.getMemberInfoByMobile( addMemberOrderUnlineDTO.getMemberTelphone() );
					if (result.isSuccess()){
						Member member = (Member) result.getData();
						phMemberOrderUnline.setMemberId(member.getId());//得到卡的会员id
						phMemberOrderUnline.setMemberPhone( member.getTelPhone() );//会员的电话号码
					}else {
						//创建会员
						MemberAddDTO memberAddDTO = new MemberAddDTO();
						memberAddDTO.setTelPhone( addMemberOrderUnlineDTO.getMemberTelphone() );
						memberAddDTO.setCreaterId( addMemberOrderUnlineDTO.getCreaterId() );
						Result creatResult = iMemberService.addMember( memberAddDTO );
						if (!creatResult.isSuccess()){
							result = new Result( false,"300","手机号不是会员,且创建会员失败:"+creatResult.getMessage() );
							return result;
						}else {
							Member m = (Member) creatResult.getData();
							phMemberOrderUnline.setMemberId( m.getId() );//创建的会员id
							phMemberOrderUnline.setMemberPhone( m.getTelPhone() );//创建的会员手机号
						}
					}
			}
			//通过手机号判断会员信息--end
//判断会员卡号相关信息_star
			MemberCardInfoDTO memberCardInfoDTO = new MemberCardInfoDTO();
			memberCardInfoDTO.setInnerCode(addMemberOrderUnlineDTO.getMemberCardNo());
			Result memberCardInfoByCode = iMemberCardService.judgeMemberCard(memberCardInfoDTO);
			logger.info(" Result ={}", JSON.toJSON(memberCardInfoByCode));
			if(memberCardInfoByCode.getCode().equals("200")){
				MemberCardInfoVO memberCardInfoVO = (MemberCardInfoVO)memberCardInfoByCode.getData();
				phMemberOrderUnline.setOuterCode( memberCardInfoVO.getOuterCode() );//会员卡的外码
			}
//判断会员卡号相关信息_end
		}else {
			//填写会员id
			Result resultMember = iMemberService.getMemberInfoById( addMemberOrderUnlineDTO.getMemberId() );
			if (resultMember.isSuccess()){
				Member member = (Member) resultMember.getData();
				phMemberOrderUnline.setMemberPhone( member.getTelPhone() );
			}else {
				logger.info( "无法获取会员电话信息，属于违规数据，memberId={}",addMemberOrderUnlineDTO.getMemberId() );
				return ResultUtil.getResult( OrderResultEnum.ORDER_OPERAION_INVALID );
			}
			phMemberOrderUnline.setMemberId( addMemberOrderUnlineDTO.getMemberId() );
		}
		return ResultUtil.getResult( RespCode.Code.SUCCESS,phMemberOrderUnline );
	}
	/**
	 * @author: 张霞
	 * @description：通过id获取线下订单
	 * @createDate: 14:46 2017/6/9
	 * @param id
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	private Result getUnlineOrderById(Long id){

		logger.info("根据Id查询线下订单信息  参数,id = : " + id);
		//1.验证id参数是否为空
		if (id==null) {
			return new Result(false, OrderResultEnum.SELECT_PARAM_NULL.getCode(), OrderResultEnum.SELECT_PARAM_NULL.getMsg());
		}
		//2.开始查询
		PhMemberOrderUnline phMemberOrderUnline = iOrderUnlineMapper.selectByPrimaryKey(id);
		//3.判断查询结果是否为空，返回相应数据
		if (phMemberOrderUnline != null ) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS,phMemberOrderUnline);
		} else {
			return ResultUtil.getResult(OrderResultEnum.NO_MEMBER_UNLINEORDER_DATA);
		}
	}
	
	@Override
	public Result getOrderSatusByBarcode(String barcodeMark) {
		logger.info("根据二维码标识得到订单状态参数： barcodeMark = " + barcodeMark);
		Integer status = iOrderUnlineMapper.selectOrderSatusByBarcode(barcodeMark);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, status);
	}
	
	@Override
	public Result getPhMemberOrderUnlineByOrderNo(String orderNo) {
		PhMemberOrderUnline record = new PhMemberOrderUnline();
		record = iOrderUnlineMapper.selectOne(record);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, record);
	}

	/**
	 * 天天返、刮刮乐
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	@Override
	@Transactional
	public Result rewardUnlineOrder(RewardUnlineOrderDTO rewardUnlineOrderDTO) throws Exception {
        logger.info("rewardUnlineOrder 入参:{}",JSON.toJSON(rewardUnlineOrderDTO));
		Long orderId = rewardUnlineOrderDTO.getId();
		if (Objects.isNull(orderId)){
            return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
		}
		PhMemberOrderUnline memberOrderUnline = iOrderUnlineMapper.selectByPrimaryKey(orderId);
		if (Objects.isNull(memberOrderUnline)) {
			return ResultUtil.setResult(false, "订单不存在");
		}
		if (!Objects.equals(rewardUnlineOrderDTO.getMemberId(), memberOrderUnline.getMemberId())) {
			return ResultUtil.setResult(false, "用户信息异常");
		}
		if (Objects.nonNull(memberOrderUnline.getType()) || Objects.nonNull(memberOrderUnline.getGuaMoney())){
            return ResultUtil.setResult(false,"重复操作");
		}
		//天天返
		if ("0".equals(rewardUnlineOrderDTO.getType())) {
			memberOrderUnline.setType(0);
			memberOrderUnline.setUpdateTime(new Date());
			iOrderUnlineMapper.updateByPrimaryKeySelective(memberOrderUnline);
			return ResultUtil.setResult(true, "天天返成功");
		}
		//刮红包
		if ("1".equals(rewardUnlineOrderDTO.getType())) {
			Member member = iMemberService.selectByPrimaryKey(memberOrderUnline.getMemberId());
			if (Objects.isNull(member)) {
				return ResultUtil.setResult(false, "用户不存在");
			}
			Long totalOrderMoney = iOrderUnlineMapper.selectTotalOrerMoneyByPhone(memberOrderUnline.getMemberId());
			//奖励金额
			BigDecimal actualRewardMoney;
			BigDecimal orderMoney = BigDecimal.valueOf(memberOrderUnline.getOrderMoney());
			if (orderMoney.compareTo(BigDecimal.valueOf(0)) < 0) {
				return ResultUtil.setResult(false, "订单金额异常");
			}
			//8% 订单最少返还金额
			BigDecimal rewardMinMoney = orderMoney.multiply(BigDecimal.valueOf(0.08));
			//10% 订单最大返还金额
			BigDecimal rewardMaxMoney = orderMoney.multiply(BigDecimal.valueOf(0.10));

			//是否首次消费
			if (Objects.equals(totalOrderMoney, 0L)) {
				//实际红包金额
				actualRewardMoney = randomDecimal(rewardMinMoney, rewardMaxMoney);
			} else {
				//刮奖资金池金额
				Long probMoney = Objects.isNull(member.getProbMoney()) ? 0L : member.getProbMoney();
				//最大金额= 最大红包+结余用户
				actualRewardMoney = randomDecimal(rewardMinMoney, rewardMaxMoney.add(BigDecimal.valueOf(probMoney)));
			}
			Long guaMoney = actualRewardMoney.divide(BigDecimal.valueOf(10000)).setScale(2,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(10000)).longValue();
			memberOrderUnline.setGuaMoney(guaMoney);
			memberOrderUnline.setUpdateTime(new Date());
			iOrderUnlineMapper.updateByPrimaryKeySelective(memberOrderUnline);
			return ResultUtil.getResult(RespCode.Code.SUCCESS, String.valueOf(BigDecimal.valueOf(guaMoney).divide(BigDecimal.valueOf(10000))));
		}
		return ResultUtil.setResult(false, "参数异常");
	}

	private  BigDecimal randomDecimal(final BigDecimal min, final BigDecimal max) throws Exception {
		if (max.compareTo(min)<0) {
			throw new Exception("min < max");
		}
		if (min.compareTo(max)==0) {
			return min;
		}
		return min.add((max.subtract(min)).multiply(BigDecimal.valueOf(new Random().nextDouble()))).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 刮刮乐确认
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	@Override
	@TxTransaction
	@Transactional
	public Result confirmRewardUnlineOrder(RewardUnlineOrderDTO rewardUnlineOrderDTO) throws Exception {
		logger.info("confirmRewardUnlineOrder 入参:{}",JSON.toJSON(rewardUnlineOrderDTO));

		Long orderId = rewardUnlineOrderDTO.getId();
		if (Objects.isNull(orderId)){
			return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
		}
		PhMemberOrderUnline memberOrderUnline = iOrderUnlineMapper.selectByPrimaryKey(orderId);
		if (Objects.isNull(memberOrderUnline)) {
			return ResultUtil.setResult(false, "订单不存在");
		}
		if (!Objects.equals(rewardUnlineOrderDTO.getMemberId(), memberOrderUnline.getMemberId())) {
			return ResultUtil.setResult(false, "用户信息异常");
		}
        BigDecimal guaMoney = Objects.isNull(memberOrderUnline.getGuaMoney())?BigDecimal.valueOf(0):BigDecimal.valueOf(memberOrderUnline.getGuaMoney());
		if (rewardUnlineOrderDTO.getMoney().compareTo(guaMoney)!=0){
			return ResultUtil.setResult(false, "订单信息异常");
		}
		if (Objects.nonNull(memberOrderUnline.getType())){
			return ResultUtil.setResult(false,"重复操作");
		}
		//刮红包
		if ("1".equals(rewardUnlineOrderDTO.getType())) {
			Member member = iMemberService.selectByPrimaryKey(memberOrderUnline.getMemberId());
			if (Objects.isNull(member)) {
				return ResultUtil.setResult(false, "用户不存在");
			}
			BigDecimal orderMoney = BigDecimal.valueOf(memberOrderUnline.getOrderMoney());
			//刮奖资金池金额
			Long probMoney = Objects.isNull(member.getProbMoney()) ? 0L : member.getProbMoney();
			//10% 订单最大返还金额
			BigDecimal rewardMaxMoney = orderMoney.multiply(BigDecimal.valueOf(0.10));
			//剩余金额
			member.setProbMoney(rewardMaxMoney.add(BigDecimal.valueOf(probMoney)).subtract(BigDecimal.valueOf(memberOrderUnline.getGuaMoney())).longValue());
			memberOrderUnline.setType(1);//刮红包
			iOrderUnlineMapper.updateByPrimaryKeySelective(memberOrderUnline);
			//更新用户信息
			MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();
			BeanUtils.copyProperties(member, memberUpdateDTO);
			memberUpdateDTO.setUpdaterId(member.getId());
			iMemberService.updateMember(memberUpdateDTO);
			//调用普惠返积分
			PuhuiScoreDTO puhuiScoreDTO = new PuhuiScoreDTO();
			puhuiScoreDTO.setOrderMoney(guaMoney);//刮红包金额
			puhuiScoreDTO.setOrderNo(memberOrderUnline.getOrderNo());
			puhuiScoreDTO.setSourceType(4);//刮红包
			puhuiScoreDTO.setToken(member.getTokenToMobile());
			boolean scoreResult = puhuiUtil.saveScore(puhuiScoreDTO);
			if (!scoreResult){
                logger.error("刮红包返积分失败:{}",JSON.toJSON(memberOrderUnline));
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		}
		return ResultUtil.setResult(false, "参数异常");
	}
}
