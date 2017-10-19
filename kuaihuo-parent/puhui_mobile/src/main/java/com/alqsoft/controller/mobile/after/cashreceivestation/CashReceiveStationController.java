package com.alqsoft.controller.mobile.after.cashreceivestation;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;

/**
 * @ClassName  CashReceiveStationController
 * Date:     2017年3月7日  15:45:41 <br/>
 * @author   dinglanlan
 * @version  提现记录
 * @see 	 
 */

@RequestMapping("mobile/after/cashreceivestation")
@Controller
public class CashReceiveStationController {

	@Autowired
	private CashReceiveStationService cashReceiveStationService;
	
	
	/**
	 * 批发商个人中心--提现明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="findcashreceivestationbyid",method=RequestMethod.POST)
	@ResponseBody
	public Result findcashreceivestationbyid(@RequestParam(value="hunterId")Long hunterId,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="5")Integer rows,
			@MemberAnno Member member){
		
		Result result =this.cashReceiveStationService.findCashReceiveStationById(hunterId,page,rows,member);
		
		return result;		
		
	}
	
	/**
	 * 批发商个人中心--提现订单明细
	 * @param hunterId
	 * @param merSeqId
	 * @return
	 */
	@RequestMapping(value="findcashreceivestationbymerseqid",method=RequestMethod.POST)
	@ResponseBody
	public Result findcashreceivestationbymerseqid(@RequestParam(value="hunterId")Long hunterId,
			@RequestParam(value="merSeqId")String merSeqId,
			@MemberAnno Member member){
		
		Result result =this.cashReceiveStationService.findCashReceiveStationByMerSeqId(hunterId,merSeqId,member);
		
		return result;		
		
	}
	/***
	 * @remeber
	 * @return 
	 */
	@RequestMapping(value="income",method=RequestMethod.POST)
	@ResponseBody
	public Result findMoneyIncome(@MemberAnno Member member){
		
		Result result=this.cashReceiveStationService.findMoneyIncome(member);
		return result;
	}
	
	
	/**
	 * 批发商个人中心--跳转提现页面
	 */
	@RequestMapping(value="tocashreceivestationmoney",method=RequestMethod.POST)
	@ResponseBody
	public Result tocashreceivestationmoney(@MemberAnno Member member){
		
		Result result =this.cashReceiveStationService.toCashReceiveStationMoney(member);
		
		return result;		
		
	}
	
	/**
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	@RequestMapping("sendmsgformoneyphone")
	@ResponseBody
	public Result sendmsgformoneyphone(@RequestParam(value="moneyPhone") String moneyPhone,
			@MemberAnno Member member){
		//验证手机号
		String regex="^1[34578]\\d{9}$";
		if(!moneyPhone.matches(regex)){
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		
		return cashReceiveStationService.sendMsgForMoneyPhone(member,moneyPhone,"PHPF2017070303");

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
	@Explosionproof
	@RequestMapping(value="cashreceivestationmoney",method=RequestMethod.POST)
	@ResponseBody
	public Result cashreceivestationmoney(@RequestParam(value="moneyPhone")String moneyPhone,
			@RequestParam(value="code")String code,
			@RequestParam(value="cardId")Long cardId,
			@RequestParam(value="hunterId")Integer hunterId,
			@RequestParam(value="money")Double money,
			@MemberAnno Member member,HttpServletRequest request){
		
		/*Result result =new Result();
		return ResultUtils.returnError("提现已关闭");	*/
		
		Result result =this.cashReceiveStationService.cashReceiveStationMoney(moneyPhone,code,cardId,hunterId,money,member,request);
		
		return result;
	}
	
}
