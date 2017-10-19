package com.ph.shopping.facade.member.service.impl;

import com.ph.shopping.facade.member.entity.PhMemberOrderOnlineSku;
import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MemberIntoMerchantMapper;
import com.ph.shopping.facade.member.dto.DishDto;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;
import com.ph.shopping.facade.member.dto.ShopCart;
import com.ph.shopping.facade.member.entity.Merchant;
import com.ph.shopping.facade.member.service.IMemberIntoMerchantTwoService;
import com.ph.shopping.facade.mapper.MemberIntoMerchantTwoMapper;
import com.ph.shopping.facade.member.vo.MerchantTimeSectionVO;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员进入商户获取信息
 * @author Lizhihua
 * @date 2017-08-27 21:43
 *
 */

@Component
@Service(version = "1.0.0")
public class MemberIntoMerchantTwoServiceImpl implements IMemberIntoMerchantTwoService {

	@Autowired
	private MemberIntoMerchantMapper memberIntoMerchantMapper;
	@Autowired
	private MemberIntoMerchantTwoMapper memberIntoMerchantTwoMapper;
	/**
	 * 获取商户基本信息
	 */
	@Override
	public Result getMerchantInfoTwo(Long merchantId) {
		//获取基本信息
		Merchant merchant= memberIntoMerchantMapper.getMerchantInfo(merchantId);
		//查询评价条数
		Long count=memberIntoMerchantMapper.getMerchantCount(merchant.getUserId());
		if(count!=null){
			merchant.setCount(count);
		}
		//获取图片信息
		List<String> url=memberIntoMerchantMapper.getMerchantImg(merchantId);
		if(!url.isEmpty()){
			merchant.setUrl(url);
		}
		if(merchant==null||"".equals(merchant)){
			return ResultUtil.getResult(RespCode.Code.NO_MERCHANTMESSAGE);
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,merchant);
	}

	/**
	 * 获取商户的消费时段
	 * @param merchantId
	 * @return
	 */
	@Override
	public Result getMerchantTimeTwo(Long merchantId) {
		//获取商家消费时间段
		List<MerchantTimeSectionVO> merchantTime=memberIntoMerchantTwoMapper.getMerchantTimeTwo(merchantId);
		if(merchantTime.size()==0||merchantTime==null){
			return  ResultUtil.getResult(RespCode.Code.NO_ORDER);
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS,merchantTime);
	}

