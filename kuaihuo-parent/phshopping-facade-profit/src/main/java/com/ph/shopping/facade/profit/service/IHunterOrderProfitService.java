package com.ph.shopping.facade.profit.service;


import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.MessageInfo;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO;
import com.ph.shopping.facade.profit.request.HunterDTO;
/**
 * 
 * @ClassName: IProfitService
 * @Description: 北京批发商分润的接口
 * @author 王强
 * @date 2017年3月17日 下午4:18:56
 */
public interface IHunterOrderProfitService {
	
	
	/**
	 * 
	* @Title: getAreaList
	* @Description: //获取省市县列表
	* @author 王强
	* @date  2017年4月25日 下午4:25:21
	* @param townId
	* @return
	 */
	public MessageInfo getAreaList(long townId);
	/**
	 * 
	* @Title: getTownList
	* @Description: //获取乡镇列表
	* @author 王强
	* @date  2017年4月25日 下午4:25:32
	* @param countyId
	* @return
	 */
	public MessageInfo getTownList(long countyId);
	/**
	 * 
	* @Title: getProvinces
	* @Description: 查询省列表
	* @author 王强
	* @date  2017年4月25日 下午4:25:39
	* @return
	 */
	public MessageInfo getProvinces() throws Exception;
	/**
	 * 
	* @Title: getCitys
	* @Description: 查询市列表
	* @author 王强
	* @date  2017年4月25日 下午4:25:50
	* @param provinceId
	* @return
	* @throws Exception
	 */
	public MessageInfo getCitys(long provinceId) throws Exception;
	/**
	 * 
	* @Title: getCountys
	* @Description: 查询区县列表
	* @author 王强
	* @date  2017年4月25日 下午4:26:00
	* @param cityId
	* @return
	* @throws Exception
	 */
	public MessageInfo getCountys(long cityId) throws Exception;
	/**
	 * 
	* @Title: getTowns
	* @Description: 查询乡镇列表
	* @author 王强
	* @date  2017年4月25日 下午4:26:13
	* @param countyId
	* @return
	* @throws Exception
	 */
	public MessageInfo getTowns(long countyId) throws Exception;
	

	
	
	
	/*------------------------------------------------上面未动-----------------------------------------------------------*/
	
	
	
	/**
	 * 
	* @Title: addHunterProfit
	* @Description: 新增批发商分润数据
	* @author 王强
	* @date  2017年4月25日 下午4:25:00
	* @param requests
	* @return
	 */
	public MessageInfo addHunterProfit(HunterDTO requests);
	
	/**
	 * 
	* @Title: hunterOrderProfitList
	* @Description: TODO(批发商订单分润记录List)
	* @author Mr.Dong
	* @date  2017年6月1日 下午5:36:57
	* @param hunterOrderProfitDTO
	* @return
	 */
	public Result hunterOrderProfitList(HunterOrderProfitDTO hunterOrderProfitDTO,PageBean pagebean);
	
	
	/**
	 * 
	* @Title: getOnLineOrderProfitEXCEL
	* @Description: TODO(导出批发商订单分润EXCEL)
	* @author Mr.Dong
	* @date  2017年6月2日 上午10:00:55
	* @param hunterOrderProfitDTO
	* @return
	 */
	public  Result getHunterOrderOrderProfitEXCEL(HunterOrderProfitDTO hunterOrderProfitDTO);

	/**
	 * 快火批发订单分润
	 * @param hunterOrderProfitDTO
	 * @param pagebean
	 * @return
	 */
	public Result hunterOrderProfitPageList(HunterOrderProfitDTO hunterOrderProfitDTO,PageBean pagebean);
}
