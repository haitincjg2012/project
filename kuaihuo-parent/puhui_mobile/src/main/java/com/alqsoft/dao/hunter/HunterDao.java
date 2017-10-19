package com.alqsoft.dao.hunter;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import com.alqsoft.entity.hunter.Hunter;

@MyBatisRepository
public interface HunterDao {

	Map<String, Object> getHunterById(Long hunterId);
	
	/**
	 * 批发商个人中心
	 * @param hunterid
	 * @return
	 */
	Map<String, Object> getHunterCenterById(Long hunterid);
	/**
	 * 根据id查询信息
	 */
	Hunter getById(@Param("id") Long id);

	List<Map<String, Object>> getHunterByTownId(@Param("townId") Long townId);
	
	/**
	 * 根据行业类别查询批发商
	 * @param industryid
	 * @return
	 */
	List<Map<String,Object>> findHunterByIndustryTypeId(Map<String, Object> params);
	
    List<Map<String,Object>> getHuntersBySubject(Map<String, Object> map);
    /**
	 * 检验昵称是否已存在
	 */
	List<Map<String, Object>> getHunterByNickname(String nickname);
	/**
	 * 根据批发商id获取店铺背景图片信息
	 */
	Map<String, Object> getBackgroundImageById(Long hunterId);
	/***
	 * 根据批发商id获取批发商的手机号
	 * @param hunterId
	 * @return
	 */

	String getPhoneByHunterId(@Param("hunterId") Long hunterId);
	/**
	 * 判断该用户和该批发商是否被关注
	 * @param mId
	 * @param hId
	 * @return
	 */
	Map<String,Object> getHunterFoucsType(@Param("mId") Long mId, @Param("hId") Long hId);

	/**
	 * 获取批发商头像
	 * @param hunterId
	 * @return
	 */
	Map<String,Object> getHunterLogoImage(Long hunterId);
}
