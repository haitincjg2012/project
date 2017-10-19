package com.ph.shopping.facade.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.Main;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.Product;
import com.google.common.collect.Maps;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.DishMapper;
import com.ph.shopping.facade.mapper.IMemberOrderOnlineMapper;
import com.ph.shopping.facade.mapper.ShopCartMapper;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IShopCartService;
import com.ph.shopping.facade.spm.entity.Merchant;

import cm.ph.shopping.facade.order.dto.ShopCartDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnline;
import cm.ph.shopping.facade.order.entity.ShopCart;
import cm.ph.shopping.facade.order.vo.DishVO;
import cm.ph.shopping.facade.order.vo.ShopCartVO;

/**
 * 购物车实现
 * 
 * @author gaoge
 *
 */
@Component
@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class ShopCartServiceImpl implements IShopCartService {
	// 日志
	private static final Logger log = LoggerFactory.getLogger(ShopCartServiceImpl.class);

	@Autowired
	private ShopCartMapper shopCartMapper;
	@Autowired
	private IMemberOrderOnlineMapper iMemberOrderOnlineMapper;
	@Autowired
	private DishMapper dishMapper;

	@Reference(version = "1.0.0")
	private IMemberService iMemberService;

	/**
	 * 添加入购物车
	 * 
	 * @param memberid
	 *            用户id
	 * @param dishid
	 *            商品ID
	 * @param num
	 *            购买数量
	 * @param merchantId
	 *            商户id
	 * @return
	 */
	@Override
	@Transactional
	public Result add(Long memberid, Long dishid, Long num, Long merchantId, String hopetime, Long type) {
		Result result = new Result();

		if (Objects.isNull(memberid) || Objects.isNull(dishid) || Objects.isNull(num) || Objects.isNull(merchantId)) {
			result.setCode("-1");
			result.setMessage("参数异常");
			return result;
		}

		try {

			// 判断商品是否存在
			DishVO dishVO = dishMapper.findDishById(dishid);
			if (dishVO == null) {
				return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
			}
			// 判断用户是否存在
			Member member = iMemberService.getMemberById(memberid);
			if (member == null) {
				return ResultUtil.getResult(RespCode.Code.NO_MERCHANTMESSAGE);
			}

			// 判断期望时间不为空
			if (hopetime == null || "".equals(hopetime)) {
				return ResultUtil.getResult(RespCode.Code.NO_HOPETIME);
			}

			Map<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("memberid", memberid);
			paramMap.put("dishid", dishid);
			paramMap.put("merchantid", merchantId);
			ShopCartVO oldCart = shopCartMapper.getShopCart(paramMap);

			// 新增
			if (oldCart == null) {
				ShopCartDTO cart = new ShopCartDTO();
				cart.setMemberId(memberid);
				cart.setBuyNum(num);
				cart.setType(type);
				cart.setDishId(dishid);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = sdf.parse(hopetime);
				if (type == 1) {
					// 查询营业时间以及非营业时间并进行判断
					Merchant merchantTime = iMemberOrderOnlineMapper.getMerchantTime(merchantId);
					if (merchantTime != null) {
						if (DateUtil.belongCalendar(date, merchantTime.getCloseBeginTime(),
								merchantTime.getCloseEndTime())) {
							// 如果在非营业时间返回
							return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
						}
						// 判断时间在营业时间之内
						String hopeTime = DateUtil.dateToStr(date, 8);// 截取预计到达时间
						long hopeTimes = Long.valueOf(hopeTime.replaceAll("[-\\s:]", "")); // 截取字符串转换成long
						long a = Long.valueOf(merchantTime.getOpenBeginTime().replaceAll("[-\\s:]", ""));
						long b = Long.valueOf(merchantTime.getOpenEndTime().replaceAll("[-\\s:]", ""));
						if (hopeTimes < a || hopeTimes > b) {
							return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_OPEN);
						}

					}

					// 判断预计时间之内没有被预定
					if (type == 1 && dishid != -1) {// 先将包间id写成-1占坑
						// 获取时间段
						List<PhMemberOrderOnline> timeFromSE = iMemberOrderOnlineMapper.getTimeByid(dishid);
						//获取餐位保留时间
						Long hopeTime=iMemberOrderOnlineMapper.getHopeTimeByDishId(dishid);
						/**
						 * 判断期望时间之内没有预定的包间
						 */
						for (int t = 0; t < timeFromSE.size(); t++) {
							if (DateUtil.belongCalendar(date, timeFromSE.get(t).getHopeServiceDate(),
									timeFromSE.get(t).getPredictServiceDate())||DateUtil.belongCalendar(date,DateUtil.JDay_Second(timeFromSE.get(t).getHopeServiceDate(),hopeTime*60),timeFromSE.get(t).getHopeServiceDate())) {
								return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
							}
						}
					}
				}
				// 当加入的是菜品时，需要判断用户是否已经选择了包间
				if (type == 0) {
					List<Integer> shopcarType = shopCartMapper.getShopcarType(paramMap);
					if (!shopcarType.contains(1) && !shopcarType.contains(-1)) {
						return ResultUtil.getResult(RespCode.Code.CHOOSE_ROOM);
					}
				}

				cart.setHopesDate(date);
				cart.setMerchantId(merchantId);
				cart.setCreatedTime(new Date());
				shopCartMapper.insertShopToCar(cart);
				result.setCode(RespCode.Code.SUCCESS.getCode());
				result.setSuccess(true);
				result.setMessage("添加购物车成功");
				return result;
				// 更新
			} else {
				// 如果是0的话就删除
				if (num == 0) {
					shopCartMapper.deleteByPrimaryKey(oldCart.getId());
					result.setSuccess(true);
					result.setMessage("删除成功");
					result.setCode(RespCode.Code.SUCCESS.getCode());
					return result;
				}

				if (oldCart.getIsDelete() == 1) {
					result.setSuccess(false);
					result.setMessage("商品已下架");
					return result;
				}

				ShopCartDTO shopCart = new ShopCartDTO();
				shopCart.setId(oldCart.getId());
				shopCart.setId(oldCart.getId());
				shopCart.setBuyNum(num);
				shopCart.setMemberId(memberid);
				shopCart.setDishId(dishid);
				shopCart.setType(type);
				shopCart.setMerchantId(merchantId);
				/*
				 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
				 */
				/*
				 * if (hopetime != null && !"".equals(hopetime)) { Date date =
				 * sdf.parse(hopetime); shopCart.setHopesDate(date); }
				 */
				shopCart.setUpdateTime(new Date());
				shopCartMapper.updateShopCart(shopCart);
				result.setSuccess(true);
				result.setCode(RespCode.Code.SUCCESS.getCode());
				result.setMessage("更新购物车成功");
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setCode("0");
			result.setMessage("接口调用失败");
			return result;
		}
	}

	@Override
	public Result batchUpdate(Long id, String cids, String nums) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result getShopCartCount(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO
	/**
	 * uid 用户id cid--dishid 商品id num 更改的数量
	 */
	@Override
	@Transactional
	public Result update(Long uid, Long cid, Long num,Long orderId) {
		if (Objects.isNull(uid) || Objects.isNull(cid) || Objects.isNull(num)) {
			return ResultUtil.getResult(RespCode.Code.MERCHANT_NO_DISH);
		}

		// 判断商品是否存在
		DishVO dishVO = dishMapper.findDishById(cid);
		if (dishVO == null) {
			return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
		}
		// 判断用户是否存在
		Member member = iMemberService.getMemberById(uid);
		if (member == null) {
			return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
		}

		Map<String, Object> map = Maps.newHashMap();
		map.put("uid", uid);
		map.put("cid", cid);
		map.put("orderId",orderId);
		try {
			ShopCartVO shopCartVO = shopCartMapper.getShopCartById(map);
			if (shopCartVO == null) {
				return ResultUtil.getResult(RespCode.Code.ERROR_PARAM, "对应购物车信息不存在.");
			}
			Long spid = shopCartVO.getDishId();
			ShopCartDTO shopCart = new ShopCartDTO();
			shopCart.setId(shopCartVO.getId());
			shopCart.setMemberId(uid);// 关联memberid
			shopCart.setBuyNum(num);
			shopCart.setType(0l);
			shopCart.setDishId(shopCartVO.getDishId());
			shopCart.setMerchantId(shopCartVO.getMerchantId());
			shopCartMapper.updateShopCart(shopCart);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}

	/**
	 * uid member 商户id cid dishid 商品id
	 */
	@Override
	@Transactional
	public Result delete(Long uid, Long cid,Long orderId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("uid", uid);
		map.put("cid", cid);
		map.put("orderId",orderId);
		try {
			ShopCartVO shopCartVO = shopCartMapper.getShopCartById(map);
			if (shopCartVO != null) {
				Long memberid = shopCartVO.getMemberId();
				if (memberid != null) {
					if (uid.equals(memberid)) {
						shopCartMapper.deleteByPrimaryKey(shopCartVO.getId());

						return ResultUtil.getResult(RespCode.Code.SUCCESS, "删除成功");
					}
					return ResultUtil.getResult(RespCode.Code.FAIL, "购物车和用户信息不匹配.");
				}
			}
			return ResultUtil.getResult(RespCode.Code.FAIL, "购物车信息不存在.");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Result getShopCartList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 添加入购物车
	 *
	 * @param memberId
	 *            用户id
	 * @param dishid
	 *            商品ID
	 * @param num
	 *            购买数量
	 * @param merchantId
	 *            商户id
	 * @return
	 */
	@Override
	@Transactional
	public Result addTwo(Long memberId, Long dishid, Long num, Long merchantId,Long orderId) {
		Result result = new Result();

		if (Objects.isNull(memberId) || Objects.isNull(dishid) || Objects.isNull(num) || Objects.isNull(merchantId)|| Objects.isNull(orderId)||num<0) {
			result.setCode("-1");
			result.setMessage("参数异常");
			return result;
		}

		try {

			// 判断商品是否存在
			DishVO dishVO = dishMapper.findDishById(dishid);
			if (dishVO == null) {
				return ResultUtil.getResult(RespCode.Code.NO_MERMBER);
			}
			// 判断用户是否存在
			Member member = iMemberService.getMemberById(memberId);
			if (member == null) {
				return ResultUtil.getResult(RespCode.Code.NO_MERCHANTMESSAGE);
			}

			Map<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("memberid", memberId);
			paramMap.put("dishid", dishid);
			paramMap.put("merchantid", merchantId);
			paramMap.put("orderid", orderId);
			ShopCartVO oldCart = shopCartMapper.getShopCart2(paramMap);

			// 新增
			if (oldCart == null) {
				ShopCartDTO cart = new ShopCartDTO();
				cart.setMemberId(memberId);
				cart.setBuyNum(num);
				cart.setDishId(dishid);
				cart.setType((long)0);
				cart.setMerchantId(merchantId);
				cart.setCreatedTime(new Date());
				cart.setOrderId(orderId);
				shopCartMapper.insertShopToCar(cart);
				result.setCode(RespCode.Code.SUCCESS.getCode());
				result.setSuccess(true);
				result.setMessage("添加购物车成功");
				return result;
				// 更新
			} else {
				// 如果是0的话就删除
				if (num == 0) {
					shopCartMapper.deleteByPrimaryKey(oldCart.getId());
					result.setSuccess(true);
					result.setMessage("删除成功");
					result.setCode(RespCode.Code.SUCCESS.getCode());
					return result;
				}

				if (oldCart.getIsDelete() == 1) {
					result.setSuccess(false);
					result.setMessage("商品已下架");
					return result;
				}

				ShopCartDTO shopCart = new ShopCartDTO();
				shopCart.setId(oldCart.getId());
				shopCart.setId(oldCart.getId());
				shopCart.setBuyNum(num);
				shopCart.setMemberId(memberId);
				shopCart.setDishId(dishid);
				shopCart.setType((long)0);
				shopCart.setMerchantId(merchantId);
				/*
				 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
				 */
				/*
				 * if (hopetime != null && !"".equals(hopetime)) { Date date =
				 * sdf.parse(hopetime); shopCart.setHopesDate(date); }
				 */
				shopCart.setUpdateTime(new Date());
				shopCartMapper.updateShopCart(shopCart);
				result.setSuccess(true);
				result.setCode(RespCode.Code.SUCCESS.getCode());
				result.setMessage("更新购物车成功");
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setCode("0");
			result.setMessage("接口调用失败");
			return result;
		}
	}
}
