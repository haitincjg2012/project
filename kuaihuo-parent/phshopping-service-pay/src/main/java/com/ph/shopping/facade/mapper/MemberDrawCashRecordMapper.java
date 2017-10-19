package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.MemberDrawcashRecord;
import com.ph.shopping.facade.member.vo.MemberInfoByCashVO;
import com.ph.shopping.facade.pay.dto.CashReceiveStation;
import com.ph.shopping.facade.profit.vo.BackMemberDrawCashVO;
import com.ph.shopping.facade.spm.entity.UserDrawcash;
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
public interface MemberDrawCashRecordMapper extends BaseMapper<MemberDrawcashRecord>{

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


	/**
	 *
	 * @Title: countDrawCash
	 * @Description: 校验订单
	 * @author 王强
	 * @date  2017年6月23日 下午6:19:03
	 * @param orderNo
	 * @return
	 */
	int countDrawCashTotal(@Param("orderNo") String orderNo);

	/**
	 *
	 * @Title: getRefundData
	 * @Description: 查询退款数据
	 * @author 王强
	 * @date  2017年6月24日 下午8:52:19
	 * @param orderNo
	 */
	BackMemberDrawCashVO getBackDrawCashData(@Param("orderNo") String orderNo);

	/**
	 *
	 * @Title: updateStatus
	 * @Description: 更新提现状态
	 * @author 王强
	 * @date  2017年6月23日 下午5:54:15
	 * @param status 提现状态
	 * @param orderNo 订单号
	 * @return
	 */
	int updateStatus(@Param("status") int status, @Param("orderNo") String orderNo, @Param("tradeState") String tradeState);

    Long getIdByOrderNo(String orderNum);

	MemberDrawcashRecord getRowLock(Long id);

	void addMemberBalance(MemberDrawcashRecord mdr);

    void updateUserCashStatus(@Param("status") int status, @Param("orderNo") String orderNo, @Param("tradeState") String tradeState);

	Long getUserCashIdByOrderNo(String orderNum);

	UserDrawcash getCashTradeRowLock(Long id);

	void addUserBalance(UserDrawcash userDrawCash);

	void addUserBalanceTrade(UserDrawcash userDrawCash);

	void addScoreIncomeTrade(MemberDrawcashRecord mdr);
}
