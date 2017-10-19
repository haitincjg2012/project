package com.ph.shopping.common.core.other.message.jpush;

import java.util.List;

import com.ph.shopping.common.core.customenum.PushTypeEnum;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

/**
 * 
 * @ClassName:  JpushTemplate   
 * @Description:消息推送模板   
 * @author: 李杰
 * @date:   2017年5月25日 下午3:14:28     
 * @Copyright: 2017
 */
public class JpushTemplate {

	/**
	 * 
	 * @Title: buildPushObjectByAll   
	 * @Description: 推送给 ios 和 Android平台
	 * @param: @param pushData
	 * @param: @return      
	 * @return: PushPayload
	 * @author：李杰      
	 * @throws
	 */
	public static PushPayload buildPushObjectByAndroidAndIos(JPushData pushData) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(getAudienceBySingle(pushData))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(AndroidNotification.newBuilder()
								.setAlert(pushData.getAlter())
								.setTitle(pushData.getTitle())
								.build())
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(pushData.getAlter())
								.incrBadge(1)
								.setSound("default")
								.build())
						.addPlatformNotification(WinphoneNotification.newBuilder()
								.setAlert(pushData.getAlter())
								.setTitle(pushData.getTitle())
								.build())
				.build())
				.setMessage(Message.newBuilder()
						.setMsgContent(pushData.getContent())
						.setTitle(pushData.getTitle())
						.setContentType(pushData.getContentType())
						.addExtras(pushData.getExtras())
						.build()
				).build();
	}
	/**
	 * 
	 * @Title: buildPushObjectByAll   
	 * @Description: 推送给所有用户   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: PushPayload
	 * @author：李杰      
	 * @throws
	 */
	public static PushPayload buildPushObjectByAll(JPushData pushData) {
	    return PushPayload.newBuilder()
	            .setPlatform(Platform.all())//设置接受的平台
	            .setAudience(getAudienceBySingle(pushData))//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
	            .setNotification(Notification.alert(pushData.getAlter()))
	            .setMessage(Message.newBuilder()
						.setMsgContent(pushData.getContent())
						.setTitle(pushData.getTitle())
						.setContentType(pushData.getContentType())
						.addExtras(pushData.getExtras())
	            		.build())
	            .build();
	}
	/**
	 * 
	 * @Title: buildPushObjectByAndroid   
	 * @Description: 向Android 设备推送消息   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: PushPayload
	 * @author：李杰      
	 * @throws
	 */
	public static PushPayload buildPushObjectByAndroid(JPushData pushData) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())// 设置接受的平台
				.setAudience(getAudienceBySingle(pushData))// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(pushData.getAlter()))
				.setMessage(Message.newBuilder()
						.setMsgContent(pushData.getContent())
						.setTitle(pushData.getTitle())
						.setContentType(pushData.getContentType())
						.addExtras(pushData.getExtras())
						.build()
				).build();
	}
	/**
	 * 
	 * @Title: buildPushObjectByIos   
	 * @Description: 推送给 ios 平台   
	 * @param: @param pushData
	 * @param: @return      
	 * @return: PushPayload
	 * @author：李杰      
	 * @throws
	 */
	public static PushPayload buildPushObjectByIos(JPushData pushData) {
	    return PushPayload.newBuilder()
	            .setPlatform(Platform.ios())
	            .setAudience(getAudienceBySingle(pushData))
	            .setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(pushData.getAlter())
	                            .incrBadge(1)
								.setSound("default")
	                            .build())
	                    .build())
	             .setMessage(Message.newBuilder()
							.setMsgContent(pushData.getContent())
							.setTitle(pushData.getTitle())
							.setContentType(pushData.getContentType())
							.addExtras(pushData.getExtras())
							.build())
	             .build();
	}
	/**
	 * 
	 * @Title: buildPushObjectByWinphone   
	 * @Description:推送给   Winphone 平台
	 * @param: @param pushData
	 * @param: @return      
	 * @return: PushPayload
	 * @author：李杰      
	 * @throws
	 */
	public static PushPayload buildPushObjectByWinphone(JPushData pushData){
		return PushPayload.newBuilder()
	            .setPlatform(Platform.winphone())
	            .setAudience(getAudienceBySingle(pushData))
	            .setNotification(Notification.newBuilder()
	                    .addPlatformNotification(WinphoneNotification.newBuilder()
								.setAlert(pushData.getAlter())
								.setTitle(pushData.getTitle())
	                            .build())
	                    .build())
	             .setMessage(Message.newBuilder()
							.setMsgContent(pushData.getContent())
							.setTitle(pushData.getTitle())
							.setContentType(pushData.getContentType())
							.addExtras(pushData.getExtras())
							.build())
	             .build();
	}
	/**
	 * 
	 * @Title: getAudience   
	 * @Description: 得到推送模式   (只针对其中一种方式)
	 * @param: @param pushData
	 * @param: @return      
	 * @return: Audience
	 * @author：李杰      
	 * @throws
	 */
	private static Audience getAudienceBySingle(JPushData pushData) {
		Audience audience = null;
		if (null != pushData) {
			final PushTypeEnum pte = pushData.getPushType();
			if (null != pte) {
				switch (pte) {
				case BROADCAST:
					audience = Audience.all();
					break;
				case PEER_TO_PEER:
					List<String> regIds = pushData.getRegistrationIds();
					if (null == regIds || regIds.isEmpty()) {
						throw new RuntimeException("push message registrationIds is null");
					}
					audience = Audience.registrationId(regIds);
					break;
				case TAGS:
					List<String> tags = pushData.getTags();
					if (null == tags || tags.isEmpty()) {
						throw new RuntimeException("push message tags is null");
					}
					audience = Audience.tag(tags);
					break;
				case ALIAS:
					List<String> alias = pushData.getAlias();
					if (null == alias || alias.isEmpty()) {
						throw new RuntimeException("push message alias is null");
					}
					audience = Audience.alias(alias);
					break;
				default:
					break;
				}
			}
		}
		// 推送方式为空不能推送
		if (null == audience) {
			throw new RuntimeException("推送方式为空  <> push audience by type is null");
		}
		return audience;
	}
}
