package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import com.ph.shopping.facade.profit.dto.UnLineOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.PromoterProfitRecord;
import com.ph.shopping.facade.profit.entity.SupplyProfitTotal;
import com.ph.shopping.facade.profit.entity.UnlineOrderProfit;
import com.ph.shopping.facade.profit.vo.MemberScoreVO;
import com.ph.shopping.facade.profit.vo.UnLineOrderProfitVO;
import com.ph.shopping.facade.profit.vo.UnLineOrderVO;
import com.ph.shopping.facade.spm.entity.Agent;
import org.apache.ibatis.annotations.Param;

/**
 * 
* @ClassName: IUnlineOrderProfitMapper
* @Description: 线下订单分润mapper
* @author Mr.Dong
* @date 2017年5月9日 下午5:11:45
 */
public interface IUnlineOrderProfitMapper {
	/**
	 * 
	 * @Title: unLineOrderProfitList
	 * @Description: 线下订单分润list
	 * @author Mr.Dong
	 * @date 2017年5月22日 下午4:16:44
	 * @param unLineOrderProfitDTO
	 * @return List<UnLineOrderProfitVO>
	 */
	public List<UnLineOrderProfitVO> unLineOrderProfitList(UnLineOrderProfitDTO unLineOrderProfitDTO);
	/**
	 * 
	* @Title: getUnLineOrderList
	* @Description: 取到要分润的线下订单
	* @author Mr.Dong
	* @date  2017年5月23日 上午10:37:29 
	* @return
	 */
	public List<UnLineOrderVO> getUnLineOrderList();
	
	
	/**
	 * 
	* @Title: insertUnlineOrderProfitBatch
	* @Description: TODO(批量入线下订单分润记录表)
	* @author Mr.Dong
	* @date  2017年5月24日 上午11:00:33
	* @param unLineOrderProfitList
	* @return
	 */
	public int  insertUnlineOrderProfitBatch(List<UnlineOrderProfit> unLineOrderProfitList);
	
	/**
	 * 
	* @Title: updateUnlineOrderBonus
	* @Description: TODO(改线下订单中是否分润)
	* @author Mr.Dong
	* @date  2017年5月24日 上午11:29:52
	* @param unLineOrderProfitList
	* @return
	 */
	public int updateUnlineOrderBonus(List<UnlineOrderProfit> unLineOrderProfitList);
	
	/**
	 * 
	* @Title: updateMemberScore
	* @Description: TODO(更新会员可用积分)
	* @author Mr.Dong
	* @date  2017年5月24日 下午2:48:41  ph_score_income_trade
	* @param memberScoreVOList
	* @return
	 */
	public int updateMemberScore(MemberScoreVO memberScoreVO);  

	/**
	 * 
	* @Title: updateMemberProfitScore
	* @Description: TODO(更新会员分润积分)
	* @author liuy
	* @date  2017年7月25日 11:39 
	* @param memberScoreVOList
	* @return
	 */
	public int updateMemberProfitScore(MemberScoreVO memberScoreVO); 

	/**
	 * 
	* @Title: updateMemberProfitScore
	* @Description: TODO(更新会员推广商户奖励积分)
	* @author liuy
	* @date  2017年7月25日 11:39 
	* @param memberScoreVOList
	* @return
	 */
	public int updateMerchantRewardScore(MemberScoreVO m);
	
	/**
	 * 
	* @Title: insertMemberScoreTotal
	* @Description: TODO(入积分流水总表)
	* @author Mr.Dong
	* @date  2017年5月24日 下午3:21:26
	* @param memberScoreVO
	* @return
	 */
	public int insertMemberScoreTotal(MemberScoreVO memberScoreVO);
	
	/**
	 * 
	* @Title: insertMemberScoreIncome
	* @Description: TODO(入积分流水进账表)
	* @author Mr.Dong
	* @date  2017年5月24日 下午3:21:46
	* @param memberScoreVO
	* @return
	 */
	public int insertMemberScoreIncome(MemberScoreVO memberScoreVO);
	
	/**
	 * 批量入推广师分润记录表
	* @Title: insertPromoterProfitRecord
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月22日 下午7:56:34
	* @param ppr
	* @return
	 */
	public int insertPromoterProfitRecord(List<PromoterProfitRecord>   ppr);

	/**
	 * 批量入会员分润记录表
	* @Title: insertPromoterProfitRecord
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author liuy
	* @date  2017年7月25日 
	* @param ppr
	* @return
	 */
	public void insertMemberProfitRecord(List<PromoterProfitRecord> mpr);
	
	/**
	 * 批量入用户分润记录表
	* @Title: insertUserProfitRecord
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月4日 下午3:00:10
	* @param spt
	* @return
	 */
	public int insertUserProfitRecord(List<SupplyProfitTotal> spt) ;
	
	/**
	* @Title: insertUserProfitRecord
	* @Description: 批量更改线下子订单表中的分润状态为会员分润
	* @author liuy
	* @date  2017年7月25日
	* @param spt
	* @return
	 */
	public int updateIsProfitToMemberProfit(List<UnlineOrderProfit> memberProfitList);

	/**
	* @Title: insertUserProfitRecord
	* @Description: 批量更改会员积分表中的会员分润积分为0
	* @author liuy
	* @date  2017年7月25日 
	* @param spt
	* @return
	 */
	public int updateMemberProfitScoreToZero(List<UnlineOrderProfit> promotionProfitList);
	
	
	/**
	 * @param orderNo
	 * @return UnLineOrderVO
	 * @Description: 根据订单号查询订单
	 * @date 2017年8月23日14:56:16
	 */
	public UnLineOrderVO getOrderByOrderNo(String orderNo);
	
	/**
	 *  获取待返积分订单详情
	 * @return
	 */
	public List<Map<String, Object>> getRecordByOrder();
	
	/**
	 * 更新订单返还积分状态为成功
	 * @param id
	 */
	public int updateOrderBackScore(Long id);

	public int updateOrderIsProfitById(Long id);

	public int updateMemberScoreByTransCode1(MemberScoreVO memberScoreVO);

	public int updateMemberScoreByTransCode2(MemberScoreVO memberScoreVO);

	public int updateMemberScoreByTransCode3(MemberScoreVO memberScoreVO);

	public Map<String, Object> getMemberScoreById(Long id);

	public Map<String, Object> getStoreManagerByUserId(Long id);

	public Map<String, Object> getMemberByStoreManager(String telPhone);

	public Agent getAgentByCityId(@Param("id") Long id);

	public Agent getAgentByCountyId(@Param("id") Long id);
}
