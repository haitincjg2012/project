package com.alqsoft.service.impl.huntersalelist;

import com.alqsoft.dao.huntersalelist.HunterSaleListDao;
import com.alqsoft.service.huntersalelist.HunterSaleListService;
import com.alqsoft.service.impl.shopcart.ShopCartServiceImpl;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.utils.StatusCodeEnums;
import org.alqframework.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月1日 下午12:02:34 批发商销售产品列表，0为销量，1跟新，2，价格，false为降序，true升序
 * id销售是0，跟新是1，价格是2，flat为true是升序，false降序，hunterName是批发商的id，currentPage当前页，numPage是当前页的数量
 * 销售和时间只有降序，价格有升序和降序
 */
@Service
@Transactional(readOnly = true)
public class HunterSaleListServiceImpl implements HunterSaleListService {

	private static Logger logger = LoggerFactory.getLogger(ShopCartServiceImpl.class);
	@Autowired
	private HunterSaleListDao hunterSaleListDao;

	@Override
	public Result findAllProductSale(Integer id, boolean flat, Long hId,Integer currentPage,Integer numPage) {
		// TODO Auto-generated method stub
		 
		String results=null;
		
		String sort=null;
		//销售只有降序
		if(id==0){
			results="spf.sale_num";
			sort="DESC";
		}
		//时间只用降序
		else if(id==1){
			results="spf.created_time";
			sort="DESC";
		}else if(id==2&& flat){
			//升序
			results="spf.sale_price";
			sort="ASC";
		}else {
			//降序
			results="spf.sale_price";
			sort="DESC";
		}
	Result result=new Result();
	
     
	
	try {
		if(hId==null){
			result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
			result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
			return result;
		}
		currentPage=(currentPage-1)*numPage;
		List<Map> map=hunterSaleListDao.findAllProductSaleList(results,sort,hId,currentPage,numPage);
		map.forEach(e->e.put("sale_num", NumberFormat.getFormatNumber(String.valueOf(e.get("sale_num")))));
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
}
