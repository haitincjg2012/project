package com.alqsoft.service.hunter;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.model.HunterLabelModel;
import com.alqsoft.model.HunterModel;
import org.alqframework.result.Result;

import java.util.Map;

/**
 * Date:     2017年3月1日  15:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */
public interface HunterService {


	/**
	 * 注册-完善批发商信息接口
	 * @return
	 */
    Result addHunter(HunterModel hunterModel);

	/**
	 * 安全设置-批发商身份认证
	 * @param name
	 * @param card
	 * @return
	 */
    Result checkHunterNameAndCard(String name, String card, Member member);
	/**
	 * 安全设置-添加银行卡
	 * @param bankCard
	 * @param member
	 * @return
	 */
    Result addHunterBankCard(BankCard bankCard, Member member);
	/**
	 * 安全设置-修改手机号  原手机号码发送验证码   PHPF2017070301
	 * @param phone
	 * @return
	 */
    Result sendMsgForOldPhone(Member member, String oldphone, String codeType);
	/**
	 * 安全设置-修改手机号  新手机号码发送验证码   PHPF2017070302
	 * @param newphone
	 * @return
	 */
    Result sendMsgForNewPhone(Member member, String newphone, String codeType);
	/**
	 * 安全设置-修改手机号
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @param member 
	 * @return
	 */
    Result updatePhone(String oldphone, String oldcode, String newphone, String newcode, Member member);
	/**
	 * 安全设置-添加银行卡-检验是否绑卡
	 * @return
	 */
    Result chackHunterBankCard(Member member);
	/**
	 * 安全设置-修改银行卡
	 */
    Result updateHunterBankCard(BankCard bankCard, Member member);
	/**
	 * 安全设置-批发商身份认证-检验是否认证
	 * @return
	 */
    Result chackhuntercard(Member member);
	/**
	 * 安全设置-删除银行卡
	 * @param id
	 * @return
	 */
    Result deleteHunterBankCard(Long id, Member member);

	/**
	 * 批发商个人中心
	 * @param hunterid
	 * @return
	 */
    Map<String,Object> getHunterCenterById(Long hunterid);
	
	Result getHunterCenter(Long hunterid);
	/**
	 * 批发商个人中心--所属协会编辑
	 * @return
	 */
    Result updateIndustryAssociation(Long id, Member member);
	/***
	 * 批发商个人中心--行业分类,fId一类级fid，二类级sid，批发商hId
	 * 
	 */
    Result upateIndustryClassify(Long fId, Long sId, Long hId);
    /**
	 * 驻地区域接口
     * @param detail2 
     * @param latitude 
     * @param member 
	 * @return
	 */
    Result updateHunterArea(Long id, Long provinceId, Long cityId, Long countyId, Long townId, String longitude,
                            String latitude, String detail, Integer positionLevel,String countyName, String station, Member member);

	
	
	/**
	 * 根据行业类别查询批发商列表
	 * @param industryid
	 * @return
	 */
    Result findHunterByIndustryTypeId(Long industryid, Integer page, Integer rows);
	
	


    Result getHuntersBySubjectId(Long sid,int page,int size);
    
    
	Hunter getById(Long id);
	/**
	 * 批发商个人中心--店铺管理--检测是否有背景图
	 * @return
	 */
    Result chackhunterbackgroundimage(Member member);
	/**
	 * 批发商个人中心--店铺管理--添加背景图
	 * @return
	 */
    Result addhunterbackgroundimage(Member member, Long hunterId, Long imageId);

	/***
	 * 判断该批发商是够被关注
	 * @param member
	 * @param hId    批发商id
	 * @return
	 */
    Result getHunterFoucsType(Member member, Long hId);

	/**
	 * 注册-完善批发商信息一期优化接口
	 * @return
	 */
    Result addhunterLabel(HunterLabelModel hunterLabelModel);
	/**
	 * 批发商个人信息编辑- 标签分类接口
	 * @param industryTypeIdList 
	 * @return
	 */
    Result updateHunterlabel(Long id, Member member, String industryTypeIdList);
	/**
     * 根据批发商id获取二级分类标签
     */
    Result findhHunterLabel(Long hunterId, Member member);

    Map<String,Object> getHunterLogoImage(Long id);
    
    Result updateHunterBusinessLicence(Long saveimageid,Long updateimageid,Long hunterid,int type);
    /**
     * 验证过手机号保存数据
     * @param hunterLabelModel 
     * @return
     */
    public Result addHunterAndRegister(HunterModel hunterModel);

    public Result hunterarearang(Long id);

    public Result updatehunterarearang(Long districtId, String districts, Member member);
}
