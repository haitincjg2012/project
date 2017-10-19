package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.ResetPasswordDTO;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.mapper.RoleMapper;
import com.ph.shopping.facade.mapper.UserMapper;
import com.ph.shopping.facade.mapper.UserRoleMapper;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.permission.constant.UserConstant;
import com.ph.shopping.facade.permission.dto.UpdatePassDTO;
import com.ph.shopping.facade.permission.dto.UpdateUserRoleDTO;
import com.ph.shopping.facade.permission.dto.UserDTO;
import com.ph.shopping.facade.permission.dto.UserRoleDTO;
import com.ph.shopping.facade.permission.entity.Role;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.entity.UserRole;
import com.ph.shopping.facade.permission.exception.PermissionBizException;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.service.check.ParamsCheck;
import com.ph.shopping.facade.permission.vo.UserVO;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @项目：phshopping-service-permission
 *
 * @描述：用户实现层
 *
 * @作者：Mr.Shu
 *
 * @创建时间：2017-05-18
 *
 * @Copyright @2017 by Mr.Shu
 */
@Component
@Service(version="1.0.0")
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    //短信发送接口
    @Autowired
    private ISmsDataService smsDataService;

    @Autowired
    private SmsUtil util;


    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result addUser(UserRoleDTO dto) {
        try {
        	logger.info("新增用户入参，dto={}",JSON.toJSONString(dto));
        	Result result = ResultUtil.getResult(PermissionEnum.Code.FAIL);
        	if (ParamsCheck.checkAddUser(dto)) {
            	//验证手机号是否存在
                if (null != userMapper.findUserByPhone(dto.getTelphone())) {
                    return ResultUtil.getResult(PermissionEnum.REGISTERED);
                }
                User user =new User();
                //参数转换
                paramsTranf(user, dto);
                //新增用戶
                userMapper.insertUseGeneratedKeys(user);
                Long userId = user.getId();
                //除了管理员类型的账号以外，需要默认分配角色
                if (dto.getRoleCode().intValue() != 1) {
                	addUserRole(dto.getRoleCode().byteValue(), userId);
                }
                result = ResultUtil.getResult(PermissionEnum.Code.SUCCESS,userId);
        	}
        	logger.info("新增用户返回值，result={}",JSON.toJSONString(result));
            return result;
        } catch (Exception ex) {
            logger.error("新增用户异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.ADD_USER_ERROR);
        }
    }

    @Override
    @Transactional
    public Result updatePassword(User user) {
        try {
        	logger.info("修改密码入参,user={}",JSON.toJSONString(user));
            if (StringUtils.isNotBlank(user.getTelphone())) {
                User findUser = this.userMapper.findUserByPhone(user.getTelphone());
                if (findUser == null) {
                    return new Result(false, "500", "用户不存在");
                }
                user.setId(findUser.getId());
            }
            modifyUser(user);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("修改密码异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_PASSWORD_ERROR);
        }
    }


    @Transactional
    @Override
    public Result updatePass(UpdatePassDTO updatePassDto) {
        try {
            Result result;
            logger.info("(后台)修改密码入参,updatePassDto={}",JSON.toJSONString(updatePassDto));
            User user = userMapper.selectByPrimaryKey(updatePassDto.getId());
            String oldPass = MD5.getMD5Str(updatePassDto.getPassword());
            String newPass = MD5.getMD5Str(updatePassDto.getNewPassword());
            if (user != null && StringUtils.isNotBlank(user.getPassword())
                    && user.getPassword().equals(oldPass)) {
                User mdyUser = new User();
                mdyUser.setId(updatePassDto.getId());
                mdyUser.setPassword(newPass);
                modifyUser(mdyUser);
                result = ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
            } else {
                result = ResultUtil.getResult(PermissionEnum.PASSWORD_ERROR);
            }
            logger.info("(后台)修改密码返回值,result={}",JSON.toJSONString(result));
            return result;
        } catch (Exception ex) {
            logger.error("(后台)修改密码入参，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_PASSWORD_ERROR);
        }
    }

    @Transactional
    @Override
    public Result updateUser(User user) {
        try {
        	logger.info("修改用户信息入参,user={}",JSON.toJSONString(user));
        	//判断手机号是否重复
            User queryUser = userMapper.findUserByPhone(user.getTelphone());
            if ( null != queryUser &&  !queryUser.getId().equals(user.getId())) {
                return ResultUtil.getResult(PermissionEnum.REGISTERED);
            }
            modifyUser(user);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("修改用户信息异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_USER_ERROR);
        }
    }

    @Transactional
    @Override
    public Result frozenOrEnableUser(User user) {
    	try {
    		logger.info("冻结或者启用用户入参,user={}",JSON.toJSONString(user));
    		user.setUpdateTime(new Date());
	        userMapper.updateByPrimaryKeySelective(user);
	        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
		} catch (Exception e) {
            throw new PermissionBizException(PermissionEnum.DISABLE_ERROR);
        }
       
    }

    @Transactional
    @Override
    public Result deleteUser(User user) {
        try {
        	logger.info("删除用户入参,user={}",JSON.toJSONString(user));
            userMapper.deleteByPrimaryKey(user);
            userRoleMapper.deleteUserRoleByUserId(user.getId());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("删除用户异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.DELETE_USER_ERROR);
        }
    }

    @Override
    public Result getUserById(User user) {
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,userMapper.selectByPrimaryKey(user));
    }

    public Result getUserListByPage(PageBean page,UserDTO userDto) {
    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<UserVO> list = userMapper.getUserByPage(userDto);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
    }

    @Override
    public Result getUserRoleByUserId(Long userId) {
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, userRoleMapper.selectUserRoleByUserId(userId));
    }

    @Transactional
    @Override
    public Result updateUserRole(UpdateUserRoleDTO updateUserRoleDto) {
    	try {
    		logger.info("修改用户对应的角色入参，updateUserRoleDto={}", JSON.toJSONString(updateUserRoleDto));
            userRoleMapper.deleteUserRoleByUserId(updateUserRoleDto.getUserId());
            userRoleMapper.insertUserRole(updateUserRoleDto.getUserId(), updateUserRoleDto.getRoleIds(), updateUserRoleDto.getCreaterId(), updateUserRoleDto.getUpdaterId());
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
    	} catch (Exception e) {
			logger.error("修改用户对应的角色异常，ex={}", e);
            throw new PermissionBizException(PermissionEnum.UPDATE_USER_ROLE_ERROR);
        }
       
    }

    @Transactional
	@Override
    public Result resetPassword(User user, SmsSourceEnum smsSourceEnum, String type) {
        try {
			logger.info("重置密码入参，user={}", JSON.toJSONString(user));
            Result result = ResultUtil.getResult(PermissionEnum.PASSWORD_REMIND);
            if (null != smsSourceEnum) {
                //创建随机密码
                String rePass = getStringRandom(8);

                //6.发送密码短信
                User findUser = new User();
                findUser.setId(user.getId());
                User dbUser = userMapper.selectOne(findUser);
                if (dbUser == null) {
                    result.setMessage("没有用户信息!");
                    return result;
                }
               // SmsSendData sendData = new SmsSendData();
                ResetPasswordDTO dto =new  ResetPasswordDTO();
               // RegisterDTO dto = new RegisterDTO();
                //sendData.setMobile(dbUser.getTelphone());
                       // dto.setKfPhone(dbUser.getTelphone());
                //判断是初始化密码还是重置密码
                if (StringUtils.isEmpty(type)) {
                    //sendData.setType(SmsCodeType.RESET_PASSWORD_PD);//代理商，商户，供应商重置密码
                   // sendData.setModelData("2");
                    dto.setRole("dl");
                } else {
                    /*sendData.setType(SmsCodeType.EXAMINE_PASS);//代理商，商户，供应商重置密码
                    sendData.setModelData("1");*/
                    dto.setRole("dl");
                }
               // sendData.setSourceEnum(smsSourceEnum);//枚举类型判断
               //  sendData.setSmsCode(rePass);//随机密码
                dto.setPassword(rePass);
                dto.setKfPhone("400–8586–315");
                dto.setPhone(dbUser.getTelphone());

               //通过后发送验证码
               // Result smsResult = smsDataService.sendSmsHaveCode(sendData);

                Result b = util.resetPassword(dto);
               /* if (smsResult == null || !MemberResultEnum.Code.SUCCESS.getCode().equals(smsResult.getCode())) {
                    logger.info("给用户发送短信密码失败:" + smsResult);
                    throw new MemberException("给用户发送短信密码失败");
                }*/
                if (!"1".equals(b.getCode())) {
                    logger.info("给用户发送短信密码失败:" );
                    throw new MemberException("给用户发送短信密码失败");
                }
                user.setPassword(MD5.getMD5Str(rePass));
                result.setMessage("重置随机密码成功!");
            } else {
                user.setPassword(MD5.getMD5Str(UserConstant.PASSWORD));
            }
            user.setUpdateTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            logger.error("重置密码异常", e);
            throw new PermissionBizException(PermissionEnum.RESET_PASSWORD_ERROR);
        }
	 	
	}

	@Override
    public UserVO getUserByCondition(UserDTO userDto) {
        return userMapper.getUserByCondition(userDto);
	}

    @Override
    public Result checkUserByCondition(UserDTO userDto) {

        UserVO userVO = userMapper.getUserByCondition(userDto);
        if (userVO == null) {
            return ResultUtil.getResult(PermissionEnum.NO_TELPHONE_ERROR);
        } else {

            //帐号审核
            if (userVO.getAgentPass() != null && !(SPMEnum.agentStatus.ONE.getIndex() == userVO.getAgentPass())) {
                return ResultUtil.getResult(PermissionEnum.NO_PASS);
            } else if (userVO.getMerchantPass() != null && !(SPMEnum.merchantStatus.ONE.getIndex() == userVO.getMerchantPass())) {
                return ResultUtil.getResult(PermissionEnum.NO_PASS);
            } else if (userVO.getSupplierPass() != null && !(SPMEnum.supplierStatus.ONE.getIndex() == userVO.getSupplierPass())) {
                return ResultUtil.getResult(PermissionEnum.NO_PASS);
                //账号锁定
            } else if (userVO.getAgentStatus() != null && SPMEnum.agentIsFrozen.ONE.getIndex() == userVO.getAgentStatus()) {
                return ResultUtil.getResult(PermissionEnum.LOCKED_ACCOUNT);
            } else if (userVO.getMerchantStatus() != null && SPMEnum.merchantIsFrozen.ONE.getIndex() == userVO.getMerchantStatus()) {
                return ResultUtil.getResult(PermissionEnum.LOCKED_ACCOUNT);
            } else if (userVO.getSupplierStatus() != null && SPMEnum.supplierIsFrozen.ONE.getIndex() == userVO.getSupplierStatus()) {
                return ResultUtil.getResult(PermissionEnum.LOCKED_ACCOUNT);
            } else if (userVO.getIsable() == 2) {
                return ResultUtil.getResult(PermissionEnum.LOCKED_ACCOUNT);
            }

            if (userVO.getAgentName() != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS, SmsSourceEnum.AGENT.getCode());
            } else if (userVO.getMerchantName() != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS, SmsSourceEnum.MERCHANT.getCode());
            } else if (userVO.getSupplierName() != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS, SmsSourceEnum.SUPPLIER.getCode());
            } else {
                return ResultUtil.getResult(PermissionEnum.NO_FIND_PASSWORD_ERROR);
            }
        }
    }

