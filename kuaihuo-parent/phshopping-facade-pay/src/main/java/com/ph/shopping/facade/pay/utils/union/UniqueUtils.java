package com.ph.shopping.facade.pay.utils.union;

import com.ph.shopping.facade.pay.utils.payeco.Base64;
import com.ph.shopping.facade.pay.utils.payeco.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @项目：phshopping-facade-pay
 * @描述：
 * @作者： 张霞
 * @创建时间： 19:20 2017/3/24
 * @Copyright @2017 by zhangxia
 */
public class UniqueUtils {
    private UniqueUtils() {
    }

    public static synchronized String getOrder() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(6);
    }
    public static synchronized String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取代付批次号
     * @return
     */
    public static synchronized String getBathNo(){
        try {
            String bath_no = "PHDF"+new String(Base64.decode(Util.generateKey(99999,8)));//批次号
            return bath_no;
        } catch (Exception e) {
            System.out.println("获取代付批次号失败");
            return "";
        }
    }
}
