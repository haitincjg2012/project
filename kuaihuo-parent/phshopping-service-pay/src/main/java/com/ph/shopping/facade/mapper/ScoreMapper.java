package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @ClassName: ScoreMapper
 * @Description: 积分模块mapper
 * @author 王强
 * @date 2017年4月26日 下午5:46:00
 */
@Repository
public interface ScoreMapper {



	/**
	 * @methodname updateMemberScore 的描述：更新用户积分
	 * @param memberId
	 * @param standbyScore
	 * @param enableScore
	 * @param drawcashScore
	 * @return int
	 * @author 郑朋
	 * @create 2017/7/11
	 */
	int updateMemberScore(@Param("memberId") long memberId, @Param("standbyScore") long standbyScore,
                                 @Param("enableScore") long enableScore, @Param("drawcashScore") long drawcashScore);


	/**
	 * @methodname insertMemberTrade 的描述：插入总流水
	 * @param totalTrade
	 * @return int
	 * @author 郑朋
	 * @create 2017/7/11
	 */
	int insertMemberTrade(PhScoreTotalTrade totalTrade);

	/**
	 * @methodname insertCostTrade 的描述：记入支出流水
	 * @param memberId
	 * @param transCode
	 * @param score
	 * @param setId
	 * @param orderNo
	 * @param handingCharge
	 * @return int
	 * @author 郑朋
	 * @create 2017/7/11
	 */
	int insertCostTrade(@Param("memberId") long memberId, @Param("transCode") int transCode,
                               @Param("score") long score, @Param("setId") long setId, @Param("orderNo") String orderNo,
                               @Param("handingCharge") long handingCharge);


	/**
	 * @methodname insertIncomeTrade 的描述：记录收入流水
	 * @param memberId
	 * @param transCode
	 * @param score
	 * @param setId
	 * @param orderNo
	 * @param handingCharge
	 * @return int
	 * @author 郑朋
	 * @create 2017/7/12
	 */
	int insertIncomeTrade(@Param("memberId") long memberId, @Param("transCode") int transCode,
								 @Param("score") long score, @Param("setId") long setId, @Param("orderNo") String orderNo,
								 @Param("handingCharge") long handingCharge);

}
