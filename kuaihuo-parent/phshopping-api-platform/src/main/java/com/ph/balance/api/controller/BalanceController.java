package com.ph.balance.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.UserBalanceOuterDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.vo.AgentVO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("web/outerbalance")
public class BalanceController extends BaseController {
	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private IUserBalanceService iUserBalanceService;

	//代理接口
	@Reference(version = "1.0.0")
	private IAgentService iAgentService;
	
	@RequestMapping(value="userbalance",method=RequestMethod.POST)
	@ResponseBody
//	@CrossOrigin(origins = "http://192.168.1.223:10086")
//	@CrossOrigin(origins = "http://123.206.8.92:10087")
	public Result userBalanceTrade(UserBalanceOuterDTO outerDTO) {
		try {
			logger.info("批发分润入参outerDTO:" + JSON.toJSONString(outerDTO));
			TransCodeEnum codeEnum = TransCodeEnum.valueOf(outerDTO.getTransCode());
			int res = iUserBalanceService.userBalanceTrade(outerDTO.getUserId(), codeEnum, outerDTO.getScore(), outerDTO.getOrderNo(), outerDTO.getHandingCharge(), outerDTO.getUserType());
			if (res > 0) {
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception e) {
			logger.error("用户余额处理失败", e);
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}
	
	/**
	 * 2017-7-20批发商城分润新需求(通过区域查询代理，这里只返回县级代理)
	* @Title: getAgentByPostion
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年7月20日 下午2:07:22
	* @param agentDTO
	* @return
	 */
	@RequestMapping(value="getAgentByPostion",method=RequestMethod.POST)
	@ResponseBody
//	@CrossOrigin(origins = "http://192.168.1.225:10087")
//	@CrossOrigin(origins = "http://123.206.8.92:10087")
	public Result getAgentByPostion(AgentDTO agentDTO){
		try {
			logger.info("批发通过区域查询代理入参agentDTO:" + JSON.toJSONString(agentDTO));
			logger.info("批发订单通过区域查询代理传入参数"+JSON.toJSONString(agentDTO));
			List<AgentVO> agentPositionList = iAgentService.getAgentPositionList(agentDTO);
			logger.info("批发订单通过区域查询代理返回"+JSON.toJSONString(agentPositionList));
			AgentVO agentVO = null;
			if(agentPositionList.size() > 0 ){
				for(AgentVO m : agentPositionList){
					if(m.getAgentLevelId() == 2){//县级代理
						agentVO = m;
					}
				}
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS,agentVO);
		} catch (Exception e) {
			logger.error("通过区域查询代理失败", e);
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}

	}
	
	
}
