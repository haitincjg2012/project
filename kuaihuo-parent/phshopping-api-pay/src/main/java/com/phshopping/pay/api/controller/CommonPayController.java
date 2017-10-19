package com.phshopping.pay.api.controller;

import java.math.BigDecimal;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.config.CommonPayConfig;
import com.ph.shopping.facade.pay.dto.PayOrderDTO;
import com.ph.shopping.facade.pay.exception.CommonPayExceptionEnum;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import com.ph.shopping.facade.pay.utils.commonpay.RSACommonUtilsTQ;

/**
 * @项目：phshopping-parent
 * @描述：调用北京通用支付接口
 * @作者： Mr.chang
 * @创建时间：2017/6/29
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping("pay/commonPay")
public class CommonPayController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICacheService cacheService; 

    /**
     * 跳转通用支付 PC
     * @param request
     * @param pd
     * @param model
     * @return
     */
    @RequestMapping(value="commonPayPage",method = RequestMethod.POST)
    public ModelAndView toCommonPayPage(HttpServletRequest request, PayOrderDTO pd, Model model){
        log.info("===========================进入北京通用支付跳转===============================");
        log.info("北京通用支付订单传入参数："+ JSON.toJSONString(pd));
        ModelAndView mv=new ModelAndView();
        try{
            String message=pd.validateForm();
            //校验参数
            if (Objects.nonNull(message)){
                model.addAttribute("msg",message);
                model.addAttribute("code", CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION.getCode());
                mv.setViewName("error/error");
                return mv;
            }else{
                PayOrderDTO pod=new PayOrderDTO();
                //从redis获取订单信息
                if(!cacheService.exists(pd.getOrderNum())){
                    model.addAttribute("msg","订单不存在");
                    model.addAttribute("code", CommonPayExceptionEnum.COMMON_PAY_ORDER_NOT_EXIST.getCode());
                    mv.setViewName("error/error");
                    return mv;
                }
                //获取redis订单号信息
                Object orderDTO = cacheService.get(pd.getOrderNum());
                JSONObject jsonObj = JSON.parseObject(orderDTO.toString());
                pod = JSON.toJavaObject(jsonObj,PayOrderDTO.class);

                //校验参数
                pod.setOrderNum(pd.getOrderNum());
                String orderMsg=pod.validateForm();
                if (Objects.nonNull(orderMsg)){
                    model.addAttribute("msg",orderMsg);
                    model.addAttribute("code", CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION.getCode());
                    mv.setViewName("error/error");
                    return mv;
                }
                BigDecimal orderMoney=new BigDecimal(pod.getAmount());
                BigDecimal scale=new BigDecimal(100);
                BigDecimal realOrderMoney=orderMoney.multiply(scale);
                //组装报文
                String value1= RSACommonUtilsTQ.encryptByPublicKey(realOrderMoney.toString(), RSACommonUtilsTQ.CharSet.UTF8);
                String value2=RSACommonUtilsTQ.encryptByPublicKey(CodeConstExt.YSBPay, RSACommonUtilsTQ.CharSet.UTF8);
                String value3=RSACommonUtilsTQ.encryptByPublicKey(pod.getOrderNum(), RSACommonUtilsTQ.CharSet.UTF8);
                //PC发起支付
                String value4 = RSACommonUtilsTQ.encryptByPublicKey(CodeConstExt.PAY_SYSTEM_FROM_PHSHOP,RSACommonUtilsTQ.CharSet.UTF8);
                mv.addObject("value1",value1);
                mv.addObject("value2",value2);
                mv.addObject("value3",value3);
                mv.addObject("value4",value4);
                mv.addObject("urlReaction", CommonPayConfig.PAY_URL);
                mv.setViewName("pay/commonpay/commonpay");
            }
        }catch (Exception e){
            log.error("北京通用支付异常：" , e);
            mv.addObject("msg","北京通用支付异常");
            mv.setViewName("error/error");
        }
        log.info("===========================结束北京通用支付跳转===============================");
        return mv;
    }
 
    /**
     * 跳转通用支付 APP
     * @param request
     * @param pd
     * @return result
     * @author 王雪洋
     */
    @RequestMapping(value="commonPay",method = RequestMethod.POST)
    @ResponseBody
    public Result toCommonPay(HttpServletRequest request, PayOrderDTO pd, Result result){
    	log.info("===========================进入北京通用支付跳转===============================");
        log.info("北京通用支付订单APP传入参数："+ JSON.toJSONString(pd));
        result = new Result();
        try{
            String message=pd.validateForm();
            //校验参数
            if (Objects.nonNull(message)){
            	result.setMessage(message);
            	result.setCode(CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION.getCode());
                return result;
            }else{
                PayOrderDTO pod=new PayOrderDTO();
                //从redis获取订单信息
                if(!cacheService.exists(pd.getOrderNum())){
                	result.setMessage("订单不存在");
                	result.setCode(CommonPayExceptionEnum.COMMON_PAY_ORDER_NOT_EXIST.getCode());
                    return result;
                }
                //获取redis订单号信息
                Object orderDTO = cacheService.get(pd.getOrderNum());
                JSONObject jsonObj = JSON.parseObject(orderDTO.toString());
                pod = JSON.toJavaObject(jsonObj,PayOrderDTO.class);

                //校验参数
                pod.setOrderNum(pd.getOrderNum());
                String orderMsg=pod.validateForm();
                if (Objects.nonNull(orderMsg)){
                	result.setMessage(orderMsg);
                	result.setCode(CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION.getCode());
                    return result;
                }
                BigDecimal orderMoney=new BigDecimal(pod.getAmount());
                BigDecimal scale=new BigDecimal(100);
                BigDecimal realOrderMoney=orderMoney.multiply(scale);
                //组装报文
                String value1= RSACommonUtilsTQ.encryptByPublicKey(realOrderMoney.toString(), RSACommonUtilsTQ.CharSet.UTF8);
                String value2=RSACommonUtilsTQ.encryptByPublicKey(CodeConstExt.YSBPay, RSACommonUtilsTQ.CharSet.UTF8);
                String value3=RSACommonUtilsTQ.encryptByPublicKey(pod.getOrderNum(), RSACommonUtilsTQ.CharSet.UTF8);
                //APP发起支付
                String value4 = RSACommonUtilsTQ.encryptByPublicKey(CodeConstExt.PAY_SYSTEM_FROM_PHSHOP_M,RSACommonUtilsTQ.CharSet.UTF8);
                //封装value数据
                String data = CommonPayConfig.PAY_URL+"?value1="+value1+"&value2="+value2+"&value3="+value3+"&value4="+value4;
                result.setData(data);
                result.setSuccess(true);
                result.setCode("200");
                result.setMessage("请求订单参数响应成功!");
                result.setCount(0);
            }
        }catch (Exception e){
            log.error("北京通用支付异常：" , e);
            result.setMessage("北京通用支付异常");
        }
        log.info("===========================结束北京通用支付跳转===============================");
        return result;
    }
}
