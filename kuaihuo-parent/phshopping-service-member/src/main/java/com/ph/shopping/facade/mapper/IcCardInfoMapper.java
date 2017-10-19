package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.entity.IcCardInfo;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;

import java.util.List;

public interface IcCardInfoMapper extends BaseMapper<IcCardInfo> {

//==================================批量导入会员卡专用========================================/

    /**
     * @描述：获取会员卡列表
     * @param: memberCardInfoDTO
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/26 10:24
     */
    List<MemberCardInfoVO> getMemberCardList(MemberCardInfoDTO memberCardInfoDTO);

    /**
     * @描述：获取会员卡列表根据状态
     * @param: memberCardInfoDTO
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/26 10:24
     */
    List<MemberCardInfoVO> getMemberCardListByStatus(MemberCardInfoDTO memberCardInfoDTO);

    /**
     * @param @param  list
     * @param @return 参数
     * @return List<String>    返回类型
     * @throws
     * @Title: selectIcinnerCodeByInnerCodes
     * @Description: 获取所有已存在的 innercode
     */
    List<String> selectIcinnerCodeByInnerCodes(List<String> list);

    /**
     * @param @param  list
     * @param @return 参数
     * @return List<String>    返回类型
     * @throws
     * @Title: selectIcinnerCodeByInnerCodes
     * @Description: 获取所有已存在的 outercode
     */
    List<String> selectIcOuterCodeByOuterCodes(List<String> list);

    /**
     * @param @param list    参数
     * @return void    返回类型
     * @throws
     * @Title: batchInsertCard
     * @Description: 批量新增
     */
    void batchInsertCard(List<IcCardInfo> list);
}
