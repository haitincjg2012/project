package com.ph.shopping.util;

import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
* @ClassName: HttpClientUtils  
* @Description: 短信发送工具类  
* @author lijie  
* @date 2017年3月17日  
*
 */
public class HttpClientUtil {
	/**
	 * 
	* @Title: getHttpsToGet  
	* @Description: 发送GET请求
	* @param @param url
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public static String getHttpsToGet(String url) {
		RestTemplate restTemplate = new RestTemplate();
		String result = (String) restTemplate.getForObject(url, String.class, new Object[0]);
		return result;
	}
	/**
	 * 
	* @Title: getHttpsToGet  
	* @Description: 发送post请求
	* @param @param url
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public static String getHttpsToPost(String url, MultiValueMap<String, String> map) {
		RestTemplate restTemplate = new RestTemplate();
		String result = (String) restTemplate.postForObject(url, map, String.class, new Object[0]);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String geHttpsAll(String url, MultiValueMap<String, String> multiValueMap, HttpHeaders headerRequest,
			String type) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity responseEntity = null;

		HttpEntity bodyAndHeader = new HttpEntity(multiValueMap, headerRequest);

		if (StringUtils.upperCase(type).equals("GET"))
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, bodyAndHeader, String.class, new Object[0]);
		else if (StringUtils.upperCase(type).equals("POST")) {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST, bodyAndHeader, String.class, new Object[0]);
		}
		String jsonResult = (String) responseEntity.getBody();
		return jsonResult;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseEntity<String> getHttpHeaders(String url, MultiValueMap<String, String> multiValueMap,
			HttpHeaders headerRequest, String type) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity responseEntity = null;

		HttpEntity bodyAndHeader = new HttpEntity(multiValueMap, headerRequest);
		if (StringUtils.upperCase(type).equals("GET"))
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, bodyAndHeader, String.class, new Object[0]);
		else if (StringUtils.upperCase(type).equals("POST")) {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST, bodyAndHeader, String.class, new Object[0]);
		}
		return responseEntity;
	}

	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);

			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();

			con.setRequestMethod("HEAD");

			return con.getResponseCode() == 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean linkType(String URL) {
		Boolean result = Boolean.valueOf(false);
		String linkType = "";
		if (URL.contains(".")) {
			linkType = URL.substring(URL.lastIndexOf("."));
		}
		if ((linkType.contains("doc")) || (linkType.contains("xls")) || (linkType.contains("ppt"))
				|| (linkType.contains("pdf")) || (linkType.contains("rar")))
			result = Boolean.valueOf(true);
		else {
			result = Boolean.valueOf(false);
		}
		return result;
	}
}