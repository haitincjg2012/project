package com.alqsoft.controller.mobile.view.producttype;

import com.alqsoft.service.producttype.ProductTypeService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 商品分类
 */
@RequestMapping("mobile/view/productyypeview")
@Controller
public class ProductTypeViewController {

	private static final Logger log = LoggerFactory.getLogger(ProductTypeViewController.class);

	@Autowired
	private ProductTypeService productTypeService;
	
	/**
	 * 用于根据商品分类查询商品列表
	 * 根据一级分类id查询二级分类，没有二级分类，直接默认未分类，
	 * @param id  父级分类id
	 * @return
	 */
	@RequestMapping(value="secondproducttype",method=RequestMethod.POST)
	@ResponseBody
	public Result findSecondProductTypeByFirst(@RequestParam(value="id") Long id){
		if(id==null){
			return ResultUtils.returnError("参数为空，请选择一级分类");
		}
		List<Map<String,Object>> second = productTypeService.getSecondProductType(id);
		if(second.size()==0){
			Map<String,Object> nosecond = new HashMap<String,Object>();
			nosecond.put("content", "未分类");
			nosecond.put("id", id);
			second.add(nosecond);
		}
		return ResultUtils.returnSuccess("查询成功", second);
	}

	/**
	 * 获取批发商的商品分类列表
	 * @param hid
	 * @return
	 */
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public @ResponseBody Result getProductTypeList(@RequestParam("hid")Long hid,
												   @RequestParam(value = "pid",required = false)
														   Long pid,
												   @RequestParam(value = "type",defaultValue = "0",required = false)
														   Long type){
		log.info("getProductTypeList : hid:{} ",hid );
		return productTypeService.getProductTypeList(hid,pid,type);
	}
}
