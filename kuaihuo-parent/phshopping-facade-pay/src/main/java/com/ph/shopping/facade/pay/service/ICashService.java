package com.ph.shopping.facade.pay.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.dto.AlipayRefundDTO;
import com.ph.shopping.facade.pay.dto.DefrayDTO;
import com.ph.shopping.facade.pay.dto.HunterDefrayDTO;

import java.util.Map;

/**
 * @项目：phshopping-facade-pay
 * @描述：提现接口
 * @作者： Mr.chang
 * @创建时间：2017年4月6日
 * @Copyright @2017 by Mr.chang
 */
public interface ICashService {


	/**
	 * 提现接口
	 * @return
	 * @author Mr.Chang
	 */
	public Result defray(DefrayDTO defrayDTO);

	/**
	 * 代付回调
	 * @param encReq
	 * @return
	 * @author Mr.Chang
	 */
	public Result defayCallBack(String encReq);

	/**
	 * 支付宝退款接口
	 * @param ard
	 * @return
	 */
	public Result alipayRefund(AlipayRefundDTO ard);


	/**
	 * @methodname hunterDefray 的描述：批发商提现，不通过审核，直接提现
	 * @param hunterDefrayDTO
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/7/11
	 */
	Result hunterDefray(HunterDefrayDTO hunterDefrayDTO);


    String verifySingNotifyForYPay(Map<String, String> map);

}
