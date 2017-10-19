package com.ph.spm.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberQueryShareDTO;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.member.MemberShareTypeEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.vo.MemberShareRecordVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentLevel;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentStatus;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.merchantIsDelete;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.merchantIsFrozen;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.merchantStatus;
import com.ph.shopping.facade.spm.util.GenerateXSSFExcel;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.service.ISystemLogService;
/**
 * @项目 phshopping-api-platform
 * @描述   商户控制层
 * @author 何文浪
 * @时间 2017-5-23
 * @version 2.1
 */
@Controller
@RequestMapping("web/hunter")
public class HunterController extends BaseController{
    //商户业务层
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;
    //代理业务层
    @Reference(version = "1.0.0")
    private IAgentService iAgentService;
    //系统日志添加
    @Reference(version = "1.0.0")
    private ISystemLogService iSystemLogService;
    //会员接口
    @Reference(version = "1.0.0")
    private IMemberService memberService;
    /**
     * 导出商户
     * @param response,MerchantDTO
     * @return void
     * @author 何文浪
     * @时间 2017-6-8
     */
    @RequestMapping(value = "/getExport")
    @ResponseBody
    public void partExport(HttpServletResponse response,MerchantDTO merchantDTO){
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        //记录日志
        Result result = addSysLog(userBean,SystemOperateEnum.IMPORT_EXPORT.getType(),"商户导出");
        //登录权限判断
        //是否为管理员
        if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
            //是否为代理商
        }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COMMUNITY_AGENT.getCode()){
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setUserId(userBean.getId());
            //查找当前级及下级代理商
            List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(agentDTO);
            if(agentList!=null && agentList.size()>0){
                List<Long> ids = new ArrayList<Long>();
                for(AgentVO vo:agentList){
                    ids.add(vo.getId());
                }
                merchantDTO.setAgentIds(ids);
            }
        }
        merchantDTO.setIsDelete(merchantIsDelete.ZERO.getIndex());
        List<MerchantVO> list = new ArrayList<MerchantVO>();
        //判断日志是否已经记录
        if(result.isSuccess()){
            list = iMerchantService.getMerchantList(merchantDTO);
        }
        if(list != null && list.size()>0){//处理状态码
            for(MerchantVO vo:list){
                if (vo.getStatus() == agentStatus.ZERO.getIndex()) {
                    if(vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.CITY_AGENT.getName()+"】"+agentStatus.ZERO.getName());
                    }else if(vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.COUNTY_AGENT.getName()+"】"+agentStatus.ZERO.getName());
                    }else if(vo.getStatusLevel() == agentLevel.ADMIN.getIndex()){
                        vo.setStatusName("【"+agentLevel.ADMIN.getName()+"】"+agentStatus.ZERO.getName());
                    }
                } else if (vo.getStatus() == agentStatus.ONE.getIndex()) {
                    if(vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.CITY_AGENT.getName()+"】"+agentStatus.ONE.getName());
                    }else if(vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.COUNTY_AGENT.getName()+"】"+agentStatus.ONE.getName());
                    }else if(vo.getStatusLevel() == agentLevel.ADMIN.getIndex()){
                        vo.setStatusName("【"+agentLevel.ADMIN.getName()+"】"+agentStatus.ONE.getName());
                    }
                } else if (vo.getStatus() == agentStatus.TWO.getIndex()) {
                    if(vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.CITY_AGENT.getName()+"】"+agentStatus.TWO.getName());
                    }else if(vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()){
                        vo.setStatusName("【"+agentLevel.COUNTY_AGENT.getName()+"】"+agentStatus.TWO.getName());
                    }else if(vo.getStatusLevel() == agentLevel.ADMIN.getIndex()){
                        vo.setStatusName("【"+agentLevel.ADMIN.getName()+"】"+agentStatus.TWO.getName());
                    }
                }
                if(vo.getIsFrozen() == merchantIsFrozen.ZERO.getIndex()){
                    vo.setIsFrozenName(merchantIsFrozen.ZERO.getName());
                }else if(vo.getIsFrozen() == merchantIsFrozen.ONE.getIndex()){
                    vo.setIsFrozenName(merchantIsFrozen.ONE.getName());
                }
            }
        }

        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("parentName","上级代理");
        headMap.put("companyName","企业名称");
        headMap.put("merchantName","门店名称");
        headMap.put("telPhone","商户账号");
        headMap.put("merchantTypeName","行业分类");
        headMap.put("positionName","所属区域");
        headMap.put("statusName","审核状态");
        headMap.put("isFrozenName","冻结状态");
        String title = "商户导出";
        GenerateXSSFExcel excel = new GenerateXSSFExcel();
        excel.downloadExcelFile(title, headMap, list, response);
    }
    /**
     * 商户列表页面
     * @return String
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value="/list")
    public String list(Model model,Long agentId){
        model.addAttribute("statusLevel", getUser());
        model.addAttribute("agentId", agentId);
        return "spm/merchant/merchantListPf";
    }

    /**
     * 扩展下级商户列表页面
     * @return String
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value="/nextList")
    public String nextList(Model model,Long agentId){
        model.addAttribute("statusLevel", getUser());
        model.addAttribute("agentId", agentId);
        return "spm/merchant/merchantList";
    }

    /**
     * 商户增加或者修改页面
     * @return String
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/addOrUpdate")
    public String addOrUpdate(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, Model model) {
        MerchantVO merchantVO = new MerchantVO();
        if (operateType.equalsIgnoreCase("update")) {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setId(id);
            merchantVO = iMerchantService.getMerchantDetailById(merchantDTO);
        }
        model.addAttribute("merchantVO", merchantVO);
        model.addAttribute("operateType", operateType);
        return "spm/merchant/addOrUpdate";
    }

    /**
     * 商户详情页面
     *
     * @return String
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/detail")
    public String detail(Long id, Model model) {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(id);
        model.addAttribute("merchantVO", iMerchantService.getMerchantDetailById(merchantDTO));
        return "spm/merchant/detail";
    }

    /**
     * 商户增加方法
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/add")
    public @ResponseBody
    Result add(MerchantDTO merchantDTO) {
        merchantDTO.setCreaterId(getLoginUser().getId());
        //代理id
        SessionUserVO user = getLoginUser();
        Result result = addSysLog(user, SystemOperateEnum.INSERT.getType(), "商户新增");
        if (result.isSuccess()) {
            //设置审核等级
            this.setLevelStatus(getLoginUser().getSessionRoleVo().get(0).getRoleCode(), merchantDTO);
            result = this.iMerchantService.addMerchant(merchantDTO);
        }
        return result;
    }

    /**
     * 商户修改方法
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/update")
    public @ResponseBody
    Result update(MerchantDTO merchantDTO) {
        merchantDTO.setUpdaterId(getLoginUser().getId());
        //代理id
        SessionUserVO user = getLoginUser();
        Result result = addSysLog(user, SystemOperateEnum.UPDATE.getType(), "商户修改");
        if (result.isSuccess()) {
            //设置审核等级
            this.setLevelStatus(getLoginUser().getSessionRoleVo().get(0).getRoleCode(), merchantDTO);
            result = this.iMerchantService.updateMerchant(merchantDTO);
        }
        return result;
    }
    /**
     * 修改商户冻结
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/updateMerchantByFreeze")
    public @ResponseBody
    Result updateMerchantByFreeze(MerchantDTO merchantDTO) {
        merchantDTO.setIsFrozen(merchantIsFrozen.ONE.getIndex());
        return iMerchantService.updateMerchantByIsFrozen(updateMerchantByFiled(merchantDTO,"商户冻结"));
    }

    /**
     * 修改商户解冻
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/updateMerchantByThaw")
    public @ResponseBody
    Result updateMerchantByThaw(MerchantDTO merchantDTO) {
        merchantDTO.setIsFrozen(merchantIsFrozen.ZERO.getIndex());
        return iMerchantService.updateMerchantByIsFrozen(updateMerchantByFiled(merchantDTO,"商户解冻"));
    }


    /**
     * 修改商户审核通过
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/updateMerchantByExaminationPassed")
    public @ResponseBody
    Result updateMerchantByExaminationPassed(MerchantDTO merchantDTO) {
        merchantDTO.setStatus(merchantStatus.ONE.getIndex());
        return iMerchantService.updateMerchantByIsFrozen(updateMerchantByFiled(merchantDTO,"商户审核通过"));
    }

    /**
     * 修改商户审核不通过
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/updateMerchantByAuditNotPassed")
    public @ResponseBody
    Result updateMerchantByAuditNotPassed(MerchantDTO merchantDTO) {
        merchantDTO.setStatus(merchantStatus.TWO.getIndex());
        return iMerchantService.updateMerchantByIsFrozen(updateMerchantByFiled(merchantDTO,"商户审核不通过"));
    }

    /**
     * 修改商户逻辑删除
     *
     * @return String
     * @author 熊克文
     * @时间 2017-5-23
     */
    @RequestMapping(value = "/updateMerchantByDelete")
    public @ResponseBody
    Result updateMerchantByDelete(MerchantDTO merchantDTO) {
        merchantDTO.setIsDelete(merchantIsDelete.ONE.getIndex());
        //调用的通用服务层
        return iMerchantService.updateMerchantByIsFrozen(updateMerchantByFiled(merchantDTO,"商户逻辑删除"));
    }

    /**
     * 列表分页查询商户- -商户登录：不能看见商户。代理商：看自己等级下所有的商户。供应商不能看商户
     * @param merchantDTO
     * @return Result
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value="/getMerchantByPage")
    public @ResponseBody Result getMerchantByPage(PageBean pageBean,MerchantDTO merchantDTO){
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        //登录权限判断
        //是否为管理员
        //if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
            //是管理员让他显示管理员置顶的商户
        //    return iMerchantService.getMerchantByPageAdmin(pageBean, merchantDTO);
            //是否为代理商
      //  }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COMMUNITY_AGENT.getCode()){
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setUserId(userBean.getId());
        merchantDTO.setId(userBean.getId());
            //取当前级及下级所有的代理商
            /*List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(agentDTO);
            if(agentList!=null && agentList.size()>0){
                List<Long> ids = new ArrayList<Long>();
                for(AgentVO vo:agentList){
                    ids.add(vo.getId());
                }
                merchantDTO.setAgentIds(ids);
            }*/
     //   }
        //状态正常
      /*  merchantDTO.setIsDelete(merchantIsDelete.ZERO.getIndex());*/
        return iMerchantService.getHunterByPage(pageBean, merchantDTO);
    }

    /**
     * 根据代理商id查询所有所有商户
     * @param merchantDTO
     * @return Result
     * @author 何文浪
     * @时间 2017-5-23
     */
    @RequestMapping(value="/getMerchantByAgentAllList")
    public @ResponseBody List<MerchantVO> getMerchantByAgentAllList(MerchantDTO merchantDTO){
        return iMerchantService.getMerchantByAgentAllList( merchantDTO);
    }

    /**
     * 修改商户对应某些指定默认值字段
     * 该方法主要针对修改时填充修改人及修改时间，当审核操作的时候当前登录人的角色与代理商码表等级互换（代理商同原理）
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-6
     */
    public  MerchantDTO updateMerchantByFiled(MerchantDTO merchantDTO,String content) {
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        Result result = addSysLog(userBean,SystemOperateEnum.UPDATE.getType(),content);
        if(result.isSuccess()){
            merchantDTO.setUpdaterId(userBean.getId());
            merchantDTO.setUpdateTime(new Date());
            if(merchantDTO.getStatus()!=null&&merchantDTO.getStatus()>=0){//针对审核状态操作
                if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
                    //管理员
                    merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
                }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode()){
                    //市级
                    merchantDTO.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
                    if(merchantDTO.getStatus() == merchantStatus.ONE.getIndex()){
                        //市级
                        merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
                        merchantDTO.setStatus(merchantStatus.ZERO.getIndex());
                    }
                }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode()){
                    //县级
                    merchantDTO.setStatusLevel(agentLevel.COUNTY_AGENT.getIndex());
                    if(merchantDTO.getStatus() == merchantStatus.ONE.getIndex()){
                        //县级
                        merchantDTO.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
                        merchantDTO.setStatus(merchantStatus.ZERO.getIndex());
                    }
                }
            }
        }
        return merchantDTO;
    }
    /**
     * 统一记录日志
     * @param userBean
     * @param enumType
     * @param content
     * @return
     */
    public Result addSysLog(SessionUserVO userBean,byte enumType,String content){
        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setCreaterId(userBean.getId());
        systemLogDTO.setOperateAccount(userBean.getTelphone());
        systemLogDTO.setCreaterName(userBean.getUserName());
        systemLogDTO.setOperateType(enumType);
        systemLogDTO.setOperateContent(content);
        return iSystemLogService.addSystemLog(systemLogDTO);
    }

    /**
     * 根据登录用户等级判断
     *  @author 何文浪
     * @return byte
     */
    public byte getUser(){
        byte statusLevel = 2 ;
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
        if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
            statusLevel = agentLevel.ADMIN.getIndex();
        }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode()){
            statusLevel = agentLevel.CITY_AGENT.getIndex();
        }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode()){
            statusLevel =agentLevel.COUNTY_AGENT.getIndex();
        }
        return statusLevel;
    }

    //设置审核状态
    private void setLevelStatus(Byte loginRoleCode, MerchantDTO merchantDTO) {
        merchantDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
        //如果不是管理员和市级代理
        if (!loginRoleCode.equals(RoleEnum.ADMIN.getCode()) && !loginRoleCode.equals(RoleEnum.CITY_AGENT.getCode())) {
            AgentDTO _agentDTO = new AgentDTO();
            _agentDTO.setUserId(getLoginUser().getId());
            AgentVO currentAgent = iAgentService.getAgentVODateilById(_agentDTO);
            //已经按照代理等级排序
            List<AgentVO> agent = iAgentService.getAgentVoByPosition(currentAgent.getCityId(), currentAgent.getCountyId(), currentAgent.getTownId());
            for (AgentVO agentVO : agent) {
                //如果寻找到agentLevel比当前更小的就设置为更小的
                if (agentVO.getAgentLevelId() < (getLoginUser().getSessionRoleVo().get(0).getRoleCode() - 2)) {
                    merchantDTO.setStatusLevel(Byte.valueOf(agentVO.getAgentLevelId().toString()));
                    break;
                }
            }
        }
    }

    /**
     * 输入的商户手机，获取推荐人
     * @param telPhone
     * @return
     * @createTime 2017年07月24日
     */
    @RequestMapping(value="/getPromoterByMerchantTel",method=RequestMethod.POST)
    @ResponseBody
    public Result getPromoterNameByTel(String telPhone){
        MemberQueryShareDTO dto = new MemberQueryShareDTO();
        dto.setToTelPhone(telPhone);
        dto.setType(MemberShareTypeEnum.SHARE_MERCHANT.getCode());
        Result result = memberService.getMemberShare(dto);
        if(result.getData()!=null){
            MemberShareRecordVO memberShareRecordVO = (MemberShareRecordVO) result.getData();
            return ResultUtil.getResult(RespCode.Code.SUCCESS,memberShareRecordVO);
        }else{
            return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
        }
    }

    /**
     * <pre>updateIsRecommend(作用：代理后台商户置顶功能)
     * 创建人：赵俊彪
     * @param
     * @param  isRecommend 		 代理置顶	  3
     */
    @RequestMapping("/updateIsRecommend")
    @ResponseBody
    public  Result updateIsRecommend(Integer isRecommend,String telPhone){
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        //运营中心
        if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
            return iMerchantService.updateIsRecommend(isRecommend,telPhone,userBean.getSessionRoleVo().get(0).getRoleCode());
            //代理
        }else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode()){
            return iMerchantService.updateIsRecommend(isRecommend,telPhone,userBean.getSessionRoleVo().get(0).getRoleCode());
        }
        return ResultUtil.getResult(RespCode.Code.FAIL);
    }
}
