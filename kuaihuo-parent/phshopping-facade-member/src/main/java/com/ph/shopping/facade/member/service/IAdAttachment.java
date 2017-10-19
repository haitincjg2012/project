package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;

/**
 *
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年8月28日 下午5:09:53
 *
 */
public interface IAdAttachment {
	/**
	 * 获取首页
	 * @param longitude
	 * @param latitude
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public Result getShowDataAdAttachment(String longitude,String latitude,Integer page,Integer pagesize,String localAddress);
	/**
	 * 附近商店
	 * @param longitude
	 * @param latitude
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public Result getNearByShopping(String longitude,String latitude,Integer page,Integer pagesize,Long industry1,Long industry2);
	/**
	 * 获取一二分类
	 * @param type 1是一级，2是2级
	 * @return
	 */
	public Result getAllIndustry(Integer type,Long parentId);

	/**
	 *
	 * @param datas      将要查询的数据
	 * @param page       当前页
	 * @param pagesize   当前页的数据
	 * @return
	 */
	public Result findData(String datas,Integer page,Integer pagesize,String longitude,String latitude);

	/**
	 * 手机更新版本状态
	 * @return
	 */
	public Result getAppVersion(Integer type, String client_type);
}
