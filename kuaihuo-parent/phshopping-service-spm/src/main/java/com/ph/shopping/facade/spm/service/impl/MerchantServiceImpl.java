package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.mapper.*;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.entity.StoreManagerRecord;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.member.MemberIsMarketingEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.permission.constant.UserConstant;
import com.ph.shopping.facade.permission.entity.User;
import com.ph.shopping.facade.permission.entity.UserRole;
import com.ph.shopping.facade.permission.exception.PermissionBizException;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.MerchantImageDTO;
import com.ph.shopping.facade.spm.entity.*;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum.*;
import com.ph.shopping.facade.spm.vo.AgentVO;
import com.ph.shopping.facade.spm.vo.MerchanTimeSectionVO;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.ph.shopping.utils.APIHttpClient;
import com.ph.shopping.utils.Tls_sigature;
import net.sf.json.JSONArray;
import org.alqframework.utils.UniqueUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 何文浪
 * @version 2.1
 * @项目 phshopping-service-spm
 * @描述 商户业务层
 * @时间 2017-5-12
 */
@Component
@Service(version = "1.0.0")
public class MerchantServiceImpl implements IMerchantService {
    // 日志
    private final static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    // 商户持久层
    @Autowired
    private MerchantMapper merchantMapper;
    // 商户类别持久层
    @Autowired
    private MerchantTypeMapper merchantTypeMapper;
    // 商户图片持久层
    @Autowired
    private MerchantImageMapper merchantImageMapper;
    // 初始化代理商持久层
    @Autowired
    private AgentMapper agentMapper;
    // 用户登录持久层
    @Autowired
    private PermissionCommonUserMapper permissionCommonUserMapper;
    // 用户角色持久层
    @Autowired
    private UserRoleMapper userRoleMapper;
    // 用户余额持久层
    @Autowired
    private UserBalanceMapper userBalanceMapper;
    // 商户行业类别挂靠持久层
    @Autowired
    private MerchantMerchantTypeMapper merchantMerchantTypeMapper;
    // 短信发送接口
    @Autowired
    private ISmsDataService smsDataService;
    @Autowired
    private StoreManagerMapper storeManagerMapper;
    // 会员接口
    @Reference(version = "1.0.0")
    IMemberService iMemberService;


    public List<MerchantVO> getMerchantListDetail(MerchantDTO merchantDTO) {
        // 如果是查询详情 增加浏览次数
        if (merchantDTO.getId() != null) {
            merchantMapper.incrementViews(merchantDTO.getId());
        }
        return merchantMapper.getMerchantListDetail(merchantDTO);
    }

    public Result getMerchantByPage(PageBean pageBean, MerchantDTO merchantDTO) {
        // 分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<MerchantVO> list = merchantMapper.getMerchantList(merchantDTO);
        // if(list==null || list.size()<=0){
        // return new Result(false,
        // supplierExtion.GET.getIndex(),supplierExtion.GET.getName(), list,
        // 0);
        // }else{
        PageInfo<MerchantVO> pageInfo = new PageInfo<MerchantVO>(list);
        return new Result(true, supplierExtion.GET.getIndex(), "", pageInfo.getList(), pageInfo.getTotal());
        // }
    }

    @Override
    public Result getHunterByPage(PageBean pageBean, MerchantDTO merchantDTO) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        //List<Map> list = merchantMapper.getHunterList(id);

