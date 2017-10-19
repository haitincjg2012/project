package com.ph.shopping.facade.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.dto.PushDTO;
import com.ph.shopping.facade.member.entity.Member;
import org.apache.ibatis.session.RowBounds;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTOS;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.vo.MemberInfo;
import cm.ph.shopping.facade.order.vo.MerchantVO;

/**
 * 交易订单Mapper类
 * @author lzh
 *
 */
public interface TradOrderMapper {
	/**
	 * 
	 * @param memberPhone
	 * @return
	 */
	MemberInfo getMemberInfo(String memberPhone);
	/**
	 * 创建交易订单
	 * @param orderUnline
	 * @param payType 
	 */
	void createTradOrder(PhMemberOrderUnlineDTOS orderUnline);
	/**
	 * 推送消息入库
	 */
	void insertPayPushMessage(Map<String,Object> map);
	/**
	 * 获取商家信息
	 * @param merchantId
	 * @return
	 */
	MerchantVO getMerchantInfo(Long merchantId);
	/**
	 * 获取交易列表
	 * @param merchant
	 * @param rowBounds 
	 * @return
	 */
	List<PhMemberOrderUnlineDTO> getMerchantTradOrder(PhMemberOrderUnlineDTO merchant, RowBounds rowBounds);
	/**
	 * 更新订单状态
	 * @param memberOrderUnline
	 */
	void updateOrder(PhMemberOrderUnline memberOrderUnline);
	/**
	 * 
	 */
	void updateOrderForFail(PhMemberOrderUnline memberOrderUnline);
	/**
	 * 获取订单号
	 * @param id
	 * @return 
	 */
	PhMemberOrderUnline getOrderNo(Long id);
	/**
	 * 修改账户余额
	 * @param merchantMoney
	 * @param merchantId
	 */
	void updateMerchantMoney(HashMap<String, Object> map);
	/**
	 * 增加余额记录
	 * @param map
	 */
	void insertMerchantMoney(HashMap<String, Object> map);
	/**
	 * 验证会员是否存在
	 * @param mermberPhone
	 * @return
	 */
	Member getMemberByPhone(String memberPhone);
	/**
	 * 查询用户是否有已经分润的交易订单
	 */
	List<Long> getOrderUnline(Long memberId);
	/**
	 * 回显商户余额
	 * @param merchantId
	 * @return
	 */
	Long getMerchantScoreById(Long merchantId);
	/**
	 * 获取会员电话通过会员ID
	 * @param memberId
	 * @return
	 */
	MemberInfo getMemberInfoByMemberId(Long memberId);

}
