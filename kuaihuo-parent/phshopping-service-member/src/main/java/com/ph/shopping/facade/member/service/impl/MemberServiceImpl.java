/**
 * 
 */
package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.OuterResultEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.customenum.StatusCodeEnums;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.smsCode.SmsCodeUtil;
import com.ph.shopping.common.util.token.TokenUtil;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.member.dto.*;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.entity.MemberPromotion;
import com.ph.shopping.facade.member.entity.MemberShareRecord;
import com.ph.shopping.facade.member.entity.ProfitOptLog;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificationEnum;
import com.ph.shopping.facade.member.menum.member.*;
import com.ph.shopping.facade.member.menum.memberscore.MemberScoreFrozenEnum;
import com.ph.shopping.facade.member.menum.profitopt.ProfitOptLogIsProfitEnum;
import com.ph.shopping.facade.member.menum.profitopt.ProfitOptLogUseTypeEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionAccountTypeEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionDeleteEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionStatusEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.user.LoginRegisterCheckService;
import com.ph.shopping.facade.member.service.user.response.CheckResponse;
import com.ph.shopping.facade.member.vo.MemberPromotionVO;
import com.ph.shopping.facade.member.vo.MemberShareRecordVO;
import com.ph.shopping.facade.member.vo.MemberVO;
import com.ph.shopping.util.APIHttpClient;
import com.ph.shopping.util.DubboResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: MemberService
 * @Description: 会员操作接口实现
 * @author liuy
 * @date 2017年5月16日 下午2:15:42
 */
@Component
@Service(version = "1.0.0")
public class MemberServiceImpl implements IMemberService {

	// 日志
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	private static final OuterResultEnum result = null;

	// 短信发送接口
	@Autowired
	private ISmsDataService smsDataService;

	// 登录注册校验服务
	@Autowired
	private LoginRegisterCheckService checkService;

	// 会员Mapper
	@Autowired
	private MemberMapper memberMapper;

	// 会员积分Mapper
	@Autowired
	MemberIntegralMapper memberIntegralMapper;

	// 推广师MApper
	@Autowired
	MemberPromotionMapper memberPromotionMapper;

	// 影响分润操作流水表Mapper
	@Autowired
	ProfitOptLogMapper profitOptLogMapper;

	// 会员分享mapper
	@Autowired
	private MemberShareRecordMapper shareRecordMapper;

	@Override
	public Result getMemberListByPage(MemberDTO memberDto) {
		log.info("分页获取会员列表  参数,MemberDTO ={} ", JSON.toJSONString(memberDto));
		PageHelper.startPage(memberDto.getPageNum(), memberDto.getPageSize());
		List<MemberVO> list = memberMapper.selectMemberListByPage(memberDto);
		PageInfo<MemberVO> pageInfo = new PageInfo<MemberVO>(list);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	@Override
	public Result getMemberList(MemberDTO dto) {
		log.info("获取会员列表(不分页) 参数,MemberDTO = {} ", JSON.toJSONString(dto));
		// 1.开始查询
		List<Member> list = memberMapper.selectMemberList(dto);
		// 2.判断查询结果是否为空，返回相应数据
		return ResultUtil.getResult(RespCode.Code.SUCCESS, list, list == null ? 0 : list.size());
	}

	@Override
	public Result getMemberInfoByMobile(String mobile) {
		log.info("根据手机号查询会员信息  参数,mobile : " + mobile);
		// 1.验证会员手机号是否为空
		if (StringUtils.isBlank(mobile)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[会员手机号]不能为空");
		}
		// 2.开始查询
		Member member = memberMapper.selectMemberInfoByMobile(mobile);
		member.setMemberName(member.getMemberName()==null?"":member.getMemberName());
		member.setIdCardNo(member.getIdCardNo()==null?"":member.getIdCardNo());
		member.setHeadImage(member.getHeadImage()==null?"":member.getHeadImage());
		member.setNikeName(member.getNikeName()==null?"":member.getNikeName());
		// 3.判断查询结果是否为空，返回相应数据
		if (member != null && StringUtils.isNotBlank(member.getTelPhone())) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
		} else {
			return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
		}
	}

