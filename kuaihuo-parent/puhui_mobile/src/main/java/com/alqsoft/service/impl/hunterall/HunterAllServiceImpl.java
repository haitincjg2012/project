package com.alqsoft.service.impl.hunterall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextListener;

import com.alqsoft.dao.hunterall.HunterAllDao;
import com.alqsoft.service.hunterall.HunterAllService;
import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.utils.StatusCodeEnums;

@Service
@Transactional(readOnly = true)
public class HunterAllServiceImpl implements HunterAllService {
	private static Logger logger = LoggerFactory.getLogger(HunterAllServiceImpl.class);

	@Autowired
	private HunterAllDao hunterAllDao;
	
	
	/*一期*/
	/**
	 * 1.先查询热门批发商&显示平台历史销量最高的前9名批发商
	 * 2.查询两个最近添加的专题分类&2个
	 * 3.查询对应两个专题分类下面的批发商列表
	 * 4.查询两个最近添加的热门推荐&2个
	 * 5.查询对应两个热门推荐下面的批发商列表
	 * 6.查询最新批发商列表&按照批发商的申请时间进行排序自动筛选出最新注册的10个批发商
	 * 7.将查询到的集合放到map中返回
	 */
	@Override
	public Result getHunterAllList() {
		
		//1 热门批发商：销量最高的前9名批发商
		List<Map> hunterAllList = hunterAllDao.getHunterAllList();
		for (Map<String, Object> map : hunterAllList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		
		//2_1：父级栏 专题分类查出所对应的专题名称  并取出ID
		List<Map> hunterSubjectListParent = hunterAllDao.getHunterSubjectListParent();
		
		//2_2：子级栏  专题分类  循环遍历出所对应父级栏的ID
		List<Map> oneHunterSubjectList = null;
		Long id = null;
		//----------------------得到的ID不为空  执行下面的集合查询第一条开始
		if(hunterSubjectListParent!=null&&!hunterSubjectListParent.isEmpty()){
			id =  (Long) hunterSubjectListParent.get(0).get("id");
		}
		oneHunterSubjectList = hunterAllDao.getHunterSubjectList(id);
		for (Map<String, Object> map : oneHunterSubjectList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		for(Map<String,Object> hunter : oneHunterSubjectList){
			String pro=hunter.get("provincename")==null?"":hunter.get("provincename").toString();
			String city=hunter.get("cityname")==null?"":hunter.get("cityname").toString();
			String country=hunter.get("countyname")==null?"":hunter.get("countyname").toString();
			if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
				hunter.put("position", pro+country);//服务的区域
			}else{
				hunter.put("position",pro+city);
			}
		}
		Map<String,Object> oneSubjectHunterListInfo=new HashMap<String,Object>();
		if(hunterSubjectListParent!=null&&!hunterSubjectListParent.isEmpty()){
			oneSubjectHunterListInfo.put("subjectInfo1", hunterSubjectListParent.get(0));
		}
		oneSubjectHunterListInfo.put("hunterList1", oneHunterSubjectList);
		//----------------------得到的ID不为空  执行下面的集合查询第一条结束
		
		//----------------------得到的ID不为空  执行下面的集合查询第二条开始
		List<Map> twoHunterSubjectList = null;
		//得到的ID不为空  执行下面的集合查询第二条
		if(hunterSubjectListParent.size()>1){
			id =  (Long) hunterSubjectListParent.get(1).get("id");
		}
		twoHunterSubjectList = hunterAllDao.getHunterSubjectList(id);
		for (Map<String, Object> map : twoHunterSubjectList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		for(Map<String,Object> hunter : twoHunterSubjectList){
			String pro=hunter.get("provincename")==null?"":hunter.get("provincename").toString();
			String city=hunter.get("cityname")==null?"":hunter.get("cityname").toString();
			String country=hunter.get("countyname")==null?"":hunter.get("countyname").toString();
			if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
				hunter.put("position", pro+country);//服务的区域
			}else{
				hunter.put("position",pro+city);
			}
		}
		Map<String,Object> twoSubjectHunterListInfo=new HashMap<String,Object>();
		if(hunterSubjectListParent.size()>1){
			twoSubjectHunterListInfo.put("subjectInfo2", hunterSubjectListParent.get(1));
		}
		twoSubjectHunterListInfo.put("hunterList2", twoHunterSubjectList);
		//----------------------得到的ID不为空  执行下面的集合查询第二条结束
		
		//3_1：父级栏 热门推荐查出所对应的热门名称  并取出ID
		List<Map> hunterHotListParent = hunterAllDao.getHunterHotListParent();
		
		//3_2子级栏  热门推荐  循环遍历出所对应父级栏的ID
		List<Map> oneRecommendProductList = null;
		//----------------------得到的ID不为空  执行下面的集合查询第一条开始
		if(hunterHotListParent!=null&&!hunterHotListParent.isEmpty()){
			id =  (Long) hunterHotListParent.get(0).get("id");
		}
		oneRecommendProductList = hunterAllDao.getHunterHotList(id);
		for (Map<String, Object> map : oneRecommendProductList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		Map<String,Object> oneRecommendProductListInfo=new HashMap<String,Object>();
		if(hunterHotListParent!=null&&!hunterHotListParent.isEmpty()){
			oneRecommendProductListInfo.put("hotInfo1", hunterHotListParent.get(0));
		}
		oneRecommendProductListInfo.put("hotList1", oneRecommendProductList);
		//----------------------得到的ID不为空  执行下面的集合查询第一条结束
		
		//----------------------得到的ID不为空  执行下面的集合查询第二条开始
		List<Map> twoRecommendProductList = null;
		//得到的ID不为空  执行下面的集合查询第二条
		if(hunterHotListParent.size()>1){
			id =  (Long) hunterHotListParent.get(1).get("id");
		}
		twoRecommendProductList = hunterAllDao.getHunterHotList(id);
		for (Map<String, Object> map : twoRecommendProductList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		Map<String,Object> twoRecommendProductListInfo=new HashMap<String,Object>();
		if(hunterHotListParent.size()>1){
			twoRecommendProductListInfo.put("hotInfo2", hunterHotListParent.get(1));
		}
		twoRecommendProductListInfo.put("hotList2", twoRecommendProductList);
		//----------------------得到的ID不为空  执行下面的集合查询第二条结束
		
		//4新到批发商：按申请时间进行排序
		List<Map> newhunterList = hunterAllDao.getNewHunterTimeList();
		for (Map<String, Object> map : newhunterList) {
			Object level = map.get("level")==null?"":map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if(valueOf==0){
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if(valueOf==1){
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if(valueOf==2){
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if(valueOf==3){
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		for(Map<String,Object> hunter : newhunterList){
			String pro=hunter.get("provincename")==null?"":hunter.get("provincename").toString();
			String city=hunter.get("cityname")==null?"":hunter.get("cityname").toString();
			String country=hunter.get("countyname")==null?"":hunter.get("countyname").toString();
			if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
				hunter.put("position", pro+country);//服务的区域
			}else{
				hunter.put("position",pro+city);
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("hunterAllList", hunterAllList);
		map.put("oneSubjectHunterListInfo", oneSubjectHunterListInfo);		
		map.put("oneRecommendProductListInfo", oneRecommendProductListInfo);		
		map.put("twoSubjectHunterListInfo",twoSubjectHunterListInfo);
		map.put("twoRecommendProductListInfo", twoRecommendProductListInfo);
		map.put("newhunterList", newhunterList);
		
		return ResultUtils.returnSuccess("获取批发商列表成功", map);
	}
	/*二期*/
	/**
	 * 1.先查询热门批发商&显示平台历史销量最高的前9名批发商 
	 * 2.查询两个最近添加的专题分类&n个 
	 * 3.查询对应n个专题分类下面的3批发商列表
	 * 4.查询两个最近添加的热门推荐&1个 
	 * 5.查询对应两个热门推荐下面的批发商列表
	 * 6.查询最新批发商列表&按照批发商的申请时间进行排序自动筛选出最新注册的10个批发商 
	 * 7.将查询到的集合放到map中返回
	 */
	@Override
	public Result getHunterAllList2() {

		// 1 热门批发商：销量最高的前10名批发商
		List<Map<String, Object>> hunterAllList = hunterAllDao.getHunterAllList2();
		for (Map<String, Object> map : hunterAllList) {
			Object level = map.get("level") == null ? "" : map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if (valueOf == 0) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if (valueOf == 1) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if (valueOf == 2) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if (valueOf == 3) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}

		// 2：父级栏 专题分类查出所对应的专题名称
		List<Map<String, Object>> hunterSubjectList = hunterAllDao.getHunterSubjectListParent2();
		for (Map<String, Object> parent : hunterSubjectList) {
			// 专题分类查出所对应的专题名称并取出ID
			List<Map<String, Object>> son = hunterAllDao.getHunterSubjectList2(Long.valueOf(parent.get("id").toString()));

			for (Map<String, Object> map : son) {
				Object level = map.get("level") == null ? "" : map.get("level");
				Integer valueOf = Integer.valueOf(level.toString());
				if (valueOf == 0) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
				}
				if (valueOf == 1) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
				}
				if (valueOf == 2) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
				}
				if (valueOf == 3) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
				}
				String pro = map.get("provincename") == null ? "" : map.get("provincename").toString();
				String city = map.get("cityname") == null ? "" : map.get("cityname").toString();
				String country = map.get("countyname") == null ? "" : map.get("countyname").toString();
				if ("北京市".equals(pro) || "天津市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro)) {
					map.put("position", pro + country);// 服务的区域
				} else {
					map.put("position", pro + city);
				}
			}

			parent.put("son", son);
		}

		// 3_1：父级栏 热门推荐查出所对应的热门名称 并取出ID
		List<Map<String, Object>> hunterHotListParent = hunterAllDao.getHunterHotListParent2();

		// 3_2子级栏 热门推荐 循环遍历出所对应父级栏的ID
		List<Map<String, Object>> oneRecommendProductList = null;
		// ----------------------得到的ID不为空 执行下面的集合查询第一条开始
		Long id = null;
		if (hunterHotListParent != null && !hunterHotListParent.isEmpty()) {
			id = (Long) hunterHotListParent.get(0).get("id");
		}
		oneRecommendProductList = hunterAllDao.getHunterHotList2(id);
		for (Map<String, Object> map : oneRecommendProductList) {
			Object level = map.get("level") == null ? "" : map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if (valueOf == 0) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if (valueOf == 1) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if (valueOf == 2) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if (valueOf == 3) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		Map<String, Object> oneRecommendProductListInfo = new HashMap<String, Object>();
		if (hunterHotListParent != null && !hunterHotListParent.isEmpty()) {
			oneRecommendProductListInfo.put("hotInfo1", hunterHotListParent.get(0));
		}
		oneRecommendProductListInfo.put("hotList1", oneRecommendProductList);
		// ----------------------得到的ID不为空 执行下面的集合查询第一条结束

		// 4新到批发商：按申请时间进行排序
		List<Map<String, Object>> newhunterList = hunterAllDao.getNewHunterTimeList2();
		for (Map<String, Object> map : newhunterList) {
			Object level = map.get("level") == null ? "" : map.get("level");
			Integer valueOf = Integer.valueOf(level.toString());
			if (valueOf == 0) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
			}
			if (valueOf == 1) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
			}
			if (valueOf == 2) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
			}
			if (valueOf == 3) {
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		for (Map<String, Object> hunter : newhunterList) {
			String pro = hunter.get("provincename") == null ? "" : hunter.get("provincename").toString();
			String city = hunter.get("cityname") == null ? "" : hunter.get("cityname").toString();
			String country = hunter.get("countyname") == null ? "" : hunter.get("countyname").toString();
			if ("北京市".equals(pro) || "天津市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro)) {
				hunter.put("position", pro + country);// 服务的区域
			} else {
				hunter.put("position", pro + city);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hunterAllList", hunterAllList);
		map.put("hunterSubjectList", hunterSubjectList);
		map.put("oneRecommendProductListInfo", oneRecommendProductListInfo);
		map.put("newhunterList", newhunterList);

		return ResultUtils.returnSuccess("获取批发商列表成功", map);
	}
	/***
	 * 通过用户的经纬度和批发商的经纬度获取距离，通过距离进行排序
	 *
	 */
	@Override
	public Result getAllHunter(String longitude, String latitude,Integer currentPage,Integer numPage) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if ("lo".equals(longitude) || "la".equals(latitude)) {
			return ResultUtils.returnError("当前用户的经纬度不能为空");
		}
		List list = new ArrayList<>();
try{
		Integer cpage = (currentPage-1)*numPage;
		List<Map<String, Object>> allHunter = hunterAllDao.getAllHunter(longitude,latitude,cpage,numPage);
		if (allHunter != null && allHunter.size() > 0) {
			for (int i = 0; allHunter.size() > i; i++) {
				//数字转换
				allHunter.get(i).put("num",NumberFormat.getFormatNumber(String.valueOf(allHunter.get(i).get("num"))));
				allHunter.get(i).put("good_comment_num_order",NumberFormat.getFormatNumber(String.valueOf(allHunter.get(i).get("good_comment_num_order"))));
				
				   // 符合要求添加用户和批发商的距离。批发商的基本信息
				    Map<String, Object> map = allHunter.get(i);
				   
					double round = (double) map.get("distance");
                    
					double double1 = round / 1000;
					BigDecimal bg = new BigDecimal(double1);
					try {
						
					
					double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					if (double1 > 0.01) {
						map.put("distance", double3 + " km");
					} else {
						map.put("distance", "<0.01 km");

					}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				
				// 拼接省市
				String provice = map.get("provincename") == null ? ""
						: map.get("provincename").toString();
				String city = map.get("cityname") == null ? "" : map.get("cityname").toString();
				String contryName = map.get("countyname") == null ? ""
						: map.get("countyname").toString();
				String position;
				if ("北京市".equals(provice) || "天津市".equals(provice) || "上海市".equals(provice)
						|| "重庆市".equals(provice)) {
					/*position = provice + contryName;*/
					position =provice+provice; 
					map.put("position", position);
				} else {
					position = provice + city;
					map.put("position", position);
				}
				Integer level = Integer
						.valueOf(map.get("level") == null ? "0" : map.get("level").toString());

				if (1 == level) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
				} else if (2 == level) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
				} else if (3 == level) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
				} else if (0 == level) {
					map.put("level",  HunterLevelEnums.LT_LEVEL_COMMON.getData());
				}

				list.add(map);
			}
		}
		    
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
			return result;
		}
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent(list);

		return result;
		
	}


	/**
	 * 通过商家配送范围匹配，销量排序
	 * @param phone
	 * @param currentPage
	 * @param numPage
	 * @return
	 */
	@Override
	public Result getAllHunterByArea(String phone, Integer currentPage, Integer numPage) {
		Result result = new Result();
		List list = new ArrayList<>();
		Integer cpage = (currentPage - 1) * numPage;
		try {
			Map<String, Object> m = hunterAllDao.getMemberMsg(phone);
			if (m == null) {
				result.setCode(0);
				result.setMsg("无该用户");
				return result;
			}
			Long countyId = (Long) m.get("countyId");
			List<Map<String, Object>> allHunter = hunterAllDao.getAllHunterByCountyId(countyId, cpage, numPage);
			if (allHunter != null && allHunter.size() > 0) {
				for (int i = 0; allHunter.size() > i; i++) {
					//数字转换
					allHunter.get(i).put("num", NumberFormat.getFormatNumber(String.valueOf(allHunter.get(i).get("num"))));
					allHunter.get(i).put("good_comment_num_order", NumberFormat.getFormatNumber(String.valueOf(allHunter.get(i).get("good_comment_num_order"))));
					// 拼接省市
					Map<String, Object> map = allHunter.get(i);
					String provice = map.get("provincename") == null ? ""
							: map.get("provincename").toString();
					String city = map.get("cityname") == null ? "" : map.get("cityname").toString();
					String contryName = map.get("countyname") == null ? ""
							: map.get("countyname").toString();
					String position;
					if ("北京市".equals(provice) || "天津市".equals(provice) || "上海市".equals(provice)
							|| "重庆市".equals(provice)) {
						position = provice + contryName;
						position = provice + provice;
						map.put("position", position);
					} else {
						position = provice + city;
						map.put("position", position);
					}
					Integer level = Integer
							.valueOf(map.get("level") == null ? "0" : map.get("level").toString());

					if (1 == level) {
						map.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
					} else if (2 == level) {
						map.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
					} else if (3 == level) {
						map.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
					} else if (0 == level) {
						map.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
					}

					list.add(map);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
			return result;
		}
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent(list);

		return result;


	}
}
