package com.ph.shopping.facade.mapper;


import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.vo.MemberVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SalesmanMapper {

	//查询用户
	MemberDTO getMemberByTelPhone(String telPhone);

	// 将用户升级为业务员
	boolean updateMemberBecomeSaleman(MemberDTO memberDto);

	// 添加业务员
	boolean addSaleman(MemberDTO memberDto);

	// 查询代理对应的业务员
	List<MemberVO> getSalesmanByAgentId(Long id);

	// 查询业务员推广企业
	List<MerchantVO> getShareCompanyBySalesmanId(Long id);

	// 冻结业务员
	boolean frozenSalesman(@Param("id")Long id,@Param("isFrozen") Integer isFrozen);

	// 根据id查询业务员
	//MemberDTO getSalesmanById(Long id);

	//返回数据
	public List<Map> saleManDataList(Map<String, Object> map);

	//通过手机获取id,findMemberIdByPhone(phone,card,name)
	public Map findMemberIdByPhone(@Param("phone") String phone);



	//保存Member
	public void addMember(@Param("name") String name,@Param("phone") String phone,@Param("card") String card,@Param("gId") Long gId);

	//通过用户名获取agentId
	public Map findAgentIdByUserName(@Param("username") String username);

	//通过id修改冻结状态
	public Map findDataById(@Param("id")Long id);


	public void updateFrozenTypeById(@Param("type")Long type,@Param("id")Long id);





}
