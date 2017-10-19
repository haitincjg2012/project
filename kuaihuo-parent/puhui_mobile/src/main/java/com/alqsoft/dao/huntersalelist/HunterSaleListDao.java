package com.alqsoft.dao.huntersalelist;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月1日 下午12:54:28
 * 获取批发商销售商品的列表
 * id销售是0，跟新是1，价格是2，flat为true是升序，false降序，hunterName是批发商的id，currentPage当前页，numPage是当前页的数量
 */
@MyBatisRepository
public interface HunterSaleListDao {
 
	/**
	 * 获取批发商数量产品的列表
	 * @param hunter 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findAllProductSaleList(@Param("result")String result,@Param("sort")String sort,@Param("hId") Long hId,@Param("currentPage") Integer currentPage,@Param("numPage")Integer numPage);
	
	
}
