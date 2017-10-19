package com.ph.shopping.facade.pay.utils.union;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by JinLei on 2016/10/17.
 */
public class HttpUtils {

    private static final Log LOG = LogFactory.getLog(HttpUtils.class);

    /**
     * 发送http请求
     *
     * @param url
     * @param jsonParam 加签的json参数
     * @param timeout
     * @return fail(失败) || 成功结果
     */
    public static String execute(String url, String jsonParam,
                                 int timeout) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost method = new HttpPost(url);

        StringEntity entity = new StringEntity(jsonParam, "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        method.setEntity(entity);

        JSONObject respJson = new JSONObject();
        String respString = null;
        try {
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            method.setConfig(requestConfig);

            LOG.info(String.format("[url=%s][req=%s]", url, jsonParam));
            HttpResponse resp = httpClient.execute(method);
            int statusCode = resp.getStatusLine().getStatusCode();
            respString = EntityUtils.toString(resp.getEntity());
            LOG.info(String.format("[statusCode=%s][resp=%s]", resp.getStatusLine().getStatusCode(), respString));
            // 通讯成功
            if (HttpStatus.SC_OK == statusCode) {
                respJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.SUCCESS.toString());
                respJson.put(SystemRetField.RET_BODY.toString(), respString);
                return respJson.toString();
            }
        } catch (Exception e) {
            LOG.error("发送http(s)发生异常：", e);
            respJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.EXCEPTION.toString());
            respJson.put(SystemRetField.RET_DESC.toString(), e.getMessage());
            return respJson.toString();
        }

        // 通用失败
        respJson.put(SystemRetField.RET_CODE.toString(), SystemRetCode.FAIL.toString());
        respJson.put(SystemRetField.RET_DESC.toString(), respString);
        return respJson.toString();
    }
}
