package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import com.ph.shopping.common.core.dto.MemberPayDTO;
import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.MerchantImageDTO;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.entity.MerchantTimeSection;
import com.ph.shopping.facade.spm.vo.MerchanTimeSectionVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;

/**
 * @author 何文浪
 * @version 2.1
 * @项目 phshopping-service-spm
 * @描述 商户数据层
 * @时间 2017-5-12
 */
public interface MerchantMapper extends BaseMapper<Merchant> {

    /**
     * 查询商户详情列表
     *
     * @param MerchantDTO
     * @return List<MerchantVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    List<MerchantVO> getMerchantListDetail(MerchantDTO merchantDTO);

    /**
     * 根据商户id 增加商户浏览量
     *
     * @param merchantId
     */
    void incrementViews(Long merchantId);

    /**
     * 查询商户列表
     *
     * @param MerchantDTO
     * @return List<MerchantVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    List<MerchantVO> getMerchantList(MerchantDTO merchantDTO);


    List<Map> getHunterList(MerchantDTO merchantDTO);

    /**
     * 查询商户列表并且展示管理员置顶的字段
     *
     * @param MerchantDTO
     * @return List<MerchantVO>
     * @author 赵俊彪
     * @时间 2017-5-12
     */
    List<Map<String,Object>> getMerchantListAdmin(MerchantDTO merchantDTO);

    /**
     * 将当前代理商下所有商户挂在该代理商上级
     *
     * @param MerchantDTO
     * @return Integer
     * @author 何文浪
     * @时间 2017-6-2
     */
    Integer updateMerchantByAgent(MerchantDTO merchantDTO);

    /**
     * 将当前代理商下所有商户挂在该代理商上级审核
     *
     * @param MerchantDTO
     * @return Integer
     * @author 何文浪
     * @时间 2017-7-1
     */
    Integer updateMerchantByStatusLevle(MerchantDTO merchantDTO);

    /**
     * @return 会员实体
     * @Title: verifyMemberProMerchantThree
     * @Description: 查询会员推广商户的个数
     * @author liuy
     * @date 2017年7月24日 下午2:35:32
     */
    int verifyMemberProMerchantNum(@Param("memberId") Long memberId);

    /*注册商户改造@维护商户表、原会员表
     *根据快火批发APP接口的需要 */
    void insertAlqMember(Map<String, Object> mapDto);

    /**
     * 根据手机号来获取数据
     *
     * @param phone 注册手机号
     * @return
     */
    public Long getDataByphone(String phone);

    /**
     * 获取商户基本信息
     *
     * @param mer
     * @return
     */
    MerchantDTO getMerchantById(MerchantDTO mer);

    /**
     * 创建人：赵俊彪
     * 代理置顶
     */
    void updateIsRecommend(@Param("isRecommend") Integer isRecommend, @Param("telPhone") String telPhone, @Param("isRecommendType") Byte isRecommendType);

    /**
     * 创建人：赵俊彪
     * 运营中心置顶
     */
    void updateIsRecommendAdmin(@Param("isRecommend") Integer isRecommend, @Param("telPhone") String telPhone, @Param("isRecommendType") Byte isRecommendType);

    Integer findIsRecomend(Long id);

    List<MerchantImageDTO> getmerchantImg(MerchantImageDTO img);


    /**
     * 修改信息
     *
     * @param map
     * @return
     */
    void updateMerchantInfoById(Map<String, Object> map);

    /**
     * 验证验证码
     *
     * @param map
     * @return
     */
    Merchant provCodeByMerchanrId(Map<String, Object> map);

    /**
     * 修改商户状态
     *
     * @param map
     */
    void updateCdkey(Map<String, Object> map);

    /**
     * 判断alq_member是是否存在和这个手机号
     *
     * @param phone
     * @return
     */
    Long getDataByphoneFromalq(String phone);

    /**
     * 判断当前商户是否已经激活
     *
     * @param merchantId
     * @return
     */
    Merchant getmerchantisCdKey(Long merchantId);

    List<String> getMerchantTypeById(Long id);

    Map<String, Object> selectMemberShareByToPhone(String phone);

    /**
     * 维护alq_member中的手机号
     * wudi
     *
     * @param Newphone 新手机号
     * @param oldPhone 旧手机号
     */
    void updateMemberByPhone(@Param("Newphone") String Newphone, @Param("oldPhone") String oldPhone);

    /**
     * 通过手机号获取商户的状态
     *
     * @param phone
     * @return
     */
    public Map getMemberAndMerchant(@Param("phone") String phone);

    List<MerchanTimeSectionVO> findDissipateList(@Param("merchantId") Long merchantId);

    void delDissipate(Long id);

    /**
     * 根据手机号来获取商户的注册信息
     *
     * @param phone
     * @return
     */
    public List<Map> getMerchantMsgByPhone(@Param("phone") String phone);

    /**
     * 判断店面经理是否已经和其他店铺绑定
     *
     * @return
     */
    public Map getStroeManagerMsg(String phone);

    public void addStoreManager(String phone, String name, Long merchantId);

    void addDissipateStartTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("merchantId") Long merchantId);


    List getIndustry(Long id);

    List<Map<String ,Object>>  getHunter(MerchantDTO merchantDTO);

    Map getLevel(Long id);

    /**
     * 通过merchantId 查找 userId
     * @param id
     * @return
     */
    Map<String,Object> getUserId(Long id);

    void updateStoreManager(@Param("memberid")Long memberid,@Param("userid")Long userid);

    void delDissipateTime(@Param("merchantId") Long merchantId);

    Long getPhoneByPermission(@Param("phone") String phone);

    Long getPhoneByAlqMember(@Param("phone") String phone);
}