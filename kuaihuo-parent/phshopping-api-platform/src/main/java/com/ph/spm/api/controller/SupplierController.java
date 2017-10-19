package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionRoleVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.IAgentService;
import com.ph.shopping.facade.spm.service.ISupplierService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.*;
import com.ph.shopping.facade.spm.util.GenerateXSSFExcel;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.service.ISystemLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @项目 phshopping-api-platform
 * @描述   供应商控制层
 * @author 何文浪
 * @时间 2017-5-25
 * @version 2.1
 */
@Controller
@RequestMapping("web/supplier")
public class SupplierController extends BaseController{
	//初始化供应商业务层
	@Reference(version = "1.0.0")
	private ISupplierService iSupplierService;
    //代理商业务层
    @Reference(version = "1.0.0")
    private IAgentService iAgentService;
	//系统日志添加
	@Reference(version = "1.0.0")
	private ISystemLogService iSystemLogService;
	/**
	 * 供应商列表页面
	 * @return String
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/list")
    public String list(Model model) {
		model.addAttribute("statusLevel", getUser());
        return "spm/supplier/supplierList";
	}
	
	
	
	/**
	 * 列表分页查询供应商---代理商登录可以看见全国和本地自己添加的。商户不能看见供应商。供应商登录不能看见供应商列表
	 * @param supplierDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getSupplierByPage")
	public @ResponseBody Result getSupplierByPage(PageBean pageBean,SupplierDTO supplierDTO){
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		//登录权限判断
		//是否为管理员
		if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
			
		}else{
			//是扩展创建人查询条件，代理商登录可以看见全国和本地自己添加的
			supplierDTO.setExtendsCreaterId(userBean.getId());
		}
		supplierDTO.setIsDelete(supplierIsDelete.ZERO.getIndex());
		return iSupplierService.getSupplierPage(pageBean, supplierDTO);
	}
	/**
	 * 导出供应商
	 * @param response,SupplierDTO
	 * @return void
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value = "/getExport")
    @ResponseBody
    public void partExport(HttpServletResponse response,SupplierDTO supplierDTO){
		supplierDTO.setIsDelete(supplierIsDelete.ZERO.getIndex());
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		//记录日志
		Result result = addSysLog(userBean,SystemOperateEnum.IMPORT_EXPORT.getType(),"供应商导出");
		//登录权限判断
		if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
			
		}else{
			//是扩展创建人查询条件，代理商登录可以看见全国和本地自己添加的
			supplierDTO.setExtendsCreaterId(userBean.getId());
		}
		supplierDTO.setIsDelete(supplierIsDelete.ZERO.getIndex());
		 List<SupplierVO> list = new ArrayList<SupplierVO>();
		//当记录日志成功时
		if(result.isSuccess()){
        	list = iSupplierService.getSupplierList(supplierDTO);//获取业务数据集
		}
        if(list != null && list.size()>0){//处理状态码
      	   for(SupplierVO vo:list){
      		   if(vo.getStatus() == supplierStatus.ZERO.getIndex()){
      			   vo.setStatusName(supplierStatus.ZERO.getName());
      		   }else if(vo.getStatus() == supplierStatus.ONE.getIndex()){
      			   vo.setStatusName(supplierStatus.ONE.getName());
      		   }else if(vo.getStatus() == supplierStatus.TWO.getIndex()){
      			   vo.setStatusName(supplierStatus.TWO.getName());
      		   }
      		   if(vo.getIsFrozen() == supplierIsFrozen.ZERO.getIndex()){
      			   vo.setIsFrozenName(supplierIsFrozen.ZERO.getName());
      		   }else if(vo.getIsFrozen() == supplierIsFrozen.ONE.getIndex()){
      			   vo.setIsFrozenName(supplierIsFrozen.ONE.getName());
      		   }else if(vo.getIsFrozen() == supplierIsFrozen.TWO.getIndex()){
      			   vo.setIsFrozenName(supplierIsFrozen.TWO.getName());
      		   }
      		   
      		   
	      	  if(vo.getSupplierType() == supplierType.LOCAL.getIndex()){
	    			   vo.setSupplierTypeName(supplierType.LOCAL.getName());
			   }else if(vo.getSupplierType() == supplierType.NATIONAL.getIndex()){
				   vo.setSupplierTypeName(supplierType.NATIONAL.getName());
			   }
      	   }
         }
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("supplierTypeName","类型");
        headMap.put("supplierName","企业名称");
        headMap.put("userTelphone","供应商账号");
        headMap.put("addressName","联系区域");
        headMap.put("statusName","审核状态");
        headMap.put("isFrozenName","冻结状态");
        headMap.put("createTime","创建时间");
        headMap.put("updateTime","修改时间");
        String title = "供应商导出";
        GenerateXSSFExcel excel = new GenerateXSSFExcel();
        excel.downloadExcelFile(title, headMap, list, response);
    }
	/**
	 * 列表查询供应商
	 * @param supplierDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getSupplierByList")
	public @ResponseBody List<SupplierVO> getSupplierByList(SupplierDTO supplierDTO){
		return iSupplierService.getSupplierList(supplierDTO);
	}
	/**
	 * 修改供应商
     * @param supplierDTO
     * @return Result
	 * @author 何文浪
	 * @时间 2017-5-31
	 */
    @RequestMapping(value = "/update")
    public @ResponseBody Result update(SupplierDTO supplierDTO){
        supplierDTO.setUpdaterId(getLoginUser().getId());
		Result result = addSysLog(getLoginUser(), SystemOperateEnum.UPDATE.getType(), "供应商修改");
		if (result.isSuccess()) {
			result = this.iSupplierService.updateSupplier(supplierDTO);
		}
		return result;
	}
    /**
   	 * 修改供应商--冻结
     * @param supplierDTO
     * @return Result
   	 * @author 何文浪
   	 * @时间 2017-5-31
   	 */
       @RequestMapping(value = "/updateSupplierFreeze")
       public @ResponseBody Result updateSupplierFreeze(SupplierDTO supplierDTO){
    	   supplierDTO.setIsFrozen(supplierIsFrozen.ONE.getIndex());
           return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商冻结"));
   	}
     
       
	   /**
	  	* 修改供应商--解冻
	    * @param supplierDTO
	    * @return Result
	  	* @author 何文浪
	  	* @时间 2017-5-31
	  	*/
	  @RequestMapping(value = "/updateSupplierThaw")
	  public @ResponseBody Result updateSupplierThaw(SupplierDTO supplierDTO){
		  supplierDTO.setIsFrozen(supplierIsFrozen.ZERO.getIndex());
	      return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商解冻"));
	 }
      
