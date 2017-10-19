package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.MemberDrawCashDTO;
import com.ph.shopping.facade.profit.vo.BackMemberDrawCashVO;
import com.ph.shopping.facade.profit.vo.DrawCashVO;
import com.ph.shopping.facade.profit.vo.MemberDrawCashDataVO;
import com.ph.shopping.facade.profit.vo.MemberDrawCashVO;

/**
 * 
* @ClassName: IMemberDrawCashMapper
* @Description: 会员提现mapper
* @author 王强
* @date 2017年6月14日 上午9:34:55
 */
public interface IMemberDrawCashMapper {
	/**
	 * 
	* @Title: getMemberDrawCashes
	* @Description: 获取会员提现
	* @author 王强
	* @date  2017年6月14日 上午10:04:51
	* @return
	 */
	List<MemberDrawCashVO> queryMemberDrawCashes(MemberDrawCashDTO memberDrawCashDTO);
	
	/**
	 * 
	* @Title: auditOperator
	* @Description: 审核操作
	* @author 王强
	* @date  2017年6月13日 下午2:38:24
	* @param auditDTO
	* @return
	 */
	int auditOperator(AuditDTO auditDTO);
	
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
	* @Title: isHaveDrawCash
	* @Description: 是否有提现记录
	* @author 王强
	* @date  2017年6月27日 上午10:05:33
	* @param drawCashId
	* @return
	 */
	DrawCashVO isHaveDrawCash(@Param("orderNo") String orderNo);
	
	MemberDrawCashDataVO getMemberDrawCashData(@Param("drawCashId") Long drawCashId);
}
