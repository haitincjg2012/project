package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.dto.CheckDTO;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.SalesmanMapper;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.vo.MemberVO;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.spm.service.ISalesmanService;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service(version = "1.0.0")
public class SalesmanServiceImpl implements ISalesmanService {

	// 日志
	private final static Logger logger = LoggerFactory.getLogger(SalesmanServiceImpl.class);

	@Autowired
	private SalesmanMapper salesmanMapper;
	@Autowired
	private SmsUtil util;


	/**
	 * 添加业务员
	 */
	@Transactional
	public Result addSaleman(String telPhone, String memberName, String idCardNo, Long agentId) {

		if(idCardNo == null || idCardNo.trim().equals("")){

			return ResultUtil.setResult(false, "身份证号不能为空");
		}

		if(memberName == null || memberName.trim().equals("")){

			return ResultUtil.setResult(false, "用户名不能为空");
		}

		idCardNo = idCardNo.trim();
		memberName = memberName.trim();

		MemberDTO member = salesmanMapper.getMemberByTelPhone(telPhone);


		// 用户不存在，新增业务员
		if(member == null){


			return ResultUtil.setResult(false,"请先注册为会员");
			/*member = new MemberDTO();
			member.setIdCardNo(idCardNo);
			member.setTelPhone(telPhone);
			member.setMemberName(memberName);
			member.setAgentId(agentId);
			member.setMemberPwd("");  //--------------------------------------------------------------------------
			logger.info("添加业务员，业务员信息为："+JSON.toJSONString(member));
			if(salesmanMapper.addSaleman(member)){

				logger.info("添加业务员成功");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}

			logger.info("添加业务员失败");
			return ResultUtil.setResult(false, "添加业务员失败");*/

			//用户已存在，将用户升级为业务员
		}else if(member.getAgentId() == null || member.getAgentId() == 0){

			logger.info("将用户升级为业务员："+JSON.toJSONString(member));
			member.setAgentId(agentId);
			if(salesmanMapper.updateMemberBecomeSaleman(member)){

				logger.info("添加业务员成功");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}

			logger.info("添加业务员失败");
			return ResultUtil.setResult(false, "添加业务员失败");

			// 该用户已是业务员
		}else{
			logger.info("该手机号已是业务员");
			return ResultUtil.setResult(false, "该手机号已被注册为业务员");
		}
	}


	/**
	 * 根据代理查询相应的业务员
	 */
	@Override
	public Result getSalesmanByAgentId(Integer pageNum, Integer pageSize, Long id) {
		if(pageNum == null || pageNum == 0){
			pageNum = 1;
		}

		if(pageSize == null || pageSize == 0){
			pageSize = 10;
		}

		PageHelper.startPage(pageNum, pageSize);

		List<MemberVO> salesList = salesmanMapper.getSalesmanByAgentId(id);
        if(salesList == null || salesList.isEmpty()){
            return ResultUtil.setResult(false,"暂无业务员");
        }
		for (MemberVO sales : salesList) {
			System.out.println(sales.getAgentId()+sales.getMemberName());
		}
		PageInfo<MemberVO> pageInfo = new PageInfo<MemberVO>(salesList);

		return new Result(true, RespCode.Code.SUCCESS.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());

	}

	/**
	 * 查询业务员推广的企业
	 */
	public Result getShareCompanyBySalesman(Long id) {

		List<MerchantVO> companyList= salesmanMapper.getShareCompanyBySalesmanId(id);

		if(companyList != null && !companyList.isEmpty()){

			return ResultUtil.getResult(RespCode.Code.SUCCESS,companyList);
		}

		return ResultUtil.setResult(false, "暂无推广企业");
	}

	/**
	 * 冻结业务员
	 */
	public Result frozenSalesman(Long id,Integer isFrozen) {

		logger.info("冻结/解冻id为"+id+"业务员，isFrozen:"+isFrozen);
		if(salesmanMapper.frozenSalesman(id,isFrozen)){

			logger.info("冻结/解冻成功");
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		}
		logger.info("冻结/解冻失败");
		return ResultUtil.setResult(false, "冻结业务员失败");
	}
	public Result saleManDataList(AdAtachmentDTO dto, String telPhone){
		logger.info("加载会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		map.put("pageNum", dto.getPageNum());
		map.put("pageSize", dto.getPageSize());
		map.put("telPhone", telPhone);
		List<Map> list = salesmanMapper.saleManDataList(map);
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());

	}

	@Override
	public Result saveSaleManData(String code, String name, String phone, String card, String username) {
		//检验验证码
		try{

		CheckDTO dto = new CheckDTO();
		dto.setCode(code);
		dto.setPhone(phone);
		dto.setCodeType("Fr170001");
		Result check = util.check(dto);
		if (!"1".equals(check.getCode())){
			return ResultUtil.setResult(false, check.getMessage());
		}
		//先通过用户名效验该代理是否存在
		Map map = salesmanMapper.findAgentIdByUserName(username);
		if(map == null){
			return ResultUtil.setResult(false, "没有此关联的代理");
		}
		Long gId=(Long)map.get("id");

		Map memberIdByPhone = salesmanMapper.findMemberIdByPhone(phone);
		if(memberIdByPhone == null){
			return ResultUtil.setResult(false, "没有此用户");
		}
        //member中关联agent数据
			salesmanMapper.addMember(name,phone,card,gId);


		  return ResultUtil.setResult(true, "保存成功");
	  }catch(Exception e){
	  	logger.info("保存业务数据异常："+e);
		  return ResultUtil.setResult(false, "保存失败");
	  }

	}

	@Override
	public Result udpateFrozenType(Long frozen) {
		try {
			Map dataById = salesmanMapper.findDataById(frozen);
			if (dataById == null) {
				return ResultUtil.setResult(false, "没有此数据");
			}
			Long type = 0L;
			if ((Long) dataById.get("isFrozen") == 0) {
				type = 1L;
			} else if ((Long) dataById.get("isFrozen") == 1) {
				type = 0L;
			}
			salesmanMapper.updateFrozenTypeById(type, frozen);
			if (type == 0L){
				return ResultUtil.setResult(true, "解冻成功");
			}else{
				return ResultUtil.setResult(true, "冻结成功");
			}
		} catch (Exception e) {
			return ResultUtil.setResult(false, "修改失败");
		}
	}
}
