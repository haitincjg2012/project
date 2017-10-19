/**  
 * @Title:  BankCardController.java   
 * @Package com.phshopping.api.controller.personal.bankcard   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月16日 下午6:06:34   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.personal.bankcard;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.other.bankcard.BankCardUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.BankCardBindInfoDTO;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IBankCodenameDataService;
import com.ph.shopping.facade.member.service.IMemberBankCardBindService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.vo.BankCodenameDataVO;
import com.ph.shopping.facade.member.vo.MemberBankCardBindInfoVO;
import com.phshopping.api.controller.dto.AppBankCardBindInfoDTO;
import com.phshopping.api.controller.dto.AppUnbindBankCardDTO;
import com.phshopping.api.controller.vo.BankCardBindVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;

/**   
 * @ClassName:  BankCardController   
 * @Description:银行卡相关操作   
 * @author: 李杰
 * @date:   2017年5月16日 下午6:06:34     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/personal/bank/")
public class BankCardController {

	private static final Logger logger = LoggerFactory.getLogger(BankCardController.class);
	
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberBankCardBindService bankCardBindService;
	
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberService memberService;
	
	@Reference(version = "1.0.0")
	private IBankCodenameDataService bankDataService;
	
	/**数据服务*/
	private final BankDataService dataService = new BankDataService();
	/**
	 * 
	 * @Title: getBankInfo   
	 * @Description: 获取 绑定的银行卡信息
	 * @param: @param request
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/getBankCardInfo/{token}", method = RequestMethod.GET)
	public Result getBankCardInfo(HttpServletRequest request, @PathVariable String token) {
		logger.info("获取绑定的银行卡信息 参数：token = " + token);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			result = bankCardBindService.selectBindMemberBankCardByUserId(memberInfo.getId());
			// 封装返回数据
			result.setData(dataService.getBankCardBindVO(result));
		}
		logger.info("获取绑定的银行卡信息返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: bindBanCard   
	 * @Description: 银行卡绑定   
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	public Result bindBankCard(HttpServletRequest request, AppBankCardBindInfoDTO dto) {
		logger.info("绑定银行卡处理参数：BankBindInfoDto = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			if (null == dto.getBankDataId()) {
				ResultUtil.setResult(result, RespCode.Code.REQUEST_DATA_ERROR);
				result.setMessage("卡数据ID不能为空");
				return result;
			}
			BankCardBindInfoDTO rdto = new BankCardBindInfoDTO();
			rdto.setUserId(memberInfo.getId());
			rdto.setBankCardNo(dto.getCardNum());
			rdto.setBindPhone(memberInfo.getTelPhone());
			rdto.setOwnerName(dto.getOwnName());
			rdto.setIdCardNo(memberInfo.getIdCardNo());
			rdto.setBankName(dto.getBankName());
			rdto.setLoginUserId(memberInfo.getId());
			rdto.setCreateIp(IPUtil.getIpAddress(request));
			rdto.setBankCodenameDataId(dto.getBankDataId());
			result = bankCardBindService.bindMemberBankCard(rdto);
		}
		logger.info("绑定银行卡返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: unbindBankCard   
	 * @Description:解绑银行卡   
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "/unbindBankCard", method = RequestMethod.POST)
	public Result unbindBankCard(HttpServletRequest request, AppUnbindBankCardDTO dto) {
		logger.info("解绑银行卡处理参数：BankBindInfoDto = {} ", JSON.toJSONString(dto));
		Result result = UserCacheUtil.getUser(dto.getToken());
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			// 解绑银行卡
			result = bankCardBindService.unBindByBindIdAndMemberId(dto.getBindCardId(), memberInfo.getId(),
					memberInfo.getId(), IPUtil.getIpAddress(request));
		}
		logger.info("解绑银行卡返回数据：Result = {} ", JSON.toJSONString(result));
		return result;
	}
	/**
	 * 
	 * @Title: getBanCardName   
	 * @Description: 根据银行卡卡号获取银行卡名称   
	 * @param: @param request
	 * @param: @param bankCardNo
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "/getBankName", method = RequestMethod.POST)
	public Result getBanCardName(HttpServletRequest request, String bankCardNo) {
		logger.info("根据银行卡卡号获取银行卡名称参数 bankCardNo：" + bankCardNo);
		Result result = ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
		/*JSONObject json = JSON.parseObject(bankCardNo);
		Object param = json.get("bankCardNo");
		bankCardNo = param == null ? null : param.toString();*/
		if (StringUtils.isNotBlank(bankCardNo)) {
			if (BankCardUtil.isNoexists(bankCardNo)) {
				return result;
			}
			String cardName = BankCardUtil.getBankName(bankCardNo);
			if (StringUtils.isNotBlank(cardName)) {
				String[] codeNames = cardName.split(",");
				BankCodenameDataVO vo = new BankCodenameDataVO();
				vo.setId(Long.valueOf(codeNames[0]));
				vo.setBankName(codeNames[1]);
				return ResultUtil.getResult(RespCode.Code.SUCCESS, vo);
			}
			final Result dresult = bankDataService.getBankCodenameDataDetailByCode(bankCardNo);
			logger.info("根据银行卡卡号获取银行卡名称返回数据：Result = {} ", JSON.toJSONString(dresult));
			boolean flag = false;
			if (dresult.isSuccess()) {
				Object obj = dresult.getData();
				if (flag = (obj instanceof BankCodenameDataVO)) {
					BankCodenameDataVO vo = (BankCodenameDataVO) obj;
					BankCardUtil.setCardName(bankCardNo, vo.getBankName(), vo.getId());
					return ResultUtil.getResult(RespCode.Code.SUCCESS, obj);
				}
			}
			if (!flag) {
				BankCardUtil.putNoexists(bankCardNo);
			}
		}
		return result;
	}
	/**
	 * 
	 * @ClassName:  BankDataService   
	 * @Description:银行卡相关数据服务  
	 * @author: 李杰
	 * @date:   2017年6月19日 下午4:59:43     
	 * @Copyright: 2017
	 */
	protected class BankDataService{
		/**
		 * 
		 * @Title: getBankCardBindVO   
		 * @Description: 封装银行卡绑定的数据   
		 * @param: @param result
		 * @param: @return      
		 * @return: BankCardBindVO
		 * @author：李杰      
		 * @throws
		 */
		public BankCardBindVO getBankCardBindVO(Result result){
			if(null != result && result.isSuccess()){
				Object obj = result.getData();
				if(obj instanceof MemberBankCardBindInfoVO){
					BankCardBindVO bv = new BankCardBindVO();
					BeanUtils.copyProperties(obj, bv);
					return bv;
				}
			}
			return null;
		}
		
	}
}
