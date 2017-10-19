package com.alqsoft.service.impl.hunter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunter.HunterDao;
import com.alqsoft.dao.position.PositionDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.position.Position;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.init.InitParams;
import com.alqsoft.model.HunterLabelModel;
import com.alqsoft.model.HunterModel;
import com.alqsoft.rpc.mobile.RpcHunterService;
import com.alqsoft.rpc.mobile.RpcPhHunterRuleAttachmentService;
import com.alqsoft.service.bankcard.BankCardService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.hunterindustrytype.HunterIndustryTypeService;
import com.alqsoft.service.hunterproductdetail.HunterProductDetailService;
import com.alqsoft.service.industrytype.IndustryTypeService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.position.PositionService;
import com.alqsoft.service.register.UserRegisterService;
import com.alqsoft.utils.HttpClientObject;
import com.alqsoft.utils.JedisUtils;
import com.alqsoft.vo.PhHunterRuleAttachmentVO;
import com.google.common.collect.Maps;
import com.mysql.jdbc.StringUtils;

/**
 * Date: 2017年3月1日 15:25:41 <br/>
 *
 * @author dinglanlan
 * @see
 */

@Service
@Transactional(readOnly = true)
public class HunterServiceImpl implements HunterService {

	private static Logger logger = LoggerFactory.getLogger(HunterServiceImpl.class);

	@Autowired
	private RpcHunterService rpcHunterService;

	@Autowired
	private InitParams initParams;
	@Autowired
	private InitParamPc initParam;
	@Autowired
	private RpcPhHunterRuleAttachmentService rpcPhHunterRuleAttachmentService;
	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private HunterDao hunterDao;

	@Autowired
	private PositionService positionService;
	@Autowired
	private PositionDao positionDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private IndustryTypeService industryTypeService;

	@Autowired
	private HunterProductDetailService hunterProductDetailService;

	@Autowired
	private HunterIndustryTypeService hunterIndustryTypeService;

	@Autowired
	private UserRegisterService userRegisterService;

	/**
	 * 驻地区域接口
	 *
	 * @return
	 */
	@Override
	public Result updateHunterArea(Long id, Long provinceId, Long cityId, Long countyId, Long townId, String longitude,
								   String latitude, String details, Integer positionLevel, String countyName, String station, Member member) {

		// 空格
		String detail = details.replaceAll(" ", "");// 详细地址

		// 验证
		if (null == id) {

			return ResultUtils.returnError("批发id不能为空");

		} else if (null == member.getHunter()) {

			return ResultUtils.returnError("角色异常");

		} else if (id.longValue() != member.getHunter().getId().longValue()) {

			return ResultUtils.returnError("用户信息不一致");

		} else if (null == longitude || "".equals(longitude)) {// 是经度

			return ResultUtils.returnError("经度不能为空");
		} else if (null == latitude || "".equals(latitude)) {// 是维度

			return ResultUtils.returnError("维度不能为空");
		} else if (null == detail) {

			return ResultUtils.returnError("详细地址不能为空");

		} else if (detail.length() < 1 || detail.length() > 30) {

			return ResultUtils.returnError("详细地址请输入1-30位");
		} else if (positionLevel == null) {

			return ResultUtils.returnError("等级不能为空");
		} else if (station == null || station.equals("")) {

			return ResultUtils.returnError("经纬度定位地址不能为空");
		}

		// wangzhen 2017年7月19日09:00:00 注释原因：改为市县两级联动
		/*
		 * else if (null == countyId) {
		 *
		 * return ResultUtils.returnError("驻地县不能为空"); } else if (null == townId)
		 * {
		 *
		 * return ResultUtils.returnError("驻地乡镇不能为空"); } else if (null ==
		 * provinceId) {
		 *
		 * return ResultUtils.returnError("驻地省不能为空"); } else if (null == cityId)
		 * {
		 *
		 * return ResultUtils.returnError("驻地市不能为空"); }
		 */

		// 检验乡镇id是否已有批发
		/*
		 * List<Map<String,Object>>
		 * hunterList=hunterDao.getHunterByTownId(townId);
		 * if(hunterList.size()>0){ return
		 * ResultUtils.returnError("所属乡镇已有批发，请重新选择"); }
		 */

		// 根据社区编号调用普惠获取省市县信息接口
		// Position position = positionService.getPHPositionByTownId(townId);
		// Position
		// position=positionService.getPositionByTownId(townId);//根据社区编号获取省市县信息接口

		// hunter.setPhTownId(position.getTownId());// 乡镇id
		// hunter.setTownName(position.getTownName());// 乡镇名称

		Hunter hunter = new Hunter();

		hunter.setId(id);// 批发id
		// hunter.setPhProvinceId(Long.valueOf("320"));// 省级id
		// hunter.setProvinceName("江苏省");// 省级名称
		// hunter.setPhCityId(Long.valueOf("320100000000"));// 市级id
		// hunter.setCityName("南京市");// 市级名称
		hunter.setLongitude(longitude);// 经度
		hunter.setLatitude(latitude);// 维度
		hunter.setDetail(detail);// 详细地址
		hunter.setPositionLevel(positionLevel);// 等级
		hunter.setStation(station);

		if (positionLevel.intValue() == 1) {

			if (null == cityId || "".equals(cityId)) {
				return ResultUtils.returnError("代理市不能为空");
			}

			Map position = positionDao.getBycityid(cityId);
			hunter.setPhCityId(cityId);
			hunter.setCityName((String) position.get("name"));

			hunter.setPhProvinceId((Long) position.get("pid"));
			hunter.setProvinceName((String) position.get("pname"));
		} else if (positionLevel.intValue() == 2) {
			if (countyId == null || "".equals(countyId) || "".equals(countyName)) {
				return ResultUtils.returnError("代理县不能为空");
			}

			List<Map<String, Object>> position = positionDao.getBycountyid(countyId);

			hunter.setPhCityId((Long) position.get(0).get("id"));
			hunter.setCityName((String) position.get(0).get("name"));

			hunter.setPhProvinceId((Long) position.get(0).get("pid"));
			hunter.setProvinceName((String) position.get(0).get("pname"));

			hunter.setPhCountyId(countyId);
			hunter.setCountyName((String) position.get(0).get("cname"));
		} else {
			return ResultUtils.returnError("等级错误");
		}

		/*
		 * if (positionLevel.intValue() == 1) {
		 * 
		 * } else if (positionLevel.intValue() == 2) {
		 * 
		 * if (null == countyName || "".equals(countyName) || null == countyId)
		 * {
		 * 
		 * return ResultUtils.returnError("驻地县不能为空"); }
		 * 
		 * hunter.setPhCountyId(countyId);// 县级id
		 * hunter.setCountyName(countyName);// 县级名称 } else {
		 * 
		 * return ResultUtils.returnError("等级错误"); }
		 */

		return rpcHunterService.updateHunterArea(hunter);
	}

