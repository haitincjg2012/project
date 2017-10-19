package com.ph.shopping.facade.score.service;

import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.score.request.QuerySingleScoreInfoDTO;
import com.ph.shopping.facade.score.request.ScorePageDTO;
import com.ph.shopping.facade.score.vo.MemberCostScoreVO;
import com.ph.shopping.facade.score.vo.MemberScoreVO1;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;

/**
 * 
 * @ClassName: IScoreService
 * @Description: 积分接口
 * @author 王强
 * @date 2017年3月17日 下午4:18:56
 */
public interface IScoreService {
	
	/**
	 * 
	* @Title: getMemberScore
	* @Description: 获取会员积分
	* @author 王强
	* @date  2017年3月22日 下午7:58:22
	* @param memberId
	* @return
	 */
	public MemberScoreVO2 getMemberScore(long memberId);
	
	/**
	 * 
	 * @Title: processScore
	 * @Description: 积分处理方法
	 * @author 王强
	 * @date 2017年3月17日 下午4:19:08
	 * @param memberId
	 * @param codeEnum
	 * @param score
	 * @return
	 */
	public long memberScoreTrade(long memberId, TransCodeEnum codeEnum, long score, String orderNo, long handingCharge) throws Exception;
	
	
	/**
	 * 
	* @Title: getMemberScores
	* @Description: 获取会员积分
	* @author 王强
	* @date  2017年4月24日 下午4:06:13
	* @return
	 */
	public List<MemberScoreVO1> getMemberScores();


	/**
	 * @methodname getMemberScorePage 的描述：商城个人积分流水分页查询
	 * @param page
	 * @param scorePageRequest
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/4/26
	 */
	Result getMemberScorePage(PageBean page, ScorePageDTO scorePageRequest);

	/**
	 * 
	* @Title: getMemberCostScore
	* @Description: 获取会员消费积分
	* @author 王强
	* @date  2017年4月11日 下午2:12:22
	* @return
	 */
	public List<MemberCostScoreVO> getMemberCostScore();
	/**
	 * 
	 * @Title: updateMemberScoreByReward   
	 * @Description: 修改会员奖励积分   
	 * @param: @param memberId
	 * @param: @param rewardScore      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	void updateMemberScoreByReward(Long memberId,long rewardScore);
	/**
	 * @Title: getMemberSingleScoreInfo
	 * @Description: 查询积分信息   
	 * @param: @param memberId
	 * @param: @param page
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMemberSingleScoreInfo(QuerySingleScoreInfoDTO dto,PageBean page);
	/**
	 * 
	 * @Title: getAndupdateMemberScore  
	 * @Description: 获取并更新会员积分
	 * @param @param memberId
	 * @param @return   
	 * @return MemberScoreVO2  
	 * @author 李治桦
	 * @param orderNo 
	 * @param orderMoney 
	 * @throws Exception 
	 */
	public Result getAndupdateMemberScore(Long memberId, Long orderMoney, String orderNo) throws Exception;

	Result getShareMerchantScoreDetail(long memberId,int transCode,int pageNo,int pageSize);

	public String getToken(Long id);

    Result getMerchantScoreDeatil(Long userId,Integer pageNo,Integer pageSize);
}
