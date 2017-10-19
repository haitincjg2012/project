package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.AddMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.CancelUnlineOrderDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.QueryMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
import cm.ph.shopping.facade.order.service.IUnlineOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineCancelDetailVO;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.order.api.service.MemberUnlineOrderDataService;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.smsCode.SmsCodeUtil;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.service.IMemberCardService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.IUserBalanceService;
import com.ph.shopping.facade.spm.vo.AgentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单
 * @作者： 张霞
 * @创建时间： 15:49 2017/5/25
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping("web/orderUnline")
public class MemberOrderUnlineController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MemberOrderUnlineController.class);

    @Reference(version = "1.0.0")
    IUnlineOrderService iUnlineOrderService;

    @Reference(version="1.0.0")
    IAgentService iAgentService;

    @Reference(version = "1.0.0")
    IMerchantService iMerchantService;

    @Reference(version = "1.0.0")
    IMemberCardService iMemberCardService;

    @Reference(version = "1.0.0")
    IUserBalanceService iUserBalance;

    @Reference(version = "1.0.0")
    IScoreService iScoreService;

    @Autowired
    ISmsDataService iSmsDataService;
    @Autowired
    MemberUnlineOrderDataService memberUnlineOrderDataService;

    @Reference(version = "1.0.0")
    IPayPasswordService iPayPasswordService;
    /**
     * @author: 张霞
     * @description：跳转至线下订单列表页面
     * @createDate: 11:33 2017/6/7
     * @param model
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "toListPage",method = {RequestMethod.GET,RequestMethod.POST})
    public String toListPage(Model model){
        return "order/unline/list";
    }
    /**
     * @author: 张霞
     * @description：线下订单列表查询
     * @createDate: 19:53 2017/6/7
     * @param model
     * @param pageBean
     * @param phMemberOrderUnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getUnlineOrderList(Model model, PageBean pageBean, PhMemberOrderUnlineDTO phMemberOrderUnlineDTO){
        return getUnlineOrderList(pageBean,phMemberOrderUnlineDTO);
    }

    /**
     * @author: 张霞
     * @description：跳转新增订单页面
     * @createDate: 10:57 2017/6/7
     * @param model
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "toAddPage",method ={RequestMethod.GET,RequestMethod.POST})
    public String toAddPage(Model model){
        return "order/unline/add";
    }
    /**
     * @author: 张霞
     * @description：后台创建线下订单
     * @createDate: 17:10 2017/6/8
     * @param addMemberOrderUnlineDTO
     * @param request
     * @return: java.lang.Object
     * @version: 2.1
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
//    @Token( key="memberTelphone" )
    public Object addUnlineOrder(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO, HttpServletRequest request){
        logger.info( "添加线下订单，页面入参参数addMemberOrderUnlineDTO：{}",JSON.toJSONString( addMemberOrderUnlineDTO ) );
        Result result = ResultUtil.getResult( RespCode.Code.SUCCESS);
        try {
            Long loginUserId = getLoginUser().getId();//正式环境
            addMemberOrderUnlineDTO.setCreaterId( loginUserId );
            addMemberOrderUnlineDTO.setMerchantId( loginUserId );
            request.getSession().setAttribute("token", null);
            if (addMemberOrderUnlineDTO.getOrderMoney().doubleValue()<0){
                result = ResultUtil.getResult( RespCode.Code.FAIL );
                result.setMessage( "输入金额有误" );
            }else {
                if (addMemberOrderUnlineDTO.getPayType()== PayTypeEnum.PAY_TYPE_SCORE.getPayType()){//验证验证
                   Result resultCheckMsm = checkSMSCode(addMemberOrderUnlineDTO);
                    if (!resultCheckMsm.isSuccess()){
                        result = ResultUtil.getResult( OrderResultEnum.CHECK_CODE_ERROR );
                        return result;
                    }
                }
                result  = iUnlineOrderService.addUnlineOrder(addMemberOrderUnlineDTO,true);
            }
        }catch (Exception e){
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        logger.info( "添加线下订单，返回页面参数result：{}",JSON.toJSONString( result ) );
        return result;
    }

    /**
     * @author: 张霞
     * @description：通过手机号或者会员卡内码获取相应的手机号或者会员卡
     * @createDate: 12:09 2017/6/22
     * @param memberCardInfoDTO
     * @return: java.lang.Object
     * @version: 2.1
     */
    @RequestMapping(value = "/getPhoneOrCard",method = {RequestMethod.POST})
    @ResponseBody
    public Object getPhoneOrCard(MemberCardInfoDTO memberCardInfoDTO){
        logger.info( "通过手机号或会员卡号获取相关会员信息，传入参数memberCardInfoDTO={}" ,JSON.toJSON( memberCardInfoDTO ));
        Result result;
        if (StringUtils.isEmpty( memberCardInfoDTO.getTelPhone() )&&StringUtils.isEmpty( memberCardInfoDTO.getInnerCode() )){
            result =  ResultUtil.getResult( RespCode.Code.FAIL );
        }else {
          try {
              result = iMemberCardService.getMemberBindCardInfoByCondition( memberCardInfoDTO );
          }catch (Exception e){
                logger.info( "通过手机号或者会员卡内码获取相应的手机号或者会员卡 失败，传入参数memberCardInfoDTO={}",JSON.toJSON( memberCardInfoDTO ) );
              result =  ResultUtil.getResult( RespCode.Code.FAIL );
          }
        }
        return result;
    }
    /**
     * 发验证码
     * @param addMemberOrderUnlineDTO
     * @return
     */
    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
    @ResponseBody
    public Object sendValidCode(@ModelAttribute AddMemberOrderUnlineDTO addMemberOrderUnlineDTO) {
        logger.info( "创建线下订单发送验证码入参参数addMemberOrderUnlineDTO={}",JSON.toJSONString( addMemberOrderUnlineDTO ) );
        try{
            Result result = getMemberINfoByCode(addMemberOrderUnlineDTO);
            if (RespCode.Code.SUCCESS.getCode().equals( result.getCode() )){
                MemberCardInfoVO memberCardInfoVO = (MemberCardInfoVO)result.getData();
                SmsSendData sendData = new SmsSendData();
                sendData.setMobile(memberCardInfoVO.getTelPhone());
                sendData.setType(SmsCodeType.SCORE_PAY_VC);
                sendData.setSourceEnum( SmsSourceEnum.MEMBER );
                sendData.setSmsCode( SmsCodeUtil.getSmsCode());
                result = iSmsDataService.sendSmsHaveCode(sendData);
                logger.info("发送短信返回数据 Result = {} ", JSON.toJSONString(result));
            }
            return result;
        }catch (Exception e) {
            logger.info( "会员卡获取会员信息异常" );
            return  ResultUtil.getResult(RespCode.Code.FAIL);
        }
    }
    /**
     * @author: 张霞
     * @description：验证短信验证码
     * @createDate: 17:04 2017/6/20
     * @param
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    private Result checkSMSCode(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO){
        Result result = getMemberINfoByCode(addMemberOrderUnlineDTO);
        if (RespCode.Code.SUCCESS.getCode().equals( result.getCode() )){
            MemberCardInfoVO memberCardInfoVO = (MemberCardInfoVO)result.getData();
            //1.短信验证
            SmsSendData sendData = new SmsSendData();
            sendData.setMobile(memberCardInfoVO.getTelPhone());
            sendData.setSmsCode(addMemberOrderUnlineDTO.getVerificationCode());//验证发送的验证码
            sendData.setType(SmsCodeType.SCORE_PAY_VC);
            sendData.setSourceEnum(SmsSourceEnum.MEMBER);
            Result resultSmsCode = iSmsDataService.checkSmsCodeByMobile(sendData);
            logger.info("根据手机号获取短信验证码验证后返回数据 Result = {} ", JSON.toJSONString(resultSmsCode));
            if (resultSmsCode != null) {
                if (!resultSmsCode.isSuccess()) {
                    return resultSmsCode;
                }
            }
            return resultSmsCode;
        }
        result = ResultUtil.getResult( RespCode.Code.FAIL );
        return result;
    }

    /**
     * @author: 张霞
     * @description：取消线下订单
     * @createDate: 13:08 2017/6/9
     * @param model
     * @param cancelUnlineOrderDTO
     * @param request
     * @return: java.lang.Object
     * @version: 2.1
     */
    @RequestMapping(value = "cancel",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object cancelUnlineOrder(Model model, CancelUnlineOrderDTO cancelUnlineOrderDTO, HttpServletRequest request){
        logger.info( "取消线下订单入参参数cancelUnlineOrderDTO={}",JSON.toJSONString( cancelUnlineOrderDTO ) );
        Long loginUserId = getLoginUser().getId();
        cancelUnlineOrderDTO.setUpdaterId( loginUserId );
        Result result;
        try {
            result = iUnlineOrderService.cancelUnlineOrder( cancelUnlineOrderDTO );
        }catch (Exception e){
            result = ResultUtil.getResult( OrderExceptionEnum.CANCEL_UNLINEORDER_EXCEPTION );
        }
        logger.info( "取消线下订单处理结果result={}",JSON.toJSONString( result ) );
        return result;
    }
    /**
     * @author: 张霞
     * @description：线下订单取消详情
     * @createDate: 16:10 2017/6/9
     * @param cancelUnlineOrderDTO
     * @return: java.lang.Object
     * @version: 2.1
     */
    @RequestMapping(value = "cancelOrderDetail",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object cancelUnlineOrderDetail(CancelUnlineOrderDTO cancelUnlineOrderDTO){
        Result result = iUnlineOrderService.getPhMemberOrderUnlineById( cancelUnlineOrderDTO.getId() );
        if (RespCode.Code.SUCCESS.getCode().equals( result.getCode() )){
            PhMemberOrderUnline phMemberOrderUnline = (PhMemberOrderUnline) result.getData();
            PhMemberOrderUnlineCancelDetailVO phMemberOrderUnlineCancelDetailVO = new PhMemberOrderUnlineCancelDetailVO();
            phMemberOrderUnlineCancelDetailVO.setCancelReason(phMemberOrderUnline.getCancelReason());
            result.setData( phMemberOrderUnlineCancelDetailVO );
        }
        return result;
    }
    /**
     * @author: 张霞
     * @description：订单详情信息获取数据和跳转页面
     * @createDate: 17:26 2017/6/11
     * @param model
     * @param queryMemberOrderUnlineDTO
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "detail", method = {RequestMethod.GET, RequestMethod.POST})
    public String detail(Model model,QueryMemberOrderUnlineDTO queryMemberOrderUnlineDTO){

        Result result = iUnlineOrderService.getUnlineOrderDetail( queryMemberOrderUnlineDTO );
        model.addAttribute( "unlineOrder",result );
        return "order/unline/detail";
    }
    /**
     * @author: 张霞
     * @description：线下订单导出数据
     * @createDate: 11:31 2017/6/12
     * @param request
     * @param response
     * @param pmouDTO
     * @return: void
     * @version: 2.1
     */
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    @SuppressWarnings("unchecked")
    public void export(HttpServletRequest request, HttpServletResponse response, PhMemberOrderUnlineDTO pmouDTO) throws Exception {
        Result result = getUnlineOrderList(null, pmouDTO);
        List<PhMemberOrderUnlineVO> phMemberOrderUnlineVOList = (List<PhMemberOrderUnlineVO>) result.getData();
        ExcelBean excelBean = memberUnlineOrderDataService.getPurchaseOrder(phMemberOrderUnlineVOList,"线下订单数据列表");
        ExcelUtil.download(request, response, excelBean);
    }
    /**
     * @author: 张霞
     * @description：通过会员卡内码获取会员手机号码
     * @createDate: 20:50 2017/6/7
     * @param addMemberOrderUnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    public Result getMemberINfoByCode(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO){
        MemberCardInfoDTO memberCardInfoDTO = new MemberCardInfoDTO();
        memberCardInfoDTO.setInnerCode(addMemberOrderUnlineDTO.getMemberCardNo());
        Result memberCardInfoByCode = iMemberCardService.judgeMemberCard(memberCardInfoDTO);
        logger.info(" Result ={}", JSON.toJSON(memberCardInfoByCode));
        if(!RespCode.Code.SUCCESS.getCode().equals(memberCardInfoByCode.getCode())){
            return  ResultUtil.getResult(OrderResultEnum.NO_MEMBERCARD_DATA);
        }
        return memberCardInfoByCode;
    }
    /**
     * @author: 张霞
     * @description：查询线下订单列表
     * @createDate: 15:07 2017/6/13
     * @param pageBean
     * @param phMemberOrderUnlineDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    public Result getUnlineOrderList(PageBean pageBean,PhMemberOrderUnlineDTO phMemberOrderUnlineDTO){
        SessionUserVO userVO  = getLoginUser();
        Long userId = userVO.getId();
        Byte roleCode = userVO.getSessionRoleVo().get(0).getRoleCode();
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setUserId(userVO.getId());
        AgentVO agentVO;
        switch (roleCode){
            case 1:
                //管理员
                iUnlineOrderService.getUnLineOrderVoList(phMemberOrderUnlineDTO,pageBean);
                break;
            case 3:
                //市级代理
                agentVO =iAgentService.getAgentVODateilById(agentDTO);
                phMemberOrderUnlineDTO.setProvinceId(agentVO.getProvinceId()==null?null:agentVO.getProvinceId().toString());
                phMemberOrderUnlineDTO.setCityId(agentVO.getCityId()==null?null:agentVO.getCityId().toString());
                break;
            case 4:
                //县级代理商
                agentVO =iAgentService.getAgentVODateilById(agentDTO);
                phMemberOrderUnlineDTO.setProvinceId(agentVO.getProvinceId()==null?null:agentVO.getProvinceId().toString());
                phMemberOrderUnlineDTO.setCityId(agentVO.getCityId()==null?null:agentVO.getCityId().toString());
                phMemberOrderUnlineDTO.setCountyId(agentVO.getCountyId()==null?null:agentVO.getCountyId().toString());
                break;
            case 5:
                //社区代理商
                agentVO =iAgentService.getAgentVODateilById(agentDTO);
                phMemberOrderUnlineDTO.setProvinceId(agentVO.getProvinceId()==null?null:agentVO.getProvinceId().toString());
                phMemberOrderUnlineDTO.setCityId(agentVO.getCityId()==null?null:agentVO.getCityId().toString());
                phMemberOrderUnlineDTO.setCountyId(agentVO.getCountyId()==null?null:agentVO.getCountyId().toString());
                phMemberOrderUnlineDTO.setTownId(agentVO.getTownId() == null ?null:agentVO.getTownId().toString());
                break;
            case 6:
                //商户
                phMemberOrderUnlineDTO.setMerchantId( userId );
                break;
        }
        return iUnlineOrderService.getUnLineOrderVoList(phMemberOrderUnlineDTO,pageBean);
    }


}


