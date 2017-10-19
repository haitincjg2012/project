package com.phshopping.api.controller.merchant;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IMerchantCommentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0.0
 * @项目：phshopping-parent
 * @描述： 商户app接口进入
 * @作者： 熊克文
 * @创建时间： 2017/5/12
 * @Copyright by xkw
 */
@Controller
@RequestMapping("api/merchants")
public class MerchantController {

    /*商户接口*/
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;
   
    @Reference(version = "1.0.0")
    private IMerchantCommentService iMerchantCommentService;
    /**
     * 分页查询商户
     * @param merchantDTO 查询条件dto
     * @param pageSize 分页条数
     * @param pageNumber 当前页数
     * @return List<MerchantVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    @RequestMapping("/getMerchantByPage")
    @ResponseBody
    public Result getMerchantByPage(
            MerchantDTO merchantDTO,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber
    ) throws Exception {
        if (StringUtils.isEmpty(merchantDTO.getLatitude())) {
            return new Result(false, "500", "纬度不能为空");
        }
        if (StringUtils.isEmpty(merchantDTO.getLongitude())) {
            return new Result(false, "500", "经度不能为空");
        }
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNumber);
        pageBean.setPageSize(pageSize);
        merchantDTO.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
        merchantDTO.setIsFrozen(SPMEnum.merchantIsFrozen.ZERO.getIndex());
        return this.iMerchantService.getMerchantByPage(pageBean, merchantDTO);
    }

    /**
     * 查询商户详情列表
     * @param merchantDTO 查询条件dto
     * @return List<MerchantVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    @RequestMapping("/getMerchantList")
    @ResponseBody
    public Result getMerchantList(@ModelAttribute MerchantDTO merchantDTO) throws Exception {
        if (merchantDTO == null) {
            return new Result(false, "500", "参数不能为空");
        }
        System.out.println("latitude:"+merchantDTO.getLatitude());
        if (StringUtils.isEmpty(merchantDTO.getLatitude())) {
            return new Result(false, "500", "纬度不能为空");
        }
        if (StringUtils.isEmpty(merchantDTO.getLongitude())) {
            return new Result(false, "500", "经度不能为空");
        }
        merchantDTO.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
        merchantDTO.setIsFrozen(SPMEnum.merchantIsFrozen.ZERO.getIndex());
        return new Result(true, "查询商户列表成功", iMerchantService.getMerchantList(merchantDTO));
    }
    
    /**
     * 查询商户详情
     * @param id
     * @return List<MerchantVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    @RequestMapping(value = "/{id}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Result getMerchantListDetail(@PathVariable(name = "id") Long id,@PathVariable(name = "type") int type) throws Exception {
        MerchantDTO merchantTypeDTO = new MerchantDTO();
        merchantTypeDTO.setId(id);
        merchantTypeDTO.setType(type);
        merchantTypeDTO.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
        List<MerchantVO> merchantVOList = iMerchantService.getMerchantListDetail(merchantTypeDTO);
        if (CollectionUtils.isEmpty(merchantVOList)) {
            return new Result(false, "", "商户不存在");
        }
        return new Result(true, "查询商户详情成功", merchantVOList.get(0));
    }
    /**
     * 商户评论接口
     * @param id
     * @param type
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/merchantcomment/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Result hunterCommentList(
			@PathVariable(name = "id")  Long id,
			@RequestParam(value="type",defaultValue="") Integer type,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows){
		
		return iMerchantCommentService.findMerchantCommentList(id,type,page,rows,null);
	}
    
    
}
