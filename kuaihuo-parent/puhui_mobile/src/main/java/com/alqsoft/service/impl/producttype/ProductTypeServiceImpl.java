package com.alqsoft.service.impl.producttype;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.producttype.ProductTypeDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.rpc.mobile.RpcProductTypeService;
import com.alqsoft.service.producttype.ProductTypeService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.ProcudtTypeVO;
import com.google.common.collect.Maps;
import com.mysql.jdbc.StringUtils;

/**
 * 商品分类
 * @author Xuejizheng
 * @date 2017-03-02 16:23
 * @since 1.8
 */
@Service
@Transactional(readOnly = true)
public class ProductTypeServiceImpl implements ProductTypeService {

    private static Logger log = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private RpcProductTypeService rpcProductTypeService;

    /**
     * 获取商品分类列表
     * @param hid 批发商ID
     * @return
     */
    @Override
    public Result getProductTypeList(Long hid,Long pid,Long rtype) {
        Result result = new Result();
        if(Objects.isNull(hid)){
          result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
          result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
          return result;
        }
        try {
            Map<String,Object> paramMap =Maps.newHashMap();
            paramMap.put("hid",hid);
            paramMap.put("pid",pid);
            paramMap.put("rtype",rtype);
            List<ProcudtTypeVO> productTypes = productTypeDao.getProductTypeList(paramMap);
            JSONArray pary = new JSONArray();
            if (Objects.isNull(pid)){
				for(ProcudtTypeVO type:productTypes){
					JSONObject pobj = new JSONObject();
					pobj.put("id",type.getId());
					pobj.put("name",type.getName());

					JSONArray ary = new JSONArray();
					Map<String,Object> map = Maps.newHashMap();
					map.put("pid",type.getId());
					map.put("hid",hid);
					paramMap.put("rtype",rtype);
					List<ProcudtTypeVO> sonproductTypes = productTypeDao.getProductTypeList(map);
					for(ProcudtTypeVO sonproductType:sonproductTypes){
						JSONObject obj = new JSONObject();
						obj.put("id",sonproductType.getId());
						obj.put("name",sonproductType.getName());
						ary.add(obj);
					}
					pobj.put("children",ary);
					pary.add(pobj);
				}
			}else{
				List<ProcudtTypeVO> sonproductTypes = productTypeDao.getProductTypeList(paramMap);
				for(ProcudtTypeVO sonproductType:sonproductTypes){
					JSONObject obj = new JSONObject();
					obj.put("id",sonproductType.getId());
					obj.put("name",sonproductType.getName());
					pary.add(obj);
				}
			}

            result.setCode(StatusCodeEnums.SUCCESS.getCode());
            result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
            result.setContent(pary);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
            return result;
        }

    }
    /***
     * 通过id获取分类，以及分类的数量
     */
	@Override
	public Result getProductTypeFirst(Long id,Integer currentPage,Integer numPage) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(id==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
	        result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
	        return result;
		}
		currentPage=(currentPage-1)*numPage;
		  try {
		    List<Map> map=productTypeDao.getAllClass(id,currentPage,numPage);
		    if(map==null){
			result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
		    }else{
			  result.setCode(StatusCodeEnums.SUCCESS.getCode());
	          result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
	          result.setContent(map);
		    }
		
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
	            result.setCode(StatusCodeEnums.ERROR.getCode());
	            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}
	/***
	 * 创建一级分类和二级分类
	 * id是批发商的id，firstType一级类级的名，secondeType二级类的名
	 * @author wudi
	 */
	public Result madeProductType(Long id, String firstType, String secondeType) {
		Result result = new Result();
		String[] split=null;
		if(firstType!=null){
			firstType=firstType.trim();
		}
		else if(StringUtils.isEmptyOrWhitespaceOnly(firstType)){
			return ResultUtils.returnError("一级类名不能为空");
		}else if (firstType.length()>20) {
			return ResultUtils.returnError("一级类名的长度不能超过20");
		}
		else if(id==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
	        result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
	        return result;
		}

		else if(secondeType!=null && !"-1".equals(secondeType)){
				 split = secondeType.split(",");
				if(split.length>10){
					return ResultUtils.returnError("第二级类个数不能超过10个");
				}
				//判断二级类名的长度不能超过20
				for(int i=0;i<split.length;i++){
					if(split[i].length()>20){
						return ResultUtils.returnError("第二级类名的长度不能超过20");
					}
				}
		}
		//保存放在rpc中,id是批发商id，firstType一级类名，secondeType二级类名
		result = rpcProductTypeService.madeProductType(id,firstType,secondeType);
	
		return result;
	}
	/***
	 * hId 是批发商id
	 * listMap 前端传过来的参数
	 * 编辑商品分类，只有批发商可以进行修改。
	 * @author wudi
	 */
	@Override
	public Result compileProductType(Long hId,String listMap) {
	
		return null;
	}
	/***
	 * 删除类级名
	 * @param cId 类级Id
	 * @param hId 批发商Id
	 */
	@Override
	public Result deleteProdcutType(Long cId,Long hId) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(cId==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
	        result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
	        return result;
		}
		try {
			Long num=null;
			//查询，如果有商品，或者-级有关联就不能删除
			ProductType productTypeById = productTypeDao.getProductTypeById(cId);
			if("null".equals(productTypeById)){
				return ResultUtils.returnError("参数异常");
			}
		        num = productTypeById.getSumProduct();
		
			if(num!=null && 0!=num){
				return ResultUtils.returnError("级别类中有商品不能删除");
			}
			//判断一级是否有关联键
			int numSon = selectProductTypeSon(cId);
			if(numSon>0){
				return ResultUtils.returnError("一级类中关联着二级不能删除");
			}
			try {
				rpcProductTypeService.deleteById(cId,hId);
			} catch (Exception e) {
				return ResultUtils.returnError("级别类中有下架商品或者以前下过单都不能删除");
			}
		    
			
			result.setCode(StatusCodeEnums.SUCCESS.getCode());
	        result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}

