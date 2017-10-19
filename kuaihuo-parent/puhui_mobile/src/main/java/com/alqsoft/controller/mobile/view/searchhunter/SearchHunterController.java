package com.alqsoft.controller.mobile.view.searchhunter;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.searchhunter.SearchHunterService;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年3月29日 下午11:21:45
 * 
 */
@Controller
@RequestMapping("mobile/view/searchhunter")
public class SearchHunterController {
	
	@Autowired
    private SearchHunterService searchHunterService;
/***
 * 	
 * @param name 批发商的匿名或者是商品
 * @param sort 排序要求，好评排序为1,销售量为2,经纬度3
 * @param longitude 经度
 * @param latitude 纬度
 * @return
 */
@RequestMapping(value="hunters",method=RequestMethod.POST)
@ResponseBody
public Result getSearchHunterByNameOrProduct(@RequestParam(value="name") String name,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="numPage",defaultValue="10") Integer numPage,@RequestParam(value="sort",defaultValue="1") Integer sort,@RequestParam(value="longitude",defaultValue="lo") String longitude,@RequestParam(value="latitude",defaultValue="la") String latitude ){
	return searchHunterService.getSearchHunterByNameOrProduct(name,currentPage,numPage,sort,longitude,latitude);
}
/***
 * @param 模糊查询批发商对应的商品
 * @param hId 批发商的id
 * @param name 模糊数据
 * @return   
 */
@RequestMapping(value="products",method=RequestMethod.POST)
@ResponseBody
public Result getSearchProductByName(@RequestParam(value="hId") Long hId,@RequestParam(value="name") String name,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="numPage",defaultValue="10") Integer numPage){
	
	return searchHunterService.getSearchProductByName(hId,name,currentPage,numPage);
}
/***
 * 	
 * @param name 批发商的匿名或者是商品
 * @param sort 排序要求，好评排序为1,销售量为2,经纬度3,都需要经纬度进行测试距离
 * @param longitude 经度
 * @param latitude 纬度
 * @return
 */
@RequestMapping(value="hunters_tude",method=RequestMethod.POST)
@ResponseBody
public Result getSearchHunterAddtudeByNameOrProduct(@RequestParam(value="name") String name,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="numPage",defaultValue="10") Integer numPage,@RequestParam(value="sort",defaultValue="1") Integer sort,@RequestParam(value="longitude",defaultValue="lo") String longitude,@RequestParam(value="latitude",defaultValue="la") String latitude ){
	return searchHunterService.getSearchHunterAddtudeByNameOrProduct(name,currentPage,numPage,sort,longitude,latitude);
}
}
