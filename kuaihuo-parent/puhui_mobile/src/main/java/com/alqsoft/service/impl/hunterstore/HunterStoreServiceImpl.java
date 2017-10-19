package com.alqsoft.service.impl.hunterstore;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterstore.HunterStoreDao;
import com.alqsoft.rpc.mobile.RpcHunterStoreService;
import com.alqsoft.service.hunterstore.HunterStoreService;

/**
 * 批发商店铺详情
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class HunterStoreServiceImpl implements HunterStoreService{

	@Autowired
	private HunterStoreDao hunterStoreDao;
	@Autowired
	private RpcHunterStoreService rpcHunterStoreService;
	
	
	@Override
	public Result findHunterStoreList(Long hunterid) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> stores = hunterStoreDao.findHunterStoreList(hunterid);
		return ResultUtils.returnSuccess("查询成功", stores);
	}

	@Override
	public Result getHunterStoreById(Long id) {
		// TODO Auto-generated method stub
		Map<String,Object> detail = hunterStoreDao.getHunterStoreById(id);
		return ResultUtils.returnSuccess("查询成功", detail);
	}

	@Override
	public Result saveHunterStore(Long id,Long hunterid, String address, String content) {
		// TODO Auto-generated method stub
		id=id==0?null:id;//手机端传0代表添加
		if(address==null||"".equals(address)){
			return ResultUtils.returnError("店铺图片不能为空");
		}
		String content2=content.trim();
		if(content2==null||"".equals(content2)){
			return ResultUtils.returnError("店铺详情不能为空");
		}
		if(id==null&&hunterStoreDao.getHunterStoreCountByHunterId(hunterid)>=6){
			return ResultUtils.returnError("最多上传6张店铺图片");
		}
		return rpcHunterStoreService.saveHunterStore(id,hunterid, address, content);
	}

	@Override
	public Result deleteHunterStore(Long id,Long hunterid) {
		// TODO Auto-generated method stub
		if(id==null){
			return ResultUtils.returnError("请选择要删除的店铺信息");
		}
		Map<String,Object> hunterstore = hunterStoreDao.getHunterStoreById(id);
		if(hunterstore==null){
			return ResultUtils.returnError("不存在此信息，无法删除");
		}
		return rpcHunterStoreService.deleteHunterStore(id, hunterid);
	}

	@Override
	public Result getHunterStoreList() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> hunterStoreList = hunterStoreDao.getHunterStoreList();
		return ResultUtils.returnSuccess("店铺列表集合", hunterStoreList);
	}

}
