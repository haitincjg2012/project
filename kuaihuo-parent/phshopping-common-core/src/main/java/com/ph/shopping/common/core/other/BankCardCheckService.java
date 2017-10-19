package com.ph.shopping.common.core.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.constant.CheckConstant;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.other.bankcard.CheckBankCardUtil;
import com.ph.shopping.common.util.other.bankcard.request.CheckBankCardData;
import com.ph.shopping.common.util.other.bankcard.response.CheckBankResponse;
/**
 * 
 * @ClassName:  BankCardCheckService   
 * @Description:银行卡校验服务   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:38:31     
 * @Copyright: 2017
 */
public class BankCardCheckService {

	private static final Logger log = LoggerFactory.getLogger(BankCardCheckService.class);
	
	/**
	 * 银行卡认证地址
	 */
	@Value("${bank.card.auth.url}")
	private String bankCardCheckUrl;
	
	/**
	 * 
	 * @Title: bankCardAuth   
	 * @Description: 银行卡校验   
	 * @param: @param cardNum 身份证号
	 * @param: @param bankCardNo 银行卡号
	 * @param: @param name 姓名
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean bankCardAuth(String cardNum, String bankCardNo, String name) {
		boolean flag = false;
		CheckBankCardData request = new CheckBankCardData();
		request.setCardNum(cardNum);
		request.setBankCardNo(bankCardNo);
		request.setCheckUrl(bankCardCheckUrl);
		request.setName(name);

		String[] fields = { "name", "cardNum", "bankCardNo", "checkUrl" };
		if (!ParamVerifyUtil.entityIsNotNullByField(request, fields)) {
			log.warn("银行卡校验 参数不全");
			return flag;
		}
		CheckBankResponse response = CheckBankCardUtil.bankCardCheck(request);
		log.info("校验身份证信息 返回数 CheckBankResponse = {}", JSON.toJSONString(response));
		flag = getResultByResponse(response);
		return flag;
	}
	/**
	 * 
	 * @Title: getResultByResponse   
	 * @Description: 处理返回结果   
	 * @param: @param response
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private boolean getResultByResponse(CheckBankResponse response) {
		boolean flag = false;
		if (response != null) {
			String code = response.getCode();
			flag = (CheckConstant.LOCALCHECK_BANK_SUCCESS.equals(code)
					|| CheckConstant.REMOTECHECK_BANK_SUCCESS.equals(code));
		}
		return flag;
	}
}
