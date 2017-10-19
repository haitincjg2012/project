package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.other.BankCardCheckService;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.member.dto.BankCardBindInfoDTO;
import com.ph.shopping.facade.member.dto.MemberBankCardBindDTO;
import com.ph.shopping.facade.member.entity.*;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.bankcard.MemberBankCardBindStatusEnum;
import com.ph.shopping.facade.member.menum.bankcard.MemberBankCardInfoIsDelete;
import com.ph.shopping.facade.member.menum.bankcard.MemberBankCardLogOperationTypeEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificationEnum;
import com.ph.shopping.facade.member.service.IMemberBankCardBindService;
import com.ph.shopping.facade.member.vo.MemberBankCardBindInfoVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 用户绑定银行卡相关实现类
 * @作者： 熊克文
 * @创建时间： 2017/5/26
 * @Copyright by xkw
 */
@Component
@Service(version = "1.0.0")
public class MemberBankCardBindServiceImpl implements IMemberBankCardBindService {

    private static final Logger logger = LoggerFactory.getLogger(MemberBankCardBindServiceImpl.class);

    /**
     * 会员mapper
     */
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 会员卡绑定银行卡mapper
     */
    @Autowired
    private MemberBankCardBindMapper memberBankCardBindMapper;

    /**
     * 银行基础信息mapper
     */
    @Autowired
    private BankCodenameDataMapper bankCodenameDataMapper;

    /**
     * 会员绑定银行卡日志记录mapper
     */
    @Autowired
    private MemberBankCardLogMapper memberBankCardLogMapper;

    /**
     * 会员银行卡基础表
     */
    @Autowired
    private MemberBankCardInfoMapper memberBankCardInfoMapper;

    //银行卡第三方校验接口
    @Autowired
    private BankCardCheckService bankCardCheckService;

