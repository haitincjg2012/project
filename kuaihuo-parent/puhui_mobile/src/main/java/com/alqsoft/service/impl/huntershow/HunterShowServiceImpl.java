package com.alqsoft.service.impl.huntershow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.huntershow.HunterShowDao;
import com.alqsoft.service.comment.OrderCommentService;
import com.alqsoft.service.huntershow.HunterShowService;
import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;



@Service
@Transactional(readOnly=true)
public class HunterShowServiceImpl implements HunterShowService {

	@Autowired
	private HunterShowDao hunterShowDao;
	@Autowired
	private OrderCommentService orderCommentService;

	@Override
	public Result getHunterShowList(Long id,Long member_id) {
		if(id==null){
			return ResultUtils.returnError("没有对应的批发商ID,请查看数据！");
		}
		//批发商介绍
		List<Map<String,Object>> showList = hunterShowDao.getHunterShowList(id);
		for (Map<String,Object> map : showList) {
			//数字转换
			map.put("num", NumberFormat.getFormatNumber(String.valueOf(map.get("num"))));
			map.put("good_comment_num_order", NumberFormat.getFormatNumber(String.valueOf(map.get("good_comment_num_order"))));
			map.put("foucs_num", NumberFormat.getFormatNumber(String.valueOf(map.get("foucs_num"))));
			map.put("allnum", NumberFormat.getFormatNumber(String.valueOf(map.get("allnum"))));
			
			Integer valueOf = Integer.valueOf(map.get("level").toString());
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
		for(Map<String,Object> hunter : showList){
			String pro=hunter.get("provincename")==null?"":hunter.get("provincename").toString();
			String city=hunter.get("cityname")==null?"":hunter.get("cityname").toString();
			String country=hunter.get("countyname")==null?"":hunter.get("countyname").toString();
			if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
				hunter.put("position", pro+country);//服务的区域
			}else{
				hunter.put("position",pro+city);
			}
		}
		//店铺介绍图
		List<Map<String,Object>> imgList = hunterShowDao.getImgList(id);
		//显示最新的一条批发商法则
		List<Map<String,Object>> roleList = hunterShowDao.getRoleList(id);
		//商品展示（以商品发布的时间顺序排序 ，显示4个）
		List<Map<String,Object>> productList = hunterShowDao.getproductList(id);
		//查看收藏
		Integer collectionType=0;
		if(member_id!=null){
			Map<String,Object> params =new HashMap<String,Object>();
			params.put("id", id);
			params.put("member_id", member_id);
			collectionType = hunterShowDao.getcollectionType(params);
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("showList", showList);
		map.put("imgList", imgList);
		map.put("roleList", roleList);
		map.put("productList", productList);
		//判断0是收藏 1是未收藏
		map.put("collectionType", collectionType==null?1:collectionType);
		return ResultUtils.returnSuccess("批发商详情页,批发商信息+介绍", map);
	}

	@Override
	public Result getSingleShowList(Long id) {
		if(id==null){
			return ResultUtils.returnError("没有对应的批发商ID,请查看数据！");
		}
		//店铺介绍图
		List<Map<String,Object>> imgList = hunterShowDao.getImgList3(id);
		//用户评论
		//List<Map<String, Object>> userCommentList = orderCommentService.hunterOrderCommentList();
		//map.put("userCommentList", userCommentList);
		return ResultUtils.returnSuccess("批发商详情页,SingleShow", imgList);
	}

	@Override
	public Result getDetailShowList(Long id) {
		if(id==null){
			return ResultUtils.returnError("没有对应的批发商ID,请查看数据！");
		}
		//批发商介绍
		List<Map<String,Object>> showList = hunterShowDao.getHunterShowList(id);
		for (Map<String,Object> map : showList) {
			Integer valueOf = Integer.valueOf(map.get("level").toString());
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
				map.put("level",  HunterLevelEnums.LT_LEVEL_TOP.getData());
			}
		}
		for(Map<String,Object> hunter : showList){
			String pro=hunter.get("provincename")==null?"":hunter.get("provincename").toString();
			String city=hunter.get("cityname")==null?"":hunter.get("cityname").toString();
			String country=hunter.get("countyname")==null?"":hunter.get("countyname").toString();
			if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
				hunter.put("position", pro+country);//服务的区域
			}else{
				hunter.put("position",pro+city);
			}
		}
		//店铺介绍图
		List<Map<String,Object>> imgList = hunterShowDao.getImgList(id);
		//显示最新的一条批发商法则
		List<Map<String,Object>> roleList = hunterShowDao.getRoleList(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("showList", showList);
		map.put("imgList", imgList);
		map.put("roleList", roleList);
		return ResultUtils.returnSuccess("批发商详情页,DetailShow", map);
	}

}
