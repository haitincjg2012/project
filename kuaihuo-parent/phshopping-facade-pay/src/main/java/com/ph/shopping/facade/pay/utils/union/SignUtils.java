package com.ph.shopping.facade.pay.utils.union;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUtils {
    private static final Log LOG = LogFactory.getLog(SignUtils.class);

    public static String signBySoft(String privateKey, String signStr)
            throws Exception {
        return signBySoft(privateKey, signStr, 2048);
    }

    public static String signBySoft(String privateKey, String signStr, int modulus)
            throws Exception {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1 = messageDigest.digest(signStr.getBytes("utf-8"));

            byte[] encryptxtArr = RsaUtils.encryptByPrivateKey(Base64.decode(privateKey.getBytes("utf-8")), sha1,
                    modulus);

            encryptxtArr = Base64.encode(encryptxtArr);
            return new String(encryptxtArr, "utf-8");
        } catch (Exception e) {
            LOG.error("软签名异常：", e);
            throw new Exception("软签名异常：" + e.getMessage());
        }
    }

    public static boolean verifyingSign(String publicKey, String signCipher, String signStr, int modulus)
            throws Exception {
        try {
            byte[] signCipherArr = RsaUtils.decryptByPublicKey(Base64.decode(publicKey.getBytes("utf-8")),
                    Base64.decode(signCipher.getBytes("utf-8")), modulus);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1Bytes = messageDigest.digest(signStr.getBytes("utf-8"));

            return Arrays.equals(sha1Bytes, signCipherArr);
        } catch (Exception e) {
            LOG.error("验签异常：", e);
            throw new Exception("验签异常：" + e.getMessage());
        }
    }

    public static boolean verifyingSign(String publicKey, String signCipher, String signStr)
            throws Exception {
        return verifyingSign(publicKey, signCipher, signStr, 2048);
    }

    /**
     * 获得特定的待签名数据
     *
     * @param jsonObject
     * @return
     */
    public static String getSignData(JSONObject jsonObject) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (isNotEmpty(entry.getValue() + "")) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        // 排序
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str) && !"null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            String signData = "custOrderId=2220120414000000002&retCode=SIGN_EXCEPTION&retDesc=验签不合格或者无权限&";
            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOg+PNOota7ppeHwDBB7zttL/OpUmaOCbei/J2t/FLNjumsMjJVGLfdxKlrqCQXVAzAZDIakQcbfRvvlhZJCEBAnL2tru8Qp6hs9dokI2zOXjf9wyk4hPQT5/noXEnlnXO1lr4MZeXhgLKCwZOQfTeE9WSWuTmdA5Dzgu0d/TvuJAgMBAAECgYBBEqdCeyQlFWyYaQVIXRhx09HS6s99xB79twnZker/9LKYKhT+AoMAsSG4BZlvm+bfxDUBSObxTUB7di099OrAw0J1F0QpCXL5Jrxc2NdW8/j1hXb77UbdgsUZg4hM5JkJ2QRxiwT0JyWUAIikSx0W+jUzTFkz1UFaMiZOwEX7rQJBAPcd1+dvclPiR77McvxEpje04dddIiGIQxCw7oZmARMOK33Jrd3+6nTv8xlhFSWM9/xgJzQq8n+aZ4X9+ZXEp9sCQQDwl4RolGsvtp/8jyFBRNGOTQ6CWM/77lK47swzeu50GCFNyf+tLNu8kOhyk+8LIftKFm44m8PZrsZDYZLNDGlrAkA6n0bHrWWGxshUV/XzKGnyDyQATiS5pbSbMg3zriEVHyhsF7r6Te3avc2CuMgmd1Gg+kJymrmaUcu7OqvJvrQ/AkBFQylwPgIZi1bFi6MEOj6l29MofU7q9TJFYSHSVDqfm27DCTsc7MQZphH1Ild3+gFw08JJc7ZPTbxwG3/6ne8fAkBDiUWE/r5GAVrLDFXIqglkG+25B0LPGT2ttTNL2Id/4QAbUXLxGuwmBY52B8m2Y2U8agJDL6YdsE+gvykcH9oB";
            // 签名过的字符串
            String sign = SignUtils.signBySoft(privateKey, signData);
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoPjzTqLWu6aXh8AwQe87bS/zqVJmjgm3ovydrfxSzY7prDIyVRi33cSpa6gkF1QMwGQyGpEHG30b75YWSQhAQJy9ra7vEKeobPXaJCNszl43/cMpOIT0E+f56FxJ5Z1ztZa+DGXl4YCygsGTkH03hPVklrk5nQOQ84LtHf077iQIDAQAB";

            // 字符未改变验签。
            boolean signResult = SignUtils.verifyingSign(publicKey, sign, signData);
            System.out.println("正面预期(true)：" + signResult);

            // 字符稍微改变验签。
            signData += "d";
            signResult = SignUtils.verifyingSign(publicKey, sign, signData);
            System.out.println("反面预期(false)：" + signResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}