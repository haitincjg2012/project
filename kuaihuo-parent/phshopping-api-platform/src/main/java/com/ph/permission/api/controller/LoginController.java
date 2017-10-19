package com.ph.permission.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.config.shiro.MyShiroRealm;
import com.ph.shopping.common.core.constant.CommonConstants;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.verifycode.VerifycodeUtils;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.ILoginService;
import com.ph.shopping.facade.permission.service.IRoleService;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.RoleVO;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.permission.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：登录controller
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017年5月12日
 *
 * @Copyright @2017 by Mr.Shu
 */
@Controller
public class LoginController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Reference(version="1.0.0")
	ILoginService loginService;

	@Autowired
	MyShiroRealm myShiroRealm;

    @Autowired
    IUserService userService;

    //角色接口
    @Autowired
    private IRoleService roleService;

    /**
     * 短信服务
     */
    @Autowired
    private ISmsDataService smsDataService;

//=========================================页面跳转===========================================/
	/**
	 * @描述：跳转登录页面
	 *
	 * @param: null
	 *
	 * @return:
	 *
     * @作者： Mr.Shu
     *
	 * @创建时间：2017/5/17 17:15
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String loginPage(){
		return "redirect:login";
	}

	/**
	 * @描述：跳转首页
	 *
	 * @param: null
	 *
	 * @return:
	 *
     * @作者： Mr.Shu
     *
	 * @创建时间：2017/5/17 17:15
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(HttpSession session) {
		ModelAndView mv=new ModelAndView("index");
		//返回index需要的用户名和角色信息
		SessionUserVO su = getLoginUser();
		if (!StringUtils.isEmpty(su)) {
			mv.addObject("userId", su.getId());
			mv.addObject("userName", su.getUserName());
            session.setAttribute("userName",su.getUserName());
			StringBuffer sb=new StringBuffer();
			if(su.getSessionRoleVo()!=null&&su.getSessionRoleVo().size()>0){
				for (SessionRoleVO sr : su.getSessionRoleVo()) {
					sb.append(sr.getRoleName()).append(",");
				}
				String str=sb.toString();
				String roleNames=str.substring(0, str.length()-1);
				mv.addObject("roleName", roleNames);
            } else {
                mv.setViewName("login");
                mv.addObject("code", PermissionEnum.NO_ROLE.getCode());
                mv.addObject("msg", PermissionEnum.NO_ROLE.getMsg());
            }
        }
		return mv;
	}

	/**
     * @描述：跳转登录
     *
     * @param: request
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/10 17:25
     */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public Object login() {
		return "login";
	}

    /**
     * @描述：加载验证码
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/6/1 10:20
     */
    @RequestMapping(value = "/loadverify", method = RequestMethod.GET)
    public void loadVerifyCode(HttpServletRequest request, HttpServletResponse response) {

        VerifycodeUtils.makeVerifyImageByNum(request, response, 4);
    }


	/**
	 * @描述：注销登录
	 * @param: request
	 * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/10 17:25
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			logger.error("注销登录处理错误", e);
		}
        return "redirect:login";
    }

	/**
     * @描述：重定向到没有权限返回页面
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/10 17:24
	 */
	@RequestMapping("/403")
	public String unauthorizedRole() {
		logger.info("------没有权限-------");
        return "redirect:noPermission";
    }

    /**
     * @描述：没有权限返回页面
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/10 17:24
     */
    @RequestMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }

	/**
	 * @描述：欢迎页
	 * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/10 17:24
	 */
	@RequestMapping("/welcome")
    public String toWelcome() {
        return "welcome";
    }

    /**
     * @描述：忘记密码
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/10 17:24
     */
    @RequestMapping("/findPassword")
    public String tofindPassword() {
        return "findPassword";
    }

