package com.ph.spm.api.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.facade.spm.dto.AgentLevelDTO;
import com.ph.shopping.facade.spm.service.AgentLevelService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.*;
import com.ph.shopping.facade.spm.vo.AgentLevelVO;

/**
 * @项目 phshopping-api-platform
 * @描述   spm枚举控制层
 * @author 何文浪
 * @时间 2017-5-25
 * @version 2.1
 */
@Controller
@RequestMapping("web/enum")
public class EnumController {
	// 代理商类别业务层
	@Reference(version = "1.0.0")
	private AgentLevelService agentLevelService;
	/**
	 * 供应商supplierType ALL("全国",1),LOCAL("本地",2);
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierTypeList")
	@ResponseBody
	public  List<Object> getEnumBySupplierTypeList(){
		return supplierType.getEnumBySupplierTypeList();
	}
	
	/**
	 * 供应商status 供应商审核状态：0待审核，1审核通过，2被驳回，;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierStatusList")
	@ResponseBody
	public  List<Object> getEnumBySupplierStatusList(){
		return supplierStatus.getEnumBySupplierStatusList();
	}
	/**
	 * 供应商isDelete 是否删除 0:否 1：是;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierIsDeleteList")
	@ResponseBody
	public  List<Object> getEnumBySupplierIsDeleteList(){
		return supplierIsDelete.getEnumBySupplierIsDeleteList();
	}
	/**
	 * 供应商isFrozen 是否冻结 0:否 1:是 2.暂冻;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierIsFrozenList")
	@ResponseBody
	public  List<Object> getEnumBySupplierIsFrozenList(){
		return supplierIsFrozen.getEnumBySupplierIsFrozenList();
	}
	/**
	 * 供应商图片 sort 序列 1营业执照  2身份证第一张  3身份第二张 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierImageSortList")
	@ResponseBody
	public  List<Object> getEnumBySupplierImageSortList(){
		return supplierImageSort.getEnumBySupplierImageSortList();
	}
	/**
	 * 供应商图片type 图片类型 1营业执照类型  2身份证类型 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierImageTypeList")
	@ResponseBody
	public  List<Object> getEnumBySupplierImageTypeList(){
		return supplierImageType.getEnumBySupplierImageTypeList();
	}
	/**
	 * 供应商异常枚举码 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumBySupplierExtionList")
	@ResponseBody
	public  List<Object> getEnumBySupplierExtionList(){
		return supplierExtion.getEnumBySupplierExtionList();
	}
	/**
	 * 商户类型 isDelete 是否删除(0.删除，1未删除)默认为1 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantTypeIsDeleteList")
	@ResponseBody
	public  List<Object> getEnumByMerchantTypeIsDeleteList(){
		return merchantTypeIsDelete.getEnumByMerchantTypeIsDeleteList();
	}
	/**
	 * 商户类型 MerchanTypeLevel 分类级别 1 一级分类 2 二级分类  ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantTypeMerchanTypeLevelList")
	@ResponseBody
	public  List<Object> getEnumByMerchantTypeMerchanTypeLevelList(){
		return merchantTypeMerchanTypeLevel.getEnumByMerchantTypeMerchanTypeLevelList();
	}
	/**
	 * 商户 status 0：审核中 1：审核通过 2：未通过 （原来对应值：审核未通过0  审核未通过2  审核通过正常1 冻结  3）  ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantStatusList")
	@ResponseBody
	public  List<Object> getEnumByMerchantStatusList(){
		return merchantStatus.getEnumByMerchantStatusList();
	}
	/**
	 * 商户 isFrozen 是否冻结 0:否 1:是;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantIsFrozenList")
	@ResponseBody
	public  List<Object> getEnumByMerchantIsFrozenList(){
		return merchantIsFrozen.getEnumByMerchantIsFrozenList();
	}
	/**
	 * 商户 isDelete 是否删除 0:否 1：是;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantIsDeleteList")
	@ResponseBody
	public  List<Object> getEnumByMerchantIsDeleteList(){
		return merchantIsDelete.getEnumByMerchantIsDeleteList();
	}
	/**
	 * 商户图片 type 图片类型 1 营业执照图片 2 身份证图片 3 门店照片;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantImageTypeList")
	@ResponseBody
	public  List<Object> getEnumByMerchantImageTypeList(){
		return merchantImageType.getEnumByMerchantImageTypeList();
	}
	/**
	 * 商户异常枚举码 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByMerchantExtionList")
	@ResponseBody
	public  List<Object> getEnumByMerchantExtionList(){
		return merchantExtion.getEnumByMerchantExtionList();
	}
	/**
	 * 代理商 status 审核状态：0：待审核，1：审核通过，2：被驳回;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByAgentStatusList")
	@ResponseBody
	public  List<Object> getEnumByAgentStatusList(){
		return agentStatus.getEnumByAgentStatusList();
	}
	/**
	 * 代理商 isDelete 是否删除 0:否 1：是;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByAgentIsDeleteList")
	@ResponseBody
	public  List<Object> getEnumByAgentIsDeleteList(){
		return agentIsDelete.getEnumByAgentIsDeleteList();
	}
	/**
	 * 代理商 isFrozen 是否冻结 0:否 1:是2.暂冻;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByAgentIsFrozenList")
	@ResponseBody
	public  List<Object> getEnumByAgentIsFrozenList(){
		return agentIsFrozen.getEnumByAgentIsFrozenList();
	}
	/**
	 * 代理商异常枚举码 ;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-23
	 */
	@RequestMapping(value="/getEnumByAgentExtionList")
	@ResponseBody
	public  List<Object> getEnumByAgentExtionList(){
		return agentExtion.getEnumByAgentExtionList();
	}
	
	
	/**
	 * 代理商 status 审核状态+等级：0：待审核，1：审核通过，2：被驳回;
	 * @return List<Object>
	 * @author 何文浪
	 * @时间 2017-5-27
	 */
	@SuppressWarnings({ "static-access"})
	@RequestMapping(value="/getEnumByAgentStatusAndLevelList")
	@ResponseBody
	public  List<Object> getEnumByAgentStatusAndLevelList(AgentLevelDTO agentLevelDTO){
		List<Object> returnList = new ArrayList<Object>();
		List<AgentLevelVO> list = agentLevelService.getAgentLevelList(agentLevelDTO);
		if(list.size()>0){
			for(AgentLevelVO vo:list){
				 for (agentStatus c : agentStatus.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", vo.getId().toString()+","+new String().valueOf(c.getIndex()));
					 map.put("name", "["+vo.getLevelName()+"]"+c.getName());
					 returnList.add(map);
				 }
			}
		}
		return returnList;
	}
}
