package com.alqsoft.dao.industrytype;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import com.alqsoft.vo.IndustryTypeVO;

/**
 * @author Xuejizheng
 * @date 2017-03-02 10:06
 * @see
 * @since 1.8
 */
@MyBatisRepository
public interface IndustryTypeDao {

    List<IndustryTypeVO>   getIndustryTypeList(Map<String,Object> map);
    
	/**
	 * 获取行类一级
	 * */
	public List<Map> getAllIndustryTypeFirst();
	
	/**
	 * 获取行类二级
	 * */
    public List<Map> getAllIndustryTypeSecond(Integer id);
    
    /**
     * 根据分类id查询分类信息   isdelete=0
     * @param id
     * @return
     */
    public Map<String,Object> getIndustryTypeById(Long id);
    /**
	 * 获取所有未禁用的一级行业分类
	 * */
	public List<Map<String, Object>> findAllIndustryType();
	/**
	 * 根据一级分类获取所有未禁用的二级分类
	 * */
	public List<Map<String, Object>> findSecondIndustryType(Map<String, Object> map);
	/**
	 * 根据一级分类获取所有未禁用的二级分类
	 * @param map 
	 * */
	public List<Map<String, Object>> findAllSecondIndustryType(Map<String, Object> map);
    /**
     * 获取一级分类最多获取16条数据
     * @param page  当前页
     * @param rows  当前页的数据
     * @return
     */
	public List<Map> getIndustryTypeFirstLimit(@Param("page") int page,@Param("rows") int rows);
}