	/**
	 * 注册-完善批发信息接口
	 *
	 * @return
	 */
	@Override
	public Result addHunter(HunterModel hunterModel) {

		JedisUtils jedisUtils = new JedisUtils();
		// 获取手机或
		String phone = jedisUtils.get(hunterModel.getPhone());
		String passWord = jedisUtils.get(hunterModel.getPassWord());
		if (phone == null || passWord == null) {
			ResultUtils.returnError("请输入手机号或者密码");
		}

		Result result = new Result();

		if (null == hunterModel.getNickname() || "".equals(hunterModel.getNickname())) {

			return ResultUtils.returnError("昵称不能为空");
		} else if (null == hunterModel.getDetail() || "".equals(hunterModel.getDetail())) {

			return ResultUtils.returnError("详细地址不能为空");
		} else if (null == hunterModel.getService() || "".equals(hunterModel.getService())) {

			return ResultUtils.returnError("批发服务不能为空");
		}
		// 去除空格
		String nickname = hunterModel.getNickname().replaceAll(" ", "");// 昵称
		String detail = hunterModel.getDetail().replaceAll(" ", "");// 详细地址
		String service = hunterModel.getService().replaceAll(" ", "");// 批发服务

		// 验证
		if (null == hunterModel.getMerberId() || "".equals(hunterModel.getMerberId())) {

			return ResultUtils.returnError("merber不能为空");
		} else if (null == hunterModel.getLogoAttachmentId() || "".equals(hunterModel.getLogoAttachmentId())) {

			return ResultUtils.returnError("头像不能为空");
		} else if (nickname.length() < 2 || nickname.length() > 10) {

			return ResultUtils.returnError("昵称请输入2-10位");
		} else if (null == hunterModel.getLatitude() || "".equals(hunterModel.getLatitude())) {// 是维度

			return ResultUtils.returnError("维度不能为空");
		} else if (null == hunterModel.getLongitude() || "".equals(hunterModel.getLongitude())) {// 是经度

			return ResultUtils.returnError("经度不能为空");
		} else if (detail.length() < 1 || detail.length() > 30) {

			return ResultUtils.returnError("详细地址请输入1-30位");
		} else if (service.length() < 1 || service.length() > 20) {

			return ResultUtils.returnError("批发服务请输入1-20位");
		} else if (hunterModel.getPositionLevel() == null) {

			return ResultUtils.returnError("等级不能为空");
		} else if (hunterModel.getStation() == null || "".equals(hunterModel)) {

			return ResultUtils.returnError("经纬度定位地址不能为空");
		} else if (hunterModel.getBusinessLicence() == null || "".equals(hunterModel.getBusinessLicence())) {
			return ResultUtils.returnError("营业执照不能为空");
		} else if (hunterModel.getIdCardAttachment() == null || "".equals(hunterModel.getIdCardAttachment())) {
			return ResultUtils.returnError("身份证照不能为空");
		} /*
			 * else if(hunterModel.getIdCardAttachment().length()!=3){ return
			 * ResultUtils.returnError("请上传身份证正反面照片"); }
			 */
		// 1 营业执照图片 2 身份证正面图片 3 身份证反面图片
		/*
		 * else if (null == hunterModel.getProvinceId() ||
		 * "".equals(hunterModel.getProvinceId())) {// 是省id
		 *
		 * return ResultUtils.returnError("驻地省不能为空"); } else if (null ==
		 * hunterModel.getCountyId() || "".equals(hunterModel.getCountyId()))
		 * {// 是县id
		 *
		 * return ResultUtils.returnError("驻地县不能为空"); } else if (null ==
		 * hunterModel.getTownId() || "".equals(hunterModel.getTownId())) {//
		 * 是townId
		 *
		 * return ResultUtils.returnError("驻地乡镇不能为空"); }else if (null ==
		 * hunterModel.getCityId() || "".equals(hunterModel.getCityId())) {//
		 * 是市id
		 *
		 * return ResultUtils.returnError("驻地市不能为空"); }
		 */ // wangzhen 2017年7月20日11:48:02 注释原因：只在南京做试点

		// 检验昵称是否已存在
		List<Map<String, Object>> hunterNicknameList = hunterDao.getHunterByNickname(nickname);
		if (hunterNicknameList.size() > 0) {
			return ResultUtils.returnError("该昵称已存在，请重新输入");
		}

		// 检验乡镇id是否已有批发
		/*
		 * List<Map<String,Object>>
		 * hunterList=hunterDao.getHunterByTownId(hunterModel.getTownId());
		 * if(hunterList.size()>0){ return
		 * ResultUtils.returnError("所属乡镇已有批发，请重新选择"); }
		 */

		// 根据社区编号调用普惠获取省市县信息接口
		// Position position =
		// positionService.getPHPositionByTownId(hunterModel.getTownId());
		// Position
		// position=positionService.getPositionByTownId(hunterModel.getTownId());//根据社区编号获取省市县信息接口

		Hunter hunter = new Hunter();
		Long merberId = hunterModel.getMerberId();// 关联的merber表id

		IndustryAssociation industryAssociation = new IndustryAssociation();// 行业协会
		if (null == hunterModel.getIndustryAssociationId() || "".equals(hunterModel.getIndustryAssociationId())) {

			hunter.setIndustryAssociation(null);
		} else {
			industryAssociation.setId(hunterModel.getIndustryAssociationId());
			hunter.setIndustryAssociation(industryAssociation);
		}

		Attachment logoAttachment = new Attachment();// 头像
		logoAttachment.setId(hunterModel.getLogoAttachmentId());
		hunter.setLogoAttachment(logoAttachment);

		ArrayList<String> arrayList = new ArrayList<String>();

		arrayList.addAll(Arrays.asList(hunterModel.getIdCardAttachment().split(",")));// 将身份证照分隔开

		ArrayList<String> arrayList2 = new ArrayList<String>();
		if (hunterModel.getOtherAttachment() != null && !"".equals(hunterModel.getOtherAttachment())) {

			arrayList2.addAll(Arrays.asList(hunterModel.getOtherAttachment().split(",")));

		}

		for (String string : arrayList2) {
			arrayList.add(string);
		}

		arrayList.add(hunterModel.getBusinessLicence());
		/*
		 * for (String id : arrayList) { logger.info(id); }
		 */

		PhHunterRuleAttachmentVO phVO = new PhHunterRuleAttachmentVO();
		phVO.setRuleAttachmentList(arrayList);

		hunter.setNickname(nickname);// 昵称

		hunter.setPhProvinceId(Long.valueOf("320"));// 省级id
		hunter.setProvinceName("江苏省");// 省级名称
		hunter.setPhCityId(Long.valueOf("320100000000"));// 市级id
		hunter.setCityName("南京市");// 市级名称
		hunter.setLongitude(hunterModel.getLongitude());// 经度
		hunter.setLatitude(hunterModel.getLatitude());// 维度

		hunter.setDetail(detail);// 详细地址

		/*
		 * IndustryType industryType = new IndustryType();// 行业分类
		 * industryType.setId(hunterModel.getIndustryTypeId2());// 二级分类
		 */
		hunter.setIndustryType(null);

		hunter.setService(service);// 服务
		hunter.setPositionLevel(hunterModel.getPositionLevel());// 等级
		hunter.setStation(hunterModel.getStation());
		hunter.setTownName(null);
		hunter.setPhTownId(null);

		if (hunterModel.getPositionLevel().intValue() == 1) {

			hunter.setPhCountyId(null);
			hunter.setCountyName(null);
		} else if (hunterModel.getPositionLevel().intValue() == 2) {

			if (null == hunterModel.getCountyName() || "".equals(hunterModel.getCountyName())
					|| null == hunterModel.getCountyId()) {

				return ResultUtils.returnError("代理县不能为空");
			}
			hunter.setPhCountyId(hunterModel.getCountyId());// 县级id
			hunter.setCountyName(hunterModel.getCountyName());// 县级名称
		} else {
			ResultUtils.returnError("等级错误");
		}

		// hunter.setPhTownId(position.getTownId());// 乡镇id
		// hunter.setTownName(position.getTownName());// 乡镇名称

		result = rpcHunterService.addHunter(merberId, hunter, phVO);

		return result;

	}

