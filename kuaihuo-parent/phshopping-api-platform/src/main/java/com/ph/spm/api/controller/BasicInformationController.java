package com.ph.spm.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.facade.permission.constant.RoleIDEnum;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.ISupplierService;

/**
 * @author 王强
 * @ClassName: BasicInformationController
 * @Description: 基本信息Controller
 * @date 2017年6月14日 下午3:23:04
 */
@Controller
@RequestMapping("web/basicinformation")
public class BasicInformationController extends BaseController {

    /**
     * @Title: toBasicInformation
     * @Description: 根据后台角色不同跳转到不同的详情页面
     * @author 王强
     * @date 2017年6月14日 下午3:25:19
     */
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;

    @Reference(version = "1.0.0")
    private IAgentService iAgentService;

    @Reference(version = "1.0.0")
    private ISupplierService iSupplierService;

    @RequestMapping("tobasicinfo")
    public String toBasicInformation(Model model) {
        SessionUserVO userVO = getLoginUser();
        long roleId = userVO.getSessionRoleVo().get(0).getId();
        if (roleId == RoleIDEnum.SUPPLIER.getId()) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setUserId(userVO.getId());
            model.addAttribute("supplierVO", iSupplierService.getSupplierListDateilById(supplierDTO));
            return "account/supply_detail";
        } else if (roleId == RoleIDEnum.MERCHANT.getId()) {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setUserId(userVO.getId());
            model.addAttribute("merchantVO", iMerchantService.getMerchantDetailById(merchantDTO));
            return "account/merchant_detail";
        } else if (roleId == RoleIDEnum.CITY_AGENT.getId() || roleId == RoleIDEnum.COUNTY_AGENT.getId()
                || roleId == RoleIDEnum.COMMUNITY_AGENT.getId()) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setUserId(userVO.getId());
            model.addAttribute("agentVO", iAgentService.getAgentVODateilById(agentDTO));
            return "account/agent_detail";
        } else if (roleId == RoleIDEnum.SUPPLER.getId() || roleId == RoleIDEnum.SHEQUPF.getId() || roleId == RoleIDEnum.XIANJIPF.getId()) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setUserId(userVO.getId());
            model.addAttribute("agentVO", iAgentService.getAgentVODateilById(agentDTO));
            return "account/suppler_detail";
        } else {
            return "";
        }
    }
}
