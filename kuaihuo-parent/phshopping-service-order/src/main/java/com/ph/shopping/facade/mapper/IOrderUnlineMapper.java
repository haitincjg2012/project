package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.QueryMerchantOrderTakeDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;
import cm.ph.shopping.facade.order.vo.QueryMerchantOrderTakeVO;
import cm.ph.shopping.facade.order.vo.QueryWebUnLineOrderVO;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.score.entity.PhScoreTotalTrade;
import com.ph.shopping.facade.score.vo.MemberScoreVO2;
import com.ph.shopping.facade.spm.entity.UserBalance;
import com.ph.shopping.facade.spm.entity.UserBalanceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @项目：phshopping-service-order
 *
 * @描述：线下订单Mapper
 *
 * @作者： Mr.Dong
 *
 * @创建时间：2017年3月13日
 *
 * @Copyright @2017 by Mr.Dong
 */
public interface IOrderUnlineMapper extends BaseMapper<PhMemberOrderUnline> {
	/**
	 * 线下订单vo
	 * @param phMemberOrderUnlineDTO
	 * @return List<PhMemberOrderUnlineVO>
	 * @author Mr.Dong
     * @createTime 2017年3月15日
	 */
	public List<PhMemberOrderUnlineVO>  getUnlineOrderVoList(PhMemberOrderUnlineDTO phMemberOrderUnlineDTO);

	/**
	 * 更新订单
	 * @param phMemberOrderUnline
	 * @return int
	 * @author Mr.Dong
     * @createTime 2017年3月15日
	 */
	public int updateUnlineOrder(PhMemberOrderUnline phMemberOrderUnline);
	/**
	 * 商户提货单
	 * @param queryMerchantOrderTakeDTO
	 * @return
	 */
	public List<QueryMerchantOrderTakeVO> getOnlineOrderVoList(QueryMerchantOrderTakeDTO queryMerchantOrderTakeDTO);

	/**
	 * 
	 * @Title: selectOrderSatusByBarcode   
	 * @Description: 根据二维码标识得到订单状态
	 * @param: @param barcodeMark
	 * @param: @return      
	 * @return: Integer
	 * @author：李杰      
	 * @throws
	 */
	Integer selectOrderSatusByBarcode(@Param("barcodeMark") String barcodeMark);

	/**
	 * 获取用户总消费金额
	 * @param memberId
	 * @return
	 */
    Long selectTotalOrerMoneyByPhone(Long memberId);
	/**
	 * 获取分享人信息
	 */
	Member getMemberProInfo(String memberPhone);
	/**
	 * 修改会员的分享人ID
	 */
	int updateMemberPro(Long proId,Long id);
	/**
	 * 插入分享关系中间表
	 */
	int  insertMemberRecord(Map<String,Object> map);

	/**
	 * 修改分享关系中间表
	 * @param map
	 * @return
	 */
	int  updateMemberRecord(Map<String,Object> map);
}
