package com.alqsoft.service.impl.area;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.area.AreaDao;
import com.alqsoft.service.area.AreaService;

@Service
@Transactional(readOnly=true)
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;

	@Override
	public Result findProList() {
		List<Map<String, Object>> proList = this.areaDao.findProList();
		return ResultUtils.returnSuccess("查询成功", proList);
	}

	@Override
	public Result findCityListByPId(Long pId) {
		if(pId.longValue() == 1){
			pId = 3281L;
		}
		if(pId.longValue() == 20){
			pId = 3282L;
		}
		if(pId.longValue() == 2249){
			pId = 3283L;
		}
		if(pId.longValue() == 795){
			pId = 3284L;
		}
		List<Map<String, Object>> cityList = this.areaDao.findCityListByPId(pId);
		return ResultUtils.returnSuccess("查询成功", cityList);
	}

	@Override
	public Result findAreaList() {
		
		List<Map<String, Object>> proList = this.areaDao.findProList();
		for (Map<String, Object> map : proList) {
			Long id = Long.valueOf(map.get("id").toString());
			if(id.longValue() == 1){
				id = 3281L;
			}
			if(id.longValue() == 20){
				id = 3282L;
			}
			if(id.longValue() == 2249){
				id = 3283L;
			}
			if(id.longValue() == 795){
				id = 3284L;
			}
			List<Map<String, Object>> list = this.areaDao.findCityListByPId(id);
			map.put("cities", list);
		}
		
		return ResultUtils.returnSuccess("查询成功", proList);
	}
	
}
