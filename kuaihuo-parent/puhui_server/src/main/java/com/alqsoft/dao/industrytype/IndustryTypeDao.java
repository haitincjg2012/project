package com.alqsoft.dao.industrytype;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.industrytype.IndustryType;

/**
 * 行业分类
 * @author Administrator
 *
 */
public interface IndustryTypeDao extends BaseDao<IndustryType>{
	
	
	/**
	 * 查询一级分类下的二级分类
	 * @param firstid
	 * @return
	 */
	@Query("from IndustryType as t where t.parentId.id=:firstid and (t.isDelete is null or t.isDelete=0)")
	public List<IndustryType> findSecondIndustryTypeByFirstId(@Param("firstid")Long firstid);
	
	/**
	 * 查询该行业分类下有没有所属批发商，验证用
	 * @param industryTypeId
	 * @return
	 */
	@Query(value="select count(*) from Hunter h where h.industryType.id=:industryTypeId")
	public int getHunterByIndustryTypeById(@Param("industryTypeId")Long industryTypeId);
	
	/**
	 * 查询一级行业分类下是否存在批发商的数量，验证用
	 * @param firstid
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_hunter h WHERE EXISTS (SELECT 1 FROM alq_industry_type t WHERE t.`parent_id`=:firstid AND t.id=h.`industry_type_id`)",nativeQuery=true)
	public int getHunterByIndustyByFisrtId(@Param("firstid")Long firstid);
	
}