	/**
	 * 安全设置-批发身份认证
	 *
	 * @param name
	 * @param card
	 * @return
	 */
	@Override
	public Result checkHunterNameAndCard(String name, String card, Member member) {

		Result result = new Result();

		// 验证
		if ("".equals(name)) {

			return ResultUtils.returnError("姓名不能为空");

		} else if ("".equals(card)) {

			return ResultUtils.returnError("身份证号不能为空");

		} else if (null == member.getHunter()) {

			return ResultUtils.returnError("角色异常");

		} else {

			// 姓名身份证验证
			String two_element_url = (String) initParams.getProperties().get("two_element_url");// 获取姓名身份证验证地址
			// 初始接口参数
			List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
			callpayParame.add(new BasicNameValuePair("name", name));
			callpayParame.add(new BasicNameValuePair("idCard", card));

			Long hunter_id = member.getHunter().getId();// 批发id

			if (null == hunter_id) {
				return ResultUtils.returnError("您的信息不存在");
			}

			try {

				logger.info("用户ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",姓名身份证验证地址：" + two_element_url
						+ "/mobile/view/checkInfoCard/checkUserInfo");

				String checkResult = HttpClientObject
						.sendGet(two_element_url + "/mobile/view/checkInfoCard/checkUserInfo", callpayParame);

				logger.info("用户ID：" + hunter_id + "，参数：" + callpayParame.toString() + ",返回值：" + checkResult.toString());

				if (checkResult.equals("true")) {

					return rpcHunterService.checkHunterNameAndCard(name, card, member);// 保存批发身份证信息
				} else {
					logger.info("验证姓名身份证失败，请核对信息！");
					return ResultUtils.returnError("验证姓名身份证失败，请核对信息！");
				}

			} catch (Exception e) {
				for (StackTraceElement st : e.getStackTrace()) {
					logger.error(st.toString());
				}
				logger.error("姓名身份证验证接口异常,错误信息：" + e.getMessage() + "时间：" + new Date());
				return ResultUtils.returnError("姓名身份证验证接口异常,错误信息：" + e.getMessage());
			}

		}

	}

	/**
	 * 安全设置-添加银行卡
	 *
	 * @param bankCard
	 * @param member
	 * @return
	 */
	@Override
	public Result addHunterBankCard(BankCard bankCard, Member member) {

		JedisUtils jedisUtils = JedisUtils.getRu(initParam);

		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}

		Long hunterId = hunter.getId();
		// 检验是够验证过手机号
		String phone = hunter.getPhone();
		if (phone == null && hunterId != null) {
			phone = hunterDao.getPhoneByHunterId(hunterId);

		}

		String value = jedisUtils.get("check" + phone);

