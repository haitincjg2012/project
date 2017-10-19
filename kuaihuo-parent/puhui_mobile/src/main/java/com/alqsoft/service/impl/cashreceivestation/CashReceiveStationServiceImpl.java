package com.alqsoft.service.impl.cashreceivestation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.date.DateUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.cashreceivestation.CashReceiveStationDao;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcCashReceiveStationService;
import com.alqsoft.service.bankcard.BankCardService;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;
import com.alqsoft.utils.CommUtils;
import com.alqsoft.utils.StatusCodeEnums;


/**
 * Date:     2017年3月7日  15:45:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */

@Service
@Transactional(readOnly=true)
public class CashReceiveStationServiceImpl implements CashReceiveStationService {
	
	private static Logger logger = LoggerFactory.getLogger(CashReceiveStationServiceImpl.class);
	
	@Autowired
	private CashReceiveStationDao cashReceiveStationDao;
	
	@Autowired
	private RpcCashReceiveStationService rpcCashReceiveStationService;
	
	@Autowired
	private BankCardService bankCardService;
	
	

	/**
	 * 批发商个人中心--提现明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public Result findCashReceiveStationById(Long hunterId, Integer page, Integer rows, Member member) {
		
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
			
			List<Map<String,Object>> cashReceiveStationList = cashReceiveStationDao.findCashReceiveStationById(param);
			
			if(cashReceiveStationList.size()>0){
				for (Map<String, Object> cashReceiveStation : cashReceiveStationList) {
					String createdTime=DateUtils.dateFormat((Date)cashReceiveStation.get("createdTime"), "yyyy-MM-dd HH:mm:ss");
					cashReceiveStation.put("createdTime", createdTime);
				}
				
				
				return ResultUtils.returnSuccess("请求成功", cashReceiveStationList);
			}else{
				return ResultUtils.returnError("没有数据");
			}
		
	}


	/**
	 * 批发商个人中心--提现订单明细
	 * @param hunterId
	 * @param merSeqId
	 * @return
	 */
	@Override
	public Result findCashReceiveStationByMerSeqId(Long hunterId, String merSeqId, Member member) {
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		Hunter hunter=member.getHunter();
		
		if(null==hunter){
			
			return ResultUtils.returnError("角色异常");
		}
		
		if(hunterId==null||"".equals(hunterId)){
			
			return ResultUtils.returnError("用户id不能为空");
		}
		
		if("".equals(merSeqId)){
			
			return ResultUtils.returnError("订单号不能为空");
		}

		Long hunterI1=member.getHunter().getId();//登录的批发商id
		
		if(hunterId.longValue() != hunterI1.longValue()){
			
			return ResultUtils.returnError("参数异常");
		}
	
		param.put("hunterId", hunterId);
		param.put("merSeqId",merSeqId);
		
		List<Map<String,Object>> cashReceiveStationList = cashReceiveStationDao.findCashReceiveStationByMerSeqId(param);
		
		if(cashReceiveStationList.size()>0){
			Map<String,Object> cashReceiveStation=cashReceiveStationList.get(0);
			String createdTime=DateUtils.dateFormat((Date)cashReceiveStation.get("createdTime"), "yyyy-MM-dd HH:mm:ss");
			String updateTime=DateUtils.dateFormat((Date)cashReceiveStation.get("updateTime"), "yyyy-MM-dd HH:mm:ss");
			cashReceiveStation.put("createdTime", createdTime);
			cashReceiveStation.put("updateTime", updateTime);
			return ResultUtils.returnSuccess("请求成功", cashReceiveStation);
		}else{
			return ResultUtils.returnError("没有数据");
		}
	}


