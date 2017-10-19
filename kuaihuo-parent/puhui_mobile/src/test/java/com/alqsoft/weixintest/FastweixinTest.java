package com.alqsoft.weixintest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.alqframework.pay.weixin.util.MD5Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.alqsoft.utils.SignUtils;
import com.alqsoft.utils.alipay.config.AlipayConfig;
import com.alqsoft.utils.alipay.sign.Base64;
import com.alqsoft.utils.alipay.sign.RSA;
import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.DownloadMediaResponse;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.github.sd4324530.fastweixin.api.response.UploadMediaResponse;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Title: FastweixinTest.java
 * @Description:微信测试类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午5:46:32
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class FastweixinTest {

    private static final Logger LOG = LoggerFactory.getLogger(FastweixinTest.class);

    @Test
    public void sfdsfdgfg(){
    	String signData = "aids=133,134,135&client_type=A&content=sdfghjkl;&orderNo=20170726144229783627_65&phone=18101357002&timestamp=1501136229&uuid=1248d4e0c2c54f16b68075531377e5e4";      	
    	//String signData = "client_type=I&timestamp=1500541146&phone=18848981859&uuid=4ac4592a3c6146d88726cc2bccef079a&id=1&provinceId=110&cityId=156696&detail=嘉里大通&countyId=156755&townId=110105002000&latitude=0.000000&longitude=0.000000&positionLevel=2&countyName=秦淮区";
    	//String signData="client_type=I&timestamp=1500540730&phone=18848981859&uuid=4ac4592a3c6146d88726cc2bccef079a&id=1&provinceId=110&cityId=156696&detail=嘉里大通&countyId=156868&townId=110105002000&latitude=0.000000&longitude=0.000000&positionLevel=2&countyName=建邺区";
    	/*Map<String, Object> map = new HashMap<String, Object>();
    	map.put("client_type", "I");
    	map.put("timestamp", "1500541146");
    	map.put("phone", "18848981859");
    	map.put("uuid", "4ac4592a3c6146d88726cc2bccef079a");
    	map.put("id", "1");
    	map.put("provinceId", "11");
    	map.put("cityId", "156696");
    	map.put("detail", "");
    	map.put("", "");
    	map.put("", "");
    	map.put("", "");
    	map.put("", "");
    	map.put("", "");*/
    	/*String signData = SignUtils.mapToLinkString2(map);
		signData = StringEscapeUtils.unescapeXml(signData);*/
    	String sign = "";
		byte[] b = null;  
	    try {  
	         b = (MD5Util.MD5Encode(signData,"utf-8")+"PHPFA").getBytes("utf-8");  
	    } catch (Exception e) {  
	         e.printStackTrace(); 
	    }  
	    if (b != null) {  
	        sign = Base64Utils.encodeToString(b);
	    }  
	    System.out.println(sign+"****************************"); 
    }
    
    
    @Test
    public void tesdsds(){
    	StringBuffer sa =new StringBuffer("service=\"mobile.securitypay.pay\"&partner=\"2088621785655902\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2F121.69.76.194%3A10034%2Fmobile%2Fview%2Falipay%2Fpayback\"&out_trade_no=\"PSAL_20170630142046002673\"&subject=\"商品支付\"&payment_type=\"1\"&seller_id=\"puhui315@yst315.com\"&total_fee=\"0.02\"&body=\"商品支付\"&it_b_pay=\"1m\"&return_url=\"http%3A%2F%2Fm.alipay.com\"&sign=\"OKzJpztX6JdpkoLgRgcA9xsMV86qAwMsUdrn6%2F%2FdfBi8zwQDC2iXS1qUjMQPxsC9J4SrjeHyp5py%0ANH3oIGq8keFCRX%2BVqvC2GKoDA3XdwqH%2Bx4MGUykWyoMV%2Bir%2BtINWBLJohMuNiWAzNC7nwG3Mf1Ka%0AqLuwQFcnKEeNh3VnyaU%3D%0A\"&sign_type=\"RSA\"");
    	System.out.println(sa.toString().replaceAll("\\", ""));
    }
    
    
    @Test
    public void test() {
         
    	String[] parameters={
               "service=\"mobile.securitypay.pay\"",//固定值（手机快捷支付）
               "partner=\"2088621785655902\"",//合作身份者ID（16位）
               "_input_charset=\"utf-8\"",
               "notify_url=\"http%253A%252F%252F121.69.76.194%253A10034%252Fmobile%252Fview%252Falipay%252Fpayback\"",//通知地址
               "out_trade_no=\"PSAL_20170630144328037394\"",//商户内部订单号
               "subject=\"商品支付\"",//测试
               "payment_type=\"1\"",//固定值
               "seller_id=\"puhui315@yst315.com\"",//账户邮箱
               "total_fee=\""+"0.02"+"\"",//支付金额（元）
               "it_b_pay=\"1m\"",
               "body=\"商品支付\"",//订单说明            
               "return_url=\"http%3A%2F%2Fm.alipay.com\""
         };
         String signOrderUrl = signAllString(parameters);
         System.out.println(signOrderUrl);
    }
    
    
    /**
     * 支付宝签名
     * @param array
     * @return
     */
    private String signAllString(String [] array){
       StringBuffer sb = new StringBuffer("");
       for (int i = 0; i < array.length; i++) {
          if(i==(array.length-1)){
             sb.append(array[i]);
          }else{
             sb.append(array[i]+"&");
          }
       }
       System.out.println(sb.toString());
       String sign = "";
       //String a = "partner=\"2088621785655902\"&out_trade_no=\"PSAL_20170630144328037394\"&subject=\"商品支付\"&body=\"商品支付\"&total_fee=\"0.02\"&notify_url=\"http%253A%252F%252F121.69.76.194%253A10034%252Fmobile%252Fview%252Falipay%252Fpayback\"&service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&return_url=\"http%3A%2F%2Fm.alipay.com\"&payment_type=\"1\"&seller_id=\"puhui315@yst315.com\"&it_b_pay=\"1m\"";
       try {
          sign = URLEncoder.encode(sign(sb.toString(), AlipayConfig.private_key, "utf-8"), "utf-8");//private_key私钥
       } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
       }
       sb.append("&sign=\""+sign+"\"&");
       sb.append("sign_type=\"RSA\"");
       return sb.toString();
    }
     


       
       public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
       
       /**
       * RSA签名
       * @param content 待签名数据
       * @param privateKey 商户私钥
       * @param input_charset 编码格式
       * @return 签名值
       */
       public static String sign(String content, String privateKey, String input_charset)
       {
            try 
            {
             byte[] decode = Base64.decode(privateKey);
             PKCS8EncodedKeySpec priPKCS8   = new PKCS8EncodedKeySpec(decode );
               KeyFactory keyf= KeyFactory.getInstance("RSA");
               PrivateKey priKey= keyf.generatePrivate(priPKCS8);

                java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

                signature.initSign(priKey);
                signature.update( content.getBytes(input_charset) );

                byte[] signed = signature.sign();
                
                return Base64.encode(signed);
            }
            catch (Exception e) 
            {
               e.printStackTrace();
            }
            
            return null;
        }
       
       /**
       * RSA验签名检查
       * @param content 待签名数据
       * @param sign 签名值
       * @param ali_public_key 支付宝公钥
       * @param input_charset 编码格式
       * @return 布尔值
       */
       public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
       {
          try 
          {
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
               byte[] encodedKey = Base64.decode(ali_public_key);
               PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
             java.security.Signature signature = java.security.Signature
             .getInstance(SIGN_ALGORITHMS);
          
             signature.initVerify(pubKey);
             signature.update( content.getBytes(input_charset) );
          
             boolean bverify = signature.verify( Base64.decode(sign) );
             return bverify;
             
          } 
          catch (Exception e) 
          {
             e.printStackTrace();
          }
          
          return false;
       }
       
       /**
       * 解密
       * @param content 密文
       * @param private_key 商户私钥
       * @param input_charset 编码格式
       * @return 解密后的字符串
       */
       public static String decrypt(String content, String private_key, String input_charset) throws Exception {
            PrivateKey prikey = getPrivateKey(private_key);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, prikey);

            InputStream ins = new ByteArrayInputStream(Base64.decode(content));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;

            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }

                writer.write(cipher.doFinal(block));
            }

            return new String(writer.toByteArray(), input_charset);
        }

       
       /**
       * 得到私钥
       * @param key 密钥字符串（经过base64编码）
       * @throws Exception
       */
       public static PrivateKey getPrivateKey(String key) throws Exception {

          byte[] keyBytes;
          
          keyBytes = Base64.decode(key);
          
          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
          
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          
          PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
          
          return privateKey;
       }
}
