package com.ph.shopping.facade.permission.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.entity.User;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：登录校验
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-03-15
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface ILoginService {
    /**
     * @描述：登录校验
     *
     * @param: user
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:52
     */
    Result login(User user);

    /**
     * @描述：忘记密码
     * @param: user
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/17 17:52
     */
    Result findPass(UserDTO userDTO);
}
