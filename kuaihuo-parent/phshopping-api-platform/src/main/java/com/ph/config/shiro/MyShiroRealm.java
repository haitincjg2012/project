package com.ph.config.shiro;

import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.service.IBtnService;
import com.ph.shopping.facade.permission.service.IMenuService;
import com.ph.shopping.facade.permission.service.IRoleService;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.BtnVO;
import com.ph.shopping.facade.permission.vo.MenuVO;
import com.ph.shopping.facade.permission.vo.RoleVO;
import com.ph.shopping.facade.permission.vo.UserVO;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @项目：Shiro配置
 * @描述：Shiro权限模块
 * @作者： Mr.Shu, liuy
 * @创建时间：2017年5月9日
 * @Copyright @2017 by Mr.Shu,liuy
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    //用户接口
    @Autowired
    private IUserService userService;
    //角色接口
    @Autowired
    private IRoleService roleService;
    //菜单接口（后面要改成权限接口）
    @Autowired
    private IMenuService menuService;
    //按钮接口
    @Autowired
    private IBtnService btnService;

    /**
     * 获取菜单接口方法，用于配置文件调用菜单接口（后面要改为获取权限接口）
     * @return 菜单接口（后面要改为权限接口）
     * @author Mr.Shu
     * @createTime 2017年05月10日
     */
    public IMenuService getMenuService() {
        return menuService;
    }

    /**
     * 获取按钮接口方法，用于配置文件调用菜单接口（后面要改为获取权限接口）
     *
     * @return 按钮接口（后面要改为权限接口）
     * @author Mr.Shu
     * @createTime 2017年05月13日
     */
    public IBtnService getBtnService() {
        return btnService;
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     * 经测试：本例中该方法的调用时机为需授权资源被访问时
     * 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     *
     * @param: principalCollection
     *
     * @return: AuthorizationInfo
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/15 13:56
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //1.获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        //2.查询用户是否存在
        UserDTO userDTO = new UserDTO();
        userDTO.setTelphone(loginName);
        UserVO user = userService.getUserByCondition(userDTO);
        //3.如果用户存在，获取该用户的角色集合和权限集合
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();	//权限信息对象info,用来存放用户的所有角色（role）及权限（permission）
            //用户的角色集合
            List<RoleVO> roles = roleService.getRoleByUserId(user.getId());
            info.setRoles(ConvertRoleListUtil.ConvertRoleListToSet(roles));

            //菜单权限
            List<Long> roleList = new ArrayList<>();
            for (RoleVO role : roles) {
                roleList.add(role.getId());
            }
            List<MenuVO> menuVOS = menuService.getMenuByRoleIds(roleList);
            info.addStringPermissions(ConvertRoleListUtil.ConvertPermissionListToStringList(menuVOS));

            //按钮权限
            //Session按钮集合
            Collection<String> permissions = new HashSet<>();
            List<Long> menuList = new ArrayList<>();
            for (MenuVO m : menuVOS) {
                menuList.add(m.getId());
            }
            for (Long roleId : roleList) {
                List<BtnVO> btnVOS = btnService.getBtnListByMenuId(menuList, roleId);
                info.addStringPermissions(ConvertRoleListUtil.ConvertBtnPermissionListToStringList(btnVOS));
                permissions.addAll(ConvertRoleListUtil.ConvertBtnPermissionListToStringList(btnVOS));
            }
            logger.info("该用户总角色数为：" + info.getRoles().size());
            logger.info("角色详细列表如下:");
            for (String s : info.getRoles()) {
                logger.info(s);
            }
            logger.info("该用户总权限数为：" + info.getStringPermissions().size());
            logger.info("权限详细列表如下:");
            for (String s : info.getStringPermissions()) {
                logger.info(s);
            }
            //获取当前的Subject把权限按钮放入Session中
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            for (String s : permissions) {
                session.setAttribute(s, s);
            }
            return info;
        }
        return null; // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
    }

    /**
     * @描述：登录认证
     * @param: authenticationToken
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/15 13:54
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        //1.查询用户是否存在
        UserDTO userDTO = new UserDTO();
        userDTO.setTelphone(token.getUsername());
        UserVO user = userService.getUserByCondition(userDTO);
        if (user == null) {//账户不存在
            throw new UnknownAccountException();
        }else{

            //帐号审核
            if(user.getAgentPass() != null && !(SPMEnum.agentStatus.ONE.getIndex() == user.getAgentPass())){
                throw new UnknownAccountException("未通过审核");
            }else if(user.getMerchantPass() != null && !(SPMEnum.merchantStatus.ONE.getIndex() == user.getMerchantPass())){
                throw new UnknownAccountException("未通过审核");
            }else if(user.getSupplierPass() != null && !(SPMEnum.supplierStatus.ONE.getIndex() == user.getSupplierPass())){
                throw new UnknownAccountException("未通过审核");
            //账号锁定
            }else if (user.getAgentStatus() != null && SPMEnum.agentIsFrozen.ONE.getIndex() == user.getAgentStatus()) {
                throw new LockedAccountException();
            } else if (user.getMerchantStatus() != null && SPMEnum.merchantIsFrozen.ONE.getIndex() == user.getMerchantStatus()) {
                throw new LockedAccountException();
            } else if (user.getSupplierStatus() != null && SPMEnum.supplierIsFrozen.ONE.getIndex() == user.getSupplierStatus()) {
                throw new LockedAccountException();
            } else if (user.getIsable() == 2) {
                 throw new LockedAccountException();
            } else {
                // 若账户存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
                 return new SimpleAuthenticationInfo(user.getTelphone(), user.getPassword(), getName());
            }
        }
    }

}