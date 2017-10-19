package com.alqsoft.service.impl.industrytype;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.industrytype.IndustryTypeDao;
import com.alqsoft.service.hunterindustrytype.HunterIndustryTypeService;
import com.alqsoft.service.impl.hunterproductdetail.HunterProductDetailServiceImpl;
import com.alqsoft.service.industrytype.IndustryTypeService;
import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.IndustryTypeVO;
import com.google.common.collect.Maps;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 上午12:17:38
 * Copyright 
 */
@Service
@Transactional(readOnly=true)
public class IndustryTypeServiceImpl implements IndustryTypeService{

	private static org.slf4j.Logger logger=LoggerFactory.getLogger(HunterProductDetailServiceImpl.class);
	
	 @Autowired
	 private IndustryTypeDao industryTypeDao;
	 
	 @Autowired
	 private HunterIndustryTypeService hunterIndustryTypeService;
	
	 /***
	  * 获取一级分类，添加
	  */
	 @Override
	 public Result getAllIndustryTypeFirst() {
		
	      Result result=new Result();
		
		try {
			
			List<Map> map = industryTypeDao.getAllIndustryTypeFirst();
			
			 int size =	map.size();
			 
			if (size <= 0) {
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
			    result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
			  
			}  else{
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
			    result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			    result.setContent(map);
			}
			
		} catch (Exception e) {
			    logger.error(e.getMessage(),e);
	            result.setCode(StatusCodeEnums.ERROR.getCode());
	            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}finally{
			
			return result;
		}
	}
    /***
     * 获取二级分类
     */
	public Result getAllIndustryTypeSecond(Integer id) {
		
		 Result result=new Result();
			
			try {
				
				List<Map> map = industryTypeDao.getAllIndustryTypeSecond(id);
				
				int size =	map.size();
				 
				if (size <= 0) {
					result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
				    result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
				  
				}  else{
					result.setCode(StatusCodeEnums.SUCCESS.getCode());
				    result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
				    result.setContent(map);
				}
				
			} catch (Exception e) {
				    logger.error(e.getMessage(),e);
		            result.setCode(StatusCodeEnums.ERROR.getCode());
		            result.setMsg(StatusCodeEnums.ERROR.getMsg());
			}finally{
				
				return result;
			}
		}

