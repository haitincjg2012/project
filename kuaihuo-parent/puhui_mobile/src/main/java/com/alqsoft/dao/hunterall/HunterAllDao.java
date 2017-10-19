package com.alqsoft.dao.hunterall;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;


@MyBatisRepository
public interface HunterAllDao {
	
	/*一期*/
	/**
	 * 1 热门批发商：销量最高的前9名批发商getHunterAllList	展示星级，等级
	 * 2_1获取专题分类父级栏目getHunterSubjectListParent
	 * 2_2获取专题分类子级栏目getHunterSubjectList	展示星级，等级
	 * 3_1获取热门推荐父级栏目getHunterHotListParent
	 * 3_2获取热门推荐子级栏目getHunterHotList		展示星级，等级
	 * 4新到批发商：按申请时间进行排序getNewHunterTimeList展示星级，等级
	 * @return
	 */
	public List<Map>  getHunterAllList();//展示星级，等级
	
	public List<Map>  getHunterSubjectListParent();
	public List<Map>  getHunterSubjectList(Long id);//展示星级，等级
	
	public List<Map>  getHunterHotListParent();
	public List<Map>  getHunterHotList(Long id);//展示星级，等级
	
	public List<Map>  getNewHunterTimeList();//展示星级，等级
	/*二期*/
	/**
	 * 1 热门批发商：销量最高的前10名批发商getHunterAllList	展示星级，等级
	 * 2_1获取专题分类父级栏目getHunterSubjectListParent
	 * 2_2获取专题分类子级栏目getHunterSubjectList	展示星级，等级
	 * 3_1获取热门推荐父级栏目getHunterHotListParent
	 * 3_2获取热门推荐子级栏目getHunterHotList		展示星级，等级
	 * 4新到批发商：按申请时间进行排序getNewHunterTimeList展示星级，等级
	 * @return
	 */
	public List<Map<String,Object>>  getHunterAllList2();//展示星级，等级
	
	public List<Map<String,Object>>  getHunterSubjectListParent2();
	public List<Map<String,Object>>  getHunterSubjectList2(Long id);//展示星级，等级
	
	public List<Map<String,Object>>  getHunterHotListParent2();
	public List<Map<String,Object>>  getHunterHotList2(Long id);//展示星级，等级
	
	public List<Map<String,Object>>  getNewHunterTimeList2();//展示星级，等级
	/**
	 * 
	 * @param longitude 经度
	 * @param latitude  维度
	 * @return
	 * @author wudi
	 */
	public List<Map<String,Object>>  getAllHunter(@Param("longitude")String longitude,@Param("latitude")String latitude,@Param("currentPage") Integer currentPage,@Param("numPage") Integer numPage);


	public Map<String,Object> getMemberMsg(String phone);


    public List<Map<String,Object>> getAllHunterByCountyId(@Param("countyId")Long countyId,@Param("currentPage") Integer cpage, @Param("numPage")Integer numPage);
}
