package com.alqsoft.service.impl.subject;

import com.alqsoft.dao.industrytype.IndustryTypeDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.entity.subject.Subject;
import com.alqsoft.mybatis.dao.hunter.HunterDao;
import com.alqsoft.mybatis.dao.industrystype.IndustrysTypeDao;
import com.alqsoft.mybatis.dao.subject.SubjectDao;
import com.alqsoft.service.subject.SubjectService;
import com.google.common.collect.Maps;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly=true)
public class SubjectServiceImpl implements SubjectService{

	private static Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Autowired
	@Qualifier("mybatisSubjectDao")
	private SubjectDao subjectDao;


	@Autowired
	private IndustrysTypeDao industrysTypeDao;

	@Autowired
	@Qualifier(value = "mybatisHunterDao")
	private HunterDao hunterDao;

	@Autowired
	private com.alqsoft.dao.hunter.HunterDao hibernateHunterDao;

	@Autowired
	private com.alqsoft.dao.subject.SubjectDao hibernateSubjectDao;

	@Autowired
	private IndustryTypeDao hibernateIndustryTypeDao;

	@Override
	public EasyuiResult getSubjectList(int page,int rows) {
		
		 List<Map<String,Object>> lists= subjectDao.getSubjectList((page-1)*rows,rows);
		 EasyuiResult result = new EasyuiResult();
		 result.setT(lists);
		 result.setTotal(Long.valueOf(lists.size()));
		 return result;
	}

	@Override
	@Transactional
	public Result delete(Long id) {
		int delNum = 0;
		Result result = new Result();
		try {
			delNum = subjectDao.delete(id);
			if (delNum>0){
				result.setCode(1);
				result.setMsg("删除成功");
			} else {
				result.setCode(0);
				result.setMsg("删除失败");
			}
		} catch (Exception e) {
			log.info(e.getMessage(),e);
			result.setCode(0);
			result.setMsg("专题下关联其他批发商信息不能删除");
		} finally {
			return result;
		}

	}

	@Override
	public List<Map<String, Object>> getIndustryType(Long pid) {
		Map<String,Object> paramMap = Maps.newHashMap();
		if (pid!=null){
		    paramMap.put("pid",pid);
		}
		return industrysTypeDao.getIndustryTypeList(paramMap);
	}

	@Override
	public List<Map<String, Object>> getHunterByIndustryType(Long pid) {
		Map<String,Object> paramMap = Maps.newHashMap();
		if (pid!=null){
			paramMap.put("tid",pid);
		} else {
			paramMap.put("tid",-1);
		}
		return hunterDao.getHunterByIndustryType(paramMap);
	}

	@Override
	public Map<String, Object> detail(Long id) {
		return subjectDao.detail(id);
	}

	@Override
	@Transactional
	public Result save(Long id, String hids,String name,String aid) {
		log.info(" id :{} ,hids: {} ,name : {}, aid:{} ",id,hids,name,aid);
		String[] hidAry = hids.split(",");
		try {
			Subject subject =hibernateSubjectDao.findOne(id);
			subject.setName(name);
			if(StringUtils.isNotBlank(aid)){
				Attachment attachment = new Attachment();
				attachment.setId(Long.parseLong(aid));
				subject.setAttachment(attachment);
			}
			hibernateSubjectDao.save(subject);
			//取消批发商专题关联
			hibernateHunterDao.setSubjectNull(id);
			for (String hid:hidAry){
				if (StringUtils.isBlank(hid)){
					continue;
				}
                Hunter hunter= hibernateHunterDao.findOne(Long.valueOf(hid));
                hunter.setSubject(subject);
                hibernateHunterDao.save(hunter);
            }
            return ResultUtils.returnSuccess("修改成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnSuccess("修改失败");
		}
	}

	/**
	 * 根据专题ID获取所有批发商信息
	 * @param sid
	 * @return
	 */
	@Override
	public String getHunterIdsBySubjectId(Long sid,Long tid) {
		log.info("sid:{}",sid);
		Subject subject = new Subject();
		subject.setId(sid);
		IndustryType industryType=new IndustryType();
		industryType.setId(tid);
		List<Hunter> hunters= hibernateHunterDao.findHuntersBySubjectId(subject,industryType);
		log.info("hunters : {} ", hunters);
		StringBuilder builder = new StringBuilder();
		for (Hunter hunter:hunters){
			builder.append(hunter.getId()).append(",");
		}
		log.info("hunterIDS:{}",builder.toString());
		return builder.toString();
	}

	@Override
	@Transactional
	public Subject saveAndModify(Subject subject) {
		return hibernateSubjectDao.save(subject);
	}

	@Override
	public String getHunterInfo(String inIds, String name) {
		name = "%"+name +"%";
		List<Hunter> hunters = hibernateHunterDao.getHunterInfo(inIds,name);
		StringBuilder builder = new StringBuilder();
		hunters.forEach(e->{
			builder.append("<option value=").append(e.getId()).append( " >").
					append(e.getNickname()).append("</option>");
		});
		return builder.toString();
	}

	@Override
	public String getAllHunterInfoById(String inIds) {
		List<Hunter> hunters = hibernateHunterDao.getAllHunterInfo(inIds);
		StringBuilder builder = new StringBuilder();
		hunters.forEach(e->{
			builder.append("<option selected='selected' value=").append(e.getId()).append( " >").
					append(e.getNickname()).append("</option>");
		});
		return builder.toString();
	}

	@Override
	public Result getIndustryTypeByHunterId(String id) {
		log.info("id={}",id);
		if(StringUtils.isNotBlank(id)){
			try {
				Hunter hunter = hibernateHunterDao.findOne(Long.parseLong(id));
				if (Objects.isNull(hunter)) {
					return ResultUtils.returnError("批发商信息不存在");
				}
				IndustryType industryType = hunter.getIndustryType();
				if (Objects.isNull(industryType)){
					return ResultUtils.returnError("行业信息不存在");
				}
				Long industryTypeId = industryType.getId();
				IndustryType parentIndustryType = industryType.getParentId();
				Long parentIndustryTypeId=0L;
				if (!Objects.isNull(parentIndustryType)){
					parentIndustryTypeId=parentIndustryType.getId();
				}
				Map<String,Object> map = Maps.newHashMap();
				map.put("id",parentIndustryTypeId+","+industryTypeId);
				return ResultUtils.returnSuccess("成功",map);
			} catch (NumberFormatException e) {
				log.error(e.getMessage(),e);
				return ResultUtils.returnError("服务异常");
			}

		}
		return ResultUtils.returnError("参数错误");
	}
}
