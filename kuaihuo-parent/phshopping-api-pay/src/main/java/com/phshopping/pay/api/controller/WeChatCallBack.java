package com.phshopping.pay.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.weixin.XMLUtil;
import com.ph.shopping.facade.pay.config.WeChatConfig;
import com.ph.shopping.facade.pay.enums.AlipayTradeCodeEnum;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.exception.AlipayException;
import com.ph.shopping.facade.pay.exception.AlipayExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Project: phshopping-parent
 * @Desc: 微信支付回调
 * Created by wangxueyang on 2017/9/5 9:18.
 */
@Controller
@RequestMapping("weChat")
public class WeChatCallBack {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version="1.0.0")
    private IPayService payService;

    @RequestMapping(value="payback", method= RequestMethod.POST)
    public void wxPayBack(HttpServletRequest request, HttpServletResponse response){
        logger.info("=========================   开启微信支付回调  ================================");
        InputStream inputStream=null;
        BufferedReader in = null;
        boolean flag = false;
        boolean status = false;
        PrintWriter writer = null;
        HashMap<String,String> packageParams = new HashMap<String,String>();
        try{
            inputStream =request.getInputStream();//获取回调的参数
            if(inputStream==null||"".equals(inputStream)){
                logger.error("获取微信回调参数失败为空");
                return;
            }
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            logger.info("微信回调获取的原始参数，未处理过"+in);
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = in.readLine()) != null){
                sb.append(s);
            }
            //解析xml成map
            Map<String, String> m = new HashMap<String, String>();
            m = XMLUtil.doXMLParse(sb.toString());
            //获取回调参数的  订单号 金额
            String total_amount = m.get("total_fee");
            String out_trade_no = m.get("out_trade_no");
            //过滤空 设置 TreeMap
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);

                String v = "";
                if(null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            status = true;
            logger.info("**********************      进入回调本地业务处理      ******************************");
            writer = response.getWriter();
            String URL = WeChatConfig.PAY_CHECK_URL;
            //发往pay工程请求验签
            //所需参数
            String systemfrom = "";  // 6.支付来源
            String bizCode = OrderUtil.getOrderBizCode(out_trade_no);
            if(bizCode.equals("XXDD")){
                //快火会员支付
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }else if(bizCode.equals("XSDD")){
                //快火掌柜
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }else if(bizCode.equals("PFDD")){
                //快火批发
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }else if(bizCode.equals("CZDD")){
                //商户充值
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }else if(bizCode.equals("SMDD")){
                //扫码支付
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }else if(bizCode.equals("ZFDD")){
                //支付订单
                systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
            }
            packageParams.put("systemfrom", systemfrom);
            HttpResult httpResult = HttpClientUtils.sendPost(URL, packageParams);
            System.out.printf("发起参数校验：");
            int i = 1;
            for (Object obj : packageParams.keySet()) {
                System.out.println("第"+(i++)+"个参数"+obj+" : "+packageParams.get(obj));
            }
            String responseContent = httpResult.getResponseContent();
            if(responseContent.equals("SUCCESS")){
                logger.info("验签成功！！");
                flag = true;
            } else if(responseContent.equals("系统异常")){
                logger.info("系统异常！！");
                throw new Exception("系统异常！！");
            }else if(responseContent.equals("FAIL")){
                logger.info("验签失败！！");
                throw new Exception("回调校验失败！！");
            }else if (responseContent.equals("请求参数非法")){
                logger.info("请求参数非法！！");
                throw new Exception("请求参数非法！！");
            }else{
                logger.info("接收回调校验结果失败！！");
                throw new Exception("接收回调校验结果失败！！");
            }
            //获取验签结果更新本地业务
            if(flag){     //成功
                PayecoOrderVo po = payService.getOrderByOrderNo(out_trade_no);

                if (StringUtils.isEmpty(po)){
                    logger.error("回调订单不存在，订单号为："+out_trade_no);
                    throw new Exception("订单不存在");
                }else{
                    //验证回调金额是否为空
                    if (StringUtils.isEmpty(total_amount)){
                        logger.error("支付订单："+out_trade_no+"回调金额错误");
                        throw new AlipayException(AlipayExceptionEnum.ALIPAY_AMOUNT_ERROR);
                    }
                    BigDecimal returnMoney=new BigDecimal(total_amount);

                    BigDecimal scale=new BigDecimal(100);//微信支付回调金额单位为分total_amount
                    BigDecimal orderMoney=new BigDecimal(po.getScore()).divide(scale);

                    logger.info("回调金额最终结果：" + orderMoney);

                    //验证回调金额是否一致
                    if (returnMoney.compareTo(orderMoney)!=0){
                        logger.error("支付订单:"+out_trade_no+"回调金额不一致");
                        throw new Exception("微信回调金额不一致，回调失败！");
                    }else{
                        //更新订单支付状态
                        long money2 = po.getScore();//  金额
                        payService.updateTradOrder(out_trade_no, money2, PayTypeEnum.PAY_TYPE_WEIXINPAY.getPayType(), PayStatusEnum.PAY_SUCCESS.getCode(), AlipayTradeCodeEnum.TRADE_FINISHED.getCode());
                    }
                }
            }
        }catch (Exception e){
            if(status){
                writer.write(XMLUtil.setXML("SUCCESS", ""));
            }else{
                writer.write(XMLUtil.setXML("FAIL", ""));
            }
            logger.error("微信支付App回调更新业务异常：",e);
        }
        logger.info("============================微信支付回调完成=================================");
    }
}
