package com.ph.shopping.facade.member.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.mapper.PayPasswordMapper;
import com.ph.shopping.facade.member.dto.PayPasswordAddDTO;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.dto.PayPasswordUpdateDTO;
import com.ph.shopping.facade.member.entity.TradersPassword;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IPayPasswordService;
/**
 * 
 * phshopping-service-member
 *
 * @description：支付密码接口实现
 *
 * @author：liuy
 *
 * @createTime：2017年5月22日
 *
 * @Copyright @2017 by liuy
 */
@Component
@Service(version = "1.0.0")
public class PayPasswordServiceImpl implements IPayPasswordService{

	//日志
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	//会员支付密码Mapper
	@Autowired
	private PayPasswordMapper memberPayPwdMapper;
	
	@Override
	public Result getPayPwdInfo(PayPasswordQueryDTO dto) {
		log.info("查询支付密码参数,PayPasswordQueryDTO = {} ", JSON.toJSONString(dto));
		//1.验证使用者类型、密码使用者ID参数是否为空
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
		}
		//2.查询会员支付密码
		TradersPassword tradersPassword = new TradersPassword();
		BeanUtils.copyProperties(dto, tradersPassword);
		TradersPassword paypwdinfo = memberPayPwdMapper.selectOne(tradersPassword);
		return ResultUtil.getResult(RespCode.Code.SUCCESS,paypwdinfo);
	}

	@Override
	public Result getPayPwdInfoByTelphone(String telPhone, Byte customerType) {
		log.info("根据手机号查询支付密码参数,telPhone,customerType : ", JSON.toJSONString(telPhone+customerType));
		TradersPassword paypwdinfo = memberPayPwdMapper.selectUserPayPwdByTelPhone(telPhone,customerType);
		return ResultUtil.getResult(RespCode.Code.SUCCESS,paypwdinfo);
	}
	
	@Override
	@Transactional
	public Result addPayPassword(PayPasswordAddDTO dto) {
		log.info("添加会员支付密码参数,PayPasswordQueryDTO = {} ", JSON.toJSONString(dto));
		try {
			//1.验证使用者类型、新密码、密码使用者ID、创建人参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			//2.新增会员支付密码
			TradersPassword tradersPassword = new TradersPassword();
			tradersPassword.setCustomerType(dto.getCustomerType());
			tradersPassword.setPayPwd(MD5.getMD5Str(dto.getNewPassword()));
			tradersPassword.setUserId(dto.getUserId());
			tradersPassword.setCreaterId(dto.getCreaterId());
			tradersPassword.setCreateTime(new Date());
			tradersPassword.setUpdateTime(new Date());
			memberPayPwdMapper.insert(tradersPassword);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error("添加会员支付密码信息错误", e);
			throw new MemberException("添加会员支付密码信息错误");
		}
	}

	@Override
	@Transactional
	public Result updatePayPassword(PayPasswordUpdateDTO dto) {
		log.info("修改支付密码参数,PayPasswordQueryDTO = {} ", JSON.toJSONString(dto));
		try {
			//1.验证使用者类型、新密码、密码使用者ID、修改人参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			//2.修改会员支付密码
			TradersPassword tradersPassword = new TradersPassword();
			tradersPassword.setUserId(dto.getUserId());
			tradersPassword.setCustomerType(dto.getCustomerType());
			tradersPassword.setPayPwd(MD5.getMD5Str(dto.getNewPassword()));
			tradersPassword.setUpdaterId(dto.getCreaterId());
			if(dto.getUpdaterId()!=null){
				tradersPassword.setUpdaterId(dto.getUpdaterId());
			}
			tradersPassword.setUpdateTime(new Date());
			memberPayPwdMapper.updadtePayPwd(tradersPassword);// 根据密码使用者和密码类型 
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error("修改支付密码信息错误", e);
			throw new MemberException("修改支付密码信息错误");
		}
	}

	@Override
	public Result verifyPayPwdIsExists(PayPasswordQueryDTO dto) {
		//1.验证使用者类型、密码使用者ID参数是否为空
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
		}
		//2.查询会员支付密码
		int count = memberPayPwdMapper.selectPayPwdIsExist(dto);
		if (count>0) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} else {
			return ResultUtil.getResult(MemberResultEnum.PAY_PWD_EMPTY);
		}
	}

}
