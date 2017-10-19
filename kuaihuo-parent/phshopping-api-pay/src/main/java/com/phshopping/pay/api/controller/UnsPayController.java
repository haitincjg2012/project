package com.phshopping.pay.api.controller;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.facade.pay.dto.PayOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @项目：phshopping-facade-order
 * @描述：银生宝支付
 * @作者： 张霞
 * @创建时间： 16:41 2017/6/23
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping(value = "pay/unspay")
public class UnsPayController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ICacheService cacheService;
    public ModelAndView toUnspayPayPage(HttpServletRequest request, PayOrderDTO pd, Model model){
        logger.info( "***************进入银生宝支付跳转****************" );
        logger.info("银生宝支付订单传入参数："+ JSON.toJSONString(pd));
        ModelAndView mv=new ModelAndView();
        try {
            PayOrderDTO payOrderDTO = new PayOrderDTO();
            //从redis获取订单信息


        }catch (Exception e){
            logger.info( "银生宝支付 异常：{}",e );
            mv.addObject("msg","银生宝支付异常");
            mv.setViewName("error/error");
        }
        logger.info("===========================结束银生宝支付跳转===============================");
        return mv;
    }
}
