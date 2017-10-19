package com.ph.shopping.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.SmsConfig;
import com.ph.shopping.common.core.dto.*;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xwolf
 * @since 1.8
 **/
@Component
public class SmsUtil {

    private static  final String DEFAULT_ENCODING="UTF-8";

    private static Logger log = LoggerFactory.getLogger(SmsUtil.class);

    @Autowired
    private  SmsConfig smsConfig;


    /**
     * 快火App代理/业务员注册
     * @param registerDTO
     * @return
     */
    public Result sendRegisterMsg(RegisterDTO registerDTO){
        Result result = new Result();
        log.info("代理商注册：{}", JSON.toJSON(registerDTO));
        String message = registerDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            registerDTO.setPhone(registerDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getAgentRegister(), BeanToMap.getMapByStr(registerDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("代理商注册content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(msg);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 快火APP注册/找回密码操作发送验证码
     * @param findPasswordDTO
     * @return
     */
    public  Result sendRegisterOrFindPasswordMsg(FindPasswordDTO findPasswordDTO){
        Result result = new Result();
        log.info("用户注册/找回密码：{}", JSON.toJSON(findPasswordDTO));
        String message = findPasswordDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            findPasswordDTO.setPhone(findPasswordDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getPassword(), BeanToMap.getMapByStr(findPasswordDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("用户注册/找回密码content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(msg);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }


    /**
     * 快火代理提醒
     * @param agentAlertDTO
     * @return
     */
    public  Result sendAgentAlert(AgentAlertDTO agentAlertDTO){
        Result result = new Result();
        log.info("快火代理提醒：{}", JSON.toJSON(agentAlertDTO));
        String message = agentAlertDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            agentAlertDTO.setPhone(agentAlertDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getAgentAlert(), BeanToMap.getMapByStr(agentAlertDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("快火代理提醒content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(msg);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 现金/积分支付
     * @param cashDTO
     * @return
     */
    public  Result sendCashMsg(CashDTO cashDTO){
        Result result = new Result();
        log.info("现金/积分支付：{}", JSON.toJSON(cashDTO));
        String message = cashDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            cashDTO.setPhone(cashDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getCash(), BeanToMap.getMapByStr(cashDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("现金/积分支付content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(msg);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 预定订单通知
     * @param prePayDTO
     * @return
     */
    public  Result  sendPrepayMsg(PrePayDTO prePayDTO){
        Result result = new Result();
        log.info("预定订单通知：{}", JSON.toJSON(prePayDTO));
        String message = prePayDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            prePayDTO.setPhone(prePayDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getPrepay(), BeanToMap.getMapByStr(prePayDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("预定订单通知content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 订单支付通知
     * @param payDTO
     * @return
     */
    public  Result sendPayMsg(PayDTO payDTO){
        Result result = new Result();
        log.info("订单支付通知：{}", JSON.toJSON(payDTO));
        String message = payDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            payDTO.setPhone(payDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getPay(), BeanToMap.getMapByStr(payDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("订单支付通知content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 商户/会员推广奖金通知
     * @param spreadDTO
     * @return
     */
    public  Result sendSpreadMsg(SpreadDTO spreadDTO){
        Result result = new Result();
        log.info("推广奖金通知：{}", JSON.toJSON(spreadDTO));
        String message = spreadDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            spreadDTO.setPhone(spreadDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getSpread(), BeanToMap.getMapByStr(spreadDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("推广奖金content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 用户支付通知<通知商户>
     * @param memberPayDTO
     * @return
     */
    public  Result sendMemberPay(MemberPayDTO memberPayDTO){
        Result result = new Result();
        log.info("用户支付通知：{}", JSON.toJSON(memberPayDTO));
        String message = memberPayDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            memberPayDTO.setPhone(memberPayDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getMemberPay(), BeanToMap.getMapByStr(memberPayDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("用户支付通知content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }


    /**
     * 用户提交订单<通知商户>
     * @param submitOrderDTO
     * @return
     */
    public  Result sendMemberSubmitOrder(SubmitOrderDTO submitOrderDTO){
        Result result = new Result();
        log.info("用户提交订单通知：{}", JSON.toJSON(submitOrderDTO));
        String message = submitOrderDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            submitOrderDTO.setPhone(submitOrderDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getSubmitOrder(), BeanToMap.getMapByStr(submitOrderDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("用户提交订单通知content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 发送公共验证码
     * @param commonDTO
     * @return
     */
    public  Result sendCommonMsg(CommonDTO commonDTO){
        Result result = new Result();
        log.info("公共验证码通知：{}", JSON.toJSON(commonDTO));
        String message = commonDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            commonDTO.setPhone(commonDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getCommon(), BeanToMap.getMapByStr(commonDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("公共验证码content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }


    /**
     * 取消订单
     * @param orderCancelDTO
     * @return
     */
    public  Result cancelOrder(OrderCancelDTO orderCancelDTO){
        Result result = new Result();
        log.info("取消订单通知：{}", JSON.toJSON(orderCancelDTO));
        String message = orderCancelDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            orderCancelDTO.setPhone(orderCancelDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getCancelOrder(), BeanToMap.getMapByStr(orderCancelDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("取消订单content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     *修改密码
     * @param updatePasswordDTO
     * @return
     */
    public  Result updatePassword(UpdatePasswordDTO updatePasswordDTO){
        Result result = new Result();
        log.info("修改密码通知：{}", JSON.toJSON(updatePasswordDTO));
        String message = updatePasswordDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            updatePasswordDTO.setPhone(updatePasswordDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getUpdatePassword(), BeanToMap.getMapByStr(updatePasswordDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("修改密码content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }

    /**
     * 重置密码
     * @param resetPasswordDTO
     * @return
     */
    public  Result resetPassword(ResetPasswordDTO resetPasswordDTO){
        Result result = new Result();
        log.info("重置密码通知：{}", JSON.toJSON(resetPasswordDTO));
        String message = resetPasswordDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            resetPasswordDTO.setPhone(resetPasswordDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getResetPassword(), BeanToMap.getMapByStr(resetPasswordDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("重置密码content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }




    /**
     * 验证 手机验证码
     * @param checkDTO
     * @return
     */
    public  Result check(CheckDTO checkDTO){
        Result result = new Result();
        log.info("验证码校验：{}", JSON.toJSON(checkDTO));
        String message = checkDTO.validateForm();
        if (Objects.nonNull(message)) {
            result.setCode("0");
            result.setMessage(message);
            return result;
        }
        try {
            checkDTO.setPhone(checkDTO.getPhone().substring(0,11));
            HttpResult httpResult = HttpClientUtils.sendPost(smsConfig.getCheck(), BeanToMap.getMapByStr(checkDTO), DEFAULT_ENCODING);
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("验证码校验content:{}",content.toJSONString());
            String msg = content.getString("msg");
            if ("1".equals(content.getString("code"))){
                result.setCode("1");
                result.setMessage(message);
                return result;
            }
            result.setCode("0");
            result.setMessage(msg);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode("0");
            result.setMessage("内部服务异常");
            return result;
        }
    }
}
