package com.ph.shopping.facade.pay.service.impl;

import cm.ph.shopping.facade.order.constant.OrderIsSettleEnum;
import cm.ph.shopping.facade.order.constant.OrderStatusEnum;
import cm.ph.shopping.facade.order.constant.OrderUnlineStatusEnum;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline;
import cm.ph.shopping.facade.order.entity.PurchaseMainOrder;
import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import cm.ph.shopping.facade.order.service.TradOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.lorne.tx.annotation.TxTransaction;
import com.ph.shopping.common.core.customenum.*;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.common.util.other.smssend.model.data.ConsumeRemindModelData;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.string.StringHelper;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.config.TaskExecuterServiceConfig;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.member.dto.message.JPushSendDTO;
import com.ph.shopping.facade.member.dto.message.JPushUserDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.message.PushMessageEnum;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.vo.MessageVO;
import com.ph.shopping.facade.pay.entity.MemberChargeRecord;
import com.ph.shopping.facade.pay.entity.PurchaseChargeRecord;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.enums.PaymentEnum;
import com.ph.shopping.facade.pay.exception.PayecoException;
import com.ph.shopping.facade.pay.exception.PayecoExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.service.impl.order.OnlineOrderService;
import com.ph.shopping.facade.pay.service.impl.order.PurchaseOrderService;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;
import com.ph.shopping.facade.pay.vo.UnlineOrderVO;
import com.ph.shopping.facade.profit.service.IUnLineSettleService;
import com.ph.shopping.facade.profit.vo.UnLineSettleOrderVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.spm.constant.ChargeConstant;
import com.ph.shopping.facade.spm.dto.UserChargeBackDTO;
import com.ph.shopping.facade.spm.service.IUserChargeService;
import com.ph.shopping.facade.spm.vo.CheckPayVO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.lorne.tx.annotation.TxTransaction;

import java.math.BigDecimal;
import java.util.*;

/**
 * @项目：phshopping-service-pay
 * @描述：支付、提现接口
 * @作者： liuy
 * @创建时间：2017年3月23日
 * @Copyright @2017 by liuy
 */
@Component
@Service(version = "1.0.0")
public class PayServiceImpl implements IPayService {
    private static final Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    // @Autowired
    // private AgentChargeRecordMapper algentChargeRecordMapper;
    //
    // @Autowired
    // private MerchantChargeRecordMapper merchantChargeRecord;
    //
    // @Autowired
    // private SupplierChargeRecordMapper supplierChargeRecordMapper;
    @Reference(version = "1.0.0")
    private IUserChargeService iUserChargeService;

    @Autowired
    MemberSubOrderOnlineMapper memberSubOrderOnlineMapper;

    @Autowired
    OnlineOrderService onlineOrderService;

    @Autowired
    MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    PurchaseChargeRecordMapper purchaseChargeRecordMapper;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    private MemberOrderUnlineMapper memberOrderUnlineMapper;

    @Autowired
    private TaskExecuterServiceConfig taskExecuterServiceConfig;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ISmsDataService smsDataService;

    @Reference(version = "1.0.0")
    private IMessageService messageService;

    @Reference(version = "1.0.0")
    private TradOrderService tradOrderService;

    @Reference(version = "1.0.0")
    private IScoreService scoreService;

    @Reference(version = "1.0.0")
    IUnLineSettleService iUnLineSettleService;

