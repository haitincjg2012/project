package com.ph.supplyManagement.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.service.IProductClassifyService;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.product.status.ProductStatusEnum;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;

/**
 * @项目：phshopping-facade-
 *
 * 						@描述： 全国本地商品控制层(货源管理)
 *
 * 						@作者： 杨颜光
 *
 * @创建时间：2017年3月14日 下午1:14:28
 *
 * @Copyright by 杨颜光
 */

@Controller
@RequestMapping("/web/supply/manager")
public class SupplyManagementController extends BaseController {

	@Reference(version = "1.0.0")
	private IProductService iProductService;

	// 商户
	@Reference(version = "1.0.0")
	public IMerchantService iMerchantService;

	// 代理商
	@Reference(version = "1.0.0")
	public IAgentService iAgentService;

	@Reference(version = "1.0.0")
	private IProductClassifyService iProductClassifyService;

	@Reference(version = "1.0.0")
	private ISupplierService iSupplierService;

	/***
	 * 本地商品列表查询页面跳转
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @ @author 杨颜光
	 * @date 2017年4月24日 下午 4:52:30
	 */
	@RequestMapping(value = "/toLocalListPage")
	public String listPage(Model model, HttpServletRequest request) {
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		SupplierDTO supplierDTO = new SupplierDTO();
		if (role.getRoleCode() == RoleEnum.MERCHANT.getCode()) {// 为商户的时候要查询他上级代理商的本地商品
			// Merchant merchant = new Merchant();
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setUserId(info.getId());
			agentDTO.setAgentLevelId(1L);// TODO
			AgentVO result = iAgentService.getAgentByMerchant(agentDTO);
			Long agentId = null;
			logger.info("通过商户id查询市级代理商id返回值，result={}", JSON.toJSONString(result));
			if (result != null) {
				agentId = result.getUserId();
				model.addAttribute("createrId", agentId);
				//代理商的冻结状态
				model.addAttribute("isFrozenAgent", result.getIsFrozen());
				supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
				supplierDTO.setCreaterId(result.getUserId());
			}
			if (null == agentId) {
				model.addAttribute("createrId", "-1");
			}
		} else if (role.getRoleCode() == RoleEnum.ADMIN.getCode()) {
			model.addAttribute("createrId", "");
		} else {
			model.addAttribute("createrId", info.getId());
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setUserId(info.getId());
			AgentVO agent = iAgentService.getAgentVODateilById(agentDTO);
			model.addAttribute("isFrozenAgent", agent.getIsFrozen());
		}
		model.addAttribute("userRole", role.getRoleCode());

		// 查询供应商数据
		// 供应商
		
		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
		}
		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
			supplierDTO.setCreaterId(info.getId());
		}
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);
		model.addAttribute("spList", supplierList);

		// 查询一级分类
		ProductClassify productClassify = new ProductClassify();
		productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		List<ProductClassify> list = this.iProductClassifyService.getProductClassifyList(productClassify);
		model.addAttribute("list", list);

		return "supply/localList";
	}

	/**
	 * 本地商品列表列表查询
	 * 
	 * @author 杨颜光
	 * @param phProductDto
	 * @return Result
	 */
	@RequestMapping(value = "/getLocalPageList", method = RequestMethod.GET)
	public @ResponseBody Object getPageList(PageBean pageBean, ProductDTO phProductDto) {

		// 查询未删除商品
		phProductDto.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		// 查询已经审核的商品
		phProductDto.setAuditState(ProductStatusEnum.UNREVIEWED.getCode());
		// 查询本地商品
		phProductDto.setProductType(ProductStatusEnum.LOCAL.getCode());

		return iProductService.getProductByPage(pageBean, phProductDto);
	}

	/**
	 * 全国商品列表查询页面跳转
	 * 
	 * @author 杨颜光 @
	 */
	@RequestMapping(value = "/toCountryListPage")
	public String countryListPage(Model model, HttpServletRequest request) {
		SessionUserVO info = getLoginUser();
		SessionRoleVO role = info.getSessionRoleVo().get(0);
		Long agentId = null;
		if (role.getRoleCode() == RoleEnum.MERCHANT.getCode()) {// 为商户的时候要查询他上级代理商的本地商品
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setUserId(info.getId());
			agentDTO.setAgentLevelId(1L);// TODO
			AgentVO result = iAgentService.getAgentByMerchant(agentDTO);
			logger.info("通过商户id查询市级代理商id返回值，result={}", JSON.toJSONString(result));
			if (null != result) {
				agentId = result.getUserId();
				model.addAttribute("createrId", "");
				model.addAttribute("isFrozenAgent", result.getIsFrozen());
			}
			if (null == agentId) {
				model.addAttribute("createrId", "-1");
			}
		}else if(role.getRoleCode() == RoleEnum.CITY_AGENT.getCode()){
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setUserId(info.getId());
			AgentVO agent = iAgentService.getAgentVODateilById(agentDTO);
			model.addAttribute("isFrozenAgent", agent.getIsFrozen());
			model.addAttribute("createrId", "");
		}
		else {
			model.addAttribute("createrId", "");
		}
		model.addAttribute("userRole", role.getRoleCode());

		// 查询供应商数据
		// 供应商
		SupplierDTO supplierDTO = new SupplierDTO();
//		if (role.getRoleCode().equals(RoleEnum.ADMIN.getCode())) {
			supplierDTO.setSupplierType(SPMEnum.supplierType.NATIONAL.getIndex());
//		}
//		if (role.getRoleCode().equals(RoleEnum.CITY_AGENT.getCode())) {
//			supplierDTO.setSupplierType(SPMEnum.supplierType.LOCAL.getIndex());
//			supplierDTO.setCreaterId(info.getId());
//		}
		supplierDTO.setStatus(SPMEnum.supplierStatus.ONE.getIndex());
		supplierDTO.setIsDelete(SPMEnum.supplierIsDelete.ZERO.getIndex());
		List<SupplierVO> supplierList = iSupplierService.getSupplierList(supplierDTO);
		model.addAttribute("spList", supplierList);

		// 查询一级分类
		ProductClassify productClassify = new ProductClassify();
		productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		productClassify.setStatus(ProductStatusEnum.ENABLED.getCode());
		productClassify.setClassifyLevel(ProductStatusEnum.CLASSIFY_LEVEL_1.getCode());
		List<ProductClassify> list = this.iProductClassifyService.getProductClassifyList(productClassify);
		model.addAttribute("list", list);
		return "supply/countryList";
	}

	/**
	 * 全国商品列表列表查询
	 * 
	 * @author 杨颜光
	 * @param phProductDto
	 * @return Result
	 */
	@RequestMapping(value = "/getCountryPageList", method = RequestMethod.GET)
	public @ResponseBody Object getCountryPageList(PageBean pageBean, ProductDTO phProductDto) {
		// 查询未删除商品
		phProductDto.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		// 查询已经审核的商品
		phProductDto.setAuditState(ProductStatusEnum.UNREVIEWED.getCode());
		// 查询全国商品
		phProductDto.setProductType(ProductStatusEnum.NATIONWIDE.getCode());
		return iProductService.getProductByPage(pageBean, phProductDto);
	}

}
