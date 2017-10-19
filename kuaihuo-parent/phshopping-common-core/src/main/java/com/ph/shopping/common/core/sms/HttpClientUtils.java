/**  
 * @Title:  HttpClient.java   
 * @Package com.ph.shopping.common.util.http   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月12日 上午9:55:43   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.sms;

import com.ph.shopping.common.util.constants.HttpConstants;
import com.ph.shopping.common.util.http.HttpResult;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  HttpClient   
 * @Description:http请求工具  
 * @author: 李杰
 * @date:   2017年5月12日 上午9:55:43     
 * @Copyright: 2017
 */
public class HttpClientUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(com.ph.shopping.common.util.http.HttpClientUtils.class);

	private HttpClientUtils(){
		
	}
	/**
	 * 
	 * @Title: sendGet   
	 * @Description:发送get请求   by headers
	 * @param: @param url
	 * @param: @param headers
	 * @param: @param params
	 * @param: @param encoding
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendGet(String url, final Map<String, String> headers, final Map<String, String> params,
			String encoding) throws Exception {
		HttpResult result = new HttpResult();
		url = url + (null == params ? "" : assemblyParameter(params));
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(requestConfig);
		if (null != headers)
			httpget.setHeaders(assemblyHeader(headers));
		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = client.execute(httpget);
		if (null == response) {
			throw new RuntimeException("send get connect response is null");
		}
		HttpEntity entity = response.getEntity();
		result.setStatusCode(response.getStatusLine().getStatusCode());
		result.setHeaders(response.getAllHeaders());
		result.setHttpEntity(entity);
		return result;
	}
	
	/**
	 * 
	 * @Title: sendGet   
	 * @Description: 发送get请求  By encoding
	 * @param: @param url
	 * @param: @param params
	 * @param: @param encoding
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendGet(String url, final Map<String, String> params, final String encoding)
			throws Exception {
		return sendGet(url, null, params, encoding);
	}
	/**
	 * 
	 * @Title: sendGet   
	 * @Description: get by UTF-8   
	 * @param: @param url
	 * @param: @param params
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendGet(String url, final Map<String, String> params)
			throws Exception {
		return sendGet(url, null, params, HttpConstants.DEFAULT_ENCODING);
	}
	
	/**
	 * 
	 * @Title: sendPost   
	 * @Description: 发送post 请求  BY encoding
	 * @param: @param url
	 * @param: @param params
	 * @param: @param encoding
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendPost(String url, final Map<String, String> params, final String encoding)
			throws Exception {
		return sendPost(url, null, params, encoding);
	}
	/**
	 * 
	 * @Title: sendPost   
	 * @Description: post 请求 默认为   UTF-8
	 * @param: @param url
	 * @param: @param params
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendPost(String url, final Map<String, String> params)
			throws Exception {
		return sendPost(url, null, params, HttpConstants.DEFAULT_ENCODING);
	}
	/**
	 * 
	 * @Title: sendPost   
	 * @Description: 发送post 请求 by headers 
	 * @param: @param url
	 * @param: @param headers
	 * @param: @param params
	 * @param: @param encoding
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: HttpResult
	 * @author：李杰      
	 * @throws
	 */
	public static HttpResult sendPost(String url, final Map<String, String> headers, final Map<String, String> params,
			final String encoding) throws Exception {
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> param : params.entrySet()) {
			NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
			paramList.add(pair);
		}
		post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
		if (null != headers)
			post.setHeaders(assemblyHeader(headers));
		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = client.execute(post);
		if (null == response) {
			throw new RuntimeException("send post connect response is null");
		}
		HttpEntity entity = response.getEntity();
		HttpResult result = new HttpResult();
		result.setStatusCode(response.getStatusLine().getStatusCode());
		result.setHeaders(response.getAllHeaders());
		result.setHttpEntity(entity);
		return result;
	}
	/**
	 * 
	 * @Title: assemblyHeader   
	 * @Description:组装头部信息  
	 * @param: @param headers
	 * @param: @return      
	 * @return: Header[]
	 * @author：李杰      
	 * @throws
	 */
	public static Header[] assemblyHeader(final Map<String, String> headers) {
		Header[] allHeader = new BasicHeader[headers.size()];
		int i = 0;
		for (String str : headers.keySet()) {
			allHeader[i] = new BasicHeader(str, headers.get(str));
			i++;
		}
		return allHeader;
	}
	
	/**
	 * 
	 * @Title: assemblyParameter   
	 * @Description: 组装get请求参数   
	 * @param: @param parameters
	 * @param: @return      
	 * @return: List<NameValuePair>
	 * @author：李杰      
	 * @throws
	 */
	public static String assemblyParameter(final Map<String, String> parameters) {
		StringBuilder sbud = new StringBuilder("?");
		for (String str : parameters.keySet()) {
			sbud.append(str).append("=").append(parameters.get(str)).append("&");
		}
		if (sbud.length() > 0) {
			sbud.deleteCharAt(sbud.length() - 1);
		}
		return sbud.toString();
	}
	/**
	 * 
	 * @Title: getRequestConfig   
	 * @Description: 请求配置  
	 * @param: @return      
	 * @return: RequestConfig
	 * @author：李杰      
	 * @throws
	 */
	static final RequestConfig requestConfig = RequestConfig.custom()  
	        .setConnectTimeout(HttpConstants.CONNECT_TIMEOUT)
	        .setConnectionRequestTimeout(HttpConstants.REQUEST_TIMEOUT)  
	        .setSocketTimeout(HttpConstants.SOCKET_TIMEOUT).build();
	/**
	 * 
	 * @Title: getContent   
	 * @Description: 获取请求中的内容   
	 * @param: @param result
	 * @param: @param chart 字符格式
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String getContent(final HttpResult result, final String chart) {

		return getContentByChart(result, chart);
	}
	/**
	 * 
	 * @Title: getContent   
	 * @Description: 处理返回结果 默认"UTF-8"   
	 * @param: @param result
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String getContent(final HttpResult result) {
		return getContentByChart(result, HttpConstants.DEFAULT_ENCODING);
	}
	/**
	 * 
	 * @Title: getContentByChart   
	 * @Description:处理返回结果  
	 * @param: @param result
	 * @param: @param chart
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private static String getContentByChart(final HttpResult result, final String chart) {
		String resultStr = "";
		if (null != result) {
			HttpEntity entity = result.getHttpEntity();
			try {
				logger.debug("response code = " + result.getStatusCode());
				logger.debug("response result = " + result.getHttpEntity().toString());
				resultStr = EntityUtils.toString(entity, chart);
			} catch (Exception e) {
				logger.error("get http response content error", e);
			} finally {
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {
					logger.error("close http entity error", e);
				}
			}
		}
		return resultStr;
	}
	/**
	 * 
	 * @Title: consume   
	 * @Description: 关闭HttpEntity流
	 * @param: @param result      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	public static void consume(final HttpResult result) {
		try {
			HttpEntity entity = result.getHttpEntity();
			if (entity == null) {
				return;
			}
			if (entity.isStreaming()) {
				InputStream instream = entity.getContent();
				if (instream != null) {
					instream.close();
				}
			}
		} catch (IOException e) {
			logger.error("close http entity error", e);
		}
	}
}
