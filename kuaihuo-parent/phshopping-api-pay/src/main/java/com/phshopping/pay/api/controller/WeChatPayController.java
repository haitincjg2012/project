package com.phshopping.pay.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.weixin.CommonUtil;
import com.ph.shopping.common.util.weixin.HttpUtil;
import com.ph.shopping.common.util.weixin.XMLUtil;
import com.ph.shopping.facade.pay.config.WeChatConfig;
import com.ph.shopping.facade.pay.exception.AlipayExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import com.ph.shopping.facade.pay.utils.union.HttpUtils;
import com.ph.shopping.facade.pay.vo.UnlineOrderVO;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project: phshopping-parent
 * @Desc: 快火微信支付 app
 * Created by wangxueyang on 2017/9/5 9:12.
 */
@Controller
@RequestMapping(value = "weChat", method = RequestMethod.POST)
public class WeChatPayController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "1.0.0")
    private IPayService payService;

    @RequestMapping(value = "kuaihuo/app/order/sign", method = RequestMethod.POST)
    @ResponseBody
    public Result orderPay(Long id , String ip){
        Result result = new Result();
        try {
            log.info("微信APP支付参数内容id= " + id+" ip: "+ip);
            // 校验参数
            if (id == null) {
                log.error("APP订单id为空");
                result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(),
                        AlipayExceptionEnum.PARAM_ERROR.getMsg());
                return result;
            }
            if (ip.equals("")|| ip == null){
                log.error("获取ip源异常!");
                result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "支付订单不存在!");
                return result;
            }
            // 校验支付订单
            UnlineOrderVO ov = payService.queryUnlineOrderVO(id);
            if (ov == null) {
                log.error("订单不存在!");
                result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "支付订单不存在!");
                return result;
            }
            if (ov.getOrderMoney() == null || ov.getOrderNo() == null) {
                log.error("获取订单数据异常!");
                result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "获取订单数据异常!");
                return result;
            }
            //  ************************* 请求微信统一下单所需参数 ***************************
            /*appid			应用号
            mch_id			商户号

            发往pay

            device_info     设备号   终端
            nonce_str		随机字符串
            sign			签名
            sign_type       签名类型
            body			商品描述
            detail          商品详情
            attach          附加数据
            out_trade_no	商户订单号
            8个
            fee_type       CNY   币种
            systemfrom    系统来源
            total_fee		总交易额
            spbill_create_ip 终端ip
            notify_url		回调地址
            trade_type		交易类型
            5个
            */
            String systemfrom = "";  // 6.支付来源
            String bizCode = OrderUtil.getOrderBizCode(ov.getOrderNo());
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
            } else{
                //支付订单来源异常
                log.error("支付订单来源异常!" );
                result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "获取订单数据异常!");
                return result;
            }
            // 处理订单金额
            //String total_fee = DoubleUtils.formatRound(ov.getOrderMoney() / 10000.00 , 2); // 2. 订单金额
            String total_fee = ov.getOrderMoney()/100+"";//单位是分(只能处理)
            Map<String, String> params = new HashMap<String,String>();
            String noncestr = CommonUtil.getRandomString(32);
            params.put("nonce_str",noncestr);  // 1. 随机字符串
            params.put("body","商品支付");  //  2.商品描述
            params.put("out_trade_no",ov.getOrderNo());  // 3.商户订单号
            params.put("total_fee",total_fee); //4.总交易额
            params.put("spbill_create_ip",ip); //5.终端ip
            params.put("notify_url",WeChatConfig.NOTIFY_URL);//6.回调地址
            params.put("trade_type","APP");//7.交易类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time_start = sdf.format(new Date());
            params.put("time_start",time_start);// 8.交易时间
            params.put("systemfrom", systemfrom); //9. 支付来源
            System.out.printf("发往pay工程 参数:");
            int i = 1;
            for (Object obj : params.keySet()) {
                System.out.println("第"+(i++)+"个参数"+obj+" : "+params.get(obj));
            }
            //发往pay工程请求加签   得到sign
            String URL= WeChatConfig.PAY_REQUEST_URL;
            HttpResult httpResult = HttpClientUtils.sendPost(URL, params);
            String responseContent = httpResult.getResponseContent();
            log.info("pay 工程响应的结果是："+responseContent);
            //校验结果
            Map map = JSON.parseObject(responseContent,Map.class);
            String  requestURL="";   //手机发起支付请求
            if(map.get("result").equals("请求参数非法") || map.get("result").equals("系统异常")){
                throw new Exception("获取签名请求参数异常");
            }else{
                requestURL = ""+map.get("result");
            }

            //发起统一下单
            params.put("appid",WeChatConfig.APPID);
            params.put("mch_id",WeChatConfig.MCH_ID);
            params.put("sign",requestURL);
            params.remove("systemfrom");
            for (Object obj : params.keySet()) {
                System.out.println("第"+(i++)+"个参数"+obj+" : "+params.get(obj));
            }
            //map 转换xml
            SortedMap<String,String> requestWX=new TreeMap<String,String>(params);
            String requestXml = CommonUtil.getRequestXml(requestWX);
            System.out.println("转换结果："+requestXml);
            String weChatURL = WeChatConfig.WeChat_ORDER_PAY;
            //String weChatResult = CommonUtil.httpsRequest(weChatURL, "POST", requestURL);
            String weChatResult = HttpUtil.postData(weChatURL, requestXml);
            System.out.println("统一下单返回结果："+weChatResult);
            Map<String, String> wMap = XMLUtil.doXMLParse(weChatResult);
            log.info("微信统一下单返回交易id："+wMap.get("prepay_id"));
            //微信请求二次加签
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("prepayid",wMap.get("prepay_id"));
            map2.put("package","Sign=WXPay");
            map2.put("noncestr",noncestr);
            String time = String.valueOf(System.currentTimeMillis() / 1000);
            map2.put("timestamp",time);//时间戳
            map2.put("systemfrom", systemfrom); //9. 支付来源
            map2.put("type","true");
            System.out.println("二次加签请求参数：");
            for (Object obj : map2.keySet()) {
                System.out.println("第"+(i++)+"个参数"+obj+" : "+map2.get(obj));
            }
            HttpResult httpResult2 = HttpClientUtils.sendPost(URL, map2);
            String responseContent2 = httpResult2.getResponseContent();
            log.info("pay 工程响应的结果是："+responseContent2);
            //校验结果
            Map map3 = JSON.parseObject(responseContent2,Map.class);
            System.out.println("pay工程二次加签的结果是:"+map3.get("result"));
            HashMap<String, String> appResult = new HashMap<>();
            appResult.put("partid",WeChatConfig.MCH_ID);
            appResult.put("appid",WeChatConfig.APPID);
            appResult.put("prepayid",wMap.get("prepay_id"));
            appResult.put("noncestr",noncestr);
            appResult.put("sign",map3.get("result")+"");
            appResult.put("timestamp",time);
//            result.setData(JSONObject.fromObject(appResult).toString());
            result.setData(appResult);
            result.setSuccess(true);
            result.setCode("200");
            result.setMessage("请求成功");
            result.setCount(0);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("微信APP签名数据异常：" + e);
            result.setMessage("微信支付异常");
        }
        return result;
    }
}
