
package com.ph.product.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.product.dto.ProductClassifyDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.service.IProductClassifyService;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.product.status.ProductStatusEnum;
import com.ph.shopping.facade.product.vo.ProductClassifyVO;

/**
 * ProductClassifyController 商品分类控制器
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-15 19:46:13
 */
@Controller
@RequestMapping("/web/product/productClassify")
public class ProductClassifyController extends BaseController {

	@Reference(version = "1.0.0")
	private IProductClassifyService iProductClassifyService;

	@Reference(version = "1.0.0")
	private IProductService iProductService;

	/**
	 * 商品类型列表查询页面跳转
	 *
	 * @author: 李超
	 * @date: 2017-05-22 17:22:08
	 */
	@RequestMapping(value = "/toClassifyList")
	public String toClassifyList()  {
		return "product/classify/list";
	}

	/**
	 * 查询商品类型树形列表
	 *
	 * @param productClassifyDto
	 * @author: 李超
	 * @date: 2017-05-22 17:22:08
	 */
	@RequestMapping(value = "/getClassifyList", method = RequestMethod.POST)
	public @ResponseBody Result getClassifyList(ProductClassifyDTO productClassifyDTO)  {
		Result result = new Result();
		result.setSuccess(true);
		result.setData(iProductClassifyService.getProductClassifyVOList(productClassifyDTO));
		return result;
	}

	/**
	 * 修改分类排序
	 *
	 * @param productClassifyDTO
	 * @return Result
	 *
	 * @author: 李超
	 * @date: 2017-05-23 11:13:29
	 */
	@RequestMapping(value = "/updateSort", method = RequestMethod.POST)
	public @ResponseBody Result updateSort(ProductClassifyDTO productClassifyDTO)  {
		ProductClassify productClassify = new ProductClassify();
		productClassify.setId(productClassifyDTO.getId());
		productClassify.setSort(productClassifyDTO.getSort());
		return iProductClassifyService.updateProductClassifySort(productClassify);
	}


	/**
	 *
	 * @description: 进入新增商品分类页面
	 * @return
	 * @author lichao
	 * @
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = "/toAddClassify", method = RequestMethod.GET)
	public String toAddProductClassify(Model model)  {
		// 获取所有已启用的一级分类
		ProductClassify productClassify = new ProductClassify();
		productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		List<ProductClassify> list = iProductClassifyService.getProductClassifyList(productClassify);
		model.addAttribute("productClassifyList", list);
		return "product/classify/add";
	}

	/**
	 * 进入修改商品分类页面
	 *
	 * @param productClassifyDTO
	 * @return 
	 * 
	 * @author: 李超
	 * @date: 2017-05-16 19:02:08
	 */
	@RequestMapping(value = "/toUpdateClassify", method = RequestMethod.GET)
	public String toUpdateProductClassify(@ModelAttribute ProductClassifyDTO productClassifyDTO, Model model) {


		//根据当前类型获取所有上级类型(页面回显)
		productClassifyDTO.setStatus(ProductStatusEnum.ENABLED.getCode());//设置条件为已启用的
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
		//设置回显时选中
		for (int i = 0; i <parentList.size() ; i++) {
			for (int j = 0; j < childrenList.size(); j++) {
				if(childrenList.get(j).getId().equals(parentList.get(i).getId())){
					childrenList.get(j).setIsSelect(1);
					continue;
				}
			}
		}

		// 验证是否被商品所引用, 0沒有引用, 否则为具体被引用的商品数量
		int quote = iProductClassifyService.getExistProductCountByClassify(productClassifyDTO.getId());
		//model.addAttribute("parentList", parentList);
		model.addAttribute("childrenList", childrenList);
		model.addAttribute("quote", quote);
		model.addAttribute("productClassify", iProductClassifyService.getProductClassifyById(productClassifyDTO.getId()));
		return "product/classify/update";
	}

	/**
	 *
	 * @description: 修改商品分类
	 * @param productClassify
	 *            商品分类对象
	 * @return Result
	 * @author 李超
	 * @
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = "/updateClassify", method = RequestMethod.POST)
	@ResponseBody
	public Result updateProductProperty(@ModelAttribute ProductClassify productClassify)  {
		logger.info("修改商品分类开始，传入参数" + productClassify);
		// 后台数据验证开始
		Result result = new Result();
		productClassify.setUpdateTime(new Date());
		result = iProductClassifyService.updateProductClassify(productClassify);
		return result;
	}


	/**
	 *
	 * @description: 新增商品分类
	 * @param productClassify
	 *            商品分类对象
	 * @return Result
	 * @author 李超
	 * @
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = "/addClassify", method = RequestMethod.POST)
	@ResponseBody
	public Result addProductProperty(@ModelAttribute ProductClassify productClassify, HttpServletRequest request)  {
		logger.info("添加商品分类开始，传入参数" + productClassify);
		Result result = new Result();
		productClassify.setCreateTime(new Date());
		SessionUserVO user = getLoginUser();
		productClassify.setCreaterId(user.getId());
		productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());//默认设置未删除
		result = iProductClassifyService.addProductClassify(productClassify);
		return result;
	}


	/***
	 * 联动查询
	 *
	 * @param id
	 * @return
	 * @author: 杨颜光
	 *
	 */
	@RequestMapping(value = "/selectClass")
	@ResponseBody
	public Result selectChildClass(@RequestParam("id") Long id)  {
		Result result = new Result();
		ProductClassify productClassify = new ProductClassify();
		// 通过id查询直接下级分类级别数据
		productClassify.setParentId(id);
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		List<ProductClassify> list = this.iProductClassifyService.getProductClassifyList(productClassify);
		result.setData(list);
		return result;
	}

