package com.alqsoft.service.impl.searchhunter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.searchhunter.SearchHunterDao;
import com.alqsoft.service.searchhunter.SearchHunterService;
import com.alqsoft.utils.StatusCodeEnums;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年3月29日 下午11:39:46
 * 
 */
@Service
@Transactional(readOnly = true)
public class SearchHunterServiceImpl implements SearchHunterService {

	private static Logger logger = LoggerFactory.getLogger(SearchHunterServiceImpl.class);

	@Autowired
	private SearchHunterDao searchHunterDao;

	@SuppressWarnings("unchecked")
	@Override
	public Result getSearchHunterByNameOrProduct(String name, Integer currentPage, Integer numPage, Integer sort,
			String longitude, String latitude) {
		// TODO Auto-generated method

		Result result = new Result();
		StringBuffer stringBuffer = new StringBuffer();
		if (name == null) {
			return ResultUtils.returnError("请输入要搜寻的批发商昵称或者商品名");
		}
		// 三元运算
		Integer current = (currentPage - 1) * numPage;
		List<Map<String, Object>> hunterByName = null;

		name = "'" + "%" + name + "%" + "'";
		if (sort == 1) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是num
			try {
				hunterByName = searchHunterDao.getHunterByNum(name, current, numPage);
			} catch (Exception e) {
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		} else if (sort == 2) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是好的评价
			try {
				hunterByName = searchHunterDao.getHunterByGoodNum(name, current, numPage);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		} else if (sort == 3) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是当前用户的经纬度
			if ("lo".equals(longitude) || "la".equals(latitude)) {
				return ResultUtils.returnError("当前用户的经纬度不能为空");
			}
			try {
				hunterByName = searchHunterDao.getHunterByDistance(name, current, numPage, longitude, latitude);

			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		}

		List list = new ArrayList<>();
		try {

			if (hunterByName != null && hunterByName.size() > 0) {
				for (int i = 0; hunterByName.size() > i; i++) {
					// stringBuffer窃取

					// Long
					// hId=Long.valueOf(((Map<String,Object>)(hunterByName.get(i)).get("hId")).toString());
					Map<String, Object> map = hunterByName.get(i);
					Long hId = (Long) map.get("hId");

					List<Map> productByName = searchHunterDao.getProductByName(name, hId);
					productByName.forEach(e->e.put("sale_num",NumberFormat.getFormatNumber(String.valueOf(e.get("sale_num")))));
					// 批发商的基本信息
					Map hunterMessage = searchHunterDao.getHunterMessage(hId);
					// 符合要求添加用户和批发商的距离
					if (sort == 3) {
						double round = (double) hunterByName.get(i).get("distance");
                        
						double double1 = round / 1000;
						BigDecimal bg = new BigDecimal(double1);
						try {
							
						
						double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						if (double1 > 0.01) {
							hunterMessage.put("distance", double3 + " km");
						} else {
							hunterMessage.put("distance", "<0.01 km");

						}
						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
					}
					hunterMessage.put("hId", hId);
					// 拼接省市
					String provice = hunterMessage.get("province") == null ? ""
							: hunterMessage.get("province").toString();
					String city = hunterMessage.get("city") == null ? "" : hunterMessage.get("city").toString();
					String contryName = hunterMessage.get("countyname") == null ? ""
							: hunterMessage.get("countyname").toString();
					String position;
					if ("北京市".equals(provice) || "天津市".equals(provice) || "上海市".equals(provice)
							|| "重庆市".equals(provice)) {
						position = provice + contryName;
						hunterMessage.put("position", position);
					} else {
						position = provice + city;
						hunterMessage.put("position", position);
					}
					Integer level = Integer
							.valueOf(hunterMessage.get("level") == null ? "0" : hunterMessage.get("level").toString());

					if (1 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
					} else if (2 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
					} else if (3 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
					} else if (0 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
					}
					hunterMessage.put("goodCommentNum", NumberFormat.getFormatNumber(String.valueOf(hunterMessage.get("goodCommentNum"))));
					hunterMessage.put("num", NumberFormat.getFormatNumber(String.valueOf(hunterMessage.get("num"))));
					hunterMessage.put("product", productByName);

					list.add(hunterMessage);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("异常：" + e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());

			return result;
		}
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent(list);

		return result;
	}

	@Override
	public Result getSearchProductByName(Long hId, String name, Integer currentPage, Integer numPage) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (name == null) {
			return ResultUtils.returnError("请输入要搜寻的商品名");
		}
		Integer current = (currentPage - 1) * numPage;
		name = "'" + "%" + name + "%" + "'";

		try {
			// 模糊查询获取商品的id
			List<Map> maps = searchHunterDao.getSearchProductByName(hId, name, current, numPage);
			maps.forEach(e->e.put("sale_num",NumberFormat.getFormatNumber(String.valueOf(e.get("sale_num")))));
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(maps);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}

		return result;
	}

	@Override
	public Result getSearchHunterAddtudeByNameOrProduct(String name, Integer currentPage, Integer numPage, Integer sort,
			String longitude, String latitude) {
		// TODO Auto-generated method

		Result result = new Result();
		if (name == null) {
			return ResultUtils.returnError("请输入要搜寻的批发商昵称或者商品名");
		}
		// 三元运算
		Integer current = (currentPage - 1) * numPage;

		List<Map<String, Object>> hunterByName = null;

		name = "'" + "%" + name + "%" + "'";
		if (sort == 1) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是num
			try {
				hunterByName = searchHunterDao.getHunterByNum(name, current, numPage);
			} catch (Exception e) {
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		} else if (sort == 2) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是好的评价
			try {
				hunterByName = searchHunterDao.getHunterByGoodNum(name, current, numPage);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		} else if (sort == 3) {
			// 搜寻批发商，通过商品模糊查询获取批发商Id排序是当前用户的经纬度
			if ("lo".equals(longitude) || "la".equals(latitude)) {
				return ResultUtils.returnError("当前用户的经纬度不能为空");
			}
			try {
				hunterByName = searchHunterDao.getHunterByDistance(name, current, numPage, longitude, latitude);

			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result.setCode(StatusCodeEnums.ERROR.getCode());
				result.setMsg(StatusCodeEnums.ERROR.getMsg());
				return result;
			}

		}

		List list = new ArrayList<>();
		try {

			if (hunterByName != null && hunterByName.size() > 0) {
				for (int i = 0; hunterByName.size() > i; i++) {
					Map<String, Object> map = hunterByName.get(i);
					Long hId = (Long) map.get("hId");

					List<Map> productByName = searchHunterDao.getProductByName(name, hId);
					//销量格式化
					productByName.forEach(e->e.put("sale_num",NumberFormat.getFormatNumber(String.valueOf(e.get("sale_num")))));
					// 批发商的基本信息
					Map hunterMessage = searchHunterDao.getHunterMessageAddTude(hId,longitude,latitude);
					//每个批发商都要展示出距离	
					double round = (double)hunterMessage.get("distance");
                        
						double double1 = round / 1000;
						BigDecimal bg = new BigDecimal(double1);
						try {
						double double3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						if (double1 > 0.01) {
							hunterMessage.put("distance", double3 + " km");
						} else {
							hunterMessage.put("distance", "<0.01 km");

						}
						} catch (Exception e) {
							logger.error(e.getMessage(),e);
						}
					
					hunterMessage.put("hId", hId);
					// 拼接省市
					String provice = hunterMessage.get("province") == null ? ""
							: hunterMessage.get("province").toString();
					String city = hunterMessage.get("city") == null ? "" : hunterMessage.get("city").toString();
					String contryName = hunterMessage.get("countyname") == null ? ""
							: hunterMessage.get("countyname").toString();
					String position;
					if ("北京市".equals(provice) || "天津市".equals(provice) || "上海市".equals(provice)
							|| "重庆市".equals(provice)) {
						position = provice + contryName;
						hunterMessage.put("position", position);
					} else {
						position = provice + city;
						hunterMessage.put("position", position);
					}
					Integer level = Integer
							.valueOf(hunterMessage.get("level") == null ? "0" : hunterMessage.get("level").toString());

					if (1 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
					} else if (2 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
					} else if (3 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
					} else if (0 == level) {
						hunterMessage.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
					}

					hunterMessage.put("goodCommentNum", NumberFormat.getFormatNumber(String.valueOf(hunterMessage.get("goodCommentNum"))));
					hunterMessage.put("num", NumberFormat.getFormatNumber(String.valueOf(hunterMessage.get("num"))));
					hunterMessage.put("product", productByName);

					list.add(hunterMessage);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("异常：" + e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());

			return result;
		}
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent(list);

		return result;
	
	}

}
