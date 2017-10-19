package com.alqsoft.service.cashreceivestation;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

/**
 * Date:     2017年3月7日  15:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */

public interface CashReceiveStationService {
	/**
	 * 批发商个人中心--提现明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @param request 
	 * @return
	 */
	public Result findCashReceiveStationById(Long hunterId, Integer page, Integer rows, Member member);
	/**
	 * 批发商个人中心--提现订单明细
	 * @param hunterId
	 * @param merSeqId
	 * @return
	 */
	public Result findCashReceiveStationByMerSeqId(Long hunterId, String merSeqId, Member member);

	/***
	 * 批发商个人中心的收入
	 * @param member
	 * @return
	 * @author wudi
	 */
	public Result findMoneyIncome(Member member);

	/**
	 * 批发商个人中心--提现
	 * @param phone
	 * @param code
	 * @param cardId
	 * @param hunterId
	 * @param money
	 * @param member 
	 * @return
	 */
	public Result cashReceiveStationMoney(String phone, String code, Long cardId, Integer hunterId, Double money, Member member, HttpServletRequest request);
	/**
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType);
	/**
	 * 批发商个人中心--跳转提现页面
	 */
	public Result toCashReceiveStationMoney(Member member);


}
