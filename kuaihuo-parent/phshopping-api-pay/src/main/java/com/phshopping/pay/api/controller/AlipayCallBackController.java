package com.phshopping.pay.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.config.AlipayConfig;
import com.ph.shopping.facade.pay.enums.AlipayTradeCodeEnum;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.exception.AlipayException;
import com.ph.shopping.facade.pay.exception.AlipayExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @项目：phshopping-parent
 * @描述：支付宝回调controller
 * @作者： Mr.chang
 * @创建时间：2017/6/2
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping("alipay/callback")
public class AlipayCallBackController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference(version="1.0.0")
    private IPayService payService;

    /**
     * 支付宝app回调更新业务代码
     * @param request
     * @author wangxueyang
     */
    @RequestMapping(value = "/asyncCallback",method = RequestMethod.POST)
    public void alipayAppCallBack(HttpServletRequest request, HttpServletResponse response){
        log.info("============================开始支付宝回调=================================");
        String out_trade_no = "";
        String trade_no = "";
        String trade_status="";
        String total_amount ="";
        boolean status = false;
        Map<String,String> params = new HashMap<String,String>();
        PrintWriter pw = null;
        try{
            //获取支付宝回调的数据
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            //订单号
            out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            log.info("订单号 ： "+out_trade_no);
            //支付宝交易号
            trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            log.info("支付宝交易号： " + trade_no);
            //交易状态
            trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            log.info("交易状态：  " + trade_status );
            //订单总金额                                                   total_amount
            total_amount = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
            log.info("订单总金额：  " + total_amount);
            //匹配回调验签结果
            //boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            status = true;
            pw=response.getWriter();
            log.info("*****************    发起pay工程回调验签    ****************");
            //向pay工程发起验签请求路径
            String URL = AlipayConfig.PaySignCheckUrl;
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
            params.put("systemfrom", systemfrom);
            //接收结果
            log.info("++++++++++++++++++    校验开始     ++++++++++++++++");
            HttpResult data = HttpClientUtils.sendPost(URL, params);
            String responseContent = data.getResponseContent();
            log.info("pay工程响应的状态码： " + data.getStatusCode());
            log.info("**************  pay工程返回结果是：" + responseContent);
            boolean verify_result = false;
            if(responseContent.equals("SUCCESS")){
                log.info("回调校验成功！！");
                verify_result = true;
            } else if(responseContent.equals("系统异常")){
                log.info("系统异常！！");
                throw new Exception("系统异常！！");
            }else if(responseContent.equals("FAIL")){
                log.info("回调校验失败！！");
                throw new Exception("回调校验失败！！");
            }else if (responseContent.equals("请求参数非法")){
                log.info("请求参数非法！！");
                throw new Exception("请求参数非法！！");
            }else{
                log.info("接收回调校验结果失败！！");
                throw new Exception("接收回调校验结果失败！！");
            }


            //判断是否验签成功
            if(verify_result){
                log.info("支付宝支付回调结果："+trade_status);
                //查询回调处理的订单号
                PayecoOrderVo po=payService.getOrderByOrderNo(out_trade_no);
                if (StringUtils.isEmpty(po)){
                    log.error("回调订单不存在，订单号为："+out_trade_no);
                    throw new Exception("订单不存在");
                }else{
                    //验证回调金额是否为空
                    if (StringUtils.isEmpty(total_amount)){
                        log.error("支付订单："+out_trade_no+"回调金额错误");
                        throw new AlipayException(AlipayExceptionEnum.ALIPAY_AMOUNT_ERROR);
                    }
                    BigDecimal returnMoney=new BigDecimal(total_amount);

                    BigDecimal scale=new BigDecimal(10000);
                    BigDecimal orderMoney=new BigDecimal(po.getScore()).divide(scale);

                    log.info("回调金额最终结果：" + orderMoney);

                    //验证回调金额是否一致
                    if (returnMoney.compareTo(orderMoney)!=0){
                        log.error("支付订单:"+out_trade_no+"回调金额不一致");
                        throw new AlipayException(AlipayExceptionEnum.ALIPAY_AMOUNT_ERROR);
                    }
                    //更新订单支付状态
                    Result result=null;
                    if(trade_status.equals(AlipayTradeCodeEnum.TRADE_FINISHED.getCode())){
                        result = payService.checkAndUpdateOrder(out_trade_no, PayTypeEnum.PAY_TYPE_ALIPAY.getPayType(), PayStatusEnum.PAY_SUCCESS.getCode(),AlipayTradeCodeEnum.TRADE_FINISHED.getCode());
                    } else if (trade_status.equals(AlipayTradeCodeEnum.TRADE_SUCCESS.getCode())){
                        result = payService.checkAndUpdateOrder(out_trade_no, PayTypeEnum.PAY_TYPE_ALIPAY.getPayType(),PayStatusEnum.PAY_SUCCESS.getCode(),AlipayTradeCodeEnum.TRADE_SUCCESS.getCode());
//                        long money2 = orderMoney.longValue();  //  金额转换为long类型
//                       result = payService.updateTradOrder(out_trade_no, money2, PayTypeEnum.PAY_TYPE_ALIPAY.getPayType(), PayStatusEnum.PAY_SUCCESS.getCode(), AlipayTradeCodeEnum.TRADE_FINISHED.getCode());
                    }else if (trade_status.equals(AlipayTradeCodeEnum.TRADE_CLOSED.getCode())){
                        //此处处理超时未支付的订单 TODO

                    }
                }
            }
            pw.write("success");
            pw.flush();
            pw.close();
        }catch (Exception e){
            if (status){
                pw.write("success");
            }else{
                pw.write("fail");
            }
            pw.flush();
            pw.close();
            log.error("支付宝App回调更新业务异常：",e);
        }
        log.info("============================支付宝回调完成=================================");
    }

}