		if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
			return ResultUtils.returnError("请验证手机号成功后再添加银行卡");
		}

		List<Map<String, Object>> bankCardList = bankCardService.getBankCardByHunterId(hunterId);// 根据批发ID获取银行卡信息

		if (bankCardList.size() > 0) {// 已经绑定银行卡信息

			return ResultUtils.returnError("已绑定银行卡，请勿重复操作");
		}

		return rpcHunterService.addHunterBankCard(bankCard, member);
	}

	/**
	 * 安全设置-修改手机号 原手机号码发送验证码 PHPF2017070301
	 *
	 * @param phone
	 * @return
	 */
	@Override
	public Result sendMsgForOldPhone(Member member, String oldphone, String codeType) {
		// 验证手机号
		String regex = "^1[34578]\\d{9}$";
		if (!oldphone.matches(regex)) {
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}

		return rpcHunterService.sendMsgForOldPhone(member, oldphone, codeType);
	}

	/**
	 * 安全设置-修改手机号 新手机号码发送验证码 PHPF2017070302
	 *
	 * @param newphone
	 * @return
	 */
	@Override
	public Result sendMsgForNewPhone(Member member, String newphone, String codeType) {

		Hunter hunter1 = member.getHunter();

		if (hunter1 == null) {
			return ResultUtils.returnError("角色异常");
		}

		// 验证手机号
		if (StringUtils.isEmptyOrWhitespaceOnly(newphone)) {
			return ResultUtils.returnError("新手机号不能为空");
		}

		String regex = "^1[34578]\\d{9}$";
		if (!newphone.matches(regex)) {
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}

		List<Map<String, Object>> memberList = memberService.getMemberByPhone(newphone);// 根据手机号查询用户信息
		if (memberList.size() > 0) {
			return ResultUtils.returnError("新手机号已存在，请重新输入");
		}

		return rpcHunterService.sendMsgForNewPhone(member, newphone, codeType);
	}

	/**
	 * 安全设置-修改手机号
	 *
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @return
	 */
	@Override
	public Result updatePhone(String oldphone, String oldcode, String newphone, String newcode, Member member) {

		Hunter hunter = member.getHunter();

		if (hunter == null) {
			return ResultUtils.returnError("角色异常");
		}

		// 验证手机号
		String regex = "^1[34578]\\d{9}$";

		if (StringUtils.isEmptyOrWhitespaceOnly(oldphone)) {
			return ResultUtils.returnError("旧手机号不能为空");
		}
		if (!oldphone.matches(regex)) {
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(oldcode)) {
			return ResultUtils.returnError("旧手机号验证码不能为空");
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(newphone)) {
			return ResultUtils.returnError("新手机号不能为空");
		}
		if (!newphone.matches(regex)) {
			return ResultUtils.returnError("请输入正确的手机号码,1(3,4,5,7,8)开头");
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(newcode)) {
			return ResultUtils.returnError("新手机号验证码不能为空");
		}

		List<Map<String, Object>> memberList = memberService.getMemberByPhone(newphone);// 根据手机号查询用户信息
		if (memberList.size() > 0) {
			return ResultUtils.returnError("新手机号已存在，请重新输入");
		}

		Map chatRegist = userRegisterService.chatRegist(newphone);
		if (chatRegist.size() <= 0) {
			return ResultUtils.returnError("腾讯聊天注册信息有误");
		}
		String identifilter = (String) chatRegist.get("IMdentifier");
		String imPassword = (String) chatRegist.get("IMPassword");
		String userSign = (String) chatRegist.get("IMUserSign");

		return rpcHunterService.updatePhone(oldphone, oldcode, newphone, newcode, member, identifilter, imPassword,
				userSign);
	}

	/**
	 * 个人中心批发dao
	 *
	 * @param hunterid
	 * @return
	 */
	@Override
	public Map<String, Object> getHunterCenterById(Long hunterid) {
		// TODO Auto-generated method stub
		return hunterDao.getHunterCenterById(hunterid);
	}

	/**
	 * 批发个人中心
	 */
	@Override
	public Result getHunterCenter(Long hunterid) {
		try {
			return ResultUtils.returnSuccess("查询成功", getHunterCenterById(hunterid));
		} catch (Exception e) {
			logger.error("批发个人中心：" + e.getMessage());
			return ResultUtils.returnError("查询失败");
		}

	}

	/**
	 * 安全设置-添加银行卡-检验是否绑卡
	 *
	 * @return
	 */
	@Override
	public Result chackHunterBankCard(Member member) {

		Map<String, Object> bankCard = new HashMap<String, Object>();

		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}

		Long hunterId = hunter.getId();

		Map<String, Object> hunterMap = hunterDao.getHunterById(hunterId);// 根据批发id获取信息
		String name = "";
		String card = "";
		if (null == hunterMap.get("name")) {
			name = "";
		} else {
			name = hunterMap.get("name").toString();
		}
		if (null == hunterMap.get("card")) {
			card = "";
		} else {
			card = hunterMap.get("card").toString();
		}

		if ("".equals(name) || "".equals(card)) {// 未进行身份认证

			bankCard.put("isBankCard", "1");

			return ResultUtils.returnSuccess("请求成功", bankCard);
		} else {// 已经进行身份认证

			List<Map<String, Object>> bankCardList = bankCardService.getBankCardByHunterId(hunterId);// 根据批发ID获取银行卡信息

			if (bankCardList.size() > 0) {// 已经绑定银行卡信息
				if (bankCardList.size() == 1) {
					bankCard = bankCardList.get(0);
					bankCard.put("isBankCard", "2");

					return ResultUtils.returnSuccess("请求成功", bankCard);
				} else {
					return ResultUtils.returnError("银行卡信息查询异常,多条数据");
				}

			} else {// 没有绑定银行卡信息

				bankCard.put("isBankCard", "3");
				bankCard.put("name", name);
				bankCard.put("card", card);

				return ResultUtils.returnSuccess("请求成功", bankCard);
			}

		}

	}

	/**
	 * 安全设置-修改银行卡
	 */
	@Override
	public Result updateHunterBankCard(BankCard bankCard, Member member) {

		JedisUtils jedisUtils = JedisUtils.getRu(initParam);

		Hunter hunter2 = member.getHunter();// 获取登录批发信息
		if (null == hunter2) {
			return ResultUtils.returnError("角色异常");
		}

		if (null == hunter2.getId()) {
			return ResultUtils.returnError("角色异常");
		}

		// 检验是够验证过手机号
		Long hunterId = hunter2.getId();
		String phone = hunter2.getPhone();
		if (phone == null && hunterId != null) {
			phone = hunterDao.getPhoneByHunterId(hunterId);

		}

		String value = jedisUtils.get("check" + phone);

		if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
			return ResultUtils.returnError("请验证手机号成功后再添加银行卡");
		}

		Hunter hunter = hunterDao.getById(hunter2.getId());// 根据id获取批发信息

		Integer count = this.bankCardService.getBankCardCount(hunter.getId());// 查询当月修改次数(每月允许修改2次)

		if (count >= 2) {
			return ResultUtils.returnError("每月只允许修改2次银行卡信息");
		}

		return rpcHunterService.updateHunterBankCard(bankCard, member);
	}

	/**
	 * 安全设置-批发身份认证-检验是否认证
	 *
	 * @return
	 */
	@Override
	public Result chackhuntercard(Member member) {

		Hunter hunter = member.getHunter();

		if (null == hunter) {
			return ResultUtils.returnError("角色异常");
		}

		Long hunterId = hunter.getId();

		Map<String, Object> hunterMap = hunterDao.getHunterById(hunterId);// 根据批发id获取信息
		String name = "";
		String card = "";
		if (null == hunterMap.get("name")) {
			name = "";
		} else {
			name = hunterMap.get("name").toString();
		}
		if (null == hunterMap.get("card")) {
			card = "";
		} else {
			card = hunterMap.get("card").toString();
		}

		if ("".equals(name) || "".equals(card)) {

			hunterMap.put("isCheck", "false");
			hunterMap.put("type", "身份证");
			return ResultUtils.returnSuccess("请求成功", hunterMap);
		} else {

			hunterMap.put("isCheck", "true");
			hunterMap.put("type", "身份证");
			return ResultUtils.returnSuccess("请求成功", hunterMap);
		}
	}

	/**
	 * 安全设置-删除银行卡
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Result deleteHunterBankCard(Long id, Member member) {

		return rpcHunterService.deleteHunterBankCard(id, member);
	}

	/**
	 * 批发个人中心--所属协会编辑
	 *
	 * @return
	 */
	@Override
	public Result updateIndustryAssociation(Long id, Member member) {

		Hunter hunter = member.getHunter();

		if (null == hunter) {
			return ResultUtils.returnError("角色异常");
		}

		return rpcHunterService.updateIndustryAssociation(id, member);
	}

	/***
	 * 批发个中心---行业分类 fId一类级id，sId二级类id,hId批发Id
	 */
	@Override
	public Result upateIndustryClassify(Long fId, Long sId, Long hId) {
		// TODO Auto-generated method stub
		if (fId == null || "".equals(fId)) {
			return ResultUtils.returnError("请选择行类");
		}
		try {

			if (sId > 0) {
				// 进行保存对应的数据hunter里面保存的是
				rpcHunterService.upateIndustryClassify(sId, hId);

			} else {
				// 保存一级
				rpcHunterService.upateIndustryClassify(fId, hId);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ResultUtils.returnError("接口异常");
		}
		return ResultUtils.returnSuccess("添加成功");

	}

	/**
	 * 根据行业类别查询批发列表
	 *
	 * @param industryid
	 * @return
	 */
	@Override
	public Result findHunterByIndustryTypeId(Long industryid, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		if (industryid == null) {
			return ResultUtils.returnError("请选择行业分类");
		}
		if (industryTypeService.getIndustryTypeById(industryid) == null) {
			return ResultUtils.returnError("不存在此行业分类");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page - 1) * rows);
		params.put("endIndex", rows);
		params.put("industryid", industryid);
		List<Map<String, Object>> hunters = hunterDao.findHunterByIndustryTypeId(params);
		for (Map<String, Object> hunter : hunters) {
			String pro = hunter.get("provincename") == null ? "" : hunter.get("provincename").toString();
			String city = hunter.get("cityname") == null ? "" : hunter.get("cityname").toString();
			String country = hunter.get("countyname") == null ? "" : hunter.get("countyname").toString();
			if ("北京市".equals(pro) || "天津市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro)) {
				hunter.put("position", pro + country);// 服务的区域
			} else {
				hunter.put("position", pro + city);
			}
		}
		return ResultUtils.returnSuccess("查询成功", hunters);
	}

	/**
	 * 根据专题ID获取批发信息
	 *
	 * @param sid
	 * @return
	 */
	@Override
	public Result getHuntersBySubjectId(Long sid, int page, int size) {
		if (Objects.isNull(sid)) {
			return ResultUtils.returnError("参数错误");
		}
		try {
			Map<String, Object> map = Maps.newHashMap();
			map.put("sid", sid);
			map.put("page", (page - 1) * size);
			map.put("size", size);
			// 根据hunter的id查询出图片
			/*
			 * Map<String, Object>
			 * result1=hunterDao.getHunterRuleAttachmentListByid(map);
			 */
			List<Map<String, Object>> result = hunterDao.getHuntersBySubject(map);
			result.forEach((m) -> {
				String pro = m.get("provincename") == null ? "" : m.get("provincename").toString();
				String city = m.get("cityname") == null ? "" : m.get("cityname").toString();
				String country = m.get("countyname") == null ? "" : m.get("countyname").toString();
				if ("北京市".equals(pro) || "天津市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro)) {
					m.put("position", pro + country);// 服务的区域
				} else {
					m.put("position", pro + city);
				}
			});
			return ResultUtils.returnSuccess("成功", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.returnError("接口异常");
		}

	}

	@Override
	public Hunter getById(Long id) {
		// TODO Auto-generated method stub
		return hunterDao.getById(id);
	}

	/**
	 * 批发个人中心--店铺管理--检测是否有背景图
	 *
	 * @return
	 */
	@Override
	public Result chackhunterbackgroundimage(Member member) {

		Map<String, Object> backgroundImage = new HashMap<String, Object>();

		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}

		Long hunterId = hunter.getId();

		Map<String, Object> backgroundImageMap = hunterDao.getBackgroundImageById(hunterId);// 根据批发id获取店铺背景图片信息
		String imageAddress = "";
		if (null == backgroundImageMap.get("imageAddress")) {
			imageAddress = "";
		} else {
			imageAddress = backgroundImageMap.get("imageAddress").toString();
		}

		if ("".equals(imageAddress)) {// 没有店铺背景图片

			backgroundImage.put("isBackgroundImage", "0");

			return ResultUtils.returnSuccess("请求成功", backgroundImage);
		} else {// 有店铺背景图片

			backgroundImage.put("isBackgroundImage", "1");
			backgroundImage.put("imageAddress", imageAddress);

			return ResultUtils.returnSuccess("请求成功", backgroundImage);

		}

	}

	/**
	 * 批发个人中心--店铺管理--添加背景图
	 *
	 * @return
	 */
	@Override
	public Result addhunterbackgroundimage(Member member, Long hunterId, Long imageId) {

		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}
		Long id = hunter.getId();// 批发id
		if (hunterId.intValue() != id.intValue()) {

			return ResultUtils.returnError("信息不一致");
		}

		return rpcHunterService.addhunterbackgroundimage(hunterId, imageId);
	}

	/***
	 * phone 手机号 code 验证码
	 *
	 * @author wudi
	 */
	public Result bankForCheckPhone(Member member, String phone, String code) {
		// TODO Auto-generated method stub
		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}
		// 验证手机号

		return null;
	}

	/***
	 * 判断登录用户是否关注该批发
	 *
	 * @param hId
	 *            批发id
	 * @author wudi
	 */
	@Override
	public Result getHunterFoucsType(Member member, Long hId) {
		// TODO Auto-generated method stub

		Result result = new Result();
		Long num = member.getId();
		Result hunterBaseMessage = hunterProductDetailService.getHunterBaseMessage(hId);
		Map<String, Object> content = (Map) hunterBaseMessage.getContent();
		Map<String, Object> map = hunterDao.getHunterFoucsType(member.getId(), hId);
		// 判断0是收藏 1是未收藏
		if (map == null) {
			content.put("type", 1);
		} else {
			content.put("type", map.get("type"));
		}
		result.setContent(content);
		return result;

	}

	/**
	 * 注册-完善批发信息一期优化接口
	 *
	 * @return
	 */
	@Override
	public Result addhunterLabel(HunterLabelModel hunterLabelModel) {

		Result result = new Result();

		if (hunterLabelModel.getNickname() == null || "".equals(hunterLabelModel.getNickname())) {

			return ResultUtils.returnError("昵称不能为空");
		} else if (hunterLabelModel.getDetail() == null || "".equals(hunterLabelModel.getDetail())) {

			return ResultUtils.returnError("详细地址不能为空");
		} else if (hunterLabelModel.getService() == null || "".equals(hunterLabelModel.getService())) {

			return ResultUtils.returnError("批发服务不能为空");
		} else if (hunterLabelModel.getMajor() == null || "".equals(hunterLabelModel.getMajor())) {

			return ResultUtils.returnError("批发专业不能为空");
		} /*
			 * else if (hunterLabelModel.getIndustryTypeIdList() == null ||
			 * "".equals(hunterLabelModel.getIndustryTypeIdList())) { <<<<<<<
			 * HEAD
			 * 
			 * =======
			 *
			 * >>>>>>> branch 'kuaihuo' of
			 * http://192.168.0.88/puhui-team/phshopping-parent.git return
			 * ResultUtils.returnError("行业分类不能为空"); }
			 */

		// 去除空格
		String nickname = hunterLabelModel.getNickname().replaceAll(" ", "");// 昵称
		String detail = hunterLabelModel.getDetail().replaceAll(" ", "");// 详细地址
		String service = hunterLabelModel.getService().replaceAll(" ", "");// 批发服务major
		String major = hunterLabelModel.getMajor().replaceAll(" ", "");// 详细地址

		// 行业二级分类id的集合
		String industryTypeIdList = hunterLabelModel.getIndustryTypeIdList();
		String[] aa = industryTypeIdList.split(",");
		if (aa.length > 10) {
			return ResultUtils.returnError("行业分类最多10个");
		}

		// 验证
		if (null == hunterLabelModel.getMerberId() || "".equals(hunterLabelModel.getMerberId())) {

			return ResultUtils.returnError("merber不能为空");
		} else if (null == hunterLabelModel.getLogoAttachmentId()
				|| "".equals(hunterLabelModel.getLogoAttachmentId())) {

			return ResultUtils.returnError("头像不能为空");
		} else if (nickname.length() < 2 || nickname.length() > 10) {

			return ResultUtils.returnError("昵称请输入2-10位");
		} else if (null == hunterLabelModel.getLatitude() || "".equals(hunterLabelModel.getLatitude())) {// 是维度

			return ResultUtils.returnError("维度不能为空");
		} else if (null == hunterLabelModel.getLongitude() || "".equals(hunterLabelModel.getLongitude())) {// 是经度

			return ResultUtils.returnError("经度不能为空");
		} else if (detail.length() < 1 || detail.length() > 30) {

			return ResultUtils.returnError("详细地址请输入1-30位");
		} else if (null == hunterLabelModel.getIndustryTypeIdList()
				|| "".equals(hunterLabelModel.getIndustryTypeIdList())) {

			return ResultUtils.returnError("请选择标签");
		} else if (service.length() < 1 || service.length() > 20) {

			return ResultUtils.returnError("批发服务请输入1-20位");
		} else if (major.length() < 1 || major.length() > 20) {

			return ResultUtils.returnError("批发专业请输入1-20位");
		} else if (hunterLabelModel.getPositionLevel() == null) {

			return ResultUtils.returnError("等级不能为空");
		} else if (hunterLabelModel.getStation() == null || hunterLabelModel.getStation().equals("")) {

			return ResultUtils.returnError("经纬度定位地址不能为空");
		}

		/*
		 * else if (null == hunterLabelModel.getProvinceId() ||
		 * "".equals(hunterLabelModel.getProvinceId())) {// 是省id
		 *
		 * return ResultUtils.returnError("驻地省不能为空"); } else if (null ==
		 * hunterLabelModel.getCityId() ||
		 * "".equals(hunterLabelModel.getCityId())) {// 是市id
		 *
		 * return ResultUtils.returnError("驻地市不能为空"); } else if (null ==
		 * hunterLabelModel.getCountyId() ||
		 * "".equals(hunterLabelModel.getCountyId())) {// 是县id
		 *
		 * return ResultUtils.returnError("驻地县不能为空"); } else if (null ==
		 * hunterLabelModel.getTownId() ||
		 * "".equals(hunterLabelModel.getTownId())) {// 是townId
		 *
		 * return ResultUtils.returnError("驻地乡镇不能为空"); }
		 */ // wangzhen 2017年7月20日16:43:34 注释原因 只在南京做试点

		// 检验昵称是否已存在
		List<Map<String, Object>> hunterNicknameList = hunterDao.getHunterByNickname(nickname);
		if (hunterNicknameList.size() > 0) {
			return ResultUtils.returnError("该昵称已存在，请重新输入");
		}

		// 根据社区编号调用普惠获取省市县信息接口
		// Position position =
		// positionService.getPHPositionByTownId(hunterLabelModel.getTownId());

		// Position
		// position=positionService.getPositionByTownId(hunterLabelModel.getTownId());//根据社区编号获取省市县信息接口

		Hunter hunter = new Hunter();
		Long merberId = hunterLabelModel.getMerberId();// 关联的merber表id

		IndustryAssociation industryAssociation = new IndustryAssociation();// 行业协会
		if (null == hunterLabelModel.getIndustryAssociationId()
				|| "".equals(hunterLabelModel.getIndustryAssociationId())) {

			hunter.setIndustryAssociation(null);
		} else {
			industryAssociation.setId(hunterLabelModel.getIndustryAssociationId());
			hunter.setIndustryAssociation(industryAssociation);
		}

		Attachment logoAttachment = new Attachment();// 头像
		logoAttachment.setId(hunterLabelModel.getLogoAttachmentId());
		hunter.setLogoAttachment(logoAttachment);

		hunter.setNickname(nickname);// 昵称

		hunter.setPhProvinceId(Long.valueOf("320"));// 省级id
		hunter.setProvinceName("江苏省");// 省级名称
		hunter.setPhCityId(Long.valueOf("320100000000"));// 市级id
		hunter.setCityName("南京市");// 市级名称

		// hunter.setPhTownId(position.getTownId());// 乡镇id
		// hunter.setTownName(position.getTownName());// 乡镇名称
		hunter.setLongitude(hunterLabelModel.getLongitude());// 经度
		hunter.setLatitude(hunterLabelModel.getLatitude());// 维度

		hunter.setDetail(detail);// 详细地址
		hunter.setService(service);// 服务
		hunter.setMajor(major);// 专业

		hunter.setPositionLevel(hunterLabelModel.getPositionLevel());// 等级
		hunter.setStation(hunterLabelModel.getStation());// 经纬度定位地址

		hunter.setTownName(null);
		hunter.setPhTownId(null);
		if (hunterLabelModel.getPositionLevel().intValue() == 1) {

			hunter.setPhCountyId(null);
			hunter.setCountyName(null);
		} else if (hunterLabelModel.getPositionLevel().intValue() == 2) {

			if (null == hunterLabelModel.getCountyName() || "".equals(hunterLabelModel.getCountyName())
					|| null == hunterLabelModel.getCountyId()) {

				return ResultUtils.returnError("驻地县不能为空");
			}
			hunter.setPhCountyId(hunterLabelModel.getCountyId());// 县级id
			hunter.setCountyName(hunterLabelModel.getCountyName());// 县级名称
		} else {
			ResultUtils.returnError("等级错误");
		}

		result = rpcHunterService.addhunterLabel(merberId, hunter, industryTypeIdList);

		return result;
	}

	/**
	 * 批发个人信息编辑- 标签分类接口
	 *
	 * @return
	 */
	@Override
	public Result updateHunterlabel(Long id, Member member, String industryTypeIdList) {
		// 验证最多10个
		String[] aa = industryTypeIdList.split(",");
		if (aa.length > 10) {
			return ResultUtils.returnError("行业分类最多10个");
		}

		return rpcHunterService.updateHunterlabel(id, member, industryTypeIdList);
	}

	/**
	 * 根据批发id获取二级分类标签
	 */
	@Override
	public Result findhHunterLabel(Long hunterId, Member member) {

		Result result = new Result();

		Hunter hunter = member.getHunter();

		if (null == hunter) {

			return ResultUtils.returnError("角色异常");
		}
		Long id = hunter.getId();// 批发id
		if (hunterId.intValue() != id.intValue()) {

			return ResultUtils.returnError("信息不一致");
		}

		List<Map<String, Object>> secondIndustryType = hunterIndustryTypeService.findhHunterLabel(hunterId);
		if (secondIndustryType.size() <= 0) {
			return ResultUtils.returnError("没有数据");
		}

		result.setCode(1);
		result.setMsg("查询成功");
		result.setContent(secondIndustryType);

		return result;
	}

	/**
	 * 获取批发商头像信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getHunterLogoImage(Long id) {
		return hunterDao.getHunterLogoImage(id);
	}

	/*
	 * 更新图片
	 */
	@Override
	public Result updateHunterBusinessLicence(Long saveimageid, Long updateimageid, Long hunterid, int type) {
		return rpcPhHunterRuleAttachmentService.updateAttachment(saveimageid, updateimageid, hunterid, type);

	}

	/**
	 * 重写 登录加注册
	 */
	@Transactional(rollbackFor = Exception.class)
	public Result addHunterAndRegister(HunterModel hunterModel) {
		Member member = new Member();
		JedisUtils jedisUtils = new JedisUtils();
		// 获取手机或
		String phone = hunterModel.getPhone();

		String passWord = hunterModel.getPassWord();// 原始密码
		/*
		 * jedisUtils.del(phone); jedisUtils.del(passWord); if (phone == null ||
		 * passWord == null) { ResultUtils.returnError("请输入手机号或者密码"); } Result
		 * addMember = userRegisterService.addMember(passWord, phone); if
		 * (addMember.getCode()==1) { member=(Member)addMember.getContent();
		 * }else{ ResultUtils.returnError("数据异常"); }
		 */

		Result result = new Result();

		if (null == hunterModel.getNickname() || "".equals(hunterModel.getNickname())) {

			return ResultUtils.returnError("昵称不能为空");
		} else if (null == hunterModel.getDetail() || "".equals(hunterModel.getDetail())) {

			return ResultUtils.returnError("详细地址不能为空");
		} else if (null == hunterModel.getService() || "".equals(hunterModel.getService())) {

			return ResultUtils.returnError("批发服务不能为空");
		}
		// 去除空格
		String nickname = hunterModel.getNickname().replaceAll(" ", "");// 昵称
		String detail = hunterModel.getDetail().replaceAll(" ", "");// 详细地址
		String service = hunterModel.getService().replaceAll(" ", "");// 批发服务
		String majar = hunterModel.getMajor().trim();
		// 行业二级分类id的集合
		String industryTypeIdList = hunterModel.getIndustryTypeIdList();
		String[] aa = industryTypeIdList.split(",");
		if (aa.length > 10) {
			return ResultUtils.returnError("行业分类最多10个");
		}

		// 验证
		/*
		 * if (null == hunterModel.getMerberId() ||
		 * "".equals(hunterModel.getMerberId())) { <<<<<<< HEAD
		 * 
		 * =======
		 *
		 * >>>>>>> branch 'kuaihuo' of
		 * http://192.168.0.88/puhui-team/phshopping-parent.git return
		 * ResultUtils.returnError("merber不能为空"); }
		 */
		if (null == hunterModel.getLogoAttachmentId() || "".equals(hunterModel.getLogoAttachmentId())) {

			return ResultUtils.returnError("头像不能为空");
		} else if (nickname.length() < 2 || nickname.length() > 10) {

			return ResultUtils.returnError("昵称请输入2-10位");
		} else if (null == hunterModel.getLatitude() || "".equals(hunterModel.getLatitude())) {// 是维度

			return ResultUtils.returnError("维度不能为空");
		} else if (null == hunterModel.getLongitude() || "".equals(hunterModel.getLongitude())) {// 是经度

			return ResultUtils.returnError("经度不能为空");
		} else if (detail.length() < 1 || detail.length() > 30) {

			return ResultUtils.returnError("详细地址请输入1-30位");
		} else if (null == hunterModel.getIndustryTypeIdList() || "".equals(hunterModel.getIndustryTypeIdList())) {

			return ResultUtils.returnError("请选择标签");
		} else if (service.length() < 1 || service.length() > 20) {

			return ResultUtils.returnError("批发服务请输入1-20位");
		} else if (majar.length() < 1 || majar.length() > 20) {

			return ResultUtils.returnError("专业请输入1-20位");
		} else if (hunterModel.getPositionLevel() == null) {

			return ResultUtils.returnError("等级不能为空");
		} else if (hunterModel.getStation() == null || "".equals(hunterModel)) {

			return ResultUtils.returnError("经纬度定位地址不能为空");
		} else if (hunterModel.getBusinessLicence() == null || "".equals(hunterModel.getBusinessLicence())) {
			return ResultUtils.returnError("营业执照不能为空");
		} else if (hunterModel.getIdCardAttachment() == null || "".equals(hunterModel.getIdCardAttachment())) {
			return ResultUtils.returnError("身份证照不能为空");
		} /*
			 * else if(hunterModel.getIdCardAttachment().length()!=3){ return
			 * ResultUtils.returnError("请上传身份证正反面照片"); }
			 */
		// 1 营业执照图片 2 身份证正面图片 3 身份证反面图片
		/*
		 * else if (null == hunterModel.getProvinceId() ||
		 * "".equals(hunterModel.getProvinceId())) {// 是省id
		 *
		 * return ResultUtils.returnError("驻地省不能为空"); } else if (null ==
		 * hunterModel.getCountyId() || "".equals(hunterModel.getCountyId()))
		 * {// 是县id
		 *
		 * return ResultUtils.returnError("驻地县不能为空"); } else if (null ==
		 * hunterModel.getTownId() || "".equals(hunterModel.getTownId())) {//
		 * 是townId
		 *
		 * return ResultUtils.returnError("驻地乡镇不能为空"); }else if (null ==
		 * hunterModel.getCityId() || "".equals(hunterModel.getCityId())) {//
		 * 是市id
		 *
		 * return ResultUtils.returnError("驻地市不能为空"); }
		 */ // wangzhen 2017年7月20日11:48:02 注释原因：只在南京做试点

		// 检验昵称是否已存在
		List<Map<String, Object>> hunterNicknameList = hunterDao.getHunterByNickname(nickname);
		if (hunterNicknameList.size() > 0) {
			return ResultUtils.returnError("该昵称已存在，请重新输入");
		}

		// 检验乡镇id是否已有批发
		/*
		 * List<Map<String,Object>>
		 * hunterList=hunterDao.getHunterByTownId(hunterModel.getTownId());
		 * if(hunterList.size()>0){ return
		 * ResultUtils.returnError("所属乡镇已有批发，请重新选择"); }
		 */
		/*
		 * private Long provinceId;// 省 直辖市 自治区 特别行政区编号 <<<<<<< HEAD
		 * 
		 * private Long cityId;// 市区编号
		 * 
		 * =======
		 *
		 * private Long cityId;// 市区编号
		 *
		 * >>>>>>> branch 'kuaihuo' of
		 * http://192.168.0.88/puhui-team/phshopping-parent.git private Long
		 * countyId;// 县编号
		 */

		// 根据社区编号调用普惠获取省市县信息接口
		// Position position =
		// positionService.getPHPositionByTownId(hunterModel.getTownId());
		// Position
		// position=positionService.getPositionByTownId(hunterModel.getTownId());//根据社区编号获取省市县信息接口

		Hunter hunter = new Hunter();
		if (hunterModel.getPositionLevel().intValue() == 1) {

			if (hunterModel.getCityId() == null || "".equals(hunterModel.getCityId())) {
				return ResultUtils.returnError("代理市不能为空");
			}

			Map position = positionDao.getBycityid(hunterModel.getCityId());
			hunter.setPhCityId((Long) position.get("id"));
			hunter.setCityName((String) position.get("name"));

			hunter.setPhProvinceId((Long) position.get("pid"));
			hunter.setProvinceName((String) position.get("pname"));
		} else if (hunterModel.getPositionLevel().intValue() == 2) {
			if (hunterModel.getCountyId() == null || "".equals(hunterModel.getCountyId())) {
				return ResultUtils.returnError("代理县不能为空");
			}

			List<Map<String, Object>> position = positionDao.getBycountyid(hunterModel.getCountyId());

			hunter.setPhCityId((Long) position.get(0).get("id"));
			hunter.setCityName((String) position.get(0).get("name"));

			hunter.setPhProvinceId((Long) position.get(0).get("pid"));
			hunter.setProvinceName((String) position.get(0).get("pname"));

			hunter.setPhCountyId((Long) position.get(0).get("cid"));
			hunter.setCountyName((String) position.get(0).get("cname"));
		} else {
			return ResultUtils.returnError("等级错误");
		}

		IndustryAssociation industryAssociation = new IndustryAssociation();// 行业协会
		if (null == hunterModel.getIndustryAssociationId() || "".equals(hunterModel.getIndustryAssociationId())) {

			hunter.setIndustryAssociation(null);
		} else {
			industryAssociation.setId(hunterModel.getIndustryAssociationId());
			hunter.setIndustryAssociation(industryAssociation);
		}

		Attachment logoAttachment = new Attachment();// 头像
		logoAttachment.setId(hunterModel.getLogoAttachmentId());
		hunter.setLogoAttachment(logoAttachment);

		ArrayList<String> arrayList = new ArrayList<String>();

		arrayList.addAll(Arrays.asList(hunterModel.getIdCardAttachment().split(",")));// 将身份证照分隔开

		ArrayList<String> arrayList2 = new ArrayList<String>();
		if (hunterModel.getOtherAttachment() != null && !"".equals(hunterModel.getOtherAttachment())) {

			arrayList2.addAll(Arrays.asList(hunterModel.getOtherAttachment().split(",")));

		}

		for (String string : arrayList2) {
			arrayList.add(string);
			System.out.println(string);
		}

		arrayList.add(hunterModel.getBusinessLicence());

		Result addMember = userRegisterService.addMember(passWord, phone);
		if (addMember.getCode() == 1) {
			member = (Member) addMember.getContent();
		} else {
			ResultUtils.returnError("数据异常");
		}

		// Long merberId = hunterModel.getMerberId();// 关联的merber表id
		Long merberId = member.getId();

		PhHunterRuleAttachmentVO phVO = new PhHunterRuleAttachmentVO();
		phVO.setRuleAttachmentList(arrayList);

		hunter.setNickname(nickname);// 昵称
		/*
		 * Position position =
		 * positionService.getAllBYTownId(hunterModel.getTownId());
		 */

		/*
		 * hunter.setPhProvinceId(hunterModel.getProvinceId());// 省级id
		 * hunter.setProvinceName(position.getProvinceName());// 省级名称
		 * hunter.setPhCityId(hunterModel.getCityId());// 市级id
		 * hunter.setCityName(position.getCityName());// 市级名称
		 * hunter.setPhTownId(hunterModel.getTownId());
		 * hunter.setTownName(position.getTownName());
		 */
		/*
		 * Position position =
		 * positionService.getAllBYTownId(hunterModel.getTownId());
		 */

		/*
		 * hunter.setPhProvinceId(hunterModel.getProvinceId());// 省级id
		 * hunter.setProvinceName(position.getProvinceName());// 省级名称
		 * hunter.setPhCityId(hunterModel.getCityId());// 市级id
		 * hunter.setCityName(position.getCityName());// 市级名称
		 * hunter.setPhTownId(hunterModel.getTownId());
		 * hunter.setTownName(position.getTownName());
		 */
		hunter.setLongitude(hunterModel.getLongitude());// 经度
		hunter.setLatitude(hunterModel.getLatitude());// 维度
		hunter.setMajor(hunterModel.getMajor());// 专业
		hunter.setDetail(detail);// 详细地址

		hunter.setService(service);// 服务
		hunter.setPositionLevel(hunterModel.getPositionLevel());// 等级
		hunter.setStation(hunterModel.getStation());
		/*
		 * hunter.setTownName(null); hunter.setPhTownId(null);
		 */

		/*
		 * if (hunterModel.getPositionLevel().intValue() == 1) {
		 * 
		 * hunter.setPhCountyId(null); hunter.setCountyName(null); } else if
		 * (hunterModel.getPositionLevel().intValue() == 2) {
		 * 
		 * if (null == hunterModel.getCountyName() ||
		 * "".equals(hunterModel.getCountyName()) || null ==
		 * hunterModel.getCountyId()) {
		 * 
		 * return ResultUtils.returnError("驻地县不能为空"); }
		 * hunter.setPhCountyId(hunterModel.getCountyId());// 县级id
		 * hunter.setCountyName(hunterModel.getCountyName());// 县级名称 } else {
		 * ResultUtils.returnError("等级错误"); }
		 */

		// hunter.setPhTownId(position.getTownId());// 乡镇id
		// hunter.setTownName(position.getTownName());// 乡镇名称
		// ==============================================保存数据到member表中==========================================================

		result = rpcHunterService.addhunterLabelAndAttachment(merberId, hunter, industryTypeIdList, phVO);

		return result;

	}

	@Override
	public Result hunterarearang(Long hunterId) {
		try {
			return rpcHunterService.hunterarearang(hunterId);
		} catch (Exception e) {
			logger.error("商户配送范围：" + e.getMessage());
			return ResultUtils.returnError("加载失败");
		}


	}

	@Override
	public Result updatehunterarearang(Long districtId, String districts, Member member) {
		try {
			rpcHunterService.updatehunterarearang(districtId, districts, member.getHunter().getId());
		} catch (Exception e) {
			return ResultUtils.returnError("接口异常");
		}
		return ResultUtils.returnSuccess("保存成功");

	}
}
