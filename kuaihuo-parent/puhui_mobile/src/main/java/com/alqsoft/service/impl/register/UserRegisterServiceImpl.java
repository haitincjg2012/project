package com.alqsoft.service.impl.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alqframework.pay.weixin.util.MD5Util;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.loader.custom.Return;
import org.javasimon.callback.timeline.StopwatchTimeRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextListener;

import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.register.UserRegisterDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;
import com.alqsoft.init.InitParam;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.init.InitParams;
import com.alqsoft.rpc.mobile.RpcUserRegisterService;
import com.alqsoft.rpc.mobile.RpcUserSendService;
import com.alqsoft.service.impl.hunter.HunterServiceImpl;
import com.alqsoft.service.impl.login.LoginServiceImpl;
import com.alqsoft.service.register.UserRegisterService;
import com.alqsoft.utils.APIHttpClient;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.utils.JedisUtils;
import com.alqsoft.utils.RSACommonUtils;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.utils.Tls_sigature;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午9:23:05 注册接口,如果为hunter登录的是批发商，如果为member登录的是会员
 */
@Transactional(readOnly = true)
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
	private static Logger logger = LoggerFactory.getLogger(HunterServiceImpl.class);
	@Autowired
	private UserRegisterDao userRegisterDao;
	@Autowired
	private RpcUserRegisterService rpcUserRegisterService;
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	@Autowired
	private RpcUserSendService rpcUserSendService;
	@Autowired
	private InitParams initParams;
	@Autowired
	private InitParamPc initParam;
	private Object object;

	@Override
	public Result userMemberRegister(HttpServletRequest request, HttpSession session) {
		// TODO Auto-generated method stub
		Result result = new Result();
		// 判断信息是否符合要求
		String phone = request.getParameter("phone").substring(0,11);
		// 获取密码
		String password = request.getParameter("password");
		// 用户输入的验证码
		String image = request.getParameter("imageCode");
		// 获取注册着的角色标识
		String role = request.getParameter("role");
		// 获取身份证号
		/*
		 * String card= request.getParameter("card"); //获取真实的姓名 String name=
		 * request.getParameter("name");
		 */

		if (role == null || "".equals(role)) {
			role = "member";
		}
		// 获取验证码的规范
		String codeType = "KHPF20170909";

		// 获取session ID
		String sessionId = session.getId();

		// 验证码的结果
		Result checkMsg = rpcUserSendService.checkMsg(phone, image, codeType);

		// 判断手机是否符合判断格式
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断用户是否存在
		Member list = userRegisterDao.findAllById(phone);

		// 判断注册的密码不能全是数字或者全是字符
		Pattern patternNum = Pattern.compile("[0-9]+");
		Pattern patterStr = Pattern.compile("[a-zA-Z]+");
		// 姓名身份证验证
		String two_element_url = (String) initParams.getProperties().get("two_element_url");// 获取姓名身份证验证地址

		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(image)) {
			return ResultUtils.returnError("验证码不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
			return ResultUtils.returnError("密码不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("不符合手机号格式");
		} else if (list != null) {
			return ResultUtils.returnError("用户已经存在，请直接登录");
		} // 判断不存在在此调用惠普接口，判断惠普是否已经存在，若存在返回已经存在，直接登录，没有存在，在本地在注册。
		else if (!(checkMsg.getCode() == 1)) {
			return ResultUtils.returnError("验证码错误");
		} else if (6 > password.length() || password.length() > 16) {
			return ResultUtils.returnError("请输入的密码长度不要低于6高于16");
		} else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {
			return ResultUtils.returnError("手机的密码不能纯数字或者纯字母");
		} else {

			// 获取ip
			String ip = loginServiceImpl.getClientIp(request);
			// 获取uuid
			String uuid = UniqueUtils.getUUID();
			uuid = uuid.replace("-", "");
			// 密码：md5(md5(密码)+uuid)
			String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
			// 获取浏览器
			String browser = getBrower(request);

			// 封装登录记录
			UserLog userLog = getUserLog(phone, ip, sessionId, "会员", browser, new Date());

			// 封装批发商
			Hunter hunter = getHunter(new Date(), ip, phone);

			Hunter hunter1 = null;
			// 如果是true封装批发商
			if (role.equals("hunter")) {
				Result save = rpcUserRegisterService.save(hunter);
				hunter1 = (Hunter) save.getContent();
			}
			try {
				Map map = chatRegist(phone);
				if (map == null) {
					return ResultUtils.returnError("聊天工具注册失败");
				}
				String identifilter = (String) map.get("IMdentifier");
				String imPassword = (String) map.get("IMPassword");
				String userSign = (String) map.get("IMUserSign");
				// name是注册用户的真实信姓名，card是用户的身份证件号
				String name = "123456";
				String card = "654321";
				Member members = getMembers(ip, uuid, passwordMD5, phone, new Date(), hunter1, identifilter, imPassword,
						userSign, name, card,null);
				Member memberSave = rpcUserRegisterService.save(members);
				/* rpcUserRegisterService.save(userLog); */

				map.put("logoAttachment", members.getLogoAttachment());
				map.put("nickName", members.getNickname());
				map.put("uuid", members.getUuid());
				map.put("phone", members.getPhone());
				map.put("mId", memberSave.getId());
				map.put("password", members.getPassword());
				map.put("IMUserSign", members.getImSign());
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
				result.setContent(map);

			} catch (Exception e) {
				// TODO: handle exception
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
			}

			return result;
		}

	}

	/**
	 * 普惠调用接口，检验手机号是否已经在批发商注册 phone 手机号
	 */
	@Override
	public Result checkPhoneToPH(String phone) {
		// TODO Auto-generated method stub
		if (phone == null || "".equals(phone)) {
			return ResultUtils.returnError("参数不能为空");
		}
		Member list = userRegisterDao.findAllById(phone);
		Map map = new HashedMap();
		if (list == null) {
			map.put("user", "false");
			Result result = new Result();
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
			result.setContent(map);
			return result;
		} else {
			map.put("user", "true");
			map.put("card", list.getCard());
			map.put("phone", list.getPhone());
			// 调用工具进行加密，私钥加密，公钥解密，或者是公钥加密，私钥解密
			/*
			 * map.put("name",RSACommonUtils.encryptByPrivateKey(list.getName(),
			 * RSACommonUtils.CharSet.UTF8)); map.put("card",
			 * RSACommonUtils.encryptByPrivateKey(list.getCard(),
			 * RSACommonUtils.CharSet.UTF8));
			 * map.put("phone",RSACommonUtils.encryptByPrivateKey(list.getPhone(
			 * ), RSACommonUtils.CharSet.UTF8));
			 * logger.info("私钥加密name:"+map.get("name")+"/n"+"私钥加密card："+map.get(
			 * "card")+"/n"+"私钥加密phone："+map.get("phone"));
			 * logger.info("公钥解密name："+RSACommonUtils.decryptByPublicKey(map.get
			 * ("name").toString(), RSACommonUtils.CharSet.UTF8));
			 * logger.info("公钥解密card："+RSACommonUtils.decryptByPublicKey(map.get
			 * ("card").toString(), RSACommonUtils.CharSet.UTF8));
			 */
			return ResultUtils.returnSuccess("用户存在", map);
		}

	}

	/***
	 * 关联普惠的注册
	 */
	public Result userMemberNewRegister(HttpServletRequest request, HttpSession session) {
		// TODO Auto-generated method stub
		Result result = new Result();
		// 判断信息是否符合要求
		String phone = request.getParameter("phone").substring(0,11);
		// 获取密码
		String password = request.getParameter("password");
		// 用户输入的验证码
		String image = request.getParameter("imageCode");
		// 获取注册着的角色标识
		String role = request.getParameter("role");
		// 获取身份证号
		String card = request.getParameter("card");
		// 获取真实的姓名
		String name = request.getParameter("name");

		if (role == null || "".equals(role)) {
			role = "member";
		}
		// 获取验证码的规范
		String codeType = "KHPF20170909";

		// 获取session ID
		String sessionId = session.getId();

		// 判断手机是否符合判断格式
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断用户是否存在
		Member list = userRegisterDao.findAllById(phone);
		// 若是不存在在判断普惠是是否存在和这个手机号
		if (list == null) {
			// 初始话参数的
			List<NameValuePair> paramUrl = new ArrayList<NameValuePair>();
			paramUrl.add(new BasicNameValuePair("phone", phone));
			// puhui_server_register
			String checkUrl = (String) initParams.getProperties().get("puhui_server_register");
			// checkUrl="http://119.29.178.31:7000/member/usersync/registerVerify";
			try {
				String results = HttpClientObject.sendPost(checkUrl, paramUrl);
				JSONObject jsonObject = JSONObject.parseObject(results);
				String names = jsonObject.getString("content");
				if ("true".equals(names)) {
					return ResultUtils.returnError("用户已经存在请直接登录");
				}
				logger.info("请求http返回的数据：" + names);
			} catch (Exception e) {
				logger.info("调用接口异常信息" + e);
				return ResultUtils.returnError("调用接口失败：" + e.getMessage());
			}

		}
		// 判断注册的密码不能全是数字或者全是字符
		Pattern patternNum = Pattern.compile("[0-9]+");
		Pattern patterStr = Pattern.compile("[a-zA-Z]+");
		// 姓名身份证验证
		String two_element_url = (String) initParams.getProperties().get("two_element_url");// 获取姓名身份证验证地址
		// 初始接口参数
		List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
		callpayParame.add(new BasicNameValuePair("name", name));
		callpayParame.add(new BasicNameValuePair("idCard", card));
		String checkResult = null;
		try {
			checkResult = HttpClientObject.sendGet(two_element_url + "/mobile/view/checkInfoCard/checkUserInfo",
					callpayParame);
			// 如果checkResult为true就是验证成功
			logger.info("checkResult:" + checkResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnError("姓名身份证验证接口异常,错误信息：" + e.getMessage());
		}

		// 判断用户是否存在
		if (StringUtils.isEmptyOrWhitespaceOnly(card)) {
			return ResultUtils.returnError("身份证的编号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(card)) {
			return ResultUtils.returnError("真实姓名不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(image)) {
			return ResultUtils.returnError("验证码不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
			return ResultUtils.returnError("密码不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("不符合手机号格式");
		} else if (list != null) {
			return ResultUtils.returnError("用户已经存在，请直接登录");
		} // 判断不存在在此调用惠普接口，判断惠普是否已经存在，若存在返回已经存在，直接登录，没有存在，在本地在注册。
		else if (!checkResult.equals("true")) {
			return ResultUtils.returnError("验证姓名身份证失败，请核对信息！");
		} else if (6 > password.length() || password.length() > 16) {
			return ResultUtils.returnError("请输入的密码长度不要低于6高于16");
		} else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {
			return ResultUtils.returnError("手机的密码不能纯数字或者纯字母");
		}
		// 验证码的结果，
		Result checkMsg = rpcUserSendService.checkMsg(phone, image, codeType);
		if (!(checkMsg.getCode() == 1)) {
			return ResultUtils.returnError(checkMsg.getMsg());
		}

		// 获取ip
		String ip = loginServiceImpl.getClientIp(request);
		// 获取uuid
		String uuid = UniqueUtils.getUUID();
		uuid = uuid.replace("-", "");
		// 密码：md5(md5(密码)+uuid)
		String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
		// 获取浏览器
		String browser = getBrower(request);

		// 封装登录记录
		UserLog userLog = getUserLog(phone, ip, sessionId, "会员", browser, new Date());

		// 封装批发商
		Hunter hunter = getHunter(new Date(), ip, phone);

		Hunter hunter1 = null;
		// 如果是true封装批发商
		if (role.equals("hunter")) {
			Result save = rpcUserRegisterService.save(hunter);
			hunter1 = (Hunter) save.getContent();
		}
		try {
			Map map = chatRegist(phone);
			if (map == null) {
				return ResultUtils.returnError("聊天工具注册失败");
			}
			String identifilter = (String) map.get("IMdentifier");
			String imPassword = (String) map.get("IMPassword");
			String userSign = (String) map.get("IMUserSign");
			// name是注册用户的真实信姓名，card是用户的身份证件号
			Member members = getMembers(ip, uuid, passwordMD5, phone, new Date(), hunter1, identifilter, imPassword,
					userSign, name, card,null);
			Member memberSave = rpcUserRegisterService.save(members);
			/* rpcUserRegisterService.save(userLog); */

			map.put("logoAttachment", members.getLogoAttachment());
			map.put("nickName", members.getNickname());
			map.put("uuid", members.getUuid());
			map.put("phone", members.getPhone());
			map.put("mId", memberSave.getId());
			map.put("password", members.getPassword());
			map.put("IMUserSign", members.getImSign());
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(map);

		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}

		return result;

	}

	/***
	 * 用户使用手机注册，在调用接口判断身份
	 */
	@Override
	public Result UserMemeberNewRegisterByPhone(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Result result = new Result();
		// 判断信息是否符合要求
		String phone = request.getParameter("phone").substring(0,11);
		// 获取密码
		String password = request.getParameter("password");
		// 用户输入的验证码
		String image = request.getParameter("imageCode");
		// 获取验证码的规范
		String codeType = "KHPF20170909";
		// 判断手机是否符合判断格式
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断用户是否存在
		Member list = userRegisterDao.findAllById(phone);
		// 若是不存在在判断普惠是是否存在和这个手机号
		/*if (list == null) {
			// 初始话参数的
			List<NameValuePair> paramUrl = new ArrayList<NameValuePair>();
			paramUrl.add(new BasicNameValuePair("phone", phone));
			// 通过配置文件获取普惠的配置文件
			String checkUrl = (String) initParams.getProperties().get("puhui_server_register");
			// checkUrl="http://119.29.178.31:7000/member/usersync/registerVerify";
			try {
				String results = HttpClientObject.sendPost(checkUrl, paramUrl);
				JSONObject jsonObject = JSONObject.parseObject(results);
				String names = jsonObject.getString("content");
				if ("true".equals(names)) {
					return ResultUtils.returnError("用户已经存在请直接登录");
				}
			
				logger.info("请求http返回的数据：" + names);
			} catch (Exception e) {
				logger.info("调用接口异常信息" + e);
				return ResultUtils.returnError("调用接口失败：" + e.getMessage());
			}

		}*/
		// 判断注册的密码不能全是数字或者全是字符

		Pattern patternNum = Pattern.compile("[0-9]+"); // 判断是否为纯属数字

		Pattern patterStr = Pattern.compile("[a-zA-Z]+");// 判断是否为纯字母
		// 判断用户是否存在
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {

			return ResultUtils.returnError("手机号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(image)) {

			return ResultUtils.returnError("验证码不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {

			return ResultUtils.returnError("密码不能为空");
		} else if (!m.matches()) {

			return ResultUtils.returnError("不符合手机号格式");
		} else if (list != null) {

			return ResultUtils.returnError("用户已经存在，请直接登录");
		} else if (6 > password.length() || password.length() > 16) {

			return ResultUtils.returnError("请输入的密码长度不要低于6高于16");
		} else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {

			return ResultUtils.returnError("手机的密码不能纯数字或者纯字母");
		}
		// 验证码的结果，
		Result checkMsg = rpcUserSendService.checkMsg(phone, image, codeType);
		if (!(checkMsg.getCode() == 1)) {
			return ResultUtils.returnError(checkMsg.getMsg());
		}
		/*//存入到缓存中
		JedisUtils jedisUtils = JedisUtils.getRu(initParam);
		jedisUtils.del(phone.trim());
		jedisUtils.del(password.trim());
		
		jedisUtils.set(phone.trim(), phone);
		jedisUtils.set(password.trim(), password);
		System.out.println("+++++++++++++++++++"+jedisUtils.get("17600685458"));*/
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent("注册成功");

		return result;
	}

	/***
	 * 验证身份正确后，保存数据到数据库
	 */
	@Override
	public Result SaveMemeberByCheckCard(HttpServletRequest request, HttpSession session) {
		Result result = new Result();
		// 判断信息是否符合要求
		String phone = request.getParameter("phone");
		// 获取密码
		String password = request.getParameter("password");
		// 获取身份证号
		String card = request.getParameter("card");
		// 获取真实的姓名
		String name = request.getParameter("name");

		// 获取session ID
		String sessionId = session.getId();

		// 判断手机是否符合判断格式
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断用户是否存在
		Member list = userRegisterDao.findAllById(phone);
		// 若是不存在在判断普惠是是否存在和这个手机号
		if (list == null) {
			// 初始话参数的
			List<NameValuePair> paramUrl = new ArrayList<NameValuePair>();
			paramUrl.add(new BasicNameValuePair("phone", phone));
			// puhui_server_register
			String checkUrl = (String) initParams.getProperties().get("puhui_server_register");

			try {
				String results = HttpClientObject.sendPost(checkUrl, paramUrl);
				JSONObject jsonObject = JSONObject.parseObject(results);
				String names = jsonObject.getString("content");
				if ("true".equals(names)) {
					return ResultUtils.returnError("用户已经存在请直接登录");
				}
				logger.info("请求http返回的数据：" + names);
			} catch (Exception e) {
				logger.info("调用接口异常信息" + e);
				return ResultUtils.returnError("调用接口失败：" + e.getMessage());
			}

		}
		// 判断注册的密码不能全是数字或者全是字符
		Pattern patternNum = Pattern.compile("[0-9]+");
		Pattern patterStr = Pattern.compile("[a-zA-Z]+");
		// 姓名身份证验证
		String two_element_url = (String) initParams.getProperties().get("two_element_url");// 获取姓名身份证验证地址
		// 初始接口参数
		List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
		callpayParame.add(new BasicNameValuePair("name", name));
		callpayParame.add(new BasicNameValuePair("idCard", card));
		String checkResult = null;
		try {
			checkResult = HttpClientObject.sendGet(two_element_url + "/mobile/view/checkInfoCard/checkUserInfo",
					callpayParame);
			// 如果checkResult为true就是验证成功
			logger.info("checkResult:" + checkResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnError("姓名身份证验证接口异常,错误信息：" + e.getMessage());
		}

		// 判断用户是否存在
		if (StringUtils.isEmptyOrWhitespaceOnly(card)) {
			return ResultUtils.returnError("身份证的编号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(card)) {
			return ResultUtils.returnError("真实姓名不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
			return ResultUtils.returnError("密码不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("不符合手机号格式");
		} else if (list != null) {
			return ResultUtils.returnError("用户已经存在，请直接登录");
		} // 判断不存在在此调用惠普接口，判断惠普是否已经存在，若存在返回已经存在，直接登录，没有存在，在本地在注册。
		else if (!checkResult.equals("true")) {
			return ResultUtils.returnError("身份证号码和姓名不匹配，请重新输入");
		} else if (6 > password.length() || password.length() > 16) {
			return ResultUtils.returnError("请输入的密码长度不要低于6高于16");
		} else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {
			return ResultUtils.returnError("手机的密码不能纯数字或者纯字母");
		}
		// 获取ip
		String ip = loginServiceImpl.getClientIp(request);
		// 获取uuid
		String uuid = UniqueUtils.getUUID();
		uuid = uuid.replace("-", "");
		// 密码：md5(md5(密码)+uuid)
		String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
		// 获取浏览器
		String browser = getBrower(request);

		// 封装登录记录
		UserLog userLog = getUserLog(phone, ip, sessionId, "会员", browser, new Date());

		// 封装批发商
		Hunter hunter = getHunter(new Date(), ip, phone);

		Hunter hunter1 = null;

		try {
			Map map = chatRegist(phone);
			if (map == null) {
				return ResultUtils.returnError("聊天工具注册失败");
			}
			String identifilter = (String) map.get("IMdentifier");
			String imPassword = (String) map.get("IMPassword");
			String userSign = (String) map.get("IMUserSign");
			// name是注册用户的真实信姓名，card是用户的身份证件号
			Member members = getMembers(ip, uuid, passwordMD5, phone, new Date(), hunter1, identifilter, imPassword,
					userSign, name, card,null);
			Member memberSave = rpcUserRegisterService.save(members);
			/* rpcUserRegisterService.save(userLog); */

			map.put("logoAttachment", members.getLogoAttachment());
			map.put("nickName", members.getNickname());
			map.put("uuid", members.getUuid());
			map.put("phone", members.getPhone());
			map.put("mId", memberSave.getId());
			map.put("password", members.getPassword());
			map.put("IMUserSign", members.getImSign());
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(map);

		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}

		return result;
	}

	/***
	 * 封装member数据
	 * 
	 * @param ip
	 * @param uuid
	 * @param passwordMD5
	 * @param phone
	 * @param date
	 * @return member
	 */
	public Member getMembers(String ip, String uuid, String passwordMD5, String phone, Date date, Hunter hunter1,
			String identifier, String password, String userSign, String name, String card,String token) {
		Member member = new Member();
		member.setCreatedIp(ip);
		member.setUuid(uuid);
		member.setPassword(passwordMD5);
		member.setPhone(phone);
		member.setCreatedTime(date);
		member.setImId(identifier);
		member.setImPassword(password);
		member.setImSign(userSign);
		/*
		 * member.setName(name); member.setCard(card);
		 */
		if (hunter1 != null) {
			member.setHunter(hunter1);
		}
		if (token !=null) {
			member.setToken(token);
		}
		return member;
	}

	/***
	 * 获取客户端的真实ip
	 * 
	 * @param request
	 * @return clientIp 从request域中取出进行判断(代理上网)XFF，XRI，Addr, 如果 forwarded_for 设成了
	 *         off,则：X-Forwarded-For: unknown
	 */
	public String getClientIp(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || "unknown".equalsIgnoreCase("ip") || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/***
	 * 获取浏览器
	 * 
	 * @return brower
	 */
	public String getBrower(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		String userbrowser = "未知";
		if (null != agent) {
			if (agent.contains("Chrome")) {
				userbrowser = "Chrome";
			} else if (agent.contains("Firefox")) {
				userbrowser = "Firefox";
			} else if (agent.contains("Safari")) {
				userbrowser = "Safari";
			} else if (agent.contains("Trident")) {
				userbrowser = "Trident";
			} else if (agent.contains("IE")) {
				userbrowser = "IE";
			} else if (agent.contains("360")) {
				userbrowser = "360";
			}
		}
		return userbrowser;
	}

	/**
	 * 获取登录者信息
	 */
	private UserLog getUserLog(String loginName, String loginIp, String sessionId, String roleName, String browser,
			Date loginTime) {

		UserLog userLog = new UserLog();// 用户登录记录

		userLog.setLoginName(loginName);// 用户名

		userLog.setRoleName(roleName);// 角色名称

		userLog.setCreatedIp(loginIp);// 登录时的ip

		userLog.setSessionId(sessionId);// 用户的sessionId

		userLog.setBrowser(browser);// 浏览器

		userLog.setCreatedTime(loginTime);
		// 创建的时间

		return userLog;
	}

	/***
	 * 封装hunter
	 * 
	 * @return hunter
	 */
	private Hunter getHunter(Date createdTime, String createdIp, String phone) {

		Hunter hunter = new Hunter();

		hunter.setCreatedTime(createdTime);

		hunter.setCreatedIp(createdIp);

		// hunter.setPhone(phone);

		return hunter;
	}

	/***
	 * 聊天的注册
	 */
	public Map chatRegist(String phone) {
	
		Map map = new HashedMap();
		
		Map txMap =this.txRegist(phone);
		Integer errotCode=Integer.valueOf(txMap.get("errotCode").toString());
		String actionStatus=txMap.get("actionStatus").toString();
		String identifier=txMap.get("Identifier").toString();
		String password=txMap.get("Password").toString();
		                   
		if (errotCode == 0 && actionStatus.equals("OK")) {
			Tls_sigature tls_sigature = new Tls_sigature();
			String userSign = tls_sigature.getUserSign(identifier);
			map.put("IMdentifier", identifier);
			map.put("IMPassword", password);
			map.put("IMUserSign", userSign.trim());
			return map;
		} else if (errotCode == 70421 && actionStatus.equals("FAIL")) {
			return map;
		} else if (errotCode == 70402 && actionStatus.equals("FAIL")) {
			Map txMap2 =this.txRegist(phone+password);
			Integer errotCode2=Integer.valueOf(txMap2.get("errotCode").toString());
			String actionStatus2=txMap2.get("actionStatus").toString();
			String identifier2=txMap2.get("Identifier").toString();
			String password2=txMap2.get("Password").toString();
			                   
			if (errotCode2== 0 && actionStatus2.equals("OK")) {
				Tls_sigature tls_sigature2 = new Tls_sigature();
				String userSign2 = tls_sigature2.getUserSign(identifier2);
				map.put("IMdentifier", identifier2);
				map.put("IMPassword", password2);
				map.put("IMUserSign", userSign2.trim());
				return map;
			} else if (errotCode2 == 70421 && actionStatus2.equals("FAIL")) {
				return map;
			}
		}
		
		return map;

	}

	public Map txRegist(String phone) {
		String ident = null;
		
		ident = phone+"l";

		String url = (String) initParams.getProperties().get("PH_IM_URL");
		// 签字
		String usersig = (String) initParams.getProperties().get("PH_IM_USERSIG");
		// 注册点
		String spn = (String) initParams.getProperties().get("PH_IM_SPN");
		;
		// 管理员账号
		String identifier = (String) initParams.getProperties().get("PH_IM_IDENTIFIER");
		// appId
		String sdkappid = (String) initParams.getProperties().get("PH_IM_SDKAPPID");
		// 返回的格式
		String contenttype = (String) initParams.getProperties().get("PH_IM_CONTENTTYPE");
		Random randoms = new Random();
		// 获取随机纯数字32位
		String random = UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[a-zA-Z]",
				randoms.nextInt(10) + "");
		String uri = url + "?usersig=" + usersig + "&sdkappid=" + sdkappid + "&sdkappid=" + sdkappid + "&random="
				+ random + "&identifier=" + identifier + "&contenttype=" + contenttype;
		// String
		// uri="https://console.tim.qq.com/v4/registration_service/register_account_v1?usersig=eJxFkF1rwjAUhv9Lr8c8SZt*DHYhTqS0WrvpKN6E0MRy7BJLGzfH2H9fDZVx7p6Hl-Oe8*Pt8rdH0XUoubDc76X35IH34LC6dtgrLo5W9SMmjDEKcLefqh-wbEZBgTBCfYB-iVIZi0d0QSE1mkkM2Ixkvdwv0nKxpO91WjS0Pl3wlX34DeouzizZERXQEAC-DD3MmkLqMm2KTTYPk2q9aucDrLabKwYHW0GUJ1VS7l8y7OOtCiKT6-b5vky23N12ax*M7Wg4ziQtauV4nBBCIIgmLur6fDGW2*9OuWf8-gHz71Y1&apn=1&identifier=admin&sdkappid=1400026262&contenttype=json";
		// 注册密码
		String registerPass = random.substring(0, 10);
		// name.add(new BasicNameValuePair("",id));
		APIHttpClient ac = new APIHttpClient(uri);
		JSONObject j = new JSONObject();

		j.put("Identifier", ident.trim());
		j.put("IdentifierType", 3);
		j.put("Password", registerPass.trim());
		String cc = ac.contentTypeJsonPost(j.toString());
		logger.info("IM注册:"+cc);
		JSONObject code = JSONObject.parseObject(cc);
		// erroCode为0注册成功，errocode为70402用户已经注册成功，errocode时70421参数的格式不对
		Integer errotCode = (Integer) code.get("ErrorCode");
		// ActionStatus为FAIL注册失败，ActionStatus为OK时注册成功
		String actionStatus = (String) code.get("ActionStatus");
		Map map = new HashedMap();
		map.put("errotCode", errotCode);
		map.put("actionStatus", actionStatus);
		map.put("Identifier", ident.trim());
		map.put("Password", registerPass.trim());
		
		return map;

	}
	

	@Override
	public Result UserMemberRegisterByPhone(HttpServletRequest request, HttpSession session) {
		// TODO Auto-generated method stub
		String token=null;
		Result result = new Result();
		// 判断信息是否符合要求
		String phone = request.getParameter("phone").substring(0,11);
		// 获取密码
		String password = request.getParameter("password");
		// 用户输入的验证码
		String image = request.getParameter("imageCode");
		// 获取session ID
		String sessionId = session.getId();
		// 验证码格式
		String codeType = "KHPF20170909";

		// 判断手机是否符合判断格式
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		// 判断用户是否存在
		Member list = userRegisterDao.findAllById(phone);
		// 若是不存在在判断普惠是是否存在和这个手机号
		if (list == null) {
			// 初始话参数的
			List<NameValuePair> paramUrl = new ArrayList<NameValuePair>();
			paramUrl.add(new BasicNameValuePair("phone", phone));
			// puhui_server_register
			String checkUrl = (String) initParams.getProperties().get("puhui_server_register");

			try {
				String results = HttpClientObject.sendPost(checkUrl, paramUrl);
				JSONObject jsonObject = JSONObject.parseObject(results);
				String names = jsonObject.getString("content");
				if ("true".equals(names)) {
					return ResultUtils.returnError("此账号已在普惠商城中注册，可直接用普惠商城的账号密码进行登录。");
				}
				logger.info("请求http返回的数据：" + names);
			} catch (Exception e) {
				logger.info("调用接口异常信息" + e);
				//return ResultUtils.returnError("调用接口失败：" + e.getMessage());
			}

		}
		// 判断注册的密码不能全是数字或者全是字符
		Pattern patternNum = Pattern.compile("[0-9]+");
		Pattern patterStr = Pattern.compile("[a-zA-Z]+");

		// 判断用户是否存在
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("手机号不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(image)) {

			return ResultUtils.returnError("验证码不能为空");
		} else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
			return ResultUtils.returnError("密码不能为空");
		} else if (!m.matches()) {
			return ResultUtils.returnError("不符合手机号格式");
		} else if (list != null) {
			return ResultUtils.returnError("用户已经存在，请直接登录");
		} // 判断不存在在此调用惠普接口，判断惠普是否已经存在，若存在返回已经存在，直接登录，没有存在，在本地在注册。
		else if (6 > password.length() || password.length() > 16) {
			return ResultUtils.returnError("请输入的密码长度不要低于6高于16");
		} else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {
			return ResultUtils.returnError("手机的密码不能纯数字或者纯字母");
		}
		 Result checkMsg = rpcUserSendService.checkMsg(phone, image, codeType);
		if (!(checkMsg.getCode() == 1)) {
			return ResultUtils.returnError(checkMsg.getMsg());
		}
		//调用重庆接口，返回token数据==============================================================================
					List<NameValuePair> registerUrl = new ArrayList<NameValuePair>();
					registerUrl.add(new BasicNameValuePair("phone", phone));
					
					String checkUrl = (String) initParams.getProperties().get("puhui_server_token");

					try {
						String results = HttpClientObject.sendPost(checkUrl, registerUrl);
						JSONObject jsonObject = JSONObject.parseObject(results);
						String names = jsonObject.getString("data");
						String code = jsonObject.getString("code");
						if (names!=null && (code.trim()).equals("200")) {
							token=names;
						}else if ((code.trim()).equals("1005")) {
							token=names;
						} 
						logger.info("请求http返回的数据：" + names);
					} catch (Exception e) {
						logger.info("调用接口异常信息" + e);
						//return ResultUtils.returnError("调用接口失败：" + e.getMessage());
					}
		
		// 获取ip
		String ip = loginServiceImpl.getClientIp(request);
		// 获取uuid
		String uuid = UniqueUtils.getUUID();
		uuid = uuid.replace("-", "");
		// 密码：md5(md5(密码)+uuid)
		String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
		// 获取浏览器
		String browser = getBrower(request);

		// 封装登录记录
		UserLog userLog = getUserLog(phone, ip, sessionId, "会员", browser, new Date());

		// 封装批发商
		Hunter hunter = getHunter(new Date(), ip, phone);

		Hunter hunter1 = null;
		
        //腾讯云注册====================================================================================
		
		try {
			Map map = chatRegist(phone);
			if (map == null) {
				return ResultUtils.returnError("聊天工具注册失败");
			}
			String identifilter = (String) map.get("IMdentifier");
			String imPassword = (String) map.get("IMPassword");
			String userSign = (String) map.get("IMUserSign");
			// name是注册用户的真实信姓名，card是用户的身份证件号
			Member members = getMembers(ip, uuid, passwordMD5, phone, new Date(), hunter1, identifilter, imPassword,
					userSign, "name", "card",token);
			Member memberSave = rpcUserRegisterService.save(members);
			/* rpcUserRegisterService.save(userLog); */

			map.put("logoAttachment", members.getLogoAttachment());
			map.put("nickName", members.getNickname());
			map.put("uuid", members.getUuid());
			map.put("phone", members.getPhone());
			map.put("mId", memberSave.getId());
			map.put("password", members.getPassword());
			map.put("IMUserSign", members.getImSign());
			map.put("token", token);
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(map);

		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}

		return result;
	}
	/**
	 * 通过密码手机号和密码来保存数据
	 */
	public Result addMember(String password,String phone){
		
		Result result = new Result();
		
		Map map = chatRegist(phone);
		if (map == null) {
			return ResultUtils.returnError("聊天工具注册失败");
		}
		String identifilter = (String) map.get("IMdentifier");
		String imPassword = (String) map.get("IMPassword");
		String userSign = (String) map.get("IMUserSign");
		// 获取uuid
		String uuid = UniqueUtils.getUUID();
		uuid = uuid.replace("-", "");
		// 密码：md5(md5(密码)+uuid)
		String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
		/*token的处理
		phone=telPhone,origin=KF,memberType=1;
		如果成功code为200*/
		/*APIHttpClient ac = new APIHttpClient("192.168.0.91:3306/api/members/register 

");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("phone", merchantDTO.getTelPhone());
		jsonObject.put("origin", "KF");
		jsonObject.put("memberType", 1);// 用户类型：0是普通会员，1是商户
		String cc = ac.contentTypeJsonPost(jsonObject.toString());
		logger.info 

("IM注册:" + cc);
		JSONObject code = JSONObject.parseObject(cc);
		if (code.get("code").equals("200")) {
			String data = (String) code.get("data");
			mapDto.put("token", data);
		} else {
			mapDto.put("token", null);
		}*/
    
		// name是注册用户的真实信姓名，card是用户的身份证件号
		String token ="00000";
		Member members = getMembers("000", uuid, passwordMD5, phone, new Date(), null, identifilter, imPassword,
				userSign, "name", "card",token);
		Member memberSave = rpcUserRegisterService.save(members);
		result.setCode(1);
		result.setMsg("成功");
		result.setContent(memberSave);
		return result;
	}

}
