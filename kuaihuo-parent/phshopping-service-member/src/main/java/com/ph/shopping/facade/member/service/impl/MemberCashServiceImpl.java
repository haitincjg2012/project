package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.PuhuiConfig;
import com.ph.shopping.common.core.config.pay.PayConfiguration;
import com.ph.shopping.common.core.customenum.MessageEnum;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import com.ph.shopping.common.core.sms.HttpClientUtils;
import com.ph.shopping.common.core.util.PuhuiUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.date.UtilDate;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.Base64;
import com.ph.shopping.common.util.rsa.RSAUtils;
import com.ph.shopping.common.util.string.StringTools;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.MemberDrawcashRecordMapper;
import com.ph.shopping.facade.member.dto.MemberCashDTO;
import com.ph.shopping.facade.member.entity.MemberDrawcashRecord;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IMemberCashService;
import com.ph.shopping.facade.member.vo.MemberInfoByCashVO;
import com.ph.shopping.facade.pay.constant.CashConstant;
import com.ph.shopping.facade.pay.dto.CashReceiveStation;
import com.ph.shopping.facade.pay.exception.CashExceptionEnum;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import com.ph.shopping.facade.pay.utils.union.UniqueUtils;
import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import com.ph.shopping.facade.score.exception.ScoreException;
import com.ph.shopping.facade.score.exception.ScoreExceptionEnum;
import com.ph.shopping.facade.score.service.IScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component
@Service(version = "1.0.0")
public class MemberCashServiceImpl implements IMemberCashService {

    private static final Logger log = LoggerFactory.getLogger(MemberCashServiceImpl.class);

    @Autowired
    MemberDrawcashRecordMapper memberDrawcashRecordMapper;

    @Reference(version = "1.0.0", retries = 0)
    IScoreService scoreService;

    @Autowired
    private PayConfiguration payConfiguration;

    @Autowired
    private PuhuiConfig puhuiConfig;

    @Override
    public int getOrderCountByOrderNum(String orderNum2) {
        return memberDrawcashRecordMapper.getOrderCountByOrderNum(orderNum2);
    }


    @Override
    public Result getMemberInfo(Long memberId) {
        log.info("获取会员详情,memberId={}", JSON.toJSON(memberId));
        Result result = ResultUtil.getResult(MemberResultEnum.Code.FAIL);
        try {
            MemberInfoByCashVO mb = memberDrawcashRecordMapper.getMemberInfo(memberId);

            if (!StringUtils.isEmpty(mb)) {
                // 查询是否已进行实名认证
                if (mb.getCertification() != 2) {
                    return ResultUtil.getResult(MemberResultEnum.ID_NOT_CERTIFIED);
                }
                // 判断是否绑定银行卡
                if (StringUtils.isEmpty(mb.getBankCard())) {
                    return ResultUtil.getResult(MemberResultEnum.NO_CHECKBIND_EXCEPTION);
                }
                // 判断是否设置支付密码
                if (StringUtils.isEmpty(mb.getPayPwd())) {
                    return ResultUtil.getResult(MemberResultEnum.PAY_PWD_EMPTY);
                }
                // 查询改用户当天提现的积分总和
                Long cashedScore = memberDrawcashRecordMapper.getSumScoreThisDay(memberId, getCurrentDate());
                if (null != cashedScore && cashedScore.longValue() > CashConstant.MAX_CASH_MONEY_EVERY_DAY) {
                    return ResultUtil.getResult(CashExceptionEnum.CASH_MONEY_PASS_MAX_ERROR);
                }
                // 计算真实积分
                mb.setScore(MoneyTransUtil.transMulti1(mb.getScore()));
                mb.setBankCard(StringTools.bankNoChange(mb.getBankCard()));
                mb.setRealName(StringTools.realNameChange(mb.getRealName()));
                result = ResultUtil.getResult(MemberResultEnum.Code.SUCCESS, mb);
            } else {
                result = ResultUtil.getResult(MemberResultEnum.MEMBERUSER_NON_EXISTENT);
            }
        } catch (Exception e) {
            log.info("获取会员详情异常,e={}", e);
        }
        return result;
    }


