/**
 * 
 */
package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.dto.MemberShareRecordDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.vo.MemberShareRecordVO;
import com.ph.shopping.facade.member.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * phshopping-service-member
 *
 * @description：会员相关操作接口
 *
 * @author：liuy
 *
 * @createTime：2017年5月27日
 *
 * @Copyright @2017 by liuy
 */
public interface MemberMapper extends BaseMapper<Member>{

	/**
     * 分页获取会员列表  
     * @param dto 查询条件  
	 * @return List<MemberVO> 返回类型  
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	List<MemberVO> selectMemberListByPage(MemberDTO dto);

	/**
     * 获取会员列表（不分页）
     * @param dto 查询条件  
	 * @return List<Member> 返回类型  
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	List<Member> selectMemberList(MemberDTO dto);

	/**
     * 根据手机号查询会员信息  
     * @param mobile 手机号
	 * @return Member 返回类型  
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Member selectMemberInfoByMobile(String mobile);

	/**
     * 根据手机号判断会员是否存在  
     * @param mobile 手机号
	 * @return Member 返回类型  
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	int selectMemberIsExistByPhone(String telPhone);

	/**
     * 查询推广师（会员）是否可以分润 
     * @param dto
	 * @author liuy
	 * @return result 返回推广师（会员）对象
	 * @createTime 2017年05月24日
     */
	public Member selectPromotionIsCanProfit(MemberDTO dto);

	/**   
	 * @Title: updateEquipmentIdByMobile   
	 * @Description: 修改设备ID   
	 * @param: @param map      
	 * @return: void
	 * @author：李杰      
	 * @throws   
	 */ 
	void updateEquipmentIdByMobile(Map<String, Object> map);

	/**   
	 * @Title: isMerchantByTelPhone   
	 * @Description: 根据手机号校验商户是否存在   
	 * @param: @param toTelPhone
	 * @param: @return      
	 * @return: int
	 * @author：李杰      
	 * @throws   
	 */ 
	int isMerchantByTelPhone(String toTelPhone);

	int isMerchantById(Long memberid);

	/**   
	 * @Title: selectMemberShareByList   
	 * @Description: 查询分享记录   
	 * @param: @param dto
	 * @param: @return      
	 * @return: List<MemberShareRecordVO>
	 * @author：李杰      
	 * @throws   
	 */ 
	List<MemberShareRecordVO> selectMemberShareByList(MemberShareRecordDTO dto);

	List<Map> getIndexAdAttachDao(Integer startInt, Integer endInt);

	Map getTextDetail(Long id);

	MemberVO selectMemberInfoByMobileVO(String telPhone);

	/**
	 * 通过昵称来查找用户
	 * @param nikeName
	 * @return
	 */
	List<Map> getNikeName(String nikeName);

	/**
	 * 修改用户昵称
	 * @param memberid
	 * @param nikeName
	 */
	void updateNikeName(@Param("memberid") Long memberid,@Param("nikeName") String nikeName);

	Map getMarkindByPhone(String phone);

	Map<String, Object> selectMerchantByTelPhone(String phone);
	void updateMerchantPromoterIdByTelPhone(Map<String, Object> map);
}
