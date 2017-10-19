package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.customenum.OuterResultEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.smsCode.SmsCodeUtil;
import com.ph.shopping.facade.mapper.IcCardInfoMapper;
import com.ph.shopping.facade.mapper.IcMemberBindMapper;
import com.ph.shopping.facade.mapper.MemberIntegralMapper;
import com.ph.shopping.facade.mapper.MemberMapper;
import com.ph.shopping.facade.mapper.MemberShareRecordMapper;
import com.ph.shopping.facade.member.dto.MemberAddDTO;
import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
import com.ph.shopping.facade.member.dto.MemberIntegralDTO;
import com.ph.shopping.facade.member.entity.IcCardInfo;
import com.ph.shopping.facade.member.entity.IcMemberBind;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.entity.MemberShareRecord;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.certificatesauth.MemberCertificationEnum;
import com.ph.shopping.facade.member.menum.member.MemberIsMarketingEnum;
import com.ph.shopping.facade.member.menum.member.MemberLevelEnum;
import com.ph.shopping.facade.member.menum.member.MemberShareTypeEnum;
import com.ph.shopping.facade.member.menum.member.MemberStatusEnum;
import com.ph.shopping.facade.member.menum.member.MemberTypeEnum;
import com.ph.shopping.facade.member.menum.membercard.MemberCardBindStatusEnum;
import com.ph.shopping.facade.member.menum.membercard.MemberCardStatusEnum;
import com.ph.shopping.facade.member.menum.memberscore.MemberScoreFrozenEnum;
import com.ph.shopping.facade.member.service.IMemberCardService;
import com.ph.shopping.facade.member.service.user.LoginRegisterCheckService;
import com.ph.shopping.facade.member.service.user.response.CheckResponse;
import com.ph.shopping.facade.member.service.user.response.Content;
import com.ph.shopping.facade.member.vo.MemberCardInfoVO;
import com.ph.shopping.util.DubboResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName: MemberCardService
 *
 * @Description:会员卡服务
 *
 * @author:Mr.Shu
 *
 * @date: 2017年5月24日 上午9:06:18
 *
 * @Copyright: 2017/5/24
 */
@Component
@Service(version = "1.0.0")
public class MemberCardServiceImpl implements IMemberCardService {

	private static final Logger logger = LoggerFactory.getLogger(MemberCardServiceImpl.class);

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private IcCardInfoMapper icCardInfoMapper;

	@Autowired
	private IcMemberBindMapper icMemberBindMapper;

	@Autowired
	private MemberIntegralMapper memberIntegralMapper;

	/**
	 * 短信发送
	 */
	@Autowired
	private ISmsDataService smsDataService;
	/**
	 * 登录注册校验服务
	 */
	@Autowired
	private LoginRegisterCheckService checkService;

	// 会员分享mapper
    @Autowired
    private MemberShareRecordMapper shareRecordMapper;
    
