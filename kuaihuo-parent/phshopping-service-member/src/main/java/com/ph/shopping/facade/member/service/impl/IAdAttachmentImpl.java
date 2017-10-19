package com.ph.shopping.facade.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.AdAttachmentMapper;
import com.ph.shopping.facade.member.service.IAdAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取首页的数据
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年8月28日 下午5:13:46
 * 
 */
@Component
@Service(version = "1.0.0")
public class IAdAttachmentImpl implements IAdAttachment {

	@Autowired
	private AdAttachmentMapper adAttachmentMapper;

	@Override
	public Result getShowDataAdAttachment(String longitude, String latitude, Integer page, Integer pagesize,String localAddress) {
		// TODO Auto-generated method stub
		//首页轮播图
		List<Map> dataCarousel = adAttachmentMapper.getDataCarousel();
		//默认二级分类
		List<Map> dataSecondIndustry = adAttachmentMapper.getDataSecondIndustry();
		//默认一级分类
		List<Map> dataSecondIndustry2 = adAttachmentMapper.getDataFirstIndustry();

		//获取图片展示10张
		List<Map> dataAttachmentPicture = adAttachmentMapper.getDataAttachmentPicture(longitude, latitude, page, pagesize);
		for (int i = 0; i < dataAttachmentPicture.size(); i++) {
			Long id = (Long) dataAttachmentPicture.get(i).get("id");
			List<Map> map = adAttachmentMapper.getIndustryNameById(id);
			dataAttachmentPicture.get(i).put("merchantTypeName", map);
			double round = (double) dataAttachmentPicture.get(i).get("distance");

			double double1 = round / 1000;
			BigDecimal bg = new BigDecimal(double1);
			double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (double1 > 0.01) {
				dataAttachmentPicture.get(i).put("distance", double3 + "/km");
			} else {
				dataAttachmentPicture.get(i).put("distance", "<0.01 km");

			}
		}
		List<Map> list = new ArrayList<>();
		List<Map> lists = new ArrayList<>();
		//通过省市级来进行展示列表
		/*if (!localAddress.equals("0")) {
			//进行截取
			String[] string = localAddress.split(",");
			//如果一二级相等就是直辖市,如果一二级不相等并不是直辖市

			//首页展示10,isRecommendType,3是代理，1是运营
			// List<Map> cItyProvinceList1=null;
			// List<Map> cItyProvinceList2=null;


			if ((string[0].trim()).equals(string[1].trim())) {
				//通过市获取对应的标识
				Map cItyProvince = adAttachmentMapper.getCItyProvince(string[0].trim());
				//省的标识
				String province = cItyProvince.get("provinceId").toString();

				list = adAttachmentMapper.getCItyProvinceList(province, 3, 0, pagesize, longitude, latitude);//代理置顶，
				pagesize = pagesize - list.size();
				lists.addAll(list);
				if (pagesize > 0) {
					list = adAttachmentMapper.getCItyProvinceList(province, 1, 0, pagesize, longitude, latitude);//运营置顶
					pagesize = pagesize - list.size();
                lists.addAll(list);
				}
				if (pagesize > 0) {//代理未置顶
					list = adAttachmentMapper.getCItyProvinceListIsNull(province, 3, 0, pagesize, longitude, latitude);
					pagesize = pagesize - list.size();
				lists.addAll(list);
				}
				if (pagesize > 0) {//运营未置顶
					list = adAttachmentMapper.getCItyProvinceListIsNull(province, 1, 0, pagesize, longitude, latitude);
				lists.addAll(list);
				}


			} else {
				//获取省
				Map cItyProvinceAndCity = adAttachmentMapper.getCItyProvinceAndCity(string[0].trim(), string[1].trim());
				//省的标识
				String provinceId = cItyProvinceAndCity.get("provinceId").toString();//省标识
				String cityId = cItyProvinceAndCity.get("cityId").toString();//市的标识
				list = adAttachmentMapper.getCItyANdProvinceListById(provinceId, cityId, 3, 0, pagesize, longitude, latitude);//代理置顶，
				pagesize = pagesize - list.size();
				lists.addAll(list);
				if (pagesize > 0) {//运营置顶
					list = adAttachmentMapper.getCItyANdProvinceListById(provinceId, cityId, 1, 0, pagesize, longitude, latitude);//运营置顶
					pagesize = pagesize - list.size();
                lists.addAll(list);
				}
				if (pagesize > 0) {//代理未置顶
					list = adAttachmentMapper.getCItyANdProvinceListByIdIsNull(provinceId, cityId, 3, 0, pagesize, longitude, latitude);
					pagesize = pagesize - list.size();
					lists.addAll(list);
				}
				if (pagesize > 0) {//运营未置顶
					list = adAttachmentMapper.getCItyANdProvinceListByIdIsNull(provinceId, cityId, 1, 0, pagesize, longitude, latitude);
				    list.addAll(list);
				}


			}


		}
		if (lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				Long id = (Long) lists.get(i).get("id");
				List<Map> map = adAttachmentMapper.getIndustryNameById(id);
				lists.get(i).put("merchantTypeName", map);
				double round = (double) lists.get(i).get("distance");

				double double1 = round / 1000;
				BigDecimal bg = new BigDecimal(double1);
				double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (double1 > 0.01) {
					lists.get(i).put("distance", double3 + "/km");
				} else {
					lists.get(i).put("distance", "<0.01 km");

				}
			}
		}*/
		try {
			Map map = new HashMap();
			map.put("adattachment", dataCarousel);
			map.put("secondImage", dataSecondIndustry);
			map.put("firstImage", dataSecondIndustry2);
			map.put("distanceImage", dataAttachmentPicture);//通过距离展示店面
			//map.put("localAddress", list);//通过省市展示店面
			map.put("localAddress",dataAttachmentPicture);//通过距离展示店面
			Result result = new Result();
			result.setCode("1");
			result.setData(map);
			result.setSuccess(true);
			result.setMessage("获取成功");
			return result;
		} catch (Exception e) {
			Result result = new Result();
			result.setCode("0");
			result.setMessage("获取成功");
			return null;
		}

	}