	/**
	 * 逻辑删除商品类型
	 *
	 * @param productClassifyDTO
	 * @return 
	 * 
	 * @author: 李超
	 * @date: 2017-05-17 19:07:35
	 */
	@RequestMapping(value = "/deleteProductClassify", method = RequestMethod.POST)
	public @ResponseBody Result deletePhProductClassify(ProductClassifyDTO productClassifyDTO)  {
		return iProductClassifyService.deleteProductClassifyById(productClassifyDTO.getId());
	}

	/**
	 * 停用商品类型
	 *
	 * @param productClassifyDTO
	 * @return Result
	 * 
	 * @author: 李超
	 * @date: 2017-05-17 19:12:41
	 */
	@RequestMapping(value = "/disableProductClassify", method = RequestMethod.POST)
	public @ResponseBody Result disablePhProductClassify(ProductClassifyDTO productClassifyDTO)  {
		return iProductClassifyService.updateDisableProductClassifyById(productClassifyDTO.getId());
	}
	
	/**
	 * 启用商品类型
	 *
	 * @param productClassifyDTO
	 * @return Result
	 * 
	 * @author: 李超
	 * @date: 2017-05-17 19:12:18
	 */
	@RequestMapping(value = "/enableProductClassify", method = RequestMethod.POST)
	public @ResponseBody Result enablePhProductClassify(ProductClassifyDTO productClassifyDTO)  {
		return iProductClassifyService.updateEnableProductClassifyById(productClassifyDTO.getId());
	}

//
//	/**
//	 * 查询商品类型分页列表
//	 *
//	 * @author 何文浪
//	 * @param productClassifyDto
//	 * @return Object
//	 * @
//	 */
//	@RequestMapping(value = "/getiPhProductClassifyByPage", method = RequestMethod.GET)
//	public @ResponseBody Object getiPhProductClassifyByPage(PageBean pageBean,
//			ProductClassifyDTO productClassifyDto)  {
//		productClassifyDto.setDeleteFlag(1);
//		return iProductClassifyService.getProductClassifyByPage(pageBean, productClassifyDto);
//	}
//
//	/**
//	 * 查询商品主从关系详情（不要调用分页插件）
//	 *
//	 * @author 何文浪
//	 * @param productClassifyDto
//	 * @return Result
//	 * @
//	 */
//	@RequestMapping(value = "/getProductClassifyDetail", method = RequestMethod.GET)
//	public @ResponseBody Result getProductClassifyDetail(ProductClassifyDTO productClassifyDto)  {
//		Result result = new Result();
//		result.setData(iProductClassifyService.getProductClassifyDetail(productClassifyDto));
//		return result;
//	}
//
//	/**
//	 * 商品分类单条查询
//	 *
//	 * @author 何文浪
//	 * @param productClassifyDto:id
//	 * @return Result
//	 * @
//	 */
//	@RequestMapping(value = "/getProductClassify", method = RequestMethod.GET)
//	public @ResponseBody Result getProductClassify(ProductClassifyDTO productClassifyDto)  {
//		Result result = new Result();
//		result.setData(iProductClassifyService.getProductClassifyDetail(productClassifyDto));
//		return result;
//	}
//
//	/**
//	 * 商品分类子级查询
//	 *
//	 * @author 何文浪
//	 * @param productClassifyDto
//	 * @return Result
//	 * @
//	 */
//	@RequestMapping(value = "/getProductClassifyAchildList", method = RequestMethod.GET)
//	public @ResponseBody Result getProductClassifyAchildList(ProductClassifyDTO productClassifyDto)
//			 {
//		Result result = new Result();
//		try {
//			List<ProductClassifyVO> list = iProductClassifyService
//					.getProductClassifyAchildList(productClassifyDto);
//			if (list.size() > 0) {
//				result.setCode("200");
//				result.setData(list);
//			} else {
//				result.setCode("300");
//				result.setData(list);
//				result.setMessage("查询失败");
//			}
//		} catch (Exception e) {
//			result.setCode("500");
//			result.setMessage("请联系管理员");
//		}
//
//		return result;
//	}
//

}
