package com.alqsoft.service.huntersalelist;

import org.alqframework.result.Result;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月1日 上午11:56:49
 * 批发商销售商品的展示
 * id销售是0，跟新是1，价格是2，flat为true是降序，false为升序，hunterName是批发商的id，currentPage当前页，numPage是当前页的数量
 */
public interface HunterSaleListService {
	
	/**
	 *通过销售量展示批发商的商品
	 */
	public Result findAllProductSale(Integer id,boolean flat,Long hId,Integer currentPage,Integer numPage);
	
	

}
