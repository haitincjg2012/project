package com.alqsoft.service.industrytype;

import java.util.Map;

import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-02 13:47
 * @see
 * @since 1.8
 */
public interface IndustryTypeService {

    Result   getIndustryTypeList(Long pid,Long type);
    
    public Result getAllIndustryTypeFirst();
     
    public Result getAllIndustryTypeSecond(Integer id);
    
    /**
     * 根据分类id查询分类信息   isdelete=0
     * @param id
     * @return
     */
    public Map<String,Object> getIndustryTypeById(Long id);
    /**
     * 获取第一,二级分类
     */
	public Result findAllIndustryType();
	/**
     * 根据1级分类id获取二级分类
     */
	public Result findAllIndustryType(int panrentId, int page, int rows);
	/**
     * 根据分类获取批发商信息
	 * @param latitude 
	 * @param longitude 
     */
	public Result findHunterByiIndustryId(String fistTypeId, String secondTypeId, int page, int rows, String longitude, String latitude);
	/**
     * 获取一级分类下的所有批发商信息
	 * @param latitude 
	 * @param longitude 
     */
	public Result findAllHunterByiIndustryId(String fistTypeId, int page, int rows, String longitude, String latitude);
   /***
    * 获取一级分类，最多可以获取16条行业分类
    * 
    * @param page
    * @param rows
    * @return
    */
	public Result getIndustryTypeFirstLimit(int page,int rows);
}
