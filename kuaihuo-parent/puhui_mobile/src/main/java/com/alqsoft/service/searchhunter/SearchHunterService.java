package com.alqsoft.service.searchhunter;

import org.alqframework.result.Result;
import org.hibernate.id.IncrementGenerator;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年3月29日 下午11:37:14
 * 
 */
public interface SearchHunterService {
    /***
     * 
     * @param name  查询的模糊数据
     * @param currentPage 当前页
     * @param numPage 当前页的数据
     * @return
     */
	public Result getSearchHunterByNameOrProduct(String name,Integer currentPage,Integer numPage,Integer sort,String longitude,String latitude);
	/***
	 * 
	 * @param hId 批发商id
	 * @param name 查询的模糊数据
	 * @param currentPage 当前页
	 * @param numPage 当前页的数据
	 * @return
	 */
	public Result getSearchProductByName(Long hId,String name,Integer currentPage,Integer numPage);
	/***
	 * 
	 * @param name         模糊数据
	 * @param currentPage  当前页
	 * @param numPage      当前页的数据
	 * @param sort         条件查询
	 * @param longitude    经度
	 * @param latitude     维度
	 * @return
	 */
	public Result getSearchHunterAddtudeByNameOrProduct(String name,Integer currentPage,Integer numPage,Integer sort,String longitude,String latitude);
}