    @Transactional
    @Override
    public Result bindMemberBankCard(BankCardBindInfoDTO dto) {
        logger.info("会员绑定银行卡参数:{}", JSON.toJSONString(dto));
        Result result = new Result(false);
        //校验参数
        String validateMessage = dto.validateForm();

        if (Objects.nonNull(validateMessage)) {
            result.setMessage(validateMessage);
        } else {
        	
        	//对银行卡名称进行转义
        	dto.setBankName(replaceCardName(dto.getBankName()));
        	
            //校验用户是否存在
            Member member = this.memberMapper.selectByPrimaryKey(dto.getUserId());

            if (Objects.isNull(member)) {
                logger.error(String.format("非法用户id:%d", dto.getUserId()));
                result.setCode(MemberResultEnum.MEMBERUSER_NON_EXISTENT.getCode());
                result.setMessage(MemberResultEnum.MEMBERUSER_NON_EXISTENT.getMsg());
            } else {
                //校验用户是否已经实名认证成功
                if (!member.getCertification().equals(MemberCertificationEnum.VERIFIED.getCode())) {
                    result.setCode(MemberResultEnum.ID_NOT_CERTIFIED.getCode());
                    result.setMessage(MemberResultEnum.ID_NOT_CERTIFIED.getMsg());
                } else {
                    //if (true) { //测试
                    //校验银行卡是否正确
                    if (this.bankCardCheckService.bankCardAuth(dto.getIdCardNo(), dto.getBankCardNo(), dto.getOwnerName())) {
                        //校验银行基础信息id 开户银行名称 两者必须输入一个
                        if (Stream.of(dto.getBankName(), dto.getBankCodenameDataId())
                                .anyMatch(ParamVerifyUtil::objIsNotNull)) {
                            Date createTime = new Date();
                            //如果银行基础卡id不为空
                            if (Objects.isNull(dto.getBankCodenameDataId())) {
                                //如果为空新增银行开户银行基础表
                                BankCodenameData bankCodenameData = new BankCodenameData();
                                bankCodenameData.setBankCode(dto.getBankCardNo().substring(0, 8));
                                bankCodenameData.setBankName(dto.getBankName());
                                bankCodenameData.setCreateTime(createTime);
                                this.bankCodenameDataMapper.insertUseGeneratedKeys(bankCodenameData);
                                dto.setBankCodenameDataId(bankCodenameData.getId());
                            }

                            MemberBankCardInfo memberBankCardInfo = new MemberBankCardInfo();
                            memberBankCardInfo.setBankCardNo(dto.getBankCardNo());
                            memberBankCardInfo = this.memberBankCardInfoMapper.selectOne(memberBankCardInfo);
                            //判断会员银行卡中是否已经有相同的银行卡
                            if (Objects.isNull(memberBankCardInfo)) {
                                //保存会员银行卡基础信息
                                memberBankCardInfo = VoDtoEntityUtil.convert(dto, MemberBankCardInfo.class);
                                memberBankCardInfo.setCreaterId(dto.getLoginUserId());
                                memberBankCardInfo.setCreateTime(createTime);
                                memberBankCardInfo.setIsDelete(MemberBankCardInfoIsDelete.NOT_DELETE.getCode());
                                this.memberBankCardInfoMapper.insertUseGeneratedKeys(memberBankCardInfo);
                            }

                            MemberBankCardBind isBind = new MemberBankCardBind();
                            isBind.setUserId(dto.getUserId());
                            isBind.setBindStatus(MemberBankCardBindStatusEnum.BIND_ED.getCode());
                            isBind = this.memberBankCardBindMapper.selectOne(isBind);
                            MemberBankCardBind memberBankCardBind;
                            MemberBankBindCardLog memberBankBindCardLog = VoDtoEntityUtil.convert(dto, MemberBankBindCardLog.class);
                            memberBankBindCardLog.setCreateTime(createTime);
                            memberBankBindCardLog.setCreaterId(dto.getLoginUserId());
                            //判断用户之前是否已经绑定过银行卡 如果有则删除 加入新绑定的银行卡
                            if (isBind != null) {
                                //更新绑定操作
                                memberBankCardBind = VoDtoEntityUtil.convert(isBind, MemberBankCardBind.class);
                                memberBankCardBind.setBankCardInfoId(memberBankCardInfo.getId());
                                memberBankCardBind.basic(dto.getUserId());
                                this.memberBankCardBindMapper.updateByPrimaryKey(memberBankCardBind);
                                //更新绑定
                                memberBankBindCardLog.setOperationType(MemberBankCardLogOperationTypeEnum.UPDATE_BIND_ED.getCode());
                            } else {
                                //新增绑定
                                memberBankCardBind = new MemberBankCardBind();
                                memberBankCardBind.setUserId(dto.getUserId());
                                memberBankCardBind.setBankCardInfoId(memberBankCardInfo.getId());
                                memberBankCardBind.setBindStatus(MemberBankCardBindStatusEnum.BIND_ED.getCode());
                                memberBankCardBind.basic(dto.getUserId());
                                this.memberBankCardBindMapper.insertUseGeneratedKeys(memberBankCardBind);
                                //绑定
                                memberBankBindCardLog.setOperationType(MemberBankCardLogOperationTypeEnum.BIND_ED.getCode());
                            }
                            //加入更新银行卡日志或新增绑定日志
                            memberBankBindCardLog.setBindCardId(memberBankCardBind.getId());
                            this.memberBankCardLogMapper.insert(memberBankBindCardLog);
                            result.setCode(RespCode.Code.SUCCESS.getCode());
                            result.setMessage(MemberResultEnum.BINDING_SUCCESSFUL.getMsg());
                            result.setSuccess(true);
                        } else {
                            result.setMessage(String.format("银行卡基础表id和开户银行名称不能同时为空，传入参数为BankName:%s,BankCodenameDataId:%d",
                                    dto.getBankName(),
                                    dto.getBankCodenameDataId()));
                        }
                    } else {
                        result.setCode(MemberResultEnum.VERIFICATION_FAILED.getCode());
                        result.setMessage(MemberResultEnum.VERIFICATION_FAILED.getMsg());
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Result selectBindMemberBankCardByUserId(Long userId) {
        return this.selectBindMemberBankCardByUserIdAndStatus(userId, MemberBankCardBindStatusEnum.BIND_ED);
    }

    @Transactional
    @Override
    public Result unBindByBindIdAndMemberId(Long bindId, Long userId, Long operateId, String ip) {
        Result result = new Result(false);
        //参数验证
        if (bindId == null) {
            result.setMessage("绑定银行卡id不能为空");
        } else {
            if (userId == null) {
                result.setMessage("绑定用户id不能为空");
            } else {
                if (operateId == null) {
                    result.setMessage("操作人id不能为空");
                } else {
                    if (ip == null) {
                        result.setMessage("操作人ip不能为空");
                    } else {
                        MemberBankCardBind params = new MemberBankCardBind();
                        params.setId(bindId);
                        params.setUserId(userId);
                        params.setBindStatus(MemberBankCardBindStatusEnum.BIND_ED.getCode());
                        MemberBankCardBind memberBankCardBind = this.memberBankCardBindMapper.selectOne(params);
                        if (memberBankCardBind == null) {
                            result.setMessage("该绑定银行卡不存在");
                        } else {
                            memberBankCardBind.setBindStatus(MemberBankCardBindStatusEnum.UN_BOUND_ED.getCode());
                            //this.memberBankCardBindMapper.updateByPrimaryKey(memberBankCardBind);
                            memberBankCardBindMapper.deleteByPrimaryKey(memberBankCardBind.getId());
                            //加入日志
                            MemberBankBindCardLog memberBankBindCardLog = VoDtoEntityUtil.convert(this.memberBankCardInfoMapper.selectByPrimaryKey(memberBankCardBind.getBankCardInfoId()), MemberBankBindCardLog.class);
                            memberBankBindCardLog.setId(null);
                            memberBankBindCardLog.setCreateTime(new Date());
                            memberBankBindCardLog.setCreaterId(operateId);
                            memberBankBindCardLog.setOperationType(MemberBankCardLogOperationTypeEnum.UNBUNDLED.getCode());
                            memberBankBindCardLog.setBindCardId(bindId);
                            memberBankBindCardLog.setUserId(userId);
                            memberBankBindCardLog.setCreateIp(ip);
                            this.memberBankCardLogMapper.insert(memberBankBindCardLog);
                            result.setSuccess(true);
                            result.setMessage("会员解绑银行卡成功");
                        }
                    }
                }
            }
        }
        return result;
    }

    private Result selectBindMemberBankCardByUserIdAndStatus(Long userId, MemberBankCardBindStatusEnum statusEnum) {
        Result result = new Result(false);
        //校验参数
        if (Objects.isNull(userId)) {
            result.setMessage("用户id不能为空");
        } else {
            MemberBankCardBindDTO dto = new MemberBankCardBindDTO();
            dto.setUserId(userId);
            dto.setBindStatus(statusEnum.getCode());
            MemberBankCardBindInfoVO res = this.memberBankCardBindMapper.selectBindCardDetail(dto);
            result.setSuccess(res != null);
            result.setCode(res == null ? MemberResultEnum.MEMBER_NO_DATA.getCode() : "200");
            result.setMessage(res == null ? MemberResultEnum.MEMBER_NO_DATA.getMsg() : "查询会员绑定银行卡详情成功");
            result.setData(res);
        }
        return result;
    }
    
    private String replaceCardName(String cardName) {
		if (StringUtils.isNotBlank(cardName)) {
			cardName = cardName.replaceAll("& #40;", "\\(").replaceAll("& #41;", "\\)");
		}
		return cardName;
	}
}
