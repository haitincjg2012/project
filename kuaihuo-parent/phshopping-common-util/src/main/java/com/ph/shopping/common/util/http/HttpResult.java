/**  
 * @Title:  HttpResult.java   
 * @Package com.ph.shopping.common.util.http   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月12日 上午9:58:39   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.ph.shopping.common.util.http;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * @ClassName: HttpResult
 * @Description:httpclient 返回结果
 * @author: 李杰
 * @date: 2017年5月12日 上午9:58:39
 * @Copyright: 2017
 */
public class HttpResult {

	/**
	 * 请求返回状态码
	 */
	private int statusCode;
	/**
	 * 头部信息
	 */
	private HashMap<String, Header> headerAll;
	/**
	 * 请求响应返回实体
	 */
	private HttpEntity httpEntity;

	/**
	 * 获取结果状态码
	 * 
	 * @return
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置结果状态码
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取结果头部信息
	 * 
	 * @return
	 */
	public HashMap<String, Header> getHeaders() {
		return headerAll;
	}

	/**
	 * 设置结果头部信息
	 * 
	 * @param headers
	 */
	public void setHeaders(Header[] headers) {
		headerAll = new HashMap<String, Header>();
		for (Header header : headers) {
			headerAll.put(header.getName(), header);
		}
	}

	/**
	 * 获取响应结果
	 * 
	 * @return
	 */
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	/**
	 * 设置响应结果
	 * 
	 * @param httpEntity
	 */
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
	/**
	 * 
	 * @Title: getRequestContent   
	 * @Description: 得到返回值   
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public String getResponseContent() {
		
		return HttpClientUtils.getContent(this);
	}
	/**
	 * 
	 * @Title: getResponseContent   
	 * @Description: 得到返回值   by encode   
	 * @param: @param encode
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public String getResponseContent(String encode) {

		return HttpClientUtils.getContent(this, encode);
	}
}
