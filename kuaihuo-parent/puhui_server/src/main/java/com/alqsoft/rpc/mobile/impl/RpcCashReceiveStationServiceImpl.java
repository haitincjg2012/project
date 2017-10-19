package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcCashReceiveStationService;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;


/**
 * Date:     2017年3月7日  16:50:41 <br/>
 * @author   dinglanlan
 * @version  提现记录
 * @see 	 
 */

@Controller
public class RpcCashReceiveStationServiceImpl implements RpcCashReceiveStationService {

	
	@Autowired
	private CashReceiveStationService cashReceiveStationService;
	
	
	/**
	 * 批发商个人中心--提现
	 * @param phone
	 * @param code
	 * @param cashReceiveStation
	 * @return
	 */
	@Override
	public Result cashReceiveStationMoney(CashReceiveStation cashReceiveStation, Double money, String phone, String code, Long cardId) {
		// TODO Auto-generated method stub
		return cashReceiveStationService.cashReceiveStationMoney(cashReceiveStation,money,phone,code,cardId);
	}

	/**
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	@Override
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType) {
		// TODO Auto-generated method stub
		return cashReceiveStationService.sendMsgForMoneyPhone(member,moneyPhone,codeType);
	}

}
