package com.alqsoft.utils.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alqsoft.utils.weixin.common.Configure;

public class RefundRequest {
	 
    //连接超时时间，默认10秒
    private int socketTimeout = 10000;
 
    //传输超时时间，默认30秒
    private int connectTimeout = 30000;
 
    //请求器的配置
    private RequestConfig requestConfig;
 
    //HTTP请求器
    private CloseableHttpClient httpClient;
    
    private static Logger logger = LoggerFactory.getLogger(RefundRequest.class);
 
    /**
     * 加载证书
     * @param path
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void initCert(String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //拼接证书的路径
        path = path+File.separator + Configure.getCertLocalPath();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
 
        //加载本地的证书进行https加密传输
        FileInputStream instream = new FileInputStream(new File(path));
        try {
            keyStore.load(instream, Configure.getMchid().toCharArray());  //加载证书密码，默认为商户ID
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
 
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getMchid().toCharArray())       //加载证书密码，默认为商户ID
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
 
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
 
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
 
    }
 
 
    /**
     * 通过Https往API post xml数据
     * @param url   API地址
     * @param xmlObj   要提交的XML数据对象
     * @param path    当前目录，用于加载证书
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String httpsRequest(String url, String xmlObj, String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //加载证书
        initCert(path);
 
        String result = null;
 
        HttpPost httpPost = new HttpPost(url);
 
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
 
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
 
        try {
            HttpResponse response = httpClient.execute(httpPost);
 
            HttpEntity entity = response.getEntity();
 
            result = EntityUtils.toString(entity, "UTF-8");
 
        } catch (ConnectionPoolTimeoutException e) {
        	logger.info("http get throw ConnectionPoolTimeoutException(wait time out)");
 
        } catch (ConnectTimeoutException e) {
        	logger.info("http get throw ConnectTimeoutException");
 
        } catch (SocketTimeoutException e) {
        	logger.info("http get throw SocketTimeoutException");
 
        } catch (Exception e) {
        	logger.info("http get throw Exception");
 
        } finally {
            httpPost.abort();
        }
 
        return result;
    }
}