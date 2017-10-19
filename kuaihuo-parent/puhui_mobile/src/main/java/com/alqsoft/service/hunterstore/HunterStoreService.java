package com.alqsoft.service.hunterstore;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

/**
 * 批发商店铺
 * @author Administrator
 *
 */
public interface HunterStoreService {

	/**
	 * 查询批发商的店铺列表
	 * @param hunterid
	 * @return
	 */
	public Result findHunterStoreList(Long hunterid);
	
	/**
	 * 查询批发商店铺详情
	 * @param hunterid
	 * @return
	 */
	public Result getHunterStoreById(Long id);
	
	/**
	 * 保存或编辑店铺
	 * @param id
	 * @param address
	 * @param content
	 * @return
	 */
	public Result saveHunterStore(Long id,Long hunterid,String address,String content);
	
	/**
	 * 根据店铺详情id删除
	 * @param id
	 * @return
	 */
	public Result deleteHunterStore(Long id,Long hunterid);
	/**
	 * 批发商店铺列表
	 * @return
	 */
	public Result getHunterStoreList();
	
}
