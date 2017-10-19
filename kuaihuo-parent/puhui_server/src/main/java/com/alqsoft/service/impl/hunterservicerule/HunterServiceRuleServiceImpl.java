package com.alqsoft.service.impl.hunterservicerule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.hunterservicerule.HunterServiceRuleDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunterrule.HunterRule;
import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterruleattachment.HunterRuleAttachmentService;
import com.alqsoft.service.hunterservicerule.HunterServiceRuleService;
import com.alqsoft.service.impl.hunter.HunterServiceImpl;

@Service
@Transactional
public class HunterServiceRuleServiceImpl implements HunterServiceRuleService{
	private static Logger logger = LoggerFactory.getLogger(HunterServiceRuleServiceImpl.class);
	@Autowired
	private HunterServiceRuleDao hunterServiceRuleDao;
	@Autowired
	private HunterRuleAttachmentService attachmentService;

	@Override
	public Result delServiceruleById(Long id, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(null==hunter){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		try {
			this.hunterServiceRuleDao.delServiceruleById(id);
			result.setCode(1);
			result.setMsg("批发商服务法则删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			result.setCode(0);
			result.setMsg("批发商服务法则删除失败");
		}	
		return result;
	}

	@Override
	public Result updateServicerule(Long id, String attachmentids, String content, Member member) {
		Result result = new Result();
		Hunter hunter = member.getHunter();//获取登录批发商信息
		if(hunter==null){
			return ResultUtils.returnError("对不起,您不是批发商,请先申请");
		}
		if(null==id){//id是法则的id
			id=0L;
		}
		//判断字符长度
		if(content.length()>200){
	    	return ResultUtils.returnError("请输入在200个字以内");
		}
		if(id.longValue()<=0){//新增
			try{
				//如果ID==null，则执行法则添加
				HunterRule hunterRule = new HunterRule();
				hunterRule.setContent(content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
				//所添加的法则关联到对应的批发商ID
				hunterRule.setHunter(member.getHunter());
				hunterRule=saveAndModify(hunterRule);//法则
			    
				/*这是获取的法则ID，添加不需要获取，修改才需要
				id=hunterRule.getId();*/
				if(attachmentids!=null&&!"".equals(attachmentids)){
					String[] picids = attachmentids.split(",");
					Collection<Long> pidslist=new ArrayList<Long>();//要批量更新的图片id
					for(int i=0;i<picids.length;i++){
						try {
							pidslist.add(Long.valueOf(picids[i]));
						} catch (Exception e) {
							return ResultUtils.returnError("法则图片参数错误");
						}
					}
					//如果图片id为空，就不添加图片
					if(pidslist.size()>0){
						attachmentService.updateHunterRulePicture(pidslist,hunterRule.getId());//更新图片关联的法则id
					}
				}
				result.setCode(1);
				result.setMsg("批发商服务法则添加成功");
			}catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
				result.setCode(0);
				result.setMsg("批发商服务法则添加失败");
			}
		}else if(id!=null&&id>0){//编辑
			try {
				//通过hunterRuleId获取到对应的hunterRuleAttachment记录
				List<HunterRuleAttachment> attList=this.attachmentService.getHunterRuleAttachmentByHunterRuleId(id);
				//比较新旧ID  新的ID做保存 旧的ID删除掉
				String[] newids= attachmentids.split(",");
				//获取需要从list里面删除的无用的attachment  
				List<Long> deleteAttachment=new ArrayList<Long>();
				
				List<Long> addAttachment=new ArrayList<Long>();
				//循环
				out:for (HunterRuleAttachment hunterRuleAttachment : attList) {
					
					for (String aid:newids){
						if(Long.valueOf(aid).longValue()==hunterRuleAttachment.getId().longValue()){
							continue out;
						}
					}
					deleteAttachment.add(hunterRuleAttachment.getId());
				}
				
				//循环
				needadd:for (String aid:newids){ {
					
					for (HunterRuleAttachment hunterRuleAttachment : attList)
						if(Long.valueOf(aid).longValue()==hunterRuleAttachment.getId().longValue()){
							continue needadd;
						}
					}
					addAttachment.add(Long.valueOf(aid));
				}
				
				//判断新的ID和旧的ID，删除掉原来（旧的）多余的ID
				StringBuffer ids1=new StringBuffer();
				for (Long long1 : deleteAttachment) {
					ids1.append(long1+",");
				}
				if (StringUtils.isNotBlank(ids1)){
					String idString=ids1.substring( 0,ids1.length()-1 );
					 //分割字符串，进行批量删除
					hunterServiceRuleDao.deleteAttachmentids(idString.split(","));
				}
				
				//--------------------------------------------------------------------------------
				//判断新的ID和旧的ID，保存现在新有的ID
				StringBuffer ids2 = new StringBuffer();
				for(Long long2 : addAttachment){
					ids2.append(long2+",");
				}
				if (StringUtils.isNotBlank(ids2)){
					String idString2=ids2.substring( 0,ids2.length()-1 );
					//分割字符串，进行批量增加
					hunterServiceRuleDao.updateAttachmentids(idString2.split(","),id);
				}
				//修改Content内容
				try {
					this.hunterServiceRuleDao.updateServiceruleContent(id, content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
				} catch (Exception e) {
					logger.error("保存异常"+e);
				    return ResultUtils.returnError("仅保存文字！");
				}
				
				result.setCode(1);
				result.setMsg("批发商服务法则编辑成功");
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
				result.setCode(0);
				result.setMsg("批发商服务法则编辑失败");
			}
		}
		return result;
	}

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HunterRule get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterServiceRuleDao.findOne(arg0);
	}

	@Override
	public HunterRule saveAndModify(HunterRule arg0) {
		// TODO Auto-generated method stub
		return hunterServiceRuleDao.save(arg0);
	}
	

}
