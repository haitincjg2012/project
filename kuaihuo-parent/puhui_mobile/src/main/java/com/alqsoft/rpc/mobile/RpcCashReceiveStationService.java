package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.member.Member;

/**
 * Date:     2017年3月1日  16:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */
public interface RpcCashReceiveStationService {
	/**
	 * 批发商个人中心--提现
	 * @param phone
	 * @param code
	 * @param cashReceiveStation
	 * @param money 
	 * @param cardId 
	 * @return
	 */
	public Result cashReceiveStationMoney(CashReceiveStation cashReceiveStation, Double money, String phone, String code, Long cardId);
	/**
	 * 批发商个人中心-提现  手机号码发送验证码   2017030801
	 * @param moneyPhone
	 * @return
	 */
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType);

}
