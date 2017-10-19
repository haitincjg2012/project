package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO;
import com.ph.shopping.facade.profit.entity.HunterOrderProfit;
import com.ph.shopping.facade.profit.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IHunterOrderProfitMapper {	
    /**
     * 
    * @Title: getAreaByTownId
    * @Description: 查询省市县
    * @author 王强
    * @date  2017年4月26日 下午2:13:07
    * @param townId
    * @return
     */
    List<PositionVO> getAreaByTownId(@Param("townId") long townId);
    /**
     * 
    * @Title: getTowns
    * @Description: 获取乡镇列表
    * @author 王强
    * @date  2017年4月26日 下午2:13:15
    * @param countyId
    * @return
     */
    List<PositionTownVO> getTowns(@Param("countyId") long countyId);
    /**
     * 
    * @Title: getPosition
    * @Description:查询省
    * @author 王强
    * @date  2017年4月26日 下午2:13:23
    * @return
     */
    List<PositionProvinceVO> getPosition();
    /**
     * 
    * @Title: getCityByPid
    * @Description: 查询市
    * @author 王强
    * @date  2017年4月26日 下午2:13:30
    * @param provinceId
    * @return
    * @throws Exception
     */
    List<PositionCityVO> getCityByPid(@Param("provinceId") long provinceId);
    /**
     * 
    * @Title: getCountyByCid
    * @Description: 查询区、县
    * @author 王强
    * @date  2017年4月26日 下午2:13:37
    * @param cityId
    * @return
    * @throws Exception
     */
    List<PositionCountyVO> getCountyByCid(@Param("cityId") long cityId) throws Exception;
    /**
     * 
    * @Title: getTownByCid
    * @Description: 查询乡镇(社区)
    * @author 王强
    * @date  2017年4月26日 下午2:13:44
    * @param countyId
    * @return
    * @throws Exception
     */
    List<PositionTownVO> getTownByCid(@Param("countyId") long countyId) throws Exception;
    
    
    /*------------------------------------------------------------------------------------------------------------------*/
        
    /**
	 * 
	* @Title: insertSelective
	* @Description: 插入批发商分润数据
	* @author 王强
	* @date  2017年4月26日 下午2:11:22
	* @param record
	* @return
	 */
    int insertSelective(HunterOrderProfit record);
    
    
    /**
     * 
    * @Title: hunterOrderProfitList
    * @Description: TODO(批发商订单分润list)
    * @author Mr.Dong
    * @date  2017年6月1日 下午5:47:02
    * @param hunterOrderProfitDTO
    * @return
     */
    List<HunterOrderProfitVO>  hunterOrderProfitList(HunterOrderProfitDTO hunterOrderProfitDTO);
    
    
    /**
     * 
    * @Title: insertHunterOrderProfit
    * @Description: TODO(插入数据批发商分润记录表)
    * @author Mr.Dong
    * @date  2017年6月2日 下午4:29:32
    * @param hunterOrderProfit
    * @return
     */
    int insertHunterOrderProfit(HunterOrderProfit hunterOrderProfit);

    /**
     * 快火批发分润订单
     * @return
     */
    List<Map> hunterOrderProfitPageList(HunterOrderProfitDTO hunterOrderProfitDTO);
}