package com.ph.shopping.facade.profit.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO;

/**
 * 
* @ClassName: IMemberScoreService
* @Description: 会员余额接口
* @author 王强
* @date 2017年6月12日 上午9:51:57
 */
public interface IMemberScoreService {
	
	/**
	 * 账号结算下会员余额list
	* @Title: getMemberScoreDetailedList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月12日 下午6:54:17
	* @param memberScoreDetailedDTO
	* @return
	 */
	public Result getMemberScoreDetailedList(MemberScoreDetailedDTO memberScoreDetailedDTO);
	
	/**
	 * 导出会员余额EXCEL
	* @Title: getMemberScoreDetailedEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月12日 下午8:55:11
	* @param memberScoreDetailedDTO
	* @return
	 */
	public Result getMemberScoreDetailedEXCEL(MemberScoreDetailedDTO memberScoreDetailedDTO);
	
	/**
	 * 更改会员积分冻结和解冻
	* @Title: updateMemberScoreStatus
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月13日 上午9:38:10
	* @param memberId
	* @param status
	* @return
	 */
	public Result updateMemberScoreStatus(Long memberId,int status);
	
	/**
	 * 定时器执行统计会员积分统计
	* @Title: memberScoreStatistics
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月5日 下午4:01:52
	* @return
	 */
	public Result memberScoreStatistics() throws Exception ;
}