    @Transactional
    public Result memberGiftCash(MemberCashDTO memberCashDTO, String token) {
        log.info("会员消费红利提现流程参数，memberCashDTO={}", JSON.toJSONString(memberCashDTO));
        if (Objects.isNull(memberCashDTO) || Objects.isNull(memberCashDTO.getId())
                || Objects.isNull(memberCashDTO.getCashScore()) || StringUtils.isEmpty(token)) {
            return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
        }
        MemberInfoByCashVO mch = memberDrawcashRecordMapper.getMemberInfo(memberCashDTO.getId());
        if (Objects.isNull(mch)) {
            return ResultUtil.setResult(false, "用户信息不存在");
        }
        if (Objects.isNull(memberCashDTO.getCashScore()) || memberCashDTO.getCashScore().longValue() <= 0) {
            return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
        }
        if (memberCashDTO.getCashScore().longValue()<100 || memberCashDTO.getCashScore().longValue()>10000){
            return ResultUtil.getResult(MessageEnum.BALANCE_BETWEEN);
        }
        //提现积分
        Long cashScore = memberCashDTO.getCashScore()*10001;

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        String tt;
        String gg;
        try {
            String response = restTemplate.postForObject(puhuiConfig.getSelectScore(), map, String.class);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
                JSONObject jsonData = JSON.parseObject(response);
                if (jsonData != null && !jsonData.equals("")) {
                    JSONObject data = jsonData.getJSONObject("data");
                    tt = data.getString("tt");
                    gg = data.getString("gg");
                } else {
                    return ResultUtil.setResult(false, "用户消费红利积分为空");
                }
            } else {
                return ResultUtil.setResult(false, "用户消费红利积分为空");
            }
        } catch (Exception e) {
            log.error("查询用户消费红利积分失败：{}", e.getMessage());
            return ResultUtil.setResult(false,"用户积分获取失败");
        }
        MemberDrawcashRecord mdr = new MemberDrawcashRecord();
        try {

            //生成代付交易批次号
            String batchNo = UniqueUtils.getBathNo();
            Long score = cashScore - CashConstant.HANDING_CHARGE;
            mdr.setMemberId(memberCashDTO.getId());
            if (memberCashDTO.getCashType() == TransCodeEnum.GUAGUALE_SCORE_CASH.getCode()) {
                mdr.setOrderNo("KHTXHB" + UtilDate.getOrderNum() + UtilDate.getThree());
            } else {

                mdr.setOrderNo("KHTXFH" + UtilDate.getOrderNum() + UtilDate.getThree());
            }
            mdr.setScore(new BigDecimal(score));
            mdr.setHandingCharge(CashConstant.HANDING_CHARGE);
            mdr.setMemberName(mch.getRealName());
            mdr.setBankCardNo(mch.getBankCard());
            mdr.setBatchNo(batchNo);
            mdr.setStatus((byte) 0);
            mdr.setTradeState("CREATE");//交易状态 处理中
            mdr.setRequestIp(memberCashDTO.getRequestIP());
            mdr.setAuditState((byte) 0);
            mdr.setCreaterId(memberCashDTO.getId());
            mdr.setCreateTime(new Date());
            mdr.setMemberName(mch.getMemberName());
            mdr.setBankCardNo(mch.getBankCard());
            mdr.setTransCode(memberCashDTO.getCashType());

            map.add("cashScore", String.valueOf(memberCashDTO.getCashScore()));
            map.add("rate", "1");
            map.add("phone", mch.getPhone());
            map.add("origin", "KH");
            map.add("bankCardNo", mch.getBankCard());
            map.add("bankName", mch.getBankName());
            map.add("cardNo", mch.getIdCardNo());
            map.add("realName", mch.getRealName());
            map.add("ip", memberCashDTO.getRequestIP());

            if (memberCashDTO.getCashType() == TransCodeEnum.GUAGUALE_SCORE_CASH.getCode()) {
                map.add("orderNo", mdr.getOrderNo());
                map.add("type", "1");
            } else {
                map.add("type", "0");
                map.add("orderNo", mdr.getOrderNo());
            }
            log.info("调用积分提现入参:{}", JSON.toJSON(map));
            String json = JSONObject.toJSONString(map);
            log.info("加密前数据:{}",json);
            byte[] encoderByteArray = RSAUtils.encryptByPublicKey(json.getBytes(), PuhuiUtil.PUBLIC_KEY,"");
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            requestEntity.add("json", Base64.encode(encoderByteArray));
            String responseEntity = restTemplate.postForObject(puhuiConfig.getCash(), requestEntity, String.class);
            log.info("积分提现返回结果:{}", responseEntity);
            if (!StringUtils.isEmpty(responseEntity)) {
                JSONObject jsonData = JSON.parseObject(responseEntity);
                if (Objects.nonNull(jsonData)) {
                    if ("200".equals(jsonData.getString("code"))) {
                        log.info("订单号:{},提现成功",mdr.getOrderNo());
                        mdr.setTradeState("CALLBACK");
                        log.info("积分提现成功:{}",JSON.toJSON(mdr));
                        return ResultUtil.getResult(RespCode.Code.SUCCESS);
                    }
                    mdr.setTradeState("FAIL");
                    log.info("订单号:{},调用提现接口,返回值code码不是200,提现失败.",mdr.getOrderNo());
                }
                log.info("订单号:{},调用提现接口,返回值为空,提现失败",mdr.getOrderNo());
            }
            log.info("订单号:{},调用提现接口,返回值为空,提现失败",mdr.getOrderNo());
            log.info("积分提现失败:{}",JSON.toJSON(mdr));
            return ResultUtil.setResult(false, "提现失败");
        } catch (Exception e) {
            log.error("提现失败,异常信息:{}", e.getMessage());
            return ResultUtil.setResult(false, "提现失败");
        }
    }


    @Transactional
    @Override
    public Result memberCash(MemberCashDTO memberCashDTO) {
        log.info("会员积分提现流程参数，memberCashDTO={}", JSON.toJSONString(memberCashDTO));
        Result result = ResultUtil.getResult(MemberResultEnum.Code.FAIL);
        if (Objects.isNull(memberCashDTO) || Objects.isNull(memberCashDTO.getId())
                || Objects.isNull(memberCashDTO.getCashScore())) {
            return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
        }
        if (TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode() != memberCashDTO.getCashType() &&
                TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode() != memberCashDTO.getCashType()
                && TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode() != memberCashDTO.getCashType()) {
            return ResultUtil.setResult(false, "请求参数异常");
        }

        if (memberCashDTO.getCashScore().longValue() <= 0) {
            return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
        }
        if (memberCashDTO.getCashScore().longValue()<100 || memberCashDTO.getCashScore().longValue()>10000){
            return ResultUtil.getResult(MessageEnum.BALANCE_BETWEEN);
        }

        PhScoreTotalTrade scoreTotalTrade = memberDrawcashRecordMapper.getScore(memberCashDTO.getId());

        if (Objects.isNull(scoreTotalTrade)) {
            return ResultUtil.setResult(false, "积分信息不存在");
        }

        MemberInfoByCashVO mch = memberDrawcashRecordMapper.getMemberInfo(memberCashDTO.getId());
        MemberDrawcashRecord mdr = null;
        long memberTradeScore = memberCashDTO.getCashScore() * 10000;
        long cashScore = memberCashDTO.getCashScore() * 10000l;
        try {
            //推广会员
            if (TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode() == memberCashDTO.getCashType() && scoreTotalTrade.getShareMemberScore() < cashScore) {
                return ResultUtil.setResult(false, "积分不足");
            }
            //推广商户
            if (TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode() == memberCashDTO.getCashType() && scoreTotalTrade.getShareMerchantScore() < cashScore) {
                return ResultUtil.setResult(false, "积分不足");
            }
            //门店代理
            if (TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode() == memberCashDTO.getCashType() && scoreTotalTrade.getStoreManagerScore() < cashScore) {
                return ResultUtil.setResult(false, "积分不足");
            }
            //生成代付交易批次号
            String batchNo = UniqueUtils.getBathNo();
            Long score = cashScore - CashConstant.HANDING_CHARGE;
            //新增提现记录
            mdr = new MemberDrawcashRecord();
            mdr.setMemberId(memberCashDTO.getId());

            if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode()) {

                mdr.setOrderNo("KHTXHY" + UtilDate.getOrderNum() + UtilDate.getThree());
            } else if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode()) {

                mdr.setOrderNo("KHTXSH" + UtilDate.getOrderNum() + UtilDate.getThree());
            }else {
                mdr.setOrderNo("KHTXDL" + UtilDate.getOrderNum() + UtilDate.getThree());
            }
            mdr.setScore(new BigDecimal(score));
            mdr.setHandingCharge(CashConstant.HANDING_CHARGE);
            mdr.setMemberName(mch.getRealName());
            mdr.setBankCardNo(mch.getBankCard());
            mdr.setBatchNo(batchNo);
            mdr.setStatus((byte) 0);
            mdr.setRequestIp(memberCashDTO.getRequestIP());
            mdr.setAuditState((byte) 0);
            mdr.setTradeState("CREATE");
            mdr.setCreaterId(memberCashDTO.getId());
            mdr.setCreateTime(new Date());
            mdr.setMemberName(mch.getMemberName());
            mdr.setBankCardNo(mch.getBankCard());
            if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode()) {
                mdr.setTransCode(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode());
            } else if(memberCashDTO.getCashType() == TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode()) {
                mdr.setTransCode(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode());
            } else {
                mdr.setTransCode(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode());
            }
            memberDrawcashRecordMapper.insertUseGeneratedKeys(mdr);
            log.info("积分扣除入参:{}",memberCashDTO);
            //扣积分
            long count = memberScoreTrade(memberCashDTO.getId(), mdr.getTransCode(), -score, mdr.getOrderNo(), -CashConstant.HANDING_CHARGE, scoreTotalTrade,true);

            log.info("积分扣除返回结果：count={}", count);
            if (count <= 0) {
                throw new MemberException("积分扣除失败");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("调用pay工程之前，保存记录 出现异常，京东代付,方法异常,提现事务回滚,订单号:" + mdr.getOrderNo() +
                    ",卡号" + mdr.getOrderNo() + ",用户id:" + mdr.getMemberId());
            log.error("订单号:{},提现发生异常:{}", mdr.getOrderNo(), e.getMessage());
            return ResultUtil.setResult(false, "新增提现记录失败");
        }

        try {
            CashReceiveStation cashReceiveStation = new CashReceiveStation();
            cashReceiveStation.setCardNo(mdr.getBankCardNo());
            cashReceiveStation.setMerSeqId(mdr.getOrderNo());
            //单位为分
            cashReceiveStation.setMoney(mdr.getScore().divide(BigDecimal.valueOf(100)).longValue());
            cashReceiveStation.setCssId(mdr.getMemberId());
            cashReceiveStation.setPersonName(mch.getRealName());
            cashReceiveStation.setBankName(mch.getBankName());
            cashReceiveStation.setFromSystem(CodeConstExt.PAY_SYSTEM_FROM_KHAPP);
            cashReceiveStation.setComments(mch.getIdCardNo());
            cashReceiveStation.setCssType(3);
            cashReceiveStation.setBankAbbr("CMB");//银行简称
            if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode()) {
                cashReceiveStation.setCurrentBalance(BigDecimal.valueOf(scoreTotalTrade.getShareMemberScore()).divide(BigDecimal.valueOf(100)).longValue());
            } else if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode()) {
                cashReceiveStation.setCurrentBalance(BigDecimal.valueOf(scoreTotalTrade.getShareMerchantScore()).divide(BigDecimal.valueOf(100)).longValue());
            }else {
                cashReceiveStation.setCurrentBalance(BigDecimal.valueOf(scoreTotalTrade.getStoreManagerScore()).divide(BigDecimal.valueOf(100)).longValue());
            }

            String json = JSON.toJSONString(cashReceiveStation);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("json", json);
            log.info("调用pay工程打款，订单号：" + mdr.getOrderNo() + ",提现金额" + mdr.getScore() + ",用户ID为" + mdr.getId()
                    + ",用户姓名为：" + mdr.getMemberName() + ",银行卡号为：" + mdr.getBankCardNo() + ",时间：" + new Date());
            log.info("pay工程调用入参:{}", json);
            RestTemplate restTemplate = new RestTemplate();
            String content = restTemplate.postForEntity(payConfiguration.getCash(),map,String.class).getBody();
            log.info("pay工程返回结果:{}", content);
            //HttpResult httpResult = HttpClientUtils.sendPost(payConfiguration.getCash(), map);
            if (!StringUtils.isEmpty(content)) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                Result jsonResult = JSONObject.toJavaObject(jsonObject, Result.class);
                //成功
                if ("0".equals(jsonResult.getCode())) {
                    log.info("调用pay工程，提现成功,用户ID为：" + mdr.getId() + "返回参数Code：" + result.getCode() + "返回参数Msg："
                            + result.getMessage() + "返回参数Content:" + result.getData() + ",时间:" + new Date());
                    mdr.setStatus((byte) 0);
                    mdr.setTradeState("CALLBACK");
                    updateCashStatus(mdr);
                    // 失败
                } else if ("1".equals(jsonResult.getCode())) {
                    mdr.setStatus((byte) 2);
                    mdr.setTradeState("FAIL");
                    //添加手续费
                    //mdr.setScore(new BigDecimal(memberCashDTO.getCashScore()));
                    /*updateCashStatus(mdr);
                    updateMemberScore(mdr.getMemberId(), mdr.getTransCode(), cashScore);
                    addMemberIncomeTrade(mdr);*/
                    if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode()) {
                        mdr.setTransCode(TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode());
                        scoreTotalTrade.setShareMemberScore(scoreTotalTrade.getShareMemberScore()-memberTradeScore);
                    } else if (memberCashDTO.getCashType() == TransCodeEnum.SHARE_MERCHANT_CASH_SCORE.getCode()){
                        mdr.setTransCode(TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode());
                        scoreTotalTrade.setShareMerchantScore(scoreTotalTrade.getShareMerchantScore()-memberTradeScore);
                    }else {
                        scoreTotalTrade.setStoreManagerScore(scoreTotalTrade.getStoreManagerScore()-memberTradeScore);
                        mdr.setTransCode(TransCodeEnum.ORDER_STORE_MANAGER_PROFIT_TRANSCODE.getCode());
                    }
                    log.info("进入会员提现失败返积分流程,入参:{}",JSON.toJSONString(scoreTotalTrade));
                    memberScoreTrade(mdr.getMemberId(), mdr.getTransCode(), cashScore-CashConstant.HANDING_CHARGE, mdr.getOrderNo(),CashConstant.HANDING_CHARGE, scoreTotalTrade,false);
                } else {
                    log.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;0:请求成功)code为：" + result.getCode());
                    return ResultUtil.setResult(false, "调用pay工程,请求返回结果状态code有误");
                }
            } else {
                log.info("调用pay工程,请求返回结果result为空");
                return ResultUtil.setResult(false, "调用pay工程,请求返回结果result为空");
            }

            result = ResultUtil.getResult(CashExceptionEnum.Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("调用pay工程,请求返回结果状态code有误，(1:请求失败;0:请求成功)code为:{},异常信息为:{}", result.getCode(), e.getMessage());
            return ResultUtil.setResult(false, "调用pay工程,请求返回结果状态code有误");
        }
        log.info("会员积分提现返回结果, result={}", JSON.toJSONString(result));
        return result;
    }

    private void addMemberIncomeTrade(MemberDrawcashRecord mdr) throws Exception {
        log.info("新增提现记录:{}", JSON.toJSON(mdr));
        if (memberDrawcashRecordMapper.addMemberIncomeTrade(mdr) != 1) {
            throw new Exception("提现失败新增收入记录失败!");
        }

    }

    private void updateCashStatus(MemberDrawcashRecord mdr) throws Exception {
        log.info("更新提现状态:{}", JSON.toJSON(mdr));
        if (memberDrawcashRecordMapper.updateCashStatus(mdr) != 1) {

            throw new Exception("修改提现状态失败");
        }
    }

    /**
     *
     * @param memberId
     * @param transCode
     * @param score
     * @param orderNo
     * @param handingCharge
     * @param scoreTotalTrade
     * @param withdraw 是否提现成功
     * @return
     */
    private long memberScoreTrade(long memberId, int transCode, long score, String orderNo,
                                  long handingCharge, PhScoreTotalTrade scoreTotalTrade,boolean withdraw) {

        try {
            log.info("=====会员提现维护积分=====,transCode={},orderNo={},scoreTotalTrade={}",transCode,orderNo,JSON.toJSON(scoreTotalTrade));
            long scoreEx = score + handingCharge;
            updateMemberScore(memberId, transCode, scoreEx);
            //long setId = getSetId(memberId, score, orderNo, transCode, handingCharge,scoreTotalTrade);
            scoreTotalTrade.setMemberId(memberId);
            scoreTotalTrade.setScore(score);
            scoreTotalTrade.setOrderNo(orderNo);
            scoreTotalTrade.setHandingCharge(handingCharge);
            scoreTotalTrade.setTransCode(transCode);
            if (transCode == TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()) {
                scoreTotalTrade.setTotelScore(scoreTotalTrade.getShareMemberScore() + score + handingCharge);
            } else if (transCode == TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()) {
                scoreTotalTrade.setTotelScore(scoreTotalTrade.getShareMerchantScore() + score + handingCharge);
            }else {
                scoreTotalTrade.setTotelScore(scoreTotalTrade.getStoreManagerScore()+score+handingCharge);
            }
            if (!withdraw){
                if (transCode == TransCodeEnum.ORDER_SHARE_MEMBER_PROFIT_TRANSCODE.getCode()) {
                    scoreTotalTrade.setTransCode(TransCodeEnum.ORDER_SHARE_MEMBER_WITHDRAW_FAIL_TRANSCODE.getCode());
                } else if (transCode == TransCodeEnum.ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE.getCode()) {
                    scoreTotalTrade.setTransCode(TransCodeEnum.ORDER_SHARE_MERCHANT_WITHDRAW_FAIL_TRANSCODE.getCode());
                }else {
                    scoreTotalTrade.setTransCode(TransCodeEnum.ORDER_STORE_MANAGER_WITHDRAW_FAIL_TRANSCODE.getCode());
                }
                insertMemberTrade(scoreTotalTrade);
            }else {
                insertMemberTrade(scoreTotalTrade);
            }

            long setId = scoreTotalTrade.getId();
            insertCostTrade(memberId, score, transCode, setId, orderNo, handingCharge);
           /* log.info("=====会员提现失败开始返回可用积分=====");
            // memberScoreTrade(memberId, TransCodeEnum.MEMBER_DRAWCASH_FAIL_REDUCE, scoreEx, orderNo, handingCharge);
            log.info("=====会员提现失败积分处理流程完成=====");*/
            return setId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScoreException(ScoreExceptionEnum.SCORE_EXCEPTION);
        }

    }


    /**
     * 记入支出流水
     */
    private void insertCostTrade(long memberId, long score, int transCode, long setId, String orderNo,
                                 long handingCharge) throws Exception {
        if (memberDrawcashRecordMapper.insertCostTrade(memberId, transCode, score, setId, orderNo, handingCharge) != 1) {
            throw new Exception("记入支出流水失败!");
        }
    }


    /**
     * 更新用户积分
     */
    private void updateMemberScore(long memberId, int transCode, long enableScore)
            throws Exception {
        if (memberDrawcashRecordMapper.updateMemberScore(memberId, transCode, enableScore) != 1) {
            throw new Exception("更新用户积分失败!");
        }
    }


    private long getSetId(long memberId, long score, String orderNo, int transCode, long handingCharge, PhScoreTotalTrade scoreTotalTrade)
            throws Exception {
        scoreTotalTrade.setMemberId(memberId);
        scoreTotalTrade.setScore(score);
        scoreTotalTrade.setOrderNo(orderNo);
        scoreTotalTrade.setTransCode(transCode);
        scoreTotalTrade.setHandingCharge(handingCharge);

        if (transCode == TransCodeEnum.SHARE_MEMBER_CASH_SCORE.getCode()) {

            scoreTotalTrade.setTotelScore(scoreTotalTrade.getShareMemberScore() + score + handingCharge);
        } else {

            scoreTotalTrade.setTotelScore(scoreTotalTrade.getShareMerchantScore() + score + handingCharge);
        }

        insertMemberTrade(scoreTotalTrade);

        return scoreTotalTrade.getId();
    }

    /**
     * 记入总流水
     */
    private int insertMemberTrade(PhScoreTotalTrade totalTrade) throws Exception {
        int ret = memberDrawcashRecordMapper.insertMemberTrade(totalTrade);
        if (ret != 1l) {
            throw new Exception("记入总流水失败!");
        }

        return ret;
    }


    private Date getCurrentDate() throws ParseException {
        Date date = new Date();
        return DateUtil.parseDate(DateUtil.dateToStr(date, DateUtil.YMR_SLASH));
    }

    @Override
    public Result getDayCashScore(Long memberId) {
        Result result = ResultUtil.getResult(MemberResultEnum.Code.SUCCESS);
        Long cashedScore = null;
        //查询改用户当天提现的积分总和
        try {
            cashedScore = memberDrawcashRecordMapper.getSumScoreThisDay(memberId, getCurrentDate());
        } catch (ParseException e) {
            throw new MemberException(e.getMessage());
        }
        if (cashedScore == null) {
            cashedScore = 0l;
        }
        result.setData(new BigDecimal(cashedScore).divide(new BigDecimal(10000)));
        return result;
    }
}
