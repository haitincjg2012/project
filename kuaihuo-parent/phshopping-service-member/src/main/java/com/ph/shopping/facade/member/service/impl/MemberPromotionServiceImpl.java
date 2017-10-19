package com.ph.shopping.facade.member.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MemberMapper;
import com.ph.shopping.facade.mapper.MemberPromotionMapper;
import com.ph.shopping.facade.mapper.ProfitOptLogMapper;
import com.ph.shopping.facade.member.dto.CheckMemberPromotionDTO;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.dto.PromotionRecordDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.entity.MemberPromotion;
import com.ph.shopping.facade.member.entity.ProfitOptLog;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificationEnum;
import com.ph.shopping.facade.member.menum.member.MemberIsMarketingEnum;
import com.ph.shopping.facade.member.menum.member.MemberStatusEnum;
import com.ph.shopping.facade.member.menum.profitopt.ProfitOptLogIsProfitEnum;
import com.ph.shopping.facade.member.menum.profitopt.ProfitOptLogUseTypeEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionAccountTypeEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionDeleteEnum;
import com.ph.shopping.facade.member.menum.promotion.PromotionStatusEnum;
import com.ph.shopping.facade.member.service.IMemberPromotionService;
import com.ph.shopping.facade.member.vo.MemberPromotionVO;
import com.ph.shopping.facade.member.vo.PromotionRecordVO;
import com.ph.shopping.util.MD5Util;

/**
 * @项目：phshopping-service-member
 * @描述：会员推广师接口实现
 * @作者： liuy
 * @创建时间：2017-05-25
 * @Copyright @2017 by liuy
 */
@Component
@Service(version = "1.0.0")
public class MemberPromotionServiceImpl implements IMemberPromotionService {

	//日志
    private static Logger logger = LoggerFactory.getLogger(MemberPromotionServiceImpl.class);

    //推广师Mapper
    @Autowired
    MemberPromotionMapper memberPromotionMapper;

    //会员Mapper
    @Autowired
    MemberMapper memberMapper;

    //影响分润操作流水表Mapper
    @Autowired
    ProfitOptLogMapper profitOptLogMapper;

    //新业态推广师校验
    @Value("${xyt_server}")
    private String xytServer;

    //本地电商推广师校验
    @Value("${xyt_server_bd}")
    private String xytServerBd;

