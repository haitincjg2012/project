package com.ph.shopping.common.util.other.bankcard;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.ph.shopping.common.util.other.delayed.CustomDelayedCache;

/**
 * 
 * @ClassName:  BankCardUtil   
 * @Description:缓存银行卡名称数据  (备注前端必须长度控制在8 位) 卡片获取 分为 6 到8 这里取最大值 
 * @author: 李杰
 * @date:   2017年5月24日 下午4:10:45     
 * @Copyright: 2017
 */
public class BankCardUtil {

	private static final ConcurrentMap<String, String> bankCardName = new ConcurrentHashMap<String, String>();
	/**
	 * 过滤垃圾访问的数据 最大缓存数
	 */
	private static final int MAXNUM = 100;
	/**
	 * 过滤垃圾访问的数据
	 */
	private static final CustomDelayedCache<String, String> delayedCache = new CustomDelayedCache<String, String>();

	/**
	 * 
	 * @Title: isNoexists   
	 * @Description:判断如果存在一次请求的直接返回  (备注这里前端控制的必要长度是8位)
	 * @param: @param cardNum
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isNoexists(String cardNum) {
		boolean flag = false;
		if (StringUtils.isNotBlank(cardNum)) {
			cardNum = getCardNum(cardNum);
			flag = (delayedCache.get(cardNum) != null);
			clearNoexists();
		}
		return flag;
	}
	/**
	 * 
	 * @Title: putNoexists   
	 * @Description: 添加过滤值   
	 * @param: @param cardNum      
	 * @return: void      
	 * @throws
	 */
	public static void putNoexists(String cardNum) {
		if (StringUtils.isNotBlank(cardNum)) {
			cardNum = getCardNum(cardNum);
			delayedCache.put(cardNum, cardNum);
		}
	}
	/**
	 * 
	 * @Title: clearNoexists   
	 * @Description: 清楚缓存   
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private static void clearNoexists() {
		if (delayedCache.size() > MAXNUM) {
			delayedCache.clear();
		}
	}
	/**
	 * 
	 * @Title: getBankName   
	 * @Description: 根据银行卡号在缓存中获取卡名   
	 * @param: @param cardNum (备注：此处的 cardNum 建议在8位 以上)
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getBankName(String cardNum) {
		String cardName = null;
		if (StringUtils.isNotBlank(cardNum)) {
			cardNum = getCardNum(cardNum);
			cardName = bankCardName.get(cardNum);
		}
		return cardName;
	}
	/**
	 * 
	 * @Title: setCardName   
	 * @Description: 将查询出来的卡片值 存放在 缓存中   
	 * @param: @param cardNum      
	 * @return: void      
	 * @throws
	 */
	public static void setCardName(String cardNum, String cardName, Long id) {
		if (StringUtils.isNotBlank(cardNum) && StringUtils.isNotBlank(cardName) && null != id) {
			cardNum = getCardNum(cardNum);
			bankCardName.put(cardNum, new StringBuilder().append(id).append(",").append(cardName).toString());
		}
	}
	/**
	 * 
	 * @Title: getCardNum   
	 * @Description: 截取大于8位的卡号   
	 * @param: @param cardNum
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private static String getCardNum(String cardNum) {
		if (cardNum.length() >= 8) {
			cardNum = cardNum.substring(0, 8);
		}
		return cardNum;
	}
}
