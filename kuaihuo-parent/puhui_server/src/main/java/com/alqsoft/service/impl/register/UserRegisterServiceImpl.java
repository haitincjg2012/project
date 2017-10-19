package com.alqsoft.service.impl.register;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.register.RegisterDao;
import com.alqsoft.dao.register.RegisterHunterDao;
import com.alqsoft.dao.register.RegisterLogDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.register.UserRegisterService;
import com.alqsoft.utils.StatusCodeEnums;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午10:52:10
 * Copyright 
 */
@Transactional(readOnly=true)
@Service
public class UserRegisterServiceImpl implements UserRegisterService{
	
	@Autowired
	private RegisterDao userRegisterDao;
	@Autowired
	private RegisterLogDao   rgisterLogDao;
	@Autowired
	private RegisterHunterDao registerHunterDao;
    /***
     * 保存会员信息
     */
	@Override
	@Transactional
	public Member save(Member member) {
		// TODO Auto-generated method stub
		Result  result=new Result();
		Member save=null;
		
		 try {
			 save = userRegisterDao.save(member);
			 result.setCode(StatusCodeEnums.SUCCESS.getCode());
			 result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			 result.setContent("注册成功");
		} catch (Exception e) {
			// TODO: handle exception
			
			 result.setCode(StatusCodeEnums.ERROR.getCode());
			 result.setMsg(StatusCodeEnums.ERROR.getMsg());
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			 
		}
		return save;
	}
	
	/***
	 * 保存登录用户记录
	 */
	public Result save(UserLog userLog) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(userLog==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
			result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
			result.setContent("参数为空");
		}
		 try {
			 rgisterLogDao.save(userLog);
			 result.setCode(StatusCodeEnums.SUCCESS.getCode());
			 result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			 result.setContent("注册成功");
		} catch (Exception e) {
			// TODO: handle exception
			 result.setCode(StatusCodeEnums.ERROR.getCode());
			 result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}
	/**
	 * 保存批发商注册信息
	 */
	public Result save(Hunter hunter) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(hunter==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
			result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
			result.setContent("参数为空");
		}
		 try {
			 Hunter hunter2 = registerHunterDao.save(hunter);
			 result.setCode(StatusCodeEnums.SUCCESS.getCode());
			 result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			 result.setContent(hunter2);
		} catch (Exception e) {
			// TODO: handle exception
			 result.setCode(StatusCodeEnums.ERROR.getCode());
			 result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}

	@Override
	public Result updateMemberById(Member member, Long id) {
		// TODO Auto-generated method stub
		Result result = new Result();
		Member findOne = userRegisterDao.findOne(id);
		if(findOne == null){
			return ResultUtils.returnError("没有此用户");
		}
		findOne.setToken(member.getToken());
		try {
			Member save = userRegisterDao.save(findOne);
			if(save != null){
				result.setCode(1);
				result.setMsg("保存成功");	
			}else{
				result.setCode(0);
				result.setMsg("数据保存失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		return result;
	}

}
