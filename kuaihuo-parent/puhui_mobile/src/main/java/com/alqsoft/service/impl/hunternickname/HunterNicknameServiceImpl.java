package com.alqsoft.service.impl.hunternickname;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunter.HunterDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterNickNameService;
import com.alqsoft.rpc.mobile.RpcMemberNickNameService;
import com.alqsoft.service.hunternickname.HunterNicknameService;

@Service
@Transactional(readOnly=true)
public class HunterNicknameServiceImpl implements HunterNicknameService{
	
	@Autowired
	private RpcHunterNickNameService rpcHunterNickNameService;
	@Autowired
	private RpcMemberNickNameService rpcMemberNickNameService;
	//检验批发商的昵称是否已存在
	@Autowired
	private HunterDao hunterDao;

	@Override
	public Result updateNickname(Integer type,String nickname, Member member) {
		//检验批发商的昵称是否已存在
		List<Map<String,Object>> hunterNicknameList=hunterDao.getHunterByNickname(nickname);
		if(hunterNicknameList.size()>0){
			return ResultUtils.returnError("该昵称已存在，请重新输入");
		}
		//判断修改的昵称是否为批发商
		//1修改会员 0修改批发商
		if(type.intValue()==1){
				return this.rpcMemberNickNameService.updateMemberNickName(member.getId(),nickname);
		}else if(type.intValue()==0){
			//不为空时修改批发商昵称
			if(member.getHunter()!=null){
				Hunter hunter = member.getHunter();
				hunter.setNickname(nickname);
				return this.rpcHunterNickNameService.updateHunterNickName(hunter);
			}else{
				return ResultUtils.returnError("该用户不是批发商");
			}
		}
		return ResultUtils.returnError("参数错误，未指定修改类型");
	}
	

}
