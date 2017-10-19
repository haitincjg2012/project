package com.alqsoft.dao.searchhunter;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年3月30日 下午2:17:28
 * 
 */
@MyBatisRepository
public interface SearchHunterDao {
     /***
      * 模糊商品对应的批发商id通过销售数量
      * @sorts 商品的销量或者商品的好评
      * @param name
      * @return
      */
	public List<Map<String,Object>> getHunterByNum(@Param("name")String name,@Param("currentPage")Integer currentPage,@Param("numPage")Integer numPage);
	/***
	 * 模糊商品对应的批发商id通过好评
	 * @param name
	 * @param currentPage
	 * @param numPage
	 * @return
	 */
	public List<Map<String,Object>> getHunterByGoodNum(@Param("name")String name,@Param("currentPage")Integer currentPage,@Param("numPage")Integer numPage);
	/***
	 * 获取商品信息
	 * @param name
	 * @param hId
	 * @return
	 */
	public List<Map> getProductByName(@Param("name")String name,@Param("hId")Long hId);
	/***
	 * 批发商的数据
	 * @param hId
	 * @return
	 */
	public Map getHunterMessage(@Param("hId")Long hId);
	/***
	 * 批发商的数据通过匿名
	 */
	public List<Map<String,Object>> getHunterByNickName(@Param("name") String name);
	/***
	 *通过
	 * 模糊查询获取批发商对应的商品
	 */
	public List<Map> getSearchProductByName(@Param("hId") Long hId,@Param("name") String name,@Param("currentPage")Integer currentPage,@Param("numPage")Integer numPage);
	
	/****
	 * name 模糊数据
	 * longitude 用户的当前经度
	 * latitude 用户的当前维度
	 */
    public	List<Map<String,Object>> getHunterByDistance(@Param("name")String name,@Param("currentPage")Integer currentPage,@Param("numPage") Integer numPage,@Param("longitude") String longitude,@Param("latitude") String latitude);
    /***
     * 
     * @param hId  用户id
     * @param longitude 用户的当前经度
     * @param latitude 用户的当前维度
     * @return
     */
    public Map getHunterMessageAddTude(@Param("hId") Long hId,@Param("longitude") String  longitude,@Param("latitude") String latitude);
}
