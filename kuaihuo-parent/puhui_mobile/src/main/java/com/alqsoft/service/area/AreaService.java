package com.alqsoft.service.area;

import org.alqframework.result.Result;

public interface AreaService {

	public Result findProList();

	public Result findCityListByPId(Long pId);

	public Result findAreaList();

}
