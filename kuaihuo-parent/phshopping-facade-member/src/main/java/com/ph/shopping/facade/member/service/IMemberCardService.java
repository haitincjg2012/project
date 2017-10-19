package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.entity.IcCardInfo;

import java.util.List;

/**
 * 
* @ClassName: IMemberCardService  
* @Description: 会员卡服务  
* @author lijie  
* @date 2017年3月16日  
*
 */
public interface IMemberCardService {


//=========================================会员卡信息基本操作接口============================================/

    /**
     * @param @param  dto
     * @param @return 参数
     * @return Result    返回类型
     * @throws
     * @Title: getMemberCardByPage
     * @Description: 获取会员卡数据
     */
    Result getMemberCardListByPage(PageBean pageBean, MemberCardInfoDTO memberCardInfoDTO);

    /**
     * @param @param  icInfo
     * @param @return 参数
     * @return Result    返回类型
     * @throws
     * @Title: addMemberCard
     * @Description: 添加会员卡
     */
    Result addMemberCard(IcCardInfo icInfo);

	/**
     * @描述：批量新增会员卡（导入专用）
     *
     * @param: list
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/24 9:33
     */
	Result batchAddMemberCard(List<IcCardInfo> list);


    //=========================================会员卡绑定关系操作接口============================================/

	/**
     * @描述：根据条件查询会员卡绑定数据
     *
     * @param: null
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/24 13:05
     */
    Result getMemberBindCardInfoByCondition(MemberCardInfoDTO memberCardInfoDTO);

	/**
     * @描述：获取已发会员卡列表数据
     *
     * @param: pageBean
     * @param: icCardInfo
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/24 9:39
     */
    Result getMemberCardSendListByPage(PageBean pageBean, MemberCardInfoDTO memberCardInfoDTO);

	/**
     * @描述：绑定会员卡
     *
     * @param: dto
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/24 9:39
     */
    Result bindMemberCard(MemberCardInfoDTO dto);

    /**
     * @描述：挂失会员卡
     * @param: null
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/24 9:39
     */
    Result lossMemberCard(MemberCardInfoDTO dto);

    /**
     * @描述：冻结会员卡
     * @param: dto
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/24 9:39
     */
    Result frozenMemberCard(MemberCardInfoDTO dto);

    /**
     * @描述： 解冻会员卡
     * @param: dto
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/24 9:38
     */
    Result relieveMemberCard(MemberCardInfoDTO dto);


    /**
     * @描述： 判断会员卡是否存在，是否被绑定
     * @param: dto
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/5/24 9:38
     */
    Result judgeMemberCard(MemberCardInfoDTO dto);


}