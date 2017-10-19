package com.alqsoft.service.impl.position;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.json.JsonUtil;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.position.PositionDao;
import com.alqsoft.entity.position.Position;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.service.position.PositionService;
import com.alqsoft.utils.HttpClientObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

@Service
@Transactional(readOnly=true)
public class PositionServiceImpl implements PositionService {

	private static Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);
	
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private InitParamPc initParam;
	
	
	@Override
	public Result getPositionByIdAndType(String id, int type) {
		String phPositionUrl = initParam.getProperties().get("PH_POSITION_URL").toString();
		MultiValueMap params = new LinkedMultiValueMap();
		List<Map<String, Object>> list = null;
		
		
		if(type == 0){
			/*phPositionUrl += "/getprovinces";
			String sendPost = HttpClientUtils.getHttpsToPost(phPositionUrl, params);
			JSONObject json = JSON.parseObject(sendPost);
			int code = Integer.parseInt(json.get("code").toString());
			if(code == 200){
				list = JsonUtil.stringToList(sendPost, "data", Map.class);*/
				list = this.positionDao.getPosition();
				/*for (Map map : list) {
					map.put("name", map.get("provinceName").toString());
				}*/
			/*}*/
		}
		if(type == 1){
			
			/*list = new ArrayList<Map<String, Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", "南京市");
			map.put("id", Long.valueOf("156696"));
			map.put("cityId", Long.valueOf("320100000000"));
			list.add(map);*/
			
			// wangzhen 2017年7月19日09:52:47   注释原因：只在南京做试点
			/*phPositionUrl += "/getcities";
			params.add("provinceId", Long.valueOf("320"));
			String sendPost = HttpClientUtils.getHttpsToPost(phPositionUrl, params);
			JSONObject json = JSON.parseObject(sendPost);
			int code = Integer.parseInt(json.get("code").toString());
			if(code == 200){
				
				list = JsonUtil.stringToList(sendPost, "data", Map.class);
				for (Map map : list) {
					//map.put("name", map.get("cityName").toString());
					
				}  
			}*/
			list = this.positionDao.getCityByPid(Long.valueOf(id));
//			for (Map map : list) {
//				map.put("name", map.get("provinceName").toString());
//			}
			
		}
		if(type == 2){
			/*phPositionUrl += "/getcounties";
			params.add("cityId", id);
			String sendPost = HttpClientUtils.getHttpsToPost(phPositionUrl, params);
			JSONObject json = JSON.parseObject(sendPost);
			int code = Integer.parseInt(json.get("code").toString());
			if(code == 200){
				list = JsonUtil.stringToList(sendPost, "data", Map.class);
				for (Map map : list) {
					map.put("name", map.get("countyName").toString());
				}
			}*/
			list = this.positionDao.getCountyByCid(Long.valueOf(id));
		}
		if(type == 3){
			/*phPositionUrl += "/gettowns";
			params.add("countyId", id);
			String sendPost = HttpClientUtils.getHttpsToPost(phPositionUrl, params);
			JSONObject json = JSON.parseObject(sendPost);
			int code = Integer.parseInt(json.get("code").toString());
			if(code == 200){
				list = JsonUtil.stringToList(sendPost, "data", Map.class);
				for (Map map : list) {
					map.put("name", map.get("townName").toString());
					map.put("id", map.get("townId").toString());
				}
			}*/
			list = this.positionDao.getTownByCid(Long.valueOf(id));
		}
		return ResultUtils.returnSuccess("查询成功", list); 
	}

	@Override
	public Position getPositionByTownId(Long townId) {
		return this.positionDao.getPositionByTownId(townId);
	}

	@Override
	public Position getCityInfoByCountyId(Long countyId) {
		return this.positionDao.getCityInfoByCountyId(countyId);
	}
	
	@Override
	public Position getAllBYCountyId(String id) {
		Position position = this.positionDao.getAllBYCountyId(Long.valueOf(id));
		return position;
	}

	@Override
	public Result contrastSaveAgreement(Long countyId, Long townId) {
		Result result = new Result();
		if(countyId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		if(townId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		Position position = this.positionDao.getAllByTownId(townId);
		Long cId = position.getCountyId();
		if(cId.equals(countyId)){
			result.setCode(0);
		}else {
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		return result;
	}

	@Override
	public Result contrastSaveAgreementAll(Long provId, Long cityId, Long countyId, Long townId) {
		Result result = new Result();
		if(countyId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		if(townId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		if(provId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		if(cityId.equals(0)){
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		Position position = this.positionDao.getAllByTownId(townId);
		Long coId = position.getCountyId();
		Long ciId = position.getCityId();
		Long prId = position.getProvinceId();
		if (coId.equals(countyId)) {
			result.setCode(0);
		} else if (ciId.equals(cityId)) {
			result.setCode(0);
		} else if (prId.equals(provId)) {
			result.setCode(0);
		} else {
			result.setCode(1);
			result.setMsg("区所在地有误，请重新选择");
			return result;
		}
		return result;
	}

	@Override
	public List<Position> getPositionByCountyId(Long countyId) {
		return this.positionDao.getPositionByCountyId(countyId);
	}

	@Override
	public Position getAllBYTownId(Long townId) {
		Position position = this.positionDao.getAllByTownId(townId);
		return position;
	}

	/**
	 * 根据社区编号调用普惠获取省市县信息接口
	 */
	@Override
	public Position getPHPositionByTownId(Long townIds) {
		
		Position position=new Position();
			
		String townId=townIds.toString();
		//加密处理
		//townId=RSACommonUtils.encryptByPrivateKey(townId, RSACommonUtils.CharSet.UTF8) ;
		
		//调用普惠获取省市县信息接口
		try {
			String phPositionUrl = (String) initParam.getProperties().get("PH_POSITION_URL");
			phPositionUrl += "/findarea";
			
			List<NameValuePair> callpayParame = new ArrayList<NameValuePair>();
			callpayParame.add(new BasicNameValuePair("townId", townId));
			
			logger.error("获取普惠区域接口,乡镇编号："+townIds+",加密后："+townId+"访问路径："+phPositionUrl+"时间："+new Date());
			String checkResult = HttpClientObject.sendPost(phPositionUrl,callpayParame);
			
			JSONObject jsonObj = JSON.parseObject(checkResult);
			Object state = jsonObj.get("code");
			JSONArray data = jsonObj.getJSONArray("data");
			Object error = jsonObj.get("msg");
			System.out.println("对接重庆区域接口返回code："+state.toString());
			if("200".equals(state.toString())){
				
				JSONObject jsonData =  data.getJSONObject(0);
				
				String provinceId = jsonData.getString("provinceId");//省级编号
				String provinceName = jsonData.getString("provinceName");//省级名称
				String cityId = jsonData.getString("cityId");//市级编号
				String cityName = jsonData.getString("cityName");//市级名称
				String countyId = jsonData.getString("countyId");//县级编号
				String countyName = jsonData.getString("countyName");//县级名称
				String townIdPH = jsonData.getString("townId");//乡级编号
				String townName = jsonData.getString("townName");//乡级名称
				//数据解密处理
				/*provinceId=RSACommonUtils.decryptByPrivateKey(provinceId, RSACommonUtils.CharSet.UTF8);
				provinceName=RSACommonUtils.decryptByPrivateKey(provinceName, RSACommonUtils.CharSet.UTF8);
				cityId=RSACommonUtils.decryptByPrivateKey(cityId, RSACommonUtils.CharSet.UTF8);
				cityName=RSACommonUtils.decryptByPrivateKey(cityName, RSACommonUtils.CharSet.UTF8);
				countyId=RSACommonUtils.decryptByPrivateKey(countyId, RSACommonUtils.CharSet.UTF8);
				countyName=RSACommonUtils.decryptByPrivateKey(countyName, RSACommonUtils.CharSet.UTF8);
				townIdPH=RSACommonUtils.decryptByPrivateKey(townIdPH, RSACommonUtils.CharSet.UTF8);
				townName=RSACommonUtils.decryptByPrivateKey(townName, RSACommonUtils.CharSet.UTF8);*/
				
				
				position.setProvinceId(Long.valueOf(provinceId));
				position.setProvinceName(provinceName);
				position.setCityId(Long.valueOf(cityId));
				position.setCityName(cityName);
				position.setCountyId(Long.valueOf(countyId));
				position.setCountyName(countyName);
				position.setTownId(Long.valueOf(townIdPH));
				position.setTownName(townName);
				
			}
			
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("获取普惠接口数据出错,乡镇编号："+townIds+","+e.getMessage()+"时间："+new Date());
			e.printStackTrace();
			
		}
		
		return position;
	}
}
