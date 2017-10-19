package com.ph.shopping.facade.permission.service;

import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.UpdatePassDTO;
import com.ph.shopping.facade.permission.dto.UpdateUserRoleDTO;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.dto.UserRoleDTO;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.vo.UserVO;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：用户接口
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-03-13
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface IUserService {

    /**
     * @描述：新增用户
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:11
     */
    Result addUser(UserRoleDTO dto);

    /**
     * @描述：修改密码
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:11
     */
    Result updatePassword(User user);


    /**
     * @描述：修改密码(后台首页)
     *
     * @param: updatePassDto
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:11
     */
    Result updatePass(UpdatePassDTO updatePassDto);


    /**
     * @描述：修改用户信息
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:11
     */
    Result updateUser(User user);


    /**
     * @描述：冻结或者启用用户
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:10
     */
    Result frozenOrEnableUser(User user);


    /**
     * @描述：刪除用户
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:10
     */
    Result deleteUser(User user);

    /**
     * @描述：查询用户
     * @param: user
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 18:10
     */
    Result getUserById(User user);

   /**
    * @描述：查询用户列表
    *
    * @param: page
    * @param: userDto
    *
    * @return:
    *
    * @作者： Mr.Shu
    *
    * @创建时间：2017/5/17 18:10
    */
   Result getUserListByPage(PageBean page,UserDTO userDto);


    /**
     * @描述：通过用户id查询用户所拥有的角色id
     *
     * @param: userId
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:08
     */
    Result getUserRoleByUserId(Long userId);

    /**
     * @描述：修改用户对应的角色
     *
     * @param: updateUserRoleDto
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:07
     */
    Result updateUserRole(UpdateUserRoleDTO updateUserRoleDto);
    
    /**
     * @描述：重置或者初始化用户密码
     *
     * @param: user
     * @param: smsSourceEnum 短信类型
     * @param: type 初始化还是重置密码
     *
     * @return: Result
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:07
     */
    Result resetPassword(User user, SmsSourceEnum smsSourceEnum, String type);
    
    

    /**
     * @描述：根据条件查询用户
     *
     * @param: userDto
     *
     * @return: UserVO
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 18:06
     */
    UserVO getUserByCondition(UserDTO userDto);

    /**
     * @描述：根据条件检查用户
     * @param: userDto
     * @return: UserVO
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 18:06
     */
    Result checkUserByCondition(UserDTO userDto);
}
