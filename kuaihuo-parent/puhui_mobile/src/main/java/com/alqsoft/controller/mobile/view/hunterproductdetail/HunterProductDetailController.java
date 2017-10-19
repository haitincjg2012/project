package com.alqsoft.controller.mobile.view.hunterproductdetail;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.hunterproductdetail.HunterProductDetailService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月1日 下午6:42:16
 * 商品的详情页面
 */
@RequestMapping("mobile/view/detail")
@Controller
public class HunterProductDetailController {
	
	@Autowired
	private HunterProductDetailService productDetailService;
	
	/***
	 *  批发商的商品的文本详情
	 * @param productId
	 * @return result
	 */
	@RequestMapping(value="text",method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterProductDetailText(@RequestParam(value="productId" ) Long productId,@RequestParam(value="mId",defaultValue="-1")Long mId){
		
		Result result=productDetailService.getProductDetailText(productId,mId);
		
		return result;
	}
     
	/***
	 * 此商品的评论展示
	 * @param productId
	 * @return result
	 */
/*	@RequestMapping(value="comment",method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterProductDetailComment(@RequestParam(value="productId" ) Integer productId){
		
		Result result=productDetailService.getProductComment(productId);
		
		return result;
	}*/
	
	/***
	 * 商品图片详情
	 * @param productId 商品Id
	 * @param mId 用户Id
	 * @return result
	 */
	@RequestMapping(value="image",method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterProductDetailImage(@RequestParam(value="productId" ) Long productId){
		Result result=productDetailService.getProductDetailImage(productId);
		return result;
	}
	
	
	/**
	 * 此接口可以获取到的是通过查询进行获取结果
	 * 这里默认使用的是批发商id
	 * */
	@RequestMapping(value="basemessage",method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterBaseMessage(@RequestParam(value="hId" ) Long hId){
		Result result=productDetailService.getHunterBaseMessage(hId);
		return result;
	}
	  /***
     * 销售商品的分类,商品的规格
     * @param pId 商品id
     * @return
     */
    @RequestMapping(value = "saleproducttype",method = RequestMethod.POST)
    @ResponseBody
    public Result saleProductType(@RequestParam(value="pId" )Long pId){
    	
    	return productDetailService.saleProductType(pId);
    }
    /**
     * 详情文本链接
     * @param id 商品id
     * @param model
     * @return
     */
    @RequestMapping(value="textdetails" )
    public String textDetailsUrl(Long id,Model model){
    	productDetailService.textDetailsUrl(id,model);
    	return "view/textDetailsIos";
    }
}
