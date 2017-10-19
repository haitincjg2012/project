package com.ph.shopping.facade.profit.service;

import java.util.List;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.AuditDTO;
import com.ph.shopping.facade.profit.dto.UserDrawCashDTO;
import com.ph.shopping.facade.profit.vo.UserDrawCashVO;

/**
 * 
 * @ClassName: IUserDrawCashService
 * @Description: 供链提现接口
 * @author 王强
 * @date 2017年6月12日 上午10:56:41
 */
public interface IUserDrawCashService {
	
	/**
	 * 
	* @Title: getUserDrawCahsList
	* @Description: 获取供链余额
	* @author 王强
	* @date  2017年6月12日 上午11:32:28
	* @param userDrawCashDTO
	* @return
	 */
	Result getUserDrawCahsList(UserDrawCashDTO userDrawCashDTO);
	
	/**
	 * 
	* @Title: getScoreDetailExport
	* @Description: 导出数据
	* @author 王强
	* @date  2017年6月7日 下午6:26:01
	* @param scoreDetailDTO
	* @return
	 */
	List<UserDrawCashVO> getExportData(UserDrawCashDTO userDrawCashDTO);
	
	/**
	 * 
	* @Title: doAuditOperator
	* @Description: 审核操作
	* @author 王强
	* @date  2017年6月13日 下午2:43:42
	* @param auditDTO
	* @return
	 */
	Result doAuditOperator(AuditDTO auditDTO);
	
	/**
	 * 
	* @Title: updStatus
	* @Description: 更新提现状态
	* @author 王强
	* @date  2017年6月23日 下午6:02:41
	* @param status
	* @param orderNo
	* @return
	 */
	Result updStatus(int status, String orderNo, String tradeState);
	
	/**
	 * 
	 * 
	* @Title: backUserScore
	* @Description: 返回用户余额
	* @author 王强
	* @date  2017年6月24日 下午8:51:21
	* @param orderNo
	 */
	void backUserScore(String orderNo);
	
}
