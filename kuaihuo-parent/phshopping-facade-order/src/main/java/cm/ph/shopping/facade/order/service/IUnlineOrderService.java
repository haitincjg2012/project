package cm.ph.shopping.facade.order.service;


import cm.ph.shopping.facade.order.dto.*;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
/**
 * @项目：phshopping-facade-order
 *
 * @描述：线下订单接口
 *
 * @作者： Mr.Dong
 *
 * @创建时间：2017年3月10日
 *
 * @Copyright @2017 by Mr.Dong
 */
public interface IUnlineOrderService {


	public Result addUnlineOrder(AddMemberOrderUnlineDTO addMemberOrderUnlineDTO, boolean payFlage) throws Exception ;

	/**
     * 线下订单VoList
	 * @param phMemberOrderUnlineDTO
	 * @param pagebean
	 * @return Result
     * @author Mr.Dong
     * @createTime 2017年3月23日
     */
	public Result getUnLineOrderVoList(PhMemberOrderUnlineDTO phMemberOrderUnlineDTO, PageBean pagebean);

	/**
   	 * 更新订单状态
	 * @param phMemberOrderUnline
     * @author Mr.Dong
     * @return int
     * @createTime 2017年3月23日
     */
	public int updateUnlineOrder(PhMemberOrderUnline phMemberOrderUnline);
	/**
	 * 商户线上订单查询列表（仅显示在此商户提货的线上订单，以会员选择的商户提货点地址为准）
	 * @param queryMerchantOrderTakeDTO
	 * @param  pagebean
     * @author Mr.Dong
     * @return Result
     * @createTime 2017年3月23日
     *//*
	public Result getOnlineOrderVoList(QueryMerchantOrderTakeDTO queryMerchantOrderTakeDTO, PageBean pagebean);
	*/

	/**
	 * @author: 张霞
	 * @description：通过id获取线下订单
	 * @createDate: 15:44 2017/5/31
	 * @param id
	 * @return: com.ph.shopping.common.util.result.Result
	 */
	public Result getPhMemberOrderUnlineById(Long id);

	/**
	 * @author: 张霞
	 * @description：取消线下订单
	 * @createDate: 15:43 2017/5/31
	 * @param cancelUnlineOrderDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 */
	public Result cancelUnlineOrder(CancelUnlineOrderDTO cancelUnlineOrderDTO);

	/**
	 * @author: 张霞
	 * @description：线下订单获取订单详情
	 * @createDate: 11:00 2017/6/12
	 * @param queryMemberOrderUnlineDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	public Result getUnlineOrderDetail(QueryMemberOrderUnlineDTO queryMemberOrderUnlineDTO);
	
	/**
	 * 
	 * @Title: getOrderSatusByBarcode   
	 * @Description: 根据二维码得到订单状态   
	 * @param: @param barcodeMark
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getOrderSatusByBarcode(String barcodeMark);

	/**   
	 * @Title: getPhMemberOrderUnlineByOrderNo   
	 * @Description: 根据订单号获取订单详情   
	 * @param: @param orderNo
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws   
	 */ 
	public Result getPhMemberOrderUnlineByOrderNo(String orderNo);


	/**
	 * 天天返、刮红包
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	Result  rewardUnlineOrder(RewardUnlineOrderDTO rewardUnlineOrderDTO) throws Exception;

	/**
	 * 确认刮红包
	 * @param rewardUnlineOrderDTO
	 * @return
	 */
	Result confirmRewardUnlineOrder(RewardUnlineOrderDTO rewardUnlineOrderDTO) throws Exception;
}
