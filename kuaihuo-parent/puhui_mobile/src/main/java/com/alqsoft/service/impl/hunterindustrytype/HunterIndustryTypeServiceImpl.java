package com.alqsoft.service.impl.hunterindustrytype;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterindustrytype.HunterIndustryTypeDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterindustrytype.HunterIndustryTypeService;


@Service
@Transactional(readOnly = true)
public class HunterIndustryTypeServiceImpl implements HunterIndustryTypeService {

	@Autowired
	private HunterIndustryTypeDao hunterIndustryTypeDao;

	/**
     * 根据分类获取批发商信息
     */
	@Override
	public List<Map<String, Object>> findHunterByiIndustryId(Map<String, Object> map) {
		
		return hunterIndustryTypeDao.findHunterByiIndustryId(map);
	}
	/**
     * 获取一级分类下的所有批发商信息
     */
	@Override
	public List<Map<String, Object>> findAllHunterByiIndustryId(Map<String, Object> map) {
		
		return hunterIndustryTypeDao.findAllHunterByiIndustryId(map);
	}
	/**
     * 根据批发商id获取二级分类标签
     */
	@Override
	public List<Map<String, Object>> findhHunterLabel(Long hunterId) {
		
		return hunterIndustryTypeDao.findhHunterLabel(hunterId);
	}
	@Override
	public List<Map<String, Object>> findAllHunter(Map<String, Object> map) {
	
		return hunterIndustryTypeDao.findAllHunter(map);
	}
	
	
}