    @Override
    public Result getMemberAuthListByPage(PageBean pageBean, MemberPromotionDTO memberAuthDTO) {
        logger.info("分页查询推广师列表 入参 ,memberAuthDTO={} ", JSON.toJSONString(memberAuthDTO));
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<MemberPromotionVO> list = memberPromotionMapper.getMemberAuthList(memberAuthDTO);
        PageInfo<MemberPromotionVO> pageInfo = new PageInfo<MemberPromotionVO>(list);
        return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Result getMemberAuthList(MemberPromotionDTO dto) {
        logger.info("查询推广师列表（不分页） 入参 ,dto={} ", JSON.toJSONString(dto));
        return ResultUtil.getResult(RespCode.Code.SUCCESS, memberPromotionMapper.getMemberAuthList(dto));
    }

    @Override
    public Result getMemberAuthInfoByCondition(MemberPromotionDTO dto) {
    	 logger.info("根据条件查询推广师信息 入参 ,MemberAuthDTO={} ", JSON.toJSONString(dto));
        return ResultUtil.getResult(RespCode.Code.SUCCESS, memberPromotionMapper.getMemberAuthInfoByCondition(dto));
    }
    
	@Override
	@Transactional
    public Result apply(MemberPromotionDTO dto) {
        logger.info("推广师申请入参,memberAuthDTO={}", JSON.toJSONString(dto));
        try {
			//1.验证图片参数是否为空
			String errorStr = dto.validateForm();
			if (errorStr!=null&&!errorStr.equals("null")) {
				return new Result(false,RespCode.Code.REQUEST_DATA_ERROR.getCode(),  errorStr);
			}
            boolean flag = false; //会员已经绑定推广师，但是推广师审核状态为非未通过（待审核、审核通过）；

			//1.验证会员是否实名认证
			Member member = memberMapper.selectByPrimaryKey(dto.getMemberId());
			if(member!=null && member.getCertification()!=null && !member.getCertification().equals(MemberCertificationEnum.VERIFIED.getCode())){
				return ResultUtil.getResult(MemberResultEnum.MEMBER_CERTIFICATE_NON);
			}
            //2.判断会员推广师审核状态 （ a.待审核、审核通过 ; b.未通过 ）
            MemberPromotionDTO memberAuthDTOById = new MemberPromotionDTO();
            memberAuthDTOById.setMemberId(dto.getMemberId());
            final MemberPromotionVO auth = memberPromotionMapper.getMemberAuthInfoByCondition(memberAuthDTOById);
            if (null != auth) {
            	//a.状态为待审核、审核通过，返回"该会员已申请了推广师"
                if (!PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(auth.getStatus())) {
                    logger.warn("该会员已申请了推广师");
                    return ResultUtil.getResult(MemberResultEnum.MEMBERAUTH_EXIST);
                }else{//b.状态为审核未通过，flag = true，用于执行修改操作
                	flag = true;
                }
            }
            //3.校验第三方账号是否为本地电商或新业态
            String url = xytServer;
            url = PromotionAccountTypeEnum.PROMOTION_LOCAL.getCode().equals(dto.getAccountType()) ? xytServerBd : url;
            Result checkReturn = checkAccount(dto, url, dto.getAccountType()); // 调用接口校验,0校验成功，20055账号不存在 ，20056 密码错误，20055 校验失败
            if (!checkReturn.isSuccess()) {
                return checkReturn;
            }
            //4.判断第三方账号是否已经绑定推广师
            MemberPromotionDTO memberAuthDTOByAcc = new MemberPromotionDTO();
            memberAuthDTOByAcc.setAccount(dto.getAccount());
            memberAuthDTOByAcc.setAccountType(dto.getAccountType());
            memberAuthDTOByAcc.setMemberId(dto.getMemberId());
            final MemberPromotionVO authtrue = memberPromotionMapper.selectPromotionIsExist(memberAuthDTOByAcc);
            if (null != authtrue) {
                logger.warn("账号已经被绑定推广师");
                return ResultUtil.getResult(MemberResultEnum.ACCOUNT_EXIST);
            }
           //5.封装推广师相关数据
            MemberPromotion memberAuth = new MemberPromotion();
            BeanUtils.copyProperties(dto, memberAuth);
            memberAuth.setStatus(PromotionStatusEnum.PROMOTION_AUDIT.getCode());//推广师状态待审核
            //6.操作推广师表,注意这里不会去操作会员表是否推广师字段，要审核的时候才操作
            if (flag) {// 修改操作
                memberAuth.setId(auth.getId());
                memberAuth.setUpdateTime(new Date());
                memberPromotionMapper.updateByPrimaryKeySelective(memberAuth);
            } else {// 新增操作
                memberAuth.setIsDelete(PromotionDeleteEnum.PROMOTION_NORMAL.getCode());
                memberAuth.setCreateTime(new Date());
                memberPromotionMapper.insert(memberAuth);
            }
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
            logger.error("推广师申请错误,ex={}", e);
            throw new MemberException("推广师申请错误");
        }
    }

    @Override
    @Transactional
    public Result check(CheckMemberPromotionDTO checkMemberAuthDTO) {
        logger.info("审核推广师入参,checkMemberAuthDTO={}", JSON.toJSONString(checkMemberAuthDTO));
        try {
            //1.验证会员id、推广师原审核状态、推广师审核状态、修改人参数是否为空
			String errorStr = checkMemberAuthDTO.validateForm();
			if (errorStr!=null&&!errorStr.equals("null")) {
				return new Result(false,RespCode.Code.REQUEST_DATA_ERROR.getCode(),  errorStr);
			}
			
			//2.根据会员ID查询会员对象 用于获取是否推广师和会员状态
			Member member = this.memberMapper.selectByPrimaryKey(checkMemberAuthDTO.getMemberId());
			if(member == null){//如果为空，设置一个默认值，避免空指针
				member = new Member();
				member.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode());
				member.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());
			}
			
			//3.数据初始化
			Member memberSave = null;			  					   	 //先将实体初始化为null,在最后根据是否为null执行数据表操作
			MemberPromotion memberAuthSave = null;						 //先将实体初始化为null
			ProfitOptLog profitOptLogSave = null;						 //先将实体初始化为null
        	Byte originalStatus = checkMemberAuthDTO.getOriginalStatus();//推广师原审核状态
        	Byte newStatus =  checkMemberAuthDTO.getStatus();			 //推广师新审核状态（想要修改成该状态）
			Byte useType = 0 ;	// 是否可分润操作流水 操作类型  0=解冻，1=冻结，2=审核不通过，3=审核通过
			
			//4.会员表、推广师表、分润权限操作流水表数据封装
			
        	//情况1： 待审核——>审核通过，审核不通过——>审核通过。
        	if((PromotionStatusEnum.PROMOTION_AUDIT.getCode().equals(originalStatus) 
        			|| PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(originalStatus)
        			|| PromotionStatusEnum.PROMOTION_NOT_PROFIT.getCode().equals(originalStatus))
        		&& PromotionStatusEnum.PROMOTION_ADOPT.getCode().equals(newStatus)){
        		//(1)修改会员表是否推广师字段为是，修改审核状态为审核通过
        		memberSave = this.setMemberData(checkMemberAuthDTO);
        		memberSave.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
        		memberAuthSave = this.setMemberPromotionData(checkMemberAuthDTO);
        		//(2)记录分润权限操作流水
                useType = ProfitOptLogUseTypeEnum.PASS.getCode();//操作类型 审核通过
                profitOptLogSave = this.setProfitOptLogData(checkMemberAuthDTO, useType, memberSave.getIsMarketing(), member.getStatus(), checkMemberAuthDTO.getStatus());
        	}
        	
        	//情况2： 待审核——>审核不通过
        	if(PromotionStatusEnum.PROMOTION_AUDIT.getCode().equals(originalStatus)
        			&& PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(newStatus)){
        		//(1)不需要修改会员表，修改审核状态为审核不通过
        		memberAuthSave = this.setMemberPromotionData(checkMemberAuthDTO);
        		//(2)记录分润权限操作流水
        		useType = ProfitOptLogUseTypeEnum.NO_PASS.getCode();//操作类型 审核不通过
        		profitOptLogSave = this.setProfitOptLogData(checkMemberAuthDTO, useType, member.getIsMarketing(), member.getStatus(), checkMemberAuthDTO.getStatus());
        	}
        	
        	//情况3： 审核通过——>审核不通过
        	if(PromotionStatusEnum.PROMOTION_ADOPT.getCode().equals(originalStatus)
        			&& PromotionStatusEnum.PROMOTION_FAIL.getCode().equals(newStatus)){
        		//(1)查询推广企业
        		PromotionRecordDTO promotionRecordDTO = new PromotionRecordDTO();
        		BeanUtils.copyProperties(checkMemberAuthDTO, promotionRecordDTO);
        		promotionRecordDTO.setPageNum(1);
        		promotionRecordDTO.setPageSize(10);
        		List<PromotionRecordVO> proRecordList = memberPromotionMapper.selectPromotionRecords(promotionRecordDTO);
        		
        		if(proRecordList!=null && proRecordList.size()!=0 ){//如果推广师存在推广企业
        			//(a)不需要修改会员表， 修改审核状态为不分润
        			memberAuthSave = this.setMemberPromotionData(checkMemberAuthDTO);
        			memberAuthSave.setStatus(PromotionStatusEnum.PROMOTION_NOT_PROFIT.getCode());
            		//(b)记录分润权限操作流水
            		useType = ProfitOptLogUseTypeEnum.NO_PASS.getCode();//操作类型 审核不通过
            		profitOptLogSave = this.setProfitOptLogData(checkMemberAuthDTO, useType, member.getIsMarketing(), member.getStatus(), memberAuthSave.getStatus());
            		
        		}else{//如果推广师不存在推广企业
        			//(a)修改会员表是否推广师字段为否，修改审核状态为审核不通过
        			memberSave = this.setMemberData(checkMemberAuthDTO);
        			memberSave.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode());
        			memberAuthSave = this.setMemberPromotionData(checkMemberAuthDTO);
            		//(b)记录分润权限操作流水
            		useType = ProfitOptLogUseTypeEnum.NO_PASS.getCode();//操作类型 审核不通过
            		profitOptLogSave = this.setProfitOptLogData(checkMemberAuthDTO, useType, memberSave.getIsMarketing(), member.getStatus(), checkMemberAuthDTO.getStatus());
        		}
        		
        	}
        	
