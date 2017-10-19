package com.alqsoft.service.impl.hunterservicerule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.dao.hunterservicerule.HunterServiceRuleDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterServiceRuleService;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.hunterservicerule.HunterServiceRuleService;

@Service
@Transactional
public class HunterServiceRuleServiceImpl implements HunterServiceRuleService{
	
	//做查询走向dao
	@Autowired
	private HunterServiceRuleDao hunterServiceRuleDao;
	//做增删改使用rpc
	@Autowired
	private RpcHunterServiceRuleService rpcHunterServiceRuleService;
	//做法则上传图片
	@Autowired
    private AttachmentService attachmentService;
	
	
	//查询
	@Override	
	public Result getHunterServiceRuleList(Long id, Member member) {
		if(id==null){
			return ResultUtils.returnError("没有对应的法则");
		}
		Hunter hunter = member.getHunter();
		//判断当前登录用户是否是批发商
		if(hunter==null){
		return ResultUtils.returnError("没有对应的批发商,请登录");
		}
		//获取当前批发商法则的content内容
		Map  hunterRuleMap = hunterServiceRuleDao.getRuleContentList(id);
		//判断content内容有且只有一个
		if(hunterRuleMap==null){
			return ResultUtils.returnError("该批发商法则信息不存在");
		}
		if(hunterRuleMap.get("hunter_id")==null){
			return ResultUtils.returnError("该批发商法则信息错误，未关联批发商信息");
		}
		//判断此批发商法则关联的批发商id和当前登录批发商的id是同一个
		if(Long.valueOf(hunterRuleMap.get("hunter_id").toString())!=member.getHunter().getId().longValue()){
			return ResultUtils.returnError("该批发商法则信息与您当前批发商身份不符");
		}
		//获取图片信息
		List<Map>  hunterRuleImgList = hunterServiceRuleDao.getRuleImgList(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("hunterrulecontent", hunterRuleMap);
		map.put("hunterruleimg", hunterRuleImgList);
		return ResultUtils.returnSuccess("批发商服务法则列表获取成功",map);
	}
	
	//删除
	@Override
	@Transactional
	public Result delServiceruleById(Long id, Member member) {
		Hunter hunter = member.getHunter();
		if(hunter==null){
			return ResultUtils.returnError("没有对应的批发商,请登录");
		}
		return rpcHunterServiceRuleService.delServiceruleById(id,member);
	}
	//修改
	@Override
	@Transactional
	public Result updateServicerule(Long id, String attachmentids, String content, Member member) {
		return rpcHunterServiceRuleService.updateServicerule(id,attachmentids, content,member);
	}

	@Override
	@Transactional
	public Result mobileUploadAttachment(MultipartFile urlfile, Object[] objects, String module, String[] extendFile) {
		// TODO Auto-generated m	
		return attachmentService.mobileUploadAttachment(urlfile,new Object[]{attachmentService,"saveHunterRuleAttachment"},module,extendFile);
	}

}
