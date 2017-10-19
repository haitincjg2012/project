package com.alqsoft.service.impl.hunternickname;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.hunternickname.HunterNicknameDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.service.hunternickname.HunterNicknameService;

@Service
@Transactional
public class HunterNicknameServiceImpl implements HunterNicknameService {

	@Autowired
	private HunterNicknameDao hunterNicknameDao;

	@Override
	public Result updateHunterNickName(Hunter hunter) {
		Result result = new Result();
		// 得到批发商的昵称
		String nickname = hunter.getNickname();
		// 除去空格
		if (nickname != null) {
			nickname = nickname.replaceAll(" ", "");
		}
		// 判断是否为空
		if (nickname == null || "".equals(nickname)) {
			return ResultUtils.returnError("批发商昵称不能为空");
		}
		// 判断字符长度
		if (nickname.length() >= 2 && nickname.length() <= 10) {
			try {
				this.hunterNicknameDao.updateHunterNickName(hunter.getId(), nickname);
				result.setCode(1);
				result.setMsg("批发商昵称修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
				result.setCode(0);
				result.setMsg("批发商昵称修改失败");
			}
		} else {
			return ResultUtils.returnError("批发商昵称长度范围在2-10个字");
		}
		return result;
	}

}
