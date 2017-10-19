package com.ph.shopping.common.util.token;

import java.util.UUID;

/**
 * @项目：wholesale
 * @描述：
 * @作者： Mr.chang
 * @创建时间：2017/6/23
 * @Copyright @2017 by Mr.chang
 */
public class TokenUtil {

    /**
     * 生成GUID唯一token值
     * @return
     */
    public static String getToken(){
        return UUID.randomUUID().toString().toUpperCase();
    }
}
