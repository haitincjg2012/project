package com.alqsoft.service.subject;

import com.alqsoft.entity.subject.Subject;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;

import java.util.List;
import java.util.Map;

public interface SubjectService {

	Subject saveAndModify(Subject subject);

	EasyuiResult getSubjectList(int page,int rows);

	Result delete(Long id);

	List<Map<String,Object>> getIndustryType(Long pid);

	List<Map<String,Object>> getHunterByIndustryType(Long pid);

	Map<String,Object> detail(Long id);

	Result save(Long id, String hids,String name,String aid);

	String getHunterIdsBySubjectId(Long sid,Long tid);

    String getHunterInfo(String inIds, String name);

	String getAllHunterInfoById(String inIds);

	Result getIndustryTypeByHunterId(String id);
}