	/**
	 * 获取商户名下的菜品信息
	 */
	@Override
	public Result getDishAllTwo(MerchantDishDTO dish) {
		Date hopeServiceDate=null;
		List<MerchantDishDTO> dishType=null;
		Map<String, Long> map=new HashMap<String, Long>();
		Map<String,Long> map2=new  HashMap<String, Long>();
		Map<String,Long> map3=new  HashMap<String, Long>();
		//如果type为0  获取商家自己建立的菜品类型
		if(dish.getType()==0){
			dishType=memberIntoMerchantMapper.getDishType0(dish);
		}
		else if(dish.getType()==1){//如果为1  所有商家的餐位固定有打包
			//查询菜品(餐位)类型
			dishType=memberIntoMerchantMapper.getDishType0(dish);
			//将打包放入餐位分类中
			MerchantDishDTO dabao=new MerchantDishDTO();
			dabao.setType(1);
			dabao.setTypeId((long)108);
			dabao.setTypeName("打包");
			dishType.add(dabao);
		}
		if(dishType==null||dishType.isEmpty()){
			return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_DISHTYPE);
		}
		//转换时间类型用来判断包间是否被预定
		if(dish.getHopeServiceDate()!=null&&!"".equals(dish.getHopeServiceDate())){
			try {
				hopeServiceDate=DateUtil.parseDateTime(dish.getHopeServiceDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}


		for(int i=0;i<dishType.size();i++){
			//遍历查询分类下的菜品信息
			map.put("typeId", dishType.get(i).getTypeId());
			map.put("merchantId", dish.getMerchantId());
			List<DishDto> dishs=memberIntoMerchantMapper.getDishByTypeId(map);
			//============将打包单独查出来放到dishs中=====================//
			if(dishType.get(i).getTypeId()==108){
				DishDto dabao=memberIntoMerchantMapper.getDabao(dishType.get(i).getTypeId());
				dishs.add(dabao);
			}
			//将查询出来的菜品遍历插入到类型实体类中
			dishType.get(i).setList(dishs);

			for(int f=0;f<dishs.size();f++){
				//处理菜品金额    /10000保留两位小数
				dishs.get(f).setMoney(DoubleUtils.formatRound(Double.parseDouble(dishs.get(f).getMoney())/10000, 2));
				//查询菜品图片
				List<String> address=memberIntoMerchantMapper.getDishImg(dishs.get(f).getId());
				if(address!=null){
					dishs.get(f).setDishImg(address);			//将图片插入到菜品实体中
				}

				if(hopeServiceDate!=null){				//判断预定时间不为空
					//读取当前用户的订单信息   //给状态赋值  如果用户已经预定赋值为3
					map2.put("memberId",dish.getMemberId());
					map2.put("dishId",dishs.get(f).getId());
					List<PhMemberOrderOnlineSku> onlineSku=memberIntoMerchantMapper.getOrderByMember(map2);
					for(int j=0;j<dishs.size();j++){

						if(dishs.get(j).getType()==1&&dishs.get(j).getId()!=-1) {//判断是餐位  -1占位打包
							//获取所有包间的时间    判断当前餐位是否让别的用户预定
							List<DishDto> timeFromSE = memberIntoMerchantMapper.getTimeByid(dishs.get(j).getId());
							//获取包间的所有的时间  用来判断用户本身是否预定该餐位
							map3.put("dishId", dishs.get(j).getId());
							map3.put("memberId", dish.getMemberId());
							map3.put("merchantId", dish.getMerchantId());
							List<DishDto> timeForThree = memberIntoMerchantMapper.getTimeForThreeByid(map3);

							if (timeForThree != null && !timeForThree.isEmpty()) {
								//判断当前餐位有没有被用户本身预定 设置为3
								for (int h = 0; h < timeForThree.size(); h++) {
									//判断时间
									if (!onlineSku.isEmpty() && DateUtil.belongCalendar(hopeServiceDate, timeForThree.get(h).getHopeServiceDate(), timeForThree.get(h).getPredictServiceDate())) {
										//判断菜品ID相等的话则设置为3
										if (timeForThree.get(h).getId().equals(dishs.get(j).getId()) || timeForThree.get(h).getId() == dishs.get(j).getId()) {
											for (int k = 0; k < onlineSku.size(); k++) {
												dishs.get(j).setStatus(3);  //会员自己已经预定

											}
										}
									}
								}
							}

								if (timeFromSE != null && !timeFromSE.isEmpty()) {
									//判断当前餐位是否让别的用户预定   设置为2
									for (int g = 0; g < timeFromSE.size(); g++) {

										//判断预计到达时间在不在这个范围
										if (DateUtil.belongCalendar(hopeServiceDate, timeFromSE.get(g).getHopeServiceDate(), timeFromSE.get(g).getPredictServiceDate()) || DateUtil.belongCalendar(hopeServiceDate, timeFromSE.get(g).getPredictServiceDate(), timeFromSE.get(g).getHopeServiceDate())) {
											dishs.get(j).setStatus(2);
										} else {
											if (dishs.get(j).getStatus() == 2) {//如果已经为2  则一直为2
												dishs.get(j).setStatus(2);
											} else {
												if (dishs.get(j).getStatus() == 3) {
													dishs.get(j).setStatus(3);
												} else {
													dishs.get(j).setStatus(0);
												}
											}
										}
									}
								}
							}
						}
				}
			}

		}


		return ResultUtil.getResult(RespCode.Code.SUCCESS,dishType);
	}

	/**
	 * 获取用户预定的包间接口
	 * @param order
	 * @return
	 */
	@Override
	public Result getOnlineOrderDish(MerchantDishDTO order) {
		Result result=ResultUtil.getResult(RespCode.Code.SUCCESS);
		//查询包间总价格和图片地址   这里的id就是订单id orderId  切记
		List<DishDto> orders=memberIntoMerchantMapper.getOnlineOrderDish(order);
		if(orders.size()==0||orders==null){
			result=ResultUtil.getResult(RespCode.Code.NO_ORDER);
		}
		//查询菜品总数量

		for(int i=0;i<orders.size();i++){
			Long count=memberIntoMerchantMapper.getOnlineOrderDishCount(orders.get(i).getId());
			Long countCart=memberIntoMerchantMapper.getShopCartDishCount(orders.get(i).getId());
			//将数量设置到实体中
			//总的数量
			orders.get(i).setCount(count+countCart);
			//查询购物车中菜品的总价格
			Long ShopCartTotalPrice=memberIntoMerchantMapper.getShopCartTotalPrice(orders.get(i).getId());
			//查询订单中菜品的总价格
			Long OrderTotalPrice=memberIntoMerchantMapper.getOrderTotalPrice(orders.get(i).getId());
			orders.get(i).setMoney(String.valueOf(ShopCartTotalPrice+OrderTotalPrice));//购物车和订单都有价格
			//转换价格
			orders.get(i).setMoney(DoubleUtils.formatRound(Double.parseDouble(orders.get(i).getMoney())/10000, 2));
		}
		result=ResultUtil.getResult(RespCode.Code.SUCCESS,orders);
		return result;
	}
	/**
	 * 包间菜品回显  购物车中的和订单中的
	 * @param orderId
	 * @return
	 * @lzh
	 */
	@Override
	public Result getOnlineOrderAndShopCartDish(Long orderId) {
		Result result=ResultUtil.getResult(RespCode.Code.NO_ORDER);
		//查询购物车中的菜品信息
		List<DishDto> dishShopCartMessage=memberIntoMerchantMapper.getDishForShopCart(orderId);
		//查询订单中的菜品信息
		List<PhMemberOrderOnlineSku> dishOrderMessage=memberIntoMerchantMapper.getDishForOrder(orderId);
		//查询订单中的餐位信息
		DishDto Dish=memberIntoMerchantMapper.getDishByOrderId(orderId);

		Map<String,Object> map=new HashedMap();
		if(dishShopCartMessage.size()==0){	//购物车为空
			for(int i=0;i<dishOrderMessage.size();i++){		//金额转换
				dishOrderMessage.get(i).setMoney( dishOrderMessage.get(i).getMoney()/10000);
			}
			map.put("Order",dishOrderMessage);
			map.put("shopCart",null);
			map.put("dish",Dish);
		}else if(dishOrderMessage.size()==0){ //订单中为空
			for(int j=0;j<dishShopCartMessage.size();j++){		//金额转换
				Long money=Long.parseLong(dishShopCartMessage.get(j).getMoney())/10000;
				dishShopCartMessage.get(j).setMoney(money.toString());
			}
			map.put("Order",null);
			map.put("shopCart",dishShopCartMessage);
			map.put("dish",Dish);
		}else if(dishOrderMessage.size()==0&&dishShopCartMessage.size()==0){  //都为空
			map.put("dish",Dish);
			return ResultUtil.getResult(RespCode.Code.NO_ORDER,map);
		}else{
			for(int i=0;i<dishOrderMessage.size();i++){		//金额转换
				dishOrderMessage.get(i).setMoney( dishOrderMessage.get(i).getMoney()/10000);
			}
			for(int j=0;j<dishShopCartMessage.size();j++){		//金额转换
				Long money=Long.parseLong(dishShopCartMessage.get(j).getMoney())/10000;
				dishShopCartMessage.get(j).setMoney(money.toString());
			}
			map.put("Order",dishOrderMessage);   //都不为空
			map.put("shopCart",dishShopCartMessage);
			map.put("dish",Dish);
		}
		result=ResultUtil.getResult(RespCode.Code.SUCCESS,map);

		return result;
	}
	/**
	 * 点击菜品显示菜品详情
	 * @param dishId
	 * @return
	 * @lzh
	 */
	@Override
	public Result getDishInfoByDishId(Long dishId) {
		Result result=ResultUtil.getResult(RespCode.Code.NO_ORDER);
		//获取菜品基本信息
		DishDto dishInfo=memberIntoMerchantMapper.getDishInfoByDishId(dishId);
		dishInfo.setMoney(String.valueOf(Long.parseLong(dishInfo.getMoney())/10000));
		//获取菜品图片信息
		List<String> address=memberIntoMerchantMapper.getDishImg(dishId);
		if(dishInfo==null){
			return result;
		}
		if(address.size()!=0){
			dishInfo.setDishImg(address);
		}
		result=ResultUtil.getResult(RespCode.Code.SUCCESS,dishInfo);

		return result;
	}

}
