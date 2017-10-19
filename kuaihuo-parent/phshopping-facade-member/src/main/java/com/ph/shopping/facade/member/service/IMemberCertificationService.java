package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.CertificatesAuthDTO;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificatesTypeEnum;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员证件相关接口
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
public interface IMemberCertificationService {

    /**
     * @Description: 证件认证接口
     * @param dto 认证参数
     * @return Result
     */
    Result authentication(CertificatesAuthDTO dto);

    /**
     * @param id 证件id
     * @return Result
     * @Description: 查询证件详情接口
     */
    Result getCertificatesAuthDetail(Long id);

    /**
     * @param code                       证件code
     * @param memberCertificatesTypeEnum 证件类型美剧
     * @return Result
     * @Description: 查询证件详情接口
     */
    Result getCertificatesAuthDetailByCodeAndType(String code, MemberCertificatesTypeEnum memberCertificatesTypeEnum);
}