        Map map = merchantMapper.getLevel(merchantDTO.getId());
        //Long.valueOf(map.get("agentLevelId").toString()), (Long) map.get("cityId"), (Long) map.get("countyId")
        merchantDTO.setLevel(Long.valueOf(map.get("agentLevelId").toString()));
        merchantDTO.setCityId((Long) map.get("cityId"));
        merchantDTO.setCountyId((Long) map.get("countyId"));
        List<Map<String, Object>> list = merchantMapper.getHunter(merchantDTO);
        for (int i = 0; i < list.size(); i++) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            List<Map> industry = merchantMapper.getIndustry((Long.valueOf(list.get(i).get("id").toString())));
            for (int j = 0; j < industry.size(); j++) {
                System.out.println(j);
                if (j < industry.size() - 1) {
                    sb.append(industry.get(j).get("name") + "、");
                } else {
                    sb.append(industry.get(j).get("name"));
                }
            }
            sb2.append(list.get(i).get("city_name")).append(list.get(i).get("county_name")).append(list.get(i).get("town_name"));
            list.get(i).put("industry", sb);
            list.get(i).put("position", sb2);

        }
        JSONArray jsonArray = JSONArray.fromObject(list);
        logger.info("list大小：  " + list.size() + "内容" + jsonArray);
        //logger.info("************"+map2.get("industry")+map2.get("position"));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return new Result(true, supplierExtion.GET.getIndex(), "", pageInfo.getList(), pageInfo.getTotal());
    }


    public Result getMerchantByPageAdmin(PageBean pageBean, MerchantDTO merchantDTO) {
        // 分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Map<String,Object>> list = merchantMapper.getMerchantListAdmin(merchantDTO);
        // if(list==null || list.size()<=0){
        // return new Result(false,
        // supplierExtion.GET.getIndex(),supplierExtion.GET.getName(), list,
        // 0);
        // }else{
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        return new Result(true, supplierExtion.GET.getIndex(), "", pageInfo.getList(), pageInfo.getTotal());
        // }
    }

    public List<MerchantVO> getMerchantList(MerchantDTO merchantDTO) {
        return merchantMapper.getMerchantList(merchantDTO);
    }

    public MerchantVO getMerchantDetailById(MerchantDTO merchantDTO) {
        List<MerchantVO> list = merchantMapper.getMerchantListDetail(merchantDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else
            return null;
    }

    public MerchantVO getMerchantListById(MerchantDTO merchantDTO) {
        List<MerchantVO> list = merchantMapper.getMerchantListDetail(merchantDTO);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public List<MerchantVO> getMerchantByAgentAllList(MerchantDTO merchantDTO) {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(merchantDTO.getAgentId());
        agentDTO.setStatus(agentStatus.ONE.getIndex());
        List<AgentVO> list = new ArrayList<AgentVO>();
        List<AgentVO> listReturn = agentMapper.getAgentList(agentDTO);// 查找当前代理商
        if (listReturn.size() > 0) {// 查询当条代理商数据
            for (AgentVO vo : listReturn) {
                list.add(VoDtoEntityUtil.convert(vo, AgentVO.class));
                AgentDTO agent = new AgentDTO();
                agent.setParentId(vo.getId());
                list.addAll(returnList(agent));// 循环查找当前代理商所有子级
            }
        } else {
            agentDTO.setId(null);
            agentDTO.setProvinceId(merchantDTO.getProvinceId());
            agentDTO.setCityId(merchantDTO.getCityId());
            agentDTO.setCountyId(merchantDTO.getCountyId());
            agentDTO.setTownId(merchantDTO.getTownId());
            List<AgentVO> agentList = agentMapper.getAgentList(agentDTO);// 查找当前代理商
            if (agentList.size() > 0) {// 查询当条代理商数据
                for (AgentVO vo : agentList) {
                    list.add(VoDtoEntityUtil.convert(vo, AgentVO.class));
                    AgentDTO agent = new AgentDTO();
                    agent.setParentId(vo.getId());
                    list.addAll(returnList(agent));// 循环查找当前代理商所有子级
                }
            }
        }
        if (list.size() > 0) {
            merchantDTO = new MerchantDTO();
            List<Long> listIds = new ArrayList<Long>();
            for (AgentVO vo : list) {// 取代理商id
                listIds.add(vo.getId());
            }
            merchantDTO.setAgentIds(listIds);
        }
        return merchantMapper.getMerchantList(merchantDTO);
    }

    @Transactional
    @Override
    public Result addMerchant(MerchantDTO merchantDTO) {
        Result result = new Result(false);
        // 验证参数
        try {
            String validateMessage = merchantDTO.validateForm();
            if (validateMessage != null) {
                result.setMessage(validateMessage);
            } else {
                // 验证创建人不能为空
                if (merchantDTO.getCreaterId() == null) {
                    result.setMessage("创建人不能为空");
                } else {
                    // 验证图片
                    Result validateMerchantImg = this.validateMerchantImg(merchantDTO);
                    Agent agent = new Agent();
                    PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                    if (validateMerchantImg.isSuccess()) {
                        return validateMerchantImg;
                    } else {
                        permissionCommonUser.setTelphone(merchantDTO.getTelPhone());
                        agent.setTelPhone(merchantDTO.getTelPhone());
                        if (this.agentMapper.selectOne(agent) != null) {
                            result.setMessage("该电话号码已经被创建");
                        }
                        if (this.permissionCommonUserMapper.selectOne(permissionCommonUser) != null) {
                            result.setMessage("该电话号码已经被创建");
                            if (merchantDTO.getMerchantMerchantTypeDTOList() == null || merchantDTO.getMerchantMerchantTypeDTOList().size()==0){
                                result.setMessage("请选择行业分类");
                            }
                            if (this.merchantMapper.getPhoneByAlqMember(merchantDTO.getTelPhone()) > 0){
                                result.setMessage("该电话号码已经被创建");
                            }
                        } else {
                            //String password = ((new Random().nextDouble()) + "").substring(2, 8);
                            String password = merchantDTO.getPassword();
                            // 创建随机密码
                            /*SmsSendData sendData = new SmsSendData();
                            sendData.setMobile(permissionCommonUser.getTelphone());
							sendData.setType(SmsCodeType.EXAMINE_PASS);// 代理商，商户，供应商重置密码
							sendData.setSourceEnum(SmsSourceEnum.MERCHANT);// 枚举类型判断
							sendData.setSmsCode(password);// 随机密码

							/*Result smsResult = smsDataService.sendSmsHaveCode(sendData);
							if (!smsResult.isSuccess()) {
								logger.info("给用户发送短信密码失败:" + smsResult);
								throw new MemberException("给用户发送短信密码失败");
							}*/

                            //门店名称
                            String merchantName = merchantDTO.getMerchantName();
                            //a.过滤特殊字符
                            String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
                            Pattern p2 = Pattern.compile(regEx);
                            Matcher m2 = p2.matcher(merchantName);
                            boolean find = m2.find();
                            m2.replaceAll("").trim();
                            if (find) {
                                result.setCode("0");
                                result.setMessage("格式不正确");
                                return result;
                            }
                            //b.过滤表情符号
                            merchantName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");

                            //企业名称
                            String companyName = merchantDTO.getCompanyName();
                            //a.过滤特殊字符
                            String regExc = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
                            Pattern p2c = Pattern.compile(regExc);
                            Matcher m2c = p2c.matcher(companyName);
                            boolean findc = m2c.find();
                            m2c.replaceAll("").trim();
                            if (findc) {
                                result.setCode("0");
                                result.setMessage("格式不正确");
                                return result;
                            }
                            //b.过滤表情符号
                            companyName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");





                            // ==============================alq_member===start===============================================
                            /*注册商户改造@维护商户表、原会员表 根据快火批发APP接口的需要，保存关联的alq_member表*/

                            Map<String, Object> mapDto = new HashMap<String, Object>();
                            //手机号
                            mapDto.put("phone", merchantDTO.getTelPhone());
                            // 获取uuid
                            String uuid = UniqueUtils.getUUID();
                            uuid = uuid.replace("-", "");
                            mapDto.put("uuid", uuid);
                            //密码
                            // mapDto.put("password",MD5.getMD5Str("abc123456"));
                            mapDto.put("password", MD5.getMD5Str(password));
                            //创建的时间
                            mapDto.put("created_time", new Date());
                            /*token的处理
                            phone=telPhone,origin=KF,memberType=1;
							如果成功code为200*/
                            //http://123.207.173.18:10088/swagger-ui.html
                            String url1 = "http://123.207.173.18:10088/api/members/register?";
                            String mobile = merchantDTO.getTelPhone();
                            String origin = "KH";
                            String memberType = "1";
                            String url = url1 + "&phone=" + mobile + "&origin=" + origin + "&memberType=" + memberType;
                            APIHttpClient ac = new APIHttpClient(url);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("phone", mobile);
                            jsonObject.put("origin", origin);
                            jsonObject.put("memberType", memberType);// 用户类型：0是普通会员，1是商户
                            //System.out.println("数据："+jsonObject.toString());
                            String cc = ac.contentTypeJsonPost(jsonObject.toString());
                            //System.out.println("获取结果："+cc);
                            JSONObject code = JSONObject.parseObject(cc);
                            if (code.get("code").equals("200")) {
                                String data = (String) code.get("data");
                                mapDto.put("token", data);
                            } else {
                                mapDto.put("token", null);
                            }
                            // 获取腾讯云的注册的密码和用户id和延签
                            Map chatRegists = chatRegist(merchantDTO.getTelPhone());
                            if (chatRegists != null) {
                                mapDto.put("im_id", chatRegists.get("IMdentifier"));
                                mapDto.put("im_password", chatRegists.get("IMPassword"));
                                mapDto.put("im_sign", chatRegists.get("IMUserSign"));
                            }
                            //一共添加的到alq_member表里参数有phone,password,created_time,token,im_id,im_password,im_sign
                            merchantMapper.insertAlqMember(mapDto);
                            // ==============================alq_member===end===============================================
                            // 保存到登录表
                            permissionCommonUser.basic(merchantDTO.getCreaterId());
                            permissionCommonUser.setUserName(merchantDTO.getMerchantName());
                            permissionCommonUser.setIsable(UserConstant.START);
                            permissionCommonUser.setPassword(MD5.getMD5Str(password));
                            this.permissionCommonUserMapper.insertUseGeneratedKeys(permissionCommonUser);
                            // 保存商户
                            Merchant merchant = VoDtoEntityUtil.convert(merchantDTO, Merchant.class);
                            merchant.setUserId(permissionCommonUser.getId());
                            // 代理id
                            AgentDTO parentAgent = new AgentDTO();
                            parentAgent.setTownId(merchant.getTownId());
                            parentAgent.setCountyId(merchant.getCountyId());
                            parentAgent.setCityId(merchant.getCityId());
                            List<AgentVO> _parentAgent = this.agentMapper.getAgentVoByPosition(parentAgent);
                            if (CollectionUtils.isEmpty(_parentAgent)) {
                                merchant.setAgentId(0L);
                            } else {
                                merchant.setAgentId(_parentAgent.get(0).getId());
                            }
                            merchant.basic(merchantDTO.getCreaterId());
                            // 审核通过
                            merchant.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
                            // 未冻结
                            merchant.setIsFrozen(SPMEnum.merchantIsFrozen.ZERO.getIndex());
                            // 未删除
                            merchant.setIsDelete(SPMEnum.merchantIsDelete.ZERO.getIndex());
                            // ==============================ph_merchant===start===============================================
                            // 新增功能@维护激活码@获取随机纯数字6位
                            Random randoms = new Random();
                            String random = UUID.randomUUID().toString().replaceAll("-", "")
                                    .replaceAll("[a-zA-Z]", randoms.nextInt(10) + "").substring(0, 6);
                            merchant.setCdKey(random);
                            merchant.setIsCdKey((byte) 0);
                            //注册商户时，企业名称和门店名称中可输入超长字符（应限制输入），特殊字符，表情，点击下一步不可进行下一步操作
                            //c.set到merchant
                            merchant.setMerchantName(merchantName);
                            //c.set到merchant
                            merchant.setCompanyName(companyName);
                            //维护推广关系
                            logger.info("注册人手机号" + merchant.getTelPhone());
                            Map<String, Object> map = this.merchantMapper.selectMemberShareByToPhone(merchant.getTelPhone());
                            if (map != null) {
                                logger.info("推荐人userId" + map.get("userId"));
                                merchant.setPromoterId(Long.valueOf(map.get("userId").toString()));
                            }
                            // ==============================ph_merchant===end===============================================
                            this.merchantMapper.insertUseGeneratedKeys(merchant);
                            logger.info("注册商户完成");



                            // 保存商户图片
                            Date createTime = new Date();
                            for (MerchantImageDTO merchantImageDTO : merchantDTO.getMerchantImageDTOList()) {
                                this.merchantImgInit(merchantImageDTO, merchant.getId(), createTime,
                                        merchantDTO.getCreaterId());
                            }

                            List<MerchantImage> list = VoDtoEntityUtil
                                    .convertList(merchantDTO.getMerchantImageDTOList(), MerchantImage.class);
                            this.merchantImageMapper.insertList(list);
                            // 保存商户类别
                            List<MerchantMerchantType> merchantMerchantTypes = VoDtoEntityUtil.convertList(
                                    merchantDTO.getMerchantMerchantTypeDTOList(), MerchantMerchantType.class);
                            for (MerchantMerchantType merchantMerchantType : merchantMerchantTypes) {
                                merchantMerchantType.setMerchantId(merchant.getId());
                                merchantMerchantType.setCreaterId(merchantDTO.getCreaterId());
                                merchantMerchantType.setCreateTime(createTime);
                            }
                            this.merchantMerchantTypeMapper.insertList(merchantMerchantTypes);
                            // 余额初始化
                            UserBalance userBalance = new UserBalance();
                            userBalance.setScore(0L);
                            userBalance.setUserId(merchant.getUserId());
                            userBalance.setUserType(Integer.valueOf(RoleEnum.MERCHANT.getCode()));
                            userBalance.basic(merchant.getCreaterId());
                            this.userBalanceMapper.insert(userBalance);
                            // 权限初始化
                            UserRole userRole = new UserRole();
                            userRole.setRoleId(Long.valueOf(RoleEnum.MERCHANT.getCode()));
                            userRole.setUserId(merchant.getUserId());
                            userRole.basic(merchant.getCreaterId());
                            this.userRoleMapper.insert(userRole);

                            // 如果推广人是会员，并且推广的商户大于等于3，自动升级为推广师
                            /*if (merchantDTO.getPromoterId() != null) {
                                Result memberResult = iMemberService.getMemberInfoById(merchantDTO.getPromoterId());
								if (memberResult.isSuccess()) {
									Member member = (Member) memberResult.getData();
									boolean isMarketing = member.getIsMarketing()
											.equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
									if (!isMarketing) {
										int proNum = merchantMapper
												.verifyMemberProMerchantNum(merchantDTO.getPromoterId());
										if (proNum >= 3) {
											MemberPromotionDTO dto = new MemberPromotionDTO();
											dto.setMemberId(merchantDTO.getPromoterId());
											dto.setCreaterId(merchantDTO.getCreaterId());
											Result memberResulto = iMemberService.upgradeMemberPromotionAuto(dto);
											if (!memberResulto.isSuccess()) {
												logger.error("会员推广3个商户自动升级为推广师错误");
												return memberResulto;
											}
										}
									}
								}
							}*/

                            result.setData(permissionCommonUser.getId());
                            result.setCode("200");
                            result.setMessage("在24小时内，会有业务员上门审核");
                            result.setSuccess(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("增加商户异常", e);
            throw e;
        }
        return result;
    }

    @Transactional
    @Override
    public Result updateMerchant(MerchantDTO merchantDTO) {
        Result result = new Result(false);
        String Newphone = merchantDTO.getTelPhone();
        String oldPhone = "";
        // 验证参数
        try {
            // 验证修改人不能为空
            if (merchantDTO.getUpdaterId() == null) {
                result.setMessage("修改人不能为空");
            } else {
                // 验证图片不能为空
                Result validateMerchantImg = this.validateMerchantImg(merchantDTO);
                if (validateMerchantImg.isSuccess()) {
                    result.setMessage(validateMerchantImg.getMessage());
                } else {
                    PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                    // 更新商户
                    Merchant merchant = this.merchantMapper.selectByPrimaryKey(merchantDTO.getId());
                    oldPhone = merchant.getTelPhone();
                    permissionCommonUser.setTelphone(oldPhone);
                    PermissionCommonUser pcu = this.permissionCommonUserMapper.selectOne(permissionCommonUser);
                    pcu.setTelphone(merchantDTO.getTelPhone());
                    this.permissionCommonUserMapper.updateByPrimaryKeySelective(pcu);
                    VoDtoEntityUtil.copyPropertiesNotNull(merchantDTO, merchant);
                    merchant.basic(merchantDTO.getUpdaterId());
                    merchant.setStatus(SPMEnum.merchantStatus.ZERO.getIndex());
                    // 代理id
                    AgentDTO parentAgent = new AgentDTO();
                    parentAgent.setTownId(merchant.getTownId());
                    parentAgent.setCountyId(merchant.getCountyId());
                    parentAgent.setCityId(merchant.getCityId());
                    List<AgentVO> _parentAgent = this.agentMapper.getAgentVoByPosition(parentAgent);
                    if (CollectionUtils.isEmpty(_parentAgent)) {
                        merchant.setAgentId(0L);
                    } else {
                        merchant.setAgentId(_parentAgent.get(0).getId());
                    }
                    this.merchantMapper.updateByPrimaryKey(merchant);
                    // 删除之前的图片
                    MerchantImage merchantImage = new MerchantImage();
                    merchantImage.setMerchantId(merchant.getId());
                    this.merchantImageMapper.delete(merchantImage);
                    // 保存商户图片
                    Date createTime = new Date();
                    for (MerchantImageDTO merchantImageDTO : merchantDTO.getMerchantImageDTOList()) {
                        this.merchantImgInit(merchantImageDTO, merchant.getId(), createTime,
                                merchantDTO.getUpdaterId());
                    }

                    List<MerchantImage> list = VoDtoEntityUtil.convertList(merchantDTO.getMerchantImageDTOList(),
                            MerchantImage.class);
                    this.merchantImageMapper.insertList(list);

                    if (!CollectionUtils.isEmpty(merchantDTO.getMerchantMerchantTypeDTOList())) {
                        MerchantMerchantType deleteMerchantType = new MerchantMerchantType();
                        deleteMerchantType.setMerchantId(merchant.getId());
                        this.merchantMerchantTypeMapper.delete(deleteMerchantType);
                        // 商户类别
                        List<MerchantMerchantType> merchantMerchantTypes = VoDtoEntityUtil
                                .convertList(merchantDTO.getMerchantMerchantTypeDTOList(), MerchantMerchantType.class);
                        for (MerchantMerchantType merchantMerchantType : merchantMerchantTypes) {
                            merchantMerchantType.setMerchantId(merchant.getId());
                            merchantMerchantType.setCreaterId(merchantDTO.getUpdaterId());
                            merchantMerchantType.setCreateTime(createTime);
                        }
                        this.merchantMerchantTypeMapper.insertList(merchantMerchantTypes);
                    }
                    //修改对应的alq_member中phone
                    merchantMapper.updateMemberByPhone(Newphone, oldPhone);
                    result.setMessage("商户修改成功");
                    result.setSuccess(true);
                }
            }
        } catch (Exception e) {
            logger.error("修改商户异常", e);
            throw e;
        }
        return result;
    }

    @Transactional
    @Override
    public Result updateMerchantByIsFrozen(MerchantDTO merchantDTO) {
        Result result = new Result(false);
        result.setMessage("操作失败");
        Merchant marchnt = new Merchant();
        // 验证参数
        try {
            if (merchantDTO.getUpdaterId() == null || merchantDTO.getUpdaterId() <= 0) {
                result.setMessage("修改人不能为空！");
            } else {
                BeanUtils.copyProperties(merchantDTO, marchnt);
                // 查找是否存在代理商，只会查询出一个代理商（正常只有审核通过的时候才找代理商）
                List<AgentVO> list = getALLAgentList(merchantDTO);
                if (list != null && list.size() > 0) {
                    marchnt.setAgentId(list.get(0).getId());
                }
                // 修改商户
                Integer returnCount = merchantMapper.updateByPrimaryKeySelective(marchnt);
                if (returnCount > 0) {
                    // 审核通过时初始化登录密码
                    if (merchantDTO.getStatus() != null && merchantDTO.getStatus() == merchantStatus.ONE.getIndex()) {
                        User user = new User();
                        user.setUpdaterId(merchantDTO.getUpdaterId());
                        user.setId(merchantDTO.getUserId());
                        // 删除的时候删除用户登录账号并且删除商户余额
                    } else if (merchantDTO.getIsDelete() != null
                            && merchantDTO.getIsDelete() == merchantIsDelete.ONE.getIndex()) {
                        User user = new User();
                        user.setId(merchantDTO.getUserId());
                        // 删除登录账号
                        result = deleteUser(user);
                        if (result.isSuccess()) {
                            // 删除余额
                            deleteUserBalance(merchantDTO.getUserId());
                        }
                    }
                } else {
                    result.setMessage(merchantExtion.OPERATING.getName());
                }
            }
        } catch (Exception e) {
            result.setMessage(
                    new SPMEnum(merchantExtion.OPERATING.getName(), merchantExtion.OPERATING.getIndex()).getMsg());
        }
        result.setSuccess(true);
        result.setMessage("操作成功！");
        return result;
    }

    // 验证至少三张图片 营业执照 法人身份证 门店照片 不满足条件result.success 设为true
    private Result validateMerchantImg(MerchantDTO merchantDTO) {
        Result result = new Result(false);
        // 至少三张图片 营业执照 法人身份证 门店照片
        List<Integer> imgTypes = merchantDTO.getMerchantImageDTOList().stream().map(MerchantImageDTO::getType)
                .collect(Collectors.toList());
        for (SPMEnum.merchantImageType merchantImageType : SPMEnum.merchantImageType.values()) {
            if (!imgTypes.contains(Integer.valueOf(merchantImageType.getIndex() + ""))) {
                result.setMessage(merchantImageType.getName() + "不能为空");
                result.setSuccess(true);
                break;
            }
        }
        return result;
    }

    private void merchantImgInit(MerchantImageDTO merchantImageDTO, Long merchantId, Date createTime, Long createId) {
        merchantImageDTO.setCreateTime(createTime);
        merchantImageDTO.setSort(0);
        merchantImageDTO.setCreaterId(createId);
        merchantImageDTO.setMerchantId(merchantId);
    }

    /**
     * 无限查询子级(只是这个类里面的方法调用)
     *
     * @param vo
     * @return List<AgentVO>
     */
    public List<AgentVO> returnList(AgentDTO vo) {
        List<AgentVO> list = new ArrayList<AgentVO>();
        List<AgentVO> pList = agentMapper.getAgentChildList(vo);
        while (pList.size() > 0) {
            list.addAll(pList);
            for (AgentVO agentVo : pList) {
                AgentDTO agent = new AgentDTO();
                agent.setParentId(agentVo.getId());
                agent.setStatus(agentStatus.ONE.getIndex());
                pList = agentMapper.getAgentChildList(agent);
            }
        }
        return list;
    }

    /**
     * 查询商户代理商
     *
     * @param dto
     * @return id
     * @author 何文浪
     * @时间 2017-6-5
     */
    public List<AgentVO> getALLAgentList(MerchantDTO dto) {
        List<AgentVO> listReturn = new ArrayList<AgentVO>();
        List<Long> ids = new ArrayList<Long>();
        ids.add(Long.valueOf(agentIsFrozen.ZERO.getIndex()));
        ids.add(Long.valueOf(agentIsFrozen.TWO.getIndex()));
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(dto.getId());
        List<MerchantVO> merchantList = merchantMapper.getMerchantList(merchantDTO);
        // 当id存在时
        if (merchantList != null && merchantList.size() > 0) {
            if (merchantList.get(0).getAgentId() > 0) {
                for (MerchantVO vo : merchantList) {
                    AgentDTO agentDTO = new AgentDTO();
                    agentDTO.setProvinceId(vo.getProvinceId());
                    agentDTO.setCityId(vo.getCityId());
                    agentDTO.setCountyId(vo.getCountyId());
                    agentDTO.setTownId(vo.getTownId());
                    agentDTO.setIsFrozens(ids);
                    agentDTO.setStatus(agentStatus.ONE.getIndex());
                    listReturn = agentMapper.getAgentList(agentDTO);// 查找当前代理商
                }
            }
        } else {// 当新增的时候
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setProvinceId(dto.getProvinceId());
            agentDTO.setCityId(dto.getCityId());
            agentDTO.setCountyId(dto.getCountyId());
            agentDTO.setTownId(dto.getTownId());
            agentDTO.setIsFrozens(ids);
            agentDTO.setStatus(agentStatus.ONE.getIndex());
            listReturn = agentMapper.getAgentList(agentDTO);// 查找当前代理商
        }
        return listReturn;
    }

    /**
     * 清空代理商余额
     *
     * @param userId
     * @return Integer
     * @author hewl
     */
    public Integer deleteUserBalance(Long userId) {
        return userBalanceMapper.delUserBalance(userId);
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     * @author 何文浪复制舒豪
     */
    public Result deleteUser(User user) {
        try {
            UserRole userRole = new UserRole();
            logger.info("删除用户入参,user={}", JSON.toJSONString(user));
            permissionCommonUserMapper.deleteByPrimaryKey(user.getId());
            userRole.setUserId(user.getId());
            userRoleMapper.delete(userRole);
            return ResultUtil.getResult(PermissionEnum.Code.SUCCESS);
        } catch (Exception ex) {
            logger.error("删除用户异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.DELETE_USER_ERROR);
        }
    }

    /**
     * 修改商户表 只修改不为null的字段. 单表操作,没有任何逻辑判断
     *
     * @param merchant
     * @return
     * @author: 李超
     * @date: 2017-07-07 09:45:26
     */
    @Override
    public Result updateMerchantSingle(Merchant merchant) {
        try {
            this.merchantMapper.updateByPrimaryKeySelective(merchant);
            merchant.setUpdateTime(new Date());
            Result result = new Result();
            result.setCode("200");
            result.setSuccess(true);
            result.setMessage("商户更新成功");
            return result;
        } catch (Exception ex) {
            logger.error("更新商户异常，ex={}", ex);
            throw new PermissionBizException(PermissionEnum.UPDATE_USER_ERROR);
        }
    }

    /***
     * 聊天的注册1@获取腾讯云的注册的密码和用户id和延签
     */
    public Map chatRegist(String phone) {

        Map map = new HashedMap();

        Map txMap = this.txRegist(phone);
        Integer errotCode = Integer.valueOf(txMap.get("errotCode").toString());
        String actionStatus = txMap.get("actionStatus").toString();
        String identifier = txMap.get("Identifier").toString();
        String password = txMap.get("Password").toString();

        if (errotCode == 0 && actionStatus.equals("OK")) {
            Tls_sigature tls_sigature = new Tls_sigature();
            String userSign = tls_sigature.getUserSign(identifier);
            map.put("IMdentifier", identifier);
            map.put("IMPassword", password);
            map.put("IMUserSign", userSign.trim());
            return map;
        } else if (errotCode == 70421 && actionStatus.equals("FAIL")) {
            return map;
        } else if (errotCode == 70402 && actionStatus.equals("FAIL")) {
            Map txMap2 = this.txRegist(phone + password);
            Integer errotCode2 = Integer.valueOf(txMap2.get("errotCode").toString());
            String actionStatus2 = txMap2.get("actionStatus").toString();
            String identifier2 = txMap2.get("Identifier").toString();
            String password2 = txMap2.get("Password").toString();

            if (errotCode2 == 0 && actionStatus2.equals("OK")) {
                Tls_sigature tls_sigature2 = new Tls_sigature();
                String userSign2 = tls_sigature2.getUserSign(identifier2);
                map.put("IMdentifier", identifier2);
                map.put("IMPassword", password2);
                map.put("IMUserSign", userSign2.trim());
                return map;
            } else if (errotCode2 == 70421 && actionStatus2.equals("FAIL")) {
                return map;
            }
        }

        return map;

    }

    /***
     * 聊天的注册2@获取腾讯云的注册的密码和用户id和延签
     */
    public Map txRegist(String phone) {
        String ident = null;

        ident = phone + "l";

        String url = "https://console.tim.qq.com/v4/registration_service/register_account_v1";
        // 签字
        String usersig = "eJxFkF1PgzAUhv8LtzPSUiqyxIvBSLaxzfpF1BvCaCEnSFu7OjaN-31IWLx9npy873t*nOf103WhNfC8sDkx3Jk6yLkasDhqMCIvKitMjzGl1EPoYg-C7EHJXngIU*wRhP4lcCEtVDAcFrwF2YxmD3WPNslLvHyYs9ksOtqt-ZibtRusVLKKszRTkGLGqoqlnyRRzKXWxd2y5gt47F4Xsa7vd*F39n5gW4m7JJLNTbm7TWnbaJ9O3ng02dxdwniTD*P*6vt9PRJ6IR6lhVYMsxChPiJBMPKiLNWXtLk9aTF84-cMzVtXdA__";
        // 注册点
        String spn = "1";
        ;
        // 管理员账号
        String identifier = "admink";
        // appId
        String sdkappid = "1400039291";
        // 返回的格式
        String contenttype = "json";
        Random randoms = new Random();
        // 获取随机纯数字32位
        String random = UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[a-zA-Z]",
                randoms.nextInt(10) + "");
        String uri = url + "?usersig=" + usersig + "&sdkappid=" + sdkappid + "&sdkappid=" + sdkappid + "&random="
                + random + "&identifier=" + identifier + "&contenttype=" + contenttype;
        // 注册密码
        String registerPass = random.substring(0, 10);
        // name.add(new BasicNameValuePair("",id));
        APIHttpClient ac = new APIHttpClient(uri);
        JSONObject j = new JSONObject();

        j.put("Identifier", ident.trim());
        j.put("IdentifierType", 3);
        j.put("Password", registerPass.trim());
        String cc = ac.contentTypeJsonPost(j.toString());
        logger.info("IM注册:" + cc);
        JSONObject code = JSONObject.parseObject(cc);
        // erroCode为0注册成功，errocode为70402用户已经注册成功，errocode时70421参数的格式不对
        Integer errotCode = (Integer) code.get("ErrorCode");
        // ActionStatus为FAIL注册失败，ActionStatus为OK时注册成功
        String actionStatus = (String) code.get("ActionStatus");
        Map map = new HashedMap();
        map.put("errotCode", errotCode);
        map.put("actionStatus", actionStatus);
        map.put("Identifier", ident.trim());
        map.put("Password", registerPass.trim());

        return map;

    }


    public Result UserMemeberNewRegisterCheckPhone(String phone, String password, String image) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        Result result = new Result();

        // 获取验证码的规范
        String codeType = "PHPF20170313";
        // 判断手机是否符合判断格式
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(phone);
        // 判断商户是否存在ph_merchant
        Long dataByphone = merchantMapper.getDataByphone(phone);

        // 若是不存在在判断alq_member是是否存在和这个手机号
        Long dataByphoneFromalq = merchantMapper.getDataByphoneFromalq(phone);

        // 判断注册的密码不能全是数字或者全是字符

        Pattern patternNum = Pattern.compile("[0-9]+"); // 判断是否为纯属数字

        Pattern patterStr = Pattern.compile("[a-zA-Z]+");// 判断是否为纯字母
        // 判断用户是否存在
        if (StringUtils.isEmptyOrWhitespaceOnly(phone)) {
            result.setCode("0");
            result.setMessage("手机号不能为空");
            return result;
        } else if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            result.setCode("0");
            result.setMessage("密码不能为空");
            return result;

        } else if (!m.matches()) {
            result.setCode("0");
            result.setMessage("不符合手机号格式");
            return result;

        } /*else if (null != dataByphone || null != dataByphoneFromalq) {
            result.setCode("0");
            result.setMessage("用户已经存在，请直接登录");
            return result;

        } */ else if (6 > password.length() || password.length() > 16) {
            result.setCode("0");
            result.setMessage("请输入的密码长度不要低于6高于16");
            return result;

        } /*else if (patternNum.matcher(password).matches() || patterStr.matcher(password).matches()) {
            result.setCode("0");
			result.setMessage("手机的密码不能纯数字或者纯字母");
			return result;
		}*/
        //2.过滤特殊字符
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].·<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p2 = Pattern.compile(regEx);
        Matcher m2 = p2.matcher(password);
        boolean find = m2.find();
        m2.replaceAll("").trim();
        if (find) {
            result.setCode("0");
            result.setMessage("密码格式不正确");
            return result;
        }
        //3.过滤表情符号
        password.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");

        result.setCode("1");
        result.setMessage("成功");

        return result;
    }

    @Transactional
    @Override
    public Result addMerchantByPhone(MerchantDTO merchantDTO) {
        Result result = new Result(false);
        // 验证参数
        try {
            String validateMessage = merchantDTO.validateForm();
            if (validateMessage != null) {
                result.setMessage(validateMessage);
            } else {
                // 验证创建人不能为空
                if (merchantDTO.getCreaterId() == null) {
                    result.setMessage("创建人不能为空");
                } else {
                    // 验证图片
                    Result validateMerchantImg = this.validateMerchantImg(merchantDTO);
                    PermissionCommonUser permissionCommonUser = new PermissionCommonUser();
                    if (validateMerchantImg.isSuccess()) {
                        return validateMerchantImg;
                    } else {
                        permissionCommonUser.setTelphone(merchantDTO.getTelPhone());
                        if (this.permissionCommonUserMapper.selectOne(permissionCommonUser) != null) {
                            result.setMessage("该电话号码已经被创建");
                        } else {
                            String password = ((new Random().nextDouble()) + "").substring(2, 8);
                            // 创建随机密码
                            SmsSendData sendData = new SmsSendData();
                            sendData.setMobile(permissionCommonUser.getTelphone());
                            sendData.setType(SmsCodeType.EXAMINE_PASS);// 代理商，商户，供应商重置密码
                            sendData.setSourceEnum(SmsSourceEnum.MERCHANT);// 枚举类型判断
                            sendData.setSmsCode(password);// 随机密码

                            Result smsResult = smsDataService.sendSmsHaveCode(sendData);
                            if (!smsResult.isSuccess()) {
                                logger.info("给用户发送短信密码失败:" + smsResult);
                                throw new MemberException("给用户发送短信密码失败");
                            }

                            // 保存到登录表
                            permissionCommonUser.basic(merchantDTO.getCreaterId());
                            permissionCommonUser.setUserName(merchantDTO.getMerchantName());
                            permissionCommonUser.setIsable(UserConstant.START);
                            permissionCommonUser.setPassword(MD5.getMD5Str(password));
                            this.permissionCommonUserMapper.insertUseGeneratedKeys(permissionCommonUser);

                            // ==============================alq_member===start===============================================
                            /*注册商户改造@维护商户表、原会员表 根据快火批发APP接口的需要，保存关联的alq_member表*/

                            Map<String, Object> mapDto = new HashMap<String, Object>();
                            //手机号
                            mapDto.put("phone", merchantDTO.getTelPhone());
                            //密码
                            // mapDto.put("password",MD5.getMD5Str("abc123456"));
                            mapDto.put("password", MD5.getMD5Str(password));
                            //创建的时间
                            mapDto.put("created_time", new Date());
                            /*token的处理
                            phone=telPhone,origin=KF,memberType=1;
							如果成功code为200*/
                            /*APIHttpClient ac = new APIHttpClient("192.168.0.91:3306/api/members/register");
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("phone", merchantDTO.getTelPhone());
							jsonObject.put("origin", "KF");
							jsonObject.put("memberType", 1);// 用户类型：0是普通会员，1是商户
							String cc = ac.contentTypeJsonPost(jsonObject.toString());
							logger.info("IM注册:" + cc);
							JSONObject code = JSONObject.parseObject(cc);
							if (code.get("code").equals("200")) {
								String data = (String) code.get("data");
								mapDto.put("token", data);
							} else {
								mapDto.put("token", null);
							}*/
                            mapDto.put("token", null);
                            // 获取腾讯云的注册的密码和用户id和延签
                            Map chatRegists = chatRegist(merchantDTO.getTelPhone());
                            if (chatRegists != null) {
                                mapDto.put("im_id", chatRegists.get("IMdentifier"));
                                mapDto.put("im_password", chatRegists.get("IMPassword"));
                                mapDto.put("im_sign", chatRegists.get("IMUserSign"));
                            }
                            //一共添加的到alq_member表里参数有phone,password,created_time,token,im_id,im_password,im_sign
                            merchantMapper.insertAlqMember(mapDto);
                            // ==============================alq_member===end===============================================
                            // 保存商户
                            Merchant merchant = VoDtoEntityUtil.convert(merchantDTO, Merchant.class);
                            merchant.setUserId(permissionCommonUser.getId());
                            // 代理id
                            AgentDTO parentAgent = new AgentDTO();
                            parentAgent.setTownId(merchant.getTownId());
                            parentAgent.setCountyId(merchant.getCountyId());
                            parentAgent.setCityId(merchant.getCityId());
                            List<AgentVO> _parentAgent = this.agentMapper.getAgentVoByPosition(parentAgent);
                            if (CollectionUtils.isEmpty(_parentAgent)) {
                                merchant.setAgentId(0L);
                            } else {
                                merchant.setAgentId(_parentAgent.get(0).getId());
                            }
                            merchant.basic(merchantDTO.getCreaterId());
                            // 审核通过
                            merchant.setStatus(SPMEnum.merchantStatus.ONE.getIndex());
                            // 未冻结
                            merchant.setIsFrozen(SPMEnum.merchantIsFrozen.ZERO.getIndex());
                            // 未删除
                            merchant.setIsDelete(SPMEnum.merchantIsDelete.ZERO.getIndex());
                            // ==============================ph_merchant===start===============================================
                            // 新增功能@维护激活码@获取随机纯数字6位
                            Random randoms = new Random();
                            String random = UUID.randomUUID().toString().replaceAll("-", "")
                                    .replaceAll("[a-zA-Z]", randoms.nextInt(10) + "").substring(0, 6);
                            merchant.setCdKey(random);
                            // ==============================ph_merchant===end===============================================
                            this.merchantMapper.insertUseGeneratedKeys(merchant);
                            // 保存商户图片
                            Date createTime = new Date();
                            for (MerchantImageDTO merchantImageDTO : merchantDTO.getMerchantImageDTOList()) {
                                this.merchantImgInit(merchantImageDTO, merchant.getId(), createTime,
                                        merchantDTO.getCreaterId());
                            }

                            List<MerchantImage> list = VoDtoEntityUtil
                                    .convertList(merchantDTO.getMerchantImageDTOList(), MerchantImage.class);
                            this.merchantImageMapper.insertList(list);
                            // 保存商户类别
                            List<MerchantMerchantType> merchantMerchantTypes = VoDtoEntityUtil.convertList(
                                    merchantDTO.getMerchantMerchantTypeDTOList(), MerchantMerchantType.class);
                            for (MerchantMerchantType merchantMerchantType : merchantMerchantTypes) {
                                merchantMerchantType.setMerchantId(merchant.getId());
                                merchantMerchantType.setCreaterId(merchantDTO.getCreaterId());
                                merchantMerchantType.setCreateTime(createTime);
                            }
                            this.merchantMerchantTypeMapper.insertList(merchantMerchantTypes);
                            // 余额初始化
                            UserBalance userBalance = new UserBalance();
                            userBalance.setScore(0L);
                            userBalance.setUserId(merchant.getUserId());
                            userBalance.setUserType(Integer.valueOf(RoleEnum.MERCHANT.getCode()));
                            userBalance.basic(merchant.getCreaterId());
                            this.userBalanceMapper.insert(userBalance);
                            // 权限初始化
                            UserRole userRole = new UserRole();
                            userRole.setRoleId(Long.valueOf(RoleEnum.MERCHANT.getCode()));
                            userRole.setUserId(merchant.getUserId());
                            userRole.basic(merchant.getCreaterId());
                            this.userRoleMapper.insert(userRole);

                            // 如果推广人是会员，并且推广的商户大于等于3，自动升级为推广师
                            if (merchantDTO.getPromoterId() != null) {
                                Result memberResult = iMemberService.getMemberInfoById(merchantDTO.getPromoterId());
                                if (memberResult.isSuccess()) {
                                    Member member = (Member) memberResult.getData();
                                    boolean isMarketing = member.getIsMarketing()
                                            .equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode());
                                    if (!isMarketing) {
                                        int proNum = merchantMapper
                                                .verifyMemberProMerchantNum(merchantDTO.getPromoterId());
                                        if (proNum >= 3) {
                                            MemberPromotionDTO dto = new MemberPromotionDTO();
                                            dto.setMemberId(merchantDTO.getPromoterId());
                                            dto.setCreaterId(merchantDTO.getCreaterId());
                                            Result memberResulto = iMemberService.upgradeMemberPromotionAuto(dto);
                                            if (!memberResulto.isSuccess()) {
                                                logger.error("会员推广3个商户自动升级为推广师错误");
                                                return memberResulto;
                                            }
                                        }
                                    }
                                }
                            }

                            result.setData(permissionCommonUser.getId());
                            result.setCode("200");
                            result.setMessage("商户保存成功");
                            result.setSuccess(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("增加商户异常", e);
            throw e;
        }
        return result;
    }

    /**
     * 代理后台商户置顶
     */
    @Transactional
    @Override
    public Result updateIsRecommend(Integer isRecommend, String telPhone, Byte isRecommendType) {
        try {
            if (isRecommend == null && telPhone == null) {
                return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
            }
            if (isRecommendType == 1) {
                merchantMapper.updateIsRecommendAdmin(isRecommend, telPhone, isRecommendType);
            } else if (isRecommendType == 3) {
                merchantMapper.updateIsRecommend(isRecommend, telPhone, isRecommendType);
            }
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商户置顶异常");
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        }
    }

    /**
     * 获取商户基本信息  更新信息
     */
    @Transactional
    @Override
    public Result updateMerchantInfo(MerchantDTO mer) {
        //获取行业分类信息
        List<String> types = merchantMapper.getMerchantTypeById(mer.getId());

        //获取基本信息
        MerchantDTO merchant = merchantMapper.getMerchantById(mer);
        if (types != null && !types.isEmpty()) {
            merchant.setMerchantType(types);//将行业分类存进实体
        }
        //查询userid
        Map<String,Object> userid=merchantMapper.getUserId(mer.getId());
        Map map1 = new HashMap();
        //根据userid查询
        Map map = storeManagerMapper.getStoreManagerById(Long.parseLong(userid.get("userId").toString()));

        //获取商户图片
        MerchantImageDTO img = new MerchantImageDTO();
        img.setMerchantId(mer.getId());
        for (int i = 1; i <= 4; i++) {
            img.setType(i);
            List<MerchantImageDTO> merchantImgs = merchantMapper.getmerchantImg(img);
            if (!merchantImgs.isEmpty()) {
                // 1 营业执照图片 2 身份证图片 3 门店照片 4 其他
                if (i == 1) {
                    merchant.setYingye(merchantImgs);
                } else if (i == 2) {
                    merchant.setIdcard(merchantImgs);
                } else if (i == 3) {
                    merchant.setMendian(merchantImgs);
                } else {
                    merchant.setOther(merchantImgs);
                }
            }
        }
        map1.put("storemanager", map);


        if (merchant == null) {
            return ResultUtil.getResult(RespCode.Code.NO_ORDER,map1);
        }

        map1.put("merchant", merchant);
        return ResultUtil.getResult(RespCode.Code.SUCCESS, map1);
    }

    /**
     * 修改商户信息
     */
    @Transactional
    @Override
    public Result updateMerchantInfoById(String openBeginTime, String openEndTime, String closeBeginTime, String closeEndTime, Long costMoney, Long merchantId) {
        Date closeBeginTime1 = null;
        Date closeEndTime1 = null;
        try {
            if (openBeginTime == null || "".equals(openBeginTime)) {
                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            } else if (openEndTime == null || "".equals(openEndTime)) {
                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            } else if (closeBeginTime == null || "".equals(closeBeginTime)) {
                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            } else if (closeEndTime == null || "".equals(closeEndTime)) {

                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            } else if (costMoney == null || "".equals(costMoney)) {
                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            } else if (merchantId == null || "".equals(merchantId)) {
                return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
            }

            ////判断非营业时间不为空 转换时间类型

            closeBeginTime1 = DateUtil.parseDateTime(closeBeginTime);
            closeEndTime1 = DateUtil.parseDateTime(closeEndTime);
            //判断非营业开始时间小于结束时间
            if (DateUtil.isCompareTime(closeEndTime1, closeBeginTime1)) {
                return ResultUtil.getResult(RespCode.Code.CLOSE_TIME_ERROR);
            }
            //判断营业时间开始时间小于结束时间
            long beginTime = Long.valueOf(openBeginTime.replaceAll("[-\\s:]", ""));
            long endTime = Long.valueOf(openEndTime.replaceAll("[-\\s:]", ""));
            if (beginTime > endTime) {
                return ResultUtil.getResult(RespCode.Code.CLOSE_TIME_ERROR);
            }
            //修改商户信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("openBeginTime", openBeginTime);
            map.put("openEndTime", openEndTime);
            map.put("closeBeginTime", closeBeginTime1);
            map.put("closeEndTime", closeEndTime1);
            map.put("costMoney", costMoney);
            map.put("id", merchantId);
            merchantMapper.updateMerchantInfoById(map);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(RespCode.Code.FAIL);
        }

        return ResultUtil.getResult(RespCode.Code.SUCCESS);
    }
/**
 * 验证商户验证码
 */

    /**
     * 验证商户验证码
     */
    @Override
    @Transactional
    public Result provCodeByMerchanrId(String cdKey, Long merchantId) {
        try {
            //判断有没有被激活
            Merchant isCdKey = merchantMapper.getmerchantisCdKey(merchantId);
            if (isCdKey == null || isCdKey.getIsCdKey() != 1) {
                //进行验证
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cdKey", cdKey);
                map.put("id", merchantId);

                Merchant merchant = merchantMapper.provCodeByMerchanrId(map);
                //判断不为空
                if (merchant == null || "".equals(merchant)) {
                    return ResultUtil.getResult(RespCode.Code.NO_CDKEY);
                }
                //验证通过 修改商户jihuo状态
                merchantMapper.updateCdkey(map);
                return ResultUtil.getResult(RespCode.Code.SUCCESS);
            }
            if (isCdKey.getIsCdKey() == 1) {
                return ResultUtil.getResult(RespCode.Code.OVER_CDKEY);
            }

        } catch (Exception e) {

        }
        return ResultUtil.getResult(RespCode.Code.FAIL);

    }

    @Override
    public String getMerchantListByPhone(String phone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long CheckPhone(String phone) {
        // TODO Auto-generated method stub
        Long id = merchantMapper.getDataByphone(phone);
        return id;
    }

    @Override
    public Map getMemberAndMerchant(String phone) {
        return merchantMapper.getMemberAndMerchant(phone);
    }

    @Override
    @Transactional
    public Result StroeManager(String phone, String name, Long merchantId) {
        //手机格式验证
        if (org.apache.commons.lang3.StringUtils.isBlank(phone)) {
            return new Result(false, RespCode.Code.REQUEST_DATA_ERROR.getCode(), "[会员手机号]不能为空");
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            return ResultUtil.getResult(RespCode.Code.ERROER_PHONE);
        }

        //昵称不能为空
        if (name == null || "".equals(name)) {
            return ResultUtil.getResult(RespCode.Code.NOTEMPTY_NIKENAME);
        }
        //判断店面经理是否是业务员
        Map markingMap = iMemberService.getMarkindByPhone(phone);
        if (markingMap == null) {
            return ResultUtil.getResult(RespCode.Code.IS_NOTMEMBER);
        } else if (markingMap.get("isMarketing").toString().equals("1")) {
            return ResultUtil.getResult(RespCode.Code.IS_SALESMAN);
        }
        //店面经理不能是商户
        List<Map> merchantMap = this.getMerchantMsgByPhone(phone);
        if (merchantMap.size() > 0) {
            return ResultUtil.getResult(RespCode.Code.IS_MERCHANT);
        }
        //店面经理只能与一家商户绑定
        Map map = merchantMapper.getStroeManagerMsg(phone);
        if (map != null) {
            return ResultUtil.getResult(RespCode.Code.IS_BINGINDG_MERCHANT);
        }

        Map<String,Object> userid=merchantMapper.getUserId(merchantId);

        StoreManagerRecord st = new StoreManagerRecord();
        st.setMemberName(name);//经理
        st.setTelPhone(phone);//经理手机号
        st.setUserId(Long.parseLong(userid.get("userId").toString()));//关联的商户userid
        st.setStatus((byte) 0);//在职
        //st.setCertification((byte) 0);//审核中
        /********************@wudi***************************/
        st.setCertification((byte) 1);//认证 0审核中 1已审核 2审核失败
        st.setCreateTime(new Date());
        st.setCreaterId((long) 0);
        this.storeManagerMapper.insert(st);

        return ResultUtil.getResult(RespCode.Code.SUCCESS);
    }

    @Override
    public void updateStoreManager(Long memberid, Long merchantid) {
        Map<String,Object> userid=merchantMapper.getUserId(merchantid);
        merchantMapper.updateStoreManager(memberid,Long.parseLong(userid.get("userId").toString()));
    }


    @Override
    public Result addDissipate(MerchanTimeSectionVO merchantTimeSection) {
        try {
            if (merchantTimeSection == null) {
                return ResultUtil.getResult(RespCode.Code.CHOOSE_CONSUMPTION_TIME);
            }
            merchantMapper.delDissipateTime(merchantTimeSection.getMerchantId());
            String[] startTime = merchantTimeSection.getStartTime().split(",");
            String[] endTime = merchantTimeSection.getEndTime().split(",");
            if (startTime.length != 0 && endTime.length != 0) {
                if (startTime.length == endTime.length) {
                    for (int i = 0; i < startTime.length; i++) {
                        merchantMapper.addDissipateStartTime(startTime[i], endTime[i], merchantTimeSection.getMerchantId());
                    }
                    return ResultUtil.getResult(RespCode.Code.SUCCESS);
                }
            }

            return ResultUtil.getResult(RespCode.Code.CHOOSE_CONSUMPTION_TIME);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(RespCode.Code.FAIL);
        }

    }

    @Override
    public Result findDissipate(Long merchantId) {
        try {
            if (merchantId == null) {
                return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
            }
            List<MerchanTimeSectionVO> listDissipate = merchantMapper.findDissipateList(merchantId);
            return ResultUtil.getResult(RespCode.Code.SUCCESS, listDissipate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(RespCode.Code.FAIL);
        }

    }

    @Override
    public Result delDissipate(Long id) {
        try {
            merchantMapper.delDissipate(id);
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<Map> getMerchantMsgByPhone(String phone) {
        return merchantMapper.getMerchantMsgByPhone(phone);
    }

    @Override
    public Long getPhoneByPermission(String phone) {

        Long a = merchantMapper.getPhoneByPermission(phone);

        Long b = merchantMapper.getPhoneByAlqMember(phone);

        return a+b;
    }
}
