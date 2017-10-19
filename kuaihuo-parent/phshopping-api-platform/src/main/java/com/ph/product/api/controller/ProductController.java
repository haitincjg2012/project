 /**
 * 
 */
package com.ph.product.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.product.dto.ProductClassifyDTO;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.Product;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.entity.ProductProperty;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.service.IProductClassifyService;
import com.ph.shopping.facade.product.service.IProductImageService;
import com.ph.shopping.facade.product.service.IProductPropertyService;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.product.service.IProductSkuService;
import com.ph.shopping.facade.product.service.IProductSpecificationService;
import com.ph.shopping.facade.product.status.ProductStatusEnum;
import com.ph.shopping.facade.product.vo.ProductClassifyVO;
import com.ph.shopping.facade.product.vo.ProductPropertyVO;
import com.ph.shopping.facade.product.vo.ProductSpecificationVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import com.ph.shopping.facade.system.service.ISystemParameterService;

/**
 * @项目：phshopping-facade-
 *
 * 						@描述：
 *
 * 						@作者： 杨颜光
 *
 * @创建时间：2017年3月14日 下午1:14:28
 * 
 * @Copyright by 杨颜光
 */

@Controller
@RequestMapping("/web/product")
public class ProductController extends BaseController {

	@Reference(version = "1.0.0")
	private IProductService iProductService;

	@Reference(version = "1.0.0")
	private IProductClassifyService iProductClassifyService;

	@Reference(version = "1.0.0")
	private IProductPropertyService iProductPropertyService;

	@Reference(version = "1.0.0")
	private ISupplierService iSupplierService;

	@Reference(version = "1.0.0")
	private IProductImageService iProductImageService;

	@Reference(version = "1.0.0")
	private IProductSpecificationService iProductSpecificationService;

	@Reference(version = "1.0.0")
	private IProductSkuService iProductSkuService;
	
	@Reference(version = "1.0.0")
	private  IProductClassifyService iPhProductClassifyService;

	// 代理商
	@Reference(version = "1.0.0")
	public IAgentService iAgentService;
	
	// 系统参数设置
	@Reference(version = "1.0.0")
	public   ISystemParameterService   iSystemParameterService;
	/***
	 * 商品添加的跳转
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @
	 * @author 杨颜光
	 * @date 2017年4月24日 下午 4:16:28
	 */
	@RequestMapping(value = {"/toAddProductPage","/toAddProductPageForSup"})
	public String toAddProduct(HttpServletRequest request, Model model)  {
		// 获取当前登录用户
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		ProductClassify productQuery1 = new ProductClassify();
		// 查询分类级别为一级的数据
		productQuery1.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		productQuery1.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		productQuery1.setStatus(ProductStatusEnum.ENABLED.getCode());
		List<ProductClassify> list = this.iProductClassifyService.getProductClassifyList(productQuery1);
		model.addAttribute("list", list);
		
		// 查询供应商数据
		// 供应商
		String supplierId = request.getParameter("supplierId");
		SupplierDTO supplierDTO = new SupplierDTO();
		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())&&!StringUtils.isNotEmpty(supplierId)) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
		}

		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
			supplierDTO.setCreaterId(info.getId());
		}
		if (StringUtils.isNotBlank(supplierId)) {
			supplierDTO.setId(Long.parseLong(supplierId));
			model.addAttribute("supplierId", supplierId);
			model.addAttribute("supType", "1");
		} else {
			model.addAttribute("supplierId","-1");
			model.addAttribute("supType", "2");
		}
