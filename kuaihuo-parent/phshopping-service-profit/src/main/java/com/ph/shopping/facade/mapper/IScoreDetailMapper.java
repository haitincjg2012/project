package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.member.entity.MemberDrawcashRecord;
import com.ph.shopping.facade.profit.dto.ScoreDetailDTO;
import com.ph.shopping.facade.profit.vo.ScoreDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
* @ClassName: IScoreDetailMapper
* @Description: 积分明细mapper
* @author 王强
* @date 2017年6月6日 上午11:05:34
 */
@Repository
public interface IScoreDetailMapper{
	/**
	 * 
	* @Title: getScoreDetails
	* @Description: 积分明细
	* @author 王强
	* @date  2017年6月7日 下午2:20:39
	* @param scoreDetailDTO
	* @return
	 */
	List<ScoreDetailVO> getScoreDetails(ScoreDetailDTO scoreDetailDTO);

	/**
	 * @methodname updateDrawCashScore 的描述：添加提现积分
	 * @param memberDrawcashRecord
	 * @return int
	 * @author 郑朋
	 * @create 2017/7/6
	 */
	int updateDrawCashScore(MemberDrawcashRecord memberDrawcashRecord);
}
