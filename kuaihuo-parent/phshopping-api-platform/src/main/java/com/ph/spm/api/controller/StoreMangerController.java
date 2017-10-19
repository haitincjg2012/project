package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.StoreDTO;
import com.ph.shopping.facade.member.dto.StoreManagerDTO;
import com.ph.shopping.facade.member.service.IStoreManagerService;
import com.ph.shopping.facade.member.vo.StoreManagerVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台店面经理展示和列表
 * Created by wudi on 2017/9/25.
 */
@Controller
@RequestMapping("web/storemanager")
public class StoreMangerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StoreMangerController.class);

    @Reference(version = "1.0.0")
    private IStoreManagerService iStoreManagerService;

    /**
     * 跳转页面路径
     *
     * @return
     */
    @RequestMapping(value = "/url")
    public String getStoreMangerUrl() {

        return "storeManager/storemanagerList";
    }

    /**
     * 店面代理获取数据
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getStoreManagerPage", method = RequestMethod.GET)
    public
    @ResponseBody
    Result getMerchantByPage(StoreManagerDTO dto,PageBean pageBean) throws Exception {
        if (dto.getPageNum() == null) {
            dto.setPageNum(PageConstant.pageNum);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(PageConstant.pageSize);
        }
        //获取当前登录用户的角色和手机号
        SessionUserVO userVO = getLoginUser();
        // mv.addObject("roleCode", userVO.getSessionRoleVo().get(0).getRoleCode());
        //  mv.addObject("telPhone", userVO.getTelphone());
        String phone = userVO.getTelphone();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        dto.setRoleCode(roleCode);
        dto.setRolePhone(phone);
        return iStoreManagerService.getMerchantByPage(dto,pageBean);
    }

    /**
     * 页面的增加和修改
     *
     * @param operateType
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/addOrUpdate")
    public String addOrUpdate(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, Model model) {
        StoreManagerVO vO = new StoreManagerVO();
        Byte loginRoleCode = (byte) -1;
        if (operateType.equalsIgnoreCase("update")) {
            StoreManagerDTO dto = new StoreManagerDTO();
            dto.setId(id);
            vO = iStoreManagerService.getStoreManagerVODateilById(dto);
        }
        if (!operateType.equalsIgnoreCase("applyAgent")) {
            loginRoleCode = getLoginUser().getSessionRoleVo().get(0).getRoleCode();
        }
        model.addAttribute("agentVO", vO);
        model.addAttribute("loginRoleCode", loginRoleCode);
        model.addAttribute("smsCodeTypeCode", SmsCodeType.APPLY_AGENT_VC.getCodeType());
        model.addAttribute("sourceCode", SmsSourceEnum.STORE.getCode());
        model.addAttribute("operateType", operateType);
       // return     "storeManager/detail";
          return     "storeManager/storemanger_detail";
       // return "/storeManager/storemanger_detail";
    }
    /**
     * 页面的增加和修改
     *
     * @param operateType
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail")
    public String addDetail(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, Model model) {
        StoreManagerVO vO = new StoreManagerVO();
        Byte loginRoleCode = (byte) -1;
        if (operateType.equalsIgnoreCase("update")) {
            StoreManagerDTO dto = new StoreManagerDTO();
            dto.setId(id);
            vO = iStoreManagerService.getStoreManagerVODateilById(dto);
        }
        if (!operateType.equalsIgnoreCase("applyAgent")) {
            loginRoleCode = getLoginUser().getSessionRoleVo().get(0).getRoleCode();
        }
        model.addAttribute("agentVO", vO);
        model.addAttribute("loginRoleCode", loginRoleCode);
        model.addAttribute("smsCodeTypeCode", SmsCodeType.APPLY_AGENT_VC.getCodeType());
        model.addAttribute("sourceCode", SmsSourceEnum.STORE.getCode());
        model.addAttribute("operateType", operateType);
        return     "storeManager/detail";
        // return "/storeManager/storemanger_detail";
    }
    /**
     * 代理商修改方法
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-5
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    Result update(StoreDTO agentDTO) {
        agentDTO.setUpdaterId(getLoginUser().getId());



        Result result = this.iStoreManagerService.updateAgentAndImg(agentDTO);

        return result;
    }

    /**
     * 修改用户审核状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateworkType")
    @ResponseBody
    public Result updateWorkType(@Param("id") Long id){
        Integer type=2;

        Result result = this.iStoreManagerService.updateWorkType(id,type);

        return result;
    }
}