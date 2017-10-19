package com.alqsoft.service.impl.huntersourcingservice;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.huntersourcingservice.HunterSourcingDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntersourcingservice.HunterSourcingService;
@Service
@Transactional
public class HunterSourcingServiceImpl implements HunterSourcingService{

	@Autowired
	private HunterSourcingDao hunterSourcingDao;
	
	@Override
	public Result saveOrModifySourcingService( String detail, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		if(detail==null||"".equals(detail)){
			return ResultUtils.returnError("请求的资源不存在");
		}
		//判断字符长度
		if(detail.length()>200){
	    	return ResultUtils.returnError("请输入在200个字以内");
		}
		try {
			//判断货源服务有没有Detail  有-修改  没有-添加
			HunterService hunterService= this.hunterSourcingDao.getHunterServiceByHId(hunter.getId());
			if(hunterService == null){
				HunterService hunterService2 = new HunterService();
				hunterService2.setHunter(hunter);
				hunterService2.setDetail(detail);
				this.saveAndModify(hunterService2);
			}else{
				hunterService.setDetail(detail);
				this.saveAndModify(hunterService);
			}
			
			result.setCode(1);
			result.setMsg("批发商货源服务添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商货源服务添加失败");
		}	
		return result;
	}

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HunterService get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterSourcingDao.findOne(arg0);
	}

	@Override
	public HunterService saveAndModify(HunterService arg0) {
		// TODO Auto-generated method stub
		return hunterSourcingDao.save(arg0);
	}

}
