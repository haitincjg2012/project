package com.alqsoft.service.impl.gzylreturn;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alqframework.result.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alqsoft.entity.alreadycashorder.AlreadyCashOrder;
import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrymoney.IndustryMoney;
import com.alqsoft.entity.membermoney.MemberMoney;
import com.alqsoft.rpc.pay.RpcPayService;
import com.alqsoft.service.alreadycashorder.AlreadyCashOrderService;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;
import com.alqsoft.service.gzylreturn.GZYLReturnService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
import com.alqsoft.service.industrymoney.IndustryMoneyService;
import com.alqsoft.service.membermoney.MemberMoneyService;
import com.alqsoft.utils.CodeConstExt;
import com.alqsoft.utils.gzyl.SignUtils;
import com.alqsoft.utils.gzyl.SystemRetCode;
import com.alqsoft.utils.gzyl.SystemRetField;
/**
 * @ClassName  GZYLReturnServiceImpl
 * Date:     2017年3月13日  14:45:41 <br/>
 * @author   dinglanlan
 * @version  批发商提现回调
 * @see 	 
 */
@Service
@Transactional
public class GZYLReturnServiceImpl implements GZYLReturnService {
	
	private static Log logger = LogFactory.getLog(GZYLReturnServiceImpl.class);
	
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
	
	@Autowired
	private AlreadyCashOrderService alreadyCashOrderService;
	
	@Autowired
	private RpcPayService rpcPayService;
	

	@Override
	public String validationParam(HttpServletRequest request, HttpServletResponse response, String encReq) {
		
		JSONObject respJson = JSONObject.parseObject(encReq);
		String custOrderId = respJson.get("custOrderId") + "";
		String transStatus = respJson.get("transStatus") + "";
		//判断回调是否已处理过
		Integer count = alreadyCashOrderService.findAlreadyCashOrderByMerSeqID(custOrderId);
		if (count>0) {
			logger.info("回调方法执行中,订单号"+custOrderId+",此订单的状态:"+transStatus+",已处理的记录查到:"+count+"条,不在回调执行范围内,返回statuserror");
			return "success";
		}
		
		
		String transStatusDesc = request.getParameter("transStatusDesc");
		logger.info("回调方法中返回的状态:"+transStatusDesc+",回调方法第一步.");
		Result verifySingNotify = this.validation(request, encReq);
		if (verifySingNotify.getMsg().equals("statuserror")) {
			return "error";
		}
		logger.info("回调方法最后一步,提现金额:"+verifySingNotify.getCode()+"单位分,订单号："+verifySingNotify.getContent().toString()+",最终状态:"+verifySingNotify.getMsg());
		String result = verifySingNotify.getMsg();
		
		if   (null!=result && result.equals("SUCCESS")) {//成功
			return "success";
		}else if (null!=result && result.equals("FAIL")) {//失败
			return "success";
		}else if (null!=result && result.equals("WPAR")) {//处理中
			
		}
		return "error";
	}

	
	
