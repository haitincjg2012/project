package com.alqsoft.service.impl.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.member.MemberDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.member.MemberService;

@Service
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService{
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberDao memberDao;

	@Override
	public Member getMemberByIdAndUuid(Map<String, Object> params) {
		return this.memberDao.getMemberByIdAndUuid(params);
	}

	/**
	 * 用户个人中心接口
	 */
	@Override
	public Map<String, Object> getMemberCenterById(Long memberid) {
		// TODO Auto-generated method stub
		return memberDao.getMemberCenterById(memberid);
	}

	@Override
	public Result getMemberCenter(Long memberid) {
		try {
			Map<String,Object> membercenter = getMemberCenterById(memberid);
			return ResultUtils.returnSuccess("查询成功",membercenter);
		} catch (Exception e) {
			// TODO: handle exception
			return ResultUtils.returnSuccess("查询失败");
		}
		
	}

	@Override
	public List<Map<String, Object>> getMemberByPhone(String newphone) {
		
		return memberDao.getMemberByPhone(newphone);
	}


	@Override
	public Member getMemberByHunterId(Long hunterid) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByHunterId(hunterid);
	}


	@Override
	public Result getNickNameByImid(Member member, String imId) {
		// TODO Auto-generated method stub
		
		List list = new ArrayList();
		if (member == null) {
			return ResultUtils.returnError("请先登录在");
		} else if (imId == null) {
			return ResultUtils.returnError("请输入参数imId");
		}
		String[] imIds = imId.split(",");
		for (int i = 0; imIds.length > i; i++) {
			try {
				Map map = memberDao.getNickNameByImid(imIds[i].trim());
				list.add(map);
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("通过imId获取数据:" + e);
				return ResultUtils.returnError("获取数据失败");
			}

		}

		return ResultUtils.returnSuccess("获取数据成功", list);
	}

	@Override
	public Map<String, Object> getMemberLogoImage(Long mid) {
		return memberDao.getMemberLogoImage(mid);
	}

	@Override
	public Long getHunteridByMemberid(Long id) {
		
		return memberDao.getHunteridByMemberid(id);
	}
}
