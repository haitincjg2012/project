package com.alqsoft.service.impl.hunterstore;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterstore.HunterStoreDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunterstoreattachment.HunterStoreAttachment;
import com.alqsoft.service.hunterstore.HunterStoreService;

@Service
@Transactional(readOnly=true)
public class HunterStoreServiceImpl implements HunterStoreService{
	
	private static Logger logger = LoggerFactory.getLogger(HunterStoreServiceImpl.class);
	@Autowired
	private HunterStoreDao hunterStoreDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HunterStoreAttachment get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterStoreDao.findOne(arg0);
	}

	@Override
	public HunterStoreAttachment saveAndModify(HunterStoreAttachment arg0) {
		// TODO Auto-generated method stub
		return hunterStoreDao.save(arg0);
	}

	@Override
	@Transactional
	public Result saveHunterStore(Long id, Long hunterid, String address, String content) {
		try {
			if(id==null){//新增加
				HunterStoreAttachment hunterStoreAttachment = new HunterStoreAttachment();
				Hunter hunter = new Hunter();
				hunter.setId(hunterid);
				hunterStoreAttachment.setHunter(hunter);
				hunterStoreAttachment.setAddress(address);
				hunterStoreAttachment.setContent(content);
				hunterStoreAttachment.setIsDelete(0);
				saveAndModify(hunterStoreAttachment);
				return ResultUtils.returnSuccess("添加成功");
			}else{//编辑
				HunterStoreAttachment hunterStoreAttachmentdb = this.get(id);
				if(hunterStoreAttachmentdb==null){
					return ResultUtils.returnError("该信息不存在，无法编辑");
				}
				if(hunterStoreAttachmentdb.getHunter()==null||hunterStoreAttachmentdb.getHunter().getId()==null){
					return ResultUtils.returnError("该信息异常，未关联批发商");
				}
				if(hunterStoreAttachmentdb.getHunter().getId().longValue()!=hunterid.longValue()){
					return ResultUtils.returnError("该信息与您批发商身份不符，无法编辑");
				}
				hunterStoreAttachmentdb.setAddress(address);
				hunterStoreAttachmentdb.setContent(content);
				saveAndModify(hunterStoreAttachmentdb);
				return ResultUtils.returnSuccess("编辑成功");
			}
		} catch (Exception e) {
			logger.error("批发商个人中心添加或编辑店铺异常："+e.getMessage());
			return ResultUtils.returnError("处理失败");
		}
	}

	@Override
	@Transactional
	public Result deleteHunterStore(Long id,Long hunterid) {
		try {
			HunterStoreAttachment hunterStoreAttachmentdb = this.get(id);
			if(hunterStoreAttachmentdb==null){
				return ResultUtils.returnError("该信息不存在，无法编辑");
			}
			if(hunterStoreAttachmentdb.getHunter()==null||hunterStoreAttachmentdb.getHunter().getId()==null){
				return ResultUtils.returnError("该信息异常，未关联批发商");
			}
			if(hunterStoreAttachmentdb.getHunter().getId().longValue()!=hunterid.longValue()){
				return ResultUtils.returnError("该信息与您批发商身份不符，无法编辑");
			}
			hunterStoreAttachmentdb.setIsDelete(1);
			saveAndModify(hunterStoreAttachmentdb);
			return ResultUtils.returnSuccess("删除成功");
		} catch (Exception e) {
			logger.error("批发商个人中心删除店铺异常id："+id+"=="+e.getMessage());
			return ResultUtils.returnError("删除失败");
		}
	}
	

}