	public Result validation(HttpServletRequest request, String encReq) {
		Result appResult = new Result();
		JSONObject respJson = JSONObject.parseObject(encReq);
		String custOrderId = respJson.get("custOrderId") + "";
		String transStatus = respJson.get("transStatus") + "";
		
		if ("".equals(custOrderId) || custOrderId == null) {
			appResult.setMsg("statuserror");
			logger.info("回调方法执行中,订单号"+custOrderId+",接收回调参数异常");
			return appResult;
		}
		CashReceiveStation crs =new CashReceiveStation();
		try {
			Long id = cashReceiveStationService.getIDByMerSeqId(custOrderId);//根据订单号获取记录id
			crs = cashReceiveStationService.getRowLock(id);//提现加锁
			if (crs==null) {
				appResult.setMsg("statuserror");
				logger.info("回调方法执行中,订单号"+custOrderId+",此订单未查询到");
				return appResult;
			}
			if (crs.getStatus()==null) {
				appResult.setMsg("statuserror");
				logger.info("回调方法执行中,订单号"+custOrderId+",此订单未查询到");
				return appResult;
			}
		} catch (Exception e1) {
			appResult.setMsg("statuserror");
			e1.printStackTrace();
			logger.info("回调方法执行中,订单号"+custOrderId+",此订单查询异常");
			return appResult;
		}
		Integer status = crs.getStatus();
		logger.info("回调方法执行中,订单号"+custOrderId+",此订单的状态:"+status);
		if (status!=1) {
			appResult.setMsg("statuserror");
			logger.info("回调方法执行中,订单号"+custOrderId+",此订单的状态:"+status+"不在回调执行范围内,返回statuserror");
			return appResult;
		}
		System.out.println("--------------贵州银联回调获取的参数-----------------"+respJson);
		/*if (!SystemRetCode.SUCCESS.toString().equalsIgnoreCase(transStatus)) {
        	logger.info(SystemRetField.RET_CODE.toString()+SystemRetCode.COM_ERROR.toString()+SystemRetField.RET_DESC.toString()+"通讯异常，请查看日志");
        	appResult.setMsg("statuserror");
            return appResult;
        }*/
		logger.info("回调方法执行中,"+"异步回调返回状态:"+transStatus+"流水号:"+custOrderId);
		
		Integer cssType=crs.getCssType();//  系统角色类型   1批发商 2行业协会
		
		logger.info("回调方法 参数++++++++++++++++++++++++++++++++++"+crs.getId()+"获取的回调参数0+"+respJson.toJSONString());
        System.out.println("----------------贵州银联回调获取的参数转换格式后-------------------------"+respJson);
        String retSign = respJson.remove("sign") + "";
        System.out.println("sign:" + retSign);
        
		try {
			//调用pay工程验签方法
	        logger.info("调用pay工程验签方法,签名："+retSign +",需要验证的数据:"+ SignUtils.getSignData(respJson));
	        
	        Result result=rpcPayService.verifyGZYLCashCall(retSign, respJson,CodeConstExt.PAY_SYSTEM_FROM_LT);//code 为0 代表和pay工程通信成功 ｍｓｇ：ＳＵＣＣＥＳＳ 代表验签成功
	        
	        logger.info("调用pay工程验签方法,请求返回结果处理开始**********");
	        logger.info("验签结果,状态码code：" + result.getCode()+",验签结果msg："+result.getMsg());
			
	        if (result.getCode()==0) {//与pay工程通信成功
	        	
	        	 logger.info("调用pay工程验签方法,与pay工程通信成功");
	        	 
	        	if(result.getMsg().equals("SUCCESS")){//验签成功
	        		
	        		logger.info("调用pay工程验签方法,验签成功,处理返回数据开始");
	        		String reqMsg = respJson.toJSONString();
		        	appResult  = tradeCode(reqMsg,crs,cssType);//处理返回数据
	        		
	        	}else{//验签失败
	        		logger.info("调用pay工程验签方法,验签失败.返回结果msg:"+result.getMsg());
	        		//logger.info(SystemRetField.RET_CODE.toString()+SystemRetCode.COM_ERROR.toString()+SystemRetField.RET_DESC.toString()+"商户验平台签名失败，请检查公钥是否正确或者数据是否被篡改");
		            appResult.setMsg("statuserror");
		            System.out.println("验签失败");
		            return appResult;
	        	}
	        	
	        }else{//与pay工程通信失败
	        	 logger.info("调用pay工程验签方法,与pay工程通信失败。返回结果code:"+result.getCode()+",msg:"+result.getMsg());
	        	 appResult.setMsg("statuserror");
	        	 System.out.println("与pay工程通信失败");
	             return appResult;
	        }
			
	        
		} catch (Exception e) {
			System.out.println("代付发生异常："+ e);
            logger.info("代付发生异常："+SystemRetField.RET_CODE.toString()+SystemRetCode.COM_ERROR.toString()+SystemRetField.RET_DESC.toString()+e.getLocalizedMessage()+"====="+e.getMessage());
            appResult.setMsg("statuserror");
            return appResult;
		}
		return appResult;
	}
	
	
	private Result tradeCode(String reqMsg, CashReceiveStation crs, Integer cssType) {
		Result appResult=new Result();
		String FAIL = "FAIL";
		JSONObject jsonParam = JSONObject.parseObject(reqMsg);
		String transStatus = jsonParam.getString("transStatus");//订单状态
		String custOrderId = jsonParam.getString("custOrderId");//订单号
		logger.info("交易结果处理方法中-处理交易结果,"+"异步回调返回状态"+transStatus+"流水号"+custOrderId);
	
		AlreadyCashOrder alreadyCashOrder=new AlreadyCashOrder();
		
		if(SystemRetCode.SUCCESS.toString().equals(transStatus)){
			System.out.println("交易成功");
			logger.info("交易成功    ========================="+reqMsg);
			
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
					MemberMoney memberMoney=memberMoneyService.getRowLock(custOrderId);//根据订单号查询信息
						logger.info("交易成功，查询收支明细表信息成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
					memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
					memberMoney.setStatus(1);//状态 0申请中  1成功   2失败
					memberMoney=memberMoneyService.saveAndModify(memberMoney);
						logger.info("交易成功，收支明细表状态修改成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
			
				logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体订单号:"+saveAndModify.getMerSeqId()+",更新收支明细表实体ID:"+memberMoney.getId());
				
					//4.保存已处理过的提现记录表
					
					alreadyCashOrder.setMerSeqId(custOrderId);//订单号
					alreadyCashOrder.setMoney(crs.getMoney());
					alreadyCashOrder.setStatus(2);// 1已申请 2打款成功 3打款失败
					alreadyCashOrder.setCreatedTime(new Date());
					 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
					logger.info("保存已处理过的提现记录表成功,实体:"+alreadyCashOrder);
				
				
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
					IndustryMoney industryMoney=industryMoneyService.getRowLock(custOrderId);//根据订单号查询信息
						logger.info("交易成功，查询收支明细表信息成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
					industryMoney.setType(1);////0分润    1提现  
					industryMoney.setStatus(1);//状态 0提现申请中  1提现成功   2提现失败
					industryMoney=industryMoneyService.saveAndModify(industryMoney);
						logger.info("交易成功，收支明细表状态修改成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
			
				logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体订单号:"+saveAndModify.getMerSeqId()+",更新收支明细表实体ID:"+industryMoney.getId());
				
					//4.保存已处理过的提现记录表
					
					alreadyCashOrder.setMerSeqId(custOrderId);//订单号
					alreadyCashOrder.setMoney(crs.getMoney());
					alreadyCashOrder.setStatus(2);// 1已申请 2打款成功 3打款失败
					alreadyCashOrder.setCreatedTime(new Date());
					 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
					logger.info("保存已处理过的提现记录表成功,实体:"+alreadyCashOrder);
				
			}
			
		
			//打款成功
			appResult.setMsg(transStatus);
			appResult.setContent(custOrderId);   
			appResult.setSessionId(crs.getCssId()+"");
			appResult.setCode(crs.getMoney());
			
		} else if(FAIL.equals(transStatus)){
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
					MemberMoney memberMoney=memberMoneyService.getRowLock(custOrderId);//根据订单号查询信息
						logger.info("交易失败,查询收支明细表信息成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
					memberMoney.setType(4);//0 支付订单  1支付定金  2定金退货  3退货  4提现  5分润
					memberMoney.setStatus(2);//状态 0申请中  1成功   2失败
					memberMoney=memberMoneyService.saveAndModify(memberMoney);
						logger.info("交易失败,收支明细表状态修改成功,ID为:"+memberMoney.getId()+"用户ID"+memberMoney.getHunter().getId());
				logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+hunter+",收支明细表实体:"+memberMoney);
				System.out.println("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+hunter+",收支明细表实体:"+memberMoney);
				
					//4.保存已处理过的提现记录表
					
					alreadyCashOrder.setMerSeqId(custOrderId);//订单号
					alreadyCashOrder.setMoney(crs.getMoney());
					alreadyCashOrder.setStatus(3);// 1已申请 2打款成功 3打款失败
					alreadyCashOrder.setCreatedTime(new Date());
					 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
					logger.info("保存已处理过的提现记录表失败,实体:"+alreadyCashOrder);

					
				
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
				IndustryMoney industryMoney=industryMoneyService.getRowLock(custOrderId);//根据订单号查询信息
					logger.info("交易成功，查询收支明细表信息成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
				industryMoney.setType(1);////0分润    1提现  
				industryMoney.setStatus(2);//状态 0提现申请中  1提现成功   2提现失败
				industryMoney=industryMoneyService.saveAndModify(industryMoney);
					logger.info("交易失败,收支明细表状态修改成功,ID为:"+industryMoney.getId()+"用户ID"+industryMoney.getIndustryAssociation().getId());
			logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+industryAssociation+",收支明细表实体:"+industryMoney);
			System.out.println("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify+",更新用户记录实体:"+industryAssociation+",收支明细表实体:"+industryMoney);
			
				//4.保存已处理过的提现记录表
				
				alreadyCashOrder.setMerSeqId(custOrderId);//订单号
				alreadyCashOrder.setMoney(crs.getMoney());
				alreadyCashOrder.setStatus(3);// 1已申请 2打款成功 3打款失败
				alreadyCashOrder.setCreatedTime(new Date());
				 alreadyCashOrder=alreadyCashOrderService.saveAndModify(alreadyCashOrder);
				logger.info("保存已处理过的提现记录表失败,实体:"+alreadyCashOrder);
				
			}
			
		
			
			
			appResult.setMsg(transStatus);
			appResult.setContent(custOrderId);   
			appResult.setSessionId(crs.getCssId()+"");
			appResult.setCode(crs.getMoney());
		} else {
			System.out.println("等待支付结果，处理中");//需查询交易获取结果或等待通知结果
			// 处理中业务逻辑
			crs.setDescriber("正在处理");
			CashReceiveStation saveAndModify = cashReceiveStationService.saveAndModify(crs);
			logger.info("订单号:"+crs.getMerSeqId()+",更新提现记录实体:"+saveAndModify);
			appResult.setMsg("正在处理");//正在处理
			appResult.setContent(custOrderId);   
			appResult.setSessionId(crs.getCssId()+"");
			appResult.setCode(crs.getMoney());
		}
		return appResult;
	}
	
}
