package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;


public interface ISalesmanService {

	// 添加业务员
	Result addSaleman(String telPhone, String memberName, String idCardNo, Long agentId);


	// 查询代理对应的业务员
	Result getSalesmanByAgentId(Integer pageNum, Integer pageSize, Long id);


	// 查询业务员推广的企业
	Result getShareCompanyBySalesman(Long id);


	// 冻结业务员
	Result frozenSalesman(Long id,Integer isFrozen);

	/**
	 * 业务首页返回的数据
	 * @param dto
	 * @return
	 */
	public Result saleManDataList(AdAtachmentDTO dto, String telPhone);

	//业务保存到数据库
	public Result saveSaleManData(String code,String name,String phone,String card,String userName);

	//修改冻结状态
	public Result udpateFrozenType(Long frozen);



}
