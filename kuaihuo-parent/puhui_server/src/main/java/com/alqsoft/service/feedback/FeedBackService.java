package com.alqsoft.service.feedback;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.feedback.FeedBack;

/**
 *@Description: 意见反馈 业务
 * 
 * @author : 曾金山 
 * @ProjectName : haitunwu
 * @version : v4.0 - 2014
 * @createTime : 2014-6-4 下午6:23:19
 * 
 */

public interface FeedBackService extends BaseService<FeedBack>{
	public EasyuiResult<List<FeedBack>> getFeedBackPage(Map<String, Object> map,Integer page,Integer rows);
	public Long getFeedBackNumByTypeId(Long id);
}