	/**
	 *  分类列表
	 * @param pid
	 * @param  rtype 请求类型 是否获取全部
	 * @return
	 */
	@Override
	public Result getIndustryTypeList(Long pid,Long rtype) {
		logger.info(" getIndustryTypeList  pid: {} ",pid);
		Result result = new Result();
		try {
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("pid",pid);
			paramMap.put("rtype",rtype);
			List<IndustryTypeVO> industryTypes = industryTypeDao.getIndustryTypeList(paramMap);
			JSONArray pary = new JSONArray();
			if (Objects.isNull(pid)){
				for(IndustryTypeVO type:industryTypes){
					JSONObject pobj = new JSONObject();
					pobj.put("id",type.getId());
					pobj.put("address",type.getAddress());
					pobj.put("name",type.getName());

					JSONArray ary = new JSONArray();
					Map<String,Object> map = Maps.newHashMap();
					map.put("pid",type.getId());
					paramMap.put("rtype",rtype);
					List<IndustryTypeVO> sonIndustryTypes = industryTypeDao.getIndustryTypeList(map);
					for(IndustryTypeVO sonIndustryType:sonIndustryTypes){
						JSONObject obj = new JSONObject();
						obj.put("id",sonIndustryType.getId());
						obj.put("name",sonIndustryType.getName());
						ary.add(obj);
					}
					pobj.put("children",ary);
					pary.add(pobj);
				}
			}  else {
				List<IndustryTypeVO> sonIndustryTypes = industryTypeDao.getIndustryTypeList(paramMap);
				for(IndustryTypeVO sonIndustryType:sonIndustryTypes){
					JSONObject obj = new JSONObject();
					obj.put("id",sonIndustryType.getId());
					obj.put("name",sonIndustryType.getName());
					pary.add(obj);
				}
			}

			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(pary);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
			return result;
		}

	}
	/**
	 * 根据分类id查询分类信息 isdelete=0
	 */
	@Override
	public Map<String, Object> getIndustryTypeById(Long id) {
		// TODO Auto-generated method stub
		return industryTypeDao.getIndustryTypeById(id);
	}
	/**
     * 获取第一,二级分类
     */
	@Override
	public Result findAllIndustryType() {
		
		Result result=new Result();
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("startIndex", 0);
		map.put("endIndex",15);
		
		//获取所有未禁用的一级行业分类
		List<Map<String,Object>> fistIndustryType=industryTypeDao.findAllIndustryType();
		
		if(fistIndustryType.size()<=0){
			return ResultUtils.returnError("没有数据");
		}
		
		/*Long fistTypeId=0L;
		List<Map<String,Object>> secondIndustryType=null;
		fistTypeId=Long.valueOf(fistIndustryType.get(0).get("fistTypeId").toString());//默认时  获取排序的第一个一级分类id
		map.put("fistTypeId", fistTypeId);
		secondIndustryType=industryTypeDao.findSecondIndustryType(map);//根据一级分类获取所有未禁用的二级分类
		if(secondIndustryType.size()<=0){
			fistIndustryType.get(0).put("data", "");
		}else{
			
			for(int i=0;i<secondIndustryType.size();i++){
				secondIndustryType.get(i).put("sort", ++i);
			}
		}	
			
			fistIndustryType.get(0).put("data", secondIndustryType);*/
		
		
		
		/*if("".equals(parentId) || null==parentId){
			
			//查询每个一级分类下的15条二级分类
			for (Map<String, Object> type : fistIndustryType) {
				
				fistTypeId=Long.valueOf(type.get("id").toString());//默认时  获取排序的第一个一级分类id
				map.put("fistTypeId", fistTypeId);
				secondIndustryType=industryTypeDao.findSecondIndustryType(map);//根据一级分类获取所有未禁用的二级分类
				if(secondIndustryType.size()<=0){
					type.put("data", "");
				}else{
					type.put("data", secondIndustryType);
				}
				
			}
			
		}else{
			//判断一级分类的下标，对应放入前15个二级分类
			
			for (Map<String, Object> type : fistIndustryType) {
				
				if(type.get("id").equals(parentId)){
					fistTypeId=Long.valueOf(parentId);//默认时  获取排序的第一个一级分类id
					map.put("fistTypeId", fistTypeId);
					secondIndustryType=industryTypeDao.findSecondIndustryType(map);//根据一级分类获取所有未禁用的二级分类
					if(secondIndustryType.size()<=0){
						type.put("data", "");
					}else{
						type.put("data", secondIndustryType);
					}
				}
				
			}
		}*/
		
		result.setCode(1);
		result.setMsg("查询成功");
		result.setContent(fistIndustryType);
		return result;
	}
	
	/**
     * 根据1级分类id获取二级分类
     */
	@Override
	public Result findAllIndustryType(int parentId, int page, int rows) {
		
		Result result=new Result();
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("startIndex", (page-1)*rows);
		map.put("endIndex",rows);
		
		
		List<Map<String,Object>> secondIndustryType=null;
		
		if( 0==parentId){//查询全部分类下的所有二级分类
			
				secondIndustryType=industryTypeDao.findAllSecondIndustryType(map);
			
		}else{
			
			map.put("parentId", parentId);
			secondIndustryType=industryTypeDao.findSecondIndustryType(map);//根据一级分类获取所有未禁用的二级分类
		}
		
		if(secondIndustryType.size()<=0){
			return ResultUtils.returnError("没有数据");
		}/*else{
			int a=0;
			for(int i=0;i<secondIndustryType.size();i++){
				
				secondIndustryType.get(i).put("sort", ++a);
			}
		}*/
			
		result.setCode(1);
		result.setMsg("查询成功");
		result.setContent(secondIndustryType);
		return result;
	}
	
