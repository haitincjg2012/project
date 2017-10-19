package com.ph.shopping.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.PushConfig;
import com.ph.shopping.common.core.dto.PushDTO;
import com.ph.shopping.common.util.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xwolf
 * @since 1.8
 **/
@Component
public class PushUtil {

    private static Logger log = LoggerFactory.getLogger(SmsUtil.class);

    @Autowired
    private PushConfig pushConfig;

    public boolean push(PushDTO pushDTO){
        log.info("推送信息：{}", JSON.toJSON(pushDTO));
        String message = pushDTO.validateForm();
        if (Objects.nonNull(message)) {
            return Boolean.FALSE;
        }
        try {
            HttpResult httpResult = HttpClientUtils.sendPost(pushConfig.getUrl(), BeanToMap.getMapByStr(pushDTO),"UTF-8");
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("content:{}",content.toJSONString());
            if ("1".equals(content.getString("code"))){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Boolean.FALSE;
        }
    }

}
