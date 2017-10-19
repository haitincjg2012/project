/**
 * 
 */
package com.ph.shopping.facade.member.service;

import org.springframework.ui.Model;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberAddDTO;
import com.ph.shopping.facade.member.dto.MemberAddShareDTO;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.dto.MemberLoginDTO;
import com.ph.shopping.facade.member.dto.MemberPasswordDTO;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.dto.MemberQueryShareDTO;
import com.ph.shopping.facade.member.dto.MemberRegisterDTO;
import com.ph.shopping.facade.member.dto.MemberShareRecordDTO;
import com.ph.shopping.facade.member.dto.MemberUpdateDTO;
import com.ph.shopping.facade.member.entity.Member;

import java.util.Map;

/**
 * 
 * @ClassName: IMemberService  
 * @Description: 会员操作接口 
 * @author liuy
 * @date 2017年05月16日 
 */
public interface IMemberService {

	/***************************以下为会员信息相关接口************************************/
	
	/**
     * 分页获取会员列表
     * @param memberDto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberListByPage(MemberDTO memberDto);

	/**
     * 获取会员列表（不分页）
     * @param memberDto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberList(MemberDTO dto);
	
    /**
     * 根据手机号查询会员信息 
     * @param mobile
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberInfoByMobile(String mobile);

    /**
     * 根据Id查询会员信息 
     * @param mobile
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberInfoById(Long id);
	
	/**
     * 新增会员（平台）
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result addMember(MemberAddDTO dto);

	/**
     * 会员冻结
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result memberFrozen(MemberDTO dto);

	/**
     * 会员解冻
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result memberRelievefrozen(MemberDTO dto);
	
	/**
     * 会员升级为推广师
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
    Result memberUpgradePromotion(MemberPromotionDTO dto);
	
	/****************以下为会员注册、登录验证、校验会员是否存在、找回密码相关接口********************/
	
	/**
     * 会员注册
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result registerMember(MemberRegisterDTO dto);
	
	/**
     * 会员登录
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result memberLogin(MemberLoginDTO dto);

	/**
	 * 校验会员（手机号）是否存在 
	 * @param phone
	 * @author liuy
	 * @createTime 2017年05月16日
	 */
	Result verifyPhoneIsExists(String phone);
	
	/**
     * 找回会员密码 
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result backMmberPassword(MemberPasswordDTO dto);

	/***********************以下为会员头像（基本信息）修改、会员手机号修改、密码修改相关接口************************/
	
	/**
     * 修改会员基本信息 
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result updateMember(MemberUpdateDTO dto);
	
	/**
     * 修改会员密码  
     * @param dto
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result updateMemberPassword(MemberPasswordDTO dto);

	/***********************以下为查询会员推广师是否能分润接口************************/

	/**
     * 查询推广师（会员）是否可以分润 
     * @param dto
	 * @author liuy
	 * @return result 返回推广师（会员）对象
	 * @createTime 2017年05月24日
     */
	Result getPromotionIsCanProfit(MemberDTO dto); 

	/***********************以下为北京方批发商登录注册、业务操作专用接口************************/

    /**
     * 根据手机号查询会员信息 
     * 如果会员存在，判断是否有token值，没有则更新，有则返回
     * 如果会员不存在，新增会员，包括token值
     * @param mobile
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberInfoByMobileRegister(String mobile);
	
    /**
     * 根据手机号查询会员信息 
     * 如果会员存在，判断是否有token值，没有则更新，有则返回
     * @param mobile
	 * @author liuy
	 * @createTime 2017年05月16日
     */
	Result getMemberInfoByMobileUpToken(String mobile);
   
	/**
	 * @Title: getMemberByToken
	 * @Description: 根据token 获取会员信息   
	 * @author liuy
	 * @date  2017年7月10日 下午4:58:36
	 * @param token
	 * @return
	 */
	Result getMemberByToken(String token);
	/**
	 * 
	 * @Title: addMemberShare   
	 * @Description: 添加会员分享  
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result addMemberShare(MemberAddShareDTO dto);
	/**
	 * 
	 * @Title: getMemberShare   
	 * @Description: 获取会员分享记录   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMemberShare(MemberQueryShareDTO dto);
	
	/***********************以下为会员推广3个商户则自动升级为推广师接口************************/
    
    /**
     * @Title: upgradeMemberPromotion
     * @Description: 当会员推广3个商户时，会员升级为推广师
     * @author liuy
     * @date  2017年7月24日 下午4:32:16
     * @return
     */
	Result upgradeMemberPromotionAuto(MemberPromotionDTO dto);
	/**
	 * 
	 * @Title: getMemberShareByPage   
	 * @Description:查询分享记录   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getMemberShareByPage(MemberShareRecordDTO dto);

	/**
	 * 根据主键获取用户信息
	 * @param id
	 * @return
	 */
	Member selectByPrimaryKey(Long id);
	/***
	 * 首页轮播图全部查询
	 * @param id 轮播图对应的图片id
	 */
	Result findAll(Integer startInt, Integer endInt);
	/***
	 * 点击轮播图进入详情页面
	 * @param id 轮播图对应的图片id
	 */
	void getTextDetail(Long id, Model model);

	/**
	 * 通过手机号来查询用户
	 * @param mobile
	 * @return
	 */
	public Member findMemberByMobile(String mobile);

	/**
	 * 通过用户id来查找用户
	 * @param id
	 * @return
	 */
	Member getMemberById(Long id);

	Result updateNikeName(Long memberid,String nikeName);

	/**
	 * 通过手机号来查找用户的身份
	 * @param phone
	 * @return
	 */
	public Map getMarkindByPhone(String phone);
}
