package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.entity.MerchantTimeSection;
import com.ph.shopping.facade.spm.vo.MerchanTimeSectionVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;

import java.util.List;
import java.util.Map;
/**
 * @项目  phshopping-facade-spm
 * @描述   商户业务层接口
 * @author 何文浪
 * @时间 2017-5-12
 * @version 2.1
 */
public interface IMerchantService {

	/**
	 * 查询商户详情列表
	 * @param merchantDTO
	 * @return List<MerchantVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	List<MerchantVO> getMerchantListDetail(MerchantDTO merchantDTO) ;

	/**
	 * 分页查询商户
	 * @param merchantDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	Result getMerchantByPage(PageBean pageBean,MerchantDTO merchantDTO) ;

	Result getHunterByPage(PageBean pageBean,MerchantDTO merchantDTO) ;
	/**
	 * 分页查询商户  并且展示管理员置顶的字段
	 * @param merchantDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	Result getMerchantByPageAdmin(PageBean pageBean,MerchantDTO merchantDTO) ;




	/**
	 * 查询商户列表
	 * @param merchantDTO
	 * @return List<MerchantVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	List<MerchantVO> getMerchantList(MerchantDTO merchantDTO) ;
	
	/**
	 * 查询商户详情列表单条及详情 --传值为主键
	 * @param merchantDTO:setId
	 * @return MerchantVO
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	MerchantVO getMerchantDetailById(MerchantDTO merchantDTO);
	/**
	 * 查询商户列表单条及详情 --传值为主键
	 * @param merchantDTO:setId
	 * @return MerchantVO
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	MerchantVO getMerchantListById(MerchantDTO merchantDTO) ;

	/**
	 * 查询代理商下级所有商户列表
	 * @param merchantDTO
	 * @return List<MerchantVO>
	 * @author 何文浪
	 * @时间 2017-5-26
	 */
	List<MerchantVO> getMerchantByAgentAllList(MerchantDTO merchantDTO);

	/**
	 * 增加商户
	 *
	 * @param merchantDTO 增加dto
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-5-26
	 */
	Result addMerchant(MerchantDTO merchantDTO);

	/**
	 * 修改商户
	 *
	 * @param merchantDTO dto
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-5-26
	 */
	Result updateMerchant(MerchantDTO merchantDTO);
	/**
	 * 冻结、解冻、暂冻操作
	 *
	 * @param merchantDTO dto
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-5-26
	 */
	Result updateMerchantByIsFrozen(MerchantDTO merchantDTO);


	/**
	 * 修改商户表
	 * 只修改不为null的字段. 单表操作,没有任何逻辑判断
	 *
	 * @param merchant
	 * @return 
	 * 
	 * @author: 李超
	 * @date: 2017-07-07 09:45:26
	 */
	Result updateMerchantSingle(Merchant merchant);
	/**
	 * 通过手机号进行验证手机号是否he'shi
	 * @param request
	 * @return
	 */
	public Result UserMemeberNewRegisterCheckPhone(String phone,String password,String imageCode);
	/**
	 * 保存到数据库
	 * @param merchantDTO
	 * @return
	 */
	public Result addMerchantByPhone(MerchantDTO merchantDTO) ;

	/**
	 * 修改商户信息（营业时间和人均消费）
	 * @param merchant
	 * @return
	 */
	Result updateMerchantInfo(MerchantDTO merchant);
	/*
	 * 修改商户信息（营业时间和人均消费）2
	 * @param merchantId 
	 * @param map
	 * @return
	 */
	Result updateMerchantInfoById(String  openBeginTime,String openEndTime,String closeBeginTime,String closeEndTime,Long costMoney, Long merchantId);
	/**
	 * 验证验证码
	 * @param cdKey
	 * @param merchantId
	 * @return
	 */
	Result provCodeByMerchanrId(String cdKey, Long merchantId);
	/**
	 * 代理置顶
	 */
	Result updateIsRecommend(Integer isRecommend, String telPhone, Byte isRecommendType);

	String getMerchantListByPhone(String phone);

	Long CheckPhone(String phone);

	/**
	 * 通过手机号获取商户的状态
	 * @param phone
	 * @return
	 */
	public Map getMemberAndMerchant(String phone);

	Result addDissipate(MerchanTimeSectionVO merchantTimeSection);

	Result findDissipate(Long merchantId);

	Result delDissipate(Long id);


	/**
	 * 根据手机号来查找商户的注册信息
	 * @return
	 */
	List<Map> getMerchantMsgByPhone(String phone);

    /**
     *  添加店面经理
     * @return
     */
    public Result StroeManager(String phone,String name,Long merchantId);

/**
 * 更新经理的状态
 */
	public void  updateStoreManager(Long memberid,Long merchantid);

	public Long getPhoneByPermission(String phone);
}