//============================================执行登录==============================================/

	/**
	 * @描述：交给shiro管理登录
	 *
	 * @param: user
	 * @param: request
	 *
	 * @return:
	 *
     * @作者： Mr.Shu
     *
	 * @创建时间：2017/5/10 17:25
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
    public Object login(@ModelAttribute User user, boolean rememberMe, String msgCode, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        //1.图片验证码验证
        String verifyCode = msgCode;
        String secode = VerifycodeUtils.getVerifyCodeBySession(request);
        if (verifyCode == null || !verifyCode.equalsIgnoreCase(secode)) {
            mv.setViewName("login");
            mv.addObject("code", 1111);
            mv.addObject("msg", "验证码错误");
            return mv;
        }
        //查询当前用户是否只包含商户角色
        UserDTO userDTO = new UserDTO();
        userDTO.setTelphone(user.getTelphone());
        UserVO u = userService.getUserByCondition(userDTO);
        //用户的角色集合
        List<RoleVO> roles = roleService.getRoleByUserId(u.getId());
        if (roles.size() ==1 && roles.get(0).getId()==6){
            mv.setViewName("login");
            mv.addObject("code", 1111);
            mv.addObject("msg", "商户禁止登录！！");
            return mv;
        }
        //2.登录认证
        String username = user.getTelphone();
        //MD5加密
        user.setPassword(MD5.getMD5Str(user.getPassword()));
        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword(), rememberMe);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            mv.setViewName("login");
            if(uae.getMessage()!=null&&!"".equals(uae.getMessage().trim())){
                mv.addObject("msg",uae.getMessage());
            }else {
                mv.addObject("msg", PermissionEnum.UNKNOWN_ACCOUNT.getMsg());
            }
            mv.addObject("code", PermissionEnum.UNKNOWN_ACCOUNT.getCode());
            return mv;
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            mv.setViewName("login");
            mv.addObject("code", PermissionEnum.INCORRECT_CREDENTIALS.getCode());
            mv.addObject("msg", PermissionEnum.INCORRECT_CREDENTIALS.getMsg());
            return mv;
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            mv.setViewName("login");
            mv.addObject("code", PermissionEnum.LOCKED_ACCOUNT.getCode());
            mv.addObject("msg", PermissionEnum.LOCKED_ACCOUNT.getMsg());
            return mv;
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            mv.setViewName("login");
            mv.addObject("code", PermissionEnum.EXCESSIVE_ATTEMPTS.getCode());
            mv.addObject("msg", PermissionEnum.EXCESSIVE_ATTEMPTS.getMsg());
            return mv;
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            mv.setViewName("login");
            mv.addObject("code", PermissionEnum.AUTHENTICATION.getCode());
            mv.addObject("msg", PermissionEnum.AUTHENTICATION.getMsg());
            return mv;
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //清除权限缓存
            myShiroRealm.getAuthorizationCache().remove(currentUser.getPrincipals());
            Session session = currentUser.getSession();
            Result result = loginService.login(user);
            session.setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, result.getData());
            mv.setViewName("redirect:index");
        } else {
            token.clear();
            mv.setViewName("redirect:login");
        }
        return mv;
    }


    /**
     * @描述：忘记密码
     * @param: userId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:43
     */
    @RequestMapping(value = "/findPass", method = RequestMethod.POST)
    @ResponseBody
    public Object resetPass(UserDTO userDTO, String codeVal) {

        //1.短信验证
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(userDTO.getTelphone());
        sendData.setSmsCode(userDTO.getMsgCode());
        sendData.setType(SmsCodeType.BACK_USER_PWD_VC);
        if (SmsSourceEnum.AGENT.getCode().equals(codeVal)) {
            sendData.setSourceEnum(SmsSourceEnum.AGENT);
        } else if (SmsSourceEnum.MERCHANT.getCode().equals(codeVal)) {
            sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
        } else if (SmsSourceEnum.SUPPLIER.getCode().equals(codeVal)) {
            sendData.setSourceEnum(SmsSourceEnum.SUPPLIER);
        }
        Result resultSmsCode = smsDataService.checkSmsCodeByMobile(sendData);
        logger.info("根据手机号获取短信验证码返回数据 Result = {} ", JSON.toJSONString(resultSmsCode));
        if (resultSmsCode != null) {
            if (!resultSmsCode.isSuccess()) {
                return resultSmsCode;
            }
        }

        Result result = loginService.findPass(userDTO);
        return result;
    }

    /**
     * @描述：检查用户信息
     * @param: userId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:43
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    @ResponseBody
    public Object checkUser(String telphone) {
        UserDTO userDTO = new UserDTO();
        userDTO.setTelphone(telphone);
        Result result = userService.checkUserByCondition(userDTO);
        return result;

    }

}
