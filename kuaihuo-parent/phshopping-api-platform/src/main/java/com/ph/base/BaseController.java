/**
 * 
 */
package com.ph.base;

import com.ph.config.shiro.MyShiroRealm;
import com.ph.shopping.common.core.constant.CommonConstants;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.service.ILoginService;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.permission.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @项目：phshopping-common-core
 * 
 * @描述：basecontroller类（用于获取用户信息等公共操作）
 * 
 * @作者： Mr.chang
 * 
 * @创建时间：2017年3月8日
 * 
 * @Copyright @2017 by Mr.chang
 */
public class BaseController {

    @Autowired
	MyShiroRealm myShiroRealm;

	@Autowired
	ILoginService loginService;

    //用户接口
    @Autowired
    private IUserService userService;

    /**
     * @描述：得到Response
     *
     * @param: null
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/6/15 10:21
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
    }

    /**
     * 得到Request对象
     * @author WQiang
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

	//创建公共日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @描述：保存登录信息到session中
	 *
	 * @param: null
	 *
	 * @return:
	 *
	 * @作者： Mr.Shu
	 *
	 * @创建时间：2017/5/17 18:52
	 */
	public void setLoginUser(Object obj) {
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.getSession().setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, obj);
	}

	/**
	 * @描述：从session中删除登录信息
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 18:53
	 */
	public void removeLoginUser() {
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.getSession().removeAttribute(CommonConstants.LOGIN_BACK_USER_SESSION);
	}

	/**
	 * @描述：通过用户手机号踢出用户Session
	 * @param: telPhone 用户手机号
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/6/6 10:43
	 */
	public void forceLogoutUsersByUserId(String telPhone) {
		//处理session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
		if (sessions != null && sessions.size() != 0) {
			for (Session session : sessions) {
				//清除该用户以前登录时保存的session
				if (telPhone.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
					sessionManager.getSessionDAO().delete(session);
				}
			}
		}
	}

	/**
	 * @描述：获取登录人的session
	 * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/17 18:53
	 */
	public SessionUserVO getLoginUser() {

		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		SessionUserVO info = (SessionUserVO) currentUser.getSession().getAttribute(
				CommonConstants.LOGIN_BACK_USER_SESSION);

		//如果是记住我，处理Session失效
		if (info == null) {
            Object telPhone = currentUser.getPrincipal();
            if (telPhone != null) {
                boolean isRemembered = currentUser.isRemembered();
                if (isRemembered) {
                    //清除权限缓存
                    myShiroRealm.getAuthorizationCache().remove(currentUser.getPrincipals());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setTelphone(telPhone.toString());
					UserVO userVO = userService.getUserByCondition(userDTO);
					//对密码进行加密后验证
					UsernamePasswordToken token = new UsernamePasswordToken(userVO.getTelphone(), userVO.getPassword(), currentUser.isRemembered());
					//把当前用户放入session
                    currentUser.login(token);
                    Session session = currentUser.getSession();
					User user = new User();
					user.setTelphone(userVO.getTelphone());
					user.setPassword(userVO.getPassword());
					Result result = loginService.login(user);
                    session.setAttribute(CommonConstants.LOGIN_BACK_USER_SESSION, result.getData());
                    info = (SessionUserVO) result.getData();
                }
            }

        }
		return info;

	}
	

}
