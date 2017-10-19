package com.alqsoft.utils;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 下午6:10:33 Copyright
 */
public class Chat {
	/***
	 * 聊天的注册
	 */
	@Test
	public void chatRegist() {
		// 主路径
		String url = "https://console.tim.qq.com/v4/registration_service/register_account_v1";
		// 签字
		String usersig = "eJxFkF1rwjAUhv9Lr8c8SZt*DHYhTqS0WrvpKN6E0MRy7BJLGzfH2H9fDZVx7p6Hl-Oe8*Pt8rdH0XUoubDc76X35IH34LC6dtgrLo5W9SMmjDEKcLefqh-wbEZBgTBCfYB-iVIZi0d0QSE1mkkM2Ixkvdwv0nKxpO91WjS0Pl3wlX34DeouzizZERXQEAC-DD3MmkLqMm2KTTYPk2q9aucDrLabKwYHW0GUJ1VS7l8y7OOtCiKT6-b5vky23N12ax*M7Wg4ziQtauV4nBBCIIgmLur6fDGW2*9OuWf8-gHz71Y1";
		// 注册点
		String spn = "1";
		// 管理员账号
		String identifier = "admin";
		// appId
		String sdkappid = "1400026262";
		// 返回的格式
		String contenttype = "json";

		String random = "12345543211234554321123455432112";
		String uri = url + "?usersig=" + usersig + "&sdkappid=" + sdkappid + "&sdkappid=" + sdkappid
				+ "&random=" + random + "&identifier=" + identifier + "&contenttype=" + contenttype;

		APIHttpClient ac = new APIHttpClient(uri);

		JSONObject j = new JSONObject();

		j.put("Identifier", "sunhuijie");
		j.put("IdentifierType", 3);
		j.put("Password", "12345644dfd");

		String cc = ac.contentTypeJsonPost(j.toString());

		System.out.println("sendPost:" + cc);

	}

}
