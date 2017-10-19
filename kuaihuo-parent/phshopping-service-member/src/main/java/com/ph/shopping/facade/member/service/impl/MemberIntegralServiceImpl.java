package com.ph.shopping.facade.member.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MemberIntegralMapper;
import com.ph.shopping.facade.member.dto.MemberIntegralDTO;
import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IMemberIntegralService;
import com.ph.shopping.facade.member.vo.MemberIntegralSourceVO;
import com.ph.shopping.util.DubboResult;

/**
 * 
 * @ClassName:  MemberIntegralService   
 * @Description:会员积分操作 
 * @author: 李杰
 * @date:   2017年4月25日 下午4:38:29     
 * @Copyright: 2017
 */
@Component
@Service(version = "1.0.0")
public class MemberIntegralServiceImpl implements IMemberIntegralService {

	private static final Logger log = LoggerFactory.getLogger(MemberIntegralServiceImpl.class);

	@Autowired
	private MemberIntegralMapper memberIntegralMapper;

	@Override
	public Result getMemberIntegralInfoLsitByPage(MemberIntegralDTO dto) {
		log.info("根据分页信息获取会员积分列表参数,MemberIntegralDTO ={} ", JSON.toJSONString(dto));
		Result result = DubboResult.getDefResult();
		try {
			String[] fields = { "pageNum", "pageSize", "memberId" };
			if (!ParamVerifyUtil.entityIsNotNullByField(dto, fields)) {
				return result;
			}
			PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
			List<MemberIntegralDTO> list = memberIntegralMapper.selectMemberIntegralListInfo(dto);
			PageInfo<MemberIntegralDTO> page = new PageInfo<MemberIntegralDTO>(list);
			result.setCount(page.getTotal());
			result.setData(page.getList());
			DubboResult.getResultBySuccess(result);
		} catch (Exception e) {
			log.error("根据分页信息获取会员积分列表信息错误", e);
			throw new MemberException("根据分页信息获取会员积分列表信息错误");
		}
		return result;
	}

	@Override
	public Result getMemberIntegralInfoList(MemberIntegralDTO dto) {
		log.info("获取会员积分列表参数,MemberIntegralDTO = {} ", JSON.toJSONString(dto));
		Result result = DubboResult.getDefResult();
		try {
			String[] fields = { "memberId" };
			if (!ParamVerifyUtil.entityIsNotNullByField(dto, fields)) {
				return result;
			}
			List<MemberIntegralDTO> list = memberIntegralMapper.selectMemberIntegralListInfo(dto);
			if (ParamVerifyUtil.objIsNotNull(list)) {
				result.setCount(list.size());
				result.setData(list);
				DubboResult.getResultBySuccess(result);
			} else {
				DubboResult.setResultByEnum(MemberResultEnum.MEMBER_NO_DATA, result);
			}
		} catch (Exception e) {
			log.error("查询会员积分列表数据错误", e);
			throw new MemberException("查询会员积分列表数据错误");
		}
		return result;
	}

	@Override
	public Result getMemberIntegralSource() {
		Result result = DubboResult.getResultByEnum(MemberResultEnum.MEMBER_NO_DATA);
		try {
			List<MemberIntegralSourceVO> list = memberIntegralMapper.selectMemberIntegralSource();
			if (ParamVerifyUtil.objIsNotNull(list)) {
				result.setCount(list.size());
				result.setData(list);
				DubboResult.getResultBySuccess(result);
			}
		} catch (Exception e) {
			log.error("查询会员积分来源字典数据错误", e);
			throw new MemberException("查询会员积分来源字典数据错误");
		}
		return result;
	}

}
