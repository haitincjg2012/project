package com.phshopping.api.controller.shopcar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IShopCartService;
import com.phshopping.api.controller.dto.ShopCartDTO;

@Controller
@RequestMapping("api/member/shopcart")
public class ShopCartController {
	private static Logger log = LoggerFactory.getLogger(ShopCartController.class);

	@Reference(version = "1.0.0")
	private IShopCartService iShopCartService;

	/**
	 * 获取购物车列表
	 * 
	 * @param member
	 * @return
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "cart-list", method = RequestMethod.POST)
	public Result getShopCartList(Long memberid, Long merchantid) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberid", memberid);
		map.put("merchantid", merchantid);
		Result result = iShopCartService.getShopCartList(map);
		return result;
	}

	/**
	 * 修改
	 *
	 * @param member
	 *            用户
	 * @param cid
	 *            购物车ID
	 * @param num
	 *            商品数量
	 * @return
	 */
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Result saveAndModify(ShopCartDTO sdto) {

		log.info(" shopcart save: uid:{},cid:{},num:{},orderId:{}", sdto.getMemberid(), sdto.getDishid(), sdto.getNum(),sdto.getOrderId());
		// 删除
		if (sdto.getNum() <= 0) {
			return iShopCartService.delete(sdto.getMemberid(), sdto.getDishid(),sdto.getOrderId());
		}
		// 修改
		if (sdto.getNum() > 0) {
			return iShopCartService.update(sdto.getMemberid(), sdto.getDishid(), sdto.getNum(),sdto.getOrderId());
		}
		return ResultUtil.getResult(RespCode.Code.FAIL);
	}

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
	// 增加的时候走的接口
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ShopCartDTO sdto) {

			log.info("shopcart add param:  uid :{} , spid :{} , num:{} , stid :{} ", sdto.getMemberid(), sdto.getDishid(),
				sdto.getNum(), sdto.getMerchantId(), sdto.getHopetime(), sdto.getType());

		return iShopCartService.add(sdto.getMemberid(), sdto.getDishid(), sdto.getNum(), sdto.getMerchantId(),
				sdto.getHopetime(), sdto.getType());
	}
	// 增加的时候走的接口
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "addTwo", method = RequestMethod.POST)
	@ResponseBody
	public Result addTwo(ShopCartDTO sdto) {

		log.info("添加购物车接收参数shopcart add param:  uid :{} , spid :{} , num:{} , stid :{} ", sdto.getMemberid(), sdto.getDishid(),
				sdto.getNum(), sdto.getMerchantId());

		return iShopCartService.addTwo(sdto.getMemberid(), sdto.getDishid(), sdto.getNum(), sdto.getMerchantId(),sdto.getOrderId());
	}

}
