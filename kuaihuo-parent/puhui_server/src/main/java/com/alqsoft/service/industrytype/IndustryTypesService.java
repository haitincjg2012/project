package com.alqsoft.service.industrytype;

import java.util.List;
import java.util.Map;

public interface IndustryTypesService {

	/**
	 * 行业列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findIndustryTypeListMybatis(Map<String,Object> params);
	
	public int getIndustryTypeListCount(Map<String,Object> params);
	
	public List<Map<String,Object>> findSecondIndustryTypeByFirstId(Long firstid);

     
}
