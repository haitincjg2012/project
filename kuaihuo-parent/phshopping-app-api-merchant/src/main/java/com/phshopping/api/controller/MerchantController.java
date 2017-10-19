package com.phshopping.api.controller;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.AddMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.CommentDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.service.IUnlineOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.CashDTO;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.dto.CommonDTO;
import com.ph.shopping.common.core.other.BankCardCheckService;
import com.ph.shopping.common.core.other.IdAuthService;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.other.sms.senum.SmsStatusEnum;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.smsCode.SmsCodeUtil;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.member.dto.*;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.paypwd.PayPwdEnum;
import com.ph.shopping.facade.member.service.*;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;
import com.ph.shopping.facade.pay.config.AlipayConfig;
import com.ph.shopping.facade.pay.enums.AlipayProductCodeEnum;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.service.ILoginService;
import com.ph.shopping.facade.permission.service.IUserService;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.spm.constant.ChargeConstant;
import com.ph.shopping.facade.spm.dto.*;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.entity.UserBalance;
import com.ph.shopping.facade.spm.entity.UserCharge;
import com.ph.shopping.facade.spm.exception.UserAccountExceptionEnum;
import com.ph.shopping.facade.spm.service.*;
import com.ph.shopping.facade.spm.service.IStoreManagerService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.*;
import com.phshopping.api.aop.annotation.AccessToken;
import com.phshopping.api.constant.MechantAppResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @项目：phshopping-parent @描述： @作者： Mr.chang @创建时间：2017/6/15
 * @Copyright @2017 by Mr.chang
 */
@RestController
@RequestMapping("api/merchant")
@Api(value = "商户接口")
public class MerchantController extends BaseMerchantController {

    //private static final Logger logger =
    //LoggerFactory.getLogger(MerchantController.class);

    // 登陆接口
    @Reference(version = "1.0.0")
    ILoginService iLoginService;

    // 会员卡接口
    @Reference(version = "1.0.0")
    IMemberCardService iMemberCardService;

    // 积分接口
    @Reference(version = "1.0.0")
    IScoreService iScoreService;

    // 积分接口
    @Reference(version = "1.0.0")
    IUserBalanceService iUserBalanceService;

    // 短信服务
    @Autowired
    private ISmsDataService smsDataService;

    // 线下订单接口
    @Reference(version = "1.0.0")
    IUnlineOrderService iUnlineOrderService;

    // 缓存
    @Autowired
    private
    @SuppressWarnings("rawtypes")
    ICacheService redisService;

    // 会员接口
    @Reference(version = "1.0.0")
    IMemberService iMemberService;

    // 用户接口
    @Reference(version = "1.0.0")
    IUserService iUserService;

    // 商户接口
    @Reference(version = "1.0.0")
    IMerchantService iMerchantService;

    //店面经理接口
    @Reference(version = "1.0.0")
    IStoreManagerService iStoreManagerService;

    // 商户接口
    @Reference(version = "1.0.0")
    IAdAttachment iAdAttachment;


    // 提现记录接口
    @Reference(version = "1.0.0")
    private IUserDrawCashService iUserDrawCashService;

    // 充值记录接口
    @Reference(version = "1.0.0")
    private IUserChargeService iUserChargeService;

    @Autowired
    private PayConfiguration payConfiguration;

    @Reference(version = "1.0.0")
    private IManageBankCardService iBankCardService;

    @Autowired
    private IdAuthService idAuthService;

    @Reference(version = "1.0.0")
    private IBankCodenameDataService iBankCodenameDataService;

    @Autowired
    private BankCardCheckService bankService;

    @Reference(version = "1.0.0")
    private IPayPasswordService iPayPasswordService;

    @Autowired
    private SmsUtil smsUtil;


    /**
     * 商户app登陆
     *
     * @param user
     * @param httpSession
     * @return
     * @Title: merchantAppLogin
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月20日 上午10:39:52
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "登陆")
    @RequestMapping(value = "/merchantAppLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public Object merchantAppLogin(@ModelAttribute("user") User user, HttpSession httpSession) {
        user.setPassword(MD5.getMD5Str(user.getPassword()));
        Result login = iLoginService.login(user);
        SessionUserVO sessionUserVo = (SessionUserVO) login.getData();
        httpSession.setAttribute("login_back_session", sessionUserVo);
        logger.info("商户App登陆返回  Result ={}", JSON.toJSON(login));
        Map<String, Object> map = new HashMap<String, Object>();
        if (sessionUserVo != null) {
            if (sessionUserVo.getSessionRoleVo().size() == 0) {
                return ResultUtil.getResult(MechantAppResultEnum.NO_ROLE);// 没得角色登陆不了
            }
            SessionRoleVO sessionRoleVo = sessionUserVo.getSessionRoleVo().get(0);
            RoleEnum roleEnum = RoleEnum.getRoleEnumByCode(sessionRoleVo.getRoleCode());
            if (!"MERCHANT".equals(roleEnum.name())) {
                return ResultUtil.getResult(MechantAppResultEnum.ONLY_MERCHANT_LOGIN);// 只允许商户登陆
            }
            //===========================验证商户是否已经冻结================
            if (sessionUserVo != null && sessionUserVo.getTelphone() != null) {
                Map memberAndMerchant = iMerchantService.getMemberAndMerchant(sessionUserVo.getTelphone());
                try {
                    if (memberAndMerchant == null) {
                        return ResultUtil.getResult(MechantAppResultEnum.NO_MERCHANT);
                    } else if (memberAndMerchant != null && memberAndMerchant.get("frozen") != null && memberAndMerchant.get("frozen").equals("1")) {
                        return ResultUtil.getResult(MechantAppResultEnum.NO_FROREN_USER);
                    }
                } catch (Exception e) {

                    return ResultUtils.returnError("用户异常");
                }
            }
            UUID token = UUID.randomUUID();// 生成唯一token
            redisService.set(getMerchantAppKey(token.toString(), roleEnum), sessionUserVo.getId());// 写入缓存
            map.put("userName", sessionUserVo.getUserName());
            map.put("telphone", sessionUserVo.getTelphone());
            map.put("token", token);
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setUserId(sessionUserVo.getId());
            MerchantVO mv = iMerchantService.getMerchantDetailById(merchantDTO);
            logger.info("商户App通过登录表id 获取商户信息 返回   Result ={}", JSON.toJSON(mv));
            if (mv == null) {
                logger.info("无此商户");
                return ResultUtil.getResult(MechantAppResultEnum.NO_MERCHANT);// 无此商户
            }
            map.put("isCdKey", mv.getIsCdKey());
            map.put("id", mv.getId());
            map.put("userId", sessionUserVo.getId());
            map.put("profitRatio", mv.getBusinessProfitRatio());
            login.setData(map);
        }
        return login;
    }

    /**
     * 会员提现明细
     */
    @AccessToken
    @RequestMapping(value = "/cashdetails", method = RequestMethod.POST)
    public Result cashDatiles(HttpServletRequest request, Integer pageNo, Integer pageSize) {
        String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
                .toString();
        return iScoreService.getMerchantScoreDeatil(Long.valueOf(merchantAppKey), pageNo, pageSize);
    }

