/**  
 * @Title:  ShareController.java   
 * @Package com.phshopping.api.controller.personal.share   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午7:45:42   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.phshopping.api.controller.personal.share;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.token.SignUtils;
import org.alqframework.pay.weixin.util.MD5Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.standard.processor.StandardInliningCDATASectionProcessor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.config.properties.WebProperties;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberAddShareDTO;
import com.ph.shopping.facade.member.dto.MemberShareRecordDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.vo.MemberShareRecordVO;
import com.phshopping.api.appenum.AppShareTypeEnum;
import com.phshopping.api.controller.dto.AppMemberShareDTO;
import com.phshopping.api.controller.dto.AppShareIndexDTO;
import com.phshopping.api.controller.vo.ShareMmberVO;
import com.phshopping.api.controller.vo.ShareRecordVO;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.exception.WebError;
import com.phshopping.api.uitl.UAgentInfo;
import com.phshopping.api.uitl.UserCacheUtil;

/**
 * @ClassName: ShareController
 * @Description:
 * @author: 李杰
 * @date: 2017年7月26日 下午7:45:42
 * @Copyright: 2017
 */
@Controller
@RequestMapping("api/share")
public class ShareController {

	private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberService memberService;

	// 短信接口
	@Autowired
	private ISmsDataService smsDataService;

	@Autowired
	private WebProperties webProerties;
	
	@Autowired
	private SmsUtil ssmUtil;

	/**
	 * 
	 * @Title: index @Description: 加载分享页面 @param: @param request @param: @param
	 *         dto @param: @return @return: String @author：李杰 @throws
	 */
	@RequestMapping(value = "shareIndex", method = RequestMethod.GET)
	public String index(HttpServletRequest request, AppShareIndexDTO dto, Model model) {
		logger.info("会员分享处理参数：AppShareIndexDTO = {} ", JSON.toJSONString(dto));
		try {
			final String telPhone = dto.getTelPhone();
			final Byte type = dto.getType();
			// 根据手机号校验会员是否存在
			Member member = getMemberInfo(telPhone);
			if (null != member) {
				ShareMmberVO vo = new ShareMmberVO();
				vo.setMemberId(member.getId());
				vo.setTelPhone(member.getTelPhone());
				vo.setType(type);
				vo.setShowTelPhone(formatTelPhone(member.getTelPhone()));
				model.addAttribute("member", vo);
				model.addAttribute("token", UUID.randomUUID().toString());
				logger.info(vo.getMemberId()+"11111111111111111111111111");
				UAgentInfo ua = getMobileOS(request);
				logger.info("detectIos = " + ua.detectIos() + " detectAndroid " + ua.detectAndroid());

				String time =UtilDate.getOrderNum();
				String timestamp[] = {time};
				String clint_type[] = {"A"};
				String url1[] = {"/api/sms/sendsharecode/"};
				Map<String, String[]> map = new HashMap<String, String[]>();
				map.put("timestamp",timestamp);
				map.put("client_type",clint_type);
				map.put("url", url1);
				String signData = SignUtils.mapToLinkString2(map);
				signData = StringEscapeUtils.unescapeXml(signData);
				String sign = "";
				String md5 = MD5Util.MD5Encode(signData,"utf-8")+"KHHYA";
				byte[] b = null;
				try {
					b = (md5).getBytes("utf-8");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(signData+":签名加密异常");
				}
				if (b != null) {
					sign = Base64Utils.encodeToString(b);
				}
				model.addAttribute("timestamp", time);
				model.addAttribute("sign", sign);

				if (AppShareTypeEnum.MEMBER.isMatching(type)) {
					String url = "";
					if (ua.detectIos()) {
						url = webProerties.getIosMemberAppUploadUrl();
					} else if (ua.detectAndroid()) {
						url = webProerties.getAndroidMemberAppUploadUrl();
					}
					// 跳转分享会员页面
					model.addAttribute("url", url);
					return "share/memberApp";
				} else if (AppShareTypeEnum.MERCHANT.isMatching(type)) {
					String url = "";
					if (ua.detectIos()) {
						url = webProerties.getIosMerchantAppUploadUrl();
					} else if (ua.detectAndroid()) {
						url = webProerties.getAndroidMerchantAppUploadUrl();
					}
					// 跳转分享商户页面
					model.addAttribute("url", url);
					return "share/shoppingApp";
				}
			}
		} catch (Exception e) {
			logger.error("加载分享页面错误");
			throw new WebError(e, e.getMessage());
		}
		logger.warn("加载分享页面 没有查询到会员数据或请求类型不匹配");
		return "error/404";
	}

