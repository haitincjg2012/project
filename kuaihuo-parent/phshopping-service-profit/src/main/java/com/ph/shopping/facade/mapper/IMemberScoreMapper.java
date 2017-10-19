package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO;
import com.ph.shopping.facade.profit.entity.MemberScoreDetailed;
import com.ph.shopping.facade.profit.vo.MemberScoreDetailedVO;

/**
 * 会员积分余额Mapper
* @ClassName: IMemberScoreMapper
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月12日 上午11:28:40
 */
public interface IMemberScoreMapper {

	/**
	 * 会员余额统计list 定时器用
	* @Title: getMemberScoreDetailedList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月12日 下午6:13:15
	* @param memberScoreDetailedDTO
	* @return
	 */
	public List<MemberScoreDetailed> getMemberScoreDetailedList();
	
	/**
	 * 更改会员积分冻结和解冻
	* @Title: updateMemberScoreStatus
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月13日 上午9:39:23
	* @param memberId
	* @param status
	* @return
	 */
	public int updateMemberScoreStatus(@Param("memberId")Long memberId, @Param("status")int status);
		
	/**
	 * 
	* @Title: insertMemberScoteTotal
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月5日 下午4:09:33
	* @param memberScoreDetailedList
	* @return
	 */
	public int insertMemberScoteTotalBatch(List<MemberScoreDetailed> memberScoreDetailedList);
	
	/**
	 * 会员积分VO页面看导出EXCEL用
	* @Title: getMemberScoreDetailedVOList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月5日 下午5:21:23
	* @param memberScoreDetailedDTO
	* @return
	 */
	public List<MemberScoreDetailedVO> getMemberScoreDetailedVOList(MemberScoreDetailedDTO memberScoreDetailedDTO);
	
	/**
	 * 根据会员id获取会员统计参数     payTotalScore  profitScore  returnScore  returnScoreOnline
	* @Title: getMemberScoreTotalParameter
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月6日 下午4:28:30
	* @return
	 */
	public MemberScoreDetailedVO  getMemberScoreTotalParameter(@Param("memberId") Long  memberId);
}
