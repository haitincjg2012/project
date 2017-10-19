package com.alqsoft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientObject {
	private static Log logger = LogFactory.getLog(HttpClientObject.class);
	 private CloseableHttpClient httpClient = null;
	    private HttpResponse response;
	 
	    private HttpPost httpPost = null;
	 
	    private HttpGet httpGet = null;
	 
	    private String paramKey = "";
	 
	    private String paramValue = "";
	 
	    private String responseString;
	 
	    public void setParamKey(String paramKey) {
	        this.paramKey = paramKey;
	    }
	 
	    public void setParamValue(String paramValue) {
	        this.paramValue = paramValue;
	    }
	 
	    public String getResponseString() {
	        return responseString;
	    }
	 
	    public HttpClientObject() {
	        this.getHttpClient();
	    }
	 
	    private List<NameValuePair> getRequestBody() {
	        NameValuePair pair1 = new BasicNameValuePair(paramKey, paramValue);
	        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
	        pairList.add(pair1);
	        return pairList;
	    }
	 
	    public void submit() {
	        try {
	            if (httpPost != null) {
	                response = httpClient.execute(httpPost);
	                httpPost = null;
	            }
	            if (httpGet != null) {
	                response = httpClient.execute(httpGet);
	                httpGet = null;
	            }
	            this.response();
	        } catch (ClientProtocolException e) {           
	            e.printStackTrace();
	        } catch (IOException e) {           
	            e.printStackTrace();
	        }
	    }
	 
	    private void response() {
	    	String result ="";
        	BufferedReader in =null;
	        try {           
	            HttpEntity httpEntity = response.getEntity();
	            responseString = EntityUtils.toString(httpEntity);
	        } catch (ClientProtocolException e) {           
	            e.printStackTrace();
	        } catch (IOException e) {           
	            e.printStackTrace();
	        }finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	 
	    }
	 
	    public void setPost(String httpUrl) {
	        try {
	            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
	                    this.getRequestBody());
	            httpPost = new HttpPost(httpUrl);
	            httpPost.addHeader("Content-Type", "”application/json;charset=UTF-8");
	            httpPost.setEntity(requestHttpEntity);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public void setGet(String httpUrl) {
	        httpGet = new HttpGet(httpUrl);     
	        httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
	    }
	 
	    private void getHttpClient() {
	        BasicCookieStore cookieStore = new BasicCookieStore();
	        CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
	            public CookieSpec create(HttpContext context) {
	 
	                return new BrowserCompatSpec() {
	                    @Override
	                    public void validate(Cookie cookie, CookieOrigin origin)
	                            throws MalformedCookieException {
	                        // Oh, I am easy
	                    }
	                };
	            }
	 
	        };
	        Registry<CookieSpecProvider> r = RegistryBuilder
	                .<CookieSpecProvider> create()
	                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
	                .register(CookieSpecs.BROWSER_COMPATIBILITY,
	                        new BrowserCompatSpecFactory())
	                .register("easy", easySpecProvider).build();
	 
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setCookieSpec("easy").setSocketTimeout(20000)
	                .setConnectTimeout(20000).build();
	 
	        httpClient = HttpClients.custom().setDefaultCookieSpecRegistry(r)
	                .setDefaultRequestConfig(requestConfig)
	                .setDefaultCookieStore(cookieStore).build();        
	    }

		/**
	     * httpclient发送http get请求；
	     * @return
	     * @throws URISyntaxException 
	     * @throws IOException 
	     * @throws ClientProtocolException 
	     */
	    public static String sendGet(String uriPath,List<NameValuePair> ns) throws URISyntaxException, ClientProtocolException, IOException{
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder uri =  new URIBuilder();
			uri.setPath(uriPath);
			uri.addParameters(ns);
			URI u = uri.build();
			HttpGet httpget = new HttpGet(u);
			CloseableHttpResponse response = httpclient.execute(httpget);
			return EntityUtils.toString(response.getEntity());
			
	    }
		/**
	     * httpclient发送http post请求；
	     * @return
	     * @throws URISyntaxException 
	     * @throws IOException 
	     * @throws ClientProtocolException 
	     */
	    public static String sendPost(String uriPath,List<NameValuePair> ns) throws URISyntaxException, ClientProtocolException, IOException{
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder uri =  new URIBuilder();
			uri.setPath(uriPath);
			uri.addParameters(ns);
			URI u = uri.build();
			HttpPost httpost=new HttpPost(u);
			CloseableHttpResponse response = httpclient.execute(httpost);
			return EntityUtils.toString(response.getEntity());
			
	    }
	    
	    
}
