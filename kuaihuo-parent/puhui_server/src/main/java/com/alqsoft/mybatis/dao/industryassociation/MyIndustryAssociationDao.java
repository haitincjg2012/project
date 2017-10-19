package com.alqsoft.mybatis.dao.industryassociation;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.industryassociation.IndustryAssociation;

/**
 * @author 丁兰兰
 * @create-time 2017-03-17 下午3:19:46
 * 
 */
@MyBatisRepository
public interface MyIndustryAssociationDao {

	/**
	 * 行业协会后台——查询身份信息
	 * @return
	 */
	public List<Map<String, Object>> findIndustryAssociationById(Map<String, Object> params);
	/**
	 * 行业协会后台——查询身份信息条数
	 */
	public int findIndustryAssociationCountById(Map<String, Object> params);
	
	
	public IndustryAssociation getIndustryAssociationById(Map<String, Object> params);
	
	/**
	 * 行业协会后台--根据手机号查询行业协会
	 */
	public IndustryAssociation getAssociationByPhone(Map<String, Object> param);
	
	/**
	 * 添加行业协会验证，名称是否重复存在
	 * @param param
	 * @return
	 */
	public int findAssociationLikeName(Map<String, Object> param);

}
