package com.alqsoft.dao.industryassociation;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface IndustryAssociationDao {

	/**
	 * 所属协会列表接口
	 * @return
	 */
	public List<Map<String, Object>> findIndustryAssociationList();

}