//		supplierDTO.setIsFrozen(SPMEnum.supplierIsFrozen.ZERO.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);

		model.addAttribute("supplierList", supplierList);
		//查询系统参数获取商品的进货价比例
		List<Long> ids=new ArrayList<>();
		ids.add(2L);
		Result result=this.iSystemParameterService.getSystemParameterListByIds(ids);
		Map<Long,Object> map= (Map<Long, Object>) result.getData();
		model.addAttribute("purchasePriceRatio",map.get(2L));
		
		return "product/products/add";
	}


	/***
	 * 通过分类Id获取商品分类的属性
	 * 
	 * @param fid
	 * @param sid
	 * @param tid
	 * @
	 * @author 杨颜光
	 * @date 2017年4月24日 下午 4:16:30
	 * 
	 */
	@RequestMapping(value = "/getClassifiesForThreeId")
	@ResponseBody
	public Result toGetClassifyPropery(@RequestParam("fid") Long fid, @RequestParam("sid") Long sid,
			@RequestParam("tid") Long tid)  {
		Result result = new Result();
		ProductProperty  first = new ProductProperty();
		first.setClassifyId(fid);
		first.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		List<ProductProperty> list1 = this.iProductPropertyService.getProductProperties(first);
		ProductProperty second = new ProductProperty();
		second.setClassifyId(sid);
		second.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		List<ProductProperty> list2 = this.iProductPropertyService.getProductProperties(second);
		ProductProperty three = new ProductProperty();
		three.setClassifyId(tid);
		three.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		List<ProductProperty> list3 = this.iProductPropertyService.getProductProperties(three);
			
		List<ProductProperty> maps = new ArrayList<ProductProperty>();
		maps.addAll(list1);
		maps.addAll(list2);
		maps.addAll(list3);
		result.setData(maps);
		return result;
	}

	/**
	 *  商品添加
	 * 
	 * @param product 商品实体
	 * @param imageJSON 图片
	 * @param productPropertyValJSON 属性值
	 * @param specificationAndValJson 规格和规格值
	 * @param productSkuJSON sku
	 * @param request
	 * @return  Result
	 * @author 杨颜光
	 * @date 2017年5月17日 下午 6:55:30
	 *
	 */
	@RequestMapping(value ={ "/add","/addSup"}, method = RequestMethod.POST)
	@ResponseBody
	public Result addProduct(@ModelAttribute Product product, @RequestParam String imageJSON,
                             @RequestParam String productPropertyValJSON,@RequestParam String specificationAndValJson,
                             @RequestParam String productSkuJSON,HttpServletRequest request)  {
		Result result = new Result();
		// 获取当前的用户
		SessionUserVO info = getLoginUser();
		product.setCreaterId(info.getId());// 创建人
		// 获取用户角色区分是全国商品，还是本地商品；
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		byte typeId = role.getRoleCode();
		if (typeId == RoleEnum.ADMIN.getCode()) { // 全国商品
			product.setProductType(ProductStatusEnum.FROM_SYSTEM_PLATFORM.getCode());
			product.setProductForm(ProductStatusEnum.NATIONWIDE.getCode());
		}
		if (typeId == RoleEnum.CITY_AGENT.getCode()) {// 本地商品
			product.setProductType(ProductStatusEnum.FROM_AGENT.getCode());
			product.setProductForm(ProductStatusEnum.LOCAL.getCode());
		}
		product.setCreateTime(new Date());
				product.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());// 未删除
				product.setAuditState(ProductStatusEnum.APPROVE.getCode());
				product.setSaleNoSaleStatus(ProductStatusEnum.UNPUBLISH.getCode());
				product.setCommoditySales(0);

		// 将json转为对象、集合
		List<ProductImage> imageList = JSON.parseArray(imageJSON, ProductImage.class);
		List<ProductPropertyVal> ppvList = JSON.parseArray(productPropertyValJSON, ProductPropertyVal.class);
		List<ProductSku> skus= JSON.parseArray(productSkuJSON, ProductSku.class);

		// 添加开始
		result = this.iProductService.addProduct(product, imageList, ppvList,skus,specificationAndValJson);
		return result;
	}

	/**
	 * 修改商品接口
	 * 
	 * @param product
	 *            商品实体, productPropertyValJSON
	 * @return result
	 * @author 李超
	 * @
	 * @Time 2017年3月14日
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public Result updateProduct(@ModelAttribute Product product, @RequestParam String description,
                                @RequestParam String imageJSON, @RequestParam String productPropertyValJSON,
                                @RequestParam String specificationAndValJson,
                                @RequestParam String productSkuJSON,HttpServletRequest request) {
		Result result = new Result();
		logger.debug("-------------------------------获取前台商品数据-------------------------------");
		logger.debug("imageList: " + imageJSON);
		logger.debug("pvList: " + productPropertyValJSON);
		logger.debug("-------------------------------获取前台商品数据-------------------------------");

		// 获取当前用户
		SessionUserVO info = getLoginUser();
		//更新商品特殊值
		product.setUpdaterId(info.getId());//更新修改人
		product.setUpdateTime(new Date());//更新修改时间
		product.setDescription(description);//单独设置富文本的值
		product.setAuditState(ProductStatusEnum.APPROVE.getCode());//重新设置为待审核
		//通过JSON解析图片为集合
		List<ProductImage> imageList = JSON.parseArray(imageJSON, ProductImage.class);
		for (ProductImage productImage : imageList) {
			productImage.setProductId(product.getId());
		}
		//通过JSON解析属性值为集合
		List<ProductPropertyVal> pvList = JSON.parseArray(productPropertyValJSON, ProductPropertyVal.class);
		//通过JSON解析SKU为集合
		List<ProductSku> skuList= JSON.parseArray(productSkuJSON, ProductSku.class);
		return iProductService.updateProduct(product, imageList, pvList, skuList,specificationAndValJson);
	}

	/**
	 *	到商品修改页面 根据商品id获取商品详细信息
	 *
	 * @param productDTO
	 * @return String
	 * 
	 * @author: 李超
	 * @date: 2017-05-18 11:16:15
	 */
	@RequestMapping(value = "/toUpdateProduct", method = RequestMethod.GET)
	public String toUpdateProduct(@ModelAttribute ProductDTO productDTO, Model model) {

		// 商品对象
		Product product = new Product();
		product.setId(productDTO.getId());
		product = iProductService.getProduct(product);
		logger.info("待修改商品数据: " +JSON.toJSONString(product));
		model.addAttribute("product", product);

		// 供应商
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		SupplierDTO supplierDTO = new SupplierDTO();
//		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())) {
//			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
//		}
//
//		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
//			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
//			supplierDTO.setCreaterId(info.getId());
//		}
		//如果商品是全国商品 则设置查询全国供应商
		if (product.getProductForm() == 1) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
		}
		//如果商品是代理商商品 则设置查询本地供应商
		if (product.getProductForm() == 2) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
			supplierDTO.setCreaterId(product.getCreaterId());
		}
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);
		logger.debug("供应商列表: " + supplierList);
		model.addAttribute("supplierList", supplierList);



		//商品图片
		ProductImage productImage = new ProductImage();
		productImage.setProductId(product.getId());
		List<ProductImage> productImageList = iProductImageService.getProductImageList(productImage);
		//返回JSON页面由控件加载
		model.addAttribute("productImageJsonList", JSON.toJSONString(productImageList));

		//商品分类
		ProductClassifyDTO productClassifyDTO = new ProductClassifyDTO();
		productClassifyDTO.setId(product.getProductClassifyId());
		productClassifyDTO.setStatus(ProductStatusEnum.ENABLED.getCode());//设置条件为已启用的

		//根据商品当前类型获取所有上级类型(页面回显)
		List<ProductClassifyVO> parentList = iProductClassifyService.getProductClassifyParents(productClassifyDTO);
		//设置获得最顶级的那个类型的条件
		ProductClassifyDTO topLevelProductClassifyDTO = new ProductClassifyDTO();
		//设置条件为已启用的
		topLevelProductClassifyDTO.setStatus(ProductStatusEnum.ENABLED.getCode());
		for (int i = 0; i < parentList.size(); i++) {
			ProductClassifyVO temp = parentList.get(i);
			if(temp.getParentId().equals(0L)){
				topLevelProductClassifyDTO.setId(temp.getId());
				break;
			}
		}
		//根据最顶级的类型获得该类型的所有子类型(页面回显)
		List<ProductClassifyVO> childrenList = iProductClassifyService.getProductClassifyChildren(topLevelProductClassifyDTO);
		//获取所有一级 加入到childrenList中用于回显
		ProductClassify productClassify = new ProductClassify();
		productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		List<ProductClassify> list = iProductClassifyService.getProductClassifyList(productClassify);
		for (int i = 0; i < list.size() ; i++) {
			ProductClassify pc = list.get(i);
			if(!pc.getId().equals(topLevelProductClassifyDTO.getId())) {
				ProductClassifyVO pcvo = new ProductClassifyVO();
				pcvo.setId(pc.getId());
				pcvo.setClassifyLevel(pc.getClassifyLevel());
				pcvo.setClassifyName(pc.getClassifyName());
				pcvo.setParentId(pc.getParentId());
				pcvo.setIsSelect(0);
				childrenList.add(pcvo);
			}
		}

		//记录二级id
		Long twoLevelId = 0L;
		//设置回显时选中
		for (int i = 0; i <parentList.size() ; i++) {
			for (int j = 0; j < childrenList.size(); j++) {
				if(childrenList.get(j).getId().equals(parentList.get(i).getId())){
					childrenList.get(j).setIsSelect(1);
					if(childrenList.get(j).getClassifyLevel() == 2){
						twoLevelId = childrenList.get(j).getId();
					}
					continue;
				}
			}
		}
		//去掉二级下不是该二级分类下的三级分类

		Iterator<ProductClassifyVO> sListIterator = childrenList.iterator();
		while(sListIterator.hasNext()){
			ProductClassifyVO e = sListIterator.next();
			if(!e.getParentId().equals(twoLevelId) && e.getClassifyLevel() == 3){
				sListIterator.remove();
			}
		}

		model.addAttribute("parentList", parentList);
		model.addAttribute("childrenList", childrenList);

		//属性和属性值
		ProductDTO p = new ProductDTO();
		p.setProductClassifyId(product.getProductClassifyId());
		p.setId(product.getId());
		List<ProductPropertyVO> productPropertyVOList = iProductPropertyService.getProductPropertyVoByThreeCassifyId(p);
		model.addAttribute("productPropertyVOList", productPropertyVOList);

		//规格和规格值
		List<ProductSpecificationVO> productSpecificationVOList = iProductSpecificationService.getProductSpecificationVOListByProductId(productDTO);
		model.addAttribute("productSpecificationVOList", productSpecificationVOList);

		//商品SKU(规格值的组合)
		ProductSku productSku = new ProductSku();
		productSku.setProductId(product.getId());
		List<ProductSku> productSkuList = iProductSkuService.getProductSkuList(productSku);
		model.addAttribute("productSkuList", productSkuList);
		
		//查询系统参数获取商品的进货价比例
		List<Long> ids=new ArrayList<>();
		ids.add(2L);
		Result result=this.iSystemParameterService.getSystemParameterListByIds(ids);
		Map<Long,Object> map= (Map<Long, Object>) result.getData();
		model.addAttribute("purchasePriceRatio",map.get(2L));
		

		return "product/products/update";
	}

	/**
	 * 商品列表查询页面跳转
	 * 
	 * @param model
	 * @param supplierId
	 * @param request
	 * @return String
	 * @author  杨颜光
	 * @date 2017年5月17日 下午 7:04:30
	 */
	@RequestMapping(value = {"/toProductPage","/toProductPageForSup"})
	public String toProductPage(Model model, String supplierId, HttpServletRequest request)  {
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		SupplierDTO supplierDTO = new SupplierDTO();
		if (StringUtils.isNotEmpty(supplierId)) {// 其他地方调用的时候
			supplierDTO.setId(Long.parseLong(supplierId));
			model.addAttribute("supplierId", supplierId);// 接口那边需要的id
			model.addAttribute("supType", "1");
		} else if(role.getRoleCode() == RoleEnum.ADMIN.getCode()) {
			model.addAttribute("createrId", "");
			model.addAttribute("supplierId", "");// 接口那边需要的id
		}else if (role.getRoleCode() == RoleEnum.SUPPLIER.getCode()) {
			SupplierDTO dto=new SupplierDTO();
			dto.setUserId(info.getId());
			SupplierVO  supplierVO=this.iSupplierService.getSupplierListById(dto);
			model.addAttribute("supplierId", supplierVO.getId());
			supplierDTO.setId(supplierVO.getId());
		} else {
			model.addAttribute("createrId", info.getId());
			model.addAttribute("supplierId", "");// 接口那边需要的id
		}
		
		// 查询供应商数据
		// 供应商
		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())&&!StringUtils.isNotEmpty(supplierId)) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
		}
		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
			supplierDTO.setCreaterId(info.getId());
			
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setUserId(info.getId());
			AgentVO agent = iAgentService.getAgentVODateilById(agentDTO);
			model.addAttribute("isFrozenAgent", agent.getIsFrozen());
		}
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);
		model.addAttribute("spList", supplierList);
		
		//查询一级分类
		  ProductClassify productClassify = new ProductClassify();
		    productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		    productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		    productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
			List<ProductClassify> list = this.iPhProductClassifyService.getProductClassifyList(productClassify);
			model.addAttribute("list", list);
			model.addAttribute("userRole", role.getRoleCode());
		return "product/products/list";
	}



	/**
	 * 商品分页列表查询
	 * 
	 * @param pageBean
	 * @param phProductDto
	 * @return Object
	 * @author 杨颜光
	 *
	 */
	@RequestMapping(value = {"/getProductByPage","/getProductByPageForSup"},method = RequestMethod.GET)
	public @ResponseBody Object getProductByPage(PageBean pageBean, ProductDTO productDto)  {
		
			productDto.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
			Result  result=iProductService.getProductByPage(pageBean, productDto);
		    return result;
	}

	/**
	 * 逻辑删除商品
	 * 
	 * @author 杨颜光
	 * @param phProductDto:id
	 * @return Result
	 */
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public @ResponseBody Result deleteProduct(Product product)  {
		SessionUserVO info = getLoginUser();
		product.setIsDelete(ProductStatusEnum.DELETED.getCode());
		product.setUpdaterId(info.getId());
		product.setUpdateTime(new Date());
		Result result =this.iProductService.deleteLogicProduct(product);
		return result;
	}

	/**
	 *  审核商品
	 * 
	 * @param product
	 * @return result
	 * @author 杨颜光
	 */
	@RequestMapping(value = {"/auditProduct","/noAuditProduct"}, method = RequestMethod.GET)
	public @ResponseBody Result auditProduct(Product product)  {
		Result result=new Result();
		SessionUserVO info = getLoginUser();
		product.setUpdaterId(info.getId());
		product.setUpdateTime(new Date());
		result=iProductService.auditProduct(product);
		return result;
	}

	
	/**
	 * 商品上架、下架
	 * 
	 * @param product
	 * @return result
	 * @author 杨颜光
	 *
	 */
	@RequestMapping(value ={"/onsalProduct","/noOnsalProduct"},method = RequestMethod.GET)
	public @ResponseBody Result onsalProduct(Product product)  {
		Result result=new Result();
		SessionUserVO info = getLoginUser();
		product.setUpdaterId(info.getId());
		product.setUpdateTime(new Date());
		result=iProductService.onsalProduct(product);
		return result;
	}

	
	/**
	 * 商品详情
	 *
	 * @param productDTO
	 * @return 
	 * 
	 * @author: 李超
	 * @date: 2017-06-16 11:49:11
	 */
	@RequestMapping(value = {"/toSeeProductDetails","/localProductDetail","/countryProductDetail"}, method = RequestMethod.GET)
	public String toSeeProductDetails(@ModelAttribute ProductDTO productDTO, Model model) {

		// 商品对象
		Product product = new Product();
		product.setId(productDTO.getId());
		product = iProductService.getProduct(product);
		logger.info("待修改商品数据: " +JSON.toJSONString(product));
		model.addAttribute("product", product);

		// 供应商
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		SupplierDTO supplierDTO = new SupplierDTO();
//		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())) {
//			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
//		}
//
//		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
//			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
//			supplierDTO.setCreaterId(info.getId());
//		}
		//如果商品是全国商品 则设置查询全国供应商
		if (product.getProductForm() == 1) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
		}
		//如果商品是代理商商品 则设置查询本地供应商
		if (product.getProductForm() == 2) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
			supplierDTO.setCreaterId(product.getCreaterId());
		}
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);
		logger.debug("供应商列表: " + supplierList);
		model.addAttribute("supplierList", supplierList);



		//商品图片
		ProductImage productImage = new ProductImage();
		productImage.setProductId(product.getId());
		List<ProductImage> productImageList = iProductImageService.getProductImageList(productImage);
		//页面模板遍历
		model.addAttribute("productImageList", productImageList);

		//商品分类
		ProductClassifyDTO productClassifyDTO = new ProductClassifyDTO();
		productClassifyDTO.setId(product.getProductClassifyId());
		productClassifyDTO.setStatus(ProductStatusEnum.ENABLED.getCode());//设置条件为已启用的

		//根据商品当前类型获取所有上级类型(页面回显)
		List<ProductClassifyVO> parentList = iProductClassifyService.getProductClassifyParents(productClassifyDTO);
		//设置获得最顶级的那个类型的条件
		ProductClassifyDTO topLevelProductClassifyDTO = new ProductClassifyDTO();
		//设置条件为已启用的
		topLevelProductClassifyDTO.setStatus(ProductStatusEnum.ENABLED.getCode());
		for (int i = 0; i < parentList.size(); i++) {
			ProductClassifyVO temp = parentList.get(i);
			if(temp.getParentId().equals(0L)){
				topLevelProductClassifyDTO.setId(temp.getId());
				break;
			}
		}
		//根据最顶级的类型获得该类型的所有子类型(页面回显)
		List<ProductClassifyVO> childrenList = iProductClassifyService.getProductClassifyChildren(topLevelProductClassifyDTO);
		//获取所有一级 加入到childrenList中用于回显
		ProductClassify productClassify = new ProductClassify();
		productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		List<ProductClassify> list = iProductClassifyService.getProductClassifyList(productClassify);
		for (int i = 0; i < list.size() ; i++) {
			ProductClassify pc = list.get(i);
			if(!pc.getId().equals(topLevelProductClassifyDTO.getId())) {
				ProductClassifyVO pcvo = new ProductClassifyVO();
				pcvo.setId(pc.getId());
				pcvo.setClassifyLevel(pc.getClassifyLevel());
				pcvo.setClassifyName(pc.getClassifyName());
				pcvo.setParentId(pc.getParentId());
				pcvo.setIsSelect(0);
				childrenList.add(pcvo);
			}
		}

		//记录二级id
		Long twoLevelId = 0L;
		//设置回显时选中
		for (int i = 0; i <parentList.size() ; i++) {
			for (int j = 0; j < childrenList.size(); j++) {
				if(childrenList.get(j).getId().equals(parentList.get(i).getId())){
					childrenList.get(j).setIsSelect(1);
					if(childrenList.get(j).getClassifyLevel() == 2){
						twoLevelId = childrenList.get(j).getId();
					}
					continue;
				}
			}
		}
		//去掉二级下不是该二级分类下的三级分类

		Iterator<ProductClassifyVO> sListIterator = childrenList.iterator();
		while(sListIterator.hasNext()){
			ProductClassifyVO e = sListIterator.next();
			if(!e.getParentId().equals(twoLevelId) && e.getClassifyLevel() == 3){
				sListIterator.remove();
			}
		}

		model.addAttribute("parentList", parentList);
		model.addAttribute("childrenList", childrenList);

		//属性和属性值
		ProductDTO p = new ProductDTO();
		p.setProductClassifyId(product.getProductClassifyId());
		p.setId(product.getId());
		List<ProductPropertyVO> productPropertyVOList = iProductPropertyService.getProductPropertyVoByThreeCassifyId(p);
		model.addAttribute("productPropertyVOList", productPropertyVOList);

		//规格和规格值
		List<ProductSpecificationVO> productSpecificationVOList = iProductSpecificationService.getProductSpecificationVOListByProductId(productDTO);
		model.addAttribute("productSpecificationVOList", productSpecificationVOList);

		//商品SKU(规格值的组合)
		ProductSku productSku = new ProductSku();
		productSku.setProductId(product.getId());
		List<ProductSku> productSkuList = iProductSkuService.getProductSkuList(productSku);
		model.addAttribute("productSkuList", productSkuList);
			return "product/products/detail";
	}
	
	/**
	 *  查看商品sku
	 * @author 杨颜光
	 */
	@RequestMapping(value = "/seeProductSku", method = RequestMethod.GET)
	public @ResponseBody Result seeProductSku(Long id)  {
		Result result=new Result();
		ProductSku  productSku=new ProductSku();
		productSku.setProductId(id);
		List<ProductSku>  productSkus=this.iProductSkuService.getProductSkuList(productSku);
		result.setData(productSkus);
		return result;
	}

}