	/**
     * 根据分类获取批发商信息
     */
	@Override
	public Result findHunterByiIndustryId(String fistTypeId, String secondTypeId, int page, int rows, String longitude, String latitude) {
		Result result=new Result();
		Map<String, Object> map = Maps.newHashMap();
		
		if("0".equals(fistTypeId)){//
			Map<String,Object> industryType=this.getIndustryTypeById(Long.valueOf(secondTypeId));
			 fistTypeId=industryType.get("parent_id").toString();
		}
		
		map.put("startIndex", (page-1)*rows);
		map.put("endIndex",rows);
		map.put("fistTypeId", fistTypeId);
		map.put("secondTypeId", secondTypeId);
		map.put("longitude",longitude);
		map.put("latitude",latitude);
		
		
		List<Map<String,Object>>  hunterList=hunterIndustryTypeService.findHunterByiIndustryId(map);
		
		if(hunterList.size()<=0){
			return ResultUtils.returnError("没有数据");
		}else{
				for (int i = 0; hunterList.size() > i; i++) {
					
					   // 符合要求添加用户和批发商的距离。批发商的基本信息
					    Map<String, Object> mapData = hunterList.get(i);
					   
						double round = (double) mapData.get("distance");
	                 
						double double1 = round / 1000;//计算距离 单位（km）
						BigDecimal bg = new BigDecimal(double1);
						try {
						
							double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							if (double1 > 0.01) {
								mapData.put("distance", double3 + " km");
							} else {
								mapData.put("distance", "<0.01 km");
		
							}
							
						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
						
						// null 做空处理
						Object num=mapData.get("num");
						if(null==num){
							mapData.put("num", "0");
						} else {
							mapData.put("num", NumberFormat.getFormatNumber(String.valueOf(num)));
						}
						Object major=mapData.get("major");
						if(null==major){mapData.put("major", "");}
						
						//等级 0 null 大众 1高级 2专家 3顶级     批发商等级
						Object level=mapData.get("level");
						if(null==level){level="";}
						level=level.toString();
						if("0".equals(level) || "".equals(level)){
							mapData.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
						}else if("1".equals(level)){
							mapData.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
						}else if("2".equals(level)){
							mapData.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
						}else if("3".equals(level)){
							mapData.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
						}
						
				}
				
			result.setCode(1);
			result.setMsg("查询成功");
			result.setContent(hunterList);
			return result;
		}
	}
	
	/**
     * 获取一级分类下的所有批发商信息
     */
	@Override
	public Result findAllHunterByiIndustryId(String fistTypeId, int page, int rows, String longitude, String latitude) {
		
		Result result=new Result();
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("startIndex", (page-1)*rows);
		map.put("endIndex",rows);
		map.put("longitude",longitude);
		map.put("latitude",latitude);
		
		List<Map<String,Object>>  hunterAllList=null;
		if("0".equals(fistTypeId)){//
			
			hunterAllList=hunterIndustryTypeService.findAllHunter(map);
		}else{
			
			map.put("fistTypeId", fistTypeId);
			hunterAllList=hunterIndustryTypeService.findAllHunterByiIndustryId(map);
		}
		
		
		
		if(hunterAllList.size()<=0){
			return ResultUtils.returnError("没有数据");
		}else{
				for (int i = 0; hunterAllList.size() > i; i++) {
				
				   // 符合要求添加用户和批发商的距离。批发商的基本信息
				    Map<String, Object> mapData = hunterAllList.get(i);
				   
					double round = (double) mapData.get("distance");
                 
					double double1 = round / 1000;//计算距离 单位（km）
					BigDecimal bg = new BigDecimal(double1);
					try {
					
						double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						if (double1 > 0.01) {
							mapData.put("distance", double3 + " km");
						} else {
							mapData.put("distance", "<0.01 km");
	
						}
						
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
					
					// null 做空处理
					Object num=mapData.get("num");
					if(null==num){
						mapData.put("num", "0");
					} else {
						mapData.put("num", NumberFormat.getFormatNumber(String.valueOf(num)));
					}
					Object major=mapData.get("major");
					if(null==major){mapData.put("major", "");}
					
					//等级 0 null 大众 1高级 2专家 3顶级     批发商等级
					Object level=mapData.get("level");
					if(null==level){level="";}
					level=level.toString();
					if("0".equals(level) || "".equals(level)){
						mapData.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
					}else if("1".equals(level)){
						mapData.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
					}else if("2".equals(level)){
						mapData.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
					}else if("3".equals(level)){
						mapData.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
					}
					
				}	
				result.setCode(1);
				result.setMsg("查询成功");
				result.setContent(hunterAllList);
				return result;
			}
				
	}
	@Override
	public Result getIndustryTypeFirstLimit(int page, int rows) {
		// TODO Auto-generated method stub
		 Result result = new Result();
		 List<Map> industryTypeFirstLimit = industryTypeDao.getIndustryTypeFirstLimit(page,rows);
		 result.setCode(1);
		 result.setMsg("获取成功");
		 result.setContent(industryTypeFirstLimit);
		 return result;
	}

}
