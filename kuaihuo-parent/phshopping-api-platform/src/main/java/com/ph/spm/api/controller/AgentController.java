package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.AgentApplyDTO;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentIsDelete;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentIsFrozen;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentLevel;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.agentStatus;
import com.ph.shopping.facade.spm.util.GenerateXSSFExcel;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @项目 phshopping-api-platform
 * @描述   代理商控制层
 * @author 何文浪
 * @时间 2017-5-25
 * @version 2.1
 */
@Controller
@RequestMapping("web/agent")
public class AgentController extends BaseController{
	//代理商业务层
	@Reference(version = "1.0.0")
	private IAgentService iAgentService;
	//短信接口
	@Autowired
	private ISmsDataService iSmsDataService;
	//系统日志添加
	@Reference(version = "1.0.0")
	private ISystemLogService iSystemLogService;
	/**
	 * 导出代理商
	 * @param response,AgentDTO
	 * @return void
	 * @author 何文浪
	 * @时间 2017-6-8
	 */
	@RequestMapping(value = "/getExport")
    @ResponseBody
    public void partExport(HttpServletResponse response, AgentDTO agentDTO) {
		if (agentDTO.getIsFormSystem() == 0) {
			// 获取当前登录用户
			SessionUserVO userBean = getLoginUser();
			Result result = addSysLog(userBean, SystemOperateEnum.IMPORT_EXPORT.getType(), "商户代理导出");
			if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.ADMIN.getCode()) {
			} else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.COMMUNITY_AGENT.getCode()) {
				AgentDTO dto = new AgentDTO();
				dto.setUserId(userBean.getId());
				List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(dto);
				if (agentList != null && agentList.size() > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (AgentVO vo : agentList) {
						ids.add(vo.getId());
					}
					agentDTO.setIds(ids);
				}
			}
			agentDTO.setIsDelete(agentIsDelete.ZERO.getIndex());
			List<AgentVO> list = new ArrayList<AgentVO>();
			if (result.isSuccess()) {
				list = iAgentService.getAgentList(agentDTO);
			}
			if (list != null && list.size() > 0) {//处理状态码
				for (AgentVO vo : list) {
					if (vo.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.CITY_AGENT.getName());
					} else if (vo.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.COUNTY_AGENT.getName());
					} else if (vo.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.COMMUNITY_AGENT.getName());
					}
					if (vo.getStatus() == agentStatus.ZERO.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.ZERO.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.ZERO.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.ZERO.getName());
						}
					} else if (vo.getStatus() == agentStatus.ONE.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.ONE.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.ONE.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.ONE.getName());
						}
					} else if (vo.getStatus() == agentStatus.TWO.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.TWO.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.TWO.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.TWO.getName());
						}
					}
					if (vo.getIsFrozen() == agentIsFrozen.ZERO.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.ZERO.getName());
					} else if (vo.getIsFrozen() == agentIsFrozen.ONE.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.ONE.getName());
					} else if (vo.getIsFrozen() == agentIsFrozen.TWO.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.TWO.getName());
					}
				}
			}
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("levelName", "代理级别");
			headMap.put("agentName", "企业名称");
			headMap.put("telPhone", "代理账号");
			headMap.put("addressName", "管辖区域");
			headMap.put("parentName", "上级代理");
			headMap.put("statusName", "审核状态");
			headMap.put("isFrozenName", "冻结状态");
			headMap.put("createTime", "创建时间");
			headMap.put("updateTime", "修改时间");
			String title = "代理商导出";
			GenerateXSSFExcel excel = new GenerateXSSFExcel();
			excel.downloadExcelFile(title, headMap, list, response);
		}

		if(agentDTO.getIsFormSystem() == 1){
			// 获取当前登录用户
			SessionUserVO userBean = getLoginUser();
			Result result = addSysLog(userBean, SystemOperateEnum.IMPORT_EXPORT.getType(), "批发商代理导出");
			if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.ADMIN.getCode()) {
			} else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.WHOLESALE_CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.WHOLESALE_COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.WHOLESALE_COMMUNITY_AGENT.getCode()) {
				AgentDTO dto = new AgentDTO();
				dto.setUserId(userBean.getId());
				List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(dto);
				if (agentList != null && agentList.size() > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (AgentVO vo : agentList) {
						ids.add(vo.getId());
					}
					agentDTO.setIds(ids);
				}
			}
			agentDTO.setIsDelete(agentIsDelete.ZERO.getIndex());
			List<AgentVO> list = new ArrayList<AgentVO>();
			if (result.isSuccess()) {
				list = iAgentService.getAgentList(agentDTO);
			}
			if (list != null && list.size() > 0) {//处理状态码
				for (AgentVO vo : list) {
					if (vo.getAgentLevelId() == agentLevel.CITY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.CITY_AGENT.getName());
					} else if (vo.getAgentLevelId() == agentLevel.COUNTY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.COUNTY_AGENT.getName());
					} else if (vo.getAgentLevelId() == agentLevel.COMMUNITY_AGENT.getIndex()) {
						vo.setLevelName(agentLevel.COMMUNITY_AGENT.getName());
					}
					if (vo.getStatus() == agentStatus.ZERO.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.ZERO.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.ZERO.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.ZERO.getName());
						}
					} else if (vo.getStatus() == agentStatus.ONE.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.ONE.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.ONE.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.ONE.getName());
						}
					} else if (vo.getStatus() == agentStatus.TWO.getIndex()) {
						if (vo.getStatusLevel() == agentLevel.CITY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.CITY_AGENT.getName() + "】" + agentStatus.TWO.getName());
						} else if (vo.getStatusLevel() == agentLevel.COUNTY_AGENT.getIndex()) {
							vo.setStatusName("【" + agentLevel.COUNTY_AGENT.getName() + "】" + agentStatus.TWO.getName());
						} else if (vo.getStatusLevel() == agentLevel.ADMIN.getIndex()) {
							vo.setStatusName("【" + agentLevel.ADMIN.getName() + "】" + agentStatus.TWO.getName());
						}
					}
					if (vo.getIsFrozen() == agentIsFrozen.ZERO.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.ZERO.getName());
					} else if (vo.getIsFrozen() == agentIsFrozen.ONE.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.ONE.getName());
					} else if (vo.getIsFrozen() == agentIsFrozen.TWO.getIndex()) {
						vo.setIsFrozenName(agentIsFrozen.TWO.getName());
					}
				}
			}
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("agentName", "企业名称");
			headMap.put("telPhone", "代理账号");
			headMap.put("addressName", "管辖区域");
			headMap.put("statusName", "审核状态");
			headMap.put("isFrozenName", "冻结状态");
			headMap.put("createTime", "创建时间");
			headMap.put("updateTime", "修改时间");
			String title = "代理商导出";
			GenerateXSSFExcel excel = new GenerateXSSFExcel();
			excel.downloadExcelFile(title, headMap, list, response);
		}
	}


    /**
	 * 代理商列表页面
	 * @return String
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/list")
	public String list(Model model,Integer parentId){
		model.addAttribute("statusLevel", getUser());
		model.addAttribute("parentId", parentId);
		return "spm/agent/agentList";
	}
	/**
	 * 批发代理商列表页面
	 * @return String
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/list2")
	public String list2(Model model,Integer parentId){
		model.addAttribute("statusLevel", getUser());
		model.addAttribute("parentId", parentId);
		return "spm/agent/agentListWholesaler";
	}
	/**
	 * 扩展下级代理商列表页面
	 * @return String
	 * @author 何文浪
	 * @时间 2017-6-12
	 */
	@RequestMapping(value="/nextList")
	public String nextList(Model model,Long parentId,String parentName){
		model.addAttribute("statusLevel", getUser());
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentName", parentName);
		return "spm/agent/agentList";
	}

	/**
	 * 列表分页查询代理商  商户登录不能看代理商。供应商登录不能看代理商。代理商登录看自己包括下级代理商
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getAgentByPage")
	public @ResponseBody Result getAgentByPage(PageBean pageBean,AgentDTO agentDTO,Long findType){
		if(findType==0){
			agentDTO.setIsFormSystem((byte)0);
			// 获取当前登录用户
			SessionUserVO userBean = getLoginUser();
			if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
			}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COMMUNITY_AGENT.getCode()){
				//代理商角色登录只能看下级代理商
				AgentDTO dto = new AgentDTO();
				dto.setUserId(userBean.getId());
				List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(dto);
				if(agentList!=null && agentList.size()>0){
					List<Long> ids = new ArrayList<Long>();
					for(AgentVO vo:agentList){
						if(!String.valueOf(vo.getUserId()).equals(String.valueOf(userBean.getId())))
							ids.add(vo.getId());
					}
					agentDTO.setIds(ids);
				}
			}
			agentDTO.setIsDelete(agentIsDelete.ZERO.getIndex());
			return iAgentService.getAgentPage(pageBean, agentDTO);
		}
		agentDTO.setIsFormSystem((byte)1);
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
		}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode() || userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COMMUNITY_AGENT.getCode()){
			//代理商角色登录只能看下级代理商
			AgentDTO dto = new AgentDTO();
			dto.setUserId(userBean.getId());
			List<AgentVO> agentList = iAgentService.getPositionGetNextAgentList(dto);
			if(agentList!=null && agentList.size()>0){
				List<Long> ids = new ArrayList<Long>();
				for(AgentVO vo:agentList){
					if(!String.valueOf(vo.getUserId()).equals(String.valueOf(userBean.getId())))
						ids.add(vo.getId());
				}
				agentDTO.setIds(ids);
			}
		}
		agentDTO.setIsDelete(agentIsDelete.ZERO.getIndex());
		return iAgentService.getAgentPage(pageBean, agentDTO);

	}
	/**
	 * 修改代理商-逻辑删除
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentDelete")
	public @ResponseBody Result updateAgentDelete(AgentDTO agentDTO){
		agentDTO.setIsDelete(agentIsDelete.ONE.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商逻辑删除"));
	}
	/**
	 * 修改代理商审核通过
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentExaminationPassed")
	public @ResponseBody Result updateAgentExaminationPassed(AgentDTO agentDTO){
		agentDTO.setStatus(agentStatus.ONE.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商审核通过"));
	}


    /**
	 * 修改代理商审核不通过
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentAuditNotPassed")
	public @ResponseBody Result updateAgentAuditNotPassed(AgentDTO agentDTO){
		agentDTO.setStatus(agentStatus.TWO.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商审核不通过"));
	}
	/**
	 * 修改代理商解冻
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentThaw")
	public @ResponseBody Result updateAgentThaw(AgentDTO agentDTO){
		agentDTO.setIsFrozen(agentIsFrozen.ZERO.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商解冻"));
	}
	/**
	 * 修改代理商暂冻
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentFrozen")
	public @ResponseBody Result updateAgentFrozen(AgentDTO agentDTO){
		agentDTO.setIsFrozen(agentIsFrozen.TWO.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商暂冻"));
	}
	/**
	 * 修改代理商冻结
	 * @param agentDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/updateAgentFreeze")
	public @ResponseBody Result updateAgentFreeze(AgentDTO agentDTO){
		agentDTO.setIsFrozen(agentIsFrozen.ONE.getIndex());
		return iAgentService.updateAgent(updateAgentByField(agentDTO,"代理商冻结"));
	}
	/**
	 * 代理商详情页面
	 *
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-5
	 */
	@RequestMapping(value = "/detail")
    public String detail(AgentDTO agentDTO, Model model) {
        model.addAttribute("agentVO", iAgentService.getAgentVODateilById(agentDTO));
        return "spm/agent/detail";
	}

    /**
     * 代理条款页面
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-5
     */
    @RequestMapping(value = "/proxyServiceAgreement")
    public String proxyServiceAgreement() {
        return "spm/agent/proxyServiceAgreement";
    }

	/**
	 * 代理商增加或者修改页面
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-5
	 */
	/**
	 * 代理商增加或者修改页面
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-5
	 */
	@RequestMapping(value = "/addOrUpdate")
	public String addOrUpdate(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, Model model,Long addType) {
		if(addType==0){
			AgentVO agentVO = new AgentVO();
			Byte loginRoleCode = (byte) -1;
			if (operateType.equalsIgnoreCase("update")) {
				AgentDTO agentDTO = new AgentDTO();
				agentDTO.setId(id);
				agentVO = iAgentService.getAgentVODateilById(agentDTO);
			}
			if (!operateType.equalsIgnoreCase("applyAgent")) {
				loginRoleCode = getLoginUser().getSessionRoleVo().get(0).getRoleCode();
			}
			model.addAttribute("agentVO", agentVO);
			model.addAttribute("loginRoleCode", loginRoleCode);
			model.addAttribute("smsCodeTypeCode", SmsCodeType.APPLY_AGENT_VC.getCodeType());
			model.addAttribute("sourceCode", SmsSourceEnum.AGENT.getCode());
			model.addAttribute("operateType", operateType);
			return "spm/agent/addOrUpdate";
		}
		AgentVO agentVO = new AgentVO();
		Byte loginRoleCode = (byte) -1;
		if (operateType.equalsIgnoreCase("update")) {
			AgentDTO agentDTO = new AgentDTO();
			agentDTO.setId(id);
			agentVO = iAgentService.getAgentVODateilById(agentDTO);
		}
		if (!operateType.equalsIgnoreCase("applyAgent")) {
			loginRoleCode = getLoginUser().getSessionRoleVo().get(0).getRoleCode();
		}
		model.addAttribute("agentVO", agentVO);
		model.addAttribute("loginRoleCode", loginRoleCode);
		model.addAttribute("smsCodeTypeCode", SmsCodeType.APPLY_AGENT_VC.getCodeType());
		model.addAttribute("sourceCode", SmsSourceEnum.AGENT.getCode());
		model.addAttribute("operateType", operateType);
		return "spm/agent/addOrUpdateWholesaler";
	}

	/**
	 * 代理商申请方法
	 *
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-8
	 */
	@RequestMapping(value = "/applyAgent")
	public @ResponseBody
	Result applyAgent(AgentApplyDTO agentDTO) {
		//1.短信验证
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(agentDTO.getTelPhone());
		sendData.setSmsCode(agentDTO.getYzm());
		sendData.setType(SmsCodeType.APPLY_AGENT_VC);
		sendData.setSourceEnum(SmsSourceEnum.AGENT);
		Result result = this.iSmsDataService.checkSmsCodeByMobile(sendData);
		if (result.isSuccess()) {
			//没有登录人设置为0
			agentDTO.setCreaterId(0L);
            //没有代理默认设置审核等级为管理员
            List<AgentVO> agentVOList = iAgentService.getAgentVoByPosition(agentDTO.getCityId(), agentDTO.getCountyId(), agentDTO.getTownId());
            if (CollectionUtils.isEmpty(agentVOList)) {
                agentDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
            } else {
                agentDTO.setStatusLevel(Byte.valueOf(agentVOList.get(0).getAgentLevelId().toString()));
            }
            agentDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
            result = this.iAgentService.addAgent(agentDTO);
        }

		return result;
	}

	/**
	 * 代理商增加方法
	 *
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-5
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody
    Result add(AgentDTO agentDTO) {
        SessionUserVO sessionUserVO = getLoginUser();
        //创建id
        agentDTO.setCreaterId(sessionUserVO.getId());
        Result result = addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "代理商添加");
        if (result.isSuccess()) {
            //设置审核等级
            this.setLevelStatus(getLoginUser().getSessionRoleVo().get(0).getRoleCode(),agentDTO);
            result = this.iAgentService.addAgent(agentDTO);
        }
        return result;
    }


	/**
	 * 代理商修改方法
	 *
	 * @return String
	 * @author 熊克文
	 * @时间 2017-6-5
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody
	Result update(AgentDTO agentDTO) {
		agentDTO.setUpdaterId(getLoginUser().getId());
        Result result = addSysLog(getLoginUser(), SystemOperateEnum.UPDATE.getType(), "代理商修改");
        if (result.isSuccess()) {
            //设置审核等级
            this.setLevelStatus(getLoginUser().getSessionRoleVo().get(0).getRoleCode(),agentDTO);
            result = this.iAgentService.updateAgentAndImg(agentDTO);
		}
		return result;
	}
	/**
     * 修改代理对应某些指定默认值字段
     * 该方法主要针对修改时填充修改人及修改时间，当审核操作的时候当前登录人的角色与代理商码表等级互换
     *
     * @return String
     * @author hewl
     * @时间 2017-6-6
	 */
	public AgentDTO updateAgentByField(AgentDTO dto,String content){
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		Result result = addSysLog(userBean,SystemOperateEnum.UPDATE.getType(),content);
		if(result.isSuccess()){
			dto.setUpdaterId(userBean.getId());
			dto.setUpdateTime(new Date());
			if(dto.getStatus()!=null&&dto.getStatus()>0){//针对审核操作，设置被操作人的审核等级和审核状态
				if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
					dto.setStatusLevel(agentLevel.ADMIN.getIndex());
				}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode()){
					dto.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
					if(dto.getStatus() == agentStatus.ONE.getIndex()){
						dto.setStatus(agentStatus.ZERO.getIndex());
						dto.setStatusLevel(agentLevel.ADMIN.getIndex());
					}
				}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode()){
					dto.setStatusLevel(agentLevel.COUNTY_AGENT.getIndex());
					if(dto.getStatus() == agentStatus.ONE.getIndex()){
						dto.setStatus(agentStatus.ZERO.getIndex());
						dto.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
					}
				}
			}
		}
		return dto;
	}

	/**
	 * 统一记录日志
	 * @param userBean
	 * @param enumType
	 * @param content
	 * @return
	 */
	public Result addSysLog(SessionUserVO userBean, byte enumType, String content) {
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
	public byte getUser() {
        byte statusLevel = 2;
        // 获取当前登录用户
        SessionUserVO userBean = getLoginUser();
        /** 审核等级：1.市级代理    2. 县级代理, 4.ADMIN */
        if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.ADMIN.getCode()) {
            statusLevel = agentLevel.ADMIN.getIndex();
        } else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.CITY_AGENT.getCode()) {
            statusLevel = agentLevel.CITY_AGENT.getIndex();
        } else if (userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.COUNTY_AGENT.getCode()) {
            statusLevel = agentLevel.COUNTY_AGENT.getIndex();
        }
        return statusLevel;
    }

    /**
     * 处理代理状态
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-6
     */
    private void setLevelStatus(Byte loginRoleCode, AgentDTO agentDTO) {
        agentDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
        //如果不是管理员
        if (!loginRoleCode.equals(RoleEnum.ADMIN.getCode()) && !loginRoleCode.equals(RoleEnum.CITY_AGENT.getCode())) {
            AgentDTO _agentDTO = new AgentDTO();
            _agentDTO.setUserId(getLoginUser().getId());
            AgentVO currentAgent = iAgentService.getAgentVODateilById(_agentDTO);
            //已经按照代理等级排序
            List<AgentVO> agent = iAgentService.getAgentVoByPosition(currentAgent.getCityId(), currentAgent.getCountyId(), currentAgent.getTownId());
            for (AgentVO agentVO : agent) {
                //如果寻找到agentLevel比当前更小的就设置为更小的
                if (agentVO.getAgentLevelId() < (getLoginUser().getSessionRoleVo().get(0).getRoleCode() - 2)) {
                    agentDTO.setStatusLevel(Byte.valueOf(agentVO.getAgentLevelId().toString()));
                    break;
                }
			}
		}
	}
}
