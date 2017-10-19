package com.alqsoft.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alqframework.pay.weixin.util.MD5Util;
import org.alqframework.result.Result;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alqsoft.utils.SignUtils;

public class SpringValidateSignatureInterceptor extends HandlerInterceptorAdapter{
	
	private static Log logger = LogFactory
			.getLog(SpringValidateSignatureInterceptor.class);
	
	private final static String[] publicurls={
			"/mobile/after/hunterservicerule/mobileUpload-uploadhunterruleattachment",//批发商完善信息 上传头像
			"/mobile/view/attachment/mobileUpload-salecircleattachment",
			"/mobile/after/updatelogo/update-member-logo",
			"/mobile/after/updatelogo/update-hunter-logo",
			"/mobile/after/hunterservicerule/update-servicerule",
			"/mobile/view/wxpay/payback",//微信回调地址
			"/mobile/after/producttrace/add", //订单跟踪 
			"/mobile/after/productmanager/productattachment", //商品轮播图添加
			"/mobile/view/register/checkphone", //注册检测用户  普惠调用
			"/mobile/view/login/checkMember", //登录检测用户 普惠调用
			"/mobile/after/productmanager/productdetailattachment", //商品详情图片添加
			"/mobile/view/alipay/payback",//支付宝回调
			"/mobile/view/phHunterRuleAttachment/mobileUpload-phHunterRuleAttachment",//图片上传
			"/mobile/view/appversion/downapp"//下载手机地址
	};
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Result result=new Result();
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
			result.setCode(201);
			result.setMsg("客户端为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else if(objTimestamp == null){
			PrintWriter out= response.getWriter();
			result.setCode(202);
			result.setMsg("时间戳为空");
			logger.info(JSON.toJSON(result).toString());
			out.print(JSON.toJSON(result).toString());
		}else{
			if(!(objType.toString().equals("I") || objType.toString().equals("A"))){
				PrintWriter out= response.getWriter();
				result.setCode(203);
				result.setMsg("客户端异常");
				logger.info(JSON.toJSON(result).toString());
				out.print(JSON.toJSON(result).toString());
			}else{
				Map<String, String[]> map = request.getParameterMap();
				String signData = SignUtils.mapToLinkString2(map);
				signData = StringEscapeUtils.unescapeXml(signData);
				String sign = "";
				byte[] b = null;  
			    try {  
			         b = (MD5Util.MD5Encode(signData,"utf-8")+"PHPF"+objType.toString()).getBytes("utf-8");  
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
						result.setCode(0);
						result.setMsg("验签通过");
						logger.info(signData+":签名=="+JSON.toJSON(result).toString());
						return true;
					}else{
						PrintWriter out= response.getWriter();
						result.setCode(204);
						result.setMsg("验签失败");
						logger.info(signData+":签名=="+JSON.toJSON(result).toString());
						out.print(JSON.toJSON(result).toString());
					}
				}else{
					PrintWriter out= response.getWriter();
					result.setCode(205);
					result.setMsg("签名为空");
					logger.info(signData+":签名=="+JSON.toJSON(result).toString());
					out.print(JSON.toJSON(result).toString());
				}
			}
		}
		return false;
	}
}
