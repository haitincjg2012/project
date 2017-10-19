package com.alqsoft.service.member;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

/**
 * @author cjd
 * @tiem 2017年3月1日下午4:50:15
 */
public interface MemberService {

	Member getMemberByIdAndUuid(Map<String, Object> params);
	
	/**
	 * 用户个人中心
	 * @param memberid
	 * @return
	 */
	Map<String,Object> getMemberCenterById(Long memberid);

	Long  getHunteridByMemberid(Long id);
	
	/**
	 * 用户个人中心
	 * @param memberid
	 * @return
	 */
	Result getMemberCenter(Long memberid);

	List<Map<String, Object>> getMemberByPhone(String newphone);

	
	Member getMemberByHunterId(Long hunterid);

	/**
	 * 通过IMid返回用户的昵称
	 * @param member
	 * @param imId
	 * @return
	 */
	Result getNickNameByImid(Member member, String imId);

    Map<String,Object> getMemberLogoImage(Long mid);
}
