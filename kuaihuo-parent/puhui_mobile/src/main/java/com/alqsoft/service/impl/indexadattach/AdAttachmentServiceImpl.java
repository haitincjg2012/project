package com.alqsoft.service.impl.indexadattach;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.alqsoft.dao.indexadattach.IndexAdAttachmentDao;
import com.alqsoft.service.indexadattach.AdAttachmentService;
import com.alqsoft.utils.StatusCodeEnums;

/**
 * 
 * @Description: 获取首页数据，展示滚动广告
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年2月28日 下午4:07:24
 *              缓存，即当重复使用该方法时，方法本身不会被调用执行，即本身会忽略，取而代之的是方法的结果，直接从 缓存中获取
 */
@Service
@Transactional(readOnly = true)
public class AdAttachmentServiceImpl implements AdAttachmentService {
	private static Logger logger = LoggerFactory.getLogger(AdAttachmentServiceImpl.class);
	
	@Autowired
	private IndexAdAttachmentDao indexAdAttachdao;
	
	@Cacheable(key="alq_ad_attachment_key",value="alq_ad_attachment")
	public Result findAll(Integer startInt, Integer endInt) {
		// TODO Auto-generated method stub
		Result result=new Result();
		try {
			if(startInt==null || endInt==null){
				result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
				result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
			}
			List<Map> adttachment = indexAdAttachdao.getIndexAdAttachDao(startInt, endInt);
			int size=adttachment.size(); 
			if (size <= 0) {
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
			    result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
			  
			}  else{
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
			    result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			    result.setContent(adttachment);
			}
			
			
		} catch (Exception e) {
			    logger.error(e.getMessage(),e);
	            result.setCode(StatusCodeEnums.ERROR.getCode());
	            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}finally{
			return result;
		}
		
	}
	public void getTextDetail(Long id,Model model){
		Map textDetail = indexAdAttachdao.getTextDetail(id);
		if (textDetail!=null ) {
			 model.addAttribute("result",textDetail.get("detailContent") );
		}
}
}
