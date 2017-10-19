package com.ph.permission.api.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.system.dto.MerchantTypeDTO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.entity.MerchantType;
import com.ph.shopping.facade.system.service.IMerchantTypeService;
import com.ph.shopping.facade.system.service.ISystemLogService;
import com.ph.shopping.facade.system.vo.MerchantTypeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * @项目 phshopping-api-platform
 * @描述   商户类型控制层
 * @author 何文浪
 * @时间 2017-5-26
 * @version 2.1
 */
@Controller
@RequestMapping("web/merchantType")
public class MerchantTypeController extends BaseController {
    @Reference(version = "1.0.0")
	private IMerchantTypeService iMerchantTypeService;

    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;

    //系统日志添加
    @Reference(version = "1.0.0")
    private ISystemLogService iSystemLogService;

    /**

     /**
     * 树形列表
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-6-13
     */
    @RequestMapping(value="/getMerchantTypeByChildList")
    @ResponseBody
    public List<MerchantTypeVO> getMerchantTypeByChildList(@ModelAttribute MerchantTypeDTO merchantTypeDTO) {
        merchantTypeDTO.setIsDelete((byte) 0);
        return iMerchantTypeService.getMerchantTypeTreeTableList(merchantTypeDTO);
    }

    @RequestMapping(value="/getHunterTypeByChildList")
    @ResponseBody
    public List<MerchantTypeVO> getHunterTypeByChildList(@ModelAttribute MerchantTypeDTO merchantTypeDTO) {
        merchantTypeDTO.setIsDelete((byte) 0);
        return iMerchantTypeService.getHunterTypeTreeTableList(merchantTypeDTO);
    }

    /**
     * 树形列表全部
     *
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-6-13
     */
    @RequestMapping(value = "/getMerchantTypeByChildListAll")
    @ResponseBody
    public List<MerchantTypeVO> getMerchantTypeByChildListAll(@ModelAttribute MerchantTypeDTO merchantTypeDTO) {
        return iMerchantTypeService.getMerchantTypeTreeTableList(merchantTypeDTO);
    }

    /**
     * 新增或修改页面
     *
     * @return String
     * @author 熊克文
     */
    @RequestMapping(value = "/addOrUpdate")
    public String merchantTypeAddOrUpdate(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, MerchantTypeVO merchantTypeVO, Model model) {
        if (id != null && id > 0) {
            operateType = "update";
            MerchantTypeDTO merchantTypeDTO = new MerchantTypeDTO();
            merchantTypeDTO.setId(id);
            merchantTypeVO = VoDtoEntityUtil.convert(iMerchantTypeService.getMerchantTypeDetail(merchantTypeDTO).getData(), MerchantTypeVO.class);
        }
        model.addAttribute("merchantTypeVO", merchantTypeVO);
        model.addAttribute("operateType", operateType);
        return "permission/system/merchantTypeAddOrUpdate";
    }

    /**
     * 列表页面
     *
     * @return String
     * @author 熊克文
     */
    @RequestMapping(value = "/list")
    public String list() {
        return "permission/system/merchantTypeList";
    }

    /**
     * 修改方法
     *
     * @return String
     * @author 熊克文
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(@ModelAttribute MerchantTypeDTO merchantTypeDTO) {
        Result result = this.canOperate(merchantTypeDTO);
        if (!result.isSuccess()) {
            return result;
        }
        result = addSysLog(getLoginUser(), SystemOperateEnum.UPDATE.getType(), "商户类别修改");
        if (result.isSuccess()) {
            merchantTypeDTO.setUpdaterId(getLoginUser().getId());
            result = iMerchantTypeService.updateMerchantType(merchantTypeDTO);
        }

        return result;
    }


    /**
     * 增加方法
     *
     * @return String
     * @author 熊克文
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Result add(@ModelAttribute MerchantTypeDTO merchantTypeDTO) {
        merchantTypeDTO.setCreaterId(getLoginUser().getId());
        Result result = addSysLog(getLoginUser(), SystemOperateEnum.INSERT.getType(), "商户类别添加");
        if (result.isSuccess()) {
            merchantTypeDTO.setUpdaterId(getLoginUser().getId());
            result = iMerchantTypeService.addMerchantType(merchantTypeDTO);
        }
        return result;
    }

    /**
     * 启用/停用
     *
     * @author 熊克文
     */
    @RequestMapping(value = "/enabled")
    @ResponseBody
    public Result enabled(MerchantTypeDTO merchantTypeDTO) {
        Result result = this.canOperate(merchantTypeDTO);
        if (!result.isSuccess()) {
            return result;
        }
        MerchantType merchantType = iMerchantTypeService.getMerchantTypeDetail(merchantTypeDTO).getData(MerchantType.class);
        merchantTypeDTO.setIsDelete(merchantType.getIsDelete().equals((byte) 1) ? (byte) 0 : (byte) 1);
        String logMessage = merchantType.getIsDelete().equals((byte) 1) ? "启用" : "禁用";
        result = addSysLog(getLoginUser(), SystemOperateEnum.UPDATE.getType(), "商户类别" + logMessage);
        if (result.isSuccess()) {
            merchantTypeDTO.setUpdaterId(getLoginUser().getId());
            result = iMerchantTypeService.updateMerchantType(merchantTypeDTO);
        }
        return result;
    }

    /**
     * 删除
     *
     * @author 熊克文
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(MerchantTypeDTO dto) {
        Result result = this.canOperate(dto);
        if (!result.isSuccess()) {
            return result;
        }
        result = addSysLog(getLoginUser(), SystemOperateEnum.DELETE.getType(), "商户类别删除");
        if (result.isSuccess()) {
            result = iMerchantTypeService.delete(dto);
        }
        return result;
    }

    /**
     * 商户分类是否可以操作
     *
     * @param dto 分类dto
     * @return
     */
    private Result canOperate(MerchantTypeDTO dto) {
        MerchantTypeDTO count = new MerchantTypeDTO();
        count.setId(dto.getId());
        List<MerchantTypeVO> list = iMerchantTypeService.getMerchantTypeTreeTableList(count);
        if (CollectionUtils.isEmpty(list)) {
            return new Result(true);
        } else {
            if (list.get(0).getMerchantCount() > 0) {
                return new Result(false, "500", "该类别下还有商户,不能操作");
            } else {
                return new Result(true);
            }
        }
    }

    /**
     * 统一记录日志
     *
     * @param userBean
     * @param enumType
     * @param content
     * @return
     */
    public Result addSysLog(SessionUserVO userBean, byte enumType, String content) {
        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setCreaterId(userBean.getId());
        systemLogDTO.setOperateAccount(userBean.getTelphone());
        systemLogDTO.setCreaterName(userBean.getUserName());
        systemLogDTO.setOperateType(enumType);
        systemLogDTO.setOperateContent(content);
        return iSystemLogService.addSystemLog(systemLogDTO);
    }
}
