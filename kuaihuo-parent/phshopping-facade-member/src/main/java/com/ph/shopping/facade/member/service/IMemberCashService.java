package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberCashDTO;

public interface IMemberCashService {

	
	/**
	 * 根据会员id获取会员详情数据(提现详情)
	 * @param memberId
	 * @return Result
	 * @author Mr.Chang
	 */
	public Result getMemberInfo(Long memberId);


	/**
	 * @methodname memberCash 的描述：积分提现
	 * @param memberCashDTO
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/6/14
	 */
	Result memberCash(MemberCashDTO memberCashDTO);
	/**
	 * 
	 * @Title: dayCashScore   
	 * @Description: 查询当天已提现积分
	 * @param: @param memberId
	 * @param: @return
	 * @return: Result
	 * @author：李杰
	 * @throws
	 */
	Result getDayCashScore(Long memberId);

	/**
	 * 消费红利提现
	 */
	Result memberGiftCash(MemberCashDTO cashDto,String token);

	int getOrderCountByOrderNum(String orderNum2);
}
