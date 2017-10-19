package com.phshopping.api.controller.personal.order;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;
import com.ph.shopping.facade.member.service.*;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ClassName:  MemberIntoMerchantTwoController
 * @Description:线上订单相关接口（二次开发）
 * @author: 李治桦
 * @date:   2017年09月26日
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/MemberIntoMerchantTwo")
public class MemberIntoMerchantTwoController {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**商户信息接口*/
    @Reference(version = "1.0.0")
    private IMemberIntoMerchantTwoService memberIntomerchantTwoService;
    /**积分接口*/
    @Reference(version = "1.0.0")
    private IScoreService scoreService;
    /**会员接口*/
    @Reference(version = "1.0.0")
    private IMemberService memberService;
    /**商户接口*/
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;
    /**
     *
     * @Title: getMerchantInfoTwo
     * @Description: 会员进入商户得到商户基本信息
     * @param: @return
     * @return: Result
     * @author：李治桦
     * @throws
     */
    @RequestMapping(value = "/getMerchantInfoTwo",method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberTradOrder(HttpServletRequest request,Long merchantId) {
        logger.info("查询商户基本信息参数：merchantId = {} ", JSON.toJSONString(merchantId));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        //获取
        result=memberIntomerchantTwoService.getMerchantInfoTwo(merchantId);
        logger.info("查询商户基本信息参数 result = {} ", JSON.toJSONString(result));
        return result;
    }
    /**
     *
     * @Title: getMerchantTimeTwo
     * @Description: 会员进入商户得到商户消费时段
     * @param: @return
     * @return: Result
     * @author：李治桦
     * @throws
     */
    @RequestMapping(value = "/getMerchantTimeTwo",method = RequestMethod.POST)
    @ResponseBody
    public Result getMerchantTimeTwo(HttpServletRequest request,Long merchantId) {
        logger.info("查询商户消费时段参数：merchantId = {} ", JSON.toJSONString(merchantId));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        //获取
        result=memberIntomerchantTwoService.getMerchantTimeTwo(merchantId);
        logger.info("查询商户消费时段参数 result = {} ", JSON.toJSONString(result));
        return result;
    }
    /**
     *
     * @Title: getDishAllTwo
     * @Description: 会员进入商户得到商户拥有的菜品信息
     * @param: @return
     * @return: Result
     * @author：李治桦
     * @throws
     */
    @RequestMapping(value = "/getDishAllTwo",method = RequestMethod.POST)
    @ResponseBody
    public Result getDishAllTwo(HttpServletRequest request,MerchantDishDTO dish) {
        logger.info("查询商户菜品信息：MerchantDishDTO = {} ", JSON.toJSONString(dish));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        if(dish.getHopeServiceDate()!=null&&!"".equals(dish.getHopeServiceDate())){
            //预计到达时间
            String[] hopeServiceDate=dish.getHopeServiceDate().split(" ");
            String[] time=hopeServiceDate[1].split("-");
            String beroreTime=hopeServiceDate[0]+" "+time[0];
            //将截取出来的时间放到实中
            dish.setHopeServiceDate(beroreTime);

        }
        //获取
        result=memberIntomerchantTwoService.getDishAllTwo(dish);
        logger.info("查询商户菜品信息 result = {} ", JSON.toJSONString(result));
        return result;
    }
    /**
     *注释
     * @Title: getOnlineOrderDish
     * @Description: 预定包间列表回显
     * @param: @return
     * @return: Result
     * @author：李治桦
     * @throws
     */
    @RequestMapping(value = "/getOnlineOrderDish",method = RequestMethod.POST)
    @ResponseBody
    public Result getOnlineOrderDish(HttpServletRequest request,MerchantDishDTO order) {
        logger.info("预定包间列表回显：MerchantDishDTO = {} ", JSON.toJSONString(order));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        //预定包间列表回显  图片和菜品名称
        result=memberIntomerchantTwoService.getOnlineOrderDish(order);
        logger.info("预定包间列表回显 result = {} ", JSON.toJSONString(result));
        return result;
    }
    /**
     * 包间菜品回显  购物车中的和订单中的
     */
    @RequestMapping(value = "/getOnlineOrderAndShopCartDish",method = RequestMethod.POST)
    @ResponseBody
    public Result getOnlineOrderAndShopCartDish(HttpServletRequest request,Long orderId) {
        logger.info("包间菜品回显 购物车中的和订单中的：orderId = {} ", JSON.toJSONString(orderId));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        //包间菜品回显  购物车中的和订单中的
        result=memberIntomerchantTwoService.getOnlineOrderAndShopCartDish(orderId);
        logger.info("包间菜品回显 购物车中的和订单中的 result = {} ", JSON.toJSONString(result));
        return result;
    }
    /**
     * 点击菜品显示菜品详情
     */
    @RequestMapping(value = "/getDishInfoByDishId",method = RequestMethod.POST)
    @ResponseBody
    public Result getDishInfoByDishId(HttpServletRequest request,Long dishId) {
        logger.info("点击菜品显示菜品详情：dishId = {} ", JSON.toJSONString(dishId));
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);

        result=memberIntomerchantTwoService.getDishInfoByDishId(dishId);

        logger.info("点击菜品显示菜品详情 result = {} ", JSON.toJSONString(result));
        return result;
    }
}