	/***
	 * 封装ProductType
	 * @param id
	 * @param date
	 * @param ip
	 * @param content
	 * @return wudi
	 */
	private ProductType getProductType(Long id,Date date,String ip,String content,Long idFirst,Long sortNum){
		
		ProductType type=new ProductType();
		
		Hunter hunter=new Hunter();
		hunter.setId(id);//批发商id
		type.setHunter(hunter);
		type.setCreatedTime(date);//创建时间
		type.setContent(content);//一级分类名称
		type.setCreatedIp(ip);//创建的ip
		type.setSortNum(sortNum.intValue());//创建的排序
		
		if(idFirst!=null){
			ProductType types=new ProductType();
			
			  types.setId(idFirst);
			  type.setParent(types);
		}
		return type;
	}
	/***
	 * flat 类级的上移为true，下移位false
	 * pId 类级的id
	 * return reuslt
	 */
    public Result getSortProductType(String cIds,String sortNums,Long hId){
    	Result result=new Result();
    	if(org.apache.commons.lang3.StringUtils.isBlank(cIds) ||org.apache.commons.lang3.StringUtils.isBlank(sortNums)){
    		result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
    		result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
    		result.setContent("数据不能为空");
    		return result;
    	}
    	try {
    		  String[] split = sortNums.split(",");
    		  String[] sid = cIds.split(",");
    	      int length = split.length;
    	      for(int i=0;i<length;i++){
    	    	  ProductType rightOrErrorAll = productTypeDao.getRightOrErrorAll(Long.valueOf(sid[i]),split[i], hId);
    	    	  
    	    		  rightOrErrorAll.getContent();
    	    		  rpcProductTypeService.updateProductTypeUp(Long.valueOf(sid[i]),split[i],i,hId);
    	      }
    	      result.setCode(StatusCodeEnums.SUCCESS.getCode());
    	      result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
    	      result.setContent("排序成功");
		} catch(NullPointerException nullPEx){
			
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
    	    result.setContent("cId和sortnum和hId对应的数据错误");
		}catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
    
    	return result;
    }
    /***
     * 通过一级类中的id获取对应的二级类
     */
    @Override
	public Result getSecondProductTypeList(Long cId, Long hId) {
    	Result result=new Result();
    	if(cId==null){
    		result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
    		result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
    	}
    	try {
    		List<Map<String, Object>> map = productTypeDao.getSecondProductType(cId);
    		if(map==null || map.isEmpty()){
    		result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
        	result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
        	return ResultUtils.returnSuccess("没有添加数据");
    		}else{
    			//位空的时候0为可以删除,1是有参数
    			
    		    List list=new ArrayList<>();
    			for (Map<String, Object> map2 : map) {
					if("0".equals( map2.get("sum_product").toString())){
						map2.put("delete","0");
					}else{
						map2.put("delete", "1");
					}
					list.add(map2);
				}
    		
    			result.setCode(StatusCodeEnums.SUCCESS.getCode());
    			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
    			result.setContent(list);
    		}
    		
        	
    	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
    	return result;
	}
    /***
     *销售商品的分类，规格
     *pId 商品Id
     *
     */
    @Override
	public Result saleProductType(Member member, Long pId) {
		// TODO Auto-generated method stub
    	Result result=new Result();
    	if(pId==null){
    		result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
    		result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
    	}
    	try {
    	List<Map> map1=productTypeDao.saleProductType(pId);
    	List<Map> map2=productTypeDao.saleProductStandard(pId);
    	Map map=new HashMap<>();
    	map.put("classify", map1);
    	map.put("standard", map2);
    	map.put("pId", pId);
    		result.setCode(StatusCodeEnums.SUCCESS.getCode());
        	result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
        	result.setContent(map);
    	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
    	return result;
	}
    /**
     * 销售商品的规格
     */
    @Override
	public Result saleProductStandard(Member member, Long pId) {
    	// TODO Auto-generated method stub
    	Result result=new Result();
    	if(pId==null){
    		result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
    		result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
    	}
    	try {
    	List<Map> map=productTypeDao.saleProductStandard(pId);
    	if(CollectionUtils.isNotEmpty(map)){
    	result.setCode(StatusCodeEnums.SUCCESS.getCode());
    	result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
    	result.setContent(map);
    	}else{
    		result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
        	result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
    	}
    	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
    	return result;
    }
	@Override
	public ProductType getProductTypeById(Long id) {
		// TODO Auto-generated method stub
		return productTypeDao.getProductTypeById(id);
	}

	@Override
	public int selectProductTypeSon(Long id) {
		// TODO Auto-generated method stub
		return productTypeDao.selectProductTypeSon(id);
	}
	
	/**
	 * 商品管理下的添加分类，获取一二级分类
	 */
	@Override
	public Result getAllProductType(Long hunterid) {
		Map<String,Object> resultdata =new HashMap<String,Object>();
		List<Map<String,Object>> first = getFirstProductType(hunterid);
		for(Map<String,Object> data : first){
			data.put("second", this.getSecondProductType(Long.valueOf(data.get("id").toString())));
		}
		resultdata.put("first", first);
		return ResultUtils.returnSuccess("查询成功", resultdata);
	}
	@Override
	public List<Map<String, Object>> getFirstProductType(Long hunterid) {
		// TODO Auto-generated method stub
		return productTypeDao.getFirstProductType(hunterid);
	}
	@Override
	public List<Map<String, Object>> getSecondProductType(Long firstid) {
		// TODO Auto-generated method stub
		return productTypeDao.getSecondProductType(firstid);
	}
	/***
	 * 获取一级分类和一级类对应的所有二级类
	 */
	public Result getFirstAndSecondeProductType(Long cId, Long hId) {
		// TODO Auto-generated method stub
		Result result=new Result();
		if(cId==null ){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
    		result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
			return result;
		}
		//通过cId查询对应的内容二级
		List<Map<String, Object>> map = productTypeDao.getSecondProductType(cId);
		//通过对应的id和批发商id获取对应的数据
		ProductType firstProductById = productTypeDao.getFirstProductById(cId,hId);
		if(firstProductById==null){
			result.setCode(StatusCodeEnums.ERROR.getCode());
		    result.setMsg(StatusCodeEnums.ERROR.getMsg());
		    result.setContent("参数异常批发商没有关联此类级");
		    return result;
		}

	      List list=new ArrayList<>();
            if(!map.isEmpty()){
			//位空的时候0为可以删除,1是有参数
			
			for (Map<String, Object> map2 : map) {
				if("0".equals(map2.get("sum_product").toString())){
					map2.put("delete","0");
				}else{
					map2.put("delete", "1");
				}
				list.add(map2);
			}
            }
		Map maps=new HashMap<>();
		if(firstProductById!=null){
		maps.put("FirstId", firstProductById.getId());
		maps.put("FirstContent", firstProductById.getContent());
		//maps.put("FirstSortNum", firstProductById.getSortNum());
		}
		Map mapss=new HashMap<>();
		mapss.put("first",maps);
		mapss.put("seconde",map);
		
		result.setCode(StatusCodeEnums.SUCCESS.getCode());
		result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
		result.setContent(mapss);
		return result;
	}
	/***
	 * 测试回滚made
	 */
	@Transactional
	public Result compileAndMadeProductType(Long hId,String listMap){
		Result compileAndMadeProductType = rpcProductTypeService.compileAndMadeProductType(hId,listMap);
		return compileAndMadeProductType;
	}
}