    /**
     * 商户App通过外码获取会员信息(扫码)
     *
     * @param outerCode
     * @return
     * @Title: merchantAppScanCode
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月20日 下午1:45:45
     */
    @ApiOperation(value = "商户App通过外码获取会员信息(扫码)")
    @AccessToken
    @RequestMapping(value = "/merchantAppScanCode", method = RequestMethod.POST)
    public Object merchantAppScanCode(String outerCode) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        MemberCardInfoDTO dto = new MemberCardInfoDTO();
        dto.setOuterCode(outerCode);
        Result memberCardInfoByOuterCode = iMemberCardService.judgeMemberCard(dto);
        logger.info("商户App通过外码获取会员信息返回   Result ={}", JSON.toJSON(memberCardInfoByOuterCode));
        if (!memberCardInfoByOuterCode.getCode().equals("200")) {
            return ResultUtil.getResult(MechantAppResultEnum.NO_MEMBERCARD_EMPTY);// 无此会员卡数据
        }
        MemberScoreVO2 memberScore = iScoreService
                .getMemberScore(((MemberCardInfoVO) memberCardInfoByOuterCode.getData()).getMemberId());
        if (memberScore == null) {
            return ResultUtil.getResult(MechantAppResultEnum.NO_MEMBERSCORCE_EXECEPTION);// 无此会员积分数据
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", ((MemberCardInfoVO) memberCardInfoByOuterCode.getData()).getMemberId());// 会员主键id
        map.put("memberPhone", ((MemberCardInfoVO) memberCardInfoByOuterCode.getData()).getTelPhone());// 会员手机号码

        // 取2位小数
        double enablescore = new Long(memberScore.getEnablescore()).doubleValue();
        enablescore = enablescore / 10000.0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        map.put("enablescore", df.format(enablescore));// 可用积分
        result.setData(map);
        return result;
    }

