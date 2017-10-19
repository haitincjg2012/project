package com.alqsoft.service.impl.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.login.LoginDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;
import com.alqsoft.init.InitParams;
import com.alqsoft.rpc.mobile.RpcMemberService;
import com.alqsoft.rpc.mobile.RpcUserLogService;
import com.alqsoft.rpc.mobile.RpcUserSendService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.login.LoginService;
import com.alqsoft.service.register.UserRegisterService;
import com.alqsoft.service.salary.SalaryService;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.utils.MD55;
import com.alqsoft.utils.StatusCodeEnums;
import com.mysql.jdbc.StringUtils;
import org.alqframework.pay.weixin.util.MD5Util;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午12:00:04 用户登录记录
 */

@Transactional(readOnly = true)
@Service
public class LoginServiceImpl implements LoginService {
	private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private RpcUserLogService rpcUserLogService;
	@Autowired
	private RpcUserSendService rpcUserSendService;
	@Autowired
	private InitParams initParams;
	@Autowired
	private RpcMemberService rpcMemberService;
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private HunterService hunterService;
	@Autowired
	private SalaryService salaryService;



	/***
	 * 用户登录使用密码和手机号
	 *
	 * @param request
	 * @param session
	 * @return
	 */

	public Result loginPhoneAndPassword(HttpServletRequest request, HttpSession session) {
		// TODO Auto-generated method stub

		Result result = new Result();

		String phone = request.getParameter("phone");

		String password = request.getParameter("password");

		// 获取ipgetClientIp
		String clientIP = getClientIp(request);

		// 获取session ID
		String sessionId = session.getId();
		// 获取浏览器
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

		// 判断是否为空
		if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
			return ResultUtils.returnError("用户不能为为空");
		}