	  /**
	  	* 修改供应商--暂冻
	    * @param supplierDTO
	    * @return Result
	  	* @author 何文浪
	  	* @时间 2017-5-31
	  	*/
	  @RequestMapping(value = "/updateSupplierFrozen")
	  public @ResponseBody Result updateSupplierFrozen(SupplierDTO supplierDTO){
		  supplierDTO.setIsFrozen(supplierIsFrozen.TWO.getIndex());
	      return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商暂冻"));
	 }
	  
	  /**
	  	* 修改供应商--审核通过
	    * @param supplierDTO
	    * @return Result
	  	* @author 何文浪
	  	* @时间 2017-5-31
	  	*/
	  @RequestMapping(value = "/updateSupplierExaminationPassed")
	  public @ResponseBody Result updateSupplierExaminationPassed(SupplierDTO supplierDTO){
		  supplierDTO.setStatus(supplierStatus.ONE.getIndex());
	      return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商审核通过"));
	 }
	  
	  /**
	  	* 修改供应商--审核不通过
	    * @param supplierDTO
	    * @return Result
	  	* @author 何文浪
	  	* @时间 2017-5-31
	  	*/
	  @RequestMapping(value = "/updateSupplierAuditNotPassed")
	  public @ResponseBody Result updateSupplierAuditNotPassed(SupplierDTO supplierDTO){
		  supplierDTO.setStatus(supplierStatus.TWO.getIndex());
	      return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商审核不通过"));
	 }
	  
