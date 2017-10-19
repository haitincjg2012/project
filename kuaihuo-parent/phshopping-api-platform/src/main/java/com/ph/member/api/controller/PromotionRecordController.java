package com.ph.member.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.PromotionRecordDTO;
import com.ph.shopping.facade.member.service.IMemberPromotionService;

/**
 * 
 * phshopping-api-platform
 *
 * @description：推广企业controller
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
@Controller
@RequestMapping("web/promotionRecord")
public class PromotionRecordController {

	//日志
    private static final Logger LOG = LoggerFactory.getLogger(PromotionRecordController.class);

    //推广企业接口
    @Reference(version = "1.0.0")
    IMemberPromotionService memberAuthService;

    /**
     * @Title: listPage
     * @Description: 跳转推广企业列表页面
     * @author liuy
     * @date  2017年6月19日 上午10:14:15
     * @param model
     * @param promotionRecordDTO
    * @return 推广企业列表页面
     */
    @RequestMapping(value = {"/toPromotionRecordListPage", "/listPage"}, method = RequestMethod.GET)
    public String listPage(Model model, PromotionRecordDTO promotionRecordDTO, String type) {
        if (promotionRecordDTO!=null&&promotionRecordDTO.getMemberId()!=null) {
			model.addAttribute("memberId", promotionRecordDTO.getMemberId());
            model.addAttribute("modelType", type);
        }
        return "memberPromotionRecord/promotionRecordList";
    }

    /**
     * @Title: list
     * @Description: 分页查询推广企业
     * @author liuy
     * @date  2017年6月19日 上午10:14:46
     * @param promotionRecordDTO
     * @return
     */
    @RequestMapping(value = {"/list", "/optList"}, method = RequestMethod.GET)
    @ResponseBody
    public Object list(PromotionRecordDTO promotionRecordDTO){
        LOG.info("分页获取推广企业列表入参, promotionRecordDTO={}",JSON.toJSONString(promotionRecordDTO));
        Result result = memberAuthService.getAllPromRecordByPage(promotionRecordDTO);
        LOG.info("分页获取推广企业列表返回值，result={}",JSON.toJSONString(result));
        return result;
    }
}