    @Override
	public Result getNearByShopping(String longitude, String latitude, Integer page, Integer pagesize, Long industry1, Long industry2) {
		// TODO Auto-generated method stub
		//获取第一个的id
		Result result = new Result();

		List<Map> dataAttachmentPictureById = adAttachmentMapper.getDataAttachmentPictureById(industry1, industry2, longitude, latitude, page, pagesize);
		for (int i = 0; i < dataAttachmentPictureById.size(); i++) {
			Long id =(Long)dataAttachmentPictureById.get(i).get("id");
			//List<Map> map=	adAttachmentMapper.getIndustryNameById(id);
			//通过条件查询数据，当前用户的id，行业1的id，行业2的id
			List<Map> map= adAttachmentMapper.getIndustryNameByIndustryId(id,industry1,industry2);
			dataAttachmentPictureById.get(i).put("merchantTypeName",map);
			double round = (double) dataAttachmentPictureById.get(i).get("distance");

			double double1 = round / 1000;
			BigDecimal bg = new BigDecimal(double1);
			double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (double1 > 0.01) {
				dataAttachmentPictureById.get(i).put("distance", double3 + "/km");
			} else {
				dataAttachmentPictureById.get(i).put("distance", "<0.01 km");

			}
		}

		result.setCode("1");
		result.setSuccess(true);
		result.setData(dataAttachmentPictureById);
		return result;
	}

	@Override
	public Result getAllIndustry(Integer type, Long parentId) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (type == 1) {//获取一级分类
			List<Map> allFirst = adAttachmentMapper.getAllFirst();
			result.setCode("1");
			result.setData(allFirst);
		} else if (type == 2) {//获取二级分类
			List<Map> allSecond = adAttachmentMapper.getAllSecond(parentId);
			result.setCode("1");
			result.setData(allSecond);
		}
		return result;
	}

	@Override
	public Result findData(String datas, Integer page, Integer pagesize, String longitude, String latitude) {
		Result result = new Result();
		datas = "%" + datas + "%";
		List<Map> map = adAttachmentMapper.findData(datas, page, pagesize, longitude, latitude);
		for (int a = 0; a < map.size(); a++) {
			Long id =(Long)map.get(a).get("id");

			List<Map> maps=	adAttachmentMapper.getIndustryNameById(id);

			map.get(a).put("merchantTypeName",maps);
			double round = (double) map.get(a).get("distance");

			double double1 = round / 1000;
			BigDecimal bg = new BigDecimal(double1);
			double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		if (double1 > 0.01) {
				map.get(a).put("distance", double3 + "/km");
			}else {
				map.get(a).put("distance", "<0.01 km");

			}

		}

		result.setData(map);
		result.setCode("1");
		return result;
	}

	@Override
	public Result getAppVersion(Integer type, String client_type) {
		Result result = new Result();
		try {
			List<Map> appVersion = adAttachmentMapper.getAppVersion(type, client_type);
			result.setCode("1");
			result.setData(appVersion);
			result.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			result.setCode("0");
		}

		return result ;
	}
}