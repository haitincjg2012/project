package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.UserDrawCashDTO;
import com.ph.shopping.facade.profit.vo.BackUserDrawCashVO;
import com.ph.shopping.facade.profit.vo.UserDrawCashDataVO;
import com.ph.shopping.facade.profit.vo.UserDrawCashVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
* @ClassName: UserDrawCashMapper
* @Description: 供链余额mapper
* @author 王强
* @date 2017年6月12日 上午11:15:34
 */
public interface IUserDrawCashMapper {
	/**
	 * 
	* @Title: queryUserDrawCashes
	* @Description: 获取供链提现记录
	* @author 王强
	* @date  2017年6月8日 下午2:16:09
	* @return
	 */
	List<UserDrawCashVO> queryUserDrawCashes(UserDrawCashDTO drawCashDTO);
	
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
	BackUserDrawCashVO getBackDrawCashData(@Param("orderNo") String orderNo);
	
	/**
	 * 
	* @Title: getUserDrawCashData
	* @Description: 用户提现数据
	* @author 王强
	* @date  2017年6月27日 下午2:57:25
	* @param drawCashId
	* @return
	 */
	UserDrawCashDataVO getUserDrawCashData(@Param("drawCashId") Long drawCashId);
}
