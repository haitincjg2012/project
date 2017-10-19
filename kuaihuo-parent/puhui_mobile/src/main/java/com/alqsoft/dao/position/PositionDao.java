package com.alqsoft.dao.position;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import com.alqsoft.entity.position.Position;

@MyBatisRepository
public interface PositionDao {

	public List<Map<String, Object>> getPosition();

	public List<Map<String, Object>> getCityByPid(@Param(value="pId")Long pId);

	public List<Map<String, Object>> getCountyByCid(@Param(value="cId")Long cId);

	public List<Map<String, Object>> getTownByCid(@Param(value="cId")Long cId);

	public Position getPositionByTownId(@Param(value="townId")Long townId);
	
	public List<Position> getPositionByCountyId(@Param(value="countyId")Long countyId);

	public Position getCityInfoByCountyId(@Param(value="countyId")Long countyId);

	public Position getAllBYCountyId(@Param(value="countyId")Long countyId);

	public Position getAllByTownId(@Param(value="townId")Long townId);
	
	
	public Map getBycityid(@Param(value="cid")Long cid);

	
	public List<Map<String, Object>> getBycountyid(@Param(value="cid")Long cId);
}