		if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
			return ResultUtils.returnError("用户密码不能为空");
		}else if (password.length()>16 || password.length()<6) {
			return ResultUtils.returnError("密码不能小于6位大于16位");
		}
		// 验证码
		/*
		 * if(!imageCode.equals(session.getAttribute("ImageCode"))){ return
		 * ResultUtils.returnError("验证码错误"); }
		 */
		// 密码：md5(md5(密码)+uuid)

		/*
		 * UserLog
		 * userlog=getUserLog(1L,"13241372414",clientIP,sessionId,"批发商会员",
		 * userbrowser,new Date()); try { rpcUserLogService.save(userlog);
		 * result.setCode(StatusCodeEnums.SUCCESS.getCode());
		 * result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		 * result.setContent(getUserBaseMessage("1234")); } catch (Exception e)
		 * { log.error(e.getMessage(),e);
		 * result.setCode(StatusCodeEnums.ERROR.getCode());
		 * result.setMsg(StatusCodeEnums.ERROR.getMsg());
		 *
		 * }
		 */

		// 获取数据
		Member loginData = loginDao.getLoginData(phone);

		if (loginData != null) {

			//加密密码
			String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + loginData.getUuid(), "utf-8");

			if (loginData.getPassword().equals(passwordMD5)) {
				// 记录登录信息，用户id,登录的次数count,updateTime,creteaIp,sessionId
				UserLog userlog = getUserLog(loginData.getId(), loginData.getPhone(), clientIP, sessionId, "批发商会员",
						userbrowser, new Date());
				try {
					rpcUserLogService.save(userlog);

					result.setCode(StatusCodeEnums.SUCCESS.getCode());

					result.setMsg(StatusCodeEnums.SUCCESS.getMsg());

					result.setContent(getUserBaseMessage(phone));
				} catch (Exception e) {
					// TODO: handle exception
					log.error(e.getMessage(), e);

					result.setCode(StatusCodeEnums.ERROR.getCode());

					result.setMsg(StatusCodeEnums.ERROR.getMsg());
				}

			} else {
				return ResultUtils.returnError("登录密码错误");
			}

		} else {
			return ResultUtils.returnError("没有此用户");

		}

		return result;
	}

	/***
	 * 用户登录使用 手机号和短信验证
	 * codeType 自定义：短信的发送规格：20170313
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	public Result loginPhoneAndMessage(HttpServletRequest request, HttpSession session,String codeType) {
		// TODO Auto-generated method stub
		// 获取手机号
		String phone = request.getParameter("phone").substring(0,11);
		//判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		if(StringUtils.isEmptyOrWhitespaceOnly(phone)){
			return ResultUtils.returnError("手机号不能为空");
		}
	    if(!m.matches()){
	    	return ResultUtils.returnError("手机号不能符合要求");
	    }
		//获取验证码
		String code = request.getParameter("code");
		//获取登录渠道type=1是商户登录；type=2是批发商登
		String type = request.getParameter("type");
		if(StringUtils.isEmptyOrWhitespaceOnly(type)){
			return ResultUtils.returnError("参数非法");
		}
		// 获取ipgetClientIp
		String clientIP = getClientIp(request);

		// 获取session ID
		String sessionId = session.getId();
		// 获取浏览器
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

		Result result = new Result();


		//查询数据库中是否有该手机号
		Map<String, Object> loginData = loginDao.getMemberByPhone2(phone);
		if(loginData==null){
			return ResultUtils.returnError("无此用户，请先注册");
		}
		//2、通过member表中关联的hunter_id,通过hunter_id获取hunter判断是否为null
		String pwd  = (String)loginData.get("password");
		String token = (String)loginData.get("token");
		Long member_id  = Long.valueOf(loginData.get("id")+"");
		String name = (String)loginData.get("name");
		String uuid = (String)loginData.get("uuid");
		String imId  = (String)loginData.get("im_id");
		String imPassword = (String)loginData.get("im_password");
		String imSign  =(String)loginData.get("im_sign");
		Object hunter_id1 = loginData.get("hunter_id");
		String phone1  = (String)loginData.get("phone");

		// 获取验证码结果
		Result imageCode = rpcUserSendService.checkMsg(phone, code, codeType);
		// 验证码
		if (!(imageCode.getCode()==1)) {
			return ResultUtils.returnError("验证码错误,请重新获取验证码");
		}
		// 登录成功，需要获取更改用户的登录记录
		//判断身份登录根据hunter是否为空
		if(null==hunter_id1&& "1".equals(type)){
			UserLog userlog = getUserLog(member_id, phone1, clientIP, sessionId, "商户",
					userbrowser, new Date());
			  rpcUserLogService.save(userlog);
			result.setCode(StatusCodeEnums.SUCCESS.getCode());

			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());

			result.setContent(getUserBaseMessage(phone));
			return result;
		}else if(null!=hunter_id1&&"2".equals(type)){
			UserLog userlog = getUserLog(member_id, phone1, clientIP, sessionId, "批发商",
					userbrowser, new Date());
			  rpcUserLogService.save(userlog);
			result.setCode(StatusCodeEnums.SUCCESS.getCode());

			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());

			result.setContent(getUserBaseMessage(phone));
			return result;
		}else if("1".equals(type)){
			return ResultUtils.returnError("当前登录不是商户");
		}else if("2".equals(type)){
			return ResultUtils.returnError("当前登录不是批发商");
		}else {
			return ResultUtils.returnError("参数非法");
		}

	}

	/***
	 * 发送短信验证码
	 * 发送验证码 codeType  2017030601
	 * phone 手机号
	 * @return
	 */
	public Result sendMessageCode(String phone,String codeType) {

		Result result = new Result();
		//判断手机号
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
		Matcher m = p.matcher(phone);
		if(StringUtils.isEmptyOrWhitespaceOnly(phone)){
			return ResultUtils.returnError("手机号不能为空");
		}
	    if(!m.matches()){
	    	return ResultUtils.returnError("手机号不能符合要求");
	    }
	    //查询数据库中是否有该手机号
		Member loginData  = loginDao.getLoginData(phone);
		if(loginData==null){
			return ResultUtils.returnError("无此用户，请先注册");
		}
	    try {
	    	result = rpcUserSendService.sendMessageCode(phone,codeType);

		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}



		return result;
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

	/**
	 * 获取登录者信息
	 */
	private UserLog getUserLog(Long userId, String loginName, String loginIp, String sessionId, String roleName,
			String browser, Date loginTime) {

		UserLog userLog = new UserLog();// 用户登录记录

		userLog.setId(userId);// 用户id

		userLog.setLoginName(loginName);// 用户名

		userLog.setRoleName(roleName);// 角色名称

		userLog.setCreatedIp(loginIp);// 登录时的ip

		userLog.setSessionId(sessionId);// 用户的sessionId

		userLog.setBrowser(browser);// 浏览器

		userLog.setLoginTime(loginTime);// 用户登录时间

		return userLog;
	}

	/***
	 * 获取登录用户的基本信息(idnent),0为会员，1为批发商
	 *
	 * @param phone
	 * @return list
	 */
	private List<Map> getUserBaseMessage(String phone) {
		String checkUrl = (String) initParams.getProperties().get("puhui_server_h5")+"?accessKey=";
		Map map = new HashMap<>();
		List<Map> list = null;
		list = loginDao.getUserBaseMessager(phone);
		if (list.get(0).get("hunter_id") == null) {
			map.put("ident", "0");
		} else {
			map.put("ident", "1");
		}
		list.add(map);
		if(list.get(0)!=null &&list.get(0).get("token") !=null){
			list.get(0).put("token", checkUrl+list.get(0).get("token"));
		}else{

			String	token = salaryService.getSalaryUrl( phone);
			list.get(0).put("token", checkUrl+token);
		}

		return list;
	}

	/***
	 * 检测是否存在用户
	 * @param phone
	 * @param password
	 */
	@Override
	public Result checkMember(String phone, String password) {

		Result result=new Result();

		if(null == phone || phone.equals("")){
			return ResultUtils.returnError("用户名不能为空");
		}
		if(null == password || password.equals("")){
			return ResultUtils.returnError("密码不能为空");
		}


		Member member = loginDao.getLoginData(phone);//根据用户名查询用户信息

		if(null==member){
			//该用户不存在
			result.setCode(0);
			result.setMsg("用户不存在");
		}else{

			//加密密码
			String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + member.getUuid(), "utf-8");

			if (member.getPassword().equals(passwordMD5)) {//存在且密码正确

				Map<String,Object> map=new HashMap<String,Object>();
				//数据加密处理
				String phone1=member.getPhone();
				String name1=member.getName();
				String card1=member.getCard();
				/*phone1=RSACommonUtils.encryptByPrivateKey(phone1, RSACommonUtils.CharSet.UTF8) ;
				name1=RSACommonUtils.encryptByPrivateKey(name1, RSACommonUtils.CharSet.UTF8) ;
				card1=RSACommonUtils.encryptByPrivateKey(card1, RSACommonUtils.CharSet.UTF8) ;*/


				map.put("phone", phone1);//用户名
				map.put("name", name1);//姓名
				map.put("card", card1);//身份证号

				result.setCode(1);
				result.setMsg("查询成功");
				result.setContent(map);

			}else{//存在密码不正确

				result.setCode(2);
				result.setMsg("密码不正确,请核对信息");
			}

		}

		return result;
	}

	/***
	 * 用户登录
	 * @param
	 * @param
	 * @param request
	 * @param session
	 */
	@Override
	@Transactional
	public Result userLogin(HttpServletRequest request, HttpSession session) {
		String checkUrl = (String) initParams.getProperties().get("puhui_server_h5")+"?accessKey=";
			String phone = request.getParameter("phone");//获取用户
			String password = request.getParameter("password");//获取密码

			Result result=new Result();

			// 获取ipgetClientIp
			String clientIP = getClientIp(request);
			// 获取session ID
			String sessionId = session.getId();
			// 获取浏览器
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

			// 判断是否为空
			if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
				return ResultUtils.returnError("用户不能为为空");
			}

			if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
				return ResultUtils.returnError("用户密码不能为空");
			}else if (password.length()>16 || password.length()<6) {
				return ResultUtils.returnError("密码不能小于6位大于16位");
			}

			// 登录信息记录表
			UserLog userlog=null;
			// 根据用户名在批发商系统查询用户信息
			Member member = loginDao.getLoginData(phone);

			Map<String,Object> memberData=new HashMap<String,Object>();


			if (member != null) {//用户存在

						//加密密码
						String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + member.getUuid(), "utf-8");
						String passwordDate=member.getPassword();
						if(null==passwordDate){passwordDate="";};
						if (passwordDate.equals(passwordMD5)) {//用户存在且密码正确

								List<Map<String,Object>> memberList=loginDao.getMemberByPhone(phone);
								if(memberList.size()==0){
									return ResultUtils.returnError("查无此用户");
								}
								if(memberList.size()>1){
									return ResultUtils.returnError("查询有误，多条记录");
								}
								memberData=memberList.get(0);//登录需返回数据
//=========================================保存toekn路径===============================================================
								if(	memberData.get("token") !=null){
									memberData.put("token", checkUrl+memberData.get("token"));
								}else{

									String	token = salaryService.getSalaryUrl( phone);
									memberData.put("token", checkUrl+token);
								}
								if(null==memberData.get("hunterId")){//判断用户是否会员   0：会员  ； 1：批发商
									memberData.put("ident", "0");
								}else{
									//是批发商  查询身份证和姓名
									Hunter hunter=hunterService.getById(Long.valueOf(memberData.get("hunterId").toString()));
									if(null==hunter){
										return ResultUtils.returnError("查无此批发商用户");
									}

									memberData.put("ident", "1");
									memberData.put("card", hunter.getCard());
									memberData.put("name", hunter.getName());
								}

								// 记录登录信息
								if(null==member.getHunter()){//没有关联的批发商

									 userlog = getUserLog(member.getId(), member.getPhone(), clientIP, sessionId, "会员",userbrowser, new Date());
								}else{

									 userlog = getUserLog(member.getId(), member.getPhone(), clientIP, sessionId, "批发商",userbrowser, new Date());
								}

								rpcUserLogService.save(userlog);//保存

								result.setCode(1);
								result.setMsg("登录成功");
								result.setContent(memberData);

						}else{//用户存在且密码不正确

								//调用普惠检测密码是否正确
								try {
									String puhui_server = (String) initParams.getProperties().get("puhui_server_login");

									List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
									//密码Md5加密
									String passwordMi=MD5Util.MD5Encode(password, "utf-8");

									callpayParame.add(new BasicNameValuePair("phone", phone));
									callpayParame.add(new BasicNameValuePair("password",passwordMi.toUpperCase()));

									log.error("获取普惠登录检测接口,手机号码："+phone+",密码："+password+"访问路径："+puhui_server+"时间："+new Date());

									String checkResult = HttpClientObject.sendPost(puhui_server,callpayParame);

									log.error("获取普惠登录路径："+checkResult);

									JSONObject jsonObj = JSON.parseObject(checkResult);

									log.error("获取普惠登录检测接口返回json数据："+jsonObj);

									Object state = jsonObj.get("code");
									Object data = jsonObj.get("content");
									Object error = jsonObj.get("msg");
									if("1".equals(state.toString())){//普惠检测用户存在  (0：用户不存在；1：用户存在且密码正确；2：用户存在密码不正确；-1、参数不全；3、服务器错误)
										//更新保存用户密码
										 passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + member.getUuid(), "utf-8");
										 member.setPassword(passwordMD5);
										 Member memberdb=rpcMemberService.saveMember(member);//保存用户信息

										 //封装登录返回数据
										 List<Map<String,Object>> memberList=loginDao.getMemberByPhone(phone);
											if(memberList.size()==0){
												return ResultUtils.returnError("查无此用户");
											}
											if(memberList.size()>1){
												return ResultUtils.returnError("查询有误，多条记录");
											}
											memberData=memberList.get(0);//登录需返回数据
											if(null==memberData.get("hunterId")){//判断用户是否会员   0：会员  ； 1：批发商
												memberData.put("ident", "0");
											}else{
												//是批发商  查询身份证和姓名
												Hunter hunter=hunterService.getById(Long.valueOf(memberData.get("hunterId").toString()));
												if(null==hunter){
													return ResultUtils.returnError("查无此批发商用户");
												}

												memberData.put("ident", "1");
												memberData.put("card", hunter.getCard());
												memberData.put("name", hunter.getName());

											}
//=================================================用户存在普惠，保存token===========================================================
											 if(memberData.get("token") !=null){
													memberData.put("token", checkUrl+memberData.get("token"));
												}else{

													String	token = salaryService.getSalaryUrl( phone);
													memberData.put("token", checkUrl+token);
												}

										userlog = getUserLog(memberdb.getId(), memberdb.getPhone(), clientIP, sessionId, "会员",userbrowser, new Date());
										rpcUserLogService.save(userlog);//保存用户登录信息

										result.setCode(1);
										result.setMsg("登录成功");
										result.setContent(memberData);


									}else if("3".equals(state.toString())){//服务器异常
										result.setCode(0);
										result.setMsg("服务器异常");
									}else if("2".equals(state.toString())){//密码不正确

										result.setCode(0);
										result.setMsg("登录密码有误,请核对后重新登录");

									}else if("0".equals(state.toString())){ //普惠检测用户不存在

										result.setCode(0);
										result.setMsg("用户密码错误");

									}else if("-1".equals(state.toString())){ //参数不全

										result.setCode(0);
										result.setMsg("用户信息有误");
									}else{
										result.setCode(0);
										result.setMsg("调用重庆接口返回参数异常");
										}


								} catch (Exception e) {

									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									log.error("获取普惠接口数据出错,手机号码"+phone+","+e.getMessage()+"时间："+new Date());
									e.printStackTrace();

									result.setCode(0);
									result.setMsg("用户登陆失败");

								}

						}
			}else{//检测用户是否存在于普惠

						//*******************批发商同步普惠数据判断***********************************************
						//如果用户在批发商库不存在，则查询普惠判断是否存在，存在则保存，并登陆成功，不存在则提示用户
						try {
							//普惠检测用户是否存在 路径
							String puhui_server = (String) initParams.getProperties().get("puhui_server_login");

							List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
							/*//参数加密
							phone=RSACommonUtils.encryptByPrivateKey(phone, RSACommonUtils.CharSet.UTF8) ;
							password=RSACommonUtils.encryptByPrivateKey(password, RSACommonUtils.CharSet.UTF8) ;*/
							//密码Md5加密
							String passwordMi=MD5Util.MD5Encode(password, "utf-8");

							callpayParame.add(new BasicNameValuePair("phone", phone));
							callpayParame.add(new BasicNameValuePair("password",passwordMi.toUpperCase()));

							log.error("获取普惠登录检测接口,手机号码："+phone+",密码："+password+"访问路径："+puhui_server+"时间："+new Date());

							String checkResult = HttpClientObject.sendPost(puhui_server,callpayParame);

							log.error("获取普惠登录路径："+checkResult);

							JSONObject jsonObj = JSON.parseObject(checkResult);

							log.error("获取普惠登录检测接口返回json数据："+jsonObj);
							Object state = jsonObj.get("code");
							Object data = jsonObj.get("content");
							Object error = jsonObj.get("msg");
								if("1".equals(state.toString())){//普惠检测用户存在  (0：用户不存在；1：用户存在且密码正确；2：用户存在密码不正确；-1、参数不全；3、服务器错误)
									JSONObject jsonData = JSON.parseObject(data.toString());

									String name = jsonData.getString("name");//从普惠获取姓名
									String card = jsonData.getString("idCard");//从普惠获取身份证号
									//数据解密处理
									/*name=RSACommonUtils.decryptByPrivateKey(name, RSACommonUtils.CharSet.UTF8);
									card=RSACommonUtils.decryptByPrivateKey(card, RSACommonUtils.CharSet.UTF8);*/


									Member member_puhui=new Member();
									member_puhui.setPhone(phone);//手机号
									member_puhui.setName(name);//用户名
									member_puhui.setCard(card);//身份证
									//加密密码
									// 获取uuid
									String uuid = UniqueUtils.getUUID();
									     uuid=uuid.replace("-", "");
									member_puhui.setUuid(uuid);
									String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");

									member_puhui.setPassword(passwordMD5);//密码

									String role="";
									Map map = userRegisterService.chatRegist(phone);
									String imId=(String)map.get("IMdentifier");//腾讯云聊天id
									String imPassword=(String)map.get("IMPassword");//腾讯云聊天密码
									String imSign=(String)map.get("IMUserSign");//腾讯云聊天签名
									member_puhui.setImId(imId);
									member_puhui.setImPassword(imPassword);
									member_puhui.setImSign(imSign);


									Member memberdb=rpcMemberService.saveMember(member_puhui);//保存用户信息

									 userlog = getUserLog(memberdb.getId(), memberdb.getPhone(), clientIP, sessionId, "会员",userbrowser, new Date());
									 rpcUserLogService.save(userlog);//保存用户登录信息

									 //封装登录返回数据
									 memberData.put("id", memberdb.getId());
									 memberData.put("name", memberdb.getName());
									 memberData.put("hunterId", "");
									 memberData.put("uuid", memberdb.getUuid());
									 memberData.put("imId", memberdb.getImId());
									 memberData.put("imPassword", memberdb.getImPassword());
									 memberData.put("imSign", memberdb.getImSign());
									 memberData.put("ident", "0");
	//=================================================用户存在普惠，保存token===========================================================
									 if(memberData.get("token") !=null){
											memberData.put("token", checkUrl+memberData.get("token"));
										}else{

											String	token = salaryService.getSalaryUrl( phone);
											memberData.put("token", checkUrl+token);
										}

									result.setCode(1);
									result.setMsg("登录成功");
									result.setContent(memberData);

								}else if("3".equals(state.toString())){//服务器异常
									result.setCode(0);
									result.setMsg("服务器异常");
								}else if("2".equals(state.toString())){//密码不正确

									result.setCode(0);
									result.setMsg("密码不正确");

								}else if("0".equals(state.toString())){ //普惠检测用户不存在

									result.setCode(0);
									result.setMsg("用户不存在");

								}else if("-1".equals(state.toString())){ //参数不全

									result.setCode(0);
									result.setMsg("参数不全");
								}else{
									result.setCode(0);
									result.setMsg("调用重庆接口返回参数异常");
									log.error("调用重庆接口返回参数异常");
									}

						} catch (Exception e) {

							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							log.error("获取普惠接口数据出错,手机号码"+phone+","+e.getMessage()+"时间："+new Date());
							e.printStackTrace();

							result.setCode(0);
							result.setMsg("用户登陆失败");

						}
					//********************************************************************


			}



		return result;
	}

	/*快火批发APP接口登录接口查询商户，供应商信息*/
	@Override
	public Result alqloginPhoneAndPassword(HttpServletRequest request, HttpSession session, Integer type) {
		Result result = new Result();
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			// 获取ipgetClientIp
			String clientIP = getClientIp(request);
			// 获取session ID
			String sessionId = session.getId();
			// 获取浏览器
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

			// 判断是否为空
			if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
				return ResultUtils.returnError("用户不能为为空");
			}

			if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
				return ResultUtils.returnError("用户密码不能为空");
			}else if (password.length()>16 || password.length()<6) {
				return ResultUtils.returnError("密码不能小于6位大于16位");
			}
			// 获取登录信息type=1是商户登录；type=2是批发商登录
			//type=1是商户登录不需要UUID；type=2是批发商登录需要UUID
			//1、通过phone查询member实体类
			Map<String,Object> loginData = loginDao.getMemberByPhone2(phone);
			if(loginData==null){
				return ResultUtils.returnError("请先注册");
			}
			Map memberAndMerchant = loginDao.getMemberAndMerchant(phone);
        log.error("数据获取："+memberAndMerchant);
        log.error("获取map中的数据："+memberAndMerchant.get("frozen"));

        try{
			if(memberAndMerchant==null){
				return ResultUtils.returnError("请先注册");
			}else if(memberAndMerchant !=null && memberAndMerchant.get("frozen")!=null&& memberAndMerchant.get("frozen").equals("1")){
				return ResultUtils.returnError("该用户已冻结请联系客服");
			}
        }catch (Exception e){
            log.error("数据异常："+e);
            return ResultUtils.returnError("用户异常");
        }
		//2、通过member表中关联的hunter_id,通过hunter_id获取hunter判断是否为null
			Object pwd  = loginData.get("password");
			Object token = loginData.get("token");
			Object member_id  = loginData.get("id");
			Object name = loginData.get("name");
			Object hunter_id  = loginData.get("hunter_id");
			Object uuid = loginData.get("uuid");
			Object imId  = loginData.get("im_id");
			Object imPassword = loginData.get("im_password");
			Object imSign  = loginData.get("im_sign");
			if(type==1&&null==hunter_id){//商户

				String md5Str = MD55.getMD5Str(password);
				if(pwd.equals(md5Str)){
					//参数
					/*{
					    code = 1;
					    content =     {
					        1hunterId = "<null>";
					        2id = 33;
					        3ident = 0;
					        4imId = 152371277518898286887l;
					        5imPassword = 3363948133;
					        6imSign = "eJxFkMtugzAQRf*FbargR4ztSlkAalLUVC0KKmKFEBhwaXgYB0Gr-nsoIur2nDuaO-NjBKfzNmlbmcWJjrHKjEcDGA8LFmMrlYiTXAs1Y0gIQQDc7SBUL5t6FghAAhEG4F-KTNRa5nIdRJhCRCmBjHGGmMUY-VqTvSzmyOuT73outpziADxsblQ*sZfxEwwH031uOt1-m04dBnayuZo5qYgtnfKinPeAl2NI8*qts6oijPwJl143*GEaddGJH3cfPj729n5-X5ZV8XLsX6vdXBdzxOEqtbyIpS3AFCPK0cqTNG2utY711IrlO783aDpZ1A__";
					        7name = "\U5b5f\U5fd7\U8363";
					        8token = "null?accessKey=null";
					        9uuid = e94f36fe1c124869b77e0b2c0d65fac6;
					    };
					    msg = "\U767b\U5f55\U6210\U529f";
					}*/
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", member_id);
					//3ident = 0;
					List<Map> list = null;
					list = loginDao.getUserBaseMessager(phone);
					if (list.get(0).get("hunter_id") == null) {
						map.put("hunterId", "null");
						map.put("ident", "0");
					} else {
						map.put("hunterId", "1");
						map.put("ident", "1");
					}
					list.add(map);
					//Long hunterId = loginData.getHunter().getId();
					//=================================================================
					/**
					 * @java-王振宁  批发积分明细接口地址：
					 *  http://123.207.173.18:10088/swagger-ui.html#!/score-web-controller/toMemberScorePageUsingGET
					 */
					//URL=http://123.207.173.18:10088/web/member/score
					//token路径的拼接返给手机端	accessKey是value换成token的值
					String url1 = "http://118.89.24.168:10088/web/member/score?";
					String accessKey = (String) token;
					//String origin = "KF";
					//String memberType = "1";
					//String url = url1 + "&accessKey="+accessKey +"&origin="+origin+"&memberType="+memberType;
					String url = url1 + "accessKey="+accessKey ;
					//=================================================================
					map.put("imId", imId);
					map.put("imPassword", imPassword);
					map.put("imSign", imSign);
					map.put("name", name);
					map.put("token", url);
					map.put("uuid", uuid);
					return ResultUtils.returnSuccess("登录成功",map);
				}else{
					return ResultUtils.returnError("登录密码错误");
				}

			}

			if(type==2&&hunter_id!=null){//批发商
				if(hunter_id!=null){
					/*Member memberdb =  loginDao.getmemberinfo((Long) hunter_id);
					if(memberdb == null) {
						return ResultUtils.returnError("数据为空");
					}*/
					//加密密码
					String passwordMD5 = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "utf-8") + uuid, "utf-8");
					if(pwd.equals(passwordMD5)){

						try {
							//手机端要返回的数据
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", member_id);
							//3ident = 0;
							List<Map> list = null;
							list = loginDao.getUserBaseMessager(phone);
							if (list.get(0).get("hunter_id") == null) {
								map.put("ident", "0");
							} else {
								map.put("ident", "1");
							}
							list.add(map);
							//Long hunterId = loginData.getHunter().getId();
							map.put("imId", imId);
							map.put("hunterId", hunter_id);
							map.put("imPassword", imPassword);
							map.put("imSign", imSign);
							map.put("name", name);
							map.put("token", token);
							map.put("uuid", uuid);

							return ResultUtils.returnSuccess("登录成功",map);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							result.setCode(StatusCodeEnums.ERROR.getCode());
							result.setMsg(StatusCodeEnums.ERROR.getMsg());
						}
					}else{
						return ResultUtils.returnError("密码错误请重新登录");
					}
				}
			}else {
				return ResultUtils.returnError("没有此用户");
			}
		return result;
	}





}