    @Transactional
    @TxTransaction
    public Result checkAndUpdateOrder(String orderNum, int payType, int payStatus, String tradeCode) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        String bizCode = OrderUtil.getOrderBizCode(orderNum);
        try {
            switch (bizCode) {
                case "XXDD":
                case "SMDD":
                case "XSDD":
                case "ZFDD":
                    //获取交易金额
                    Long orderMoney = memberOrderUnlineMapper.getOrderMoney(orderNum);
                    System.out.println("*********  当前订单金额是：" + orderMoney);
                    System.out.println("调用更新方法传入订单号：" + orderNum);
                    updateTradOrder(orderNum, orderMoney, payType, payStatus, tradeCode);
                    break;
                case "CZDD":
                    log.info("------------------------------------------进入易联支付");
                    UserChargeBackDTO userChargeBackDTO = new UserChargeBackDTO();
                    userChargeBackDTO.setChargeType(payType);
                    userChargeBackDTO.setOrderNo(orderNum);
                    userChargeBackDTO.setResponseCode(tradeCode);
                    // 判断支付状态
                    if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
                        userChargeBackDTO.setChargeStatus(ChargeConstant.CHARGED);
                        log.info("成功更新充值记录的参数userChargeBackDTO:" + userChargeBackDTO);
                        return iUserChargeService.updateUSerChargeRecord(userChargeBackDTO);

                    } else {
                        // 充值失败
                        userChargeBackDTO.setChargeStatus(ChargeConstant.UNCHARGE);
                        log.info("失败更新充值记录的参数userChargeBackDTO:" + userChargeBackDTO);
                        return iUserChargeService.updateUSerChargeRecord(userChargeBackDTO);
                    }
                case "JH01":
                    /**
                     * mdy 郑朋 2017-05-18 version-2.1
                     */
                    // 供应链子订单支付回调处理
                    PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selPurSubOrderByOrderNo(orderNum);
                    Long payUserId = purchaseSubOrder.getPurchaserId();
                    // 订单状态为待付款状态才会进行订单更新
                    if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode() && purchaseSubOrder.getStatus() == OrderStatusEnum.UNPAID_ORDER.getStatus()) {
                        purchaseOrderService.updateOrderBySubOrder(purchaseSubOrder, PaymentEnum.THIRD.getCode());
                    }
                    // 新增供应链支付记录
                    insertPurchaseChargeRecord(payUserId, orderNum, purchaseSubOrder.getMd5(), (byte) payStatus, tradeCode,
                            purchaseSubOrder.getTotalCost());
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "JHZD":
                    /**
                     * mdy 郑朋 2017-05-18 version-2.1
                     */
                    // 供应链用总订单支付回调处理
                    // 查询主订单信息
                    PurchaseMainOrder purchaseMainOrder = purchaseOrderMapper.selPurMainOrderByOrderNo(orderNum);
                    if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
                        // 修改主订单支付时间
                        purchaseOrderService.updateOrderByMainOrder(purchaseMainOrder, PaymentEnum.THIRD.getCode());
                    }
                    // 新增供应链支付记录
                    insertPurchaseChargeRecord(purchaseMainOrder.getPurchaserId(), orderNum, purchaseMainOrder.getMd5(),
                            (byte) payStatus, tradeCode, purchaseMainOrder.getTotalCost());
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;

                case "XS01":
                    // 会员订单更新
                    PhMemberSubOrderOnline po = memberSubOrderOnlineMapper.getMemberOrderByOrderNo(orderNum);
                    if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
                        // 支付成功,更新订单为待发货状
                        onlineOrderService.updateOrder(po.getId(), po.getCreaterId(), (byte) payType);
                    }
                    // 新增会员支付记录
                    MemberChargeRecord mr = new MemberChargeRecord();
                    mr.setOrderNo(po.getOrderNo());
                    mr.setScore(new BigDecimal(po.getOrderMoney()));
                    mr.setResponseCode(tradeCode);
                    mr.setMemberId(po.getCreaterId());
                    mr.setChargeStatus((byte) payStatus);
                    mr.setChargeType((byte) 3);
                    mr.setCreaterId(po.getCreaterId());
                    mr.setCreateTime(new Date());
                    mr.setUpdateTime(new Date());
                    memberChargeRecordMapper.insert(mr);
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "XXOD":
                    log.info("===========================进入会员线下订单业务处理==========================");
                    log.info("支付回调订单号：" + orderNum + " 支付交易状态：" + tradeCode);
                    // 会员订单更新
                    PhMemberOrderUnline pou = memberOrderUnlineMapper.getMemberOrderByOrderNum(orderNum);
                    if (OrderUnlineStatusEnum.STATUS_DONE_ORDER.getStatus() == pou.getStatus() || OrderUnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus() == pou.getStatus()) {
                        log.info("订单" + pou.getOrderNo() + "已经处理过,直接返回通知支付宝");
                        result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                        return result;
                    }
                    if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
                        // 支付成功,更新订单为交易完成
                        pou.setPayType((byte) payType);
                        pou.setIsSettle(OrderIsSettleEnum.IS_SETTLE.getIsSettle());// 是否结算（0=未结算；1=结算）//变更需求，立马结算
                        pou.setIsBackScore((byte) 1);
                        pou.setStatus(OrderUnlineStatusEnum.STATUS_DONE_ORDER.getStatus());
                        pou.setDoneTime(new Date());
                        pou.setPayTime(new Date());
                        pou.setUpdateTime(new Date());
                        pou.setUpdaterId(0l);
                        pou.setSettleTime(new Date());// 结算时间
                        memberOrderUnlineMapper.updateByPrimaryKeySelective(pou);

                        //更新会员积分
                        scoreService.memberScoreTrade(pou.getMemberId(), TransCodeEnum.MEMBER_RETURN_STANDBYSCORE, pou.getOrderMoney(), pou.getOrderNo(), 0l);
                        // 更新会员推荐奖励积分
                        Member member = memberMapper.selectByPrimaryKey(pou.getMemberId());
                        if (null != member) {
                            // 得到推荐人ID
                            final Long shareMemberId = member.getPromoterId();
                            if (null != shareMemberId) {
                                log.info("支付宝支付 给推荐人返可用积分 start ");
                                BigDecimal shareMoney = MoneyTransUtil
                                        .formatByMultiply(pou.getOrderMoney().longValue(), 0.002);
                                // 给推荐人返可用积分
                                scoreService.memberScoreTrade(shareMemberId, TransCodeEnum.MEMBER_RETURN_ENABLESCORE,
                                        shareMoney.longValue(), pou.getOrderNo(), 0);
                                // 设置奖励积分并记录奖励积分流水
                                scoreService.memberScoreTrade(shareMemberId, TransCodeEnum.RECOMMEND_MEMBER_UNLINE_ORDER_REWARD_SCORE,
                                        shareMoney.longValue(), pou.getOrderNo(), 0);
                                log.info("支付宝支付 给推荐人返可用积分 end ");
                            }
                        }
                        // 立即结算
                        settleOrderNow(pou);
                        result = ResultUtil.getResult(RespCode.Code.SUCCESS);

                        //异步推送支付成功消息,同时发送短信息给会员通知
                        taskExecuterServiceConfig.AsyncTask().execute(new Runnable() {
                            @Override
                            public void run() {
                                //查询会员信息
                                Member member = memberMapper.selectByPrimaryKey(pou.getMemberId());
                                if (!StringUtils.isEmpty(member)) {
                                    //查询模板信息
                                    Result result1 = messageService.getMessageSendInfoByTmplateId(MessageTemplateEnum.UNLINE_ORDER_PAY.getTemplateId());
                                    if (!StringUtils.isEmpty(result1.getData())) {
                                        MessageVO mv = (MessageVO) result1.getData();
                                        //组装消息替换内容
                                        Map<String, String> map = new HashMap<String, String>();
                                        map.put("merchantName", pou.getMerchantName());
                                        map.put("trustSlogan", mv.getTrustSlogan());
                                        map.put("brandSlogan", mv.getBrandSlogan());
                                        if (pou.getOrderMoney() >= 0) {
                                            Double money = pou.getOrderMoney() / 10000.0;
                                            map.put("money", money.toString());
                                            map.put("integral", money.toString());
                                        } else {
                                            map.put("money", "0");
                                            map.put("integral", "0");
                                        }
                                        //替换推送消息内容参数
                                        String content = StringHelper.renderString(mv.getMessageTemplate(), map);
                                        JPushSendDTO jsd = new JPushSendDTO();
                                        jsd.setTemplateId(MessageTemplateEnum.UNLINE_ORDER_PAY.getTemplateId());
                                        jsd.setSendType((byte) 0);//0:所有移动设备 1:android 2:ios
                                        jsd.setMessageContent(content);
                                        jsd.setCreaterId(0L);
                                        jsd.setPushType(PushTypeEnum.PEER_TO_PEER);
                                        //组装推送用户信息
                                        List<JPushUserDTO> pushUserList = new ArrayList<JPushUserDTO>();
                                        JPushUserDTO jdt = new JPushUserDTO();
                                        jdt.setUserId(pou.getMemberId());
                                        jdt.setUserType(PushMessageEnum.MEMBER.getCode());
                                        jdt.setEquipmentId(member.getEquipmentId());
                                        pushUserList.add(jdt);
                                        jsd.setUsers(pushUserList);
                                        //执行推送
                                        messageService.pushMessage(jsd);
                                        log.info("====================支付消息推送完成==================");

                                        //查询会员积分
                                        MemberScoreVO2 msv = scoreService.getMemberScore(member.getId());
                                        //给会员发送短信
                                        SmsSendData ssd = new SmsSendData();
                                        ssd.setMobile(member.getTelPhone());
                                        ssd.setType(SmsCodeType.USER_CONSUME_REMIND);
                                        ssd.setSourceEnum(SmsSourceEnum.MEMBER);
                                        //构造短信模板
                                        ConsumeRemindModelData crm = new ConsumeRemindModelData();
                                        DateTime dt = new DateTime();
                                        crm.setDay(String.valueOf(dt.getDayOfMonth()));
                                        crm.setMonth(String.valueOf(dt.getMonthOfYear()));
                                        crm.setYear(String.valueOf(dt.getYear()));
                                        crm.setOrderNo(orderNum);
                                        //计算订单金额
                                        BigDecimal bd = new BigDecimal(pou.getOrderMoney());
                                        String orderMoney = bd.divide(new BigDecimal(10000)).setScale(5, BigDecimal.ROUND_HALF_UP).toString();
                                        crm.setMoney(orderMoney);
                                        crm.setSmsAcceptType(SmsSourceEnum.MEMBER.getMsg());
                                        BigDecimal stand = new BigDecimal(msv.getStandbyscore());
                                        BigDecimal standScore = stand.divide(new BigDecimal(10000).setScale(5, BigDecimal.ROUND_HALF_UP));
                                        crm.setStandbyScore(standScore.toString());

                                        BigDecimal enable = new BigDecimal(msv.getEnablescore());
                                        BigDecimal enableScore = enable.divide(new BigDecimal(10000).setScale(5, BigDecimal.ROUND_HALF_UP));
                                        crm.setUsableScore(enableScore.toString());
                                        crm.setSubsidyScore(orderMoney);
                                        ssd.setModelData(crm);

                                        Result result = smsDataService.sendSmsByCustomModelData(ssd);
                                        log.info("会员线下订单消费短信发送结果：" + JSON.toJSONString(result));
                                    }
                                }
                            }
                        });
                        log.info("===========================结束会员线下订单业务处理==========================");
                        break;

                    }

            }
        } catch (Exception e) {
            log.error("易联支付回调更新订单异常：" + e);
            result = ResultUtil.getResult(PayecoExceptionEnum.PAYECO_RETURN_EXCEPTION);
            throw new PayecoException(PayecoExceptionEnum.PAYECO_RETURN_EXCEPTION);
        }

        return result;
    }

    private Result settleOrderNow(PhMemberOrderUnline phMemberOrderUnline) throws Exception {
        //立即结算--star
        log.info("线下订单创建后，立即结算--start");
        UnLineSettleOrderVO unLineSettleOrderVO = new UnLineSettleOrderVO();
        if (PayTypeEnum.PAY_TYPE_CASH.getPayType() == phMemberOrderUnline.getPayType()) {
            //余额支付时结算金额为0
            unLineSettleOrderVO.setSettleMoney(0l);
        } else {
            //其他支付时结算金额为订单金额-服务费金额
            unLineSettleOrderVO.setSettleMoney(phMemberOrderUnline.getOrderMoney() - phMemberOrderUnline.getServiceCost());
        }
        unLineSettleOrderVO.setId(phMemberOrderUnline.getId());
        unLineSettleOrderVO.setMerchantId(phMemberOrderUnline.getMerchantId());
        unLineSettleOrderVO.setOrderNo(phMemberOrderUnline.getOrderNo());
        Result result = iUnLineSettleService.doUnLineSettleNow(unLineSettleOrderVO);
        log.info("线下订单创建后，立即结算--end");
        //立即结算--end
        return result;
    }

    @Override
    public PayecoOrderVo getOrderByOrderNo(String orderNum) {
        PayecoOrderVo pv = null;
        // 截取订单业务状态码
        String bizCode = OrderUtil.getOrderBizCode(orderNum);
        switch (bizCode) {
            case "CZDD":
                pv = new PayecoOrderVo();
                CheckPayVO checkPayVO = iUserChargeService.getUserCharegMd5Str(orderNum);
                pv.setMd5Str(checkPayVO.getMd5Str());
                pv.setScore(checkPayVO.getScore());
                return pv;
            case "JH01":
                /**
                 * mdy 郑朋 2017-05-18 version-2.1
                 */
                // 供应链子订单
                PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selPurSubOrderByOrderNo(orderNum);
                pv = new PayecoOrderVo();
                pv.setOrderNum(purchaseSubOrder.getOrderNo());
                pv.setScore(purchaseSubOrder.getTotalCost().longValue());
                pv.setMd5Str(purchaseSubOrder.getMd5());
                break;
            case "JHZD":
                /**
                 * mdy 郑朋 2017-05-18 version-2.1
                 */
                // 供应链总订单
                PurchaseMainOrder purchaseMainOrder = purchaseOrderMapper.selPurMainOrderByOrderNo(orderNum);
                pv = new PayecoOrderVo();
                pv.setOrderNum(purchaseMainOrder.getOrderNo());
                pv.setScore(purchaseMainOrder.getTotalCost().longValue());
                pv.setMd5Str(purchaseMainOrder.getMd5());
                break;
            case "XS01":
                // 会员订单
                PhMemberSubOrderOnline po = memberSubOrderOnlineMapper.getMemberOrderByOrderNo(orderNum);
                pv = new PayecoOrderVo();
                pv.setOrderNum(po.getOrderNo());
                pv.setScore(po.getOrderMoney().longValue());
                break;
            case "XXOD":
                // 线下订单
                PhMemberOrderUnline pou = memberOrderUnlineMapper.getMemberOrderByOrderNum(orderNum);
                pv = new PayecoOrderVo();
                pv.setOrderNum(pou.getOrderNo());
                pv.setScore(pou.getOrderMoney());
                break;
            case "XXDD":
            case "SMDD":
            case "XSDD":
            case "ZFDD":
                //快火线下订单   by  王雪洋
                PhMemberOrderUnline p = memberOrderUnlineMapper.getMemberOrderByOrderNum(orderNum);
                pv = new PayecoOrderVo();
                pv.setOrderNum(p.getOrderNo());
                pv.setScore(p.getOrderMoney());
                break;
        }
        return pv;
    }

    @Override
    public void updateOrderMD5Sign(String orderNum, String mac) throws BizException {
        String bizCode = OrderUtil.getOrderBizCode(orderNum);
        switch (bizCode) {
            // case "CZ01":
            // //商户充值
            // MerchantChargeRecord
            // mr=merchantChargeRecord.getMerchantChargeRecordByRecordNo(orderNum);
            // if (!StringUtils.isEmpty(mr)) {
            // mr.setMd5Str(mac);//更新mac值到订单表,回调时调用
            // merchantChargeRecord.updateByPrimaryKeySelective(mr);
            // }
            //
            // break;
            // case "CZ02":
            // //代理充值
            // AgentChargeRecord
            // ar=algentChargeRecordMapper.getAgentChargeRecordByRecordNo(orderNum);
            // if (!StringUtils.isEmpty(ar)) {
            // ar.setMd5Str(mac);//更新mac值到订单表,回调时调用
            // algentChargeRecordMapper.updateByPrimaryKeySelective(ar);
            // }
            // break;
            // case "CZ03":
            // //供应商充值
            // SupplierChargeRecord
            // sr=supplierChargeRecordMapper.getSupplierChargeRecordByRecordNo(orderNum);
            // if (!StringUtils.isEmpty(sr)) {
            // sr.setMd5Str(mac);//更新mac值到订单表,回调时调用
            // supplierChargeRecordMapper.updateByPrimaryKeySelective(sr);
            // }
            // break;
            case "JH01":
                /**
                 * mdy 郑朋 2017-05-18 version-2.1
                 */
                // 子订单
                PurchaseSubOrder pt = purchaseOrderMapper.selPurSubOrderByOrderNo(orderNum);
                if (!StringUtils.isEmpty(pt)) {
                    PurchaseSubOrder updateOrder = new PurchaseSubOrder();
                    updateOrder.setId(pt.getId());
                    updateOrder.setMd5(mac);// 更新mac值到订单表,回调时调用
                    purchaseOrderMapper.updateByPrimaryKeySelective(updateOrder);
                }
                break;
            case "JHZD":
                /**
                 * mdy 郑朋 2017-05-18 version-2.1
                 */
                // 总订单
                PurchaseMainOrder pa = purchaseOrderMapper.selPurMainOrderByOrderNo(orderNum);
                if (!StringUtils.isEmpty(pa)) {
                    PurchaseMainOrder updateOrder = new PurchaseMainOrder();
                    updateOrder.setId(pa.getId());
                    pa.setMd5(mac);// 更新mac值到订单表,回调时调用
                    purchaseOrderMapper.updatePurMainOrder(updateOrder);
                }
                break;
            case "XS01":
                // 会员订单
                PhMemberSubOrderOnline po = memberSubOrderOnlineMapper.getMemberOrderByOrderNo(orderNum);
                if (!StringUtils.isEmpty(po)) {
                /*po.setMd5(mac);//更新mac值到订单表,回调时调用
				memberOrderMapper.updateByPrimaryKeySelective(po);*/
                }
                break;
        }
    }

    @Override
    public Result dealT503Status(String orderNum, int payType, int payStatus, String tradeCode) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        String bizCode = OrderUtil.getOrderBizCode(orderNum);
        try {
            switch (bizCode) {
                case "CZDD":
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "JH01":
                    /**
                     * mdy 郑朋 2017-05-18 version-2.1
                     */

                    // 供应链子订单支付回调处理，订单处理为过期
                    PurchaseSubOrder purchaseSubOrder = purchaseOrderMapper.selPurSubOrderByOrderNo(orderNum);
                    Long payUserId = purchaseSubOrder.getPurchaserId();
                    // 订单状态为待付款状态才会进行订单更新
                    purchaseOrderService.updateOrderBySubOrderT503(purchaseSubOrder);
                    // 新增供应链支付记录
                    insertPurchaseChargeRecord(payUserId, orderNum, purchaseSubOrder.getMd5(), (byte) payStatus, tradeCode,
                            purchaseSubOrder.getTotalCost());
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "JHZD":
                    /**
                     * mdy 郑朋 2017-05-18 version-2.1
                     */
                    // 供应链用总订单支付回调处理，订单处理为过期
                    // 查询主订单信息
                    PurchaseMainOrder purchaseMainOrder = purchaseOrderMapper.selPurMainOrderByOrderNo(orderNum);
                    // 修改订单处理为过期
                    purchaseOrderService.updateOrderByMainOrderT503(purchaseMainOrder);
                    // 新增供应链支付记录
                    insertPurchaseChargeRecord(purchaseMainOrder.getPurchaserId(), orderNum, purchaseMainOrder.getMd5(),
                            (byte) payStatus, tradeCode, purchaseMainOrder.getTotalCost());
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "XS01":
                    // 会员订单更新
                    PhMemberSubOrderOnline po = memberSubOrderOnlineMapper.getMemberOrderByOrderNo(orderNum);
                    // 银行订单过期
                    onlineOrderService.updateOrderT503(po.getId(), po.getCreaterId());
                    // 新增会员支付记录
                    MemberChargeRecord mr = new MemberChargeRecord();
                    mr.setOrderNo(po.getOrderNo());
                    mr.setScore(new BigDecimal(po.getOrderMoney()));
                    mr.setResponseCode(tradeCode);
                    mr.setMemberId(po.getCreaterId());
                    mr.setChargeStatus((byte) payStatus);
                    mr.setChargeType((byte) 3);
                    mr.setCreaterId(po.getCreaterId());
                    mr.setCreateTime(new Date());
                    mr.setUpdateTime(new Date());
                    memberChargeRecordMapper.insert(mr);
                    result = ResultUtil.getResult(RespCode.Code.SUCCESS);
                    break;
                case "XXOD":
                    //TODO
                    log.info("===========================进入会员线下订单业务处理==========================");
                    log.info("支付回调订单号：" + orderNum + " 支付交易状态：" + tradeCode);
                    log.info("===========================结束会员线下订单业务处理==========================");
                    break;
            }
        } catch (Exception e) {
            log.error("易联支付回调更新订单异常：", e);
            result = ResultUtil.getResult(PayecoExceptionEnum.PAYECO_RETURN_EXCEPTION);
            throw new PayecoException(PayecoExceptionEnum.PAYECO_RETURN_EXCEPTION);
        }
        return result;
    }

    /**
     * @param payUserId
     * @param orderNo
     * @param md5
     * @param payStatus
     * @param responseCode
     * @param totalCost
     * @return void
     * @methodname insertPurchaseChargeRecord 的描述：新增供应链支付记录
     * @author 郑朋
     * @create 2017/5/19
     */
    private void insertPurchaseChargeRecord(Long payUserId, String orderNo, String md5, Byte payStatus,
                                            String responseCode, BigDecimal totalCost) {
        // 新增供应链支付记录
        PurchaseChargeRecord purchaseChargeRecord = new PurchaseChargeRecord();
        purchaseChargeRecord.setCreateTime(new Date());
        purchaseChargeRecord.setCreaterId(payUserId);
        purchaseChargeRecord.setUserId(payUserId);
        purchaseChargeRecord.setOrderNo(orderNo);
        purchaseChargeRecord.setChargeStatus(payStatus);
        purchaseChargeRecord.setChargeType(PaymentEnum.THIRD.getCode());
        purchaseChargeRecord.setMd5Str(md5);
        purchaseChargeRecord.setResponseCode(responseCode);
        purchaseChargeRecord.setScore(MoneyTransUtil.transMultiToBig1(totalCost));
        purchaseChargeRecordMapper.insert(purchaseChargeRecord);
    }

    /**
     * 支付成功修改订单状态
     *
     * @author lzh
     */
    @Override
    @Transactional
    public Result updateTradOrder(String orderNum, Long orderMoney, int payType, int payStatus, String tradeCode) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        // 会员订单更新
        PhMemberOrderUnline po = memberOrderUnlineMapper.getMemberOrderByOrderNumAndStatus(orderNum);
        if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
            // 支付成功,更新订单为已支付状态
            result = tradOrderService.updateOrder(po.getId(), po.getCreaterId(), (byte) payType, orderMoney);
        }


        return result;
    }
    /**
     * 支付失败修改订单状态
     *
     * @author lzh
     */
    @Override
    @Transactional
    public Result updateTradOrderForFail(String orderNum, Long orderMoney, int payType, int payStatus, String tradeCode) {
        Result result = ResultUtil.getResult(RespCode.Code.FAIL);
        // 会员订单更新
        PhMemberOrderUnline po = memberOrderUnlineMapper.getMemberOrderByOrderNumAndStatus(orderNum);
        if (payStatus == PayStatusEnum.PAY_SUCCESS.getCode()) {
            // 支付成功,更新订单为已支付状态
            result = tradOrderService.updateOrder(po.getId(), po.getCreaterId(), (byte) payType, orderMoney);
        }


        return result;
    }
    /**
     * 根据交易订单id获取交易订单
     */
    @Override
    public UnlineOrderVO queryUnlineOrderVO(Long id) {
        UnlineOrderVO vo = null;
        try {
            vo = purchaseOrderMapper.selectUnlineOrderVO(id);
        } catch (Exception e) {
            log.error("获取交易订单异常：", e);
        }
        return vo;
    }
}
