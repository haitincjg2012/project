package com.alqsoft.service.impl.payreturnmsg;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alqframework.result.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.service.cashreceivestation.CashReceiveStationService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.industrymoney.IndustryMoneyService;
import com.alqsoft.service.membermoney.MemberMoneyService;
import com.alqsoft.service.payreturnmsg.PayReturnMsgService;
import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrymoney.IndustryMoney;
import com.alqsoft.entity.membermoney.MemberMoney;



@Service
@Transactional
public class PayReturnMsgServiceImpl implements PayReturnMsgService {

	private static Log logger = LogFactory.getLog(PayReturnMsgServiceImpl.class);
	
	@Autowired
	private CashReceiveStationService cashReceiveStationService;
	
	@Autowired
	private MemberMoneyService memberMoneyService;
	
	@Autowired
	private IndustryMoneyService industryMoneyService;
	
	@Autowired
	private HunterService hunterService;
	
	@Autowired
	private IndustryAssociationService industryAssociationService;
	
	
	
	@Override
	public String verifySingNotifyForYPay(Map<String, String> map) {
		
		
		String orderNum =map.get("orderNum");
		String payStatus =map.get("payStatus");
		String moneyPay =map.get("money"); 
		
		//判断订单号是否为空
		if ("".equals(orderNum) || payStatus == null) {
			logger.info("回调方法执行中,订单号"+orderNum+",接收回调参数异常");
			return "statuserror";
		}
		logger.info("回调方法中返回的状态:"+payStatus+",回调方法第一步.订单号:"+orderNum);
		
		//根据订单号获取提现记录信息
		CashReceiveStation crs = new CashReceiveStation();
		try {
			Long id = cashReceiveStationService.getIDByMerSeqId(orderNum);//根据订单号获取记录id
			crs = cashReceiveStationService.getRowLock(id);//提现加锁
			if (crs==null) {
				logger.info("回调方法执行中,订单号" + orderNum + ",此订单未查询到");
				return  "statuserror";
			}
			if (crs.getStatus()==null) {
				logger.info("回调方法执行中,订单号" + orderNum + ",此订单未查询到");
				return  "statuserror";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("回调方法执行中,订单号"+orderNum+",此订单查询异常");
			return "statuserror";
		}
		
		//判断提现金额是否一致
		Integer moneyCash=crs.getMoney();
		Integer feeIncome = crs.getFeeIncome();
		
		if(Integer.parseInt(moneyPay) != (moneyCash.intValue()-feeIncome.intValue())){
			logger.info("提现金额不一致,订单号"+orderNum+",pay返回金额："+moneyPay+",记录表中提现金额："+moneyCash.toString());
			return "statuserror";
		}
		
		
		//判断订单状态是否异常
		Integer status = crs.getStatus();//订单状态
		logger.info("回调方法执行中,订单号"+orderNum+",此订单的状态:"+status);
		if (status!=1) {
			logger.info("回调方法执行中,订单号"+orderNum+",此订单的状态:"+status+"不在回调执行范围内,返回statuserror");
			return "statuserror";
		}
		
		//从记录表中获取订单角色
		Integer cssType=crs.getCssType();//  系统角色类型   1批发商 2行业协会
		//Map<String, Object> params = new HashMap<>();
		logger.info("交易结果处理方法中-处理交易结果,"+"异步回调返回状态"+payStatus+"流水号"+orderNum);
		try {
			if("2".equals(payStatus)){//成功
				System.out.println("交易成功");
				logger.info("交易成功    =========================");
				
				if(1==cssType){// cssType 系统角色类型   1批发商 2行业协会
					logger.info("提现成功业务逻辑处理开始,订单号："+crs.getMerSeqId()+",系统角色为批发商 类型："+cssType+",( 1批发商 2行业协会)");
					
						//1.修改提现记录状态
						crs.setStatus(2);//1已申请 2打款成功 3打款失败
						crs.setDescriber("打款成功");
						crs.setMoney(crs.getMoney());
						crs.setCssId(crs.getCssId());
						crs.setCssType(crs.getCssType());
						crs.setUpdateTime(new Date());
						crs.setApproveDate(new Date());
						CashReceiveStation saveAndModify = cashReceiveStationService.saveAndModify(crs);
							logger.info("交易成功,提现记录表状态修改成功,实体:"+crs);
					   //2.用户信息（不变）
					
					
					   //3.收支明细表(修改状态)
						MemberMoney memberMoney=memberMoneyService.getRowLock(orderNum);//根据订单号查询信息
							logger.info("交易成功，查询收支明细表信息成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
						memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
						memberMoney.setStatus(1);//状态 0申请中  1成功   2失败
						memberMoney=memberMoneyService.saveAndModify(memberMoney);
							logger.info("交易成功，收支明细表状态修改成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
				
					logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体订单号:"+saveAndModify.getMerSeqId()+",更新收支明细表实体ID:"+memberMoney.getId());
					
						//4.保存已处理过的提现记录表
						
						/*alreadyCashOrder.setMerSeqId(custOrderId);//订单号
						alreadyCashOrder.setMoney(crs.getMoney());
						alreadyCashOrder.setStatus(2);// 1已申请 2打款成功 3打款失败
						alreadyCashOrder.setCreatedTime(new Date());
						 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
						logger.info("保存已处理过的提现记录表成功,实体:"+alreadyCashOrder);*/
					
					
				}else if(2==cssType){
					logger.info("提现成功业务逻辑处理开始,订单号："+crs.getMerSeqId()+",系统角色为行业协会 类型："+cssType+",( 1批发商 2行业协会)");
					
						//1.修改提现记录状态
						crs.setStatus(2);//1已申请 2打款成功 3打款失败
						crs.setDescriber("打款成功");
						crs.setMoney(crs.getMoney());
						crs.setCssId(crs.getCssId());
						crs.setCssType(crs.getCssType());
						crs.setUpdateTime(new Date());
						crs.setApproveDate(new Date());
						CashReceiveStation saveAndModify = cashReceiveStationService.saveAndModify(crs);
							logger.info("交易成功,提现记录表状态修改成功,实体:"+crs);
					   //2.用户信息（不变）
					
					
					   //3.收支明细表(修改状态)
						IndustryMoney industryMoney=industryMoneyService.getRowLock(orderNum);//根据订单号查询信息
							logger.info("交易成功，查询收支明细表信息成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
						industryMoney.setType(1);////0分润    1提现  
						industryMoney.setStatus(1);//状态 0提现申请中  1提现成功   2提现失败
						industryMoney=industryMoneyService.saveAndModify(industryMoney);
							logger.info("交易成功，收支明细表状态修改成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
				
					logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体订单号:"+saveAndModify.getMerSeqId()+",更新收支明细表实体ID:"+industryMoney.getId());
					
						//4.保存已处理过的提现记录表
						/*
						alreadyCashOrder.setMerSeqId(custOrderId);//订单号
						alreadyCashOrder.setMoney(crs.getMoney());
						alreadyCashOrder.setStatus(2);// 1已申请 2打款成功 3打款失败
						alreadyCashOrder.setCreatedTime(new Date());
						 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
						logger.info("保存已处理过的提现记录表成功,实体:"+alreadyCashOrder);*/
					
				}
				
			
			} else if ("3".equals(payStatus)){//失败
				System.out.println("交易关闭，交易失败");
				
				// 失败后业务逻辑
				if(1==cssType){// cssType 系统角色类型   1批发商 2行业协会
					logger.info("提现失败业务逻辑处理开始,订单号："+crs.getMerSeqId()+",系统角色为批发商 类型："+cssType+",( 1批发商 2行业协会)");
					
						//1.提现记录表(修改状态)
						crs.setStatus(3);//1已申请 2打款成功 3打款失败
						crs.setDescriber("打款失败");
						crs.setFeeIncome(0);//失败时设置手续费0元.-------- 
						crs.setUpdateTime(new Date());
						CashReceiveStation saveAndModify = cashReceiveStationService.saveAndModify(crs);
							logger.info("交易失败,提现记录表状态修改成功,实体:"+crs);
						
						//2.用户表(给用户加钱)
						Hunter hunter = hunterService.getRowLock(Long.valueOf(crs.getCssId()));//加锁查询用户信息
							logger.info("交易失败,查询用户信息成功,用户ID为:"+hunter.getId()+"用户姓名"+hunter.getName());
						Long money=Long.valueOf(crs.getMoney());
							logger.info("贵州银联代付,终态方法中,失败情况,用户id:"+hunter.getId()+",订单号:"+crs.getMerSeqId()+",用户现已提现:"+
							hunter.getHaveDepositMoney()+"单位分,本次提现金额:"+crs.getMoney()+"单位分");
						Long leftDepositMoney=hunter.getLeftDepositMoney();//剩余可提现
						if(null==leftDepositMoney){ leftDepositMoney=0L;}
						leftDepositMoney=leftDepositMoney+money;
						
						Long haveDepositMoney=hunter.getHaveDepositMoney();//已提现
						if(null==haveDepositMoney){ haveDepositMoney=0L;}
						haveDepositMoney=haveDepositMoney-money;
						
						hunter.setLeftDepositMoney(leftDepositMoney);
						hunter.setHaveDepositMoney(haveDepositMoney);
						hunter=hunterService.saveAndModify(hunter);
							logger.info("贵州银联代付,终态方法中,失败情况,用户id:"+hunter.getId()+",订单号:"+crs.getMerSeqId()+",用户更新余额返回实体:"
									+hunter+",更新后现有余额:"+hunter.getHaveDepositMoney()+"单位分,本次提现金额:"+crs.getMoney()+"单位分");
					
						//3.收支明细表(修改状态，修改变动金额)
						MemberMoney memberMoney=memberMoneyService.getRowLock(orderNum);//根据订单号查询信息
							logger.info("交易失败,查询收支明细表信息成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
						memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
						memberMoney.setStatus(2);//状态 0申请中  1成功   2失败
						memberMoney=memberMoneyService.saveAndModify(memberMoney);
							logger.info("交易失败,收支明细表状态修改成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
					logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+hunter+",收支明细表实体:"+memberMoney);
					System.out.println("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+hunter+",收支明细表实体:"+memberMoney);
					
						//4.保存已处理过的提现记录表
						
						/*alreadyCashOrder.setMerSeqId(custOrderId);//订单号
						alreadyCashOrder.setMoney(crs.getMoney());
						alreadyCashOrder.setStatus(3);// 1已申请 2打款成功 3打款失败
						alreadyCashOrder.setCreatedTime(new Date());
						 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
						logger.info("保存已处理过的提现记录表失败,实体:"+alreadyCashOrder);*/

						
					
				}else if(2==cssType){
					logger.info("提现失败业务逻辑处理开始,订单号："+crs.getMerSeqId()+",系统角色为行业协会 类型："+cssType+",( 1批发商 2行业协会)");
					
					//1.提现记录表(修改状态)
					crs.setStatus(3);//1已申请 2打款成功 3打款失败
					crs.setDescriber("打款失败");
					crs.setFeeIncome(0);//失败时设置手续费0元.-------- 
					crs.setUpdateTime(new Date());
					CashReceiveStation saveAndModify = cashReceiveStationService.saveAndModify(crs);
						logger.info("交易失败,提现记录表状态修改成功,实体:"+crs);
					
					//2.用户表(给用户加钱)
					IndustryAssociation industryAssociation = industryAssociationService.getRowLock(Long.valueOf(crs.getCssId()));//加锁查询用户信息
						logger.info("交易失败,查询用户信息成功,用户ID为:"+industryAssociation.getId()+"用户姓名"+industryAssociation.getName());
					Long money=Long.valueOf(crs.getMoney());
						logger.info("贵州银联代付,终态方法中,失败情况,用户id:"+industryAssociation.getId()+",订单号:"+crs.getMerSeqId()+",用户现已提现:"+
								industryAssociation.getHaveDepositMoney()+"单位分,本次提现金额:"+crs.getMoney()+"单位分");
					Long leftDepositMoney=industryAssociation.getLeftDepositMoney();//剩余可提现
					if(null==leftDepositMoney){ leftDepositMoney=0L;}
					leftDepositMoney=leftDepositMoney+money;
					
					Long haveDepositMoney=industryAssociation.getHaveDepositMoney();//已提现
					if(null==haveDepositMoney){ haveDepositMoney=0L;}
					haveDepositMoney=haveDepositMoney-money;
					
					industryAssociation.setLeftDepositMoney(leftDepositMoney);
					industryAssociation.setHaveDepositMoney(haveDepositMoney);
					industryAssociation=industryAssociationService.saveAndModify(industryAssociation);
						logger.info("贵州银联代付,终态方法中,失败情况,用户id:"+industryAssociation.getId()+",订单号:"+crs.getMerSeqId()+",用户更新余额返回实体:"
								+industryAssociation+",更新后现有余额:"+industryAssociation.getHaveDepositMoney()+"单位分,本次提现金额:"+crs.getMoney()+"单位分");
				
					//3.收支明细表(修改状态，修改变动金额)
					IndustryMoney industryMoney=industryMoneyService.getRowLock(orderNum);//根据订单号查询信息
						logger.info("交易成功，查询收支明细表信息成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
					industryMoney.setType(1);////0分润    1提现  
					industryMoney.setStatus(2);//状态 0提现申请中  1提现成功   2提现失败
					industryMoney=industryMoneyService.saveAndModify(industryMoney);
						logger.info("交易失败,收支明细表状态修改成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
				logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+industryAssociation+",收支明细表实体:"+industryMoney);
				System.out.println("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+industryAssociation+",收支明细表实体:"+industryMoney);
				
					//4.保存已处理过的提现记录表
					
					/*alreadyCashOrder.setMerSeqId(custOrderId);//订单号
					alreadyCashOrder.setMoney(crs.getMoney());
					alreadyCashOrder.setStatus(3);// 1已申请 2打款成功 3打款失败
					alreadyCashOrder.setCreatedTime(new Date());
					 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
					logger.info("保存已处理过的提现记录表失败,实体:"+alreadyCashOrder);*/
					
				}
				
				
			} else {
				
				System.out.println("等待支付结果，处理中");//需查询交易获取结果或等待通知结果
			}
			
			
		} catch (Exception e) {
			logger.info("订单号:" + orderNum + ",更新状态时异常:" + e.toString());
			e.printStackTrace();
			return "statuserror";
		}
		
		
		return "success";
	}

}
