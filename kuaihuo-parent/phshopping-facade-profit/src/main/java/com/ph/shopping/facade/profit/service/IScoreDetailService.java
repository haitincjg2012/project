package com.ph.shopping.facade.profit.service;

import java.util.List;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.ScoreDetailDTO;
import com.ph.shopping.facade.profit.vo.ScoreDetailVO;

/**
 * 
* @ClassName: IScoreDetailService
* @Description: 积分明细接口
* @author 王强
* @date 2017年6月7日 上午11:41:43
 */
public interface IScoreDetailService {
	/**
	 * 
	* @Title: getScoreDetail
	* @Description: 积分明细
	* @author 王强
	* @date  2017年6月7日 下午2:00:50
	* @param scoreDetailDTO
	* @return
	 */
	Result getScoreDetail(ScoreDetailDTO scoreDetailDTO);
	/**
	 * 
	* @Title: getScoreDetailExport
	* @Description: 导出数据
	* @author 王强
	* @date  2017年6月7日 下午6:26:01
	* @param scoreDetailDTO
	* @return
	 */
	List<ScoreDetailVO> getExportData(ScoreDetailDTO scoreDetailDTO);
}
