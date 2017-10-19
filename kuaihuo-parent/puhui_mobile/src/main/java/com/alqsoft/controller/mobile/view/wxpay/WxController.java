package com.alqsoft.controller.mobile.view.wxpay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.entity.wxpayorder.WxPayOrder;
import com.alqsoft.init.InitParams;
import com.alqsoft.service.wxpay.WxPayService;
import com.alqsoft.utils.weixin.wxpay.PayCommonUtil1;
import com.alqsoft.utils.weixin.wxpay.XMLUtil;

@RestController
@RequestMapping("mobile/view/wxpay")
public class WxController {

	private static Logger logger = LoggerFactory.getLogger(WxController.class);
	@Autowired
	private InitParams initParams;
	@Autowired
	private WxPayService wxPayService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="payback", method=RequestMethod.POST)
	public void wxPayBack(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
				String key=initParams.getProperties().getProperty("WECHAT_KEY");//商户key
				InputStream inputStream=null;
				BufferedReader in = null;
				boolean flag = false;
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
				        //过滤空 设置 TreeMap  
				        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();      
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
				        //判断签名是否正确  
				        if(PayCommonUtil1.isTenpaySign("UTF-8", packageParams,key)) {  
					        String resXml ="";  
					        String result_code=(String)packageParams.get("result_code");//业务结果 SUCCESS/FAIL 
					        String return_msg=(String)packageParams.get("return_msg");//业务结果 SUCCESS/FAIL 
					        String err_code=(String)packageParams.get("err_code");		//错误代码
					        String err_code_des=(String)packageParams.get("err_code_des");//err_code_des
					        String out_trade_no=(String)packageParams.get("out_trade_no");//订单号
					        String transaction_id=(String)packageParams.get("transaction_id");//微信支付订单号
					        String time_end=(String)packageParams.get("time_end");//支付完成时间
					        String total_fee = packageParams.get("total_fee").toString();//支付金额
					        //查询订单是否处理过，防止重复处理
					    	Map<String, Object> params = new HashMap<String, Object>();
				        	params.put("out_trade_no", packageParams.get("out_trade_no"));
				        	List<WxPayOrder> list = this.wxPayService.findWxPayOrderByWxOrderNum(params);
				        	if (list.size() == 0) {
								logger.error("订单号：" + packageParams.get("out_trade_no") + "不存在,result_code:"
										+ result_code);
								flag = true;
							}
				        	
				        	Long money = 0L;
				        	for (WxPayOrder wxPayOrder : list) {
				        		int status = wxPayOrder.getStatus();
				        		if(status !=0){//查询微信支付表里的该订单支付业务结果,判断是否重复回调
				        			logger.info("订单号：" + out_trade_no
				        					+ "重复回调，该订单已处理");
				        			flag = true;
				        		}
				        		money += wxPayOrder.getMoney();
							}
				        	
				        	WxPayOrder wxPayOrder = new WxPayOrder();
				        	if(money.longValue() != Long.valueOf(total_fee).longValue()){
				        		logger.info("支付订单"+out_trade_no+"金额异常");
				        		wxPayOrder.setWxOrderNum(out_trade_no);
								wxPayOrder.setWxSerialNumber(transaction_id);
								wxPayOrder.setDescription("金额异常,需人工处理");
								wxPayOrder.setStatus(2);
								this.wxPayService.updateWxPayOrderType(wxPayOrder);
								flag = true;
				        	}
				        	
				        	BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
				        	if(flag){
				        		resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
				                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
				        		out.write(resXml.getBytes());  //给微信后台返回接收结果通知
					            out.flush();  
					            out.close();
					            return;
				        	}
				        	 
							
					        //回调业务处理
					        logger.info("result_code值为"+result_code);
					        if("SUCCESS".equals(result_code)){ //支付成功result_code为SUCCESS
				        
									wxPayOrder.setWxOrderNum(out_trade_no);
									wxPayOrder.setWxSerialNumber(transaction_id);
									wxPayOrder.setDescription("交易成功");
									wxPayOrder.setStatus(1);
									this.wxPayService.updateWxPayOrderType(wxPayOrder);
									logger.info("订单号：" + out_trade_no + "支付成功");
								
								//通知微信后台已经收到通知结果	
					        	resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
					                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
								
							}else{
								wxPayOrder.setWxOrderNum(out_trade_no);
								wxPayOrder.setWxSerialNumber(transaction_id);
								wxPayOrder.setDescription(return_msg);
								wxPayOrder.setStatus(2);
								this.wxPayService.updateWxPayOrderType(wxPayOrder);
				        		logger.info("支付失败,错误信息：" +"---err_code"+err_code +"--err_code_des"+err_code_des);  
				                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
				                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
				        	}
				            out.write(resXml.getBytes());  //给微信后台返回接收结果通知
				            out.flush();  
				            out.close();  
				        }else{
				        	 logger.error("微信支付回调验证签名结果[失败].");
				        }    
				}catch(Exception e){
					  logger.error("解析微信回调参数失败"+e.getMessage());
					  e.printStackTrace();

				}finally{
					try {
						if(in!=null){
							in.close();
						}
						if(inputStream!=null){
						 inputStream.close();  
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("解析微信回调参数失败"+e.getMessage());
						e.printStackTrace();
					}  
			       
				}
	}
}
