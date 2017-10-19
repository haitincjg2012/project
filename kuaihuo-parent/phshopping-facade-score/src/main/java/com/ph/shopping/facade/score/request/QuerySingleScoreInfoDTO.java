/**  
 * @Title:  QuerySingleScoreInfoDTO.java   
 * @Package com.ph.shopping.facade.score.request   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午1:54:33   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.score.request;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.facade.score.senum.ScoreTypeEnum;

/**   
 * @ClassName:  QuerySingleScoreInfoDTO   
 * @Description:查询积分数据   
 * @author: 李杰
 * @date:   2017年7月26日 下午1:54:33     
 * @Copyright: 2017
 */
public class QuerySingleScoreInfoDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -3907867485521544666L;
	/**
	 * 会员ID
	 */
	@NotNull(message="会员ID不能为空")
	private Long memberId;
	/**
	 * 查询类型
	 */
	@NotNull(message="查询类型不能为空")
	private ScoreTypeEnum scoreType;
	/**
	 * 是否只查询列表
	 */
	private boolean isQueryList;
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public ScoreTypeEnum getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreTypeEnum scoreType) {
		this.scoreType = scoreType;
	}

	public boolean isQueryList() {
		return isQueryList;
	}

	public void setQueryList(boolean isQueryList) {
		this.isQueryList = isQueryList;
	}
	
}
