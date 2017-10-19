package com.alqsoft.mybatis.dao.industrystype;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.industrytype.IndustryType;

@MyBatisRepository
public interface IndustrysTypeDao {

	public List<Map<String,Object>> findIndustryTypeListMybatis(Map<String,Object> params);
	
	public int getIndustryTypeListCount(Map<String,Object> params);

	List<Map<String,Object>> getIndustryTypeList(Map<String,Object> map);
	
	/**后台编辑回显，置顶和销量排序
	 * 查询一级分类下的二级分类
	 * @param firstid
	 * @return
	 */
	public List<Map<String,Object>> findSecondIndustryTypeByFirstId(Long firstid);
}