    /**
     * 商户APP 通过会员手机号码获取会员信息或者没有会员信息创建会员
     *
     * @param memberPhone
     * @return
     * @Title: getMemberInfoByTel
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月23日 上午11:49:29
     */
    @ApiOperation(value = "商户App通过手机号获取会员信息")
    @AccessToken
    @RequestMapping(value = "/getMemberInfoByTel", method = RequestMethod.POST)
    public Object getMemberInfoByTel(String memberPhone, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Result result1 = ResultUtil.getResult(RespCode.Code.SUCCESS);
        // 通过手机号判断会员信息--star
        if (memberPhone == null || "".equals(memberPhone)) {
            return ResultUtil.getResult(OrderResultEnum.ORDER_OPERAION_INVALID);
        } else {
            Result result = iMemberService.getMemberInfoByMobile(memberPhone);
            if (result.isSuccess()) {
                Member member = (Member) result.getData();
                map.put("memberId", member.getId());// 会员主键id
                map.put("memberPhone", member.getTelPhone());// 会员手机号码

                MemberScoreVO2 memberScore = iScoreService.getMemberScore(member.getId());
                if (memberScore == null) {
                    return ResultUtil.getResult(MechantAppResultEnum.NO_MEMBERSCORCE_EXECEPTION);// 无此会员积分数据
                }
                // 取2位小数
                double enablescore = new Long(memberScore.getEnablescore()).doubleValue();
                enablescore = enablescore / 10000.0;
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.FLOOR);
                map.put("enablescore", df.format(enablescore));// 可用积分
            } else {
                try {
                    // 创建会员
                    MemberAddDTO memberAddDTO = new MemberAddDTO();
                    memberAddDTO.setTelPhone(memberPhone);
                    memberAddDTO.setCreaterId(Long.valueOf(redisService
                            .get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT)).toString()));
                    Result creatResult = iMemberService.addMember(memberAddDTO);
                    if (creatResult == null || !creatResult.isSuccess()) {
                        return ResultUtil.getResult(MechantAppResultEnum.DO_MEMBER_FAULT);
                    } else {
                        Member m = (Member) creatResult.getData();
                        map.put("memberId", m.getId());// 创建的会员id
                        map.put("memberPhone", m.getTelPhone());// 创建的会员手机号
                        map.put("enablescore", 0);// 可用积分
                    }
                } catch (MemberException e) {
                    logger.error("创建会员失败");
                    return ResultUtil.getResult(MechantAppResultEnum.DO_MEMBER_FAULT);
                }
            }
        }
        result1.setData(map);
        return result1;
    }

    /**
     * 商户App 输入金额点击确定获取商户余额
     *
     * @return
     * @Title: merchantAppGetMerchantBalance
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月20日 下午2:04:22
     */
    @ApiOperation(value = "商户App 输入金额点击确定获取商户余额")
    @AccessToken
    @RequestMapping(value = "/merchantAppGetMerchantBalance", method = RequestMethod.POST)
    public Object merchantAppGetMerchantBalance(HttpSession httpSession, HttpServletRequest request) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
                .toString();
        Result userBalance2 = iUserBalanceService.getUserBalance(Long.valueOf(merchantAppKey));// 获取商户金额
        if (!userBalance2.getCode().equals("200")) {
            return userBalance2;
        }
        BalanceVO out = (BalanceVO) userBalance2.getData();
        Map<String, Object> map = new HashMap<String, Object>();
        // 取2位小数
        double balance = out.getScore();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        map.put("balance", df.format(balance));
        result.setData(map);
        return result;
    }

    /**
     * 商户App 选择积分支付、且点击支付 发短信至会员手机
     *
     * @param memberPhone
     * @return
     * @Title: merchantAppToPay
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月20日 下午2:17:45
     */
    @ApiOperation(value = "商户App 选择积分支付、且点击支付 发短信至会员手机")
    @AccessToken
    @RequestMapping(value = "/merchantAppToPay", method = RequestMethod.POST)
    public Object merchantAppToPay(String memberPhone) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try {
            SmsSendData sendData = new SmsSendData();
            sendData.setMobile(memberPhone);
            sendData.setType(SmsCodeType.SCORE_PAY_VC);
            sendData.setSourceEnum(SmsSourceEnum.MEMBER);
            sendData.setSmsCode(SmsCodeUtil.getSmsCode());
            result = smsDataService.sendSmsHaveCode(sendData);
            logger.info("发送短信返回数据 Result = {} ", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.getResult(RespCode.Code.FAIL);
    }

    /**
     * 商户App订单支付
     * <p>
     * 订单金额 (1 现金 2 积分 3快捷支付) (会员id) (商户支付密码) (会员手机收到的短信验证码)
     *
     * @param memberPhone (会员手机号)
     */
    @ApiOperation(value = "商户App订单支付")
    @AccessToken
    @RequestMapping(value = "/merchantAppPay", method = RequestMethod.POST)
    public Object addUnlineOrder(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO, HttpSession httpSession,
                                 String memberPhone, HttpServletRequest request) {
        logger.info("商户App订单支付处理参数：AddMemberOrderUnlineDTO = {} ", JSON.toJSONString(addMemberOrderUnlineDTO));
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
                .toString();
        if (StringUtils.isBlank(merchantAppKey)) {
            logger.info("商户App订单支付 根据token 未获取到相关数据");
            return ResultUtil.getResult(RespCode.Code.PERMISSION_DENIED);
        }
        // 参数改下 memberTelphone
        String memberTelphone = request.getParameter("memberPhone");
        if (memberTelphone != null && memberTelphone != "") {
            addMemberOrderUnlineDTO.setMemberTelphone(memberTelphone);
        }

        try {
            Long loginUserId = Long.valueOf(merchantAppKey);
            addMemberOrderUnlineDTO.setCreaterId(loginUserId);
            addMemberOrderUnlineDTO.setMerchantId(loginUserId);
            if (addMemberOrderUnlineDTO.getOrderMoney().doubleValue() < 0) {
                result = ResultUtil.getResult(RespCode.Code.FAIL);
                result.setMessage("输入金额有误");
            } else {
                if (addMemberOrderUnlineDTO.getPayType() == PayTypeEnum.PAY_TYPE_SCORE.getPayType()) { // 验证验证
                    Result resultCheckMsm = checkSMSCode(addMemberOrderUnlineDTO, memberPhone);
                    if (!resultCheckMsm.isSuccess()) {
                        result = ResultUtil.getResult(OrderResultEnum.CHECK_CODE_ERROR);
                        return result;
                    }
                }
                // 3快捷支付（只下单不支付）
                if (addMemberOrderUnlineDTO.getPayType() == PayTypeEnum.PAY_TYPE_SHORTCUT.getPayType()) {
                    result = iUnlineOrderService.addUnlineOrder(addMemberOrderUnlineDTO, false);
                } else {
                    result = iUnlineOrderService.addUnlineOrder(addMemberOrderUnlineDTO, true);
                }
            }
        } catch (Exception e) {
            logger.error("商户App订单支付异常", e);
            result = ResultUtil.getResult(RespCode.Code.FAIL);
        }
        return result;
    }

    /**
     * 商户App获取线下订单
     */
    @ApiOperation(value = "商户App获取线下订单列表")
    @AccessToken
    @RequestMapping(value = "/merchantAppGetUnlineOrderList", method = RequestMethod.POST)
    public Object merchantAppGetUnlineOrderList(PageBean pagebean, HttpSession httpSession,
                                                HttpServletRequest request) {
        try {
            String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
                    .toString();
            PhMemberOrderUnlineDTO phMemberOrderUnlineDTO = new PhMemberOrderUnlineDTO();
            phMemberOrderUnlineDTO.setMerchantId(Long.valueOf(merchantAppKey));
            phMemberOrderUnlineDTO.setStatus((byte) 2);
            return iUnlineOrderService.getUnLineOrderVoList(phMemberOrderUnlineDTO, pagebean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取线下订单列表异常");
            return ResultUtil.getResult(MechantAppResultEnum.NO_UNLINEORDERDATA_EXCEPTION);
        }
    }

    /**
     * 获取商户的余额流水
     *
     * @return
     */
    @ApiOperation(value = "获取商户的余额流水")
    @SuppressWarnings("unchecked")
    @AccessToken
    @RequestMapping(value = "/merchantAppGetMerchantBalanceList", method = RequestMethod.POST)
    public Object merchantAppGetMerchantBalanceList(PageBean page, UserBalanceTradeDTO userBalanceTradeDTO,
                                                    HttpSession httpSession, HttpServletRequest request) {
        Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
        try {
            String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
                    .toString();
            userBalanceTradeDTO.setUserId(Long.valueOf(merchantAppKey));
            logger.info("商户余额流水记录参数:{}",JSON.toJSON(userBalanceTradeDTO));
            Result userBalanceTrade = iUserBalanceService.getUserBalanceTrade(userBalanceTradeDTO);
            logger.info("获取到商户余额信息:{}",JSON.toJSON(userBalanceTrade));
            List<UserBalanceTradeVO> p = (List<UserBalanceTradeVO>) userBalanceTrade.getData();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (UserBalanceTradeVO o : p) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map2.put("createTime", df.format(o.getCreateTime()));
                map2.put("money", o.getScore());
                map2.put("source", o.getSource());
                map2.put("fee", o.getHandingCharge());
                mapList.add(map2);
            }
            // 下面将用户的余额记录放入集合中
            UserBalance userBalance = new UserBalance();
            userBalance.setUserId(Long.valueOf(merchantAppKey));
            Result userBalance2 = iUserBalanceService.getUserBalance(Long.valueOf(merchantAppKey));// 获取商户金额
            logger.info("获取到的商户金额记录:{}",JSON.toJSON(userBalance2));
            if (!userBalance2.getCode().equals("200")) {
                return userBalance2;
            }

            BalanceVO out = (BalanceVO) userBalance2.getData();
            Map<String, Object> mapAll = new HashMap<String, Object>();
            // 取2位小数
            double balance = out.getScore();
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            mapAll.put("userBalance", df.format(balance));
            mapAll.put("dataMap", mapList);
            result.setData(mapAll);
            result.setMessage("成功");
            result.setCount(userBalanceTrade.getCount());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商户余额流水列表异常");
            return ResultUtil.getResult(MechantAppResultEnum.NO_MERCHANT_BALANCERECORD);
        }
    }

    /**
     * @param
     * @author: 张霞
     * @description：验证短信验证码
     * @createDate: 17:04 2017/6/20
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    private Result checkSMSCode(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO, String memberPhone) {
        // 1.短信验证
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(memberPhone);
        sendData.setSmsCode(addMemberOrderUnlineDTO.getVerificationCode());// 验证发送的验证码
        sendData.setType(SmsCodeType.SCORE_PAY_VC);
        sendData.setSourceEnum(SmsSourceEnum.MEMBER);
        Result resultSmsCode = smsDataService.checkSmsCodeByMobile(sendData);
        logger.info("根据手机号获取短信验证码验证后返回数据 Result = {} ", JSON.toJSONString(resultSmsCode));
        if (resultSmsCode != null) {
            if (!resultSmsCode.isSuccess()) {
                return resultSmsCode;
            }
        }
        return resultSmsCode;
    }

    /**
     * @Title: getOrderSatusByBarcode @Description:
     * TODO:根据二维码得到订单状态 @param: @param
     * barcodeMark @param: @return @return: Result @author：李杰 @throws
     */
    @AccessToken
    @RequestMapping(value = "/getOrderSatusByBarcode/{barcodeMark}", method = RequestMethod.GET)
    public Result getOrderSatusByBarcode(@PathVariable String barcodeMark) {
        try {
            Result result = iUnlineOrderService.getOrderSatusByBarcode(barcodeMark);
            if (result.getData() != null && (Integer) (result.getData()) == 3) {
                result.setMessage("订单支付失败");
            }
            logger.info("根据二维码得到订单状态返回数据 result = {} ", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("根据二维码得到订单状态异常", e);
            return ResultUtil.getResult(MechantAppResultEnum.NO_UNLINEORDERDATA_EXCEPTION);
        }
    }

    /**
     * @Title: getBarcodeMark @Description:
     * TODO:获取二位码唯一标识 @param: @return @return: Result @author：李杰 @throws
     */
    @AccessToken
    @RequestMapping(value = "/getUUID", method = RequestMethod.GET)
    public Result getUUID() {
        return ResultUtil.getResult(RespCode.Code.SUCCESS, UUID.randomUUID().toString());
    }

    /**
     * 清除缓存中的token值
     *
     * @return
     * @Title: logout
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Mr.Dong
     * @date 2017年6月21日 上午11:54:55
     */
    @SuppressWarnings("unchecked")
    @AccessToken
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        redisService.remove(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT));
        return ResultUtil.getResult(RespCode.Code.SUCCESS);
    }

    /**
     * @param userDrawCashDTO
     * @param request
     * @author: 张霞
     * @description：商户APP提现
     * @createDate: 15:10 2017/7/12
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @ApiOperation(value = "商户提现", notes = "在登录状态中，通过传入参数进行提现操作", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录的token值", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "receiver", value = "账户名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bankNo", value = "银行卡号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bankName", value = "所属银行", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "drawCashScore", value = "提现金额，只能输入数额数字为正整数类型", required = true, dataType = "Long", paramType = "query")})
    @AccessToken
    @RequestMapping(value = "/adduserdrawcashrecord", method = RequestMethod.POST)
    public Result addUserDrawCashRecord(UserDrawCashDTO userDrawCashDTO, HttpServletRequest request) {
        logger.info("商户APP控制层提现入参参数userDrawCashDTO={}", JSON.toJSONString(userDrawCashDTO));
        Result result;
        try {
            if (userDrawCashDTO.getDrawCashScore() instanceof Long) {
                Long userId = getUserId();
                userDrawCashDTO.setHandingCharge(5l);// 手续费
                // 比如输入100,实际提取95
                userDrawCashDTO.setScore(userDrawCashDTO.getDrawCashScore() - userDrawCashDTO.getHandingCharge());// 实际所得金额
                userDrawCashDTO.setUserId(userId); // 商户id
                userDrawCashDTO.setUserType(RoleEnum.MERCHANT.getCode()); // 商户角色
                result = iUserDrawCashService.addDrawCashRecord(userDrawCashDTO);
            } else {
                result = ResultUtil.getResult(UserAccountExceptionEnum.BALANCE_INTEGER);
            }
        } catch (Exception e) {
            logger.error("提现失败", e);
            result = ResultUtil.getResult(UserAccountExceptionEnum.DRAWCASH_EXCEPTION);
        }
        logger.info("商户APP控制层提现结果result={}", JSON.toJSONString(result));
        return result;
    }

    // @RequestMapping("/callbackmsg")
    // public String returnMsg(Model model, HttpServletRequest request,
    // HttpServletResponse response)throws IOException {
    //
    // String orderNum2 = request.getParameter("shen");
    // String payStatus2 = request.getParameter("hui"); //2 成功 3 失败
    // String money2 = request.getParameter("yuan");
    // if(orderNum2 != null && payStatus2 != null && money2 != null){
    // String orderNum = RSACommonUtils.decryptByPublicKey(orderNum2,
    // RSACommonUtils.CharSet.UTF8);
    // String payStatus = RSACommonUtils.decryptByPublicKey(payStatus2,
    // RSACommonUtils.CharSet.UTF8);
    // String money = RSACommonUtils.decryptByPublicKey(money2,
    // RSACommonUtils.CharSet.UTF8);
    //
    // Map<String, String> map = new HashMap<>();
    // map.put("orderNum", orderNum);
    // map.put("payStatus", payStatus);
    // map.put("money", money);
    // String call = iUserDrawCashService.verifySingNotifyForYPay(map);
    //
    // if("success".equals(call)){
    // logger.info("订单号"+orderNum2+"回调方法处理完毕，返回的字符串"+call+"通知pay收到回调");
    // return "success";
    // }
    // }else{
    // return "NullError";
    // }
    // return "error";
    // }

    /**
     * @param request
     * @param userChargeDTO
     * @param chargeType
     * @author: 张霞
     * @description：商户充值，通过支付宝通知和银行卡（暂时银行卡不能实现） @createDate: 14:20 2017/7/13
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @ApiOperation(value = "商户APP充值", notes = "在登录状态中进行操作，通过传入参数进行充值操作", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chargeType", value = "充值方式：目前只支持支付宝充值，参数值为'2'", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "登录的token值", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "score", value = "充值金额，只能是正整数,填充score值", required = true, dataType = "Long", paramType = "query")})
    @AccessToken
    @RequestMapping(value = "/chargeRecord", method = RequestMethod.POST)
    public Result chargeRecord(HttpServletRequest request, UserChargeDTO userChargeDTO, String chargeType) {
        Result result;
        logger.info("商户APP充值参数：userChargeDTO={},chargeType={}", JSON.toJSONString(userChargeDTO),
                JSON.toJSONString(chargeType));
        try {
            Long userId = getUserId();
            UserCharge userCharge = new UserCharge();
            userCharge.setChargeStatus((byte) ChargeConstant.CHARGING);// 充值状态为充值中
            userCharge.setUserId(userId);
            userCharge.setCreaterId(userId);
            userCharge.setUserType(RoleEnum.MERCHANT.getCode());
            userCharge.setScoreTrans(userChargeDTO.getScore());
            userCharge.setScore(MoneyTransUtil.transDoubleToBigDecimal(userChargeDTO.getScore()));
            userCharge.setCreaterId(userId);
            userCharge.setChargeType(Byte.valueOf(chargeType).byteValue());
            if (PayTypeEnum.PAY_TYPE_ALIPAY.getPayType() == Byte.valueOf(chargeType).byteValue()) {
                // 支付宝充值
                userCharge.setPayUrl(payConfiguration.getAliPay());
            } else {
                // 银行卡充值
                userCharge.setPayUrl(payConfiguration.getEcoPay());
            }
            result = iUserChargeService.addUserChargeRecord(userCharge);
            // 存redis
            if (result.isSuccess() && result.getData() != null) {
                String orderNo = result.getData().toString();
                String description = "商户支付宝充值订单";
                StringBuilder sb = new StringBuilder();
                sb.append("{\"amount\":").append("\"").append(userChargeDTO.getScore()).append("\"").append(",")
                        .append("\"description\":").append("\"").append(description).append("\"").append("}");
                // redis中缓存支付信息，金额和描述(第三方支付用)
                logger.info("商户通过支付宝充值，缓存记录orderNo={}，sb={}", orderNo, sb.toString());
                redisService.set(orderNo, sb.toString());
                // 返回页面跳转
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("orderNo", orderNo);
                // resultMap.put("aliPay", payConfiguration.getAliPay());
                resultMap.put("description", description);
                result.setData(resultMap);

            }
        } catch (Exception e) {
            result = ResultUtil.getResult(RespCode.Code.FAIL);
        }
        return result;
    }

    /**
     * @param orderNo
     * @param description
     * @param amount
     * @author: 张霞
     * @description：支付宝充值信息加密
     * @createDate: 16:35 2017/7/14
     * @return: java.lang.String
     * @version: 2.1
     */
    private String encoAliPay(String orderNo, String description, String amount) {
        String alipayResult = "";
        try {
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            // 封装请求支付信息
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(orderNo);
            model.setSubject(description);
            model.setTotalAmount(amount);
            model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);// 设置订单超时时间
            model.setProductCode(AlipayProductCodeEnum.PRODUCT_FAST_INSTANT_CODE.getCode());
            alipayRequest.setBizModel(model);
            // 设置异步通知地址
            alipayRequest.setNotifyUrl(AlipayConfig.ASYNC_NOTIFY_URL);
            alipayResult = alipayClient.sdkExecute(alipayRequest).getBody();// APP请求
            // alipayResult=alipayClient.pageExecute(alipayRequest).getBody();//页面请求
        } catch (Exception e) {
            logger.info("支付宝充值信息加密 异常={}", e.getMessage());
            alipayResult = "";
        }
        return alipayResult;
    }

    /**
     * @return
     * @Title: validateRealNameAuth
     * @Description: 校验是否实名认证
     * @author 王强
     * @date 2017年7月13日 下午5:12:55
     */
    @ApiOperation(value = "校验是否实名认证", notes = "在登录状态中进行操作，校验是否实名认证")
    @AccessToken
    @RequestMapping(value = "/validateRealName", method = RequestMethod.GET)
    public Result validateRealNameAuth() {
        try {
            Long userId = getUserId();
            // 首先判断是否已认证
            int isAuth = 0;
            RealAndIdCardNoVO realAndIdCardNoVO = null;
            Result res = iBankCardService.getRealNameAndIdCardNoInfo(userId);
            logger.info("查询实名认证数据：" + JSON.toJSONString(res));
            if (res.getData() == null) {
                realAndIdCardNoVO = new RealAndIdCardNoVO();
                realAndIdCardNoVO.setIsAuth(isAuth);
                logger.info("未实名认证 : " + JSON.toJSONString(realAndIdCardNoVO));
                return ResultUtil.getResult(RespCode.Code.SUCCESS, realAndIdCardNoVO);
            } else {
                realAndIdCardNoVO = (RealAndIdCardNoVO) res.getData();
                isAuth = 1;
                realAndIdCardNoVO.setIsAuth(isAuth);
                logger.info("返回实名认证数据 : " + JSON.toJSONString(res));
                return res;
            }
        } catch (Exception e) {
            logger.error("validateRealNameAuth失败", e);
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param realNameAuthDTO
     * @return
     * @Title: realNameAuthentication
     * @Description: 实名认证
     * @author 王强
     * @date 2017年7月13日 上午11:45:58
     */
    @ApiOperation(value = "实名认证", notes = "在登录状态中进行操作，通过传入参数进行实名认证操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "telPhone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号", required = true, dataType = "String", paramType = "query")})
    @AccessToken
    @RequestMapping(value = "/realNameAuthentication", method = RequestMethod.POST)
    public Result realNameAuthentication(RealNameAuthDTO realNameAuthDTO) {
        try {

            Long userId = getUserId();
            logger.info("商户ID ： " + userId);
            logger.info("实名认证参数:realNameAuthDTO=" + JSON.toJSONString(realNameAuthDTO));
            if (VerifyUtil.verifyEntityField(realNameAuthDTO)) {
                return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
            }

            // 新增实名认证
            if (idAuthService.idCertificatesAuth(realNameAuthDTO.getRealName(), realNameAuthDTO.getIdCardNo())) {
                ManageBankCardInfo bankCardInfo = new ManageBankCardInfo();
                bankCardInfo.setCreateTime(new Date());
                bankCardInfo.setIdCardNo(realNameAuthDTO.getIdCardNo());
                bankCardInfo.setOwnerName(realNameAuthDTO.getRealName());
                bankCardInfo.setUserId(userId);
                bankCardInfo.setCreaterId(userId);
                bankCardInfo.setTelPhone(realNameAuthDTO.getTelPhone());
                bankCardInfo.setIsDelete((byte) 0);
                bankCardInfo.setCreateIp(IPUtil.getIpAddress());
                // 新增认证信息
                logger.info("新增实名认证入参:" + JSON.toJSONString(bankCardInfo));
                long ret = iBankCardService.insertRealNameAndIdCardNo(bankCardInfo);
                if (ret > 0) {
                    return ResultUtil.getResult(RespCode.Code.SUCCESS);
                } else {
                    return ResultUtil.getResult(RespCode.Code.FAIL);
                }
            } else {
                return ResultUtil.getResult(MechantAppResultEnum.AUTHENTICATION_ERROR);
            }
        } catch (Exception e) {
            logger.error("认证失败", e);
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return
     * @Title: getBankList
     * @Description: 查询银卡列表
     * @author 王强
     * @date 2017年7月13日 下午5:44:24
     */
    @ApiOperation(value = "查询银行卡", notes = "登录状态才能进行")
    @AccessToken
    @RequestMapping(value = "/getBankList", method = RequestMethod.GET)
    public Result getBankList() {
        try {
            Long userId = getUserId();
            BankVO bankVO = iBankCardService.getBankInfo(userId);
            if (bankVO == null) {
                return ResultUtil.getResult(MechantAppResultEnum.NO_BANK);
            }
            return ResultUtil.getResult(RespCode.Code.SUCCESS, bankVO);
        } catch (Exception e) {
            logger.info("查询银行卡失败");
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return
     * @Title: validateBindBank
     * @Description: 绑定银行卡之前校验是否实名认证
     * @author 王强
     * @date 2017年7月13日 下午6:23:54
     */
    @ApiOperation(value = "实名认证后才能绑定银行卡", notes = "登录状态才能进行")
    @AccessToken
    @RequestMapping(value = "/validateBindBank", method = RequestMethod.GET)
    public Result validateBindBank() {
        try {
            Long userId = getUserId();
            Result res = iBankCardService.getRealNameAndIdCardNoInfo(userId);
            if (res.getData() == null) {
                return ResultUtil.getResult(MechantAppResultEnum.AUTH_REQUERED);
            } else {
                return ResultUtil.getResult(RespCode.Code.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("validateBindBank失败", e);
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param bankDTO
     * @return
     * @Title: bindBankNo
     * @Description: 银行卡绑定
     * @author 王强
     * @date 2017年7月13日 下午6:25:53
     */
    @ApiOperation(value = "银行卡绑定", notes = "登录状态才能进行绑定银行卡操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "ownerName", value = "真实姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "银行名称(根据卡号带出)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cardNo", value = "银行卡号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "telPhone", value = "登录手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "operator", value = "操作类型(1新增，2编辑，3删除)", required = true, dataType = "Integer")})
    @AccessToken
    @RequestMapping(value = "operatorBank", method = RequestMethod.POST)
    public Result bindBankNoOrunBindBankNo(BankDTO bankDTO) {
        try {
            logger.info("银行卡操作入参bankDTO = {} ", JSON.toJSONString(bankDTO));
            if (VerifyUtil.verifyEntityField(bankDTO)) {
                return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
            }
            Long userId = getUserId();
            bankDTO.setUserId(userId);
            // 校验银行卡
            Result res = iBankCardService.getRealNameAndIdCardNoInfo(userId);
            logger.info("查询实名认证信息返回结果 Result = {}", JSON.toJSONString(res));
            String cardNum = "";
            String ownerName = "";
            if (res.isSuccess()) {
                Object obj = res.getData();
                if (obj instanceof RealAndIdCardNoVO) {
                    RealAndIdCardNoVO vo = (RealAndIdCardNoVO) obj;
                    cardNum = vo.getIdCardNo();
                    ownerName = vo.getOwnerName();
                }
            }
            if (bankService.bankCardAuth(cardNum, bankDTO.getCardNo(), ownerName)) {
                //return iBankCardService.insertBindBank(bankDTO);
                return iBankCardService.updateBankCardInfo(userId, bankDTO);
            } else {
                Result result = ResultUtil.getResult(RespCode.Code.FAIL);
                result.setMessage("银行卡与身份信息不匹配，请检查数据");
                return result;
            }
        } catch (Exception e) {
            logger.error("绑定银行卡失败", e);
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param cardNo
     * @return
     * @Title: getBankName
     * @Description: 根据银行卡号获取银卡名称
     * @author 王强
     * @date 2017年7月14日 下午2:13:23
     */
    @ApiOperation(value = "根据银行卡号获取银行名称", notes = "登录状态才能进行绑定银行卡操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardNo", value = "银行卡号", required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "getBankName", method = RequestMethod.POST)
    public Result getBankName(CardNoDTO cardNo) {
        try {
            logger.info("获取银行卡名称入参:" + JSON.toJSONString(cardNo));
            if (VerifyUtil.verifyEntityField(cardNo)) {
                return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
            }
            Object data = iBankCodenameDataService.getBankCodenameDataDetailByCode(cardNo.getCardNo()).getData();
            logger.info("返回数据:" + JSON.toJSONString(data));
            if (data != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS, data);
            } else {
                return ResultUtil.getResult(MechantAppResultEnum.NO_BANK_MESSAGE);
            }

        } catch (Exception e) {
            logger.error("获取银行卡名称失败", e);
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 商户申请
     *
     * @author 熊克文
     * @时间 2017-7-25
     */
    @RequestMapping(value = "/applyMerchant", method = RequestMethod.POST)
    public Result applyMerchant(MerchantDTO merchantDTO,
                                @RequestParam(name = "verificationCode") String verificationCode) {
        // 1.短信验证
        /*
         * SmsSendData sendData = new SmsSendData();
		 * sendData.setMobile(merchantDTO.getTelPhone());
		 * sendData.setSmsCode(verificationCode);
		 * sendData.setType(SmsCodeType.MER_REGISTER_VC);
		 * sendData.setSourceEnum(SmsSourceEnum.MERCHANT); Result result =
		 * this.smsDataService.checkSmsCodeByMobile(sendData);
		 * 
		 * if (!result.isSuccess()) { return result; }
		 */
        Result result = new Result();
        merchantDTO.setCreaterId(0L);
        // 设置审核等级
        merchantDTO.setStatusLevel(SPMEnum.agentLevel.ADMIN.getIndex());
        logger.info("商户申请参数:merchantDTO = {}", JSON.toJSONString(merchantDTO));
        result = this.iMerchantService.addMerchant(merchantDTO);
        result.setCode(result.isSuccess() ? "200" : "500");
        return result;
    }

    /**
     * 商户更新门店地址
     *
     * @author 熊克文
     * @时间 2017-7-25
     */
    @RequestMapping(value = "/updateMerchant", method = RequestMethod.POST)
    @AccessToken
    public Result updateMerchant(@RequestParam(name = "address") String address,
                                 @RequestParam(name = "latitude") String latitude, @RequestParam(name = "longitude") String longitude) {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUserId(getUserId());
        MerchantVO merchantVO = this.iMerchantService.getMerchantDetailById(merchantDTO);
        if (merchantVO == null) {
            return new Result(false, "500", "当前商户不存在");
        }

        Merchant update = new Merchant();
        update.setId(merchantVO.getId());
        update.setAddress(address);
        update.setLatitude(latitude);
        update.setLongitude(longitude);
        return this.iMerchantService.updateMerchantSingle(update);
    }

    /**
     * 商户忘记密码获取验证码
     *
     * @author 熊克文
     * @时间 2017-7-25
     */
    @RequestMapping(value = "/getVerificationCode/{phone}/{type}/{mobileType}", method = RequestMethod.GET)
    public Object getVerificationCode(@PathVariable String phone, @PathVariable String type,
                                      @PathVariable String mobileType) {
        Result result = new Result();
        try {
            // 判断注册和忘记密码；mobileType==1注册；mobileType==2忘记密码
            if (StringUtils.isBlank(phone) && StringUtils.isBlank(mobileType) && StringUtils.isBlank(type)) {
                result = ResultUtil.getResult(SmsStatusEnum.PARAM_INCOMPLETE);
                return result;
            }

           Long id = this.iMerchantService.CheckPhone(phone);
             /*if (mobileType.equals("1") && null != id) {
                result.setCode("201");
                result.setMessage("该用户已注册，请直接登录");
                return result;
            }*/

            if (mobileType.equals("2") && null == id) {
                result.setCode("201");
                result.setMessage("当前用户不存在!");
                return result;

            }

            SmsSendData sendData = new SmsSendData();
            sendData.setMobile(phone);
            sendData.setType(SmsCodeType.getSmsCodeTypeByCode(type));
            // sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
            // sendData.setSmsCode(SmsCodeUtil.getSmsCode());
            sendData.setModelData(mobileType);
            //sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
            //sendData.setSmsCode(SmsCodeUtil.getSmsCode());
            result = smsDataService.sendSmsHaveCode(sendData);
            logger.info("商户忘记密码验证码，返回参数result = {}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.getResult(RespCode.Code.FAIL);
    }

    /**
     * 商户修改登录密码
     *
     * @author 熊克文
     * @时间 2017-7-25
     */
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public Object forgetPassword(@RequestParam(name = "merchantPhone") String merchantPhone,
                                 @RequestParam(name = "password") String password,
                                 @RequestParam(name = "verificationCode") String verificationCode,
                                 @RequestParam(name = "type") String type) {
        // 1.短信验证
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(merchantPhone);
        sendData.setSmsCode(verificationCode);
        sendData.setType(SmsCodeType.MER_REGISTER_FR);
        Result result = this.smsDataService.checkSmsCodeByMobile(sendData);
        if (result.isSuccess()) {
            User user = new User();
            user.setTelphone(merchantPhone);
            // 1.密码长度限制
            if (6 > password.length() || password.length() > 16) {
                result.setCode("0");
                result.setMessage("请输入的密码长度不要低于6高于16");
                return result;
            }
            // 2.过滤特殊字符
            String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(password);
            boolean find = m.find();
            m.replaceAll("").trim();
            if (find) {
                result.setCode("0");
                result.setMessage("密码格式不正确");
                return result;
            }
            // 3.过滤表情符号
            password.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
            user.setPassword(MD5.getMD5Str(password));
            result = iUserService.updatePassword(user);
            result.setCode("200");
        }
        return result;
    }

    /**
     * @Title: updatePayPwd @Description: 修改支付密码 @param: @param
     * merchantPhone @param: @param password @param: @param
     * verificationCode @param: @param type @param: @return @return:
     * Object @author：李杰 @throws
     */
    @RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST)
    public Object updatePayPwd(@RequestParam(name = "merchantPhone") String merchantPhone,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "verificationCode") String verificationCode,
                               @RequestParam(name = "type") String type) {

        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(merchantPhone);
        sendData.setSmsCode(verificationCode);
        sendData.setType(SmsCodeType.getSmsCodeTypeByCode(type));
        sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
        Result result = this.smsDataService.checkSmsCodeByMobile(sendData);
        //密码验证
        // 1.密码长度限制
        if (6 > password.length() || password.length() > 16) {
            result.setCode("0");
            result.setMessage("请输入的密码长度不要低于6高于16");
            return result;
        }
        // 2.过滤特殊字符
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        boolean find = m.find();
        m.replaceAll("").trim();
        if (find) {
            result.setCode("0");
            result.setMessage("密码格式不正确");
            return result;
        }
        // 3.过滤表情符号
        password.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        if (result.isSuccess()) {
            Long userId = getUserId();
            PayPasswordUpdateDTO dto = new PayPasswordUpdateDTO();
            dto.setCustomerType(PayPwdEnum.PLATFORM_PAY_PWD.getCode());
            dto.setNewPassword(password);
            dto.setUpdaterId(userId);
            dto.setUserId(userId);
            result = iPayPasswordService.updatePayPassword(dto);
        }
        logger.info("修改支付密码 返回参数 = {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 找回账号
     *
     * @param merchantPhone
     * @param verificationCode
     * @return
     */
    @RequestMapping(value = "findaccount", method = RequestMethod.POST)
    @ResponseBody
    public Result findAccount(@RequestParam(name = "merchantPhone") String merchantPhone,
                              @RequestParam(name = "verificationCode") String verificationCode) {
        Result result = ResultUtil.getResult(RespCode.Code.NO_ORDER);
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(merchantPhone);
        sendData.setSmsCode(verificationCode);
        result = this.smsDataService.checkSmsCodeByMobile(sendData);
        if (result.isSuccess()) {
            List<Map> msgMap = iMerchantService.getMerchantMsgByPhone(merchantPhone);
            if (msgMap.size() != 0 && msgMap != null) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS, msgMap);
            } else {
                result.setData("[]");
                result.setMessage("该手机号未注册");
                result.setCode("200");
                result.setSuccess(true);
                return result;
            }
        }
        return result;
    }


    @AccessToken
    @RequestMapping(value = "/setPayPwd", method = RequestMethod.POST)
    public Object setPayPwd(@RequestParam(name = "merchantPhone") String merchantPhone,
                            @RequestParam(name = "password") String password,
                            @RequestParam(name = "verificationCode") String verificationCode,
                            @RequestParam(name = "type") String type) {
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(merchantPhone);
        sendData.setSmsCode(verificationCode);
        sendData.setType(SmsCodeType.getSmsCodeTypeByCode(type));
        sendData.setSourceEnum(SmsSourceEnum.MERCHANT);
        logger.info("===================" + getUserId());
        Result result = this.smsDataService.checkSmsCodeByMobile(sendData);
        if (result.isSuccess()) {
            Long userId = getUserId();

            PayPasswordAddDTO dto = new PayPasswordAddDTO();
            dto.setCustomerType(PayPwdEnum.PLATFORM_PAY_PWD.getCode());
            dto.setNewPassword(password);
            dto.setCreaterId(userId);
            dto.setUserId(userId);
            result = iPayPasswordService.addPayPassword(dto);
        }
        logger.info("设置支付密码 返回参数 result = {}", JSON.toJSONString(result));
        return result;
    }

    @RequestMapping(value = "/verifyPayPwdIsExists", method = RequestMethod.GET)
    public Object verifyPayPwdIsExists() {
        Long userId = getUserId();
        PayPasswordQueryDTO dto = new PayPasswordQueryDTO();
        dto.setCustomerType(PayPwdEnum.PLATFORM_PAY_PWD.getCode());
        dto.setUserId(userId);
        Result result = iPayPasswordService.verifyPayPwdIsExists(dto);
        logger.info("校验是否存在支付密码 返回参数 result = {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @Title: getShareRecord @Description:根据商户手机号获取分享时绑定的会员信息 @param: @param
     * telPhone @param: @return @return: Object @author：李杰 @throws
     */
    @RequestMapping(value = "/getShareRecord/{telPhone}", method = RequestMethod.GET)
    public Object getShareRecord(@PathVariable String telPhone) {
        MemberQueryShareDTO dto = new MemberQueryShareDTO();
        dto.setToTelPhone(telPhone);
        dto.setType((byte) 1);
        Result result = iMemberService.getMemberShare(dto);
        logger.info("根据手机号查询分享绑定记录 返回数据 Result = {}", JSON.toJSONString(result));
        return result;
    }

    /***
     * 用户先用手机号注册,在调用另一个接口验证身份(验证手机号是否符合要求)
     *
     * @return
     */
    @RequestMapping(value = "registerbyphone", method = RequestMethod.POST)
    public Result UserMemeberNewRegisterByPhone(MerchantDTO merchantDTO,
                                                @RequestParam(name = "verificationCode") String verificationCode) {
        // 1.短信验证
        SmsSendData sendData = new SmsSendData();
        sendData.setMobile(merchantDTO.getTelPhone());
        sendData.setSmsCode(verificationCode);
        sendData.setType(SmsCodeType.MER_REGISTER_FR);
        Result result = this.smsDataService.checkSmsCodeByMobile(sendData);

        if (!result.isSuccess()) {
            return result;
        }
        String phone = merchantDTO.getTelPhone();
        String password = merchantDTO.getPassword();
        String imageCode = verificationCode;
        result = iMerchantService.UserMemeberNewRegisterCheckPhone(phone, password, imageCode);
        return result;
    }

    /*
     * 完善门店信息接口
     *
     * @lzh
     */
    @RequestMapping(value = "updateMerchantInfo", method = RequestMethod.POST)
    public Result updateMerchantInfo(MerchantDTO merchant) {
        // 获取商户基本信息再修改营业时间和人均消费
        Result result = iMerchantService.updateMerchantInfo(merchant);

        return result;
    }

    /**
     * 修改商户信息（营业时间 人均消费）
     *
     * @lzh
     */
    @RequestMapping(value = "updateMerchantInfoById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateMerchantInfoById(String openBeginTime, String openEndTime, String closeBeginTime,
                                         String closeEndTime, Long costMoney, Long merchantId) {

        // 修改营业时间和人均消费
        Result result = iMerchantService.updateMerchantInfoById(openBeginTime, openEndTime, closeBeginTime,
                closeEndTime, costMoney, merchantId);

        return result;
    }

    /**
     * 输入激活码验证
     */
    @RequestMapping(value = "provCodeByMerchanrId", method = RequestMethod.POST)
    @ResponseBody
    public Result provCodeByMerchanrId(String cdKey, Long merchantId) {
        // 修改营业时间和人均消费
        Result result = iMerchantService.provCodeByMerchanrId(cdKey, merchantId);

        return result;
    }


    @RequestMapping(value = "sendcodebeforepay/{phone}/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Result sendCodeBeforePay(@PathVariable String phone, @PathVariable String type) {
        CashDTO cashDTO = new CashDTO();
        Result result = ResultUtil.getResult(SmsStatusEnum.OVERTIME);
        if (phone == null || "".equals(phone)) {
            return ResultUtil.getResult(RespCode.Code.FAIL, "手机号不能为空");
        }
       /* Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            result.setCode("0");
            result.setMessage("不符合手机号格式");
            return result;
        }*/
        //cashDTO.setCodeType("Fr170002");
        cashDTO.setKfPhone("400–8586–315");
        cashDTO.setYTime("1");
        cashDTO.setPhone(phone);
        switch (type) {
            case "1":
                cashDTO.setOperation("修改登录密码");
                break;
            case "2":
                cashDTO.setOperation("设置支付密码");
                break;
            case "3":
                cashDTO.setOperation("修改支付密码");
                break;
            case "4":
                cashDTO.setOperation("线下收");
                break;
            case "5":
                cashDTO.setOperation("找回账号");
        }

        logger.info("短信验证码开始发送>> 手机号：" + phone);
        Result check = smsUtil.sendCashMsg(cashDTO);
        if ("1".equals(check.getCode())){
            return ResultUtil.getResult(RespCode.Code.SUCCESS, "发送成功");
        }else {
            return ResultUtil.getResult(RespCode.Code.FAIL, check.getMessage());
        }
    }

    @RequestMapping(value = "checkpaycode", method = RequestMethod.POST)
    @ResponseBody
    public Result checkCodeByPay(CheckDTO checkDTO) {
        Result result = ResultUtil.getResult(RespCode.Code.CODE_ERROR);

        checkDTO.setCodeType("Fr170002");
        Result check = smsUtil.check(checkDTO);
        if ("1".equals(check.getCode())){
            return ResultUtil.getResult(RespCode.Code.SUCCESS, "验证成功");
        }else {
            return ResultUtil.getResult(RespCode.Code.FAIL, check.getMessage());
        }

    }

    /**
     * 手机版本跟新状态
     *
     * @return
     */
    @RequestMapping("/appversion")
    @ResponseBody
    public Result getAppVersion(@RequestParam("type") Integer type, String client_type) {

        return iAdAttachment.getAppVersion(type, client_type);
    }

    /**
     * 根据手机号查找是否已经注册了商户
     *
     * @return
     */
    @RequestMapping(value = "getmsgbyphone", method = RequestMethod.POST)
    @ResponseBody
    public Result getMerchantMsgByPhone(String phone) {
        Result result = new Result();
        Map<String, Object> map = new HashMap();

        List<Map> msgMap = iMerchantService.getMerchantMsgByPhone(phone);
        if (msgMap != null && msgMap.size() != 0 ) {
            String a = msgMap.get(msgMap.size() - 1).get("telPhone").toString();
            if (a.length() == 11) {
                String registphone = a + "01";
                map.put("registphone", registphone);
            } else {
                long b = Long.parseLong(a) + 1;
                map.put("registphone", b);
            }
            map.put("list", msgMap);
            result.setCode("200");
            result.setData(map);
            result.setMessage("操作成功");
            return result;
        }
        Long count = iMerchantService.getPhoneByPermission(phone);
        if(count > 0 ){
            result.setCode("10000");
            result.setMessage("该手机号已认证其他角色");
            return result;
        }
        return ResultUtil.getResult(RespCode.Code.SUCCESS);
    }

    /**
     * 添加店铺经理发送验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "tocodestroemamager", method = RequestMethod.POST)
    @ResponseBody
    public Result toCodeByAddStroeManager(String phone) {
        CommonDTO commonDTO = new CommonDTO();
        commonDTO.setPhone(phone);
        commonDTO.setCodeType("KHadd");
        Result sendCommonMsg = smsUtil.sendCommonMsg(commonDTO);
        if ("1".equals(sendCommonMsg.getCode())) {
            return ResultUtil.getResult(RespCode.Code.SUCCESS, "发送验证码成功");
        } else {
            return ResultUtil.getResult(RespCode.Code.FAIL,sendCommonMsg.getMessage());
        }
    }

    @RequestMapping(value = "checkpaycodebymanager", method = RequestMethod.POST)
    @ResponseBody
    public Result checkCodeByaddStroeManager(CheckDTO checkDTO) {
        checkDTO.setCodeType("KHadd");
        Result check = smsUtil.check(checkDTO);
        if ("1".equals(check.getCode())){
            return ResultUtil.getResult(RespCode.Code.SUCCESS, "验证成功");
        }else {
            return ResultUtil.getResult(RespCode.Code.FAIL, check.getMessage());
        }
    }

    /**
     * 添加店面经理
     *
     * @param name  经理名称
     * @param code  验证码
     * @param phone 手机号
     * @return
     */
    @RequestMapping(value = "addstroemanager", method = RequestMethod.POST)
    @ResponseBody
    public Result addStoreManager(String name, String phone, String code, Long merchantId) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        CheckDTO checkDTO = new CheckDTO();
        checkDTO.setCode(code);
        checkDTO.setPhone(phone);
        result = checkCodeByaddStroeManager(checkDTO);
        if (result.isSuccess()) {
            result = iMerchantService.StroeManager(phone, name, merchantId);
            if (result.isSuccess()) {
                //更新member 的状态
               Member member= iMemberService.findMemberByMobile(phone);
                iMerchantService.updateStoreManager(member.getId(),merchantId);
                return ResultUtil.getResult(RespCode.Code.SUCCESS, "添加成功");
            }
        }

        return result;
    }

    /**
     * 商户解聘店面经理
     *
     * @return
     */
    @RequestMapping(value = "dismissal", method = RequestMethod.POST)
    @ResponseBody
    public Result dismissalStorManager(Long stroeManagerId) {

        if (stroeManagerId == null) {
            return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
        }

        iStoreManagerService.updateStoreManager(stroeManagerId);

        return ResultUtil.getResult(RespCode.Code.SUCCESS);
    }

}
