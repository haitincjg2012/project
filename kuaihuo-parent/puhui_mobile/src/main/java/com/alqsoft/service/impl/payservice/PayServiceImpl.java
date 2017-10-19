package com.alqsoft.service.impl.payservice;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.rpc.mobile.RpcZhiFuService;
import com.alqsoft.service.payservice.PayService;


@Service
@Transactional(readOnly = true)
public class PayServiceImpl implements PayService{
	
	@Autowired
	private RpcZhiFuService rpcZhiFuService;
	@Override
	public Result sendCodeUnifiedOrder(String orderids, Integer type, String ip) {
		if(orderids==null){
			return ResultUtils.returnError("订单号为空");
		}
		if(type!=0&&type!=1&&type!=2){
			return ResultUtils.returnError("订单支付类型异常");
		}
		if(StringUtils.isBlank(ip)){
			return ResultUtils.returnError("ip不能为空");
		}
		return rpcZhiFuService.sendCodeUnifiedOrder(orderids, type, ip);
	}

}
