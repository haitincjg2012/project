package com.alqsoft.service.impl.membernickname;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.hunternickname.HunterNicknameDao;
import com.alqsoft.dao.membernickname.MemberNicknameDao;
import com.alqsoft.service.membernickname.MemberNicknameService;

@Service
@Transactional
public class MemberNicknameServiceImpl implements MemberNicknameService{

	@Autowired
	private MemberNicknameDao memberNicknameDao;
	
	@Override
	public Result updateMemberNickName(Long id, String nickname) {
		Result result = new Result();
		try{
			this.memberNicknameDao.updateMemberNickName(id,nickname);
			result.setCode(1);
			result.setMsg("会员昵称修改成功");
		}catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("会员昵称修改失败");
		}
			
		return result;
	}

}
