package com.ph.shopping.common.core.other.message.jpush.handle;

import com.ph.shopping.common.core.other.message.jpush.JPushData;
import com.ph.shopping.common.util.result.Result;
/**
 * 
 * @ClassName:  IJPushHandle   
 * @Description:消息推送处理   
 * @author: 李杰
 * @date:   2017年5月27日 上午10:51:09     
 * @Copyright: 2017
 */
public interface IJPushHandle {

	/**
	 * 
	 * @Title: sendPushByAndroidAndIos   
	 * @Description:消息推送 (推送对象 Android 和 ios)   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result sendPushByAndroidAndIos(JPushData pushData);
	/**
	 * 
	 * @Title: sendPushByAndroid   
	 * @Description: 发送平台Android
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result sendPushByAndroid(JPushData pushData);
	/**
	 * 
	 * @Title: sendPushByIos   
	 * @Description: 发送平台IOS 
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result sendPushByIos(JPushData pushData);
}
