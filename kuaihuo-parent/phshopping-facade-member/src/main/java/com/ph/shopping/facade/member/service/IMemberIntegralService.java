package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberIntegralDTO;

/**
 * 
* @ClassName: IMemberIntegralService  
* @Description:会员积分 
* @author tony  
* @date 2017年3月23日  
*
 */
public interface IMemberIntegralService {

	/**
	 * 
	* @Title: getMemberIntegralInfoByPage  
	* @Description: 根据分页信息查询会员积分数据
	* @param @param dto
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	Result getMemberIntegralInfoLsitByPage(MemberIntegralDTO dto);
	/**
	 * 
	* @Title: getMemberIntegralInfoList  
	* @Description: 查询会员积分数据 
	* @param @param dto
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	Result getMemberIntegralInfoList(MemberIntegralDTO dto);
	/**
	 * 
	* @Title: getMemberIntegralSource  
	* @Description: 获取积分来源字典数据
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	Result getMemberIntegralSource();
}
