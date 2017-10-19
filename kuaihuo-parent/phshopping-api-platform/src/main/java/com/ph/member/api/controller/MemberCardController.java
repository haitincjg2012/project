package com.ph.member.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.log.SystemService;
import com.ph.member.api.service.MemberDataService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.entity.IcCardInfo;
import com.ph.shopping.facade.member.service.IMemberCardService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MemberCardController
 *
 * @Description:会员卡相关操作
 *
 * @author:李杰
 * @updater:Mr.Shu
 *
 * @date: 2017年5月23日 下午3:20:38
 *
 * @Copyright: 2017/5/23
 */
@Controller
@RequestMapping("web/memberCard")
public class MemberCardController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(MemberCardController.class);

    /**
     * 会员卡服务
     */
    @Reference(version = "1.0.0", retries = 0, timeout = 30000)
    private IMemberCardService memberCardService;

    /**
     * 会员服务
     */
    @Reference(version = "1.0.0", retries = 0, timeout = 30000)
    private IMemberService memberService;

    //日志接口
    @Autowired
    private SystemService systemService;

    /**
     * 短信服务
     */
    @Autowired
    private ISmsDataService smsDataService;

    @Autowired
    private MemberDataService memberDataService;


//=============================================页面跳转==============================================/


    /**
     * @描述：会员卡列表页面
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 13:38
     */
    @RequestMapping(value = "/memberCardListPage", method = RequestMethod.GET)
    public String toMemberCardListPage() {
        return "memberCard/memberCardList";
    }

    /**
     * @描述：添加会员卡页面
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 13:38
     */
    @RequestMapping(value = "/addMemberCardPage", method = RequestMethod.GET)
    public String toAddMemberCard() {
        return "memberCard/memberCardAdd";
    }

    /**
     * @描述：导入会员卡页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 15:51
     */
    @RequestMapping(value = "/importMemberCardPage", method = RequestMethod.GET)
    public String toImportMemberCardPage() {
        return "memberCard/memberCardImport";
    }

    /**
     * @描述：会员卡发卡列表页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 16:44
     */
    @RequestMapping(value = "/memberCardSendListPage", method = RequestMethod.GET)
    public String toMemberCardSendListPage() {
        return "memberCard/memberCardSendList";
    }

    /**
     * @描述：发卡页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 17:31
     */
    @RequestMapping(value = "/sendMemberCardPage", method = RequestMethod.GET)
    public String toSendMemberCardPage() {
        return "memberCard/memberCardSend";
    }




    /**
     * @描述：挂失页面
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 17:34
     */
    @RequestMapping(value = "/lossMemberCardPage", method = RequestMethod.GET)
    public String toLossMemberCardPage() {
        return "memberCard/memberCardLoss";
    }


    //===============================================数据操作=================================================/
    /**
     * @描述：获取会员卡列表数据
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 17:36
     */
    @ResponseBody
    @RequestMapping("/memberCardList")
    public Result getMemberCardListByPage(PageBean pageBean, MemberCardInfoDTO memberCardInfoDTO) {
        log.info("加载会员卡列表参数 memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        Result result = memberCardService.getMemberCardListByPage(pageBean, memberCardInfoDTO);
        log.info("加在会员卡列表返回参数 Result = {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @描述：添加会员卡
     *
     * @param: icCardInfo
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 15:04
     */
    @ResponseBody
    @RequestMapping("/addMemberCard")
    public Result addMemberCard(IcCardInfo icCardInfo) {
        log.info("添加会员卡参数 icCardInfo = {} ", JSON.toJSONString(icCardInfo));
        SessionUserVO sessionUserVO = getLoginUser();
        icCardInfo.setCreaterId(sessionUserVO.getId());
        icCardInfo.setUpdaterId(sessionUserVO.getId());
        icCardInfo.setCreateTime(new Date());
        icCardInfo.setUpdateTime(new Date());

        //日志记录
        Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "添加会员卡");
        if (result.isSuccess()) {
            result = memberCardService.addMemberCard(icCardInfo);
        }
        log.info("添加会员卡返回数据 Result = {} ", JSON.toJSONString(result));
        return result;
    }

    /**
     * @描述：导入会员卡
     * @param: request
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 17:43
     */
    @ResponseBody
    @RequestMapping("/importMemberCard")
    public Result importMemberCard(HttpServletRequest request) {
        Result result = ResultUtil.setResult(false, "导入会员失败");
        try {
            String[] names = {"innerCode", "outerCode"};
            List<IcCardInfo> list = memberDataService.getExcelImportInfos(IcCardInfo.class, names, request);
            log.info("导入会员卡数据 = {} ", JSON.toJSONString(list));
            SessionUserVO sessionUserVO = getLoginUser();
            for (IcCardInfo i : list) {
                i.setUpdaterId(sessionUserVO.getId());
                i.setCreaterId(sessionUserVO.getId());
            }

            //日志记录
            result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "导入会员卡");
            if (result.isSuccess()) {
                result = memberCardService.batchAddMemberCard(list);
                log.info("导入会员卡返回数据 = {} ", JSON.toJSONString(result));
                if (result != null) {
                    if (result.isSuccess()) {
                        Object obj = result.getData();
                        int successNum = 0;
                        int errorNum = 0;
                        if (obj instanceof List) {
                            List<?> errors = (List<?>) obj;
                            errorNum = errors.size();
                            successNum = list.size() - errorNum;
                        }
                        Map<String, Object> map = ContainerUtil.map();
                        map.put("successNum", successNum);
                        map.put("errorNum", errorNum);
                        result.setData(map);
                    }
                }
            }
        } catch (Exception e) {
            log.error("导入会员错误", e);
        }
        return result;
    }

    /**
     * @描述：获取已发卡列表数据
     * @param: pageBean
     * @param: memberCardInfoDTO
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 17:37
     */
    @RequestMapping("/memberCardSendList")
    @ResponseBody
    public Result getMemberCardSendListByPage(PageBean pageBean, MemberCardInfoDTO memberCardInfoDTO) {
        log.info("加载会员卡列表参数 memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        SessionUserVO sessionUserVO = getLoginUser();
        SessionRoleVO sessionRoleVO = sessionUserVO.getSessionRoleVo().get(0);
        //如果是商户角色，则获取商户下的会员卡列表
        if (RoleEnum.MERCHANT.getCode().equals(sessionRoleVO.getRoleCode())) {
            memberCardInfoDTO.setCreaterId(sessionUserVO.getId());
        }
        Result result = memberCardService.getMemberCardSendListByPage(pageBean, memberCardInfoDTO);
        log.info("平台查询所有绑定数据会员卡列表返回参数 Result = {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * @描述：发卡
     *
     * @param: memberCardInfoDTO
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 17:41
     */
    @ResponseBody
    @RequestMapping("/bindMemberCard")
    public Result bindMemberCard(MemberCardInfoDTO memberCardInfoDTO) {
        log.info("会员发卡参数 memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        Result result = ResultUtil.setResult(false, "会员卡发卡失败");
        SessionUserVO user = getLoginUser();
        if (user == null || user.getId() == null) {
            log.debug("未获取到当前登录人信息");
        } else {
            //1.短信验证
            SmsSendData sendData = new SmsSendData();
            sendData.setMobile(memberCardInfoDTO.getTelPhone());
            sendData.setSmsCode(memberCardInfoDTO.getMsgCode());
            sendData.setType(SmsCodeType.BIND_MEMBERCARD_VC);
            sendData.setSourceEnum(SmsSourceEnum.MEMBER);
            Result resultSmsCode = smsDataService.checkSmsCodeByMobile(sendData);
            logger.info("根据手机号获取短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
            if (resultSmsCode != null) {
                if (!resultSmsCode.isSuccess()) {
                    return resultSmsCode;
                }
            }
            //发卡
            memberCardInfoDTO.setCreaterId(user.getId());
            memberCardInfoDTO.setUpdaterId(user.getId());
            memberCardInfoDTO.setMerchantId(user.getId());
            result = memberCardService.bindMemberCard(memberCardInfoDTO);
            log.info("会员发卡 返回数据 Result = {} ", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * @描述：挂失
     * @param: phone
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 17:44
     */
    @ResponseBody
    @RequestMapping("/lossMemberCard")
    public Result lossMemberCard(MemberCardInfoDTO memberCardInfoDTO, String isSms) {
        log.info("挂失会员卡参数 memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        Result result = ResultUtil.setResult(false, "挂失会员卡失败");
        SessionUserVO user = getLoginUser();
        if (user == null || user.getId() == null) {
            log.debug("未获取到当前登录人信息");
        } else {
            if (null != isSms && !"".equals(isSms.trim())) {
                //1.短信验证
                SmsSendData sendData = new SmsSendData();
                sendData.setMobile(memberCardInfoDTO.getTelPhone());
                sendData.setSmsCode(memberCardInfoDTO.getMsgCode());
                sendData.setType(SmsCodeType.LOSS_MEMBERCARD_VC);//挂失会员卡
                sendData.setSourceEnum(SmsSourceEnum.MEMBER);//会员
                Result resultSmsCode = smsDataService.checkSmsCodeByMobile(sendData);
                logger.info("根据手机号获取短信验证码返回数据 Result = {} ", JSON.toJSONString(result));
                if (resultSmsCode != null) {
                    if (!resultSmsCode.isSuccess()) {
                        return resultSmsCode;
                    }
                }
            }

            //2.挂失操作
            MemberCardInfoDTO dto = new MemberCardInfoDTO();
            dto.setTelPhone(memberCardInfoDTO.getTelPhone());
            dto.setUpdaterId(user.getId());
            result = memberCardService.lossMemberCard(dto);
            log.info("挂失会员卡返回数据 Result = {} ", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * @描述：冻结
     * @param: icCardId
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/23 17:46
     */
    @RequestMapping("/frozenMemberCard")
    @ResponseBody
    public Result frozenMemberCard(Long icCardId) {
        log.info("冻结会员卡会员卡参数icCardId: " + icCardId);
        Result result = ResultUtil.setResult(false, "冻结会员卡失败");
        SessionUserVO sessionUserVO = getLoginUser();
        if (sessionUserVO == null || sessionUserVO.getId() == null) {
            log.debug("未获取到当前登录人信息");
        } else {
            MemberCardInfoDTO dto = new MemberCardInfoDTO();
            dto.setIcCardId(icCardId);
            dto.setUpdaterId(sessionUserVO.getId());
            //日志记录
            result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "冻结会员卡");
            if (result.isSuccess()) {
                result = memberCardService.frozenMemberCard(dto);
            }
            log.info("冻结会员卡返回数据 Result = {} ", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * @描述：解冻
     *
     * @param: icCardId
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/23 17:47
     */
    @ResponseBody
    @RequestMapping("/relieveMemberCard")
    public Result relieveMemberCard(Long icCardId) {
        log.info("解冻会员卡参数icCardId: " + icCardId);
        Result result = ResultUtil.setResult(false, "解除冻结会员卡失败");
        SessionUserVO sessionUserVO = getLoginUser();
        if (sessionUserVO == null || sessionUserVO.getId() == null) {
            log.debug("未获取到当前登录人信息");
        } else {
            MemberCardInfoDTO dto = new MemberCardInfoDTO();
            dto.setIcCardId(icCardId);
            dto.setUpdaterId(sessionUserVO.getId());
            //日志记录
            result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "解冻会员卡");
            if (result.isSuccess()) {
                result = memberCardService.relieveMemberCard(dto);
            }
            log.info("解冻会员卡返回数据 Result = {} ", JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * @描述：根据手机号获取会员卡信息
     *
     * @param: phones
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/31 15:43
     */
    @ResponseBody
    @RequestMapping("/getMemberCardByPhone")
    public Result getMemberCardByPhone(String phone) {
        log.info("根据手机卡号获取会员卡数据据参数phone: " + phone);
        if (null != phone && !"".equals(phone.trim())) {
            MemberCardInfoDTO memberCardInfoDTO = new MemberCardInfoDTO();
            memberCardInfoDTO.setTelPhone(phone);
            Result result = memberCardService.getMemberBindCardInfoByCondition(memberCardInfoDTO);
            log.info("根据手机卡号获取会员卡数据返回数据 Result = {} ", JSON.toJSONString(result));
            return result;
        } else {
            return null;
        }

    }


}
