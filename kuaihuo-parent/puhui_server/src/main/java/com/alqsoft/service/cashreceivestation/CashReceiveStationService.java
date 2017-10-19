package com.alqsoft.service.cashreceivestation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.cashreceivestation.CashReceiveStation;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.user.User;

public interface CashReceiveStationService extends BaseService<CashReceiveStation>{

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
	 * 批发商个人中心-提现  手机号码发送验证码   PHPF2017070303
	 * @param moneyPhone
	 * @return
	 */
	public Result sendMsgForMoneyPhone(Member member, String moneyPhone, String codeType);
	/**
	 * 根据订单号获取记录ID
	 */
	public Long getIDByMerSeqId(String custOrderId);
	/**
	 * 提现加锁
	 */
	public CashReceiveStation getRowLock(Long id);
	/**
	 * 行业协会后台--提现 手机号发送验证码   PHPF2017070304
	 * @param tixianPhone
	 * @param request 
	 * @return
	 */
	public Result sendMsgForUpdatePhone(String tixianPhone, String codeType, HttpServletRequest request);
	/**
	 *  行业协会后台--提现
	 */
	public Result industryAssociationCashmoney(String tixianPhone, String feephonecode, Long bankCardId,
			Long industryAssociationId, Double money, String bankNo, String name, String card, String bank,
			HttpServletRequest request);
	
}
