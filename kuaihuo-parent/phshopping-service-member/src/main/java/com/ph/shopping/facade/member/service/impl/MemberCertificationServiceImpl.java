package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.base.BaseEntity;
import com.ph.shopping.common.core.other.IdAuthService;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.CertificatesAuthMapper;
import com.ph.shopping.facade.mapper.MemberMapper;
import com.ph.shopping.facade.member.dto.CertificatesAuthDTO;
import com.ph.shopping.facade.member.entity.CertificatesAuth;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificatesStatusEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificatesTypeEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificationEnum;
import com.ph.shopping.facade.member.service.IMemberCertificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员证件相关接口实现类
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
@Component
@Service(version = "1.0.0")
public class MemberCertificationServiceImpl implements IMemberCertificationService {

    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(MemberCertificationServiceImpl.class);

    /**
     * 会员查询mapper
     */
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 证件查询Mapper
     */
    @Autowired
    private CertificatesAuthMapper certificatesAuthMapper;

    /**
     * 第三方身份证校验接口
     */
    @Autowired
    private IdAuthService idAuthService;

    @Override
    @Transactional
    public Result authentication(CertificatesAuthDTO dto) {
        logger.info("会员身份证认证参数：certificatesAuthDTO ={} ", JSON.toJSONString(dto));
        Result result = new Result(false);
        String validateMessage = dto.validateForm();
        //验证参数
        if (Objects.nonNull(validateMessage)) {
            result.setMessage(validateMessage);
        } else {
            //验证是否证件认证
            MemberCertificatesTypeEnum memberCertificatesTypeEnum = MemberCertificatesTypeEnum.getEnumByCode(dto.getCertificatesType());
            //验证是否是身份证认证
            if (memberCertificatesTypeEnum.equals(MemberCertificatesTypeEnum.ID_CARD)) {
                //if (true) { //测试
                //调用第三方校验身份证
                if (this.idAuthService.idCertificatesAuth(dto.getCertificatesName(), dto.getCertificatesCode())) {
                    CertificatesAuth paramCertificatesAuth = VoDtoEntityUtil.convert(dto, CertificatesAuth.class, BaseEntity.BASE_FIELD_STRINGS);
                    //noinspection ConstantConditions
                    paramCertificatesAuth.setStatus(MemberCertificatesStatusEnum.SUCCESS.getCode());
                    CertificatesAuth certificatesAuth = this.certificatesAuthMapper.selectOne(paramCertificatesAuth);
                    //查询当前证件是否存在于证件基础表中
                    if (certificatesAuth == null) {
                        certificatesAuth = VoDtoEntityUtil.convert(dto, CertificatesAuth.class);
                        certificatesAuth.setStatus(MemberCertificatesStatusEnum.SUCCESS.getCode());
                        certificatesAuth.basic(dto.getLoginUserId());
                        //保存基础证件信息
                        this.certificatesAuthMapper.insertUseGeneratedKeys(certificatesAuth);
                    }
                    Member member = this.memberMapper.selectByPrimaryKey(dto.getUserId());
                    //校验用户是否存在
                    if (Objects.isNull(member)) {
                        logger.error(String.format("非法用户id:%d", dto.getUserId()));
                        result.setCode(MemberResultEnum.MEMBERUSER_NON_EXISTENT.getCode());
                        result.setMessage(MemberResultEnum.MEMBERUSER_NON_EXISTENT.getMsg());
                    }
                    //noinspection ConstantConditions
                    member.setCertificatesAuthId(certificatesAuth.getId());
                    member.setCertification(MemberCertificationEnum.VERIFIED.getCode());
                    member.setMemberName(certificatesAuth.getCertificatesName());
                    member.setIdCardNo(certificatesAuth.getCertificatesCode());
                    member.basic(dto.getLoginUserId());
                    //更新member认证状态和更新证件id
                    this.memberMapper.updateByPrimaryKey(member);
                    result.setSuccess(true);
                    result.setCode(MemberResultEnum.CERTIFICATE_SUCCESS.getCode());
                    result.setMessage(MemberResultEnum.CERTIFICATE_SUCCESS.getMsg());
                } else {
                    result.setCode(MemberResultEnum.FAIL_CHECK_CERTIFICATE.getCode());
                    result.setMessage(MemberResultEnum.FAIL_CHECK_CERTIFICATE.getMsg());
                }
            } else {
                result.setCode(MemberResultEnum.CERTIFICATE_TYPE_WRONG.getCode());
                result.setMessage(MemberResultEnum.CERTIFICATE_TYPE_WRONG.getMsg());
            }

        }
        return result;
    }

    @Override
    public Result getCertificatesAuthDetail(Long id) {
        Result result = new Result(false, MemberResultEnum.MEMBER_NO_DATA.getCode(), MemberResultEnum.MEMBER_NO_DATA.getMsg());
        //校验参数
        if (Objects.isNull(id)) {
            result.setMessage("传入参数错误,id不能为空");
        } else {
            CertificatesAuth certificatesAuth = this.certificatesAuthMapper.selectByPrimaryKey(id);
            result.setData(certificatesAuth);
            result.setSuccess(certificatesAuth != null);
        }
        return result;
    }

    @Override
    public Result getCertificatesAuthDetailByCodeAndType(String code, MemberCertificatesTypeEnum memberCertificatesTypeEnum) {
        logger.info("获取个人认证信息:code={},memberCertificatesTypeEnum={}",code,memberCertificatesTypeEnum.getCode());
        Result result = new Result(false, MemberResultEnum.MEMBER_NO_DATA.getCode(), MemberResultEnum.MEMBER_NO_DATA.getMsg());
        if (Objects.isNull(code)) {
            result.setMessage("证件编码不能为空");
        } else {
            CertificatesAuth certificatesAuth = new CertificatesAuth();
            certificatesAuth.setCertificatesCode(code);
            certificatesAuth.setCertificatesType(memberCertificatesTypeEnum.getCode());
            certificatesAuth.setStatus(MemberCertificatesStatusEnum.SUCCESS.getCode());
            logger.info("查询的认证信息入参为:{}",JSON.toJSON(certificatesAuth));
            CertificatesAuth _certificatesAuth = this.certificatesAuthMapper.selectOne(certificatesAuth);
            logger.info("查询的认证信息结果集为:{}",JSON.toJSON(_certificatesAuth));
            result.setData(_certificatesAuth);
            result.setCode(_certificatesAuth == null ? MemberResultEnum.MEMBER_NO_DATA.getCode() : "200");
            result.setMessage(_certificatesAuth == null ? MemberResultEnum.MEMBER_NO_DATA.getMsg() : "查询证件详情成功");
            result.setSuccess(_certificatesAuth != null);
        }
        return result;
    }
}
