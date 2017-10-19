package com.alqsoft.service.position;

import java.util.List;

import org.alqframework.result.Result;

import com.alqsoft.entity.position.Position;

public interface PositionService {

	public Result getPositionByIdAndType(String id, int type);

	public Position getPositionByTownId(Long id);
	
	public List<Position> getPositionByCountyId(Long countyId);

	public Position getCityInfoByCountyId(Long valueOf);
	
	public Position getAllBYCountyId(String id);

	public Result contrastSaveAgreement(Long countyId, Long townId);

	public Result contrastSaveAgreementAll(Long provId, Long cityId, Long countyId, Long townId);

	public Position getAllBYTownId(Long string);

	/**
	 * 根据社区编号获取省市县信息
	 */
	public Position getPHPositionByTownId(Long townId);

}
