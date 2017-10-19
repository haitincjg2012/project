package com.alqsoft.controller.mobile.after.product;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.json.JsonUtil;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.utils.UploadFileName;
import com.alqsoft.vo.ProductVo;

@RequestMapping("mobile/after/productmanager")
@Controller
public class ProductManagerController {

	@Autowired
	private ProductService productService;
	
	/**
	 * 商品管理界面的出售中和下架列表
	 * @param status  : 0 下架 1出售中
	 * @param type    1：添加时间降序，2添加时间升序，3销量降序，4销量升序，5库存降序，6库存降序
	 * @param page
	 * @param rows
	 * @param member  
	 * @return
	 */
	@RequestMapping(value="productsaleorcancellist",method=RequestMethod.POST)
	@ResponseBody
	public Result productSaleOrCancelList(
			@RequestParam(value="status",defaultValue="1") Integer status,
			@RequestParam(value="type",defaultValue="1") Integer type,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows,
			 @MemberAnno Member member){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		return productService.findProductSaleOrCancelList(member.getHunter().getId(), status, type,page, rows);
	}
	
	/**
	 * 商品的批量下架和上架
	 * @param productid   多个商品id以逗号拼接
	 * @param member
	 * @param type        1：下架，2：上架
	 * @return
	 */
	@RequestMapping(value="productbatchsaleorcancel",method=RequestMethod.POST)
	@ResponseBody
	public Result productBatchSaleOrCancel(
			@RequestParam(value="productid") String productid,
			@RequestParam(value="type")  Integer type,
			@MemberAnno Member member
			){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		return productService.updateBatchProductSaleOrCancel(member.getHunter().getId(),productid,type);
		
	}
	
	/**
	 * 商品的批量分类  如果一级分类下有二级分类，那么只能移动到二级分类下。
	 * @param productid   多个商品id以逗号拼接    
	 * @param kindid      分类id
	 * @return
	 */
	@RequestMapping(value="productbathctype",method=RequestMethod.POST)
	@ResponseBody
	public Result productBathcType(
			@RequestParam(value="productid") String productid,
			@RequestParam(value="kindid")  Long kindid,
			@MemberAnno Member member){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		return productService.updateBatchProductKind(member.getHunter().getId(),productid,kindid);
	}
	
	
	/**
	 * 商品轮播图片上传
	 * @param productid   多个商品id以逗号拼接    
	 * @return
	 */
	@RequestMapping(value="productattachment",method=RequestMethod.POST)
	@ResponseBody
	public Result productattachment(
			@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request,
			@MemberAnno Member member){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module=UploadFileName.PRODUCT_TRACE.getName();
		return productService.mobileUploadProductAttachment(urlfile,new Object[]{productService,"saveProductAttachment"},module,extendFile);
	}
	
	
	/**
	 * 添加商品  @MemberAnno Member member
	 * @return
	 */
	@RequestMapping(value="addproduct",method=RequestMethod.POST)
	@ResponseBody
	@Explosionproof
	public Result addproduct(
			@RequestParam(value="product") String product,
			@MemberAnno Member member){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		ProductVo productdata=null;
		try{
			productdata = JsonUtil.jsonToObject(product, ProductVo.class);
		}catch(Exception e){
			e.printStackTrace();
			return ResultUtils.returnError("参数格式非法");
		}
		return productService.saveProduct(productdata,member.getHunter().getId());
	}
	
	
	/**
	 * app上传商品详情图片
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("productdetailattachment")
	@ResponseBody
	public Result uploadProductDetailAttachment(
			@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request
			){
		
		String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module=UploadFileName.PRODUCT_TRACE.getName();
		
		return productService.mobileUploadProductDetailAttachment(urlfile,new Object[]{productService,"saveProductDetailAttachment"},module,extendFile);
	}
	
	/**
	 * 根据商品id获取商品的所有信息，编辑商品时会用到
	 * @param member
	 * @return
	 */
	@RequestMapping(value="productallmsg",method=RequestMethod.POST)
	@ResponseBody
	public Result getProductAllMsg(@MemberAnno Member member,Long productid){
		if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
		return productService.getProductAllMsg(member.getHunter().getId(),productid);
	
	}


}
