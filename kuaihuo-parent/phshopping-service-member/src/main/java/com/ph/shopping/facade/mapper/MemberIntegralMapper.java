package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.member.dto.MemberIntegralDTO;
import com.ph.shopping.facade.member.vo.MemberIntegralSourceVO;

public interface MemberIntegralMapper {

	/**
	 * 
	* @Title: selectMemberIntegralListInfo  
	* @Description: 查询会员积分数据列表  
	* @param @param dto
	* @param @return    参数  
     * @return List<MemberIntegralDTO>    返回类型
     * @throws
	 */
	List<MemberIntegralDTO> selectMemberIntegralListInfo(MemberIntegralDTO dto);
	/**
	 * 
	* @Title: selectMemberIntegralSource  
	* @Description:查询会员积分来源字典数据  
	* @param @return    参数  
     * @return List<MemberIntegralSourceVO>    返回类型
     * @throws
	 */
	List<MemberIntegralSourceVO> selectMemberIntegralSource();
	
	/**
     * 新增会员积分表数据（新增会员时），这里单独写一个，没有调用积分接口的方法
     * 因为在会员Service调用积分Service，涉及到事物回滚，但是新增积分表无法最后执行，后面还会调用短信发送。
     * @param dto 会员积分对象
	 * @author 李杰
	 * @createTime 2017年3月14日
	 * @updateTime liuy重构 2017年05月16日
     */
	void insertMemberScore(MemberIntegralDTO dto);
}
