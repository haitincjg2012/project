package com.phshopping.api.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ph.shopping.common.util.token.SignUtils;
import org.alqframework.pay.weixin.util.MD5Util;
import com.ph.shopping.common.util.result.Result;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

@Component
public class SpringValidateSignatureInterceptor extends HandlerInterceptorAdapter{

	private static Log logger = LogFactory
			.getLog(SpringValidateSignatureInterceptor.class);

	private final static String[] publicurls={
			"/api/merchant/image/upload",
			"/error"
	};

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
		/*Result result=new Result();
		Object objSign=request.getParameter("sign");
		Object objType=request.getParameter("client_type");
		Object objTimestamp=request.getParameter("timestamp");
		String requestURI = request.getRequestURI();//获取请求路径
		for (String url : publicurls) {
			if(url.equals(requestURI)){
				return true;
			}
		}
		if(objType == null){
			PrintWriter out= response.getWriter();
			result.setCode("201");
			result.setMessage("客户端为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else if(objTimestamp == null){
			PrintWriter out= response.getWriter();
			result.setCode("202");
			result.setMessage("时间戳为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else{
			if(!(objType.toString().equals("I") || objType.toString().equals("A"))){
				PrintWriter out= response.getWriter();
				result.setCode("203");
				result.setMessage("客户端异常");
				logger.info(JSON.toJSON(result).toString());
				out.print(JSON.toJSON(result).toString());
			}else{
				Map<String, String[]> map = request.getParameterMap();
				Map<String, String[]> map1 = new HashMap<String, String[]>();
				map1.putAll(map);
				String[] url = {requestURI};
				map1.put("url", url);
				String signData = SignUtils.mapToLinkString2(map1);
				signData = StringEscapeUtils.unescapeXml(signData);
				String sign = "";
				String type = "KHZG";
				String md5 = MD5Util.MD5Encode(signData,"utf-8")+type+objType.toString();
				byte[] b = null;
			    try {
			         b = (md5).getBytes("utf-8");
			    } catch (Exception e) {
			         e.printStackTrace();
			         logger.info(signData+":签名加密异常");
			    }
			    if (b != null) {
			        sign = Base64Utils.encodeToString(b);
			    }
			    System.out.println(sign+"****************************");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
				if(null!=objSign)
				{
					logger.info(objSign+"===================="+sign);
					String signApp = objSign.toString();
					if((signApp.trim()).equals(sign.trim())){
						result.setCode("0");
						result.setMessage("验签通过");
						logger.info(signData+":签名=="+JSON.toJSON(result).toString());
						return true;
					}else{
						PrintWriter out= response.getWriter();
						result.setCode("204");
						result.setMessage("验签失败");
						logger.info(signData+":签名=="+JSON.toJSON(result).toString());
						out.print(JSON.toJSON(result).toString());
					}
				}else{
					PrintWriter out= response.getWriter();
					result.setCode("205");
					result.setMessage("签名为空");
					logger.info(signData+":签名=="+JSON.toJSON(result).toString());
					out.print(JSON.toJSON(result).toString());
				}
			}
		}
		return false;*/
	}
}