        	//5.数据库操作
        	if(memberSave!=null){
                memberMapper.updateByPrimaryKeySelective(memberSave);
        	}
        	if(memberAuthSave!=null){
                memberPromotionMapper.updateStatus(memberAuthSave);
        	}
        	if(profitOptLogSave!=null){
        		profitOptLogMapper.insertUseGeneratedKeys(profitOptLogSave);
        	}
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
            logger.error("审核推广师错误,ex={}", e);
            throw new MemberException("审核推广师错误");
        }
    }

    @Override
    public Result getAllPromRecordByPage(PromotionRecordDTO dto) {
		logger.info("查询推广师推广记录（推广企业）  参数,PromotionRecordDTO ={} ", JSON.toJSONString(dto));
    	PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
		List<PromotionRecordVO> list = memberPromotionMapper.selectPromotionRecords(dto);
		PageInfo<PromotionRecordVO> page = new PageInfo<PromotionRecordVO>(list);
		return ResultUtil.getResult(RespCode.Code.SUCCESS,page.getList(),page.getTotal());
    }

//================================================私有通用方法======================================================/

    /**
     * 新业态和本地推广师接口校验
     * @param memberAuthDTO
     * @param url 新业态或者本地电商校验地址
     * @param type 推广师账号类型
     * @return boolean 0校验成功，1 账号不存在，2 密码错误，3 校验失败
     * @author liuy
     * @createTime 2017年05月25日
     */
    private Result checkAccount(MemberPromotionDTO memberAuthDTO, String url, Byte type) {
    	Result result = ResultUtil.getResult(MemberResultEnum.ACCOUNT_NOT_EXIST);//默认设置 账号必须为本地电商或新业态
		try {
			Map<String, String> callParams = getCheckAccountData(memberAuthDTO, type);
			if (callParams == null || callParams.isEmpty()) {
				return result;
			}
			logger.info("推广师账户校验请求地址url={},callParams={}", url, JSON.toJSONString(callParams));
			String checkResult = HttpClientUtils.sendPost(url, callParams).getResponseContent();
			logger.info("推广师账户校验请求返回值,checkResult={}", JSON.toJSONString(checkResult));
			if (StringUtils.isNotBlank(checkResult)) {
				JSONObject jsonObj = JSON.parseObject(checkResult);
				Object state = jsonObj.get("state");
				Object data = jsonObj.get("data");
				if (state != null && data != null) {
					if("0".equals(state.toString()) && "0000".equals(data.toString())){
						result = ResultUtil.getResult(RespCode.Code.SUCCESS);//验证成功
					}else if("1".equals(state.toString()) && "0007".equals(data.toString())){
						result = ResultUtil.getResult(MemberResultEnum.ACCOUNT_PWD_ERROR);//密码错误
					}
				}
			}
		} catch (Exception ex) {
			logger.error("推广师账户校验异常,ex={}", ex);
        }
        return result;
    }

    /**
     * 封装Member会员实体基础数据
     * @param memberAuthDTO
     * @param type 推广师账号类型
     * @return Map<String, String>
     * @author liuy
     * @createTime 2017年05月25日
     */
    private Map<String, String> getCheckAccountData(MemberPromotionDTO memberAuthDTO, Byte type) {
		Map<String, String> map = ContainerUtil.map();
		//String password = memberAuthDTO.getPassword();
		String account = memberAuthDTO.getAccount();
		map.put("mobile", account);
		map.put("serviceName", "VerifyPromotionDivision");
		/*if (PromotionAccountTypeEnum.PROMOTION_XYT.getCode().equals(type)) {
			String passwordMd5 = MD5Util.MD5Encode(password, "utf-8");
			String valid = MD5Util.MD5Encode(account + passwordMd5, "utf-8");
			map.put("valid", valid);
		} else if (PromotionAccountTypeEnum.PROMOTION_LOCAL.getCode().equals(type)) {
			String valid = MD5Util.MD5Encode("ystbd" + account, "utf-8");
			map.put("newvalid", valid);
		}*/
		String valid = MD5Util.MD5Encode("ystbd" + account, "utf-8");
		map.put("newvalid", valid);
        return map;
    }

    /**
     * 封装Member会员实体基础数据
     * @param checkMemberAuthDTO
     * @return Member
     * @author liuy
     * @createTime 2017年05月25日
     */
    private Member setMemberData(CheckMemberPromotionDTO checkMemberAuthDTO){
    	Member member = new Member();
        member.setId(checkMemberAuthDTO.getMemberId());
        member.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
        member.setUpdateTime(new Date());
        member.setUpdaterId(checkMemberAuthDTO.getUpdaterId());
		return member;
    }

    /**
     * 封装MemberPromotion推广师实体基础数据
     * @param checkMemberAuthDTO
     * @return MemberPromotion
     * @author liuy
     * @createTime 2017年05月25日
     */
    private MemberPromotion setMemberPromotionData (CheckMemberPromotionDTO checkMemberAuthDTO){
    	MemberPromotion memberAuth = new MemberPromotion();
    	memberAuth = new MemberPromotion();
    	memberAuth.setMemberId(checkMemberAuthDTO.getMemberId());
    	memberAuth.setStatus(checkMemberAuthDTO.getStatus().byteValue());
    	memberAuth.setUpdaterId(checkMemberAuthDTO.getUpdaterId());
    	memberAuth.setUpdateTime(new Date());
    	memberAuth.setCheckTime(new Date());// 通过需要修改审核时间
		return memberAuth;
    }
    
    /**
     * 封装ProfitOptLog影响分润操作流水实体基础数据 
     * @param checkMemberAuthDTO 推广师审核DTO
     * @param useType 操作类型
     * @param isMarketing 是否是推广师
     * @param memberStatus 会员状态
     * @param promotionStatus 推广师审核状态
     * @return ProfitOptLog
     * @author liuy
     * @createTime 2017年05月25日
     */
    private ProfitOptLog setProfitOptLogData(CheckMemberPromotionDTO checkMemberAuthDTO,
		Byte useType,Byte isMarketing,Byte memberStatus,Byte promotionStatus){
		Byte isProfit = 0;
		//只有满足是推广师、会员表状态为正常（非冻结）、推广师审核状态为审核通过 三个条件，是否分润才可设置为可分润
		if(isMarketing.equals(MemberIsMarketingEnum.IS_MARKETING_BYYES)&&memberStatus.equals(MemberStatusEnum.MEMBER_NORMAL)
				&&promotionStatus.equals(PromotionStatusEnum.PROMOTION_ADOPT)){
			isProfit = ProfitOptLogIsProfitEnum.PROFIT_YES.getCode();
		}
		//备注
		String remark = "";
		if(isMarketing.equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){
			remark +=  "是否推广师 : "+MemberIsMarketingEnum.IS_MARKETING_BYYES.getRemark()+" ; ";
		}else{
			remark +=  "是否推广师  : "+MemberIsMarketingEnum.IS_MARKETING_BYNO.getRemark()+" ; ";
		}
		if(memberStatus.equals(MemberStatusEnum.MEMBER_NORMAL.getCode())){
			remark +=  "会员状态 : "+MemberStatusEnum.MEMBER_NORMAL.getRemark()+" ; ";
		}else if(memberStatus.equals(MemberStatusEnum.MEMBER_FROZEN.getCode())){
			remark +=  "会员状态 : "+MemberStatusEnum.MEMBER_FROZEN.getRemark()+" ; ";
		}else{
			remark +=  "会员状态 : "+MemberStatusEnum.MEMBER_DELETE.getRemark()+" ; ";
		}
		if(promotionStatus.equals(PromotionStatusEnum.PROMOTION_ADOPT.getCode())){
			remark +=  "推广师审核状态 : "+PromotionStatusEnum.PROMOTION_ADOPT.getRemark()+" ; ";
		}else if(promotionStatus.equals(PromotionStatusEnum.PROMOTION_FAIL)){
			remark +=  "推广师审核状态 : "+PromotionStatusEnum.PROMOTION_FAIL.getRemark()+" ; ";
		}else if(promotionStatus.equals(PromotionStatusEnum.PROMOTION_NOT_PROFIT)){
			remark +=  "推广师审核状态 : "+PromotionStatusEnum.PROMOTION_NOT_PROFIT.getRemark()+" ; ";
		}else{
			remark +=  "推广师审核状态  : "+PromotionStatusEnum.PROMOTION_AUDIT.getRemark()+" ; ";
		}
    	ProfitOptLog profitOptLog = new ProfitOptLog();
    	profitOptLog.setUserId(checkMemberAuthDTO.getMemberId());
    	profitOptLog.setUseType(useType);
    	profitOptLog.setIsProfit(isProfit);
    	profitOptLog.setCreateIp(checkMemberAuthDTO.getCreateIp());
    	profitOptLog.setRemark(remark);
    	profitOptLog.setCreaterId(checkMemberAuthDTO.getUpdaterId());
    	profitOptLog.setCreateTime(new Date());
    	return profitOptLog;
    }
}
