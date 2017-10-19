package com.alqsoft.dao.member;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.member.Member;

@MyBatisRepository
public interface MemberDao {

	Member getMemberByIdAndUuid(Map<String, Object> params);
	
	/**
	 * 用户个人中心数据接口
	 * @param memberid
	 * @return
	 */
	Map<String,Object> getMemberCenterById(Long memberid);

	List<Map<String, Object>> getMemberByPhone(String newphone);

	 Long  getHunteridByMemberid(Long id);
	
	/**
	 * 根据批发商id查询memberid
	 * @param hunterid
	 * @return
	 */
	Member getMemberByHunterId(Long hunterid);

	/**
	 * 通过imId获取昵称
	 * @param imId
	 * @return
	 */
	Map<String, Object> getNickNameByImid(String imId);


    Map<String,Object> getMemberLogoImage(Long mid);
}