//=======================================私有通用方法===============================================/

    /**
     * @描述：修改用户
     * @param: user
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:47
     */
    private void modifyUser(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(user.getPassword());
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * @描述：参数转换
     * @param: user
     * @param: dto
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:49
     */
    private void paramsTranf(User user, UserRoleDTO dto) {
        user.setCreaterId(dto.getCreaterId());
        user.setUpdaterId(dto.getUpdaterId());
        user.setUserName(dto.getUserName());
        user.setTelphone(dto.getTelphone());
        user.setPassword(MD5.getMD5Str(dto.getPassword()));
        user.setIsable(UserConstant.START);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

    }

    /**
     * @描述：除了管理员类的角色自动分配
     * @param: roleCode
     * @param: userId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/18 9:49
     */
    private void addUserRole(Byte roleCode, Long userId) {
        //通过roleCode查询角色信息
        Role role = new Role();
        role.setRoleCode(roleCode);
        List<Role> list = roleMapper.selectRoleBySelective(role);
        Long roleId = null;
        if (CollectionUtils.isNotEmpty(list)) {
            roleId = list.get(0).getId();
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        userRoleMapper.inserUserRole(userRole);
    }

    /**
     * @描述：生成随机数字和字母的密码
     * @param: length 密码长度
     * @return: String
     * @作者： Mr.Shu
     * @创建时间：2017/6/5 10:03
     */
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


}
