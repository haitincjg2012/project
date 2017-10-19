package com.ph.shopping.common.core.other;

import java.util.HashMap;
import java.util.Map;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.cache.redis.impl.RedisCacheService;
import com.ph.shopping.common.util.spring.SpringUtil;

/**
 * 
* @ClassName: EcoPayHelper
* @Description: 易联支付支付类
* @author 王强
* @date 2017年6月21日 下午3:30:52
 */
public class EcoPayHelper {
	
	private static class SingletonInstance {
		public static final EcoPayHelper INSTANCE = new EcoPayHelper();
	}

	public static final EcoPayHelper getInstance() {
		return SingletonInstance.INSTANCE;
	}

	private EcoPayHelper() {
	}

	public Map<String, Object> ecoPay(String description, String amount, String orderNo, String payUrl) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"amount\":").append("\"").append(amount).append("\"").append(",").append("\"description\":")
					.append("\"").append(description).append("\"").append(",").append("\"orderNum\":").append("\"")
					.append(orderNo).append("\"").append("}");
			// redis中缓存支付信息，金额和描述(第三方支付用)
			@SuppressWarnings("unchecked")
			ICacheService<String, Object> iCacheService = SpringUtil.getBeanByClass(RedisCacheService.class);
			iCacheService.set(orderNo, sb.toString());
			Map<String, Object> payMap = new HashMap<String, Object>();
			payMap.put("description", description);
			payMap.put("amount", amount);
			payMap.put("orderNum", orderNo);
			payMap.put("payUrl", payUrl);
			return payMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public static void main(String[] args) {
//		TestPayVO payVO = GsonUtil.unpackt(EcoPayHelper.getInstance().ecoPay("用户线上充值", "100", "wq1234567", "www.wq.com"), TestPayVO.class);
//		System.out.println(new Gson().toJson(payVO));
//	}
}
