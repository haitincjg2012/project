package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.PositionDTO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.IPositionService;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.spm.vo.PositionVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @项目 phshopping-api-platform
 * @描述   区域控制层
 * @author 何文浪
 * @时间 2017-5-24
 * @version 2.1
 */
@Controller
@RequestMapping("web/position")
public class PositionController  extends BaseController{
	//区域业务层
	@Reference(version = "1.0.0")
	private IPositionService iPositionService;
	
	//商户业务层
	@Reference(version = "1.0.0")
	private IMerchantService iMerchantService;
	//代理业务层
	@Reference(version = "1.0.0")
	private IAgentService iAgentService;
	//初始化供应商业务层
	@Reference(version = "1.0.0")
	private ISupplierService iSupplierService;
	/**
	 * 不带权限查询子级区域列表查询  provinceId传0查询省级，provinceId大于0时查询市级，依次类推
     * @param positionDTO
     * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-24
	 */
	@RequestMapping(value="/getPositionNoAuthorityList")
	@ResponseBody
	public List<PositionVO> getPositionNoAuthorityList(PositionDTO positionDTO){
		return iPositionService.getPositionChildList(positionDTO);
	}
	/**
	 * 带权限查询子级区域列表查询  provinceId传0查询省级，provinceId大于0时查询市级，依次类推
     * @param positionDTO
     * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-24
	 */
	@RequestMapping(value="/getPositionList")
	@ResponseBody
	public List<PositionVO> getPositionList(PositionDTO positionDTO){
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		if(userBean!=null){
            if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.ADMIN.getCode()) {//管理员

            } else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.CITY_AGENT.getCode()) {//市代理
                AgentDTO agentDTO = new AgentDTO();
                agentDTO.setUserId(userBean.getId());
                AgentVO agentVO = iAgentService.getAgentVOListById(agentDTO);
                if(agentVO != null){
                    positionDTO.setCityId(agentVO.getCityId());
                }
            } else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.COUNTY_AGENT.getCode()) {//县代理
                AgentDTO agentDTO = new AgentDTO();
                agentDTO.setUserId(userBean.getId());
                AgentVO agentVO = iAgentService.getAgentVOListById(agentDTO);
                if (agentVO != null) {
                    positionDTO.setCountyId(agentVO.getCountyId());
                }
            } else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.COMMUNITY_AGENT.getCode()) {//社区代理
                AgentDTO agentDTO = new AgentDTO();
                agentDTO.setUserId(userBean.getId());
                AgentVO agentVO = iAgentService.getAgentVOListById(agentDTO);
                if (agentVO != null) {
                    positionDTO.setTownId(agentVO.getTownId());
                }
            }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.SUPPLIER.getCode()){//供应商
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setUserId(userBean.getId());
                SupplierVO supplierVO = iSupplierService.getSupplierListById(supplierDTO);
                if(supplierVO != null){
                    //				positionDTO.setProvinceId(supplierVO.getProvinceId());
                    positionDTO.setCityId(supplierVO.getCityId());
                }
            }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.MERCHANT.getCode()){//商户
                MerchantDTO merchantDTO = new MerchantDTO();
                merchantDTO.setUserId(userBean.getId());
                MerchantVO mrchantVO = iMerchantService.getMerchantListById(merchantDTO);
                if(mrchantVO != null){
                    //				positionDTO.setProvinceId(mrchantVO.getProvinceId());
                    positionDTO.setCityId(mrchantVO.getCityId());
                }
            }
		}
		return iPositionService.getPositionChildList(positionDTO);
	}
}
