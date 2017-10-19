package com.phshopping.pay.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.facade.pay.config.PayecoConfig;
import com.ph.shopping.facade.pay.dto.PayOrderDTO;
import com.ph.shopping.facade.pay.utils.payeco.PayecoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @项目：phshopping-parent
 * @描述：易联支付
 * @作者： Mr.chang
 * @创建时间：2017/5/27
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping(value = "pay/payeco")
public class PayecoController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICacheService cacheService;

    @RequestMapping(value="payecoPage",method = RequestMethod.POST)
    public ModelAndView toPayecoPayPage(HttpServletRequest request, PayOrderDTO pd, Model model){
        log.info("===========================进入易联支付跳转===============================");
        log.info("易联支付订单传入参数："+ JSON.toJSONString(pd));
        ModelAndView mv=new ModelAndView();
        try{
            String message=pd.validateForm();
            //校验参数
            if (Objects.nonNull(message)){
                model.addAttribute("msg",message);
                mv.setViewName("error/error");
                return mv;
            }else{
                PayOrderDTO pod=new PayOrderDTO();
                //从redis获取订单信息
                if(!cacheService.exists(pd.getOrderNum())){
                    model.addAttribute("msg","订单不存在");
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
                    mv.setViewName("error/error");
                    return mv;
                }
                //获得MD5加密验签
                String mac = PayecoUtils.getMac(pod.getAmount(), pod.getOrderNum());
                log.info("易联支付生成的订单mac值："+mac);
                //组装支付报文
                String requestText = PayecoUtils.encryptMiWen(mac,pod.getAmount(),request.getLocalAddr(),pod.getDescription(),pod.getOrderNum());
                log.info("报文发送内容:"+requestText);
                mv.addObject("requestText",requestText);
                mv.addObject("urlReaction", PayecoConfig.PAYECO_URL);
                mv.setViewName("pay/payeco/payeco");
            }
        }catch (Exception e){
            log.error("易联支付异常：" , e);
            mv.addObject("msg","易联支付异常");
            mv.setViewName("error/error");
        }
        log.info("===========================结束易联支付跳转===============================");
        return mv;
    }
}
