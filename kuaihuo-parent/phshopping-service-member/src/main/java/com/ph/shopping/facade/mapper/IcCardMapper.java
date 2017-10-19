package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.entity.IcCardInfo;
import com.ph.shopping.facade.member.entity.IcMemberBind;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;

import java.util.List;
import java.util.Map;

public interface IcCardMapper extends BaseMapper<IcCardInfo>{

	/**
	 * 
	* @Title: selectIcCardInfoByCode  
	* @Description: 根据条形码获取会员卡信息 
	* @param @param barCode
	* @param @return    参数  
	* @return IcCardInfo    返回类型  
	* @throws
	 */
	List<IcCardInfo> selectIcCardInfoByCode(Map<String, Object> map);
	/**
	 * 
	* @Title: insertIcMemberBind  
	* @Description: 绑定会员卡 新增数据  
	* @param @param memberBind    参数  
	* @return void    返回类型  
	* @throws
	 */
	void insertIcMemberBind(IcMemberBind memberBind);
	/**
	 * 
	* @Title: selectMemberCarBind  
	* @Description: 根据会员ID 查询绑定的会员卡  
	* @param @param id
	* @param @return    参数  
	* @return IcMemberBind    返回类型  
	* @throws
	 */
	IcMemberBind selectMemberCarBind(IcMemberBind icmember);
	/**
	 * 
	* @Title: updateMemeberIcCardBind  
	* @Description: 修改会员卡绑定数据表 
	* @param @param memberBind    参数  
	* @return void    返回类型  
	* @throws
	 */
	void updateMemeberIcCardBind(IcMemberBind memberBind);
	/**
	 * 
	* @Title: selectMemberBindCardByPage  
	* @Description: 获取绑定改的会员卡数据
	 * @param @param dto
	 * @param @return    参数
	 * @return List<MemberCardInfoVO>    返回类型
	 * @throws
	 */
	List<MemberCardInfoVO> selectMemberBindCardByPage(MemberCardInfoDTO memberCardInfoDTO);
	
	/**
	 * 
	* @Title: selectMemberBindCardInfoByPhone  
	* @Description:根据手机号查询数据 
	* @param @param phone
	* @param @return    参数  
     * @return MemberCardInfoVO    返回类型
     * @throws
	 */
	List<MemberCardInfoVO> selectMemberBindCardInfoByPhone(String phone);
	/**
	 * 
	* @Title: selectMemberCardBindNum  
	* @Description: 判断会员卡是否被绑定 
	* @param @param map
	* @param @return    参数  
	* @return Integer    返回类型  
	* @throws
	 */
	int selectMemberCardBindNum(Map<String, Object> map);
	/**
	 * 
	* @Title: updateCardBindInfoByIcId  
	* @Description: 根据会员卡ID修改数据  
	* @param @param icCardId    参数  
	* @return void    返回类型  
	* @throws
	 */
	void updateCardBindInfoByIcId(IcMemberBind memberBind);
	/**
	 * 
	* @Title: selectMemberCardByPage  
	* @Description: 加载会员卡列表数据  
	* @param @param map
	* @param @return    参数  
     * @return List<MemberCardInfoVO>    返回类型
     * @throws
	 */
	List<MemberCardInfoVO> selectMemberCardByPage(Map<String, Object> map);
	/**
	 * 
	* @Title: selectIcCardCountInfoByCode  
	* @Description: 判断卡号是否存在   
	* @param @param map
	* @param @return    参数  
	* @return int    返回类型  
	* @throws
	 */
	int selectIcCardCountInfoByCode(Map<String, Object> map);
	/**
	 * 
	* @Title: selectIcinnerCodeByInnerCodes  
	* @Description: 获取所有已存在的 innercode 
	* @param @param list
	* @param @return    参数  
	* @return List<String>    返回类型  
	* @throws
	 */
	List<String> selectIcinnerCodeByInnerCodes(List<String> list);
	/**
	 * 
	* @Title: selectIcinnerCodeByInnerCodes  
	* @Description: 获取所有已存在的 outercode 
	* @param @param list
	* @param @return    参数  
	* @return List<String>    返回类型  
	* @throws
	 */
	List<String> selectIcOuterCodeByOuterCodes(List<String> list);
	/**
	 * 
	* @Title: batchInsertCard  
	* @Description: 批量新增
	* @param @param list    参数  
	* @return void    返回类型  
	* @throws
	 */
	void batchInsertCard(List<IcCardInfo> list);
	/**
	 * 
	* @Title: selectMemberCardInfoByCode  
	* @Description: 根据会员卡查询会员信息  
	* @param @param map
	* @param @return    参数  
     * @return MemberCardInfoDTO    返回类型
     * @throws
	 */
	MemberCardInfoDTO selectMemberCardInfoByCode(Map<String, Object> map);
}
