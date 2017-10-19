package com.alqsoft.controller.mobile.view.salary;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年6月26日 下午4:16:13
 * 
 */
@RequestMapping("mobile/view/salary")
@Controller
public class SalaryConytroller {
	
	@RequestMapping(value = "url")
	public void getSalary(HttpServletResponse writer,@RequestParam(value="token") String token){
		 
	
	     try {
	    	StringBuffer sbHtml = new StringBuffer();
	  	    // sbHtml.append(HttpClientUtils.getHttpsToGet("http://123.206.8.92:10086/web/member/score?token="+token));
	    	sbHtml.append(HttpClientUtils.getHttpsToGet("http://123.206.8.92:10086/web/member/score?token="+token));
	    	writer.getWriter().write(sbHtml.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
