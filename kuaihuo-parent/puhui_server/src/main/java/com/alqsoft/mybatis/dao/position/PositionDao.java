package com.alqsoft.mybatis.dao.position;

import com.alqsoft.vo.PositionVO;
import org.alqframework.orm.mybatis.MyBatisRepository;

import java.util.List;

@MyBatisRepository
public interface PositionDao {
	/**
	 *个人中心---查询配送范围
	 */
	public List<PositionVO> selectAreaRang(List<Long> districtList);

}