	//============================================会员卡信息表操作===================================================/
    @Override
	public Result getMemberCardListByPage(PageBean pageBean, MemberCardInfoDTO memberCardInfoDTO) {
		logger.info("加载会员卡列表参数,MemberIcCardInfoPageDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<MemberCardInfoVO> list = icCardInfoMapper.getMemberCardList(memberCardInfoDTO);
		PageInfo<MemberCardInfoVO> pageInfo = new PageInfo<MemberCardInfoVO>(list);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

	@Override
	@Transactional
	public Result addMemberCard(IcCardInfo icInfo) {
		logger.info("添加会员卡参数,IcCardInfo = {} ", JSON.toJSONString(icInfo));
		try {
			//1.验证条形码参数是否为空
			if (icInfo.getOuterCode() == null || "".equals(icInfo.getOuterCode().toString().trim())) {
				return ResultUtil.setResult(false, "条形码不能为空");
			}

			//2.验证IC卡号参数是否为空
			if (icInfo.getInnerCode() == null || "".equals(icInfo.getInnerCode().toString().trim())) {
				return ResultUtil.setResult(false, "IC卡号不能为空");
			}

			//分条件判断会员卡是否存在
			IcCardInfo icCardInfoCondition = new IcCardInfo();
			//条形码
			icCardInfoCondition.setOuterCode(icInfo.getOuterCode());
			IcCardInfo out = icCardInfoMapper.selectOne(icCardInfoCondition);
			if (out != null) {
				return ResultUtil.getResult(MemberResultEnum.SHAPE_CODE_EXIST);
			}
			//IC卡号
			icCardInfoCondition.setOuterCode(null);
			icCardInfoCondition.setInnerCode(icInfo.getInnerCode());
			IcCardInfo in = icCardInfoMapper.selectOne(icCardInfoCondition);
			if (in != null) {
				return ResultUtil.getResult(MemberResultEnum.IC_CODE_EXIST);
			}
            icInfo.setIsDelete(MemberCardStatusEnum.CARD_NORMAL.getCode());
            icCardInfoMapper.insert(icInfo);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("添加会员卡信息错误", e);
			throw new MemberException(MemberResultEnum.ADD_MEMBER_CARD_ERROR);
		}
	}

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Result batchAddMemberCard(List<IcCardInfo> list) {
        logger.info("批量添加会员卡参数,IcCardInfo = {} ", JSON.toJSONString(list));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		try {
            Map<String, Object> ois = getOuterAndInner(list);
            if (ois != null && !ois.isEmpty()) {
                List<IcCardInfo> backList = ContainerUtil.lList();
                List<String> iCodes = (List<String>) ois.get("innerCodes");
                List<String> oCodes = (List<String>) ois.get("outerCodes");
                List<IcCardInfo> repeatCodes = (List<IcCardInfo>) ois.get("repeatCodes");
                if (ParamVerifyUtil.objIsNotNull(repeatCodes)) {
                    backList.addAll(repeatCodes);
                }
                Map<String, List<IcCardInfo>> retMap = null;
                if (ParamVerifyUtil.objIsNotNull(iCodes)) {
                    List<String> innerCodes = icCardInfoMapper.selectIcinnerCodeByInnerCodes(iCodes);
                    retMap = getIcCradindInfoDTOs(innerCodes, list, "innerCode");
                    if (retMap != null) {
                        list = retMap.get("noExists");
                        List<IcCardInfo> existList = retMap.get("exists");
                        if (ParamVerifyUtil.objIsNotNull(existList)) {
                            backList.addAll(existList);
                        }
                    }
                }
                if (ParamVerifyUtil.objIsNotNull(oCodes)) {
                    List<String> outerCodes = icCardInfoMapper.selectIcOuterCodeByOuterCodes(iCodes);
                    retMap = getIcCradindInfoDTOs(outerCodes, list, "outerCode");
                    if (retMap != null) {
                        list = retMap.get("noExists");
                        List<IcCardInfo> existList = retMap.get("exists");
                        if (ParamVerifyUtil.objIsNotNull(existList)) {
                            backList.addAll(existList);
                        }
                    }
                }
                // 执行批量新增操作
                if (ParamVerifyUtil.objIsNotNull(list)) {
                    if (batchInsertCard(list)) {
						return ResultUtil.getResult(RespCode.Code.SUCCESS, backList);
					}
                } else {
					return ResultUtil.getResult(MemberResultEnum.IC_CARD_EXIST);
				}
            }
        } catch (Exception e) {
            logger.error("批量添加会员卡错误", e);
			throw new MemberException(MemberResultEnum.ADD_ALL_MEMBER_CARD_ERROR);
		}
        return result;
    }

    @Override
	public Result getMemberBindCardInfoByCondition(MemberCardInfoDTO memberCardInfoDTO) {
        List<MemberCardInfoVO> memberCardInfoVOS = icMemberBindMapper.getMemberBindCardList(memberCardInfoDTO);
        if (memberCardInfoVOS != null && memberCardInfoVOS.size() != 0) {
            return ResultUtil.getResult(RespCode.Code.SUCCESS, memberCardInfoVOS.get(0));
        } else {
            return ResultUtil.getResult(RespCode.Code.SUCCESS, null);
        }


	}


//==============================================会员卡绑定关系操作====================================================/

    @Override
    public Result getMemberCardSendListByPage(PageBean pageBean, MemberCardInfoDTO dto) {
        logger.info("根据分页信息获取会员绑定的会员卡列表,MemberIcCardInfoPageDTO = {} ", JSON.toJSONString(dto));
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<MemberCardInfoVO> list = icMemberBindMapper.getMemberBindCardList(dto);
		PageInfo<MemberCardInfoVO> pageInfo = new PageInfo<MemberCardInfoVO>(list);
        return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

	@Override
	@Transactional
	public Result bindMemberCard(MemberCardInfoDTO memberCardInfoDTO) {
		logger.info("会员绑定会员卡参数,memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
        try {
            //1.验证会员账号参数是否为空
            if (memberCardInfoDTO.getTelPhone() == null || "".equals(memberCardInfoDTO.getTelPhone().toString().trim())) {
                return ResultUtil.setResult(false, "会员账号不能为空");
            }

            //2.验证商户Id参数是否为空
            if (memberCardInfoDTO.getMerchantId() == null || "".equals(memberCardInfoDTO.getMerchantId().toString().trim())) {
                return ResultUtil.setResult(false, "商户Id不能为空");
            }

            //获取参数信息
            IcCardInfo icCardInfoCondition = new IcCardInfo();
            icCardInfoCondition.setOuterCode(memberCardInfoDTO.getOuterCode());
            icCardInfoCondition.setInnerCode(memberCardInfoDTO.getInnerCode());

            //判断会员卡
            IcCardInfo info = icCardInfoMapper.selectOne(icCardInfoCondition);
            if (info == null) {
                return ResultUtil.setResult(result, MemberResultEnum.IC_CARD_INEXISTENCE);
            } else if (MemberCardStatusEnum.CARD_FROZEN.getCode() == info.getIsDelete()) {
            	return ResultUtil.setResult(result, MemberResultEnum.MEMBER_CARD_FROZEN);
            } else if (verifyMemberCardIsBind(memberCardInfoDTO)) {
            	return ResultUtil.setResult(result, MemberResultEnum.IC_CARD_ALREADYBIND);
            }

            //获取绑定的会员信息
            Member member = memberMapper.selectMemberInfoByMobile(memberCardInfoDTO.getTelPhone());
            logger.info("绑定会员卡的会员数据：Member = {}", JSON.toJSONString(member));
            String pwd = null;
            boolean flag = false;
            if (flag = (member != null && StringUtils.isNotBlank(member.getTelPhone()))) {
                Byte status = member.getStatus();
                if (MemberStatusEnum.MEMBER_FROZEN.getCode().equals(status)) {
                    result.setMessage("当前会员处于冻结状态");
                    result.setCode(MemberResultEnum.MEMBER_FROZEN.getCode());
                    return result;
                }
            } else {
                // 判断北京方是否已存在会员数据，存在则直接返回成功
                member = backOuterData(memberCardInfoDTO.getTelPhone(),memberCardInfoDTO.getCreaterId(), null);

                flag = (member != null && StringUtils.isNotBlank(member.getTelPhone()));
                if (!flag) {// true 表示北京方已存在会员数据 false 本地做处理

                    MemberAddDTO memberRegisterDTO = getRegisterMemberDTO(memberCardInfoDTO);
                    result = addMember(memberRegisterDTO);
                    if (result == null || !RespCode.Code.SUCCESS.getCode().equals(result.getCode())) {
                        throw new MemberException("新增会员数据失败");
                    }
                    member = memberMapper.selectMemberInfoByMobile(memberCardInfoDTO.getTelPhone());
                    pwd = result.getData().toString();

					/*result = getMemberByPhone(memberCardInfoDTO);
					if (result != null && MemberResultEnum.SUCCESS.getCode().equals(result.getCode())) {
						Object data = result.getData();
						if (data instanceof Map) {
							@SuppressWarnings({"unchecked", "rawtypes"})
							Map<String, Object> retMap = (Map) result.getData();
							Object mobj = retMap.get("member");
							if (mobj instanceof Member) {
								member = (Member) mobj;
							}
							Object pwdobj = retMap.get("password");
							if (pwdobj != null) {
								pwd = pwdobj.toString();
							}
						}
					}*/
                }
            }
            if (member == null || StringUtils.isBlank(member.getTelPhone())) {
                throw new MemberException("未获取到会员相关数据，不能进行绑卡操作");
            }

            //丰富dto数据
            memberCardInfoDTO.setIcCardId(info.getId());
            memberCardInfoDTO.setMemberId(member.getId());
            result = bindMemberCardByInfo(memberCardInfoDTO);
            if (result != null && RespCode.Code.SUCCESS.getCode().equals(result.getCode())) {
                // 绑定会员卡成功给用户发送短信 ，flag 为true 时表示数据已存在 不需要发送短信密码
                if (!flag) {
                    if (sendSmsPwdCode(member.getTelPhone(), pwd)) {
                        return ResultUtil.getResult(RespCode.Code.SUCCESS);
                    }
                }
            }
        } catch (BizException e) {
            logger.error("调用北京注册校验接口失败", e);
            throw new MemberException("系统繁忙，请重试");
        } catch (Exception e) {
			logger.error("绑定会员卡信息错误", e);
			throw new MemberException(MemberResultEnum.BIND_MEMBER_CARD_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
    public Result lossMemberCard(MemberCardInfoDTO memberCardInfoDTO) {
        logger.info("挂失会员卡参数,memberCardInfoDTO = {} ", JSON.toJSONString(memberCardInfoDTO));
        try {
			//1.验证会员账号参数是否为空
			if (memberCardInfoDTO.getTelPhone() == null || "".equals(memberCardInfoDTO.getTelPhone().toString().trim())) {
				return ResultUtil.setResult(false, "会员账号不能为空");
			}
			//2.验证修改人Id参数是否为空
			if (memberCardInfoDTO.getUpdaterId() == null || "".equals(memberCardInfoDTO.getUpdaterId().toString().trim())) {
				return ResultUtil.setResult(false, "修改人Id不能为空");
			}

			String telPhone = memberCardInfoDTO.getTelPhone();
			Member member = memberMapper.selectMemberInfoByMobile(telPhone);
			if (member == null || member.getId() == null) {
				logger.info("根据手机号：" + telPhone + " 没有找到相关会员消息");
                return ResultUtil.getResult(MemberResultEnum.MEMBERUSER_NON_EXISTENT);
            } else {
				Byte status = member.getStatus();
                if (MemberStatusEnum.MEMBER_FROZEN.getCode().equals(status)) {
                    return ResultUtil.getResult(MemberResultEnum.MEMBER_FROZEN);
                }
				IcMemberBind icMemberBind = new IcMemberBind();
				icMemberBind.setMemberId(member.getId());
				icMemberBind.setStatus(MemberCardBindStatusEnum.MEMBERCARD_BINDING.getCode());
				icMemberBind = icMemberBindMapper.selectOne(icMemberBind);
				if (icMemberBind != null && icMemberBind.getId() != null) {
					// 挂失会员卡
                    icMemberBind.setUpdaterId(memberCardInfoDTO.getUpdaterId());
                    icMemberBind.setStatus(MemberCardBindStatusEnum.MEMBERCARD_UNBINDING.getCode());
                    icMemberBind.setReportTime(new Date());
                    icMemberBind.setUpdateTime(new Date());
                    icMemberBindMapper.updateByPrimaryKeySelective(icMemberBind);
                    return ResultUtil.getResult(RespCode.Code.SUCCESS);
                } else {
					logger.info("根据手机号：" + telPhone + " 没有找到会员绑定的会员卡消息");
                    return ResultUtil.getResult(MemberResultEnum.MEMBER_NO_DATA);
                }
			}
		} catch (Exception e) {
			logger.error("挂失会员卡错误", e);
			throw new MemberException(MemberResultEnum.LOSS_MEMBER_CARD_ERROR);
		}
	}


	@Override
	@Transactional
	public Result frozenMemberCard(MemberCardInfoDTO dto) {
		logger.info("冻结会员卡参数,MemberCardInfoDTO = {} ", JSON.toJSONString(dto));
        try {
			//1.验证会员卡Id参数是否为空
			if (dto.getIcCardId() == null || "".equals(dto.getIcCardId().toString().trim())) {
				return ResultUtil.setResult(false, "会员卡id(icCardId)不能为空");
			}
			IcCardInfo icCardInfo = new IcCardInfo();
			icCardInfo.setId(dto.getIcCardId());
            icCardInfo.setIsDelete(MemberCardStatusEnum.CARD_FROZEN.getCode());
            icCardInfo.setUpdateTime(new Date());
            icCardInfo.setUpdaterId(dto.getUpdaterId());
            // 冻结会员卡
			icCardInfoMapper.updateByPrimaryKeySelective(icCardInfo);
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
			logger.error("冻结会员卡错误", e);
			throw new MemberException(MemberResultEnum.FROZEN_MEMBER_CARD_ERROR);
		}
	}

	@Override
	@Transactional
	public Result relieveMemberCard(MemberCardInfoDTO dto) {
		logger.info("解冻会员卡参数,MemberCardInfoDTO = {} ", JSON.toJSONString(dto));
        try {

			//1.验证icCardId参数是否为空
			if (dto.getIcCardId() == null || "".equals(dto.getIcCardId().toString().trim())) {
				return ResultUtil.setResult(false, "会员卡id(icCardId)不能为空");
			}
			//2.解冻
			IcCardInfo icCardInfo = new IcCardInfo();
			icCardInfo.setId(dto.getIcCardId());
            icCardInfo.setIsDelete(MemberCardStatusEnum.CARD_NORMAL.getCode());
            icCardInfo.setUpdateTime(new Date());
            icCardInfo.setUpdaterId(dto.getUpdaterId());
            // 解冻会员卡
			icCardInfoMapper.updateByPrimaryKeySelective(icCardInfo);
            return ResultUtil.getResult(RespCode.Code.SUCCESS);
        } catch (Exception e) {
			logger.error("解冻会员卡错误", e);
			throw new MemberException(MemberResultEnum.START_MEMBER_CARD_ERROR);
		}
	}

	@Override
	public Result judgeMemberCard(MemberCardInfoDTO dto) {
		IcCardInfo icCardInfo = new IcCardInfo();
		icCardInfo.setInnerCode(dto.getInnerCode());
		icCardInfo.setOuterCode(dto.getOuterCode());
		icCardInfo.setId(dto.getId());
		IcCardInfo icf = icCardInfoMapper.selectOne(icCardInfo);
		if (icf != null) {
			if (MemberCardStatusEnum.CARD_FROZEN.getCode() == icf.getIsDelete()) {
				return ResultUtil.getResult(MemberResultEnum.MEMBER_CARD_FROZEN);
			}
			Result result = this.getMemberBindCardInfoByCondition(dto);
			if (result != null && null != result.getData()) {
				MemberCardInfoVO memberCardInfoVO = (MemberCardInfoVO) result.getData();
				if (MemberCardBindStatusEnum.MEMBERCARD_UNBINDING.getCode() == memberCardInfoVO.getStatus()) {
					return ResultUtil.getResult(MemberResultEnum.MEMBER_CARD_LOSS);
				}
				return ResultUtil.getResult(RespCode.Code.SUCCESS, memberCardInfoVO);
			}
			return ResultUtil.getResult(MemberResultEnum.NO_MEMBERCARD_EMPTY);
		} else {
			return ResultUtil.getResult(MemberResultEnum.NO_MEMBERCARD_EMPTY);
		}

	}


//===================================================私有通用方法======================================================/

    /**
     * @描述：判断会员卡是否被绑定 true 为已绑定
     * @param: memberCardInfoDTO
     * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/24 10:23
     */
    private boolean verifyMemberCardIsBind(MemberCardInfoDTO memberCardInfoDTO) {
        MemberCardInfoDTO dto = new MemberCardInfoDTO();
        dto.setInnerCode(memberCardInfoDTO.getInnerCode());
        dto.setOuterCode(memberCardInfoDTO.getOuterCode());
        dto.setStatus(MemberCardBindStatusEnum.MEMBERCARD_BINDING.getCode());//已绑定
        int num = icMemberBindMapper.getBindMemberCardByCodeAndStatus(dto);
        return num > 0;
    }

    /**
     * @描述：绑定会员卡信息
     * @param: memberCardInfoDTO
     * @return:
	 * @作者： Mr.Shu
	 * @创建时间：2017/5/24 10:51
     */
    private Result bindMemberCardByInfo(MemberCardInfoDTO memberCardInfoDTO) {
        Result result = ResultUtil.getResult(MemberResultEnum.MEMBER_FAILURE);
        IcMemberBind icMemberBind = new IcMemberBind();
        icMemberBind.setMemberId(memberCardInfoDTO.getMemberId());
		icMemberBind.setStatus(MemberCardBindStatusEnum.MEMBERCARD_BINDING.getCode());
		icMemberBind = icMemberBindMapper.selectOne(icMemberBind);
        if (icMemberBind != null && icMemberBind.getId() != null) {
        	ResultUtil.setResult(result, MemberResultEnum.IC_CARD_ALREADYBIND);
        } else {
            // 绑定会员卡操作
            if (insertBindIcCard(memberCardInfoDTO)) {
                return ResultUtil.getResult(RespCode.Code.SUCCESS);
            }
        }
		return result;
	}

    /**
     * @描述：insert绑定会员卡表基础数据
     *
     * @param: memberCardInfoDTO
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/24 11:02
     */
	@Transactional
    private boolean insertBindIcCard(MemberCardInfoDTO memberCardInfoDTO) {
        IcMemberBind memberBind = new IcMemberBind();
        memberBind.setCreateTime(new Date());
        memberBind.setUpdateTime(new Date());
        memberBind.setIcCardId(memberCardInfoDTO.getIcCardId());
        memberBind.setMemberId(memberCardInfoDTO.getMemberId());
        memberBind.setMerchantId(memberCardInfoDTO.getMerchantId());
        memberBind.setStatus(MemberCardBindStatusEnum.MEMBERCARD_BINDING.getCode());
        memberBind.setCreaterId(memberCardInfoDTO.getCreaterId());
        icMemberBindMapper.insert(memberBind);
        return true;
    }

	/**
	 * @param @param  phone
	 * @param @return 参数
	 * @return Member    返回类型
	 * @throws
	 * @Title: backOuterData
     * @Description:处理同步北京调用方的会员数据(没同步密码)
     */
	private Member backOuterData(String phone,Long createId, Byte level) {
		Member member = null;
		CheckResponse response = checkService.registerCheck(phone);
		if (response != null && OuterResultEnum.SUCCESS.getCode().equals(response.getCode())) {
			Content content = response.getContent();
			if (content != null && StringUtils.isNotBlank(content.getPhone())) {
				member = new Member();
				member.setCreateTime(new Date());
				member.setIdCardNo(content.getCard());
				member.setMemberName(content.getName());
				member.setTelPhone(content.getPhone());
                member.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());
				member.setType(MemberTypeEnum.HUNTER_TYPE.getCode());//从北京方验证的，会员类型为：1批发商
                member.setCertification(MemberCertificationEnum.NOT_CERTIFIED.getCode());//未实名认证
				if (level == null) {
                    member.setLevel(MemberLevelEnum.MEMBER_GENERAL.getCode());
                } else {
					member.setLevel(level);
				}
				memberMapper.insert(member);
				Long memberId = member.getId();
				// 向会员积分表新增数据
				boolean flag = insertIntoScoreData(memberId,createId);
				if (!flag) {
					throw new MemberException("新增会员时向会员积分表新增数据失败");
				}
			}
		}
		return member;
	}

	/**
	 * @param @param  phone
	 * @param @param  pwd
	 * @param @return 参数
	 * @return boolean    返回类型
	 * @throws
	 * @Title: sendSmsPwdCode
	 * @Description: 发送短信
	 */
	private boolean sendSmsPwdCode(String phone, String pwd) {
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(phone);
		sendData.setType(SmsCodeType.MEMBER_REGISTER_PD);
		sendData.setSmsCode(pwd);
		sendData.setSourceEnum(SmsSourceEnum.MEMBER);
		Result result = smsDataService.sendSmsHaveCode(sendData);
		if (result == null || !RespCode.Code.SUCCESS.getCode().equals(result.getCode())) {
			logger.info("给用户发送短信密码失败");
			throw new MemberException("给用户发送短信密码失败");
		}
		return true;
	}

	@Transactional
    private Result addMember(MemberAddDTO dto) {
        Result result = DubboResult.getResultByEnum(MemberResultEnum.MEMBER_FAILURE);
		Member mem = memberMapper.selectMemberInfoByMobile(dto.getTelPhone());
		if (mem != null && StringUtils.isNotBlank(mem.getTelPhone())) {
			result.setCode(MemberResultEnum.MEMBER_EXISTS.getCode());
			result.setMessage("当前会员已存在，不能重复添加");
			return result;
		}
		final String pwd = SmsCodeUtil.getMemberPwdCode();//随机密码 字母开头+5位数字
		Member member = new Member();
		// TODO:判断会员是否被分享、绑定分享关系
		MemberShareRecord record = new MemberShareRecord();
		record.setToTelPhone(dto.getTelPhone());
		record.setType(MemberShareTypeEnum.SHARE_MEMBER.getCode());
		record = shareRecordMapper.selectOne(record);
		if (null != record && record.getUserId() != null) {
			member.setPromoterId(record.getUserId());
		}
		member.setTelPhone(dto.getTelPhone());
        member.setMemberPwd(MD5.getMD5Str(pwd));
        member.setStatus(MemberStatusEnum.MEMBER_NORMAL.getCode());
        member.setCertification(MemberCertificationEnum.NOT_CERTIFIED.getCode());//未实名认证
		member.setType(MemberTypeEnum.MEMBER_TYPE.getCode());//默认为本地新增的会员，会员类型为：0会员
		// 默认为会员状态
        member.setIsMarketing(MemberIsMarketingEnum.IS_MARKETING_BYNO.getCode());
        if (member.getLevel() == null) {
            member.setLevel(MemberLevelEnum.MEMBER_GENERAL.getCode());
        }
		member.setCreaterId(dto.getCreaterId());
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
		memberMapper.insertUseGeneratedKeys(member);
		Long memberId = member.getId();
		// 向会员积分表新增数据
		boolean flag = insertIntoScoreData(memberId,dto.getCreaterId());
		if (!flag) {
			throw new MemberException("新增会员时向会员积分表新增数据失败");
		}
		result.setData(pwd);// 将新增的密码返回 提供给需要密码的取值
		return DubboResult.getResultBySuccess(result);
	}

	/**
	 * @param @param  memberId
	 * @param @return 参数
	 * @return boolean    返回类型
	 * @throws
	 * @Title: insertIntoScoreData
	 * @Description: 新增会员时 向会员积分数据表新增记录
	 */
	private boolean insertIntoScoreData(Long memberId,Long createId) {
		if (memberId != null) {
			MemberIntegralDTO memberIntegralDTO = new MemberIntegralDTO();
			memberIntegralDTO.setMemberId(memberId);
			memberIntegralDTO.setCreateTime(new Date());
			memberIntegralDTO.setCreaterId(createId);
			memberIntegralDTO.setStatus(MemberScoreFrozenEnum.MEMBER_SCORE_NORMAL.getCode());//非冻结
			memberIntegralDTO.setEnableScore(new BigDecimal(0));//可用积分
			memberIntegralDTO.setDrawcashScore(new BigDecimal(0));//已提现积分
			memberIntegralDTO.setStandbyScore(new BigDecimal(0));//待用积分
			memberIntegralDTO.setMemberProfitScore(new BigDecimal(0));// 会员分润积分
			memberIntegralDTO.setMemberRewardScore(new BigDecimal(0));// 推荐会员奖励 积分
			memberIntegralDTO.setMerchantRewardScore(new BigDecimal(0));// 推荐商户奖励积分
			memberIntegralMapper.insertMemberScore(memberIntegralDTO);
			return true;
		}
		return false;
	}

    /**
     * @描述：根据添加会员卡的数据查询会员信息，存在则返回数据，不存在则新增
     *
     * @param: memberCardInfoDTO
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/24 15:27
     */
	@Transactional
    private Result getMemberByPhone(MemberCardInfoDTO memberCardInfoDTO) {
        Result result = DubboResult.getResultByEnum(MemberResultEnum.MEMBER_FAILURE);
        Member member = memberMapper.selectMemberInfoByMobile(memberCardInfoDTO.getTelPhone());
        if (member == null || member.getId() == null) {
            MemberAddDTO memberRegisterDTO = getRegisterMemberDTO(memberCardInfoDTO);
            result = addMember(memberRegisterDTO);
            if (result == null || !RespCode.Code.SUCCESS.getCode().equals(result.getCode())) {
                throw new MemberException("新增会员数据失败");
            }
            member = memberMapper.selectMemberInfoByMobile(memberCardInfoDTO.getTelPhone());
            Object pwd = result.getData();
            Map<String, Object> retMap = ContainerUtil.map();
            retMap.put("password", pwd);
            retMap.put("member", member);
            result.setData(retMap);
            DubboResult.getResultBySuccess(result);
        }
        return result;
    }

    /**
     * @描述：dto转换
     *
     * @param: memberCardInfoDTO
     *
     * @return:
     *
	 * @作者： Mr.Shu
	 *
     * @创建时间：2017/5/24 15:27
     */
    private MemberAddDTO getRegisterMemberDTO(MemberCardInfoDTO memberCardInfoDTO) {
        MemberAddDTO memberRegisterDTO = null;
        if (memberCardInfoDTO != null) {
            memberRegisterDTO = new MemberAddDTO();
            memberRegisterDTO.setTelPhone(memberCardInfoDTO.getTelPhone());
            memberRegisterDTO.setMemberName(memberCardInfoDTO.getMemberName());
            memberRegisterDTO.setCreaterId(memberCardInfoDTO.getCreaterId());
        }
        return memberRegisterDTO;
    }


//==============================================私有通用方法，批量新增会员卡操作（导入）================================================/

    /**
     * @param @param  list
     * @param @return 参数
     * @return boolean    返回类型
     * @throws
     * @Title: batchInsertCard
     * @Description: 批量信息会员卡
     */
    @Transactional
    private boolean batchInsertCard(List<IcCardInfo> list) {
        if (ParamVerifyUtil.objIsNotNull(list)) {
            icCardInfoMapper.batchInsertCard(list);
            return true;
        }
        return false;
    }

    /**
     * @param @param  codes
     * @param @param  list
     * @param @param  type
     * @param @return 参数
     * @return Map<String,List<IcCradindInfoDTO>>    返回类型
     * @throws
     * @Title: getIcCradindInfoDTOs
     * @Description: 移除相同的数据
     */
    private Map<String, List<IcCardInfo>> getIcCradindInfoDTOs(List<String> codes, List<IcCardInfo> list, String type) {
        if (!ParamVerifyUtil.objIsNotNull(codes) || !ParamVerifyUtil.objIsNotNull(list)) {
            return null;
        }
        Map<String, List<IcCardInfo>> map = ContainerUtil.map();
        List<IcCardInfo> existsList = ContainerUtil.lList();
        Iterator<IcCardInfo> itor = list.iterator();
        while (itor.hasNext()) {
            IcCardInfo dto = itor.next();
            if ("outerCode".equals(type)) {
                if (codes.contains(dto.getOuterCode())) {
                    existsList.add(dto);
                    itor.remove();
                    continue;
                }
            } else if ("innerCode".equals(type)) {
                if (codes.contains(dto.getInnerCode())) {
                    existsList.add(dto);
                    itor.remove();
                    continue;
                }
            }
        }
        map.put("exists", existsList);
        map.put("noExists", list);
        return map;
    }

    /**
     * @param @param  list
     * @param @return 参数
     * @return Map<String,List<String>>    返回类型
     * @throws
     * @Title: getOuterAndInner
     * @Description: 将新增的会员卡 内码和外码 分开存储
     * （备注：此处会移除重复的数据，会改变传入集合的数据，没有做返回处理）
     */
    private Map<String, Object> getOuterAndInner(List<IcCardInfo> list) {
        Map<String, Object> map = null;
        if (ParamVerifyUtil.objIsNotNull(list)) {
            map = ContainerUtil.map();
            Set<String> os = ContainerUtil.set();
            Set<String> is = ContainerUtil.set();
            // excel重复的数据
            List<IcCardInfo> repeatCodes = ContainerUtil.lList();
            Iterator<IcCardInfo> itor = list.iterator();
            while (itor.hasNext()) {
                IcCardInfo info = itor.next();
                if (os.contains(info.getOuterCode())) {
                    repeatCodes.add(info);
                    itor.remove();
                    continue;
                }
                if (is.contains(info.getInnerCode())) {
                    repeatCodes.add(info);
                    itor.remove();
                    continue;
                }
                info.setCreateTime(new Date());
                info.setIsDelete(MemberCardStatusEnum.CARD_NORMAL.getCode());
                os.add(info.getOuterCode());
                is.add(info.getInnerCode());
            }
            map.put("outerCodes", ContainerUtil.aList(os));// 需要导入的外码
            map.put("innerCodes", ContainerUtil.aList(is));// 需要导入的内码
            map.put("repeatCodes", repeatCodes);// excel 重复的数据
        }
        return map;
    }

}
