package com.alqsoft.service.impl.hunterproductdetail;

import com.alqsoft.dao.productdetail.ProductDetailDao;
import com.alqsoft.entity.product.Product;
import com.alqsoft.service.hunterproductdetail.HunterProductDetailService;
import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.ProductDetailVO;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月2日 上午9:27:30
 * 商品详情的展示
 */

@Service
@Transactional(readOnly=true)
public class HunterProductDetailServiceImpl implements HunterProductDetailService {

	private static org.slf4j.Logger logger=LoggerFactory.getLogger(HunterProductDetailServiceImpl.class);

	@Autowired
	private ProductDetailDao productDetailDao;

	@Override
	public Result getProductDetailText(Long productId,Long mId) {
		// TODO Auto-generated method stub

		Result result=new Result();

		try {
			if(productId==null){
				result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
				result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
				return result;
			}
			Map<String, Object> maps=new HashedMap();
			//判断用户是否登录，不登录不用查询，登录会查询返回收藏状态

			List<Map> map = productDetailDao.getProductDetailText(productId);
			map.forEach(e->e.put("sale_num",NumberFormat.getFormatNumber(String.valueOf(e.get("sale_num")))));
			//销售商品的的轮播图片
			List<Map>  map3=productDetailDao.saleProductImage(productId);
			maps.put("ImageUrl", map3);
			if(productId!=null){
				//String commentDetail =(String) getProductComment(productId).get("commentDetail");
				Map<Object, String> productComment = getProductComment(productId);
				//maps.put("commentDetail",commentDetail);
				maps.put("createTime", productComment.get("created_time"));
				maps.put("phone", productComment.get("phone"));
				maps.put("nickName", productComment.get("nickname"));
				maps.put("content", productComment.get("content"));
				maps.put("LogoAdress",productComment.get("logoAddress"));
			}
			maps.put("pId", productId);


			Map mapAll = getProductNumCommentAll(productId);
			//String countComment = getCountComment().get("countComment");
			String niceNum;
			String badNum;
			String commonNum;
			try {
				niceNum = NumberFormat.getFormatNumber(String.valueOf(mapAll.get("niceNum")));
			} catch (Exception e) {
				// TODO: handle exception
				niceNum="0";
			}
			try {
				badNum =NumberFormat.getFormatNumber(String.valueOf(mapAll.get("badNum")));
			} catch (Exception e) {
				// TODO: handle exception
				badNum="0";
			}
			try {
				commonNum =NumberFormat.getFormatNumber(String.valueOf(mapAll.get("comNum")));
			} catch (Exception e) {
				// TODO: handle exception
				commonNum="0";
			}

			Integer countComment=(Integer.parseInt(niceNum)+Integer.parseInt(badNum)+Integer.parseInt(commonNum));

			maps.put("text", map);
			maps.put("countComment", NumberFormat.getFormatNumber(String.valueOf(countComment)));
			maps.put("niceNum", niceNum);
			maps.put("badNum",badNum);
			maps.put("commonNum", commonNum);



			if(mId>0){
				//查询返回结构,type中的0是收藏
				Long type= productDetailDao.findCollectionType(productId,mId);
				if (type>0) {
					maps.put("type", "0");
				}else{
					maps.put("type", "1");
				}
			}

			int size =	map.size();
			if (size <= 0) {
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());

			}  else{
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
				result.setContent(maps);
			}


		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}finally{
			return result;
		}
	}

	/**
	 * 总的评价数
	 * @return map
	 */
	private  Map<Object, String> getCountComment(){

		Long num = productDetailDao.getCountComment();

		Map<Object, String> map=new HashedMap();

		map.put("countComment", num.toString());

		return map;

	}

	/**
	 * 好的评价
	 * */
	private Map<Object, String> getNiceCommentNum(){

		Long num = productDetailDao.getNiceCommentNum();

		Map<Object, String> map=new HashedMap();

		map.put("niceNum", num.toString());

		return map;
	}

	/**
	 * 坏的评价
	 * */
	private Map<Object, String> getBadCommentNum(){

		Long num = productDetailDao.getBadCommentNum();

		Map<Object, String> map=new HashedMap();

		map.put("badNum", num.toString());

		return map;
	}

	/**
	 * 一般的评价
	 * */
	private Map<Object, String> getCommonCommentNum(){

		Long num = productDetailDao.getCommonCommentNum();

		Map<Object, String> map=new HashedMap();

		map.put("commonNum", num.toString());

		return map;
	}

	/**
	 * 该商品的所有的评价
	 * */
	@SuppressWarnings("rawtypes")
	private  Map<Object, String> getProductComment(Long productId){

		Map<Object, String> maps=new HashedMap();

		List<Map> map = productDetailDao.getProductComment(productId);

		if(map==null || map.size()<=0){

			maps.put("commentDetail", "没有评论");
			return maps;
		}else{
			//maps.put("commentDetail", map.get(0).toString());
			return map.get(0);
		}



	}
	/***
	 * 获取所有的商品评价
	 * @param productId
	 * @return
	 */
	private Map getProductNumCommentAll(Long productId){

		return  productDetailDao.getProductNumCommentAll(productId);
	}

	/***
	 * 图片详情展示
	 */
	@Override
	public Result getProductDetailImage(Long productId) {

		// TODO Auto-generated method stub

		Result result=new Result();

		try {
			if(productId==null){
				result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
				result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
				return result;
			}
			List<Map> map = productDetailDao.getProductDetailImage(productId);

			int size =	map.size();
			if (size <= 0) {
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());

			}  else{
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
				result.setContent(map);
			}


		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}finally{
			return result;
		}
	}

	/**
	 * 此接口默认的参数是批发商id。
	 *
	 * */
	@Override
	public Result getHunterBaseMessage(Long hId) {

		Result result=new Result();

		try {
			if(hId==null){
				result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
				result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
				return result;
			}
			Map<String, Object> map = productDetailDao.getHunterBaseMessage(hId);
			if(map==null ){
				return ResultUtils.returnError("没有该批发商");
			}
			//数字转换
			map.put("num", NumberFormat.getFormatNumber(String.valueOf(map.get("num"))));
			map.put("goodCommentNum",NumberFormat.getFormatNumber(String.valueOf(map.get("goodCommentNum"))));
			map.put("foucsNum",NumberFormat.getFormatNumber(String.valueOf(map.get("foucsNum"))));
			map.put("countNum",NumberFormat.getFormatNumber(String.valueOf(map.get("countNum"))));


			//拼接省市
			String provice=map.get("province")==null?"":map.get("province").toString();
			String  city =map.get("city")==null?"":map.get("city").toString();
			String  contryName=map.get("countyname")==null?"":map.get("countyname").toString();
			String position;
			if("北京市".equals(provice)||"天津市".equals(provice)||"上海市".equals(provice)||"重庆市".equals(provice)){
		    	/*position=provice+contryName;*/
				position=provice+provice;
				map.put("position", position);
			}else {
				position=provice+city;
				map.put("position", position);
			}

			int size =	map.size();
			if (size <= 0) {
				result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());

			}  else{
				Integer level = Integer.parseInt(map.get("level")==null?"0":map.get("level").toString());

				if (1==level) {
					map.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
				}else if(2==level){
					map.put("level", HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
				}else if (3==level) {
					map.put("level", HunterLevelEnums.LT_LEVEL_TOP.getData());
				}else if(0==level){
					map.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
				}
				result.setCode(StatusCodeEnums.SUCCESS.getCode());
				result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
				result.setContent(map);
			}


		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}finally{
			return result;
		}
	}

	/**
	 * 商品编辑时回显得商品详情
	 */
	@Override
	public List<Map<String, Object>> getProductDetailByProductId(Long productid) {
		// TODO Auto-generated method stub
		return productDetailDao.getProductDetailByProductId(productid);
	}
	/**
	 * 商品规格类型
	 */
	@SuppressWarnings("unused")
	public Result saleProductType(Long pId){
		Result result=new Result();
		if(pId==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
			result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
		}
		try {

			List<Map> map1=productDetailDao.saleProductType(pId);
			List<Map> map2=productDetailDao.saleProductStandard(pId);

			@SuppressWarnings("rawtypes")
			List<Map> saleMap = productDetailDao.getProuctSaleMessage(pId);
			//获取图片，价格，库存，起批量
			Map<Object, Object> map=new HashMap<>();
			if(saleMap.size()>0){
				map.put("imageUrl",saleMap.get(0).get("imageurl") );
				map.put("SaleSum",saleMap.get(0).get("SaleSum") );
				map.put("startNum", saleMap.get(0).get("startNum"));
			}

			map.put("classify", map1);
			map.put("standard", map2);
			map.put("pId", pId);


			if (!map2.isEmpty()) {
				map.put("defaultSalePrice", map2.get(0).get("salePrice"));
				map.put("defaultNum", map2.get(0).get("KuNum"));
			}

			result.setCode(StatusCodeEnums.SUCCESS.getCode());
			result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
			result.setContent(map);

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(StatusCodeEnums.ERROR.getCode());
			result.setMsg(StatusCodeEnums.ERROR.getMsg());
		}
		return result;
	}

	@Override
	public void textDetailsUrl(Long pId, Model model) {

		List<Map> map = productDetailDao.getProductDetailImage(pId);
		if (map!=null ) {
			model.addAttribute("textDetails",map);
		}

	}

	@Override
	public List<ProductDetailVO> getProductDetailVoByProductId(Long productId) {
		return productDetailDao.getProductDetailVoByProductId(productId);
	}
}