	  /**
	  	* 修改供应商--逻辑删除
	    * @param supplierDTO
	    * @return Result
	  	* @author 何文浪
	  	* @时间 2017-5-31
	  	*/
	  @RequestMapping(value = "/updateSupplierDelete")
	  public @ResponseBody Result updateSupplierDelete(SupplierDTO supplierDTO){
		  supplierDTO.setIsDelete(supplierIsDelete.ONE.getIndex());
	      return iSupplierService.updateSupplierByStatus(updateSupplierByFiled(supplierDTO,"供应商逻辑删除"));
	 }
	  
      
    /**
     * 供应商增加方法
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-2
     */
    @RequestMapping(value = "/add")
    public @ResponseBody
    Result add(SupplierDTO supplierDTO) {
        SessionUserVO user = getLoginUser();
        //判断用户是否是管理员
        boolean isAdmin = user.getSessionRoleVo().stream().map(SessionRoleVO::getRoleCode).anyMatch(RoleEnum.ADMIN.getCode()::equals);
        supplierDTO.setCreaterId(user.getId());
        //供应商类型 用户是管理员全国供应商 否则本地供应商号
        supplierDTO.setSupplierType(isAdmin ? SPMEnum.supplierType.NATIONAL.getIndex() : SPMEnum.supplierType.LOCAL.getIndex());
		Result result = addSysLog(user, SystemOperateEnum.INSERT.getType(), "供应商添加");
		if (result.isSuccess()) {
			result = this.iSupplierService.addSupplier(supplierDTO);
		}
		return result;
	}

    /**
     * 供应商详情页面
     *
     * @return String
     * @author 熊克文
     * @时间 2017-6-2
     */
    @RequestMapping(value = "/detail")
    public String detail(Long id, Model model) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setId(id);
        model.addAttribute("supplierVO", iSupplierService.getHunterListDateilById(supplierDTO));
        return "spm/supplier/detail";
    }

    /**
     * 供应商增加或者修改页面
     * @return String
     * @author 熊克文
     * @时间 2017-6-2
     */
    @RequestMapping(value = "/addOrUpdate")
    public String addOrUpdate(@RequestParam(name = "operateType", defaultValue = "add") String operateType, Long id, Model model) {

        SupplierVO supplierVO = new SupplierVO();
        if (operateType.equalsIgnoreCase("update")) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setId(id);
            supplierVO = iSupplierService.getSupplierListDateilById(supplierDTO);
        }
        model.addAttribute("supplierVO", supplierVO);
        model.addAttribute("operateType", operateType);
        return "spm/supplier/addOrUpdate";
    }
    
    /**
	 * 修改供应商--自动填充某些字段
	 * 目前只是起到修改时初始化修改人和修改时间，审核状态暂无处理(当时按照原型需求所做，后面产品稍微变更了需求)
	 * @param supplierDTO
     * @return SupplierDTO
	 * @author 何文浪
	 * @时间 2017-6-6
	 */
    public SupplierDTO updateSupplierByFiled(SupplierDTO supplierDTO,String content){
    	// 获取当前登录用户
    	SessionUserVO userBean = getLoginUser();
    	Result result = addSysLog(userBean,SystemOperateEnum.UPDATE.getType(),content);
		if(result.isSuccess()){
	        supplierDTO.setUpdaterId(userBean.getId());
	        supplierDTO.setUpdateTime(new Date(0));
	        if(supplierDTO.getStatus()!=null&&supplierDTO.getStatus()>0){//针对审核状态操作
				if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.ADMIN.getCode()){
					supplierDTO.setStatusLevel(agentLevel.ADMIN.getIndex());
				}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.CITY_AGENT.getCode()){
					supplierDTO.setStatusLevel(agentLevel.CITY_AGENT.getIndex());
					if(supplierDTO.getStatus() == supplierStatus.ONE.getIndex()){
						supplierDTO.setStatus(supplierStatus.ZERO.getIndex());
					}
				}else if(userBean.getSessionRoleVo().get(0).getRoleCode()==RoleEnum.COUNTY_AGENT.getCode()){
					supplierDTO.setStatusLevel(agentLevel.COUNTY_AGENT.getIndex());
					if(supplierDTO.getStatus() == supplierStatus.ONE.getIndex()){
						supplierDTO.setStatus(supplierStatus.ZERO.getIndex());
					}
				}
			}
		}
        return supplierDTO;
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
}