	@Override
	public Result getMemberInfoById(Long id) {
		log.info("根据Id查询会员信息  参数,id = : " + id);
		// 1.验证id参数是否为空
		if (id == null) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[会员id]不能为空");
		}
		// 2.开始查询
		Member member = memberMapper.selectByPrimaryKey(id);
		// 3.判断查询结果是否为空，返回相应数据
		if (member != null && StringUtils.isNotBlank(member.getTelPhone())) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
		} else {
			return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
		}
	}

	@Override
	@Transactional
	public Result addMember(MemberAddDTO dto) {
		log.info("新增会员 参数,MemberAddDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证手机号、创建人参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.封装需要新增的会员基础数据
			final String pwd = SmsCodeUtil.getMemberPwdCode();// 随机密码 字母开头+5位数字
			Member member = new Member();
			member.setMemberName(dto.getMemberName()); // 会员名称
			member.setTelPhone(dto.getTelPhone()); // 电话
			member.setMemberPwd(pwd); // 密码
			member.setCreaterId(dto.getCreaterId());// ----这里要改为dto获取
			member.setType(MemberTypeEnum.MEMBER_TYPE.getCode());// 默认为本地新增的会员，会员类型为：0会员

			// 3.判断北京方是否已存在会员数据，存在则在本地新增一条会员数据（商户线下订单新需求20170628，之前不向本地新增）
			CheckResponse response = checkService.registerCheck(dto.getTelPhone());
			log.info("调用北京注册校验接口返回结果 ：" + response);
			if (response != null && OuterResultEnum.SUCCESS.getCode().equals(response.getCode())) {
				// 如果北京验证成功，设置会员密码为空！！！
				member.setMemberPwd(null);
				member.setType(MemberTypeEnum.HUNTER_TYPE.getCode());// 从北京方验证的，会员类型为：1批发商
			}
			// 4.向会员表、积分表新增一条数据
			Result insertMermberResult = this.insertMermberInfo(member);// 验证会员存在，新增会员表，新增积分表
			if (!RespCode.Code.SUCCESS.getCode().equals(insertMermberResult.getCode())) {
				return insertMermberResult;
			}
			// 6.发送密码短信（密码不为空，即非北京方会员存在的情况，才发送短信）
			if (StringUtils.isNotBlank(member.getMemberPwd())) {
				SmsSendData sendData = new SmsSendData();
				sendData.setMobile(dto.getTelPhone());
				sendData.setType(SmsCodeType.MEMBER_REGISTER_PD);// 会员新增
				sendData.setSourceEnum(SmsSourceEnum.MEMBER);// 会员
				sendData.setSmsCode(pwd);// 随机密码
				Result smsResult = smsDataService.sendSmsHaveCode(sendData);
				if (smsResult == null || !RespCode.Code.SUCCESS.getCode().equals(smsResult.getCode())) {
					log.info("给用户发送短信密码失败:" + smsResult);
					throw new MemberException("给用户发送短信密码失败");
				}
			}
			return insertMermberResult;
		} catch (BizException e) {
			log.error("调用北京注册校验接口失败", e);
			throw new MemberException("系统繁忙，请重试");
		} catch (Exception e) {
			log.error("添加会员信息错误", e);
			throw new MemberException("添加会员信息错误");
		}
	}

	@Override
	@Transactional
	public Result memberFrozen(MemberDTO dto) {
		log.info("会员冻结参数,MemberDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证会员Id参数是否为空
			if (dto.getId() == null) {
				return new Result(false, "会员Id不能为空", null);
			}
			// 2.开始冻结会员
			Member member = new Member();
			member.setId(dto.getId());
			member.setUpdateTime(new Date());
			member.setStatus(MemberStatusEnum.MEMBER_FROZEN.getCode());
			member.setTelPhone(null);// 将入参的手机号置空，防止传入手机号被修改
			member.setUpdaterId(dto.getUpdaterId());
			memberMapper.updateByPrimaryKeySelective(member);
			member = this.memberMapper.selectByPrimaryKey(member.getId());
			// 3.如果是推广师，向会员分润权限流水表中新增一条记录
			if (member.getIsMarketing() != null
					&& member.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())) {
				Byte useType = ProfitOptLogUseTypeEnum.FROZEN.getCode();// 操作类型
																		// 冻结
				dto.setStatus(member.getStatus());
				dto.setIsMarketing(member.getIsMarketing());
				dto.setId(member.getId());
				ProfitOptLog profitOptLogSave = this.setProfitOptLogData(dto, useType);
				if (profitOptLogSave != null) {
					profitOptLogMapper.insertUseGeneratedKeys(profitOptLogSave);
				}
			}

			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error("会员冻结错误", e);
			throw new MemberException("会员冻结错误");
		}
	}

	@Override
	@Transactional
	public Result memberRelievefrozen(MemberDTO dto) {
		log.info("会员解冻参数,MemberDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证会员Id参数是否为空
			if (dto.getId() == null) {
				return new Result(false, "会员Id不能为空", null);
			}
			// 2.开始解冻会员
			Member member = new Member();
			member.setId(dto.getId());
			member.setUpdateTime(new Date());
			member.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());
			member.setTelPhone(null);// 将入参的手机号置空，防止传入手机号被修改
			member.setUpdaterId(dto.getUpdaterId());
			memberMapper.updateByPrimaryKeySelective(member);
			// 3.向会员分润权限流水表中新增一条记录
			if (member.getIsMarketing() != null
					&& member.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())) {
				Byte useType = ProfitOptLogUseTypeEnum.UNFROZEN.getCode();// 操作类型
																			// 解冻
				dto.setStatus(member.getStatus());
				dto.setIsMarketing(member.getIsMarketing());
				dto.setId(member.getId());
				ProfitOptLog profitOptLogSave = this.setProfitOptLogData(dto, useType);
				if (profitOptLogSave != null) {
					profitOptLogMapper.insertUseGeneratedKeys(profitOptLogSave);
				}
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error("会员解除冻结错误", e);
			throw new MemberException("会员解除冻结错误");
		}
	}

	@Override
	public Result memberUpgradePromotion(MemberPromotionDTO dto) {
		log.info("会员升级为推广师参数,PromotionRecordDTO = {} ", JSON.toJSONString(dto));
		try {
			boolean flag = false; // 会员已经绑定推广师，但是推广师审核状态为非未通过（待审核、审核通过）；
			// 1.验证图片参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.验证会员是否实名认证
			Member member = memberMapper.selectByPrimaryKey(dto.getMemberId());
			if (member != null && member.getCertification() != null
					&& !member.getCertification().equals(MemberCertificationEnum.VERIFIED.getCode())) {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_CERTIFICATE_NON);
			}
			// 3.判断会员推广师审核状态 （ a.待审核、审核通过 ; b.未通过 ）
			MemberPromotionDTO memberAuthDTOById = new MemberPromotionDTO();
			memberAuthDTOById.setMemberId(dto.getMemberId());
			final MemberPromotionVO auth = memberPromotionMapper.getMemberAuthInfoByCondition(memberAuthDTOById);
			if (null != auth) {
				// a.状态为待审核、审核通过，返回"该会员已申请了推广师"
				if (!PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(auth.getStatus())) {
					log.warn("该会员已申请了推广师");
					return ResultUtil.getResult(MemberResultEnum.MEMBERAUTH_EXIST);
				} else {// b.状态为审核未通过，flag = true，用于执行修改操作
					flag = true;
				}
			}
			// 4.新增（不存在推广师）或修改（存在推广师但审核状态为未通过）推广师表
			// 注意这里不会去操作会员表是否推广师字段，要审核的时候才操作
			MemberPromotion memberAuth = new MemberPromotion();
			memberAuth.setMemberId(dto.getMemberId());
			memberAuth.setUrl(dto.getUrl());
			memberAuth.setAccountType(PromotionAccountTypeEnum.PROMOTION_PLATFORM.getCode());// 账号类型
																								// 平台添加
			memberAuth.setAccount(dto.getAccount());
			memberAuth.setStatus(PromotionStatusEnum.PROMOTION_AUDIT.getCode());// 待审核状态
			memberAuth.setUpdateTime(new Date());
			memberAuth.setIsDelete(PromotionDeleteEnum.PROMOTION_NORMAL.getCode());// 未删除，即正常状态
			if (flag) {// 修改操作
				memberAuth.setId(auth.getId());
				memberAuth.setUpdaterId(dto.getCreaterId());
				memberPromotionMapper.updateByPrimaryKeySelective(memberAuth);
			} else {// 新增操作
				memberAuth.setCreaterId(dto.getCreaterId());
				memberAuth.setCreateTime(new Date());
				memberPromotionMapper.insert(memberAuth);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, memberAuth);
		} catch (Exception e) {
			log.error("会员升级为推广师错误", e);
			throw new MemberException("会员升级为推广师错误");
		}
	}

	/******************* 以下为会员注册、登录验证、校验会员是否存在、找回密码相关接口 ********************/
	
	/**
	  * java生成随机数字和字母组合10位数
	  * @param
	  * @return
	  */
	 public static String getRandomNickname(int length) {
	  String val = "";
	  Random random = new Random();
	  for (int i = 0; i < length; i++) {
	   // 输出字母还是数字
	   String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
	   // 字符串
	   if ("char".equalsIgnoreCase(charOrNum)) {
	    // 取得大写字母还是小写字母
	    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
	    val += (char) (choice + random.nextInt(26));
	   } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
	    val += String.valueOf(random.nextInt(10));
	   }
	  }
	  return val;
	 }
	
	// TODO
	@Override
	@Transactional
	public Result registerMember(MemberRegisterDTO dto) {
		log.info("注册会员参数,RegisterMemberDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证手机号参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}

			// 2.封装需要新增的会员基础数据
			//	2.1密码验证
			String memberPwd = dto.getMemberPwd();
			if (6 > memberPwd.length() || memberPwd.length() > 16) {
				return new Result(false, RespCode.Code.PWD_ERROR.getCode(), errorStr);
			}
			//2.2过滤特殊字符
	        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
	        Pattern p2 = Pattern.compile(regEx);
	        Matcher m2 = p2.matcher(memberPwd);
	        boolean find = m2.find();
	        m2.replaceAll("").trim();
	        if(find){
	        	return new Result(false, RespCode.Code.PWD_ERROR.getCode(), errorStr);
	        }
	        //2.3过滤表情符号
	        memberPwd.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		    //2.4用户添加昵称字段生成随机数字和字母组合10位数
	        //System.out.println("java生成随机数字和字母组合10位数：" + getRandomNickname(10));
	        String nikeName = getRandomNickname(10);
	        
			Member meber = new Member();
			meber.setTelPhone(dto.getTelPhone());
			meber.setMemberPwd(memberPwd);
			meber.setNikeName(nikeName);
			meber.setCreaterId(0l);// ----平台添加
			meber.setEquipmentId(dto.getEquipmentId());
			meber.setType(MemberTypeEnum.MEMBER_TYPE.getCode());// 默认为本地新增的会员，会员类型为：0会员
			// 3.判断北京方是否已存在会员数据，存在则直接返回成功（登陆密码为北京方密码，登陆时会去同步北京方数据）
			/*
			 * CheckResponse response =
			 * checkService.registerCheck(dto.getTelPhone());
			 * log.info("调用北京注册校验接口返回结果 ：", response); if (response != null &&
			 * OuterResultEnum.SUCCESS.getCode().equals(response.getCode())) {
			 * //如果北京验证成功，设置会员密码为空！！！ meber.setMemberPwd(null);
			 * meber.setType(MemberTypeEnum.HUNTER_TYPE.getCode());//从北京方验证的，
			 * 会员类型为：1批发商 //return ResultUtil.getResult(RespCode.Code.SUCCESS); }
			 */
			// =============================================================================
			// 4.向会员表、积分表新增一条数据
			/*
			 * token的处理 phone=telPhone,origin=KF,memberType=1; 如果成功code为200
			 */
			// http://123.207.173.18:10088/swagger-ui.html
			//118.89.24.168
			String url1 = "http://123.207.173.18:10088/api/members/register?";
			String mobile = dto.getTelPhone();
			String origin = "KH";
			String memberType = "0";
			String url = url1 + "&phone=" + mobile + "&origin=" + origin + "&memberType=" + memberType;
			APIHttpClient ac = new APIHttpClient(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("phone", mobile);
			jsonObject.put("origin", origin);
			jsonObject.put("memberType", memberType);// 用户类型：0是普通会员，1是商户
			// System.out.println("数据："+jsonObject.toString());
			String cc = ac.contentTypeJsonPost(jsonObject.toString());
			// System.out.println("获取结果："+cc);
			JSONObject code = JSONObject.parseObject(cc);
			if (code.get("code").equals("200")) {
				String data = (String) code.get("data");
				meber.setTokenToMobile(data);
			} else {
				meber.setTokenToMobile(null);
			}
			// =============================================================================
			return insertMermberInfo(meber);// 验证会员存在，新增会员表，新增积分表
		} catch (BizException e) {
			log.error("调用北京注册校验接口失败", e);
			throw new MemberException("系统繁忙，请重试");
		} catch (Exception e) {
			log.error("注册会员信息错误", e);
			throw new MemberException("注册会员信息错误");
		}
	}

	@Override
	@Transactional
	public Result memberLogin(MemberLoginDTO dto) {
		log.info("会员登录参数,MemberLoginDTO = {} ", JSON.toJSONString(dto));

		boolean flag = false;// true:表示数据库中会员存在，且密码为空。(会员密码为空情况是,会员卡发卡时,如果发卡手机号本地不存在,但是在北京方存在,同步北京方数据，但不同步密码。)
		try {
			// 1.验证手机号和密码参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.在数据库查询会员信息
			// Member member =
			// memberMapper.selectMemberInfoByMobile(dto.getTelPhone());
			// 2.1登录MemberVO
			MemberVO memberVO = memberMapper.selectMemberInfoByMobileVO(dto.getTelPhone());
			// 判断会员是否存在
			if (null == memberVO) {
				return ResultUtil.getResult(MemberResultEnum.MEMBERUSER_NON_EXISTENT);
			}
			log.info("数据库查询会员返回结果,Member = {}", JSON.toJSONString(memberVO));
			// 3.会员在数据库存在
			if (memberVO != null) {
				// 1)会员状态为冻结
				if (MemberStatusEnum.MEMBER_FROZEN.getCode().equals(memberVO.getStatus())) {
					return ResultUtil.getResult(MemberResultEnum.MEMBER_FROZEN);
				}
				// 2)判断会员密码是否为空（为空的情况是会员发卡的时候）
				if (StringUtils.isNotBlank(memberVO.getTelPhone())) {
					flag = StringUtils.isBlank(memberVO.getMemberPwd());
					if (!flag) {
						// 3)会员密码不为空，判断数据库密码与输入的密码是否匹配
						if (memberVO.getMemberPwd().equals(MD5.getMD5Str(dto.getMemberPwd()))) {
							// 跟新设备ID
							updateEquipmentId(dto.getTelPhone(), dto.getEquipmentId());
							return ResultUtil.getResult(RespCode.Code.SUCCESS, memberVO);
						} else {
							return DubboResult.getResultByEnum(MemberResultEnum.MEMBER_PWD_MISMATCH);
						}
					}
				}
			}
			// 4.（会员在数据库不存在，或者会员在数据库存在但是密码为空），都要去北京方校验
			/*
			 * if (member == null || flag) { //去北京方进行登陆校验（手机号、页面输入的密码）
			 * CheckResponse response =
			 * checkService.loginCheck(dto.getTelPhone(), dto.getMemberPwd());
			 * log.info("调用北京登录校验接口返回结果 ：", response); if (response != null &&
			 * response.getCode() != null &&
			 * !OuterResultEnum.USER_NOEXISTS.getCode().equals(response.getCode(
			 * ))) { //北京方返回的Code为校验成功 if
			 * (OuterResultEnum.SUCCESS.getCode().equals(response.getCode())) {
			 * if (flag) {//a.如果本地数据库密码为空，则重新设置密码为输入的登录密码（必须校验成功才能重新设置密码）
			 * log.info("重新设置密码为输入的登录密码 ：", dto.getMemberPwd()); boolean
			 * updateFlag = resetMemberPwd(member, dto.getMemberPwd()); if
			 * (!updateFlag) { throw new MemberException("修改会员密码错误"); } //
			 * 跟新设备ID updateEquipmentId(dto.getTelPhone(),
			 * dto.getEquipmentId()); return
			 * ResultUtil.getResult(RespCode.Code.SUCCESS,member); } else
			 * {//b.如果本地数据库数据不存在，将北京方返回的会员数据新增到本地数据库 Content content =
			 * response.getContent(); if (content != null &&
			 * StringUtils.isNotBlank(content.getPhone())) { //向会员表、积分表新增一条数据
			 * Member meber = new Member(); meber.setCreateTime(new Date());
			 * meber.setTelPhone(content.getPhone());
			 * meber.setMemberName(content.getName());
			 * meber.setIdCardNo(content.getCard());
			 * meber.setMemberPwd(dto.getMemberPwd()); meber.setCreaterId(0l);
			 * meber.setType(MemberTypeEnum.HUNTER_TYPE.getCode());//从北京方验证的，
			 * 会员类型为：1批发商 Result result =
			 * insertMermberInfo(meber);//验证会员存在，新增会员表，新增积分表 if
			 * (result.isSuccess()) { // 跟新设备ID
			 * updateEquipmentId(dto.getTelPhone(), dto.getEquipmentId()); }
			 * return result; } else { throw new
			 * MemberException("新增会员数据同步返回错误"); } } } else if
			 * (OuterResultEnum.PWD_ERROR.getCode().equals(response.getCode()))
			 * { //北京方返回的Code为密码错误，则返回密码错误 return
			 * ResultUtil.getResult(MemberResultEnum.MEMBER_PWD_MISMATCH); }
			 * }else{ //去北京方校验失败，会员不存在（包括北京方和本地数据库） return
			 * ResultUtil.getResult(MemberResultEnum.MEMBER_USER_MISMATCH); } }
			 */
			/*
			 * } catch(BizException e){ log.error("调用北京登录校验接口失败", e); throw new
			 * MemberException("系统繁忙，请重试"); }
			 */ } catch (Exception e) {
			log.error("会员登录错误", e);
			throw new MemberException("会员登录错误");
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}

	@Override
	public Result verifyPhoneIsExists(String phone) {
		log.info("校验手机号是否存在参数 : " + phone);
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		}
		if (verifyPhone(phone)) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} else {
			return ResultUtil.getResult(MemberResultEnum.MEMBER_USER_MISMATCH);
		}
	}

	@Override
	@Transactional
	public Result backMmberPassword(MemberPasswordDTO dto) {
		log.info("会员找回密码参数,MemberPasswordDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证手机号、密码参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.根据手机号查询会员信息
			String mobile = dto.getTelPhone();
			Member member = memberMapper.selectMemberInfoByMobile(mobile);
			// 3.如果会员不存在，去北京方找
			if (member == null || StringUtils.isBlank(member.getTelPhone())) {
				CheckResponse response = checkService.registerCheck(mobile);
				log.info("调用北京注册校验接口返回结果 ：", response);
				// 如果北京方不存在，则返回会员不存在提示
				if (response == null || !OuterResultEnum.SUCCESS.getCode().equals(response.getCode())) {
					log.warn("重置密码会员不存在");
					return ResultUtil.getResult(MemberResultEnum.MEMBERUSER_NON_EXISTENT);
				}
				// 如果北京方存在，则同步会员数据到本地数据库
				Member memberAdd = new Member();
				memberAdd.setIdCardNo(response.getContent().getCard());
				memberAdd.setMemberName(response.getContent().getName());
				memberAdd.setTelPhone(response.getContent().getPhone());
				memberAdd.setMemberPwd(dto.getNewPassword());
				memberAdd.setCreaterId(0l);// ----平台添加
				memberAdd.setType(MemberTypeEnum.HUNTER_TYPE.getCode());// 从北京方验证的，会员类型为：1批发商
				this.insertMermberInfo(memberAdd);// 验证会员存在，新增会员表，新增积分表
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else if (MemberStatusEnum.MEMBER_FROZEN.getCode().equals(member.getStatus())) {
				// 会员存在，但会员是冻结状态
				return ResultUtil.getResult(MemberResultEnum.MEMBER_FROZEN);
			}
			// 4.重设会员密码
			boolean flag = resetMemberPwd(member, dto.getNewPassword());
			if (flag) {
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_FAILURE);
			}
		} catch (BizException e) {
			log.error("调用北京注册校验接口失败", e);
			throw new MemberException("系统繁忙，请重试");
		} catch (Exception e) {
			log.error("找回密码错误", e);
			throw new MemberException("找回密码错误");
		}
	}

	/*********************** 以下为会员头像（基本信息）修改、会员手机号修改、登录密码修改相关接口 ************************/

	@Override
	public Result updateMember(MemberUpdateDTO dto) {
		log.info("修改会员基本信息参数,dto = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证修改人是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.修改会员信息
			Member member = new Member();
			BeanUtils.copyProperties(dto, member);
			memberMapper.updateByPrimaryKeySelective(member);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error("修改会员基本信息错误", e);
			throw new MemberException("修改会员基本信息错误");
		}
	}

	@Override
	@Transactional
	public Result updateMemberPassword(MemberPasswordDTO dto) {
		log.info("会员修改登录密码参数,MemberPasswordDTO = {} ", JSON.toJSONString(dto));
		try {
			// 1.验证手机号、新密码参数是否为空
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), errorStr);
			}
			// 2.验证会员是否存在，并且状态是否为冻结，验证通过再修改密码
			Member member = memberMapper.selectMemberInfoByMobile(dto.getTelPhone());
			if (member == null || StringUtils.isBlank(member.getTelPhone())) {
				return ResultUtil.getResult(MemberResultEnum.MEMBERUSER_NON_EXISTENT);
			} else if (MemberStatusEnum.MEMBER_FROZEN.getCode().equals(member.getStatus())) {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_FROZEN);
			} else {
				String pwd = MD5.getMD5Str(dto.getNewPassword());
				member.setMemberPwd(pwd);
				member.setUpdateTime(new Date());
				member.setTelPhone(null);
				member.setUpdaterId(dto.getUpdaterId());
				memberMapper.updateByPrimaryKeySelective(member);
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
		} catch (Exception e) {
			log.error("修改密码错误", e);
			throw new MemberException("修改密码错误");
		}
	}

	@Override
	public Result getPromotionIsCanProfit(MemberDTO dto) {
		log.info("查询推广师（会员）是否可以分润  参数,MemberDTO={} " + JSON.toJSONString(dto));
		// 1.验证会员id是否为空
		if (dto.getId() == null) {
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		}
		// 2.查询会员是否存在，并且未冻结
		Member memberCopy = new Member();
		memberCopy.setId(dto.getId());
		memberCopy.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());// 会员为正常（没冻结）
		memberCopy.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode());// 是会员
		Member member = memberMapper.selectOne(memberCopy);
		// 判断查询结果是否为空，返回相应数据
		if (member != null) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
		} else {
			// 3.查询推广师是否存在，未冻结，审核通过
			dto.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());// 会员为正常（没冻结）
			dto.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());// 是推广师
			dto.setPromotionStatus(PromotionStatusEnum.PROMOTION_ADOPT.getCode());// 推广师审核状态为正常
			member = memberMapper.selectPromotionIsCanProfit(dto);
			// 判断查询结果是否为空，返回相应数据
			if (member != null) {
				return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
			} else {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
			}
		}
	}

	/*********************** 以下为北京方批发商登录注册专用接口 ************************/
	@Override
	@Transactional
	public Result getMemberInfoByMobileRegister(String mobile) {
		log.info("根据手机号查询会员信息（提供北京注册专用）  参数,mobile : " + mobile);
		// 1.验证会员手机号是否为空
		if (StringUtils.isBlank(mobile)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[会员手机号]不能为空");
		}
		// 2.开始查询
		Member member = memberMapper.selectMemberInfoByMobile(mobile);
		// 3.判断查询结果是否为空，返回相应数据
		if (member != null && StringUtils.isNotBlank(member.getTelPhone())) {
			if (StringUtils.isBlank(member.getToken())) {
				String token = TokenUtil.getToken();// 生成token值
				member.setToken(token);
				Member memberUp = new Member();
				memberUp.setId(member.getId());
				memberUp.setToken(token);
				memberMapper.updateByPrimaryKeySelective(memberUp);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, member.getToken());
		} else {
			member = new Member();
			String token = TokenUtil.getToken();// 生成token值
			member.setToken(token);
			member.setTelPhone(mobile);
			member.setCreaterId(0l);
			member.setType(MemberTypeEnum.HUNTER_TYPE.getCode());// 会员类型为：1批发商
			Result result = this.insertMermberInfo(member);
			if (result.isSuccess() && result.getData() != null) {
				Member retMember = (Member) result.getData();
				return ResultUtil.getResult(RespCode.Code.SUCCESS, retMember.getToken());
			}
		}
		return ResultUtil.getResult(MemberResultEnum.MEMBER_FAILURE);
	}

	@Override
	@Transactional
	public Result getMemberInfoByMobileUpToken(String mobile) {
		log.info("根据手机号查询会员信息并处理Token值  参数,mobile : " + mobile);
		// 1.验证会员手机号是否为空
		if (StringUtils.isBlank(mobile)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[会员手机号]不能为空");
		}
		// 2.开始查询
		Member member = memberMapper.selectMemberInfoByMobile(mobile);
		// 3.判断查询结果是否为空，返回相应数据
		if (member != null && StringUtils.isNotBlank(member.getTelPhone())) {
			if (StringUtils.isBlank(member.getToken())) {
				// 生成token值
				String token = TokenUtil.getToken();
				member.setToken(token);
				Member memberUp = new Member();
				memberUp.setId(member.getId());
				memberUp.setToken(token);
				memberMapper.updateByPrimaryKeySelective(memberUp);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
		} else {
			return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
		}
	}

	@Override
	public Result getMemberByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[token]不能为空");
		}
		Member mem = new Member();
		mem.setToken(token);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, memberMapper.selectOne(mem));
	}
	// ================================================私有通用方法======================================================/

	/**
	 * 
	 * @Title: insertMermberInfo
	 * @Description: 新增会员表、会员积分表记录
	 * @author liuy
	 * @date 2017年5月18日 下午2:18:06
	 * @param member
	 *            会员对象
	 * @return Result 返回类型
	 */
	private Result insertMermberInfo(Member member) {
		// 1.判断会员在数据库是否存在，已存在不能重复添加
		int isExist = memberMapper.selectMemberIsExistByPhone(member.getTelPhone());
		if (isExist > 0) {
			return ResultUtil.getResult(MemberResultEnum.MEMBER_EXISTS);
		}
		// TODO:判断会员是否被分享过
		MemberShareRecord record = new MemberShareRecord();
		record.setToTelPhone(member.getTelPhone());
		record.setType(MemberShareTypeEnum.SHARE_MEMBER.getCode());
		record = shareRecordMapper.selectOne(record);
		if (null != record && record.getUserId() != null) {
			member.setPromoterId(record.getUserId());
		}
		String pwd = member.getMemberPwd();// 会员密码
		// 2.封装会员数据
		member.setMemberName(member.getMemberName()); // 会员名称
		member.setTelPhone(member.getTelPhone()); // 电话
		if (StringUtils.isNotBlank(pwd)) {// 如果是北京方有会员，保存会员到本地，没有密码
			member.setMemberPwd(MD5.getMD5Str(pwd));// 密码全部在后台加密
		}
		member.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode()); // 会员正常状态
		member.setCertification(MemberCertificationEnum.NOT_CERTIFIED.getCode()); // 未认证
		member.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode()); // 默认为会员状态
		member.setIsFrozen((byte) 0); // 默认为会员状态
		member.setCreateTime(new Date());
		member.setUpdateTime(new Date());
		member.setCreaterId(member.getCreaterId());
		if (member.getLevel() == null) {
			member.setLevel(MemberLevelEnum.MEMBER_GENERAL.getCode()); // 普通会员
		}
		member.setType(member.getType());// 会员类型
		log.info("新增会员时的会员类型  type : " + member.getType());
		// 3.开始新增会员
		this.memberMapper.insertUseGeneratedKeys(member);
		// 4.向会员积分表新增数据
		MemberIntegralDTO dto = new MemberIntegralDTO();
		dto.setMemberId(member.getId());
		dto.setCreaterId(member.getCreaterId());
		dto.setStatus(MemberScoreFrozenEnum.MEMBER_SCORE_NORMAL.getCode());// 非冻结
		dto.setEnableScore(new BigDecimal(0));// 可用积分
		dto.setDrawcashScore(new BigDecimal(0));// 已提现积分
		dto.setStandbyScore(new BigDecimal(0));// 待用积分
		dto.setMemberProfitScore(new BigDecimal(0));// 会员分润积分
		dto.setMemberRewardScore(new BigDecimal(0));// 推荐会员奖励 积分
		dto.setMerchantRewardScore(new BigDecimal(0));// 推荐商户奖励积分
		memberIntegralMapper.insertMemberScore(dto);

		return ResultUtil.getResult(RespCode.Code.SUCCESS, member);
	}

	/**
	 * 
	 * @Title: resetMemberPwd
	 * @Description: 重设会员密码 （根据用户输入的新密码）
	 * @author liuy
	 * @date 2017年5月23日 下午2:16:35
	 * @param member
	 *            会员对象
	 * @param newPwd
	 *            新密码
	 * @return boolean 返回类型
	 */
	private boolean resetMemberPwd(Member member, String newPwd) {
		boolean flag = false;
		if (member != null && member.getId() != null) {
			Member inmember = new Member();
			String pwd = MD5.getMD5Str(newPwd);
			inmember.setMemberPwd(pwd);
			inmember.setUpdateTime(new Date());
			inmember.setId(member.getId());
			memberMapper.updateByPrimaryKeySelective(inmember);
			flag = true;
		}
		return flag;
	}

	/**
	 * @Title: verifyPhone
	 * @Description: 验证手机号是否存在 (本地数据库+北京方)
	 * @author liuy
	 * @date 2017年6月13日 下午3:00:06
	 * @param phone
	 * @return
	 */
	private boolean verifyPhone(String phone) {
		boolean flag = false;
		int num = memberMapper.selectMemberIsExistByPhone(phone);
		flag = num > 0;
		if (!flag) {
			CheckResponse response = checkService.registerCheck(phone);
			flag = (response != null && OuterResultEnum.SUCCESS.getCode().equals(response.getCode()));
		}
		return flag;
	}

	/**
	 * 
	 * @Title: setProfitOptLogData
	 * @Description: 封装ProfitOptLog影响分润操作流水实体基础数据
	 * @author liuy
	 * @date 2017年5月25日 下午2:17:14
	 * @param dto
	 *            会员DTO
	 * @param useType
	 *            操作类型
	 * @return ProfitOptLog
	 */
	private ProfitOptLog setProfitOptLogData(MemberDTO dto, Byte useType) {
		Byte isProfit = 0;

		ProfitOptLog profitOptLog = null;

		// 1.根据会员Id查询推广师认证记录
		MemberPromotionDTO memberPromotionDTO = new MemberPromotionDTO();
		memberPromotionDTO.setMemberId(dto.getId());
		MemberPromotionVO memberPromotionVO = memberPromotionMapper.getMemberAuthInfoByCondition(memberPromotionDTO);
		if (memberPromotionVO != null) {

			// 只有满足是推广师、会员表状态为正常（非冻结）、推广师审核状态为审核通过 三个条件，是否分润才可设置为可分润
			if (dto.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES)
					&& dto.getStatus().equals(MemberStatusEnum.MEMBER_NORMAL)
					&& memberPromotionVO.getStatus().equals(PromotionStatusEnum.PROMOTION_ADOPT)) {
				isProfit = ProfitOptLogIsProfitEnum.PROFIT_YES.getCode();
			}
			// 备注
			String remark = "";
			if (dto.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES)) {
				remark += "是否推广师 : " + MemberIsMarketingEnum.IS_MARKETING_BYYES.getRemark() + " ; ";
			} else {
				remark += "是否推广师  : " + MemberIsMarketingEnum.IS_MARKETING_BYNO.getRemark() + " ; ";
			}
			if (dto.getStatus().equals(MemberStatusEnum.MEMBER_NORMAL)) {
				remark += "会员状态 : " + MemberStatusEnum.MEMBER_NORMAL.getRemark() + " ; ";
			} else if (dto.getStatus().equals(MemberStatusEnum.MEMBER_FROZEN)) {
				remark += "会员状态 : " + MemberStatusEnum.MEMBER_FROZEN.getRemark() + " ; ";
			} else {
				remark += "会员状态 : " + MemberStatusEnum.MEMBER_DELETE.getRemark() + " ; ";
			}
			if (memberPromotionVO.getStatus().equals(PromotionStatusEnum.PROMOTION_ADOPT)) {
				remark += "推广师审核状态 : " + PromotionStatusEnum.PROMOTION_ADOPT.getRemark() + " ; ";
			} else if (memberPromotionVO.getStatus().equals(PromotionStatusEnum.PROMOTION_FAIL)) {
				remark += "推广师审核状态 : " + PromotionStatusEnum.PROMOTION_FAIL.getRemark() + " ; ";
			} else if (memberPromotionVO.getStatus().equals(PromotionStatusEnum.PROMOTION_NOT_PROFIT)) {
				remark += "推广师审核状态 : " + PromotionStatusEnum.PROMOTION_NOT_PROFIT.getRemark() + " ; ";
			} else {
				remark += "推广师审核状态  : " + PromotionStatusEnum.PROMOTION_AUDIT.getRemark() + " ; ";
			}
			profitOptLog = new ProfitOptLog();
			profitOptLog.setUserId(dto.getId());
			profitOptLog.setUseType(useType);
			profitOptLog.setIsProfit(isProfit);
			profitOptLog.setCreateIp(dto.getCreateIp());
			profitOptLog.setRemark(remark);
			profitOptLog.setCreaterId(dto.getUpdaterId());
			profitOptLog.setCreateTime(new Date());
		}
		return profitOptLog;
	}

	/**
	 * 
	 * @Title: updateEquipmentId @Description: 跟新设备ID @param: @param
	 * telPhone @param: @param equipmentId @return: void @author：李杰 @throws
	 */
	private void updateEquipmentId(String telPhone, String equipmentId) {
		Map<String, Object> map = ContainerUtil.map();
		map.put("telPhone", telPhone);
		map.put("equipmentId", equipmentId);
		memberMapper.updateEquipmentIdByMobile(map);
	}

	@Override
	@Transactional
	public Result addMemberShare(MemberAddShareDTO dto) {
		log.info("添加分享数据参数：MemberShareDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR); //返回的消息
		if (null != dto) {  //上传的数据是否为空
			try {
				String errorStr = dto.validateForm();
				if (StringUtils.isNotBlank(errorStr)) {
					result.setMessage(errorStr);
					return result;
				}
				// 被分享人账号
				final String toTelPhone = dto.getToTelPhone().trim();  //手机号
				final Byte type = dto.getType();   //MemberAddShareDTO   type类型
				// 查询会员信息
				Member member = memberMapper.selectByPrimaryKey(dto.getUserId());
				// 判断会员是否存在
				if (null == member) {
					ResultUtil.setResult(result, MemberResultEnum.MEMBERUSER_NON_EXISTENT);
					result.setMessage("当前会员不存在，不能进行分享");
					return result;
				}
				// 判断是否已实名认证
				/*if (!MemberCertificationEnum.VERIFIED.getCode().equals(member.getCertification())) {
					ResultUtil.setResult(result, MemberResultEnum.ID_NOT_CERTIFIED);
					result.setMessage("当前会员没有实名认证，不能进行分享");
					return result;
				}*/
				MemberShareRecord record = new MemberShareRecord();
				record.setToTelPhone(toTelPhone);
				record.setType(type);
				record = shareRecordMapper.selectOne(record);
				if (null != record && StringUtils.isNotBlank(record.getToTelPhone())) {
					ResultUtil.setResult(result, MemberResultEnum.MEMBER_ATTENTION);
					return result;
				}
				//通过上传的电话号码，从表中查询数据，验证数据是否存在
				MemberShareRecord inrecord = new MemberShareRecord();
				inrecord.setCreateIp(dto.getCreateIp());
				inrecord.setCreateTime(new Date());
				inrecord.setTelPhone(dto.getTelPhone());
				inrecord.setToTelPhone(toTelPhone);
				inrecord.setType(type);
				inrecord.setUserId(dto.getUserId());
				shareRecordMapper.insert(inrecord);
				// 将被分享人的信息保存到表中
				//如果被分享的人是会员则保存表中，否则不保存

				if (MemberShareTypeEnum.SHARE_MEMBER.getCode().equals(type)) {
					Member member1 = memberMapper.selectMemberInfoByMobile(toTelPhone);
					if(member1 == null) {
						MemberRegisterDTO dto2 = new MemberRegisterDTO();
						// TODO
						dto2.setTelPhone(dto.getToTelPhone());
						dto2.setMemberPwd(dto.getPassword());
						dto2.setEquipmentId("bsm");
						this.registerMember(dto2);
					}else{
						member1.setPromoterId(member.getId());
						memberMapper.updateByPrimaryKeySelective(member1);
					}
				}
				if (MemberShareTypeEnum.SHARE_MERCHANT.getCode().equals(type)){
						Map<String, Object> map = memberMapper.selectMerchantByTelPhone(toTelPhone);
						if(!map.isEmpty()){
							map.put("aa", member.getId());
							memberMapper.updateMerchantPromoterIdByTelPhone(map);
						}
				}
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} catch (Exception e) {
				log.error("添加分享数据错误", e);
				throw new MemberException("添加分享数据错误");
			}
		}
		return result;
	}

	@Override
	public Result getMemberShare(MemberQueryShareDTO dto) {
		log.info("查询分享数据参数：MemberQueryShareDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != dto) {
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
				return result;
			}
			MemberShareRecord record = new MemberShareRecord();
			record.setToTelPhone(dto.getToTelPhone());
			record.setType(dto.getType());
			MemberShareRecordVO vo = shareRecordMapper.getShareRecordInfoByTelphone(record);
			if (vo == null) {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, vo);
		}
		return result;
	}

	@Override
	public Result upgradeMemberPromotionAuto(MemberPromotionDTO dto) {
		log.info("会员推广3个商户自动升级为推广师参数,PromotionRecordDTO = {} ", JSON.toJSONString(dto));
		try {
			boolean flag = false; // 会员已经绑定推广师，但是推广师审核状态为非未通过（待审核、审核通过）；
			// 1.验证会员是否实名认证
			Member member = memberMapper.selectByPrimaryKey(dto.getMemberId());
			if (member != null && member.getCertification() != null
					&& !member.getCertification().equals(MemberCertificationEnum.VERIFIED.getCode())) {
				throw new MemberException("会员未实名认证");
				// return
				// ResultUtil.getResult(MemberResultEnum.MEMBER_CERTIFICATE_NON);
			}
			// 2.判断会员推广师审核状态 （ a.待审核、审核通过 ; b.未通过 ）
			MemberPromotionDTO memberAuthDTOById = new MemberPromotionDTO();
			memberAuthDTOById.setMemberId(dto.getMemberId());
			final MemberPromotionVO auth = memberPromotionMapper.getMemberAuthInfoByCondition(memberAuthDTOById);
			if (null != auth) {
				// a.状态为待审核、审核通过，返回"该会员已申请了推广师"
				if (!PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(auth.getStatus())) {
					log.warn("该会员已申请了推广师");
					throw new MemberException("该会员已申请了推广师");
					// return
					// ResultUtil.getResult(MemberResultEnum.MEMBERAUTH_EXIST);
				} else {// b.状态为审核未通过，flag = true，用于执行修改操作
					flag = true;
				}
			}
			// 3.新增（不存在推广师）或修改（存在推广师但审核状态为未通过）推广师表
			// 注意这里不会去操作会员表是否推广师字段，要审核的时候才操作
			MemberPromotion memberAuth = new MemberPromotion();
			memberAuth.setMemberId(dto.getMemberId());
			memberAuth.setAccountType(PromotionAccountTypeEnum.PROMOTION_PLATFORM.getCode());// 账号类型
																								// 平台添加
			memberAuth.setStatus(PromotionStatusEnum.PROMOTION_ADOPT.getCode());// 待审核状态
			memberAuth.setUpdateTime(new Date());
			memberAuth.setIsDelete(PromotionDeleteEnum.PROMOTION_NORMAL.getCode());// 未删除，即正常状态
			memberAuth.setCheckTime(new Date());// 审核时间
			if (flag) {// 修改操作
				memberAuth.setId(auth.getId());
				memberAuth.setUpdaterId(dto.getCreaterId());
				memberPromotionMapper.updateByPrimaryKeySelective(memberAuth);
			} else {// 新增操作
				memberAuth.setCreaterId(dto.getCreaterId());
				memberAuth.setCreateTime(new Date());
				memberPromotionMapper.insert(memberAuth);
			}
			// 4.修改会员是否推广师字段为是
			Member memberSave = new Member();
			memberSave.setId(dto.getMemberId());
			memberSave.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
			memberMapper.updateByPrimaryKeySelective(memberSave);

			// 5.发送升级推广师短信
			SmsSendData sendData = new SmsSendData();
			sendData.setMobile(member.getTelPhone());
			sendData.setType(SmsCodeType.UPGRADE_MEMBER_PROMOTION_AUTO);// 会员自动升级为推广师
			sendData.setSourceEnum(SmsSourceEnum.MEMBER);// 会员
			Result smsResult = smsDataService.sendSmsNoCode(sendData);
			log.info("给用户发送自动升级为推广师信息结果:" + smsResult);
			if (smsResult == null || !RespCode.Code.SUCCESS.getCode().equals(smsResult.getCode())) {
				log.info("给用户发送自动升级为推广师信息失败:" + smsResult);
				throw new MemberException("给用户发送自动升级为推广师信息失败");
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS, memberAuth);
		} catch (Exception e) {
			log.error("会员推广3个商户自动升级为推广师错误", e);
			throw new MemberException("会员推广3个商户自动升级为推广师错误");
		}
	}

	@Override
	public Result getMemberShareByPage(MemberShareRecordDTO dto) {
		log.info("查询分享数据参数：MemberShareRecordDTO = {} ", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (null != dto) {
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
				return result;
			}
			PageHelper.startPage(dto.getPage().getPageNum(), dto.getPage().getPageSize());
			List<MemberShareRecordVO> ms = memberMapper.selectMemberShareByList(dto);
			PageInfo<MemberShareRecordVO> page = new PageInfo<MemberShareRecordVO>(ms);
			result.setCount(page.getTotal());
			result.setData(page.getList());
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}

	@Override
	public Member selectByPrimaryKey(Long id) {
		log.info("根据Id查询会员信息  参数,id = :{} ", id);
		// 1.验证id参数是否为空
		if (id == null) {
			return null;
		}
		// 2.开始查询
		Member member = memberMapper.selectByPrimaryKey(id);
		// 3.判断查询结果是否为空，返回相应数据
		return member;
	}

	@Override
	public Result findAll(Integer startInt, Integer endInt) {
		// TODO Auto-generated method stub
		Result result = new Result();
		try {
			if (startInt == null || endInt == null) {
				result.setCode(String.valueOf(StatusCodeEnums.ERROR_PARAM.getCode()));
				result.setMessage(StatusCodeEnums.ERROR_PARAM.getMsg());
			}
			List<Map> adttachment = memberMapper.getIndexAdAttachDao(startInt, endInt);
			int size = adttachment.size();
			if (size <= 0) {
				result.setCode(String.valueOf(StatusCodeEnums.SUCCESS_NO_DATA.getCode()));
				result.setMessage(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());

			} else {
				result.setCode(String.valueOf(StatusCodeEnums.SUCCESS.getCode()));
				result.setMessage(StatusCodeEnums.SUCCESS.getMsg());
				result.setData(adttachment);
				;
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setCode(String.valueOf(StatusCodeEnums.ERROR.getCode()));
			result.setMessage(StatusCodeEnums.ERROR.getMsg());
		} finally {
			return result;
		}

	}

	@Override
	public void getTextDetail(Long id, Model model) {
		Map textDetail = memberMapper.getTextDetail(id);
		if (textDetail != null) {
			model.addAttribute("result", textDetail.get("detailContent"));
		}
	}

	@Override
	public Member findMemberByMobile(String mobile) {
		Member member = memberMapper.selectMemberInfoByMobile(mobile);
		return member;
	}

	@Override
	public Member getMemberById(Long id) {
		Member member = memberMapper.selectByPrimaryKey(id);
		return member;
	}

	/**
	 * 用户修改昵称
	 * @param memberid
	 * @param nikeName
	 */
	@Override
	public Result updateNikeName(Long memberid, String nikeName) {
		String nikename=nikeName.trim();
		List<Map> getMemberByNikeName=memberMapper.getNikeName(nikename);
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		//用户昵称不可重复
		if (getMemberByNikeName.size()>0){
			return ResultUtil.getResult(RespCode.Code.EXIST_NIKENAME);
		}
		//判断用户是否存在
		int num=memberMapper.isMerchantById(memberid);
		if(num == 0){
			return  ResultUtil.getResult(RespCode.Code.NO_MERMBER);
		}

		//昵称不能为空
		if(nikename==null || "".equals(nikename)){
			return  ResultUtil.getResult(RespCode.Code.NOTEMPTY_NIKENAME);
		}

		//限制昵称字符数,4-16位
		Integer len=0;
		for(int i=0;i<nikename.length();i++){
			if(nikename.charAt(i)>127 || nikename.charAt(i)==94){
				len+=2;
			}else{
				len++;
			}
		}
		if(len<4 ||len>16){
			return  ResultUtil.getResult(RespCode.Code.MAXTWENTY_NIKENAME);
		}
		//用户昵称以英文字母或汉字开头
		String firstName=nikename.substring(0,1);
		if(!firstName.matches("^[\u4E00-\u9FA5a-zA-Z]*$")){
			return  ResultUtil.getResult(RespCode.Code.FIRST_ENGLISHANDCHINESE);
		}
		memberMapper.updateNikeName(memberid,nikename);
		result.setData(nikename);
		return  result;
	}

	@Override
	public Map getMarkindByPhone(String phone) {
		return memberMapper.getMarkindByPhone(phone);
	}
}
