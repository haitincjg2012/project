package com.alqsoft.dao.hunterstore;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.alqframework.result.Result;

/**
 * 批发商店铺
 * @author Administrator
 *
 */
@MyBatisRepository
public interface HunterStoreDao {

	/**
	 * 查询批发商的店铺列表
	 * @param hunterid
	 * @return
	 */
	public List<Map<String,Object>> findHunterStoreList(Long hunterid);
	
	/**
	 * 查询批发商店铺详情
	 * @param hunterid
	 * @return
	 */
	public Map<String,Object> getHunterStoreById(Long id);
	/**
	 * 批发商店铺列表
	 * @return
	 */
	public List<Map<String,Object>> getHunterStoreList();
	
	/**
	 * 查询给批发商的店铺有多少张图片，限制不超过6张
	 * @param hunterid
	 * @return
	 */
	public int getHunterStoreCountByHunterId(Long hunterid);
}
