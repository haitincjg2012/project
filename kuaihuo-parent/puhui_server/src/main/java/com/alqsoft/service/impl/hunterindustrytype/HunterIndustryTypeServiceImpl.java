package com.alqsoft.service.impl.hunterindustrytype;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterindustrytype.HunterIndustryTypeDao;
import com.alqsoft.entity.hunterindustrytype.HunterIndustryType;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterindustrytype.HunterIndustryTypeService;


@Service
@Transactional(readOnly=true)
public class HunterIndustryTypeServiceImpl implements HunterIndustryTypeService {

	@Autowired
	private HunterIndustryTypeDao hunterIndustryTypeDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HunterIndustryType get(Long arg0) {
		
		return hunterIndustryTypeDao.findOne(arg0);
	}

	@Override
	@Transactional
	public HunterIndustryType saveAndModify(HunterIndustryType arg0) {
		
		return hunterIndustryTypeDao.save(arg0);
	}

	/**
	 * 根据批发商id删除关联关系
	 */
	@Override
	@Transactional
	public int deletehunterIndustryTypeById(Long id) {
		
		return hunterIndustryTypeDao.deletehunterIndustryTypeById(id);
	}
	
}
