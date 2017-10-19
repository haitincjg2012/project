package com.alqsoft.service.impl.industryassociation;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.industryassociation.IndustryAssociationDao;
import com.alqsoft.service.industryassociation.IndustryAssociationService;
/**
 * Date:     2017年3月1日  9:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */

@Service
@Transactional(readOnly=true)
public class IndustryAssociationServiceImpl implements IndustryAssociationService {

	@Autowired
	private IndustryAssociationDao industryAssociationDao;

	/**
	 * 所属协会列表接口
	 * @return
	 */
	@Override
	public Result findIndustryAssociationList() {
		List<Map<String,Object>> industryAssociationList = industryAssociationDao.findIndustryAssociationList();//查询所属协会列表
		if(industryAssociationList.size()>0){
			return ResultUtils.returnSuccess("获取列表成功", industryAssociationList);
		}else{
			return ResultUtils.returnError("没有数据");
		}
		
	}
}
