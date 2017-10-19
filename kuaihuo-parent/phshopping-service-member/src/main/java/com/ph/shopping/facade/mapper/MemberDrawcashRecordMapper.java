package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberCashDTO;
import com.ph.shopping.facade.member.entity.MemberDrawcashRecord;
import com.ph.shopping.facade.member.vo.MemberInfoByCashVO;
import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 
 * @项目：phshopping-service-pay
 *
 * @描述：会员提现记录Mapper
 *
 * @作者： Mr.Chang
 *
 * @创建时间：2017年3月23日
 *
 * @Copyright @2017 by Mr.Chang
 */
@Repository
public interface MemberDrawcashRecordMapper  extends BaseMapper<MemberDrawcashRecord>{
	
	/**
	 * 根据id获取会员详情
	 * @param memberId
	 * @return
	 * @author Mr.Chang
	 */
	MemberInfoByCashVO getMemberInfo(@Param("memberId") Long memberId);

	/**
	 * 查询会员当天的提现积分总和
	 * @param memberId
	 * @param currentDate
	 * @return
	 * @author Mr.Chang
	 *
	 */
	Long getSumScoreThisDay(@Param("memberId") Long memberId,@Param("currentDate") Date currentDate);

	
	
	int insertCostTrade(@Param("memberId") long memberId, @Param("transCode") int transCode,
			@Param("score") long score, @Param("setId") long setId, @Param("orderNo") String orderNo,
			@Param("handingCharge") long handingCharge);

	
	
	
	int updateMemberScore(@Param("memberId") long memberId, @Param("cashCode") long cashCode,
						  @Param("enableScore") long enableScore);
	int insertMemberTrade(PhScoreTotalTrade totalTrade);

	PhScoreTotalTrade getScore(@Param("memberId") Long memberId);

	int updateCashStatus(MemberDrawcashRecord mdr);

	boolean addIncomeScore(MemberDrawcashRecord mdr);


    int getOrderCountByOrderNum(String orderNum2);

    int addMemberIncomeTrade(MemberDrawcashRecord mdr);
}
