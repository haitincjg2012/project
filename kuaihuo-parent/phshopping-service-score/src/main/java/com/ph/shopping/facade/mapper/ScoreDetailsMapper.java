package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import com.ph.shopping.facade.score.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 王振
 *  积分接口
 */
public interface ScoreDetailsMapper {


    /**
     * 会员积分
     * @param memberId
     * @return
     */
    MemberScoreVO2 getScore(long memberId);

    /**
     * 当日提现金额
     * @param map
     * @return
     */
    Long dayCashScore(Map<String, Object> map);

    /**
     * 积分流水明细
     * @return
     */
    List<Map<String,Object>> getScoreDetail(@Param("id")long id, @Param("transCode")int transCode, @Param("rowBounds")RowBounds rowBounds);

    /**
     *
     * @Title: updateMemberScore
     * @Description:更新用户积分
     * @author 王强
     * @date 2017年3月17日 下午5:39:13
     * @param memberId
     * @param standbyScore
     * @param enableScore
     * @return
     * @throws Exception
     */
    public int updateMemberScore(@Param("memberId") long memberId, @Param("standbyScore") long standbyScore,
                                 @Param("enableScore") long enableScore, @Param("drawcashScore") long drawcashScore) throws Exception;

    /**
     *
     * @Title: insertMemberTrade
     * @Description: 插入总流水
     * @author 王强
     * @date 2017年3月17日 下午5:42:34
     * @return
     */
    // public int insertMemberTrade(@Param("memberId") long memberId,
    // @Param("transCode") int transCode, @Param("score") long score,
    // @Param("createTime") Date createTime);
    public int insertMemberTrade(PhScoreTotalTrade totalTrade);

    /**
     *
     * @Title: insertIncomeTrade
     * @Description: 记入收入流水
     * @author 王强
     * @date 2017年3月17日 下午8:02:37
     * @param memberId
     * @param transCode
     * @param score
     * @param setId
     * @return
     */
    public int insertIncomeTrade(@Param("memberId") long memberId, @Param("transCode") int transCode,
                                 @Param("score") long score, @Param("setId") long setId, @Param("orderNo") String orderNo,
                                 @Param("handingCharge") long handingCharge);

    /**
     *
     * @Title: insertCostTrade
     * @Description: 记入支出流水
     * @author 王强
     * @date 2017年3月17日 下午8:02:59
     * @param memberId
     * @param transCode
     * @param score
     * @param setId
     * @return
     */
    public int insertCostTrade(@Param("memberId") long memberId, @Param("transCode") int transCode,
                               @Param("score") long score, @Param("setId") long setId, @Param("orderNo") String orderNo,
                               @Param("handingCharge") long handingCharge);

    /***
     *
     * @Title: countOnLineMoney
     * @Description: 统计线上订单金额
     * @author 王强
     * @date 2017年3月22日 下午2:04:28
     * @param memberId
     * @return
     */
    public long countOnLineMoney(@Param("memberId") long memberId);

    /**
     *
     * @Title: countUnLineMoney
     * @Description: 统计线下订单金额
     * @author 王强
     * @date 2017年3月22日 下午2:35:23
     * @param memberId
     * @return
     */
    public long countUnLineMoney(@Param("memberId") long memberId);

    /**
     *
     * @Title: getMemberLevel
     * @Description: 获取用户等级
     * @author 王强
     * @date 2017年3月23日 下午4:49:19
     * @return
     */
    public int getMemberLevel(@Param("memberId") long memberId);

    /**
     *
     * @Title: getMemberScores
     * @Description: 查询会员积分信息
     * @author 王强
     * @date 2017年3月23日 下午6:40:32
     * @return
     */
    public List<MemberScoreVO1> getMemberScores();

    /**
     *
     * @Title: getMemberScorePage
     * @Description: 查询会员积分分页查询
     * @author zhengpeng
     * @date 2017年3月31日
     * @return
     */
    public List<MemberScoreShopVO> getMemberScorePage(@Param("memberId") Long memberId);

    /**
     *
     * @Title: getMemberCostScore
     * @Description: 获取会员总消费积分
     * @author 王强
     * @date 2017年4月11日 下午2:08:11
     * @return
     */
    public List<MemberCostScoreVO> getMemberCostScore();

    /**
     *
     * @Title: updateOrderIsBackScore1
     * @Description: 更新线上订单返积分状态
     * @author 王强
     * @date 2017年4月26日 下午5:46:31
     * @param orderNo
     * @return
     * @throws Exception
     */
    public int updateOrderIsBackScore1(@Param("orderNo") String orderNo) throws Exception;

    /**
     *
     * @Title: updateOrderIsBackScore2
     * @Description: 更新线下订单返积分状态
     * @author 王强
     * @date 2017年4月26日 下午5:46:39
     * @param orderNo
     * @return
     * @throws Exception
     */
    public int updateOrderIsBackScore2(@Param("orderNo") String orderNo) throws Exception;

    /**
     * @Title: updateMemberScoreByReward
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param memberId
     * @param: @param rewardScore
     * @param: @return
     * @return: int
     * @author：李杰
     * @throws
     */
    public int updateMemberScoreByReward(@Param("memberId") Long memberId, @Param("rewardScore") long rewardScore);

    /**
     * @Title: getCashScore
     * @Description: 查询现金余额数据
     * @param: @param memberId
     * @param: @return
     * @return: MemberSingleScoreVO
     * @author：李杰
     * @throws
     */
    public Long getAccumulativeScore(Map<String, Object> map);

    /**
     * @Title: getTodayScore
     * @Description: 查询当日到账积分
     * @param: @param map
     * @param: @return
     * @return: Long
     * @author：李杰
     * @throws
     */
    public Long getTodayScore(Map<String, Object> map);

    /**
     * @Title: selectScoreRecordsByList
     * @Description: 查询
     * @param: @param map
     * @param: @return
     * @return: List<MemberScoreRecordVO>
     * @author：李杰      可用积分记录
     * @throws
     */
    public List<MemberScoreRecordVO> selectScoreRecordsByList(Map<String, Object> map);

    /**
     * @Title: isPromotionByMemberId
     * @Description:判断会员是否是推广师
     * @param: @param memberId
     * @param: @return
     * @return: int
     * @author：李杰
     * @throws
     */
    public int isPromotionByMemberId(Long memberId);


    /**
     * 查询用户token
     */
	String getToken(Long id);

    /**
     * 查询商户余额明细
     */
    List<Map<String,Object>> getMerchantScoreDetail(@Param("userId") Long userId,@Param("rowBounds") RowBounds rowBounds);

    Long getCurrentDayScore(Map<String, Object> map);
}
