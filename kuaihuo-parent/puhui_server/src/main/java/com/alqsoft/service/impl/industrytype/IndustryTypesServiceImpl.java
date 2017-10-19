package com.alqsoft.service.impl.industrytype;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.mybatis.dao.industrystype.IndustrysTypeDao;
import com.alqsoft.service.industrytype.IndustryTypesService;

@Service
@Transactional(readOnly=true)
public class IndustryTypesServiceImpl implements IndustryTypesService{
	
	@Autowired
	private IndustrysTypeDao industryTypeDao;

	@Override
	public List<Map<String, Object>> findIndustryTypeListMybatis(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return industryTypeDao.findIndustryTypeListMybatis(params);
	}

	@Override
	public int getIndustryTypeListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return industryTypeDao.getIndustryTypeListCount(params);
	}

	@Override
	public List<Map<String, Object>> findSecondIndustryTypeByFirstId(Long firstid) {
		// TODO Auto-generated method stub
		return industryTypeDao.findSecondIndustryTypeByFirstId(firstid);
	}

}
