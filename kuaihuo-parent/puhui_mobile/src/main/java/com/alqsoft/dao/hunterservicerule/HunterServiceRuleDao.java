package com.alqsoft.dao.hunterservicerule;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.alqframework.result.Result;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface HunterServiceRuleDao {

	public Map getRuleContentList(@Param("id")Long id);
	
	public List<Map> getRuleImgList(@Param("id")Long id);

}
