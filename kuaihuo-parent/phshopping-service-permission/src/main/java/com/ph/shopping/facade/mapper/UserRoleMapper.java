package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.permission.entity.UserRole;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述：
 * @作者： Mr.zheng
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.zheng
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * @methodname deleteUserRoleByUserId 的描述：通过用户id删除用户角色关系
     * @param userId
     * @return int
     * @author Mr.Shu
     * @create 2017/5/17
     */
    int deleteUserRoleByUserId(@Param("userId") Long userId);


    /**
     * @methodname selectUserRoleByUserId 的描述：通过用户id查询用户对应的角色id
     * @param userId
     * @return java.util.List<java.lang.Long>
     * @author Mr.Shu
     * @create 2017/5/17
     */
    List<Long> selectUserRoleByUserId(@Param("userId") Long userId);


    /**
     * @methodname insertUserRole 的描述：给用户赋予角色
     * @param userId
     * @param roleIds
     * @return int
     * @author Mr.Shu
     * @create 2017/5/17
     */
    int insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds, @Param("createrId") Long createrId, @Param("updaterId") Long updaterId);

    /**
     * @methodname selectRoleVOByUserId 的描述：通过用户id查询role
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.permission.vo.SessionRoleVO>
     * @author Mr.Shu
     * @create 2017/5/17
     */
    List<SessionRoleVO> selectRoleVOByUserId(@Param("userId") Long userId);
    
    /**
     * @methodname inserUserRole 的描述：新增用户对应的角色
     * @param userRole
     * @return int
     * @author 郑朋
     * @create 2017/4/24
     */
    int inserUserRole(UserRole userRole);


    /**
     * @methodname selectUserRole 的描述：查询角色用户
     * @param userRole
     * @return java.util.List<com.ph.shopping.facade.permission.entity.UserRole>
     * @author 郑朋
     * @create 2017/4/24
     */
    List<UserRole> selectUserRole(UserRole userRole);
}
