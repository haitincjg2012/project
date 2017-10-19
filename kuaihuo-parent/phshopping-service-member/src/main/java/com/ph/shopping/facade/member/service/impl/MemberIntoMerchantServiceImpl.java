package com.ph.shopping.facade.member.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.ph.shopping.facade.member.service.IMemberIntoMerchantService;

/**
 * 会员进入商户获取信息
 * @author Lizhihua
 * @date 2017-08-27 21:43
 *
 */

@Component
@Service(version = "1.0.0")
public class MemberIntoMerchantServiceImpl implements IMemberIntoMerchantService {
	
	@Autowired
	private MemberIntoMerchantMapper memberIntoMerchantMapper;
	/**
	 * 获取商户基本信息
	 */
	@Override
	public Result getMerchantInfo(Long merchantId) {
		//获取基本信息
		Merchant merchant= memberIntoMerchantMapper.getMerchantInfo(merchantId);
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
	 * 获取商户名下的菜品信息
	 */
	@Override
	public Result getDishAll(MerchantDishDTO dish) {
		Date hopeServiceDate=null;
		List<MerchantDishDTO> dishType=null;
		//如果type为0  获取商家自己建立的菜品类型
		if(dish.getType()==0){
			 dishType=memberIntoMerchantMapper.getDishType0(dish);
		}
		else if(dish.getType()==1){//如果为1  所有商家的餐位是固定的
		//查询菜品(餐位)类型
			dishType=memberIntoMerchantMapper.getDishType(dish.getType());
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
			Map<String, Long> map=new HashMap<String, Long>();
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
				
				
				//读取当前用户的购物车信息   //给状态赋值  如果在购物车中赋值为3
				List<ShopCart> shopCart=memberIntoMerchantMapper.getShopCart(dish.getMemberId());
				if(!shopCart.isEmpty()){
					for(int k=0;k<shopCart.size();k++){
						if(shopCart.get(k).getDishId()==dishs.get(f).getId()){
							dishs.get(f).setStatus(3);
						}
					
					}
			}
				
			if(hopeServiceDate!=null){				//判断预定时间不为空
				
				for(int j=0;j<dishs.size();j++){			
					
					if(dishs.get(j).getType()==1&&dishs.get(j).getId()!=-1){//判断是餐位  -1占位打包
						//获取所有包间的时间
						List<DishDto> timeFromSE=memberIntoMerchantMapper.getTimeByid(dishs.get(j).getId());
						//获取当前菜品的保留时间
						Long hopetime=dishs.get(j).getHopeTime();
						if(timeFromSE!=null&&!timeFromSE.isEmpty()&&hopetime!=null){
							for(int g=0;g<timeFromSE.size();g++){
								//判断预计到达时间在不在这个范围
								if(DateUtil.belongCalendar(hopeServiceDate, timeFromSE.get(g).getHopeServiceDate(), timeFromSE.get(g).getPredictServiceDate())||DateUtil.belongCalendar(hopeServiceDate,DateUtil.JDay_Second(timeFromSE.get(g).getHopeServiceDate(),hopetime*60),timeFromSE.get(g).getHopeServiceDate())){
									dishs.get(j).setStatus(2);
								}else{
							    	if(dishs.get(j).getStatus()==2){//如果已经为2  则一直为2
										dishs.get(j).setStatus(2);
									}else{
										dishs.get(j).setStatus(1);
									}
								}
							}
						}
						//读取当前用户的购物车信息
//						List<ShopCart> shopCart=memberIntoMerchantMapper.getShopCart(dish.getMemberId());
//						if(!shopCart.isEmpty()){
//							for(int k=0;k<shopCart.size();k++){
//								if(shopCart.get(k).getDishId()==dishs.get(j).getId()){
//									dishs.get(j).setStatus(3);
//								}
//							
//							}
						}
					}
					
				}
			}
			
		
//			if(dishType.get(i).getList().isEmpty()){
//				return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_DISH,dishType);
//			}
		}
		
		
		return ResultUtil.getResult(RespCode.Code.SUCCESS,dishType);
	}

}
