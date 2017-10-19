package com.ph.shopping.facade.spm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.constants.Constants;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.mapper.ManageBankCardBindMapper;
import com.ph.shopping.facade.mapper.ManageBankCardInfoMapper;
import com.ph.shopping.facade.mapper.ManageBankCardLogMapper;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.BankDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardBind;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.entity.ManageBankCardLog;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.IManageBankCardService;
import com.ph.shopping.facade.spm.vo.BankVO;
import com.ph.shopping.facade.spm.vo.RealAndIdCardNoVO;

/**
 * 
 * @ClassName: ManageBankCardService
 * @Description: 绑定银行卡
 * @author 王强
 * @date 2017年7月13日 上午11:46:41
 */
@Component
@Service(version = "1.0.0")
public class ManageBankCardService implements IManageBankCardService {

	private static final Logger logger = LoggerFactory.getLogger(ManageBankCardService.class);

	@Autowired
	ManageBankCardInfoMapper bankCardInfoMapper;

	@Autowired
	ManageBankCardBindMapper bankCardBindMapper;

	@Autowired
	ManageBankCardLogMapper bankCardLogMapper;

	public Result getBindCardInfo(ManageBankCardInfo bankCardInfo) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS,
				bankCardInfoMapper.getManagerBankCardInfo(bankCardInfo.getUserId()));
	}

	@Transactional(rollbackFor = Exception.class)
	public Result bindCard(ManageBankCardInfo bankCardInfo, SessionUserVO sessionUserVo) throws BizException {
		logger.info("绑定银行卡参数入参，bankCardInfo={}", JSON.toJSONString(bankCardInfo));
		// Result result = getResult(ResultEnum.SUCCESS);
		try {
			// 查找该银行卡信息
			 ManageBankCardInfo findBankCardInfo = new ManageBankCardInfo();
			 findBankCardInfo.setUserId(sessionUserVo.getId());
			 findBankCardInfo.setCardNo(bankCardInfo.getCardNo());
			 ManageBankCardInfo oldBankCardInfo = bankCardInfoMapper.selectOne(findBankCardInfo);
			 // 修改
			
			if (oldBankCardInfo != null && oldBankCardInfo.getId() != null) {
				bankCardInfo.setId(oldBankCardInfo.getId());
				bankCardInfo.setIsDelete((byte)0);
				bankCardInfoMapper.updateByPrimaryKeySelective(bankCardInfo);
			} else {
				// 保存银行卡信息
				Date date = new Date();
				bankCardInfo.setUserId(sessionUserVo.getId());
				bankCardInfo.setTelPhone(sessionUserVo.getTelphone());
				bankCardInfo.setCreateTime(date);
				bankCardInfo.setCreaterId(sessionUserVo.getId());
				bankCardInfo.setIsDelete((byte) 0);
				bankCardInfoMapper.insertUseGeneratedKeys(bankCardInfo);
			}
			ManageBankCardBind record = new ManageBankCardBind();
			record.setUserId(sessionUserVo.getId());
			record = bankCardBindMapper.selectOne(record);
			if(null == record){
				// 建立绑定关系
				ManageBankCardBind bankCardBind = new ManageBankCardBind();
				bankCardBind.setBankCardInfoId(bankCardInfo.getId());
				bankCardBind.setUserId(sessionUserVo.getId());
				bankCardBind.setBindStatus(1);
				bankCardBind.setCreateTime(new Date());
				bankCardBind.setCreaterId(sessionUserVo.getId());
				bankCardBindMapper.insertSelective(bankCardBind);
				// 记录绑定日志
				bindCardLog(bankCardInfo, sessionUserVo, (byte) 1);
			}else{
				record.setBindStatus(1);
				record.setBankCardInfoId(bankCardInfo.getId());
				bankCardBindMapper.updateByPrimaryKeySelective(record);
				// 记录绑定日志
				bindCardLog(bankCardInfo, sessionUserVo, (byte) 2);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);

		} catch (Exception ex) {
			logger.error("绑定银行卡异常,ex={}", ex);
			ex.printStackTrace();
			throw new BizException("绑定银行卡异常:12011");
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result unBindCard(ManageBankCardInfo bankCardInfo, SessionUserVO sessionUserVo) throws BizException {
		logger.info("解绑银行卡参数入参，bankCardInfo={}", JSON.toJSONString(bankCardInfo));
		try {
			// 解绑及修改银行卡
			// ManageBankCardInfo oldBankCardInfo =
			// bankCardInfoMapper.selectOne(bankCardInfo);
			//
			// //判断是否存在
			// if (oldBankCardInfo == null) {
			//// return getResult(ResultEnum.NO_CHECKBIND_EXCEPTION);
			// return
			// ResultUtil.getResult(UserAccountExceptionEnum.NO_CHECKBIND_EXCEPTION);
			// }
			// 修改银行卡信息
			// bankCardInfoMapper.updateByPrimaryKeySelective(bankCardInfo);

			ManageBankCardBind bankCardBind = new ManageBankCardBind();

			bankCardBind.setUserId(sessionUserVo.getId());

			bankCardBindMapper.updateBankCardBind(sessionUserVo.getId());
			// 删除绑定信息
			// ManageBankCardBind bankCardBind = new ManageBankCardBind();
			// bankCardBind.setBankCardInfoId(bankCardInfo.getId());
			// ManageBankCardBind bankCardBindEntity =
			// bankCardBindMapper.selectOne(bankCardBind);
			// bankCardBindMapper.delete(bankCardBindEntity);

			// 记录解绑日志
			bindCardLog(bankCardInfo, sessionUserVo, (byte) 2);

			return ResultUtil.getResult(RespCode.Code.SUCCESS);

		} catch (Exception ex) {
			logger.error("解绑银行卡异常,ex={}", ex);
			ex.printStackTrace();
			throw new BizException("绑定银行卡异常:12012");
		}
	}

	public void bindCardLog(ManageBankCardInfo bankCardInfo, SessionUserVO sessionUserVo, byte useType) {
		// 记录绑定日志
		ManageBankCardLog bankCardLog = new ManageBankCardLog();
		bankCardLog.setBankCardNo(bankCardInfo.getCardNo());
		bankCardLog.setOwnerName(bankCardInfo.getOwnerName());
		bankCardLog.setTelPhone(bankCardInfo.getTelPhone());
		bankCardLog.setIdCardNo(bankCardInfo.getIdCardNo());
		bankCardLog.setCreaterId(sessionUserVo.getId());
		bankCardLog.setUserId(sessionUserVo.getId());
		bankCardLog.setUseType(useType);
		bankCardLog.setCreateTime(new Date());
		bankCardLogMapper.insertSelective(bankCardLog);
	}
	
	@Override
	public Result getRealNameAndIdCardNoInfo(Long userId) {
		List<RealAndIdCardNoVO> list = bankCardInfoMapper.getRealAndIdCardNo(userId);
		logger.info("获取到的认证信息list=" + JSON.toJSONString(list));
		if(VerifyUtil.listIsNotNull(list)) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS, list.get(0));
		}
		
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}

	@Override
	public int insertRealNameAndIdCardNo(ManageBankCardInfo bankCardInfo) throws Exception {
		logger.info("新增认证信息 ：" + JSON.toJSONString(bankCardInfo));
		int bankInfo = bankCardInfoMapper.insertUseGeneratedKeys(bankCardInfo);
		if (bankInfo != 1) {
			return Constants.FAIL;
		}
		logger.info("新增认证信息成功");
		return bankInfo;
	}

	@Override
	public BankVO getBankInfo(Long userId) {
		return bankCardInfoMapper.getBankInfo(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result insertBindBank(BankDTO bankDTO) throws Exception {
		// 更新认证信息
		logger.info("绑定银行卡入参:" + JSON.toJSONString(bankDTO));
		int bindStatus = 2;
		if(bankDTO.getOperator() == 1 || bankDTO.getOperator() == 2) {
			bindStatus = 1;
		} else if(bankDTO.getOperator() == 3) {
			bindStatus = 2;
		} else {
			return ResultUtil.getResult(UserAccountExceptionEnum.NEED_PARAMS);
		}
		// 银行卡基础数据，存在则修改反之新增
		ManageBankCardInfo record = getManageBankCardInfoQuery(bankDTO);
		record = bankCardInfoMapper.selectOne(record);
		if(null == record){
			record = new ManageBankCardInfo();
			record.setUserId(bankDTO.getUserId());
			record.setCardNo(bankDTO.getCardNo());
			record.setBankName(bankDTO.getBankName());
			record.setCreaterId(bankDTO.getUserId());
			record.setCreateTime(new Date());
			record.setTelPhone(bankDTO.getTelPhone());
			record.setIsDelete((byte)0);
			record.setOwnerName(bankDTO.getOwnerName());
			bankCardInfoMapper.insert(record);
		}else{
			bankCardInfoMapper.updateBankInfo(bankDTO);
		}
		record = getManageBankCardInfoQuery(bankDTO);
		// 重新得到银行卡信息
		record = bankCardInfoMapper.selectOne(record);
		logger.info(" 银行卡基础信息 record =" + JSON.toJSONString(record));
		//判断是否有过绑定关系
		ManageBankCardBind recordt = new ManageBankCardBind();
		recordt.setUserId(bankDTO.getUserId());
		ManageBankCardBind brecord = bankCardBindMapper.selectOne(recordt);
		if(null == brecord) {
			List<RealAndIdCardNoVO> list = bankCardInfoMapper.getRealAndIdCardNo(bankDTO.getUserId());
			logger.info("认证信息集合list=" + JSON.toJSONString(list));
			if (VerifyUtil.listIsNotNull(list)) {
				RealAndIdCardNoVO realNameVo = list.get(0);
				//新增绑定关系
				ManageBankCardBind bankCardBind = new ManageBankCardBind();
				bankCardBind.setBankCardInfoId(record.getId());
				bankCardBind.setBindStatus(bindStatus);
				bankCardBind.setUserId(bankDTO.getUserId());
				bankCardBind.setCreateTime(new Date());
				bankCardBind.setCreaterId(bankDTO.getUserId());
				
				logger.info("新增绑定关系入参bankCardBind:" + JSON.toJSONString(bankCardBind));
				int bankBind = bankCardBindMapper.insertSelective(bankCardBind);
				if (bankBind != 1) {
					logger.error("=====绑定银行卡失败=====bankBind:" + bankBind);
					throw new Exception("绑定银行卡失败");
				}
				
				//新增绑定日志
				ManageBankCardLog bankCardLog = new ManageBankCardLog();
				bankCardLog.setUserId(bankDTO.getUserId());
				bankCardLog.setBankCardNo(bankDTO.getCardNo());
				bankCardLog.setTelPhone(bankDTO.getTelPhone());
				bankCardLog.setOwnerName(realNameVo.getOwnerName());
				bankCardLog.setIdCardNo(realNameVo.getIdCardNo());
				bankCardLog.setUseType((byte) bindStatus);
				bankCardLog.setCreateTime(new Date());
				bankCardLog.setCreaterId(bankDTO.getUserId());
				bankCardLog.setCreateIp(IPUtil.getIpAddress());
				
				logger.info("新增银行卡绑定日志入参bankCardLog:" + bankCardLog);
				int bankLog = bankCardLogMapper.insertSelective(bankCardLog);
				if(bankLog != 1) {
					logger.error("=====新增银行卡绑定日志失败=====bankLog:" + bankLog);
					throw new Exception("新增银行卡绑定日志失败");
				}
				
			} else {
				return ResultUtil.getResult(UserAccountExceptionEnum.AUTH_COMPLETE);
			}
		} else {
			//更新状态
			logger.info("更新银行卡绑定关系参数:bindStatus=" + bindStatus + ",userId=" + bankDTO.getUserId());
			ManageBankCardBind recordo = new ManageBankCardBind();
			recordo.setBankCardInfoId(record.getId());
			recordo.setBindStatus(bindStatus);
			recordo.setUpdaterId(bankDTO.getUserId());
			recordo.setUserId(bankDTO.getUserId());
			recordo.setUpdateTime(new Date());
			recordo.setId(brecord.getId());
			bankCardBindMapper.updateByPrimaryKeySelective(recordo);
		}
		
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}
	
	private ManageBankCardInfo getManageBankCardInfoQuery(BankDTO bankDTO){
		ManageBankCardInfo record = new ManageBankCardInfo();
		record.setUserId(bankDTO.getUserId());
		record.setCardNo(bankDTO.getCardNo());
		record.setIsDelete((byte)0);
		return record;
	}

	@Override
	public Result updateBankCardInfo(Long userId,BankDTO bankDTO) throws Exception {
		ManageBankCardInfo manageBankCardInfo = new ManageBankCardInfo();
		manageBankCardInfo.setUserId(userId);
		ManageBankCardInfo resultBankCardInfo = bankCardInfoMapper.selectOne(manageBankCardInfo);
		if (Objects.isNull(resultBankCardInfo)){
            return ResultUtil.setResult(false,"身份信息不存在");
		}
		resultBankCardInfo.setCardNo(bankDTO.getCardNo());
		resultBankCardInfo.setBankName(bankDTO.getBankName());
		resultBankCardInfo.setUpdaterId(userId);
		resultBankCardInfo.setUpdateTime(new Date());
		//新增
		if ( 1 == bankDTO.getOperator() ){
			bankCardInfoMapper.updateByPrimaryKeySelective(resultBankCardInfo);
			ManageBankCardBind manageBankCardBind = new ManageBankCardBind();
			manageBankCardBind.setUserId(userId);
			manageBankCardBind.setBankCardInfoId(resultBankCardInfo.getId());
			manageBankCardBind.setBindStatus(1);
			manageBankCardBind.setCreaterId(userId);
			manageBankCardBind.setCreateTime(new Date());
			bankCardBindMapper.insert(manageBankCardBind);
			return ResultUtil.setResult(true,"绑卡成功");
		}
        //编辑
		if (2== bankDTO.getOperator()){
			bankCardInfoMapper.updateByPrimaryKeySelective(resultBankCardInfo);
			return ResultUtil.setResult(true,"绑卡成功");
		}
		//删除
		if ( 3 == bankDTO.getOperator() ){
			resultBankCardInfo.setBankName("");
			resultBankCardInfo.setCardNo("");
			bankCardInfoMapper.updateByPrimaryKeySelective(resultBankCardInfo);
			ManageBankCardBind manageBankCardBind = bankCardBindMapper.selectByPrimaryKey(resultBankCardInfo.getId());
            bankCardBindMapper.delete(manageBankCardBind);
			return ResultUtil.setResult(true,"删除成功");
		}
		return ResultUtil.setResult(true,"绑卡成功");
	}
}
