package com.alqsoft.controller.mobile.after.salary;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.init.InitParams;
import com.alqsoft.service.salary.SalaryService;
import com.alqsoft.utils.HttpClientObject;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年6月26日 下午4:16:13
 * 
 */
@RequestMapping("mobile/after/salary")
@Controller
public class SalaryController {

	@Autowired
	private SalaryService salaryService;
	@Autowired
	private InitParams initParams;

	@RequestMapping(value = "url")
	public void getSalary(HttpServletResponse writer, 
			               @MemberAnno Member member,
			               @RequestParam(value = "token") String token) {
		String phone = null;
		if (member != null && member.getPhone() != null) {
			phone = member.getPhone();
		}
		if ((token.equals("null") || token !=null) && phone !=null ) {
			token = salaryService.getSalaryUrl( phone);
		}

		try {
			StringBuffer sbHtml = new StringBuffer();
			
			String checkUrl = (String) initParams.getProperties().get("puhui_server_h5");

			sbHtml.append(HttpClientUtils.getHttpsToGet(checkUrl+"?token="+token));
			writer.getWriter().write(sbHtml.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
