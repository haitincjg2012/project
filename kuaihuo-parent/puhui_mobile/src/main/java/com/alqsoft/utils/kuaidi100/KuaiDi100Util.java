package com.alqsoft.utils.kuaidi100;

import java.util.List;
import java.util.Properties;

import org.alqframework.json.JsonUtil;
import org.alqframework.net.html.HttpClientUtils;
import org.alqframework.string.MyStringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alqsoft.entity.kuaidi100.KuaiDi100;
import com.alqsoft.entity.kuaidi100.KuaiDiData;

/**
 * 
 * @Title: KuaiDi100Util.java
 * @Description: 解析调用快递100 接口
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午8:38:22
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
 
public class KuaiDi100Util {

	/**
	 * 
	 * @Title: getKuaiDiJson
	 * @Description: 解析物流接口方法
	 * @param @param wuliuCode 快递公司代码
	 * @param @param wuliuNo 物流单号(快递单号)
	 * @param @param show
	 *        0：返回json字符串，1：返回xml对象，2：返回html对象，3：返回text文本。如果不填，默认返回json字符串
	 * @param @param muti 1:返回多行完整的信息，0:只返回一行信息。不填默认返回多行
	 * @param @param order desc：按时间由新到旧排列，asc：按时间由旧到新排列。不填默认返回倒序（大小不敏感）
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public KuaiDi100 getKuaiDiJson(Properties properties, String wuliuCode,
			String wuliuNo, String show, String muti, String order) {

		String key = properties.getProperty("kuaidi100Key");
		String url = properties.getProperty("kuaidi100Url");

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("key", key); // http://code.google.com/p/kuaidi-api/wiki/Open_API_HTML_API
		// map.add("id", key);
		map.add("com", wuliuCode);
		map.add("nu", wuliuNo);
		/*
		 * if (wuliuCode.equals("jiajiwuliu"))
		 * {//查询快递的电话号码，目前只有佳吉物流需要这个参数，其他公司请忽略 map.add("valicode",""); }
		 */
		map.add("show", show);
		map.add("order", order);

		String resultSuccess = HttpClientUtils.getHttpsToPost(url, map);

		RestTemplate rest = new RestTemplate();
		String htmlStr = rest.getForObject(resultSuccess, String.class);
		String orderNo = MyStringUtils.getMiddleString(htmlStr, "var nu=\"", "\"");
		System.out.println(orderNo);
		String result = rest.getForObject("http://www.kuaidi100.com/query?id=1&type=" + wuliuCode
				+ "&postid=" + orderNo + "&valicode=&temp=0.27232756764744444", String.class);
		System.out.println(result);

		KuaiDi100 entity = new KuaiDi100();
		JSONObject jsonObject = JSONObject.parseObject(result);
		entity.setMessage(jsonObject.getString("message"));
		entity.setState(jsonObject.getIntValue("state"));
		entity.setStatus(jsonObject.getIntValue("status"));
		entity.setCom(jsonObject.getString("com"));
		if (entity.getStatus() == 200 && jsonObject.get("data") != null) {
			// 把字符串json转为实体类集合
			List<KuaiDiData> kuaiDiDataList =JsonUtil.stringToList(result, "data",
					KuaiDiData.class);
			entity.setKuaiDiDataList(kuaiDiDataList);
		}
		return entity;

	}

}