	private UAgentInfo getMobileOS(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		String httpAccept = request.getHeader("Accept");
		return new UAgentInfo(userAgent, httpAccept);
	}

	/**
	 * 
	 * @Title: memberShare @Description:
	 *         会员分享(此处使用被分享人作为token的key就是未了防止同一个手机号被并发使用) @param: @param
	 *         request @param: @param dto @param: @return @return:
	 *         Result @author：李杰 @throws
	 */
	@Token(key = "toTelPhone")
	@RequestMapping(value = "memberShare", method = RequestMethod.POST)
	public @ResponseBody Result memberShare(HttpServletRequest request, @RequestBody AppMemberShareDTO dto) {
		logger.info("会员分享处理参数：AppMemberShareDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		String errorStr = dto.validateForm();
		if (StringUtils.isNotBlank(errorStr)) {
			result.setMessage(errorStr);
			return result;
		}

		if (dto.getTelPhone().substring(0,11).equals(dto.getToTelPhone())){
			result.setMessage("分享用户不能为自己");
			return result;
		}
		/*
		 * // 判断该手机号是否已经注册 Member
		 * member=iMemberService.findMemberByMobile(dto.getTelPhone());
		 * if(member!=null){ result.setCode("300");
		 * result.setMessage("该手机号已经注册了"); return result; }
		 */
		// 校验验证码
		CheckDTO checkDTO=new CheckDTO();
		checkDTO.setCode(dto.getSmsCode());
		checkDTO.setCodeType("Fr170001");
		checkDTO.setPhone(dto.getToTelPhone());
		Result result1 = ssmUtil.check(checkDTO);
		if (!"1".equals(result1.getCode())) {
			
			return ResultUtil.setResult(false,result1.getMessage());
		}
		// 执行新增分享数据
		MemberAddShareDTO rdto = new MemberAddShareDTO();
		rdto.setCreateIp(IPUtil.getIpAddress(request));
		rdto.setTelPhone(dto.getTelPhone());
		rdto.setToTelPhone(dto.getToTelPhone());
		rdto.setType(dto.getType());
		rdto.setUserId(dto.getUserId());
		rdto.setPassword(dto.getPassword());
		result = memberService.addMemberShare(rdto);
		logger.info("会员分享返回数据：result = {} ", JSON.toJSONString(result));
		return result;
	}

	/**
	 * 
	 * @Title: verifySmsCode @Description:校验短信验证码 @param: @param
	 *         mobile @param: @param smsCode @param: @return @return:
	 *         boolean @author：李杰 @throws
	 */
	private Result verifySmsCode(String mobile, String smsCode, SmsCodeType type) {
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(mobile);
		sendData.setSmsCode(smsCode);
		sendData.setSourceEnum(SmsSourceEnum.MEMBER);
		sendData.setType(type);
		Result result = smsDataService.checkSmsCodeByMobile(sendData);
		logger.info("校验短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}

	public static String formatTelPhone(String telPhone) {
		if (StringUtils.isNotBlank(telPhone)) {
			int len = telPhone.length();
			telPhone = telPhone.substring(0, 3) + "****" + telPhone.substring(len - 4, len);
		}
		return telPhone;
	}

	/**
	 * 
	 * @Title: getMemberInfo @Description: 查询会员对象 @param: @param
	 *         telPhone @param: @return @return: Member @author：李杰 @throws
	 */
	private Member getMemberInfo(String telPhone) {
		Result mresult = memberService.getMemberInfoByMobile(telPhone);
		if (mresult.isSuccess()) {
			Object data = mresult.getData();
			if (data instanceof Member) {
				return (Member) data;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getShareRecord @Description: 查询分享列表 @param: @param
	 *         request @param: @param token @param: @param
	 *         type @param: @return @return: Result @author：李杰 @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "getShareList/{token}/{type}/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public @ResponseBody Result getShareRecord(HttpServletRequest request, @PathVariable String token,
			@PathVariable Byte type, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
		logger.info("查询分享列表请求参数：token = " + token + " type = " + type);
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			MemberShareRecordDTO dto = new MemberShareRecordDTO();
			dto.setMemberId(memberInfo.getId());
			dto.setType(type);
			PageBean page = new PageBean();
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			dto.setPage(page);
			result = memberService.getMemberShareByPage(dto);
			if (result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof List) {
					result.setData(getShareRecords((List) obj));
				}
			}
		}
		logger.info("查询分享列表返回数据 Result = {} ", JSON.toJSONString(result));
		return result;
	}

	private List<ShareRecordVO> getShareRecords(List<MemberShareRecordVO> list) {
		List<ShareRecordVO> result = new LinkedList<ShareRecordVO>();
		if (null != list && !list.isEmpty()) {
			for (MemberShareRecordVO v : list) {
				ShareRecordVO vo = new ShareRecordVO();
				vo.setCreateTime(v.getCreateTime());
				vo.setTelPhone(formatTelPhone(v.getToTelPhone()));
				vo.setUserName(v.getUserName());
				result.add(vo);
			}
		}
		return result;
	}

	// 测试页面跳转
	@RequestMapping(value = "memberapp", method = RequestMethod.GET)
	public String shartMember(Model model) {
		// TODO
		final Byte type = 0;
		Member member = getMemberInfo("15237127751");
		ShareMmberVO vo = new ShareMmberVO();
		vo.setMemberId(member.getId());
		vo.setTelPhone(member.getTelPhone());
		vo.setType(type);
		vo.setShowTelPhone(formatTelPhone(member.getTelPhone()));
		model.addAttribute("member", vo);
		model.addAttribute("token", UUID.randomUUID().toString());

		String time = UtilDate.getOrderNum();
		String timestamp[] = {time};
		String clint_type[] = {"A"};
		String url1[] = {"/api/sms/sendsharecode/"};
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("timestamp",timestamp);
		map.put("client_type",clint_type);
		map.put("url", url1);
		String signData = SignUtils.mapToLinkString2(map);
		signData = StringEscapeUtils.unescapeXml(signData);
		logger.info(signData+"++++++++++++++++++++++");
		String sign = "";
		String md5 = MD5Util.MD5Encode(signData,"utf-8")+"KHHYA";
		byte[] b = null;
		try {
			b = (md5).getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(signData+":签名加密异常");
		}
		if (b != null) {
			sign = Base64Utils.encodeToString(b);
		}
		model.addAttribute("timestamp", time);
		model.addAttribute("sign", sign);
		// UAgentInfo ua = getMobileOS(request);
		// logger.info("detectIos = " + ua.detectIos() + " detectAndroid " +
		// ua.detectAndroid());
		/*
		 * if (AppShareTypeEnum.MEMBER.isMatching(type)) { String url = ""; if
		 * (ua.detectIos()) { url = webProerties.getIosMemberAppUploadUrl(); }
		 * else if (ua.detectAndroid()) { url =
		 * webProerties.getAndroidMemberAppUploadUrl(); }
		 */
		String url = "www.baidu.com";
		// 跳转分享会员页面
		model.addAttribute("url", url);
		return "share/shoppingApp";
	}

	@RequestMapping(value = "shopingapp", method = RequestMethod.GET)
	public String shartShopingApp(Model model) {
		String phone = "17600685458";
		String phoneNumber = formatTelPhone(phone);
		model.addAttribute("showTelPhone", phoneNumber);
		return "share/shoppingApp";
	}

	@ResponseBody
	@RequestMapping(value = "hideorshow",method = RequestMethod.GET)
	public String hideorshow(String telPhone){
		Member memberByMobile = memberService.findMemberByMobile(telPhone);
		if (memberByMobile!=null){
			return "1";
		}
		return "2";
	}

}
