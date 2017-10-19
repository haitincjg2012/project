package com.alqsoft.service.impl.salary;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.register.UserRegisterDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.init.InitParams;
import com.alqsoft.rpc.mobile.RpcUserRegisterService;
import com.alqsoft.service.impl.hunter.HunterServiceImpl;
import com.alqsoft.service.salary.SalaryService;
import com.alqsoft.utils.HttpClientObject;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年6月30日 下午3:02:48
 * 
 */
@Transactional(readOnly = true)
@Service
public class SalaryServiceImpl implements SalaryService {

	private static Logger logger = LoggerFactory.getLogger(SalaryServiceImpl.class);

	@Autowired
	private InitParams initParams;
	
	@Autowired
	private UserRegisterDao userRegisterDao;
	
	@Autowired
	private RpcUserRegisterService rpcUserRegisterService;

	@Override
	public String getSalaryUrl( String phone) {
		// 调用重庆接口，返回token数据==============================================================================
		String token = null;
		List<NameValuePair> registerUrl = new ArrayList<NameValuePair>();
		
		registerUrl.add(new BasicNameValuePair("phone", phone));

		String checkUrl = (String) initParams.getProperties().get("puhui_server_token");

		try {
			String results = HttpClientObject.sendPost(checkUrl, registerUrl);
			JSONObject jsonObject = JSONObject.parseObject(results);
			String names = jsonObject.getString("data");
			String code = jsonObject.getString("code");
			if (names != null && (code.trim()).equals("200")) {
				token = names;
			} else if ((code.trim()).equals("1005")) {//1005是用户已经存在
			   token = names;
			} 		
			
			logger.info("请求http返回的数据：" + names);
			
			Member list = userRegisterDao.findAllById(phone);
			list.setToken(token);
			Result updateMember = rpcUserRegisterService.updateMember(list,list.getId());//保存member数据
			
		} catch (Exception e) {
			logger.info("调用接口异常信息" + e);
			
		}
		return token;
	}

}
