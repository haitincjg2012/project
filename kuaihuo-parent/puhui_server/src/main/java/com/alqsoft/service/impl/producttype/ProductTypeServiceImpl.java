package com.alqsoft.service.impl.producttype;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.json.JsonUtil;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.producttype.ProductTypeDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.rpc.mobile.RpcProductTypeService;
import com.alqsoft.service.producttype.ProductTypeService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.ProcudtTypeSaveVO;

@Transactional(readOnly = true)
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	private static final Logger logger = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
	@Autowired
	private ProductTypeDao productTypeDao;
	@Autowired
	private RpcProductTypeService rpcProductTypeService;

	@CacheEvict(key = "'alq_product_type'+#arg0", value = "alq_product_type")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Cacheable(key = "'alq_product_type'+#arg0", value = "alq_product_type")
	@Override
	public ProductType get(Long arg0) {
		// TODO Auto-generated method stub
		return productTypeDao.findOne(arg0);
	}

	@CacheEvict(key = "'alq_product_type'+#arg0.id", value = "alq_product_type")
	@Override
	public ProductType saveAndModify(ProductType arg0) {
		// TODO Auto-generated method stub
		return productTypeDao.save(arg0);
	}

	@Override
	public ProductType madeProductTypeFirst(ProductType productType) {
		// TODO Auto-generated method stub
		return productTypeDao.save(productType);
	}

	@Override
	public int updateProductType(Long pId, String newContent) {
		// TODO Auto-generated method stub
		return productTypeDao.updateProductType(pId, newContent);
	}

	@Override
	public int deleteById(Long pId, Long hId) {
		// TODO Auto-generated method stub
		return productTypeDao.deleteById(pId, hId);
	}

	@Override
	@Transactional
	public ProductType getRowLock(Long id) {
		// TODO Auto-generated method stub
		return productTypeDao.getRowLock(id);
	}

	@Override
	public int updateProductTypeUp(Long cId, String split, Integer sortNum, Long hId) {
		// TODO Auto-generated method stub
		return productTypeDao.updateProductTypeUp(cId, split, sortNum, hId);
	}

	/**
	 * 根据商品分类id查询该分类下的商品数量
	 */
	@Override
	public int getProductCountByTypeId(Long kindid) {
		// TODO Auto-generated method stub
		return productTypeDao.getProductCountByTypeId(kindid);
	}

	/***
	 * 创建修改分类
	 */
	@Transactional
	public Result compileAndMadeProductType(Long hId, String listMap) {
		// TODO Auto-generated method stub
		Result result = new Result();
		// listMap是前端穿过的参数,name是要修改的类名，id是要创建的id,
		// name,id
		try {
			ProcudtTypeSaveVO vo = JsonUtil.jsonToObject(listMap, ProcudtTypeSaveVO.class);
			JSONArray ARY = JSONObject.parseObject(listMap).getJSONArray("productTypeVos");
			int num = ARY.size();
			Long parentId = null;
			String parentName = null;
			String name = null;
			String names = null;
			// 判断是否符合要求
			if (num > 11) {
				return ResultUtils.returnError("二级商品数量最多不能超过10个");
			}
			for (int i = 0; i < num; i++) {
				JSONObject obj = (JSONObject) ARY.get(i);
				// 获取一级分类数据
				if (i == 0) {
					// 获取一级类参数，id,name
					String pid = String.valueOf((Integer) obj.get("id"));
					if (org.apache.commons.lang3.StringUtils.isNotBlank(pid)) {
						parentId = Long.valueOf(pid);
						parentName = (String) obj.get("content");
						// 判断是否已经存在该类名在本批发商中
						int findRightOrError = productTypeDao.findRightOrError(hId, parentName, parentId);
						// 判断一级类中是否存商品
						Long productTypeContainProduct = productTypeDao.getProductTypeContainProduct(parentId);
						if (productTypeContainProduct > 0) {
							return ResultUtils.returnError("一级类中含有上架或下架商品都不能创建二级分类");
						} else if (findRightOrError > 0) {
							return ResultUtils.returnError("这个类名已经存在请换一个");
						}
						try {
							if (parentName.length() > 20) {
								return ResultUtils.returnError("类名的长度不能超过20");
							}
						} catch (Exception e) {
							return ResultUtils.returnError("类名不能为空");
						}

					}

				}
				// 获取二级分类
				else {
					Long pid = null;
					try {
						pid = Long.parseLong(obj.get("id").toString());
					} catch (Exception e) {
						// TODO: handle exception
						pid = 0L;
					}
					try {
						name = (String) obj.get("content");
					} catch (Exception e) {
						// TODO: handle exception
						name = null;
					}
					// 判断是否为空
					if (name == null || "".equals(name)) {
						// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtils.returnError("类级名不能为空");
					}
					if (name.length() > 20) {
						// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return ResultUtils.returnError("类级名长度不能超过20");
					}

				}

			}
			// 保存
			for (int a = 1; a < num; a++) {

				JSONObject obj = (JSONObject) ARY.get(a);
				Long pids = null;
				try {
					pids = Long.parseLong(obj.get("id").toString());
				} catch (Exception e) {
					// TODO: handle exception
					pids = 0L;
				}
				try {
					names = (String) obj.get("content");
				} catch (Exception e) {
					// TODO: handle exception
					names = null;
				}
				if (pids <= 0) {
					// 增加二级分类,一级分类的Id
					// id,是批发商hId，ip创建的ip,idFirst是第一级类的id，num2是类 型的sortNum
					name = (String) obj.get("content");
					Long num2 = productTypeDao.getCount(parentId);
					// 保存二级出去重复，自定义id创建的时候判断是否已经存在
				
					int findMade = productTypeDao.findRightOrError(hId, name, parentId);
					if (findMade <= 0) {
						ProductType types = getProductType(hId, new Date(), null, name, parentId, num2);
						rpcProductTypeService.madeProductType(types);
					}

				} else {
					// 修改二级类名
					name = (String) obj.get("content");
					int findUpadte = productTypeDao.findRightOrError(hId, name, pids);
					if (findUpadte <= 0) {

						productTypeDao.updateProductType(pids, name);
					}

				}
			}
			// 保存一级类名
			productTypeDao.updateProductType(parentId, parentName);
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error(e.getMessage(), e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());

		}

		return result;
	}

	/***
	 * 封装ProductType
	 * 
	 * @param id
	 * @param date
	 * @param ip
	 * @param content
	 * @return wudi
	 */
	private ProductType getProductType(Long id, Date date, String ip, String content, Long idFirst, Long sortNum) {

		ProductType type = new ProductType();

		Hunter hunter = new Hunter();
		hunter.setId(id);// 批发商id
		type.setHunter(hunter);
		type.setCreatedTime(date);// 创建时间
		type.setContent(content);// 一级分类名称
		type.setCreatedIp(ip);// 创建的ip
		type.setSortNum(sortNum.intValue());// 创建的排序

		if (idFirst != null) {
			ProductType types = new ProductType();

			types.setId(idFirst);
			type.setParent(types);
		}
		return type;
	}

	@Transactional
	public Result madeProductType(Long hId, String firstType, String secondeType) {
		Result result = new Result();
		String[] split = null;
		// TODO Auto-generated method stub
		Long pId = -1L;// 定义一个虚假id，犹豫
		List<ProductType> findRightOrError = productTypeDao.findRightOrError(hId, firstType);
		if (findRightOrError.size() > 0 || !findRightOrError.isEmpty()) {
			return ResultUtils.returnError("这个一级类名已经存在请换一个");
		}

		try {
			// 获取ip
			Long idFirst = null;
			String ip = null;

			Long num1 = productTypeDao.getCount();
			// id批发商，创建时间，ip，firstType是第一类的名，idFirst：num1是sortNum

			ProductType type = getProductType(hId, new Date(), ip, firstType, idFirst, num1);
			// 一级目录
			ProductType madeProductType = rpcProductTypeService.madeProductType(type);
			// 二级目录
			if (secondeType != null && !"-1".equals(secondeType)) {
				Long num2 = productTypeDao.getCount();
				split = secondeType.split(",");
				if (split.length > 10) {
					return ResultUtils.returnError("二级分类不能超过10个");
				}
				if (split != null && split.length > 0) {
					for (int i = 0; i < split.length; i++) {
						num2 = num2 + 1;
						idFirst = madeProductType.getId();
						ProductType types = getProductType(hId, new Date(), ip, split[i], idFirst, num2);
						// 判断是否有重复的类名设定一个id出去
						Long cId = -1L;
						String name = split[i].trim();
						Long secondTypeName = productTypeDao.getSecondTypeName(idFirst, hId, name);
						if (secondTypeName <= 0) {
							rpcProductTypeService.madeProductType(types);
						}
					}
				}
			}

			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(StatusCodeEnums.SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		return result;
	}
}