package com.alqsoft.controller.mobile.view.product;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.product.ProductService;

@RequestMapping("mobile/view/productshow")
@Controller
public class ProductViewController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 根据商品分类查询商品
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findproductbytype",method=RequestMethod.POST)
	@ResponseBody
	public Result findProductByTypeList(
			@RequestParam(value="id") Long id,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		
		return productService.findProductByTypeList(id, page, rows);
	}

	/**
	 * 商品详情回显ALL
	 * @return
	 */
	@RequestMapping(value = "productdisplay",method = RequestMethod.POST)
	@ResponseBody
	public Result getAllProduct(@RequestParam(value="hunterId")Long hunterId,String phone,String uuid) {
		Result result = new Result();

		try {
			result = productService.getAllProduct(hunterId);
		}catch (Exception e){
			result.setCode(0);
			result.setMsg("显示商户商品列表失败");
		}
		return result;
	}

	/**
	 * 商品详情回显ByType
	 * @return
	 */
	@RequestMapping(value = "productdisplayByType",method = RequestMethod.POST)
	@ResponseBody
	public Result getProductByType(@RequestParam(value="hunterId")Long hunterId,
								   @RequestParam(value="productTypeId")Long productTypeId,
								   @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
								   @RequestParam(value = "numPage", defaultValue = "10") Integer numPage,
								   String phone,String uuid){
		Result result = new Result();
		try {
			result = productService.getProductByType(hunterId,productTypeId,phone,uuid,currentPage,numPage);
		}catch (Exception e){
			result.setCode(0);
			result.setMsg("显示商户商品列表失败");
		}
		return result;

	}

}
