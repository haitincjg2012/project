package com.ph.shopping.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.PuhuiConfig;
import com.ph.shopping.common.core.dto.PuhuiScoreDTO;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.rsa.Base64;
import com.ph.shopping.common.util.rsa.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xwolf
 * @date 2017-09-01 10:55
 * @since 1.8
 **/
@Component
public class PuhuiUtil {

    private static Logger log = LoggerFactory.getLogger(PuhuiUtil.class);

    public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHYNoo0B0yyyOu5jYsg9GEJK2kXRrcMx/6Ei+FD2twxAEPLLhyiK3BVTbU+yDpEkgBpdHq/oJPKOY97DjjLI6tN08K2YNXJ09WWvLEqAytu4mZ7r5bX+SL1du74MQJyF+ARkpd+Ec5b6TuWZBatWobnwB8oY6Ov4Uzvqex9md3SQIDAQAB";

    @Autowired
    private PuhuiConfig puhuiConfig;

    /**
     * 添加积分
     * @param scoreDTO
     * @return
     */
    public boolean saveScore(PuhuiScoreDTO scoreDTO){
        log.info("添加积分信息：{}", JSON.toJSON(scoreDTO));
        String message = scoreDTO.validateForm();
        if (Objects.nonNull(message)) {
            return Boolean.FALSE;
        }
        try {
            Map<String,String>  map = new HashMap<>();
            String json = JSONObject.toJSONString(scoreDTO);
            log.info("加密前数据:{}",json);
            byte[] encoderByteArray = RSAUtils.encryptByPublicKey(json.getBytes(),PUBLIC_KEY,"");
            map.put("json", Base64.encode(encoderByteArray));
            HttpResult httpResult = HttpClientUtils.sendPost(puhuiConfig.getAdd(), map,"UTF-8");
            JSONObject content = JSONObject.parseObject(httpResult.getResponseContent());
            log.info("添加积分信息content:{}",content.toJSONString());
            if ("200".equals(content.getString("code"))){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Boolean.FALSE;
        }

    }

}
