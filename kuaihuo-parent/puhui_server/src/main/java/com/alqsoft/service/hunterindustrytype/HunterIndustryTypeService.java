package com.alqsoft.service.hunterindustrytype;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.hunterindustrytype.HunterIndustryType;

public interface HunterIndustryTypeService extends BaseService<HunterIndustryType>{
	/**
	 * 根据批发商id删除关联关系
	 */
	public int deletehunterIndustryTypeById(Long id);
	
}