	/**
	 * 批发商个人中心--收入明细
	 * @param hunterId
	 * @param merSeqId
	 * @return
	 */
	@Override
	public Result findMoneyIncome(Member member) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(member==null ){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
			result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		try {
			Long id=member.getHunter().getId();
			List<Map> list = cashReceiveStationDao.findMoneyIncome(id);
			if(list==null || list.size()<=0){
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
			} 
			   result.setCode(StatusCodeEnums.SUCCESS.getCode());
			   result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			   result.setContent(list);
		} catch (Exception e) {
			// TODO: handle exception
			 result.setCode(StatusCodeEnums.ERROR.getCode());
			 result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}


	/**
	 * 批发商个人中心--提现
	 * @param phone
	 * @param code
	 * @param cardNo
	 * @param hunterId
	 * @param money
	 * @return
	 */
	@Override
	public Result cashReceiveStationMoney(String phone, String code,  Long cardId, Integer hunterId, Double money, Member member, HttpServletRequest request) {
		Result result=new Result();
		
		Hunter hunter=member.getHunter();
		
		if(null==hunter){
			
			return ResultUtils.returnError("角色异常");
		}
		
		if("".equals(phone)){
			
			return ResultUtils.returnError("手机号码不能为空");
		}
		if("".equals(code)){
			
			return ResultUtils.returnError("验证码不能为空");
		}
	
		if(hunterId==null||"".equals(hunterId.toString())){
	
			return ResultUtils.returnError("用户id不能为空");
		}

		Long hunterI1=member.getHunter().getId();//登录的批发商id
		
		if(hunterId.intValue() != hunterI1.intValue()){
			
			return ResultUtils.returnError("用户id异常");
		}
		if(null==cardId){
			
			return ResultUtils.returnError("银行卡id不能为空");
		}
		
		BankCard bankCard=bankCardService.getBankCardById(cardId);
		if(null==bankCard){
			
			return ResultUtils.returnError("银行卡信息不存在");
		}
		Integer status=bankCard.getStatus();
		if(null==status){
			status=0;
		}
		if(1!=status){
			
			return ResultUtils.returnError("银行卡已被禁用");
		}
		
		if(money==null||"".equals(money.toString())){
			
			return ResultUtils.returnError("提现金额不能为空");
		}
		if(money<=0){
			
			return ResultUtils.returnError("提现金额不能为负数");
		}
		if(money<10){
			return ResultUtils.returnError("提现金额请最少输入10元");
		}
		
		String ipAddr = CommUtils.getIpAddr(request);//ip
		
		CashReceiveStation cashReceiveStation=new CashReceiveStation();
		
			//cashReceiveStation.setMoney(money.intValue());//提现金额
			cashReceiveStation.setCssId(hunterId);//用户id
			cashReceiveStation.setCreatedIp(ipAddr);;//用户ip
			//接口工程调用server服务
			logger.info("接口工程调用server服务  用户ip:"+ipAddr+"用户ID为："+hunterId+"手机号："+phone+"银行卡号："+cardId+"时间："+new Date());
			
			result=rpcCashReceiveStationService.cashReceiveStationMoney(cashReceiveStation,money,phone,code,cardId);
			logger.info("接口工程调用server服务结束 ");
			logger.info("接口工程调用server服务结束  用户ip:"+ipAddr+"用户ID为："+result.getCode()+"MSG:"+result.getMsg()+"时间："+new Date());
		 
		 return result;
	}

	/**
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	@Override
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType) {
		// TODO Auto-generated method stub
		return rpcCashReceiveStationService.sendMsgForMoneyPhone(member,moneyPhone,codeType);
	}

	/**
	 * 批发商个人中心--跳转提现页面
	 */
	@Override
	public Result toCashReceiveStationMoney(Member member) {
		
		Hunter hunter=member.getHunter();
		
		if(null==hunter){
			
			return ResultUtils.returnError("角色异常");
		}
		Long hunterId=hunter.getId();
		List<Map<String,Object>> bankCardList=bankCardService.getHunterBankCardMsg(hunterId);//根据批发商ID获取银行卡信息
		
		if(bankCardList.size()>0){
			
			Map<String,Object> bankCard=bankCardList.get(0);
			return ResultUtils.returnSuccess("查询成功", bankCard);
		}else{
			return ResultUtils.returnError("查无银行卡信息");
		}
		
	}


	


}
